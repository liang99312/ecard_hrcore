/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.comm;

import org.jhrcore.util.DateUtil;
import com.jgoodies.binding.beans.Model;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import org.jhrcore.client.CommUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.impl.DataImpl;
import org.jhrcore.rebuild.EntityBuilder;
import org.jhrcore.util.MsgUtil;
import org.jhrcore.util.SysUtil;

/**
 *
 * @author hflj
 */
public class BeanManager {

    public static HashMap<Object, HashMap<String, Object>> change_objs = new HashMap<Object, HashMap<String, Object>>();
    public static HashMap<Object, HashMap<String, Object>> old_objs = new HashMap<Object, HashMap<String, Object>>();

    public static void manager(final Object obj) {
        if (obj == null || !(obj instanceof Model)) {// || BeanManager.change_objs.keySet().contains(obj)) {
            return;
        }
        if (obj instanceof IKey) {
            IKey key = (IKey) obj;
            if (key.getKey() == 1) {
                return;
            }
        }
        String key = EntityBuilder.getEntityKey(obj.getClass());
        Object keyObj = PublicUtil.getProperty(obj, key);
        if (keyObj == null || keyObj.toString().equals("-1")) {
            return;
        }
        ((Model) obj).addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                String property = pce.getPropertyName();
                if (property.endsWith("_code_")) {
                    return;
                }
                HashMap<String, Object> changes = BeanManager.change_objs.get(obj);
                HashMap<String, Object> oldPropertys = BeanManager.old_objs.get(obj);
                if (changes == null) {
                    changes = new HashMap<String, Object>();
                    BeanManager.change_objs.put(obj, changes);
                }
                if (oldPropertys == null) {
                    oldPropertys = new HashMap();
                    BeanManager.old_objs.put(obj, oldPropertys);
                }
                Object oldValue = pce.getOldValue();
                Object newValue = pce.getNewValue();
                if (oldValue == newValue) {
                    return;
                }
                if (oldValue != null && oldValue.equals(newValue)) {
                    return;
                }
                if (newValue != null && newValue.equals(oldValue)) {
                    return;
                }
                if (oldValue != null && newValue != null) {
                    if (oldValue.getClass().getName().startsWith("org.jhrcore.entity") && newValue.getClass().getName().startsWith("org.jhrcore.entity")) {
                        String old_key = EntityBuilder.getEntityKey(oldValue.getClass());
                        String new_key = EntityBuilder.getEntityKey(newValue.getClass());
                        Object old_key_str = PublicUtil.getProperty(oldValue, old_key);
                        Object new_key_str = PublicUtil.getProperty(newValue, new_key);
                        if (old_key_str.equals(new_key_str)) {
                            return;
                        }
                    }
                }
                changes.put(property, newValue);
                oldPropertys.put(property, oldValue);
            }
        });
    }

    public static ValidateSQLResult updateEntity(Object obj, boolean editable) {
        return updateEntity(obj, true, editable);
    }

    public static ValidateSQLResult updateEntity(Object obj, boolean comm_flag, boolean editable) {
        if (!editable) {
            cancel(obj);
            return new ValidateSQLResult();
        }
        if (obj == null) {
            return new ValidateSQLResult();
        }
        String key = EntityBuilder.getEntityKey(obj.getClass());
        Object keyObj = PublicUtil.getProperty(obj, key);
        if (keyObj == null || keyObj.toString().equals("-1")) {
            return new ValidateSQLResult();
        }
        HashMap<String, Object> changes = BeanManager.change_objs.get(obj);
        if (changes == null) {
            cancel(obj);
            return new ValidateSQLResult();
        } else {
            if (changes.isEmpty()) {
                return new ValidateSQLResult();
            }
            ValidateSQLResult result1 = CommUtil.entity_triger(obj, false);
            if (result1 != null) {
                return result1;
            }
            String hql = getChangeHQL(obj);
            if (hql.equals("")) {
                cancel(obj);
                return new ValidateSQLResult();
            }
            try {
                ValidateSQLResult result = DataImpl.update(obj, hql);
                if (result.getResult() == 0) {
                    HashMap<String, String> chgs = tranChanges(obj, changes);
                    HashMap<String, String> olds = tranChanges(obj, BeanManager.old_objs.get(obj));
                    for (String ckey : changes.keySet()) {
                        HrLog.infoData(obj, ckey, SysUtil.objToStr(olds.get(ckey)).replace("'", ""), SysUtil.objToStr(chgs.get(ckey)).replace("'", ""), HrLog.TypeUpdate);
                    }
                }
                cancel(obj);
                if (comm_flag) {
                    if (result.getResult() == 0) {
                        MsgUtil.showHRSaveSuccessMsg(null);
                    } else {
                        MsgUtil.showHRSaveErrorMsg(result);
                    }
                }
                return result;
            } catch (Exception ex) {
            }
            return CommUtil.getServiceErrorResult();
        }
    }

    private static HashMap<String, String> tranChanges(Object obj, HashMap<String, Object> changes) {
        if (changes == null || changes.isEmpty()) {
            return new HashMap<String, String>();
        }
        HashMap<String, String> f_sqls = new HashMap<String, String>();
        for (String property : changes.keySet()) {
            Object value = changes.get(property);
            try {
                Field field = obj.getClass().getField(property);
                String type = field.getType().getSimpleName().toLowerCase();
                String result = "";
                if (type.equals("string")) {
                    result = value == null ? null : ("'" + value.toString().replace("'", "''") + "'");
                } else if (type.equals("boolean")) {
                    result = (value == null) ? "0" : (((Boolean) value) ? "1" : "0");
                } else if (type.equals("int") || type.equals("integer") || type.equals("float") || type.equals("double") || type.equals("bigdecimal")) {
                    result = value + "";
                } else if (type.equals("date")) {
                    result = (value == null || !(value instanceof Date)) ? null : DateUtil.toStringForQuery((Date) value, "yyyy-MM-dd HH:mm:ss");
                } else {
                    Object value_str = null;
                    if (value != null) {
                        String value_key = EntityBuilder.getEntityKey(value.getClass());
                        if (value.getClass().getName().contains("org.jhrcore.entity")) {
                            value_str = PublicUtil.getProperty(value, value_key);
                        } else {
                            value_str = value;
                        }
                    }
                    result = value_str == null ? null : ("'" + value_str.toString().replace("'", "''") + "'");
                }
                f_sqls.put(property, result);
            } catch (Exception ex) {
                ex.printStackTrace();
                continue;
            }
        }
        return f_sqls;
    }

    public static String getChangeHQL(Object obj) {
        HashMap<String, Object> changes = BeanManager.change_objs.get(obj);
        if (changes == null || changes.isEmpty()) {
            return "";
        }
        HashMap<String, String> sqls = tranChanges(obj, changes);
        HashMap<String, String> f_sqls = new HashMap<String, String>();
        for (String property : changes.keySet()) {
            try {
                Field field = obj.getClass().getField(property);
                String value = sqls.get(property);
                if (field.getType().getName().startsWith("org.jhrcore.entity")) {
                    value = property + "_key=" + value;
                } else {
                    value = property + "=" + value;
                }
                f_sqls.put(property, value);
            } catch (Exception ex) {
                continue;
            }
        }
        String hql = getChangeSQL(obj, obj.getClass(), f_sqls);
        if (f_sqls.keySet().size() > 0) {
            Class c = obj.getClass().getSuperclass();
            if (c.getName().startsWith("org.jhrcore.entity")) {
                if (!hql.equals("")) {
                    hql += ";";
                }
                hql += getChangeSQL(obj, c, f_sqls);
            }
        }
        return hql;
    }

    private static String getChangeSQL(Object obj, Class c, HashMap<String, String> changes) {
        List<Field> fields = EntityBuilder.getDeclareFieldListOf(c, EntityBuilder.COMM_FIELD_ALL);
        if (c.getSuperclass().getName().startsWith("org.jhrcore.entity")) {
            Inheritance in = (Inheritance) c.getSuperclass().getAnnotation(Inheritance.class);
            if (in.strategy() == InheritanceType.TABLE_PER_CLASS) {
                fields = EntityBuilder.getCommFieldListOf(c, EntityBuilder.COMM_FIELD_ALL);
            }
        }
        String hql = "";
        List<String> existsKeys = new ArrayList<String>();
        for (Field field : fields) {
            for (String f : changes.keySet()) {
                if (field.getName().equals(f)) {
                    hql += "," + changes.get(f);
                    existsKeys.add(f);
                    break;
                }
            }
        }
        for (String k : existsKeys) {
            changes.remove(k);
        }
        if (!hql.equals("")) {
            String key = EntityBuilder.getEntityKey(c);
            String entityName = c.getSimpleName();
            Table t = (Table) c.getAnnotation(Table.class);
            if (t != null) {
                entityName = t.name();
            }
            Object keyObj = PublicUtil.getProperty(obj, key);
            if (keyObj == null) {
                return "";
            }
            if (keyObj instanceof String) {
                keyObj = "'" + keyObj + "'";
            }
            hql = hql.substring(1);
            hql = "update " + entityName + " set " + hql + " where " + key + "=" + keyObj;
        }
        return hql;
    }

    public static void propertyChanged(Object obj, String propertyName, Object oldValue, Object newValue) {
        if (oldValue == newValue) {
            return;
        }
        if (oldValue != null && oldValue.equals(newValue)) {
            return;
        }
        if (newValue != null && newValue.equals(oldValue)) {
            return;
        }
        HashMap<String, Object> changes = change_objs.get(obj);
        if (changes == null) {
            changes = new HashMap<String, Object>();
            change_objs.put(obj, changes);
        }
        changes.put(propertyName, newValue);
        changes = old_objs.get(obj);
        if (changes == null) {
            changes = new HashMap<String, Object>();
            old_objs.put(obj, changes);
        }
        changes.put(propertyName, oldValue);
    }

    public static Object getChangeObj(Object obj) {
        if (obj == null) {
            return null;
        }
        HashMap<String, Object> changes = change_objs.get(obj);
        if (changes == null || changes.isEmpty()) {
            return null;
        }
        return obj;
    }

    public static boolean isChanged(Object obj) {
        if (obj == null) {
            return false;
        }
        HashMap<String, Object> changes = change_objs.get(obj);
        if (changes == null || changes.isEmpty()) {
            return false;
        }
        return true;
    }

    public static void cancel(Object obj) {
        if (obj == null) {
            return;
        }
        HashMap<String, Object> changes = change_objs.get(obj);
        if (changes != null) {
            changes.clear();
        }
        changes = old_objs.get(obj);
        if (changes != null) {
            changes.clear();
        }
    }

    public static void remove(Object obj) {
        if (obj == null) {
            return;
        }
        if (obj instanceof Model) {
            Model model = (Model) obj;
            PropertyChangeListener[] listeners = model.getPropertyChangeListeners();
            for (PropertyChangeListener listener : listeners) {
                model.removePropertyChangeListener(listener);
            }
        }
        change_objs.remove(obj);
        old_objs.remove(obj);
    }
}
