package org.jhrcore.ui.renderer;

import org.jhrcore.ui.listener.CheckNodeTreeSelectListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.EventObject;

import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import org.jhrcore.ui.CheckTreeNode;
import org.jhrcore.ui.TreeSelectMod;
import org.jhrcore.ui.listener.IPickTreeSelectListener;
import org.jhrcore.util.RenderderUtil;

public class HRCommTreeEditor implements TreeCellEditor, IPickTreeSelectListener {

    private static final long serialVersionUID = 1L;
    private JCheckBox checkBox = new JCheckBox();
    private JLabel lbl = new JLabel();
    private CheckTreeNode cn = null;
    private JPanel pnl = new JPanel(new GridBagLayout());//new BorderLayout());
    private GridBagLayout gridbag = new GridBagLayout();
    private JTree tree;
    private boolean singleMod = false;//�Ƿ�ѡ,Ĭ�϶�ѡ
    private boolean parentFollowMod = false;//ѡ�񸸽ڵ㣬�ӽڵ��Ƿ���棬Ĭ�ϲ�����
    private boolean childFollowMod = false;//ѡ�񸸽ڵ㣬�ӽڵ��Ƿ���棬Ĭ�ϲ�����
    private RenderderMap map = null;
    private GridBagConstraints con = new GridBagConstraints();
    private List<CheckNodeTreeSelectListener> checkNodeTreeSelectListeners = new ArrayList<CheckNodeTreeSelectListener>();

    public void addCheckNodeTreeSelectListener(CheckNodeTreeSelectListener listener) {
        checkNodeTreeSelectListeners.add(listener);
    }

    public void delCheckNodeTreeSelectListener(CheckNodeTreeSelectListener listener) {
        checkNodeTreeSelectListeners.remove(listener);
    }

    public HRCommTreeEditor(JTree tree, int selectMod) {
        this(tree, selectMod, null);
    }

    public HRCommTreeEditor(JTree tree, int selectMod, RenderderMap map) {
        super();
        con.fill = GridBagConstraints.BOTH;
        con.weightx = 1.0;
        this.tree = tree;
        tree.setEditable(selectMod > TreeSelectMod.nodeSelectMod);
        this.singleMod = selectMod == TreeSelectMod.singleCheckMod;
        this.childFollowMod = selectMod == TreeSelectMod.nodeCheckChildFollowMod || selectMod == TreeSelectMod.nodeCheckAllFollowMod;
        this.parentFollowMod = selectMod == TreeSelectMod.nodeCheckParentFollowMod || selectMod == TreeSelectMod.nodeCheckAllFollowMod;
        this.map = map;
        checkBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cn == null) {
                    return;
                }
                cn.setSelected(checkBox.isSelected());
                changeChildren(cn);
                changeParent(cn);
                for (CheckNodeTreeSelectListener listener : checkNodeTreeSelectListeners) {
                    listener.pickSelectNode(checkBox.isSelected(), cn);
                }
                HRCommTreeEditor.this.tree.updateUI();
            }
        });
    }
