/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.query;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.base.ModuleInfo;
import javax.persistence.Transient;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;
import org.jhrcore.mutil.QueryUtil;
import org.jhrcore.rebuild.EntityBuilder;

/**
 *
 * @author Administrator
 *
 * 这是查询分析方案实体类
 */
@Entity
@Table(name = "QueryAnalysisScheme")
@ClassAnnotation(displayName = "通用统计方案表", moduleName = "系统维护")
public class QueryAnalysisScheme extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String queryAnalysisScheme_key;
    private String queryAnalysisScheme_name;
    // 查询分析方案所属模块名称
    private String module_name;
    // 查询分析方案分类
    private String queryAnalysisScheme_type;
    // 查询分析的类，全称，例如："org.jhrcore.entity.BasePerson"
    private String entity_fullname;
    private ModuleInfo moduleInfo;
    private String query_code;
    public transient int new_flag = 0;
    private static Hashtable<String, String> ht_codes = new Hashtable<String, String>();

    @Id
    public String getQueryAnalysisScheme_key() {
        return queryAnalysisScheme_key;
    }

    public void setQueryAnalysisScheme_key(String queryAnalysisScheme_key) {
        String old = this.queryAnalysisScheme_key;
        this.queryAnalysisScheme_key = queryAnalysisScheme_key;
        this.firePropertyChange("queryAnalysisScheme_key", old, queryAnalysisScheme_key);
    }

    public String buildSQLforReport(QueryScheme queryScheme2) {
        // 从查询的实体开始，到包含该属性的实体，所有实体名，用分号分开。如"BasePerson;Dept"
        // 表示该字段与所属实体之间的关系，直到查询实体，用分号分开。如“2；1”，1：普通属性；2：实体属性；3：集合属性
        boolean bhasGroupField = false;
        for (QueryAnalysisField qaf : getQueryAnalysisFields()) {
            if (!qaf.getStat_type().equals("普通")) {
                bhasGroupField = true;
                break;
            }
        }

        ht_codes.clear();

        String fields = "";
        String group_fields = "";
        String order_fields = "";

        String tables = "";
        String joins = "";

        int ind = -1;

        for (QueryAnalysisField qaf : getQueryAnalysisFields()) {
            ind++;
            String field_name = qaf.getEntity_name() + "." + qaf.getField_name();
            if (qaf.getField_name().endsWith("_code_")) {
                if (qaf.getStat_type().equals("普通")) {
                    String fullClassName = EntityBuilder.getHt_entity_classes().get(qaf.getEntity_name());
                    String code_type = PublicUtil.getCode_typeBy(fullClassName, qaf.getField_name());
                    ht_codes.put("" + ind, code_type);
                }
                field_name = qaf.getEntity_name() + "." + qaf.getField_name().substring(0, qaf.getField_name().length() - 6);
            }
            if (!fields.trim().equals("")) {
                fields = fields + ",\n";
            }
            if (qaf.getStat_type().equals("普通")) {
                fields = fields + "      " + field_name + " " + qaf.getField_caption2();//+ qaf.getField_name();
                if (bhasGroupField) {
                    if (!group_fields.equals("")) {
                        group_fields = group_fields + ",";
                    }
                    group_fields = group_fields + field_name;
                }
            } else if (qaf.getStat_type().equals("计数")) {
                fields = fields + "      " + "count(" + field_name + ") " + qaf.getField_caption2();
            } else if (qaf.getStat_type().equals("平均")) {
                fields = fields + "      " + "avg(" + field_name + ") " + qaf.getField_caption2();
            } else if (qaf.getStat_type().equals("求和")) {
                fields = fields + "      " + "sum(" + field_name + ") " + qaf.getField_caption2();
            }

            if (qaf.getOrder_type().equals("不排序")) {
            } else if (qaf.getOrder_type().equals("升序")) {
                if (!order_fields.equals("")) {
                    order_fields = order_fields + ",";
                }
                order_fields = order_fields + field_name;
            } else if (qaf.getOrder_type().equals("降序")) {
                if (!order_fields.equals("")) {
                    order_fields = order_fields + ",";
                }
                order_fields = order_fields + field_name + " desc";
            }

            if (!tables.contains(qaf.getEntity_name())) {
                if (!tables.equals("")) {
                    tables = tables + ",";
                }
                tables = tables + qaf.getEntity_name();
            }

            String[] tmp_entityNames = qaf.getEntityNames().split(";");
            String[] tmp_fieldClasses = qaf.getFieldClasses().split(";");

            for (String entity1 : tmp_entityNames) {
                if (!tables.contains(entity1)) {
                    if (!tables.equals("")) {
                        tables = tables + ",";
                    }
                    tables = tables + entity1;
                }
            }
//            System.out.println("qaf.getEntityNames():" + qaf.getEntityNames());
//            System.out.println("qaf.getFieldClasses():" + qaf.getEntityNames());
            if (tmp_entityNames.length > 1) {
                for (int i = 0; i < (tmp_entityNames.length - 1); i++) {
                    String tmp_entity_1 = tmp_entityNames[i];
                    String tmp_entity_2 = tmp_entityNames[i + 1];
                    //1：普通属性；2：实体属性；3：集合属性
                    if (tmp_fieldClasses[i].equals("1")) {
                    } else if (tmp_fieldClasses[i].equals("2")) {
                        String tmp_join = tmp_entity_1 + "." + EntityBuilder.getEntityKey(tmp_entity_2) + "="
                                + tmp_entity_2 + "." + EntityBuilder.getEntityKey(tmp_entity_2);
                        if (!joins.contains(tmp_join)) {
                            if (!joins.equals("")) {
                                joins = joins + " and ";
                            }
                            joins = joins + tmp_join;
                        }
                    } else if (tmp_fieldClasses[i].equals("3")) {
                        String tmp_join = tmp_entity_1 + "." + EntityBuilder.getEntityKey(tmp_entity_1) + "="
                                + tmp_entity_2 + "." + EntityBuilder.getEntityKey(tmp_entity_1);
                        if (!joins.contains(tmp_join)) {
                            if (!joins.equals("")) {
                                joins = joins + " and ";
                            }
                            joins = joins + tmp_join;
                        }
                    } else if (tmp_fieldClasses[i].equals("5")) {
                        String tmp_join = tmp_entity_1 + "." + EntityBuilder.getEntityKey(tmp_entity_2) + "="
                                + tmp_entity_2 + "." + EntityBuilder.getEntityKey(tmp_entity_2);
                        if (!joins.contains(tmp_join)) {
                            if (!joins.equals("")) {
                                joins = joins + " and ";
                            }
                            joins = joins + tmp_join;
                        }
                    }
                }
            }
        }

//        System.out.println("tables1:" + tables);
//        System.out.println("joins:" + joins);

        QueryScheme queryScheme = queryScheme2;
        String tmp_condition_expression = "";
        if (queryScheme != null) {
            String condition_expression = queryScheme.getCondition_expression();
            if (condition_expression == null || condition_expression.equals("")) {
                condition_expression = "";
                for (int m = 1; m <= queryScheme.getConditions().size(); m++) {
                    if (m != 1) {
                        condition_expression = condition_expression + "+";
                    }
                    condition_expression = condition_expression + m;
                }
            }

            tmp_condition_expression = condition_expression;
            tmp_condition_expression = tmp_condition_expression.replaceAll("\\+", " and ");
            tmp_condition_expression = tmp_condition_expression.replaceAll(",", " or ");
            tmp_condition_expression = QueryUtil.tranExpStr(tmp_condition_expression, queryScheme.getConditions().size());
            int i = 0;
            for (Condition condition : queryScheme.getConditions()) {
                i++;
//                System.out.println("condition.getEntityNames()2:" + condition.getEntityNames());
//                System.out.println("condition.getFieldClasses()2:" + condition.getFieldClasses());
//                System.out.println("condition.getFieldClasses()2:" + condition.displayName);
//                System.out.println("condition.getFieldClasses()2:" + condition.fieldName);
                String[] tmp_entityNames = condition.getEntityNames().split(";");
                String[] tmp_fieldClasses = condition.getFieldClasses().split(";");

                for (String entity1 : tmp_entityNames) {
                    if (!tables.contains(entity1)) {
                        if (!tables.equals("")) {
                            tables = tables + ",";
                        }
                        tables = tables + entity1;
                    }
                }

                for (int i2 = 0; i2 < tmp_fieldClasses.length - 1; i2++) {
                    String entity1 = tmp_entityNames[i2];
                    String entity2 = tmp_entityNames[i2 + 1];
                    if (entity1.equals("Pay") || entity2.equals("Pay") || entity1.equals("In_account") || entity2.equals("In_account")) {
                        continue;
                    }

                    String fieldClass = tmp_fieldClasses[i2];
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

                String tmp_con =
                        condition.getOperator().replace("%", "");// + tmp_fieldValue;
                String tmp_2 = "";
                boolean is_code_ = condition.getFieldName().contains("_code_");
                String tmp_field = condition.getFieldName().replaceAll("_code_", "");
                String param_name = condition.getDisplayName().substring(condition.getDisplayName().indexOf(".") + 1);
                String tmp_param_name = param_name;
                int tmp_i = 1;
                while (tmp_condition_expression.contains("[?" + tmp_param_name + "?]")) {
                    tmp_param_name = param_name + tmp_i++;
                }
                param_name = tmp_param_name;
                String fieldvalue = condition.getFieldValue();
                if (fieldvalue != null && fieldvalue.startsWith("'") && fieldvalue.endsWith("'")) {
                    fieldvalue = fieldvalue.substring(1, fieldvalue.length() - 1);
                }
                if (is_code_) {
                    String code_type = PublicUtil.getCode_typeBy(condition.getEntityFullName(), condition.getFieldName());

                    if (condition.getPara() == 0) {
                        tmp_2 = condition.getEntityName() + "." + tmp_field + " in ( select code_id from Code where code_type = '" + code_type + "'"
                                + " and (code_id " + tmp_con + " '" + condition.getOperator().replace(tmp_con, fieldvalue) + "'"
                                + " or code_name " + tmp_con + " '" + condition.getOperator().replace(tmp_con, fieldvalue) + "'"
                                + "))";
                    } else {
                        tmp_2 = condition.getEntityName() + "." + tmp_field + " in ( select code_id from Code where code_type = '" + code_type + "'"
                                + " and (code_id " + condition.getOperator() + " [?" + param_name + "?]"
                                + " or code_name " + condition.getOperator() + " [?" + param_name + "?]"
                                + "))";
                    }
                } else {
                    if (condition.getPara() == 0) {
                        tmp_2 = condition.getEntityName() + "." + tmp_field + " " + tmp_con + " '" + condition.getOperator().replace(tmp_con, fieldvalue) + "'";
                    } else {
                        tmp_2 = condition.getEntityName() + "." + tmp_field + " " + condition.getOperator() + " [?" + param_name + "?]";
                    }
                }
                tmp_condition_expression = tmp_condition_expression.replace(QueryUtil.getTempStr(i), tmp_2);
            }

        }
//        System.out.println("tables2:" + tables);
//        System.out.println("joins2:" + joins);
        String sql = "select \n" + fields + "\n  from " + tables + "\n";

        if (!tmp_condition_expression.equals("")) {
            if (!joins.equals("")) {
                joins = joins + "\n   and ";
            }
            joins = joins + " (" + tmp_condition_expression + ")";
        }


        if (!joins.equals("")) {
            sql = sql + " where " + joins;
        }


        if (!group_fields.equals("")) {
            sql = sql + " group by " + group_fields;
        }
        if (!order_fields.equals("")) {
            sql = sql + " order by " + order_fields;
        }
        // System.out.println("PPPPPPPPPPPPPPPPLLLO:" + sql);
        return sql;
    }

    public String getEntity_fullname() {
        return entity_fullname;
    }

    public void setEntity_fullname(String entityName) {
        String old = this.entity_fullname;
        this.entity_fullname = entityName;
        this.firePropertyChange("entity_fullname", old, entityName);
    }
    public List<QueryAnalysisField> queryAnalysisFields = new ArrayList<QueryAnalysisField>();

    @Transient
    public String[] getFieldCaptions() {
        List<String> list = new ArrayList<String>();
        for (QueryAnalysisField qaf : queryAnalysisFields) {
            String caption = qaf.getField_caption() == null ? "" : qaf.getField_caption();
            if (!qaf.getStat_type().equals("普通")) {
                caption = caption + "(" + qaf.getStat_type() + ")";
            }
            list.add(caption);
        }
        String[] tmp = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            tmp[i] = list.get(i);
        }
        return tmp;
    }

    @Override
    public String toString() {
        return queryAnalysisScheme_name;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        String old = this.module_name;
        this.module_name = module_name;
        this.firePropertyChange("module_name", old, module_name);
    }

    public String getQueryAnalysisScheme_type() {
        return queryAnalysisScheme_type;
    }

    public void setQueryAnalysisScheme_type(String queryAnalysisScheme_type) {
        String old = this.queryAnalysisScheme_type;
        this.queryAnalysisScheme_type = queryAnalysisScheme_type;
        this.firePropertyChange("queryAnalysisScheme_type", old, queryAnalysisScheme_type);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "queryAnalysisScheme", fetch = FetchType.LAZY)
    @OrderBy("order_no")
    public List<QueryAnalysisField> getQueryAnalysisFields() {
        return queryAnalysisFields;
    }

    public void setQueryAnalysisFields(List<QueryAnalysisField> queryField3s) {
        this.queryAnalysisFields = queryField3s;
    }

    public String getQueryAnalysisScheme_name() {
        return queryAnalysisScheme_name;
    }

    public void setQueryAnalysisScheme_name(String queryAnalysisScheme_name) {
        String old = this.queryAnalysisScheme_name;
        this.queryAnalysisScheme_name = queryAnalysisScheme_name;
        this.firePropertyChange("queryAnalysisScheme_name", old, queryAnalysisScheme_name);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_key")
    public ModuleInfo getModuleInfo() {
        return moduleInfo;
    }

    public void setModuleInfo(ModuleInfo moduleInfo) {
        this.moduleInfo = moduleInfo;
    }

    public String getQuery_code() {
        return query_code;
    }

    public void setQuery_code(String query_code) {
        String old = this.query_code;
        this.query_code = query_code;
        this.firePropertyChange("query_code", old, query_code);
    }

    @Override
    public void assignEntityKey(String key) {
        this.new_flag = 1;
        this.queryAnalysisScheme_key = key;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QueryAnalysisScheme other = (QueryAnalysisScheme) obj;
        if ((this.queryAnalysisScheme_key == null) ? (other.queryAnalysisScheme_key != null) : !this.queryAnalysisScheme_key.equals(other.queryAnalysisScheme_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.queryAnalysisScheme_key != null ? this.queryAnalysisScheme_key.hashCode() : 0);
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

    public static Hashtable<String, String> getHt_codes() {
        return ht_codes;
    }

    public static void setHt_codes(Hashtable<String, String> ht_codes) {
        QueryAnalysisScheme.ht_codes = ht_codes;
    }
}
