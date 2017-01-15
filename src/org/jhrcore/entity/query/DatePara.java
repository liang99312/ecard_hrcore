/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.query;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author DB2INST3
 */
@Entity
public class DatePara extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String datePara_key;
    //0:普通；1：当前日期；2：参数；3：其它字段
    private int para_type;
    private String para_name;
    private String para_value;
    private String para_option;
    private String para_plus_value;
    private int baseCondition_key;
    public transient int new_flag = 0;

    @Id
    public String getDatePara_key() {
        return datePara_key;
    }

    public void setDatePara_key(String datePara_key) {
        String old = this.datePara_key;
        this.datePara_key = datePara_key;
        this.firePropertyChange("datePara_key", old, datePara_key);
    }

    public String getPara_name() {
        return para_name;
    }

    public void setPara_name(String para_name) {
        String old = this.para_name;
        this.para_name = para_name;
        this.firePropertyChange("para_name", old, para_name);
    }

    public String getPara_option() {
        return para_option;
    }

    public void setPara_option(String para_option) {
        String old = this.para_option;
        this.para_option = para_option;
        this.firePropertyChange("para_option", old, para_option);
    }

    public String getPara_plus_value() {
        return para_plus_value;
    }

    public void setPara_plus_value(String para_plus_value) {
        String old = this.para_plus_value;
        this.para_plus_value = para_plus_value;
        this.firePropertyChange("para_plus_value", old, para_plus_value);
    }

    public int getPara_type() {
        return para_type;
    }

    public void setPara_type(int para_type) {
        int old = this.para_type;
        this.para_type = para_type;
        this.firePropertyChange("para_type", old, para_type);
    }

    public String getPara_value() {
        return para_value;
    }

    public void setPara_value(String para_value) {
        String old = this.para_value;
        this.para_value = para_value;
        this.firePropertyChange("para_value", old, para_value);
    }

    public int getBaseCondition_key() {
        return baseCondition_key;
    }

    public void setBaseCondition_key(int baseCondition_key) {
        int old = this.baseCondition_key;
        this.baseCondition_key = baseCondition_key;
        this.firePropertyChange("baseCondition_key", old, baseCondition_key);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DatePara other = (DatePara) obj;
        if ((this.datePara_key == null) ? (other.datePara_key != null) : !this.datePara_key.equals(other.datePara_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.datePara_key != null ? this.datePara_key.hashCode() : 0);
        return hash;
    }

    @Override
    public void assignEntityKey(String key) {
        this.new_flag = 1;
        this.datePara_key = key;
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
