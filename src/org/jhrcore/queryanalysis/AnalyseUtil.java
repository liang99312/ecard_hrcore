/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.queryanalysis;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.query.CommAnalyseField;
import org.jhrcore.entity.query.QueryExtraField;

/**
 *
 * @author mxliteboss
 */
public class AnalyseUtil {

    public static QueryExtraField createExtraField(String field_code, String scheme_key, String field_sql, String entityName, int order_no) {
        QueryExtraField qef = new QueryExtraField();
        qef.setScheme_key(scheme_key);
        qef.setEditable(false);
        qef.setField_type("普通");
        qef.setField_code(field_code);
        qef.setEntity_name(entityName);
        qef.setField_sql(field_sql);
        qef.setOrder_no(order_no);
        qef.setUsed(true);
        int field_code_no = 0;
        if ("个人穿透".equals(field_code)) {
            field_code_no = 1;
        }
        qef.setQueryExtraField_key(scheme_key + "_" + field_sql + "_" + field_code_no);
        return qef;
    }

    public static void replaceCaption(List<TempFieldInfo> all_infos, List<QueryExtraField> extra_fields) {
        Hashtable<String, TempFieldInfo> captions = new Hashtable<String, TempFieldInfo>();
        for (TempFieldInfo tfi : all_infos) {
            captions.put(tfi.getEntity_name() + "." + tfi.getField_name().replace("_code_", ""), tfi);
        }
        for (QueryExtraField exf : extra_fields) {
            if (exf.isEditable()) {
                continue;
            }
            TempFieldInfo tmp = captions.get(exf.getField_sql());
            exf.setField_caption("");
            exf.setCode_type("@@@");
            if (tmp != null) {
                exf.setFieldType(tmp.getField_type());
                exf.setField_caption(tmp.getCaption_name());
                exf.setCode_type((tmp.getCode_type_name()==null||tmp.getCode_type_name().trim().equals(""))?"@@@":tmp.getCode_type_name());
            }
        }
    }

    public static AnalyseField createAnalyseField(CommAnalyseField paf) {
        return createAnalyseField(paf, 0, null, null, null);
    }

    public static AnalyseField createAnalyseField(CommAnalyseField paf, int field_type, String field_value, String field_caption, String key) {
        return createAnalyseField(paf, field_type, field_value, field_caption, false, key);
    }

    public static AnalyseField createAnalyseField(CommAnalyseField paf, int field_type, String field_value, String field_caption, String key, boolean end_flag) {
        return createAnalyseField(paf, field_type, field_value, field_caption, false, key, end_flag);
    }

    public static AnalyseField createAnalyseField(CommAnalyseField paf, int field_type, String field_value, String field_caption, boolean first_field, String key) {
        return createAnalyseField(paf, field_type, field_value, field_caption, first_field, key, false);
    }

    public static AnalyseField createAnalyseField(CommAnalyseField paf, int field_type, String field_value, String field_caption, boolean first_field, String key, boolean end_flag) {
        AnalyseField af = new AnalyseField();
        af.setEntity_caption(paf.getEntity_caption());
        af.setEntity_name(paf.getEntity_name());
        af.setStat_type(paf.getStat_type());
        af.setDecimal_len(paf.getDecimal_len());
        af.setAf_key(key);
        af.setEnd_flag(end_flag);
        if (paf.getField_name() != null) {
            af.setField_name(paf.getField_name().replace("_code_", ""));
            if (paf.getField_name().equals("a01_key")) {
                af.setStat_operator("count");
                af.setStat_type("计数");
            }
        }
        af.setField_caption(paf.getField_caption());
        af.setAnalyse_type(paf.getCal_type());
        af.setUser_define(paf.isUser_define());
        af.setFormula_sql(paf.getFormula_sql());
        af.setField_type(field_type);
        af.setField_value(field_value);
        af.setFirst_field(first_field);
        af.setField_desc(paf.getField_caption());
        if (field_caption != null) {
            af.setField_caption(field_caption);
        }
        return af;
    }

    public static AnalyseField createAnalyseField(String field_caption, String field_value, String field_desc, int field_type) {
        AnalyseField af = new AnalyseField();
        af.setField_caption(field_caption);
        af.setField_value(field_value);
        af.setField_type(field_type);
        af.setField_desc(field_desc);
        return af;
    }

    public static void buildTables(String entity_name, HashSet<String> tableNames) {
        if (entity_name != null && !entity_name.equals("") && !tableNames.contains(entity_name)) {
            if (entity_name.contains(";")) {
                String[] entitys = entity_name.split("\\;");
                for (String entity : entitys) {
                    entity = entity.toUpperCase();
                    if (!tableNames.contains(entity)) {
                        tableNames.add(entity);
                    }
                }
            } else {
                entity_name = entity_name.toUpperCase();
                if (!tableNames.contains(entity_name)) {
                    tableNames.add(entity_name);
                }
            }

        }
    }
    /**
     * 该方法用于获得一个统计字段对应的统计条件SQL
     * @param af：目标统计字段
     * @return：SQL条件
     */
    public static String getAnalyseFieldValue(AnalyseField af) {
        if (af == null || af.getField_value() == null) {
            return "1=1";
        }
        if (af.getField_type() == 4) {
            return "C21.gz_ym='" + af.getField_value() + "'";
        } else if (af.getField_type() == 2) {
            if (af.isEnd_flag()) {
                return af.getEntity_name() + "." + af.getField_name() + "='" + af.getField_value() + "'";
            } else {
                return af.getEntity_name() + "." + af.getField_name() + " like '" + af.getField_value() + "%'";
            }
        } else if (af.getField_type() == 1) {
            return "DeptCode.dept_code like '" + af.getField_value() + "%'";
        }
        return af.getField_value();
    }
    /**
     * 该方法用于获得一个统计字段对应的统计条件SQL
     * @param af：目标统计字段
     * @return：SQL条件
     */
    public static String getEmpAnalyseFieldValue(AnalyseField af) {
        if (af == null || af.getField_value() == null) {
            return "1=1";
        }
        if (af.getField_type() == 2) {
            if (af.isEnd_flag()) {
                return af.getEntity_name() + "." + af.getField_name() + "='" + af.getField_value() + "'";
            } else {
                return af.getEntity_name() + "." + af.getField_name() + " like '" + af.getField_value() + "%'";
            }
        } else if (af.getField_type() == 1) {
            return "A01.deptCode.dept_code like '" + af.getField_value() + "%'";
        }
        return af.getField_value();
    }
}
