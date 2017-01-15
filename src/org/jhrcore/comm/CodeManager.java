/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.comm;

import org.jhrcore.entity.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.tree.DefaultMutableTreeNode;
import org.apache.log4j.Logger;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.annotation.ObjectListHint;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.ui.CodeSelectDialog;
import org.jhrcore.util.ComponentUtil;

/**
 *
 * @author wangzhenhua
 * 该类负责编码到中文含义的转换
 */
public class CodeManager {

    private Logger log = Logger.getLogger(CodeManager.class.getName());
    private static CodeManager codeManager = null;
    // 这是保存编码的hashtable，先按类型索引，再按编码索引
    private Hashtable<String, Hashtable<String, Code>> ht_types = new Hashtable<String, Hashtable<String, Code>>();
    //所有系统编码
    private Hashtable<String, Hashtable<String, Code>> all_ht_types = new Hashtable<String, Hashtable<String, Code>>();
    private Hashtable<String, Code> codeKeys = new Hashtable<String, Code>();
    private List<Code> code_types = new ArrayList<Code>();
    // 编码限制，索引为编码的类型名称，值为许可的编码前缀。
    private Hashtable<String, String> ht_code_limits = new Hashtable<String, String>();
    // 编码限制脚本，索引为表名，值为脚本
    private Hashtable<String, String> ht_limit_script = new Hashtable<String, String>();

    public Hashtable<String, String> getHt_code_limits() {
        return ht_code_limits;
    }

    public Hashtable<String, String> getHt_limit_script() {
        return ht_limit_script;
    }

    private CodeManager() {
        super();
    }

    public Hashtable<String, Hashtable<String, Code>> getHt_types() {
        return ht_types;
    }

    // 根据编码类型返回编码列表
    public List<Code> getCodeListBy(String code_type) {
        List<Code> list = new ArrayList<Code>();
        Hashtable<String, Code> tmp_ht_codes = ht_types.get(code_type);
        if (tmp_ht_codes != null) {
            list.addAll(tmp_ht_codes.values());
            for (Code c : list) {
                c.setHide_flag(false);
            }
        }
        sortCode(list);
        return list;
    }

    public void limitCodeForRight(String code_type, String[] code_limits, String[] edit_limits) {
        Hashtable<String, Code> type_codes = all_ht_types.get(code_type);
        if (type_codes == null) {
            return;
        }
        Hashtable<String, Code> result_codes = new Hashtable<String, Code>();
        if (code_limits == null || code_limits.length == 0) {
            result_codes.putAll(type_codes);
        } else {
            for (String key : code_limits) {
                if (!type_codes.keySet().contains(key)) {
                    continue;
                }
                for (String code_id : type_codes.keySet()) {
                    if (code_id.startsWith(key)) {
                        result_codes.put(code_id, type_codes.get(code_id));
                    }
                }
            }
        }
        if (edit_limits == null || edit_limits.length == 0) {
            for (Code code : result_codes.values()) {
                code.setEdit_flag(true);
            }
        } else {
            for (Code code : result_codes.values()) {
                code.setEdit_flag(false);
            }
            for (String key : edit_limits) {
                for (String code_id : result_codes.keySet()) {
                    if (code_id.startsWith(key)) {
                        result_codes.get(code_id).setEdit_flag(true);
                    }
                }
            }
        }
        ht_types.put(code_type, result_codes);
    }
    // 用编码填充

    public void fillCodes(List<?> list) {
        codeKeys.clear();
        code_types.clear();
        ht_types.clear();
        all_ht_types.clear();
        for (Object obj : list) {
            Code code = (Code) obj;
            code.setEdit_flag(true);
            code.setHide_flag(false);
            codeKeys.put(code.getCode_key(), code);
            if (code.getParent_id().equals("ROOT")) {
                code_types.add(code);
                continue;
            }
            Hashtable<String, Code> tmp_hash_code = ht_types.get(code.getCode_type());
            if (tmp_hash_code == null) {
                tmp_hash_code = new Hashtable<String, Code>();
                ht_types.put(code.getCode_type(), tmp_hash_code);
                all_ht_types.put(code.getCode_type(), tmp_hash_code);
            }
            tmp_hash_code.put(code.getCode_id(), code);
        }
    }

