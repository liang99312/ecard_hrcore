/*
 * PickFieldSelectPanel.java
 *
 * Created on 2008年11月10日, 下午1:56
 */
package org.jhrcore.queryanalysis;

import com.foundercy.pf.control.table.FTable;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.JListBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jhrcore.ui.WizardPanel;
import org.jhrcore.util.UtilTool;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.query.QueryAnalysisField;
import org.jhrcore.entity.query.QueryAnalysisScheme;
import org.jhrcore.mutil.QueryUtil;
import org.jhrcore.query3.TempEntity;
import org.jhrcore.query3.TempField;
import org.jhrcore.ui.SearchFieldsPanel;
import org.jhrcore.ui.listener.IPickCloseSearchPanelListener;

/**
 *
 * @author  Owner
 */
public class PickFieldSelectPanel extends WizardPanel {

    private QueryAnalysisScheme queryScheme;
    private FTable ftable_fields = new FTable(QueryAnalysisField.class, false, false, false);
    private BindingGroup bindGroup;
    // 当前选中编辑的字段
    private QueryAnalysisField queryAnalysisField;
    private JComboBoxBinding jcomboBoxBindingEntity;
    private JListBinding jListBinding_Field = null;
    private List<TempEntity> listTempEntity = new ArrayList<TempEntity>();
    private Class entity_class;
    private int cur_index = 0;
    private JPopupMenu popupMenu = new JPopupMenu();
    private JMenuItem searchField = new JMenuItem("查找");
    private Logger log = Logger.getLogger(PickFieldSelectPanel.class.getName());
    // 是否包含集合类型字段
    private boolean include_set_field = true;
    private List<TempField> listTempField = new ArrayList<TempField>();
    private HashSet<String> exist_keys = new HashSet<String>();
    private String title = "第二步：设置查询字段";
    public void setTitle(String title){
        this.title = title;
    }
    public PickFieldSelectPanel(QueryAnalysisScheme queryScheme) {
        this(queryScheme, "第二步：设置查询字段");
    }

    public PickFieldSelectPanel(QueryAnalysisScheme queryScheme, String title) {
        this.queryScheme = queryScheme;
        initComponents();
        initUI();
        setupEvents();
        this.title = title;
    }

