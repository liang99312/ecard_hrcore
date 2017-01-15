/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.query;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;
import org.jhrcore.entity.annotation.UILayout;

import com.jgoodies.binding.beans.Model;
import javax.persistence.FetchType;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.rebuild.EntityBuilder;
import javax.persistence.Transient;
import org.jhrcore.util.DateUtil;
import org.jhrcore.comm.CodeManager;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.base.TempFieldInfo;

/**
 *
 * @author Administrator
 */
@Entity
@UILayout(colNum = 4, colWidth = "30dlu")
@ClassAnnotation(displayName = "通用查询条件表", moduleName = "系统维护")
public class Condition extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String condition_key;
    //0:无参；1：单个参数；2：多选参数
    @FieldAnnotation(visible = true, displayName = "参数", groupName = "Default")
    public int para = 0;
    // 字段名
    @FieldAnnotation(visible = false, displayName = "fieldName", groupName = "Default")
    public String fieldName;
    // 字段名含义
    @FieldAnnotation(visible = true, displayName = "名", isEditable = false, groupName = "Default")
    public String displayName;
    @FieldAnnotation(visible = true, displayName = "字段类型", isEditable = false, groupName = "Default")
    public String fieldType;
    // 比较操作符
    @FieldAnnotation(visible = true, displayName = " ", groupName = "Default")
    @EnumHint(enumList = "=;<>;>;<;>=;<=")
    public String operator = "";
    @FieldAnnotation(visible = false)
    private QueryScheme queryScheme;
    //字段匹配值
    @FieldAnnotation(visible = false)
    public String fieldValue;
    @FieldAnnotation(visible = false)
    public String displayValue;
    //与条件表达式对应序号
    public int order_no = 1;
    // 该条件所属于的表名，在该条件为子集条件的时候使用
    public String entityName;
    // 该条件所属于的表名，全称
    public String entityFullName;
    // 从查询的实体开始，到包含该属性的实体，所有实体名，用分号分开。如"A01;Dept"
    public String entityNames;
    // 表示该字段与所属实体之间的关系，直到查询实体，用分号分开。如“2；1”，1：普通属性；2：实体属性；3：集合属性
    public String fieldClasses;
    public String bindName;
    //由于日期要做特殊处理,所以增加字段的临时保存值
    public transient Object tmp_value;
    public transient int new_flag = 0;
    private transient boolean temp_flag = false;

    @Id
    public String getCondition_key() {
        return condition_key;
    }

    public void setCondition_key(String condition_key) {
        String old = this.condition_key;
        this.condition_key = condition_key;
        this.firePropertyChange("condition_key", old, condition_key);
    }

    @Transient
    public boolean isTemp_flag() {
        return temp_flag;
    }

    public void setTemp_flag(boolean temp_flag) {
        this.temp_flag = temp_flag;
    }

    @Transient
    public String getCode_type() {
        return PublicUtil.getCode_typeBy(entityFullName, fieldName);
    }

    @Transient
    public Object getTmp_value() {
        return tmp_value;
    }

    public void setTmp_value(Object tmp_value) {
        this.tmp_value = tmp_value;
    }

    public String getBindName() {
        return bindName;
    }

    public void setBindName(String bindName) {
        String old = this.bindName;
        this.bindName = bindName;
        this.firePropertyChange("bindName", old, bindName);
    }

    public String getEntityFullName() {
        return entityFullName;
    }

    public void setEntityFullName(String entityFullName) {
        String old = this.entityFullName;
        this.entityFullName = entityFullName;
        this.firePropertyChange("entityFullName", old, entityFullName);
    }

    // 生成不采用in的条件
    public String buildDirectHqlCondition(String entity_name) {
        String tmp_fieldValue = this.fieldValue;
        String tmp_fieldName = this.fieldName.replace("_code_", "");
        if (this.getFieldName().contains("_code_")) {// && !this.operator.contains("like")) {
            Object obj = CodeManager.getCodeManager().getCodeIdBy(getCode_type(), tmp_fieldValue.replace("'", ""));
            if (obj == null) {
                obj = tmp_fieldValue;
            }
            tmp_fieldValue = (obj == null) ? "" : obj.toString();
            if (!tmp_fieldValue.contains("'")) {
                tmp_fieldValue = "'" + tmp_fieldValue + "'";
            }
        }
        if (tmp_fieldValue != null) {
            tmp_fieldValue = tmp_fieldValue.replaceAll("or " + tmp_fieldName, "or " + entity_name + "." + this.bindName.replace("_code_", ""));// + "." + tmp_fieldName);
        }
        if (this.operator.contains("like") && !tmp_fieldValue.contains("like")) {
            tmp_fieldValue = "'" + this.operator.replace(this.operator.replaceAll("%", ""), tmp_fieldValue.replaceAll("'", "")) + "'";
        }
        String tmp_con = " " + this.operator.replaceAll("%", "") + " " + tmp_fieldValue;

        if (this.getFieldName().contains("_code_") && this.operator.contains("like")) {
            String val = this.operator.contains("not") ? " and " : " or ";
            tmp_con = " in (select code_id from Code c where code_type = '" + getCode_type() + "' and (c.code_id" + tmp_con + val + "c.code_name" + tmp_con + "))";
        }
        String[] entitis = entityNames.split(";");
        for (int i = 1; i < entitis.length; i++) {
            entity_name = entity_name + "." + entitis[i].substring(0, 1).toLowerCase() + entitis[i].substring(1);
        }
        String tmp_2 = entity_name + "." + tmp_fieldName;

        TempFieldInfo tfi = EntityBuilder.getTempFieldInfoByName(this.entityName, tmp_fieldName, false);
        String field_type = tfi.getField_type().toLowerCase();
        if (field_type.equalsIgnoreCase("date")) {
            String format = tfi == null ? "yyyy-MM-dd" : tfi.getFormat();
            format = format == null ? "yyyy-MM-dd" : format;
            // 考虑字段包含时间的情况
            tmp_con = " ";//+ this.operator.replaceAll("%", "");//+ " " + DateUtil.toStringForQuery((Date) tmp_value);
            tmp_2 = DateUtil.getDateSQL(tmp_2, this.operator.replaceAll("%", ""), tmp_fieldValue, format);
//            if (UserContext.sql_dialect.equals("sqlserver")) {
//                String value = format;
//                value = value.replace("yyyy", "substring("+tmp_2+",1,4)").replace("MM", "substring("+tmp_2+",6,7)").replace("dd", "substring("+tmp_2+",9,10)").replace("HH", "substring("+tmp_2+",12,13)");
//                if (tmp_con.equals("=")) {
//                    tmp_con += ">=" + DateUtil.toStringForQuery((Date) tmp_value, format);
//                } else {
//                    tmp_con += " " + DateUtil.toStringForQuery((Date) tmp_value, format);
//                }
//            } else {
//                tmp_con += "'" + DateUtil.DateToStr((Date) tmp_value, format) + "'";
//                tmp_2 = "to_char(" + tmp_2 + ",'" + format.replace("hh", "HH").replace("HH", "HH24").replace("mm", "MI") + "')";
//            }
//            tmp_con =
//                    " " + this.operator.replaceAll("%", "") + " " + DateUtil.toStringForQuery((Date) tmp_value);
//            // 考虑字段包含时间的情况
//            if (operator.equals("=")) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime((Date) tmp_value);
//                calendar.add(Calendar.DATE, 1);
//                tmp_con =
//                        " >= " + DateUtil.toStringForQuery((Date) tmp_value) + " and " + entity_name + "." + tmp_fieldName + "<" + DateUtil.toStringForQuery(calendar.getTime()) + " ";
//            } else if (operator.equals("<=")) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime((Date) tmp_value);
//                calendar.add(Calendar.DATE, 1);
//                tmp_con = " <" + DateUtil.toStringForQuery(calendar.getTime()) + " ";
//            }
        }
        if (this.operator.contains("null")) {
            String oper = "=";
            String hql = "";
            if (this.operator.contains("not")) {
                oper = "<>";
            }
            if (field_type.equals("string")) {
                if (org.jhrcore.client.UserContext.sql_dialect.equals("sqlserver")) {
                    hql = "isnull(replace(" + tmp_2 + ",' ',''),'')" + oper + "''";
                } else {
                    hql = "nvl(replace(replace(" + tmp_2 + ",' ',''),'@','#'),'@')" + oper + "'@'";
                }
            }
            return hql;
        }
//        String hql = entity_name + "." + tmp_fieldName + tmp_con + " ";
        String hql = tmp_2 + tmp_con + " ";
        return hql;
    }

    @Transient
    public String getWhereCondition2() {
        String alias_name = "e" + order_no;
        String hql = "";
        String lastEntityName = "";
        String[] tmp_entityNames = entityNames.split(";");
        String[] tmp_fieldClasses = fieldClasses.split(";");
        String tmp_fieldName = this.fieldName.replace("_code_", "");
//        int query_type = 0;
//        if (this.operator.toLowerCase().equals("not like%") || this.operator.toLowerCase().equals("like%") || this.operator.toLowerCase().equals("%like") || this.operator.toLowerCase().equals("%like%")) {
//            query_type = 1;
//        } else if (this.operator.toUpperCase().equals("NULL") || this.operator.toUpperCase().equals("NOT NULL")) {
//            query_type = 2;
//        } else if (this.operator.toUpperCase().equals("MAX") || this.operator.toUpperCase().equals("MIN")) {
//            query_type = 3;
//        }
        for (int i = tmp_fieldClasses.length - 1; i >= 0; i--) {
            String tmp_entityName = tmp_entityNames[i];
            String tmp_fieldClass = tmp_fieldClasses[i];
            String tmp_alias_name = alias_name + "_" + i;
            String tmp_fieldValue = this.fieldValue;
            if (this.getFieldName().contains("_code_")) {//&& !this.getOperator().contains("like")) {
                Object obj = CodeManager.getCodeManager().getCodeIdBy(getCode_type(), tmp_fieldValue.replace("'", ""));
                if (obj == null) {
                    obj = tmp_fieldValue;
                }
                tmp_fieldValue = (obj == null) ? "" : obj.toString();
                if (!tmp_fieldValue.contains("'")) {
                    tmp_fieldValue = "'" + tmp_fieldValue + "'";
                }
            }
            if (this.operator.contains("like")) {
                tmp_fieldValue = "'" + this.operator.replace(this.operator.replaceAll("%", ""), tmp_fieldValue.replaceAll("'", "")) + "'";
            }
            String tmp_con =
                    " " + this.operator.replaceAll("%", "") + " " + tmp_fieldValue;
            if (this.getFieldName().contains("_code_") && this.operator.contains("like")) {
                tmp_con = " in (select code_id from Code c where code_type = '" + getCode_type() + "' and (c.code_id" + tmp_con + " or c.code_name" + tmp_con + "))";
            }
            if (tmp_fieldClass.equals("1")) {
                TempFieldInfo tfi = EntityBuilder.getTempFieldInfoByName(this.entityName, tmp_fieldName, false);
                if (tfi.getField_type().equalsIgnoreCase("date")) {
                    String format = tfi == null ? "yyyy-MM-dd" : tfi.getFormat();
                    format = format == null ? "yyyy-MM-dd" : format;
                    tmp_con = DateUtil.getDateSQL(tmp_fieldName, this.operator.replaceAll("%", ""), tmp_fieldValue, format);
                    // 考虑字段包含时间的情况
                    tmp_con = " ";//+ this.operator.replaceAll("%", "");//+ " " + DateUtil.toStringForQuery((Date) tmp_value);
                    tmp_fieldName = " ";
//                    if (UserContext.sql_dialect.equals("sqlserver") || format.contains("y")) {
//                        tmp_con += " " + DateUtil.toStringForQuery((Date) tmp_value, format);
//                    } else {
//                        tmp_con += "'" + DateUtil.DateToStr((Date) tmp_value, format) + "'";
//                        tmp_fieldName = "to_char(" + tmp_fieldName + ",'" + format.replace("hh", "HH").replace("HH", "HH24").replace("mm", "MI") + "')";
//                    }
//                    // 考虑字段包含时间的情况
//                    tmp_con =
//                            " " + this.operator.replaceAll("%", "") + " " + DateUtil.toStringForQuery((Date) tmp_value);
//                    if (operator.equals("=")) {
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime((Date) tmp_value);
//                        calendar.add(Calendar.DATE, 1);
//                        tmp_con =
//                                " " + ">" + " " + DateUtil.toStringForQuery((Date) tmp_value) + " and " + tmp_fieldName + "<" + DateUtil.toStringForQuery(calendar.getTime()) + " ";
//                    } else if (operator.equals("<=")) {
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime((Date) tmp_value);
//                        calendar.add(Calendar.DATE, 1);
//                        tmp_con = " <" + DateUtil.toStringForQuery(calendar.getTime()) + " ";
//                    }
                }
                hql = " select " + tmp_alias_name + " from " + tmp_entityName + " " + tmp_alias_name + " where " + tmp_fieldName + tmp_con + " ";
            } else if (tmp_fieldClass.equals("2")) {
                if (hql.equals("")) {
                    String key_field = tmp_fieldName + "_key";
                    hql = " select " + tmp_alias_name + " from " + tmp_entityName + " " + tmp_alias_name + " where " + tmp_fieldName + "." + key_field + tmp_con + " ";
                } else {
                    String ent_field = getEntityField(lastEntityName);
                    hql = " select " + tmp_alias_name + " from " + tmp_entityName + " " + tmp_alias_name + " where " + ent_field + " in (" + hql + ")";
                }
            } else if (tmp_fieldClass.equals("3")) {
                String ent_field = getEntityField(tmp_entityName);
                hql = " select " + ent_field + " from " + lastEntityName + " " + tmp_alias_name + " where " + tmp_alias_name + " in (" + hql + ")";
            } else if (tmp_fieldClass.equals("4")) {
                // A01;C21
                String key_field = getEntityKey(tmp_entityName);
                hql = " select " + tmp_alias_name + " from " + tmp_entityName + " " + tmp_alias_name + " where " + tmp_alias_name + "." + key_field + " in (" + "select " + tmp_alias_name + "_1." + key_field + " from " + lastEntityName + " " + tmp_alias_name + "_1" + " where " + tmp_alias_name + "_1" + " in (" + hql + "))";
            } else if (tmp_fieldClass.equals("5")) {
                // C21;A01
                String key_field = getEntityKey(lastEntityName);
                hql = " select " + tmp_alias_name + " from " + tmp_entityName + " " + tmp_alias_name + " where " + tmp_alias_name + "." + key_field + " in (" + "select " + tmp_alias_name + "_1." + key_field + " from " + lastEntityName + " " + tmp_alias_name + "_1" + " where " + tmp_alias_name + "_1" + " in (" + hql + "))";
            }
            lastEntityName = tmp_entityName;
        }
        return hql;
    }

    public static String getEntityField(String EntityClass) {
        String ent_field = EntityClass.substring(0, 1).toLowerCase() + EntityClass.substring(1);
        return ent_field;
    }

    public static String getEntityKey(String EntityClass) {
        String fullEntityClass = EntityBuilder.getHt_entity_classes().get(EntityClass);
        try {
            Class aClass = Class.forName(fullEntityClass);
            while (aClass.getSuperclass().getName().contains("entity")) {
                aClass = aClass.getSuperclass();
            }
            EntityClass = aClass.getSimpleName();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Condition.class.getName()).log(Level.SEVERE, null, ex);
        }

        String ent_field = EntityClass.substring(0, 1).toLowerCase() + EntityClass.substring(1);
        return ent_field + "_key";
    }

