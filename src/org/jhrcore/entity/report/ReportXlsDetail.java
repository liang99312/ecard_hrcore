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
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "字段表", moduleName = "报表管理")
public class ReportXlsDetail extends Model implements Serializable, KeyInterface, IKey {

    @FieldAnnotation(visible = false, displayName = "ID")
    public String reportXlsDetail_key;
    @FieldAnnotation(visible = true, displayName = "主键")
    public boolean id_flag;
    @FieldAnnotation(visible = true, displayName = "字段名", isEditable = false)
    public String col;
    @FieldAnnotation(visible = true, displayName = "字段描述")
    public String col_name;
    @FieldAnnotation(visible = false, displayName = "表字段类型")
    public String col_type;
    @FieldAnnotation(visible = false, displayName = "排序值")
    public int order_no;
    @FieldAnnotation(visible = false, displayName = "数据长度")
    public int col_len;
    @FieldAnnotation(visible = false, displayName = "小数点后数据精度")
    public int col_scale;
    @FieldAnnotation(visible = false, displayName = "是否启用")
    public boolean used = false;
    @FieldAnnotation(visible = false, displayName = "所属方案表主键")
    public ReportXlsScheme reportXlsScheme;
    public transient long new_flag = 0;

    @Id
    public String getReportXlsDetail_key() {
        return reportXlsDetail_key;
    }

    public void setReportXlsDetail_key(String reportXlsDetail_key) {
        this.reportXlsDetail_key = reportXlsDetail_key;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportXlsScheme_key")
    public ReportXlsScheme getReportXlsScheme() {
        return reportXlsScheme;
    }

    public void setReportXlsScheme(ReportXlsScheme reportXlsScheme) {
        this.reportXlsScheme = reportXlsScheme;
    }

    @Override
    public void assignEntityKey(String key) {
        this.reportXlsDetail_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public int getCol_len() {
        return col_len;
    }

    public void setCol_len(int col_len) {
        this.col_len = col_len;
    }

    public String getCol_name() {
        return col_name;
    }

    public void setCol_name(String col_name) {
        this.col_name = col_name;
    }

    public int getCol_scale() {
        return col_scale;
    }

    public void setCol_scale(int col_scale) {
        this.col_scale = col_scale;
    }

    public String getCol_type() {
        return col_type;
    }

    public void setCol_type(String col_type) {
        this.col_type = col_type;
    }

    public boolean isId_flag() {
        return id_flag;
    }

    public void setId_flag(boolean id_flag) {
        this.id_flag = id_flag;
    }

    @Transient
    public long getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(long new_flag) {
        this.new_flag = new_flag;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
