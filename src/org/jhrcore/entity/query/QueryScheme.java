/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.query;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.FieldAnnotation;

import com.jgoodies.binding.beans.Model;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OrderBy;
import org.apache.log4j.Logger;
import org.jhrcore.util.DateUtil;
import org.jhrcore.comm.CodeManager;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.mutil.QueryUtil;
import org.jhrcore.rebuild.EntityBuilder;

/**
 *
 * @author Administrator
 */
@Entity
@ClassAnnotation(displayName = "通用查询方案表", moduleName = "系统维护")
public class QueryScheme extends Model implements Serializable, KeyInterface, IKey {

    private static Logger log = Logger.getLogger(QueryScheme.class.getSimpleName());
    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String queryScheme_key;
    @FieldAnnotation(visible = true, displayName = "方案名称", groupName = "Default")
    public String queryScheme_name;
    // 查询方案对应的实体类
    @FieldAnnotation(visible = false)
    protected String queryEntity;
    public List<Condition> conditions = new ArrayList<Condition>();
    @FieldAnnotation(visible = false, displayName = "角色ID", groupName = "Default")
    public String person_code;
    @FieldAnnotation(visible = false, displayName = "共享标识", groupName = "Default")
    public boolean share_flag = false;
    //0:条件查询；2：直观挑选;1:查询合集;
    @FieldAnnotation(visible = false, displayName = "条件类型", groupName = "Default")
    public int query_type = 0;
    //保存直观挑选的类的_key
    private String query_text;
    @FieldAnnotation(visible = true, displayName = "条件表达式", groupName = "Default")
    private String condition_expression;
    //方案类型，用来区分方案用在什么地方
    private String scheme_type;
    private Date make_date = new Date();
    public transient int new_flag = 0;

    @Id
    public String getQueryScheme_key() {
        return queryScheme_key;
    }

    public void setQueryScheme_key(String queryScheme_key) {
        String old = this.queryScheme_key;
        this.queryScheme_key = queryScheme_key;
        this.firePropertyChange("queryScheme_key", old, queryScheme_key);
    }

    public String getScheme_type() {
        return scheme_type;
    }

    public void setScheme_type(String scheme_type) {
        String old = this.scheme_type;
        this.scheme_type = scheme_type;
        this.firePropertyChange("scheme_type", old, scheme_type);
    }

    public int getQuery_type() {
        return query_type;
    }

    public void setQuery_type(int query_type) {
        int old = this.query_type;
        this.query_type = query_type;
        this.firePropertyChange("query_type", old, query_type);
    }

    // fromHql ：hql的from部分，比如from A01 ed join fetch ed.dept
    // 注意主实体的别名必须是ed
    public String buildHql(String fromHql) {
        return buildHql(fromHql, "ed");
    }

