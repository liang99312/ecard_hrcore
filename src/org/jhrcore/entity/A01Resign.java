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
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;

@Entity
@ClassAnnotation(displayName = "员工离职申请表", moduleName = "人事管理")
public class A01Resign extends Model implements Serializable, IKey, KeyInterface {

    @FieldAnnotation(visible = false, displayName = "ID", not_null = true)
    public String a01Resign_key;
    @FieldAnnotation(visible = false, displayName = "状态")
    @EnumHint(enumList="未提交;已提交")
    public String re_state = "未提交";
    @FieldAnnotation(visible = true, displayName = "填表时间")
    public Date apply_date;
    @FieldAnnotation(visible = true, displayName = "离职日期")
    public Date resign_date;
    @FieldAnnotation(visible = false, displayName = "部门代码")
    public String dept_code;
    @FieldAnnotation(visible = true, displayName = "部门名称")
    public String content;    
    @FieldAnnotation(visible = false, displayName = "部门key")
    public String deptCode_key;    
    @FieldAnnotation(visible = true, displayName = "申请人")
    public String a0101;   
    @FieldAnnotation(visible = true, displayName = "人员编号")
    public String a0190;
    @FieldAnnotation(visible = false, displayName = "员工ID")
    public String a01_key;
    @FieldAnnotation(visible = true, displayName = "离职原因")
    public String re_reason;        
    public transient int new_flag = 0;

    public String getRe_state() {
        return re_state;
    }

    public void setRe_state(String re_state) {
        this.re_state = re_state;
    }
    
    public Date getResign_date() {
        return resign_date;
    }

    public void setResign_date(Date resign_date) {
        this.resign_date = resign_date;
    }

    public String getRe_reason() {
        return re_reason;
    }

    public void setRe_reason(String re_reason) {
        this.re_reason = re_reason;
    }
    
    public String getA0101() {
        return a0101;
    }

    public void setA0101(String a0101) {
        this.a0101 = a0101;
    }

    public String getA0190() {
        return a0190;
    }

    public void setA0190(String a0190) {
        this.a0190 = a0190;
    }

    @Id
    public String getA01Resign_key() {
        return a01Resign_key;
    }

    public void setA01Resign_key(String a01Resign_key) {
        this.a01Resign_key = a01Resign_key;
    }

    public Date getApply_date() {
        return apply_date;
    }

    public void setApply_date(Date apply_date) {
        this.apply_date = apply_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDeptCode_key() {
        return deptCode_key;
    }

    public void setDeptCode_key(String deptCode_key) {
        this.deptCode_key = deptCode_key;
    }

    public String getDept_code() {
        return dept_code;
    }

    public void setDept_code(String dept_code) {
        this.dept_code = dept_code;
    }

    public String getA01_key() {
        return a01_key;
    }

    public void setA01_key(String a01_key) {
        String old = this.a01_key;
        this.a01_key = a01_key;
        this.firePropertyChange("a01_key", old, a01_key);
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
    @Transient
    public long getKey() {
        return new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.a01Resign_key = key;
        this.new_flag = 1;
    }
}
