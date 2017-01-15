/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ReportParaSetPanel.java
 *
 * Created on 2011-12-28, 20:48:49
 */
package org.jhrcore.client.report.comm;

import com.foundercy.pf.control.table.FTable;
import com.fr.report.parameter.Parameter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jhrcore.client.CommUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.client.report.ReportModel;
import org.jhrcore.entity.report.ReportDef;
import org.jhrcore.entity.report.ReportParaSet;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.mutil.ReportUtil;
import org.jhrcore.ui.renderer.HRRendererView;
import org.jhrcore.util.MsgUtil;

/**
 *
 * @author mxliteboss
 */
public class ReportParaSetPanel extends javax.swing.JPanel {

    private List<ReportDef> rds = null;
    private List modules = null;
    private JTree reportTree = null;
    private FTable ftable = null;

    /** Creates new form ReportParaSetPanel */
    public ReportParaSetPanel(List<ReportDef> rds, List modules) {
        this.rds = rds;
        this.modules = modules;
        initComponents();
        initOthers();
        setupEvents();
    }

    private void initOthers() {
        ReportModel reportModel = new ReportModel(modules, rds, UserContext.role_id);
        reportTree = new JTree(reportModel);
        HRRendererView.getReportMap(reportTree).initTree(reportTree);
        reportTree.setRootVisible(false);
        reportTree.setShowsRootHandles(true);
        pnlReport.add(new JScrollPane(reportTree));
        ftable = new FTable(ReportParaSet.class, false, false, false, "ReportParaSetPanel");
        ftable.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlMain.add(ftable);
    }

    private void setupEvents() {
        reportTree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                if (e.getPath() == null || e.getPath().getLastPathComponent() == null) {
                    return;
                }
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
                fetchPara(node.getUserObject());
            }
        });
        btnSetPara.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setPara((ReportParaSet) ftable.getCurrentRow());
            }
        });
    }

    private void fetchPara(Object obj) {
        if (obj == null || !(obj instanceof ReportDef)) {
            ftable.deleteAllRows();
            return;
        }
        ReportDef rd = (ReportDef) obj;
        if (rd == null) {
            return;
        }
        Parameter[] meters = ReportUtil.getReportParameters(rd);
        if(meters == null){
            return;
        }
        List list = CommUtil.fetchEntities("from ReportParaSet rps where rps.reportDef_key='" + rd.getReportDef_key() + "'");
        List data = new ArrayList();
        for (Parameter meter : meters) {
            String name = meter.getName();
            ReportParaSet para = null;
            for (Object r_obj : list) {
                ReportParaSet rs = (ReportParaSet) r_obj;
                if (name.equals(rs.getParaname())) {
                    para = rs;
                    break;
                }
            }
            if (para == null) {
                para = new ReportParaSet();
                para.setReportParaSet_key(rd.getReportDef_key() + "_" + name);
                para.setReportDef_key(rd.getReportDef_key());
                para.setParaname(name);
                CommUtil.saveOrUpdate(para);
            }
            data.add(para);
        }
        ftable.setObjects(data);
    }

    private void setPara(ReportParaSet rps) {
        if (rps == null || rps.getReportParaSet_key() == null) {
            return;
        }
        String oldField = rps.getParafield();
        rps.setParafield(jcbPara.getSelectedItem().toString());
        ValidateSQLResult result = CommUtil.updateEntity(rps);
        if (result.getResult() == 0) {
            JOptionPane.showMessageDialog(null, "设置成功");
        } else {
            rps.setParafield(oldField);
            MsgUtil.showHRSaveErrorMsg(result);
        }
        pnlMain.updateUI();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        pnlReport = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jcbPara = new javax.swing.JComboBox();
        btnSetPara = new javax.swing.JButton();
        pnlMain = new javax.swing.JPanel();

        jSplitPane1.setDividerLocation(150);

        pnlReport.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setLeftComponent(pnlReport);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jLabel1.setText("参数：");
        jToolBar1.add(jLabel1);

        jcbPara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "@部门", "@年/月/季度", "@para1", "@para2", "@para3", "@para4", "@para5" }));
        jcbPara.setMaximumSize(new java.awt.Dimension(100, 32767));
        jToolBar1.add(jcbPara);

        btnSetPara.setText("设置");
        btnSetPara.setFocusable(false);
        btnSetPara.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSetPara.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSetPara);

        pnlMain.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSetPara;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JComboBox jcbPara;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlReport;
    // End of variables declaration//GEN-END:variables
}