    /**
     *
     * @param fromHql:开始语句
     * @param entity_name：表的别名
     * @return:最终的SQL语句
     */
    public String buildHql(String fromHql, String entity_name) {
        if (entity_name.equals("")) {
            entity_name = "ed";
        }
        String hql = " from " + this.queryEntity + " " + entity_name + " ";
        if (fromHql != null && !fromHql.equals("")) {
            hql = fromHql;
        }
        if (!hql.contains("where")) {
            hql = hql + " where 1=1 ";// + this.buildWhere();
        }
        String hql1 = "";
        if (this.query_type == 0 || this.query_type == 1) {
            if (condition_expression != null && !condition_expression.equals("")) {
                String tmp_condition_expression = condition_expression;
                tmp_condition_expression = tmp_condition_expression.replaceAll("\\+", " and ");
                tmp_condition_expression = tmp_condition_expression.replaceAll(",", " or ");
                tmp_condition_expression = QueryUtil.tranExpStr(tmp_condition_expression, conditions.size());
                int i = 0;
                for (Condition condition : conditions) {
                    i++;
                    if (condition.getOperator() == null || condition.getOperator().trim().equals("")) {
                        String s_where = condition.getFieldValue();
                        s_where = entity_name + s_where.substring(s_where.indexOf("."));
                        tmp_condition_expression = tmp_condition_expression.replace(QueryUtil.getTempStr(i), s_where);
                    } else if (condition.getFieldClasses().replaceAll("2;", "").equals("1")) {
                        tmp_condition_expression = tmp_condition_expression.replace(QueryUtil.getTempStr(i), "(" + condition.buildDirectHqlCondition(entity_name) + ")");
                    } else {
                        tmp_condition_expression = tmp_condition_expression.replace(QueryUtil.getTempStr(i), entity_name + " in (" + condition.getWhereCondition2() + ")");
                    }
                }
                hql1 = hql1 + " and (" + tmp_condition_expression + ")";
            } else {
                int i = 0;
                for (Condition condition : conditions) {
                    if (condition.getOperator().equals("")) {
                        hql1 = hql1 + " and " + condition.getFieldValue();
                    } else if (condition.getFieldClasses().replaceAll("2;", "").equals("1")) {
                        hql1 = hql1 + " and " + "(" + condition.buildDirectHqlCondition(entity_name) + ")";
                    } else {
                        hql1 = hql1 + " and " + entity_name + " in (" + condition.getWhereCondition2() + ")";
                    }
                    i++;
                }
            }
        }
        if (this.query_type == 2 || this.query_type == 1) {
            String s_plus = " and ";
            if (!hql1.equals("") && this.query_type == 1) {
                s_plus = " or ";
            }
            if (this.query_text != null && !this.query_text.trim().equals("") && !this.query_text.trim().equals("'-1'")) {
                if (queryEntity.equals("DeptCode")) {
                    hql1 += s_plus + entity_name + ".deptCode_key in(" + this.query_text + ")";
                } else {
                    hql1 += s_plus + entity_name + ".a01_key in(" + this.query_text + ")";
                }
            } else {
                hql1 += s_plus + "1=0";
            }
        }
        if (hql1.startsWith(" and")) {
            hql1 = hql1.substring(4);
        }
        if (!hql1.equals("")) {
            hql = hql + " and (" + hql1 + ")";
        }
        return hql + " ";
    }

