/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ColumnReplacePanel.java
 *
 * Created on 2010-1-17, 20:28:06
 */
package org.jhrcore.ui;

import com.foundercy.pf.control.listener.IPickColumnReplaceListener;
import com.foundercy.pf.control.table.FTable;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.SysParameter;
import org.jhrcore.entity.annotation.FieldAnnotation;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.rebuild.EntityBuilder;
import org.jhrcore.ui.action.CloseAction;
import org.jhrcore.ui.listener.IPickReplaceListener;
import org.jhrcore.util.ComponentUtil;
import org.jhrcore.util.ImageUtil;
import org.jhrcore.util.MsgUtil;

/**
 *
 * @author mxliteboss
 */
public class ColumnReplacePanel extends javax.swing.JPanel {

    private FTable ftable;
    private boolean isAdvance_method = false;
    private IPickReplaceListener cur_listener;
    private List<IPickColumnReplaceListener> listeners = new ArrayList<IPickColumnReplaceListener>();
    private JComboBoxBinding field_binding;
    private List<TempFieldInfo> replace_fields = new ArrayList<TempFieldInfo>();
    private FTable ftable_field;
//    private List container_data = null;
//    private boolean isContainer_visible = false;
    private JPanel pnlField;
    private JButton btnSearch = new JButton("", ImageUtil.getSearchIcon());
    private JTextField jtfSearch = new JTextField();
    private String last_search_val = "";
    private int last_search_ind = 0;
    private List selectedList;
    public Set<String> disable_fields = new HashSet<String>();
    private SysParameter replace_field_para = null;

    public void addPickColumnReplaceListener(IPickColumnReplaceListener listener) {
        listeners.add(listener);
    }

    public void delPickColumnReplaceListener(IPickColumnReplaceListener listener) {
        listeners.remove(listener);
    }

