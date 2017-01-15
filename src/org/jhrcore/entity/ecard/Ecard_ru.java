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
@Table(name = "ecard_ru")
@ClassAnnotation(displayName = "信用卡汇款信息表", moduleName = "信用卡管理")
public class Ecard_ru extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String ecard_ru_key;
    @FieldAnnotation(visible = false)
    public String ecard_key;
    @FieldAnnotation(visible = true, displayName = "账户名称", groupName = "Default", not_null = true,editableWhenNew=false, editableWhenEdit = false)
    public String ecard_name;
    @FieldAnnotation(visible = true, displayName = "卡号", groupName = "Default", not_null = true,editableWhenNew=false, editableWhenEdit = false)
    public String ecard_code;
    @FieldAnnotation(visible = true, displayName = "发卡银行", groupName = "Default", not_null = true,editableWhenNew=false, editableWhenEdit = false)
    public String ecard_bank;
    @FieldAnnotation(visible = true, displayName = "年月", groupName = "Default", not_null = true,editableWhenNew=false, editableWhenEdit = false)
    public String ru_ym;
    @FieldAnnotation(visible = true, displayName = "日期", groupName = "Default", not_null = true,editableWhenNew=false, editableWhenEdit = false)
    public Date ru_date;
    @FieldAnnotation(visible = true, displayName = "汇款金额", groupName = "Default", isEditable = true)
    public Integer ru_je;
    @FieldAnnotation(visible = true, displayName = "备注", groupName = "Default", isEditable = true)
    public String ru_mark;
    public transient int new_flag = 0;
    public transient int ru_zonge = 0;

    @Id
    public String getEcard_ru_key() {
        return ecard_ru_key;
    }

    public void setEcard_ru_key(String ecard_ru_key) {
        String old = this.ecard_ru_key;
        this.ecard_ru_key = ecard_ru_key;
        this.firePropertyChange("ecard_ru_key", old, ecard_ru_key);
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

    public String getRu_ym() {
        return ru_ym;
    }

    public void setRu_ym(String ru_ym) {
        String old = this.ru_ym;
        this.ru_ym = ru_ym;
        this.firePropertyChange("ru_ym", old, ru_ym);
    }

    public Date getRu_date() {
        return ru_date;
    }

    public void setRu_date(Date ru_date) {
        Date old = this.ru_date;
        this.ru_date = ru_date;
        this.firePropertyChange("ru_date", old, ru_date);
    }

    public Integer getRu_je() {
        return ru_je;
    }

    public void setRu_je(Integer ru_je) {
        Integer old = this.ru_je;
        this.ru_je = ru_je;
        this.firePropertyChange("ru_je", old, ru_je);
    }

    public String getRu_mark() {
        return ru_mark;
    }

    public void setRu_mark(String ru_mark) {
        String old = this.ru_mark;
        this.ru_mark = ru_mark;
        this.firePropertyChange("ru_mark", old, ru_mark);
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
    
    @Transient
    public int getRu_zonge() {
        return ru_zonge;
    }

    public void setRu_zonge(int ru_zonge) {
        int old = this.ru_zonge;
        this.ru_zonge = ru_zonge;
        this.firePropertyChange("ru_zonge", old, ru_zonge);
    }

    @Override
    public void assignEntityKey(String key) {
        this.ecard_ru_key = key;
        this.new_flag = 1;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.ecard_ru_key != null ? this.ecard_ru_key.hashCode() : 0);
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
        final Ecard_ru other = (Ecard_ru) obj;
        if ((this.ecard_ru_key == null) ? (other.ecard_ru_key != null) : !this.ecard_ru_key.equals(other.ecard_ru_key)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ecard_code;
    }

}
