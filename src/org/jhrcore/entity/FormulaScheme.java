/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.entity;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import org.jhrcore.entity.annotation.FieldAnnotation;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.EnumHint;
/**
 *
 * @author DB2INST3
 */
@Entity
@ClassAnnotation(displayName = "公式方案表", moduleName = "系统维护")
public class FormulaScheme extends Model implements Serializable,KeyInterface,IKey {
    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String formulaScheme_key;
    @FieldAnnotation(visible = true, displayName = "启用", groupName = "Default")
    public boolean used = false;
    @FieldAnnotation(visible = true, displayName = "方案名称", groupName = "Default")
    public String scheme_name;
    // 公式方案的编码，用来区分是什么样的计算公式方案，比如工资的计算方案该编码为"Pay"
    public String scheme_code;
    // 公式方案的id，用来进一步区分是什么样的计算公式方案，比如工资的计算方案该id为某一个薪酬体系的主键
    // 如果不再区分的话，该id直接填成0
    public String scheme_id;
    @FieldAnnotation(visible = true, displayName = "方案类型", groupName = "基本信息")
    @EnumHint(enumList = "人员工资;部门工资",nullable = false)
    public String scheme_type;
    public String scheme_user;
    @FieldAnnotation(visible = true, displayName = "序号", groupName = "Default")
    public Integer order_no = 1 ;
    private Set<FormulaDetail> formulaDetails ;
    @FieldAnnotation(visible = false, displayName = "启用", groupName = "Default")
    public transient boolean use_flag = true;
    public transient int new_flag = 0;
    @Id
    public String getFormulaScheme_key() {
        return formulaScheme_key;
    }

    public void setFormulaScheme_key(String formulaScheme_key) {
        String old = this.formulaScheme_key;
        this.formulaScheme_key = formulaScheme_key;
        this.firePropertyChange("formulaScheme_key", old, formulaScheme_key);
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
        this.firePropertyChange("scheme_name",old,scheme_name);
    }

    public String getScheme_user() {
        return scheme_user;
    }

    public void setScheme_user(String scheme_user) {
        String old = this.scheme_user;
        this.scheme_user = scheme_user;
        this.firePropertyChange("scheme_user", old, scheme_user);
    }
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formulaScheme", fetch = FetchType.LAZY)
    @OrderBy("order_no")
    public Set<FormulaDetail> getFormulaDetails() {
        if(formulaDetails == null)
            return new HashSet();
        return formulaDetails;
    }

    public void setFormulaDetails(Set<FormulaDetail> formulaDetails) {
        this.formulaDetails = formulaDetails;
    }
    @Transient
    public boolean isUse_flag() {
        return use_flag;
    }

    public void setUse_flag(boolean use_flag) {
        boolean old = this.use_flag;
        this.use_flag = use_flag;
        this.firePropertyChange("use_flag", old, use_flag);
    }
    
    @Override
    public String toString(){
        return scheme_name;
    }

    @Override
    public void assignEntityKey(String key) {
        this.formulaScheme_key=key;
        this.new_flag=1;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FormulaScheme other = (FormulaScheme) obj;
        if ((this.formulaScheme_key == null) ? (other.formulaScheme_key != null) : !this.formulaScheme_key.equals(other.formulaScheme_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.formulaScheme_key != null ? this.formulaScheme_key.hashCode() : 0);
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
        if(order_no == null)
            return 0;
        return order_no;
    }

    public void setOrder_no(Integer order_no) {
        Integer old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }

    public String getScheme_id() {
        return scheme_id;
    }

    public void setScheme_id(String scheme_id) {
        String old = this.scheme_id;
        this.scheme_id = scheme_id;
        this.firePropertyChange("scheme_id", old, scheme_id);
    }

    public String getScheme_type() {
        return scheme_type;
    }

    public void setScheme_type(String scheme_type) {
        this.scheme_type = scheme_type;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        boolean old = this.used;
        this.used = used;
        this.firePropertyChange("used", old, used);
    }
    
}
