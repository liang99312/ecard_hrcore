/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.report;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.FieldAnnotation;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "报表字段表", moduleName = "报表管理")
public class ReportField extends Model implements Serializable, IKey, KeyInterface {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String reportField_key;
    @FieldAnnotation(visible = false)
    public ReportDef reportDef;
    @FieldAnnotation(visible = false)
    public String entity_name;
    @FieldAnnotation(visible = true, displayName = "表名称", groupName = "基本信息", isEditable = false)
    public String entity_caption;
    @FieldAnnotation(visible = false)
    public String field_name;
    @FieldAnnotation(visible = true, displayName = "字段名称", groupName = "基本信息", isEditable = false)
    public String field_caption;
    @FieldAnnotation(visible = false, displayName = "字段描述", groupName = "基本信息", isEditable = false)
    public String field_desc;
    // 从查询的实体开始，到包含该属性的实体，所有实体名，用分号分开。如"BasePerson;Dept;"
    private String entityNames;
    // 表示该字段与所属实体之间的关系，直到查询实体，用分号分开。如“2；1”，1：普通属性；2：实体属性；3：集合属性
    private String fieldClasses;
    // 统计方式
    private String stat_type = "普通";
    // 排序方式
    private String order_type = "不排序";
    public transient int new_flag = 0;

    @Id
    public String getReportField_key() {
        return reportField_key;
    }

    public void setReportField_key(String reportField_key) {
        String old = this.reportField_key;
        this.reportField_key = reportField_key;
        this.firePropertyChange("reportField_key", old, reportField_key);
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        String old = this.order_type;
        this.order_type = order_type;
        this.firePropertyChange("order_type", old, order_type);
    }

    public String getStat_type() {
        return stat_type;
    }

    public void setStat_type(String stat_type) {
        String old = this.stat_type;
        this.stat_type = stat_type;
        this.firePropertyChange("stat_type", old, stat_type);
    }

    public String getField_desc() {
        return field_desc;
    }

    public void setField_desc(String field_desc) {
        String old = this.field_desc;
        this.field_desc = field_desc;
        this.firePropertyChange("field_desc", old, field_desc);
    }

    public String getEntityNames() {
        return entityNames;
    }

    public void setEntityNames(String entityNames) {
        String old = this.entityNames;
        this.entityNames = entityNames;
        this.firePropertyChange("entityNames", old, entityNames);
    }

    public String getFieldClasses() {
        return fieldClasses;
    }

    public void setFieldClasses(String fieldClasses) {
        String old = this.fieldClasses;
        this.fieldClasses = fieldClasses;
        this.firePropertyChange("fieldClasses", old, fieldClasses);
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

    public String buildSelectStr() {
        return "ed." + this.field_name;
    }

    @ManyToOne
    @JoinColumn(name = "reportDef_key")
    public ReportDef getReportDef() {
        return reportDef;
    }

    public void setReportDef(ReportDef reportDef) {
        //    ReportDef old = this.reportDef;
        this.reportDef = reportDef;
        //    this.firePropertyChange("reportDef", old, reportDef);
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

    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.new_flag = 1;
        this.reportField_key = key;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReportField other = (ReportField) obj;
        if ((this.reportField_key == null) ? (other.reportField_key != null) : !this.reportField_key.equals(other.reportField_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.reportField_key != null ? this.reportField_key.hashCode() : 0);
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
