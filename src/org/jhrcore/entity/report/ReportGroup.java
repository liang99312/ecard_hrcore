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
@ClassAnnotation(displayName = "套表信息", moduleName = "报表管理")
public class ReportGroup extends Model implements Serializable, KeyInterface, IKey {
    @Id
    public String reportGroup_key;
    @FieldAnnotation(visible = true, displayName = "分组名称", not_null = true)
    public String group_name;
    @FieldAnnotation(visible = true, displayName = "套表名称", not_null = true)
    public String rname;
    @FieldAnnotation(visible = true, displayName = "套表类型", not_null = true)
    @EnumHint(enumList = "月报;季报;年报")
    public String r_type = "月报";
    public transient long new_flag = 0;

    @Override
    public void assignEntityKey(String key) {
        this.reportGroup_key = key;
        this.new_flag = 1;
    }

    @Transient
    @Override
    public long getKey() {
        return this.new_flag;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        String old = this.group_name;
        this.group_name = group_name;
        this.firePropertyChange("group_name", old, group_name);
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
    @Id
    public String getReportGroup_key() {
        return reportGroup_key;
    }

    public void setReportGroup_key(String reportGroup_key) {
        String old = this.reportGroup_key;
        this.reportGroup_key = reportGroup_key;
        this.firePropertyChange("reportGroup_key", old, reportGroup_key);
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        String old = this.rname;
        this.rname = rname;
        this.firePropertyChange("rname", old, rname);
    }

    public String getR_type() {
        return r_type;
    }

    public void setR_type(String r_type) {
        String old = this.r_type;
        this.r_type = r_type;
        this.firePropertyChange("r_type", old, r_type);
    }

    @Override
    public String toString() {
        return rname;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReportGroup other = (ReportGroup) obj;
        if ((this.reportGroup_key == null) ? (other.reportGroup_key != null) : !this.reportGroup_key.equals(other.reportGroup_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.reportGroup_key != null ? this.reportGroup_key.hashCode() : 0);
        return hash;
    }
    
}