    public String buildSql() {
        String tables = queryEntity;
        String superClass = queryEntity; // 包含主键的类
        String joins = "";
        String fullEntityClass = EntityBuilder.getHt_entity_classes().get(queryEntity);
        try {
            Class aClass = Class.forName(fullEntityClass);
            if (aClass.getSuperclass().getName().contains("entity")) {
                if (aClass.getSuperclass().getSimpleName().equals("Pay")) {
                    if (!queryEntity.equals("C21")) {
                        superClass = "C21";
                        tables = tables + "," + superClass;
                        String key_field = EntityBuilder.getEntityKey(queryEntity);
                        joins = queryEntity + "." + key_field + "=" + superClass + "." + key_field;
                    }
                } else if (aClass.getSuperclass().getSimpleName().equals("In_account")) {
                } else if (aClass.getSuperclass().getSimpleName().equals("A01")) {
                    aClass = aClass.getSuperclass();
                    superClass = aClass.getSimpleName();
                    tables = tables + "," + superClass;
                    String key_field = EntityBuilder.getEntityKey(queryEntity);
                    joins = queryEntity + "." + key_field + "=" + superClass + "." + key_field;
                }
            }
        } catch (ClassNotFoundException ex) {
            log.error(ex);
        }

        QueryScheme queryScheme = this;
        String key_field = EntityBuilder.getEntityKey(queryEntity);
        String tmp_condition_expression = "";
        if (this.query_type == 0 || this.query_type == 1) {
            tmp_condition_expression = condition_expression;
            if (tmp_condition_expression == null || tmp_condition_expression.equals("")) {
                tmp_condition_expression = "";
                for (int m = 1; m <= queryScheme.getConditions().size(); m++) {
                    if (m != 1) {
                        tmp_condition_expression = tmp_condition_expression + "+";
                    }
                    tmp_condition_expression = tmp_condition_expression + m;
                }
            }
            tmp_condition_expression = QueryUtil.tranExpStr(tmp_condition_expression, queryScheme.getConditions().size());
            tmp_condition_expression = tmp_condition_expression.replaceAll("\\+", " and ");
            tmp_condition_expression = tmp_condition_expression.replaceAll(",", " or ");
            int i = 0;
            for (Condition condition : queryScheme.getConditions()) {
                i++;
                if (condition.getOperator().trim().equals("") || condition.getBindName().trim().equals("高级条件")) {
                    tmp_condition_expression = tmp_condition_expression.replace(QueryUtil.getTempStr(i),
                            "(" + condition.getBindName() + ")");
//                            "(" + condition.getFieldValue() + ")");
                    continue;
                }
                String[] tmp_entityNames = condition.getEntityNames().split(";");
                String[] tmp_fieldClasses = condition.getFieldClasses().split(";");

                for (int i2 = 0; i2 < tmp_fieldClasses.length - 1; i2++) {
                    String entity1 = tmp_entityNames[i2];
                    String entity2 = tmp_entityNames[i2 + 1];

                    String ent_field1 = entity1.substring(0, 1).toLowerCase() + entity1.substring(1);
                    String ent_field2 = entity2.substring(0, 1).toLowerCase() + entity2.substring(1);

                    String fieldClass = tmp_fieldClasses[i2];
                    if (queryEntity.equals(entity1) && (fieldClass.equals("2") || fieldClass.equals("5"))) {
                        if (!EntityBuilder.containsField(entity1, ent_field2)) {
                            entity1 = superClass;
                        }
                    } else if (queryEntity.equals(entity2)) {
                        if (!EntityBuilder.containsField(entity2, ent_field1)) {
                            entity2 = superClass;
                        }
                    }

                    if (!tables.contains(entity1)) {
                        if (!tables.equals("")) {
                            tables = tables + ",";
                        }
                        tables = tables + entity1;
                    }
                    if (!tables.contains(entity2)) {
                        if (!tables.equals("")) {
                            tables = tables + ",";
                        }
                        tables = tables + entity2;
                    }

                    if (fieldClass.equals("2")) {
                        String tmp_join = entity1 + "." + EntityBuilder.getEntityKey(entity2) + "="
                                + entity2 + "." + EntityBuilder.getEntityKey(entity2);
                        if (!joins.contains(tmp_join)) {
                            if (!joins.equals("")) {
                                joins = joins + " and ";
                            }
                            joins = joins + tmp_join;
                        }
                    } else if (fieldClass.equals("3")) {
                        String tmp_join = entity1 + "." + EntityBuilder.getEntityKey(entity1) + "="
                                + entity2 + "." + EntityBuilder.getEntityKey(entity1);
                        if (!joins.contains(tmp_join)) {
                            if (!joins.equals("")) {
                                joins = joins + " and ";
                            }
                            joins = joins + tmp_join;
                        }
                    } else if (fieldClass.equals("5")) {
                        String tmp_join = entity1 + "." + EntityBuilder.getEntityKey(entity2) + "="
                                + entity2 + "." + EntityBuilder.getEntityKey(entity2);
                        if (!joins.contains(tmp_join)) {
                            if (!joins.equals("")) {
                                joins = joins + " and ";
                            }
                            joins = joins + tmp_join;
                        }
                    }
                }
                String entity_name = condition.getEntityName();
                String tmp_fieldValue = condition.fieldValue;
                String tmp_fieldName = condition.fieldName.replace("_code_", "");
                TempFieldInfo tfi = EntityBuilder.getTempFieldInfoByName(entity_name, tmp_fieldName, false);
                if (tfi != null) {
                    Field field = tfi.getField();
                    Class c = field.getDeclaringClass();
                    Inheritance in = (Inheritance) c.getAnnotation(Inheritance.class);
                    if (in != null) {
                        if (in.strategy() == InheritanceType.JOINED) {
                            entity_name = field.getDeclaringClass().getSimpleName();
                        }
                    }
                }
                String tmp_2 = entity_name + "." + tmp_fieldName;
                if (condition.getFieldName().contains("_code_")) {//&& !condition.getOperator().contains("like")) {
                    Object obj = CodeManager.getCodeManager().getCodeIdBy(condition.getCode_type(), tmp_fieldValue != null ? tmp_fieldValue.replace("'", "") : "");
                    if (obj == null) {
                        obj = tmp_fieldValue;
                    }
                    tmp_fieldValue = (obj == null) ? "" : obj.toString();
                    if (!tmp_fieldValue.contains("'")) {
                        tmp_fieldValue = "'" + tmp_fieldValue + "'";
                    }
                }
                if (condition.operator.contains("like") && !tmp_fieldValue.contains("like")) {
                    tmp_fieldValue = "'" + condition.operator.replace(condition.operator.replaceAll("%", ""), tmp_fieldValue.replaceAll("'", "")) + "'";
//                    tmp_fieldValue = "'" + condition.operator.replace("like", tmp_fieldValue.replaceAll("'", "")) + "'";
                }
                String tmp_con =
                        " " + condition.operator.replaceAll("%", "") + " " + tmp_fieldValue;
                if (condition.getFieldName().contains("_code_") && condition.operator.contains("like")) {
                    String val = condition.operator.contains("not") ? " and " : "or";
                    tmp_con = " in (select code_id from Code c where code_type = '" + condition.getCode_type() + "' and (c.code_id" + tmp_con + " " + val + " c.code_name" + tmp_con + "))";
                }
                String operator = condition.operator;
                String tmp_value = condition.fieldValue;
                String field_type = tfi.getField_type().toLowerCase();
                if (field_type.equals("date")) {
                    String format = tfi.getFormat();
                    format = format == null ? "yyyy-MM-dd" : format;
                    tmp_2 = DateUtil.getDateSQL(tmp_2, condition.operator.replaceAll("%", ""), tmp_value, format);
                    // 考虑字段包含时间的情况
                    tmp_con = " ";// + condition.operator.replaceAll("%", "");//+ " " + DateUtil.toStringForQuery((Date) tmp_value);
//                    if (UserContext.sql_dialect.equals("sqlserver") || format.contains("y")) {
//                        tmp_con += " " + DateUtil.toStringForQuery((Date) tmp_value, format);
//                    } else {
//                        tmp_con += "'" + DateUtil.DateToStr((Date) tmp_value, format) + "'";
//                        tmp_2 = "to_char(" + tmp_2 + ",'" + format.replace("hh", "HH").replace("HH", "HH24").replace("mm", "MI") + "')";
//                    }
//                    if (operator.equals("=")) {
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime((Date) tmp_value);
//                        calendar.add(Calendar.DATE, 1);
//                        tmp_con = " >= " + DateUtil.toStringForQuery((Date) tmp_value) + " and " + entity_name + "." + tmp_fieldName + "<" + DateUtil.toStringForQuery(calendar.getTime()) + " ";
//                    } else if (operator.equals("<=")) {
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime((Date) tmp_value);
//                        calendar.add(Calendar.DATE, 1);
//                        tmp_con = " <" + DateUtil.toStringForQuery(calendar.getTime()) + " ";
//                    }
                }
                if (operator.contains("null")) {
                    String oper = "=";
                    if (operator.contains("not")) {
                        oper = "<>";
                    }
                    if (field_type.equals("string")) {
                        if (org.jhrcore.client.UserContext.sql_dialect.equals("sqlserver")) {
                            tmp_2 = "isnull(replace(" + tmp_2 + ",' ',''),'')" + oper + "''";
//                        tmp_2 = "isnull(replace(" + entity_name + "." + tmp_fieldName + ",' ',''),'')" + oper + "''";
                        } else {
                            tmp_2 = "nvl(replace(replace(" + tmp_2 + ",' ',''),'@','#'),'@')" + oper + "'@'";
//                        tmp_2 = "nvl(replace(replace(" + entity_name + "." + tmp_fieldName + ",' ',''),'@','#'),'@')" + oper + "'@'";
                        }
                    }
                } else {
                    tmp_2 += " " + tmp_con + " ";
                    //tmp_2 = entity_name + "." + tmp_fieldName + " " + tmp_con + " "; //condition.getOperator() + " [?" + condition.getDisplayName().substring(condition.getDisplayName().indexOf(".") + 1) + "?]";
                }
                entity_name = condition.getEntityName();
                if (!EntityBuilder.containsField(entity_name, tmp_fieldName)) {
                    entity_name = superClass;
                }
                tmp_condition_expression = tmp_condition_expression.replace(QueryUtil.getTempStr(i),
                        "(" + tmp_2 + ")");
            }
        }
        if (!tmp_condition_expression.equals("")) {
            tmp_condition_expression = "(" + tmp_condition_expression + ")";
        }
        if (this.query_type == 2 || this.query_type == 1) {
            String s_plus = " and ";
            String s_where_append = "";//用于直观挑选
            if (!tmp_condition_expression.equals("") && (this.query_type == 1)) {
                s_plus = " or ";
            } else if (tmp_condition_expression.equals("")) {
                s_plus = " ";
            }
            if (this.query_text != null && !this.query_text.trim().equals("") && !this.query_text.trim().equals("'-1'")) {
                if (queryEntity.equals("DeptCode")) {
                    s_where_append = s_plus + this.queryEntity + ".deptCode_key in(" + this.query_text.replace("'-1',", "") + ")";
                } else {
                    s_where_append = s_plus + this.queryEntity + ".a01_key in(" + this.query_text.replace("'-1',", "") + ")";
                }
            } else {
                s_where_append = s_plus + "1=0";
            }
            tmp_condition_expression += s_where_append;
        }
        if (!joins.equals("")) {
            joins = joins + "\n   and ";
        }
        if (tmp_condition_expression != null && tmp_condition_expression.trim().length() > 0) {
            joins = joins + " (" + tmp_condition_expression + ")";
        }
        String sql = "select \n" + superClass + "." + key_field + "\n  from " + tables + "\n";
        if (!joins.equals("")) {
            sql = sql + " where " + joins;
        }
        return sql + " ";
    }