    /** Creates new form ColumnReplacePanel */
    public ColumnReplacePanel(FTable ftable) {
        this.ftable = ftable;
        initComponents();
        initOthers();
        setupEvents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        pnlMain = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        btnView = new javax.swing.JButton();
        btnReplace = new javax.swing.JButton();
        btnAdvance = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnCheck = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jrbSelect = new javax.swing.JRadioButton();
        jrbAll = new javax.swing.JRadioButton();
        jrbContainer = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jcbField = new javax.swing.JComboBox();
        btnField = new javax.swing.JButton();
        btnSetField = new javax.swing.JButton();

        pnlMain.setLayout(new java.awt.BorderLayout());

        btnView.setText("Ԥ��");

        btnReplace.setText("�滻");

        btnAdvance.setText("�߼�");

        btnClose.setText("�ر�");

        btnClear.setText("���");

        btnCheck.setText("У��");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(211, Short.MAX_VALUE)
                .addComponent(btnClear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnView)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReplace)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAdvance)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose)
                .addGap(66, 66, 66))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnView)
                    .addComponent(btnClear)
                    .addComponent(btnCheck)
                    .addComponent(btnReplace)
                    .addComponent(btnAdvance)
                    .addComponent(btnClose))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("�滻����Χ��"));

        buttonGroup1.add(jrbSelect);
        jrbSelect.setSelected(true);
        jrbSelect.setText("��ǰѡ���¼");

        buttonGroup1.add(jrbAll);
        jrbAll.setText("��ǰ���м�¼");

        buttonGroup1.add(jrbContainer);
        jrbContainer.setText("��Ա����");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jrbSelect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbContainer)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jrbSelect)
                .addComponent(jrbAll)
                .addComponent(jrbContainer))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("�滻�ֶΣ�"));

        jLabel1.setText("�ֶΣ�");

        jcbField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnField.setText("...");

        btnSetField.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/setrepfield.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbField, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSetField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jcbField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSetField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, 0, 52, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdvance;
    private javax.swing.JButton btnCheck;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnField;
    private javax.swing.JButton btnReplace;
    private javax.swing.JButton btnSetField;
    private javax.swing.JButton btnView;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox jcbField;
    private javax.swing.JRadioButton jrbAll;
    private javax.swing.JRadioButton jrbContainer;
    private javax.swing.JRadioButton jrbSelect;
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables

    private void initOthers() {
        btnSetField.setEnabled(UserContext.isSA);
        getRepFieldPara();
        disable_fields.add("a0190");
        disable_fields.add("a0101");
        disable_fields.add("a0191");
        selectedList = ftable.getSelectObjects();
        field_binding = SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE, replace_fields, jcbField);
        field_binding.bind();
        refreshReplaceField();
        List<String> t_fields = new ArrayList<String>();
        t_fields.add("field_name");
        t_fields.add("caption_name");
        t_fields.add("field_type");
        t_fields.add("code_type_name");
        ftable_field = new FTable(TempFieldInfo.class, t_fields, false, false, false, "Sys");
        ftable_field.setObjects(replace_fields);
        pnlField = new JPanel(new BorderLayout());
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.add(ftable_field, BorderLayout.CENTER);
        pnl.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlField.add(pnl, BorderLayout.CENTER);
        JToolBar toolbar = new JToolBar();
        toolbar.add(new JLabel("���ң�"));
        toolbar.setFloatable(false);
        ComponentUtil.setSize(jtfSearch, 120, 22);
        ComponentUtil.setSize(btnSearch, 22, 22);
        toolbar.add(jtfSearch);
        toolbar.add(btnSearch);
        pnlField.add(toolbar, BorderLayout.NORTH);
        refreshView();
    }

    private void refreshReplaceField() {
        if (replace_field_para.getSysparameter_value() != null && !replace_field_para.getSysparameter_value().trim().equals("")) {
            String[] fields = replace_field_para.getSysparameter_value().split(";");
            for (String field : fields) {
                disable_fields.add(field);
            }
        }
        List<String> fields = ftable.getFields();
        if (ftable.getDisable_fields() != null) {
            disable_fields.addAll(ftable.getDisable_fields());
        }
        TempFieldInfo select_field = (TempFieldInfo) jcbField.getSelectedItem();
        replace_fields.clear();
        String field = ftable.getCurrentColumnIndex() >= 0 ? ftable.getFields().get(ftable.getCurrentColumnIndex()) : null;
        List<TempFieldInfo> all_infos = ftable.getAll_fields();
        for (TempFieldInfo tfi : all_infos) {
            if (tfi.getField_name().contains(".")) {
                continue;
            }
            if (disable_fields.contains(tfi.getField_name())) {
                continue;
            }
            FieldAnnotation fa = tfi.getField().getAnnotation(FieldAnnotation.class);
            if (!(fa.isEditable() || fa.editableWhenEdit())) {
                continue;
            }
            if (fields.contains(tfi.getField_name())) {
                if (UserContext.getFieldRight(tfi.getEntity_name() + "." + tfi.getField_name().replace("_code_", "")) == 1) {
                    replace_fields.add(tfi);
                    if (select_field == null && field != null && tfi.getField_name().equals(field)) {
                        select_field = tfi;
                    }
                }
            }
        }
        field_binding.unbind();
        field_binding.bind();
        if (select_field != null) {
            jcbField.setSelectedItem(select_field);
        }
        jcbField.updateUI();
    }

    private void setupEvents() {
        btnSetField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<TempFieldInfo> all_infos = ftable.getAll_fields();
                List<TempFieldInfo> r_infos = new ArrayList<TempFieldInfo>();
                for (TempFieldInfo tfi : all_infos) {
                    if (tfi.getField_name().contains(".")) {
                        continue;
                    }
                    FieldAnnotation fa = tfi.getField().getAnnotation(FieldAnnotation.class);
                    if (!(fa.isEditable() || fa.editableWhenEdit())) {
                        continue;
                    }
                    r_infos.add(tfi);
                }
                ColumnReplaceFieldDialog crDlg = new ColumnReplaceFieldDialog(r_infos, replace_field_para);
                ContextManager.locateOnMainScreenCenter(crDlg);
                crDlg.setVisible(true);
                if (crDlg.isClick_ok()) {
                    refreshReplaceField();
                }
            }
        });
        ActionListener al = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String txt = jtfSearch.getText();
                if (txt == null || txt.trim().equals("")) {
                    return;
                }
                List<String> fetch_fields = new ArrayList<String>();
                fetch_fields.add("field_name");
                fetch_fields.add("caption_name");
                fetch_fields.add("pym");
                last_search_ind = SysUtil.locateEmp(-1, txt, last_search_val, last_search_ind, ftable_field, fetch_fields);
                last_search_val = txt;
                if (last_search_ind != -1) {
                    ftable_field.setRowSelectionInterval(last_search_ind, last_search_ind);
                }
            }
        };
        jtfSearch.addActionListener(al);
        btnSearch.addActionListener(al);
        btnField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!ModalDialog.doModal(btnView, pnlField, "��ѡ���滻�ֶ�:")) {
                    return;
                }
                int ind = ftable_field.getCurrentRowIndex();
                if (ftable_field.getObjects().isEmpty() || ind == -1) {
                    return;
                }
                jcbField.setSelectedIndex(ind);
            }
        });
        jcbField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cur_listener != null && jcbField.getSelectedItem() != null) {
                    ftable_field.setRowSelectionInterval(jcbField.getSelectedIndex(), jcbField.getSelectedIndex());
                    cur_listener.setReplaceField((TempFieldInfo) jcbField.getSelectedItem());
                }
            }
        });
        btnAdvance.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                isAdvance_method = !isAdvance_method;
                refreshView();
            }
        });
        btnClear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cur_listener != null) {
                    cur_listener.clear();
                }
            }
        });
        btnReplace.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cur_listener != null) {
                    ValidateSQLResult result = cur_listener.replace();
                    if (result == null) {
                        return;
                    }
                    if (result.getResult() == 0) {
                        JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(btnReplace), "�滻�ɹ�");
                        cur_listener.refreshData();
                        for (IPickColumnReplaceListener listener : listeners) {
                            listener.pickReplace();
                        }
                    } else {
                        MsgUtil.showHRSaveErrorMsg(result);
                    }
                }
            }
        });
        btnCheck.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cur_listener != null) {
                    cur_listener.check();
                }
            }
        });
        btnView.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cur_listener != null) {
                    cur_listener.preview();
                }
            }
        });
        CloseAction.doCloseAction(btnClose);
        jrbSelect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cur_listener != null) {
                    cur_listener.changeobject(0);
                }
            }
        });
        jrbAll.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cur_listener != null) {
                    cur_listener.changeobject(1);
                }
            }
        });
        jrbContainer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cur_listener != null) {
                    cur_listener.changeobject(2);
                }
            }
        });
        refreshView();
        if (cur_listener != null) {
            cur_listener.changeobject(getObjectMethod());
        }
    }

    private SysParameter getRepFieldPara() {
        String key = "ReplaceField_" + ftable.getUseModuleCode();
        if (replace_field_para == null) {
            replace_field_para = (SysParameter) CommUtil.fetchEntityBy("from SysParameter where sysParameter_key='" + key + "'");
        }
        if (replace_field_para == null) {
            replace_field_para = new SysParameter();
            replace_field_para.setSysParameter_key(key);
            CommUtil.saveOrUpdate(replace_field_para);
        }
        return replace_field_para;
    }

    public static List getQueryPara(String text, String entityName, HashSet<String> entitys) {
        List<String> list = new ArrayList<String>();
        String s_from = "," + entityName;
        String s_where = "1=1";
        for (String entity_name : entitys) {
            String key = "";
            if (entity_name.equals(entityName)) {
                continue;
            }
            if (text.toLowerCase().contains(entity_name.toLowerCase() + ".")) {
                key = EntityBuilder.getEntityKey(entity_name);
                s_from += "," + entity_name;
                s_where += " and " + entity_name + "." + key + "=" + entityName + "." + key;
            }
        }
        s_from = s_from.substring(1);
        list.add(s_from);
        list.add(s_where);
        return list;
    }

