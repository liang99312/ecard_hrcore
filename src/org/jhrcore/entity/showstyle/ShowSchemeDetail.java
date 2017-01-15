/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.showstyle;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author Administrator
 */
@Entity
@ClassAnnotation(displayName = "显示方案字段表", moduleName = "系统维护")
public class ShowSchemeDetail extends Model implements Serializable, IKey, KeyInterface {

    private static final long serialVersionUID = 1L;
    public String showSchemeDetail_key;
    public String field_name;
    public transient String field_caption;
    public String entity_name;
    public transient String entity_caption;
    public int order_no = 0;
    private ShowScheme showScheme;
    public String field_group;
    public transient int new_flag = 0;

    @Id
    public String getShowSchemeDetail_key() {
        return showSchemeDetail_key;
    }

    public void setShowSchemeDetail_key(String showSchemeDetail_key) {
        String old = this.showSchemeDetail_key;
        this.showSchemeDetail_key = showSchemeDetail_key;
        this.firePropertyChange("showSchemeDetail_key", old, showSchemeDetail_key);
    }

    @ManyToOne
    @JoinColumn(name = "showScheme_key")
    public ShowScheme getShowScheme() {
        return showScheme;
    }

    public void setShowScheme(ShowScheme showScheme) {
        this.showScheme = showScheme;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ShowSchemeDetail other = (ShowSchemeDetail) obj;
        if ((this.showSchemeDetail_key == null) ? (other.showSchemeDetail_key != null) : !this.showSchemeDetail_key.equals(other.showSchemeDetail_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.showSchemeDetail_key != null ? this.showSchemeDetail_key.hashCode() : 0);
        return hash;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        int old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }

    @Transient
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

    @Transient
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

    public String getField_group() {
        return field_group;
    }

    public void setField_group(String field_group) {
        String old = this.field_group;
        this.field_group = field_group;
        this.firePropertyChange("field_group", old, field_group);
    }

    @Override
    public String toString() {
        return field_caption;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.new_flag = 1;
        this.showSchemeDetail_key = key;
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
