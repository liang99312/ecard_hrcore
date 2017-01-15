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
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName="系统分组表",moduleName="系统维护")
public class SysGroup extends Model implements Serializable, IKey, KeyInterface {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false, displayName = "分组ID", groupName = "Default")
    public String sysGroup_key;
    @FieldAnnotation(visible = true, displayName = "分类名称", groupName = "Default")
    public String group_name;
    @FieldAnnotation(visible = false, displayName = "模块标识", groupName = "Default")
    public String module_code;
    public String code;
    @FieldAnnotation(visible = true, displayName = "排序号", groupName = "Default")
    public int order_no = 0;
    public transient int new_flag = 0;

    @Id
    public String getSysGroup_key() {
        return sysGroup_key;
    }

    public void setSysGroup_key(String sysGroup_key) {
        this.sysGroup_key = sysGroup_key;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getModule_code() {
        return module_code;
    }

    public void setModule_code(String module_code) {
        this.module_code = module_code;
    }
    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    @Override
    public String toString() {
        return group_name;
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.sysGroup_key = key;
        this.new_flag = 1;
    }
}