//    public void setContainer_data(List list, boolean visible) {
//        this.isContainer_visible = visible;
//        container_data = list;
//        jrbContainer.setVisible(false);
//        if (container_data == null) {
//            jrbContainer.setEnabled(false);
//        } else {
//            jrbContainer.setEnabled(true);
//            jrbContainer.setSelected(isContainer_visible);
//        }
//        jrbContainer.updateUI();
//        if (cur_listener != null) {
//            cur_listener.setContainerData(list);
//            if (container_data != null && isContainer_visible) {
//                cur_listener.changeobject(2);
//            }
//        }
//    }

    private void refreshView() {
        jrbContainer.setVisible(false);
        pnlMain.removeAll();
        btnClear.setVisible(isAdvance_method);
        btnCheck.setVisible(isAdvance_method);
        if (isAdvance_method) {
            btnAdvance.setText("��ͨ");
            ColumnReplaceAdvancePanel pnl = new ColumnReplaceAdvancePanel(ftable);//, container_data);
            pnlMain.add(pnl, BorderLayout.CENTER);
            cur_listener = pnl;
            cur_listener.setSelectedObject(selectedList);
            cur_listener.changeobject(getObjectMethod());
        } else {
            btnAdvance.setText("�߼�");
            ColumnReplaceSimPanel pnl = new ColumnReplaceSimPanel(ftable);//, container_data);
            pnlMain.add(pnl, BorderLayout.CENTER);
            cur_listener = pnl;
            cur_listener.setSelectedObject(selectedList);
            cur_listener.changeobject(getObjectMethod());
        }
        cur_listener.setReplaceField((TempFieldInfo) jcbField.getSelectedItem());
        pnlMain.updateUI();
    }

    private int getObjectMethod() {
        if (jrbSelect.isSelected()) {
            return 0;
        }
        if (jrbAll.isSelected()) {
            return 1;
        }
        return 2;
    }
}