    /**
     * 该方法用于构造单表(single)查询模式下的SQL语句条件，与buildScript()方法成对使用，用于权限管理中的数据集权限
     * @param start_name：条件表别名
     * @return：翻译后的SQL(HQL)语句
     */
    public String buildSimpleSQL(String start_name) {
        String result = "";
        Hashtable<Integer, String> cons = new Hashtable<Integer, String>();
        String db_type = org.jhrcore.client.UserContext.sql_dialect;
        if (this.getConditions() != null && this.getConditions().size() > 0) {
            for (Condition c : this.getConditions()) {
                String oper = c.getOperator();
                String type = c.getFieldType().toLowerCase();
                String val = c.getDisplayValue();               
                System.out.println("val:"+val);
                String field = c.getFieldName();
                if (field.endsWith("_code_")) {
                    field = field.substring(0, field.length() - 6);
                    String val1 = CodeManager.getCodeManager().getCodeIdBy(c.getCode_type(), val);
                    if (val1 != null) {
                        val = val1;
                    }
                }
                val = val.replace("'", "");
                System.out.println("val1:"+val);
                String start = start_name + "." + field + " ";
                String con = start;
                if (oper.equals("=") || oper.equals(">") || oper.equals(">=") || oper.equals("<") || oper.equals("<=") || oper.equals("<>")) {
                    if (type.equals("int") || type.equals("integer") || type.equals("boolean")) {
                        con += oper + val;
                    } else if (type.equalsIgnoreCase("date")) {
                        TempFieldInfo tfi = EntityBuilder.getTempFieldInfoByName(c.getEntityFullName(), field, true);
                        String format = tfi.getFormat();
                        format = format == null ? "yyyy-MM-dd" : format;
                        con = DateUtil.getDateSQL(con, oper, val, format);
//                        if (UserContext.sql_dialect.equals("sqlserver") || format.contains("y")) {
//                            val = DateUtil.toStringForQuery(DateUtil.StrToDate(val, format), format);
//                        } else {
//                            val = "'" + val + "'";
//                            con = "to_char(" + con + ",'" + format.replace("hh", "HH").replace("HH", "HH24").replace("mm", "MI") + "')";
//                        }
//                        con += oper + val;
                    } else {
                        con += oper + "'" + val + "'";
                    }
                } else if (oper.equals("is null")) {
                    if (db_type.equals("sqlserver")) {
                        con += " is null or rtrim(ltrim(" + start + "))=''";
                    } else {
                        con = "trim(" + con + ") is null";
                    }
                } else if (oper.equals("is not null")) {
                    if (db_type.equals("sqlserver")) {
                        con = " rtrim(ltrim(" + start + "))<>''";
                    } else {
                        con = "trim(" + con + ") is not null";
                    }
                } else if (oper.contains("like")) {
                    con += oper.replace("%", "") + " '" + oper.replace("like", val) + "'";
                }
                cons.put(c.getOrder_no(), con);
            }
            int len = this.getConditions().size();
            result = this.getCondition_expression().replace("+", " and ").replace(",", " or ");
            result = QueryUtil.tranExpStr(result, len);
            for (int i = len; i > 0; i--) {
                result = result.replace(QueryUtil.getTempStr(i), cons.get(i));
            }
        }
        return result;
    }

