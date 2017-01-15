/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.entity.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author Owner
 */
public class TempFieldInfo implements Serializable {
    
    // 表示该字段来源于的实体名称
    @FieldAnnotation(visible = true, displayName = "表名", groupName = "Default")
    public String entity_name;
    @FieldAnnotation(visible = true, displayName = "表的表述", groupName = "Default")
    public String entity_caption;
    @FieldAnnotation(visible = true, displayName = "字段名", groupName = "Default")
    public String field_name;
    @FieldAnnotation(visible = true, displayName = "字段表述", groupName = "Default",isEditable=false,editableWhenEdit=false,editableWhenNew=false)
    public String caption_name;
    @FieldAnnotation(visible = true, displayName = "字段类型", groupName = "Default")
    public String field_type;
    @FieldAnnotation(visible = true, displayName = "格式化", groupName = "Default")
    public String format;
    @FieldAnnotation(visible = false, displayName = "默认值", groupName = "Default")
    public String default_value;
    @FieldAnnotation(visible = true, displayName = "字段活性", groupName = "Default",visibleWhenNew=false,editableWhenNew=false,editableWhenEdit=false)
    public String field_mark = "";
    private Field field;
    private String group_name;
    public String pym;
    public int order_no = 0;
    private int field_width = 0;
    private int field_scale = 0;
    private int view_width = 0;
    @FieldAnnotation(visible = true, displayName = "关联编码", groupName = "Default")
    public String code_type_name;
    private boolean showEntity = false;
    @FieldAnnotation(visible = true, displayName = "选择", groupName = "Default")
    public boolean selected = false;
    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        this.entity_name = entity_name;
    }
    
    public String getCaption_name() {
        return caption_name;
    }

    public void setCaption_name(String caption_name) {
        this.caption_name = caption_name;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        this.field_type = field_type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getEntity_caption() {
        return entity_caption;
    }

    public void setEntity_caption(String entity_caption) {
        this.entity_caption = entity_caption;
    }

    public String getField_mark() {
        return field_mark;
    }

    public void setField_mark(String field_mark) {
        this.field_mark = field_mark;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TempFieldInfo other = (TempFieldInfo) obj;
        if ((this.entity_name == null) ? (other.entity_name != null) : !this.entity_name.equals(other.entity_name)) {
            return false;
        }
        if ((this.field_name == null) ? (other.field_name != null) : !this.field_name.equals(other.field_name)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        if(showEntity)
            return caption_name+"["+entity_caption+"]";
        return caption_name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.entity_name != null ? this.entity_name.hashCode() : 0);
        hash = 71 * hash + (this.field_name != null ? this.field_name.hashCode() : 0);
        return hash;
    }

    

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getPym() {
        return pym;
    }

    public void setPym(String pym) {
        this.pym = pym;
    }

    public int getField_width() {
        return field_width;
    }

    public void setField_width(int field_width) {
        this.field_width = field_width;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    public int getField_scale() {
        return field_scale;
    }

    public void setField_scale(int field_scale) {
        this.field_scale = field_scale;
    }

    public String getCode_type_name() {
        return code_type_name;
    }

    public void setCode_type_name(String code_type_name) {
        this.code_type_name = code_type_name;
    }

    public boolean isShowEntity() {
        return showEntity;
    }

    public void setShowEntity(boolean showEntity) {
        this.showEntity = showEntity;
    }

    public String getDefault_value() {
        return default_value;
    }

    public void setDefault_value(String default_value) {
        this.default_value = default_value;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getView_width() {
        return view_width;
    }

    public void setView_width(int view_width) {
        this.view_width = view_width;
    }
}