//    public TreeCellCheckEditor(JTree tree, boolean childFollowMod) {
//        this(tree, childFollowMod, false, false);
//    }
//
//    public TreeCellCheckEditor(JTree tree, boolean childFollowMod, RenderderMap map) {
//        this(tree, childFollowMod, false, false, map);
//    }
//
//    public TreeCellCheckEditor(JTree tree, boolean childFollowMod, final boolean singleMod) {
//        this(tree, childFollowMod, singleMod, false);
//    }
//
//    public TreeCellCheckEditor(JTree tree, boolean childFollowMod, final boolean singleMod, boolean parentFollowMod) {
//        this(tree, childFollowMod, singleMod, parentFollowMod, null);
//    }
//
//    public TreeCellCheckEditor(JTree tree, boolean childFollowMod, final boolean singleMod, boolean parentFollowMod, RenderderMap map) {

    public void checkAll() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getModel().getRoot();
        Enumeration en = node.breadthFirstEnumeration();
        while (en.hasMoreElements()) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) en.nextElement();
            if (!(n instanceof CheckTreeNode)) {
                continue;
            }
            CheckTreeNode cn = (CheckTreeNode) n;
            cn.setSelected(true);
        }
        tree.updateUI();
    }

    public void unCheckAll() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getModel().getRoot();
        Enumeration en = node.breadthFirstEnumeration();
        while (en.hasMoreElements()) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) en.nextElement();
            if (!(n instanceof CheckTreeNode)) {
                continue;
            }
            CheckTreeNode cn = (CheckTreeNode) n;
            cn.setSelected(false);
        }
        tree.updateUI();
    }

    public void fanCheckAll() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getModel().getRoot();
        Enumeration en = node.breadthFirstEnumeration();
        while (en.hasMoreElements()) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) en.nextElement();
            if (!(n instanceof CheckTreeNode)) {
                continue;
            }
            CheckTreeNode cn = (CheckTreeNode) n;
            cn.setSelected(!cn.isSelected());
        }
        tree.updateUI();
    }

    /**
     * ����ָ����ˢ���ӽڵ�״̬����childFollowModģʽ�£�����ʱָ���ڵ���ӽڵ��ѡ��״̬��ָ���ڵ��ѡ��״��һ��
     * ��singleModģʽ�£�����ȡ��ָ���ڵ�������ӽڵ�ѡ��״̬������singleMod���ȼ���
     * @param parent
     */
    private void changeChildren(CheckTreeNode parent) {
        if (childFollowMod) {
            for (int i = 0; i < parent.getChildCount(); i++) {
                if (parent.getChildAt(i) instanceof CheckTreeNode) {
                    CheckTreeNode cn2 = (CheckTreeNode) parent.getChildAt(i);
                    cn2.setSelected(parent.isSelected());
                    changeChildren(cn2);
                }
            }
        }
        if (singleMod) {
            for (int i = 0; i < parent.getChildCount(); i++) {
                if (parent.getChildAt(i) instanceof CheckTreeNode) {
                    CheckTreeNode cn2 = (CheckTreeNode) parent.getChildAt(i);
                    if (parent.isSelected()) {
                        cn2.setSelected(false);
                        changeChildren(cn2);
                    }
                }
            }
        }
    }

    /**
     * ����ָ���ڵ�ˢ�¸��ڵ�״̬����singleModģʽ�£�����ȡ�����ڵ�ѡ��״̬������ʱ����Ч
     * @param parent��ָ���ڵ�
     */
    private void changeParent(CheckTreeNode parent) {
        CheckTreeNode p = parent;
        if (singleMod) {
            while (p.getParent() != null && p.getParent() instanceof CheckTreeNode) {
                p = (CheckTreeNode) p.getParent();
                p.setSelected(false);
            }
        }
        if (parentFollowMod) {
            DefaultMutableTreeNode pn = (DefaultMutableTreeNode) p.getParent();
            while (pn != null && !pn.isRoot()) {
                boolean tmp = false;
                for (int i = 0; i < pn.getChildCount(); i++) {
                    if (pn.getChildAt(i) instanceof CheckTreeNode) {
                        CheckTreeNode cn = (CheckTreeNode) pn.getChildAt(i);
                        tmp = tmp || cn.isSelected();
                        if (tmp) {
                            break;
                        }
                    }
                }
                if (pn instanceof CheckTreeNode) {
                    CheckTreeNode c_tmp = (CheckTreeNode) pn;
                    c_tmp.setSelected(tmp);
                }

                pn = (DefaultMutableTreeNode) pn.getParent();
            }
        }
    }

    @Override
    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object obj = node.getUserObject();
        if (obj == null) {
            return new JLabel();
        }
        String tag = null;
        Component c = lbl;
        lbl.setText(obj.toString());
        if (map != null) {
            tag = map.getIconTag(tree, obj, node.getLevel());
            if (tag == null) {
                if (node.isRoot()) {
                    tag = map.getIconByTag("ROOT");
                } else if (node.isLeaf()) {
                    tag = map.getIconByTag("leaf");
                } else {
                    tag = map.getIconByTag("node");
                }
            }
        }
        if (tag == null) {
            tag = RenderderUtil.getIconTag(obj);
        }
        if (tag == null) {
            if (leaf) {
                tag = "leaf";
            } else if (node == tree.getModel().getRoot()) {
                tag = "root";
            } else {
                tag = "node";
            }
        }
        String[] tags = tag.split(";");
        if (tree.isEditable() && value instanceof CheckTreeNode) {
            cn = (CheckTreeNode) value;
            checkBox.setSelected(cn.isSelected());
            checkBox.setBackground(Color.WHITE);
        }
        if (tags.length > 1 || cn != null) {
            pnl.removeAll();
            int cols = tags.length;
            if (cn != null) {
                cols++;
            }

            if (cn != null) {
                gridbag.setConstraints(checkBox, con);
                pnl.add(checkBox);
            }
            for (int i = 0; i < (tags.length - 1); i++) {
                JLabel lbl = new JLabel();
                lbl.setIcon(RenderderUtil.getIconByTag(tags[i]));
                gridbag.setConstraints(lbl, con);
                pnl.add(lbl);
            }
            c = pnl;
            pnl.setBackground(Color.WHITE);
            pnl.add(lbl);
            gridbag.setConstraints(lbl, con);
        }
        lbl.setIcon(RenderderUtil.getIconByTag(tags[tags.length - 1]));
        if (isSelected) {
            if (tags[tags.length - 1].equals("node")) {
                lbl.setIcon(RenderderUtil.getIconByTag("nodeopen"));
            }
            lbl.setBackground(new Color(184, 207, 229));
            lbl.setForeground(Color.BLUE);
            lbl.setOpaque(true);
        }
        return c;
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {
        // TODO Auto-generated method stub
    }

    @Override
    public void cancelCellEditing() {
        // TODO Auto-generated method stub
    }

    @Override
    public Object getCellEditorValue() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void selectChild(boolean selected) {
        this.childFollowMod = selected;
    }
}
