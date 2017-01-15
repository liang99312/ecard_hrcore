/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.query;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "统计分析分段详细表", moduleName = "系统维护")
public class QueryExtraField extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    public String queryExtraField_key;
    @FieldAnnotation(visible = true, displayName = "项目名称", groupName = "基本信息", isEditable = true)
    public String field_caption;
    public String field_text;
    public String field_sql;
    @FieldAnnotation(visible = true, displayName = "启用", groupName = "基本信息", isEditable = true)
    public boolean used = false;
    @FieldAnnotation(visible = true, displayName = "排序号", groupName = "基本信息", isEditable = false, editableWhenEdit = false, editableWhenNew = false)
    public int order_no = 1;
    @FieldAnnotation(visible = true, displayName = "类型", groupName = "基本信息", isEditable = true)
    @EnumHint(enumList = "普通;求和;平均;最大;最小;计数", nullable = false)
    public String field_type = "普通";
    @FieldAnnotation(visible = false, displayName = "标识", groupName = "基本信息", isEditable = true)
    private String field_code;
    //该字段用于区分该字段是否来自网格右键的设置显示字段，从而保证该字段为单纯字段，并且不可编辑
    @FieldAnnotation(visible = false, displayName = "是否可编辑", groupName = "基本信息", isEditable = false)
    private boolean editable = true;
    private String entity_name;
    private String scheme_key;
    private transient String code_type = "@@@";
    private transient String fieldType = "String";
    public transient int new_flag = 0;

    @Id
    public String getQueryExtraField_key() {
        return queryExtraField_key;
    }

    public void setQueryExtraField_key(String queryExtraField_key) {
        String old = this.queryExtraField_key;
        this.queryExtraField_key = queryExtraField_key;
        this.firePropertyChange("queryExtraField_key", old, queryExtraField_key);
    }

    public String getField_caption() {
        return field_caption;
    }

    public void setField_caption(String field_caption) {
        String old = this.field_caption;
        this.field_caption = field_caption;
        this.firePropertyChange("field_caption", old, field_caption);
    }

    public String getField_code() {
        return field_code;
    }

    public void setField_code(String field_code) {
        String old = this.field_code;
        this.field_code = field_code;
        this.firePropertyChange("field_code", old, field_code);
    }

    public String getField_sql() {
        return field_sql;
    }

    public void setField_sql(String field_sql) {
        String old = this.field_sql;
        this.field_sql = field_sql;
        this.firePropertyChange("field_sql", old, field_sql);
    }

    public String getField_text() {
        return field_text;
    }

    public void setField_text(String field_text) {
        String old = this.field_text;
        this.field_text = field_text;
        this.firePropertyChange("field_text", old, field_text);
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

    public String getScheme_key() {
        return scheme_key;
    }

    public void setScheme_key(String scheme_key) {
        String old = this.scheme_key;
        this.scheme_key = scheme_key;
        this.firePropertyChange("scheme_key", old, scheme_key);
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        boolean old = this.used;
        this.used = used;
        this.firePropertyChange("used", old, used);
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        int old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        String old = this.entity_name;
        this.entity_name = entity_name;
        this.firePropertyChange("entity_name", old, entity_name);
    }

    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        String old = this.field_type;
        this.field_type = field_type;
        this.firePropertyChange("field_type", old, field_type);
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        boolean old = this.editable;
        this.editable = editable;
        this.firePropertyChange("editable", old, editable);
    }

    @Transient
    public String getCode_type() {
        return code_type;
    }

    public void setCode_type(String code_type) {
        this.code_type = code_type;
    }

    @Transient
    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public void assignEntityKey(String key) {
        this.queryExtraField_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }
}
