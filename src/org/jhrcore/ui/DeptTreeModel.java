/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.DefaultTreeModel;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.DeptCode;

/**
 *
 * @author DB2INST3
 */
public class DeptTreeModel extends DefaultTreeModel {

    private static final long serialVersionUID = 1L;
    private CheckTreeNode rootNode = new CheckTreeNode("所有部门");
    private String sql = "";
    private List list;
    private boolean show_g10_flag = UserContext.show_g10_flag;

    public List getList() {
        return list;
    }

    public DeptTreeModel(List<DeptCode> depts) {
        super(new CheckTreeNode());
        this.setRoot(rootNode);
        list = depts;
        buildTree(list);
    }

    public DeptTreeModel(List<DeptCode> depts, boolean show_g10_flag) {
        super(new CheckTreeNode());
        this.setRoot(rootNode);
        list = depts;
        this.show_g10_flag = show_g10_flag;
        buildTree(list);
    }

    public DeptTreeModel(String hql) {
        super(new CheckTreeNode());
        this.setRoot(rootNode);
        if (!hql.equals("")) {
            sql = hql;
            buildTree(sql);
        }

    }

    public void rebuild() {
        Enumeration enumt = rootNode.breadthFirstEnumeration();
        if (show_g10_flag) {
            
        } else {
            List<CheckTreeNode> remove_nodes = new ArrayList<CheckTreeNode>();
            while (enumt.hasMoreElements()) {
                CheckTreeNode cur = (CheckTreeNode) enumt.nextElement();
                Object obj = cur.getUserObject();
            }
            for (CheckTreeNode node : remove_nodes) {
                node.removeFromParent();
            }
        }
    }

    public void setShow_g10_flag(boolean show_g10_flag) {
        this.show_g10_flag = show_g10_flag;
    }

    public void buildTree(List list) {
        rootNode.removeAllChildren();
        CheckTreeNode tmp = rootNode;
        if (show_g10_flag) {
            for (Object obj : list) {
                
            }
        } else {
            for (Object obj : list) {
                DeptCode dept = (DeptCode) obj;
                while (tmp != rootNode && !((DeptCode) tmp.getUserObject()).getDept_code().equals(
                        dept.getParent_code())) {
                    tmp = (CheckTreeNode) tmp.getParent();
                }
                CheckTreeNode cur = new CheckTreeNode(dept);
                tmp.add(cur);
                tmp = cur;
            }
        }

    }

    public void buildTree(List list, List rightList) {
        rootNode.removeAllChildren();
        CheckTreeNode tmp = rootNode;
        for (Object obj : list) {
                DeptCode dept = (DeptCode) obj;
                while (tmp != rootNode && !((DeptCode) tmp.getUserObject()).getDept_code().equals(
                        dept.getParent_code())) {
                    tmp = (CheckTreeNode) tmp.getParent();
                }
                CheckTreeNode cur = new CheckTreeNode(dept);
                tmp.add(cur);
                tmp = cur;
            }
    }

    public CheckTreeNode getNodeByDept(DeptCode dept) {
        CheckTreeNode resultNode = null;
        Enumeration deptEnum = rootNode.depthFirstEnumeration();
        String val = dept.getDeptCode_key();
        while (deptEnum.hasMoreElements()) {
            CheckTreeNode tmpNode = (CheckTreeNode) deptEnum.nextElement();
            if (tmpNode.getUserObject() instanceof DeptCode) {
                DeptCode dept1 = (DeptCode) tmpNode.getUserObject();
                String field_val = dept1.getDeptCode_key();
                if (val.equals(field_val)) {
                    resultNode = tmpNode;
                    break;
                }
            }
        }
        return resultNode;
    }

    public List<DeptCode> getSortDepts() {
        return getSortDepts(rootNode);
    }

    private List<DeptCode> getSortDepts(CheckTreeNode tmp) {
        List<DeptCode> list = new ArrayList();
        Enumeration e = tmp.children();
        while (e.hasMoreElements()) {
            CheckTreeNode node = (CheckTreeNode) e.nextElement();
            if (node.getUserObject() instanceof DeptCode) {
                list.add((DeptCode) node.getUserObject());
            }
            if (node.isLeaf()) {
                continue;
            } else {
                list.addAll(getSortDepts(node));
            }
        }
        return list;
    }

    public void buildTree(String tmpHql) {
        list = CommUtil.fetchEntities(tmpHql);
        buildTree(list);
    }
}
