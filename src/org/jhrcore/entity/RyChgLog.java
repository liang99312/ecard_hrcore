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
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "人员新增删除日志表", moduleName = "人事管理")
public class RyChgLog extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String ryChgLog_key;
    @FieldAnnotation(visible = true, displayName = "部门", groupName = "人员新增删除日志表", isEditable = false)
    public String dept_name;
    @FieldAnnotation(visible = true, displayName = "人员编号", groupName = "人员新增删除日志表", isEditable = false)
    public String a0190;
    @FieldAnnotation(visible = true, displayName = "姓名", groupName = "人员新增删除日志表", isEditable = false)
    public String a0101;
    @FieldAnnotation(visible = true, displayName = "日期", groupName = "人员新增删除日志表", isEditable = false)
    public Date chg_date;
    @FieldAnnotation(visible = true, displayName = "前状态", groupName = "人员新增删除日志表")
    public String beforestate;
    @FieldAnnotation(visible = true, displayName = "后状态", groupName = "人员新增删除日志表")
    public String afterstate;
    @FieldAnnotation(visible = true, displayName = "变动类型", groupName = "人员新增删除日志表", isEditable = false)
    public String chg_type;
    @FieldAnnotation(visible = true, displayName = "操作者", groupName = "人员新增删除日志", isEditable = false)
    public String chg_user;
    @FieldAnnotation(visible = true, displayName = "操作IP", groupName = "人员新增删除日志表", isEditable = false)
    public String chg_ip;
    @FieldAnnotation(visible = true, displayName = "操作Mac地址", groupName = "人员新增删除日志表", isEditable = false)
    public String chg_mac;
    @FieldAnnotation(visible = true, displayName = "变动字段", groupName = "人员新增删除日志表", isEditable = false)
    public String chg_field;
    @FieldAnnotation(visible = false, displayName = "变动前部门代码", groupName = "人员新增删除日志表", isEditable = false)
    public String old_dept_code;
    @FieldAnnotation(visible = false, displayName = "变动后部门代码", groupName = "人员新增删除日志表", isEditable = false)
    public String new_dept_code;
    private String changeScheme_key;
    private String a01_key;
    private String log_content;
    public transient int new_flag = 0;

    @Id
    public String getRyChgLog_key() {
        return ryChgLog_key;
    }

    public void setRyChgLog_key(String ryChgLog_key) {
        String old = this.ryChgLog_key;
        this.ryChgLog_key = ryChgLog_key;
        this.firePropertyChange("ryChgLog_key", old, ryChgLog_key);
    }

    public String getAfterstate() {
        return afterstate;
    }

    public void setAfterstate(String afterstate) {
        String old = this.afterstate;
        this.afterstate = afterstate;
        this.firePropertyChange("afterstate", old, afterstate);
    }

    public String getBeforestate() {
        return beforestate;
    }

    public void setBeforestate(String beforestate) {
        String old = this.beforestate;
        this.beforestate = beforestate;
        this.firePropertyChange("beforestate", old, beforestate);
    }

    public Date getChg_date() {
        return chg_date;
    }

    public void setChg_date(Date chg_date) {
        Date old = this.chg_date;
        this.chg_date = chg_date;
        this.firePropertyChange("chg_date", old, chg_date);
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

    public String getChg_field() {
        return chg_field;
    }

    public void setChg_field(String chg_field) {
        String old = this.chg_field;
        this.chg_field = chg_field;
        this.firePropertyChange("chg_field", old, chg_field);
    }

    @Override
    public void assignEntityKey(String key) {
        this.ryChgLog_key = key;
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
        final RyChgLog other = (RyChgLog) obj;
        if ((this.ryChgLog_key == null) ? (other.ryChgLog_key != null) : !this.ryChgLog_key.equals(other.ryChgLog_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.ryChgLog_key != null ? this.ryChgLog_key.hashCode() : 0);
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

    public String getA0101() {
        return a0101;
    }

    public void setA0101(String a0101) {
        String old = this.a0101;
        this.a0101 = a0101;
        this.firePropertyChange("a0101", old, a0101);
    }

    public String getA0190() {
        return a0190;
    }

    public void setA0190(String a0190) {
        String old = this.a0190;
        this.a0190 = a0190;
        this.firePropertyChange("a0190", old, a0190);
    }

    public String getA01_key() {
        return a01_key;
    }

    public void setA01_key(String a01_key) {
        String old = this.a01_key;
        this.a01_key = a01_key;
        this.firePropertyChange("a01_key", old, a01_key);
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        String old = this.dept_name;
        this.dept_name = dept_name;
        this.firePropertyChange("dept_name", old, dept_name);
    }

    public String getNew_dept_code() {
        return new_dept_code;
    }

    public void setNew_dept_code(String new_dept_code) {
        String old = this.new_dept_code;
        this.new_dept_code = new_dept_code;
        this.firePropertyChange("new_dept_code", old, new_dept_code);
    }

    public String getOld_dept_code() {
        return old_dept_code;
    }

    public void setOld_dept_code(String old_dept_code) {
        String old = this.old_dept_code;
        this.old_dept_code = old_dept_code;
        this.firePropertyChange("old_dept_code", old, old_dept_code);
    }

    public String getChangeScheme_key() {
        return changeScheme_key;
    }

    public void setChangeScheme_key(String changeScheme_key) {
        String old = this.changeScheme_key;
        this.changeScheme_key = changeScheme_key;
        this.firePropertyChange("changeScheme_key", old, changeScheme_key);
    }

    public String getLog_content() {
        return log_content;
    }

    public void setLog_content(String log_content) {
        this.log_content = log_content;
    }
}
