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
import javax.persistence.OneToOne;
import org.jhrcore.entity.Code;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.FieldAnnotation;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;

/**
 *
 * @author Owner
 */
@Entity
@ClassAnnotation(displayName = "角色编码表", moduleName = "权限管理")
public class RoleCode extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String role_code_key;
    private Role role;
    @FieldAnnotation(visible = true, displayName = "代码名称", editableWhenEdit = false, editableWhenNew = false)
    public Code code;
    @FieldAnnotation(visible = true, displayName = "新增")
    public boolean add_flag = false;
    @FieldAnnotation(visible = true, displayName = "编辑")
    public boolean edit_flag = false;
    @FieldAnnotation(visible = true, displayName = "删除")
    public boolean del_flag = false;
    @FieldAnnotation(visible = true, displayName = "查看")
    public boolean view_flag = false;
    @FieldAnnotation(visible = false, displayName = "查看条件", editableWhenEdit = false, editableWhenNew = false)
    public String right_sql;
    @FieldAnnotation(visible = false, displayName = "编辑条件", editableWhenEdit = false, editableWhenNew = false)
    public String edit_sql;
    @FieldAnnotation(visible = true, displayName = "查看条件", editableWhenEdit = false, editableWhenNew = false)
    public transient String code_limit;
    @FieldAnnotation(visible = true, displayName = "编辑条件", editableWhenEdit = false, editableWhenNew = false)
    public transient String code_limit_edit;
    public transient int new_flag = 0;

    @Id
    public String getRole_code_key() {
        return role_code_key;
    }

    public void setRole_code_key(String role_code_key) {
        String old = this.role_code_key;
        this.role_code_key = role_code_key;
        this.firePropertyChange("role_code_key", old, role_code_key);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_key")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_key")
    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public boolean isAdd_flag() {
        return add_flag;
    }

    public void setAdd_flag(boolean add_flag) {
        boolean old = this.add_flag;
        this.add_flag = add_flag;
        this.firePropertyChange("add_flag", old, add_flag);
    }

    public boolean isDel_flag() {
        return del_flag;
    }

    public void setDel_flag(boolean del_flag) {
        boolean old = this.del_flag;
        this.del_flag = del_flag;
        this.firePropertyChange("del_flag", old, del_flag);
    }

    public boolean isEdit_flag() {
        return edit_flag;
    }

    public void setEdit_flag(boolean edit_flag) {
        boolean old = this.edit_flag;
        this.edit_flag = edit_flag;
        this.firePropertyChange("edit_flag", old, edit_flag);
    }

    public String getRight_sql() {
        return right_sql;
    }

    public void setRight_sql(String right_sql) {
        String old = this.right_sql;
        this.right_sql = right_sql;
        this.firePropertyChange("right_sql", old, right_sql);
    }

    @Transient
    public String getCode_limit_edit() {
        return code_limit_edit;
    }

    public void setCode_limit_edit(String code_limit_edit) {
        this.code_limit_edit = code_limit_edit;
    }

    public String getEdit_sql() {
        return edit_sql;
    }

    public void setEdit_sql(String edit_sql) {
        this.edit_sql = edit_sql;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RoleCode other = (RoleCode) obj;
        if ((this.role_code_key == null) ? (other.role_code_key != null) : !this.role_code_key.equals(other.role_code_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.role_code_key != null ? this.role_code_key.hashCode() : 0);
        return hash;
    }

    @Override
    public void assignEntityKey(String key) {
        this.role_code_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    @Transient
    public String getCode_limit() {
        return code_limit;
    }

    public void setCode_limit(String code_limit) {
        String old = this.code_limit;
        this.code_limit = code_limit;
        this.firePropertyChange("code_limit", old, code_limit);
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

    public boolean isView_flag() {
        return view_flag;
    }

    public void setView_flag(boolean view_flag) {
        boolean old = this.view_flag;
        this.view_flag = view_flag;
        this.firePropertyChange("view_flag", old, view_flag);
    }
}
