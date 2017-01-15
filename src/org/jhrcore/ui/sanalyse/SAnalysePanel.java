/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.sanalyse;

import com.foundercy.pf.control.table.FTable;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.JListBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.base.EntityDef;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.query.QueryScheme;
import org.jhrcore.rebuild.EntityBuilder;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.ui.ModelFrame;
import org.jhrcore.ui.QueryClassPanel;
import org.jhrcore.ui.SelectFieldDialog;
import org.jhrcore.ui.ShowProcessDlg;
import org.jhrcore.util.MsgUtil;
import org.jhrcore.util.UtilTool;

/**
 *
 * @author Administrator
 */
public class SAnalysePanel extends javax.swing.JPanel {

    private SAnalyseScheme analyseScheme;
    private List<EntityDef> entityDefs = new ArrayList<EntityDef>();
    private List<TempFieldInfo> fieldList = new ArrayList<TempFieldInfo>();
    private List<String> analyseNameList = new ArrayList<String>();
    private JListBinding lb_fields;
    private List<SAnalyseField> analyseList = new ArrayList<SAnalyseField>();
    private JComboBoxBinding field_binding;
    private JComboBoxBinding table_binding;
    private Class cur_class = null;
    private FTable ftable;
    private boolean move_flag = false;

    /**
     * Creates new form SAnalysePanel
     */
    public SAnalysePanel(SAnalyseScheme analyseScheme) {
        this.analyseScheme = analyseScheme;
        initComponents();
        initOthers();
        setupEvents();
    }

    private void initOthers() {
        entityDefs.addAll(analyseScheme.getEntityDefs());
        table_binding = SwingBindings.createJComboBoxBinding(AutoBinding.UpdateStrategy.READ, entityDefs, cb_entity);
        table_binding.bind();
        field_binding = SwingBindings.createJComboBoxBinding(AutoBinding.UpdateStrategy.READ, fieldList, cb_field);
        field_binding.bind();
        lb_fields = SwingBindings.createJListBinding(AutoBinding.UpdateStrategy.READ, analyseList, afList);
        lb_fields.bind();
        btnQueryDept.setVisible(false);
    }

