/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.ecard;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author Administrator
 */
@Entity
@ClassAnnotation(displayName = "休息日表", moduleName = "考勤管理")
public class Ecard_leave extends Model implements Serializable, IKey, KeyInterface {

    @FieldAnnotation(visible = false, displayName = "ID")
    public String ecard_leave_key;
    @FieldAnnotation(visible = true, displayName = "休息日名")
    public String ecard_leave_name;
    @FieldAnnotation(visible = true, displayName = "日期")
    public Date ecard_leave_date;
    @FieldAnnotation(visible=false, displayName="标识")
    public String ecard_leave_flag;
    @FieldAnnotation(visible = true, displayName = "备注")
    public String ecard_leave_remark;
    public transient long new_flag = 0;

    @Id
    public String getEcard_leave_key() {
        return ecard_leave_key;
    }

    public void setEcard_leave_key(String ecard_leave_key) {
        this.ecard_leave_key = ecard_leave_key;
    }
    
    public String getEcard_leave_flag()
  {
    return this.ecard_leave_flag;
  }
  
  public void setEcard_leave_flag(String ecard_leave_flag)
  {
    String old = this.ecard_leave_flag;
    this.ecard_leave_flag = ecard_leave_flag;
    this.firePropertyChange("ecard_leave_flag", old, ecard_leave_flag);
  }

    public Date getEcard_leave_date() {
        return ecard_leave_date;
    }

    public void setEcard_leave_date(Date ecard_leave_date) {
        Date old = this.ecard_leave_date;
        this.ecard_leave_date = ecard_leave_date;
        this.firePropertyChange("ecard_leave_date", old, ecard_leave_date);
    }

    public String getEcard_leave_name() {
        return ecard_leave_name;
    }

    public void setEcard_leave_name(String ecard_leave_name) {
        String old = this.ecard_leave_name;
        this.ecard_leave_name = ecard_leave_name;
        this.firePropertyChange("ecard_leave_name", old, ecard_leave_name);
    }

    public String getEcard_leave_remark() {
        return ecard_leave_remark;
    }

    public void setEcard_leave_remark(String ecard_leave_remark) {
        String old = this.ecard_leave_remark;
        this.ecard_leave_remark = ecard_leave_remark;
        this.firePropertyChange("ecard_leave_remark", old, ecard_leave_remark);
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.ecard_leave_key = key;
        this.new_flag = 1L;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ecard_leave other = (Ecard_leave) obj;
        if ((this.ecard_leave_key == null) ? (other.ecard_leave_key != null) : !this.ecard_leave_key.equals(other.ecard_leave_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.ecard_leave_key != null ? this.ecard_leave_key.hashCode() : 0);
        return hash;
    }

    @Transient
    public long getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(long new_flag) {
        this.new_flag = new_flag;
    }
}
