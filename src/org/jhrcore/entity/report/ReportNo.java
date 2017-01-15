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
@ClassAnnotation(displayName = "报表单号表", moduleName = "报表管理")
public class ReportNo extends Model implements Serializable, KeyInterface, IKey {

    @FieldAnnotation(visible = false, displayName = "ID", groupName = "基本信息")
    public String reportNo_key;
    @FieldAnnotation(visible = false, displayName = "部门ID", groupName = "基本信息")
    public String deptCode_key;
    @FieldAnnotation(visible = false, displayName = "报表ID", groupName = "基本信息")
    public String reportDef_key;
    @FieldAnnotation(visible = false, displayName = "套表ID", groupName = "基本信息")
    public String reportGroup_key;
    @FieldAnnotation(visible = true, displayName = "单据号", groupName = "基本信息")
    public String cno;
    @FieldAnnotation(visible = true, displayName = "类型", groupName = "基本信息")
    public String r_type;
    @FieldAnnotation(visible = true, displayName = "状态", groupName = "基本信息")
    //未生成/未提交/已提交/审核通过/未存档/已存档
    public String noState;
    @FieldAnnotation(visible = true, displayName = "年/月/季度", groupName = "基本信息")
    public String ym;
    @FieldAnnotation(visible = false, displayName = "部门代码", groupName = "基本信息")
    public String dept_code2;
    @FieldAnnotation(visible = false, displayName = "参数1", groupName = "基本信息")
    public String para1;
    @FieldAnnotation(visible = false, displayName = "参数2", groupName = "基本信息")
    public String para2;
    @FieldAnnotation(visible = false, displayName = "参数3", groupName = "基本信息")
    public String para3;
    @FieldAnnotation(visible = false, displayName = "参数4", groupName = "基本信息")
    public String para4;
    @FieldAnnotation(visible = false, displayName = "参数5", groupName = "基本信息")
    public String para5;
    @FieldAnnotation(visible = true, displayName = "数据生成次数", groupName = "基本信息")
    public int cnum = 0;
    @FieldAnnotation(visible = true, displayName = "最后一次数据生成时间", groupName = "基本信息", format = "yyyy-MM-dd HH:mm:ss")
    public Date cdate;
    @FieldAnnotation(visible = true, displayName = "办事员编号", groupName = "基本信息")
    public String buserNo;
    @FieldAnnotation(visible = true, displayName = "办事员名称", groupName = "基本信息")
    public String buserName;
    @FieldAnnotation(visible = true, displayName = "需要审核", groupName = "基本信息")
    public boolean needCheck = false;
    public transient boolean checked = true;
    public transient int new_flag = 0;

    @Id
    public String getReportNo_key() {
        return reportNo_key;
    }

    public void setReportNo_key(String reportNo_key) {
        String old = this.reportNo_key;
        this.reportNo_key = reportNo_key;
        this.firePropertyChange("reportNo_key", old, reportNo_key);
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        Date old = this.cdate;
        this.cdate = cdate;
        this.firePropertyChange("cdate", old, cdate);
    }

    public int getCnum() {
        return cnum;
    }

    public void setCnum(int cnum) {
        int old = this.cnum;
        this.cnum = cnum;
        this.firePropertyChange("cnum", old, cnum);
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

    public String getReportDef_key() {
        return reportDef_key;
    }

    public void setReportDef_key(String reportDef_key) {
        String old = this.reportDef_key;
        this.reportDef_key = reportDef_key;
        this.firePropertyChange("reportDef_key", old, reportDef_key);
    }

    public String getNoState() {
        return noState;
    }

    public void setNoState(String noState) {
        String old = this.noState;
        this.noState = noState;
        this.firePropertyChange("noState", old, noState);
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        String old = this.cno;
        this.cno = cno;
        this.firePropertyChange("cno", old, cno);
    }

    public String getPara1() {
        return para1;
    }

    public void setPara1(String para1) {
        String old = this.para1;
        this.para1 = para1;
        this.firePropertyChange("para1", old, para1);
    }

    public String getPara2() {
        return para2;
    }

    public void setPara2(String para2) {
        String old = this.para2;
        this.para2 = para2;
        this.firePropertyChange("para2", old, para2);
    }

    public String getPara3() {
        return para3;
    }

    public void setPara3(String para3) {
        String old = this.para3;
        this.para3 = para3;
        this.firePropertyChange("para3", old, para3);
    }

    public String getPara4() {
        return para4;
    }

    public void setPara4(String para4) {
        String old = this.para4;
        this.para4 = para4;
        this.firePropertyChange("cpara4no", old, para4);
    }

    public String getPara5() {
        return para5;
    }

    public void setPara5(String para5) {
        String old = this.para5;
        this.para5 = para5;
        this.firePropertyChange("para5", old, para5);
    }

    public String getDeptCode_key() {
        return deptCode_key;
    }

    public void setDeptCode_key(String deptCode_key) {
        String old = this.deptCode_key;
        this.deptCode_key = deptCode_key;
        this.firePropertyChange("deptCode_key", old, deptCode_key);
    }

    public String getDept_code2() {
        return dept_code2;
    }

    public void setDept_code2(String dept_code2) {
        String old = this.dept_code2;
        this.dept_code2 = dept_code2;
        this.firePropertyChange("dept_code2", old, dept_code2);
    }

    public String getYm() {
        return ym;
    }

    public void setYm(String ym) {
        String old = this.ym;
        this.ym = ym;
        this.firePropertyChange("ym", old, ym);
    }

    public String getBuserName() {
        return buserName;
    }

    public void setBuserName(String buserName) {
        String old = buserName;
        this.buserName = buserName;
        this.firePropertyChange("buserName", old, buserName);
    }

    public String getBuserNo() {
        return buserNo;
    }

    public void setBuserNo(String buserNo) {
        String old = this.buserNo;
        this.buserNo = buserNo;
        this.firePropertyChange("buserNo", old, buserNo);
    }

    @Transient
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        boolean old = this.checked;
        this.checked = checked;
        this.firePropertyChange("checked", old, checked);
    }

    @Override
    public void assignEntityKey(String key) {
        this.reportNo_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    @Override
    public String toString() {
        return cno;
    }

    public String getR_type() {
        return r_type;
    }

    public void setR_type(String r_type) {
        String old = this.r_type;
        this.r_type = r_type;
        this.firePropertyChange("r_type", old, r_type);
    }

    public String getReportGroup_key() {
        return reportGroup_key;
    }

    public void setReportGroup_key(String reportGroup_key) {
        String old = this.reportGroup_key;
        this.reportGroup_key = reportGroup_key;
        this.firePropertyChange("reportGroup_key", old, reportGroup_key);
    }

    public boolean isNeedCheck() {
        return needCheck;
    }

    public void setNeedCheck(boolean needCheck) {
        boolean old = this.needCheck;
        this.needCheck = needCheck;
        this.firePropertyChange("needCheck", old, needCheck);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReportNo other = (ReportNo) obj;
        if ((this.reportNo_key == null) ? (other.reportNo_key != null) : !this.reportNo_key.equals(other.reportNo_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + (this.reportNo_key != null ? this.reportNo_key.hashCode() : 0);
        return hash;
    }
}
