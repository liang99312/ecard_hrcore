/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.queryanalysis;

import java.util.Hashtable;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.query.CommAnalyseScheme;

/**
 *
 * @author mxliteboss
 */
public class AnalyseTreeModel extends DefaultTreeModel {

    private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("所有方案");
    private DefaultMutableTreeNode myNode = new DefaultMutableTreeNode("我的方案");
    private DefaultMutableTreeNode shareNode = new DefaultMutableTreeNode("共享方案");
    // 体系类型节点
    private Hashtable<String, DefaultMutableTreeNode> class_table = new Hashtable<String, DefaultMutableTreeNode>();

    public AnalyseTreeModel(List list) {
        super(new DefaultMutableTreeNode());
        this.setRoot(rootNode);
        rootNode.add(myNode);
        rootNode.add(shareNode);
        buildTree(list);
    }

    public DefaultMutableTreeNode addAnalyseScheme(CommAnalyseScheme analyseScheme) {
        if (UserContext.person_code.equals(analyseScheme.getUser_code())) {
            return addAnalyseScheme(myNode, analyseScheme, 0);
        } else {
            return addAnalyseScheme(shareNode, analyseScheme, 1);
        }
    }

    private DefaultMutableTreeNode addAnalyseScheme(DefaultMutableTreeNode parent_node, CommAnalyseScheme analyseScheme, int share) {
        String s_group = analyseScheme.getGroup_name();
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(analyseScheme);
        if (s_group != null && !s_group.equals("")) {
            s_group = share + "_" + s_group;
            DefaultMutableTreeNode class_node = class_table.get(s_group);
            if (class_node == null) {
                class_node = new DefaultMutableTreeNode(analyseScheme.getGroup_name());
                class_table.put(s_group, class_node);
                parent_node.add(class_node);
            }
            class_node.add(node);
        } else {
            parent_node.add(node);
        }
        return node;
    }

    public void buildTree(List scheme_list) {
        myNode.removeAllChildren();
        shareNode.removeAllChildren();
        class_table.clear();
        for (Object obj : scheme_list) {
            addAnalyseScheme((CommAnalyseScheme) obj);
        }
    }
}
