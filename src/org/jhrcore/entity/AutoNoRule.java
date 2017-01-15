/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author Administrator
 */
// ���������
@Entity
@ClassAnnotation(displayName="ϵͳ���������",moduleName="ϵͳά��")
public class AutoNoRule extends Model implements Serializable, IKey, KeyInterface {

    @FieldAnnotation(visible = false, displayName = "�����������")
    public String autoNoRule_key;
    @FieldAnnotation(visible = false, displayName = "��������ʶ")
    private String autoNoRule_id;
    @FieldAnnotation(visible = true, displayName = "����")
    public transient String dept_name = "";
    @FieldAnnotation(visible = true, displayName = "��Ա���")
    public transient String person_class = "";
    @FieldAnnotation(visible = false, displayName = "��������", isUnique = true)
    public String autoNoRule_name;
    @FieldAnnotation(visible = false, displayName = "����ǰ׺")
    public boolean add_perfix = false;
    @EnumHint(enumList = "˳�����;����;����;����", nullable = false)
    @FieldAnnotation(visible = true, displayName = "���뷽ʽ")
    public String no_unit = "˳�����";
    @FieldAnnotation(visible = true, displayName = "����ǰ׺")
    public String perfix = "";
    @FieldAnnotation(visible = true, displayName = "���λ��")
    public int no_lenth = 4;
    @FieldAnnotation(visible = true, displayName = "��ʼֵ")
    public int init_no = 0;
    @FieldAnnotation(visible = true, displayName = "����ֵ")
    public int inc_no = 1;
    @FieldAnnotation(visible = false, displayName = "���Ա���ǰ׺")
    public String t_perfix;
    public transient int new_flag = 0;

    @Id
    public String getAutoNoRule_key() {
        return autoNoRule_key;
    }

    public void setAutoNoRule_key(String autoNoRule_key) {
        this.autoNoRule_key = autoNoRule_key;
    }

    public boolean isAdd_perfix() {
        return add_perfix;
    }

    public void setAdd_perfix(boolean add_perfix) {
        boolean old = this.add_perfix;
        this.add_perfix = add_perfix;
        this.firePropertyChange("add_perfix", old, add_perfix);
    }

    public String getAutoNoRule_name() {
        return autoNoRule_name;
    }

    public void setAutoNoRule_name(String autoNoRule_name) {
        String old = this.autoNoRule_name;
        this.autoNoRule_name = autoNoRule_name;
        this.firePropertyChange("autoNoRule_name", old, autoNoRule_name);
    }

    public int getNo_lenth() {
        return no_lenth;
    }

    public void setNo_lenth(int no_lenth) {
        int old = this.no_lenth;
        this.no_lenth = no_lenth;
        this.firePropertyChange("no_lenth", old, no_lenth);
    }

    public String getNo_unit() {
        return no_unit;
    }

    public void setNo_unit(String no_unit) {
        String old = this.no_unit;
        this.no_unit = no_unit;
        this.firePropertyChange("no_unit", old, no_unit);
    }

    public String getPerfix() {
        return perfix;
    }

    public void setPerfix(String perfix) {
        String old = this.perfix;
        this.perfix = perfix;
        this.firePropertyChange("perfix", old, perfix);
    }

    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.autoNoRule_key = key;
        this.new_flag = 1;
    }

    public int getInc_no() {
        return inc_no;
    }

    public void setInc_no(int inc_no) {
        int old = this.inc_no;
        this.inc_no = inc_no;
        this.firePropertyChange("inc_no", old, inc_no);
    }

    @Transient
    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    @Transient
    public String getPerson_class() {
        return person_class;
    }

    public void setPerson_class(String person_class) {
        this.person_class = person_class;
    }

    public int getInit_no() {
        return init_no;
    }

    public void setInit_no(int init_no) {
        int old = this.init_no;
        this.init_no = init_no;
        this.firePropertyChange("init_no", old, init_no);
    }

    public String getT_perfix() {
        return t_perfix;
    }

    public void setT_perfix(String t_perfix) {
        String old = this.t_perfix;
        this.t_perfix = t_perfix;
        this.firePropertyChange("t_perfix", old, t_perfix);
    }

    public String getAutoNoRule_id() {
        return autoNoRule_id;
    }

    public void setAutoNoRule_id(String autoNoRule_id) {
        String old = this.autoNoRule_id;
        this.autoNoRule_id = autoNoRule_id;
        this.firePropertyChange("autoNoRule_id", old, autoNoRule_id);
    }
    
}