    /**
     * 将目标编码加入缓存
     * @param code：目标编码
     */
    public void addCodeToMemory(Code code) {
        if (!code.isUsed()) {
            return;
        }
        if (code.getParent_id().equals("ROOT")) {
            for (Code c : code_types) {
                if (c.getCode_key().equals(code.getCode_key())) {
                    c.setCode_name(code.getCode_name());
                    return;
                }
            }
            code_types.add(code);
            return;
        }
        if (code.getCode_type() == null) {
            return;
        }
        Hashtable<String, Code> tmp_hash_code = ht_types.get(code.getCode_type());
        if (tmp_hash_code == null) {
            tmp_hash_code = new Hashtable<String, Code>();
            ht_types.put(code.getCode_type(), tmp_hash_code);
        }
        tmp_hash_code.put(code.getCode_id(), code);
    }

    /**
     * 将目标编码从缓存移除
     * @param code：目标编码
     */
    public void removeCodeFromMemory(Code c) {
        if (c.getParent_id().equals("ROOT")) {
            Code remove_code = null;
            for (Code code : code_types) {
                if (code.getCode_key().equals(c.getCode_key())) {
                    remove_code = code;
                    break;
                }
            }
            if (remove_code != null) {
                code_types.remove(remove_code);
            }
            ht_types.remove(c.getCode_type());
            return;
        }
        Hashtable<String, Code> tmp_hash_code = ht_types.get(c.getCode_type());
        if (tmp_hash_code == null) {
            return;
        }
        tmp_hash_code.remove(c.getCode_id());
    }

    public Code getCodeBy(String code_type, String code_id) {
        if (code_type == null || code_id == null) {
            return null;
        }
        Hashtable<String, Code> ht = all_ht_types.get(code_type);
        if (ht == null) {
            return null;
        }
        return ht.get(code_id);
    }

    public Code getCodeByName(String code_type, String code_name) {
        if (code_type == null || code_name == null) {
            return null;
        }
        List<Code> codes = getCodeListBy(code_type);
        for (Code c : codes) {
            if (code_name.equals(c.getCode_name())) {
                return c;
            }
        }
        return null;
    }

    public String getCodeIdBy(String code_type, String code_name) {
        if (code_type == null || code_name == null) {
            return null;
        }
        Hashtable<String, Code> ht = ht_types.get(code_type);
        if (ht == null) {
            return null;
        }
        for (Code code : ht.values()) {
            if (code.getCode_name().equals(code_name)) {
                return code.getCode_id();
            }
        }
        return null;
    }

    public String getCodeNameBy(String code_type, String code_id) {
        if (code_type == null || code_id == null) {
            return null;
        }
        Hashtable<String, Code> ht = ht_types.get(code_type);
        if (ht == null) {
            return null;
        }
        Code code = ht.get(code_id);
        return code == null ? "" : code.getCode_name();
    }

    private void sortCode(List<Code> codes) {
        Collections.sort(codes, new Comparator() {

            @Override
            public int compare(Object arg0, Object arg1) {
                Code code0 = (Code) arg0;
                Code code1 = (Code) arg1;
                return code0.getCode_id().compareTo(code1.getCode_id());
            }
        });
    }

    public List<Code> getAll_codes() {
        List<Code> all_codes = new ArrayList<Code>();
        sortCode(code_types);
        for (Code code : code_types) {
            all_codes.add(code);
            all_codes.addAll(getCodeListBy(code.getCode_type()));
        }
        return all_codes;
    }

    public static CodeManager getCodeManager() {
        if (codeManager == null) {
            codeManager = new CodeManager();
        }
        return codeManager;
    }

    public List<Code> getCode_types() {
        return code_types;
    }

    /**
     * 验证触发语句是否正确
     * @param triger_text:触发语句
     * @param bean:触发对象
     * @param old_val:老值
     * @param new_val：新值
     * @return:验证结果
     */
    public ValidateSQLResult validateTriger(String text, Object bean) {
        ht_code_limits.clear();
        ValidateSQLResult validate_result = new ValidateSQLResult();
        validate_result.setMsg("错误信息如下：");
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        engine.put("e", bean);
        engine.put("code_limits", ht_code_limits);
        try {
            engine.eval(text);
        } catch (ScriptException ex) {
            validate_result.setResult(1);
            validate_result.setMsg(validate_result.getMsg() + "\n " + ". 第" + ex.getLineNumber() + "行 " + ex.getMessage());
            log.error(ex);
        }
        return validate_result;
    }

