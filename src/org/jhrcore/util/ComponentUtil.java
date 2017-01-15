/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import com.foundercy.pf.control.table.FBaseTableColumn;
import com.foundercy.pf.control.table.FBaseTableSorter;
import com.foundercy.pf.control.table.FBaseTableSumRowModel;
import com.foundercy.pf.control.table.FTable;
import com.foundercy.pf.control.table.FTableModel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.jhrcore.client.CommUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.comm.FieldTrigerManager;
import org.jhrcore.comm.ConfigManager;
import org.jhrcore.entity.AutoExcuteScheme;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.right.FuntionRight;
import org.jhrcore.entity.right.RoleEntity;
import org.jhrcore.ui.action.AutoExcuteAction;
import org.jhrcore.ui.CheckTreeNode;
import org.jhrcore.ui.task.IModuleCode;
import org.jhrcore.ui.listener.IPickSelectListener;
import org.jhrcore.ui.property.ClientProperty;

/**
 *
 * @author mxliteboss
 */
public class ComponentUtil {

    public static boolean showViewModule = true;

    public static void setSize(JComponent c, int x, int y) {
        c.setPreferredSize(new Dimension(x, y));
        c.setMaximumSize(new Dimension(x, y));
    }

    public static DefaultMutableTreeNode getNextNode(DefaultMutableTreeNode cur_node) {
        DefaultMutableTreeNode tmp_node = null;
        if (cur_node.getNextSibling() != null) {
            tmp_node = cur_node.getNextSibling();
        } else if (cur_node.getPreviousSibling() != null) {
            tmp_node = cur_node.getPreviousSibling();
        } else {
            tmp_node = (DefaultMutableTreeNode) cur_node.getParent();
        }
        return tmp_node;
    }

    public static DefaultMutableTreeNode getNodeByObj(JTree tree, Object obj) {
        if (tree == null || obj == null) {
            return null;
        }
        DefaultMutableTreeNode tmp_node = null;
        Enumeration enumt = ((DefaultMutableTreeNode) tree.getModel().getRoot()).breadthFirstEnumeration();
        while (enumt.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumt.nextElement();
            if (node.getUserObject() == obj) {
                tmp_node = node;
                break;
            }
        }
        return tmp_node;
    }

    public static DefaultMutableTreeNode getNodeByObj(TreeModel model, Object obj) {
        if (model == null || obj == null) {
            return null;
        }
        DefaultMutableTreeNode tmp_node = null;
        Enumeration enumt = ((DefaultMutableTreeNode) model.getRoot()).breadthFirstEnumeration();
        while (enumt.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumt.nextElement();
            if (node.getUserObject() == obj) {
                tmp_node = node;
                break;
            }
        }
        return tmp_node;
    }

