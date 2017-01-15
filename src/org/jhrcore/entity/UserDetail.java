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

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "用户公式组合对应表", moduleName = "系统维护")
public class UserDetail extends Model implements Serializable, IKey, KeyInterface {

    public String userDetail_key;
    public String userScheme_key;
    public String detail_key;
    public transient int new_flag = 0;

    @Id
    public String getUserDetail_key() {
        return userDetail_key;
    }

    public void setUserDetail_key(String userDetail_key) {
        String old = this.userDetail_key;
        this.userDetail_key = userDetail_key;
        this.firePropertyChange("userDetail_key", old, userDetail_key);
    }

    public String getUserScheme_key() {
        return userScheme_key;
    }

    public void setUserScheme_key(String userScheme_key) {
        String old = this.userScheme_key;
        this.userScheme_key = userScheme_key;
        this.firePropertyChange("userScheme_key", old, userScheme_key);
    }

    public String getDetail_key() {
        return detail_key;
    }

    public void setDetail_key(String detail_key) {
        String old = this.detail_key;
        this.detail_key = detail_key;
        this.firePropertyChange("detail_key", old, detail_key);
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
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.userDetail_key = key;
        this.new_flag = 1;
    }
}
