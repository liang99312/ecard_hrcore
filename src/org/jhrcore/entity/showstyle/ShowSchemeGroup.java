/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.showstyle;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;

/**
 *
 * @author mxliteboss
 */
@Entity
public class ShowSchemeGroup extends Model implements Serializable, IKey, KeyInterface {

    private static final long serialVersionUID = 1L;
    private String showSchemeGroup_key;
    public String field_name;
    public String person_code;
    public String field_group;
    public String entity_name;
    public int order_no = 0;
    public transient int new_flag = 0;

    @Id
    public String getShowSchemeGroup_key() {
        return showSchemeGroup_key;
    }

    public void setShowSchemeGroup_key(String showSchemeGroup_key) {
        this.showSchemeGroup_key = showSchemeGroup_key;
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

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        String old = this.field_name;
        this.field_name = field_name;
        this.firePropertyChange("field_name", old, field_name);
    }

    public String getPerson_code() {
        return person_code;
    }

    public void setPerson_code(String person_code) {
        String old = this.person_code;
        this.person_code = person_code;
        this.firePropertyChange("person_code", old, person_code);
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.showSchemeGroup_key = key;
        this.new_flag = 1;
    }

    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        int old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }

    @Override
    public String toString() {
        return field_name;
    }
}
