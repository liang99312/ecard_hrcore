/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.client.report;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.swing.tree.DefaultTreeModel;
import org.jhrcore.util.SysUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.report.ReportDef;
import org.jhrcore.entity.right.RoleRightTemp;
import org.jhrcore.ui.CheckTreeNode;

/**
 *
 * @author hflj
 */
public class ReportCheckModel extends DefaultTreeModel {

    private CheckTreeNode rootScheme;
    // 报表类型节点
    private Hashtable<String, CheckTreeNode> class_table = new Hashtable<String, CheckTreeNode>();
    private Hashtable<String, List<ReportDef>> class_keys = new Hashtable<String, List<ReportDef>>();

    private CheckTreeNode getSchemeRoot() {
        RoleRightTemp rrt = new RoleRightTemp();
        rrt.setTemp_name("所有报表");
        return new CheckTreeNode(rrt);
    }

    public ReportCheckModel(List defs) {
        super(new CheckTreeNode());
        rootScheme = getSchemeRoot();
        this.setRoot(rootScheme);
        buildTree(defs);
    }

    private void buildTree(List defs) {
        rootScheme.removeAllChildren();
        List<ReportDef> reports = new ArrayList<ReportDef>();
        for (Object obj : defs) {
            ReportDef reportDef = (ReportDef) obj;
            reportDef.setReport_class(reportDef.getReport_class() == null ? "" : reportDef.getReport_class());
            if (UserContext.hasReportRight(reportDef.getReportDef_key())) {
                reports.add(reportDef);
            }
        }
        for (ReportDef rd : reports) {
            String key = rd.getReport_class();
            List<ReportDef> list = class_keys.get(key);
            if (list == null) {
                list = new ArrayList<ReportDef>();
            }
            list.add(rd);
            class_keys.put(key, list);
            if (!rd.getReport_class().equals("")) {
                RoleRightTemp temp_rrt = new RoleRightTemp();
                temp_rrt.setTemp_name(rd.getReport_class());
                CheckTreeNode class_node = class_table.get(key);
                if (class_node == null) {
                    class_node = new CheckTreeNode(temp_rrt);
                    rootScheme.add(class_node);
                }
                class_table.put(key, class_node);
            }
        }
        for (String key : class_keys.keySet()) {
            List<ReportDef> list = class_keys.get(key);
            SysUtil.sortListByInteger(list, "order_no");
            CheckTreeNode parent_node = class_table.get(key);
            for (ReportDef reportDef : list) {
                CheckTreeNode node = new CheckTreeNode(reportDef);
                parent_node.add(node);
            }
        }
    }
}
