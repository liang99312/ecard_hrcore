/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ReportRulePanel.java
 *
 * Created on 2011-12-18, 15:05:19
 */
package org.jhrcore.client.report.comm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jdesktop.beansbinding.BindingGroup;
import org.jhrcore.client.CommUtil;
import org.jhrcore.comm.HrLog;
import org.jhrcore.util.SysUtil;
import org.jhrcore.entity.AutoNoRule;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.impl.RSImpl;
import org.jhrcore.ui.EditorFactory;
import org.jhrcore.ui.HrTextPane;
import org.jhrcore.ui.action.CloseAction;
import org.jhrcore.ui.renderer.HRRendererView;
import org.jhrcore.util.MsgUtil;

/**
 *
 * @author mxliteboss
 */
public class ReportNoRulePanel extends javax.swing.JPanel {

    private HrTextPane jtaFormulaText;
    private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("参数列表");
    private JTree para_tree;
    private AutoNoRule cur_rule = null;
    private BindingGroup bindingGroupC = new BindingGroup();
    private HrLog log = new HrLog(ReportNoRulePanel.class.getName());

    /** Creates new form ReportRulePanel */
    public ReportNoRulePanel() {
        initComponents();
        initOthers();
        setupEvents();
    }

    private void initOthers() {
        cur_rule = (AutoNoRule) CommUtil.fetchEntityBy("from AutoNoRule anr where anr.autoNoRule_id='ReportNo'");
        if (cur_rule == null) {
            cur_rule = new AutoNoRule();
            cur_rule.setAutoNoRule_key("ReportNo");
            cur_rule.setAutoNoRule_id("ReportNo");
            cur_rule.setInc_no(1);
            cur_rule.setNo_lenth(3);
            cur_rule.setInit_no(1);
            cur_rule.setAutoNoRule_name("报表单号规则");
            CommUtil.saveOrUpdate(cur_rule);
        }
        String[] paras = new String[]{"@报表编码", "@年份", "@月份", "@日期"};
        List tmp_list = new ArrayList();
        Hashtable<String, List> lookups = new Hashtable<String, List>();
        Hashtable<String, String> keyword_groups = new Hashtable<String, String>();
        Hashtable<String, String> k_keywords = new Hashtable<String, String>();
        for (String para : paras) {
            rootNode.add(new DefaultMutableTreeNode(para));
            k_keywords.put(para, para);
            keyword_groups.put(para, "常量参数");
            tmp_list.add(para);
        }
        lookups.put("常量参数", tmp_list);
        para_tree = new JTree(rootNode);
        HRRendererView.getCommMap().initTree(para_tree);
        pnlPara.add(new JScrollPane(para_tree), BorderLayout.CENTER);
        jtaFormulaText = new HrTextPane();
        pnlEditor.add(jtaFormulaText);
        jtaFormulaText.revokeDocumentKeys(lookups, keyword_groups, k_keywords);
        bindingGroupC.addBinding(EditorFactory.createComponentBinding(cur_rule, "no_lenth", jtfLen, "text_ON_ACTION_OR_FOCUS_LOST"));
        bindingGroupC.addBinding(EditorFactory.createComponentBinding(cur_rule, "init_no", jtfInit_no, "text_ON_ACTION_OR_FOCUS_LOST"));
        bindingGroupC.addBinding(EditorFactory.createComponentBinding(cur_rule, "inc_no", jtfInc_no, "text_ON_ACTION_OR_FOCUS_LOST"));
        bindingGroupC.bind();
        refreshBeanUI();
    }

