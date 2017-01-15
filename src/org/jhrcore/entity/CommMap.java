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
@ClassAnnotation(displayName = "通用业务授权表", moduleName = "系统维护")
public class CommMap extends Model implements Serializable, IKey, KeyInterface {

    @FieldAnnotation(visible = false, displayName = "ID")
    public String commMap_key;
    @FieldAnnotation(visible = true, displayName = "业务名称", view_width = 80)
    public String map_name;
    @FieldAnnotation(visible = false, displayName = "业务类型")
    public String map_type;//wake,cal,report---警戒提示;自动计算;我的报表
    @FieldAnnotation(visible = false, displayName = "业务ID")
    public String map_key;
    @FieldAnnotation(visible = false, displayName = "用户ID")
    //一般写入roleA01_key
    public String user_key;
    @FieldAnnotation(visible = false, displayName = "创建用户ID")
    public String c_user_key;
    @FieldAnnotation(visible = true, displayName = "创建时间")
    public Date c_date;
    @FieldAnnotation(visible = false, displayName = "业务参数")
    public String map_para;
    @FieldAnnotation(visible = true, displayName = "创建用户")
    public String c_user;
    public transient int new_flag = 0;

    @Id
    public String getCommMap_key() {
        return commMap_key;
    }

    public void setCommMap_key(String commMap_key) {
        this.commMap_key = commMap_key;
    }

    public Date getC_date() {
        return c_date;
    }

    public void setC_date(Date c_date) {
        this.c_date = c_date;
    }

    public String getC_user() {
        return c_user;
    }

    public void setC_user(String c_user) {
        this.c_user = c_user;
    }

    public String getMap_key() {
        return map_key;
    }

    public void setMap_key(String map_key) {
        this.map_key = map_key;
    }

    public String getUser_key() {
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }

    public String getMap_type() {
        return map_type;
    }

    public void setMap_type(String map_type) {
        this.map_type = map_type;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.commMap_key = key;
        this.new_flag = 1;
    }

    @Override
    public String toString() {
        return map_name;
    }

    public String getMap_name() {
        return map_name;
    }

    public void setMap_name(String map_name) {
        this.map_name = map_name;
    }

    public String getMap_para() {
        return map_para;
    }

    public void setMap_para(String map_para) {
        this.map_para = map_para;
    }

    public String getC_user_key() {
        return c_user_key;
    }

    public void setC_user_key(String c_user_key) {
        this.c_user_key = c_user_key;
    }
}