    public List<Code> getLimitCodes(Object bean, ObjectListHint objectListHint) {
        String hql = objectListHint.hqlForObjectList();
        String code_type_name = hql.substring(hql.indexOf("=") + 1);
        return getLimitCodes(bean, code_type_name, objectListHint.nullable());
    }

    public List<Code> getLimitCodes(Object bean, String code_type_name, boolean nullable) {
        limitCode(bean);
        List tmp_codes = new ArrayList();
        tmp_codes.addAll(getCodeListBy(code_type_name));
        String code_limit = ht_code_limits.get(code_type_name);
        if (code_limit != null && !code_limit.equals("")) {
            String[] prefixs = code_limit.split(";");
//            List result_codes = new ArrayList();
            for (Object obj : tmp_codes) {
                Code c = (Code) obj;
                c.setHide_flag(true);
                for (String key : prefixs) {
                    if (key.startsWith("#")) {
                        key = key.substring(1);
                        key = CodeManager.getCodeManager().getCodeIdBy(code_type_name, key);
                    }
                    if (c.getCode_id().startsWith(key)) {
                        c.setHide_flag(false);
//                        result_codes.add(c);
                        break;
                    }
                }
            }
//            tmp_codes.clear();
//            tmp_codes.addAll(result_codes);
        }
        if (nullable) {
            Code tmp_code = new Code();
            tmp_code.setCode_id(null);
            tmp_code.setCode_name("      ");
            if (tmp_codes.size() > 0) {
                tmp_code.setGrades(((Code) tmp_codes.get(0)).getGrades());
            }
            tmp_codes.add(0, tmp_code);
        }
        return tmp_codes;
    }

    public void limitCode(Object bean) {
        String class_name = bean.getClass().getName();
        if (class_name.startsWith("org.jhrcore.entity.base")) {
            return;
        }
        if (!class_name.startsWith("org.jhrcore.entity")) {
            return;
        }
        ht_code_limits.clear();
        String limit_script = ht_limit_script.get(bean.getClass().getSimpleName());
        if (limit_script == null) {
            limit_script = "";
            Class the_class = bean.getClass();
            String ent_names = "'" + the_class.getSimpleName() + "'";
            the_class = the_class.getSuperclass();
            while (the_class.getName().contains("entity")) {
                ent_names = ent_names + ",'" + the_class.getSimpleName() + "'";
                the_class = the_class.getSuperclass();
            }
            List list = CommUtil.fetchEntities("select limit_script from EntityDef where entityName in(" + ent_names + ") and limit_flag=1");
            for (Object obj : list) {
                limit_script = limit_script + ((obj == null || obj.toString().toLowerCase().trim().equals("null")) ? "" : obj.toString());
            }
            ht_limit_script.put(bean.getClass().getSimpleName(), limit_script);
        }
        if (!limit_script.trim().equals("")) {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            engine.put("e", bean);
            engine.put("code_limits", ht_code_limits);
            try {
                engine.eval(limit_script);
            } catch (ScriptException ex) {
                ex.printStackTrace();
                //JOptionPane.showMessageDialog(null, limit_script + "\n " + ". 第" + ex.getLineNumber() + "行 " + ex.getMessage());
                log.error(ex);
            }
        }
    }

    public List<Code> getEndCodeListBy(String code_type) {
        List<Code> codes = getCodeListBy(code_type);
        CodeSelectDialog dlg = new CodeSelectDialog(codes, "");
        List<DefaultMutableTreeNode> nodes = ComponentUtil.getChildNodes(dlg.getCodeTree());
        dlg.dispose();
        List<Code> result = new ArrayList<Code>();
        for (DefaultMutableTreeNode node : nodes) {
            if (node.isRoot()) {
                continue;
            }
            result.add((Code) node.getUserObject());
        }
        return result;
    }

    public Code getCodeByKey(String key) {
        if (key == null) {
            return null;
        }
        return codeKeys.get(key);
    }
}
