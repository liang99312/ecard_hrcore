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

/**
 *
 * @author Administrator
 */
@Entity
@ClassAnnotation(displayName = "语言种类表", moduleName = "国际化")
public class InternationLang extends Model implements Serializable, IKey, KeyInterface {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false, displayName = "国际化资源信息表主键")
    public String internationLang_key;
    @FieldAnnotation(visible = true, fieldWidth = 50, displayName = "语言标识")
    public String locale;
    @FieldAnnotation(visible = true, fieldWidth = 50, displayName = "语种说明")
    public String explain;
    @FieldAnnotation(visible = false, displayName = "是否默认语种")
    public String defaultLang;
    @FieldAnnotation(visible = true, displayName = "顺序")
    public int orderNum;
    public transient long new_flag = 0;

    @Transient
    public long getKey() {
        return new_flag;
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

    @Id
    public String getInternationLang_key() {
        return internationLang_key;
    }

    public void setInternationLang_key(String internationLang_key) {
        this.internationLang_key = internationLang_key;
    }

    @Override
    public void assignEntityKey(String key) {
        this.internationLang_key = key;
        this.new_flag = 1l;
    }

    public String getDefaultLang() {
        return defaultLang;
    }

    public void setDefaultLang(String defaultLang) {
        String old = this.defaultLang;
        this.defaultLang = defaultLang;
        this.firePropertyChange("defaultLang", old, defaultLang);
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        String old = this.explain;
        this.explain = explain;
        this.firePropertyChange("explain", old, explain);
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        String old = this.locale;
        this.locale = locale;
        this.firePropertyChange("locale", old, locale);
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        int old = this.orderNum;
        this.orderNum = orderNum;
        this.firePropertyChange("orderNum", old, orderNum);
    }
}
