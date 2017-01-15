/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import java.util.List;
import javax.swing.tree.DefaultTreeModel;
import org.jhrcore.client.CommUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.right.FuntionRight;
import org.jhrcore.entity.right.Role;

/**
 *
 * @author yangzhou
 */
public class FuntionTreeModel extends DefaultTreeModel {

    private CheckTreeNode rootNode;

    public final CheckTreeNode getRootNode() {
        return new CheckTreeNode(CommUtil.fetchEntityBy("from FuntionRight fr where fr.fun_parent_code='ROOT'"));
    }

    public FuntionTreeModel() {
        this(UserContext.funtion_list);
    }

    public FuntionTreeModel(List list) {
        super(new CheckTreeNode());
        buildTree(list);
    }

    public FuntionTreeModel(Role role) {
        super(new CheckTreeNode());
        rootNode = getRootNode();
        this.setRoot(rootNode);
        buildTree(role);
    }

    private void buildTree(Role role) {
        List<String> set_fun = null;
        if(role!=null){
            set_fun =  (List<String>) CommUtil.selectSQL("select rf.funtionright_key from RoleFuntion rf where rf.role_key = '" + role.getRole_key() + "'");
        }
        CheckTreeNode tmp = rootNode;
        for (Object obj : UserContext.funtion_list) {
            FuntionRight funtionRight = (FuntionRight) obj;
            if (funtionRight.getFun_parent_code().equals("ROOT")) {
                continue;
            }
            if (set_fun!=null&&!set_fun.contains(funtionRight.getFuntionRight_key())) {
                continue;
            }
            while (tmp != rootNode && !funtionRight.getFun_parent_code().equals(((FuntionRight) tmp.getUserObject()).getFun_code())) {
                tmp = (CheckTreeNode) tmp.getParent();
            }
            CheckTreeNode cc = new CheckTreeNode(funtionRight);
            tmp.add(cc);
            tmp = cc;
        }
    }

    private void buildTree(List list_all) {
        Object root_obj = null;
        for (Object obj : list_all) {
            FuntionRight r = (FuntionRight) obj;
            if (r.getFun_parent_code().equals("ROOT")) {
                root_obj = r;
                break;
            }
        }
        rootNode = new CheckTreeNode(root_obj);
        this.setRoot(rootNode);
        CheckTreeNode tmp = rootNode;
        for (Object obj : list_all) {
            FuntionRight funtionRight = (FuntionRight) obj;
            if (funtionRight.getFun_parent_code().equals("ROOT")) {
                continue;
            }
            if (!UserContext.hasFunctionRight(funtionRight.getFun_module_flag())) {
                continue;
            }
            while (tmp != rootNode && !funtionRight.getFun_parent_code().equals(((FuntionRight) tmp.getUserObject()).getFun_code())) {
                tmp = (CheckTreeNode) tmp.getParent();
            }
            CheckTreeNode cc = new CheckTreeNode(funtionRight);
            tmp.add(cc);
            tmp = cc;
        }
    }
}
