/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.base;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;


import com.jgoodies.binding.beans.Model;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Transient;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author Administrator
 */
@Entity
@ClassAnnotation(displayName = "功能操作日志表")
public class LogInfo extends Model implements Serializable, IKey, KeyInterface {

    @FieldAnnotation(visible = false)
    public String logInfo_key;
    @FieldAnnotation(visible = true, displayName = "日志级别", isEditable = false)
    @EnumHint(enumList = "信息;警告;错误", nullable = false)
    public String logLevel = "信息";// 日志级别，loggingEvent.getLevel()
    @FieldAnnotation(visible = true, displayName = "日志类型", isEditable = false)
//    @EnumHint(enumList = "功能操作;信息变更;信息删除", nullable = false)
    public String logType;
    @FieldAnnotation(visible = true, displayName = "日志名称", isEditable = false)
    public String loggerName; // 日志名称，loggingEvent.getLoggerName()
    @FieldAnnotation(visible = false, displayName = "日志编码", isEditable = false)
    public String logCode; // 日志名称，loggingEvent.getLoggerName()
    @Column(length = 4000)
    @FieldAnnotation(visible = true, displayName = "日志内容", isEditable = false)
    public String messgae; //日志内容， loggingEvent.getRenderedMessage()
    @FieldAnnotation(visible = true, displayName = "日志时间", isEditable = false, format = "yyyy-MM-dd HH:mm:ss")
    public Date log_date; //日志时间，loggingEvent.getStartTime()
    @FieldAnnotation(visible = true, displayName = "用户IP", isEditable = false)
    public String logIp;//用户ip
    @FieldAnnotation(visible = true, displayName = "用户MAC地址", isEditable = false)
    public String logMac;//用户ip
    @FieldAnnotation(visible = false, displayName = "人员主键", isEditable = false)
    public String person_key = "-1"; // 当前登录用户的人员主键
    @FieldAnnotation(visible = true, displayName = "人员编码", isEditable = false)
    public String person_code;//当前登录用户的人员编码   *******
    @FieldAnnotation(visible = true, displayName = "人员姓名", isEditable = false)
    public String person_name;//当前登录用户的人员名称
    @FieldAnnotation(visible = true, displayName = "当前登录部门", isEditable = false)
    public String dept_name; //当前登录用户的部门
    public transient int new_flag = 0;

    @Id
    public String getLogInfo_key() {
        return logInfo_key;
    }

    public void setLogInfo_key(String logInfo_key) {
        String old = this.logInfo_key;
        this.logInfo_key = logInfo_key;
        this.firePropertyChange("logInfo_key", old, logInfo_key);
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        String old = this.dept_name;
        this.dept_name = dept_name;
        this.firePropertyChange("dept_name", old, dept_name);
    }

    public String getPerson_code() {
        return person_code;
    }

    public void setPerson_code(String person_code) {
        String old = this.person_code;
        this.person_code = person_code;
        this.firePropertyChange("person_code", old, person_code);
    }

    public String getPerson_key() {
        return person_key;
    }

    public void setPerson_key(String person_key) {
        String old = this.person_key;
        this.person_key = person_key;
        this.firePropertyChange("person_key", old, person_key);
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        String old = this.person_name;
        this.person_name = person_name;
        this.firePropertyChange("person_name", old, person_name);
    }

    public Date getLog_date() {
        return log_date;
    }

    public void setLog_date(Date log_date) {
        Date old = this.log_date;
        this.log_date = log_date;
        this.firePropertyChange("log_date", old, log_date);
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        String old = this.loggerName;
        this.loggerName = loggerName;
        this.firePropertyChange("loggerName", old, loggerName);
    }

    public String getMessgae() {
        return messgae;
    }

    public void setMessgae(String messgae) {
        String old = this.messgae;
        this.messgae = messgae;
        this.firePropertyChange("messgae", old, messgae);
    }

    public String getLogIp() {
        return logIp;
    }

    public void setLogIp(String logIp) {
        String old = this.logIp;
        this.logIp = logIp;
        this.firePropertyChange("logIp", old, logIp);
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        String old = this.logLevel;
        this.logLevel = logLevel;
        this.firePropertyChange("logLevel", old, logLevel);
    }

    @Override
    public void assignEntityKey(String key) {
        logInfo_key = key;
        this.new_flag = 1;
    }

    @Transient
    @Override
    public long getKey() {
        return new_flag;
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

    public String getLogMac() {
        return logMac;
    }

    public void setLogMac(String logMac) {
        String old = this.logMac;
        this.logMac = logMac;
        this.firePropertyChange("logMac", old, logMac);
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        String old = this.logType;
        this.logType = logType;
        this.firePropertyChange("logType", old, logType);
    }

    public String getLogCode() {
        return logCode;
    }

    public void setLogCode(String logCode) {
        String old = this.logCode;
        this.logCode = logCode;
        this.firePropertyChange("logCode", old, logCode);
    }
}
