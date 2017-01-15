/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZpPlanPanel.java
 *
 * Created on 2011-2-22, 9:37:55
 */
package org.jhrcore.ui;

import org.jhrcore.ui.task.IModuleCode;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.UtilTool;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.rebuild.EntityBuilder;
import org.jhrcore.ui.listener.IPickBeanPanelEditListener;
import org.jhrcore.util.ComponentUtil;
import org.jhrcore.comm.HrLog;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.CommClass;
import org.jhrcore.ui.listener.IPickDeptListener;
import org.jhrcore.ui.renderer.HRRendererView;
import org.jhrcore.util.MsgUtil;

/**
 *
 * @author PMJ
 */
public class CommClassPanel extends javax.swing.JPanel implements IModuleCode {

    private CommClassTreeModel model;
    private JTree classTree;
    private DefaultMutableTreeNode cur_node = null;
    private CommClass cur_class;//当对前节点选中的对象
    private List<IPickDeptListener> listeners = new ArrayList<IPickDeptListener>();
    private String module_code = "commclass";
    private HrLog log = new HrLog(module_code);
    private boolean menu;
    private JButton btnAddClass = new JButton("增加");
    private JButton btnEditClass = new JButton("修改");
    private JButton btnDelClass = new JButton("删除");
    private JTabbedPane jtpMain = new JTabbedPane();
    private JToolBar toolBars;

    public void addPickObjListener(IPickDeptListener listener) {
        listeners.add(listener);
    }

    public void delPickObjListener(IPickDeptListener listener) {
        listeners.remove(listener);
    }

    /** Creates new form ZpPlanPanel */
    public CommClassPanel(String module_code, boolean menu) {
        this.module_code = module_code + ".class";
        this.menu = menu;
        initComponents();
        initOthers();
        setupEvents();
    }

    private void initOthers() {
        this.add(jtpMain);
        if (menu) {
            toolBars = new JToolBar();
            this.add(toolBars, BorderLayout.NORTH);
            toolBars.setFloatable(false);
            toolBars.add(btnAddClass);
            toolBars.add(btnEditClass);
            toolBars.add(btnDelClass);
        }
        model = new CommClassTreeModel(module_code, "所有分类");
        classTree = new JTree(model);
        HRRendererView.getCommMap().initTree(classTree);
        JPanel pnlLeft1 = new JPanel();
        String module_name = UserContext.getFuntionName(module_code);
        jtpMain.addTab(module_name, pnlLeft1);
        pnlLeft1.setLayout(new java.awt.BorderLayout());
        pnlLeft1.add(new JScrollPane(classTree), BorderLayout.CENTER);
        ComponentUtil.setSysFuntionNew(this);
    }

    private void setupEvents() {
        classTree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                if (e.getPath() == null || e.getPath().getLastPathComponent() == null) {
                    return;
                }
                cur_node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
                if (cur_node.getUserObject() instanceof CommClass) {
                    cur_class = (CommClass) cur_node.getUserObject();
                } else {
                    cur_class = null;
                }
                for (IPickDeptListener listener : listeners) {
                    listener.pickDept(cur_class);
                }
            }
        });
        if (menu) {
            //增加分类
            btnAddClass.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    addClass();
                }
            });

            //修改分类名称
            btnEditClass.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    modifyClass();
                }
            });
            //删除分类
            btnDelClass.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteClass();
                }
            });
        }
    }

    /**
     *
     *删除分类
     * 
     **/
    public void deleteClass() {
        if (cur_node == null || cur_class == null) {
            return;
        }
        if (cur_class.getParent_code().equals("ROOT")) {
            return;
        }
        if (cur_node.getChildCount() > 0) {
            JOptionPane.showMessageDialog(ContextManager.getMainFrame(), "该分类有子分类，不允许删除！",
                    "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (CommUtil.exists("select 1 from Px_planmx p where p.commClass_key ='" + cur_class.getCommClass_key() + "'")) {
            JOptionPane.showMessageDialog(ContextManager.getMainFrame(), "该分类下有培训计划，不能删除！",
                    "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (CommUtil.exists("select 1 from Px_act p where p.commClass_key ='" + cur_class.getCommClass_key() + "'")) {
            JOptionPane.showMessageDialog(ContextManager.getMainFrame(), "该分类下有培训活动，不能删除！",
                    "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(ContextManager.getMainFrame(),
                "确定要删除【" + cur_class.getClass_name() + "】分类么", "询问", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
            return;
        }
        ValidateSQLResult vs = CommUtil.deleteEntity(cur_class);
        if (vs.getResult() == 0) {
            DefaultMutableTreeNode tmp_node = ComponentUtil.getNextNode(cur_node);
            cur_node.removeFromParent();
            ComponentUtil.initTreeSelection(classTree, tmp_node);
            log.info("删除成功");
        } else {
            log.info("删除失败，原因" + vs.getMsg());
        }
    }

    /**
     *
     *修改分类
     * 
     **/
    public void modifyClass() {
        if (cur_class == null) {
            return;
        }
        List<String> fields = EntityBuilder.getCommFieldNameListOf(CommClass.class, EntityBuilder.COMM_FIELD_VISIBLE);
        fields.remove("parent_name");
        if (BeanPanel.edit(cur_class, fields, "修改分类：", getVE())) {
            ValidateSQLResult result = CommUtil.updateEntity(cur_class);
            if (result.getResult() == 0) {
                MsgUtil.showHRSaveSuccessMsg(null);
                classTree.updateUI();
            } else {
                MsgUtil.showHRSaveErrorMsg(result);
            }
        }
    }

    /**
     *
     *增加分类
     *
     **/
    public void addClass() {
        if (cur_class == null) {
            return;
        }

        IPickBeanPanelEditListener listener = new IPickBeanPanelEditListener() {

            @Override
            public void pickSave(Object obj) {
                ValidateSQLResult vs = CommUtil.saveEntity(obj);
                if (vs.getResult() == 0) {
                    CheckTreeNode node = new CheckTreeNode(obj);
                    cur_node.add(node);
                    classTree.updateUI();
                } else {
                    MsgUtil.showHRSaveErrorMsg(vs);
                }
            }

            @Override
            public Object getNew() {
                CommClass p_node = (CommClass) UtilTool.createUIDEntity(CommClass.class);
                p_node.setParent_code(cur_class.getCode());
                p_node.setCode(model.getNewCode(module_code, cur_class.getCode()));
                p_node.setClass_code(module_code);
                p_node.setCdate(CommUtil.getServerDate());
                p_node.setCuser(UserContext.getCurPerson());
                p_node.setParent_name(cur_class.getClass_name());
                return p_node;
            }
        };
        List<String> fields = EntityBuilder.getCommFieldNameListOf(CommClass.class, EntityBuilder.COMM_FIELD_VISIBLE);
        BeanPanel.editForRepeat((JFrame) JOptionPane.getFrameForComponent(btnAddClass), fields, "新增分类", getVE(), listener);
    }

    private ValidateEntity getVE() {
        return new ValidateEntity() {

            @Override
            public boolean isEntityValidate(Object obj) {
                CommClass p_node = (CommClass) obj;
                if (null == p_node.getClass_name() || p_node.getClass_name().length() == 0) {
                    JOptionPane.showMessageDialog(ContextManager.getMainFrame(), "分类名称不能为空！",
                            "错误", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                return true;
            }
        };
    }

    public CommClass getCur_class() {
        return cur_class;
    }

    public JTree getClassTree() {
        return classTree;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public String getModuleCode() {
        return module_code;
    }
}
