/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ReportNoPanel.java
 *
 * Created on 2011-12-21, 11:13:59
 */
package org.jhrcore.client.report;

import com.foundercy.pf.control.listener.IPickFieldOrderListener;
import com.foundercy.pf.control.listener.IPickQueryExListener;
import com.foundercy.pf.control.table.FTable;
import com.foundercy.pf.control.table.FTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.DbUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.client.report.comm.ReportGroupModel;
import org.jhrcore.client.report.comm.ReportNoRulePanel;
import org.jhrcore.client.report.comm.ReportParaSetPanel;
import org.jhrcore.client.report.comm.ReportRulePanel;
import org.jhrcore.client.report.comm.ReportSetUserPanel;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.util.UtilTool;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.query.QueryScheme;
import org.jhrcore.entity.report.ReportDef;
import org.jhrcore.entity.report.ReportDept;
import org.jhrcore.entity.report.ReportGroup;
import org.jhrcore.entity.report.ReportRef;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.entity.showstyle.ShowScheme;
import org.jhrcore.iservice.impl.CommImpl;
import org.jhrcore.iservice.impl.ReportImpl;
import org.jhrcore.mutil.ReportUtil;
import org.jhrcore.rebuild.EntityBuilder;
import org.jhrcore.ui.BeanPanel;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.ui.DeptSelectDlg;
import org.jhrcore.ui.task.IModulePanel;
import org.jhrcore.ui.ModalDialog;
import org.jhrcore.ui.ModelFrame;
import org.jhrcore.ui.TreeSelectMod;
import org.jhrcore.ui.ValidateEntity;
import org.jhrcore.ui.listener.IPickBeanPanelEditListener;
import org.jhrcore.ui.listener.IPickWindowCloseListener;
import org.jhrcore.ui.renderer.HRRendererView;
import org.jhrcore.util.ComponentUtil;
import org.jhrcore.util.MsgUtil;

/**
 *
 * @author mxliteboss
 */
public class ReportNoPanel extends javax.swing.JPanel implements IModulePanel {

    private FTable ftable_report = null;//报表表格
    private FTable ftable_dept = null;//报表单位
    private JPopupMenu ppTree = new JPopupMenu();
    private JPopupMenu ppCheck = new JPopupMenu();
    private JPopupMenu ppDept = new JPopupMenu();
    private JPopupMenu ppReport = new JPopupMenu();
    private JPopupMenu ppTool = new JPopupMenu();
    private JMenuItem miAddGroup = new JMenuItem("新增套表");
    private JMenuItem miEditGroup = new JMenuItem("编辑套表");
    private JMenuItem miDelGroup = new JMenuItem("删除套表");
    private JMenuItem miAddDept = new JMenuItem("新增单位");
    private JMenuItem miDelDept = new JMenuItem("删除单位");
    private JMenuItem miAddReport = new JMenuItem("新增报表");
    private JMenuItem miDelReport = new JMenuItem("删除报表");
    private JMenuItem miSetCheck = new JMenuItem("设置审批");
    private JMenuItem miSetNotCheck = new JMenuItem("取消审批");
    private JMenuItem miNoSet = new JMenuItem("单号规则设置");
    private JMenuItem miReportParaSet = new JMenuItem("报表参数设置");
    private JMenuItem miRule = new JMenuItem("校验规则设置");
    private JTree jTree = null;
    private ReportGroupModel model = null;
    private DefaultMutableTreeNode cur_node = null;
    private List<ReportDef> reports = new ArrayList<ReportDef>();
    private List modules = null;
    public static final String module_code = "ReportNo";
    private String dept_order_sql = "DeptCode.dept_code";

    /**
     * Creates new form ReportNoPanel
     */
    public ReportNoPanel() {
        initComponents();
        initOthers();
        setupEvents();
    }

