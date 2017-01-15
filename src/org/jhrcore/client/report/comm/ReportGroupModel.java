/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.client.report.comm;

import java.util.Hashtable;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.jhrcore.entity.report.ReportGroup;
import org.jhrcore.mutil.ReportUtil;

/**
 *
 * @author lenovo
 */
public class ReportGroupModel extends DefaultTreeModel {

    private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("ËùÓÐÌ×±í");
    private Hashtable<String, DefaultMutableTreeNode> class_name = new Hashtable<String, DefaultMutableTreeNode>();

    public ReportGroupModel() {
        super(new DefaultMutableTreeNode());
        this.setRoot(rootNode);
        buildTree();
    }

    public DefaultMutableTreeNode addGroup(DefaultMutableTreeNode node, ReportGroup rg) {
        String s_group = rg.getGroup_name();
        DefaultMutableTreeNode cur_name = new DefaultMutableTreeNode(rg);
        if (s_group != null && !s_group.isEmpty()) {
            DefaultMutableTreeNode class_node = class_name.get(s_group);
            if (class_node == null) {
                class_node = new DefaultMutableTreeNode(s_group);
                class_name.put(s_group, class_node);
                rootNode.add(class_node);
            }
            class_node.add(cur_name);
        } else {
            rootNode.add(cur_name);
        }
        if (node != null) {
            DefaultMutableTreeNode pNode = (DefaultMutableTreeNode) node.getParent();
            node.removeFromParent();
            if (pNode.getChildCount() == 0) {
                pNode.removeFromParent();
            }
        }
        return cur_name;
    }

    public void buildTree() {
        rootNode.removeAllChildren();
        class_name.clear();
        List<ReportGroup> rgs = ReportUtil.getReportGroups();
        for (ReportGroup rg : rgs) {
            addGroup(null, rg);
        }
    }
}