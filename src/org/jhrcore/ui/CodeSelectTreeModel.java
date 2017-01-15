/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import java.util.List;
import javax.swing.tree.DefaultTreeModel;
import org.jhrcore.entity.Code;

/**
 *
 * @author mxliteboss
 */
public class CodeSelectTreeModel extends DefaultTreeModel {

    private static final long serialVersionUID = 1L;
    private CheckTreeNode rootNode = null;

    public CodeSelectTreeModel(List codes, String parent_name) {
        super(new CheckTreeNode());
        Code c = new Code();
        c.setCode_name(parent_name);
        c.setCode_id("");
        rootNode = new CheckTreeNode(c);
        this.setRoot(rootNode);
        buildTree(codes);
    }

    private void buildTree(List codes) {
        rootNode.removeAllChildren();
        CheckTreeNode cur_node = rootNode;
        for (int i = 0; i < codes.size(); i++) {
            Code code = (Code) codes.get(i);
            if (code.isHide_flag()) {
                continue;
            }
            CheckTreeNode tn2 = new CheckTreeNode(code);
            while (true) {
                if (cur_node == rootNode) {
                    break;
                }
                Object obj = cur_node.getUserObject();
                if (obj instanceof Code) {
                    Code parent_code = (Code) cur_node.getUserObject();
                    if (code.getParent_id().equals(parent_code.getCode_id())) {
                        break;
                    }

                    cur_node = (CheckTreeNode) cur_node.getParent();
                } else {
                    break;
                }
            }
            cur_node.add(tn2);
            cur_node = tn2;
        }
    }
}
