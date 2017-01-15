/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "导出方案对应字段表", moduleName = "系统维护")
public class ExportDetail extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String exportDetail_key;
    public String field_name;
    public String field_caption;
    public String entity_name;
    public String entity_caption;
    public String field_type;
    public String format;
    public Integer order_no = 0;
    public ExportScheme exportScheme;
    public transient int new_flag = 0;

    @Id
    public String getExportDetail_key() {
        return exportDetail_key;
    }

    public void setExportDetail_key(String exportDetail_key) {
        String old = this.exportDetail_key;
        this.exportDetail_key = exportDetail_key;
        this.firePropertyChange("exportDetail_key", old, exportDetail_key);
    }

    public String getField_caption() {
        return field_caption;
    }

    public void setField_caption(String field_caption) {
        String old = this.field_caption;
        this.field_caption = field_caption;
        this.firePropertyChange("field_caption", old, field_caption);
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        String old = this.field_name;
        this.field_name = field_name;
        this.firePropertyChange("field_name", old, field_name);
    }

    public Integer getOrder_no() {
        if (order_no == null) {
            return 0;
        }
        return order_no;
    }

    public void setOrder_no(Integer order_no) {
        Integer old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exportScheme_key")
    public ExportScheme getExportScheme() {
        return exportScheme;
    }

    public void setExportScheme(ExportScheme exportScheme) {
        this.exportScheme = exportScheme;
    }

    @Override
    public String toString() {
        return field_caption;
    }

    public String getEntity_caption() {
        return entity_caption;
    }

    public void setEntity_caption(String entity_caption) {
        String old = this.entity_caption;
        this.entity_caption = entity_caption;
        this.firePropertyChange("entity_caption", old, entity_caption);
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        String old = this.entity_name;
        this.entity_name = entity_name;
        this.firePropertyChange("entity_name", old, entity_name);
    }

    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        String old = this.field_type;
        this.field_type = field_type;
        this.firePropertyChange("field_type", old, field_type);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        String old = this.format;
        this.format = format;
        this.firePropertyChange("format", old, format);
    }

    @Override
    public void assignEntityKey(String key) {
        this.exportDetail_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExportDetail other = (ExportDetail) obj;
        if ((this.exportDetail_key == null) ? (other.exportDetail_key != null) : !this.exportDetail_key.equals(other.exportDetail_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.exportDetail_key != null ? this.exportDetail_key.hashCode() : 0);
        return hash;
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
}
