/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.comm;

import org.jhrcore.util.PublicUtil;
import java.util.Hashtable;
import java.util.List;
import javax.script.*;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.Code;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.base.FieldDef;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.impl.CommImpl;
import org.jhrcore.iservice.impl.DataImpl;
import org.jhrcore.rebuild.EntityBuilder;

/**
 *
 * @author Administrator
 */
public class FieldTrigerManager {

    private Logger log = Logger.getLogger(FieldTrigerManager.class.getName());
    private static FieldTrigerManager fieldTrigerManager = null;
    private ScriptEngine engine = null;
    private String cur_entity_name = null;
    private Hashtable<String, FieldDef> ht_fielddef = new Hashtable<String, FieldDef>();
    private Hashtable<String, Hashtable<String, FieldDef>> entitys = new Hashtable<String, Hashtable<String, FieldDef>>();

    public Hashtable<String, FieldDef> getHt_fielddef() {
        return ht_fielddef;
    }

    public void refreshTriger() {
        ht_fielddef.clear();
        cur_entity_name = "";
    }

    public static FieldTrigerManager getFieldTrigerManager() {
        if (fieldTrigerManager == null) {
            fieldTrigerManager = new FieldTrigerManager();
        }
        return fieldTrigerManager;
    }

    private FieldTrigerManager() {
        super();
    }

    public boolean checkObjs(List list_objs) {
        if (list_objs == null || list_objs.isEmpty()) {
            return true;
        }
        cur_entity_name = "";
        fetchRegular(list_objs.get(0).getClass());
        for (Object obj : list_objs) {
            for (String field_name : ht_fielddef.keySet()) {
                Object new_val = PublicUtil.getProperty(obj, field_name);
                if (!validate(field_name, obj, null, new_val)) {
                    return false;
                }
                if (!validateunotnull(field_name, obj, null, new_val)) {
                    return false;
                }
                triger(field_name, obj, null, new_val);
            }
        }
        return true;
    }

    public void trigerObjs(List list_objs) {
        if (list_objs == null || list_objs.isEmpty()) {
            return;
        }
        cur_entity_name = "";
        fetchRegular(list_objs.get(0).getClass());
        for (Object obj : list_objs) {
            for (String field_name : ht_fielddef.keySet()) {
                Object new_val = PublicUtil.getProperty(obj, field_name);
                triger(field_name, obj, null, new_val);
            }
        }
    }

    /**
     * 验证触发语句是否正确
     * @param triger_text:触发语句
     * @param bean:触发对象
     * @param old_val:老值
     * @param new_val：新值
     * @return:验证结果
     */
    public ValidateSQLResult validateTriger(String triger_text, Object bean, Object old_val, Object new_val) {
        return DataImpl.validateTriger(triger_text, bean, old_val, new_val);
    }

    public Object triger(String text, Object bean) {
        if (bean == null) {
            return null;
        }
        if (text == null || text.trim().equals("")) {
            return null;
        }
        if (engine == null) {
            ScriptEngineManager manager = new ScriptEngineManager();
            engine = manager.getEngineByName("js");
        }
        engine.put("e", bean);
        try {
            text = "importPackage(org.jhrcore.entity);\nimportPackage(org.jhrcore.client);\n" + text;
            Object result = engine.eval(text);
            return result;
        } catch (ScriptException ex) {
            log.error(ex);
        }
        return null;
    }

    public int validateEditable(Object bean, String text) {
        if (bean == null) {
            return 0;
        }
        if (text == null || text.trim().equals("")) {
            return 0;
        }
        if (bean instanceof IKey) {
            if (((IKey) bean).getKey() == 1) {
                return 0;
            }
        }
        if (engine == null) {
            ScriptEngineManager manager = new ScriptEngineManager();
            engine = manager.getEngineByName("js");
        }
        engine.put("e", bean);
        try {
            text = "importPackage(org.jhrcore.entity);\nimportPackage(org.jhrcore.client);\n" + text;
//            System.out.println("script:"+text);
//            String[] texts = text.split("\\;");
//            for (String script : texts) {
            Object result = engine.eval(text);
            result = (result == null) ? "false" : result.toString();
            if (Boolean.valueOf(result.toString())) {
                return -1;
            }
//            }
        } catch (ScriptException ex) {
            log.error(ex);
        }
        return 0;
    }
    // 在修改一个对象后，判断一个对象是不是符合所有的校验规则，改变的字段值通过new_val访问

