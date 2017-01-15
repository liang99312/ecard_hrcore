package org.jhrcore.entity.ecard;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.jgoodies.binding.beans.Model;
import java.util.Date;

//代码
import javax.persistence.Transient;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;

@Entity
@Table(name = "ecard_chu")
@ClassAnnotation(displayName = "信用卡消费信息表", moduleName = "信用卡管理")
public class Ecard_chu extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String ecard_chu_key;
    @FieldAnnotation(visible = false)
    public String ecard_key;
    @FieldAnnotation(visible = true, displayName = "账户名称", groupName = "Default", not_null = true, editableWhenNew = false, editableWhenEdit = false)
    public String ecard_name;
    @FieldAnnotation(visible = true, displayName = "卡号", groupName = "Default", not_null = true, editableWhenNew = false, editableWhenEdit = false)
    public String ecard_code;
    @FieldAnnotation(visible = true, displayName = "发卡银行", groupName = "Default", not_null = true, editableWhenNew = false, editableWhenEdit = false)
    public String ecard_bank;
    @FieldAnnotation(visible = true, displayName = "年月", groupName = "Default", not_null = true, editableWhenNew = false, editableWhenEdit = false)
    public String chu_ym;
    @FieldAnnotation(visible = true, displayName = "日期", groupName = "Default", not_null = true, editableWhenNew = false, editableWhenEdit = false)
    public Date chu_date;
    @FieldAnnotation(visible = true, displayName = "POS机编号", isEditable = false)
    public String epos_code;
    @FieldAnnotation(visible = true, displayName = "POS机名称", isEditable = false)
    public String epos_name;
    @FieldAnnotation(visible = true, displayName = "消费项目", groupName = "Default", isEditable = false)
    public String chu_item;
    @FieldAnnotation(visible = true, displayName = "费率", groupName = "Default", isEditable = false)
    public String chu_fl;
    @FieldAnnotation(visible = true, displayName = "消费金额", groupName = "Default", isEditable = true)
    public Float chu_je;
    @FieldAnnotation(visible = true, displayName = "备注", groupName = "Default", isEditable = true)
    public String chu_mark;
    public transient int new_flag = 0;
    public transient Float chu_zonge = 0F;
    public transient float chu_sxf = 0F;

    @Id
    public String getEcard_chu_key() {
        return ecard_chu_key;
    }

    public void setEcard_chu_key(String ecard_chu_key) {
        String old = this.ecard_chu_key;
        this.ecard_chu_key = ecard_chu_key;
        this.firePropertyChange("ecard_chu_key", old, ecard_chu_key);
    }

    public String getEcard_key() {
        return ecard_key;
    }

    public void setEcard_key(String ecard_key) {
        this.ecard_key = ecard_key;
    }

    public String getEcard_code() {
        return ecard_code;
    }

    public void setEcard_code(String ecard_code) {
        String old = this.ecard_code;
        this.ecard_code = ecard_code;
        this.firePropertyChange("ecard_code", old, ecard_code);
    }

    public String getEcard_name() {
        return ecard_name;
    }

    public void setEcard_name(String ecard_name) {
        String old = this.ecard_name;
        this.ecard_name = ecard_name;
        this.firePropertyChange("ecard_name", old, ecard_name);
    }

    public String getEcard_bank() {
        return ecard_bank;
    }

    public void setEcard_bank(String ecard_bank) {
        String old = this.ecard_bank;
        this.ecard_bank = ecard_bank;
        this.firePropertyChange("ecard_bank", old, ecard_bank);
    }

    public String getChu_ym() {
        return chu_ym;
    }

    public void setChu_ym(String chu_ym) {
        String old = this.chu_ym;
        this.chu_ym = chu_ym;
        this.firePropertyChange("chu_ym", old, chu_ym);
    }

    public Date getChu_date() {
        return chu_date;
    }

    public void setChu_date(Date chu_date) {
        Date old = this.chu_date;
        this.chu_date = chu_date;
        this.firePropertyChange("chu_date", old, chu_date);
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

    public Float getChu_je() {
        return chu_je;
    }

    public void setChu_je(Float chu_je) {
        Float old = this.chu_je;
        this.chu_je = chu_je;
        this.firePropertyChange("chu_je", old, chu_je);
    }

    @Transient
    public Float getChu_zonge() {
        return chu_zonge;
    }

    public void setChu_zonge(Float chu_zonge) {
        Float old = this.chu_zonge;
        this.chu_zonge = chu_zonge;
        this.firePropertyChange("chu_zonge", old, chu_zonge);
    }

    @Transient
    public float getChu_sxf() {
        return chu_sxf;
    }

    public void setChu_sxf(float chu_sxf) {
        float old = this.chu_sxf;
        this.chu_sxf = chu_sxf;
        this.firePropertyChange("chu_sxf", old, chu_sxf);
    }
    public String getChu_mark() {
        return chu_mark;
    }

    public void setChu_mark(String chu_mark) {
        String old = this.chu_mark;
        this.chu_mark = chu_mark;
        this.firePropertyChange("chu_mark", old, chu_mark);
    }

    public String getChu_item() {
        return chu_item;
    }

    public void setChu_item(String chu_item) {
        String old = this.chu_item;
        this.chu_item = chu_item;
        this.firePropertyChange("chu_item", old, chu_item);
    }

    public String getChu_fl() {
        return chu_fl;
    }

    public void setChu_fl(String chu_fl) {
        String old = this.chu_fl;
        this.chu_fl = chu_fl;
        this.firePropertyChange("chu_fl", old, chu_fl);
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
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
    public void assignEntityKey(String key) {
        this.ecard_chu_key = key;
        this.new_flag = 1;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.ecard_chu_key != null ? this.ecard_chu_key.hashCode() : 0);
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
        final Ecard_chu other = (Ecard_chu) obj;
        if ((this.ecard_chu_key == null) ? (other.ecard_chu_key != null) : !this.ecard_chu_key.equals(other.ecard_chu_key)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ecard_code;
    }

}
