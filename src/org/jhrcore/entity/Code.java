package org.jhrcore.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jhrcore.entity.annotation.FieldAnnotation;

import com.jgoodies.binding.beans.Model;

//代码
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.ui.language.WebHrMessage;

@Entity
@Table(name = "Code")
@ClassAnnotation(displayName = "编码表", moduleName = "系统维护")
public class Code extends Model implements Serializable, IObjectAttribute, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String code_key;
    @FieldAnnotation(visible = true, displayName = "编码", groupName = "Default", not_null = true, editableWhenEdit = false, isUnique = true)
    public String code_id;
    @FieldAnnotation(visible = true, displayName = "父编码", groupName = "Default", editableWhenEdit = false)
    public String parent_id;
    @FieldAnnotation(visible = true, displayName = "名称", groupName = "Default", not_null = true)
    public String code_name;
    @FieldAnnotation(visible = true, displayName = "编码类型", groupName = "Default", editableWhenEdit = false)
    public String code_type; // 编码类型，目前=编码分类的名称，也就是最顶级的编码名称   
    @FieldAnnotation(visible = true, displayName = "编码等级", groupName = "Default", editableWhenEdit = false)
    public Integer code_level = 0;
    @FieldAnnotation(visible = false, displayName = "是否使用", groupName = "Default", editableWhenEdit = false)
    public boolean used = true;
    @FieldAnnotation(visible = true, displayName = "总级次", editableWhenEdit = false)
    public Integer grades;
    @FieldAnnotation(visible = true, displayName = "末级标识", groupName = "Default", editableWhenEdit = false)
    public boolean end_flag = true;
    @FieldAnnotation(visible = false, displayName = "编辑类型", editableWhenEdit = false)
    @Transient
    public Integer editType = IObjectAttribute.EDITTYPE_EDIT;
    @FieldAnnotation(visible = false, displayName = "拼音码", groupName = "Default", not_null = true, editableWhenEdit = false)
    public String pym;
    private String code_tag;
    public transient int new_flag = 0;
    public transient boolean edit_flag = true;//是否可编辑
    public transient boolean hide_flag = false;//是否可查看，用于数据重构LIMIT

    @Id
    public String getCode_key() {
        return code_key;
    }

    public void setCode_key(String code_key) {
        String old = this.code_key;
        this.code_key = code_key;
        this.firePropertyChange("code_key", old, code_key);
    }

    public String getCode_id() {
        return code_id;
    }

    public void setCode_id(String code_id) {
        String old = this.code_id;
        this.code_id = code_id;
        this.firePropertyChange("code_id", old, code_id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Code other = (Code) obj;
        if ((this.code_key == null) ? (other.code_key != null) : !this.code_key.equals(other.code_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.code_key != null ? this.code_key.hashCode() : 0);
        return hash;
    }

    public void setEditType(Integer editType) {
        Integer old = this.editType;
        this.editType = editType;
        this.firePropertyChange("editType", old, editType);
    }

    public String getCode_type() {
        return code_type;
    }

    public void setCode_type(String code_type) {
        String old = this.code_type;
        this.code_type = code_type;
        this.firePropertyChange("code_type", old, code_type);
    }

    @Override
    public String toString() {
        String title = WebHrMessage.getString(this.code_tag);
        title = (title == null || title.trim().equals("")) ? this.code_name : title;
        return title;
    }

    public String getCode_name() {
        return code_name;
    }

    public void setCode_name(String code_name) {
        String old = this.code_name;
        this.code_name = code_name;
        this.firePropertyChange("code_name", old, code_name);
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        String old = this.parent_id;
        this.parent_id = parent_id;
        this.firePropertyChange("parent_id", old, parent_id);
    }

    @Override
    public int editType() {
        return editType;
    }

    public String getCode_tag() {
        return code_tag;
    }

    public void setCode_tag(String code_tag) {
        String old = this.code_tag;
        this.code_tag = code_tag;
        this.firePropertyChange("code_tag", old, code_tag);
    }

    @Transient
    public Integer getEditType() {
        if (code_key == null) {
            editType = 0;
        } else {
            editType = 1;
        }
        return editType;
    }

    public String getPym() {
        return pym;
    }

    public void setPym(String pym) {
        String old = this.pym;
        this.pym = pym;
        this.firePropertyChange("pym", old, pym);
    }

    @Override
    public void assignEntityKey(String key) {
        this.code_key = key;
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

    public Integer getCode_level() {
        if (code_level == null) {
            return 0;
        }
        return code_level;
    }

    public void setCode_level(Integer code_level) {
        Integer old = this.code_level;
        this.code_level = code_level;
        this.firePropertyChange("code_level", old, code_level);
    }

    public boolean isEnd_flag() {
        return end_flag;
    }

    public void setEnd_flag(boolean end_flag) {
        boolean old = this.end_flag;
        this.end_flag = end_flag;
        this.firePropertyChange("end_flag", old, end_flag);
    }

    public Integer getGrades() {
        if (grades == null) {
            return 0;
        }
        return grades;
    }

    public void setGrades(Integer grades) {
        Integer old = this.grades;
        this.grades = grades;
        this.firePropertyChange("grades", old, grades);
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        boolean old = this.used;
        this.used = used;
        this.firePropertyChange("used", old, used);
    }

    @Transient
    public boolean isEdit_flag() {
        return edit_flag;
    }

    public void setEdit_flag(boolean edit_flag) {
        this.edit_flag = edit_flag;
    }

    @Transient
    public boolean isHide_flag() {
        return hide_flag;
    }

    public void setHide_flag(boolean hide_flag) {
        this.hide_flag = hide_flag;
    }
}