    /**
     * 该方法用于将当前方案翻译成JAVAScript脚本，以便于在程序中的JAVASCRIPT引擎调用，从而判断其数据编辑性，与buildSimpleSQL()方法成对使用
     * @return:翻译后的JAVASCRIPT脚本
     */
    public String buildScript() {
        String result = "e!=null";
        Hashtable<Integer, String> cons = new Hashtable<Integer, String>();
        if (this.getConditions() != null && this.getConditions().size() > 0) {
            for (Condition c : this.getConditions()) {
                String oper = c.getOperator();
                String type = c.getFieldType().toLowerCase();
                String val = c.getDisplayValue();
                String field = c.getFieldName().substring(0, 1).toUpperCase() + c.getFieldName().substring(1);
                if (field.endsWith("_code_")) {
                    field = field.substring(0, field.length() - 6);
                    String val1 = CodeManager.getCodeManager().getCodeIdBy(c.getCode_type(), val);
                    if (val1 != null) {
                        val = val1;
                    }
                }
                String con = "e.get" + field + "()";
                if (oper.equals("is null")) {
                    //等于null,加入"".equals(val)
                    con = con + " == null||\"\".equals("+con+")";
                } else if (oper.equals("is not null")) {
                    con += "!=null";
                } else {
                    if (!oper.equals("<>")){
                        con = con + "!=null&&!\"\".equals("+con+")&&" + con;
                    }
                    if (oper.equals("=") || oper.equals(">") || oper.equals(">=") || oper.equals("<") || oper.equals("<=") || oper.equals("<>")) {
                        if (oper.equals("=")) {
                            oper = "==";
                        } else if (oper.equals("<>")) {
                            oper = "!=";
                            //<>的情况,加入null判断
                            con = "(" + con + " == null||\"\".equals("+con+")) || " + con;
                        }
                        if (type.equals("int") || type.equals("integer") || type.equals("boolean")) {
                            con += oper + val;
                        } else {
                            con += oper + "'" + val + "'";
                        }
                    } else if (oper.equals("like%")) {
                        con += ".startsWith(\"" + val + "\")";
                    } else if (oper.equals("%like")) {
                        con += ".endWith(\"" + val + "\")";
                    } else if (oper.equals("%like%")) {
                        con += ".contains(\"" + val + "\")";
                    } else if (oper.equals("not like%")) {
                        con = "!" + con + ".startsWith(\"" + val + "\")";
                    } else if (oper.equals("not %like")) {
                        con = "!" + con + ".endWith(\"" + val + "\")";
                    } else if (oper.equals("not %like%")) {
                        con = "!" + con + ".contains(\"" + val + "\")";
                    }
                }
                con = "(" + con + ")";
                cons.put(c.getOrder_no(), con);
            }
            int len = this.getConditions().size();
            result = result + "&&(" + this.getCondition_expression().replace("+", "&&").replace(",", "||") + ")";
            result = QueryUtil.tranExpStr(result, len);
            for (int i = len; i > 0; i--) {
                result = result.replace(QueryUtil.getTempStr(i), cons.get(i));
            }
        }
        return result;
    }

