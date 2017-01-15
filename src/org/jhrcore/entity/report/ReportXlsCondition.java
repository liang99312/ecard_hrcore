/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.report;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@ClassAnnotation(displayName = "字段条件表", moduleName = "报表管理")
public class ReportXlsCondition extends Model implements Serializable, KeyInterface, IKey {

    @FieldAnnotation(visible = false, displayName = "ID")
    public String reportXlsCondition_key;
    @FieldAnnotation(visible = false, displayName = "列信息")
    public ReportXlsScheme reportXlsScheme;
    @FieldAnnotation(visible = true, displayName = "序号")
    public int order_no = 1;
    @FieldAnnotation(visible = true, displayName = "字段名", isEditable = false)
    public String col;
    @FieldAnnotation(visible = true, displayName = "条件")
    @EnumHint(nullable = false, enumList = "=;<;<=;>;>=;!=;like%;%like;%like%")
    public String operator = "=";
    @FieldAnnotation(visible = true, displayName = "条件类型")
    @EnumHint(nullable = false, enumList = "普通;部门")
    public String ui_type = "普通";
    @FieldAnnotation(visible = true, displayName = "排序规则")
    @EnumHint(nullable = false, enumList = "升序;降序")
    public String order_type = "升序";
    public String condition_type = "CONDITION";//CONDITION:条件;ORDER:排序
    public transient int new_flag = 0;

    @Id
    public String getReportXlsCondition_key() {
        return reportXlsCondition_key;
    }

    public void setReportXlsCondition_key(String reportXlsCondition_key) {
        this.reportXlsCondition_key = reportXlsCondition_key;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportXlsScheme_key")
    public ReportXlsScheme getReportXlsScheme() {
        return reportXlsScheme;
    }

    public void setReportXlsScheme(ReportXlsScheme reportXlsScheme) {
        this.reportXlsScheme = reportXlsScheme;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getCondition_type() {
        return condition_type;
    }

    public void setCondition_type(String condition_type) {
        this.condition_type = condition_type;
    }

    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getUi_type() {
        return ui_type;
    }

    public void setUi_type(String ui_type) {
        this.ui_type = ui_type;
    }

    @Override
    public void assignEntityKey(String key) {
        this.reportXlsCondition_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }
}
