/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;
import java.util.Hashtable;
import org.jhrcore.entity.base.TempFieldInfo;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "导出方案表", moduleName = "系统维护")
public class ExportScheme extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String exportScheme_key;
    private String scheme_name;
    private String person_code;
    public boolean order_flag = false;
    public boolean plus_flag = false;
    public String scheme_titile;
    private List<ExportDetail> exportDetails = new ArrayList<ExportDetail>();
    private String entity_name;
    private transient Hashtable<String, TempFieldInfo> classMap = new Hashtable<String, TempFieldInfo>();
    public transient int new_flag = 0;

    @Id
    public String getExportScheme_key() {
        return exportScheme_key;
    }

    public void setExportScheme_key(String exportScheme_key) {
        String old = this.exportScheme_key;
        this.exportScheme_key = exportScheme_key;
        this.firePropertyChange("exportScheme_key", old, exportScheme_key);
    }

    public String getPerson_code() {
        return person_code;
    }

    public void setPerson_code(String person_code) {
        String old = this.person_code;
        this.person_code = person_code;
        this.firePropertyChange("person_code", old, person_code);
    }

    public String getScheme_name() {
        return scheme_name;
    }

    public void setScheme_name(String scheme_name) {
        String old = this.scheme_name;
        this.scheme_name = scheme_name;
        this.firePropertyChange("scheme_name", old, scheme_name);
    }

    @OneToMany(mappedBy = "exportScheme", fetch = FetchType.LAZY)
    @OrderBy("order_no")
    public List<ExportDetail> getExportDetails() {
        if (exportDetails == null) {
            return new ArrayList();
        }
        return exportDetails;
    }

    public void setExportDetails(List<ExportDetail> exportDetails) {
        this.exportDetails = exportDetails;
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        String old = this.entity_name;
        this.entity_name = entity_name;
        this.firePropertyChange("entity_name", old, entity_name);
    }

    public String getScheme_titile() {
        return scheme_titile;
    }

    public void setScheme_titile(String scheme_titile) {
        String old = this.scheme_titile;
        this.scheme_titile = scheme_titile;
        this.firePropertyChange("scheme_titile", old, scheme_titile);
    }

    @Override
    public String toString() {
        return scheme_name;
    }

    @Override
    public void assignEntityKey(String key) {
        this.exportScheme_key = key;
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
        final ExportScheme other = (ExportScheme) obj;
        if ((this.exportScheme_key == null) ? (other.exportScheme_key != null) : !this.exportScheme_key.equals(other.exportScheme_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + (this.exportScheme_key != null ? this.exportScheme_key.hashCode() : 0);
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

    public boolean isOrder_flag() {
        return order_flag;
    }

    public void setOrder_flag(boolean order_flag) {
        boolean old = this.order_flag;
        this.order_flag = order_flag;
        this.firePropertyChange("order_flag", old, order_flag);
    }

    public boolean isPlus_flag() {
        return plus_flag;
    }

    public void setPlus_flag(boolean plus_flag) {
        boolean old = this.plus_flag;
        this.plus_flag = plus_flag;
        this.firePropertyChange("plus_flag", old, plus_flag);
    }

    @Transient
    public Hashtable<String, TempFieldInfo> getClassMap() {
        return classMap;
    }

    public void setClassMap(Hashtable<String, TempFieldInfo> classMap) {
        this.classMap = classMap;
    }
}
