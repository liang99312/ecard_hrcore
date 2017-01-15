/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.base;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
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
@ClassAnnotation(displayName = "版本号信息", moduleName = "国际化")
public class Version extends Model implements Serializable, IKey, KeyInterface {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false, displayName = "版本号主键")
    public String version_key;
    @FieldAnnotation(visible = true, fieldWidth = 50, displayName = "版本号")
    public String version_num;
    @FieldAnnotation(visible = true, fieldWidth = 50, displayName = "创建时间")
    public Date version_time;
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
    public String getVersion_key() {
        return version_key;
    }

    public void setVersion_key(String version_key) {
        this.version_key = version_key;
    }

    public String getVersion_num() {
        return version_num;
    }

    public void setVersion_num(String version_num) {
        this.version_num = version_num;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getVersion_time() {
        return version_time;
    }

    public void setVersion_time(Date version_time) {
        this.version_time = version_time;
    }

    @Override
    public void assignEntityKey(String version_key) {
        this.version_key = version_key;
    }

    @Override
    public String toString() {
        return version_num;
    }
}
