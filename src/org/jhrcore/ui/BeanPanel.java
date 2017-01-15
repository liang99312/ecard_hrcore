/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import org.jhrcore.ui.renderer.TempCodeEditor;
import com.foundercy.pf.control.table.FTable;
import com.foundercy.pf.control.table.FTableModel;
import org.jhrcore.ui.renderer.ComboBoxCellRenderer;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.binding.beans.Model;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import org.apache.log4j.Logger;
import org.jfree.ui.PaintSample;
import org.jhrcore.comm.BeanManager;
import org.jhrcore.client.CommUtil;
import org.jhrcore.client.UIContext;
import org.jhrcore.util.DateUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.comm.FieldTrigerManager;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.entity.Code;
import org.jhrcore.comm.CodeManager;
import org.jhrcore.comm.ConfigManager;
import org.jhrcore.entity.A01;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.IObjectAttribute;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;
import org.jhrcore.entity.annotation.ObjectListHint;
import org.jhrcore.entity.annotation.UILayout;
import org.jhrcore.entity.base.EntityDef;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.right.FuntionRight;
import org.jhrcore.entity.showstyle.ShowScheme;
import org.jhrcore.ui.listener.IPickCodeSelectListener;
import org.jhrcore.rebuild.EntityBuilder;
import org.jhrcore.ui.listener.IPickBeanPanelEditListener;
import org.jhrcore.ui.renderer.HRRendererView;
import org.jhrcore.util.ColorUtil;
import org.jhrcore.util.ComponentUtil;
import org.jhrcore.util.ImageUtil;

/**
 *
 * @author Administrator
 */
public class BeanPanel extends JPanel {

    private Object bean;//当前对象
    private List<String> fields = new ArrayList<String>();//当前显示属性
    public int columns = ConfigManager.getConfigManager().getIntegerFromProperty("UI.BeanPanel.cols") == null ? 2 : ConfigManager.getConfigManager().getIntegerFromProperty("UI.BeanPanel.cols");//界面列数
    public static int colWith = 100;//编辑宽度
    public static int colspan = 15;//列间距
    public static int rowspan = 2;//行间距
    private String direction = "right";//对齐方向
    private boolean field_editor_able = false;
    private boolean editable = false;
    private static boolean dlg_result = false;//对话框返回值
    private List<UIItemGroup> fg_list = null;
    private List<String> disable_fields = null;
    private List<String> editable_fields = null;
    private ShowScheme show_scheme = null;
    private Hashtable<String, Component> component_keys = new Hashtable<String, Component>();
    private Hashtable<String, Field> field_keys = new Hashtable<String, Field>();
    private BeanAdapter adapter;
    private Dimension screen_size = ContextManager.getMainFrame().getSize();
    private Logger log = Logger.getLogger(BeanPanel.class.getName());
    private HashSet<String> diseditable_fields = new HashSet<String>();
    private boolean bean_changed = false;
    public static Hashtable<String, List> objHint_data = new Hashtable<String, List>();
    // 保存从其他表引入的值，索引为 表名，HashMap为:字段名、值
    private Hashtable<String, HashMap> ht_OtherTable = new Hashtable<String, HashMap>();
    // 保存从其他表获取数据时使用的SQL语句，例如：
    // select f01,f02 from Tab1 where tab1_key
    // 该语句拼上当前行的对象主键为完整的获取数据语句。
    private Hashtable<String, String> ht_OtherTableSql = new Hashtable<String, String>();
    private boolean editForChangeScheme = false;
    private static boolean deptCode_chind_only_flag = true;//当有deptCode类时，设置选中时是否允许选中非叶子节点    
    private Color cell_value = null;

    public boolean isDeptCode_chind_only_flag() {
        return deptCode_chind_only_flag;
    }

    public void setDeptCode_chind_only_flag(boolean deptCode_chind_only_flag) {
        BeanPanel.deptCode_chind_only_flag = deptCode_chind_only_flag;
    }

    public void setEditable_fields(List<String> editable_fields) {
        this.editable_fields = editable_fields;
    }

    public Object getChangeObj(Object obj) {
        if (obj != null || !bean_changed || !isEditable()) {
            return obj;
        }
        return bean;
    }

    public void setEditForChangeScheme(boolean editForChangeScheme) {
        this.editForChangeScheme = editForChangeScheme;
    }

    public Hashtable<String, Component> getComponent_keys() {
        return component_keys;
    }

    public BeanAdapter getAdapter() {
        return adapter;
    }

    public ShowScheme getShow_scheme() {
        return show_scheme;
    }

    public void setShow_scheme(ShowScheme show_scheme) {
        this.show_scheme = show_scheme;
        this.fg_list = null;
    }

    public List<String> getDisable_fields() {
        return disable_fields;
    }

