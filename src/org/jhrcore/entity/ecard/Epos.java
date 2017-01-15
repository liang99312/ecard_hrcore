/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.entity.ecard;

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
 * @author hflj
 */
@Entity
@ClassAnnotation(displayName = "POS机", moduleName = "信用卡管理")
public class Epos extends Model implements Serializable, IKey, KeyInterface  {
    @FieldAnnotation(visible = false,isEditable=false, displayName = "ID")
    public String epos_key;
    @FieldAnnotation(visible = true,isEditable=true,displayName = "编号")
    public String epos_code;
    @FieldAnnotation(visible = true,isEditable=true,displayName = "POS机名称")
    public String epos_name;
    @FieldAnnotation(visible = true,isEditable=true,displayName = "消费项目")
    public String epos_item;
    @FieldAnnotation(visible = true,isEditable=true,displayName = "费率")
    public String epos_fei;
    @FieldAnnotation(visible = true, displayName = "消费上限", groupName = "Default", isEditable = true)
    public Float chu_up = 0F;
    @FieldAnnotation(visible = true, displayName = "消费下限", groupName = "Default", isEditable = true)
    public Float chu_down = 0F;
    @FieldAnnotation(visible = true,isEditable=true,displayName = "保留小数")
    @EnumHint(enumList = "是;否")
    public String epos_xiaoshu;
    @FieldAnnotation(visible = true,isEditable=true,displayName = "备注")
    public String epos_mark;
    public transient int new_flag = 0;
    @Id
    public String getEpos_key() {
        return epos_key;
    }

    public void setEpos_key(String epos_key) {
        this.epos_key = epos_key;
    }

    public String getEpos_code() {
        return epos_code;
    }

    public void setEpos_code(String epos_code) {
        String old = this.epos_code;
        this.epos_code = epos_code;
        this.firePropertyChange("epos_code", old, epos_code);
    }

    public String getEpos_name() {
        return epos_name;
    }

    public void setEpos_name(String epos_name) {
        String old = this.epos_name;
        this.epos_name = epos_name;
        this.firePropertyChange("epos_name", old, epos_name);
    }

    public String getEpos_item() {
        return epos_item;
    }

    public void setEpos_item(String epos_item) {
        String old = this.epos_item;
        this.epos_item = epos_item;
        this.firePropertyChange("epos_item", old, epos_item);
    }

    public String getEpos_fei() {
        return epos_fei;
    }

    public void setEpos_fei(String epos_fei) {
        String old = this.epos_fei;
        this.epos_fei = epos_fei;
        this.firePropertyChange("epos_fei", old, epos_fei);
    }

    public Float getChu_up() {
        return chu_up;
    }

    public void setChu_up(Float chu_up) {
        Float old = this.chu_up;
        this.chu_up = chu_up;
        this.firePropertyChange("chu_up", old, chu_up);
    }

    public Float getChu_down() {
        return chu_down;
    }

    public void setChu_down(Float chu_down) {
        Float old = this.chu_down;
        this.chu_down = chu_down;
        this.firePropertyChange("chu_down", old, chu_down);
    }

    public String getEpos_xiaoshu() {
        return epos_xiaoshu;
    }

    public void setEpos_xiaoshu(String epos_xiaoshu) {
        String old = this.epos_xiaoshu;
        this.epos_xiaoshu = epos_xiaoshu;
        this.firePropertyChange("epos_xiaoshu", old, epos_xiaoshu);
    }

    public String getEpos_mark() {
        return epos_mark;
    }

    public void setEpos_mark(String epos_mark) {
        String old = this.epos_mark;
        this.epos_mark = epos_mark;
        this.firePropertyChange("epos_mark", old, epos_mark);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.epos_key != null ? this.epos_key.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Epos other = (Epos) obj;
        if ((this.epos_key == null) ? (other.epos_key != null) : !this.epos_key.equals(other.epos_key)) {
            return false;
        }
        return true;
    }


    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }
    
    @Transient
    @Override
    public long getKey() {
        return new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.epos_key = key;
        this.new_flag = 1;
    }
    
    @Override
    public String toString(){
        return epos_name;
    }
}
