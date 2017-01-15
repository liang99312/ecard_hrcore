/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.client.report;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import net.sf.fjreport.util.ModalDialog;
import org.jhrcore.client.CommUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.report.ReportXlsCondition;
import org.jhrcore.entity.report.ReportXlsDetail;
import org.jhrcore.entity.report.ReportXlsScheme;
import org.jhrcore.mutil.QueryUtil;
import org.jhrcore.mutil.ReportUtil;
import org.jhrcore.ui.DeptSelectPanel;
import org.jhrcore.ui.JhrDatePicker;
import org.jhrcore.ui.action.CloseAction;

/**
 *
 * @author Administrator
 */
public class FillDialog extends JDialog {

    private Map<String, String> map = new HashMap<String, String>();
    private JButton jbtOk = new JButton("确定");
    private JButton jbtCan = new JButton("取消");
    private ReportXlsScheme export;
    private List<ReportXlsCondition> list;
    private Hashtable<String, ReportXlsDetail> detailKeys = new Hashtable<String, ReportXlsDetail>();
    private boolean click_ok = false;
    private String con_sql = "";

    public FillDialog(ReportXlsScheme export) {
        this.export = export;
        this.setTitle("导出模板：");
        initOthers();
    }

    private void initOthers() {
        map.put("DATE", "yyyy-MM-dd");
        map.put("TIME", "HH:mm:ss");
        map.put("TIMESTAMP", "yyyy-MM-dd HH:mm:ss");
        list = ReportUtil.getConditions(export, "CONDITION");
        for (ReportXlsDetail rd : export.getReportXlsDetails()) {
            detailKeys.put(rd.getCol(), rd);
        }
        int k = list.size();
        for (int i = 0; i < k; i++) {
            ReportXlsCondition tab = (ReportXlsCondition) list.get(i);
            ReportXlsDetail rd = detailKeys.get(tab.getCol());
            if (rd == null) {
                continue;
            }
            JLabel jl1 = new JLabel(rd.getCol_name() != null
                    ? rd.getCol_name()
                    : tab.getCol());
            jl1.setBounds(70, 30 * i + 10, 80, 20);
            add(jl1);
            String type = rd.getCol_type();
            if (type.equals("DATE") || type.equals("TIME") || type.equals("TIMESTAMP")) {
                String format = map.get(type);
                JPanel jp = new JPanel();
                jp.setBounds(150, 30 * i + 10, 160, 20);
                jp.setLayout(new java.awt.BorderLayout());
                JhrDatePicker picker = new JhrDatePicker(format);
                jp.add(picker);
                add(jp, i);

            } else if (type.equals("VARCHAR2") || type.equals("VARCHAR") || type.equals("CHAR")) {
                if ("部门".equals(tab.getUi_type())) {
                    JComponent jcomp = createDeptEditor();
                    jcomp.setBounds(150, 30 * i + 10, 160, 20);
                    add(jcomp, i);
                } else {
                    JTextField jdept = new JTextField();
                    jdept.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
                    jdept.setBounds(150, 30 * i + 10, 160, 20);
                    add(jdept, i);
                }
            } else if (type.equals("NUMBER")) {
                JNumberField intField = new JNumberField(rd.getCol_len(), rd.getCol_scale());
                intField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
                intField.setBounds(150, 30 * i + 10, 160, 20);
                add(intField, i);
            }
        }
        jbtOk.setBounds(120, 30 * k + 120, 60, 22);
        jbtCan.setBounds(200, 30 * k + 120, 60, 22);
        add(jbtOk);
        add(jbtCan);
        this.setModal(true);
        setLayout(null);
        setSize(400, k * 30 + 180 + 10);
        jbtOk.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                con_sql = getsql();
                click_ok = true;
                dispose();
            }
        });
        CloseAction.doCloseAction(jbtCan);
    }

    public boolean isClick_ok() {
        return click_ok;
    }

    public String getCon_sql() {
        return con_sql;
    }

