/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ReportMngPanel.java
 *
 * Created on 2011-12-13, 16:25:02
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.client.report.comm.CheckInputPanel;
import org.jhrcore.client.report.comm.CreateReportDataPanel;
import org.jhrcore.client.report.comm.ReportGroupModel;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.entity.A01PassWord;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.util.UtilTool;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.query.QueryScheme;
import org.jhrcore.entity.report.ReportDef;
import org.jhrcore.entity.report.ReportDept;
import org.jhrcore.entity.report.ReportGroup;
import org.jhrcore.entity.report.ReportLog;
import org.jhrcore.entity.report.ReportNo;
import org.jhrcore.entity.report.ReportParaSet;
import org.jhrcore.entity.report.ReportRegula;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.entity.showstyle.ShowScheme;
import org.jhrcore.iservice.impl.CommImpl;
import org.jhrcore.iservice.impl.ReportImpl;
import org.jhrcore.mutil.ReportUtil;
import org.jhrcore.rebuild.EntityBuilder;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.ui.DeptPanel;
import org.jhrcore.ui.HrTextPane;
import org.jhrcore.ui.task.IModulePanel;
import org.jhrcore.ui.ModalDialog;
import org.jhrcore.ui.ModelFrame;
import org.jhrcore.ui.StepWorkUserSelectPanel;
import org.jhrcore.ui.listener.IPickWindowCloseListener;
import org.jhrcore.ui.renderer.HRRendererView;
import org.jhrcore.util.ComponentUtil;
import org.jhrcore.util.MsgUtil;

/**
 *
 * @author mxliteboss
 */
public class ReportUsePanel extends javax.swing.JPanel implements IModulePanel {

    private JTree jTree;
    private DefaultMutableTreeNode cur_node;
    private JPopupMenu ppUp = new JPopupMenu();
    private JPopupMenu ppCheck = new JPopupMenu();
    private JMenuItem miDataUp = new JMenuItem("�ύ����");
    private JMenuItem miDataCancel = new JMenuItem("ȡ���ύ");
    private JMenuItem miCheck = new JMenuItem("���ͨ��");
    private JMenuItem miCheckNot = new JMenuItem("��˲���");
    private JMenuItem miCheckCancel = new JMenuItem("ȡ�����");
    private JButton btnCreateNo = new JButton("�����µ���");
    private JButton btnCreateData = new JButton("��������");
    private JButton btnUp = new JButton("�ύ");
    private JButton btnCheck = new JButton("���");
    private JButton btnBackReport = new JButton("�浵");
    private JButton btnExcute = new JButton("ִ��");
    private JButton btnCheckData = new JButton("У��");
    private JButton btnTool = new JButton("����");
    private JMenuItem btnSetUser = new JMenuItem("���ð���Ա");
    private JMenuItem btnDelUser = new JMenuItem("ȡ������Ա");
    private JMenuItem btnDel = new JMenuItem("ɾ������");
    private JPopupMenu jpoFill = new JPopupMenu("�����");
    private JButton btnFill = new JButton("�����");
    private JMenuItem btnOutFill = new JMenuItem("����ģ������");
    private JMenuItem btnOutData = new JMenuItem("���������");
    private JMenuItem btnInPort = new JMenuItem("���������");
    private JLabel lblType = new JLabel("��������");
    private List<ReportDef> reports = new ArrayList<ReportDef>();
    private List modules = null;
    public static final String module_code = "ReportUse";
    private String no_order_sql = "ReportNo.cno desc";
    private String log_order_sql = "ReportLog.cdate desc";
    private String dept_order_sql = "DeptCode.dept_code";
    private String cur_type = "δ�ύ";
    private String cur_ym = "";
    private FTable ftable_dept = null;
    private FTable ftable_no = null;//���ű��
    private FTable ftable_log = null;//��־���
    private DeptCode deptCode = null;
    private ReportDept cur_dept = null;
    private StepWorkUserSelectPanel user_select_pnl = null;
    private ReportGroupModel model = null;
    private JComboBoxBinding ym_binding;
    private List<String> yms = new ArrayList();
    private ActionListener jcbYmAction;
    private DeptPanel deptPanel = null;

    /** Creates new form ReportMngPanel */
    public ReportUsePanel() {
        initComponents();
        initOthers();
        setupEvents();
    }