    private void setupEvents() {
        cb_entity.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tableChange();
            }
        });
        btnField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cur_class == null) {
                    return;
                }
                SelectFieldDialog dlg = new SelectFieldDialog(cur_class);
                ContextManager.locateOnMainScreenCenter(dlg);
                dlg.setVisible(true);
                if (dlg.IsClick_ok()) {
                    TempFieldInfo tfi = dlg.getSlectField();
                    if (tfi != null) {
                        cb_field.setSelectedItem(tfi);
                    }
                }
            }
        });
        btnAddDef.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addDef();
            }
        });
        btnDelDef.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                delDef();
            }
        });
        btnUp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                movePosition(-1);
            }
        });
        btnDown.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                movePosition(1);
            }
        });
        btnQuery.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                QueryScheme cur_qs = analyseScheme.getQueryScheme();
                if (cur_qs == null) {
                    cur_qs = (QueryScheme) UtilTool.createUIDEntity(QueryScheme.class);
                    cur_qs.setQueryScheme_key("JYTJFX");
                    cur_qs.setQueryEntity(analyseScheme.getMain_class().getSimpleName());
                    analyseScheme.setQueryScheme(cur_qs);
                }
                QueryClassPanel fPnl = new QueryClassPanel(cur_qs, analyseScheme.getMain_class(), 0);
                ModelFrame.showModel((JFrame) JOptionPane.getFrameForComponent(btnQuery), fPnl, true, "条件设置", 700, 540);
            }
        });
        btnQueryDept.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                QueryScheme cur_qs = analyseScheme.getQuerySchemeDept();
                if (cur_qs == null) {
                    cur_qs = (QueryScheme) UtilTool.createUIDEntity(QueryScheme.class);
                    cur_qs.setQueryScheme_key("JYTJFXDEPT");
                    cur_qs.setQueryEntity("DeptCode");
                    analyseScheme.setQuerySchemeDept(cur_qs);
                }
                QueryClassPanel fPnl = new QueryClassPanel(cur_qs, DeptCode.class, 0);
                ModelFrame.showModel((JFrame) JOptionPane.getFrameForComponent(btnQueryDept), fPnl, true, "条件设置", 700, 540);
            }
        });
        btnExec.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (analyseScheme.getAnalyseFields().isEmpty()) {
                    MsgUtil.showInfoMsg("请添加统计项目");
                    return;
                }
                setObjects();
            }
        });
        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ModelFrame.close((ModelFrame) JOptionPane.getFrameForComponent(btnCancel));
            }
        });
        btnExport.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ftable.exportData();
            }
        });
        if (!entityDefs.isEmpty()) {
            cb_entity.setSelectedIndex(0);
        }
    }

    private void addDef() {
        if (cb_field.getSelectedItem() == null) {
            return;
        }
        TempFieldInfo tfi = (TempFieldInfo) cb_field.getSelectedItem();
        SAnalyseField analyseField = new SAnalyseField();
        analyseField.setCode_name(tfi.getCode_type_name());
        analyseField.setEntity_name(tfi.getEntity_name());
        String field_name = tfi.getField_name().replace("_code_", "");
        String f_content = "";
        String f_style = "";
        if (rb_avg.isSelected()) {
            f_content = "[" + tfi.getCaption_name() + "]平均";
            f_style = "avg";
        } else if (rb_count.isSelected()) {
            f_content = "[" + tfi.getCaption_name() + "]计数";
            f_style = "count";
        } else if (rb_group.isSelected()) {
            f_content = "[" + tfi.getCaption_name() + "]分组";
            f_style = "group";
        } else if (rb_max.isSelected()) {
            f_content = "[" + tfi.getCaption_name() + "]最大";
            f_style = "max";
        } else if (rb_min.isSelected()) {
            f_content = "[" + tfi.getCaption_name() + "]最小";
            f_style = "min";
        } else if (rb_sum.isSelected()) {
            f_content = "[" + tfi.getCaption_name() + "]汇总";
            f_style = "sum";
        } else if (rb_asc.isSelected()) {
            f_content = "[" + tfi.getCaption_name() + "]升序";
            f_style = "asc";
        } else if (rb_desc.isSelected()) {
            f_content = "[" + tfi.getCaption_name() + "]降序";
            f_style = "desc";
        } else {
            MsgUtil.showInfoMsg("请选择字段统计类型");
            return;
        }
        if (analyseNameList.contains(tfi.getEntity_name() + "_" + field_name + "_" + f_style)) {
            MsgUtil.showInfoMsg("该字段统计已存在，请重新设置");
            return;
        }
        if (rb_asc.isSelected()) {
            if (analyseNameList.contains(tfi.getEntity_name() + "_" + field_name + "_desc")) {
                MsgUtil.showInfoMsg("该字段排序已存在，请重新设置");
                return;
            }
        } else if (rb_desc.isSelected()) {
            if (analyseNameList.contains(tfi.getEntity_name() + "_" + field_name + "_asc")) {
                MsgUtil.showInfoMsg("该字段排序已存在，请重新设置");
                return;
            }
        }
        analyseNameList.add(tfi.getEntity_name() + "_" + field_name + "_" + f_style);
        analyseField.setField_content(f_content);
        analyseField.setField_name(field_name);
        analyseField.setField_style(f_style);
        analyseField.setField_type(tfi.getField_type());
        if (rb_asc.isSelected()) {
            analyseScheme.getOrderFields().add(analyseField);
        } else if (rb_desc.isSelected()) {
            analyseScheme.getOrderFields().add(analyseField);
        } else {
            analyseScheme.getAnalyseFields().add(analyseField);
        }
        analyseList.add(analyseField);
        lb_fields.unbind();
        lb_fields.bind();
        afList.setSelectedIndex(analyseList.size() - 1);
        if (rb_group.isSelected() && tfi.getEntity_name().equals("DeptCode") && tfi.getField_name().equals("dept_code")
                && !"DeptCode".equals(analyseScheme.getMain_class().getSimpleName())) {
            btnQueryDept.setVisible(true);
            analyseScheme.setDept_flag(true);
        }
    }

    private void delDef() {
        int ind = afList.getSelectedIndex();
        if (afList.getSelectedValue() == null) {
            MsgUtil.showInfoMsg("请选择已设置字段");
            return;
        }
        SAnalyseField analyseField = (SAnalyseField) afList.getSelectedValue();
        if ("group".equals(analyseField.getField_style()) && analyseField.getEntity_name().equals("DeptCode")
                && analyseField.getField_name().equals("dept_code")
                && !"DeptCode".equals(analyseScheme.getMain_class().getSimpleName())) {
            btnQueryDept.setVisible(false);
            analyseScheme.setDept_flag(false);
        }
        analyseNameList.remove(analyseField.getEntity_name() + "_" + analyseField.getField_name() + "_" + analyseField.getField_style());
        analyseList.remove(analyseField);
        analyseScheme.getAnalyseFields().remove(analyseField);
        analyseScheme.getOrderFields().remove(analyseField);
        lb_fields.unbind();
        lb_fields.bind();
        if (ind < analyseList.size()) {
        } else {
            ind = ind - 1;
        }
        if (ind > -1 && analyseList.size() > 0) {
            afList.setSelectedIndex(ind);
        }
    }

    private void movePosition(int step) {
        if (afList.getSelectedValue() == null) {
            return;
        }
        int ind = afList.getSelectedIndex();
        int ind2 = ind + step;
        if (ind2 == -1) {
            ind2 = analyseList.size() - 1;
        }
        if (ind2 == analyseList.size()) {
            ind2 = 0;
        }
        Object obj1 = analyseList.get(ind);
        Object obj2 = analyseList.get(ind2);
        analyseList.set(ind2, (SAnalyseField) obj1);
        analyseList.set(ind, (SAnalyseField) obj2);
        lb_fields.unbind();
        lb_fields.bind();
        move_flag = true;
        afList.setSelectedIndex(ind2);
    }

    private void setAnalyseFields() {
        analyseScheme.getOrderFields().clear();
        analyseScheme.getAnalyseFields().clear();
        for (SAnalyseField analyseField : analyseList) {
            if ("asc".equals(analyseField.getField_style()) || "desc".equals(analyseField.getField_style())) {
                analyseScheme.getOrderFields().add(analyseField);
            } else {
                analyseScheme.getAnalyseFields().add(analyseField);
            }
        }
        move_flag = false;
    }

    private void tableChange() {
        EntityDef entityDef = (EntityDef) cb_entity.getSelectedItem();
        if (entityDef == null) {
            return;
        }
        fieldList.clear();
        try {
            Class c = Class.forName(entityDef.getPackageName() + "." + entityDef.getEntityName());
            cur_class = c;
            fieldList.addAll(EntityBuilder.getDeclareFieldInfoListOf(c, EntityBuilder.COMM_FIELD_VISIBLE));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SAnalysePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        field_binding.unbind();
        field_binding.bind();
    }

    private void setObjects() {
        ShowProcessDlg.startProcess(ContextManager.getMainFrame(), "正在执行");
        final Runnable run = new Runnable() {

            public void run() {
                if (move_flag) {
                    setAnalyseFields();
                }
                pnlFtable.removeAll();
                pnlFtable.setLayout(new BorderLayout());
                List result = analyseScheme.getResult();
                ShowProcessDlg.endProcess();
                zj_label.setText("总计：" + result.size());
                List<String> x_caption = new ArrayList<String>();
                for (SAnalyseField af : analyseScheme.getAnalyseFields()) {
                    if ("group".equals(af.getField_style())) {
                        x_caption.add(af.getField_content().replace("[", "").replace("]", "").replace("分组", ""));
                        if (af.getField_name().equals("dept_code") && af.getEntity_name().equals("DeptCode") && analyseScheme.isDept_flag()) {
                            x_caption.add("部门名称");
                        }
                    } else {
                        x_caption.add(af.getField_content());
                    }
                }
                ftable = new FTable(x_caption);
                ftable.setRight_allow_flag(true);
                ftable.removeAllFunItems();
                ftable.setObjects(result);
                pnlFtable.add(ftable, BorderLayout.CENTER);
                pnlFtable.updateUI();
            }
        };
        new Thread(run).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cb_field = new javax.swing.JComboBox();
        rb_count = new javax.swing.JRadioButton();
        rb_sum = new javax.swing.JRadioButton();
        rb_avg = new javax.swing.JRadioButton();
        btnAddDef = new javax.swing.JButton();
        btnDelDef = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        afList = new javax.swing.JList();
        btnField = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cb_entity = new javax.swing.JComboBox();
        rb_max = new javax.swing.JRadioButton();
        rb_min = new javax.swing.JRadioButton();
        btnUp = new javax.swing.JButton();
        btnDown = new javax.swing.JButton();
        btnExec = new javax.swing.JButton();
        btnQuery = new javax.swing.JButton();
        rb_group = new javax.swing.JRadioButton();
        rb_asc = new javax.swing.JRadioButton();
        rb_desc = new javax.swing.JRadioButton();
        btnQueryDept = new javax.swing.JButton();
        pnlFtable = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        zj_label = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnExport = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        jLabel1.setText("项目名");

        cb_field.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cb_field.setMaximumSize(new java.awt.Dimension(125, 22));
        cb_field.setMinimumSize(new java.awt.Dimension(125, 22));
        cb_field.setPreferredSize(new java.awt.Dimension(125, 22));
        cb_field.setVerifyInputWhenFocusTarget(false);

        buttonGroup1.add(rb_count);
        rb_count.setSelected(true);
        rb_count.setText("计数");

        buttonGroup1.add(rb_sum);
        rb_sum.setText("求和");

        buttonGroup1.add(rb_avg);
        rb_avg.setText("平均");

        btnAddDef.setText("添加项目");

        btnDelDef.setText("删除项目");

        afList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(afList);

        btnField.setText("jButton6");
        btnField.setMaximumSize(new java.awt.Dimension(21, 21));
        btnField.setMinimumSize(new java.awt.Dimension(21, 21));
        btnField.setPreferredSize(new java.awt.Dimension(21, 21));

        jLabel2.setText("数据表");

        cb_entity.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cb_entity.setMaximumSize(new java.awt.Dimension(153, 22));
        cb_entity.setMinimumSize(new java.awt.Dimension(153, 22));
        cb_entity.setPreferredSize(new java.awt.Dimension(153, 22));

        buttonGroup1.add(rb_max);
        rb_max.setText("最大");

        buttonGroup1.add(rb_min);
        rb_min.setText("最小");

        btnUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/move_up.png"))); // NOI18N
        btnUp.setMaximumSize(new java.awt.Dimension(21, 21));
        btnUp.setMinimumSize(new java.awt.Dimension(21, 21));
        btnUp.setPreferredSize(new java.awt.Dimension(21, 21));

        btnDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/move_down.png"))); // NOI18N
        btnDown.setMaximumSize(new java.awt.Dimension(21, 21));
        btnDown.setMinimumSize(new java.awt.Dimension(21, 21));
        btnDown.setPreferredSize(new java.awt.Dimension(21, 21));

        btnExec.setText("执行");

        btnQuery.setText("条件设置");

        buttonGroup1.add(rb_group);
        rb_group.setText("分组");

        buttonGroup1.add(rb_asc);
        rb_asc.setText("升序");

        buttonGroup1.add(rb_desc);
        rb_desc.setText("降序");

        btnQueryDept.setText("部门设置");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rb_group)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_asc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_desc)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnExec)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(rb_count)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(rb_sum)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(rb_avg))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cb_field, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cb_entity, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(rb_max)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(rb_min))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnAddDef)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnDelDef))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnQueryDept)
                                .addGap(5, 5, 5)
                                .addComponent(btnQuery)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cb_entity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cb_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_count)
                    .addComponent(rb_sum)
                    .addComponent(rb_avg))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_max)
                    .addComponent(rb_min))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_group)
                    .addComponent(rb_asc)
                    .addComponent(rb_desc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddDef)
                    .addComponent(btnDelDef))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnQuery)
                        .addComponent(btnQueryDept))
                    .addComponent(btnUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExec))
        );

        pnlFtable.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlFtable.setLayout(new java.awt.BorderLayout());

        zj_label.setText("总计：");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(zj_label)
                .addContainerGap(430, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(zj_label)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlFtable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFtable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnExport.setText("导出");

        btnCancel.setText("退出");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(467, Short.MAX_VALUE)
                .addComponent(btnExport)
                .addGap(49, 49, 49)
                .addComponent(btnCancel)
                .addGap(91, 91, 91))
            .addComponent(jSeparator1)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExport)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList afList;
    private javax.swing.JButton btnAddDef;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelDef;
    private javax.swing.JButton btnDown;
    private javax.swing.JButton btnExec;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnField;
    private javax.swing.JButton btnQuery;
    private javax.swing.JButton btnQueryDept;
    private javax.swing.JButton btnUp;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cb_entity;
    private javax.swing.JComboBox cb_field;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel pnlFtable;
    private javax.swing.JRadioButton rb_asc;
    private javax.swing.JRadioButton rb_avg;
    private javax.swing.JRadioButton rb_count;
    private javax.swing.JRadioButton rb_desc;
    private javax.swing.JRadioButton rb_group;
    private javax.swing.JRadioButton rb_max;
    private javax.swing.JRadioButton rb_min;
    private javax.swing.JRadioButton rb_sum;
    private javax.swing.JLabel zj_label;
    // End of variables declaration//GEN-END:variables
}