    private void setupEvents() {
        para_tree.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!jtaFormulaText.isEnabled()) {
                    return;
                }
                if (e.getClickCount() >= 2) {
                    if (para_tree.getSelectionPath() == null) {
                        return;
                    }

                    if (para_tree.getSelectionPath().getLastPathComponent() == para_tree.getModel().getRoot()) {
                        return;
                    }
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) para_tree.getSelectionPath().getLastPathComponent();
                    int tmp = jtaFormulaText.getSelectionStart();
                    String operator = node.getUserObject().toString();
                    jtaFormulaText.replaceSelection(operator);
                    jtaFormulaText.setCaretPosition(tmp + operator.length());
                    jtaFormulaText.requestFocus();
                }
            }
        });
        btnSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                saveObj(cur_rule);
            }
        });
        btnPriview.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cur_rule == null) {
                    return;
                }
                log.info(e);
                cur_rule.setAdd_perfix(true);
                Hashtable<String, String> ht = new Hashtable<String, String>();
                ht.put("@报表编码", "'Report'");
                String tmp = CommUtil.fetchNewNoBy(cur_rule, ht);
                if (tmp == null) {
                    JOptionPane.showMessageDialog(null, "前缀存在语法错误，无法生成新编码，保存失败!");
                    log.info("前缀存在语法错误，无法生成新编码，保存失败");
                    return;
                }
                String msg = "当前规则：\n";
                msg += "初始值：" + cur_rule.getInit_no() + " 序号位数：" + cur_rule.getNo_lenth() + " 增长值：" + cur_rule.getInc_no() + " 编码方式：" + cur_rule.getNo_unit() + "\n";
                msg += "新号码：\n";
                msg += "      " + tmp + "\n";
                String new_no = tmp.substring(tmp.length() - cur_rule.getNo_lenth());
                int no = SysUtil.objToInt(new_no);
                no = no + cur_rule.getInc_no();
                new_no = no + "";
                int i = new_no.length();
                while (i < cur_rule.getNo_lenth()) {
                    new_no = "0" + new_no;
                    i++;
                }
                msg += "      " + tmp.substring(0, tmp.length() - cur_rule.getNo_lenth()) + new_no + "\n      ...";
                JOptionPane.showMessageDialog(null, msg);
            }
        });
        jComboBox1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cur_rule == null) {
                    return;
                }
                cur_rule.setNo_unit(jComboBox1.getSelectedItem().toString());
            }
        });
        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cur_rule == null) {
                    return;
                }
                AutoNoRule anr = (AutoNoRule) CommUtil.fetchEntityBy("from AutoNoRule anr where anr.autoNoRule_key='ReportNo'");
                if (anr != null) {
                    cur_rule = anr;
                    refreshBeanUI();
                }
            }
        });
        CloseAction.doCloseAction(btnClose);
    }

    private void refreshBeanUI() {
        jComboBox1.setSelectedItem(cur_rule.getNo_unit());
        jtaFormulaText.setText(cur_rule.getPerfix());
        bindingGroupC.unbind();
        bindingGroupC.bind();
    }

    /**
     * 该方法用于保存指定规则
     * @param anr：当前规则
     */
    private void saveObj(AutoNoRule anr) {
        anr.setPerfix(jtaFormulaText.getText());
        anr.setAdd_perfix(true);
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("@报表编码", "'Report'");
        String preview_no = CommUtil.fetchNewNoBy(anr, ht);
        if (preview_no == null) {
            log.info("前缀存在语法错误，无法生成新编码，保存失败");
            JOptionPane.showMessageDialog(null, "前缀存在语法错误，无法生成新编码，保存失败!");
            return;
        } else if (preview_no.startsWith("@@@")) {
            JOptionPane.showMessageDialog(null, "当前初始值A：" + anr.getInit_no() + ",数据库当前值B:" + preview_no.substring(3) + ",A不能小于B，否则导致生成序号重复!");
            return;
        }
        ValidateSQLResult result = RSImpl.saveEmpNoRule(anr, "ReportNo");
        if (result.getResult() == 0) {
            JOptionPane.showMessageDialog(null, "保存成功!");
            log.info("保存成功");
        } else {
            log.info("保存失败");
            MsgUtil.showHRSaveErrorMsg(result);
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

        jToolBar1 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnPriview = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jtfInit_no = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jtfInc_no = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtfLen = new javax.swing.JTextField();
        pnlEditor = new javax.swing.JPanel();
        pnlPara = new javax.swing.JPanel();

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnSave.setText("保存");
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSave);

        btnCancel.setText("取消");
        btnCancel.setFocusable(false);
        btnCancel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnCancel);

        btnPriview.setText("预览编号");
        jToolBar1.add(btnPriview);

        btnClose.setText("关闭");
        jToolBar1.add(btnClose);

        jSplitPane2.setDividerLocation(500);
        jSplitPane2.setDividerSize(2);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("规则设定："));

        jLabel5.setText("初始值  ");

        jtfInit_no.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel6.setText("增长值");

        jtfInc_no.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel3.setText("前缀 ");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "顺序编码", "按年", "按月", "按天" }));

        jLabel7.setText(" 编码方式");

        jLabel4.setText(" 序号位数");

        jtfLen.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        pnlEditor.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtfInc_no, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(jtfInit_no, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBox1, 0, 135, Short.MAX_VALUE)
                            .addComponent(jtfLen, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)))
                    .addComponent(pnlEditor, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfInit_no, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfLen, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfInc_no, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58))
                    .addComponent(pnlEditor, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jSplitPane2.setLeftComponent(jPanel4);

        pnlPara.setBorder(javax.swing.BorderFactory.createTitledBorder("常用前缀参数："));
        pnlPara.setLayout(new java.awt.BorderLayout());
        jSplitPane2.setRightComponent(pnlPara);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(436, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnPriview;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField jtfInc_no;
    private javax.swing.JTextField jtfInit_no;
    private javax.swing.JTextField jtfLen;
    private javax.swing.JPanel pnlEditor;
    private javax.swing.JPanel pnlPara;
    // End of variables declaration//GEN-END:variables
}
