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
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author lenovo
 */
@Entity
@ClassAnnotation(displayName = "套表报表信息", moduleName = "报表管理")
public class ReportRef extends Model implements Serializable, KeyInterface, IKey {

    public String reportRef_key;
    @FieldAnnotation(visible = true, displayName = "序号", not_null = true)
    public int order_no;
    @FieldAnnotation(visible = true, displayName = "报表类型", not_null = true)
    @EnumHint(enumList = "月报;季报;年报")
    public String r_type;
    @FieldAnnotation(visible = false, displayName = "报表主键", not_null = true)
    public String reportDef_key;
    @FieldAnnotation(visible = false, displayName = "套表", not_null = true)
    public String reportGroup_key;
    public transient long new_flag = 0;

    @Id
    public String getReportRef_key() {
        return reportRef_key;
    }

    public void setReportRef_key(String reportRef_key) {
        String old = this.reportRef_key;
        this.reportRef_key = reportRef_key;
        this.firePropertyChange("reportRef_key", old, reportRef_key);
    }

    @Override
    public void assignEntityKey(String key) {
        this.reportRef_key = key;
        this.new_flag = 1;
    }

    @Transient
    @Override
    public long getKey() {
        return new_flag;
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

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        int old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }

    public String getReportDef_key() {
        return reportDef_key;
    }

    public void setReportDef_key(String reportDef_key) {
        String old = this.reportDef_key;
        this.reportDef_key = reportDef_key;
        this.firePropertyChange("reportDef_key", old, reportDef_key);
    }

    public String getReportGroup_key() {
        return reportGroup_key;
    }

    public void setReportGroup_key(String reportGroup_key) {
        String old = this.reportGroup_key;
        this.reportGroup_key = reportGroup_key;
        this.firePropertyChange("reportGroup_key", old, reportGroup_key);
    }

    public String getR_type() {
        return r_type;
    }

    public void setR_type(String r_type) {
        String old = this.r_type;
        this.r_type = r_type;
        this.firePropertyChange("r_type", old, r_type);
    }
}
