/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.client.report;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import javax.swing.tree.DefaultTreeModel;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.base.ModuleInfo;
import org.jhrcore.entity.report.ReportDef;
import org.jhrcore.entity.right.RoleRightTemp;
import org.jhrcore.iservice.impl.CommImpl;
import org.jhrcore.mutil.RightUtil;
import org.jhrcore.ui.CheckTreeNode;

/**
 *
 * @author yangzhou
 */
public class ReportModel extends DefaultTreeModel {

    private CheckTreeNode rootScheme;
    // 报表模块节点
    private Hashtable<String, CheckTreeNode> module_table = new Hashtable<String, CheckTreeNode>();
    // 报表类型节点
    private Hashtable<String, CheckTreeNode> class_table = new Hashtable<String, CheckTreeNode>();
    private Hashtable<String, List<ReportDef>> class_keys = new Hashtable<String, List<ReportDef>>();

    private CheckTreeNode getSchemeRoot() {
        RoleRightTemp rrt = new RoleRightTemp();
        rrt.setTemp_name("所有报表");
        return new CheckTreeNode(rrt);
    }

    public ReportModel() {
        this(null, UserContext.role_id);
    }

    public ReportModel(String roleKey) {
        this(null, roleKey);
    }

    public ReportModel(ModuleInfo mi, String roleKey) {
        super(new CheckTreeNode());
        rootScheme = getSchemeRoot();
        this.setRoot(rootScheme);
        buildTree(mi, roleKey);
    }

    public ReportModel(List defs) {
        this(null, defs);
    }

    public ReportModel(String roleKey, List defs) {
        super(new CheckTreeNode());
        rootScheme = getSchemeRoot();
        this.setRoot(rootScheme);
        buildTree(defs, roleKey);
    }

    public ReportModel(List module_list, List reports, String roleKey) {
        super(new CheckTreeNode());
        rootScheme = getSchemeRoot();
        this.setRoot(rootScheme);
        buildTree(module_list, reports, roleKey);
    }

    public ReportModel(List module_list, List reports, List existsReports, String roleKey) {
        super(new CheckTreeNode());
        rootScheme = getSchemeRoot();
        this.setRoot(rootScheme);
        buildTree(module_list, reports, roleKey);
        setExists(existsReports);
    }

    private void setExists(List<ReportDef> existsReport) {
        Enumeration<CheckTreeNode> e = rootScheme.breadthFirstEnumeration();
        while (e.hasMoreElements()) {
            CheckTreeNode checkNode = e.nextElement();
            if (checkNode.getUserObject() instanceof ReportDef) {
                ReportDef rdf = (ReportDef) checkNode.getUserObject();
                if (existsReport.contains(rdf)) {
                    checkNode.setSelected(true);
                }
            }
        }
    }

    public void removeReportDef(ReportDef reportDef, String comp_key) {
        List<ReportDef> list = class_keys.get(comp_key);
        list.remove(reportDef);
        class_keys.put(comp_key, list);
    }

    public CheckTreeNode addReportDef(ReportDef reportDef) {
        String key = reportDef.getModuleInfo().getModule_code() + "__" + reportDef.getReport_class();
        List<ReportDef> list = class_keys.get(key);
        if (list == null) {
            list = new ArrayList<ReportDef>();
        }
        if (!list.contains(reportDef)) {
            list.add(reportDef);
        }
        class_keys.put(key, list);
        SysUtil.sortListByInteger(list, "order_no");
        CheckTreeNode parent_node;
        if (key.endsWith("__")) {
            parent_node = module_table.get(key.substring(0, key.length() - 2));
        } else {
            parent_node = class_table.get(key);
            if (parent_node == null) {
                RoleRightTemp temp_rrt = new RoleRightTemp();
                temp_rrt.setTemp_name(reportDef.getReport_class());
                temp_rrt.setModuleInfo(reportDef.getModuleInfo());
                parent_node = new CheckTreeNode(temp_rrt);
                CheckTreeNode module_node = module_table.get(reportDef.getModuleInfo().getModule_code());
                module_node.add(parent_node);
                class_table.put(key, parent_node);
            }
        }
        int ind = parent_node.getChildCount();
        int insert_ind = list.indexOf(reportDef);
        CheckTreeNode node = new CheckTreeNode(reportDef);
        if (insert_ind < ind) {
            parent_node.insert(node, list.indexOf(reportDef));
        } else {
            parent_node.add(node);
        }
        return node;
    }

