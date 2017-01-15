/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;

/**
 *
 * @author DB2INST3
 */
@Entity
@ClassAnnotation(displayName = "部门变动信息表", moduleName = "部门机构")
public class DeptChgLog extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String deptChgLog_key;
    @FieldAnnotation(visible = true, displayName = "操作类型", groupName = "基本信息")
    @EnumHint(enumList = "新增;逻辑删除;物理删除;级次设置;部门编辑")
    public String chg_type;
    public String chg_name;
    @FieldAnnotation(visible = true, displayName = "变动项目", groupName = "基本信息")
    public String chg_caption;
    @FieldAnnotation(visible = true, displayName = "操作用户", groupName = "基本信息")
    public String chg_user;
    @FieldAnnotation(visible = true, displayName = "操作日期", groupName = "基本信息")
    public Date chg_date;
    @FieldAnnotation(visible = true, displayName = "操作前", groupName = "基本信息")
    public String chg_before;
    @FieldAnnotation(visible = true, displayName = "操作后", groupName = "基本信息")
    public String chg_after;
    @FieldAnnotation(visible = true, displayName = "操作IP", groupName = "基本信息")
    public String chg_ip;
    public String chg_mac;
    public transient int new_flag = 0;

    @Id
    public String getDeptChgLog_key() {
        return deptChgLog_key;
    }

    public void setDeptChgLog_key(String deptChgLog_key) {
        String old = this.deptChgLog_key;
        this.deptChgLog_key = deptChgLog_key;
        this.firePropertyChange("deptChgLog_key", old, deptChgLog_key);
    }

    public String getChg_after() {
        return chg_after;
    }

    public void setChg_after(String chg_after) {
        String old = this.chg_after;
        this.chg_after = chg_after;
        this.firePropertyChange("chg_after", old, chg_after);
    }

    public String getChg_before() {
        return chg_before;
    }

    public void setChg_before(String chg_before) {
        String old = this.chg_before;
        this.chg_before = chg_before;
        this.firePropertyChange("chg_before", old, chg_before);
    }

    public String getChg_caption() {
        return chg_caption;
    }

    public void setChg_caption(String chg_caption) {
        String old = this.chg_caption;
        this.chg_caption = chg_caption;
        this.firePropertyChange("chg_caption", old, chg_caption);
    }

    public Date getChg_date() {
        return chg_date;
    }

    public void setChg_date(Date chg_date) {
        Date old = this.chg_date;
        this.chg_date = chg_date;
        this.firePropertyChange("chg_date", old, chg_date);
    }

    public String getChg_name() {
        return chg_name;
    }

    public void setChg_name(String chg_name) {
        String old = this.chg_name;
        this.chg_name = chg_name;
        this.firePropertyChange("chg_name", old, chg_name);
    }

    public String getChg_type() {
        return chg_type;
    }

    public void setChg_type(String chg_type) {
        String old = this.chg_type;
        this.chg_type = chg_type;
        this.firePropertyChange("chg_type", old, chg_type);
    }

    public String getChg_user() {
        return chg_user;
    }

    public void setChg_user(String chg_user) {
        String old = this.chg_user;
        this.chg_user = chg_user;
        this.firePropertyChange("chg_user", old, chg_user);
    }

    public String getChg_ip() {
        return chg_ip;
    }

    public void setChg_ip(String chg_ip) {
        String old = this.chg_ip;
        this.chg_ip = chg_ip;
        this.firePropertyChange("chg_ip", old, chg_ip);
    }

    public String getChg_mac() {
        return chg_mac;
    }

    public void setChg_mac(String chg_mac) {
        String old = this.chg_mac;
        this.chg_mac = chg_mac;
        this.firePropertyChange("chg_mac", old, chg_mac);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DeptChgLog other = (DeptChgLog) obj;
        if ((this.deptChgLog_key == null) ? (other.deptChgLog_key != null) : !this.deptChgLog_key.equals(other.deptChgLog_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.deptChgLog_key != null ? this.deptChgLog_key.hashCode() : 0);
        return hash;
    }

    @Override
    public void assignEntityKey(String key) {
        this.deptChgLog_key = key;
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
}
