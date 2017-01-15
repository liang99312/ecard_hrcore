package org.jhrcore.entity.base;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.jhrcore.entity.annotation.FieldAnnotation;

import com.jgoodies.binding.beans.Model;
import java.util.HashSet;
import javax.persistence.Transient;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;

@Entity
@ClassAnnotation(displayName = "模块表", moduleName = "系统维护")
public class ModuleInfo extends Model implements Serializable, IKey, KeyInterface {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String module_key;
    @FieldAnnotation(visible = true, displayName = "模块编号", groupName = "Default", isEditable = false)
    public String module_code;
    @FieldAnnotation(visible = true, displayName = "模块中文名称", groupName = "Default", isEditable = false)
    public String module_name;
    @FieldAnnotation(visible = true, displayName = "模块描述", groupName = "Default")
    public String module_desc;
    @FieldAnnotation(visible = true, displayName = "顺序号", groupName = "Default")
    public int order_no = 0;
    public String query_entity_name;
    @FieldAnnotation(visible = false, displayName = "包路径", isEditable = true)
    public String packageName;
    public Set<EntityClass> entityClasss;
    @FieldAnnotation(visible = false, displayName = "启用", groupName = "Default")
    public boolean used = false;
    public transient int fun_flag = 0;
    public transient int new_flag = 0;

    @Override
    public String toString() {
        return module_name;
    }

    @Id
    public String getModule_key() {
        return module_key;
    }

    public void setModule_key(String module_key) {
        String old = this.module_key;
        this.module_key = module_key;
        this.firePropertyChange("module_key", old, module_key);
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        String old = this.module_name;
        this.module_name = module_name;
        this.firePropertyChange("module_name", old, module_name);
    }

    public String getModule_desc() {
        return module_desc;
    }

    public void setModule_desc(String module_desc) {
        String old = this.module_code;
        this.module_desc = module_desc;
        this.firePropertyChange("module_desc", old, module_desc);
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        int old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "moduleInfo", fetch = FetchType.LAZY)
    @OrderBy("order_no")
    public Set<EntityClass> getEntityClasss() {
        if (entityClasss == null) {
            entityClasss = new HashSet();
        }
        return entityClasss;
    }

    public void setEntityClasss(Set<EntityClass> entityClasss) {
        this.entityClasss = entityClasss;
    }

    public String getModule_code() {
        return module_code;
    }

    public void setModule_code(String module_code) {
        String old = this.module_code;
        this.module_code = module_code;
        this.firePropertyChange("module_code", old, module_code);
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

    @Transient
    @Override
    public long getKey() {
        return new_flag;
    }

    public String getQuery_entity_name() {
        return query_entity_name;
    }

    public void setQuery_entity_name(String query_entity_name) {
        String old = this.query_entity_name;
        this.query_entity_name = query_entity_name;
        this.firePropertyChange("query_entity_name", old, query_entity_name);
    }

    @Override
    public void assignEntityKey(String key) {
        this.module_key = key;
        this.new_flag = 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ModuleInfo other = (ModuleInfo) obj;
        if ((this.module_key == null) ? (other.module_key != null) : !this.module_key.equals(other.module_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.module_key != null ? this.module_key.hashCode() : 0);
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

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        String old = this.packageName;
        this.packageName = packageName;
        this.firePropertyChange("packageName", old, packageName);
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        boolean old = this.used;
        this.used = used;
        this.firePropertyChange("used", old, used);
    }
}
