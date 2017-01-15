/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;
import javax.persistence.Transient;
import org.jhrcore.entity.right.FuntionRight;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "警戒提示表", moduleName = "警戒提示")
public class AutoExcuteScheme extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String autoExcuteScheme_key;
    @FieldAnnotation(visible = true, displayName = "排序号", groupName = "Default")
    public Integer order_no = 1;
    @FieldAnnotation(visible = true, displayName = "方案名称", groupName = "Default")
    public String scheme_name;
    @FieldAnnotation(visible = true, displayName = "方案类型", groupName = "Default", editableWhenEdit = false)
    @EnumHint(enumList = "自动计算;警戒提示")
    public String scheme_type;
    @FieldAnnotation(visible = true, displayName = "所属模块")
    public FuntionRight funtionRight;
    @FieldAnnotation(visible = true, displayName = "是否启用", groupName = "Default", isEditable = false)
    public boolean used_flag = false;
    @FieldAnnotation(visible = false, displayName = "提示内容", groupName = "Default", view_width = 80)
    public String content;
    @FieldAnnotation(visible = false, displayName = "计算公式", groupName = "Default")
    public String formula;
    @FieldAnnotation(visible = false, displayName = "计算公式", groupName = "Default")
    // 公式含义，即中文显示
    public String formula_meaning;
    @FieldAnnotation(visible = false, displayName = "方案标识", groupName = "Default")
    public String scheme_code;
    @FieldAnnotation(visible = false, displayName = "是否附加数据能容")
    public boolean data_flag = true;
    @FieldAnnotation(visible = false, displayName = "是否只有当有数据内容提示")
    public boolean show_flag = true;
    public transient int new_flag = 0;

    @Id
    public String getAutoExcuteScheme_key() {
        return autoExcuteScheme_key;
    }

    public void setAutoExcuteScheme_key(String autoExcuteScheme_key) {
        String old = this.autoExcuteScheme_key;
        this.autoExcuteScheme_key = autoExcuteScheme_key;
        this.firePropertyChange("autoExcuteScheme_key", old, autoExcuteScheme_key);
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        String old = this.formula;
        this.formula = formula;
        this.firePropertyChange("formula", old, formula);
    }

    public String getFormula_meaning() {
        return formula_meaning;
    }

    public void setFormula_meaning(String formula_meaning) {
        String old = this.formula_meaning;
        this.formula_meaning = formula_meaning;
        this.firePropertyChange("formula_meaning", old, formula_meaning);
    }

    public String getScheme_code() {
        return scheme_code;
    }

    public void setScheme_code(String scheme_code) {
        String old = this.scheme_code;
        this.scheme_code = scheme_code;
        this.firePropertyChange("scheme_code", old, scheme_code);
    }

    public String getScheme_name() {
        return scheme_name;
    }

    public void setScheme_name(String scheme_name) {
        String old = this.scheme_name;
        this.scheme_name = scheme_name;
        this.firePropertyChange("scheme_name", old, scheme_name);
    }

    public String getScheme_type() {
        return scheme_type;
    }

    public void setScheme_type(String scheme_type) {
        String old = this.scheme_type;
        this.scheme_type = scheme_type;
        this.firePropertyChange("scheme_type", old, scheme_type);
    }

    public boolean isData_flag() {
        return data_flag;
    }

    public void setData_flag(boolean data_flag) {
        boolean old = this.data_flag;
        this.data_flag = data_flag;
        this.firePropertyChange("data_flag", old, data_flag);
    }

    public boolean isShow_flag() {
        return show_flag;
    }

    public void setShow_flag(boolean show_flag) {
        boolean old = this.show_flag;
        this.show_flag = show_flag;
        this.firePropertyChange("show_flag", old, show_flag);
    }

    @Override
    public String toString() {
        return scheme_name;
    }

    @Override
    public void assignEntityKey(String key) {
        this.autoExcuteScheme_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AutoExcuteScheme other = (AutoExcuteScheme) obj;
        if ((this.autoExcuteScheme_key == null) ? (other.autoExcuteScheme_key != null) : !this.autoExcuteScheme_key.equals(other.autoExcuteScheme_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.autoExcuteScheme_key != null ? this.autoExcuteScheme_key.hashCode() : 0);
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

    public Integer getOrder_no() {
        if (order_no == null) {
            return 0;
        }
        return order_no;
    }

    public void setOrder_no(Integer order_no) {
        Integer old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }

    public boolean isUsed_flag() {
        return used_flag;
    }

    public void setUsed_flag(boolean used_flag) {
        boolean old = this.used_flag;
        this.used_flag = used_flag;
        this.firePropertyChange("used_flag", old, used_flag);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        String old = this.content;
        this.content = content;
        this.firePropertyChange("content", old, content);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funtionRight_key")
    public FuntionRight getFuntionRight() {
        return funtionRight;
    }

    public void setFuntionRight(FuntionRight funtionRight) {
        this.funtionRight = funtionRight;
    }
}
