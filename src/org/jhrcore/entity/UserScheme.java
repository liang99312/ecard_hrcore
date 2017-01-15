/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Column;
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
@ClassAnnotation(displayName = "用户公式组合表", moduleName = "系统维护")
public class UserScheme extends Model implements Serializable, IKey, KeyInterface {

    @FieldAnnotation(visible = false, displayName = "ID")
    public String userScheme_key;
    @FieldAnnotation(visible = true, displayName = "排序号")
    public int order_no = 1;
    @FieldAnnotation(visible = true, displayName = "组合方案名称")
    public String scheme_name;
    @FieldAnnotation(visible = false, displayName = "用户编号")
    public String user_code;
    @FieldAnnotation(visible = false, displayName = "方案编码")
    //如：pay：工资；用于后期扩展
    public String scheme_code;
    @FieldAnnotation(visible = false, displayName = "方案编码")
    //如：可用于区别工资体系
    public String scheme_id;
    public transient int new_flag = 0;

    @Id
    public String getUserScheme_key() {
        return userScheme_key;
    }

    public void setUserScheme_key(String userScheme_key) {
        String old = this.userScheme_key;
        this.userScheme_key = userScheme_key;
        this.firePropertyChange("userScheme_key", old, userScheme_key);
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

    public String getScheme_code() {
        return scheme_code;
    }

    public void setScheme_code(String scheme_code) {
        String old = this.scheme_code;
        this.scheme_code = scheme_code;
        this.firePropertyChange("scheme_code", old, scheme_code);
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        String old = this.user_code;
        this.user_code = user_code;
        this.firePropertyChange("user_code", old, user_code);
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        int old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }

    public String getScheme_name() {
        return scheme_name;
    }

    public void setScheme_name(String scheme_name) {
        String old = this.scheme_name;
        this.scheme_name = scheme_name;
        this.firePropertyChange("scheme_name", old, scheme_name);
    }

    public String getScheme_id() {
        return scheme_id;
    }

    public void setScheme_id(String scheme_id) {
        String old = this.scheme_id;
        this.scheme_id = scheme_id;
        this.firePropertyChange("scheme_id", old, scheme_id);
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.userScheme_key = key;
        this.new_flag = 1;
    }

    @Override
    public String toString() {
        return scheme_name;
    }
}
