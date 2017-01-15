/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.showstyle;

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
@ClassAnnotation(displayName = "通用汇总方案表", moduleName = "系统维护")
public class ColumnSum extends Model implements Serializable, IKey, KeyInterface {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String columnSum_key;
    public String entity_name;
    public String field_name;
    @FieldAnnotation(visible = true, displayName = "字段描述", groupName = "Default", isEditable = false, editableWhenEdit = false, editableWhenNew = false)
    public String field_caption;
    @FieldAnnotation(visible = true, displayName = "汇总方式", groupName = "Default", isEditable = true)
    @EnumHint(enumList = "求和;平均;计数", nullable = false)
    public String sum_type;
    public int type = 0;
    @FieldAnnotation(visible = false, displayName = "字段类型", groupName = "Default", isEditable = false, editableWhenEdit = false, editableWhenNew = false)
    public String field_type;
    private String user_code;
    public transient int new_flag = 0;

    @Id
    public String getColumnSum_key() {
        return columnSum_key;
    }

    public void setColumnSum_key(String columnSum_key) {
        String old_value = this.columnSum_key;
        this.columnSum_key = columnSum_key;
        this.firePropertyChange("columnSum_key", old_value, this.columnSum_key);
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        String old_value = this.entity_name;
        this.entity_name = entity_name;
        this.firePropertyChange("entity_name", old_value, this.entity_name);
    }

    public String getField_caption() {
        return field_caption;
    }

    public void setField_caption(String field_caption) {
        String old_value = this.field_caption;
        this.field_caption = field_caption;
        this.firePropertyChange("field_caption", old_value, this.field_caption);
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        String old_value = this.field_name;
        this.field_name = field_name;
        this.firePropertyChange("field_name", old_value, this.field_name);
    }

    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        int old_value = this.new_flag;
        this.new_flag = new_flag;
        this.firePropertyChange("new_flag", old_value, this.new_flag);
    }

    public String getSum_type() {
        return sum_type;
    }

    public void setSum_type(String sum_type) {
        String old_value = this.sum_type;
        this.sum_type = sum_type;
        this.firePropertyChange("sum_type", old_value, this.sum_type);
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        String old_value = this.user_code;
        this.user_code = user_code;
        this.firePropertyChange("user_code", old_value, this.user_code);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        int old_value = this.type;
        this.type = type;
        this.firePropertyChange("type", old_value, this.type);
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        columnSum_key = key;
        new_flag = 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ColumnSum other = (ColumnSum) obj;
        if ((this.columnSum_key == null) ? (other.columnSum_key != null) : !this.columnSum_key.equals(other.columnSum_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.columnSum_key != null ? this.columnSum_key.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return field_caption + "[" + sum_type + "]";
    }

    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        String old_value = this.field_type;
        this.field_type = field_type;
        this.firePropertyChange("field_type", old_value, this.field_type);
    }
}
