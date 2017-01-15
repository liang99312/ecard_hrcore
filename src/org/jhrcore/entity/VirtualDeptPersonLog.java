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
@ClassAnnotation(displayName="虚拟部门人员日志表",moduleName="人事管理")
public class VirtualDeptPersonLog extends Model implements Serializable,KeyInterface,IKey {
    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String virtualDeptPersonLog_key;
    @FieldAnnotation(visible = true, displayName = "移除日期")
    public Date remove_date;
    @FieldAnnotation(visible = true, displayName = "移除原因")
    public String remove_reason;
    @FieldAnnotation(visible = true, displayName = "加入日期")
    public Date add_date;
    @FieldAnnotation(visible = true, displayName = "加入原因")
    public String add_reason;
    @FieldAnnotation(visible = false, displayName = "操作者")
    public String person_code;
    @FieldAnnotation(visible = false, displayName = "人员")
    public A01 a01;
    @FieldAnnotation(visible = false, displayName = "部门")
    public DeptCode deptCode;
    public transient int new_flag = 0;
    @Id
    public String getVirtualDeptPersonLog_key() {
        return virtualDeptPersonLog_key;
    }

    public void setVirtualDeptPersonLog_key(String virtualDeptPersonLog_key) {
        String old = this.virtualDeptPersonLog_key;
        this.virtualDeptPersonLog_key = virtualDeptPersonLog_key;
        this.firePropertyChange("virtualDeptPersonLog_key", old, virtualDeptPersonLog_key);
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

    public Date getRemove_date() {
        return remove_date;
    }

    public void setRemove_date(Date remove_date) {
        Date old = this.remove_date;
        this.remove_date = remove_date;
        this.firePropertyChange("remove_date", old, remove_date);
    }

    public String getRemove_reason() {
        return remove_reason;
    }

    public void setRemove_reason(String remove_reason) {
        String old = this.remove_reason;
        this.remove_reason = remove_reason;
        this.firePropertyChange("remove_reason", old, remove_reason);
    }

    public String getPerson_code() {
        return person_code;
    }

    public void setPerson_code(String person_code) {
        String old  = this.person_code;
        this.person_code = person_code;
        this.firePropertyChange("person_code", old, person_code);
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
        final VirtualDeptPersonLog other = (VirtualDeptPersonLog) obj;
        if ((this.virtualDeptPersonLog_key == null) ? (other.virtualDeptPersonLog_key != null) : !this.virtualDeptPersonLog_key.equals(other.virtualDeptPersonLog_key)) {
            return false;
        }
        return true;
    }



    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.virtualDeptPersonLog_key != null ? this.virtualDeptPersonLog_key.hashCode() : 0);
        return hash;
    }

   

    @Override
    public void assignEntityKey(String key) {
        this.virtualDeptPersonLog_key=key;
        this.new_flag=1;
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
    
}
