/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import java.util.List;
import javax.swing.tree.DefaultTreeModel;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.entity.CommClass;

/**
 *
 * @author mxliteboss
 */
public class CommClassTreeModel extends DefaultTreeModel {

    private String module_code;
    private String module_name;
    private CheckTreeNode rootNode = new CheckTreeNode("所有分类");

    public CommClassTreeModel(String module_code, String module_name) {
        super(new CheckTreeNode());
        this.module_code = module_code;
        this.module_name = module_name;
        this.setRoot(rootNode);
        buildTree();
    }

    public final void buildTree() {
        List list = CommUtil.fetchEntities("from CommClass mg where mg.class_code='" + module_code + "'  order by mg.code");
        CommClass root_mg = null;
        for (Object obj : list) {
            CommClass edc = (CommClass) obj;
            if (edc.getParent_code().equals("ROOT")) {
                root_mg = edc;
                break;
            }
        }
        if (root_mg == null) {
            root_mg = new CommClass();
            root_mg.setCommClass_key(module_code + ".ROOT");
            root_mg.setParent_code("ROOT");
            root_mg.setCode("1");
            root_mg.setClass_code(module_code);
            root_mg.setClass_name(module_name);
            CommUtil.saveOrUpdate(root_mg);
        } else {
            list.remove(root_mg);
        }
        rootNode.setUserObject(root_mg);
        rootNode.removeAllChildren();
        CheckTreeNode tmp = rootNode;
        for (Object obj : list) {
            CommClass mg = (CommClass) obj;
            while (tmp != rootNode && !((CommClass) tmp.getUserObject()).getCode().equals(
                    mg.getParent_code())) {
                tmp = (CheckTreeNode) tmp.getParent();
            }
            CheckTreeNode cur = new CheckTreeNode(mg);
            tmp.add(cur);
            tmp = cur;
        }
    }

    public String getNewCode(String module_code, String parent_code) {
        String tmp = null;
        if (parent_code == null || parent_code.equalsIgnoreCase("ROOT")) {
            tmp = (String) CommUtil.fetchEntityBy("select max(code) from CommClass where class_code='" + module_code + "'");
        } else {
            tmp = (String) CommUtil.fetchEntityBy("select max(code) from CommClass r where class_code='" + module_code + "' and r.parent_code='" + parent_code + "'");
        }
        return SysUtil.getNewFuntionCode(parent_code, tmp);
    }
}
