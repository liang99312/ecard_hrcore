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

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "显示方案排序字段表", moduleName = "系统维护")
public class ShowSchemeOrder extends Model implements Serializable, IKey, KeyInterface {

    private static final long serialVersionUID = 1L;
    public String showSchemeOrder_key;
    public String field_name;
    public String field_caption;
    public String field_order;
    private String entity_name;
    public int order_no = 0;
    private ShowScheme showScheme;
    public transient int new_flag = 0;

    @Id
    public String getShowSchemeOrder_key() {
        return showSchemeOrder_key;
    }

    public void setShowSchemeOrder_key(String showSchemeOrder_key) {
        String old = this.showSchemeOrder_key;
        this.showSchemeOrder_key = showSchemeOrder_key;
        this.firePropertyChange("showSchemeOrder_key", old, showSchemeOrder_key);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ShowSchemeOrder other = (ShowSchemeOrder) obj;
        if ((this.showSchemeOrder_key == null) ? (other.showSchemeOrder_key != null) : !this.showSchemeOrder_key.equals(other.showSchemeOrder_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.showSchemeOrder_key != null ? this.showSchemeOrder_key.hashCode() : 0);
        return hash;
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

    public String getField_order() {
        return field_order;
    }

    public void setField_order(String field_order) {
        String old = this.field_order;
        this.field_order = field_order;
        this.firePropertyChange("field_order", old, field_order);
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        int old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }

    @ManyToOne
    @JoinColumn(name = "showScheme_key")
    public ShowScheme getShowScheme() {
        return showScheme;
    }

    public void setShowScheme(ShowScheme showScheme) {
        this.showScheme = showScheme;
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        String old = this.entity_name;
        this.entity_name = entity_name;
        this.firePropertyChange("entity_name", old, entity_name);
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.new_flag = 1;
        this.showSchemeOrder_key = key;
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