//    @Transient
//    public String getWhereCondition() {
//        String alias_name = "e" + order_no;
//        String hql = "";
//        String lastEntityName = "";
//        String[] tmp_entityNames = entityNames.split(";");
//        String[] tmp_fieldClasses = fieldClasses.split(";");
//        String tmp_fieldName = this.fieldName.replace("_code_", "");
//        int query_type = 0;
//        if (this.operator.toLowerCase().equals("not like%") || this.operator.toLowerCase().equals("like%") || this.operator.toLowerCase().equals("%like") || this.operator.toLowerCase().equals("%like%")) {
//            query_type = 1;
//        } else if (this.operator.toUpperCase().equals("NULL") || this.operator.toUpperCase().equals("NOT NULL")) {
//            query_type = 2;
//        } else if (this.operator.toUpperCase().equals("MAX") || this.operator.toUpperCase().equals("MIN")) {
//            query_type = 3;
//        }
//        for (int i = tmp_fieldClasses.length - 1; i >= 0; i--) {
//            String tmp_entityName = tmp_entityNames[i];
//            String tmp_fieldClass = tmp_fieldClasses[i];
//            String tmp_alias_name = alias_name + "_" + i;
//            String tmp_fieldValue = this.fieldValue;
//
//            if (this.operator.contains("like")) {
//                tmp_fieldValue = "'" + this.operator.replace(this.operator.replaceAll("%", ""), tmp_fieldValue.replaceAll("'", "")) + "'";
//            }
//            String tmp_con =
//                    " " + this.operator.replaceAll("%", "") + " " + tmp_fieldValue;
//
//            if (this.getFieldName().contains("_code_") /*&& this.getOperator().contains("like")*/) {
//                tmp_con = " in (select code_id from Code c where code_type = '" + getCode_type() + "' and (c.code_name" + tmp_con + "))";
//            }
//            if (tmp_fieldClass.equals("1")) {
//                if (tmp_value != null && (tmp_value instanceof Date)) {
//                    // 考虑字段包含时间的情况
//                    tmp_con =
//                            " " + this.operator.replaceAll("%", "") + " " + DateUtil.toStringForQuery((Date) tmp_value);
//                    if (operator.equals("=")) {
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime((Date) tmp_value);
//                        calendar.add(Calendar.DATE, 1);
//                        tmp_con =
//                                " " + ">" + " " + DateUtil.toStringForQuery((Date) tmp_value) + " and " + tmp_fieldName + "<" + DateUtil.toStringForQuery(calendar.getTime()) + " ";
//                    } else if (operator.equals("<=")) {
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime((Date) tmp_value);
//                        calendar.add(Calendar.DATE, 1);
//                        tmp_con = " <" + DateUtil.toStringForQuery(calendar.getTime()) + " ";
//                    }
//                }
//                hql = " select " + tmp_alias_name + " from " + tmp_entityName + " " + tmp_alias_name + " where " + tmp_fieldName + tmp_con + " ";
//            } else if (tmp_fieldClass.equals("2")) {
//                if (hql.equals("")) {
//                    String key_field = tmp_fieldName + "_key";
//                    hql = " select " + tmp_alias_name + " from " + tmp_entityName + " " + tmp_alias_name + " where " + tmp_fieldName + "." + key_field + tmp_con + " ";
//                } else {
//                    String ent_field = getEntityField(lastEntityName);
//                    hql = " select " + tmp_alias_name + " from " + tmp_entityName + " " + tmp_alias_name + " where " + ent_field + " in (" + hql + ")";
//                }
//            } else if (tmp_fieldClass.equals("3")) {
//                String ent_field = getEntityField(tmp_entityName);
//                hql = " select " + ent_field + " from " + lastEntityName + " " + tmp_alias_name + " where " + tmp_alias_name + " in (" + hql + ")";
//            } else if (tmp_fieldClass.equals("4")) {
//                // A01;C21
//                String key_field = getEntityKey(tmp_entityName);
//                hql = " select " + tmp_alias_name + " from " + tmp_entityName + " " + tmp_alias_name + " where " + tmp_alias_name + "." + key_field + " in (" + "select " + tmp_alias_name + "_1." + key_field + " from " + lastEntityName + " " + tmp_alias_name + "_1" + " where " + tmp_alias_name + "_1" + " in (" + hql + "))";
//            } else if (tmp_fieldClass.equals("5")) {
//                // C21;A01
//                String key_field = getEntityKey(lastEntityName);
//                hql = " select " + tmp_alias_name + " from " + tmp_entityName + " " + tmp_alias_name + " where " + tmp_alias_name + "." + key_field + " in (" + "select " + tmp_alias_name + "_1." + key_field + " from " + lastEntityName + " " + tmp_alias_name + "_1" + " where " + tmp_alias_name + "_1" + " in (" + hql + "))";
//            }
//            lastEntityName = tmp_entityName;
//        }
//        return hql;
//    }
//    @Transient
//    public String getSQLWhereCondition() {
//        String alias_name = "e" + order_no;
//        String hql = "";
//        String lastEntityName = "";
//        String[] tmp_entityNames = entityNames.split(";");
//        String[] tmp_fieldClasses = fieldClasses.split(";");
//        String tmp_fieldName = this.fieldName.replace("_code_", "");
//        for (int i = tmp_fieldClasses.length - 1; i >= 0; i--) {
//            String tmp_entityName = tmp_entityNames[i];
//            String tmp_fieldClass = tmp_fieldClasses[i];
//            String tmp_alias_name = alias_name + "_" + i;
//            String ent_field = getEntityField(tmp_entityName);
//            String key_field = ent_field + "_key";
//            String tmp_fieldValue = this.fieldValue;
//            if (this.operator.contains("like")) {
//                tmp_fieldValue = "'" + this.operator.replace(this.operator.replaceAll("%", ""), tmp_fieldValue.replaceAll("'", "")) + "'";
//            }
//            String tmp_con =
//                    " " + this.operator.replaceAll("%", "") + " " + tmp_fieldValue;
//            if (this.getFieldName().contains("_code_")/* && this.getOperator().contains("like")*/) {
//                tmp_con = " in (select code_id from Code c where code_type = '" + getCode_type() + "' and (c.code_id" + tmp_con + " or c.code_name" + tmp_con + "))";
//            }
//            if (tmp_fieldClass.equals("1")) {
//                hql = " select " + key_field + " from " + tmp_entityName + " " + tmp_alias_name + " where " + tmp_fieldName + tmp_con + " ";
//            } else if (tmp_fieldClass.equals("2")) {
//                if (hql.equals("")) {
//                    String key_field2 = tmp_fieldName + "_key";
//                    hql = " select " + key_field + " from " + tmp_entityName + " " + tmp_alias_name + " where " + key_field2 + tmp_con + " ";
//                } else {
//                    String key_field2 = getEntityField(lastEntityName) + "_key";
//                    hql = " select " + key_field + " from " + tmp_entityName + " " + tmp_alias_name + " where " + key_field2 + " in (" + hql + ")";
//                }
//            } else if (tmp_fieldClass.equals("3")) {
//                String key_field2 = getEntityField(lastEntityName) + "_key";
//                hql = " select " + key_field + " from " + lastEntityName + " " + tmp_alias_name + " where " + key_field2 + " in (" + hql + ")";
//            } else if (tmp_fieldClass.equals("4")) {
//                // A01;C21
//                String key_field0 = getEntityKey(tmp_entityName);
//                hql = " select " + tmp_alias_name + "." + key_field0 + " from " + lastEntityName + " " + tmp_alias_name + " where " + tmp_alias_name + "." + getEntityKey(lastEntityName) + " in (" + hql + ")";
//            } else if (tmp_fieldClass.equals("5")) {
//                // C21;A01
//                String key_field0 = getEntityKey(tmp_entityName);
//                String key_field2 = getEntityKey(lastEntityName);
//                hql = " select " + tmp_alias_name + "." + key_field0 + " from " + tmp_entityName + " " + tmp_alias_name + " where " + tmp_alias_name + "." + key_field2 + " in (" + hql + "))";
//            }
//            lastEntityName = tmp_entityName;
//        }
//        return hql;
//    }
//    @Transient
//    public String getSQLWhereCondition3() {
//        if (this.bindName.trim().equals("高级条件")) {
//            return this.fieldValue;
//        }
//        String alias_name = "e" + order_no;
//        String hql = "";
//        String lastEntityName = "";
//        String[] tmp_entityNames = entityNames.split(";");
//        String[] tmp_fieldClasses = fieldClasses.split(";");
//        String tmp_fieldName = this.fieldName.replace("_code_", "");
//        for (int i = tmp_fieldClasses.length - 1; i >= 0; i--) {
//            String tmp_entityName = tmp_entityNames[i];
//            String tmp_fieldClass = tmp_fieldClasses[i];
//            String tmp_alias_name = alias_name + "_" + i;
//            String ent_field = getEntityField(tmp_entityName);
//            String key_field = ent_field + "_key";
//            String tmp_fieldValue = this.fieldValue;
//            if (this.getFieldName().contains("_code_")/* && this.getOperator().contains("like")*/) {
//                Object obj = CodeManager.getCodeManager().getCodeIdBy(getCode_type(), tmp_fieldValue.replace("'", ""));
//                if (obj == null) {
//                    obj = tmp_fieldValue;
//                }
//                tmp_fieldValue = (obj == null) ? "" : obj.toString();
//                if (!tmp_fieldValue.contains("'")) {
//                    tmp_fieldValue = "'" + tmp_fieldValue + "'";
//                }
//            }
//            if (this.operator.contains("like")) {
//                tmp_fieldValue = tmp_fieldValue.replace("%", "");
//                tmp_fieldValue = "'" + this.operator.replace(this.operator.replace("%", ""), tmp_fieldValue.replace("'", "")) + "'";
//            }
//            String tmp_con = " " + this.operator.replace("%", "") + " " + tmp_fieldValue;
//            if (tmp_fieldClass.equals("1")) {
//                hql = " select " + key_field + " from " + tmp_entityName + " " + tmp_alias_name + " where " + tmp_fieldName + tmp_con + " ";
//            } else if (tmp_fieldClass.equals("2")) {
//                if (hql.equals("")) {
//                    String key_field2 = tmp_fieldName + "_key";
//                    hql = " select " + key_field + " from " + tmp_entityName + " " + tmp_alias_name + " where " + key_field2 + tmp_con + " ";
//                } else {
//                    String key_field2 = getEntityField(lastEntityName) + "_key";
//                    hql = " select " + key_field + " from " + tmp_entityName + " " + tmp_alias_name + " where " + key_field2 + " in (" + hql + ")";
//                }
//            } else if (tmp_fieldClass.equals("3")) {
//                String key_field2 = getEntityField(lastEntityName) + "_key";
//                hql = " select " + key_field + " from " + lastEntityName + " " + tmp_alias_name + " where " + key_field2 + " in (" + hql + ")";
//            } else if (tmp_fieldClass.equals("4")) {
//                // A01;C21
//                String key_field0 = getEntityKey(tmp_entityName);
//                hql = " select " + tmp_alias_name + "." + key_field0 + " from " + lastEntityName + " " + tmp_alias_name + " where " + tmp_alias_name + "." + getEntityKey(lastEntityName) + " in (" + hql + ")";
//            } else if (tmp_fieldClass.equals("5")) {
//                // C21;A01
//                String key_field0 = getEntityKey(tmp_entityName);
//                String key_field2 = getEntityKey(lastEntityName);
//                hql = " select " + tmp_alias_name + "." + key_field0 + " from " + tmp_entityName + " " + tmp_alias_name + " where " + tmp_alias_name + "." + key_field2 + " in (" + hql + "))";
//            }
//            lastEntityName = tmp_entityName;
//        }
//        return hql;
//    }

    public static void main(String[] args) {
        // 人员    合同    意外保险   保险项目
        //Person;Contract;Emergency;Item
        //  3;      3        2       1
        Condition con = new Condition();
        con.setFieldName("item_code");
        con.setOperator("=");
        con.setFieldValue("'123'");
        con.setEntityNames("Person;Contract;Emergency;Item;C21");
        con.setFieldClasses("3;3;2;4;1");
    }

    public String getFieldClasses() {
        return fieldClasses;
    }

    public void setFieldClasses(String fieldClasses) {
        String old = this.fieldClasses;
        this.fieldClasses = fieldClasses;
        this.firePropertyChange("fieldClasses", old, fieldClasses);
    }

    public String getEntityNames() {
        return entityNames;
    }

    public void setEntityNames(String entityNames) {
        String old = this.entityNames;
        this.entityNames = entityNames;
        this.firePropertyChange("entityNames", old, entityNames);
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        String old = this.entityName;
        this.entityName = entityName;
        this.firePropertyChange("entityName", old, entityName);
    }

    @Override
    public String toString() {
        return "" + order_no + ":" + displayName + " " + operator + " " + (displayValue == null ? "" : displayValue);
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        String old = this.displayValue;
        this.displayValue = displayValue;
        this.firePropertyChange("displayValue", old, displayValue);
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        String old = this.fieldName;
        this.fieldName = fieldName;
        this.firePropertyChange("fieldName", old, fieldName);
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        String old = this.operator;
        this.operator = operator;
        this.firePropertyChange("operator", old, operator);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "queryScheme_key")
    public QueryScheme getQueryScheme() {
        return queryScheme;
    }

    public void setQueryScheme(QueryScheme queryScheme) {
        this.queryScheme = queryScheme;
    }

    public int getPara() {
        return para;
    }

    public void setPara(int para) {
        int old = this.para;
        this.para = para;
        this.firePropertyChange("para", old, para);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        String old = this.displayName;
        this.displayName = displayName;
        this.firePropertyChange("displayName", old, displayName);
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String value) {
        String old = this.fieldValue;
        this.fieldValue = value;
        this.firePropertyChange("fieldValue", old, value);
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        int old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        String old = this.fieldType;
        this.fieldType = fieldType;
        this.firePropertyChange("fieldType", old, fieldType);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Condition other = (Condition) obj;
        if ((this.condition_key == null) ? (other.condition_key != null) : !this.condition_key.equals(other.condition_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.condition_key != null ? this.condition_key.hashCode() : 0);
        return hash;
    }

    @Override
    public void assignEntityKey(String key) {
        this.condition_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
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
}
