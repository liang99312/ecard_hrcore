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
    // ���ֶ�������ʵ��������ȫ��
    private Class entityClass;

    private String field_name;

    private String display_name;

    // ���ڰ󶨵��༭��������
    private String bindName;

    // ���ڰ󶨵��༭������������
    private String displayBindName;

    // �Ӳ�ѯ��ʵ�忪ʼ�������������Ե�ʵ�壬����ʵ�������÷ֺŷֿ�����"BasePerson;Dept;"
    private String entityNames;

    // ��ʾ���ֶ�������ʵ��֮��Ĺ�ϵ��ֱ����ѯʵ�壬�÷ֺŷֿ����硰2��1����1����ͨ���ԣ�2��ʵ�����ԣ�3����������
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
