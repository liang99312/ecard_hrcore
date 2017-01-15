/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * createReportDataPanel.java
 *
 * Created on 2011-12-27, 16:21:20
 */
package org.jhrcore.client.report.comm;

import com.fr.cell.editor.AbstractCellEditor;
import com.fr.report.parameter.Parameter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.DateUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.client.report.ReportPanel;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.util.UtilTool;
import org.jhrcore.entity.report.ReportDef;
import org.jhrcore.entity.report.ReportLog;
import org.jhrcore.entity.report.ReportNo;
import org.jhrcore.entity.report.ReportParaSet;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.impl.ReportImpl;
import org.jhrcore.mutil.ReportUtil;
import org.jhrcore.ui.HrTextPane;
import org.jhrcore.ui.ModalDialog;
import org.jhrcore.ui.ModelFrame;
import org.jhrcore.util.MsgUtil;

/**
 *
 * @author mxliteboss
 */
public class CreateReportDataPanel extends javax.swing.JPanel {

    private HrTextPane textPane = null;
    private List<ReportNo> rns = null;
    private List reports = null;

    /** Creates new form createReportDataPanel */
    public CreateReportDataPanel(List<ReportNo> rns, List reports) {
        this.reports = reports;
        this.rns = rns;
        initComponents();
        initOthers();
        setupEvents();
    }

    private void initOthers() {
        textPane = new HrTextPane();
        jPanel1.add(textPane);
    }

    private void setupEvents() {
        btnClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ModelFrame.close();
            }
        });
        btnCreateData.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Runnable run = new Runnable() {

                    @Override
                    public void run() {
                        createData(rns);
                    }
                };
                new Thread(run).run();
            }
        });
    }

    public void createData(final List<ReportNo> rns) {
        textPane.setText("开始生成数据，开始时间：" + DateUtil.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        ReportLog rlog = (ReportLog) UtilTool.createUIDEntity(ReportLog.class);
        rlog.setA0190(UserContext.person_code);
        rlog.setA0101(UserContext.person_name);
        rlog.setCtype("生成数据");
        List<String> noKeys = new ArrayList<String>();
        for (ReportNo rn : rns) {
            String reportName = (String) CommUtil.fetchEntityBy("select report_name from ReportDef rd where reportDef_key='" + rn.getReportDef_key() + "'");
            textPane.setText(textPane.getText() + "\n正在生成报表：" + reportName + " 单号：" + rn.getCno());
            String rn_obj = (String) CommUtil.fetchEntityBy("select rn.noState from ReportNo rn where rn.reportNo_key='" + rn.getReportNo_key() + "'");
            if (rn_obj == null) {
                textPane.setText(textPane.getText() + "\n单号数据不合法，数据生成失败");
                continue;
            } else if (rn_obj.equals("审核通过") || rn_obj.equals("已存档")) {
                textPane.setText(textPane.getText() + "\n已审核通过或存档数据不允许再次生成，数据生成失败");
                continue;
            }
            rn.setCnum(rn.getCnum() + 1);
            ReportDef rd = ReportUtil.getReportDef(rn.getReportDef_key(), reports);
            rd.setReportDef_key(rn.getReportDef_key());
            Parameter[] meters = ReportUtil.getReportParameters(rd);
            List<Parameter> meterList = new ArrayList<Parameter>();
            meterList.addAll(Arrays.asList(meters));
            HashMap hm = new HashMap();
            List list = CommUtil.fetchEntities("from ReportParaSet rps where rps.reportDef_key='" + rn.getReportDef_key() + "'");
            Hashtable<String, ReportParaSet> reportParaKeys = new Hashtable<String, ReportParaSet>();
            for (Object obj : list) {
                ReportParaSet paraSet = (ReportParaSet) obj;
                String pName = paraSet.getParaname();
                String pField = paraSet.getParafield();
                if (pField == null || pField.trim().equals("")) {
                    continue;
                }
                pField = pField.replace("@", "").replace("部门", "dept_code2").replace("年/月/季度", "ym");
                Object pValue = PublicUtil.getProperty(rn, pField);
                if (pValue != null) {
                    hm.put(pName, pValue);
                    for (Parameter meter : meters) {
                        if (meter.getName().equals(pName)) {
                            meterList.remove(meter);
                            break;
                        }
                    }
                } else {
                    reportParaKeys.put(pName, paraSet);
                }
            }
            meters = new Parameter[meterList.size()];
            for (int i = 0; i < meterList.size(); i++) {
                meters[i] = meterList.get(i);
            }
            if (meters.length > 0) {
                HashMap<String, AbstractCellEditor> nameHash = new HashMap<String, AbstractCellEditor>();
                JPanel pnlPara = ReportUtil.getPanelByParas(meters, nameHash);
                if (!ModalDialog.doModal(JOptionPane.getFrameForComponent(btnCreateData), pnlPara, "请输入参数:")) {
                    return;
                }
                for (Parameter meter : meterList) {
                    String pName = meter.getName();
                    AbstractCellEditor editor = nameHash.get(pName);
                    try {
                        Object value = editor.getCellEditorValue();
                        hm.put(pName, value);
                        ReportParaSet rps = reportParaKeys.get(pName);
                        if (rps != null) {
                            String pField = rps.getParafield();
                            if (pField == null || pField.trim().equals("")) {
                                continue;
                            }
                            pField = pField.replace("@", "");
                            PublicUtil.setValueBy2(rn, pField, value);
                        }
                    } catch (Exception ex) {
                    }
                }
            }
            boolean success = ReportPanel.excute_report((JFrame) JOptionPane.getFrameForComponent(btnCreateData), rd, hm, true, false, false);
            if (success) {
                noKeys.add(rn.getReportNo_key());
                rn.setNoState("未提交");
                textPane.setText(textPane.getText() + "\n数据生成成功");
                rlog.setCtext((rlog.getCtext() == null ? "" : rlog.getCtext()) + "报表:" + reportName + ",单号:" + rn.getCno() + "," + rlog.getCtype() + "成功;");
            } else {
                textPane.setText(textPane.getText() + "\n报表执行错误，数据生成失败");
                rlog.setCtext(rlog.getCtext() == null ? "" : rlog.getCtext() + "报表:" + reportName + ",单号:" + rn.getCno() + ",报表执行错误，" + rlog.getCtype() + "失败;");
            }
        }
        if (noKeys.isEmpty()) {
            textPane.setText(textPane.getText() + "\n数据生成结束,结束时间：" + DateUtil.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
            return;
        }
        ValidateSQLResult result = ReportImpl.createReportData(noKeys, rlog);
        if (result.getResult() == 0) {
            textPane.setText(textPane.getText() + "\n数据生成结束,结束时间：" + DateUtil.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } else {
            MsgUtil.showHRSaveErrorMsg(result);
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        btnClose = new javax.swing.JButton();
        btnCreateData = new javax.swing.JButton();

        jPanel1.setLayout(new java.awt.BorderLayout());

        btnClose.setText("关闭");

        btnCreateData.setText("生成数据");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(242, Short.MAX_VALUE)
                .addComponent(btnCreateData)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClose)
                .addGap(65, 65, 65))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnCreateData))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnCreateData;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
