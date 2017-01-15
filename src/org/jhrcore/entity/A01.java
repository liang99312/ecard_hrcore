package org.jhrcore.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jhrcore.entity.annotation.FieldAnnotation;

import com.jgoodies.binding.beans.Model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.ObjectListHint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@ClassAnnotation(displayName = "人员基本信息表", moduleName = "人事管理")
public class A01 extends Model implements Serializable, IKey, KeyInterface {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false, displayName = "员工ID", not_null = true)
    public String a01_key;
    @FieldAnnotation(visible = true, displayName = "员工号", not_null = true)
    public String a0190;
    @FieldAnnotation(visible = true, displayName = "姓名", not_null = true)
    public String a0101;
    @ObjectListHint(hqlForObjectList = "select e.entityCaption from EntityDef e where e.entityClass.entityType_code='CLASS'")
    @FieldAnnotation(visible = true, displayName = "人员类别", isEditable = false, editableWhenNew = true, editableWhenEdit = false)
    public String a0191 = null;
    @FieldAnnotation(visible = false, displayName = "人员状态标识")
    public int a0193 = 0;//0 在库 1 逻辑删除 2 入职登记
    @FieldAnnotation(visible = true, displayName = "身份证号码")
    public String a0177;
    @FieldAnnotation(visible = true, displayName = "性别")
    public String a0107;
    @FieldAnnotation(visible = true, displayName = "出生年月")
    public Date a0111;
    @FieldAnnotation(visible = true, displayName = "年龄")
    public Integer a0112;
    @FieldAnnotation(visible = true, displayName = "民族")
    public String a0121;
    @FieldAnnotation(visible = true, displayName = "参加工作时间")
    public Date a0141;
    @FieldAnnotation(visible = false, displayName = "拼音码")
    public String pydm;
    @FieldAnnotation(visible = true, displayName = "岗位名称")
    public String a0105;
    @FieldAnnotation(visible = true, displayName = "职务")
    public String a0104;
    @FieldAnnotation(visible = true, displayName = "本单位工龄")
    public BigDecimal a0152;
    @FieldAnnotation(visible = false, displayName = "工资停发标识", isEditable = false, editableWhenNew = true, editableWhenEdit = false)
    public Boolean a0199 = false;
    @FieldAnnotation(visible = true, displayName = "社保标识", isEditable = true)
    public String in_code;
    @FieldAnnotation(visible = true, displayName = "部门")
    public DeptCode deptCode;
    @FieldAnnotation(visible = false, displayName = "人员附表")
    public Set<BasePersonAppendix> basePersonAppendixs;
    @FieldAnnotation(visible = false, displayName = "人员变动表")
    public Set<BasePersonChange> basePersonChanges;
    public Set<VirtualDeptPerson> virtualDeptPersons;
    public Set<VirtualDeptPersonLog> virtualDeptPersonLogs;
    public transient int new_flag = 0;
    @FieldAnnotation(visible = false, displayName = "照片")
    public String pic_path;

    @Id
    public String getA01_key() {
        return a01_key;
    }

    public void setA01_key(String a01_key) {
        String old = this.a01_key;
        this.a01_key = a01_key;
        this.firePropertyChange("a01_key", old, a01_key);
    }

    public String getPic_path() {
        return pic_path;
    }

    public void setPic_path(String pic_path) {
        this.pic_path = pic_path;
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }

    public String getA0191() {
        if (a0191 == null) {
            if (!this.getClass().getSimpleName().equals("A01")) {
                ClassAnnotation classAnnotation = (ClassAnnotation) this.getClass().getAnnotation(ClassAnnotation.class);
                if (classAnnotation != null) {
                    a0191 = classAnnotation.displayName();
                }
            }
        }
        return a0191;
    }

    public void setA0191(String a0191) {
        String old = this.a0191;
        this.a0191 = a0191;
        this.firePropertyChange("a0191", old, a0191);
    }
    
    public String getA0101() {
        return a0101;
    }

    public void setA0101(String person_Name) {
        String old = this.a0101;
        this.a0101 = person_Name;
        this.firePropertyChange("a0101", old, person_Name);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deptCode_key")
    public DeptCode getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(DeptCode deptCode) {
        this.deptCode = deptCode;
    }

    public String getA0121() {
        return a0121;
    }

    public void setA0121(String person_code) {
        String old = this.a0121;
        this.a0121 = person_code;
        this.firePropertyChange("a0121", old, person_code);
    }

    @OneToMany(mappedBy = "a01", fetch = FetchType.LAZY)
    @FieldAnnotation(visible = true, displayName = "变动")
    public Set<BasePersonChange> getBasePersonChanges() {
        return basePersonChanges;
    }

    public void setBasePersonChanges(Set<BasePersonChange> basePersonChanges) {
        this.basePersonChanges = basePersonChanges;
    }

    public String getA0190() {
        return a0190;
    }

    public void setA0190(String person_code) {
        String old = this.a0190;
        this.a0190 = person_code;
        this.firePropertyChange("a0190", old, person_code);
    }

    public String getPydm() {
        return pydm;
    }

    public void setPydm(String pinyin) {
        String old = this.pydm;
        this.pydm = pinyin;
        this.firePropertyChange("pydm", old, pinyin);
    }

    public String getA0177() {
        return a0177;
    }

    public void setA0177(String person_identity_card) {
        String old = this.a0177;
        this.a0177 = person_identity_card;
        this.firePropertyChange("a0177", old, person_identity_card);
    }

    public String getA0107() {
        return a0107;
    }

    public void setA0107(String person_sex) {
        String old = this.a0107;
        this.a0107 = person_sex;
        this.firePropertyChange("a0107", old, person_sex);
    }

    public String getA0105() {
        return a0105;
    }

    public void setA0105(String person_position) {
        String old = this.a0105;
        this.a0105 = person_position;
        this.firePropertyChange("a0105", old, person_position);
    }

    public Date getA0111() {
        return a0111;
    }

    public void setA0111(Date person_birthday) {
        Date old_value = this.a0111;
        this.a0111 = person_birthday;
        this.firePropertyChange("a0111", old_value, this.a0111);
    }

    public Date getA0141() {
        return a0141;
    }

    public void setA0141(Date person_work_time) {
        Date old = this.a0141;
        this.a0141 = person_work_time;
        this.firePropertyChange("a0141", old, person_work_time);
    }

    public String getA0104() {
        return a0104;
    }

    public void setA0104(String a0104) {
        String old = this.a0104;
        this.a0104 = a0104;
        this.firePropertyChange("a0104", old, a0104);
    }

    @OneToMany(mappedBy = "a01", fetch = FetchType.LAZY)
    @FieldAnnotation(visible = false, displayName = "虚拟部门人员对应", groupName = "基本信息")
    public Set<VirtualDeptPerson> getVirtualDeptPersons() {
        return virtualDeptPersons;
    }

    public void setVirtualDeptPersons(Set<VirtualDeptPerson> virtualDeptPersons) {
        this.virtualDeptPersons = virtualDeptPersons;
    }

    @OneToMany(mappedBy = "a01", fetch = FetchType.LAZY)
    @FieldAnnotation(visible = false, displayName = "虚拟部门人员对应日志", groupName = "基本信息")
    public Set<VirtualDeptPersonLog> getVirtualDeptPersonLogs() {
        return virtualDeptPersonLogs;
    }

    public void setVirtualDeptPersonLogs(Set<VirtualDeptPersonLog> virtualDeptPersonLogs) {
        this.virtualDeptPersonLogs = virtualDeptPersonLogs;
    }

    public BigDecimal getA0152() {
        return a0152;
    }

    public void setA0152(BigDecimal a0152) {
        BigDecimal old = this.a0152;
        this.a0152 = a0152;
        this.firePropertyChange("a0152", old, a0152);
    }

    @Override
    public String toString() {
        return a0101;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final A01 other = (A01) obj;
        if ((this.a01_key == null) ? (other.a01_key != null) : !this.a01_key.equals(other.a01_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (this.a01_key != null ? this.a01_key.hashCode() : 0);
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
    public void assignEntityKey(String key) {
        this.a01_key = key;
        new_flag = 1;
    }

    public Integer getA0112() {
        if (a0112 == null) {
            a0112 = 0;
        }
        return a0112;
    }

    public void setA0112(Integer a0112) {
        Integer old = this.a0112;
        this.a0112 = a0112;
        this.firePropertyChange("a0112", old, a0112);
    }

    public int getA0193() {
        return a0193;
    }

    public void setA0193(int a0193) {
        int old = this.a0193;
        this.a0193 = a0193;
        this.firePropertyChange("a0193", old, a0193);
    }

    public Boolean getA0199() {
        return a0199;
    }

    public void setA0199(Boolean a0199) {
        Boolean old = this.a0199;
        this.a0199 = a0199;
        this.firePropertyChange("a0199", old, a0199);
    }

    @OneToMany(mappedBy = "a01", fetch = FetchType.LAZY)
    @FieldAnnotation(visible = true, displayName = "附表")
    public Set<BasePersonAppendix> getBasePersonAppendixs() {
        return basePersonAppendixs;
    }

    public void setBasePersonAppendixs(Set<BasePersonAppendix> basePersonAppendixs) {
        this.basePersonAppendixs = basePersonAppendixs;
    }

    public String getIn_code() {
        return in_code;
    }

    public void setIn_code(String in_code) {
        String old = this.in_code;
        this.in_code = in_code;
        this.firePropertyChange("in_code", old, in_code);
    }
}
