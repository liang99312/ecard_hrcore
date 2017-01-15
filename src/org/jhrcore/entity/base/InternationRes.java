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
import org.jhrcore.entity.annotation.FieldAnnotation;
import org.jhrcore.entity.annotation.ObjectListHint;

/**
 *
 * @author Administrator
 */
@Entity
@ClassAnnotation(displayName = "国际化资源转译表", moduleName = "国际化")
public class InternationRes extends Model implements Serializable, IKey, KeyInterface {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false, displayName = "国际化资源信息表主键")
    public String internationRes_key;
    @FieldAnnotation(visible = false, fieldWidth = 50, displayName = "语种类别")
    @ObjectListHint(hqlForObjectList = "select p.locale from InternationLang p   order by p.orderNum")
    public String language;
    @FieldAnnotation(visible = true, displayName = "语种翻译")
    public String res_value;
    public String res_key;
    public transient long new_flag = 0;

    @Id
    public String getInternationRes_key() {
        return internationRes_key;
    }

    public void setInternationRes_key(String internationRes_key) {
        this.internationRes_key = internationRes_key;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        String old = this.language;
        this.language = language;
        this.firePropertyChange("language", old, language);
    }

    public String getRes_value() {
        return res_value;
    }

    public void setRes_value(String res_value) {
        String old = this.res_value;
        this.res_value = res_value;
        this.firePropertyChange("res_value", old, res_value);
    }

    public String getRes_key() {
        return res_key;
    }

    public void setRes_key(String res_key) {
        String old = this.res_key;
        this.res_key = res_key;
        this.firePropertyChange("res_key", old, res_key);
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

    @Override
    public void assignEntityKey(String key) {
        this.internationRes_key = key;
        this.new_flag = 1l;
    }

    @Transient
    public long getKey() {
        return new_flag;
    }
}
