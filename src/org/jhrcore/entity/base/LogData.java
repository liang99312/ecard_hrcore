/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.base;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "数据日志表")
public class LogData extends Model implements Serializable, IKey, KeyInterface {

    @FieldAnnotation(visible = false)
    public String logData_key;
    @FieldAnnotation(visible = true, displayName = "操作类型", isEditable = false)
    @EnumHint(enumList = "新增记录;变更记录;删除记录", nullable = false)
    public String logType;
    @FieldAnnotation(visible = true, displayName = "操作数据", isEditable = false)
    public String logName; // 张三
    @FieldAnnotation(visible = false, displayName = "操作数据ID", isEditable = false)
    public String data_key;
    @FieldAnnotation(visible = true, displayName = "操作表", isEditable = false)
    public String logEntity; // 人员基本信息表.姓名
    @FieldAnnotation(visible = true, displayName = "操作字段", isEditable = false)
    public String logField; // 人员基本信息表.姓名
    @FieldAnnotation(visible = false, displayName = "操作表", isEditable = false)
    public String logTable; // A01.a0101
    @FieldAnnotation(visible = false, displayName = "操作列", isEditable = false)
    public String logColumn; // A01.a0101
    @FieldAnnotation(visible = true, displayName = "变动前", groupName = "变动主表")
    public String beforestate;
    @FieldAnnotation(visible = true, displayName = "变动后", groupName = "变动主表")
    public String afterstate;
    @FieldAnnotation(visible = true, displayName = "操作时间", isEditable = false, format = "yyyy-MM-dd HH:mm:ss")
    public Date logDate; //日志时间，loggingEvent.getStartTime()
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
    public transient int new_flag = 0;

    @Id
    public String getLogData_key() {
        return logData_key;
    }

    public void setLogData_key(String logData_key) {
        this.logData_key = logData_key;
    }

    public String getAfterstate() {
        return afterstate;
    }

    public void setAfterstate(String afterstate) {
        this.afterstate = afterstate;
    }

    public String getBeforestate() {
        return beforestate;
    }

    public void setBeforestate(String beforestate) {
        this.beforestate = beforestate;
    }

    public String getData_key() {
        return data_key;
    }

    public void setData_key(String data_key) {
        this.data_key = data_key;
    }

    public String getLogColumn() {
        return logColumn;
    }

    public void setLogColumn(String logColumn) {
        this.logColumn = logColumn;
    }

    public String getLogTable() {
        return logTable;
    }

    public void setLogTable(String logTable) {
        this.logTable = logTable;
    }

    public String getLogIp() {
        return logIp;
    }

    public void setLogIp(String logIp) {
        this.logIp = logIp;
    }

    public String getLogMac() {
        return logMac;
    }

    public void setLogMac(String logMac) {
        this.logMac = logMac;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getLogField() {
        return logField;
    }

    public void setLogField(String logField) {
        this.logField = logField;
    }

    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }

    public String getPerson_code() {
        return person_code;
    }

    public void setPerson_code(String person_code) {
        this.person_code = person_code;
    }

    public String getPerson_key() {
        return person_key;
    }

    public void setPerson_key(String person_key) {
        this.person_key = person_key;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getLogEntity() {
        return logEntity;
    }

    public void setLogEntity(String logEntity) {
        this.logEntity = logEntity;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.logData_key = key;
        this.new_flag = 1;
    }
}
