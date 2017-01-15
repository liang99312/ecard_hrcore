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
 * @author Administrator
 * 这是保存自动编号的实体类
 */
@Entity
@ClassAnnotation(displayName = "系统计数序号表", moduleName = "系统维护")
public class AutoNo extends Model implements Serializable {

    private String autoNo_key;
    // 最新的号码
    private int new_no = 1;
    private transient int new_flag=0;

    @Id
    public String getAutoNo_key() {
        return autoNo_key;
    }

    public void setAutoNo_key(String autoNo_key) {
        this.autoNo_key = autoNo_key;
    }

    public int getNew_no() {
        return new_no;
    }

    public void setNew_no(int new_no) {
        this.new_no = new_no;
    }
    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }
}
