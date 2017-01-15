/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.change;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author Administrator
 */
@Entity
@ClassAnnotation(displayName = "人事调配方案表", moduleName = "人事管理")
public class ChangeScheme extends Model implements Serializable, IKey, KeyInterface {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String changeScheme_key;
    @FieldAnnotation(visible = false)
    private int changeScheme_no = 0;
    @FieldAnnotation(visible = true, displayName = "变动名称", groupName = "Default")
    public String changeScheme_name;
    @FieldAnnotation(visible = true, displayName = "变动分组", groupName = "Default")
    public String changeScheme_type;
    @FieldAnnotation(visible = true, displayName = "是否需要审批", groupName = "Default")
    public boolean check_flag = false;
    @FieldAnnotation(visible = true, displayName = "起薪控制", groupName = "Default")
    public boolean pay_flag = true;
    @FieldAnnotation(visible = true, displayName = "显示所有部门", groupName = "Default")
    public boolean all_dept_flag = false;
    @FieldAnnotation(visible = true, displayName = "合同跟随人事变化", groupName = "Default")
    public boolean change_ht_flag = true;
    @FieldAnnotation(visible = true, displayName = "方案类型", groupName = "Default")
    @EnumHint(enumList = "调配;借调;临时")
    public String scheme_type = "调配";
    public String oldPersonClassName;//变动前的人员类别，可以为多个，用分号隔开
    public String newPersonClassName; // 变动后的人员类型名称
    @FieldAnnotation(visible = false, displayName = "变动前人员类别", groupName = "Default", view_width = 50)
    public transient String oldPersonClass;
    @FieldAnnotation(visible = false, displayName = "变动后人员类别", groupName = "Default")
    public transient String newPersonClass;
    private Set<ChangeItem> changeItems = new HashSet();
    private Set<ChangeField> changeFields = new HashSet();
    private Set<ChangeMethod> changeMethods = new HashSet();
    @FieldAnnotation(visible = false, displayName = "调令表Key键", groupName = "Default")
    private String report_key;
    @FieldAnnotation(visible = false, displayName = "人员方案Key键", groupName = "Default")
    private String queryScheme_key;
    public transient int new_flag = 0;

    @Id
    public String getChangeScheme_key() {
        return changeScheme_key;
    }

    public void setChangeScheme_key(String changeScheme_key) {
        String old = this.changeScheme_key;
        this.changeScheme_key = changeScheme_key;
        this.firePropertyChange("changeScheme_key", old, changeScheme_key);
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "changeScheme", fetch = FetchType.LAZY)
    @OrderBy("appendix_name")
    public Set<ChangeField> getChangeFields() {
        if (changeFields == null) {
            changeFields = new HashSet();
        }
        return changeFields;
    }

    public void setChangeFields(Set<ChangeField> changeFields) {
        this.changeFields = changeFields;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "changeScheme", fetch = FetchType.LAZY)
    public Set<ChangeItem> getChangeItems() {
        if (changeItems == null) {
            changeItems = new HashSet();
        }
        return changeItems;
    }

    public void setChangeItems(Set<ChangeItem> changeItems) {
        this.changeItems = changeItems;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "changeScheme", fetch = FetchType.LAZY)
    public Set<ChangeMethod> getChangeMethods() {
        if (changeMethods == null) {
            changeMethods = new HashSet();
        }
        return changeMethods;
    }

    public void setChangeMethods(Set<ChangeMethod> changeMethods) {
        this.changeMethods = changeMethods;
    }

    public String getNewPersonClassName() {
        return newPersonClassName;
    }

    public void setNewPersonClassName(String newPersonClassName) {
        String old = this.newPersonClassName;
        this.newPersonClassName = newPersonClassName;
        this.firePropertyChange("newPersonClassName", old, newPersonClassName);
    }

    public String getChangeScheme_name() {
        return changeScheme_name;
    }

    public void setChangeScheme_name(String changeScheme_name) {
        String old_changeScheme_name = this.changeScheme_name;
        this.changeScheme_name = changeScheme_name;
        this.firePropertyChange("changeScheme_name", old_changeScheme_name, this.changeScheme_name);

    }

