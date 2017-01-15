/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.report;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "报表单位表", moduleName = "报表管理")
public class ReportDept extends Model implements Serializable, KeyInterface, IKey {

    public String reportDept_key;
    public String reportGroup_key;
    public String deptCode_key;
    @FieldAnnotation(visible = true, displayName = "提交用户编号", groupName = "基本信息")
    public String tuserNo;
    @FieldAnnotation(visible = true, displayName = "提交用户名称", groupName = "基本信息")
    public String tuserName;
    @FieldAnnotation(visible = true, displayName = "审核用户编号", groupName = "基本信息")
    public String suserNo;
    @FieldAnnotation(visible = true, displayName = "审核用户名称", groupName = "基本信息")
    public String suserName;
    @FieldAnnotation(visible = true, displayName = "需要审核", groupName = "基本信息")
    public boolean needCheck = false;
    public transient int new_flag = 0;

    @Id
    public String getReportDept_key() {
        return reportDept_key;
    }

    public void setReportDept_key(String reportDept_key) {
        this.reportDept_key = reportDept_key;
    }

    public String getDeptCode_key() {
        return deptCode_key;
    }

    public void setDeptCode_key(String deptCode_key) {
        this.deptCode_key = deptCode_key;
    }

    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.reportDept_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    public String getSuserName() {
        return suserName;
    }

    public void setSuserName(String suserName) {
        String old = this.suserName;
        this.suserName = suserName;
        this.firePropertyChange("suserName", old, suserName);
    }

    public String getSuserNo() {
        return suserNo;
    }

    public void setSuserNo(String suserNo) {
        String old = this.suserNo;
        this.suserNo = suserNo;
        this.firePropertyChange("suserNo", old, suserNo);
    }

    public String getTuserName() {
        return tuserName;
    }

    public void setTuserName(String tuserName) {
        String old = this.tuserName;
        this.tuserName = tuserName;
        this.firePropertyChange("tuserName", old, tuserName);
    }

    public String getTuserNo() {
        return tuserNo;
    }

    public void setTuserNo(String tuserNo) {
        String old = this.tuserNo;
        this.tuserNo = tuserNo;
        this.firePropertyChange("tuserNo", old, tuserNo);
    }

    public boolean isNeedCheck() {
        return needCheck;
    }

    public void setNeedCheck(boolean needCheck) {
        boolean old = this.needCheck;
        this.needCheck = needCheck;
        this.firePropertyChange("needCheck", old, needCheck);
    }

    public String getReportGroup_key() {
        return reportGroup_key;
    }

    public void setReportGroup_key(String reportGroup_key) {
        String old = this.reportGroup_key;
        this.reportGroup_key = reportGroup_key;
        this.firePropertyChange("reportGroup_key", old, reportGroup_key);
    }
}