    private void initOthers() {
        initToolBar();
        modules = CommImpl.getSysModule(false, false, false);//CommUtil.fetchEntities("from ModuleInfo mi order by mi.order_no");
        ReportUtil.initReportModel(modules, reports);
        model = new ReportGroupModel();
        jTree = new JTree(model);
        HRRendererView.getCommMap().initTree(jTree);
        pnlGroup.add(new JScrollPane(jTree));
        ftable_dept = new FTable(ReportDept.class, true, true, false, module_code);
        List<TempFieldInfo> all_infos = new ArrayList<TempFieldInfo>();
        List<TempFieldInfo> default_infos = new ArrayList<TempFieldInfo>();
        List<TempFieldInfo> dept_infos = EntityBuilder.getCommFieldInfoListOf(DeptCode.class, EntityBuilder.COMM_FIELD_VISIBLE);
        for (TempFieldInfo tfi : dept_infos) {
            if ("content".equals(tfi.getField_name()) || "dept_code".equals(tfi.getField_name())) {
                default_infos.add(tfi);
            }
            tfi.setField_name("#" + tfi.getEntity_name() + "." + tfi.getField_name());
            all_infos.add(tfi);
        }
        List<TempFieldInfo> rd_infos = EntityBuilder.getCommFieldInfoListOf(ReportDept.class, EntityBuilder.COMM_FIELD_VISIBLE);
        all_infos.addAll(rd_infos);
        default_infos.addAll(rd_infos);
        ftable_dept.getOther_entitys().put("DeptCode", "DeptCode DeptCode,ReportDept ReportDept");
        ftable_dept.getOther_entity_keys().put("DeptCode", "DeptCode.deptCode_key=ReportDept.deptCode_key and ReportDept.reportDept_key ");
        ftable_dept.setAll_fields(all_infos, default_infos, new ArrayList(), module_code);
        dept_order_sql = SysUtil.getSQLOrderString(ftable_dept.getCurOrderScheme(), dept_order_sql, ftable_dept.getAll_fields());
        ftable_dept.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ftable_dept.setRight_allow_flag(true);
        ftable_dept.removeSumAndReplaceItem();
        pnlTop.add(ftable_dept, BorderLayout.CENTER);
        List<TempFieldInfo> all_infos2 = new ArrayList<TempFieldInfo>();
        List<TempFieldInfo> default_infos2 = new ArrayList<TempFieldInfo>();
        List<TempFieldInfo> report_infos = EntityBuilder.getCommFieldInfoListOf(ReportDef.class, EntityBuilder.COMM_FIELD_VISIBLE);
        for (TempFieldInfo tfi : report_infos) {
            if ("report_name".equals(tfi.getField_name())) {
                default_infos2.add(tfi);
            }
            tfi.setField_name("#" + tfi.getEntity_name() + "." + tfi.getField_name());
            all_infos2.add(tfi);
        }
        List<TempFieldInfo> rr_infos = EntityBuilder.getCommFieldInfoListOf(ReportRef.class, EntityBuilder.COMM_FIELD_VISIBLE);
        all_infos2.addAll(rr_infos);
        default_infos2.addAll(rr_infos);
        ftable_report = new FTable(ReportRef.class, false, false, false, module_code);
        ftable_report.getOther_entitys().put("ReportDef", "ReportDef ReportDef,ReportRef ReportRef");
        ftable_report.getOther_entity_keys().put("ReportDef", "ReportDef.reportDef_key=ReportRef.reportDef_key and ReportRef.reportRef_key ");
        ftable_report.setAll_fields(all_infos2, default_infos2, new ArrayList(), module_code);
        ftable_report.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ftable_report.setRight_allow_flag(true);
        ftable_report.removeItemQOSR();
        pnlBom.add(ftable_report, BorderLayout.CENTER);
    }

