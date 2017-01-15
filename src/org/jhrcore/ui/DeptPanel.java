package org.jhrcore.ui;

import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;

import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.jhrcore.client.UserContext;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.ui.listener.IPickDeptRefreshListener;
import org.jhrcore.ui.listener.IPickSelectListener;
import org.jhrcore.ui.property.ClientProperty;
import org.jhrcore.ui.renderer.HRRendererView;
import org.jhrcore.util.ComponentUtil;
import org.jhrcore.util.SysUtil;

/*
 * @author yangzhou
 * @version 部门树的生成
 */
public class DeptPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTree deptTree;
    private DeptTreeModel moduleTreeModel;
    private List<DeptCode> depts = new ArrayList<DeptCode>();
    private JPopupMenu popupMenu = new JPopupMenu();
    private JMenuItem miShowCode = new JMenuItem("显示部门代码");
    private JMenuItem miShowG10 = new JMenuItem("显示岗位");
    private JMenuItem miRefersh = new JMenuItem("刷新");
    private int show_type = 0;//为0时，按默认显示，为1时则无论如何都不会显示岗位
    private List<IPickDeptRefreshListener> listeners = new ArrayList<IPickDeptRefreshListener>();
    private boolean discontiguous_selection = false;
    private String showG10 = "DeptMng.show_g10";
    private String showCode = "DeptMng.show_code";
    private DeptCode curDept;

    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    public void addPickDeptRefreshListener(IPickDeptRefreshListener listener) {
        listeners.add(listener);
    }

    public void delPickDeptRefreshListener(IPickDeptRefreshListener listener) {
        listeners.remove(listener);
    }

    public DeptPanel(List<DeptCode> depts) {
        this(depts, 0);
    }

    public DeptPanel(List<DeptCode> depts, int show_type) {
        this(depts, show_type, false);
    }

    public DeptPanel(List<DeptCode> depts, int show_type, boolean discontiguous_selection) {
        super(new BorderLayout());
        this.show_type = show_type;
        this.depts.addAll(depts);
        this.discontiguous_selection = discontiguous_selection;
        initUI();
        setEvents();
    }

    public CheckTreeNode getNodeByDept(DeptCode dept) {
        CheckTreeNode node = (CheckTreeNode) moduleTreeModel.getRoot();
        CheckTreeNode resultNode = null;
        Enumeration deptEnum = node.depthFirstEnumeration();
        String val = dept.getDeptCode_key();
        while (deptEnum.hasMoreElements()) {
            CheckTreeNode tmpNode = (CheckTreeNode) deptEnum.nextElement();
            if (tmpNode.getUserObject() instanceof DeptCode) {
                DeptCode dept1 = (DeptCode) tmpNode.getUserObject();
                String field_val = dept1.getDeptCode_key();
                if (val.equals(field_val)) {
                    resultNode = tmpNode;
                    break;
                }
            }
        }
        return resultNode;
    }

    public void locateDept(DeptCode dept) {
        CheckTreeNode node = (CheckTreeNode) moduleTreeModel.getRoot();
        CheckTreeNode resultNode = null;
        Enumeration deptEnum = node.depthFirstEnumeration();
        String val = dept.getDeptCode_key();
        while (deptEnum.hasMoreElements()) {
            CheckTreeNode tmpNode = (CheckTreeNode) deptEnum.nextElement();
            if (tmpNode.getUserObject() instanceof DeptCode) {
                DeptCode dept1 = (DeptCode) tmpNode.getUserObject();
                String field_val = dept1.getDeptCode_key();
                if (val.equals(field_val)) {
                    resultNode = tmpNode;
                    break;
                }
            }
        }
        if (resultNode == null) {
            return;
        }
        ComponentUtil.initTreeSelection(deptTree, resultNode);

    }

    private void initUI() {
        ComponentUtil.setIcon(miRefersh, "blank");
        popupMenu.add(miShowCode);
        if (show_type == 0) {
            popupMenu.add(miShowG10);
            moduleTreeModel = new DeptTreeModel(depts, UserContext.show_g10_flag);
        } else {
            moduleTreeModel = new DeptTreeModel(depts, show_type < 0);
        }
        popupMenu.add(miRefersh);
        deptTree = new JTree(moduleTreeModel);
        deptTree.setRootVisible(false);
        deptTree.setShowsRootHandles(true);
        initSelection();
        HRRendererView.getDeptMap(deptTree).initTree(deptTree);
        JScrollPane scrollPane = new JScrollPane(deptTree);
        this.add(scrollPane, BorderLayout.CENTER);
        if (discontiguous_selection) {
            deptTree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        }
    }

    private void setEvents() {
        deptTree.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    updateDeptCode();
                    popupMenu.show(deptTree, e.getX(), e.getY());
                }
            }
        });
        ComponentUtil.setBooleanIcon(miShowCode, showCode, new IPickSelectListener() {

            @Override
            public void select(boolean select) {
                UserContext.show_dept_code_flag = select;
                updateDeptCode();
            }
        });
        if (show_type == 0) {
            ComponentUtil.setBooleanIcon(miShowG10, showG10, new IPickSelectListener() {

                @Override
                public void select(boolean select) {
                    UserContext.show_g10_flag = select;
                    moduleTreeModel.setShow_g10_flag(UserContext.show_g10_flag);
                    moduleTreeModel.rebuild();
                    deptTree.updateUI();
                }
            });
        }
        miRefersh.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                rebuildTree();
            }
        });
    }

    public static void initDeptTreeSelection(JTree tree) {
        String dept_code = ClientProperty.getInstance().getUI_Tree_deptSelect().getSysparameter_value();
        CheckTreeNode node = null;
        if (!SysUtil.objToStr(dept_code).equals("")) {
            DeptCode dept = null;
            for (DeptCode dc : UserContext.getDepts(false)) {
                if (dc.getDept_code().equals(dept_code)) {
                    dept = dc;
                    break;
                }
            }
            if (dept != null) {
                node = locateDept(tree, dept);
            }
        }
        ComponentUtil.initTreeSelection(tree, node);
        if(node!=null)
        tree.expandPath(new TreePath(node.getPath()));

    }

    public void initSelection() {
        initDeptTreeSelection(deptTree);
        Object obj = ((CheckTreeNode) deptTree.getLastSelectedPathComponent()).getUserObject();
        if (obj instanceof DeptCode) {
            curDept = (DeptCode) obj;
        } else {
            curDept = null;
        }
    }

    public static CheckTreeNode locateDept(JTree tree, Object obj) {
        if (obj == null) {
            return null;
        }
        CheckTreeNode root = (CheckTreeNode) tree.getModel().getRoot();
        Enumeration enumt = root.breadthFirstEnumeration();
        CheckTreeNode locate_node = null;
        if (obj instanceof DeptCode) {
            DeptCode locate_dept = (DeptCode) obj;
            while (enumt.hasMoreElements()) {
                CheckTreeNode node = (CheckTreeNode) enumt.nextElement();
                if (node == root) {
                    continue;
                }
                if (node.getUserObject() instanceof DeptCode) {
                    DeptCode cur_dept = (DeptCode) node.getUserObject();
                    if (cur_dept.getDeptCode_key().equals(locate_dept.getDeptCode_key())) {
                        locate_node = node;
                        break;
                    }
                }

            }
        }
        return locate_node;
    }

    public DeptCode getCurDept() {
        return curDept;
    }

    public void rebuildTree() {
        List<DeptCode> new_depts = new ArrayList<DeptCode>();
        for (IPickDeptRefreshListener listener : listeners) {
            new_depts.addAll(listener.refreshDepts());
        }
        if (new_depts.isEmpty()) {
            new_depts.addAll(depts);
        }
        depts.clear();
        depts.addAll(new_depts);
        moduleTreeModel.buildTree(depts);
        updateDept(depts);
        ComponentUtil.initTreeSelection(deptTree);
    }

    public void rebuildTree(List list) {
        depts.clear();
        depts.addAll(list);
        moduleTreeModel.buildTree(depts);
        updateDept(depts);
        ComponentUtil.initTreeSelection(deptTree);
    }

    public static void updateDept(List<DeptCode> depts) {
        for (DeptCode dc : depts) {
            dc.setShow_code_flag(UserContext.show_dept_code_flag);
        }
    }

    private void updateDeptCode() {
        updateDept(depts);
        deptTree.updateUI();
    }

    public DeptTreeModel getModuleTreeModel() {
        return moduleTreeModel;
    }

    public JTree getDeptTree() {
        return deptTree;
    }

    public void updateUIView() {
        Runnable run2 = new Runnable() {

            @Override
            public void run() {
                updateDeptCode();
            }
        };
        SwingUtilities.invokeLater(run2);
    }
}
