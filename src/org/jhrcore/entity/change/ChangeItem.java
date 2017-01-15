/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.change;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author Administrator
 * 变动项目，指变动人员基本信息表什么字段。
 */
@Entity
@ClassAnnotation(displayName="人事调配附属业务表",moduleName="人事管理")
public class ChangeItem extends Model implements Serializable, IKey, KeyInterface {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String changeItem_key;
    @FieldAnnotation(visible = true, displayName = "是否启用", groupName = "Default")
    public boolean used = false;//是否启用
    @FieldAnnotation(visible = true, displayName = "变动字段名", groupName = "Default",isEditable=false)
    // 需要变动的人员属性
    public String fieldName;
    @FieldAnnotation(visible = true, displayName = "变动字段描述", groupName = "Default",isEditable=false)
    // 需要变动的人员属性中文名称
    public String displayName;
    @FieldAnnotation(visible = true, displayName = "字段类型", groupName = "Default",isEditable=false)
    public String field_type;
    @FieldAnnotation(visible = true, displayName = "变动字段不允许编辑", groupName = "Default")
    public transient boolean diseditable = false;
    @FieldAnnotation(visible = true, displayName = "是否共用", groupName = "Default")
    public boolean comm_flag = true;
    private ChangeScheme changeScheme;
    public transient int new_flag = 0;

    @ManyToOne()
    @JoinColumn(name = "changeScheme_key")
    public ChangeScheme getChangeScheme() {
        return changeScheme;
    }

    public void setChangeScheme(ChangeScheme changeScheme) {
        this.changeScheme = changeScheme;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        String old = this.displayName;
        this.displayName = displayName;
        this.firePropertyChange("displayName", old, displayName);
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        String old = this.fieldName;
        this.fieldName = fieldName;
        this.firePropertyChange("fieldName", old, fieldName);
    }

    @Id
    public String getChangeItem_key() {
        return changeItem_key;
    }

    public void setChangeItem_key(String changeItem_key) {
        String old = this.changeItem_key;
        this.changeItem_key = changeItem_key;
        this.firePropertyChange("changeItem_key", old, changeItem_key);
    }

    @Override
    public String toString() {
        return displayName;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.changeItem_key = key;
        this.new_flag = 1;
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

    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        String old = this.field_type;
        this.field_type = field_type;
        this.firePropertyChange("field_type", old, field_type);
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
    @Transient
    public boolean isDiseditable() {
        return diseditable;
    }

    public void setDiseditable(boolean diseditable) {
        boolean old = this.diseditable;
        this.diseditable = diseditable;
        this.firePropertyChange("diseditable", old, diseditable);
    }

    public boolean isComm_flag() {
        return comm_flag;
    }

    public void setComm_flag(boolean comm_flag) {
        boolean old = this.comm_flag;
        this.comm_flag = comm_flag;
        this.firePropertyChange("comm_flag", old, comm_flag);
    }

}