    public String getChangeScheme_type() {
        return changeScheme_type;
    }

    public void setChangeScheme_type(String changeScheme_type) {
        String old_changeScheme_type = this.changeScheme_type;
        this.changeScheme_type = changeScheme_type;
        this.firePropertyChange("changeScheme_type", old_changeScheme_type, this.changeScheme_type);
    }

    public void remove(String fieldName) {
        for (ChangeItem ci : changeItems) {
            if (ci.getFieldName().equals(fieldName)) {
                changeItems.remove(ci);
                return;
            }
        }
    }

    public boolean contains(String fieldName) {
        for (ChangeItem ci : changeItems) {
            if (ci.getFieldName().toLowerCase().equals(fieldName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChangeScheme other = (ChangeScheme) obj;
        if ((this.changeScheme_key == null) ? (other.changeScheme_key != null) : !this.changeScheme_key.equals(other.changeScheme_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.changeScheme_key != null ? this.changeScheme_key.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return changeScheme_name;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.changeScheme_key = key;
        this.new_flag = 1;
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

    public int getChangeScheme_no() {
        return changeScheme_no;
    }

    public void setChangeScheme_no(int changeScheme_no) {
        int old = this.changeScheme_no;
        this.changeScheme_no = changeScheme_no;
        this.firePropertyChange("changeScheme_no", old, changeScheme_no);
    }

    public String getOldPersonClassName() {
        return oldPersonClassName;
    }

    public void setOldPersonClassName(String oldPersonClassName) {
        this.oldPersonClassName = oldPersonClassName;
    }

    public boolean isCheck_flag() {
        return check_flag;
    }

    public void setCheck_flag(boolean check_flag) {
        boolean old = this.check_flag;
        this.check_flag = check_flag;
        this.firePropertyChange("check_flag", old, check_flag);
    }

    public boolean isPay_flag() {
        return pay_flag;
    }

    public void setPay_flag(boolean pay_flag) {
        boolean old = this.pay_flag;
        this.pay_flag = pay_flag;
        this.firePropertyChange("pay_flag", old, pay_flag);
    }

    public boolean isAll_dept_flag() {
        return all_dept_flag;
    }

    public void setAll_dept_flag(boolean all_dept_flag) {
        boolean old = this.all_dept_flag;
        this.all_dept_flag = all_dept_flag;
        this.firePropertyChange("all_dept_flag", old, all_dept_flag);
    }

    @Transient
    public String getNewPersonClass() {
        return newPersonClass;
    }

    public void setNewPersonClass(String newPersonClass) {
        String old = this.newPersonClass;
        this.newPersonClass = newPersonClass;
        this.firePropertyChange("newPersonClass", old, newPersonClass);
    }

    @Transient
    public String getOldPersonClass() {
        return oldPersonClass;
    }

    public void setOldPersonClass(String oldPersonClass) {
        String old = this.oldPersonClass;
        this.oldPersonClass = oldPersonClass;
        this.firePropertyChange("oldPersonClass", old, oldPersonClass);
    }

    public String getReport_key() {
        return report_key;
    }

    public void setReport_key(String report_key) {
        String old = this.report_key;
        this.report_key = report_key;
        this.firePropertyChange("report_key", old, report_key);
    }

    public String getQueryScheme_key() {
        return queryScheme_key;
    }

    public void setQueryScheme_key(String queryScheme_key) {
        String old = this.queryScheme_key;
        this.queryScheme_key = queryScheme_key;
        this.firePropertyChange("queryScheme_key", old, queryScheme_key);
    }

    public boolean isChange_ht_flag() {
        return change_ht_flag;
    }

    public void setChange_ht_flag(boolean change_ht_flag) {
        boolean old = this.change_ht_flag;
        this.change_ht_flag = change_ht_flag;
        this.firePropertyChange("change_ht_flag", old, change_ht_flag);
    }

    public String getScheme_type() {
        return scheme_type;
    }

    public void setScheme_type(String scheme_type) {
        String old = this.scheme_type;
        this.scheme_type = scheme_type;
        this.firePropertyChange("scheme_type", old, scheme_type);
    }
}
