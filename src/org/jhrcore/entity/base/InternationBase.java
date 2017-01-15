/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.base;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "国际化资源基础表", moduleName = "国际化")
public class InternationBase extends Model implements Serializable, IKey, KeyInterface {

    public String internationBase_key;
    @FieldAnnotation(visible = true, displayName = "字段标识")
    public String res_key;
    @FieldAnnotation(visible = true, displayName = "原文")
    public String res_text;
    @FieldAnnotation(visible = true, displayName = "分组")
    @EnumHint(enumList = "菜单;边框;消息;标签;字段;非字段")
    public String res_flag;
    @FieldAnnotation(visible = true, displayName = "类型")
    public String res_type;
    @FieldAnnotation(visible = true, displayName = "启用", isEditable = false)
    public boolean used = true;
    public transient long new_flag = 0;

    @Id
    public String getInternationBase_key() {
        return internationBase_key;
    }

    public void setInternationBase_key(String internationBase_key) {
        this.internationBase_key = internationBase_key;
    }

    public String getRes_flag() {
        return res_flag;
    }

    public void setRes_flag(String res_flag) {
        this.res_flag = res_flag;
    }

    public String getRes_key() {
        return res_key;
    }

    public void setRes_key(String res_key) {
        String old = this.res_key;
        this.res_key = res_key;
        this.firePropertyChange("res_key", old, res_key);
    }

    public String getRes_text() {
        return res_text;
    }

    public void setRes_text(String res_text) {
        String old = this.res_text;
        this.res_text = res_text;
        this.firePropertyChange("res_text", old, res_text);
    }

    public String getRes_type() {
        return res_type;
    }

    public void setRes_type(String res_type) {
        String old = this.res_type;
        this.res_type = res_type;
        this.firePropertyChange("res_type", old, res_type);
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

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        boolean old = this.used;
        this.used = used;
        this.firePropertyChange("used", old, used);
    }

    @Override
    public void assignEntityKey(String key) {
        this.internationBase_key = key;
        this.new_flag = 1l;
    }

    @Transient
    @Override
    public long getKey() {
        return new_flag;
    }
}
