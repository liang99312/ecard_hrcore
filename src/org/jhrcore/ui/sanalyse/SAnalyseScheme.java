/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.sanalyse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jhrcore.client.CommUtil;
import org.jhrcore.comm.CodeManager;
import org.jhrcore.entity.base.EntityDef;
import org.jhrcore.entity.query.QueryScheme;
import org.jhrcore.util.DbUtil;
import org.jhrcore.util.MsgUtil;

/**
 *
 * @author Administrator
 */
public class SAnalyseScheme {

    private Class main_class;
    private List<EntityDef> entityDefs = new ArrayList<EntityDef>();
    private HashMap<String, String> whereMap = new HashMap<String, String>();
    private QueryScheme queryScheme;
    private QueryScheme querySchemeDept;
    private List<SAnalyseField> analyseFields = new ArrayList<SAnalyseField>();
    private List<SAnalyseField> orderFields = new ArrayList<SAnalyseField>();
    private boolean dept_flag = false;

    public Class getMain_class() {
        return main_class;
    }

    public void setMain_class(Class main_class) {
        this.main_class = main_class;
    }

    public List<EntityDef> getEntityDefs() {
        return entityDefs;
    }

    public void setEntityDefs(List<EntityDef> entityDefs) {
        this.entityDefs = entityDefs;
    }

    public QueryScheme getQueryScheme() {
        return queryScheme;
    }

    public void setQueryScheme(QueryScheme queryScheme) {
        this.queryScheme = queryScheme;
    }

    public List<SAnalyseField> getAnalyseFields() {
        return analyseFields;
    }

    public void setAnalyseFields(List<SAnalyseField> analyseFields) {
        this.analyseFields = analyseFields;
    }

    public HashMap<String, String> getWhereMap() {
        return whereMap;
    }

    public void setWhereMap(HashMap<String, String> whereMap) {
        this.whereMap = whereMap;
    }

    public List<SAnalyseField> getOrderFields() {
        return orderFields;
    }

    public void setOrderFields(List<SAnalyseField> orderFields) {
        this.orderFields = orderFields;
    }

    public QueryScheme getQuerySchemeDept() {
        return querySchemeDept;
    }

    public void setQuerySchemeDept(QueryScheme querySchemeDept) {
        this.querySchemeDept = querySchemeDept;
    }

    public boolean isDept_flag() {
        return dept_flag;
    }

    public void setDept_flag(boolean dept_flag) {
        this.dept_flag = dept_flag;
    }

    public String check_fields() {
        boolean group_flag = false;
        List<String> fieldList = new ArrayList<String>();
        for (int i = 0; i < analyseFields.size(); i++) {
            SAnalyseField temp_field = analyseFields.get(i);
            if ("group".equals(temp_field.getField_style())) {
                group_flag = true;
                fieldList.add(temp_field.getEntity_name() + "." + temp_field.getField_name());
            }
        }
        if (!group_flag || orderFields.isEmpty()) {
            return "0";
        }
        for (int i = 0; i < orderFields.size(); i++) {
            SAnalyseField temp_field = orderFields.get(i);
            if (!fieldList.contains(temp_field.getEntity_name() + "." + temp_field.getField_name())) {
                return "排序字段（" + temp_field.getField_content() + "）必须包含在分组字段中";
            }
        }
        return "0";
    }