    public boolean validate(String field_name, Object bean, Object old_val, Object new_val) {
        if (bean == null) {
            return true;
        }
        Class entity_class = bean.getClass();
        fetchRegular(entity_class);

        if (engine == null) {
            ScriptEngineManager manager = new ScriptEngineManager();
            engine = manager.getEngineByName("js");
        }
        engine.put("e", bean);
        engine.put("old_val", old_val);
        engine.put("new_val", new_val);
        engine.put("log", log);
        FieldDef fr = ht_fielddef.get(field_name.replace("_code_", ""));
        if (fr == null || !fr.isRegula_use_flag() || fr.getRegula_text() == null || fr.getRegula_text().trim().equals("")) {
            return true;
        }

        Object result = null;
        engine.put("result", result);
        try {
            result = engine.eval(fr.getRegula_text());
        } catch (ScriptException ex) {
            System.out.println(ex);
            log.error(ex);
        }
        Object tmp_result = engine.get("result");
        if (tmp_result != null) {
            if (!fr.isSave_flag()) {
                JOptionPane.showMessageDialog(null, tmp_result.toString(), "提示", JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                JOptionPane.showMessageDialog(null, tmp_result.toString(), "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            result = (result == null) ? "false" : result.toString();
            if ("true".equals(result)) {
                if (!fr.isSave_flag()) {
                    JOptionPane.showMessageDialog(null, fr.getRegula_msg(), "提示", JOptionPane.ERROR_MESSAGE);
                    return false;
                } else {
                    JOptionPane.showMessageDialog(null, fr.getRegula_msg(), "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        return true;
    }

    // 在修改一个对象后，判断这个属性是否满足取值唯一、必填等要求
    public boolean validateunotnull(String field_name, Object bean, Object old_val, Object new_val) {
        if (bean == null) {
            return true;
        }
        Class entity_class = bean.getClass();
        fetchRegular(entity_class);
        final FieldDef fd = ht_fielddef.get(field_name.replace("_code_", ""));
        if (fd == null) {
            return true;
        }

        if (fd.isNot_null() && (new_val == null || new_val.equals("")
                || (field_name.endsWith("_code_") && (((Code) new_val).code_id == null || ((Code) new_val).code_id.equals(""))))) {
            if (!fd.isSave_flag()) {
                JOptionPane.showMessageDialog(null, fd.getField_caption() + " 不能为空", "提示", JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                if (JOptionPane.showConfirmDialog(
                        null, fd.getField_caption() + " 不能为空，是否继续", "询问", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
                    return false;
                }
            }
        }

        if (field_name.endsWith("_code_")) {
            return true;
        }
        if (fd.isUnique_flag()) {
            String key_field = EntityBuilder.getEntityKey(bean.getClass());
            Object key_val = PublicUtil.getProperty(bean, key_field);
            String hql = "select 1 from " + bean.getClass().getSimpleName() + " where " + key_field + "<>\'" + key_val + "\'" + " and " + field_name + " =\'" + new_val + "\'";
            boolean exists = CommUtil.exists(hql);
            if (exists) {
                if (!fd.isSave_flag()) {
                    JOptionPane.showMessageDialog(null, fd.getField_caption() + " 必须唯一", "提示", JOptionPane.ERROR_MESSAGE);
                    return false;
                } else {
                    if (JOptionPane.showConfirmDialog(
                            null, fd.getField_caption() + " 必须唯一，是否继续", "询问", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    // 字段值发生变化后促发执行脚本
    public Object triger(String field_name, Object bean, Object old_val, Object new_val) {
        if (bean == null) {
            return null;
        }
        Class entity_class = bean.getClass();
        fetchRegular(entity_class);
        FieldDef fd = ht_fielddef.get(field_name.replace("_code_", ""));
        if (fd == null) {
            return null;
        }
        if (!fd.isRelation_flag() || fd.getRelation_text() == null || fd.getRelation_text().trim().equals("")) {
            return null;
        }
        if (bean instanceof IKey) {
            // 表示是新增时
            if (((IKey) bean).getKey() == 1 && !fd.isRelation_add_flag()) {
                return null;
            }
            // 表示是编辑时
            if (((IKey) bean).getKey() == 0 && !fd.isRelation_edit_flag()) {
                return null;
            }
        }
        if (engine == null) {
            ScriptEngineManager manager = new ScriptEngineManager();
            engine = manager.getEngineByName("js");
        }
        engine.put("e", bean);
        engine.put("old_val", old_val);
        engine.put("new_val", new_val);
        engine.put("log", log);
        Object result = null;
        try {
            result = engine.eval(fd.getRelation_text());
            if (result == null) {
                result = new Object();
            }
        } catch (ScriptException ex) {
            System.out.println(ex);
            log.error(ex);
        }
        return result;
    }

    public void fetchRegular(Class entity_class) {
        if (!entity_class.getSimpleName().equals(cur_entity_name)) {
            cur_entity_name = entity_class.getSimpleName();
//            ht_fielddef.clear();
            //临时性修改，避免多次读取
            Hashtable<String, FieldDef> fds = entitys.get(cur_entity_name);
            if (fds == null) {
                fds = new Hashtable<String, FieldDef>();
//                String ent_names = "'" + cur_entity_name + "'";
                String ent_names = cur_entity_name;
                entity_class = entity_class.getSuperclass();
                while (!entity_class.getName().endsWith(".Model") && !entity_class.getName().endsWith(".Object")) {
//                    ent_names = ent_names + ",'" + entity_class.getSimpleName() + "'";
                    ent_names = ent_names + ";" + entity_class.getSimpleName();
                    entity_class = entity_class.getSuperclass();
                }
                List listFieldDef = CommImpl.getSysTrigerField(ent_names, true);// CommUtil.fetchEntities("from FieldDef e where e.entityDef.entityName in (" + ent_names + ") and (e.relation_flag=1 or not_null=1 or unique_flag = 1 or regula_use_flag = 1) order by e.order_no");
                for (int i = 0; i < listFieldDef.size(); i++) {
                    FieldDef fd2 = (FieldDef) listFieldDef.get(i);
                    fds.put(fd2.getField_name(), fd2);
                }
                entitys.put(cur_entity_name, fds);
            }
            ht_fielddef = fds;
        }
    }
}
