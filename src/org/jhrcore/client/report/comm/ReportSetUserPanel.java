/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ReportSetUserPanel.java
 *
 * Created on 2012-2-7, 10:27:38
 */
package org.jhrcore.client.report.comm;

import com.foundercy.pf.control.table.FTable;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.A01PassWord;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.report.ReportDept;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.rebuild.EntityBuilder;
import org.jhrcore.ui.StepWorkUserSelectPanel;
import org.jhrcore.ui.action.CloseAction;
import org.jhrcore.util.ComponentUtil;
import org.jhrcore.util.MsgUtil;

/**
 *
 * @author lenovo
 */
public class ReportSetUserPanel extends javax.swing.JPanel {

    private FTable ftable_dept;
    private StepWorkUserSelectPanel user_select_pnl;
    private List objs;

    public ReportSetUserPanel(List objs) {
        this.objs = objs;
        initComponents();
        initOthers();
        setupEvents();
    }

    private void initOthers() {
        ComponentUtil.setSysFuntion(this, "ReportNo.btnSetUser");
        ftable_dept = new FTable(ReportDept.class, false, false, false, "ReportSetUserPanel");
        List<TempFieldInfo> all_infos = new ArrayList<TempFieldInfo>();
        List<TempFieldInfo> default_infos = new ArrayList<TempFieldInfo>();
        List<TempFieldInfo> dept_infos = EntityBuilder.getCommFieldInfoListOf(DeptCode.class, EntityBuilder.COMM_FIELD_VISIBLE);
        for (TempFieldInfo tfi : dept_infos) {
            if ("content".equals(tfi.getField_name()) || "dept_code".equals(tfi.getField_name())) {
                default_infos.add(tfi);
            }
            tfi.setField_name("#" + tfi.getEntity_name() + "." + tfi.getField_name());
            all_infos.add(tfi);
        }
        List<TempFieldInfo> rd_infos = EntityBuilder.getCommFieldInfoListOf(ReportDept.class, EntityBuilder.COMM_FIELD_VISIBLE);
        for (TempFieldInfo tfi : rd_infos) {
            if ("needCheck".equals(tfi.getField_name())) {
                continue;
            }
            all_infos.add(tfi);
            default_infos.add(tfi);
        }
        ftable_dept.getOther_entitys().put("DeptCode", "DeptCode DeptCode,ReportDept ReportDept");
        ftable_dept.getOther_entity_keys().put("DeptCode", "DeptCode.deptCode_key=ReportDept.deptCode_key and ReportDept.reportDept_key ");
        ftable_dept.setAll_fields(all_infos, default_infos, new ArrayList(), "ReportSetUserPanel");
        ftable_dept.setObjects(objs);
        pnlLeft.add(ftable_dept, BorderLayout.CENTER);
        user_select_pnl = new StepWorkUserSelectPanel();
        user_select_pnl.buildTree("员工", new HashSet());
        pnlRight.add(user_select_pnl, BorderLayout.CENTER);

    }

