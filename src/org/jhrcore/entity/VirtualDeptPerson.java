/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;
import javax.persistence.Transient;

/**
 *
 * @author DB2INST3
 */
@Entity
@ClassAnnotation(displayName = "虚拟部门人员表", moduleName = "人事管理")
public class VirtualDeptPerson extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String virtualDeptPerson_key;
    @FieldAnnotation(visible = true, displayName = "加入日期", isEditable = true)
    public Date add_date;
    @FieldAnnotation(visible = true, displayName = "截止时间", isEditable = true)
    public Date end_date;
    @FieldAnnotation(visible = true, displayName = "加入原因", isEditable = true, view_width = 70)
    public String add_reason;
    public A01 a01;
    public DeptCode deptCode;
    public transient int new_flag = 0;

    @Id
    public String getVirtualDeptPerson_key() {
        return virtualDeptPerson_key;
    }

    public void setVirtualDeptPerson_key(String virtualDeptPerson_key) {
        String old = this.virtualDeptPerson_key;
        this.virtualDeptPerson_key = virtualDeptPerson_key;
        this.firePropertyChange("virtualDeptPerson_key", old, virtualDeptPerson_key);
    }

    public Date getAdd_date() {
        return add_date;
    }

    public void setAdd_date(Date add_date) {
        Date old = this.add_date;
        this.add_date = add_date;
        this.firePropertyChange("add_date", old, add_date);
    }

    public String getAdd_reason() {
        return add_reason;
    }

    public void setAdd_reason(String add_reason) {
        String old = this.add_reason;
        this.add_reason = add_reason;
        this.firePropertyChange("add_reason", old, add_reason);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "a01_key")
    public A01 getA01() {
        return a01;
    }

    public void setA01(A01 basePerson) {
        this.a01 = basePerson;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deptCode_key")
    public DeptCode getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(DeptCode deptCode) {
        this.deptCode = deptCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VirtualDeptPerson other = (VirtualDeptPerson) obj;
        if ((this.virtualDeptPerson_key == null) ? (other.virtualDeptPerson_key != null) : !this.virtualDeptPerson_key.equals(other.virtualDeptPerson_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.virtualDeptPerson_key != null ? this.virtualDeptPerson_key.hashCode() : 0);
        return hash;
    }

    @Override
    public void assignEntityKey(String key) {
        this.virtualDeptPerson_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
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

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        Date old = this.end_date;
        this.end_date = end_date;
        this.firePropertyChange("end_date", old, end_date);
    }
}
