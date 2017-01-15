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
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import javax.persistence.Transient;

/**
 *
 * @author yangzhou
 */
@Entity
public class RoleDept extends Model implements Serializable, KeyInterface, IKey {

    public String roleDept_key;
    private RoleA01 roleA01;
    private DeptCode deptCode;
    public int fun_flag = 0;
    public transient int new_flag = 0;

    @Id
    public String getRoleDept_key() {
        return roleDept_key;
    }

    public void setRoleDept_key(String roleDept_key) {
        String old = this.roleDept_key;
        this.roleDept_key = roleDept_key;
        this.firePropertyChange("roleDept_key", old, roleDept_key);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deptCode_key")
    public DeptCode getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(DeptCode deptCode) {
        this.deptCode = deptCode;
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
    @JoinColumn(name = "roleA01_key")
    public RoleA01 getRoleA01() {
        return roleA01;
    }

    public void setRoleA01(RoleA01 roleA01) {
        this.roleA01 = roleA01;
    }

    @Override
    public void assignEntityKey(String key) {
        this.roleDept_key = key;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RoleDept other = (RoleDept) obj;
        if ((this.roleDept_key == null) ? (other.roleDept_key != null) : !this.roleDept_key.equals(other.roleDept_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.roleDept_key != null ? this.roleDept_key.hashCode() : 0);
        return hash;
    }
    
}