    private void setupEvents() {
        btnSetUser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<A01PassWord> objs = user_select_pnl.getFTable().getSelectObjects();
                if (!objs.isEmpty()) {
                    List<String> sqls = new ArrayList();
                    A01PassWord apw = objs.get(0);
                    for (Object o : ftable_dept.getSelectObjects()) {
                        if (o instanceof ReportDept) {
                            ReportDept rdept = (ReportDept) o;
                            rdept.setTuserNo(apw.getA01().getA0190());
                            rdept.setTuserName(apw.getA01().getA0101());
                            String sql = "update ReportDept set tuserNo='" + rdept.getTuserNo() + "',tuserName='" + rdept.getTuserName() + "' where reportDept_key='" + rdept.getReportDept_key() + "'";
                            sqls.add(sql);
                        }
                    }
                    ValidateSQLResult vsr = CommUtil.excuteSQLs(sqls);
                    if (vsr.getResult() == 0) {
                        ftable_dept.updateUI();
                        JOptionPane.showMessageDialog(null, "设置提交用户成功");
                    } else {
                        MsgUtil.showHRSaveErrorMsg(vsr);
                    }
                }
            }
        });
        btnNoUser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> sqls = new ArrayList();
                for (Object o : ftable_dept.getSelectObjects()) {
                    if (o instanceof ReportDept) {
                        ReportDept rdept = (ReportDept) o;
                        rdept.setTuserNo(null);
                        rdept.setTuserName(null);
                        String sql = "update ReportDept set tuserNo=null,tuserName=null where reportDept_key='" + rdept.getReportDept_key() + "'";
                        sqls.add(sql);
                    }
                }
                ValidateSQLResult vsr = CommUtil.excuteSQLs(sqls);
                if (vsr.getResult() == 0) {
                    ftable_dept.updateUI();
                    JOptionPane.showMessageDialog(null, "取消提交用户成功");
                } else {
                    MsgUtil.showHRSaveErrorMsg(vsr);
                }
            }
        });
        btnChecker.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<A01PassWord> objs = user_select_pnl.getFTable().getSelectObjects();
                if (!objs.isEmpty()) {
                    List<String> sqls = new ArrayList();
                    A01PassWord apw = objs.get(0);
                    for (Object o : ftable_dept.getSelectObjects()) {
                        if (o instanceof ReportDept) {
                            ReportDept rdept = (ReportDept) o;
                            rdept.setSuserNo(apw.getA01().getA0190());
                            rdept.setSuserName(apw.getA01().getA0101());
                            String sql = "update ReportDept set suserNo='" + rdept.getSuserNo() + "',suserName='" + rdept.getSuserName() + "' where reportDept_key='" + rdept.getReportDept_key() + "'";
                            sqls.add(sql);
                        }
                    }
                    ValidateSQLResult vsr = CommUtil.excuteSQLs(sqls);
                    if (vsr.getResult() == 0) {
                        ftable_dept.updateUI();
                        JOptionPane.showMessageDialog(null, "设置审核用户成功");
                    } else {
                        MsgUtil.showHRSaveErrorMsg(vsr);
                    }
                }
            }
        });
        btnNoChecker.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> sqls = new ArrayList();
                for (Object o : ftable_dept.getSelectObjects()) {
                    if (o instanceof ReportDept) {
                        ReportDept rdept = (ReportDept) o;
                        rdept.setSuserNo(null);
                        rdept.setSuserName(null);
                        String sql = "update ReportDept set suserNo=null,suserName=null where reportDept_key='" + rdept.getReportDept_key() + "'";
                        sqls.add(sql);
                    }
                }
                ValidateSQLResult vsr = CommUtil.excuteSQLs(sqls);
                if (vsr.getResult() == 0) {
                    ftable_dept.updateUI();
                    JOptionPane.showMessageDialog(null, "取消审核用户成功");
                } else {
                    MsgUtil.showHRSaveErrorMsg(vsr);
                }
            }
        });
        CloseAction.doCloseAction(btnClose);
        ComponentUtil.refreshJSplitPane(jspMain, "ReportSetUserPanel.jspMain");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jspMain = new javax.swing.JSplitPane();
        pnlLeft = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        pnlRight = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnSetUser = new javax.swing.JButton();
        btnNoUser = new javax.swing.JButton();
        btnChecker = new javax.swing.JButton();
        btnNoChecker = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        jspMain.setDividerLocation(190);
        jspMain.setDividerSize(2);
        jspMain.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        pnlLeft.setLayout(new java.awt.BorderLayout());
        jspMain.setLeftComponent(pnlLeft);

        pnlRight.setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnSetUser.setText("设置提交用户");
        btnSetUser.setFocusable(false);
        btnSetUser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSetUser.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSetUser);

        btnNoUser.setText("取消提交用户");
        btnNoUser.setFocusable(false);
        btnNoUser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNoUser.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnNoUser);

        btnChecker.setText("设置审核用户");
        btnChecker.setFocusable(false);
        btnChecker.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnChecker.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnChecker);

        btnNoChecker.setText("取消审核用户");
        btnNoChecker.setFocusable(false);
        btnNoChecker.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNoChecker.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnNoChecker);

        btnClose.setText("关闭");
        btnClose.setFocusable(false);
        btnClose.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClose.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnClose);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
            .addComponent(pnlRight, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlRight, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
        );

        jspMain.setRightComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMain, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMain, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChecker;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnNoChecker;
    private javax.swing.JButton btnNoUser;
    private javax.swing.JButton btnSetUser;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JSplitPane jspMain;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlRight;
    // End of variables declaration//GEN-END:variables
}
