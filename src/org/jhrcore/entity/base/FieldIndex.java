/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.base;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author hflj
 */
public class FieldIndex extends Model implements Serializable {

    @FieldAnnotation(visible = false, displayName = "ID", groupName = "Default")
    private String fieldIndex_key;
    @FieldAnnotation(visible = false, displayName = "表名", groupName = "Default",isEditable=false)
    private String entity_name;
    @FieldAnnotation(visible = false, displayName = "字段名", groupName = "Default",isEditable=false)
    private String field_name;
    @FieldAnnotation(visible = true, displayName = "索引名称", groupName = "Default")
    public String index_name;
    @FieldAnnotation(visible = true, displayName = "表的描述", groupName = "Default",isEditable=false)
    public String entity_caption;
    @FieldAnnotation(visible = true, displayName = "字段描述", groupName = "Default",isEditable=false)
    public String field_caption;
    @FieldAnnotation(visible = true, displayName = "主键索引", groupName = "Default",isEditable=false)
    public boolean primary_flag = false;
    @FieldAnnotation(visible = true, displayName = "唯一性", groupName = "Default")
    public boolean unique_flag = false;
    public FieldIndex(){

    }
    public String getFieldIndex_key() {
        return fieldIndex_key;
    }

    public void setFieldIndex_key(String fieldIndex_key) {
        String old = this.fieldIndex_key;
        this.fieldIndex_key = fieldIndex_key;
        this.firePropertyChange("fieldIndex_key", old, fieldIndex_key);
    }

    public String getEntity_caption() {
        return entity_caption;
    }

    public void setEntity_caption(String entity_caption) {
        String old = this.entity_caption;
        this.entity_caption = entity_caption;
        this.firePropertyChange("entity_caption", old, entity_caption);
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        String old = this.entity_name;
        this.entity_name = entity_name;
        this.firePropertyChange("entity_name", old, entity_name);
    }

    public String getField_caption() {
        return field_caption;
    }

    public void setField_caption(String field_caption) {
        String old = this.field_caption;
        this.field_caption = field_caption;
        this.firePropertyChange("field_caption", old, field_caption);
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        String old = this.field_name;
        this.field_name = field_name;
        this.firePropertyChange("field_name", old, field_name);
    }

    public String getIndex_name() {
        return index_name;
    }

    public void setIndex_name(String index_name) {
        String old = this.index_name;
        this.index_name = index_name;
        this.firePropertyChange("index_name", old, index_name);
    }

    public boolean isUnique_flag() {
        return unique_flag;
    }

    public void setUnique_flag(boolean unique_flag) {
        boolean old = this.unique_flag;
        this.unique_flag = unique_flag;
        this.firePropertyChange("unique_flag", old, unique_flag);
    }

    public boolean isPrimary_flag() {
        return primary_flag;
    }

    public void setPrimary_flag(boolean primary_flag) {
        boolean old = this.primary_flag;
        this.primary_flag = primary_flag;
        this.firePropertyChange("primary_flag", old, primary_flag);
    }
}
