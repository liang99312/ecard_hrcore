/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import org.jhrcore.ui.renderer.MyComboBoxEditor;
import org.jhrcore.ui.listener.IDatePickerListener;
import com.jgoodies.binding.beans.Model;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.Converter;
import org.jdesktop.beansbinding.Validator;
import org.jdesktop.swingbinding.SwingBindings;
import org.jhrcore.client.CommUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.entity.Code;
import org.jhrcore.comm.CodeManager;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;
import org.jhrcore.entity.annotation.ObjectListHint;
import org.jhrcore.ui.renderer.ComboBoxCellRenderer;

/**
 *
 * @author wangzhenhua
 * 该类用于生成各种不同属性的编辑器
 */
public class EditorFactory {

    public static boolean editForChangeScheme = false;
    public static boolean onlyChildDept = false;
    public static boolean onlyChildCode = false;

    public static Component createParaEditorOf(Object bean, final String bindName) {
        int row_height = 25;
        int row_width = 180;
        String fieldName = bindName;
        Object data_obj = bean;
        Field field = null;
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
        for (int i = 0; i < tmp_list.size(); i++) {
            String field_name = tmp_list.get(i);
            try {
                field = aclass.getField(field_name);
                aclass = field.getType();
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(EditorFactory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(EditorFactory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        EnumHint enumHint = field.getAnnotation(EnumHint.class);
        if (enumHint != null) {
            JPanel pnl_tmp = new JPanel();
            pnl_tmp.setLayout(new BoxLayout(pnl_tmp, BoxLayout.Y_AXIS));
            String[] enums = enumHint.enumList().split(";");
            for (String s : enums) {
                pnl_tmp.add(new JCheckBox(s, false));
            }
            JScrollPane sp = new JScrollPane(pnl_tmp);
            sp.setPreferredSize(new Dimension(row_width, row_height * enums.length));
            return sp;
        }

        if ((bean instanceof DeptCode) && (bindName.equals("dept_code") || bindName.equals("content"))) {
            DeptSelectPanel pnl = new DeptSelectPanel(UserContext.getDepts(false, false), null, onlyChildDept ? TreeSelectMod.leafCheckMod : TreeSelectMod.nodeCheckMod);
            JTree dept_tree = pnl.getDept_tree();
            JScrollPane sp = new JScrollPane(dept_tree);
            sp.setPreferredSize(new Dimension(row_width, 10 * row_height));
            return sp;
        }

        ObjectListHint objHint = field.getAnnotation(ObjectListHint.class);
        if (objHint != null) {
            if (objHint.hqlForObjectList().startsWith("from Code ")) {
                String hql = objHint.hqlForObjectList();
                String code_type = hql.substring(hql.indexOf("=") + 1);
                CodeSelectDialog dlg = new CodeSelectDialog(CodeManager.getCodeManager().getCodeListBy(code_type), code_type, null, TreeSelectMod.nodeCheckMod);
                JTree code_tree = dlg.getCodeTree();
                JScrollPane sp = new JScrollPane(code_tree);
                sp.setPreferredSize(new Dimension(row_width, row_height * Math.min(6, CodeManager.getCodeManager().getCodeListBy(code_type).size())));
                return sp;
            }
            JPanel pnl_tmp = new JPanel();
            pnl_tmp.setLayout(new BoxLayout(pnl_tmp, BoxLayout.Y_AXIS));
            List list = CommUtil.fetchEntities(PublicUtil.replaceProperty(objHint.hqlForObjectList()));
            for (Object s : list) {
                pnl_tmp.add(new JCheckBox(s.toString(), false));
            }
            JScrollPane sp = new JScrollPane(pnl_tmp);
            sp.setPreferredSize(new Dimension(row_width, row_height * list.size()));
            return sp;
        }

        if (field.getGenericType().toString().equals("int") || field.getType().equals(Integer.class)) {
            JTextArea textfield = new JTextArea();
            textfield.setLineWrap(true);
            JScrollPane sp = new JScrollPane(textfield);
            sp.setPreferredSize(new Dimension(row_width, row_height * 2));
            return sp;
        }

        if (field.getType().equals(Boolean.class) || field.getGenericType().toString().equals("boolean")) {
            JPanel pnl_tmp = new JPanel();
            pnl_tmp.setLayout(new BoxLayout(pnl_tmp, BoxLayout.Y_AXIS));
            pnl_tmp.add(new JCheckBox("是", false));
            pnl_tmp.add(new JCheckBox("否", false));
            JScrollPane sp = new JScrollPane(pnl_tmp);
            sp.setPreferredSize(new Dimension(row_width, row_height * 2));
            return sp;
        }

        if (field.getType().equals(String.class)) {
            JTextArea textfield = new JTextArea();
            textfield.setLineWrap(true);
            JScrollPane sp = new JScrollPane(textfield);
            sp.setPreferredSize(new Dimension(row_width, row_height * 2));
            return sp;
        }

        if (field.getType().equals(float.class) || field.getType().equals(Float.class) || field.getType().equals(java.math.BigDecimal.class)) {
            JTextArea textfield = new JTextArea();
            textfield.setLineWrap(true);
            JScrollPane sp = new JScrollPane(textfield);
            sp.setPreferredSize(new Dimension(row_width, row_height * 2));
            return sp;
        }

        if (field.getType().equals(Date.class)) {
            JTextArea textfield = new JTextArea();
            textfield.setLineWrap(true);
            JScrollPane sp = new JScrollPane(textfield);
            sp.setPreferredSize(new Dimension(row_width, row_height * 2));
            return sp;
        }

        if (Model.class.isAssignableFrom(field.getType())) {
            JTextField textfield = new JTextField();
            BeanProperty beanP = BeanProperty.create(bindName);
            BeanProperty textP = BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST");
            Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, bean,
                    beanP, textfield, textP, bindName);
            binding.bind();
            return textfield;
        }
        return new JTextField("无法创建编辑器");
    }

    @SuppressWarnings("unchecked")
    public static Binding createComponentBinding(Object bean, String beanP, JComponent component, String textP) {
        if (component instanceof JTextField) {
            JTextField field = (JTextField) component;
            Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, bean, BeanProperty.create(beanP),
                    field, BeanProperty.create(textP), beanP);
            return binding;
        }
        if (component instanceof JComboBox) {
            JComboBox comboBox = (JComboBox) component;
            Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, bean, BeanProperty.create(beanP),
                    comboBox, BeanProperty.create(textP), beanP);
            return binding;
        }
        if (component instanceof JSpinner) {
            JSpinner jSpinner = (JSpinner) component;
            Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, bean,
                    BeanProperty.create(beanP), jSpinner, BeanProperty.create(textP), beanP);
            binding.setConverter(new Converter() {

                @Override
                public Object convertForward(Object value) {
                    return value == null ? new Date() : (Date) value;
                }

                @Override
                public Object convertReverse(Object value) {
                    return (Date) value;
                }
            });
            return binding;
        }

        if (component instanceof JCheckBox) {
            JCheckBox box = (JCheckBox) component;
            Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, bean,
                    BeanProperty.create(beanP), box, BeanProperty.create(textP), beanP);
            return binding;
        }
        return null;
    }

    public static JComponent createEditorOf(Object bean, final String bindName) {
        String fieldName = bindName;
        Object data_obj = bean;
        Field field = null;
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
        for (int i = 0; i < tmp_list.size(); i++) {
            String field_name = tmp_list.get(i);
            try {
                field = aclass.getField(field_name);
                aclass = field.getType();
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(EditorFactory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(EditorFactory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        FieldAnnotation fieldAnnotation = field.getAnnotation(FieldAnnotation.class);

        EnumHint enumHint = field.getAnnotation(EnumHint.class);
        if (enumHint != null) {
            JComboBox cmBox = new JComboBox();
            List<String> list = new ArrayList();
            for (String s : enumHint.enumList().split(
                    ";")) {
                list.add(s);
            }

            SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE, list, cmBox).bind();
            Binding binding = Bindings.createAutoBinding(
                    AutoBinding.UpdateStrategy.READ_WRITE, bean, BeanProperty.create(bindName),
                    cmBox, BeanProperty.create("selectedItem"));
            binding.bind();
            return cmBox;
        }

        if (field.getType().equals(org.jhrcore.entity.DeptCode.class)) {
            boolean end_flag = onlyChildDept;
            boolean forChangeScheme = editForChangeScheme;
            return createDeptEditor(bean, field, bindName, end_flag, forChangeScheme);
        }

        if (bean instanceof DeptCode && (field.getType().equals(String.class))) {
            String name = field.getName();
            if (name.equals("dept_code") || name.equals("content") || name.equals("parent_code")) {
                return createDeptEditor(bean, field, bindName, false);
            }
        }

        ObjectListHint objHint = field.getAnnotation(ObjectListHint.class);
        if (objHint != null) {
            if (objHint.hqlForObjectList().startsWith("from Code ")) {
                return createEditorOfCode(bean, fieldAnnotation, objHint.hqlForObjectList(), bindName);
            }
            JComboBox cmBox = new JComboBox();
            List list = CommUtil.fetchEntities(PublicUtil.replaceProperty(objHint.hqlForObjectList()));
            SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE, list, cmBox).bind();

            Binding binding = Bindings.createAutoBinding(
                    AutoBinding.UpdateStrategy.READ_WRITE, bean, BeanProperty.create(bindName),
                    cmBox, BeanProperty.create("selectedItem"));
            binding.bind();
            return cmBox;
        }

        if (field.getGenericType().toString().equals("int") || field.getType().equals(Integer.class)) {
            JTextField textfield = new JTextField();
            BeanProperty beanP = BeanProperty.create(bindName);
            BeanProperty textP = BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST");
            Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, bean,
                    beanP, textfield, textP, bindName);
            binding.bind();
            return textfield;
        }

        if (field.getType().equals(Boolean.class) || field.getGenericType().toString().equals("boolean")) {
            JComboBox cmBox = new JComboBox();
            List<String> list = new ArrayList();
            list.add("是");
            list.add("否");

            SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE, list, cmBox).bind();
            Binding binding = Bindings.createAutoBinding(
                    AutoBinding.UpdateStrategy.READ_WRITE, bean, BeanProperty.create(bindName),
                    cmBox, BeanProperty.create("selectedItem"));
            binding.setConverter(new BooleanConverter());
            binding.bind();
            return cmBox;
        }

        if (field.getType().equals(String.class)) {
            JTextField textfield = new JTextField();
            BeanProperty beanP = BeanProperty.create(bindName);
            BeanProperty textP = BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST");
            Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, bean,
                    beanP, textfield, textP, bindName);

            if ((fieldAnnotation != null) && fieldAnnotation.not_null()) {
                binding.setValidator(new Validator() {

                    public Result validate(Object arg) {
                        if ((arg == null) || (arg.equals(""))) {
                            return new Result(null, "该字段不能为空");
                        }
                        return null;
                    }
                });
            }
            binding.bind();
            return textfield;
        }

        if (field.getType().equals(Float.class) || field.getGenericType().toString().equals("float") || field.getType().toString().contains("BigDecimal")) {
            JTextField textfield = new JTextField();
            BeanProperty beanP = BeanProperty.create(bindName);
            BeanProperty textP = BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST");
            Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, bean,
                    beanP, textfield, textP, bindName);
            binding.bind();
            return textfield;
        }

        if (field.getType().equals(Date.class)) {
            final Field field2 = field;
            final Object bean2 = bean;
            //JXDatePicker
            String format = "yyyy-MM-dd";
            if (!fieldAnnotation.format().equals("")) {
                format = fieldAnnotation.format();
            }
            final JhrDatePicker picker = new JhrDatePicker(format);
            setValueBy(bean2, bindName, new Date());
            picker.addDatePickListener(new IDatePickerListener() {

                @Override
                public void actionPerformed() {
                    try {
                        setValueBy(bean2, bindName, picker.getDate());
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(BeanPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            return picker;
        }

        if (Model.class.isAssignableFrom(field.getType())) {
            JTextField textfield = new JTextField();
            BeanProperty beanP = BeanProperty.create(bindName);
            BeanProperty textP = BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST");
            Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, bean,
                    beanP, textfield, textP, bindName);
            binding.bind();
            return textfield;
        }
        return new JTextField("无法创建编辑器");
    }

    public static void setValueBy(Object data_obj, String fieldName, Object val_obj) {
        PublicUtil.setValueBy2(data_obj, fieldName, val_obj);
    }

    public static Object getValueBy(Object data_obj, String fieldName) {
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
                    return null;
                }
                aclass = data_obj.getClass();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String field_name = tmp_list.get(tmp_list.size() - 1);
        try {
            Class field_class = aclass.getField(field_name).getType();
            String getMethodName = "get" + field_name.substring(0, 1).toUpperCase() + field_name.substring(1);
            if (field_class.getSimpleName().equals("boolean")) {
                getMethodName = "is" + field_name.substring(0, 1).toUpperCase() + field_name.substring(1);
            }

            Method method = aclass.getMethod(getMethodName);//, new Class[]{field_class});
            data_obj = method.invoke(data_obj);
            return data_obj;
        } catch (Exception ex) {
            Logger.getLogger(EditorFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static JComponent createDeptEditor(final Object bean, final Field field, String bindName, final boolean end_flag) {
        return createDeptEditor(bean, field, bindName, end_flag, false);
    }

    public static JComponent createDeptEditor(final Object bean, final Field field, String bindName, final boolean end_flag, final boolean editForChangeScheme) {
        final JTextField textfield = new JTextField();
        final boolean editable = !bindName.contains(".");
        final String field_name = editable ? field.getName() : (field.getName() + "." + "content");
        BeanProperty beanP = BeanProperty.create(field_name);
        BeanProperty textP = BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST");
        Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, bean,
                beanP, textfield, textP, field.getName()).bind();

        textfield.setEditable(editable);

        final JPanel jpanel = new JPanel(new BorderLayout());
        jpanel.setFocusTraversalKeysEnabled(false);
        JButton btnSelelect = new JButton("");
        btnSelelect.setPreferredSize(new Dimension(18, btnSelelect.getPreferredSize().height));
        jpanel.add(textfield, BorderLayout.CENTER);
        jpanel.add(btnSelelect, BorderLayout.EAST);
        btnSelelect.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e2) {
                List depts = UserContext.getDepts(false);
                if (editForChangeScheme) {
                    depts = CommUtil.fetchEntities("from DeptCode where del_flag=0 order by px_code");
                }
                DeptSelectDlg dlg = new DeptSelectDlg(depts, null, end_flag ? TreeSelectMod.leafSelectMod : TreeSelectMod.nodeSelectMod);
                ContextManager.locateOnMainScreenCenter(dlg);
                dlg.setVisible(true);
                if (dlg.isClick_ok()) {
                    textfield.setText(dlg.getCurDept().getContent());
//                    String field_name = field.getName();
                    try {
                        Class field_class = field.getType();
                        Method method = bean.getClass().getMethod("set" + field_name.substring(0, 1).toUpperCase() + field_name.substring(1), new Class[]{field_class});
                        method.invoke(bean, new Object[]{editable ? PublicUtil.getProperty(dlg.getCurDept(), field_name) : dlg.getCurDept()});
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

    public static JComponent createEditorOfCode(final Object bean, FieldAnnotation fieldAnnotation, final String hql, final String bindName) {
        final List codes = CodeManager.getCodeManager().getCodeListBy(hql.substring(hql.indexOf("=") + 1));
        int align = JTextField.LEFT;
        if (fieldAnnotation != null) {
            if ("居中".equals(fieldAnnotation.field_align())) {
                align = JTextField.CENTER;
            } else if ("右对齐".equals(fieldAnnotation.field_align())) {
                align = JTextField.RIGHT;
            }
        }
        if (codes.size() == 0 || ((Code) codes.get(0)).getGrades() <= 2) {
            // 如果是单级代码
            JComboBox cmBox = new JComboBox();
            cmBox.setRenderer(new ComboBoxCellRenderer(align));
            SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE, codes, cmBox).bind();
            Binding binding = Bindings.createAutoBinding(
                    AutoBinding.UpdateStrategy.READ_WRITE, bean, BeanProperty.create(bindName),
                    cmBox, BeanProperty.create("selectedItem"));
            binding.bind();
//            cmBox.setEditor(new MyComboBoxEditor());
            return cmBox;
        }
        return new CodeEditor2(bean, fieldAnnotation, hql, bindName, codes, onlyChildCode);
    }
}