//获取拼接的条件语句
    public String getsql() {
        String tmp_condition_expression = this.buildSql();
        SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String simplesql = "";
        String field = "";
        for (int i = 0; i < list.size(); i++) {
            ReportXlsCondition tab = list.get(i);
            ReportXlsDetail rd = detailKeys.get(tab.getCol());
            if (rd == null) {
                continue;
            }
            //字段
            field = " " + tab.getCol() + " ";
            //条件
            String cond = tab.getOperator();
            String text = "";
            //字段类型
            String type = rd.getCol_type();
            JPanel jpanel = (JPanel) getContentPane();
            if (type.equals("CHAR") || type.equals("VARCHAR2") || type.equals("NUMBER")) {
                //值
                if ("部门".equals(tab.getUi_type())) {
                    JComponent jcomp = ((JComponent) jpanel.getComponent(i));
                    JDeptField jdeptfield = (JDeptField) jcomp.getComponent(0);
                    text = "";
                    if (null != jdeptfield.getText()) {
                        text = jdeptfield.getText() == null ? "" : jdeptfield.getText();
                    }
                } else {
                    text = ((JTextField) jpanel.getComponent(i)).getText() == null ? "" : ((JTextField) jpanel.getComponent(i)).getText();
                }
            }
            //sql 串

            if (type.equals("CHAR") || type.equals("VARCHAR2")) {
                if (cond.contains("like")) {//like 情形 默认全字 前后匹配 格式  '%xxxx%'
                    simplesql = field + " like '" + cond.replace("like", text) + "' ";
                } else {
                    simplesql = field + cond + " '" + text + "' ";
                }
                tmp_condition_expression = tmp_condition_expression.replace(QueryUtil.getTempStr(i + 1), simplesql);
                continue;
            }
            //时间
            if (type.equals("DATE") || type.equals("TIME") || type.equals("TIMESTAMP")) {
                Date date = ((JhrDatePicker) ((JPanel) jpanel.getComponent(i)).getComponent(0)).getDate();
                if (null == date) {
                    date = CommUtil.getServerDate();
                }
                if (type.equals("DATE")) {
                    myfmt = new SimpleDateFormat("yyyy-MM-dd");
                    simplesql = field + cond + " to_date('" + myfmt.format(date) + "','yyyy-mm-dd') ";
                }
                if (type.equals("TIME")) {
                    myfmt = new SimpleDateFormat("HH:mm:ss");
                    simplesql = field + cond + " to_date('" + myfmt.format(date) + "','hh24:mi:ss') ";
                }
                if (type.equals("TIMESTAMP")) {
                    simplesql = field + cond + " to_date('" + myfmt.format(date) + "','yyyy-mm-dd hh24:mi:ss') ";
                }
                tmp_condition_expression = tmp_condition_expression.replace(QueryUtil.getTempStr(i + 1), simplesql);
                continue;
            }
            //数字
            if (type.equals("NUMBER")) {
                if (cond.contains("like")) {//like 情形 默认全字 前后匹配 格式  '%xxxx%'
                    simplesql = field + " like '" + cond.replace("like", text) + "' ";
                } else {
                    simplesql = field + cond + text;
                }
                tmp_condition_expression = tmp_condition_expression.replace(QueryUtil.getTempStr(i + 1), simplesql);
                continue;
            }
        }
        return " AND " + tmp_condition_expression;
    }

    private JComponent createDeptEditor() {
        final JDeptField textfield = new JDeptField();
        textfield.setSize(140, 20);
        int align = JTextField.LEFT;
        textfield.setHorizontalAlignment(align);
        textfield.setEditable(false);
        JPanel jpanel = new JPanel(new BorderLayout());
        jpanel.setBorder(null);
        jpanel.setFocusTraversalKeysEnabled(false);
        JButton btnSelect = new JButton("...");
        btnSelect.setPreferredSize(new java.awt.Dimension(22, 22));
        jpanel.add(textfield, BorderLayout.CENTER);
        jpanel.add(btnSelect, BorderLayout.EAST);
        //条件筛选 删除
        btnSelect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                final DeptSelectPanel deptSelectManyPanel = new DeptSelectPanel(UserContext.getDepts(false));
                if (ModalDialog.doModal(null, deptSelectManyPanel, "选择部门")) {
                    DeptCode deptcode = deptSelectManyPanel.getCur_dept();
                    textfield.setText(deptcode.getDept_code());
                    textfield.setDeptcode(deptcode);
                }
            }
        });
        return jpanel;
    }

    private String buildSql() {
        String tmp_condition_expression = "";
        tmp_condition_expression = export.condition_expression;
        if (tmp_condition_expression == null || tmp_condition_expression.equals("")) {
            tmp_condition_expression = "";
            for (int m = 1; m <= list.size(); m++) {
                if (m != 1) {
                    tmp_condition_expression = tmp_condition_expression + "+";
                }
                tmp_condition_expression = tmp_condition_expression + m;
            }
        }
        tmp_condition_expression = QueryUtil.tranExpStr(tmp_condition_expression, list.size());

        tmp_condition_expression = tmp_condition_expression.replaceAll("\\+", " AND ");
        tmp_condition_expression = tmp_condition_expression.replaceAll(",", " OR ");
        return tmp_condition_expression;
    }
}
