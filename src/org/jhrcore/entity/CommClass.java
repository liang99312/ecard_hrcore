/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "通用分类表", moduleName = "培训管理")
public class CommClass extends Model implements Serializable, IKey, KeyInterface {

    public String commClass_key;
    @FieldAnnotation(visible = false, displayName = "编码")
    public String code;
    @FieldAnnotation(visible = false, displayName = "父编码")
    public String parent_code;
    @FieldAnnotation(visible = true, displayName = "分类名称")
    public String class_name;
    @FieldAnnotation(visible = false, displayName = "分类标识")
    public String class_code;
    @FieldAnnotation(visible = false, displayName = "创建时间")
    public Date cdate;
    @FieldAnnotation(visible = true, displayName = "上级分类", isEditable = false)
    public transient String parent_name;
    @FieldAnnotation(visible = false, displayName = "创建人")
    public String cuser;
    public transient int new_flag = 0;

    @Id
    public String getCommClass_key() {
        return commClass_key;
    }

    public void setCommClass_key(String commClass_key) {
        String old = this.commClass_key;
        this.commClass_key = commClass_key;
        this.firePropertyChange("commClass_key", old, commClass_key);
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        String old = this.class_code;
        this.class_code = class_code;
        this.firePropertyChange("class_code", old, class_code);
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        String old = this.class_name;
        this.class_name = class_name;
        this.firePropertyChange("class_name", old, class_name);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        String old = this.code;
        this.code = code;
        this.firePropertyChange("code", old, code);
    }

    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }

    public String getParent_code() {
        return parent_code;
    }

    public void setParent_code(String parent_code) {
        String old = this.parent_code;
        this.parent_code = parent_code;
        this.firePropertyChange("parent_code", old, parent_code);
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        Date old = this.cdate;
        this.cdate = cdate;
        this.firePropertyChange("cdate", old, cdate);
    }

    public String getCuser() {
        return cuser;
    }

    public void setCuser(String cuser) {
        String old = this.cuser;
        this.cuser = cuser;
        this.firePropertyChange("cuser", old, cuser);
    }

    @Transient
    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.commClass_key = key;
        this.new_flag = 1;
    }

    @Override
    public String toString() {
        return this.getClass_name();
    }
}