    private void initOthers() {
        initToolBar();
        deptPanel = new DeptPanel(new ArrayList(), 1);
        pnlDept.add(deptPanel);
        modules = CommImpl.getSysModule(false, false, false);//CommUtil.fetchEntities("from ModuleInfo mi order by mi.order_no");
        ReportUtil.initReportModel(modules, reports);
        model = new ReportGroupModel();
        jTree = new JTree(model);
        HRRendererView.getCommMap().initTree(jTree);
        pnlGroup.add(new JScrollPane(jTree));
        ftable_dept = new FTable(ReportDept.class, true, false, false, module_code);
        ftable_dept.getOther_entitys().put("DeptCode", "ReportDept ReportDept,DeptCode DeptCode");
        ftable_dept.getOther_entity_keys().put("DeptCode", "ReportDept.deptCode_key=DeptCode.deptCode_key and ReportDept.reportDept_key ");
        List<TempFieldInfo> all_dept_infos = new ArrayList<TempFieldInfo>();
        List<TempFieldInfo> default_dept_infos = new ArrayList<TempFieldInfo>();
        initFields(all_dept_infos, default_dept_infos, true);
        EntityBuilder.buildInfo(ReportDept.class, all_dept_infos, default_dept_infos);
        ftable_dept.setAll_fields(all_dept_infos, default_dept_infos, module_code);
        ftable_dept.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlDeptData.add(ftable_dept);
        ftable_dept.setRight_allow_flag(true);
        ftable_dept.removeItemQSR();
        ftable_no = new FTable(ReportNo.class, true, true, false, module_code);
        ftable_no.setRight_allow_flag(true);
        ftable_no.removeSumAndReplaceItem();
        List<TempFieldInfo> all_infos = new ArrayList<TempFieldInfo>();
        List<TempFieldInfo> default_infos = new ArrayList<TempFieldInfo>();
        initFields(all_infos, default_infos, false);
        EntityBuilder.buildInfo(ReportNo.class, all_infos, default_infos);
        ftable_no.getOther_entitys().put("ReportDef", "ReportDef ReportDef,ReportNo ReportNo");
        ftable_no.getOther_entity_keys().put("ReportDef", "ReportDef.reportDef_key=ReportNo.reportDef_key and ReportNo.reportNo_key ");
        ftable_no.getOther_entitys().put("DeptCode", "DeptCode DeptCode,ReportNo ReportNo");
        ftable_no.getOther_entity_keys().put("DeptCode", "DeptCode.deptCode_key=ReportNo.deptCode_key and ReportNo.reportNo_key ");
        ftable_no.setAll_fields(all_infos, default_infos, module_code);
        ftable_no.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlNo.add(ftable_no);
        ym_binding = SwingBindings.createJComboBoxBinding(UpdateStrategy.READ, yms, jcbYm);
        ym_binding.bind();
        ftable_log = new FTable(ReportLog.class, true, true, false, module_code);
        ftable_log.getOther_entitys().put("ReportDef", "ReportDef ReportDef,ReportLog ReportLog");
        ftable_log.getOther_entity_keys().put("ReportDef", "ReportDef.reportDef_key=ReportLog.reportDef_key and ReportLog.reportLog_key ");
        ftable_log.getOther_entitys().put("DeptCode", "DeptCode DeptCode,ReportLog ReportLog");
        ftable_log.getOther_entity_keys().put("DeptCode", "DeptCode.deptCode_key=ReportLog.deptCode_key and ReportLog.reportLog_key ");
        List<TempFieldInfo> all_log_infos = new ArrayList<TempFieldInfo>();
        List<TempFieldInfo> default_log_infos = new ArrayList<TempFieldInfo>();
//        initFields(all_log_infos, default_log_infos, false);
        EntityBuilder.buildInfo(ReportLog.class, all_log_infos, default_log_infos);
        ftable_log.setAll_fields(all_log_infos, default_log_infos, module_code);
        ftable_log.setRight_allow_flag(true);
        ftable_log.removeSumAndReplaceItem();
        ftable_log.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlLog.add(ftable_log);
        ym_binding = SwingBindings.createJComboBoxBinding(UpdateStrategy.READ, yms, jcbYm);
        ym_binding.bind();
        dept_order_sql = SysUtil.getSQLOrderString(ftable_dept.getCurOrderScheme(), dept_order_sql, ftable_dept.getAll_fields());
        no_order_sql = SysUtil.getSQLOrderString(ftable_no.getCurOrderScheme(), no_order_sql, ftable_no.getAll_fields());
        log_order_sql = SysUtil.getSQLOrderString(ftable_log.getCurOrderScheme(), log_order_sql, ftable_log.getAll_fields());
    }

