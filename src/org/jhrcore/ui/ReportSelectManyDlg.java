/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ReportSelectManyDlg.java
 *
 * Created on 2010-8-30, 21:59:07
 */
package org.jhrcore.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import org.jhrcore.util.SysUtil;
import org.jhrcore.entity.report.ReportDef;
import org.jhrcore.entity.right.RoleRightTemp;
import org.jhrcore.ui.renderer.HRRendererView;

/**
 *
 * @author hflj
 */
public class ReportSelectManyDlg extends javax.swing.JDialog {

    private CheckTreeNode rootNode = new CheckTreeNode("所有报表");
    private List reportDefs;
    private boolean click_ok = false;
    private List<ReportDef> selectReports = new ArrayList<ReportDef>();
// 报表类型节点
    private Hashtable<String, CheckTreeNode> class_table = new Hashtable<String, CheckTreeNode>();
    private Hashtable<String, List<ReportDef>> class_keys = new Hashtable<String, List<ReportDef>>();
    private JTree reportTree;

    public boolean isClick_ok() {
        return click_ok;
    }

    public List<ReportDef> getSelectReports() {
        return selectReports;
    }

    /** Creates new form ReportSelectManyDlg */
    public ReportSelectManyDlg(java.awt.Frame parent, List defs) {
        super(parent);
        initComponents();
        this.reportDefs = defs;
        initOthers();
        setupEvents();
    }

    /** Creates new form ReportSelectManyDlg */
    public ReportSelectManyDlg(java.awt.Frame parent, List defs, List selectedDefs) {
        super(parent);
        initComponents();
        this.reportDefs = defs;
        this.selectReports.addAll(selectedDefs);
        initOthers();
        setupEvents();
    }

    private HashSet getKeys(List list) {
        HashSet report_keys = new HashSet();
        if (list != null || list.size() != 0) {
            for (Object obj : list) {
                ReportDef reportDef = (ReportDef) obj;
                report_keys.add(reportDef.getReportDef_key());
            }
        }
        return report_keys;
    }

    private void initOthers() {
        HashSet report_keys = getKeys(selectReports);
        rootNode.removeAllChildren();
        for (Object obj : reportDefs) {
            ReportDef reportDef = (ReportDef) obj;
            String key = reportDef.getReport_class() == null ? "" : reportDef.getReport_class().trim();
            List<ReportDef> list = class_keys.get(key);
            if (list == null) {
                list = new ArrayList<ReportDef>();
            }
            if (!list.contains(reportDef)) {
                list.add(reportDef);
            }
            class_keys.put(key, list);
            SysUtil.sortListByInteger(list, "order_no");
            CheckTreeNode parent_node = class_table.get(key);
            if (parent_node == null) {
                RoleRightTemp temp_rrt = new RoleRightTemp();
                temp_rrt.setTemp_name(reportDef.getReport_class());
                parent_node = new CheckTreeNode(temp_rrt);
                rootNode.add(parent_node);
                class_table.put(key, parent_node);
            }
            int ind = parent_node.getChildCount();
            int insert_ind = list.indexOf(reportDef);
            CheckTreeNode node = new CheckTreeNode(reportDef);
            if (selectReports.contains(reportDef)) {
                node.setSelected(true);
            }
            if (insert_ind < ind) {
                parent_node.insert(node, list.indexOf(reportDef));
            } else {
                parent_node.add(node);
            }
            if (report_keys.contains(reportDef.getReportDef_key())) {
                node.setSelected(true);
            }
        }
        reportTree = new JTree(rootNode);
        HRRendererView.getCommMap().initTree(reportTree, TreeSelectMod.nodeCheckMod);
        reportTree.setRootVisible(false);
        reportTree.setShowsRootHandles(true);
        pnlMain.add(new JScrollPane(reportTree));
    }

    private void setupEvents() {
        btnOk.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                click_ok = true;
                Enumeration enumt = rootNode.breadthFirstEnumeration();
                selectReports.clear();
                while (enumt.hasMoreElements()) {
                    CheckTreeNode node = (CheckTreeNode) enumt.nextElement();
                    if (node.isSelected()) {
                        Object obj = node.getUserObject();
                        if (obj instanceof ReportDef) {
                            selectReports.add((ReportDef) obj);
                        }
                    }
                }
                dispose();
            }
        });
        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);

        pnlMain.setLayout(new java.awt.BorderLayout());

        btnOk.setText("确定");

        btnCancel.setText("取消");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(271, Short.MAX_VALUE)
                .addComponent(btnOk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancel)
                .addGap(38, 38, 38))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnOk))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 433, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables
}