    public void setDisable_fields(List<String> disable_fields) {
        this.disable_fields = disable_fields;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public Object getBean() {
        return bean;
    }

    private void modifyUIItemGroup(Object old_bean, Object new_bean) {
        if (new_bean == null) {
            return;
        }
        if (old_bean != null && old_bean.getClass().getName().equals(new_bean.getClass().getName())) {
            return;
        }
        fields.clear();
        fg_list = null;
    }

    public void setBean(Object bean, List fields) {
        this.fields.clear();
        fg_list = null;
        this.fields.addAll(fields);
        setBean2(bean);
    }

    public void setBean(Object bean) {
        modifyUIItemGroup(this.bean, bean);
        setBean2(bean);
    }

    public void setBean(Object bean, boolean editable) {
        modifyUIItemGroup(this.bean, bean);
        setBean2(bean);
        this.editable = editable;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields.clear();
        this.fields.addAll(fields);
        fg_list = null;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void setBean_changed(boolean bean_changed) {
        this.bean_changed = bean_changed;
    }

    public BeanPanel() {
        super(new BorderLayout());
    }

    public BeanPanel(Object bean) {
        super(new BorderLayout());
        setBean2(bean);
    }

    public BeanPanel(Object bean, List<String> fields) {
        super(new BorderLayout());
        setBean2(bean);
        this.fields.clear();
        this.fields.addAll(fields);
    }

    public void bind() {
        bindUI();
        BeanManager.manager(bean);
        bean_changed = false;
    }

    public void bindUI() {
        diseditable_fields.clear();
        EnterToTab.getList_bean_component().clear();
        if (this.bean == null) {
            return;
        }
        boolean isNew = false;
        if (bean instanceof IKey) {
            isNew = ((IKey) bean).getKey() == 1;
        } else if (bean instanceof IObjectAttribute) {
            isNew = ((IObjectAttribute) bean).editType() == IObjectAttribute.EDITTYPE_NEW;
        }
        component_keys.clear();
        if (fg_list == null) {
            fg_list = new ArrayList<UIItemGroup>();
            if (fields.isEmpty()) {
                fields.addAll(EntityBuilder.getCommFieldNameListOf(bean.getClass(), EntityBuilder.COMM_FIELD_VISIBLE, isNew));
            }
            fg_list.addAll(UIItemGroup.getBindGroupListOf(bean.getClass(), show_scheme, fields));
        }
        this.removeAll();
        JPanel pnl = getPanel(fg_list, isNew);
        this.add(pnl, BorderLayout.CENTER);
        this.updateUI();
    }

    private JPanel getPanel(List<UIItemGroup> fg_list, boolean isNew) {
//        ht_OtherTable.clear();
        if (fg_list.isEmpty()) {
            return new JPanel();
        }

        // 字段个数
        int field_num = 0;
        for (UIItemGroup uIItemGroup : fg_list) {
            field_num = field_num + uIItemGroup.getBindDetails().size();
        }

        int col = columns;
        String colWidth = colWith + "dlu";
        // 检查该实体是否有UILayout注解,如果有的话,使用注解的参数------>>
        UILayout uILayout = bean.getClass().getAnnotation(UILayout.class);
        if (uILayout != null) {
            col = uILayout.colNum();
            colWidth = uILayout.colWidth();
        }
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<------<<

        int row = 0;
        for (UIItemGroup fg : fg_list) {
            row = row + 1;
            int tmp_col = 0;
            for (UIItemDetail uIItemDetail : fg.getBindDetails()) {
                int viewWidth = uIItemDetail.getView_width();
                if (viewWidth > 40 && viewWidth <= 70) {
                    row++;
                    tmp_col = 0;
                } else if (viewWidth > 70) {
                    row = row + 35;
                    tmp_col = 0;
                } else {
                    if (tmp_col == 0) {
                        row++;
                    }
                    tmp_col++;
                    if (tmp_col == col) {
                        tmp_col = 0;
                    }
                }
            }
        }

        String scol = "";
        for (int i = 0; i < col; i++) {
            if (i != 0) {
                if (colspan > 0 && direction.equals("left")) {
                    scol = scol + ", " + colspan + "dlu,l:p, " + colspan + "dlu," + colWidth;
                } else if (colspan > 0 && direction.equals("right")) {
                    scol = scol + ", " + colspan + "dlu,r:p, " + colspan + "dlu," + colWidth;
                } else {
                    if (direction.equals("left")) {
                        scol = scol + ", " + colspan + "dlu,l:p, " + colspan + "dlu," + colWidth;
                    } else {
                        scol = scol + ", " + colspan + "dlu,r:p, " + colspan + "dlu," + colWidth;
                    }
                }

            }// 80dlu";
            else {
                if (colspan > 0 && direction.equals("left")) {
                    scol = scol + "l:p, " + colspan + "dlu," + colWidth;
                } else if (colspan > 0 && direction.equals("right")) {
                    scol = scol + "r:p, " + colspan + "dlu," + colWidth;
                } else {
                    if (direction.equals("left")) {
                        scol = scol + "l:p, " + colspan + "dlu," + colWidth;
                    } else {
                        scol = scol + "r:p, " + colspan + "dlu," + colWidth;
                    }
                }
            }// 80dlu";
        }

        String srow = "";
        for (int i = 0; i < row; i++) {
            if (i != 0) {
                srow = srow + ", " + rowspan + "dlu, t:p";
            } else {
                srow = srow + "t:p";
            }
        }

        FormLayout layout = new FormLayout(scol, srow);
        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();
        adapter = new BeanAdapter(bean);
        int icol = 1, irow = 1;
        boolean grouped = false;
        if (show_scheme != null && show_scheme.isGroup_flag()) {
            grouped = true;
        }
        for (UIItemGroup fg : fg_list) {
            if (icol != 1) {
                irow = irow + 2;
                icol = 1;
            }
            if (grouped) {
                //if (fg_list.size() > 1) {
                JLabel lbl = new JLabel(fg.getGroupName() + ":");
                lbl.setFont(new Font(fg.getGroupName() + ":", Font.BOLD, 12));
                builder.add(lbl, cc.xyw(icol, irow, 1, cc.LEFT, cc.CENTER));
                builder.addSeparator("", cc.xyw(icol + 1, irow, col * 4 - 2, cc.DEFAULT, cc.CENTER));
                // builder.addSeparator(fg.getGroupName() + ":", cc.xyw(icol, irow, col * 4 - 1));
            }
            //builder.add(new JLabel(fg.getGroupName() + ":"), cc.xy(icol, irow));
            irow = irow + 2;
            for (UIItemDetail uIItemDetail : fg.getBindDetails()) {
                Field field = uIItemDetail.getField();
                String bind_name = uIItemDetail.getBindName();
                FieldAnnotation fieldAnnotation = field.getAnnotation(FieldAnnotation.class);
                String displayName = uIItemDetail.getField_caption();
                if (disable_fields != null && (disable_fields.contains(uIItemDetail.getBindName()) || disable_fields.contains(uIItemDetail.getBindName().replace("_code_", "")))) {
                    diseditable_fields.add(bind_name);
                    field_editor_able = false;
                } else if (editable_fields != null && editable_fields.contains(uIItemDetail.getBindName().replace("_code_", ""))) {
                    field_editor_able = true;
                } else if (uIItemDetail.getBindName().contains(".")) {
                    field_editor_able = false;
                    diseditable_fields.add(bind_name);
                } else if (fieldAnnotation != null) {
                    field_editor_able = checkEditable(bind_name, fieldAnnotation, fieldAnnotation.isEditable(), isNew);
                } else {
                    diseditable_fields.add(bind_name);
                    field_editor_able = false;
                }
                int view_width = uIItemDetail.getView_width();//fieldAnnotation.view_width();
                if (view_width > 40 && view_width <= 80) {
                    if (icol != 1) {
                        irow = irow + 2;
                        icol = 1;
                    }
                    createLabel(displayName, icol, irow, builder, cc);
                    JComponent com = createEditorOf(adapter, bean, uIItemDetail, isNew);
                    component_keys.put(uIItemDetail.getBindName(), com);
                    builder.add(com, cc.xyw(icol + 2, irow, col * 4 - 3));
                    irow = irow + 2;
                    icol = 1;
                } else if (view_width > 80) {
                    if (icol != 1) {
                        irow = irow + 2;
                        icol = 1;
                    }
                    createLabel(displayName, icol, irow, builder, cc);
                    JTextArea at = BasicComponentFactory.createTextArea(adapter.getValueModel(bind_name));
                    component_keys.put(uIItemDetail.getBindName(), at);
                    at.setLineWrap(true);
                    at.setEditable(field_editor_able);
                    at.setPreferredSize(new Dimension(at.getWidth(), 150));
                    at.setBorder(null);
                    int rows = view_width / 80 + (view_width % 80 == 0 ? 0 : 1);
                    int height = 20 * rows;
                    at.setPreferredSize(new Dimension(at.getWidth(), height - 1));
                    builder.add(new JScrollPane(at), cc.xywh(icol + 2, irow, col * 4 - 3, height - 1));
                    irow = irow + height;
                    icol = 1;
                } else {
                    createLabel(displayName, icol, irow, builder, cc);
                    icol = icol + 2;
                    JComponent com = createEditorOf(adapter, bean, uIItemDetail, isNew);
                    component_keys.put(uIItemDetail.getBindName(), com);
                    builder.add(com, cc.xy(
                            icol, irow));

                    icol = icol + 2;
                    if (icol > 3 * col + 1) {
                        irow = irow + 2;
                        icol = 1;
                    }
                }
            }
        }
        return builder.getPanel();
    }

    private void addFocusListener(final Component com) {
        com.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                if (com instanceof JTextField) {
                    final JTextField jtf = (JTextField) com;
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            EventQueue evtq = Toolkit.getDefaultToolkit().getSystemEventQueue();
                            Date date = new Date();
                            /*
                             * Post 'Home' key event
                             */
                            evtq.postEvent(new KeyEvent(jtf, KeyEvent.KEY_PRESSED, date.getTime(), 0, KeyEvent.VK_HOME, KeyEvent.CHAR_UNDEFINED));
                            /*
                             * Post 'End' key event with 'Shift' key pressed
                             */
                            evtq.postEvent(new KeyEvent(jtf, KeyEvent.KEY_PRESSED, date.getTime(), KeyEvent.SHIFT_DOWN_MASK, KeyEvent.VK_END, KeyEvent.CHAR_UNDEFINED));
                        }
                    });
                }
                Rectangle vr = BeanPanel.this.getVisibleRect();
                if (vr.contains(com.getBounds())) {
                    return;
                }
                int y = com.getBounds().y;
                y = y == 0 ? vr.y : y;
                Rectangle r = new Rectangle(0, y + com.getBounds().height + 5, 1, 1);
                BeanPanel.this.scrollRectToVisible(r);
            }
        });
    }

    private void createLabel(String displayName, int icol, int irow,
            PanelBuilder builder, CellConstraints cc) {
        builder.add(new JLabel(displayName), cc.xy(icol, irow));
    }

    public Hashtable<String, String> getHt_OtherTableSql() {
        return ht_OtherTableSql;
    }

    public void refreshOtherTableSQL(FTable ftable) {
        if (ftable != null) {
            this.ht_OtherTableSql = ((FTableModel) ftable.getModel()).getHt_OtherTableSql();
        }
    }

    public void setHt_OtherTableSql(Hashtable<String, String> ht_OtherTableSql) {
        this.ht_OtherTableSql = ht_OtherTableSql;
    }

    public JComponent createEditorOf(final BeanAdapter adapter, final Object bean, final UIItemDetail uIItemDetail,
            boolean isNew) {
        final Field field = uIItemDetail.getField();
        String format_str = null;
        FieldAnnotation annotation = field.getAnnotation(FieldAnnotation.class);
        int align = JTextField.LEFT;
        if (annotation != null) {
            format_str = annotation.format();
            if ("居中".equals(annotation.field_align())) {
                align = JTextField.CENTER;
            } else if ("右对齐".equals(annotation.field_align())) {
                align = JTextField.RIGHT;
            }
        }
        final String bind_name = uIItemDetail.getBindName();
        field_keys.put(bind_name, field);
        if (bind_name.contains(".")) {
            Object data_obj = null;
            if (bind_name.startsWith("#")) {
                String[] tmp_s = bind_name.substring(1).split("\\.");
                String entity_name = tmp_s[0];
                HashMap tmp_hm = ht_OtherTable.get(entity_name);
                if (tmp_hm == null) {
                    String tmp_sql = ht_OtherTableSql.get(entity_name);
                    Object key_obj = PublicUtil.getProperty(bean, EntityBuilder.getEntityKey(bean.getClass()));
                    tmp_hm = new HashMap();
                    if (tmp_sql != null && key_obj != null) {
                        ArrayList objs = CommUtil.selectSQL("select " + tmp_sql + "='" + key_obj + "'");
                        String tmp_sel_fields = tmp_sql.substring(0, tmp_sql.indexOf("from"));
                        String[] sel_fields = tmp_sel_fields.split(",");
                        Object[] tmp_objs = null;
                        if (sel_fields.length == 1) {
                            tmp_objs = new Object[1];
                            tmp_objs[0] = objs.size() > 0 ? objs.get(0) : null;
                        } else {
                            tmp_objs = objs.size() > 0 ? (Object[]) objs.get(0) : null;
                        }
                        if (tmp_objs != null) {
                            for (int i = 0; i < sel_fields.length; i++) {
                                tmp_hm.put(sel_fields[i].trim(), tmp_objs[i]);
                            }
                        }
                        ht_OtherTable.put(entity_name, tmp_hm);
                    }
                }
                data_obj = tmp_hm.get(SysUtil.tranField(tmp_s[1]));
                if (data_obj == null) {
                    //加表名取值
                    data_obj = tmp_hm.get(SysUtil.tranField(tmp_s[0] + "." + tmp_s[1]));
                }
                if (bind_name.endsWith("_code_")) {
                    if (data_obj != null || !(data_obj instanceof Code)) {
                        TempFieldInfo tfi = EntityBuilder.getTempFieldInfoByName(bind_name);
                        if (tfi != null) {
                            data_obj = CodeManager.getCodeManager().getCodeBy(tfi.getCode_type_name(), data_obj == null ? null : data_obj.toString());
                        }
                    }
                }
            } else {
                data_obj = PublicUtil.getProperty(bean, bind_name);
            }
            String text = "";
            if (data_obj != null) {
                text = data_obj.toString();
                if (data_obj instanceof Boolean) {
                    text = ((Boolean) data_obj).booleanValue() ? "是" : "否";
                } else if (data_obj instanceof Date) {
                    if (format_str == null || "".equals(format_str)) {
                        format_str = "yyyy-MM-dd";
                    }
                    text = DateUtil.DateToStr((Date) data_obj, format_str);
                }
            }
            JTextField textfield = new JTextField(text);
            textfield.setEditable(false);
            initComponent(textfield, align);
            return textfield;
        }
        final ValueModel model = adapter.getValueModel(bind_name);
        EnumHint enumHint = field.getAnnotation(EnumHint.class);
        if (enumHint != null) {
            List<String> list = new ArrayList();
            if (enumHint.nullable()) {
                list.add(null);
            }
            for (String s : enumHint.enumList().split(";")) {
                list.add(s);
            }
            return createComboBoxEditor(bean, bind_name, list, model.getValue(), true, align);
        }
        if (field.getType().equals(org.jhrcore.entity.DeptCode.class)) {
            return createDeptEditor(adapter, bean, field, field_editor_able, editForChangeScheme);
        } else if (field.getType().equals((new ArrayList()).getClass())) {
            ParameterizedType ltType = (ParameterizedType) field.getGenericType();
            Class<?> ctype = (Class<?>) ltType.getActualTypeArguments()[0];
            if ("EntityDef".equals(ctype.getSimpleName())) {
                return createPersonClassEditor(adapter, bean, field, field_editor_able);
            }
        } else if (field.getType().equals(org.jhrcore.entity.right.FuntionRight.class)) {
            return createFunEditor(adapter, bean, field, field_editor_able);
        }

        final ObjectListHint objHint = field.getAnnotation(ObjectListHint.class);
        if (objHint != null) {
            if (objHint.hqlForObjectList().contains("from Code ")) {
                return createEditorOfCode(adapter, bean, bind_name, objHint, align);
            }
            if (!objHint.editType().equals("对话框")) {
                List list = getObjHintData(objHint, bean, bind_name);
                return createComboBoxEditor(bean, bind_name, list, model.getValue(), true, align);
            } else {
                final JTextField textfield = BasicComponentFactory.createTextField(model);
                textfield.setEditable(false);
                JPanel jpanel = new JPanel(new BorderLayout());
                jpanel.setBorder(null);
                jpanel.setFocusTraversalKeysEnabled(false);
                final JButton btnSelect = new JButton("...");
                jpanel.add(textfield, BorderLayout.CENTER);
                jpanel.add(btnSelect, BorderLayout.EAST);
                btnSelect.setPreferredSize(new Dimension(18, 22));
                btnSelect.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Object val = EditorFactory.getValueBy(bean, bind_name);
                        List list = getObjHintData(objHint, bean, bind_name);
                        HqlForObjectListDlg hfolDlg = new HqlForObjectListDlg(JOptionPane.getFrameForComponent(btnSelect), list, val);
                        ContextManager.locateOnMainScreenCenter(hfolDlg);
                        hfolDlg.setVisible(true);
                        if (hfolDlg.isClick_ok()) {
                            Object obj1 = hfolDlg.getSelect_obj();
                            EditorFactory.setValueBy(bean, bind_name, obj1);
                            textfield.setText(obj1 == null ? "" : obj1.toString());
                        }

                    }
                });
                ComponentUtil.setRightEditComponent(bean, bind_name, btnSelect, field_editor_able);
                initComponent(textfield, align, btnSelect.isEnabled());
                initComponent(btnSelect, align);
                return jpanel;
            }
        }
        if (field.getGenericType().toString().equals("int") || field.getType().equals(Integer.class)) {
            JTextField textfield = BasicComponentFactory.createIntegerField(model);
            ComponentUtil.setRightEditComponent(bean, bind_name, textfield, field_editor_able);
            initComponent(textfield, align);
            return textfield;
        }

        if (field.getType().equals(Boolean.class) || field.getGenericType().toString().equals("boolean")) {
            List<String> list = new ArrayList();
            list.add("是");
            list.add("否");
            final JComboBox cmBox = new JComboBox(list.toArray());
            cmBox.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    Object obj = cmBox.getSelectedItem();
                    model.setValue("是".equals(obj.toString()));
                }
            });
            try {
                cmBox.setSelectedIndex(new Boolean(model.getValue() == null ? "0" : model.getValue().toString()).booleanValue() ? 0 : 1);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            ComponentUtil.setRightEditComponent(bean, bind_name, cmBox, field_editor_able);
            initComponent(cmBox, align);
            return cmBox;
        }

        if (field.getType().equals(String.class)) {
            JTextField textfield = BasicComponentFactory.createTextField(model);
            ComponentUtil.setRightEditComponent(bean, bind_name, textfield, field_editor_able);
            initComponent(textfield, align);
            return textfield;
        }
        if (field.getType().equals(BigDecimal.class)) {
            JTextField textfield = BasicComponentFactory.createFormattedTextField(model, new DecimalFormatter(format_str));//df);
            ComponentUtil.setRightEditComponent(bean, bind_name, textfield, field_editor_able);
            initComponent(textfield, align);
            return textfield;
        }
        if (field.getType().equals(Float.class) || field.getGenericType().toString().equals("float")) {
            JTextField textfield = BasicComponentFactory.createFormattedTextField(model, new FloatFormatter(format_str));
            ComponentUtil.setRightEditComponent(bean, bind_name, textfield, field_editor_able);
            initComponent(textfield, align);
            return textfield;
        }
        if (field.getType().equals(Date.class)) {
            if (format_str == null || "".equals(format_str)) {
                format_str = "yyyy-MM-dd";
            }
            final MaskDateFormatter mf1 = MaskDateFormatter.getFormatter(format_str);//new MaskDateFormatter(getDateFormatStr(format_str), format_str);//new MaskDateFormatter(format_str);
            final JFormattedTextField picker = new JFormattedTextField(mf1);
            final String dateFormatStr = format_str;
            ComponentUtil.setRightEditComponent(bean, bind_name, picker, field_editor_able);
            initComponent(picker, align);
            final JDialog dialog = new JDialog(JOptionPane.getFrameForComponent(picker), "日期时间选择", true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent e) {
                    enabled_PropertyChangeListener = false;
                    picker.requestFocus();
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    enabled_PropertyChangeListener = true;
                }
            });
            picker.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!picker.isEditable()) {
                        return;
                    }
                    if (e.getClickCount() < 2) {
                        return;
                    }
                    if (!dateFormatStr.toLowerCase().contains("d")) {
                        return;
                    }
                    DateChooser dateChooser = new DateChooser(dateFormatStr);
                    Date date = DateUtil.StrToDate((String) picker.getText(), mf1.getFormat());
                    dateChooser.setDate2(date == null ? new Date() : date);
                    dateChooser.setDialog(dialog);
                    dialog.getContentPane().removeAll();
                    dialog.getContentPane().add(dateChooser, BorderLayout.CENTER);
                    dialog.pack();
                    dialog.setModal(true);
                    Dimension d = dialog.getSize();
                    Point p = picker.getLocationOnScreen();
                    dialog.setLocation(getScreenPoint(p, d, 40, 40));
                    dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                    if (dateChooser.isPicked()) {
                        try {
                            enabled_PropertyChangeListener = true;
                            Date date1 = dateChooser.getDate();
                            setValueBy(bean, bind_name, date1);
                            model.setValue(new Timestamp(date1.getTime()));
                            picker.setText(mf1.getDate_format().format(date1));
                        } catch (IllegalArgumentException ex) {
                            log.error(ex.getMessage());
                        }
                    }
                }
            });
            picker.addFocusListener(new FocusAdapter() {

                @Override
                public void focusLost(FocusEvent e) {
                    boolean need_change = false;
                    if (!dialog.isVisible()) {
                        Object obj = e.getOppositeComponent();
                        while (obj != null && obj instanceof JComponent) {
                            if (obj == BeanPanel.this) {
                                need_change = true;
                                break;
                            }
                            obj = ((JComponent) obj).getParent();
                        }
                    }
                    if (!need_change) {
                        enabled_PropertyChangeListener = false;
                    }
                    String tmp = (String) picker.getText();
                    Date oldValue = (Date) model.getValue();
                    if (mf1.getReplace_key().replace("#", "_").equals(tmp)) {
                        if (oldValue == null) {
                            return;
                        }
                        model.setValue(null);
                        picker.setValue(null);
                        enabled_PropertyChangeListener = true;
                        return;
                    }
                    Date date = DateUtil.StrToDate(tmp, mf1.getFormat());
                    if (date == null) {
                        JOptionPane.showMessageDialog(picker, "错误的日期格式!");
                        try {
                            model.setValue(null);
                            picker.setValue(null);
                            picker.requestFocus();
                        } catch (IllegalArgumentException ex) {
                            log.error(ex.getMessage());
                        }
                    } else {
                        if (DateUtil.DateToStr(oldValue, mf1.getFormat()).equals(tmp)) {
                            return;
                        }
                        setValueBy(bean, bind_name, date);
                        model.setValue(new Timestamp(date.getTime()));
                        picker.setText(mf1.getDate_format().format(date));
                    }
                    enabled_PropertyChangeListener = true;
                }
            });

            if (model.getValue() != null) {
                picker.setText(mf1.getDate_format().format(PublicUtil.getProperty(bean, bind_name)));
            }
            return picker;
        }

        if (field.getType().getName().contains("org.jhrcore.entity")) {
            JTextField textfield = new JTextField();
            textfield.setText((PublicUtil.getProperty(bean, bind_name) == null) ? "" : PublicUtil.getProperty(bean, bind_name).toString());
            textfield.setEditable(false);
            initComponent(textfield, align);
            return textfield;
        }
        if (Model.class.isAssignableFrom(field.getType())) {
            JTextField textfield = BasicComponentFactory.createTextField(model);
            textfield.setEditable(false);
            initComponent(textfield, align);
            return textfield;
        }
        if (field.getType().equals(Color.class)) {
            JFormattedTextField picker = new JFormattedTextField();
            picker.setFocusTraversalKeysEnabled(false);
            picker.setEnabled(field_editor_able);
            picker.setLayout(new BorderLayout());
            final PaintSample sample = new PaintSample(this.getBackground());
            final JButton btnSelelect = new JButton("");
            btnSelelect.setEnabled(field_editor_able);
            btnSelelect.setPreferredSize(new Dimension(18, btnSelelect.getPreferredSize().height));
            btnSelelect.setVisible(false);
            cell_value = this.getBackground();
            if (PublicUtil.getProperty(bean, bind_name) != null) {
                cell_value = (Color) PublicUtil.getProperty(bean, bind_name);
            }
            sample.setPaint(cell_value);
            picker.add(btnSelelect, BorderLayout.EAST);
            picker.add(sample, BorderLayout.CENTER);
            btnSelelect.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    Color c = JColorChooser.showDialog(null, "选择颜色", (Color) cell_value);
                    if (c != null) {
                        sample.setPaint(c);
                        PublicUtil.setValueBy2(bean, bind_name, c);
                    }
                }
            });
            picker.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getClickCount() >= 2) {
                        btnSelelect.setVisible(field_editor_able);
                    }
                }
            });
            return picker;
        }
        return new JTextField("无法创建编辑器");
    }

    private void initComponent(JComponent c, int align) {
        initComponent(c, align, false);
    }

    private void initComponent(JComponent c, int align, boolean editable) {
        c.setForeground(ColorUtil.commForegroundColor);
        if (c instanceof JTextField) {
            JTextField jtf = (JTextField) c;
            jtf.setHorizontalAlignment(align);
            c.setBackground((jtf.isEditable() || editable) ? ColorUtil.commBackgroundColor : ColorUtil.noBackgroundColor);
            c.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
            c.setPreferredSize(new Dimension(c.getWidth(), 22));
        } else if (c instanceof JComboBox) {
            ((JComboBox) c).setRenderer(new ComboBoxCellRenderer(align));
            c.setPreferredSize(new Dimension(c.getWidth(), 22));
        } else if (c instanceof JButton) {
            c.setPreferredSize(new Dimension(20, 22));
        }
        if (c.isEnabled()) {
            addFocusListener(c);
            new EnterToTab(c);
        }
    }

    private JComponent createComboBoxEditor(final Object bean, final String bind_name, final List items, final Object value, boolean enable, final int horizontalAlignment) {
        final JComboBox cmBox = new JComboBox(items.toArray());
        ComponentUtil.setRightEditComponent(bean, bind_name, cmBox, field_editor_able && enable);
        initComponent(cmBox, horizontalAlignment);
        if (value != null) {
            cmBox.setSelectedItem(value);
        }
        cmBox.setOpaque(true);
        cmBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cmBox.getSelectedItem() == null) {
                    return;
                }
                setValueBy(bean, bind_name, cmBox.getSelectedItem());
            }
        });
        boolean editable = cmBox.isEnabled();
        if (!editable) {
            cmBox.setEditable(true);
            cmBox.setFocusable(false);
            cmBox.setEnabled(true);
            Component[] cs = cmBox.getComponents();
            for (Component c : cs) {
                if (c instanceof JTextField) {
                    continue;
                }
                c.setEnabled(false);
            }
            java.awt.Component comp = cmBox.getEditor().getEditorComponent();
            if (comp instanceof JTextField) {
                JTextField field = (JTextField) comp;
                field.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
                field.setEditable(false);
                field.setBackground(ColorUtil.noBackgroundColor);
                field.setSelectionColor(ColorUtil.noBackgroundColor);
            }
            cmBox.disable();
        }
        new EnterToTab(cmBox);
        addFocusListener(cmBox);
        return cmBox;
    }

    private Point getScreenPoint(Point screen_point, Dimension component_size, int x_move, int y_move) {
        Point point = new Point();
        int x = screen_point.x;
        int y = screen_point.y;
        if (x + component_size.width > screen_size.width) {
            x = screen_size.width - component_size.width - x_move;
        }
        if (y + component_size.height > screen_size.height) {
            y = screen_size.height - component_size.height - y_move;
        }
        point.x = x;
        point.y = y;
        return point;
    }

    private static JComponent createFunEditor(BeanAdapter adapter, final Object bean, final Field field, boolean field_editor_able) {
        final JTextField textfield = new JTextField();
        textfield.setPreferredSize(new Dimension(textfield.getWidth(), 22));
        textfield.setEditable(false);
        ValueModel model = adapter.getValueModel(field.getName());
        Object obj = model.getValue();
        textfield.setText(obj == null ? "" : ((FuntionRight) obj).getFun_name());
        final JPanel jpanel = new JPanel(new BorderLayout());
        jpanel.setFocusTraversalKeysEnabled(false);
        final JButton btnSelect = new JButton("");
        btnSelect.setEnabled(field_editor_able);
        btnSelect.setPreferredSize(new Dimension(18, 22));
        jpanel.add(textfield, BorderLayout.CENTER);
        jpanel.add(btnSelect, BorderLayout.EAST);
        btnSelect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e2) {
                List modules = new ArrayList();
                for (Object obj : UserContext.funtion_list) {
                    FuntionRight fr = (FuntionRight) obj;
                    if (fr.isModule_flag()) {
                        modules.add(fr);
                    }
                }
                FuntionTreeModel model = new FuntionTreeModel(modules);
                JTree tree = new JTree(model);
                JPanel pnl = new JPanel(new BorderLayout());
                pnl.add(new JScrollPane(tree));
                pnl.setPreferredSize(new Dimension(400, 350));
                HRRendererView.getFunMap(tree).initTree(tree);
                ComponentUtil.initTreeSelection(tree);
                if (ModalDialog.doModal(btnSelect, pnl, "请选择模块：", tree)) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    FuntionRight fr = null;
                    if (node.getUserObject() instanceof FuntionRight) {
                        fr = (FuntionRight) node.getUserObject();
                    }
                    textfield.setText(fr == null ? "" : fr.getFun_name());
                    String field_name = field.getName();
                    try {
                        Class field_class = field.getType();
                        Method method = bean.getClass().getMethod("set" + field_name.substring(0, 1).toUpperCase() + field_name.substring(1), new Class[]{field_class});
                        method.invoke(bean, new Object[]{fr});
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return jpanel;
    }

    // 创建部门编辑器

    private static JComponent createDeptEditor(BeanAdapter adapter, final Object bean, final Field field,
            boolean canModify, final boolean editForChangeScheme) {
        final JTextField textfield = new JTextField();
        textfield.setPreferredSize(new Dimension(textfield.getWidth(), 22));
        textfield.setEditable(false);
        final ValueModel model = adapter.getValueModel(field.getName());
        Object obj = model.getValue();
        textfield.setText(obj == null ? "" : (((DeptCode) obj).getContent() + "{" + ((DeptCode) obj).getDept_code() + "}"));
        final JPanel jpanel = new JPanel(new BorderLayout());
        jpanel.setFocusTraversalKeysEnabled(false);
        JButton btnSelect = new JButton("");
        btnSelect.setEnabled(canModify);
        btnSelect.setPreferredSize(new Dimension(18, 22));
        jpanel.add(textfield, BorderLayout.CENTER);
        jpanel.add(btnSelect, BorderLayout.EAST);
        textfield.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    doDeptAction(model, textfield, bean, field, editForChangeScheme);
                }
            }
        });
        btnSelect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e2) {
                doDeptAction(model, textfield, bean, field, editForChangeScheme);
            }
        });
        return jpanel;
    }

    private static void doDeptAction(final ValueModel model, JTextField textfield, Object bean, Field field, boolean editForChangeScheme) {
        List<DeptCode> depts = UserContext.getDepts(false, false);
        if (editForChangeScheme) {
            depts = (List<DeptCode>) CommUtil.fetchEntities("from DeptCode d where d.del_flag=0 order by dept_code");
        }
        DeptSelectDlg dlg = new DeptSelectDlg(depts, model.getValue(), deptCode_chind_only_flag ? TreeSelectMod.leafSelectMod : TreeSelectMod.nodeSelectMod);
        ContextManager.locateOnMainScreenCenter(dlg);
        dlg.setVisible(true);
        if (dlg.isClick_ok()) {
            textfield.setText(dlg.getCurDept().getContent() + "{" + dlg.getCurDept().getDept_code() + "}");
            String field_name = field.getName();
            try {
                Class field_class = field.getType();
                Method method = bean.getClass().getMethod("set" + field_name.substring(0, 1).toUpperCase() + field_name.substring(1), new Class[]{field_class});
                method.invoke(bean, new Object[]{dlg.getCurDept()});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static JComponent createPersonClassEditor(BeanAdapter adapter, final Object bean, final Field field,
            boolean canModify) {
        final JTextField textfield = new JTextField();
        final String field_name = field.getName();
        textfield.setPreferredSize(new Dimension(textfield.getWidth(), 22));
        textfield.setEditable(false);
        textfield.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    textfield.setText("");
                    try {
                        Class field_class = field.getType();
                        Method method = bean.getClass().getMethod("set" + field_name.substring(0, 1).toUpperCase() + field_name.substring(1), new Class[]{field_class});
                        method.invoke(bean, new Object[]{null});
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        final ValueModel model = adapter.getValueModel(field.getName());
        Object obj = model.getValue();
        textfield.setText(obj == null ? "" : obj.toString());
        final JPanel jpanel = new JPanel(new BorderLayout());
        jpanel.setFocusTraversalKeysEnabled(false);
        JButton btnSelect = new JButton("");
        btnSelect.setEnabled(canModify);
        btnSelect.setPreferredSize(new Dimension(18, 22));
        jpanel.add(textfield, BorderLayout.CENTER);
        jpanel.add(btnSelect, BorderLayout.EAST);
        btnSelect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e2) {
                String key = "";
                PersonClassSelectDlg dlg = new PersonClassSelectDlg();
                dlg.checkedPerson_calss(key);
                ContextManager.locateOnMainScreenCenter(dlg);
                dlg.setVisible(true);
                if (dlg.isClick_ok()) {
                    String keys = dlg.GetPerson_calss_key();
                    ArrayList<EntityDef> lt = dlg.GetPerson_calss_list();
                    textfield.setText(lt.toString());
                    PublicUtil.setValueBy2(bean, "a0191s", keys);
                    PublicUtil.setValueBy2(bean, "entityDefs", lt);
                }
            }
        });
        return jpanel;
    }

    private JComponent createEditorOfCode(final BeanAdapter adapter, final Object bean, final String bind_name,
            final ObjectListHint objectListHint, int horizontalAlignment) {
        final TempCodeEditor editor = TempCodeEditor.getCodeEditor(bean, objectListHint, adapter.getValue(bind_name));
        if (editor.isSingle()) {
            return createComboBoxEditor(bean, bind_name, editor.getCodes(), editor.getCell_value(), editor.isEnable(), horizontalAlignment);
        }
        final JPanel jpanel = new JPanel(new BorderLayout());
        final Object theBean = bean;
        final JTextField textfield = new JTextField();
        textfield.setEditable(false);
        textfield.setText(editor.getCell_value() == null ? "" : editor.getCell_value().toString());
        jpanel.setFocusTraversalKeysEnabled(false);
        final JButton btnSelect = new JButton("...");
        ComponentUtil.setRightEditComponent(bean, bind_name, btnSelect, field_editor_able && editor.isEnable());
        initComponent(textfield, horizontalAlignment, btnSelect.isEnabled());
        initComponent(btnSelect, horizontalAlignment);
        jpanel.add(textfield, BorderLayout.CENTER);
        jpanel.add(btnSelect, BorderLayout.EAST);
        textfield.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2 && btnSelect.isEnabled()) {
                    TempCodeEditor editor1 = TempCodeEditor.getCodeEditor(bean, objectListHint, adapter.getValue(bind_name));
                    doCodeAction(theBean, textfield, editor1, jpanel, bind_name);
                }
            }
        });
        btnSelect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                TempCodeEditor editor1 = TempCodeEditor.getCodeEditor(bean, objectListHint, adapter.getValue(bind_name));
                doCodeAction(theBean, textfield, editor1, jpanel, bind_name);
            }
        });
        return jpanel;
    }

    private void doCodeAction(final Object theBean, final JTextField textfield, final TempCodeEditor editor, final JPanel jpanel, final String bind_name) {
        CodeSelectDialog csDialog = new CodeSelectDialog(editor.getCodes(), editor.getCode_type(), editor.getCell_value());// model.getValue());
        csDialog.addPickCodeSelectListener(new IPickCodeSelectListener() {

            @Override
            public void pickCode(DefaultMutableTreeNode node) {
                if (node != null) {
                    textfield.setText(node.getUserObject().toString());
                    editor.setCell_value(node.getUserObject());
                    setValueBy(theBean, bind_name, editor.getCell_value());
                }
            }
        });
        csDialog.setTitle("请选择");
        Dimension d = csDialog.getSize();
        Point p = jpanel.getLocationOnScreen();
        csDialog.setLocation(getScreenPoint(p, d, 40, 40));
        csDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        csDialog.setVisible(true);
    }
    private static boolean enabled_PropertyChangeListener = true;
    private PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt == null) {
                return;
            }
            if (!BeanPanel.this.editable) {
                return;
            }
            bean_changed = true;
            if (!enabled_PropertyChangeListener) {
                return;
            }
            String bind_name = evt.getPropertyName();
            try {
                if (component_keys.get(bind_name) == null) {
                    return;
                }
                if (disable_fields != null && disable_fields.contains(bind_name)) {
                    return;
                }
                if (!FieldTrigerManager.getFieldTrigerManager().validateunotnull(bind_name, bean, evt.getOldValue(), evt.getNewValue())
                        || !FieldTrigerManager.getFieldTrigerManager().validate(bind_name, bean, evt.getOldValue(), evt.getNewValue())) {
                    enabled_PropertyChangeListener = false;
                    PublicUtil.setValueBy2(bean, bind_name, evt.getOldValue());
                    bindUI();
                    Component com = component_keys.get(bind_name);
                    com.requestFocus();
                    return;
                }
            } finally {
                Runnable run = new Runnable() {

                    @Override
                    public void run() {
                        enabled_PropertyChangeListener = true;
                    }
                };
                SwingUtilities.invokeLater(run);
            }

            Object result = FieldTrigerManager.getFieldTrigerManager().triger(evt.getPropertyName(), bean, evt.getOldValue(), evt.getNewValue());
            if (result != null) {
                bindUI();
                final Component com = component_keys.get(bind_name);
                if (com == null) {
                    return;
                }
                Runnable run = new Runnable() {

                    @Override
                    public void run() {
                        KeyEvent ke = new KeyEvent(com, KeyEvent.KEY_PRESSED,
                                50, 0, KeyEvent.VK_TAB);
                        com.dispatchEvent(ke);

                    }
                };
                SwingUtilities.invokeLater(run);
                return;
            }
            for (String key : field_keys.keySet()) {
                Field field = field_keys.get(key);
                ObjectListHint objHint = field.getAnnotation(ObjectListHint.class);
                if (objHint == null) {
                    continue;
                }
                if (!objHint.hqlForObjectList().startsWith("from Code ")) {
                    continue;
                }
                Component com = component_keys.get(key);
                if (com == null) {
                    continue;
                }
                if (!(com instanceof JComboBox)) {
                    continue;
                }
                JComboBox jcb = (JComboBox) com;
                int items = jcb.getItemCount();
                List list = CodeManager.getCodeManager().getLimitCodes(bean, objHint);
                if (items != list.size()) {
                    bindUI();
                    return;
                }
                for (int i = 0; i < items; i++) {
                    Object obj = jcb.getItemAt(i);
                    if (!list.contains(obj)) {
                        bindUI();
                        break;
                    }
                }
            }
        }
    };

    private void setBean2(Object bean) {
        ht_OtherTable.clear();
        if (bean == null) {
            this.bean = bean;
            return;
        }
        if (this.bean != null) {
            Model md = (Model) this.bean;
            md.removePropertyChangeListener(propertyChangeListener);
        }
        this.bean = bean;
        Model md = (Model) this.bean;
        md.addPropertyChangeListener(propertyChangeListener);
        bean_changed = false;
    }

    private void setValueBy(Object data_obj, String fieldName, Object val_obj) {
        List<String> tmp_list = new ArrayList<String>();
        int start_ind = 0;
        for (int i = 0; i < fieldName.length(); i++) {
            if (fieldName.charAt(i) == '.') {
                tmp_list.add(fieldName.substring(start_ind, i));
                start_ind = i + 1;
            } else if (i == fieldName.length() - 1) {
                tmp_list.add(fieldName.substring(start_ind));
            }
        }


        Class aclass = data_obj.getClass();
        for (int i = 0; i < tmp_list.size() - 1; i++) {
            String field_name = tmp_list.get(i);
            try {
                Method method = aclass.getMethod("get" + field_name.substring(0, 1).toUpperCase() + field_name.substring(1), new Class[]{});
                data_obj = method.invoke(data_obj, new Object[]{});
                if (data_obj == null) {
                    break;
                }
                aclass = data_obj.getClass();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        String field_name = tmp_list.get(tmp_list.size() - 1);
        try {
            Class field_class = aclass.getField(field_name).getType();
            Method method = aclass.getMethod("set" + field_name.substring(0, 1).toUpperCase() + field_name.substring(1), new Class[]{field_class});
            data_obj = method.invoke(data_obj, new Object[]{val_obj});
            //if (data_obj == null) {
            //    break;
            //}
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public boolean checkEditable(String bind_name, FieldAnnotation fieldAnnotation, boolean isEditable, boolean isNew) {
        boolean result = false;
        if (!editable) {
            result = false;
        } else if (isEditable) {
            int field_editable = UserContext.getFieldRight(bean == null ? null : bean.getClass(), bind_name);
            if (isNew && fieldAnnotation.editableWhenNew() && field_editable == 1) {
                result = true;
            } else if (!isNew && fieldAnnotation.editableWhenEdit() && field_editable == 1) {
                result = true;
            } else {
                diseditable_fields.add(bind_name);
            }
        } else {
            diseditable_fields.add(bind_name);
        }

        return result;
    }

    public static boolean edit(Object bean) {
        return edit(bean, null);
    }

    public static boolean edit(final Object bean, List<String> fields,
            final ValidateEntity validateEntity) {
        return edit(bean, fields, "编辑", validateEntity);
    }

    public static boolean edit(final Object bean, List<String> fields,
            String title, final ValidateEntity validateEntity) {
        return edit((JFrame) null, bean, fields, title, validateEntity);
    }

    public static void clearObjHint_dataByEntityName(String entityName) {
        List<String> remove_keys = new ArrayList<String>();
        for (String key : objHint_data.keySet()) {
            if (key.startsWith(entityName)) {
                remove_keys.add(key);
            }
        }
        for (String key : remove_keys) {
            objHint_data.remove(key);
        }
    }

    /**
     * 弹出一个关于Bean的编辑框
     * @param bean：显示的对象
     * @param fields：显示属性列表，当为空时直接显示对象本身熟悉
     * @param title：对话框标题
     * @param validateEntity：验证器
     * @return
     */
    public static boolean edit(JFrame jf, final Object bean, List<String> fields,
            String title, final ValidateEntity validateEntity) {
        dlg_result = false;
        final JDialog dlg = new JDialog((JFrame) null, title);
        dlg.setIconImage(ImageUtil.getIconImage());
        dlg.setModal(true);
        JPanel pnl_comm = new JPanel(new FlowLayout());
        JButton btn_ok = new JButton("确定");
        JButton btn_cancel = new JButton("取消");
        pnl_comm.add(btn_ok);
        pnl_comm.add(btn_cancel);
        dlg.getContentPane().add(pnl_comm, BorderLayout.SOUTH);
        String entityName = bean.getClass().getSimpleName();
        clearObjHint_dataByEntityName(entityName);
        BeanPanel panel = new BeanPanel(bean, fields);
        panel.setBean(bean);
        if (fields.size() > 0) {
            panel.setFields(fields);
        }
        panel.setColumns(1);
        if (fields.size() >= 8) {
            panel.setColumns(2);
        }
        panel.setEditable(true);
        panel.bind();
        dlg.getContentPane().add(new JScrollPane(panel), BorderLayout.CENTER);

        dlg.setSize(panel.getPreferredSize().width + 50,
                panel.getPreferredSize().height + 80);

        Dimension paneSize = dlg.getSize();
        Dimension screenSize = dlg.getToolkit().getScreenSize();
        dlg.setLocation((screenSize.width - paneSize.width) / 2,
                (screenSize.height - paneSize.height) / 2);

        btn_cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                dlg_result = false;
                dlg.dispose();
            }
        });
        dlg.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                dlg_result = false;
                dlg.dispose();
            }
        });
        btn_ok.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if ((validateEntity != null) && (!validateEntity.isEntityValidate(bean))) {
                        return;
                    }
                    dlg_result = true;
                    dlg.dispose();
                }
            }
        });

        btn_ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if ((validateEntity != null) && (!validateEntity.isEntityValidate(bean))) {
                    return;
                }
                dlg_result = true;
                dlg.dispose();
            }
        });
        dlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dlg.setVisible(true);
        return dlg_result;
    }

    public static boolean edit(final Object bean,
            final ValidateEntity validateEntity) {
        return edit(bean, new ArrayList(), "编辑", validateEntity);
    }

    public static boolean edit(final Object bean,
            String title, final ValidateEntity validateEntity) {
        return edit(bean, new ArrayList(), title, validateEntity);
    }

    public boolean isBean_changed() {
        return bean_changed;
    }

    /**
     * 弹出一个关于Bean的编辑框
     * @param bean：显示的对象
     * @param fields：显示属性列表，当为空时直接显示对象本身熟悉
     * @param title：对话框标题
     * @param validateEntity：验证器
     * @return
     */
    public static void editForRepeat(JFrame jf, List<String> fields,
            Object title, final ValidateEntity validateEntity, final IPickBeanPanelEditListener listener) {
        int cols = 1;
        if (fields.size() >= 8) {
            cols = 2;
        }
        editForRepeat(jf, fields, cols, title, validateEntity, listener);
    }

    /**
     * 弹出一个关于Bean的编辑框
     * @param bean：显示的对象
     * @param fields：显示属性列表，当为空时直接显示对象本身熟悉
     * @param title：对话框标题
     * @param validateEntity：验证器
     * @return
     */
    public static void editForRepeat(JFrame jf, List<String> fields, int cols,
            Object title, final ValidateEntity validateEntity, final IPickBeanPanelEditListener listener) {
        final JDialog dlg = new JDialog((JFrame) null, SysUtil.objToStr(title));
        dlg.setIconImage(ImageUtil.getIconImage());
        dlg.setModal(true);
        dlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dlg.setIconImage(UIContext.getCurrenFace().getSmallLogo());
        JPanel pnl_comm = new JPanel(new FlowLayout());
        final JCheckBox jcb_repeat = new JCheckBox("保存后重新生成");
        JButton btn_ok = new JButton("确定");
        JButton btn_cancel = new JButton("取消");
        pnl_comm.add(jcb_repeat);
        pnl_comm.add(btn_ok);
        pnl_comm.add(btn_cancel);
        dlg.getContentPane().add(pnl_comm, BorderLayout.SOUTH);
        Object bean = listener.getNew();
        String entityName = bean.getClass().getSimpleName();
        List<String> remove_keys = new ArrayList<String>();
        for (String key : objHint_data.keySet()) {
            if (key.startsWith(entityName)) {
                remove_keys.add(key);
            }
        }
        for (String key : remove_keys) {
            objHint_data.remove(key);
        }
        final BeanPanel panel = new BeanPanel(bean, fields);
        panel.setBean(bean);
        if (fields.size() > 0) {
            panel.setFields(fields);
        }
        panel.setColumns(cols);
        panel.setEditable(true);
        panel.bind();
        dlg.getContentPane().add(new JScrollPane(panel), BorderLayout.CENTER);
        int width = panel.getPreferredSize().width + 60;
        int height = panel.getPreferredSize().height + 100;
        width = width < 400 ? 400 : width;
        width = width > 800 ? 800 : width;
        height = height < 350 ? 350 : height;
        height = height > 600 ? 600 : height;
        dlg.setSize(width,
                height);
        Dimension paneSize = dlg.getSize();
        Dimension screenSize = dlg.getToolkit().getScreenSize();
        dlg.setLocation((screenSize.width - paneSize.width) / 2,
                (screenSize.height - paneSize.height) / 2);
        btn_cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                dlg.dispose();
            }
        });
        btn_ok.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                if ((validateEntity != null) && (!validateEntity.isEntityValidate(panel.getBean()))) {
                    return;
                }
                listener.pickSave(panel.getBean());
                if (jcb_repeat.isSelected()) {
                    panel.setBean(listener.getNew());
                    panel.bind();
                    return;
                }
                dlg.dispose();
            }
        });

        btn_ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if ((validateEntity != null) && (!validateEntity.isEntityValidate(panel.getBean()))) {
                    return;
                }
                listener.pickSave(panel.getBean());
                if (jcb_repeat.isSelected()) {
                    panel.setBean(listener.getNew());
                    panel.bind();
                    return;
                }
                dlg.dispose();
            }
        });
        dlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dlg.setVisible(true);
    }

    public static List getObjHintData(ObjectListHint objectListHint, Object bean, String bind_name) {
        if (bean == null) {
            return new ArrayList();
        }
        String hql = objectListHint.hqlForObjectList();
        List list = objHint_data.get(bean.getClass().getSimpleName() + "." + bind_name);
        if (list == null) {
            list = new ArrayList();
            if (hql.contains("[") && hql.contains("]")) {
                String para = hql.substring(hql.indexOf("["), hql.indexOf("]") + 1);
                Object obj = PublicUtil.getProperty(bean, para.replace("[", "").replace("]", ""));
                String value = "";
                if (hql.contains("from FieldFormat")) {
                    String field_type = obj == null ? "-1" : obj.toString();
                    if (field_type.equals("BigDecimal")) {
                        field_type = "Float";
                    }
                    value = "'" + field_type + "'";
                } else {
                    if (obj instanceof String) {
                        value = "'" + ((obj == null) ? "-1" : obj.toString()) + "'";
                    } else if (obj instanceof Date) {
                        value = DateUtil.toStringForQuery((obj == null) ? new Date() : (Date) obj);
                    } else {
                        value = ((obj == null) ? "'-1'" : (obj.toString()));
                    }
                }
                if (para != null && !para.trim().equals("")) {
                    PublicUtil.getProps_value().setProperty(para, value);
                    hql = PublicUtil.replaceProperty(hql, para);
                }
                list.addAll(CommUtil.fetchEntities(hql));
            } else {
                list.addAll(CommUtil.fetchEntities(hql));
            }
            if (objectListHint.nullable()) {
                list.add(0, null);
            }
            objHint_data.put(bean.getClass().getSimpleName() + "." + bind_name, list);
        }
        return list;
    }

    public static void refreshUIForTable(FTable ftable, BeanPanel bp, boolean editable) {
        if (ftable == null) {
            return;
        }
        bp.setBean(ftable.getCurrentRow());
        bp.setShow_scheme(ftable.getCur_show_scheme());
        bp.setFields(ftable.getFields());
        bp.setEditable(editable);
        bp.setHt_OtherTableSql(((FTableModel) ftable.getModel()).getHt_OtherTableSql());
        bp.bind();
        bp.updateUI();
    }

    public Hashtable<String, HashMap> getHt_OtherTable() {
        return ht_OtherTable;
    }
}
