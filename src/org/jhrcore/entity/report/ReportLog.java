/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.report;

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
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "报表日志表", moduleName = "报表管理")
public class ReportLog extends Model implements Serializable, KeyInterface, IKey {

    @FieldAnnotation(visible = false, displayName = "ID", groupName = "基本信息")
    public String reportLog_key;
    @FieldAnnotation(visible = true, displayName = "年/月/季度", groupName = "基本信息")
    public String ym;
    @FieldAnnotation(visible = true, displayName = "报表单号", groupName = "基本信息")
    public String cno;
    @FieldAnnotation(visible = true, displayName = "操作用户ID", groupName = "基本信息")
    public String a0190;
    @FieldAnnotation(visible = true, displayName = "操作用户", groupName = "基本信息")
    public String a0101;
    @FieldAnnotation(visible = true, displayName = "操作时间", groupName = "基本信息", format = "yyyy-MM-dd HH:mm:ss")
    public Date cdate;
    @FieldAnnotation(visible = true, displayName = "操作类型", groupName = "基本信息")
    //生成单号//生成数据//提交/取消提交//审核通过//审核驳回//取消审核//存档//设置提交用户//设置审核用户
    public String ctype;
    @FieldAnnotation(visible = true, displayName = "备注", groupName = "基本信息")
    //用于填写驳回原因、提交备注等
    public String ctext;
    public String deptCode_key;
    public String reportGroup_key;
    public String reportDef_key;
    public transient long new_flag = 0;

    @Id
    public String getReportLog_key() {
        return reportLog_key;
    }

    public void setReportLog_key(String reportLog_key) {
        String old = this.reportLog_key;
        this.reportLog_key = reportLog_key;
        this.firePropertyChange("reportLog_key", old, reportLog_key);
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        String old = this.ctype;
        this.ctype = ctype;
        this.firePropertyChange("ctype", old, ctype);
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        Date old = this.cdate;
        this.cdate = cdate;
        this.firePropertyChange("cdate", old, cdate);
    }

    public String getCtext() {
        return ctext;
    }

    public void setCtext(String ctext) {
        String old = this.ctext;
        this.ctext = ctext;
        this.firePropertyChange("ctext", old, ctext);
    }

    @Transient
    public long getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(long new_flag) {
        long old = this.new_flag;
        this.new_flag = new_flag;
        this.firePropertyChange("new_flag", old, new_flag);
    }

    @Override
    public void assignEntityKey(String key) {
        this.reportLog_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
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

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        String old = this.cno;
        this.cno = cno;
        this.firePropertyChange("cno", old, cno);
    }

    public String getDeptCode_key() {
        return deptCode_key;
    }

    public void setDeptCode_key(String deptCode_key) {
        String old = this.deptCode_key;
        this.deptCode_key = deptCode_key;
        this.firePropertyChange("deptCode_key", old, deptCode_key);
    }

    public String getReportGroup_key() {
        return reportGroup_key;
    }

    public void setReportGroup_key(String reportGroup_key) {
        String old = this.reportGroup_key;
        this.reportGroup_key = reportGroup_key;
        this.firePropertyChange("reportGroup_key", old, reportGroup_key);
    }

    public String getYm() {
        return ym;
    }

    public void setYm(String ym) {
        String old = this.ym;
        this.ym = ym;
        this.firePropertyChange("ym", old, ym);
    }

    public String getReportDef_key() {
        return reportDef_key;
    }

    public void setReportDef_key(String reportDef_key) {
        String old = this.reportDef_key;
        this.reportDef_key = reportDef_key;
        this.firePropertyChange("reportDef_key", old, reportDef_key);
    }
    
}
