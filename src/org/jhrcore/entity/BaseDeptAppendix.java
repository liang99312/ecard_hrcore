package org.jhrcore.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import com.jgoodies.binding.beans.Model;

// 部门附加信息的基类
import javax.persistence.FetchType;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;

@Entity
@ClassAnnotation(displayName = "部门附表基础表", moduleName = "部门管理")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseDeptAppendix extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String baseDeptAppendix_key;
    public DeptCode deptCode;
    @FieldAnnotation(visible = true, displayName = "次数", isEditable = false)
    public Integer a_id = 1;
    @FieldAnnotation(visible = true, displayName = "最新状态", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public String last_flag = "";
    public transient int new_flag = 0;

    @Id
    public String getBaseDeptAppendix_key() {
        return baseDeptAppendix_key;
    }

    public void setBaseDeptAppendix_key(String deptAppendix_key) {
        String old = this.baseDeptAppendix_key;
        this.baseDeptAppendix_key = deptAppendix_key;
        this.firePropertyChange("baseDeptAppendix_key", old, deptAppendix_key);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deptCode_key")
    public DeptCode getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(DeptCode deptCode) {
        DeptCode old = this.deptCode;
        this.deptCode = deptCode;
        this.firePropertyChange("deptCode", old, deptCode);
    }

    public Integer getA_id() {
        if (a_id == null) {
            return 1;
        }
        return a_id;
    }

    public void setA_id(Integer number) {
        Integer old = this.a_id;
        this.a_id = number;
        this.firePropertyChange("a_id", old, number);
    }

    public String getLast_flag() {

        if (last_flag == null || last_flag.equals("")) {
            return "";
        } else {
            return "最新";
        }
    }

    public void setLast_flag(String last_flag) {
        String old = this.last_flag;
        this.last_flag = last_flag;
        this.firePropertyChange("last_flag", old, last_flag);
    }

    @Override
    public void assignEntityKey(String key) {
        new_flag = 1;
        this.baseDeptAppendix_key = key;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseDeptAppendix other = (BaseDeptAppendix) obj;
        if ((this.baseDeptAppendix_key == null) ? (other.baseDeptAppendix_key != null) : !this.baseDeptAppendix_key.equals(other.baseDeptAppendix_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.baseDeptAppendix_key != null ? this.baseDeptAppendix_key.hashCode() : 0);
        return hash;
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
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
