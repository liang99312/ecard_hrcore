/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ReportRulePanel.java
 *
 * Created on 2011-12-23, 11:36:20
 */
package org.jhrcore.client.report.comm;

import com.foundercy.pf.control.table.FTable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jhrcore.comm.BeanManager;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.client.report.ReportModel;
import org.jhrcore.util.UtilTool;
import org.jhrcore.entity.report.ReportDef;
import org.jhrcore.entity.report.ReportRegula;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.impl.ReportImpl;
import org.jhrcore.mutil.ReportUtil;
import org.jhrcore.ui.BeanPanel;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.ui.HrTextPane;
import org.jhrcore.ui.ModalDialog;
import org.jhrcore.ui.ModelFrame;
import org.jhrcore.ui.ValidateEntity;
import org.jhrcore.ui.listener.IPickBeanPanelEditListener;
import org.jhrcore.ui.renderer.HRRendererView;
import org.jhrcore.util.ComponentUtil;
import org.jhrcore.util.MsgUtil;

/**
 *
 * @author mxliteboss
 */
public class ReportRulePanel extends javax.swing.JPanel {

    private HrTextPane textPane = new HrTextPane();
    private FTable ftable_rule;
    private List<ReportDef> rds;
    private List modules;
    private JTree reportTree = null;
    private JTree paraTree = null;
    private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("参数列表");
    private ReportDef reportDef = null;
    private String module_code = "ReportNo.rule";

    /** Creates new form ReportRulePanel */
    public ReportRulePanel(List modules, List<ReportDef> rds) {
        this.modules = modules;
        this.rds = rds;
        initComponents();
        initOthers();
        setupEvents();
    }

    private void initOthers() {
        ReportModel reportModel = new ReportModel(modules, rds, UserContext.role_id);
        reportTree = new JTree(reportModel);
        HRRendererView.getReportMap(reportTree).initTree(reportTree);
        pnlReport.add(new JScrollPane(reportTree));
        pnlText.add(textPane);
        ftable_rule = new FTable(ReportRegula.class, false, false, false, "ReportRulePanel");
        pnlRule.add(ftable_rule);
        paraTree = new JTree(rootNode);
        HRRendererView.getCommMap().initTree(paraTree);
        pnlPara.add(new JScrollPane(paraTree));
        ComponentUtil.setSysFuntion(this, module_code);
    }

