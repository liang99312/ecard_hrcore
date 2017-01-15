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
import javax.persistence.Transient;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author Administrator
 */
@Entity
@ClassAnnotation(displayName = "系统角色表", moduleName = "权限管理")
public class Role extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String role_key;
    @FieldAnnotation(visible = true, displayName = "角色名称", groupName = "基本信息")
    public String role_name;
    @FieldAnnotation(visible = true, displayName = "角色序号", groupName = "基本信息", isEditable = false)
    public String order_no;
    @FieldAnnotation(visible = true, displayName = "角色编码", groupName = "基本信息", isEditable = true)
    public String role_code;
    @FieldAnnotation(visible = true, displayName = "角色父编码", groupName = "基本信息", isEditable = false)
    public String parent_code;
    @FieldAnnotation(visible = true, displayName = "选择", groupName = "基本信息", isEditable = true)
    public transient boolean select_flag = false;
    private String role_dept_code;
    private Integer grade;
    private Set<RoleFuntion> roleFuntions;
//    private Set<RoleDept> roleDepts;
    private Set<RoleField> roleFields;
    private Set<RoleReport> roleReports;
    private Set<RoleCode> roleCodes;
    private Set<RoleEntity> roleEntitys;
    private Set<RoleA01> roleA01s;
    private transient int new_flag = 0;
    @Id
    public String getRole_key() {
        return role_key;
    }

    public void setRole_key(String role_key) {
        String old = this.role_key;
        this.role_key = role_key;
        this.firePropertyChange("role_key", old, role_key);
    }

//    @ManyToMany(targetEntity = FuntionRight.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "ROLE_FUNCTIONRIGHT", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "funtionRight_id")})
//    public Set<FuntionRight> getFunRights() {
//        if (funRights == null) {
//            funRights = new HashSet<FuntionRight>();
//        }
//        return funRights;
//    }
//
//    public void setFunRights(Set<FuntionRight> funRights) {
//        this.funRights = funRights;
//    }
//    @ManyToMany(targetEntity = Dept.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "ROLE_DEPTRIGHT", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "dept_key")})
//    public Set<Dept> getDepts() {
//        if(depts == null)
//            depts = new HashSet<Dept>();
//        return depts;
//    }
//
//    public void setDepts(Set<Dept> depts) {
//        this.depts = depts;
//    }
    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        String old = this.role_name;
        this.role_name = role_name;
        this.firePropertyChange("role_name", old, role_name);
    }
//    @ManyToMany(targetEntity = ReportDef.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "ROLE_REPORTRIGHT", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "reportDef_id")})
//    public Set<ReportDef> getReportDefs() {
//        return reportDefs;
//    }
//
//    public void setReportDefs(Set<ReportDef> reportDefs) {
//        this.reportDefs = reportDefs;
//    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.LAZY)
    public Set<RoleFuntion> getRoleFuntions() {
        return roleFuntions;
    }

    public void setRoleFuntions(Set<RoleFuntion> roleFuntions) {
        this.roleFuntions = roleFuntions;
    }

  
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.LAZY)
//    public Set<RoleDept> getRoleDepts() {
//        return roleDepts;
//    }
//
//    public void setRoleDepts(Set<RoleDept> roleDepts) {
//        this.roleDepts = roleDepts;
//    }

    public String getParent_code() {
        return parent_code;
    }

    public void setParent_code(String parent_code) {
        String old = this.parent_code;
        this.parent_code = parent_code;
        this.firePropertyChange("parent_code", old, parent_code);
    }

    public String getRole_code() {
        return role_code;
    }

    public void setRole_code(String role_code) {
        String old = this.role_code;
        this.role_code = role_code;
        this.firePropertyChange("role_code", old, role_code);
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.LAZY)
    public Set<RoleField> getRoleFields() {
        return roleFields;
    }

    public void setRoleFields(Set<RoleField> roleFields) {
        this.roleFields = roleFields;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.LAZY)
    public Set<RoleReport> getRoleReports() {
        return roleReports;
    }

    public void setRoleReports(Set<RoleReport> roleReports) {
        this.roleReports = roleReports;
    }

    public String getRole_dept_code() {
        return role_dept_code;
    }

    public void setRole_dept_code(String role_dept_code) {
        String old = this.role_dept_code;
        this.role_dept_code = role_dept_code;
        this.firePropertyChange("role_dept_code", old, role_dept_code);
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.LAZY)
    public Set<RoleCode> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(Set<RoleCode> roleCodes) {
        this.roleCodes = roleCodes;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.LAZY)
    public Set<RoleEntity> getRoleEntitys() {
        return roleEntitys;
    }

    public void setRoleEntitys(Set<RoleEntity> roleEntitys) {
        this.roleEntitys = roleEntitys;
    }
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.LAZY)
    public Set<RoleA01> getRoleA01s() {
        return roleA01s;
    }

    public void setRoleA01s(Set<RoleA01> roleA01s) {
        this.roleA01s = roleA01s;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        String old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }

    @Override
    public void assignEntityKey(String key) {
        this.new_flag = 1;
        this.role_key = key;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Role other = (Role) obj;
        if ((this.role_key == null) ? (other.role_key != null) : !this.role_key.equals(other.role_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.role_key != null ? this.role_key.hashCode() : 0);
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

    @Override
    public String toString() {
        return role_name+"{"+role_code+"}";
    }
}