    private void buildTree(ModuleInfo mi, String roleKey) {
        rootScheme.removeAllChildren();
        List list_report = new ArrayList();
        List module_list = new ArrayList();
        if (mi == null) {
            module_list.addAll(CommImpl.getSysModule(false, false, false));//CommUtil.fetchEntities("from ModuleInfo order by order_no"));
            list_report.addAll(CommUtil.fetchEntities("from ReportDef rd join fetch rd.moduleInfo order by rd.moduleInfo.order_no,rd.report_class,rd.order_no"));
        } else {
            module_list.add(mi);
            list_report.addAll(CommUtil.fetchEntities("from ReportDef rd join fetch rd.moduleInfo where rd.moduleInfo.module_key='" + mi.getModule_key() + "' order by rd.report_class,rd.order_no"));
        }
        for (Object obj : list_report) {
            ReportDef reportDef = (ReportDef) obj;
            reportDef.setReport_class(reportDef.getReport_class() == null ? "" : reportDef.getReport_class());
        }
        buildTree(module_list, list_report, roleKey);
    }

    private void buildTree(List defs, String roleKey) {
        rootScheme.removeAllChildren();
        List module_list = new ArrayList();
        for (Object obj1 : defs) {
            ReportDef def = (ReportDef) obj1;
            if (!module_list.contains(def.getModuleInfo())) {
                module_list.add(def.getModuleInfo());
            }
        }
        for (Object obj : defs) {
            ReportDef reportDef = (ReportDef) obj;
            reportDef.setReport_class(reportDef.getReport_class() == null ? "" : reportDef.getReport_class());
        }
        buildTree(module_list, defs, roleKey);
    }

    private void buildTree(List module_list, List list_report, String roleKey) {
        List<ReportDef> reports = new ArrayList<ReportDef>();
        if (roleKey == null || roleKey.equals("-1") || roleKey.trim().equals("")) {
            for (Object obj : list_report) {
                ReportDef reportDef = (ReportDef) obj;
                if (UserContext.hasReportRight(reportDef.getReportDef_key())) {
                    reports.add(reportDef);
                }
            }
        } else {
            Hashtable<String, Object> ht = RightUtil.getReportRight(roleKey);
            for (Object obj : list_report) {
                ReportDef reportDef = (ReportDef) obj;
                if (RightUtil.getReportRight(reportDef.getReportDef_key(), ht) == 0) {
                    continue;
                }
                reports.add(reportDef);
            }
        }
        for (Object obj : module_list) {
            ModuleInfo module = (ModuleInfo) obj;
            if (module.getModule_code().equals("ssjk")) {
                continue;//过滤实时监控
            }
            CheckTreeNode module_node = module_table.get(module.getModule_code());
            if (module_node == null) {
                RoleRightTemp rrt = new RoleRightTemp();
                rrt.setTemp_name(module.getModule_name());
                rrt.setModuleInfo(module);
                module_node = new CheckTreeNode(rrt);
                module_table.put(module.getModule_code(), module_node);
                rootScheme.add(module_node);
            }
        }
        for (ReportDef rd : reports) {
            ModuleInfo module = rd.getModuleInfo();
            String key = module.getModule_code() + "__" + rd.getReport_class();
            CheckTreeNode module_node = module_table.get(module.getModule_code());
            if (module_node == null) {
                RoleRightTemp rrt = new RoleRightTemp();
                rrt.setTemp_name(module.getModule_name());
                rrt.setModuleInfo(module);
                module_node = new CheckTreeNode(rrt);
                module_table.put(module.getModule_code(), module_node);
                rootScheme.add(module_node);
            }
            List<ReportDef> list = class_keys.get(key);
            if (list == null) {
                list = new ArrayList<ReportDef>();
            }
            list.add(rd);
            class_keys.put(key, list);
            if (!rd.getReport_class().equals("")) {
                RoleRightTemp temp_rrt = new RoleRightTemp();
                temp_rrt.setTemp_name(rd.getReport_class());
                temp_rrt.setModuleInfo(rd.getModuleInfo());
                CheckTreeNode class_node = class_table.get(key);
                if (class_node == null) {
                    class_node = new CheckTreeNode(temp_rrt);
                    module_node.add(class_node);
                }
                class_table.put(key, class_node);
            }
        }
        for (String key : class_keys.keySet()) {
            List<ReportDef> list = class_keys.get(key);
            SysUtil.sortListByInteger(list, "order_no");
            CheckTreeNode parent_node;
            if (key.endsWith("__")) {
                parent_node = module_table.get(key.substring(0, key.length() - 2));
            } else {
                parent_node = class_table.get(key);
            }
            for (ReportDef reportDef : list) {
                CheckTreeNode node = new CheckTreeNode(reportDef);
                parent_node.add(node);
            }
        }
    }
}
