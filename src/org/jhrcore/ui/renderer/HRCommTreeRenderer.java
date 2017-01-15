/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.jhrcore.ui.CheckTreeNode;
import org.jhrcore.util.RenderderUtil;

/**
 *
 * @author mxliteboss
 */
public class HRCommTreeRenderer extends DefaultTreeCellRenderer {

    private RenderderMap map = null;
    private GridBagLayout gridbag = new GridBagLayout();
    private GridBagConstraints con = new GridBagConstraints();

    public HRCommTreeRenderer() {
        this(null);
    }

    public HRCommTreeRenderer(RenderderMap map) {
        super();
        this.map = map;
        con.fill = GridBagConstraints.BOTH;
        con.weightx = 1.0;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree,
            Object value,
            boolean sel,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object obj = node.getUserObject();
        if (obj == null) {
            return new JLabel();
        }
        JLabel lblTmp = new JLabel(obj.toString());
        Component c = lblTmp;
        String tag = null;
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
        JCheckBox checkBox = null;
        if (tree.isEditable() && value instanceof CheckTreeNode) {
            CheckTreeNode cn1 = (CheckTreeNode) value;
            checkBox = new JCheckBox();
            checkBox.setSelected(cn1.isSelected());
            checkBox.setBackground(Color.WHITE);
            lblTmp.setText(value.toString());
        }
        if (tags.length > 1 || checkBox != null) {
            int cols = tags.length;
            if (checkBox != null) {
                cols++;
            }
            JPanel pnl = new JPanel(gridbag);
            if (checkBox != null) {
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

            pnl.add(lblTmp);
            gridbag.setConstraints(lblTmp, con);
        }
        lblTmp.setIcon(RenderderUtil.getIconByTag(tags[tags.length - 1]));
        if (sel) {
            if (tags[tags.length - 1].equals("node")) {
                lblTmp.setIcon(RenderderUtil.getIconByTag("nodeopen"));
            }
            lblTmp.setBackground(new Color(184, 207, 229));
            lblTmp.setForeground(Color.BLUE);
            lblTmp.setOpaque(true);
        }
        return c;
    }
}
