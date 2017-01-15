/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.ui;

import java.util.Hashtable;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.jhrcore.entity.base.TempFieldInfo;

/**
 *
 * @author mxliteboss
 */
public class GroupedFieldTreeModel extends DefaultTreeModel {

    private static final long serialVersionUID = 1L;
    private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("×Ö¶ÎÁÐ±í");
    private Hashtable<String, DefaultMutableTreeNode> field_groups = new Hashtable<String, DefaultMutableTreeNode>();

    public Hashtable<String, DefaultMutableTreeNode> getField_groups() {
        return field_groups;
    }

    public void setField_groups(Hashtable<String, DefaultMutableTreeNode> field_groups) {
        this.field_groups = field_groups;
    }

    public void refreshGroupName(String old_name, String new_name) {
        DefaultMutableTreeNode node = field_groups.get(old_name);
        if (node != null) {
            field_groups.remove(old_name);
            node.setUserObject(new_name);
            field_groups.put(new_name, node);
        }
    }

    public GroupedFieldTreeModel() {
        super(new DefaultMutableTreeNode());
        this.setRoot(rootNode);
    }

    @SuppressWarnings("unchecked")
    public GroupedFieldTreeModel(List<TempFieldInfo> field_infos, boolean group_flag) {
        super(new DefaultMutableTreeNode());
        //this.field_infos = field_infos;
        this.setRoot(rootNode);
        buildTree(field_infos, group_flag);
    }

    public void removeGroupNode(DefaultMutableTreeNode node, boolean grouped) {
        if (grouped) {
            String group_name = node.getUserObject().toString();
            field_groups.remove(group_name);
        }
        node.removeFromParent();
    }

    public void buildTree(List<TempFieldInfo> field_infos, boolean grouped) {
        rootNode.removeAllChildren();
        for (TempFieldInfo tfi : field_infos) {
            addNode(tfi, grouped);
        }
    }

    public DefaultMutableTreeNode addNode(TempFieldInfo fd, boolean grouped) {
        DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(fd);
        if (grouped) {
            DefaultMutableTreeNode group_node = field_groups.get(fd.getGroup_name());
            if (group_node == null) {
                group_node = new DefaultMutableTreeNode(fd.getGroup_name());
                rootNode.add(group_node);
                field_groups.put(fd.getGroup_name(), group_node);
            }
            group_node.add(tmp);
        } else {
            rootNode.add(tmp);
        }
        return tmp;
    }
}