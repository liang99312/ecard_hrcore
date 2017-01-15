/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.mutil;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import org.apache.log4j.Logger;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.base.EntityDef;
import org.jhrcore.entity.base.FieldDef;
import org.jhrcore.entity.base.ModuleInfo;
import org.jhrcore.entity.report.ReportDef;
import org.jhrcore.entity.right.FuntionRight;
import org.jhrcore.entity.right.Role;
import org.jhrcore.entity.right.RoleA01;
import org.jhrcore.entity.right.RoleCode;
import org.jhrcore.entity.right.RoleEntity;
import org.jhrcore.entity.right.RoleField;
import org.jhrcore.entity.right.RoleFuntion;
import org.jhrcore.entity.right.RoleReport;
import org.jhrcore.entity.right.RoleRightTemp;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.impl.RightImpl;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.ui.CopyCurRightDialog;
import org.jhrcore.util.MsgUtil;

/**
 *
 * @author hflj
 */
public class RightUtil {

    private static Logger log = Logger.getLogger(RightUtil.class.getName());

    public static boolean giveFuntionRight(List<Role> sel_roles, List sel_nodes, int mod, Hashtable<String, Hashtable<String, RoleFuntion>> rf_keys, DefaultMutableTreeNode rootNode) {
        String roleKeys = "";
        Hashtable<String, Role> roles = new Hashtable<String, Role>();
        for (Role r : sel_roles) {
            if (rf_keys.get(r.getRole_key()) == null) {
                roleKeys += ",'" + r.getRole_key() + "'";
                rf_keys.put(r.getRole_key(), new Hashtable<String, RoleFuntion>());
            }
            roles.put(r.getRole_key(), r);
        }
        List<FuntionRight> funs = new ArrayList<FuntionRight>();
        Hashtable<String, DefaultMutableTreeNode> nodeKeys = new Hashtable<String, DefaultMutableTreeNode>();
        for (Object obj : sel_nodes) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) obj;
            FuntionRight fr = (FuntionRight) node.getUserObject();
            funs.add(fr);
            nodeKeys.put(fr.getFun_code(), node);
        }
        SysUtil.sortListByStr(funs, "fun_code");
        List<DefaultMutableTreeNode> nodes = new ArrayList<DefaultMutableTreeNode>();
        List<String> fun_codes = new ArrayList<String>();
        for (FuntionRight fr : funs) {
            boolean haschild = false;
            for (String str : fun_codes) {
                if (fr.getFun_code().startsWith(str)) {
                    haschild = true;
                    break;
                }
            }
            if (haschild) {
                continue;
            }
            fun_codes.add(fr.getFun_code());
        }
        List<String> fp_codes = new ArrayList<String>();
        for (String fun_code : fun_codes) {
            DefaultMutableTreeNode node = nodeKeys.get(fun_code);
            nodes.add(node);
            FuntionRight fr = (FuntionRight) node.getUserObject();
            if (fp_codes.contains(fr.getFun_parent_code())) {
                continue;
            }
            fp_codes.add(fr.getFun_parent_code());
        }
        if (!roleKeys.equals("")) {
            roleKeys = roleKeys.substring(1);
            String sql = "select rf.role_key,rf.funtionright_key,rf.fun_flag from RoleFuntion rf,funtionright fr where rf.funtionright_key=fr.funtionright_key and fr.granted=1 and role_key in(" + roleKeys + ") and (1=0";
            String db_type = UserContext.sql_dialect;
            if (db_type.equals("sqlserver")) {
                for (String fp_code : fp_codes) {
                    sql += " or fr.fun_code like '" + fp_code + "%' or charindex(fun_code,'" + fp_code + "')=1";
                }
            } else if (db_type.equals("oracle")) {
                for (String fp_code : fp_codes) {
                    sql += " or fr.fun_code like '" + fp_code + "%' or instr('" + fp_code + "',fun_code)=1";
                }
            } else {
                for (String fp_code : fp_codes) {
                    sql += " or fr.fun_code like '" + fp_code + "%' or LOCATE(fun_code,'" + fp_code + "')=1";
                }
            }
            List rights = CommUtil.selectSQL(sql + ")");
            for (Object obj : rights) {
                Object[] objs = (Object[]) obj;
                Hashtable<String, RoleFuntion> rfs = rf_keys.get(objs[0].toString());
                rf_keys.put(objs[0].toString(), rfs);
                RoleFuntion rf = new RoleFuntion();
                rf.setRole(roles.get(objs[0].toString()));
                FuntionRight fr = UserContext.getFunByKey(objs[1].toString());
                if (fr == null) {
                    JOptionPane.showMessageDialog(null, "授权菜单不存在，请重新登录后尝试");
                    return false;
                }
                rf.setFun_flag(Integer.valueOf(objs[2].toString()));
                rf.setFuntionRight(fr);
                rf.setFun_flag(Integer.valueOf(objs[2].toString()));
                rfs.put(objs[1].toString(), rf);
            }
        }
        List<String[]> data = new ArrayList<String[]>();
        for (Role r : sel_roles) {
            Hashtable<String, RoleFuntion> rfs = rf_keys.get(r.getRole_key());
            initFunTree(rfs, rootNode);
            for (DefaultMutableTreeNode node : nodes) {
                Enumeration enumt = node.breadthFirstEnumeration();
                while (enumt.hasMoreElements()) {
                    DefaultMutableTreeNode tmpNode = (DefaultMutableTreeNode) enumt.nextElement();
                    FuntionRight fr = (FuntionRight) tmpNode.getUserObject();
                    fr.setFun_flag(mod);
                    String funKey = fr.getFuntionRight_key();
                    RoleFuntion tmpRf = rfs.get(fr.getFuntionRight_key());
                    if (tmpRf == null) {
                        tmpRf = new RoleFuntion();
                        data.add(new String[]{"'" + r.getRole_key() + "'", "'" + funKey + "'", mod + ""});
                    } else if (tmpRf.getFun_flag() != mod) {
                        tmpRf.setFun_flag(mod);
                        data.add(new String[]{"'" + r.getRole_key() + "'", "'" + funKey + "'", mod + ""});
                    }
                    tmpRf.setRole(r);
                    tmpRf.setFuntionRight(fr);
                    tmpRf.setFun_flag(fr.getFun_flag());
                    rfs.put(funKey, tmpRf);
                }
                //更新父节点
                DefaultMutableTreeNode tmpParent_node = node;
                while (tmpParent_node != rootNode) {
                    tmpParent_node = (DefaultMutableTreeNode) tmpParent_node.getParent();
                    FuntionRight tmpParent_right = (FuntionRight) tmpParent_node.getUserObject();
                    tmpParent_right.setFun_flag(mod);
                    Enumeration enum1 = tmpParent_node.children();
                    while (enum1.hasMoreElements()) {
                        DefaultMutableTreeNode enum1Node = (DefaultMutableTreeNode) enum1.nextElement();
                        FuntionRight enum1FuntionRight = (FuntionRight) enum1Node.getUserObject();
                        if (enum1FuntionRight.getFun_flag() != mod) {
                            tmpParent_right.setFun_flag(2);
                            break;
                        }
                    }
                    String funFlag = tmpParent_right.getFun_flag() + "";
                    String funKey = tmpParent_right.getFuntionRight_key();
                    RoleFuntion tmpRf = rfs.get(funKey);
                    if (tmpRf == null) {
                        tmpRf = new RoleFuntion();
                        data.add(new String[]{"'" + r.getRole_key() + "'", "'" + funKey + "'", funFlag});
                    } else if (tmpRf.getFun_flag() != tmpParent_right.getFun_flag()) {
                        data.add(new String[]{"'" + r.getRole_key() + "'", "'" + funKey + "'", funFlag});
                    }
                    tmpRf.setRole(r);
                    tmpRf.setFuntionRight(tmpParent_right);
                    tmpRf.setFun_flag(tmpParent_right.getFun_flag());
                    rfs.put(funKey, tmpRf);
                }
            }
        }
        if (data.isEmpty()) {
            return false;
        }
        ValidateSQLResult result = RightImpl.defineFuntionRight(data);
        if (result.getResult() == 0) {
            return true;
        } else {
            MsgUtil.showHRSaveErrorMsg(result);
        }
        return false;
    }

    public static List<DefaultMutableTreeNode> initFunTree(Hashtable<String, RoleFuntion> rfs, DefaultMutableTreeNode rootNode) {
        List<DefaultMutableTreeNode> parent_nodes = new ArrayList<DefaultMutableTreeNode>();
        Enumeration enumt = rootNode.breadthFirstEnumeration();
        while (enumt.hasMoreElements()) {
            DefaultMutableTreeNode cnode = (DefaultMutableTreeNode) enumt.nextElement();
            if (cnode.getUserObject() instanceof FuntionRight) {
                FuntionRight fr = (FuntionRight) cnode.getUserObject();
                fr.setFun_flag(0);
                RoleFuntion rf = rfs.get(fr.getFuntionRight_key());
                if (rf != null) {
                    fr.setFun_flag(rf.getFun_flag());
                    if (fr.getFun_flag() == 1) {
                        parent_nodes.add(cnode);
                    }
                }
            }
        }
        return parent_nodes;
    }

    public static void refreshParentFuntion(List<DefaultMutableTreeNode> parent_nodes, DefaultMutableTreeNode rootNode) {
        for (DefaultMutableTreeNode node : parent_nodes) {
            FuntionRight parent_fr = (FuntionRight) node.getUserObject();
            Enumeration enumt = node.children();
            int fun_flag = parent_fr.getFun_flag();
            if (node.getChildCount() > 0) {
                fun_flag = ((FuntionRight) ((DefaultMutableTreeNode) node.getFirstChild()).getUserObject()).getFun_flag();
            }
            while (enumt.hasMoreElements()) {
                DefaultMutableTreeNode child_node = (DefaultMutableTreeNode) enumt.nextElement();
                FuntionRight fr = (FuntionRight) child_node.getUserObject();
                if (fr.getFun_flag() != fun_flag) {
                    fun_flag = 2;
                    break;
                }
            }
            parent_fr.setFun_flag(fun_flag);
            node.setUserObject(parent_fr);
            while (node != rootNode) {
                fun_flag = ((FuntionRight) node.getUserObject()).getFun_flag();
                node = (DefaultMutableTreeNode) node.getParent();
                FuntionRight parent_fr1 = (FuntionRight) node.getUserObject();
                Enumeration enumt1 = node.children();
                while (enumt1.hasMoreElements()) {
                    DefaultMutableTreeNode child_node = (DefaultMutableTreeNode) enumt1.nextElement();
                    FuntionRight fr = (FuntionRight) child_node.getUserObject();
                    if (fr.getFun_flag() != fun_flag) {
                        fun_flag = 2;
                    }
                }
                parent_fr1.setFun_flag(fun_flag);
                node.setUserObject(parent_fr1);
            }
        }
    }

    public static boolean giveFieldRight(List<Role> sel_roles, List sel_nodes, int mod, Hashtable<String, Hashtable<String, RoleField>> rf_keys, DefaultMutableTreeNode rootNode1) {
        String roleKeys = "";
        String pCode = "";
        boolean isRoot = false;
        Hashtable<String, Role> roles = new Hashtable<String, Role>();
        for (Role r : sel_roles) {
            if (rf_keys.get(r.getRole_key()) == null) {
                roleKeys += ",'" + r.getRole_key() + "'";
                rf_keys.put(r.getRole_key(), new Hashtable<String, RoleField>());
            } else {
                pCode = r.getParent_code();
            }
            roles.put(r.getRole_key(), r);
        }
        List v_fields = null;
        if (pCode.trim().equals("") || pCode.trim().equalsIgnoreCase("ROOT")) {
            v_fields = new ArrayList();
        } else if (mod == 1) {
            v_fields = CommUtil.selectSQL("select rf.field_name from rolefield rf,role r where rf.role_key=r.role_key and r.role_code='" + pCode + "' and rf.fun_flag=2");
        }

        List<String> entitys = new ArrayList<String>();
        for (Object obj : sel_nodes) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) obj;
            if (node == rootNode1) {
                isRoot = true;
            }
            Object userObj = node.getUserObject();
            if (userObj instanceof FieldDef) {
                FieldDef fd = (FieldDef) userObj;
                if (!entitys.contains(fd.getEntityDef().getEntityName())) {
                    entitys.add(fd.getEntityDef().getEntityName());
                }
            } else {
                Enumeration enumt = node.breadthFirstEnumeration();
                while (enumt.hasMoreElements()) {
                    DefaultMutableTreeNode cnode = (DefaultMutableTreeNode) enumt.nextElement();
                    if (cnode.getUserObject() instanceof FieldDef) {
                        FieldDef fd = (FieldDef) cnode.getUserObject();
                        if (!entitys.contains(fd.getEntityDef().getEntityName())) {
                            entitys.add(fd.getEntityDef().getEntityName());
                        }
                    }
                }
            }
        }
        if (!roleKeys.equals("") && mod > 0) {
            roleKeys = roleKeys.substring(1);
            String sql = "select rf.role_key,rf.field_name,rf.fun_flag from RoleField rf where  role_key in(" + roleKeys + ") and (1=0";
            if (isRoot) {
                sql += " or 1=1";
            } else {
                for (String entity : entitys) {
                    sql += " or rf.field_name like '" + entity + ".%'";
                }
            }
            List rights = CommUtil.selectSQL(sql + ")");
            for (Object obj : rights) {
                Object[] objs = (Object[]) obj;
                Hashtable<String, RoleField> rfs = rf_keys.get(objs[0].toString());
                rf_keys.put(objs[0].toString(), rfs);
                RoleField rf = new RoleField();
                rf.setRole(roles.get(objs[0].toString()));
                rf.setField_name(objs[1].toString());
                rf.setFun_flag(Integer.valueOf(objs[2].toString()));
                rfs.put(objs[1].toString(), rf);
            }
        }
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) rootNode1.clone();
        List<String[]> data = new ArrayList<String[]>();
        for (Role r : sel_roles) {
            Hashtable<String, RoleField> rfs = rf_keys.get(r.getRole_key());
            initFieldTree(rfs, rootNode);
            for (Object obj : sel_nodes) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) obj;
                Enumeration enumt = node.breadthFirstEnumeration();
                while (enumt.hasMoreElements()) {
                    DefaultMutableTreeNode tmpNode = (DefaultMutableTreeNode) enumt.nextElement();
                    Object userObj = tmpNode.getUserObject();
                    if (userObj instanceof FieldDef) {
                        FieldDef fd = (FieldDef) userObj;
                        String f_name = fd.getEntityDef().getEntityName() + "." + fd.getField_name();
                        int fun_flag = mod;
                        if (fun_flag == 1 && v_fields.contains(f_name)) {
                            fun_flag = 2;
                        }
                        RoleField tmpRf = rfs.get(f_name);
                        if (tmpRf == null) {
                            tmpRf = new RoleField();
                            tmpRf.setField_name(f_name);
                            tmpRf.setRole(r);
                            data.add(new String[]{"'" + r.getRole_key() + "'", "'" + f_name + "'", fun_flag + ""});
                        } else if (tmpRf.getFun_flag() != fun_flag) {
                            data.add(new String[]{"'" + r.getRole_key() + "'", "'" + f_name + "'", fun_flag + ""});
                        }
                        tmpRf.setFun_flag(fun_flag);
                        rfs.put(f_name, tmpRf);
                    }
                }
            }
        }
        if (data.isEmpty()) {
            return false;
        }
        ValidateSQLResult result = RightImpl.defineFieldRight(data);
        if (result.getResult() == 0) {
            return true;
        } else {
            MsgUtil.showHRSaveErrorMsg(result);
        }
        return false;
    }

    public static Set<DefaultMutableTreeNode> initFieldTree(Hashtable<String, RoleField> rfs, DefaultMutableTreeNode rootNode) {
        Set<DefaultMutableTreeNode> parent_nodes = new HashSet<DefaultMutableTreeNode>();
        Enumeration enumt = rootNode.breadthFirstEnumeration();
        while (enumt.hasMoreElements()) {
            DefaultMutableTreeNode cNode = (DefaultMutableTreeNode) enumt.nextElement();
            Object obj = cNode.getUserObject();
            if (obj instanceof FieldDef) {
                FieldDef fd = (FieldDef) obj;
                fd.setFun_flag(0);
                RoleField rf = rfs.get(fd.getEntityDef().getEntityName() + "." + fd.getField_name());
                if (rf != null) {
                    fd.setFun_flag(rf.getFun_flag());
                    DefaultMutableTreeNode pNode = (DefaultMutableTreeNode) cNode.getParent();
                    parent_nodes.add(pNode);
                }
            } else if (obj instanceof EntityDef) {
                ((EntityDef) obj).setFun_flag(0);
            } else if (obj instanceof ModuleInfo) {
                ((ModuleInfo) obj).setFun_flag(0);
            } else if (obj instanceof RoleRightTemp) {
                ((RoleRightTemp) obj).setFun_flag(0);
            }
        }
        return parent_nodes;
    }

    public static boolean giveReportRight(List<Role> sel_roles, List sel_nodes, int mod, Hashtable<String, Hashtable<String, RoleReport>> rf_keys, DefaultMutableTreeNode rootNode) {
        String roleKeys = "";
        String pCode = "";
        boolean isRoot = false;
        Hashtable<String, Role> roles = new Hashtable<String, Role>();
        for (Role r : sel_roles) {
            if (rf_keys.get(r.getRole_key()) == null) {
                roleKeys += ",'" + r.getRole_key() + "'";
                rf_keys.put(r.getRole_key(), new Hashtable<String, RoleReport>());
            } else {
                pCode = r.getParent_code();
            }
            roles.put(r.getRole_key(), r);
        }
        List v_fields = null;
        if (pCode.trim().equals("") || pCode.trim().equalsIgnoreCase("ROOT")) {
            v_fields = new ArrayList();
        } else if (mod == 1) {
            v_fields = CommUtil.selectSQL("select rf.reportDef_key from RoleReport rf,role r where rf.role_key=r.role_key and r.role_code='" + pCode + "' and rf.fun_flag=2");
        }
        Hashtable<String, ReportDef> reportKeys = new Hashtable<String, ReportDef>();
        for (Object obj : sel_nodes) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) obj;
            if (node == rootNode) {
                isRoot = true;
            }
            Object userObj = node.getUserObject();
            if (userObj instanceof ReportDef) {
                ReportDef rd = (ReportDef) userObj;
                reportKeys.put(rd.getReportDef_key(), rd);
            } else {
                Enumeration enumt = node.breadthFirstEnumeration();
                while (enumt.hasMoreElements()) {
                    DefaultMutableTreeNode cnode = (DefaultMutableTreeNode) enumt.nextElement();
                    if (cnode.getUserObject() instanceof ReportDef) {
                        ReportDef rd = (ReportDef) cnode.getUserObject();
                        reportKeys.put(rd.getReportDef_key(), rd);
                    }
                }
            }
        }
        if (reportKeys.isEmpty()) {
            return false;
        }
        if (!roleKeys.equals("") && mod > 0) {
            roleKeys = roleKeys.substring(1);
            String sql = "select rf.role_key,rf.reportDef_key,rf.fun_flag from RoleReport rf where  role_key in(" + roleKeys + ") ";
            if (isRoot) {
                sql += " and 1=1";
            } else {
                String key = "";
                for (String entity : reportKeys.keySet()) {
                    key += ",'" + entity + "'";
                }
                sql += " and rf.reportDef_key in(" + key.substring(1) + ")";
            }
            List rights = CommUtil.selectSQL(sql);
            for (Object obj : rights) {
                Object[] objs = (Object[]) obj;
                ReportDef rd = reportKeys.get(objs[1].toString());
                if (rd == null) {
                    continue;
                }
                Hashtable<String, RoleReport> rfs = rf_keys.get(objs[0].toString());
                rf_keys.put(objs[0].toString(), rfs);
                RoleReport rf = new RoleReport();
                rf.setRole(roles.get(objs[0].toString()));
                rf.setReportDef(rd);
                rf.setFun_flag(Integer.valueOf(objs[2].toString()));
                rfs.put(objs[1].toString(), rf);
            }
        }
        List<String[]> data = new ArrayList<String[]>();
        for (Role r : sel_roles) {
            Hashtable<String, RoleReport> rfs = rf_keys.get(r.getRole_key());
            initReportTree(rfs, rootNode);
            for (Object obj : sel_nodes) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) obj;
                Enumeration enumt = node.breadthFirstEnumeration();
                while (enumt.hasMoreElements()) {
                    DefaultMutableTreeNode tmpNode = (DefaultMutableTreeNode) enumt.nextElement();
                    Object userObj = tmpNode.getUserObject();
                    if (userObj instanceof ReportDef) {
                        ReportDef fd = (ReportDef) userObj;
                        String f_name = fd.getReportDef_key();
                        int fun_flag = mod;
                        if (fun_flag == 1 && v_fields.contains(f_name)) {
                            fun_flag = 2;
                        }
                        RoleReport tmpRf = rfs.get(f_name);
                        if (tmpRf == null) {
                            tmpRf = new RoleReport();
                            tmpRf.setReportDef(fd);
                            tmpRf.setRole(r);
                            data.add(new String[]{"'" + r.getRole_key() + "'", "'" + f_name + "'", fun_flag + ""});
                        } else if (tmpRf.getFun_flag() != fun_flag) {
                            data.add(new String[]{"'" + r.getRole_key() + "'", "'" + f_name + "'", fun_flag + ""});
                        }
                        tmpRf.setFun_flag(fun_flag);
                        rfs.put(f_name, tmpRf);
                    }
                }
            }
        }
        if (data.isEmpty()) {
            return false;
        }
        ValidateSQLResult result = RightImpl.defineReportRight(data);
        if (result.getResult() == 0) {
            return true;
        } else {
            MsgUtil.showHRSaveErrorMsg(result);
        }
        return false;
    }

    public static Set<DefaultMutableTreeNode> initReportTree(Hashtable<String, RoleReport> rfs, DefaultMutableTreeNode rootNode) {
        Set<DefaultMutableTreeNode> parent_nodes = new HashSet<DefaultMutableTreeNode>();
        Enumeration enumt = rootNode.breadthFirstEnumeration();
        while (enumt.hasMoreElements()) {
            DefaultMutableTreeNode cNode = (DefaultMutableTreeNode) enumt.nextElement();
            Object obj = cNode.getUserObject();
            if (obj instanceof ReportDef) {
                ReportDef fd = (ReportDef) obj;
                fd.setFun_flag(0);
                RoleReport rf = rfs.get(fd.getReportDef_key());
                if (rf != null) {
                    fd.setFun_flag(rf.getFun_flag());
                    DefaultMutableTreeNode pNode = (DefaultMutableTreeNode) cNode.getParent();
                    parent_nodes.add(pNode);
                }
            } else if (obj instanceof RoleRightTemp) {
                ((RoleRightTemp) obj).setFun_flag(0);
            }
//            list.add((Role) tmpNode.getUserObject());
        }
        return parent_nodes;
    }

    public static List getRole(DefaultMutableTreeNode roleNode, boolean child, int mod) {
        List<Role> roles = new ArrayList<Role>();
        List<Role> list = new ArrayList<Role>();
        roles.add((Role) roleNode.getUserObject());
        Enumeration enumt1 = roleNode.breadthFirstEnumeration();
        while (enumt1.hasMoreElements()) {
            DefaultMutableTreeNode tmpNode = (DefaultMutableTreeNode) enumt1.nextElement();
            if (tmpNode == roleNode) {
                continue;
            }
            list.add((Role) tmpNode.getUserObject());
        }
        if (mod == 0) {
            roles.addAll(list);
        } else if (child) {
            if (list.size() > 0) {
                CopyCurRightDialog copyDialog = new CopyCurRightDialog(list);
                ContextManager.locateOnMainScreenCenter(copyDialog);
                copyDialog.setVisible(true);
                if (copyDialog.isClick_ok()) {
                    roles.addAll(copyDialog.getSelect_roles());
                } else {
                    return null;
                }
            }
        }
        return roles;
    }

    public static void giveEntityRight(String p_role_key, DefaultMutableTreeNode roleNode, List<RoleEntity> rcs, int mod, boolean child) {
        Object cur_role = roleNode.getUserObject();
        if (!(cur_role instanceof Role)) {
            return;
        }
        List<String> roleKeys = new ArrayList<String>();
        roleKeys.add(((Role) cur_role).getRole_key());
        if (child && mod != 0) {
            List<Role> list = new ArrayList<Role>();
            Enumeration enumt1 = roleNode.breadthFirstEnumeration();
            while (enumt1.hasMoreElements()) {
                DefaultMutableTreeNode tmpNode = (DefaultMutableTreeNode) enumt1.nextElement();
                if (tmpNode == roleNode) {
                    continue;
                }
                list.add((Role) tmpNode.getUserObject());
            }
            if (list.size() > 0) {
                CopyCurRightDialog copyDialog = new CopyCurRightDialog(list);
                ContextManager.locateOnMainScreenCenter(copyDialog);
                copyDialog.setVisible(true);
                if (copyDialog.isClick_ok()) {
                    List<Role> s_roles = copyDialog.getSelect_roles();
                    for (Role r : s_roles) {
                        roleKeys.add(r.getRole_key());
                    }
                } else {
                    return;
                }
            }
        }
        ValidateSQLResult result = RightImpl.defineEntityRight(p_role_key, roleKeys, rcs, mod);
        if (result.getResult() == 0) {
            JOptionPane.showMessageDialog(null, "授权成功");
        } else {
            MsgUtil.showHRSaveErrorMsg(result);
        }
    }

    public static void giveCodeRight(String p_role_key, DefaultMutableTreeNode roleNode, List<RoleCode> rcs, int mod, boolean child) {
        Object cur_role = roleNode.getUserObject();
        if (!(cur_role instanceof Role)) {
            return;
        }
        List<String> roleKeys = new ArrayList<String>();
        roleKeys.add(((Role) cur_role).getRole_key());
        if (child && mod != 0) {
            List<Role> list = new ArrayList<Role>();
            Enumeration enumt1 = roleNode.breadthFirstEnumeration();
            while (enumt1.hasMoreElements()) {
                DefaultMutableTreeNode tmpNode = (DefaultMutableTreeNode) enumt1.nextElement();
                if (tmpNode == roleNode) {
                    continue;
                }
                list.add((Role) tmpNode.getUserObject());
            }
            if (list.size() > 0) {
                CopyCurRightDialog copyDialog = new CopyCurRightDialog(list);
                ContextManager.locateOnMainScreenCenter(copyDialog);
                copyDialog.setVisible(true);
                if (copyDialog.isClick_ok()) {
                    List<Role> s_roles = copyDialog.getSelect_roles();
                    for (Role r : s_roles) {
                        roleKeys.add(r.getRole_key());
                    }
                } else {
                    return;
                }
            }
        }
        ValidateSQLResult result = RightImpl.defineCodeRight(p_role_key, roleKeys, rcs, mod);
        if (result.getResult() == 0) {
            JOptionPane.showMessageDialog(null, "授权成功");
        } else {
            MsgUtil.showHRSaveErrorMsg(result);
        }
    }

    /**
     * 该方法用于根据指定部门LIST构建树
     * @param list：指定部门集合
     * @return：树的根节点
     */
    public static DefaultMutableTreeNode initDeptTree(List list) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("所有部门");
        for (Object obj : list) {
            Object[] objs = (Object[]) obj;
            if ("ROOT".equalsIgnoreCase(objs[1].toString())) {
                rootNode.setUserObject(getDept(objs));
                break;
            }
        }
        DefaultMutableTreeNode tmp = rootNode;
        for (Object obj : list) {
            DeptCode dept = getDept((Object[]) obj);
            while (tmp != rootNode && !((DeptCode) tmp.getUserObject()).getDept_code().equals(
                    dept.getParent_code())) {
                tmp = (DefaultMutableTreeNode) tmp.getParent();
            }
            DefaultMutableTreeNode cur = new DefaultMutableTreeNode(dept);
            tmp.add(cur);
            tmp = cur;
        }
        return rootNode;
    }

    /**
     * 该方法用于根据当前节点、根节点刷新整棵树授权状态
     * @param node：当前节点，必然为有权限
     * @param root：根节点
     */
    public static void refreshDeptNode(DefaultMutableTreeNode node, DefaultMutableTreeNode root) {
        int fun_flag = ((DeptCode) node.getUserObject()).getFun_flag();
        if (fun_flag != 2) {
            Enumeration enumt = node.breadthFirstEnumeration();
            while (enumt.hasMoreElements()) {
                DefaultMutableTreeNode no = (DefaultMutableTreeNode) enumt.nextElement();
                DeptCode dept = (DeptCode) no.getUserObject();
                dept.setFun_flag(fun_flag);
            }
        }
        while (node != root) {
            DefaultMutableTreeNode tmpParent_node = (DefaultMutableTreeNode) node.getParent();
            Object obj = tmpParent_node.getUserObject();
            if (!(obj instanceof DeptCode)) {
                break;
            }
            Enumeration enumt1 = tmpParent_node.children();
            DeptCode dept = (DeptCode) obj;
            dept.setFun_flag(fun_flag);
            while (enumt1.hasMoreElements()) {
                DefaultMutableTreeNode child_node = (DefaultMutableTreeNode) enumt1.nextElement();
                if (((DeptCode) child_node.getUserObject()).getFun_flag() != fun_flag) {
                    dept.setFun_flag(2);
                    break;
                }
            }
            node = tmpParent_node;
        }
    }

    /**
     * 该方法用于将指定数组型数据转换成DEPT，仅将数组前三列依次赋值dept_code,parent_code,deptcode_key三个字段
     * @param objs：指定数组型数据
     * @return：赋值后的DEPT
     */
    public static DeptCode getDept(Object[] objs) {
        DeptCode dc = new DeptCode();
        dc.setDept_code(objs[0].toString());
        dc.setParent_code(objs[1].toString());
        dc.setDeptCode_key(objs[2].toString());
        return dc;
    }

    /**
     * 该方法用于根据用户权限部门KEY集合来更新部门树状态
     * @param root：根节点
     * @param cur_role_dept：用户权限部门KEY集合
     */
    public static void refreshDeptRight(DefaultMutableTreeNode root, Set<String> cur_role_dept) {
        DeptCode dept;
        Set<DefaultMutableTreeNode> refreshNode = new HashSet<DefaultMutableTreeNode>();
        Enumeration enumt = root.breadthFirstEnumeration();
        if (cur_role_dept.size() > 0) {
            while (enumt.hasMoreElements()) {
                DefaultMutableTreeNode no = (DefaultMutableTreeNode) enumt.nextElement();
                Object obj = no.getUserObject();
                if (!(obj instanceof DeptCode)) {
                    continue;
                }
                dept = (DeptCode) no.getUserObject();
                if (cur_role_dept.contains(dept.getDeptCode_key())) {
                    dept.setFun_flag(1);
                    refreshNode.add(no);
                } else {
                    dept.setFun_flag(0);
                }
            }
        } else {
            while (enumt.hasMoreElements()) {
                DefaultMutableTreeNode no = (DefaultMutableTreeNode) enumt.nextElement();
                if (no == root) {
                    continue;
                }
                dept = (DeptCode) no.getUserObject();
                dept.setFun_flag(0);
            }
        }
        for (DefaultMutableTreeNode node : refreshNode) {
            RightUtil.refreshDeptNode(node, root);
        }
    }

    /**
     * 该方法用于获取当前树下授权部门，并放入指定LIST
     * @param rootNode：根节点
     * @param deptcodes：指定LIST
     */
    public static void getDeptRight(DefaultMutableTreeNode rootNode, List<DeptCode> deptcodes) {
        DefaultMutableTreeNode tmpNode = rootNode;
        Enumeration enumt = tmpNode.children();
        while (enumt.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumt.nextElement();
            DeptCode dc = (DeptCode) node.getUserObject();
            if (dc.getFun_flag() == 0) {
                continue;
            }
            if (dc.getFun_flag() == 1) {
                deptcodes.add(dc);
                continue;
            }
            getDeptRight(node, deptcodes);
        }
    }

    /**
     * 该方法用于部门授权，将指定部门授予访问/拒绝权限给指定用户
     * @param deptCode_key：指定部门Key
     * @param mod：0：拒绝；1：访问
     * @param a01password_key：指定用户Key
     */
    public static ValidateSQLResult defineDeptRight(String dept_code, int mod, final String user_key, boolean isSA, String g_a01password_key) {
        if (!UserContext.isSA) {
            for (RoleA01 r : UserContext.roles) {
                if (r.getRoleA01_key().equals(user_key)) {
                    ValidateSQLResult vs = new ValidateSQLResult();
                    vs.setResult(1);
                    vs.setMsg("不能给自己授权");
                    return vs;
                }
            }
        }
        return RightImpl.defineDeptRight(dept_code, mod, user_key, isSA, g_a01password_key);
    }

    public static Hashtable<String, Object> getReportRight(String roleKey) {
        Hashtable<String, Object> ht = new Hashtable<String, Object>();
        List list = RightImpl.getReportRight(roleKey);
        for (Object obj : list) {
            ht.put(obj.toString().replace(" ", ""), obj);
        }
        return ht;
    }

    public static int getReportRight(String reportDef_key, Hashtable<String, Object> reportRights) {
        if (reportRights == null) {
            return 1;
        }
        if (reportRights.get(reportDef_key + "@@1") != null) {
            return 1;
        }
        if (reportRights.get(reportDef_key + "@@2") != null) {
            return 2;
        }
        return 0;
    }

    /**
     * 获得当前用户当前角色所拥有的权限角色
     * @return 
     */
    public static List<Role> getUserRoles() {
        List listRole = new ArrayList();
        String hql = "";
        if (UserContext.isSA) {
            hql = "from Role r order by r.role_code";
        } else if (!UserContext.role_right_str.equals("")) {
            hql = "from Role r where " + UserContext.role_right_str + " order by r.role_code";
        }
        if (!hql.equals("")) {
            listRole.addAll(CommUtil.fetchEntities(hql));
        }
        return listRole;
    }
}
