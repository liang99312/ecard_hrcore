/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.queryanalysis;

import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.jhrcore.entity.Code;
import org.jhrcore.entity.query.CommAnalyseField;

/**
 *
 * @author mxliteboss
 */
public class AnalyseParaTreeModel extends DefaultTreeModel {

    private static final long serialVersionUID = 1L;
    private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Ыљга");
    private CommAnalyseField paf;

    public CommAnalyseField getPaf() {
        return paf;
    }

    public AnalyseParaTreeModel(Object root_obj, List data, CommAnalyseField paf) {
        super(new DefaultMutableTreeNode());
        this.paf = paf;
        this.setRoot(rootNode);
        buildTree(root_obj, data);
    }

    public void buildTree(Object root_obj, List list) {
        rootNode.removeAllChildren();
        if (root_obj == null) {
            return;
        }
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(root_obj);
        rootNode.add(node);
//        if(list.isEmpty())
//            return;
        if (list.get(0) instanceof Code) {
            DefaultMutableTreeNode tmp = node;
            for (Object obj : list) {
                Code code = (Code) obj;
                while (tmp != node && !code.getParent_id().toUpperCase().equals(((Code) tmp.getUserObject()).getCode_id().toUpperCase()) && code.getCode_tag().toUpperCase().equals((((Code) tmp.getUserObject()).getCode_tag().substring(0, 2).toUpperCase() + code.getCode_id().toUpperCase()))) {
                    tmp = (DefaultMutableTreeNode) tmp.getParent();
                }
                DefaultMutableTreeNode cur = new DefaultMutableTreeNode(code);
                tmp.add(cur);
                tmp = cur;
            }
        } else {
            for (Object obj : list) {
                node.add(new DefaultMutableTreeNode(obj));
            }
        }
    }
}
