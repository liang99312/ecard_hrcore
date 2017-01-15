/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.jhrcore.entity.base.TempFieldInfo;

/**
 *
 * @author mxliteboss
 */
public class ShowFieldTreeModel extends DefaultTreeModel {

    private static final long serialVersionUID = 1L;
    private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("×Ö¶ÎÁÐ±í");
    private List<TempFieldInfo> field_infos;
    private Hashtable<String, DefaultMutableTreeNode> field_groups = new Hashtable<String, DefaultMutableTreeNode>();
    private Hashtable<TempFieldInfo, Integer> field_flags = new Hashtable<TempFieldInfo, Integer>();

    public ShowFieldTreeModel(List<TempFieldInfo> field_infos) {
        super(new DefaultMutableTreeNode());
        this.field_infos = field_infos;
        this.setRoot(rootNode);
        buildTree(field_infos);
    }

    public void rebuild() {
        field_groups.clear();
        field_flags.clear();
        buildTree(field_infos);
    }
    public DefaultMutableTreeNode getRootNode(){
        return rootNode;
    }

    public void buildTree(List<TempFieldInfo> field_infos) {
        field_groups.clear();
        field_flags.clear();
        rootNode.removeAllChildren();
        for (TempFieldInfo tfi : field_infos) {
            addField(tfi);
        }
    }

    public DefaultMutableTreeNode addNode(TempFieldInfo tfi) {
        DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(tfi);
        DefaultMutableTreeNode group_node = field_groups.get(tfi.getEntity_name());
        if (group_node == null) {
            if (tfi.getEntity_name().equals("")) {
                if (rootNode.getChildCount() <= tfi.getOrder_no()) {
                    rootNode.add(tmp);
                } else {
                    rootNode.insert(tmp, tfi.getOrder_no());
                }
                return tmp;
            }
            group_node = new DefaultMutableTreeNode(tfi.getEntity_caption());
            rootNode.add(group_node);
            field_groups.put(tfi.getEntity_name(), group_node);
            group_node.add(tmp);
        } else {
            int insert_ind = -1;
            Enumeration enumt = group_node.children();
            while (enumt.hasMoreElements()) {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) enumt.nextElement();
                TempFieldInfo child_tfi = (TempFieldInfo) child.getUserObject();
                if (child_tfi.getOrder_no() > tfi.getOrder_no()) {
                    insert_ind = group_node.getIndex(child);
                    break;
                }
            }
            if (insert_ind != -1) {
                group_node.insert(tmp, insert_ind);
            } else {
                group_node.add(tmp);
            }
        }
        return tmp;
    }

    private void addField(TempFieldInfo tfi) {
        DefaultMutableTreeNode group_node = field_groups.get(tfi.getEntity_name());
        if (tfi.getEntity_name().equals("")) {
            rootNode.add(new DefaultMutableTreeNode(tfi));
            return;
        }
        if (group_node == null) {
            group_node = new DefaultMutableTreeNode(tfi.getEntity_caption());
            rootNode.add(group_node);
            field_groups.put(tfi.getEntity_name(), group_node);
        }
        DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(tfi);
        group_node.add(tmp);
        field_flags.put(tfi, group_node.getChildCount());
    }

    public void removeNode(DefaultMutableTreeNode node) {
        if (node == null || node.getUserObject() == null) {
            return;
        }
        Object obj = node.getUserObject();
        if (obj instanceof TempFieldInfo) {
            node.removeFromParent();
        } 
    }
}
