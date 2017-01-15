package org.jhrcore.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.jhrcore.entity.annotation.FieldAnnotation;
import javax.persistence.Transient;
import com.jgoodies.binding.beans.Model;
import javax.persistence.FetchType;
import org.jhrcore.entity.annotation.ClassAnnotation;

// 附属集合类,附属于人员的属性集合类
@Entity
@ClassAnnotation(displayName = "人员附表基础表", moduleName = "人员管理")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BasePersonAppendix extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String basePersonAppendix_key;
    @FieldAnnotation(visible = false, displayName = "人员")
    public A01 a01;
    @FieldAnnotation(visible = true, displayName = "次数", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public Integer a_id = 1;
    @FieldAnnotation(visible = true, displayName = "最新状态", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public String last_flag = "";
    public transient int new_flag = 0;

    @Override
    public void assignEntityKey(String key) {
        basePersonAppendix_key = key;
        new_flag = 1;
    }

    @Id
    public String getBasePersonAppendix_key() {
        return basePersonAppendix_key;
    }

    public void setBasePersonAppendix_key(String personAppendix_key) {
        String old = this.basePersonAppendix_key;
        this.basePersonAppendix_key = personAppendix_key;
        this.firePropertyChange("basePersonAppendix_key", old, personAppendix_key);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "a01_key")
    public A01 getA01() {
        return a01;
    }

    public void setA01(A01 person) {
        A01 old = this.a01;
        this.a01 = person;
        this.firePropertyChange("a01", old, person);
    }

    public Integer getA_id() {
        if (a_id == null) {
            return 0;
        }
        return a_id;
    }

    public void setA_id(Integer a_id) {
        Integer old = this.a_id;
        this.a_id = a_id;
        this.firePropertyChange("a_id", old, a_id);
    }

    public String getLast_flag() {
        if ("最新".equals(last_flag)) {
            return last_flag;
        }
        return "";
    }

    public void setLast_flag(String last_flag) {
        String old = this.last_flag;
        this.last_flag = last_flag;
        this.firePropertyChange("last_flag", old, last_flag);
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BasePersonAppendix other = (BasePersonAppendix) obj;
        if ((this.basePersonAppendix_key == null) ? (other.basePersonAppendix_key != null) : !this.basePersonAppendix_key.equals(other.basePersonAppendix_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.basePersonAppendix_key != null ? this.basePersonAppendix_key.hashCode() : 0);
        return hash;
    }
}
