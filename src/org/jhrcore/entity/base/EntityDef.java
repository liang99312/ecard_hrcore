package org.jhrcore.entity.base;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;

import com.jgoodies.binding.beans.Model;
import javax.persistence.Column;
import javax.persistence.Transient;
import javax.persistence.Table;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;

@Entity
@Table(name = "TABNAME")
@ClassAnnotation(displayName = "数据表信息")
public class EntityDef extends Model implements Serializable, IKey, KeyInterface {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String entity_key;
    @FieldAnnotation(visible = true, displayName = "顺序号", groupName = "Default")
    public Integer order_no = 0;
    @FieldAnnotation(visible = true, displayName = "表的名称", groupName = "Default", isEditable = true, editableWhenNew = true, editableWhenEdit = false)
    public String entityName;
    @FieldAnnotation(visible = true, displayName = "表的描述", groupName = "Default")
    public String entityCaption;
    @FieldAnnotation(visible = false, displayName = "类型", groupName = "Default", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public EntityClass entityClass;
    @FieldAnnotation(visible = false, displayName = "显示", groupName = "Default", editableWhenNew = false, editableWhenEdit = false)
    public String to_String;
    @FieldAnnotation(visible = true, displayName = "表类型", groupName = "Default", editableWhenNew = false, editableWhenEdit = false, visibleWhenNew = false)
    @EnumHint(enumList = "系统逻辑表;系统业务表;用户自定义")
    public String canmodify = "用户自定义";
    private Set<FieldDef> fieldDefs;
    //0: 无改变；1：新增；2：编辑；3：删除
    public transient int fun_flag = 0;
    public transient int new_flag = 0;
    @FieldAnnotation(visible = false)
    public transient boolean success_build = false;
    //0: 无改变；1：新增；2：编辑；3：删除
//    private transient int change_flag = 0;
    @FieldAnnotation(visible = false, displayName = "选择", groupName = "Default")
    public transient boolean select_flag = false;
    @FieldAnnotation(visible = false, displayName = "编码限制脚本", groupName = "Default")
    @Column(length = 500)
    public String limit_script;
    @FieldAnnotation(visible = false, displayName = "是否启用脚本", groupName = "Default")
    public boolean limit_flag = false;
    @FieldAnnotation(visible = false, displayName = "初始化脚本", groupName = "Default")
    @Column(length = 2000)
    public String init_script;
    @FieldAnnotation(visible = false, displayName = "是否启用脚本", groupName = "Default")
    public boolean init_flag = false;
    public transient String packageName;

    @Transient
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getLimit_script() {
        return limit_script;
    }

    public void setLimit_script(String limit_script) {
        String old = this.limit_script;
        this.limit_script = limit_script;
        this.firePropertyChange("limit_script", old, limit_script);
    }

    @Override
    public String toString() {
        return entityCaption;
    }

    @Id
    public String getEntity_key() {
        return entity_key;
    }

    public void setEntity_key(String entity_key) {
        String old = this.entity_key;
        this.entity_key = entity_key;
        this.firePropertyChange("entity_key", old, entity_key);
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        String old_value = this.entityName;
        this.entityName = entityName;
        this.firePropertyChange("entityName", old_value, this.entityName);
    }

    public String getEntityCaption() {
        return entityCaption;
    }

    public void setEntityCaption(String entityCaption) {
        String old_value = this.entityCaption;
        this.entityCaption = entityCaption;
        this.firePropertyChange("entityCaption", old_value, this.entityCaption);
    }

    @OneToMany(mappedBy = "entityDef", fetch = FetchType.LAZY)
    @OrderBy("order_no")
    public Set<FieldDef> getFieldDefs() {
        if (fieldDefs == null) {
            fieldDefs = new HashSet<FieldDef>();
        }
        return fieldDefs;
    }

    public void setFieldDefs(Set<FieldDef> fieldDefs) {
        this.fieldDefs = fieldDefs;
    }

    public String getTo_String() {
        return to_String;
    }

    public void setTo_String(String to_String) {
        String old = this.to_String;
        this.to_String = to_String;
        this.firePropertyChange("to_String", old, to_String);
    }

    public String getCanmodify() {
        return canmodify;
    }

    public void setCanmodify(String canmodify) {
        String old = this.canmodify;
        this.canmodify = canmodify;
        this.firePropertyChange("canmodify", old, canmodify);
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entityClass_key")
    public EntityClass getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(EntityClass e) {
        this.entityClass = e;
    }

    public boolean isSuccess_build() {
        return success_build;
    }

    public void setSuccess_build(boolean success_build) {
        boolean old = this.success_build;
        this.success_build = success_build;
        this.firePropertyChange("success_build", old, success_build);
    }

    @Transient
    public int getFun_flag() {
        return fun_flag;
    }

    public void setFun_flag(int fun_flag) {
        this.fun_flag = fun_flag;
    }

    @Transient
    public boolean isSelect_flag() {
        return select_flag;
    }

    public void setSelect_flag(boolean select_flag) {
        boolean old = this.select_flag;
        this.select_flag = select_flag;
        this.firePropertyChange("select_flag", old, select_flag);
    }

    @Transient
    @Override
    public long getKey() {
        return new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.entity_key = key;
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
        final EntityDef other = (EntityDef) obj;
        if ((this.entity_key == null) ? (other.entity_key != null) : !this.entity_key.equals(other.entity_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.entity_key != null ? this.entity_key.hashCode() : 0);
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

    public boolean isLimit_flag() {
        return limit_flag;
    }

    public void setLimit_flag(boolean limit_flag) {
        boolean old = this.limit_flag;
        this.limit_flag = limit_flag;
        this.firePropertyChange("limit_flag", old, limit_flag);
    }

    public boolean isInit_flag() {
        return init_flag;
    }

    public void setInit_flag(boolean init_flag) {
        boolean old = this.init_flag;
        this.init_flag = init_flag;
        this.firePropertyChange("init_flag", old, init_flag);
    }

    public String getInit_script() {
        return init_script;
    }

    public void setInit_script(String init_script) {
        String old = this.init_script;
        this.init_script = init_script;
        this.firePropertyChange("init_script", old, init_script);
    }
}