    public String getQuery_text() {
        return query_text;
    }

    public void setQuery_text(String query_text) {
        String old = this.query_text;
        this.query_text = query_text;
        this.firePropertyChange("query_text", old, query_text);
    }

    @OneToMany(mappedBy = "queryScheme", fetch = FetchType.LAZY)
    @OrderBy("order_no")
    public List<Condition> getConditions() {
        if (conditions == null) {
            conditions = new ArrayList();
        }
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public String getQueryScheme_name() {
        return queryScheme_name;
    }

    public void setQueryScheme_name(String queryScheme_name) {
        String old = this.queryScheme_name;
        this.queryScheme_name = queryScheme_name;
        this.firePropertyChange("queryScheme_name", old, queryScheme_name);
    }

    public String getQueryEntity() {
        return queryEntity;
    }

    public void setQueryEntity(String queryEntity) {
        String old = this.queryEntity;
        this.queryEntity = queryEntity;
        this.firePropertyChange("queryEntity", old, queryEntity);
    }

    public String getCondition_expression() {
        return condition_expression;
    }

    public void setCondition_expression(String condition_expression) {
        String old = this.condition_expression;
        this.condition_expression = condition_expression;
        this.firePropertyChange("condition_expression", old, condition_expression);
    }

    public String getPerson_code() {
        return person_code;
    }

    public void setPerson_code(String person_code) {
        String old = this.person_code;
        this.person_code = person_code;
        this.firePropertyChange("person_code", old, person_code);
    }

    public boolean isShare_flag() {
        return share_flag;
    }

    public void setShare_flag(boolean share_flag) {
        boolean old = this.share_flag;
        this.share_flag = share_flag;
        this.firePropertyChange("share_flag", old, share_flag);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QueryScheme other = (QueryScheme) obj;
        if ((this.queryScheme_key == null) ? (other.queryScheme_key != null) : !this.queryScheme_key.equals(other.queryScheme_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (this.queryScheme_key != null ? this.queryScheme_key.hashCode() : 0);
        return hash;
    }

    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        int old = this.new_flag;
        this.new_flag = new_flag;
        this.firePropertyChange("new_flag", old, new_flag);
    }

    @Override
    public String toString() {
        return this.queryScheme_name;
    }

    @Override
    public void assignEntityKey(String key) {
        this.queryScheme_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    public Date getMake_date() {
        return make_date;
    }

    public void setMake_date(Date make_date) {
        Date old = this.make_date;
        this.make_date = make_date;
        this.firePropertyChange("make_date", old, make_date);
    }
}
