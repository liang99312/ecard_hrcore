package org.jhrcore.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jhrcore.entity.annotation.FieldAnnotation;
import javax.persistence.Transient;
import com.jgoodies.binding.beans.Model;
import org.jhrcore.entity.annotation.ClassAnnotation;

@Entity
@Table(name = "SysParameter")
@ClassAnnotation(displayName = "系统参数表", moduleName = "系统维护")
public class SysParameter extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String sysParameter_key;
    @FieldAnnotation(visible = false, displayName = "编码", groupName = "基本信息")
    public String sysparameter_code;
    @FieldAnnotation(visible = true, displayName = "参数名", isEditable = false, groupName = "基本信息")
    public String sysparameter_name;
    @FieldAnnotation(visible = true, displayName = "值", groupName = "基本信息")
    public String sysparameter_value = "";
    @FieldAnnotation(visible = true, displayName = "参数类型", groupName = "基本信息")
    public String sysparameter_type;
    @FieldAnnotation(visible = true, displayName = "角色ID", groupName = "基本信息")
    public String sysparameter_roleid;
    public transient int new_flag = 0;

    @Id
    public String getSysParameter_key() {
        return sysParameter_key;
    }

    public void setSysParameter_key(String sysParameter_key) {
        String old = this.sysParameter_key;
        this.sysParameter_key = sysParameter_key;
        this.firePropertyChange("sysParameter_key", old, sysParameter_key);
    }

    public String getSysparameter_code() {
        return sysparameter_code;
    }

    public void setSysparameter_code(String sysParameter_code) {
        String old = this.sysparameter_code;
        this.sysparameter_code = sysParameter_code;
        this.firePropertyChange("sysParameter_code", old, sysParameter_code);
    }

    public String getSysparameter_name() {
        return sysparameter_name;
    }

    public void setSysparameter_name(String sysParameter_name) {
        String old = this.sysparameter_name;
        this.sysparameter_name = sysParameter_name;
        this.firePropertyChange("sysparameter_name", old, sysParameter_name);
    }

    public String getSysparameter_value() {
        return sysparameter_value;
    }

    public void setSysparameter_value(String sysParameter_value) {
        String old = this.sysparameter_value;
        this.sysparameter_value = sysParameter_value;
        this.firePropertyChange("sysparameter_value", old, sysParameter_value);
    }

    public String getSysparameter_roleid() {
        return sysparameter_roleid;
    }

    public void setSysparameter_roleid(String sysParameter_roleID) {
        String old = this.sysparameter_roleid;
        this.sysparameter_roleid = sysParameter_roleID;
        this.firePropertyChange("sysparameter_roleid", old, sysParameter_roleID);
    }

    public String getSysparameter_type() {
        return sysparameter_type;
    }

    public void setSysparameter_type(String sysParameter_type) {
        String old = this.sysparameter_type;
        this.sysparameter_type = sysParameter_type;
        this.firePropertyChange("sysparameter_type", old, sysParameter_type);
    }

    public boolean contains(String inputString) {
        boolean result = false;
        String[] tmp = sysparameter_value.split("\\;");
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i].equals(inputString)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public void assignEntityKey(String key) {
        this.sysParameter_key = key;
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

    @Override
    public String toString() {
        return sysparameter_name;
    }
}
