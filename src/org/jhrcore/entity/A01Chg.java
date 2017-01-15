package org.jhrcore.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.jgoodies.binding.beans.Model;


import java.util.Date;
import javax.persistence.FetchType;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;

// 这是人员变动主表
@Entity
@ClassAnnotation(displayName = "变动附表", moduleName = "人事管理")
public class A01Chg extends Model implements Serializable,IKey ,KeyInterface{
    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String a01Chg_key;
    @FieldAnnotation(visible = true, displayName = "变动项目", groupName = "变动主表", isEditable = false)
    public String chgfield ;
    @FieldAnnotation(visible = false, displayName = "变动字段名", groupName = "变动主表", isEditable = false)
    private String chgfieldname ;
    @FieldAnnotation(visible = true, displayName = "变动前", groupName = "变动主表")
    public String beforestate;
    @FieldAnnotation(visible = true, displayName = "变动后", groupName = "变动主表")
    public String afterstate;
    @FieldAnnotation(visible = true, displayName = "变动日期", groupName = "变动主表", isEditable = false)
    public Date chgdate;
    @FieldAnnotation(visible = false, displayName = "变动前部门编码", groupName = "变动主表")
    public String b_dept_code;
    @FieldAnnotation(visible = true, displayName = "变动前部门全称", groupName = "变动主表")
    public String b_full_name;
    @FieldAnnotation(visible = false, displayName = "变动后部门编码", groupName = "变动主表", isEditable = false)
    public String a_dept_code;
    @FieldAnnotation(visible = true, displayName = "变动后部门全称", groupName = "变动主表", isEditable = false)
    public String a_full_name;
    @FieldAnnotation(visible = false)
    public BasePersonChange basePersonChange;
    @FieldAnnotation(visible = false, displayName = "所属变动模板Key", groupName = "变动主表", isEditable = false)
    public String changeScheme_key;
    public transient int new_flag = 0;
    @Id
    public String getA01Chg_key() {
        return a01Chg_key;
    }

    public void setA01Chg_key(String a01Chg_key) {
        String old = this.a01Chg_key;
        this.a01Chg_key = a01Chg_key;
        this.firePropertyChange("a01Chg_key",old,a01Chg_key);
    }
    
    public String getAfterstate() {
        return afterstate;
    }

    public void setAfterstate(String new_value) {
        String old = this.afterstate;
        this.afterstate = new_value;
        this.firePropertyChange("afterstate", old, new_value);
    }

    public String getBeforestate() {
        return beforestate;
    }

    public void setBeforestate(String old_value) {
        String old = this.beforestate;
        this.beforestate = old_value;
        this.firePropertyChange("beforestate", old, old_value);
    }

    public String getB_full_name() {
        return b_full_name;
    }

    public void setB_full_name(String b_full_name) {
        String old = this.b_full_name;
        this.b_full_name = b_full_name;
        this.firePropertyChange("b_full_name", old, b_full_name);
    }

    public Date getChgdate() {
        return chgdate;
    }

    public void setChgdate(Date apply_date) {
        Date old = this.chgdate;
        this.chgdate = apply_date;
        this.firePropertyChange("chgdate", old, apply_date);
    }

    public String getChgfield() {
        return chgfield;
    }

    public void setChgfield(String chgfield) {
        String old = this.chgfield;
        this.chgfield = chgfield;
        this.firePropertyChange("chgfield", old, chgfield);
    }

    public String getA_dept_code() {
        return a_dept_code;
    }

    public void setA_dept_code(String a_dept_code) {
        String old = this.a_dept_code;
        this.a_dept_code = a_dept_code;
        this.firePropertyChange("a_dept_code", old, a_dept_code);
    }

    public String getA_full_name() {
        return a_full_name;
    }

    public void setA_full_name(String a_full_name) {
        String old = this.a_full_name;
        this.a_full_name = a_full_name;
        this.firePropertyChange("a_full_name", old, a_full_name);
    }

    public String getB_dept_code() {
        return b_dept_code;
    }

    public void setB_dept_code(String b_dept_code) {
        String old = this.b_dept_code;
        this.b_dept_code = b_dept_code;
        this.firePropertyChange("b_dept_code", old, b_dept_code);
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basePersonChange_key")
    public BasePersonChange getBasePersonChange() {
        return basePersonChange;
    }

    public void setBasePersonChange(BasePersonChange basePersonChange) {
        this.basePersonChange = basePersonChange;
    }

    @Override
    @Transient
    public long getKey() {
        return  new_flag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final A01Chg other = (A01Chg) obj;
        if ((this.a01Chg_key == null) ? (other.a01Chg_key != null) : !this.a01Chg_key.equals(other.a01Chg_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.a01Chg_key != null ? this.a01Chg_key.hashCode() : 0);
        return hash;
    }

    @Override
    public void assignEntityKey(String key) {
       this.a01Chg_key=key;
       new_flag=1;
    }
    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        int old = this.new_flag;
        this.new_flag = new_flag;
        this.firePropertyChange("new_flag",old,new_flag);
    }
    public String getChgfieldname() {
        return chgfieldname;
    }

    public void setChgfieldname(String chgfieldname) {
        String old = this.chgfieldname;
        this.chgfieldname = chgfieldname;
        this.firePropertyChange("chgfieldname",old,chgfieldname);
    }
    
    public String getChangeScheme_key() {
        return changeScheme_key;
    }

    public void setChangeScheme_key(String changeScheme_key) {
        String old = this.changeScheme_key;
        this.changeScheme_key = changeScheme_key;
        this.firePropertyChange("changeScheme_key",old,changeScheme_key);
    }
    
}
