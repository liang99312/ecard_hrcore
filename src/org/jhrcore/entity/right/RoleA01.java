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
import javax.persistence.Transient;
import org.jhrcore.entity.A01PassWord;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;

/**
 *
 * @author hflj
 */
@Entity
@ClassAnnotation(displayName="角色人员对应表",moduleName="权限管理")
public class RoleA01 extends Model implements Serializable  ,KeyInterface,IKey{
    public String roleA01_key;
    public Role role;
    public A01PassWord a01PassWord;
    private transient int new_flag = 0;
    @Id
    public String getRoleA01_key() {
        return roleA01_key;
    }

    public void setRoleA01_key(String roleA01_key) {
        this.roleA01_key = roleA01_key;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "a01PassWord_key")
    public A01PassWord getA01PassWord() {
        return a01PassWord;
    }

    public void setA01PassWord(A01PassWord a01PassWord) {
        this.a01PassWord = a01PassWord;
    }
    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_key")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    
    @Override
    public void assignEntityKey(String key) {
        this.roleA01_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }
    
}
