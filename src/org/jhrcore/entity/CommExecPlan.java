/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "执行计划表", moduleName = "系统维护")
public class CommExecPlan extends Model implements Serializable, KeyInterface, IKey {

    @FieldAnnotation(visible = false, displayName = "ID")
    public String commExecPlan_key;
    @FieldAnnotation(visible = true, displayName = "计划名称")
    public String plan_name;
    @FieldAnnotation(visible = true, displayName = "频率")
    @EnumHint(enumList = "每月;每周;每日")
    public String cyc_type = "每月";
    @FieldAnnotation(visible = true, displayName = "执行间隔")
    public Integer cyc_gap = 1;
    @FieldAnnotation(visible = true, displayName = "执行明细")
    public String cyc_gapmore;
    @FieldAnnotation(visible = true, displayName = "开始日期", format = "yyyy-MM-dd")
    public Date cyc_start;
    @FieldAnnotation(visible = true, displayName = "结束日期", format = "yyyy-MM-dd")
    //为null时表示无结束日期
    public Date cyc_end;
    @FieldAnnotation(visible = true, displayName = "每天频率")
    @EnumHint(enumList = "执行一次;执行周期")
    public String day_type = "执行一次";
    @FieldAnnotation(visible = true, displayName = "每天执行间隔")
    public Integer day_gap = 1;
    @FieldAnnotation(visible = true, displayName = "每天执行间隔时间单位")
    @EnumHint(enumList = "小时;分钟")
    public String day_gapunit = "小时";
    @FieldAnnotation(visible = true, displayName = "每天执行开始时间")
    public Date day_start;
    @FieldAnnotation(visible = true, displayName = "每天执行结束时间")
    public Date day_end;
    @FieldAnnotation(visible = true, displayName = "计划说明")
    @Column(length = 1000)
    public String plan_mark;
    public transient int new_flag = 0;

    @Id
    public String getCommExecPlan_key() {
        return commExecPlan_key;
    }

    public void setCommExecPlan_key(String commExecPlan_key) {
        this.commExecPlan_key = commExecPlan_key;
    }

    public Date getCyc_end() {
        return cyc_end;
    }

    public void setCyc_end(Date cyc_end) {
        this.cyc_end = cyc_end;
    }

    public Integer getCyc_gap() {
        return cyc_gap;
    }

    public void setCyc_gap(Integer cyc_gap) {
        this.cyc_gap = cyc_gap;
    }

    public String getCyc_gapmore() {
        return cyc_gapmore;
    }

    public void setCyc_gapmore(String cyc_gapmore) {
        this.cyc_gapmore = cyc_gapmore;
    }

    public Date getCyc_start() {
        return cyc_start;
    }

    public void setCyc_start(Date cyc_start) {
        this.cyc_start = cyc_start;
    }

    public String getCyc_type() {
        return cyc_type;
    }

    public void setCyc_type(String cyc_type) {
        this.cyc_type = cyc_type;
    }

    public Date getDay_end() {
        return day_end;
    }

    public void setDay_end(Date day_end) {
        this.day_end = day_end;
    }

    public Integer getDay_gap() {
        return day_gap;
    }

    public void setDay_gap(Integer day_gap) {
        this.day_gap = day_gap;
    }

    public String getDay_gapunit() {
        return day_gapunit;
    }

    public void setDay_gapunit(String day_gapunit) {
        this.day_gapunit = day_gapunit;
    }

    public Date getDay_start() {
        return day_start;
    }

    public void setDay_start(Date day_start) {
        this.day_start = day_start;
    }

    public String getDay_type() {
        return day_type;
    }

    public void setDay_type(String day_type) {
        this.day_type = day_type;
    }

    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }

    public String getPlan_mark() {
        return plan_mark;
    }

    public void setPlan_mark(String plan_mark) {
        this.plan_mark = plan_mark;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    @Override
    public void assignEntityKey(String key) {
        this.commExecPlan_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }
}
