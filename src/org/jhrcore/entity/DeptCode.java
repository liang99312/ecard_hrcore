package org.jhrcore.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.jhrcore.entity.annotation.FieldAnnotation;
import com.jgoodies.binding.beans.Model;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.EnumHint;

@Entity
@ClassAnnotation(displayName = "部门基本信息表", moduleName = "部门机构")
public class DeptCode extends Model implements Serializable, IKey, KeyInterface, Comparable {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false, displayName = "部门ID", isEditable = false)
    public String deptCode_key;
    @FieldAnnotation(visible = false, displayName = "排序码", isEditable = true)
    public String px_code;
    @FieldAnnotation(visible = true, displayName = "部门代码", isEditable = true)
    public String dept_code;
    @FieldAnnotation(visible = true, displayName = "部门名称", isEditable = true)
    public String content;
    @FieldAnnotation(visible = true, displayName = "父编码", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public String parent_code;
    @FieldAnnotation(visible = true, displayName = "组织类型")
    public String dept_class;
    @FieldAnnotation(visible = true, displayName = "级数", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public Integer grade = 1;
    @FieldAnnotation(visible = true, displayName = "末级部门标识", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public boolean end_flag = true;
    @FieldAnnotation(visible = true, displayName = "部门全称", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public String dept_full_name;
    public String prechar = "";
    @FieldAnnotation(visible = true, displayName = "虚拟部门")
    public boolean virtual = false;
    public boolean del_flag = false;
    @FieldAnnotation(visible = true, displayName = "机构性质")
    @EnumHint(enumList="单位;部门")
    public String dept_quality;
    public Set<VirtualDeptPerson> virtualDeptPersons;
    public Set<VirtualDeptPersonLog> virtualDeptPersonLogs;
    public transient int fun_flag = 0;
    @FieldAnnotation(visible = false, displayName = "选择")
    public transient boolean selected_flag = false;
    // 部门附属信息集合
    public Set<BaseDeptAppendix> baseDeptAppendixs;
    private Set<SysNotice> sysNotices;
    public transient boolean show_code_flag = true;
    public transient long new_flag = 0;
    
    public String getDept_quality() {
        return dept_quality;
    }
    
    public void setDept_quality(String dept_quality) {
        String old = this.dept_quality;
        this.dept_quality = dept_quality;
        this.firePropertyChange("dept_quality", old, dept_quality);
    }

    @Id
    public String getDeptCode_key() {
        return deptCode_key;
    }

    public void setDeptCode_key(String dept_key) {
        String old = this.deptCode_key;
        this.deptCode_key = dept_key;
        this.firePropertyChange("deptCode_key", old, dept_key);
    }

    public String getDept_code() {
        return dept_code;
    }

    public void setDept_code(String dept_code) {
        String old = this.dept_code;
        this.dept_code = dept_code;
        this.firePropertyChange("dept_code", old, dept_code);

    }

    public String getParent_code() {
        return parent_code;
    }

    public void setParent_code(String parent_code) {
        String old = this.parent_code;
        this.parent_code = parent_code;
        this.firePropertyChange("parent_code", old, parent_code);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        String old = this.content;
        this.content = content;
        this.firePropertyChange("content", old, content);
    }

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deptCode", fetch = FetchType.LAZY)
//    public Set<A01> getA01s() {
//        return a01s;
//    }
//
//    public void setA01s(Set<A01> a01s) {
//        this.a01s = a01s;
//    }
    @Transient
    public boolean isSelected_flag() {
        return selected_flag;
    }

    public void setSelected_flag(boolean selected_flag) {
        boolean old = this.selected_flag;
        this.selected_flag = selected_flag;
        this.firePropertyChange("selected_flag", old, selected_flag);
    }

    @Override
    public String toString() {
        if (show_code_flag) {
            return content + "{" + dept_code + "}";
        }
        return content;
    }

    public String getPrechar() {
        return prechar;
    }

    public void setPrechar(String preChar) {
        String old = this.prechar;
        this.prechar = preChar;
        this.firePropertyChange("prechar", old, preChar);
    }

    @OneToMany(mappedBy = "deptCode", fetch = FetchType.LAZY)
    @FieldAnnotation(visible = false)
    public Set<BaseDeptAppendix> getBaseDeptAppendixs() {
        return baseDeptAppendixs;
    }

    public void setBaseDeptAppendixs(Set<BaseDeptAppendix> baseDeptAppendixs) {
        this.baseDeptAppendixs = baseDeptAppendixs;
    }

//    public Set<RoleDept> getRoleDepts() {
//        return roleDepts;
//    }
//
//    public void setRoleDepts(Set<RoleDept> roleDepts) {
//        this.roleDepts = roleDepts;
//    }
    @OneToMany(mappedBy = "deptCode", fetch = FetchType.LAZY)
    public Set<VirtualDeptPerson> getVirtualDeptPersons() {
        return virtualDeptPersons;
    }

    public void setVirtualDeptPersons(Set<VirtualDeptPerson> virtualDeptPersons) {
        this.virtualDeptPersons = virtualDeptPersons;
    }

    @OneToMany(mappedBy = "deptCode", fetch = FetchType.LAZY)
    public Set<VirtualDeptPersonLog> getVirtualDeptPersonLogs() {
        return virtualDeptPersonLogs;
    }

    public void setVirtualDeptPersonLogs(Set<VirtualDeptPersonLog> virtualDeptPersonLogs) {
        this.virtualDeptPersonLogs = virtualDeptPersonLogs;
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

    public String getDept_full_name() {
        return dept_full_name;
    }

    public void setDept_full_name(String dept_full_name) {
        String old = this.dept_full_name;
        this.dept_full_name = dept_full_name;
        this.firePropertyChange("dept_full_name", old, dept_full_name);
    }
    
    @OneToMany(mappedBy = "deptCode", fetch = FetchType.LAZY)
    public Set<SysNotice> getSysNotices() {
        return sysNotices;
    }

    public void setSysNotices(Set<SysNotice> sysNotices) {
        this.sysNotices = sysNotices;
    }

    @Transient
    @Override
    public long getKey() {
        return new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.deptCode_key = key;
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
        DeptCode other = (DeptCode) obj;
        if ((this.deptCode_key == null) ? (other.deptCode_key != null) : !this.deptCode_key.equals(other.deptCode_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.deptCode_key != null ? this.deptCode_key.hashCode() : 0);
        return hash;
    }

    @Transient
    public long getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(long new_flag) {
        long old = this.new_flag;
        this.new_flag = new_flag;
        this.firePropertyChange("new_flag", old, new_flag);
    }

    public boolean isDel_flag() {
        return del_flag;
    }

    public void setDel_flag(boolean del_flag) {
        boolean old = this.del_flag;
        this.del_flag = del_flag;
        this.firePropertyChange("del_flag", old, del_flag);
    }

    public boolean isEnd_flag() {
        return end_flag;
    }

    public void setEnd_flag(boolean end_flag) {
        boolean old = this.end_flag;
        this.end_flag = end_flag;
        this.firePropertyChange("end_flag", old, end_flag);
    }

    public Integer getGrade() {
        if (grade == null) {
            return 0;
        }
        return grade;
    }

    public void setGrade(Integer grade) {
        Integer old = this.grade;
        this.grade = grade;
        this.firePropertyChange("grade", old, grade);
    }

    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        boolean old = this.virtual;
        this.virtual = virtual;
        this.firePropertyChange("virtual", old, virtual);
    }

    public String getDept_class() {
        return dept_class;
    }

    public void setDept_class(String dept_class) {
        String old = this.dept_class;
        this.dept_class = dept_class;
        this.firePropertyChange("dept_class", old, dept_class);
    }

    public String getPx_code() {
        return px_code;
    }

    public void setPx_code(String px_code) {
        String old = this.px_code;
        this.px_code = px_code;
        this.firePropertyChange("px_code", old, px_code);
    }

    @Transient
    public boolean isShow_code_flag() {
        return show_code_flag;
    }

    public void setShow_code_flag(boolean show_code_flag) {
        this.show_code_flag = show_code_flag;
    }

    @Override
    public int compareTo(Object o) {
        return this.dept_code.compareTo(((DeptCode) o).getDept_code());
    }
}