    private void setupEvents() {
        paraTree.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!textPane.isEnabled()) {
                    return;
                }
                if (e.getClickCount() >= 2) {
                    if (paraTree.getSelectionPath() == null) {
                        return;
                    }

                    if (paraTree.getSelectionPath().getLastPathComponent() == paraTree.getModel().getRoot()) {
                        return;
                    }
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) paraTree.getSelectionPath().getLastPathComponent();
                    int tmp = textPane.getSelectionStart();
                    String operator = node.getUserObject().toString();
                    textPane.replaceSelection(operator);
                    textPane.setCaretPosition(tmp + operator.length());
                    textPane.requestFocus();
                }
            }
        });

        reportTree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                reportDef = null;
                if (e.getPath() == null | e.getPath().getLastPathComponent() == null) {
                    return;
                }
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
                if (node.getUserObject() instanceof ReportDef) {
                    reportDef = (ReportDef) node.getUserObject();
                }
                refreshMain(reportDef);
            }
        });
        btnAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addRule(reportDef);
            }
        });
        btnEdit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                editRule((ReportRegula) ftable_rule.getCurrentRow());
            }
        });
        btnSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ftable_rule.editingStopped();
                ReportRegula rr = (ReportRegula) ftable_rule.getCurrentRow();
                if (rr == null || rr.getReportRegula_key() == null) {
                    return;
                }
                checkSQL(rr);
                rr.setR_text(textPane.getText());
                ValidateSQLResult result = CommUtil.updateEntity(rr);
                if (result.getResult() == 0) {
                    MsgUtil.showHRSaveSuccessMsg(JOptionPane.getFrameForComponent(btnSave));
                } else {
                    MsgUtil.showHRSaveErrorMsg(result);
                }
            }
        });
        ftable_rule.addListSelectionListener(new ListSelectionListener() {

            ReportRegula rr = null;

            @Override
            public void valueChanged(ListSelectionEvent e) {
                BeanManager.updateEntity(rr, true);
                if (rr == ftable_rule.getCurrentRow()) {
                    return;
                }
                rr = (ReportRegula) ftable_rule.getCurrentRow();
                if (rr != null) {
                    textPane.setText(rr.getR_text());
                }
            }
        });
        btnDel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                delRule(ftable_rule.getSelectKeys());
            }
        });
        btnClear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                textPane.setText("");
            }
        });
        btnCheck.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                checkSQL((ReportRegula) ftable_rule.getCurrentRow());
            }
        });
        btnCopy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                copy(reportDef, ftable_rule.getSelectKeys());
            }
        });
        btnQuit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ModelFrame.close();
            }
        });
    }

    private void refreshMain(ReportDef rd) {
        refreshRule(rd);
        refreshPara(rd);
    }

    private void refreshRule(ReportDef rd) {
        if (rd == null || rd.getReportDef_key() == null) {
            ftable_rule.deleteAllRows();
        } else {
            ftable_rule.setObjects(CommUtil.fetchEntities("from ReportRegula rr where rr.reportDef_key='" + rd.getReportDef_key() + "' order by rr.order_no"));
        }
    }

    private void refreshPara(ReportDef rd) {
        rootNode.removeAllChildren();
        if (rd != null) {
            List list = CommUtil.fetchEntities("select rps.paraname from ReportParaSet rps where rps.reportDef_key='" + rd.getReportDef_key() + "' and rps.parafield is not null");
            List tmp_list = new ArrayList();
            Hashtable<String, List> lookups = new Hashtable<String, List>();
            Hashtable<String, String> keyword_groups = new Hashtable<String, String>();
            Hashtable<String, String> k_keywords = new Hashtable<String, String>();
            for (Object para : list) {
                para = "@" + para;
                rootNode.add(new DefaultMutableTreeNode(para));
                k_keywords.put(para.toString(), para.toString());
                keyword_groups.put(para.toString(), "常量参数");
                tmp_list.add(para);
            }
            lookups.put("常量参数", tmp_list);
            textPane.revokeDocumentKeys(lookups, keyword_groups, k_keywords);
        }
        paraTree.updateUI();
    }

    private void addRule(final ReportDef rd) {
        if (rd == null) {
            return;
        }
        IPickBeanPanelEditListener listener = new IPickBeanPanelEditListener() {

            @Override
            public void pickSave(Object obj) {
                ValidateSQLResult result = CommUtil.saveEntity(obj);
                if (result.getResult() == 0) {
                    JOptionPane.showMessageDialog(null, "保存成功");
                    int i = ftable_rule.getObjects().size();
                    ftable_rule.addObject(obj);
                    ftable_rule.setRowSelectionInterval(i, i);
                } else {
                    MsgUtil.showHRSaveErrorMsg(result);
                }
            }

            @Override
            public Object getNew() {
                ReportRegula rr = (ReportRegula) UtilTool.createUIDEntity(ReportRegula.class);
                int order_no = 0;
                Object obj = CommUtil.fetchEntityBy("select max(order_no) from ReportRegula rr where rr.reportDef_key='" + rd.getReportDef_key() + "'");
                if (obj != null) {
                    order_no = SysUtil.objToInt(obj);
                }
                order_no++;
                rr.setOrder_no(order_no);
                rr.setReportDef_key(rd.getReportDef_key());
                return rr;
            }
        };
        BeanPanel.editForRepeat(ContextManager.getMainFrame(), Arrays.asList(new String[]{"order_no", "r_name", "used"}), "新增规则", getVE(), listener);
    }

    private void editRule(ReportRegula rr) {
        if (rr == null || rr.getReportRegula_key() == null) {
            return;
        }
        if (BeanPanel.edit(rr, getVE())) {
            ValidateSQLResult result = CommUtil.updateEntity(rr);
            if (result.getResult() == 0) {
                MsgUtil.showHRSaveSuccessMsg(JOptionPane.getFrameForComponent(btnEdit));
                ftable_rule.updateUI();
            } else {
                MsgUtil.showHRSaveErrorMsg(result);
            }
        }
    }

    private void delRule(List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return;
        }
        if (JOptionPane.showConfirmDialog(JOptionPane.getFrameForComponent(btnDel),
                "确定要删除选择的规则吗", "询问", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
            return;
        }
        ValidateSQLResult result = CommUtil.deleteObjs("ReportRegula", "reportRegula_key", keys);
        if (result.getResult() == 0) {
            ftable_rule.deleteSelectedRows();
        } else {
            MsgUtil.showHRDelErrorMsg(result);
        }
    }

    private void copy(ReportDef reportDef, List<String> ruleKeys) {
        if (reportDef == null || reportDef.getReportDef_key() == null || ruleKeys == null || ruleKeys.isEmpty()) {
            return;
        }
        ReportModel reportModel = new ReportModel(modules, rds, UserContext.role_id);
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.add(new JScrollPane(ReportUtil.getReportTree(reportModel)));
        pnl.setPreferredSize(new Dimension(400, 350));
        if (ModalDialog.doModal(JOptionPane.getFrameForComponent(btnCopy), pnl, "")) {
            List checkedObjs = ComponentUtil.getCheckedObjs(reportModel);
            if (checkedObjs.isEmpty()) {
                return;
            }
            List<String> keys = new ArrayList<String>();
            for (Object cobj : checkedObjs) {
                if (cobj instanceof ReportDef) {
                    keys.add(((ReportDef) cobj).getReportDef_key());
                }
            }
            keys.remove(reportDef.getReportDef_key());
            if (keys.isEmpty()) {
                JOptionPane.showMessageDialog(null, "未选择任何报表");
                return;
            }
            ValidateSQLResult result = ReportImpl.copyRule(ruleKeys, keys);
            if (result.getResult() == 0) {
                JOptionPane.showMessageDialog(null, "复制成功");
            } else {
                MsgUtil.showHRSaveErrorMsg(result);
            }
        }
    }

    private ValidateEntity getVE() {
        return new ValidateEntity() {

            @Override
            public boolean isEntityValidate(Object obj) {
                ReportRegula rr = (ReportRegula) obj;
                if (rr.getR_name() == null || rr.getR_name().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "规则名称不能为空");
                    return false;
                }
                if (CommUtil.exists("select 1 from ReportRegula rr where rr.r_name='" + rr.getR_name().replace("'", "''") + "' and rr.reportRegula_key!='" + rr.getReportRegula_key() + "' and rr.reportDef_key='" + rr.getReportDef_key() + "'")) {
                    JOptionPane.showMessageDialog(null, "规则名称不能重复");
                    return false;
                }
                return true;
            }
        };
    }

    private boolean checkSQL(ReportRegula rr) {
        if (rr == null || rr.getReportRegula_key() == null) {
            return false;
        }
        String text = textPane.getText();
        rr.setR_text(text);
        rr.setUsed(true);
        if (text == null || text.trim().equals("")) {
            return true;
        } else {
            String v_text = text;
            Enumeration enumt = rootNode.children();
            while (enumt.hasMoreElements()) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumt.nextElement();
                String name = node.getUserObject().toString();
                v_text = v_text.replace(name, "'123'");
            }
            ValidateSQLResult vs = CommUtil.validateSQL(v_text, false);
            boolean result = vs.getResult() == 0;
            String sql_msg = text;
            if (!result) {
                sql_msg += ";\n错误提示：\n    " + vs.getMsg();
            }
            rr.setUsed(result);
            MsgUtil.showHRValidateMsg(sql_msg, "", result);
            return result;
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        pnlReport = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        btnCopy = new javax.swing.JButton();
        btnQuit = new javax.swing.JButton();
        jSplitPane2 = new javax.swing.JSplitPane();
        pnlRule = new javax.swing.JPanel();
        jSplitPane3 = new javax.swing.JSplitPane();
        pnlPara = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        pnlText = new javax.swing.JPanel();
        btnClear = new javax.swing.JButton();
        btnCheck = new javax.swing.JButton();

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setDividerSize(1);

        pnlReport.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setLeftComponent(pnlReport);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnAdd.setText("新增规则");
        btnAdd.setFocusable(false);
        btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnAdd);

        btnEdit.setText("修改");
        btnEdit.setFocusable(false);
        btnEdit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnEdit);

        btnSave.setText("保存");
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSave);

        btnDel.setText("删除");
        btnDel.setFocusable(false);
        btnDel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnDel);

        btnCopy.setText("复制到其他表");
        btnCopy.setFocusable(false);
        btnCopy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCopy.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnCopy);

        btnQuit.setText("退出");
        btnQuit.setFocusable(false);
        btnQuit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnQuit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnQuit);

        jSplitPane2.setDividerLocation(200);
        jSplitPane2.setDividerSize(1);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        pnlRule.setLayout(new java.awt.BorderLayout());
        jSplitPane2.setTopComponent(pnlRule);

        jSplitPane3.setDividerLocation(150);
        jSplitPane3.setDividerSize(1);

        pnlPara.setLayout(new java.awt.BorderLayout());
        jSplitPane3.setLeftComponent(pnlPara);

        pnlText.setLayout(new java.awt.BorderLayout());

        btnClear.setText("清空");

        btnCheck.setText("确认校验");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnClear)
                .addGap(18, 18, 18)
                .addComponent(btnCheck)
                .addContainerGap(170, Short.MAX_VALUE))
            .addComponent(pnlText, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear)
                    .addComponent(btnCheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlText, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
        );

        jSplitPane3.setRightComponent(jPanel4);

        jSplitPane2.setRightComponent(jSplitPane3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCheck;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnCopy;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnQuit;
    private javax.swing.JButton btnSave;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel pnlPara;
    private javax.swing.JPanel pnlReport;
    private javax.swing.JPanel pnlRule;
    private javax.swing.JPanel pnlText;
    // End of variables declaration//GEN-END:variables
}
