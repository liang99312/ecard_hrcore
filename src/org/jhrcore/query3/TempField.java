/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.query3;

/**
 *
 * @author Administrator
 */
public class TempField {
    // 该字段所属的实体类名，全名
    private Class entityClass;

    private String field_name;

    private String display_name;

    // 用于绑定到编辑器的名称
    private String bindName;

    // 用于绑定到编辑器的中文名称
    private String displayBindName;

    // 从查询的实体开始，到包含该属性的实体，所有实体名，用分号分开。如"BasePerson;Dept;"
    private String entityNames;

    // 表示该字段与所属实体之间的关系，直到查询实体，用分号分开。如“2；1”，1：普通属性；2：实体属性；3：集合属性
    private String fieldClasses;
    private String fieldType;
    private String format;
    public String getEntityNames() {
        return entityNames;
    }

    public void setEntityNames(String entityNames) {
        this.entityNames = entityNames;
    }

    public String getFieldClasses() {
        return fieldClasses;
    }

    public void setFieldClasses(String fieldClasses) {
        this.fieldClasses = fieldClasses;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    public String getDisplayBindName() {
        return displayBindName;
    }

    public void setDisplayBindName(String displayBindName) {
        this.displayBindName = displayBindName;
    }
    
    public String getBindName() {
        return bindName;
    }

    public void setBindName(String bindName) {
        this.bindName = bindName;
    }

    public String toString(){
        return display_name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
    
    
}