    private void initUI() {
        pnl_select_fields.add(ftable_fields, BorderLayout.CENTER);
        ftable_fields.clear();
        try {
            entity_class = Class.forName(queryScheme.getModuleInfo().getQuery_entity_name());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            log.error(ex);
        }
        QueryUtil.createEntityFieldList(entity_class, "", "", entity_class.getSimpleName(), "", 1,false,include_set_field,entity_class,listTempEntity);
//        createEntityFieldList(entity_class, "", "", entity_class.getSimpleName(), "", 1);
        jcomboBoxBindingEntity = SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE, listTempEntity, comboxEntity);
        jcomboBoxBindingEntity.bind();
        jListBinding_Field = SwingBindings.createJListBinding(UpdateStrategy.READ, listTempField, jList1_Fields);
        jListBinding_Field.bind();
        List<Object[]> entities = QueryUtil.createEntities(entity_class);
        for(Object[] entity:entities){
            addQueryEntity((Class) entity[0],entity[1].toString(),entity[2].toString(),entity[3].toString());
        }
    }

    private void setupEvents() {
        btnAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (jList1_Fields.getSelectedValue() == null) {
                    return;
                }
                cur_index = jList1_Fields.getSelectedIndex();
                List list = new ArrayList();
                for (Object obj : jList1_Fields.getSelectedValues()) {
                    list.add(obj);
                }
                addField(list);
            }
        });
        jList1_Fields.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() < 2) {
                    return;
                }
                if (jList1_Fields.getSelectedValue() == null) {
                    return;
                }
                cur_index = jList1_Fields.getSelectedIndex();
                List list = new ArrayList();
                for (Object obj : jList1_Fields.getSelectedValues()) {
                    list.add(obj);
                }
                addField(list);
            }

            @Override
            public void mousePressed(MouseEvent e){
                if(e.getButton() == 3){
                    popupMenu.removeAll();
                    popupMenu.add(searchField);
                    popupMenu.show(jList1_Fields, e.getX(), e.getY());
                }
            }
        });
        searchField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (pnlLeft.getComponentCount() == 1) {
                    SearchFieldsPanel panel = new SearchFieldsPanel(jList1_Fields,listTempField);
                    panel.addPickCloseSearchPanelListener(new IPickCloseSearchPanelListener(){

                        @Override
                        public void closeSearchPanel() {
                            pnlLeft.removeAll();
                            pnlLeft.add(new JScrollPane(jList1_Fields), BorderLayout.CENTER);
                            pnlLeft.updateUI();
                        }
                    });
                    pnlLeft.removeAll();
                    pnlLeft.add(new JScrollPane(jList1_Fields), BorderLayout.CENTER);
                    pnlLeft.add(panel, BorderLayout.SOUTH);
                    pnlLeft.updateUI();
                } else if (pnlLeft.getComponentCount() == 2) {
                    pnlLeft.removeAll();
                    pnlLeft.add(new JScrollPane(jList1_Fields), BorderLayout.CENTER);
                    pnlLeft.updateUI();
                }
            }
        });
        btnAddAll.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addField(listTempField);
            }
        });
        btnDown.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                upOrder(1);
            }
        });
        btnUp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                upOrder(-1);
            }
        });
        btnRemove.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (Object obj : ftable_fields.getSelectObjects()) {
                    QueryAnalysisField qaf = (QueryAnalysisField) obj;
                    exist_keys.remove(qaf.getEntity_name() + "." + qaf.getField_name());
                }
                ftable_fields.deleteSelectedRows();
                refreshFieldList();
            }
        });
        btnRemoveAll.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (Object obj : ftable_fields.getObjects()) {
                    QueryAnalysisField qaf = (QueryAnalysisField) obj;
                    exist_keys.remove(qaf.getEntity_name() + "." + qaf.getField_name());
                }
                ftable_fields.deleteAllRows();
                refreshFieldList();
            }
        });
        ActionListener al1 = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                refreshFieldList();
            }
        };
        comboxEntity.addActionListener(al1);

        ftable_fields.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (queryAnalysisField == ftable_fields.getCurrentRow()) {
                    return;
                }
                queryAnalysisField = (QueryAnalysisField) ftable_fields.getCurrentRow();
                if (bindGroup != null) {
                    bindGroup.unbind();
                }
                bindGroup = new BindingGroup();
                if (queryAnalysisField == null) {
                    return;
                }

                BeanProperty beanP = BeanProperty.create("field_name");
                BeanProperty textP = BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST");
                Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, queryAnalysisField,
                        beanP, txtFieldName, textP, "field_name");
                bindGroup.addBinding(binding);

                beanP = BeanProperty.create("field_caption");
                textP = BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST");
                binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, queryAnalysisField,
                        beanP, txtFieldCaption, textP, "field_caption");
                bindGroup.addBinding(binding);
                jRadioButton1.setSelected(jRadioButton1.getText().equals(queryAnalysisField.getStat_type()));
                jRadioButton2.setSelected(jRadioButton2.getText().equals(queryAnalysisField.getStat_type()));
                jRadioButton3.setSelected(jRadioButton3.getText().equals(queryAnalysisField.getStat_type()));
                jRadioButton4.setSelected(jRadioButton4.getText().equals(queryAnalysisField.getStat_type()));
                jRadioButton5.setSelected(jRadioButton5.getText().equals(queryAnalysisField.getOrder_type()));
                jRadioButton6.setSelected(jRadioButton6.getText().equals(queryAnalysisField.getOrder_type()));
                jRadioButton7.setSelected(jRadioButton7.getText().equals(queryAnalysisField.getOrder_type()));

                bindGroup.bind();
            }
        });
        ftable_fields.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    for (Object obj : ftable_fields.getSelectObjects()) {
                        QueryAnalysisField qaf = (QueryAnalysisField) obj;
                        exist_keys.remove(qaf.getEntity_name() + "." + qaf.getField_name());
                    }
                    ftable_fields.deleteSelectedRows();
                    refreshFieldList();
                }
            }
        });

        ActionListener al = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton jRadioButton = (JRadioButton) e.getSource();
                if (jRadioButton.isSelected() && queryAnalysisField != null) {
                    queryAnalysisField.setStat_type(jRadioButton.getText());
                }
            }
        };
        jRadioButton1.addActionListener(al);
        jRadioButton2.addActionListener(al);
        jRadioButton3.addActionListener(al);
        jRadioButton4.addActionListener(al);

        al = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton jRadioButton = (JRadioButton) e.getSource();
                if (jRadioButton.isSelected() && queryAnalysisField != null) {
                    queryAnalysisField.setOrder_type(jRadioButton.getText());
                }
            }
        };
        jRadioButton5.addActionListener(al);
        jRadioButton6.addActionListener(al);
        jRadioButton7.addActionListener(al);
        al1.actionPerformed(null);
        ftable_fields.setObjects(queryScheme.getQueryAnalysisFields());
    }

    private void addField(List list) {
        List remove_list = new ArrayList();
        for (Object obj : list) {
            TempField curTempField = (TempField) obj;
            QueryAnalysisField qaField = (QueryAnalysisField) UtilTool.createUIDEntity(QueryAnalysisField.class);
            ClassAnnotation ca = (ClassAnnotation) curTempField.getEntityClass().getAnnotation(ClassAnnotation.class);
            qaField.setQueryAnalysisScheme(queryScheme);
            qaField.setEntity_caption(ca == null ? curTempField.getEntityClass().getSimpleName() : ca.displayName());
            qaField.setEntity_name(curTempField.getEntityClass().getSimpleName());
            qaField.setField_caption(curTempField.getDisplay_name());
            qaField.setField_name(curTempField.getField_name());
            qaField.setEntityNames(curTempField.getEntityNames());
            qaField.setFieldClasses(curTempField.getFieldClasses());
            ftable_fields.addObject(qaField);
            remove_list.add(obj);
            exist_keys.add(curTempField.getEntityClass().getSimpleName() + "." + curTempField.getField_name());
        }
        listTempField.removeAll(remove_list);     
        jListBinding_Field.unbind();
        jListBinding_Field.bind();
        if(listTempField.size() > 0){
            if(listTempField.size() <= cur_index){
                jList1_Fields.setSelectedIndex(0);
                cur_index = 0;
            }else{
                jList1_Fields.setSelectedIndex(cur_index);
            }
            jList1_Fields.ensureIndexIsVisible(cur_index);
        }
    }

    private void refreshFieldList() {
        if (comboxEntity.getSelectedItem() == null) {
            return;
        }
        TempEntity te = (TempEntity) comboxEntity.getSelectedItem();
        List<TempField> fields = te.getTempFields();
        listTempField.clear();
        for (TempField tf : fields) {
            if (exist_keys.contains(tf.getEntityClass().getSimpleName() + "." + tf.getField_name())) {
                continue;
            }
            listTempField.add(tf);
        }
        jListBinding_Field.unbind();
        jListBinding_Field.bind();
    }
    // 增加额外的查询表，其中the_class2为要增加的表对应的类，entityDisplayName要增加的表对应的中文名称，entityName表示表名，fieldClassType表示增加的表和查询的表之间的关系。
    // 比如查询实体为A01，要增加的表为C21，addQueryEntity(C21.class, "工资主表", "C21", "4"); 其中4表示C21包含A01的主键
    //           createEntityFieldList(C21.class, "C21", "工资主表", "A01:C21", "4", 100);
    // 比如查询实体为C21，要增加的表为A01，addQueryEntity(A01.class, "人员信息", "A01", "5"); 其中5表示查询表包含增加的表的主键
    // 比如查询实体为Dept，要增加的表为C21，addQueryEntity(C21.class, "工资主表", "A01;C21", "3;4"); 其中4表示增加的表包含A01的主键，3表示A01包含一个Dept的属性
    public void addQueryEntity(Class the_class2, String entityDisplayName, String entityName, String fieldClassType) {
        int level = 100;
        // 为了在查询工资其他表的时候可以看到部门信息，设置level=99，这样可以在查询条件中看到部门信息（通过C21的部门属性）
//        if (entity_class.getSuperclass().getSimpleName().equals("Pay") && the_class2.getSimpleName().equals("C21")) {
//            //level = 99;
//        }
        QueryUtil.createEntityFieldList(the_class2, entityName, entityDisplayName, entity_class.getSimpleName() + ";" + entityName, fieldClassType, level, false, include_set_field, entity_class, listTempEntity);
//        createEntityFieldList(the_class2, entityName, entityDisplayName, entity_class.getSimpleName() + ";" + entityName, fieldClassType, level);
        jcomboBoxBindingEntity.unbind();
        jcomboBoxBindingEntity.bind();
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        pnl_select_fields = new javax.swing.JPanel();
        btnDown = new javax.swing.JButton();
        btnUp = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        comboxEntity = new javax.swing.JComboBox();
        btnAddAll = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnRemoveAll = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtFieldName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtFieldCaption = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        pnlLeft = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1_Fields = new javax.swing.JList();

        setLayout(null);

        pnl_select_fields.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnl_select_fields.setLayout(new java.awt.BorderLayout());
        add(pnl_select_fields);
        pnl_select_fields.setBounds(230, 60, 230, 330);

        btnDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/move_down.png"))); // NOI18N
        add(btnDown);
        btnDown.setBounds(390, 30, 22, 22);

        btnUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/move_up.png"))); // NOI18N
        add(btnUp);
        btnUp.setBounds(420, 30, 22, 22);

        jLabel1.setText("表选择：");
        add(jLabel1);
        jLabel1.setBounds(10, 10, 48, 15);

        comboxEntity.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(comboxEntity);
        comboxEntity.setBounds(10, 30, 170, 22);

        btnAddAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/select_all.png"))); // NOI18N
        add(btnAddAll);
        btnAddAll.setBounds(190, 60, 22, 22);

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/remove_one.png"))); // NOI18N
        add(btnRemove);
        btnRemove.setBounds(190, 120, 22, 22);

        btnRemoveAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/remove_all.png"))); // NOI18N
        add(btnRemoveAll);
        btnRemoveAll.setBounds(190, 150, 22, 21);

        jLabel2.setText("已选字段：");
        add(jLabel2);
        jLabel2.setBounds(230, 10, 60, 15);

        jLabel4.setText("字段描述：");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("统计方式"));

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("普通");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("计数");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("平均");

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("求和");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("排序方式"));

        buttonGroup2.add(jRadioButton5);
        jRadioButton5.setText("不排序");

        buttonGroup2.add(jRadioButton6);
        jRadioButton6.setText("升序");

        buttonGroup2.add(jRadioButton7);
        jRadioButton7.setText("降序");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton5)
                    .addComponent(jRadioButton6)
                    .addComponent(jRadioButton7))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jRadioButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setText("字段名：");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFieldCaption, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                    .addComponent(txtFieldName))
                .addGap(83, 83, 83))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFieldCaption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        add(jPanel1);
        jPanel1.setBounds(470, 10, 120, 370);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/select_one.png"))); // NOI18N
        add(btnAdd);
        btnAdd.setBounds(190, 90, 22, 22);

        pnlLeft.setLayout(new java.awt.BorderLayout());

        jList1_Fields.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1_Fields);

        pnlLeft.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(pnlLeft);
        pnlLeft.setBounds(10, 70, 170, 320);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public boolean isValidate() {
        List list = ftable_fields.getObjects();
        if (list.size() == 0) {
            JOptionPane.showMessageDialog(
                    JOptionPane.getFrameForComponent(this),
                    "必须选择字段", // message
                    "错误", // title
                    JOptionPane.ERROR_MESSAGE); // messageType
            return false;
        }
        int ind = 0;
        for (Object obj : list) {
            QueryAnalysisField qaf = (QueryAnalysisField) obj;
            ind++;
            qaf.setOrder_no(ind);
        }
        return true;
    }

    @Override
    public void beforeLeave() {
        if (comboxEntity.getSelectedIndex() > -1) {
            queryScheme.setEntity_fullname(queryScheme.getModuleInfo().getQuery_entity_name());
        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddAll;
    private javax.swing.JButton btnDown;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnRemoveAll;
    private javax.swing.JButton btnUp;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox comboxEntity;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList jList1_Fields;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnl_select_fields;
    private javax.swing.JTextField txtFieldCaption;
    private javax.swing.JTextField txtFieldName;
    // End of variables declaration//GEN-END:variables

    private void upOrder(int step) {
        if (ftable_fields.getObjects().size() <= 1) {
            return;
        }
        int ind = ftable_fields.getCurrentRowIndex();
        int rows = ftable_fields.getObjects().size();
        Object obj = ftable_fields.getCurrentRow();
        List list = ftable_fields.getObjects();
        list.remove(obj);
        int index = ind + step;
        if (index == -1) {
            index = rows - 1;
        } else if (index == rows) {
            index = 0;
        }
        if (index == rows - 1) {
            list.add(obj);
        } else {
            list.add(index, obj);
        }
        ftable_fields.setObjects(list);
        ftable_fields.setRowSelectionInterval(index, index);
    }

    @Override
    public String getTitle() {
        return title;
    }
}
