/*
 * CopyCurRightDialog.java
 *
 * Created on 2008��11��22��, ����4:15
 */
package org.jhrcore.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import org.jhrcore.util.SysUtil;
import org.jhrcore.entity.right.Role;
import org.jhrcore.ui.renderer.HRRendererView;

/**
 *
 * @author  Owner
 */
public class CopyCurRightDialog extends javax.swing.JDialog {

    private List<Role> roles = new ArrayList<Role>();
    private boolean click_ok = false;
    private List<Role> select_roles = new ArrayList<Role>();
    private CheckTreeNode rootNode = new CheckTreeNode("�����Ӽ�");

    public CopyCurRightDialog(List<Role> roles) {
        this.setTitle("ͬ��Ӧ���Ӽ���ɫ��");
        this.roles.addAll(roles);
        this.setModal(true);
        initComponents();
        initOthers();
        setupEvents();
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
        jPanel1 = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlMain.setLayout(new java.awt.BorderLayout());

        btnOk.setText("ȷ��");

        btnCancel.setText("ȡ��");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(225, Short.MAX_VALUE)
                .addComponent(btnOk)
                .addGap(18, 18, 18)
                .addComponent(btnCancel)
                .addGap(32, 32, 32))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnOk))
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables

    private void initOthers() {
        SysUtil.sortListByStr(roles, "role_code");
        rootNode.removeAllChildren();
        CheckTreeNode tmp = rootNode;
        for (Object obj : roles) {
            Role role = (Role) obj;
            while (tmp != rootNode && !((Role) tmp.getUserObject()).getRole_code().equals(
                    role.getParent_code())) {
                tmp = (CheckTreeNode) tmp.getParent();
            }
            CheckTreeNode cur = new CheckTreeNode(role);
            tmp.add(cur);
            tmp = cur;
        }
        JTree tree = new JTree(rootNode);
        HRRendererView.getRoleMap().initTree(tree, TreeSelectMod.nodeCheckChildFollowMod);
        pnlMain.add(new JScrollPane(tree), BorderLayout.CENTER);
    }

    private void setupEvents() {
        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                click_ok = false;
                dispose();
            }
        });
        btnOk.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                select_roles.clear();
                Enumeration enumt = rootNode.breadthFirstEnumeration();
                while (enumt.hasMoreElements()) {
                    CheckTreeNode node = (CheckTreeNode) enumt.nextElement();
                    if (node.isSelected() && node.getUserObject() instanceof Role) {
                        select_roles.add((Role) node.getUserObject());
                    }
                }
                click_ok = true;
                dispose();
            }
        });

    }

    public boolean isClick_ok() {
        return click_ok;
    }

    public List<Role> getSelect_roles() {
        return select_roles;
    }
}