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
// 编码规则定义
@Entity
@ClassAnnotation(displayName="系统计数规则表",moduleName="系统维护")
public class AutoNoRule extends Model implements Serializable, IKey, KeyInterface {

    @FieldAnnotation(visible = false, displayName = "编码规则主键")
    public String autoNoRule_key;
    @FieldAnnotation(visible = false, displayName = "编码规则标识")
    private String autoNoRule_id;
    @FieldAnnotation(visible = true, displayName = "部门")
    public transient String dept_name = "";
    @FieldAnnotation(visible = true, displayName = "人员类别")
    public transient String person_class = "";
    @FieldAnnotation(visible = false, displayName = "规则名称", isUnique = true)
    public String autoNoRule_name;
    @FieldAnnotation(visible = false, displayName = "增加前缀")
    public boolean add_perfix = false;
    @EnumHint(enumList = "顺序编码;按年;按月;按天", nullable = false)
    @FieldAnnotation(visible = true, displayName = "编码方式")
    public String no_unit = "顺序编码";
    @FieldAnnotation(visible = true, displayName = "编码前缀")
    public String perfix = "";
    @FieldAnnotation(visible = true, displayName = "序号位数")
    public int no_lenth = 4;
    @FieldAnnotation(visible = true, displayName = "初始值")
    public int init_no = 0;
    @FieldAnnotation(visible = true, displayName = "增长值")
    public int inc_no = 1;
    @FieldAnnotation(visible = false, displayName = "测试编码前缀")
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
