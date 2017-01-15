/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;
import javax.persistence.Transient;

/**
 * 人员变动基类
 * @author Administrator
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@ClassAnnotation(displayName = "变动主表", moduleName = "人事管理")
public class BasePersonChange extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String basePersonChange_key;
    @FieldAnnotation(visible = false, displayName = "人员基本信息表")
    public A01 a01;
    @FieldAnnotation(visible = true, displayName = "变动申请日期", groupName = "变动主表", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public Date apply_date;
    @FieldAnnotation(visible = true, displayName = "变动原因", groupName = "变动主表")
    public String reason;
    @FieldAnnotation(visible = true, displayName = "确认状态", groupName = "变动主表", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public String chg_state;
    @FieldAnnotation(visible = true, displayName = "变动类型", groupName = "变动主表", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public String chg_type;
    @FieldAnnotation(visible = true, displayName = "起用日期", groupName = "变动主表")
    public Date action_date;
    @FieldAnnotation(visible = true, displayName = "起薪日期", groupName = "变动主表")
    public Date pay_date;
    @FieldAnnotation(visible = true, displayName = "操作用户", groupName = "变动主表", isEditable = true, editableWhenNew = true, editableWhenEdit = true)
    public String chg_user;
    private String changescheme_key;
    @FieldAnnotation(visible = true, displayName = "批次号", groupName = "变动主表", isEditable = false)
    public String order_no;
    @FieldAnnotation(visible = false, displayName = "批次号")
    public String part_no;
    @FieldAnnotation(visible = true, displayName = "合同随人事变化", groupName = "变动主表")
    public boolean change_ht_flag = true;
    @FieldAnnotation(visible = false, displayName = "附表处理方式", groupName = "变动主表")
    private String appendix_type;
    public Set<A01Chg> a01Chgs;
    public transient int new_flag = 0;

    @Id
    public String getBasePersonChange_key() {
        return basePersonChange_key;
    }

    public void setBasePersonChange_key(String personChange_key) {
        String old = this.basePersonChange_key;
        this.basePersonChange_key = personChange_key;
        this.firePropertyChange("basePersonChange_key", old, personChange_key);
    }

    public String getChg_state() {
        return chg_state;
    }

    public void setChg_state(String chg_state) {
        String old = this.chg_state;
        this.chg_state = chg_state;
        this.firePropertyChange("chg_state", old, chg_state);
    }

    public Date getAction_date() {
        return action_date;
    }

    public void setAction_date(Date action_date) {
        Date old = this.action_date;
        this.action_date = action_date;
        this.firePropertyChange("action_date", old, action_date);
    }

    public Date getApply_date() {
        return apply_date;
    }

    public void setApply_date(Date apply_date) {
        Date old = this.apply_date;
        this.apply_date = apply_date;
        this.firePropertyChange("apply_date", old, apply_date);
    }

    public String getChg_type() {
        return chg_type;
    }

    public void setChg_type(String chg_type) {
        String old_value = this.chg_type;
        this.chg_type = chg_type;
        this.firePropertyChange("chg_type", old_value, this.chg_type);
    }

    public String getChg_user() {
        return chg_user;
    }

    public void setChg_user(String chg_user) {
        String old = this.chg_user;
        this.chg_user = chg_user;
        this.firePropertyChange("chg_user", old, chg_user);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        String old_value = this.reason;
        this.reason = reason;
        this.firePropertyChange("reason", old_value, this.reason);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "a01_key")
    public A01 getA01() {
        return a01;
    }

    public void setA01(A01 person) {
        A01 old = this.a01;
        this.a01 = person;
        this.firePropertyChange("a01", old, person);
    }

    @OneToMany(mappedBy = "basePersonChange", fetch = FetchType.LAZY)
    public Set<A01Chg> getA01Chgs() {
        return a01Chgs;
    }

    public void setA01Chgs(Set<A01Chg> a01Chgs) {
        this.a01Chgs = a01Chgs;
    }

    @Override
    public void assignEntityKey(String key) {
        this.basePersonChange_key = key;
        this.new_flag = 1;
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
        final BasePersonChange other = (BasePersonChange) obj;
        if ((this.basePersonChange_key == null) ? (other.basePersonChange_key != null) : !this.basePersonChange_key.equals(other.basePersonChange_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.basePersonChange_key != null ? this.basePersonChange_key.hashCode() : 0);
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

    public String getChangescheme_key() {
        return changescheme_key;
    }

    public void setChangescheme_key(String changeScheme_key) {
        String old = this.changescheme_key;
        this.changescheme_key = changeScheme_key;
        this.firePropertyChange("changescheme_key", old, changeScheme_key);
    }

    public Date getPay_date() {
        return pay_date;
    }

    public void setPay_date(Date pay_date) {
        Date old = this.pay_date;
        this.pay_date = pay_date;
        this.firePropertyChange("pay_date", old, pay_date);
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        String old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }

    public boolean isChange_ht_flag() {
        return change_ht_flag;
    }

    public void setChange_ht_flag(boolean change_ht_flag) {
        boolean old = this.change_ht_flag;
        this.change_ht_flag = change_ht_flag;
        this.firePropertyChange("change_ht_flag", old, change_ht_flag);
    }

    public String getAppendix_type() {
        return appendix_type;
    }

    public void setAppendix_type(String appendix_type) {
        String old = this.appendix_type;
        this.appendix_type = appendix_type;
        this.firePropertyChange("appendix_type", old, appendix_type);
    }

    public String getPart_no() {
        return part_no;
    }

    public void setPart_no(String part_no) {
        this.part_no = part_no;
    }
}
