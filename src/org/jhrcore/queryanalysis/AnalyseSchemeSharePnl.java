/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AnalyseSchemeSharePnl.java
 *
 * Created on 2012-12-18, 11:44:18
 */
package org.jhrcore.queryanalysis;

import com.foundercy.pf.control.table.FTable;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.DbUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.entity.A01PassWord;
import org.jhrcore.util.UtilTool;
import org.jhrcore.entity.query.CommAnalyseScheme;
import org.jhrcore.entity.right.Role;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.ui.CheckTreeNode;
import org.jhrcore.ui.ModalDialog;
import org.jhrcore.ui.ModelFrame;
import org.jhrcore.ui.TreeSelectMod;
import org.jhrcore.ui.renderer.HRRendererView;
import org.jhrcore.util.ComponentUtil;
import org.jhrcore.util.MsgUtil;

/**
 *
 * @author mxliteboss
 */
public class AnalyseSchemeSharePnl extends javax.swing.JPanel {

    private List scheme_list;
    private List listRole = new ArrayList();
    private CommAnalyseScheme scheme;
    private JTree schemeTree;
    private AnalyseTreeModel model;
    private JRadioButton jrbCur = new JRadioButton("当前选择方案");
    private JRadioButton jrbAll = new JRadioButton("所有方案");
    private FTable ftable_a01;
    private FTable ftable_tmp;
    private JTree role_tree;
    private CheckTreeNode rootNode = new CheckTreeNode("全部角色");
    private String share_type = "按角色";
    private List<String> shareKeys = new ArrayList();
    private String module_code = "AnalyseSchemeSharePnl";

    /** Creates new form AnalyseSchemeSharePnl */
    public AnalyseSchemeSharePnl(List scheme_list, CommAnalyseScheme scheme) {
        this.scheme_list = scheme_list;
        this.scheme = scheme;
        initComponents();
        initOthers();
        setupEvents();
    }

    private void initOthers() {
        model = new AnalyseTreeModel(new ArrayList());
        schemeTree = new JTree(model);
        HRRendererView.getQuerySchemeMap(schemeTree).initTree(schemeTree);
        pnlLeft.add(new JScrollPane(schemeTree));
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.add(jrbCur);
        toolbar.add(jrbAll);
        pnlLeft.add(toolbar, BorderLayout.NORTH);
        ButtonGroup bg = new ButtonGroup();
        bg.add(jrbCur);
        bg.add(jrbAll);

        List<String> fields = new ArrayList<String>();
        fields.add("a01.deptCode.dept_code");
        fields.add("a01.deptCode.content");
        fields.add("a01.a0190");
        fields.add("a01.a0101");
        ftable_a01 = new FTable(A01PassWord.class, fields, false, false, false, module_code);
        ftable_tmp = new FTable(A01PassWord.class, fields, false, false, false, module_code);
        if (!UserContext.isSA) {
            listRole.addAll(CommUtil.fetchEntities("from Role r where " + UserContext.role_right_str + " and r.role_key<>'&&&' order by r.role_code"));
        } else {
            listRole.addAll(CommUtil.fetchEntities("from Role r where r.role_key<>'&&&' order by r.role_code"));
        }
        role_tree = new JTree(rootNode);
        DefaultMutableTreeNode tmp = rootNode;
        for (Object obj : listRole) {
            Role role = (Role) obj;
            while (tmp != rootNode && !((Role) tmp.getUserObject()).getRole_code().equals(
                    role.getParent_code())) {
                tmp = (DefaultMutableTreeNode) tmp.getParent();
            }
            CheckTreeNode cur = new CheckTreeNode(role);
            tmp.add(cur);
            tmp = cur;
        }
    }

