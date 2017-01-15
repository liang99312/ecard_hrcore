package org.jhrcore.entity.base;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@ClassAnnotation(displayName = "模块业务表", moduleName = "系统维护")
public class EntityClass extends Model implements Serializable, IKey, KeyInterface {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String entityClass_key;
    @FieldAnnotation(visible = true, displayName = "顺序号", groupName = "Default", isEditable = true)
    public Integer order_no = 0;
    @FieldAnnotation(visible = true, displayName = "业务中文名称", groupName = "Default", isEditable = true)
    public String entityType_name;
    @FieldAnnotation(visible = false)
    public ModuleInfo moduleInfo;
    @FieldAnnotation(visible = true, displayName = "类别编码", groupName = "Default", isEditable = false)
    public String entityType_code = "0";
    // 表名前缀，表示该类别表的命名前缀，表名=前缀+2位数字,字段名=表名+2位数字
    @FieldAnnotation(visible = true, displayName = "表名前缀", groupName = "Default", isEditable = true)
    public String preEntityName = "";
    @FieldAnnotation(visible = false, displayName = "可编辑", isEditable = true, groupName = "Default")
    public Boolean modify_flag = false;
    @FieldAnnotation(visible = false, displayName = "父类", isEditable = true, groupName = "Default")
    public String super_class;
    public Integer start_num = 0;
    public Set<EntityDef> entityDefs;
    //0: 无改变；1：新增；2：编辑；3：删除
    public transient int fun_flag = 0;
    public transient int new_flag = 0;

    @Override
    public String toString() {
        return entityType_name;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entityClass", fetch = FetchType.LAZY)
    @OrderBy("order_no")
    public Set<EntityDef> getEntityDefs() {
        if (entityDefs == null) {
            entityDefs = new HashSet();
        }
        return entityDefs;
    }

    public void setEntityDefs(Set<EntityDef> entityDefs) {
        this.entityDefs = entityDefs;
    }

    @Id
    public String getEntityClass_key() {
        return entityClass_key;
    }

    public void setEntityClass_key(String entityClass_key) {
        String old = this.entityClass_key;
        this.entityClass_key = entityClass_key;
        this.firePropertyChange("entityClass_key", old, entityClass_key);
    }

    public String getEntityType_name() {
        return entityType_name;
    }

    public void setEntityType_name(String entityType_name) {
        String old = this.entityType_name;
        this.entityType_name = entityType_name;
        this.firePropertyChange("entityType_name", old, entityType_name);
    }

    @ManyToOne
    @JoinColumn(name = "module_key")
    public ModuleInfo getModuleInfo() {
        return moduleInfo;
    }

    public void setModuleInfo(ModuleInfo moduleInfo) {
        ModuleInfo old = this.moduleInfo;
        this.moduleInfo = moduleInfo;
        this.firePropertyChange("moduleInfo", old, moduleInfo);
    }

    public String getEntityType_code() {
        return entityType_code;
    }

    public void setEntityType_code(String entityType_code) {
        String old = this.entityType_code;
        this.entityType_code = entityType_code;
        this.firePropertyChange("entityType_code", old, entityType_code);
    }

    public String getPreEntityName() {
        return preEntityName;
    }

    public void setPreEntityName(String preEntityName) {
        String old = this.preEntityName;
        this.preEntityName = preEntityName;
        this.firePropertyChange("preEntityName", old, preEntityName);
    }

    public Boolean getModify_flag() {
        return modify_flag;
    }

    public void setModify_flag(Boolean modify_flag) {
        Boolean old = this.modify_flag;
        this.modify_flag = modify_flag;
        this.firePropertyChange("modify_flag", old, modify_flag);
    }

    @Transient
    @Override
    public long getKey() {
        return this.new_flag;
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

    public String getSuper_class() {
        return super_class;
    }

    public void setSuper_class(String super_class) {
        String old = this.super_class;
        this.super_class = super_class;
        this.firePropertyChange("super_class", old, super_class);
    }

    @Override
    public void assignEntityKey(String key) {
        this.entityClass_key = key;
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
        final EntityClass other = (EntityClass) obj;
        if ((this.entityClass_key == null) ? (other.entityClass_key != null) : !this.entityClass_key.equals(other.entityClass_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.entityClass_key != null ? this.entityClass_key.hashCode() : 0);
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

    public Integer getOrder_no() {
        if (order_no == null) {
            return 0;
        }
        return order_no;
    }

    public void setOrder_no(Integer order_no) {
        Integer old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }

    public Integer getStart_num() {
        if (start_num == null) {
            return 0;
        }
        return start_num;
    }

    public void setStart_num(Integer start_num) {
        Integer old = this.start_num;
        this.start_num = start_num;
        this.firePropertyChange("start_num", old, start_num);
    }
}
