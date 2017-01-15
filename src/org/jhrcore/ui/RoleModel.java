/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import javax.swing.tree.DefaultTreeModel;
import org.jhrcore.client.CommUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.right.Role;
import org.jhrcore.mutil.RightUtil;

/**
 *
 * @author yangzhou
 */
public class RoleModel extends DefaultTreeModel {

    private CheckTreeNode rootNode = new CheckTreeNode("È«²¿½ÇÉ«");
    private Hashtable<String, Role> roleKeys = new Hashtable<String, Role>();

    public RoleModel() {
        super(new CheckTreeNode());
        this.setRoot(rootNode);
        buildTree(RightUtil.getUserRoles());

    }

    public RoleModel(List list) {
        super(new CheckTreeNode());
        this.setRoot(rootNode);
        buildTree(list);
    }

    private void buildTree(List listRole) {
        roleKeys.clear();
        rootNode.removeAllChildren();
        CheckTreeNode tmp = rootNode;
        for (Object obj : listRole) {
            Role role = (Role) obj;
            roleKeys.put(role.getRole_key(), role);
            while (tmp != rootNode && !((Role) tmp.getUserObject()).getRole_code().equals(
                    role.getParent_code())) {
                tmp = (CheckTreeNode) tmp.getParent();
            }
            CheckTreeNode cur = new CheckTreeNode(role);
            tmp.add(cur);
            tmp = cur;
        }
    }

    public void fresh() {
        this.rootNode.removeAllChildren();
        List listRole = new ArrayList();
        if (!UserContext.isSA) {
            listRole.addAll(CommUtil.fetchEntities("from Role r where " + UserContext.role_right_str + " order by r.role_code"));
        } else {
            listRole.addAll(CommUtil.fetchEntities("from Role r order by r.role_code"));
        }
        buildTree(listRole);
    }

    public CheckTreeNode getNodeByRole(Role role) {
        CheckTreeNode node = null;
        Enumeration enumt = rootNode.breadthFirstEnumeration();
        while (enumt.hasMoreElements()) {
            CheckTreeNode child_node = (CheckTreeNode) enumt.nextElement();
            Object obj = child_node.getUserObject();
            if (obj instanceof Role) {
                if (((Role) obj).getRole_key().equals(role.getRole_key())) {
                    node = child_node;
                    break;
                }
            }
        }
        return node;
    }
}
