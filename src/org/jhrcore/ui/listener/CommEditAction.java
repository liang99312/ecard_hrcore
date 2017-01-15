/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.listener;

import com.foundercy.pf.control.table.FTable;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jhrcore.comm.BeanManager;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.rebuild.EntityBuilder;
import org.jhrcore.ui.BeanPanel;

/**
 *
 * @author mxliteboss
 */
public class CommEditAction {

    public static boolean doAction(Object obj, String type, FTable ftable) {
        return doAction(obj, type, ftable, null);
    }

    /**
     * 该方法用于保存一个指定对象，并刷新指定网格状态
     * @param obj：指定对象
     * @param ftable ：指定网格
     */
    public static boolean doSaveAction(Object obj, FTable ftable) {
        return doAction(obj, "save", ftable, null);
    }

    /**
     * 该方法用于保存一个指定对象，并刷新指定网格/卡片状态
     * @param obj：指定对象
     * @param ftable：指定网格
     * @param beanPanel ：指定卡片
     */
    public static boolean doSaveAction(Object obj, FTable ftable, BeanPanel beanPanel) {
        return doAction(obj, "save", ftable, beanPanel);
    }

    /**
     * 该方法用于取消一个指定对象的属性修改，并刷新指定网格状态
     * @param obj：指定对象
     * @param ftable ：指定网格
     */
    public static boolean doCancelAction(Object obj, FTable ftable) {
        return doAction(obj, "cancel", ftable, null);
    }

    /**
     * 该方法用于取消一个指定对象的属性修改，并刷新指定网格/卡片状态
     * @param obj：指定对象
     * @param ftable：指定网格
     * @param beanPanel ：指定卡片
     */
    public static boolean doCancelAction(Object obj, FTable ftable, BeanPanel beanPanel) {
        return doAction(obj, "cancel", ftable, beanPanel);
    }

    /**
     * 该方法用于执行浏览动作，并判断指定对象属性是否更改，若更改，则提示是否保存，
     * 若用户选择保存，则保存数据修改
     * 若用户选择不保存，则读取数据库数据覆盖到该对象
     * 最后刷新指定网格状态
     * @param obj：指定对象
     * @param ftable ：指定网格
     */
    public static boolean doViewAction(Object obj, FTable ftable) {
        return doAction(obj, "view", ftable, null);
    }

    /**
     * 该方法用于执行浏览动作，并判断指定对象属性是否更改，若更改，则提示是否保存，
     * 若用户选择保存，则保存数据修改
     * 若用户选择不保存，则读取数据库数据覆盖到该对象
     * 最后刷新指定网格/卡片状态
     * @param obj：指定对象
     * @param ftable：指定网格
     * @param beanPanel ：指定卡片
     */
    public static boolean doViewAction(Object obj, FTable ftable, BeanPanel beanPanel) {
        return doAction(obj, "view", ftable, beanPanel);
    }

    public static boolean doAction(Object obj, String type, FTable ftable, BeanPanel beanPanel) {
        if (obj == null || type == null) {
            return false;
        }
        int mod = -1;
        ValidateSQLResult result = null;
        if (type.equals("save")) {
            if (ftable != null) {
                ftable.editingStopped();
            }
            result = BeanManager.updateEntity(obj, true);
        } else if (type.equals("view")) {
            if (ftable != null) {
                ftable.editingStopped();
            }
            if (BeanManager.isChanged(obj)) {
                if (JOptionPane.showConfirmDialog(null,
                        "当前记录有改动，是否需要保存", "询问", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
                    result = BeanManager.updateEntity(obj, true);
                } else {
                    mod = fetchOldObj(obj, ftable, beanPanel) ? 0 : 1;
                }
            }
        } else if (type.equals("cancel")) {
            if (ftable != null) {
                ftable.editingStopped();
            }
            mod = fetchOldObj(obj, ftable, beanPanel) ? 0 : 1;
        }
        if (ftable != null) {
            if (ftable.getParent() instanceof JPanel) {
                ((JPanel) ftable.getParent()).updateUI();
            } else {
                ftable.updateUI();
            }
        }
        if (beanPanel != null) {
            beanPanel.bind();
        }
        if (mod != -1) {
            return mod == 0;
        }
        return result != null && result.getResult() == 0;
    }

    /**
     * 该方法用于保存指定对象修改，若editable为false或指定对象无属性修改，则该方法不做任何处理
     * @param obj：指定对象
     * @param editable ：是否可编辑，一般用于返回FTABLE或BEANPANEL的编辑性,
     */
    public static boolean doRowSaveAction(Object obj, boolean editable) {
        return BeanManager.updateEntity(obj, editable).getResult() == 0;
    }

    private static boolean fetchOldObj(Object obj, FTable ftable, BeanPanel beanPanel) {
        if (ftable != null) {
            ftable.editingStopped();
        }
        String key = EntityBuilder.getEntityKey(obj.getClass());
        BeanManager.cancel(obj);
        String hql = PublicUtil.getProps_value().getProperty(obj.getClass().getName());
        if (hql == null || hql.trim().equals("")) {
            hql = PublicUtil.getProps_value().getProperty(obj.getClass().getSuperclass().getName());
        }
        if (hql == null || hql.trim().equals("")) {
            return false;
        }
        Object keyObj = PublicUtil.getProperty(obj, key);
        hql += "('" + keyObj + "')";
        try {
            obj = CommUtil.fetchEntityBy(hql);
            if (ftable != null) {
                ftable.replaceRow(obj, key);
            }
            if (beanPanel != null) {
                beanPanel.setBean(obj);
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public static boolean isChanged(Object obj) {
        return isChanged(obj, null);
    }

    public static boolean isChanged(Object obj, FTable ftable) {
        if (ftable != null) {
            ftable.editingStopped();
        }
        return BeanManager.isChanged(obj);
    }
}
