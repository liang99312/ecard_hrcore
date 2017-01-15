/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.right;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.FieldAnnotation;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;

/**
 *
 * @author Administrator
 */
@Entity
@ClassAnnotation(displayName = "功能菜单表", moduleName = "系统维护")
public class FuntionRight extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String funtionRight_key;
    @FieldAnnotation(visible = true, displayName = "功能编码", groupName = "基本信息", isEditable = true)
    public String fun_code;
    @FieldAnnotation(visible = true, displayName = "功能父编码", groupName = "基本信息", isEditable = true)
    public String fun_parent_code;
    public Integer fun_level = 0;
    @FieldAnnotation(visible = true, displayName = "功能名称", groupName = "基本信息")
    public String fun_name;
    @FieldAnnotation(visible = false, displayName = "当前方言", groupName = "基本信息")
    public String fun_language;
    @FieldAnnotation(visible = true, displayName = "模块标识", groupName = "基本信息", isEditable = true)
    public String fun_module_flag;
    @FieldAnnotation(visible = true, displayName = "是否业务模块", groupName = "基本信息", isEditable = true)
    public boolean module_flag = false;
    @FieldAnnotation(visible = true, displayName = "是否显示", groupName = "基本信息", isEditable = true)
    public boolean visible = true;
    @FieldAnnotation(visible = false, displayName = "授权标识", groupName = "基本信息", isEditable = false)
    public boolean granted = false;
    private Set<RoleFuntion> roleFuntions;
    //0:无权限；1：有权限；2：部分权限
    public transient int fun_flag = 0;
    public transient int new_flag = 0;

    @Id
    public String getFuntionRight_key() {
        return funtionRight_key;
    }

    public void setFuntionRight_key(String funtionRight_key) {
        String old = this.funtionRight_key;
        this.funtionRight_key = funtionRight_key;
        this.firePropertyChange("funtionRight_key", old, funtionRight_key);
    }

    @Transient
    public int getFun_flag() {
        return fun_flag;
    }

    public void setFun_flag(int fun_flag) {
        int old = this.fun_flag;
        this.fun_flag = fun_flag;
        this.firePropertyChange("fun_flag", old, fun_flag);
    }

    public String getFun_code() {
        return fun_code;
    }

    public void setFun_code(String fun_code) {
        String old = this.fun_code;
        this.fun_code = fun_code;
        this.firePropertyChange("fun_code", old, fun_code);
    }

    public String getFun_name() {
        return fun_name;
    }

    public void setFun_name(String fun_name) {
        String old = this.fun_name;
        this.fun_name = fun_name;
        this.firePropertyChange("fun_name", old, fun_name);
    }

    public String getFun_parent_code() {
        return fun_parent_code;
    }

    public void setFun_parent_code(String fun_parent_code) {
        String old = this.fun_parent_code;
        this.fun_parent_code = fun_parent_code;
        this.firePropertyChange("fun_parent_code", old, fun_parent_code);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funtionRight", fetch = FetchType.LAZY)
    public Set<RoleFuntion> getRoleFuntions() {
        return roleFuntions;
    }

    public void setRoleFuntions(Set<RoleFuntion> roleFuntions) {
        //    Set<RoleFuntion> old = this.roleFuntions;
        this.roleFuntions = roleFuntions;
        //    this.firePropertyChange("roleFuntions", old, roleFuntions);
    }

    public String getFun_module_flag() {
        return fun_module_flag;
    }

    public void setFun_module_flag(String fun_module_flag) {
        String old = this.fun_module_flag;
        this.fun_module_flag = fun_module_flag;
        this.firePropertyChange("fun_module_flag", old, fun_module_flag);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FuntionRight other = (FuntionRight) obj;
        if ((this.funtionRight_key == null) ? (other.funtionRight_key != null) : !this.funtionRight_key.equals(other.funtionRight_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.funtionRight_key != null ? this.funtionRight_key.hashCode() : 0);
        return hash;
    }

    public Integer getFun_level() {
        if (fun_level == null) {
            return 0;
        }
        return fun_level;
    }

    public void setFun_level(Integer fun_level) {
        Integer old = this.fun_level;
        this.fun_level = fun_level;
        this.firePropertyChange("fun_level", old, fun_level);
    }

    @Override
    public String toString() {
        return fun_name;
        // return "org.jhrcore.entity.right.FuntionRight[id=" + funtionRight_id + "]";
    }

    @Override
    public void assignEntityKey(String key) {
        this.funtionRight_key = key;
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

    public boolean isGranted() {
        return granted;
    }

    public void setGranted(boolean granted) {
        boolean old = this.granted;
        this.granted = granted;
        this.firePropertyChange("granted", old, granted);
    }

    public String getFun_language() {
        return fun_language;
    }

    public void setFun_language(String fun_language) {
        String old = this.fun_language;
        this.fun_language = fun_language;
        this.firePropertyChange("fun_language", old, fun_language);
    }

    public boolean isModule_flag() {
        return module_flag;
    }

    public void setModule_flag(boolean module_flag) {
        boolean old = this.module_flag;
        this.module_flag = module_flag;
        this.firePropertyChange("module_flag", old, module_flag);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        boolean old = this.visible;
        this.visible = visible;
        this.firePropertyChange("visible", old, visible);
    }
}