    private void setupEvents() {
        ftable_dept.addPickQueryExListener(new IPickQueryExListener() {

            @Override
            public void pickQuery(QueryScheme qs) {
                fetchData(cur_node, 1, qs);
            }
        });
        ftable_dept.addPickFieldOrderListener(new IPickFieldOrderListener() {

            @Override
            public void pickOrder(ShowScheme showScheme) {
                dept_order_sql = SysUtil.getSQLOrderString(showScheme, dept_order_sql, ftable_dept.getAll_fields());
                fetchData(cur_node, 1, ftable_dept.getCur_query_scheme());
            }
        });
        miRule.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List list = CommUtil.selectSQL("select distinct rrf.reportDef_key from ReportRef rrf");
                Hashtable<String, ReportDef> keys = new Hashtable<String, ReportDef>();
                for (ReportDef rd : reports) {
                    keys.put(rd.getReportDef_key(), rd);
                }
                List<ReportDef> rds = new ArrayList<ReportDef>();
                for (Object obj : list) {
                    ReportDef rd = keys.get(obj.toString());
                    if (rd != null) {
                        rds.add(rd);
                    }
                }
                ReportRulePanel pnl = new ReportRulePanel(modules, rds);
                ModelFrame.showModel(ContextManager.getMainFrame(), pnl, true, "校验规则设置：", 800, 600);
            }
        });
        miReportParaSet.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setPara();
            }
        });
        miAddGroup.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> fields = EntityBuilder.getCommFieldNameListOf(ReportGroup.class, EntityBuilder.COMM_FIELD_VISIBLE);
                BeanPanel.editForRepeat(ContextManager.getMainFrame(), fields, "新增套表", new ValidateEntity() {

                    @Override
                    public boolean isEntityValidate(Object obj) {
                        return validateGroup(obj, true);
                    }
                }, new IPickBeanPanelEditListener() {

                    @Override
                    public void pickSave(Object obj) {
                        ValidateSQLResult vsr = CommUtil.saveEntity(obj);
                        if (vsr.getResult() == 0) {
                            DefaultMutableTreeNode tmmNode = model.addGroup(null, (ReportGroup) obj);
                            ComponentUtil.initTreeSelection(jTree, tmmNode);
                        }
                    }

                    @Override
                    public Object getNew() {
                        ReportGroup rgroup = (ReportGroup) UtilTool.createUIDEntity(ReportGroup.class);
                        return rgroup;
                    }
                });


            }
        });
        miEditGroup.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(cur_node.getUserObject() instanceof ReportGroup)) {
                    JOptionPane.showMessageDialog(null, "不能选择分组进行编辑");
                    return;
                }
                ReportGroup rgroup = (ReportGroup) cur_node.getUserObject();
                List<String> fields = EntityBuilder.getCommFieldNameListOf(ReportGroup.class, EntityBuilder.COMM_FIELD_VISIBLE);
                if (BeanPanel.edit(ContextManager.getMainFrame(), rgroup, fields, "编辑套表", new ValidateEntity() {

                    @Override
                    public boolean isEntityValidate(Object obj) {
                        return validateGroup(obj, false);
                    }
                })) {
                    ValidateSQLResult vsr = CommUtil.updateEntity(rgroup);
                    if (vsr.getResult() > 0) {
                        MsgUtil.showHRSaveErrorMsg(vsr);
                    } else {
                        DefaultMutableTreeNode temp_node = model.addGroup(cur_node, rgroup);
                        ComponentUtil.initTreeSelection(jTree, temp_node);
                        JOptionPane.showMessageDialog(null, "编辑套表成功");
                    }
                }
            }
        });
        miDelGroup.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                delGroup();
            }
        });
        jTree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                if (e.getPath() == null || e.getPath().getLastPathComponent() == null) {
                    return;
                }
                Object obj = e.getPath().getLastPathComponent();
                if (obj instanceof DefaultMutableTreeNode) {
                    cur_node = (DefaultMutableTreeNode) obj;
                }
                fetchData(cur_node, 3, null);
            }
        });
        jTree.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 3) {
                    ppTree.show(jTree, e.getX(), e.getY());
                }
            }
        });
        miAddDept.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addDept();
            }
        });
        miDelDept.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                delReportDept();
            }
        });
        miAddReport.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addReport(cur_node);
            }
        });
        miDelReport.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                delReport(ftable_report.getSelectKeys());
            }
        });
        miNoSet.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ReportNoRulePanel pnl = new ReportNoRulePanel();
                ModelFrame.showModel(ContextManager.getMainFrame(), pnl, true, "单号规则设置：");
            }
        });
        btnSetCheck.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ppCheck.show(btnSetCheck, 0, 25);
            }
        });
        miSetCheck.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setReportChecked(ftable_dept.getSelectKeys(), true);
            }
        });
        miSetNotCheck.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setReportChecked(ftable_dept.getSelectKeys(), false);
            }
        });
        btnSetUser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List objs = ftable_dept.getObjects();
                ModelFrame mf = ModelFrame.showModel(ContextManager.getMainFrame(), new ReportSetUserPanel(objs), true, "设置用户", 800, 600);
                mf.addIPickWindowCloseListener(new IPickWindowCloseListener() {

                    @Override
                    public void pickClose() {
                        fetchData(cur_node, 1, null);
                    }
                });
            }
        });
        ComponentUtil.popRightMenu(btnGroup, ppTree);
        ComponentUtil.popRightMenu(btnDept, ppDept);
        ComponentUtil.popRightMenu(btnReport, ppReport);
        ComponentUtil.popRightMenu(btnTool, ppTool);
        ComponentUtil.initTreeSelection(jTree);
    }

    private boolean validateGroup(Object obj, boolean isNew) {
        if (obj instanceof ReportGroup) {
            ReportGroup rg = (ReportGroup) obj;
            if (rg.getRname() == null || rg.getRname().isEmpty()) {
                JOptionPane.showMessageDialog(null, "套表名称不能为空");
                return false;
            }
            if (CommUtil.exists("select 1 from ReportGroup rg where rg.rname='" + rg.getRname() + "' and rg.reportGroup_key<>'" + rg.getReportGroup_key() + "'")) {
                JOptionPane.showMessageDialog(null, "套表名称不允许重复");
                return false;
            }
            return true;
        }
        return false;
    }

    private void initToolBar() {
        ppTree.add(miAddGroup);
        ppTree.add(miEditGroup);
        ppTree.add(miDelGroup);
        ppReport.add(miAddReport);
        ppReport.add(miDelReport);
        ppDept.add(miAddDept);
        ppDept.add(miDelDept);
        ppCheck.add(miSetCheck);
        ppCheck.add(miSetNotCheck);
        ppTool.add(miReportParaSet);
        ppTool.addSeparator();
        ppTool.add(miNoSet);
        ppTool.add(miRule);
    }

    private void setReportChecked(List<String> keys, boolean checked) {
        if (keys.isEmpty()) {
            return;
        }
        ValidateSQLResult result = CommUtil.excuteSQLs("update ReportDept set needCheck=" + (checked ? 1 : 0) + " where reportDept_key in ", keys);
        if (result.getResult() == 0) {
            fetchData(cur_node, 1, null);
            JOptionPane.showMessageDialog(null, "设置成功");
        } else {
            MsgUtil.showHRSaveErrorMsg(result);
        }
    }

    private void delGroup() {
        if (cur_node == null) {
            return;
        }
        List<String> keys = new ArrayList<String>();
        Enumeration enumt = cur_node.breadthFirstEnumeration();
        while (enumt.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumt.nextElement();
            if (node.getUserObject() instanceof ReportGroup) {
                keys.add(((ReportGroup) node.getUserObject()).getReportGroup_key());
            }
        }
        if (keys.size() > 500) {
            JOptionPane.showMessageDialog(null, "一次最多允许删除500个套帐");
            return;
        }
        if (JOptionPane.showConfirmDialog(JOptionPane.getFrameForComponent(btnDept),
                "该操作将删除选中的套帐及相关的套帐数据，包括套帐单位、套帐报表、套帐单号、套帐日志，确定要继续吗", "询问", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
            return;
        }
        String keyStr = DbUtil.getQueryForMID("", keys, "", "");
        if (CommUtil.exists("select 1 from ReportNo rn where rn.reportGroup_key in " + keyStr)) {
            if (JOptionPane.showConfirmDialog(JOptionPane.getFrameForComponent(btnDept),
                    "当前选择套帐已经存在单号数据，确定要继续删除吗", "询问", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
                return;
            }
        }
        String sql = "delete from ReportLog where reportGroup_key in " + keyStr + ";";
        sql += "delete from ReportNo where reportGroup_key in " + keyStr + ";";
        sql += "delete from ReportDept where reportGroup_key in " + keyStr + ";";
        sql += "delete from ReportRef where reportGroup_key in " + keyStr + ";";
        sql += "delete from ReportGroup where reportGroup_key in " + keyStr + ";";
        ValidateSQLResult result = CommUtil.excuteSQLs(sql, ";");
        if (result.getResult() == 0) {
            JOptionPane.showMessageDialog(null, "删除成功");
            DefaultMutableTreeNode node = ComponentUtil.getNextNode(cur_node);
            cur_node.removeFromParent();
            ComponentUtil.initTreeSelection(jTree, node);
        } else {
            MsgUtil.showHRDelErrorMsg(result.getMsg());
        }
    }

    private void addDept() {
        List existsDept = new ArrayList();
        for (Object obj : ftable_dept.getAllObjects()) {
            ReportDept rdept = (ReportDept) obj;
            DeptCode dc = UserContext.getDcKeys().get(rdept.getDeptCode_key());
            if (dc == null) {
                continue;
            }
            existsDept.add(dc);
        }
        DeptSelectDlg deptSelectDlg = new DeptSelectDlg(UserContext.getDepts(false), existsDept, TreeSelectMod.nodeCheckMod);
        ContextManager.locateOnMainScreenCenter(deptSelectDlg);
        deptSelectDlg.setVisible(true);
        if (deptSelectDlg.isClick_ok()) {
            ReportGroup rgroup = (ReportGroup) cur_node.getUserObject();
            List<DeptCode> depts = deptSelectDlg.getSelectDepts();
            List<String> deptKeys = new ArrayList();
            for (DeptCode dc : depts) {
                deptKeys.add(dc.getDeptCode_key());
            }
            ValidateSQLResult result = ReportImpl.addDeptsToGroup(rgroup, deptKeys);
            if (result.getResult() == 0) {
                fetchData(cur_node, 1, null);
                JOptionPane.showMessageDialog(null, "添加成功");
            } else {
                MsgUtil.showHRSaveErrorMsg(result);
            }
        }
    }

    private void delReportDept() {
        if (ftable_dept.getSelectObjects().isEmpty()) {
            return;
        }
        if (JOptionPane.showConfirmDialog(JOptionPane.getFrameForComponent(btnDept),
                "该操作将删除选中的单位，确定要继续吗", "询问", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
            return;
        }
        String sql = DbUtil.getQueryForMID("delete from ReportDept where reportDept_key in", ftable_dept.getSelectKeys());
        sql = sql.substring(0, sql.length() - 1);
        ValidateSQLResult vsr = CommUtil.excuteSQL(sql);
        if (vsr.getResult() == 0) {
            ((FTableModel) ftable_dept.getModel()).getHt_OtherTable().clear();
            ftable_dept.deleteSelectedRows();
            JOptionPane.showMessageDialog(null, "删除成功");
        } else {
            MsgUtil.showHRSaveErrorMsg(vsr);
        }
    }

    private void fetchData(DefaultMutableTreeNode curNode, int both, QueryScheme qs) {
        if (curNode == null || !(curNode.getUserObject() instanceof ReportGroup)) {
            ftable_report.clear();
            ftable_dept.clear();
            return;
        }
        ReportGroup rgroup = (ReportGroup) curNode.getUserObject();
        boolean sign = false;
        if (both == 3) {
            sign = true;
        }
        if (both == 1 || sign) {
            ftable_dept.setCur_query_scheme(qs);
            String hql = "select ReportDept.reportDept_key from ReportDept,DeptCode where ReportDept.deptCode_key=DeptCode.deptCode_key and ReportDept.reportGroup_key= '" + rgroup.getReportGroup_key() + "'";
            if (qs != null) {
                hql += " and ReportDept.reportDept_key in(" + qs.buildSql() + ")";
            }
            hql += " order by " + dept_order_sql;
            ((FTableModel) ftable_dept.getModel()).getHt_OtherTable().clear();
            PublicUtil.getProps_value().setProperty(ReportDept.class.getName(), "select rd from ReportDept rd where rd.reportDept_key in");
            ftable_dept.setObjects(CommUtil.selectSQL(hql));
        }
        if (both == 2 || sign) {
            String hql = "select rrf.reportRef_key from ReportRef rrf,ReportDef rdf where rrf.reportDef_key=rdf.reportDef_key and rrf.reportGroup_key= '" + rgroup.getReportGroup_key() + "'";
            hql += " order by rrf.order_no";
            ((FTableModel) ftable_report.getModel()).getHt_OtherTable().clear();
            PublicUtil.getProps_value().setProperty(ReportRef.class.getName(), "select rrf from ReportRef rrf where rrf.reportRef_key in");
            ftable_report.setObjects(CommUtil.fetchEntities(hql));
        }
    }

    private void addReport(DefaultMutableTreeNode node) {
        if (node == null || node.isRoot()) {
            return;
        }
        ReportGroup rgroup = (ReportGroup) node.getUserObject();
        List<String> existKeys = (List<String>) CommUtil.selectSQL("select rrf.reportDef_key from ReportRef rrf where rrf.reportGroup_key='" + rgroup.getReportGroup_key() + "'");
        List<ReportDef> existsReports = new ArrayList<ReportDef>();
        for (ReportDef rd : reports) {
            if (existKeys.contains(rd.getReportDef_key())) {
                existsReports.add(rd);
            }
        }
        ReportModel reportModel = new ReportModel(modules, reports, existsReports, UserContext.role_id);
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.add(new JScrollPane(ReportUtil.getReportTree(reportModel)));
        pnl.setPreferredSize(new Dimension(400, 350));
        if (ModalDialog.doModal(ContextManager.getMainFrame(), pnl, "")) {
            List checkedObjs = ComponentUtil.getCheckedObjs(reportModel);
            if (checkedObjs.isEmpty()) {
                return;
            }
            List<String> reportKeys = new ArrayList<String>();
            for (Object cobj : checkedObjs) {
                if (cobj instanceof ReportDef) {
                    reportKeys.add(((ReportDef) cobj).getReportDef_key());
                }
            }
            if (reportKeys.isEmpty()) {
                JOptionPane.showMessageDialog(null, "未选择任何报表");
                return;
            }
            ValidateSQLResult result = ReportImpl.addReportsToGroup(rgroup, reportKeys);
            if (result.getResult() == 0) {
                fetchData(node, 2, null);
                JOptionPane.showMessageDialog(null, "添加成功");
            } else {
                MsgUtil.showHRSaveErrorMsg(result);
            }
        }
    }

    private void delReport(List<String> reports) {
        if (reports == null || reports.isEmpty()) {
            return;
        }
        if (JOptionPane.showConfirmDialog(JOptionPane.getFrameForComponent(btnDept),
                "该操作将删除当前选中的报表，确定要继续吗", "询问", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
            return;
        }
        String sql = "delete from ReportRef where reportRef_key in";
        sql = DbUtil.getQueryForMID(sql, ftable_report.getSelectKeys());
        sql = sql.substring(0, sql.length() - 1);
        ValidateSQLResult result = CommUtil.excuteSQL(sql);
        if (result.getResult() == 0) {
            ftable_report.deleteSelectedRows();
            JOptionPane.showMessageDialog(null, "删除成功");
        } else {
            MsgUtil.showHRDelErrorMsg(result);
        }
    }

    private void setPara() {
        List<String> list = (List<String>) CommUtil.selectSQL("select distinct rrf.reportDef_key from ReportRef rrf");
        List<ReportDef> rds = new ArrayList<ReportDef>();
        for (ReportDef rd : reports) {
            if (list.contains(rd.getReportDef_key())) {
                rds.add(rd);
            }
        }
        ReportParaSetPanel pnl = new ReportParaSetPanel(rds, modules);
        ModelFrame.showModel(ContextManager.getMainFrame(), pnl, true, "报表参数设置");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        pnlGroup = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnGroup = new javax.swing.JButton();
        btnDept = new javax.swing.JButton();
        btnReport = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnSetUser = new javax.swing.JButton();
        btnSetCheck = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnTool = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        pnlTop = new javax.swing.JPanel();
        pnlBom = new javax.swing.JPanel();

        jSplitPane1.setDividerLocation(180);
        jSplitPane1.setDividerSize(4);

        pnlGroup.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setLeftComponent(pnlGroup);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnGroup.setText("套表管理");
        btnGroup.setFocusable(false);
        btnGroup.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGroup.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnGroup);

        btnDept.setText("单位管理");
        btnDept.setFocusable(false);
        btnDept.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDept.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnDept);

        btnReport.setText("报表管理");
        btnReport.setFocusable(false);
        btnReport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnReport);
        jToolBar1.add(jSeparator1);

        btnSetUser.setText("用户设置");
        btnSetUser.setFocusable(false);
        btnSetUser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSetUser.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSetUser);

        btnSetCheck.setText("审批设置");
        btnSetCheck.setFocusable(false);
        btnSetCheck.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSetCheck.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSetCheck);
        jToolBar1.add(jSeparator2);

        btnTool.setText("工具");
        btnTool.setFocusable(false);
        btnTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnTool);

        jSplitPane2.setDividerLocation(200);
        jSplitPane2.setDividerSize(4);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        pnlTop.setBorder(javax.swing.BorderFactory.createTitledBorder("套表单位"));
        pnlTop.setLayout(new java.awt.BorderLayout());
        jSplitPane2.setTopComponent(pnlTop);

        pnlBom.setBorder(javax.swing.BorderFactory.createTitledBorder("套表报表"));
        pnlBom.setLayout(new java.awt.BorderLayout());
        jSplitPane2.setRightComponent(pnlBom);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 562, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 439, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSplitPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(446, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jSplitPane1.setRightComponent(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 747, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDept;
    private javax.swing.JButton btnGroup;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnSetCheck;
    private javax.swing.JButton btnSetUser;
    private javax.swing.JButton btnTool;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel pnlBom;
    private javax.swing.JPanel pnlGroup;
    private javax.swing.JPanel pnlTop;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setFunctionRight() {
    }

    @Override
    public void pickClose() {
    }

    @Override
    public void refresh() {
    }

    @Override
    public String getModuleCode() {
        return module_code;
    }
}
