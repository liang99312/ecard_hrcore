/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.right.RoleA01;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "员工个人密码表", moduleName = "人事管理")
public class A01PassWord extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String a01PassWord_key;
    public A01 a01;
    @FieldAnnotation(visible = false, displayName = "个人密码")
    public String pswd;
    @FieldAnnotation(visible = true, displayName = "适用UKey")
    @EnumHint(enumList = "是;否")
    public String use_ukey = "否";
    @FieldAnnotation(visible = false, displayName = "创建时间")
    public Date c_date;
    @FieldAnnotation(visible = true, displayName = "认证方式")
    @EnumHint(enumList = "密码;指纹;密码+指纹")
    public String load_type = "密码";
    @FieldAnnotation(visible = true, displayName = "是否采集指纹",isEditable=false)
    @EnumHint(enumList = "是;否")
    public String f_flag = "否";
//    @FieldAnnotation(visible = false)
//    private String role_key;
    private Set<RoleA01> roleA01s;
    public transient int new_flag = 0;

    @Id
    public String getA01PassWord_key() {
        return a01PassWord_key;
    }

    public void setA01PassWord_key(String a01PassWord_key) {
        String old = this.a01PassWord_key;
        this.a01PassWord_key = a01PassWord_key;
        this.firePropertyChange("a01PassWord_key", old, a01PassWord_key);
    }

    @Override
    public String toString() {
        return a01.getA0190() + "-" + a01.getA0101();
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "a01_key")
    public A01 getA01() {
        return a01;
    }

    public void setA01(A01 a01) {
        this.a01 = a01;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        String old = this.pswd;
        this.pswd = pswd;
        this.firePropertyChange("pswd", old, pswd);
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

//    public String getRole_key() {
//        return role_key;
//    }
//
//    public void setRole_key(String role_key) {
//        String old = this.role_key;
//        this.role_key = role_key;
//        this.firePropertyChange("role_key", old, role_key);
//    }

    @Override
    public void assignEntityKey(String key) {
        this.a01PassWord_key = key;
        new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final A01PassWord other = (A01PassWord) obj;
        if ((this.a01PassWord_key == null) ? (other.a01PassWord_key != null) : !this.a01PassWord_key.equals(other.a01PassWord_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.a01PassWord_key != null ? this.a01PassWord_key.hashCode() : 0);
        return hash;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "a01PassWord", fetch = FetchType.LAZY)
    public Set<RoleA01> getRoleA01s() {
        return roleA01s;
    }

    public void setRoleA01s(Set<RoleA01> roleA01s) {
        this.roleA01s = roleA01s;
    }

    public String getUse_ukey() {
        return use_ukey;
    }

    public void setUse_ukey(String use_ukey) {
        String old = this.use_ukey;
        this.use_ukey = use_ukey;
        this.firePropertyChange("use_ukey", old, use_ukey);
    }

    public Date getC_date() {
        return c_date;
    }

    public void setC_date(Date c_date) {
        Date old = this.c_date;
        this.c_date = c_date;
        this.firePropertyChange("c_date", old, c_date);
    }

    public String getLoad_type() {
        return load_type;
    }

    public void setLoad_type(String load_type) {
        String old = this.load_type;
        this.load_type = load_type;
        this.firePropertyChange("load_type", old, load_type);
    }

    public String getF_flag() {
        return f_flag;
    }

    public void setF_flag(String f_flag) {
        String old = this.f_flag;
        this.f_flag = f_flag;
        this.firePropertyChange("f_flag", old, f_flag);
    }
    
}
