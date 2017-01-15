/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * XlsModelDefinePanel.java
 *
 * Created on 2012-8-8, 15:58:54
 */
package org.jhrcore.client.report;

import com.foundercy.pf.control.table.FTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.util.UtilTool;
import org.jhrcore.entity.report.ReportXlsCondition;
import org.jhrcore.entity.report.ReportXlsDetail;
import org.jhrcore.entity.report.ReportXlsScheme;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.impl.ReportImpl;
import org.jhrcore.mutil.ReportUtil;
import org.jhrcore.ui.action.CloseAction;
import org.jhrcore.util.MsgUtil;

/**
 *
 * @author mxliteboss
 */
public class XlsModelDefinePanel extends javax.swing.JPanel {

    private List scheme_list = new ArrayList();
    private ReportXlsScheme curExportScheme = null;
    private JComboBoxBinding scheme_binding;
    private FTable fTable;
    private FTable selfTable;
    private FTable conditionTable;
    private FTable orderTable;
    private String cur_exp = "";
    private String module_code = "XlsModelDefinePanel";

    /** Creates new form XlsModelDefinePanel */
    public XlsModelDefinePanel() {
        initComponents();
        initOthers();
        setupEvents();
    }

    private void initOthers() {
        fTable = new FTable(ReportXlsDetail.class, false, false, false, module_code);
        pnlLeft.add(fTable);
        selfTable = new FTable(ReportXlsDetail.class, false, false, false, module_code);
        pnlMid.add(selfTable);
        conditionTable = new FTable(ReportXlsCondition.class, new String[]{"order_no", "col", "operator", "ui_type"}, false, false, false, module_code);
        orderTable = new FTable(ReportXlsCondition.class, new String[]{"order_no", "col", "order_type"}, false, false, false, module_code);
        pnlCondition.add(conditionTable);
        pnlOrder.add(orderTable);
        conditionTable.setEditable(true);
        orderTable.setEditable(true);
        scheme_list = CommUtil.fetchEntities("from ReportXlsScheme  es where es.scheme_type ='1'");
        scheme_binding = SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE, scheme_list, jcbbScheme);
        scheme_binding.bind();
    }

    /**
     *
     *根据方案刷新页面
     * 
     */
    private void setCurExportScheme() {
        fTable.deleteAllRows();
        selfTable.deleteAllRows();
        conditionTable.deleteAllRows();
        orderTable.deleteAllRows();
        if (null == curExportScheme || null == curExportScheme.getReportXlsScheme_key()) {
            jtfTitle.setText("");
            mc.setText("");
            return;
        }
        ReportUtil.readXlsScheme(curExportScheme);
        List<ReportXlsDetail> fillList = new ArrayList(curExportScheme.getReportXlsDetails());
        List<ReportXlsDetail> selList = new ArrayList();
        List<ReportXlsCondition> tab1List = new ArrayList(curExportScheme.getReportXlsConditions());
        for (ReportXlsDetail fill : fillList) {
            if (fill.isUsed()) {
                selList.add(fill);
            }
        }
        fillList.removeAll(selList);
        SysUtil.sortListByInteger(selList, "order_no");
        SysUtil.sortListByStr(fillList, "col");
        SysUtil.sortListByInteger(tab1List, "order_no");
        selfTable.setObjects(selList);
        fTable.setObjects(fillList);
        fTable.setEditable(true);
        selfTable.setEditable(true);
        for (ReportXlsCondition tab : tab1List) {
            if ("CONDITION".equals(tab.getCondition_type())) {
                conditionTable.addObject(tab);
            } else if ("ORDER".equals(tab.getCondition_type())) {
                orderTable.addObject(tab);
            }
        }
        if (null == curExportScheme.getCondition_expression() || curExportScheme.getCondition_expression().length() == 0) {
            refreshConditonExp(tab1List.size(), false);
        } else {
            cur_exp = curExportScheme.getCondition_expression();
            txtQueryExp.setText(cur_exp);
        }
        jtfTitle.setText(curExportScheme.getScheme_title());
        mc.setText(curExportScheme.getEntity_name());
    }

    private void setupEvents() {
        ActionListener al_select = new ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableSelect();
            }
        };
        btn_cx.addActionListener(al_select);
        mc.addActionListener(al_select);
        //条件筛选 删除
        btnDelCond.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                removeData(jtpMain.getSelectedIndex());
            }
        });

        btnAddCond.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addData(jtpMain.getSelectedIndex());
            }
        });
        btnAddDetail.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addDetail();
            }
        });
        btnDelDetail.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                removeDetail();
            }
        });

        jcbbScheme.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                curExportScheme = (ReportXlsScheme) jcbbScheme.getSelectedItem();
                setCurExportScheme();
            }
        });
        btnSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                saveScheme();
            }
        });
        btnOk.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                exportData();
            }
        });
        CloseAction.doCloseAction(btnCancel);
        btnDel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                deleteScheme();
            }
        });
        btnUp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                upDetail(-1);
            }
        });

        btnDown.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                upDetail(1);
            }
        });
        if (scheme_list.size() > 0) {
            jcbbScheme.setSelectedIndex(0);
        }
    }

    /**
     *
     *是否选择匹配项
     *
     **/
    public boolean validityKye() {
        selfTable.editingStopped();
        String key = "";
        for (Object obj : selfTable.getObjects()) {
            ReportXlsDetail tab = (ReportXlsDetail) obj;
            if (tab.isId_flag()) {
                key += tab.getCol() + "#";
            }
        }
        if (key.length() == 0) {
            JOptionPane.showMessageDialog(null, "至少在导出字段中选择一个作为匹配项！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    public void exportData() {
        if (selfTable.getObjects().isEmpty()) {
            JOptionPane.showMessageDialog(null, "请选择要导出的数据列！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (validityKye()) {
            return;
        }
        ReportUtil.exportExcel(curExportScheme, jcbOpen.isSelected(), (JFrame) JOptionPane.getFrameForComponent(btnOk));
    }

    public void deleteScheme() {
        if (null == curExportScheme) {
            return;
        }
        Object obj = jcbbScheme.getSelectedItem();
        if (obj == null || ((ReportXlsScheme) obj).getNew_flag() == 1) {
            return;
        }
        ReportXlsScheme exportDataScheme = (ReportXlsScheme) obj;
        if (JOptionPane.showConfirmDialog(null, "确认方案\"" + exportDataScheme.getScheme_name() + "\"删除吗？", "确认方案删除", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null) == JOptionPane.YES_OPTION) {
            String schemeKey = exportDataScheme.getReportXlsScheme_key();
            String ex_sql = "delete from ReportXlsCondition  where reportXlsScheme_key ='" + schemeKey + "';";
            ex_sql += "delete from ReportXlsDetail where reportXlsScheme_key='" + schemeKey + "';";
            ex_sql += "delete from ReportXlsScheme where reportXlsScheme_key='" + schemeKey + "';";
            ValidateSQLResult result = CommUtil.excuteSQLs(ex_sql, ";");
            if (result.getResult() == 0) {
                scheme_list.remove(obj);
                scheme_binding.unbind();
                scheme_binding.bind();
            } else {
                MsgUtil.showHRDelErrorMsg(result);
            }
        }

    }

    private void addData(int tabIndex) {
        List list = selfTable.getSelectObjects();
        if (list.isEmpty()) {
            return;
        }
        if (tabIndex == 0) {
            for (Object obj : list) {
                ReportXlsDetail fsd = (ReportXlsDetail) obj;
                ReportXlsCondition tc = (ReportXlsCondition) UtilTool.createUIDEntity(ReportXlsCondition.class);
                tc.setCol(fsd.getCol());
                tc.setOrder_no(conditionTable.getObjects().size() + 1);
                conditionTable.addObject(tc);
            }
            conditionTable.updateUI();
            refreshConditonExp(conditionTable.getObjects().size(), true);
            curExportScheme.setCondition_expression(cur_exp);
        } else {
            for (Object obj : list) {
                ReportXlsDetail fsd = (ReportXlsDetail) obj;
                ReportXlsCondition tc = (ReportXlsCondition) UtilTool.createUIDEntity(ReportXlsCondition.class);
                tc.setCol(fsd.getCol());
                tc.setOrder_no(orderTable.getObjects().size() + 1);
                tc.setCondition_type("ORDER");
                orderTable.addObject(tc);
            }
            orderTable.updateUI();
        }
    }

    private void removeData(int tabIndex) {
        if (tabIndex == 0) {
            conditionTable.deleteSelectedRows();
            List list = conditionTable.getObjects();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ReportXlsCondition tc = (ReportXlsCondition) list.get(i);
                tc.setOrder_no(i + 1);
            }
            conditionTable.updateUI();
            refreshConditonExp(size, true);
        } else {
            orderTable.deleteSelectedRows();
        }
    }

    /**
     *
     *右边显示列
     * 
     */
    private void addDetail() {
        List<Object> objList = fTable.getSelectObjects();
        fTable.deleteSelectedRows();
        selfTable.addObjects(objList);
        selfTable.setRowSelectionInterval(selfTable.getRowCount() - 1, selfTable.getRowCount() - 1);
    }

    /**
     *
     *左边显示列
     * 
     */
    private void removeDetail() {
        List<Object> objList = selfTable.getSelectObjects();
        selfTable.deleteSelectedRows();
        fTable.addObjects(objList);
        fTable.setRowSelectionInterval(fTable.getRowCount() - 1, fTable.getRowCount() - 1);
    }

    /**
     *
     *根据表 查询表结构
     * 
     */
    public void tableSelect() {
        if (null == mc.getText() || mc.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "请输入要查询的表名！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String tableName = mc.getText().toUpperCase();
        boolean rebuild = jcbRebuid.isSelected();
        if (rebuild) {
            if (JOptionPane.showConfirmDialog(null, "重新生成 将会清空此表有关的所有方案和字段信息！ 确认要清空么？", "确认重新生成",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null) != JOptionPane.YES_OPTION) {
                return;
            }
        }
        String hql = "from ReportXlsScheme t left join fetch t.reportXlsDetails where t.reportXlsScheme_key ='" + tableName + "'";
        ReportXlsScheme scheme = (ReportXlsScheme) CommUtil.fetchEntityBy(hql);
        if (scheme == null || rebuild) {
            int i = ReportImpl.getTabColumn(tableName);
            if (i == 2) {
                JOptionPane.showMessageDialog(null, "您输入的表名不存在，请重新输入！", "错误", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            scheme = (ReportXlsScheme) CommUtil.fetchEntityBy(hql);
        }
        scheme = createNewScheme(scheme);
        scheme.setEntity_name(tableName);
        ReportXlsScheme new_rs = null;
        for (Object obj : scheme_list) {
            ReportXlsScheme rs = (ReportXlsScheme) obj;
            if (rs.getNew_flag() == 1) {
                new_rs = rs;
                break;
            }
        }
        if (new_rs != null) {
            scheme_list.remove(new_rs);
        }
        scheme_list.add(0, scheme);
        scheme_binding.unbind();
        scheme_binding.bind();
        jcbbScheme.setSelectedIndex(0);
    }

    private ReportXlsScheme createNewScheme(ReportXlsScheme scheme) {
        ReportXlsScheme newScheme = (ReportXlsScheme) UtilTool.createUIDEntity(ReportXlsScheme.class);
        newScheme.setScheme_type("1");
        List<String> fields = Arrays.asList(new String[]{"col", "id_flag", "col_name", "col_type", "col_len", "col_scale"});
        for (ReportXlsDetail fsd : scheme.getReportXlsDetails()) {
            ReportXlsDetail newDetail = (ReportXlsDetail) UtilTool.createUIDEntity(ReportXlsDetail.class);
            newDetail.setReportXlsScheme(newScheme);
            PublicUtil.copyProperties(fsd, newDetail, fields, fields);
            newScheme.getReportXlsDetails().add(newDetail);
        }
        return newScheme;
    }

    /**
     * 该方法用于根据指定方式上/下移选择字段的顺序
     * @param step ：1：下移；-1上移
     */
    private void upDetail(int step) {
        if (selfTable.getObjects().isEmpty()) {
            return;
        }
        int size = selfTable.getObjects().size();
        int row = selfTable.getCurrentRowIndex();
        Object obj = selfTable.getCurrentRow();
        row = row + step;
        if (row >= size) {
            row = 0;
        } else if (row == -1) {
            row = size - 1;
        }
        selfTable.getObjects().remove(obj);
        selfTable.getObjects().add(row, obj);
        selfTable.setRowSelectionInterval(row, row);
        selfTable.updateUI();
    }

    /**
     *
     *保存方案
     * 
     */
    private void saveScheme() {
        if (selfTable.getObjects().isEmpty()) {
            JOptionPane.showMessageDialog(null, "请选择要导出的数据列！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        fTable.editingStopped();
        selfTable.editingStopped();
        conditionTable.editingStopped();
        orderTable.editingStopped();
        if (validityKye()) {
            return;
        }
        String scheme_name = (String) JOptionPane.showInputDialog(JOptionPane.getFrameForComponent(btnSave), "", "请输入方案名：", JOptionPane.INFORMATION_MESSAGE, null, null, curExportScheme.getScheme_name());
        if (scheme_name == null) {
            return;
        }
        if (scheme_name.trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "方案名不可为空!");
            return;
        }
        Set<ReportXlsDetail> details = new HashSet();
        int i = 1;
        for (Object obj : selfTable.getObjects()) {
            ReportXlsDetail rd = (ReportXlsDetail) obj;
            rd.setUsed(true);
            rd.setReportXlsScheme(curExportScheme);
            rd.setOrder_no(i);
            details.add(rd);
            i++;
        }
        for (Object obj : fTable.getObjects()) {
            ReportXlsDetail rd = (ReportXlsDetail) obj;
            rd.setUsed(false);
            rd.setReportXlsScheme(curExportScheme);
            details.add(rd);
        }
        Set<ReportXlsCondition> set = new HashSet(conditionTable.getObjects());
        set.addAll(orderTable.getObjects());
        for (ReportXlsCondition rc : set) {
            rc.setReportXlsScheme(curExportScheme);
        }
        curExportScheme.setReportXlsConditions(set);
        curExportScheme.setReportXlsDetails(details);
        curExportScheme.setScheme_name(scheme_name);
        curExportScheme.setScheme_type("1");
        curExportScheme.setCondition_expression(txtQueryExp.getText());
        curExportScheme.setScheme_title(jtfTitle.getText() == null ? "JHR导出文件" : jtfTitle.getText());
        ValidateSQLResult result = ReportImpl.saveXlsScheme(curExportScheme, curExportScheme.getNew_flag() == 1);
        if (result.getResult() == 0) {
            curExportScheme.setNew_flag(0);
            JOptionPane.showMessageDialog(null, "保存成功");
            jcbbScheme.updateUI();
        } else {
            MsgUtil.showHRSaveErrorMsg(result);
        }
    }

    public String getQueryText() {
        return txtQueryExp.getText().replace(" ", "");
    }

    private void refreshConditonExp(int order_no, boolean add_flag) {
        if (cur_exp == null || cur_exp.trim().length() == 0) {
            cur_exp = "";
            correctExp(order_no);
        }
        if (add_flag) {
            if (order_no == 1) {
                cur_exp = "1";
            } else {
                cur_exp += "+" + order_no;
            }
        } else {
            if (cur_exp.length() > 2) {
                cur_exp = cur_exp.substring(0, cur_exp.length() - 1);
            } else {
                cur_exp = "";
            }
        }
        txtQueryExp.setText("");
        txtQueryExp.setText(cur_exp);
    }

    private void correctExp(int count) {
        for (int i = 0; i < count; i++) {
            cur_exp += (i + 1) + "+";
        }
        if (cur_exp == null) {
            cur_exp = "";
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mc = new javax.swing.JTextField();
        btn_cx = new javax.swing.JButton();
        jcbRebuid = new javax.swing.JCheckBox();
        pnlLeft = new javax.swing.JPanel();
        btnAddDetail = new javax.swing.JButton();
        btnDelDetail = new javax.swing.JButton();
        btnUp = new javax.swing.JButton();
        btnDown = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        pnlMid = new javax.swing.JPanel();
        btnAddCond = new javax.swing.JButton();
        btnDelCond = new javax.swing.JButton();
        jtpMain = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        pnlCondition = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtQueryExp = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        pnlOrder = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jtfTitle = new javax.swing.JTextField();
        jcbOpen = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jcbbScheme = new javax.swing.JComboBox();
        btnSave = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        mc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        mc.setPreferredSize(new java.awt.Dimension(6, 22));

        btn_cx.setText("查询");
        btn_cx.setPreferredSize(new java.awt.Dimension(6, 23));

        jcbRebuid.setText("重新生成");

        pnlLeft.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlLeft.setLayout(new java.awt.BorderLayout());

        btnAddDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/select_one.png"))); // NOI18N

        btnDelDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/remove_one.png"))); // NOI18N

        btnUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/move_up.png"))); // NOI18N

        btnDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/move_down.png"))); // NOI18N

        jLabel3.setText("选定字段：");

        pnlMid.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlMid.setLayout(new java.awt.BorderLayout());

        btnAddCond.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/min.png"))); // NOI18N

        btnDelCond.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/del1.png"))); // NOI18N

        pnlCondition.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlCondition.setLayout(new java.awt.BorderLayout());

        jLabel2.setText("条件表达式：");

        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("注：加号( + )表示并且关系，");

        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("    逗号( ,)表示或者关系");

        jLabel9.setForeground(new java.awt.Color(255, 0, 0));
        jLabel9.setText("    用( )可以确保优先关系，如：1+(2,3)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtQueryExp, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
            .addComponent(pnlCondition, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(pnlCondition, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQueryExp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jtpMain.addTab("数据筛选", jPanel1);

        pnlOrder.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlOrder.setLayout(new java.awt.BorderLayout());
        jtpMain.addTab("数据排序", pnlOrder);

        jLabel4.setText("报表标题：");

        jcbOpen.setText("导出直接打开");

        jLabel1.setText("方案：");

        btnSave.setText("保存");

        btnDel.setText("删除");

        btnOk.setText("导出");

        btnCancel.setText("关闭");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 985, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbbScheme, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(467, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jcbbScheme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave)
                    .addComponent(btnDel)
                    .addComponent(btnOk)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(mc, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_cx, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcbRebuid)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnDelDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnUp, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnAddDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnDown, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pnlMid, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnAddCond, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnDelCond, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addComponent(jtpMain, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE))
                            .addComponent(jLabel3)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbOpen)))
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btn_cx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jcbRebuid)
                                    .addComponent(mc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(pnlMid, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                                    .addComponent(pnlLeft, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                                    .addComponent(jtpMain, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(btnAddDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDelDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUp, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDown, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jtfTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbOpen)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(btnAddCond, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelCond, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCond;
    private javax.swing.JButton btnAddDetail;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnDelCond;
    private javax.swing.JButton btnDelDetail;
    private javax.swing.JButton btnDown;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUp;
    private javax.swing.JButton btn_cx;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JCheckBox jcbOpen;
    private javax.swing.JCheckBox jcbRebuid;
    private javax.swing.JComboBox jcbbScheme;
    private javax.swing.JTextField jtfTitle;
    private javax.swing.JTabbedPane jtpMain;
    private javax.swing.JTextField mc;
    private javax.swing.JPanel pnlCondition;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlMid;
    private javax.swing.JPanel pnlOrder;
    private javax.swing.JTextField txtQueryExp;
    // End of variables declaration//GEN-END:variables
}
