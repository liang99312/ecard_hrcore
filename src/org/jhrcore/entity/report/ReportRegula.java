/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.report;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Column;
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
@ClassAnnotation(displayName = "报表校验规则表", moduleName = "报表管理")
public class ReportRegula extends Model implements Serializable, KeyInterface, IKey {

    public String reportRegula_key;
    @FieldAnnotation(visible = true, displayName = "序号", not_null = true)
    public int order_no;
    @FieldAnnotation(visible = true, displayName = "规则名称", not_null = true)
    public String r_name;
    @FieldAnnotation(visible = true, displayName = "启用", not_null = true)
    public boolean used = false;
    public String r_text;
    public String reportDef_key;
    public transient int new_flag = 0;

    @Id
    public String getReportRegula_key() {
        return reportRegula_key;
    }

    public void setReportRegula_key(String reportRegula_key) {
        String old = this.reportRegula_key;
        this.reportRegula_key = reportRegula_key;
        this.firePropertyChange("reportRegula_key", old, reportRegula_key);
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

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        int old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        String old = this.r_name;
        this.r_name = r_name;
        this.firePropertyChange("r_name", old, r_name);
    }

    @Column(length = 1000)
    public String getR_text() {
        return r_text;
    }

    public void setR_text(String r_text) {
        String old = this.r_text;
        this.r_text = r_text;
        this.firePropertyChange("r_text", old, r_text);
    }

    public String getReportDef_key() {
        return reportDef_key;
    }

    public void setReportDef_key(String reportDef_key) {
        String old = this.reportDef_key;
        this.reportDef_key = reportDef_key;
        this.firePropertyChange("reportDef_key", old, reportDef_key);
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        boolean old = this.used;
        this.used = used;
        this.firePropertyChange("used", old, used);
    }

    @Override
    public void assignEntityKey(String key) {
        this.reportRegula_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }
}
