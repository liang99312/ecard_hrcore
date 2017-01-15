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

/**
 *
 * @author yangzhou
 */
@Entity
public class RoleFuntion extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    public String role_funtion_key;
    protected Role role;
    protected FuntionRight funtionRight;
    //0:无权限；1：有权限；2：部分权限
    private Integer fun_flag = 0;
    public transient int new_flag = 0;

    @Id
    public String getRole_funtion_key() {
        return role_funtion_key;
    }

    public void setRole_funtion_key(String role_funtion_key) {
        String old = this.role_funtion_key;
        this.role_funtion_key = role_funtion_key;
        this.firePropertyChange("role_funtion_key", old, role_funtion_key);
    }

    public Integer getFun_flag() {
        if (fun_flag == null) {
            return 0;
        }
        return fun_flag;
    }

    public void setFun_flag(Integer fun_flag) {
        Integer old = this.fun_flag;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funtionRight_key")
    public FuntionRight getFuntionRight() {
        return funtionRight;
    }

    public void setFuntionRight(FuntionRight funtionRight) {
        //    FuntionRight old = this.funtionRight;
        this.funtionRight = funtionRight;
        //    this.firePropertyChange("funtionRight", old, funtionRight);
    }

    @Override
    public void assignEntityKey(String key) {
        this.role_funtion_key = key;
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
        final RoleFuntion other = (RoleFuntion) obj;
        if ((this.role_funtion_key == null) ? (other.role_funtion_key != null) : !this.role_funtion_key.equals(other.role_funtion_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.role_funtion_key != null ? this.role_funtion_key.hashCode() : 0);
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