    public String getSql() {
        if (analyseFields.isEmpty()) {
            return "";
        }
        String sql = "";
        if (dept_flag) {
            String main_table = main_class.getSimpleName();
            sql += "select ";
            String r_select_sql = "select ";
            String table_sql = "";
            String where_sql = " where 1=1 ";
            String group_sql = "";
            String order_sql = "";
            table_sql += "(select " + main_table + ".*,DeptCode.dept_code from "
                    + main_table + ",DeptCode where " + whereMap.get("DeptCode") + ")" + " " + main_table;
            for (int i = 0; i < analyseFields.size(); i++) {
                boolean d_flag = false;
                SAnalyseField temp_field = analyseFields.get(i);
                if ("group".equals(temp_field.getField_style())) {
                    sql += temp_field.getEntity_name() + "." + temp_field.getField_name() + ",";
                    if ("dept_code".equals(temp_field.getField_name())) {
                        r_select_sql += temp_field.getEntity_name() + "." + temp_field.getField_name() + ",";
                    } else {
                        r_select_sql += main_table + "." + temp_field.getField_name() + ",";
                    }
                    if ("dept_code".equals(temp_field.getField_name()) && "DeptCode".equals(temp_field.getEntity_name())) {
                        r_select_sql += "DeptCode.content,";
                        d_flag = true;
                    }
                    group_sql += "," + temp_field.getEntity_name() + "." + temp_field.getField_name();
                } else {
                    sql += temp_field.getField_style() + "(" + temp_field.getEntity_name() + "." + temp_field.getField_name() + ") as s" + (i + 1) + ",";
                    r_select_sql += main_table + ".s" + (i + 1) + ",";
                }
                if (main_table.equals(temp_field.getEntity_name()) || table_sql.contains(temp_field.getEntity_name() + " " + temp_field.getEntity_name())) {
                    continue;
                }
                table_sql += "," + temp_field.getEntity_name() + " " + temp_field.getEntity_name();
                if (d_flag) {
                    continue;
                }
                where_sql += " and " + whereMap.get(temp_field.getEntity_name());
            }
            for (int i = 0; i < orderFields.size(); i++) {
                SAnalyseField temp_field = orderFields.get(i);
                boolean d_flag = false;
                if (!"DeptCode".equals(temp_field.getEntity_name())) {
                    order_sql += "," + main_table + "." + temp_field.getField_name() + " " + temp_field.getField_style();
                } else {
                    order_sql += ",DeptCode." + temp_field.getField_name() + " " + temp_field.getField_style();
                }
                if (main_table.equals(temp_field.getEntity_name()) || table_sql.contains(temp_field.getEntity_name() + " " + temp_field.getEntity_name())) {
                    continue;
                }
                table_sql += "," + temp_field.getEntity_name() + " " + temp_field.getEntity_name();
                if (d_flag) {
                    continue;
                }
                where_sql += " and " + whereMap.get(temp_field.getEntity_name());
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += " from " + table_sql;
            if (queryScheme != null) {
                where_sql += " and " + main_table + "." + main_table.substring(0, 1).toLowerCase() + main_table.substring(1) + "_key in (" + queryScheme.buildSql() + ")";
            }
            sql += where_sql;
            sql += " and " + main_table + ".dept_code like DeptCode.dept_code " + DbUtil.getPlusStr(DbUtil.SQL_dialect_check(CommUtil.getSQL_dialect())) + "'%'";
            if (group_sql.startsWith(",")) {
                sql += " group by " + group_sql.substring(1);
            }
            r_select_sql = r_select_sql.substring(0, r_select_sql.length() - 1);
            r_select_sql += " from (" + sql + ") " + main_table + ",DeptCode where " + main_table + ".dept_code=DeptCode.dept_code";
            if (querySchemeDept != null) {
                r_select_sql += " and DeptCode.deptCode_key in (" + querySchemeDept.buildSql() + ")";
            }
            if (order_sql.startsWith(",")) {
                r_select_sql += " order by " + order_sql.substring(1);
            }
            sql = r_select_sql;
        } else {
            String main_table = main_class.getSimpleName();
            sql += "select ";
            String table_sql = "";
            String where_sql = " where 1=1 ";
            String group_sql = "";
            String order_sql = "";
            table_sql += main_table + " " + main_table;
            for (int i = 0; i < analyseFields.size(); i++) {
                SAnalyseField temp_field = analyseFields.get(i);
                if ("group".equals(temp_field.getField_style())) {
                    sql += temp_field.getEntity_name() + "." + temp_field.getField_name() + ",";
                    group_sql += "," + temp_field.getEntity_name() + "." + temp_field.getField_name();
                } else {
                    sql += temp_field.getField_style() + "(" + temp_field.getEntity_name() + "." + temp_field.getField_name() + ") as s" + (i + 1) + ",";
                }
                if (table_sql.contains(temp_field.getEntity_name() + " " + temp_field.getEntity_name())) {
                    continue;
                }
                table_sql += "," + temp_field.getEntity_name() + " " + temp_field.getEntity_name();
                where_sql += " and " + whereMap.get(temp_field.getEntity_name());
            }
            for (int i = 0; i < orderFields.size(); i++) {
                SAnalyseField temp_field = orderFields.get(i);
                order_sql += "," + temp_field.getEntity_name() + "." + temp_field.getField_name() + " " + temp_field.getField_style();
                if (table_sql.contains(temp_field.getEntity_name() + " " + temp_field.getEntity_name())) {
                    continue;
                }
                table_sql += "," + temp_field.getEntity_name() + " " + temp_field.getEntity_name();
                where_sql += " and " + whereMap.get(temp_field.getEntity_name());
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += " from " + table_sql;
            if (queryScheme != null) {
                String keyString = main_table.substring(0, 1).toLowerCase() + main_table.substring(1) + "_key";
                if(main_table.equalsIgnoreCase("C21")){
                    keyString = "pay_key";
                }
                where_sql += " and " + main_table + "." + keyString + " in (" + queryScheme.buildSql() + ")";
            }
            sql += where_sql;
            if (group_sql.startsWith(",")) {
                sql += " group by " + group_sql.substring(1);
            }
            if (order_sql.startsWith(",")) {
                sql += " order by " + order_sql.substring(1);
            }
        }
        System.out.println("sql=" + sql);
        return sql;
    }

    public List getResult() {
        List result = new ArrayList();
        String cString = check_fields();
        if (!"0".equals(cString)) {
            MsgUtil.showErrorMsg(cString);
            return result;
        }
        String sql = getSql();
        //校验语句
//        ValidateSQLResult validateSQLResult = CommUtil.validateSQL(sql, false);
//        if (validateSQLResult.getResult() != 0) {
//            MsgUtil.showErrorMsg(validateSQLResult.getMsg());
//            return result;
//        }
        if ("".equals(sql)) {
        } else {
            result = CommUtil.selectSQL(sql);
            int d_flag = 0;
            for (int i = 0; i < analyseFields.size(); i++) {
                SAnalyseField temp_field = analyseFields.get(i);
                if (temp_field.getField_name().equals("dept_code") && temp_field.getEntity_name().equals("DeptCode") && dept_flag) {
                    d_flag = 1;
                }
                if ("group".equals(temp_field.getField_style())) {
                    if (temp_field.getCode_name() != null && !"".equals(temp_field.getCode_name())) {
                        for (Object obj : result) {
                            if (obj instanceof Object[]) {
                                Object[] objs = (Object[]) obj;
                                objs[i + d_flag] = (objs[i + d_flag] == null || "".equals(objs[i + d_flag].toString())) ? "" : CodeManager.getCodeManager().getCodeNameBy(temp_field.getCode_name(), objs[i + d_flag].toString());
                            } else {
                                obj = (obj == null || "".equals(obj.toString())) ? "" : CodeManager.getCodeManager().getCodeNameBy(temp_field.getCode_name(), obj.toString());
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
}
