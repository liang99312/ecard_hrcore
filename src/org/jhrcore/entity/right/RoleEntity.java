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
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.FieldAnnotation;
import org.jhrcore.entity.base.EntityDef;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;

/**
 *
 * @author DB2INST3
 */
@Entity
@ClassAnnotation(displayName = "角色表权限表", moduleName = "权限管理")
public class RoleEntity extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String roleEntity_key;
    public Role role;
    @FieldAnnotation(visible = true, displayName = "新增")
    public boolean add_flag = false;
    @FieldAnnotation(visible = true, displayName = "编辑")
    public boolean edit_flag = false;
    @FieldAnnotation(visible = true, displayName = "删除")
    public boolean del_flag = false;
    @FieldAnnotation(visible = true, displayName = "查看")
    public boolean view_flag = false;
    @FieldAnnotation(visible = true, displayName = "查看条件", editableWhenEdit = false, editableWhenNew = false)
    public String right_sql;
    @FieldAnnotation(visible = true, displayName = "编辑条件", editableWhenEdit = false, editableWhenNew = false)
    public String edit_sql;
    private String queryScheme_key;//对应的查看权限方案Key
    private String querySchemeEdit_key;//对应的编辑权限方案Key
    public EntityDef entityDef;
    public transient int new_flag = 0;

    @Id
    public String getRoleEntity_key() {
        return roleEntity_key;
    }

    public void setRoleEntity_key(String roleEntity_key) {
        String old = this.roleEntity_key;
        this.roleEntity_key = roleEntity_key;
        this.firePropertyChange("roleEntity_key", old, roleEntity_key);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_key")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
        if (right_sql == null) {
            right_sql = "";
        }
        return right_sql;
    }

    public void setRight_sql(String right_sql) {
        String old = this.right_sql;
        this.right_sql = right_sql;
        this.firePropertyChange("right_sql", old, right_sql);
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_key")
    public EntityDef getEntityDef() {
        return entityDef;
    }

    public void setEntityDef(EntityDef entityDef) {
        this.entityDef = entityDef;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RoleEntity other = (RoleEntity) obj;
        if ((this.roleEntity_key == null) ? (other.roleEntity_key != null) : !this.roleEntity_key.equals(other.roleEntity_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.roleEntity_key != null ? this.roleEntity_key.hashCode() : 0);
        return hash;
    }

    @Override
    public void assignEntityKey(String key) {
        this.new_flag = 1;
        this.roleEntity_key = key;
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

    public boolean isView_flag() {
        return view_flag;
    }

    public void setView_flag(boolean view_flag) {
        boolean old = this.view_flag;
        this.view_flag = view_flag;
        this.firePropertyChange("view_flag", old, view_flag);
    }

    public String getQueryScheme_key() {
        return queryScheme_key;
    }

    public void setQueryScheme_key(String queryScheme_key) {
        String old = this.queryScheme_key;
        this.queryScheme_key = queryScheme_key;
        this.firePropertyChange("queryScheme_key", old, queryScheme_key);
    }

    public String getEdit_sql() {
        return edit_sql;
    }

    public void setEdit_sql(String edit_sql) {
        String old = this.edit_sql;
        this.edit_sql = edit_sql;
        this.firePropertyChange("edit_sql", old, edit_sql);
    }

    public String getQuerySchemeEdit_key() {
        return querySchemeEdit_key;
    }

    public void setQuerySchemeEdit_key(String querySchemeEdit_key) {
        String old = this.querySchemeEdit_key;
        this.querySchemeEdit_key = querySchemeEdit_key;
        this.firePropertyChange("querySchemeEdit_key", old, querySchemeEdit_key);
    }
}
