/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.right;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author yangzhou
 */
@Entity
public class RoleField extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    public String role_field_key;
    private Role role;
    private String field_name;
    private String entity_name;
    public int fun_flag = 0;
    public transient int new_flag = 0;

    @Id
    public String getRole_field_key() {
        return role_field_key;
    }

    public void setRole_field_key(String role_field_key) {
        String old = this.role_field_key;
        this.role_field_key = role_field_key;
        this.firePropertyChange("role_field_key", old, role_field_key);
    }

    public int getFun_flag() {
        return fun_flag;
    }

    public void setFun_flag(int fun_flag) {
        int old = this.fun_flag;
        this.fun_flag = fun_flag;
        this.firePropertyChange("fun_flag", old, fun_flag);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_key")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        //    Role old = this.role;
        this.role = role;
        //    this.firePropertyChange("role", old, role);
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        String old = this.field_name;
        this.field_name = field_name;
        this.firePropertyChange("field_name", old, field_name);
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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RoleField other = (RoleField) obj;
        if ((this.role_field_key == null) ? (other.role_field_key != null) : !this.role_field_key.equals(other.role_field_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (this.role_field_key != null ? this.role_field_key.hashCode() : 0);
        return hash;
    }

    @Override
    public void assignEntityKey(String key) {
        this.role_field_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
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