    private void setupEvents() {
        jTree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                if (e.getPath() == null || e.getPath().getLastPathComponent() == null) {
                    return;
                }
                cur_node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
                fetchDept(cur_node);
                fetchYm();
            }
        });
        btnExcute.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                excute_report((ReportNo) ftable_no.getCurrentRow());
            }
        });
        btnBackReport.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List list = ftable_dept.getAllSelectObjects();
                if (list.isEmpty()) {
                    return;
                }
                List<String> keys = new ArrayList<String>();
                for (Object obj : list) {
                    ReportDept rd = (ReportDept) obj;
                    if (rd.isNeedCheck() && !cur_type.equals("�����")) {
                        continue;
                    }
                    keys.add(rd.getReportDept_key());
                }
                if (keys.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "������Ҫ��˵ĵ�λ������浵�����ͨ���ı���");
                    return;
                }
                checkReport(keys, "�浵");
            }
        });
        btnCreateNo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                createNo(cur_node, deptCode);
            }
        });
        btnCreateData.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                createData(ftable_no.getAllSelectObjects());
            }
        });
        jcbYmAction = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (jcbYm.getSelectedItem() != null) {
                    cur_ym = jcbYm.getSelectedItem().toString();
                } else {
                    cur_ym = "";
                }
                cur_type = jcbType.getSelectedItem().toString();
                fetchDept(cur_ym, cur_type);
                setMainState();
            }
        };
        jcbYm.addActionListener(jcbYmAction);
        jcbType.addActionListener(jcbYmAction);
        ftable_dept.addPickFieldOrderListener(new IPickFieldOrderListener() {

            @Override
            public void pickOrder(ShowScheme showScheme) {
                dept_order_sql = SysUtil.getSQLOrderString(showScheme, dept_order_sql, ftable_dept.getAll_fields());
                fetchDept(cur_ym, cur_type);
            }
        });
        ftable_dept.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (cur_dept == ftable_dept.getCurrentRow()) {
                    return;
                }
                cur_dept = (ReportDept) ftable_dept.getCurrentRow();
                fetchNoLog(cur_dept, cur_ym, cur_type, null);
            }
        });
        jtpMain.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                fetchNoLog(cur_dept, cur_ym, cur_type, null);
            }
        });
        ftable_no.addPickFieldOrderListener(new IPickFieldOrderListener() {

            @Override
            public void pickOrder(ShowScheme showScheme) {
                no_order_sql = SysUtil.getSQLOrderString(showScheme, no_order_sql, ftable_no.getAll_fields());
                if (jcbYm.getSelectedItem() != null) {
                    fetchNo(cur_dept, cur_ym, cur_type, ftable_no.getCur_query_scheme());
                } else {
                    ftable_no.clear();
                }
            }
        });
        ftable_no.addListSelectionListener(new ListSelectionListener() {

            ReportNo cur_no = null;

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (cur_no == ftable_no.getCurrentRow()) {
                    return;
                }
                cur_no = (ReportNo) ftable_no.getCurrentRow();
            }
        });
        ftable_no.addPickQueryExListener(new IPickQueryExListener() {

            @Override
            public void pickQuery(QueryScheme qs) {
                if (jcbYm.getSelectedItem() != null) {
                    fetchNo(cur_dept, cur_ym, cur_type, qs);
                } else {
                    ftable_no.clear();
                }
            }
        });
        ftable_log.addPickFieldOrderListener(new IPickFieldOrderListener() {

            @Override
            public void pickOrder(ShowScheme showScheme) {
                log_order_sql = SysUtil.getSQLOrderString(showScheme, log_order_sql, ftable_log.getAll_fields());
                fetchLog(cur_dept, cur_ym, cur_type, ftable_log.getCur_query_scheme());
            }
        });
        ftable_log.addPickQueryExListener(new IPickQueryExListener() {

            @Override
            public void pickQuery(QueryScheme qs) {
                fetchLog(cur_dept, cur_ym, cur_type, qs);
            }
        });
        btnSetUser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setUser(ftable_no.getSelectKeys());
            }
        });
        btnDelUser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                delUser();
            }
        });
        btnCheckData.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                checkSQL(ftable_no.getAllSelectObjects(), false);
            }
        });
        btnDel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                delNo(ftable_no.getSelectKeys());
            }
        });
        ComponentUtil.initTreeSelection(jTree);

        btnFill.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jpoFill.show(btnFill, 0, 30);
            }
        });
        btnOutFill.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                XlsModelDefinePanel pnl = new XlsModelDefinePanel();
                ModelFrame.showModel(ContextManager.getMainFrame(), pnl, true, "����ģ������:", 1000, 650);
            }
        });
        btnOutData.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ReportXlsExportPanel pnl = new ReportXlsExportPanel();
                ModelFrame.showModel(ContextManager.getMainFrame(), pnl, true, "", 400, 200);
            }
        });

        btnInPort.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ImportDialog eDlg = new ImportDialog();
                ContextManager.locateOnMainScreenCenter(eDlg);
                eDlg.setVisible(true);
            }
        });
    }

    private void initFields(List<TempFieldInfo> all_infos, List<TempFieldInfo> default_infos, boolean isOnlyDept) {
        if (!isOnlyDept) {
            List<TempFieldInfo> report_infos = EntityBuilder.getCommFieldInfoListOf(ReportDef.class, EntityBuilder.COMM_FIELD_VISIBLE);
            for (TempFieldInfo tfi : report_infos) {
                if (tfi.getField_name().equals("report_name")) {
                    default_infos.add(tfi);
                }
                tfi.setField_name("#" + tfi.getEntity_name() + "." + tfi.getField_name());
                all_infos.add(tfi);
            }
        }
        List<TempFieldInfo> dept_infos = EntityBuilder.getCommFieldInfoListOf(DeptCode.class, EntityBuilder.COMM_FIELD_VISIBLE);
        for (TempFieldInfo tfi : dept_infos) {
            if (tfi.getField_name().equals("dept_code") || tfi.getField_name().equals("content")) {
                default_infos.add(tfi);
            }
            tfi.setField_name("#" + tfi.getEntity_name() + "." + tfi.getField_name());
            all_infos.add(tfi);
        }

    }

    private void fetchDept(DefaultMutableTreeNode node) {
        List<DeptCode> cur_depts = new ArrayList();
        if (node != null && node.getUserObject() instanceof ReportGroup) {
            ReportGroup rgroup = (ReportGroup) node.getUserObject();
            String hql = "select distinct b.deptCode_key,b.dept_code from (select d.deptCode_key,d.dept_code from ReportDept rd,DeptCode d where d.deptCode_key=rd.deptCode_key and (" + UserContext.dept_right_rea_str + ") and rd.reportGroup_key='" + rgroup.getReportGroup_key()
                    + "') a,DeptCode b where a.deptCode_key=b.deptCode_key ";
            if (UserContext.sql_dialect.equals("sqlserver")) {
                hql += " or charindex(b.dept_code,a.dept_code)=1";
            } else {
                hql += " or instr(a.dept_code,b.dept_code)=1";
            }
            hql += " order by b.dept_code";
            List dpt = CommUtil.selectSQL(hql);
            for (Object obj : dpt) {
                Object[] objs = (Object[]) obj;
                DeptCode dc = UserContext.getDcKeys().get(objs[0].toString());
                if (dc == null) {
                    continue;
                }
                cur_depts.add(dc);
            }
        }
        deptPanel = new DeptPanel(cur_depts);
        deptPanel.getDeptTree().addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                deptCode = null;
                Object obj = ((DefaultMutableTreeNode) deptPanel.getDeptTree().getLastSelectedPathComponent()).getUserObject();
                if (obj instanceof DeptCode) {
                    deptCode = (DeptCode) obj;
                }
                fetchDept(cur_ym, cur_type);
            }
        });
        pnlDept.removeAll();
        pnlDept.add(deptPanel);
        pnlDept.updateUI();
        Object obj = ((DefaultMutableTreeNode) deptPanel.getDeptTree().getLastSelectedPathComponent()).getUserObject();
        if (obj instanceof DeptCode) {
            deptCode = (DeptCode) obj;
        }
    }

    private void fetchDept(String ym, String type) {
        ((FTableModel) ftable_dept.getModel()).getHt_OtherTable().clear();
        ((FTableModel) ftable_dept.getModel()).getHt_OtherTableSql();
        ReportGroup rg = getGroup(cur_node);
        if (ym.trim().equals("") || rg == null || deptCode == null) {
            ftable_dept.deleteAllRows();
            return;
        }
        String s_comm = "(" + UserContext.getDept_right_rea_str("DeptCode") + ") and DeptCode.dept_code like '" + deptCode.getDept_code() + "%'";
        String sql = null;
        if (type.equals("�޵���")) {
            sql = "select rd.reportDept_key from DeptCode,ReportDept rd where DeptCode.deptCode_key=rd.deptCode_key and rd.reportGroup_key='" + rg.getReportGroup_key() + "' and " + s_comm;
            sql += " and not exists(select 1 from ReportNo rn where rn.reportGroup_key='" + rg.getReportGroup_key() + "' and rn.ym='" + ym + "' and DeptCode.deptCode_key=rn.DeptCode_key)";
        } else {
            sql = "select ReportDept.reportDept_key from ReportDept,DeptCode,(select distinct DeptCode.deptCode_key from ReportNo rn,DeptCode where  rn.deptCode_key=DeptCode.deptCode_key and rn.reportGroup_key='" + rg.getReportGroup_key() + "' and rn.ym='" + ym + "' and rn.noState='" + getNoState(type)
                    + "' and " + s_comm + ") a where ReportDept.deptCode_key=a.deptCode_key and ReportDept.reportGroup_key='" + rg.getReportGroup_key() + "' and DeptCode.deptCode_key=a.deptCode_key and " + s_comm;
        }
        sql += " order by " + dept_order_sql;
        PublicUtil.getProps_value().setProperty(ReportDept.class.getName(), "from ReportDept rd where rd.reportDept_key in");
        ftable_dept.setObjects(CommUtil.selectSQL(sql));
    }

    private void fetchYm() {
        yms.clear();
        if (!(cur_node.getUserObject() instanceof ReportGroup)) {
            return;
        }
        ReportGroup rGroup = (ReportGroup) cur_node.getUserObject();
        List list = CommUtil.selectSQL("select distinct ym from ReportNo rn where rn.reportGroup_key='" + rGroup.getReportGroup_key() + "'  order by ym desc");
        yms.addAll(list);
        jcbYm.removeActionListener(jcbYmAction);
        ym_binding.unbind();
        ym_binding.bind();
        jcbYm.addActionListener(jcbYmAction);
        String cur_ym = yms.isEmpty() ? null : yms.get(0);
        jcbYm.setSelectedItem(cur_ym);
    }

    private void fetchNoLog(ReportDept dc, String ym, String type, QueryScheme qs) {
        if (jtpMain.getSelectedIndex() == 0) {
            fetchNo(dc, ym, type, qs);
        } else {
            fetchLog(dc, ym, type, qs);
        }
    }

    private ReportGroup getGroup(DefaultMutableTreeNode cur_node) {
        if (cur_node == null || !(cur_node.getUserObject() instanceof ReportGroup)) {
            return null;
        }
        return (ReportGroup) cur_node.getUserObject();
    }

    private void createNo(DefaultMutableTreeNode node, DeptCode deptCode) {
        if (deptCode == null || node == null || node == model.getRoot() || !(node.getUserObject() instanceof ReportGroup)) {
            JOptionPane.showMessageDialog(null, "��ǰ����Ϊ�գ����ߵ�ǰѡ������ױ���飬���������µ��ţ�");
            return;
        }
        if (!UserContext.isSA) {
            if (!CommUtil.exists("select count(rd.reportDept_key) from ReportDept rd where rd.tuserNo='" + UserContext.person_code + "' and rd.deptCode_key='" + deptCode.getDeptCode_key() + "'")) {
                JOptionPane.showMessageDialog(null, "ֻ���ύ�û��ſ������ɵ��š�");
                return;
            }
        }
        ReportGroup rgroup = (ReportGroup) node.getUserObject();
        createNo(rgroup, deptCode);
    }

    private void createNo(ReportGroup rgroup, DeptCode dc) {
        CheckInputPanel pnl = new CheckInputPanel(rgroup, dc);
        if (ModalDialog.doModal(ContextManager.getMainFrame(), pnl, "ѡ�񱨱�")) {
            List rs = pnl.getCheckReports();
            List<DeptCode> depts = pnl.getCheckDepts();
            String msg = "";
            for (DeptCode d : depts) {
                ValidateSQLResult result = ReportImpl.createReportNo(d, rgroup, pnl.getYm(), rs);
                msg += "\n " + d.getContent() + "{" + d.getDept_code() + "} " + ((result.getResult() == 0) ? "��ӳɹ�" : result.getMsg());
            }
            MsgUtil.showHRMsg(msg, "���������");
            fetchYm();
        }
    }

    private void createData(final List<ReportNo> rns) {
        if (rns == null || rns.isEmpty()) {
            JOptionPane.showMessageDialog(null, "δѡ�񵥺�");
            return;
        }
        if (!UserContext.isSA) {
            boolean hasRight = true;
            for (ReportNo rno : rns) {
                hasRight = hasRight && UserContext.person_code.equals(rno.getBuserNo());
            }
            hasRight = hasRight || CommUtil.exists("select 1 from ReportDept rd where rd.tuserNo='" + UserContext.person_code + "' and rd.deptCode_key='" + deptCode.getDeptCode_key() + "'");
            if (!hasRight) {
                JOptionPane.showMessageDialog(null, "ֻ���ύ�û�������Ա�ſ����������ݡ�");
                return;
            }
        }
        final CreateReportDataPanel pnl = new CreateReportDataPanel(rns, reports);
        ModelFrame mf = ModelFrame.showModel(ContextManager.getMainFrame(), pnl, true, "�������ݣ�", 700, 600, false);
        mf.addIPickWindowCloseListener(new IPickWindowCloseListener() {

            @Override
            public void pickClose() {
                fetchNo(cur_dept, cur_ym, cur_type, null);
            }
        });
        mf.setVisible(true);
    }

    private void fetchLog(ReportDept dc, String ym, String type, QueryScheme qs) {
        if (dc == null || !(cur_node.getUserObject() instanceof ReportGroup)) {
            return;
        }
        ((FTableModel) ftable_log.getModel()).getHt_OtherTable().clear();
        ((FTableModel) ftable_log.getModel()).getHt_OtherTableSql();
        ReportGroup rGroup = (ReportGroup) cur_node.getUserObject();
        ftable_log.setCur_query_scheme(qs);
        String hql = "select reportLog_key from ReportLog where ReportLog.deptCode_key='" + dc.getDeptCode_key() + "' and ReportLog.reportGroup_key='" + rGroup.getReportGroup_key() + "'"
                + "and ReportLog.ym='" + ym + "'";
        if (qs != null) {
            hql = " and reportLog_key in(" + qs.buildSql() + ")";
        }
        hql += " order by " + log_order_sql;
        PublicUtil.getProps_value().setProperty(ReportLog.class.getName(), "from ReportLog rl where rl.reportLog_key in");
        ftable_log.setObjects(CommUtil.selectSQL(hql));
    }

    private void delNo(List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return;
        }
        if (!(cur_type.equals("δ����") || cur_type.equals("δ�ύ"))) {
            JOptionPane.showMessageDialog(null, "ֻ��״̬��{δ���ɡ�δ�ύ}�ĵ��Ųſ���ɾ��");
            return;
        }
        if (JOptionPane.showConfirmDialog(JOptionPane.getFrameForComponent(btnDel),
                "ȷ��Ҫɾ��ѡ��ĵ�����", "ѯ��", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
            return;
        }
        ReportGroup rgroup = (ReportGroup) cur_node.getUserObject();
        if (!UserContext.isSA && !CommUtil.exists("select 1 from ReportDept rd where rd.reportGroup_key='" + rgroup.getReportGroup_key() + "' and rd.deptCode_key='" + cur_dept.getDeptCode_key() + "' and (rd.tuserNo='" + UserContext.person_code + "' or rd.suserNo='" + UserContext.person_code + "')")) {
            JOptionPane.showMessageDialog(null, "ֻ���ύ�ˡ�����˲���ɾ������");
            return;
        }
        ReportLog rl = (ReportLog) UtilTool.createUIDEntity(ReportLog.class);
        rl.setA0101(UserContext.person_name);
        rl.setA0190(UserContext.person_code);
        rl.setCtype("ɾ������");
        ValidateSQLResult result = ReportImpl.delReportNo(keys, rl);
        if (result.getResult() == 0) {
            ftable_no.deleteSelectedRows();
            ((FTableModel) ftable_no.getModel()).getHt_OtherTable().clear();
            ((FTableModel) ftable_no.getModel()).getHt_OtherTableSql();
            JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
        } else {
            MsgUtil.showHRDelErrorMsg(result);
        }
    }

    private void initToolBar() {
        toolbar.add(btnCreateNo);
        toolbar.add(btnCreateData);
        toolbar.addSeparator();
        toolbar.add(btnExcute);
        toolbar.add(btnCheckData);
        toolbar.addSeparator();
        toolbar.add(btnUp);
        toolbar.add(btnCheck);
        toolbar.addSeparator();
        toolbar.add(btnBackReport);
        toolbar.add(btnTool);
        toolbar.add(btnFill);
        ComponentUtil.setSize(jcbYm, 100, 22);
        ComponentUtil.setSize(lblType, 50, 22);
        ppCheck.add(miCheck);
        ppCheck.add(miCheckNot);
        ppCheck.add(miCheckCancel);
        ppUp.add(miDataUp);
        ppUp.add(miDataCancel);
        final JPopupMenu pp = new JPopupMenu();
        pp.add(btnSetUser);
        pp.add(btnDelUser);
        pp.addSeparator();
        pp.add(btnDel);
        jpoFill.add(btnOutFill);
        jpoFill.add(btnOutData);
        jpoFill.add(btnInPort);

        btnTool.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pp.show(btnTool, 0, 25);
            }
        });
        btnUp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ppUp.show(btnUp, 0, 25);
            }
        });
        btnCheck.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ppCheck.show(btnCheck, 0, 25);
            }
        });
        miDataUp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                checkReport(ftable_dept.getSelectKeys(), "�ύ");
            }
        });
        miDataCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                checkReport(ftable_dept.getSelectKeys(), "ȡ���ύ");
            }
        });
        miCheck.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                checkReport(ftable_dept.getSelectKeys(), "���ͨ��");
            }
        });
        miCheckNot.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                checkReport(ftable_dept.getSelectKeys(), "��˲���");
            }
        });
        miCheckCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                checkReport(ftable_dept.getSelectKeys(), "���ȡ��");
            }
        });

    }

    private void excute_report(ReportNo rn) {
        if (rn == null || rn.getReportNo_key() == null || rn.getReportDef_key() == null) {
            return;
        }
        HashMap parameter = new HashMap();
        ReportDef reportDef = ReportUtil.getReportDef(rn.getReportDef_key(), reports);
        ReportUtil.initReportParameter(parameter, rn);
        boolean enable = "δ�ύ".equals((String) CommUtil.fetchEntityBy("select rn.noState from ReportNo rn where rn.reportNo_key='" + rn.getReportNo_key() + "'"));
        ReportPanel.excute_report(ContextManager.getMainFrame(), reportDef, parameter, false, true, enable);
    }

    private void checkReport(List<String> depts, String type) {
        if (cur_node == null || !(cur_node.getUserObject() instanceof ReportGroup) || cur_ym == null) {
            return;
        }
        if (depts == null || depts.isEmpty() || type == null) {
            return;
        }
        if (type.equals("�ύ")) {
            List<String> keys = ftable_dept.getSelectKeys();
            ReportGroup rgroup = (ReportGroup) cur_node.getUserObject();
            String hql = "select rn from ReportNo rn,ReportDept rd where rn.deptCode_key=rd.deptCode_key and rn.reportGroup_key='" + rgroup.getReportGroup_key() + "'";
            hql += " and rn.ym='" + cur_ym + "' and rn.noState='δ�ύ' ";
            if (!UserContext.isSA) {
                hql += " and (rn.buserNo='" + UserContext.person_code + "' or rd.tuserNo='" + UserContext.person_code + "' or rd.suserNo='" + UserContext.person_code + "')";
            }
            hql += " and rd.reportDept_key in";
            boolean checked = checkSQL((List<ReportNo>) CommUtil.fetchEntities(hql, keys), true);
            if (!checked) {
                return;
            }
        }
        ReportGroup rg = (ReportGroup) cur_node.getUserObject();
        String old_type = "";
        String new_type = "δ�ύ";
        List list = CommUtil.selectSQL("select reportDept_key,tuserno,suserno,needCheck from ReportDept where reportDept_key in ", depts);
        if (type.equals("�ύ")) {
            old_type = "δ�ύ";
            new_type = "���ύ";
        } else if (type.equals("���ȡ��")) {
            old_type = "���ͨ��";
        } else if (type.equals("ȡ���ύ") || type.equals("��˲���")) {
            old_type = "���ύ";
        } else if (type.equals("���ͨ��")) {
            old_type = "���ύ";
            new_type = "���ͨ��";
        } else if (type.equals("�浵")) {
            old_type = "���ͨ��";
            new_type = "�Ѵ浵";
        }
        List<String> result = new ArrayList<String>();
        for (Object obj : list) {
            Object[] objs = (Object[]) obj;
            String key = objs[0].toString();
            String tuser = objs[1] == null ? "" : objs[1].toString();
            String suser = objs[2] == null ? "" : objs[2].toString();
            boolean needcheck = objs[3] == null ? false : ("1".equals(objs[3].toString()));
            if (type.equals("�ύ") || type.equals("ȡ���ύ")) {
                if (UserContext.isSA || tuser.equals(UserContext.person_code)) {
                    result.add(key);
                }
            } else if (needcheck && (type.equals("���ȡ��") || type.equals("��˲���") || type.equals("���ͨ��"))) {
                if (UserContext.isSA || suser.equals(UserContext.person_code)) {
                    result.add(key);
                }
            } else if (type.equals("�浵")) {
                result.add(key);
            }
        }
        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(null, "�����߱���ǰѡ���ŵ� " + type + " Ȩ��");
            return;
        }
        if (type.equals("�浵") && JOptionPane.showConfirmDialog(ContextManager.getMainFrame(),
                "�ò������浵��ǰ���ţ��浵��õ������ݲ��������κα����ȷ��Ҫ�浵��", "ѯ��", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
            return;
        }
        upReport(result, old_type, new_type, type, rg, cur_ym);
    }

    private void fetchNo(ReportDept rd, String ym, String type, QueryScheme qs) {
        if (!(cur_node.getUserObject() instanceof ReportGroup)) {
            return;
        }
        ReportGroup rgroup = (ReportGroup) cur_node.getUserObject();
        ftable_no.setCur_query_scheme(qs);
        ((FTableModel) ftable_no.getModel()).getHt_OtherTable().clear();
        ((FTableModel) ftable_no.getModel()).getHt_OtherTableSql();
        if (rd == null) {
            ftable_no.deleteAllRows();
            return;
        }
        String hql = "select reportNo_key from ReportNo,ReportDept,DeptCode,ReportDef where ReportDef.reportDef_key=ReportNo.reportDef_key and ReportDept.deptCode_key=DeptCode.deptCode_key and ReportNo.deptCode_key=ReportDept.deptCode_key and ReportNo.reportGroup_key='" + rgroup.getReportGroup_key() + "'";
        hql += " and ReportNo.ym='" + ym + "' and ReportNo.noState='" + getNoState(type) + "'  and ReportDept.reportDept_key= '" + rd.getReportDept_key() + "' ";
        if (!UserContext.isSA) {
            hql += " and (ReportNo.buserNo='" + UserContext.person_code + "' or ReportDept.tuserNo='" + UserContext.person_code + "' or ReportDept.suserNo='" + UserContext.person_code + "')";
        }
        if (qs != null) {
            hql += " and ReportNo.reportNo_key in(" + qs.buildSql() + ")";
        }
        hql += " order by " + no_order_sql;
        PublicUtil.getProps_value().setProperty(ReportNo.class.getName(), "from ReportNo rn where rn.reportNo_key in");
        ftable_no.setObjects(CommUtil.selectSQL(hql));
    }

    private String getNoState(String type) {
        if (type.equals("�����")) {
            return "���ͨ��";
        }
        return type;
    }

    private void upReport(List<String> data, String old_type, String new_type, String type, ReportGroup rg, String ym) {
        FTable ftableDept = new FTable(ReportDept.class, ftable_dept.getFields(), false, false, false, module_code);
        PublicUtil.getProps_value().setProperty(ReportDept.class.getName(), "from ReportDept rd where rd.reportDept_key in");
        ftableDept.getOther_entitys().put("DeptCode", "ReportDept ReportDept,DeptCode DeptCode");
        ftableDept.getOther_entity_keys().put("DeptCode", "ReportDept.deptCode_key=DeptCode.deptCode_key and ReportDept.reportDept_key ");
        ftableDept.setAll_fields(ftable_dept.getAll_fields(), ftable_dept.getDefault_fields(), new ArrayList(), module_code);
        ftableDept.setObjects(data);
        ftableDept.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        JPanel pnlUp = new JPanel(new BorderLayout());
        pnlUp.add(ftableDept);
        pnlUp.setBorder(javax.swing.BorderFactory.createTitledBorder("�������ţ�"));
        JPanel pnlDown = new JPanel(new BorderLayout());
        HrTextPane textPane = new HrTextPane();
        pnlDown.add(new JLabel("������" + type + "˵����"), BorderLayout.NORTH);
        pnlDown.add(textPane, BorderLayout.CENTER);
        JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pnlUp, pnlDown);
        jsp.setDividerLocation(250);
        jsp.setDividerSize(1);
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.add(jsp);
        pnl.setPreferredSize(new Dimension(600, 500));

        if (ModalDialog.doModal(ContextManager.getMainFrame(), pnl, type)) {
            ReportLog rl = (ReportLog) UtilTool.createUIDEntity(ReportLog.class);
            rl.setCtype(type);
            rl.setA0101(UserContext.person_name);
            rl.setA0190(UserContext.person_code);
            rl.setCtext(textPane.getText());
            List<String> deptKeys = new ArrayList<String>();
            for (Object obj : ftableDept.getAllObjects()) {
                deptKeys.add(((ReportDept) obj).getDeptCode_key());
            }
            ValidateSQLResult result = ReportImpl.updateReportNoState(deptKeys, rl, old_type, new_type, rg.getReportGroup_key(), ym);
            if (result.getResult() == 0) {
                fetchDept(cur_ym, cur_type);
                JOptionPane.showMessageDialog(null, "����ɹ�");
            } else {
                MsgUtil.showHRSaveErrorMsg(result);
            }
        }
    }

    private boolean checkSQL(List<ReportNo> rns, boolean submit) {
        List<String> reportKeys = new ArrayList<String>();
        for (ReportNo rn : rns) {
            if (reportKeys.contains(rn.getReportDef_key())) {
                continue;
            }
            reportKeys.add(rn.getReportDef_key());
        }

        List<ReportParaSet> paras = (List<ReportParaSet>) CommUtil.fetchEntities("from ReportParaSet rps where rps.reportDef_key in", reportKeys);
        List<ReportRegula> rrs = (List<ReportRegula>) CommUtil.fetchEntities("from ReportRegula rr where used=1 and rr.reportDef_key in", reportKeys);
        if (rrs == null || rrs.isEmpty()) {
            return true;
        } else {
            StringBuilder msg = new StringBuilder();
            Hashtable<String, List<ReportRegula>> rrKeys = new Hashtable<String, List<ReportRegula>>();
            Hashtable<String, List<ReportParaSet>> paraKeys = new Hashtable<String, List<ReportParaSet>>();
            for (ReportRegula rr : rrs) {
                List<ReportRegula> list = rrKeys.get(rr.getReportDef_key());
                if (list == null) {
                    list = new ArrayList<ReportRegula>();
                }
                list.add(rr);
                rrKeys.put(rr.getReportDef_key(), list);
            }
            for (ReportParaSet para : paras) {
                List<ReportParaSet> list = paraKeys.get(para.getReportDef_key());
                if (list == null) {
                    list = new ArrayList<ReportParaSet>();
                    paraKeys.put(para.getReportDef_key(), list);
                }
                list.add(para);
            }
            for (ReportNo rn : rns) {
                List<ReportRegula> list = rrKeys.get(rn.getReportDef_key());
                if (list == null || list.isEmpty()) {
                    continue;
                }
                List<ReportParaSet> paraset = paraKeys.get(rn.getReportDef_key());
                Hashtable<String, String> values = new Hashtable<String, String>();
                if (paraset != null) {
                    for (ReportParaSet para : paraset) {
                        String pName = para.getParaname();
                        String pField = para.getParafield();
                        if (pField == null || pField.trim().equals("")) {
                            continue;
                        }
                        pField = pField.replace("@", "").replace("����", "dept_code2").replace("��/��/����", "ym");
                        Object obj = PublicUtil.getProperty(rn, pField);
                        values.put("@" + pName, obj == null ? "" : obj.toString());
                    }
                }
                int len = msg.toString().length();
                boolean error = false;
                for (ReportRegula rr : list) {
                    String text = rr.getR_text();
                    if (text == null || text.trim().equals("")) {
                        continue;
                    }
                    for (String key : values.keySet()) {
                        text = text.replace(key, "'" + values.get(key) + "'");
                    }
                    List data = CommUtil.selectSQL(text);
                    if (data != null && data.size() > 0 && data.get(0) != null) {
                        if (data.get(0).toString().equals("1")) {
                            continue;
                        }
                        msg.append("��ʾ��Ϣ��").append(data.get(0).toString()).append("\n");
                        rn.setChecked(false);
                        error = true;
                    }
                }
                if (error) {
                    msg.insert(len, "��֤���ݺţ�" + rn.getCno() + "���ִ��󣬴�����Ϣ���£�\n");
                }
            }
            boolean success = msg.toString().equals("");
            String checkMsg = success ? "���й�����֤ͨ��" : msg.toString();
            if (!success || !submit) {
                MsgUtil.showHRValidateMsg(checkMsg, "У�鱨�棺", success);
            }
            return success;
        }
    }

    private void setUser(List<String> rns) {
        if (rns == null || rns.isEmpty()) {
            JOptionPane.showMessageDialog(null, "δѡ���κε���");
            return;
        }
        if (!UserContext.isSA) {
            if (!CommUtil.exists("select 1 from ReportDept rd where rd.tuserNo='" + UserContext.person_code + "' and rd.deptCode_key='" + deptCode.getDeptCode_key() + "'")) {
                JOptionPane.showMessageDialog(null, "ֻ���ύ�û��ſ������ð���Ա��");
                return;
            }
        }
        if (user_select_pnl == null) {
            user_select_pnl = new StepWorkUserSelectPanel();
        }
        user_select_pnl.buildTree("Ա��", new HashSet());
        if (!ModalDialog.doModal(ContextManager.getMainFrame(), user_select_pnl, "���ð���Ա�û���")) {
            return;
        }
        A01PassWord apw = (A01PassWord) user_select_pnl.getFTable().getCurrentRow();
        if (apw == null || apw.getA01PassWord_key() == null) {
            JOptionPane.showMessageDialog(null, "δѡ���κ��û�");
            return;
        }
        ReportLog rl = (ReportLog) UtilTool.createUIDEntity(ReportLog.class);
        rl.setA0190(apw.getA01().getA0190());
        rl.setA0101(apw.getA01().getA0101());
        ValidateSQLResult result = ReportImpl.setReportNoUser(rns, rl);
        if (result.getResult() == 0) {
            pnlDeptData.updateUI();
            JOptionPane.showMessageDialog(null, "���óɹ�");
            fetchNo(cur_dept, cur_ym, cur_type, null);
        } else {
            MsgUtil.showHRSaveErrorMsg(result);
        }
    }

    private void delUser() {
        List<String> keys = ftable_no.getSelectKeys();
        if (keys.isEmpty()) {
            JOptionPane.showMessageDialog(null, "δѡ���κε���");
            return;
        }
        if (JOptionPane.showConfirmDialog(ContextManager.getMainFrame(),
                "ȷ��Ҫȡ����ǰѡ�񵥺ŵİ���Ա��", "ѯ��", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
            return;
        }
        ValidateSQLResult result = CommUtil.excuteSQLs("update ReportNo set buserNo=null,buserName=null where reportNo_key in", keys);
        if (result.getResult() == 0) {
            JOptionPane.showMessageDialog(null, "ȡ���ɹ�");
            fetchNo(cur_dept, cur_ym, cur_type, null);
        } else {
            MsgUtil.showHRSaveErrorMsg(result);
        }
    }

    private void setMainState() {
        String type = getNoState(cur_type);
        boolean enable_back = type.equals("���ͨ��") || type.equals("���ύ");
        btnUp.setEnabled(UserContext.hasFunctionRight(module_code + ".btnUp") && (type.equals("δ�ύ") || type.equals("���ύ")));
        btnCheck.setEnabled(UserContext.hasFunctionRight(module_code + ".btnCheck") && enable_back);
        btnBackReport.setEnabled(UserContext.hasFunctionRight(module_code + ".btnBackReport") && enable_back);
        miDataUp.setEnabled(UserContext.hasFunctionRight(module_code + ".miDataUp") && type.equals("δ�ύ"));
        miDataCancel.setEnabled(UserContext.hasFunctionRight(module_code + ".miDataCancel") && type.equals("���ύ"));
        miCheck.setEnabled(UserContext.hasFunctionRight(module_code + ".miCheck") && type.equals("���ύ"));
        miCheckNot.setEnabled(UserContext.hasFunctionRight(module_code + ".miCheckNot") && type.equals("���ύ"));
        miCheckCancel.setEnabled(UserContext.hasFunctionRight(module_code + ".miCheckCancel") && type.equals("���ͨ��"));
        btnCreateData.setEnabled(UserContext.hasFunctionRight(module_code + ".btnCreateData") && (type.equals("δ����") || type.equals("δ�ύ")));
        btnDel.setEnabled(UserContext.hasFunctionRight(module_code + ".btnDel") && (type.equals("δ����") || type.equals("δ�ύ")));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jspMain = new javax.swing.JSplitPane();
        pnlLeft = new javax.swing.JPanel();
        jspLeft = new javax.swing.JSplitPane();
        pnlGroup = new javax.swing.JPanel();
        pnlDept = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        toolbar = new javax.swing.JToolBar();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel6 = new javax.swing.JLabel();
        jcbYm = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jcbType = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane3 = new javax.swing.JSplitPane();
        pnlDeptData = new javax.swing.JPanel();
        jtpMain = new javax.swing.JTabbedPane();
        pnlNo = new javax.swing.JPanel();
        pnlLog = new javax.swing.JPanel();

        jspMain.setDividerLocation(200);
        jspMain.setDividerSize(4);

        jspLeft.setDividerLocation(250);
        jspLeft.setDividerSize(2);
        jspLeft.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        pnlGroup.setBorder(javax.swing.BorderFactory.createTitledBorder("���ʣ�"));
        pnlGroup.setLayout(new java.awt.BorderLayout());
        jspLeft.setTopComponent(pnlGroup);

        pnlDept.setBorder(javax.swing.BorderFactory.createTitledBorder("��λ��"));
        pnlDept.setLayout(new java.awt.BorderLayout());
        jspLeft.setRightComponent(pnlDept);

        javax.swing.GroupLayout pnlLeftLayout = new javax.swing.GroupLayout(pnlLeft);
        pnlLeft.setLayout(pnlLeftLayout);
        pnlLeftLayout.setHorizontalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspLeft, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
        );
        pnlLeftLayout.setVerticalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspLeft, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
        );

        jspMain.setLeftComponent(pnlLeft);

        toolbar.setFloatable(false);
        toolbar.setRollover(true);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jLabel6.setText(" ��/��/���ȣ�");
        jToolBar1.add(jLabel6);

        jcbYm.setMaximumSize(new java.awt.Dimension(100, 32767));
        jToolBar1.add(jcbYm);

        jLabel7.setText(" ״̬��");
        jToolBar1.add(jLabel7);

        jcbType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "�޵���", "δ����", "δ�ύ", "���ύ", "�����", "�Ѵ浵" }));
        jcbType.setSelectedItem("δ�ύ");
        jcbType.setMaximumSize(new java.awt.Dimension(100, 32767));
        jToolBar1.add(jcbType);

        jSplitPane3.setDividerLocation(300);
        jSplitPane3.setDividerSize(4);
        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        pnlDeptData.setBorder(javax.swing.BorderFactory.createTitledBorder("��λ�б�"));
        pnlDeptData.setLayout(new java.awt.BorderLayout());
        jSplitPane3.setTopComponent(pnlDeptData);

        pnlNo.setLayout(new java.awt.BorderLayout());
        jtpMain.addTab("�����б�", pnlNo);

        pnlLog.setLayout(new java.awt.BorderLayout());
        jtpMain.addTab("������־", pnlLog);

        jSplitPane3.setRightComponent(jtpMain);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jspMain.setRightComponent(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMain, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMain, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JComboBox jcbType;
    private javax.swing.JComboBox jcbYm;
    private javax.swing.JSplitPane jspLeft;
    private javax.swing.JSplitPane jspMain;
    private javax.swing.JTabbedPane jtpMain;
    private javax.swing.JPanel pnlDept;
    private javax.swing.JPanel pnlDeptData;
    private javax.swing.JPanel pnlGroup;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlLog;
    private javax.swing.JPanel pnlNo;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setFunctionRight() {
        setMainState();
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