    private void setupEvents() {
        ActionListener alTree = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTreeUI();
            }
        };
        jrbCur.addActionListener(alTree);
        jrbAll.addActionListener(alTree);
        ActionListener alUser = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                refreshUserUI();
            }
        };
        jrbRole.addActionListener(alUser);
        jrbPerson.addActionListener(alUser);
        btnSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                saveRoleShare();
            }
        });
        btnClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ModelFrame.close();
            }
        });
        btnAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addShareUser();
            }
        });
        btnDel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                delShareUser();
            }
        });
        schemeTree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                if (e.getPath() == null || e.getPath().getLastPathComponent() == null) {
                    return;
                }
                shareKeys.clear();
                share_type = "按角色";
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
                if (node.getUserObject() instanceof CommAnalyseScheme) {
                    scheme = (CommAnalyseScheme) node.getUserObject();
                    List list = CommUtil.selectSQL("select userKey,shareType from SchemeShareDetail where scheme_key='" + scheme.getCommAnalyseScheme_key() + "'");
                    for (Object obj : list) {
                        Object[] objs = (Object[]) obj;
                        shareKeys.add(objs[0].toString());
                        share_type = "按员工".equals(SysUtil.objToStr(objs[1])) ? "按员工" : "按角色";
                    }
                    if ("按员工".equals(share_type)) {
                        jrbPerson.setSelected(true);
                    } else {
                        jrbRole.setSelected(true);
                    }
                }
                refreshUserUI();
            }
        });
        role_tree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                if (e.getPath() == null || e.getPath().getLastPathComponent() == null) {
                    return;
                }
                CheckTreeNode cnode = (CheckTreeNode) e.getPath().getLastPathComponent();
                if (cnode.getUserObject() instanceof Role) {
                    String roleKey = ((Role) cnode.getUserObject()).getRole_key();
                    String hql = "select apw.a01password_key from A01PassWord apw,A01 a01,DeptCode d  where apw.a01_key=a01.a01_key and a01.deptCode_key=d.deptCode_key "
                            + "and exists(select 1 from RoleA01 ra where ra.role_key='" + roleKey + "' and ra.a01password_key=apw.a01password_key) ";
                    hql += " order by d.dept_code,A01.a0190";
                    List list = CommUtil.selectSQL(hql);
                    PublicUtil.getProps_value().setProperty(A01PassWord.class.getName(), "from A01PassWord apw join fetch apw.a01 join fetch apw.a01.deptCode where apw.a01PassWord_key in ");
                    ftable_tmp.setObjects(list);
                }
            }
        });
        jrbCur.setSelected(true);
        refreshTreeUI();
        if ("按员工".equals(share_type)) {
            jrbPerson.setSelected(true);
        } else {
            jrbRole.setSelected(true);
        }
        refreshUserUI();
    }

    private void refreshTreeUI() {
        List list = new ArrayList();
        if (jrbCur.isSelected()) {
            if (scheme == null) {
                return;
            }
            if (scheme.getUser_code().equals(UserContext.person_code) && "手动指定".equals(scheme.getShare_type())) {
                list.add(scheme);
            }
        } else {
            for (Object obj : scheme_list) {
                CommAnalyseScheme cs = (CommAnalyseScheme) obj;
                if (cs.getUser_code().equals(UserContext.person_code) && !"所有人".equals(cs.getShare_type())) {
                    list.add(cs);
                }
            }
        }
        model.buildTree(list);
        if (list.isEmpty()) {
            scheme = null;
        }
        DefaultMutableTreeNode initNode = null;
        Enumeration enumt = ((DefaultMutableTreeNode) model.getRoot()).breadthFirstEnumeration();
        while (enumt.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumt.nextElement();
            if (node.getUserObject() == scheme) {
                initNode = node;
                break;
            }
        }
        ComponentUtil.initTreeSelection(schemeTree, initNode);
    }

    private void refreshUserUI() {
        pnlRight.removeAll();
        boolean isRole = jrbRole.isSelected();
        btnAdd.setEnabled(!isRole);
        btnDel.setEnabled(!isRole);
        btnSave.setEnabled(isRole);
        if (isRole) {
            Enumeration enumt = rootNode.breadthFirstEnumeration();
            while (enumt.hasMoreElements()) {
                CheckTreeNode cnode = (CheckTreeNode) enumt.nextElement();
                cnode.setSelected(false);
                if (cnode.getUserObject() instanceof Role) {
                    Role role = (Role) cnode.getUserObject();
                    if (shareKeys.contains(role.getRole_key())) {
                        cnode.setSelected(true);
                    }
                }
            }
            HRRendererView.getRoleMap().initTree(role_tree, TreeSelectMod.nodeCheckChildFollowMod);
            pnlRight.add(getRoleSharePanel());
        } else {
            PublicUtil.getProps_value().setProperty(A01PassWord.class.getName(), "from A01PassWord apw join fetch apw.a01 join fetch apw.a01.deptCode where apw.a01PassWord_key in ");
            if (share_type.equals("按员工")) {
                ftable_a01.setObjects(shareKeys);
            } else {
                ftable_a01.setObjects(new ArrayList());
            }
            ftable_a01.setBorder(javax.swing.BorderFactory.createEtchedBorder());
            pnlRight.add(ftable_a01);
        }
        pnlRight.updateUI();
    }

    private void saveRoleShare() {
        if (scheme == null) {
            return;
        }
        List list = ComponentUtil.getCheckedObjs(role_tree);
        String exStr = "delete from SchemeShareDetail where scheme_key='" + scheme.getCommAnalyseScheme_key() + "';";
        List<String> keys = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof Role) {
                keys.add(((Role) obj).getRole_key());
            }
        }
        exStr += tranUserSQL(keys, "按角色", scheme.getCommAnalyseScheme_key());
        ValidateSQLResult result = CommUtil.excuteSQLs(exStr.toString(), ";");
        if (result.getResult() == 0) {
            shareKeys.clear();
            shareKeys.addAll(keys);
            share_type = "按角色";
            JOptionPane.showMessageDialog(null, "保存成功");
        } else {
            MsgUtil.showHRSaveErrorMsg(result);
        }
    }

    private JPanel getRoleSharePanel() {
        JPanel pnlMain = new JPanel(new BorderLayout());
        JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        jsp.setDividerSize(2);
        jsp.setLeftComponent(new JScrollPane(role_tree));
        jsp.setRightComponent(ftable_tmp);
        pnlMain.add(jsp);
        ComponentUtil.refreshJSplitPane(jsp, module_code + ".jsp", 180);
        ComponentUtil.initTreeSelection(role_tree);
        return pnlMain;
    }

    private void addShareUser() {
        if (scheme == null) {
            return;
        }
        JPanel pnl = getRoleSharePanel();
        HRRendererView.getRoleMap().initTree(role_tree, TreeSelectMod.nodeSelectMod);
        if (ModalDialog.doModal(btnAdd, pnl, "新增共享用户：")) {
            List<String> list = ftable_tmp.getSelectKeys();
            String key = scheme.getCommAnalyseScheme_key();
            String exStr = DbUtil.getQueryForMID("delete from SchemeShareDetail where scheme_key='" + key + "' and userKey in", list, "", ";");
            exStr += "delete from SchemeShareDetail where scheme_key='" + key + "' and shareType='按角色';";
            exStr += tranUserSQL(list, "按员工", key);
            ValidateSQLResult result = CommUtil.excuteSQLs(exStr.toString(), ";");
            if (result.getResult() == 0) {
                shareKeys.clear();
                List keys = CommUtil.selectSQL("select userKey from SchemeShareDetail where scheme_key='" + key + "' and shareType='按员工'");
                shareKeys.addAll(keys);
                share_type = "按员工";
                JOptionPane.showMessageDialog(null, "保存成功");
                refreshUserUI();
            } else {
                MsgUtil.showHRSaveErrorMsg(result);
            }
        }
    }

    private String tranUserSQL(List<String> list, String shareType, String scheme_key) {
        if (list.isEmpty()) {
            return "";
        }
        List<String[]> keys = new ArrayList();
        for (String str : list) {
            keys.add(new String[]{"'" + UtilTool.getUID() + "'", "'" + scheme_key + "'", "'" + shareType + "'", "'" + str + "'"});
        }
        return DbUtil.getInstrForMID("insert into SchemeShareDetail(schemeShareDetail_key,scheme_key,shareType,userKey)", keys, "", ";");
    }

    private void delShareUser() {
        if (scheme == null) {
            return;
        }
        List<String> list = ftable_a01.getSelectKeys();
        if (list.isEmpty()) {
            return;
        }
        if (JOptionPane.showConfirmDialog(JOptionPane.getFrameForComponent(btnDel),
                "确定要删除选择的共享用户吗", "询问", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
            return;
        }
        String key = scheme.getCommAnalyseScheme_key();
        String exSQL = DbUtil.getQueryForMID("delete from SchemeShareDetail where scheme_key='" + key + "' and userKey in", list);
        ValidateSQLResult result = CommUtil.excuteSQLs(exSQL, ";");
        if (result.getResult() == 0) {
            JOptionPane.showMessageDialog(null, "删除成功");
            shareKeys.clear();
            List keys = CommUtil.selectSQL("select userKey from SchemeShareDetail where scheme_key='" + key + "' and shareType='按员工'");
            shareKeys.addAll(keys);
            refreshUserUI();
        } else {
            MsgUtil.showHRDelErrorMsg(result);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jspMain = new javax.swing.JSplitPane();
        pnlLeft = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jrbRole = new javax.swing.JRadioButton();
        jrbPerson = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnAdd = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnClose = new javax.swing.JButton();
        pnlRight = new javax.swing.JPanel();

        jspMain.setDividerLocation(200);
        jspMain.setDividerSize(2);

        pnlLeft.setLayout(new java.awt.BorderLayout());
        jspMain.setLeftComponent(pnlLeft);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        buttonGroup1.add(jrbRole);
        jrbRole.setSelected(true);
        jrbRole.setText("按角色");
        jrbRole.setFocusable(false);
        jrbRole.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jrbRole.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jrbRole);

        buttonGroup1.add(jrbPerson);
        jrbPerson.setText("按员工");
        jrbPerson.setFocusable(false);
        jrbPerson.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jrbPerson.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jrbPerson);
        jToolBar1.add(jSeparator1);

        btnAdd.setText("增加");
        btnAdd.setFocusable(false);
        btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnAdd);

        btnDel.setText("删除");
        btnDel.setFocusable(false);
        btnDel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnDel);

        btnSave.setText("保存");
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSave);
        jToolBar1.add(jSeparator2);

        btnClose.setText("退出");
        btnClose.setFocusable(false);
        btnClose.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClose.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnClose);

        pnlRight.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
            .addComponent(pnlRight, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlRight, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE))
        );

        jspMain.setRightComponent(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMain, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMain, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnSave;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JRadioButton jrbPerson;
    private javax.swing.JRadioButton jrbRole;
    private javax.swing.JSplitPane jspMain;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlRight;
    // End of variables declaration//GEN-END:variables
}
