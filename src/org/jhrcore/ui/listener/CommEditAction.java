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
     * �÷������ڱ���һ��ָ�����󣬲�ˢ��ָ������״̬
     * @param obj��ָ������
     * @param ftable ��ָ������
     */
    public static boolean doSaveAction(Object obj, FTable ftable) {
        return doAction(obj, "save", ftable, null);
    }

    /**
     * �÷������ڱ���һ��ָ�����󣬲�ˢ��ָ������/��Ƭ״̬
     * @param obj��ָ������
     * @param ftable��ָ������
     * @param beanPanel ��ָ����Ƭ
     */
    public static boolean doSaveAction(Object obj, FTable ftable, BeanPanel beanPanel) {
        return doAction(obj, "save", ftable, beanPanel);
    }

    /**
     * �÷�������ȡ��һ��ָ������������޸ģ���ˢ��ָ������״̬
     * @param obj��ָ������
     * @param ftable ��ָ������
     */
    public static boolean doCancelAction(Object obj, FTable ftable) {
        return doAction(obj, "cancel", ftable, null);
    }

    /**
     * �÷�������ȡ��һ��ָ������������޸ģ���ˢ��ָ������/��Ƭ״̬
     * @param obj��ָ������
     * @param ftable��ָ������
     * @param beanPanel ��ָ����Ƭ
     */
    public static boolean doCancelAction(Object obj, FTable ftable, BeanPanel beanPanel) {
        return doAction(obj, "cancel", ftable, beanPanel);
    }

    /**
     * �÷�������ִ��������������ж�ָ�����������Ƿ���ģ������ģ�����ʾ�Ƿ񱣴棬
     * ���û�ѡ�񱣴棬�򱣴������޸�
     * ���û�ѡ�񲻱��棬���ȡ���ݿ����ݸ��ǵ��ö���
     * ���ˢ��ָ������״̬
     * @param obj��ָ������
     * @param ftable ��ָ������
     */
    public static boolean doViewAction(Object obj, FTable ftable) {
        return doAction(obj, "view", ftable, null);
    }

    /**
     * �÷�������ִ��������������ж�ָ�����������Ƿ���ģ������ģ�����ʾ�Ƿ񱣴棬
     * ���û�ѡ�񱣴棬�򱣴������޸�
     * ���û�ѡ�񲻱��棬���ȡ���ݿ����ݸ��ǵ��ö���
     * ���ˢ��ָ������/��Ƭ״̬
     * @param obj��ָ������
     * @param ftable��ָ������
     * @param beanPanel ��ָ����Ƭ
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
                        "��ǰ��¼�иĶ����Ƿ���Ҫ����", "ѯ��", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
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
     * �÷������ڱ���ָ�������޸ģ���editableΪfalse��ָ�������������޸ģ���÷��������κδ���
     * @param obj��ָ������
     * @param editable ���Ƿ�ɱ༭��һ�����ڷ���FTABLE��BEANPANEL�ı༭��,
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
