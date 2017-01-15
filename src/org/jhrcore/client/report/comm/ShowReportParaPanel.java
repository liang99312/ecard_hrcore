/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ShowReportParaPanel.java
 *
 * Created on 2011-12-20, 11:49:48
 */
package org.jhrcore.client.report.comm;

import com.fr.cell.editor.AbstractCellEditor;
import com.fr.report.parameter.Parameter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.entity.AutoNoRule;
import org.jhrcore.util.UtilTool;
import org.jhrcore.entity.report.ReportDef;
import org.jhrcore.entity.report.ReportNo;
import org.jhrcore.entity.report.ReportParaSet;
import org.jhrcore.mutil.ReportUtil;
import org.jhrcore.ui.action.CloseAction;

/**
 *
 * @author mxliteboss
 */
public class ShowReportParaPanel extends javax.swing.JPanel {

    private AutoNoRule cur_rule = null;
    private JTextField jtfNo = null;
    private ReportDef reportDef = null;
    private HashMap<String, AbstractCellEditor> nameHash = new HashMap<String, AbstractCellEditor>();

    /** Creates new form ShowReportParaPanel */
    public ShowReportParaPanel(AutoNoRule cur_rule, ReportDef rd) {
        this.cur_rule = cur_rule;
        this.reportDef = rd;
        initComponents();
        initOthers();
        setupEvents();
    }

    private void initOthers() {
        JLabel lbl = new JLabel("单号：");
        jtfNo = new JTextField();
        lbl.setPreferredSize(new Dimension(40, 22));
        jtfNo.setPreferredSize(new Dimension(160, 22));
        JPanel pnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnl.add(lbl);
        pnl.add(jtfNo);
        jPanel1.add(pnl, BorderLayout.NORTH);
        Parameter[] ps = ReportUtil.getReportParameters(reportDef);
        if (ps == null) {
            JOptionPane.showMessageDialog(null, "报表数据不存在");
            return;
        }
        JPanel pnlPara = ReportUtil.getPanelByParas(ps, nameHash);
        jPanel1.add(pnlPara);
        jtfNo.setText(viewNo());
    }

    private void setupEvents() {
        btnOk.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        CloseAction.doCloseAction(btnCancel);
    }

    private void save() {
        ReportNo no = (ReportNo) UtilTool.createUIDEntity(ReportNo.class);
//        no.setCuserNo(UserContext.person_code);
//        no.setCuserName(UserContext.person_name);
        no.setReportDef_key(reportDef.getReportDef_key());
        String s_where = "";
        Parameter[] ps = ReportUtil.getReportParameters(reportDef);
        Hashtable<String, ReportParaSet> paraKeys = ReportUtil.initReportNoParameter(reportDef, ps);
        for (String para : nameHash.keySet()) {
            ReportParaSet pps = paraKeys.get(para);
            String field = "";
            if (pps == null) {
                JOptionPane.showMessageDialog(null, "报表解析错误");
                return;
            } else {
                field = pps.getParafield();
            }
            AbstractCellEditor editor = nameHash.get(para);
            try {
                Object obj = editor.getCellEditorValue();
                String value = obj == null ? "" : obj.toString().replace("'", "''");
                s_where += " and rn." + pps.getParafield() + "='" + value + "'";
                PublicUtil.setValueBy2(no, field, value);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        String sql = "select 1 from ReportNo rn where rn.reportDef_key='" + reportDef.getReportDef_key() + "'" + s_where;
        if (CommUtil.exists(sql)) {
            JOptionPane.showMessageDialog(null, "当前参数数据已存在，不需要生成");
            return;
        }
//        ValidateSQLResult result = BBImpl.createReportNo(no);
//        if (result.getResult() == 0) {
//            if (jcbClose.isSelected()) {
//                jtfNo.setText(viewNo());
//            } else {
//                ModelFrame.close((ModelFrame) JOptionPane.getFrameForComponent(btnCancel));
//            }
//        } else {
//            FormulaTextDialog.showErrorMsg(result.getMsg());
//        }
    }

    private String viewNo() {
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("@报表编码", "'" + (reportDef.getReport_code()==null?"":reportDef.getReport_code()) + "'");
        String tmp = CommUtil.fetchNewNoBy(cur_rule.getAutoNoRule_key(), 0, ht);
        return tmp;
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
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jcbClose = new javax.swing.JCheckBox();

        jPanel1.setLayout(new java.awt.BorderLayout());

        btnOk.setText("确定");

        btnCancel.setText("取消");

        jcbClose.setText("保存时不关闭窗口");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jcbClose)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(btnOk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancel)
                .addGap(173, 173, 173))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnOk)
                        .addComponent(btnCancel))
                    .addComponent(jcbClose))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JCheckBox jcbClose;
    // End of variables declaration//GEN-END:variables
}