    public static void setVisible(Object obj, JComponent c, boolean visible) {
        if (c == null) {
            return;
        }
        if (!visible || obj == null || !(obj instanceof IModuleCode)) {
            c.setVisible(visible);
            return;
        }
        String fieldName = "";
        Class cs = obj.getClass();
        Field[] fields = cs.getDeclaredFields();
        String module_code = ((IModuleCode) obj).getModuleCode();
        module_code = module_code == null ? cs.getSimpleName() : module_code;
        for (Field field : fields) {
            if (field.getType().getName().startsWith("javax.swing")) {
                // 获取原来的访问控制权限
                boolean accessFlag = field.isAccessible();
                // 修改访问控制权限
                field.setAccessible(true);
                try {
                    Object fieldObj = field.get(obj);
                    if (fieldObj == c) {
                        fieldName = field.getName();
                        c.setVisible(UserContext.hasFunctionRight(module_code + "." + fieldName));//这里与通用权限可能存在冲突
                        break;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    // 恢复访问控制权限
                    field.setAccessible(accessFlag);
                }
            }
        }
    }

    public static void setSysTableFuntion(FTable ftable, String sys_code) {
        if (ftable == null) {
            return;
        }
        ftable.setQueryItemEnable(UserContext.hasFunctionRight(sys_code + ".mi_search"));
        ftable.setFieldItemEnable(UserContext.hasFunctionRight(sys_code + ".mi_setShowItems"));
        ftable.setExportItemEnable(UserContext.hasFunctionRight(sys_code + ".mi_export"));
        ftable.setReplaceItemEnable(UserContext.hasFunctionRight(sys_code + ".mi_replace"));
    }

    /**
     * 该方法用于通过匹配符对指定控件设置其相对于HR系统的enable和text属性
     * @param c：指定控件
     * @param sys_code：匹配符,若匹配不到，则认为不予处理
     */
    public static void setSysCompFuntion(JComponent c, String sys_code) {
        String text = UserContext.getFuntionName(sys_code);
        if (text == null) {
            return;
        }
        c.setEnabled(UserContext.hasFunctionRight(sys_code));
        if (!text.trim().equals("")) {
            try {
                Method[] meths = c.getClass().getMethods();
                for (Method meth : meths) {
                    if (meth.getName().equals("setText")) {
                        meth.invoke(c, text);
                        break;
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public static void setSysFuntionNew(Object c) {
        setSysFuntionNew(c, true);
    }

    public static void setSysFuntionNew(Object c, boolean control) {
        if (!(c instanceof IModuleCode)) {
            return;
        }
        String sys_code = ((IModuleCode) c).getModuleCode();
        Field[] fields = c.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().getName().startsWith("javax.swing")) {
                try {
                    if (field.getName().equals("btnSearch")) {
                        continue;
                    }
                    // 获取原来的访问控制权限
                    boolean accessFlag = field.isAccessible();
                    // 修改访问控制权限
                    field.setAccessible(true);
                    // 获取在对象f中属性fields[i]对应的对象中的变量
                    Object o = field.get(c);
                    if (o == null) {
                        continue;
                    }
                    String comp_code = sys_code + "." + field.getName();
                    String text = UserContext.getFuntionName(comp_code);
                    if (o instanceof JMenuItem || o instanceof JMenu) {
                        setIcon(o, ImageUtil.blankIcon);
                    } else if (o instanceof JSplitPane) {
                        refreshJSplitPane((JSplitPane) o, comp_code);
                    }
                    String fieldType = field.getType().getSimpleName();
                    if (fieldType.equals("JPanel") || fieldType.equals("JList")) {
                        if (text == null || text.trim().equals("")) {
                            continue;
                        }
                        Object border = null;
                        Method[] meths = o.getClass().getMethods();
                        for (Method meth : meths) {
                            if (meth.getName().equals("getBorder")) {
                                border = meth.invoke(o);
                                break;
                            }
                        }
                        if (border == null) {
                            continue;
                        }
                        if (border instanceof TitledBorder) {
                            TitledBorder tb = (TitledBorder) border;
                            tb.setTitle(text);
                        }
                    } else if (fieldType.equals("JTabbedPane")) {
                        JTabbedPane jtp = (JTabbedPane) o;
                        for (int i = 0; i < jtp.getTabCount(); i++) {
                            text = UserContext.getFuntionName(comp_code + i);
                            if (text == null || text.trim().equals("")) {
                                continue;
                            }
                            jtp.setTitleAt(i, text);
                        }
                    } else if (fieldType.equals("JSplitPane")) {
                        JSplitPane jsp = (JSplitPane) o;
                        refreshJSplitPane(jsp, sys_code + "." + field.getName());
                    }
                    if (text == null || text.trim().equals("")) {
                        field.setAccessible(accessFlag);
                        continue;
                    }
                    if (o instanceof JComponent) {
                        JComponent com = (JComponent) o;
                        FuntionRight fr = UserContext.getFuntion_keys().get(comp_code);
                        if (fr != null && !fr.isVisible()) {
                            com.setVisible(fr.isVisible());
                        }
                        if (control) {
                            boolean enable = UserContext.hasFunctionRight(comp_code);
                            com.setEnabled(enable);
                            if (showViewModule && !enable) {
                                com.setVisible(false);
                            }
                        }
                        Method[] meths = o.getClass().getMethods();
                        if (text != null && !text.trim().equals("")) {
                            for (Method meth : meths) {
                                if (meth.getName().equals("setText")) {
                                    meth.invoke(o, text);
                                    break;
                                }
                            }
                        } else {
                            if (!UserContext.isSA) {
                                Object text1 = null;
                                for (Method meth : meths) {
                                    if (meth.getName().equals("getText")) {
                                        text1 = meth.invoke(o);
                                        break;
                                    }
                                }
                                for (Method meth : meths) {
                                    if (meth.getName().equals("setText")) {
                                        text1 = meth.invoke(o, comp_code + "{" + text1 + "}");
                                        break;
                                    }
                                }
                            }
                        }

                    }
                    // 恢复访问控制权限
                    field.setAccessible(accessFlag);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 该方法用于通过匹配符对指定对象（系统模块实例）设置其相对HR系统的enable和text属性
     * 实现原理通过JAVA底层方法获得CLASS中FIELD，修改其访问权限后，设置其属性，设置完毕后还原其访问权限
     * @param c：指定对象（模块实例）
     * @param sys_code：匹配符
     */
    public static void setSysFuntion(Object c, String sys_code) {
        Field[] fields = c.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().getName().startsWith("javax.swing")) {
                try {
                    if (field.getName().equals("btnSearch")) {
                        continue;
                    }
                    // 获取原来的访问控制权限
                    boolean accessFlag = field.isAccessible();
                    // 修改访问控制权限
                    field.setAccessible(true);
                    // 获取在对象f中属性fields[i]对应的对象中的变量
                    Object o = field.get(c);
                    if (o == null) {
                        continue;
                    }
                    boolean isActionComponent = o instanceof JButton || o instanceof JMenu || o instanceof JMenuItem;
                    String comp_code = sys_code + "." + field.getName();
                    String text = UserContext.getFuntionName(comp_code);
                    if (o instanceof JMenuItem || o instanceof JMenu) {
                        setIcon(o, ImageUtil.blankIcon);
                    } else if (o instanceof JSplitPane) {
                        refreshJSplitPane((JSplitPane) o, comp_code);
                    }
                    String fieldType = field.getType().getSimpleName();
                    if (fieldType.equals("JPanel") || fieldType.equals("JList")) {
                        Object border = null;
                        Method[] meths = o.getClass().getMethods();
                        for (Method meth : meths) {
                            if (meth.getName().equals("getBorder")) {
                                border = meth.invoke(o);
                                break;
                            }
                        }
                        if (border == null) {
                            continue;
                        }
                        if (border instanceof TitledBorder) {
                            TitledBorder tb = (TitledBorder) border;
                            tb.setTitle(text);
                        }
                    } else if (fieldType.equals("JTabbedPane")) {
                        JTabbedPane jtp = (JTabbedPane) o;
                        for (int i = 0; i < jtp.getTabCount(); i++) {
                            text = UserContext.getFuntionName(comp_code + i);
                            if (text == null || text.equals("")) {
                                continue;
                            }
                            jtp.setTitleAt(i, text);
                        }
                    }
                    if (text == null || text.trim().equals("")) {
//                    if (!isActionComponent && (text == null || text.trim().equals(""))) {
                        // 恢复访问控制权限
                        field.setAccessible(accessFlag);
                        continue;
                    }
                    if (o instanceof JComponent) {
//                        if (text == null || text.trim().equals("")) {
//                            if (isActionComponent) {
//                                ((JComponent) o).setEnabled(false);
//                            }
//                            // 恢复访问控制权限
//                            field.setAccessible(accessFlag);
//                            continue;
//                        }
                        boolean enable = UserContext.hasFunctionRight(comp_code);
                        ((JComponent) o).setEnabled(enable);
                        Method[] meths = o.getClass().getMethods();
                        if (text != null && !text.trim().equals("")) {
                            for (Method meth : meths) {
                                if (meth.getName().equals("setText")) {
                                    meth.invoke(o, text);
                                    break;
                                }
                            }
                        } else {
                            if (!UserContext.isSA) {
                                Object text1 = null;
                                for (Method meth : meths) {
                                    if (meth.getName().equals("getText")) {
                                        text1 = meth.invoke(o);
                                        break;
                                    }
                                }
                                for (Method meth : meths) {
                                    if (meth.getName().equals("setText")) {
                                        text1 = meth.invoke(o, comp_code + "{" + text1 + "}");
                                        break;
                                    }
                                }
                            }
                        }

                    }
                    // 恢复访问控制权限
                    field.setAccessible(accessFlag);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

//    /**
//     * 该方法用于简化设置JComponent控件的Enable属性，HR系统中的控件Enable属性任何时候都要考虑功能权限，即UserContext.hasFunctionRight(sys_code)
//     * @param c:控件对象
//     * @param sys_code：控件对象对应的HR功能菜单编码
//     * @param enable ：外界条件的Enable，例如：在某种情况下此菜单不可点
//     */
//    public static void setCompEnable(JComponent c, String sys_code, boolean enable) {
//        c.setEnabled(enable && UserContext.hasFunctionRight(sys_code));
//    }
    public static void setCompEnable(Object obj, JComponent c, boolean enable) {
        if (c == null) {
            return;
        }
        if (!enable || UserContext.isSA || obj == null || !(obj instanceof IModuleCode)) {
            c.setEnabled(enable);
            return;
        }
        String fieldName = "";
        Class cs = obj.getClass();
        Field[] fields = cs.getDeclaredFields();
        String module_code = ((IModuleCode) obj).getModuleCode();
        module_code = module_code == null ? cs.getSimpleName() : module_code;
        for (Field field : fields) {
            if (field.getType().getName().startsWith("javax.swing")) {
                // 获取原来的访问控制权限
                boolean accessFlag = field.isAccessible();
                // 修改访问控制权限
                field.setAccessible(true);
                try {
                    Object fieldObj = field.get(obj);
                    if (fieldObj == c) {
                        fieldName = field.getName();
                        break;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    // 恢复访问控制权限
                    field.setAccessible(accessFlag);
                }
            }
        }

        c.setEnabled(UserContext.hasFunctionRight(module_code + "." + fieldName) && enable);
    }

    /**
     * 为指定的分隔栏增加分隔线位置监听器，并初始化其分隔线位置
     * @param jsp：指定的分隔栏
     * @param map_code：匹配编码
     */
    public static void refreshJSplitPane(final JSplitPane jsp, final String map_code) {
        refreshJSplitPane(jsp, map_code, null);
    }

    public static void refreshJSplitPane(final JSplitPane jsp, final String map_code, Integer default_location) {;
        Integer div_location = ConfigManager.getConfigManager().getIntegerFromProperty("UI.JSplitPane." + map_code);
        if (div_location == null || div_location == 0) {
            div_location = default_location;
        }
        if (div_location != null) {
            jsp.setDividerLocation(div_location);
        }
        jsp.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent e) {
                if (e.getPropertyName().equals(JSplitPane.DIVIDER_LOCATION_PROPERTY)) {
                    ConfigManager.getConfigManager().setProperty("UI.JSplitPane." + map_code, jsp.getDividerLocation() + "");
                    ConfigManager.getConfigManager().save2();
                }
            }
        });
    }

    /**
     * 为指定的复选框增加选择监听器，并初始化是否选中
     * @param jcb：指定的复选框
     * @param map_code：匹配编码
     */
    public static void refreshJCheckBox(final JCheckBox jcb, final String map_code) {
        String property = ConfigManager.getConfigManager().getProperty("UI.JCheckBox." + map_code);
        jcb.setSelected("1".equals(property));
        jcb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ConfigManager.getConfigManager().setProperty("UI.JCheckBox." + map_code, jcb.isSelected() ? "1" : "0");
                ConfigManager.getConfigManager().save2();
            }
        });
    }

    public static void initTreeSelection(JTree tree) {
        initTreeSelection(tree, null);
    }

    public static void initTreeSelection(JTree tree, DefaultMutableTreeNode node) {
        if (node == null) {
            int row = SysUtil.objToInt(ClientProperty.getInstance().getUI_Tree_rowSelect().getSysparameter_value());
            row = row <= 0 ? 3 : row;
            node = (DefaultMutableTreeNode) tree.getModel().getRoot();
            while (node.getChildCount() > 0 && node.getFirstChild() != null && row > 0) {
                node = (DefaultMutableTreeNode) node.getFirstChild();
                row--;
            }
        }
        if (node != null) {
            TreePath tp = new TreePath(node.getPath());
            TreePath parentPath = tp;
            if (!node.isRoot()) {
                parentPath = new TreePath(((DefaultMutableTreeNode) node.getParent()).getPath());
            }
            tree.expandPath(parentPath);
            tree.setSelectionPath(tp);
            tree.scrollPathToVisible(tp);
        } else {
            tree.clearSelection();
        }
        tree.updateUI();
    }

    public static void expandTree(JTree tree) {
        TreeNode node = (TreeNode) tree.getModel().getRoot();
        expandAll(tree, new TreePath(node), true);
    }

    public static void collapseTree(JTree tree) {
        TreeNode node = (TreeNode) tree.getModel().getRoot();
        expandAll(tree, new TreePath(node), false);
    }

    private static void expandAll(JTree tree, TreePath parent, boolean expand) {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() > 0) {
            for (Enumeration e = node.children(); e.hasMoreElements();) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }
        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }

    public static void setMenuIcon(JMenuItem m) {
        if (m == null) {
            return;
        }
        Icon icon = m.getIcon();
        if (icon == null) {
            setIcon(m, "blank");
        }
        if (m instanceof JMenu) {
            JMenu menu = (JMenu) m;
            for (int i = 0; i < menu.getItemCount(); i++) {
                setMenuIcon(menu.getItem(i));
            }
        }
    }

    public static boolean setBooleanIcon(final AbstractButton com, final String code, final IPickSelectListener listener) {
        if (code == null || com == null) {
            return false;
        }
        ActionListener[] als = com.getActionListeners();
        for (ActionListener al : als) {
            com.removeActionListener(al);
        }
        com.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ConfigManager.saveBooleanProperty(code);
                boolean flag = "1".equals(ConfigManager.getConfigManager().getProperty(code));
                if (code.equals(UserContext.codeShowViewModule)) {
                    showViewModule = flag;
                }
                ComponentUtil.setBooleanIcon(com, flag);
                if (listener != null) {
                    listener.select(flag);
                }
            }
        });
        boolean flag = "1".equals(ConfigManager.getConfigManager().getProperty(code));
        if (code.equals(UserContext.codeShowViewModule)) {
            showViewModule = flag;
        }
        ComponentUtil.setBooleanIcon(com, flag);
        if (listener != null) {
            listener.select(flag);
        }
        return flag;
    }

    public static boolean setBooleanIcon(final AbstractButton com, String code) {
        return setBooleanIcon(com, code, null);
    }

    public static void setBooleanIcon(Object obj, boolean select) {
        ComponentUtil.setIcon(obj, select ? "select" : "unSelect");
    }

    public static void setIcon(Object obj, String icon_name) {
        setIcon(obj, ImageUtil.getIcon(icon_name));
    }

    public static void setIcon(Object obj, Icon icon) {
        if (obj == null || icon == null) {
            return;
        }
        if (obj instanceof AbstractButton) {
            ((AbstractButton) obj).setIcon(icon);
        } else if (obj instanceof JLabel) {
            ((JLabel) obj).setIcon(icon);
        }
    }

    public static void setIcon(Object[] items, String icon_name) {
        if (icon_name == null) {
            return;
        }
        setIcon(items, ImageUtil.getIcon(icon_name));
    }

    public static void setIcon(Object[] items, Icon icon) {
        if (items == null || icon == null) {
            return;
        }
        for (Object obj : items) {
            if (obj instanceof AbstractButton) {
                ((AbstractButton) obj).setIcon(icon);
            }
        }
    }

    public static List<CheckTreeNode> getCheckedNodes(JTree tree) {
        List<CheckTreeNode> nodes = new ArrayList();
        Enumeration enumt = ((DefaultMutableTreeNode) tree.getModel().getRoot()).breadthFirstEnumeration();
        while (enumt.hasMoreElements()) {
            CheckTreeNode node = (CheckTreeNode) enumt.nextElement();
            if (node.isSelected()) {
                nodes.add(node);
            }
        }
        return nodes;

    }

    public static List getCheckedObjs(JTree tree) {
        Enumeration enumt = ((DefaultMutableTreeNode) tree.getModel().getRoot()).breadthFirstEnumeration();
        return getCheckedObjs(enumt);
    }

    public static List getCheckedObjs(DefaultTreeModel model) {
        Enumeration enumt = ((DefaultMutableTreeNode) model.getRoot()).breadthFirstEnumeration();
        return getCheckedObjs(enumt);
    }

    public static List getCheckedObjs(Enumeration enumt) {
        List nodes = new ArrayList();
        while (enumt.hasMoreElements()) {
            CheckTreeNode node = (CheckTreeNode) enumt.nextElement();
            if (node.isSelected()) {
                nodes.add(node.getUserObject());
            }
        }
        return nodes;
    }

    public static void buildAutoExcuteMenu(AbstractButton c, String module_code) {
        c.removeAll();
        String tmp_hql2 = "from AutoExcuteScheme aes where aes.scheme_type='自动计算' and aes.used_flag = 1 and aes.scheme_code='" + module_code + "'"
                + " and exists(select 1 from CommMap cm where cm.map_key=aes.autoExcuteScheme_key and cm.user_key='" + UserContext.rolea01_key + "')";
        List scheme_list = CommUtil.fetchEntities(tmp_hql2);
        for (Object obj : scheme_list) {
            AutoExcuteScheme au = (AutoExcuteScheme) obj;
            JMenuItem item = new JMenuItem(new AutoExcuteAction(au));
            ComponentUtil.setIcon(item, "blank");
            c.add(item);
        }
    }

    public static void popRightMenu(final AbstractButton c, final JPopupMenu popMenu) {
        c.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                popMenu.show(c, 0, c.getHeight());
            }
        });
    }

    public static void initTabKeys(JTabbedPane tabPane, Hashtable<Integer, Integer> tabKeys) {
        for (int i = 0; i < tabPane.getTabCount(); i++) {
            tabKeys.put(i, 1);
        }
    }

    public static DefaultMutableTreeNode getSelectNode(JTree tree) {
        if (tree == null) {
            return null;
        }
        TreePath path = tree.getSelectionPath();
        if (path == null) {
            return null;
        }
        return (DefaultMutableTreeNode) path.getLastPathComponent();
    }

    public static List getSelectObj(JList jls, boolean isAll) {
        if (jls == null) {
            return null;
        }
        List list = new ArrayList();
        if (isAll) {
            ListModel model = jls.getModel();
            int len = model.getSize();
            for (int i = 0; i < len; i++) {
                list.add(model.getElementAt(i));
            }
        } else {
            list.addAll(Arrays.asList(jls.getSelectedValues()));
        }
        return list;
    }

    public static Object getSelectObj(JTree tree) {
        if (tree == null) {
            return null;
        }
        TreePath path = tree.getSelectionPath();
        if (path == null) {
            return null;
        }
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        if (node == null) {
            return null;
        }
        return node.getUserObject();
    }

    public static List<DefaultMutableTreeNode> getChildNodes(JTree tree) {
        List<DefaultMutableTreeNode> nodes = new ArrayList();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
        Enumeration enumt = root.breadthFirstEnumeration();
        while (enumt.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumt.nextElement();
            if (node.isLeaf()) {
                nodes.add(node);
            }
        }
        return nodes;
    }

    public static void setRightEditComponent(JTable table, int row, int col, JComponent com) {
        setRightEditComponent(table, row, col, com, true);
    }

    public static void setRightEditComponent(JTable table, int row, int col, JComponent com, boolean enable) {
        TableModel model = table.getModel();
        if (model instanceof FBaseTableSumRowModel) {
            FBaseTableSumRowModel sumModel = (FBaseTableSumRowModel) model;
            FTableModel fmodel = (FTableModel) ((FBaseTableSorter) sumModel.getModel()).getModel();
            if (fmodel.getEntityClass() == null || fmodel.getObjects().isEmpty() || fmodel.getObjects().size() <= row) {
                return;
            }
            FBaseTableColumn tableCol = (FBaseTableColumn) sumModel.getColumnModel().getColumn(col);
            if (tableCol.isLargeText()) {
            } else if (tableCol.isEditable()) {
                setRightEditComponent(fmodel.getObjects().get(row), tableCol.getId(), com, enable);
            }
        }
    }

    public static void setRightEditComponent(Object bean, String fieldName, JComponent com, boolean oldEditable) {
        if (bean == null || fieldName.contains(".") || fieldName.contains("#")) {
            return;
        }
        Class c = bean.getClass();
        boolean editable = oldEditable && isRightEditComponent(bean, c);
        if (editable && c.getSuperclass().getName().startsWith("org.jhrcore.entity")) {
            editable = isRightEditComponent(bean, c.getSuperclass());
        }
        if (com instanceof JTextField) {
            ((JTextField) com).setEditable(editable);
        } else {
            com.setEnabled(editable);
        }
    }

    private static boolean isRightEditComponent(Object bean, Class c) {
        if (bean instanceof IKey) {
            if (((IKey) bean).getKey() == 1) {
                return true;
            }
        }
        RoleEntity re = UserContext.entityRights.get(c.getSimpleName());
        if (re != null && re.getEdit_sql() != null && !re.getEdit_sql().trim().equals("")) {
            int i = FieldTrigerManager.getFieldTrigerManager().validateEditable(bean, re.getEdit_sql());
            return i == -1;
        }
        return true;
    }
}
