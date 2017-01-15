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
import org.jhrcore.entity.IObjectAttribute;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;

@Entity
@Table(name = "ecard")
@ClassAnnotation(displayName = "信用卡基本信息表", moduleName = "信用卡管理")
public class Ecard extends Model implements Serializable, IObjectAttribute, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String ecard_key;
    @FieldAnnotation(visible = true, displayName = "账户名称", groupName = "Default", not_null = true)
    public String ecard_name;
    @FieldAnnotation(visible = true, displayName = "身份证号", groupName = "Default", not_null = true)
    public String ecard_sfzh;
    @FieldAnnotation(visible = true, displayName = "联系电话", groupName = "Default", not_null = true)
    public String ecard_phone;
    @FieldAnnotation(visible = true, displayName = "qq及密码", groupName = "Default", not_null = true)
    public String ecard_qq;
    @FieldAnnotation(visible = true, displayName = "卡号", groupName = "Default", not_null = true)
    public String ecard_code;
    @FieldAnnotation(visible = true, displayName = "刷卡管理员", groupName = "Default", not_null = true)
    public String ecard_manager;
    @FieldAnnotation(visible = true, displayName = "用卡人", groupName = "Default", not_null = true)
    public String ecard_user;
    @FieldAnnotation(visible = true, displayName = "发卡银行", groupName = "Default", not_null = true)
    public String ecard_bank;
    @FieldAnnotation(visible = true, displayName = "额度", groupName = "Default", not_null = true)
    public Integer ecard_edu;
    @FieldAnnotation(visible = true, displayName = "下卡日", groupName = "Default", not_null = true)
    public Date ecard_xdate;
    @FieldAnnotation(visible = true, displayName = "到期日", groupName = "Default", not_null = true)
    public Date ecard_ddate;
    @FieldAnnotation(visible = true, displayName = "状态", groupName = "Default", not_null = true)
    @EnumHint(enumList = "已激活;已停止")
    public String ecard_state;
    @FieldAnnotation(visible = true, displayName = "养卡方式", groupName = "Default", not_null = true)
    @EnumHint(enumList = "普养;中养;精养")
    public String ecard_type;
    @FieldAnnotation(visible = true, displayName = "期初欠款", groupName = "Default", not_null = true)
    public Float m_qichu;
    @FieldAnnotation(visible = true, displayName = "欠款", groupName = "Default", not_null = true)
    public Float m_qiankuan;
    @FieldAnnotation(visible = true, displayName = "可用余额", groupName = "Default", not_null = true)
    public Float m_keyong = 0F;
    @FieldAnnotation(visible = true, displayName = "账单日", groupName = "Default", not_null = true)
    public Integer d_zhangdan;
    @FieldAnnotation(visible = true, displayName = "还款日", groupName = "Default", not_null = true)
    public Integer d_huankuan;
    @FieldAnnotation(visible = true, displayName = "汇款总额", groupName = "Default", not_null = true)
    public Integer m_zonge;
    @FieldAnnotation(visible = true, displayName = "单次汇款上限", groupName = "Default", not_null = true)
    public Integer m_shangxian;
    @FieldAnnotation(visible = true, displayName = "单次汇款下限", groupName = "Default", not_null = true)
    public Integer m_xiaxian;
    @FieldAnnotation(visible = true, displayName = "汇款开始日", groupName = "Default", not_null = true)
    public Integer m_hkstart;
    @FieldAnnotation(visible = true, displayName = "汇款结束日", groupName = "Default", not_null = true)
    public Integer m_hkend;
    @FieldAnnotation(visible = true, displayName = "汇款次数", groupName = "Default", not_null = true)
    public Integer m_cishu;
    @FieldAnnotation(visible = true, displayName = "单次消费上限", groupName = "Default", not_null = true)
    public Integer x_shangxian;
    @FieldAnnotation(visible = true, displayName = "消费次数", groupName = "Default", not_null = true)
    public Integer x_cishu;
    @FieldAnnotation(visible = true, displayName = "积分", groupName = "Default", not_null = true)
    public Integer ecard_jifen;
    @FieldAnnotation(visible = true, displayName = "备注", groupName = "Default", not_null = true)
    public String ecard_mark;

    @FieldAnnotation(visible = false, displayName = "编辑类型", editableWhenEdit = false)
    @Transient
    public Integer editType = IObjectAttribute.EDITTYPE_EDIT;
    public transient int new_flag = 0;

    @Id
    public String getEcard_key() {
        return ecard_key;
    }

    public void setEcard_key(String ecard_key) {
        String old = this.ecard_key;
        this.ecard_key = ecard_key;
        this.firePropertyChange("ecard_key", old, ecard_key);
    }

    public String getEcard_code() {
        return ecard_code;
    }

    public void setEcard_code(String ecard_code) {
        String old = this.ecard_code;
        this.ecard_code = ecard_code;
        this.firePropertyChange("ecard_code", old, ecard_code);
    }

    public String getEcard_manager() {
        return ecard_manager;
    }

    public void setEcard_manager(String ecard_manager) {
        String old = this.ecard_manager;
        this.ecard_manager = ecard_manager;
        this.firePropertyChange("ecard_manager", old, ecard_manager);
    }

    public String getEcard_name() {
        return ecard_name;
    }

    public void setEcard_name(String ecard_name) {
        String old = this.ecard_name;
        this.ecard_name = ecard_name;
        this.firePropertyChange("ecard_name", old, ecard_name);
    }

    public String getEcard_sfzh() {
        return ecard_sfzh;
    }

    public void setEcard_sfzh(String ecard_sfzh) {
        String old = this.ecard_sfzh;
        this.ecard_sfzh = ecard_sfzh;
        this.firePropertyChange("ecard_sfzh", old, ecard_sfzh);
    }

    public String getEcard_phone() {
        return ecard_phone;
    }

    public void setEcard_phone(String ecard_phone) {
        String old = this.ecard_phone;
        this.ecard_phone = ecard_phone;
        this.firePropertyChange("ecard_phone", old, ecard_phone);
    }

    public String getEcard_qq() {
        return ecard_qq;
    }

    public void setEcard_qq(String ecard_qq) {
        String old = this.ecard_qq;
        this.ecard_qq = ecard_qq;
        this.firePropertyChange("ecard_qq", old, ecard_qq);
    }

    public Date getEcard_xdate() {
        return ecard_xdate;
    }

    public void setEcard_xdate(Date ecard_xdate) {
        Date old = this.ecard_xdate;
        this.ecard_xdate = ecard_xdate;
        this.firePropertyChange("ecard_xdate", old, ecard_xdate);
    }

    public Date getEcard_ddate() {
        return ecard_ddate;
    }

    public void setEcard_ddate(Date ecard_ddate) {
        Date old = this.ecard_ddate;
        this.ecard_ddate = ecard_ddate;
        this.firePropertyChange("ecard_ddate", old, ecard_ddate);
    }

    public String getEcard_user() {
        return ecard_user;
    }

    public void setEcard_user(String ecard_user) {
        String old = this.ecard_user;
        this.ecard_user = ecard_user;
        this.firePropertyChange("ecard_user", old, ecard_user);
    }

    public String getEcard_bank() {
        return ecard_bank;
    }

    public void setEcard_bank(String ecard_bank) {
        String old = this.ecard_bank;
        this.ecard_bank = ecard_bank;
        this.firePropertyChange("ecard_bank", old, ecard_bank);
    }

    public Integer getEcard_edu() {
        return ecard_edu;
    }

    public void setEcard_edu(Integer ecard_edu) {
        Integer old = this.ecard_edu;
        this.ecard_edu = ecard_edu;
        this.firePropertyChange("ecard_edu", old, ecard_edu);
    }

    public Float getM_qichu() {
        return m_qichu;
    }

    public void setM_qichu(Float m_qichu) {
        Float old = this.m_qichu;
        this.m_qichu = m_qichu;
        this.firePropertyChange("m_qichu", old, m_qichu);
    }

    public Integer getM_hkstart() {
        return m_hkstart;
    }

    public void setM_hkstart(Integer m_hkstart) {
        Integer old = this.m_hkstart;
        this.m_hkstart = m_hkstart;
        this.firePropertyChange("m_hkstart", old, m_hkstart);
    }

    public Integer getM_hkend() {
        return m_hkend;
    }

    public void setM_hkend(Integer m_hkend) {
        Integer old = this.m_hkend;
        this.m_hkend = m_hkend;
        this.firePropertyChange("m_hkend", old, m_hkend);
    }

    public Float getM_qiankuan() {
        return m_qiankuan;
    }

    public void setM_qiankuan(Float m_qiankuan) {
        Float old = this.m_qiankuan;
        this.m_qiankuan = m_qiankuan;
        this.firePropertyChange("m_qiankuan", old, m_qiankuan);
    }

    public Float getM_keyong() {
        return m_keyong;
    }

    public void setM_keyong(Float m_keyong) {
        Float old = this.m_keyong;
        this.m_keyong = m_keyong;
        this.firePropertyChange("m_keyong", old, m_keyong);
    }

    public Integer getM_cishu() {
        return m_cishu;
    }

    public void setM_cishu(Integer m_cishu) {
        Integer old = this.m_cishu;
        this.m_cishu = m_cishu;
        this.firePropertyChange("m_cishu", old, m_cishu);
    }

    public Integer getX_shangxian() {
        return x_shangxian;
    }

    public void setX_shangxian(Integer x_shangxian) {
        Integer old = this.x_shangxian;
        this.x_shangxian = x_shangxian;
        this.firePropertyChange("x_shangxian", old, x_shangxian);
    }

    public Integer getX_cishu() {
        return x_cishu;
    }

    public void setX_cishu(Integer x_cishu) {
        Integer old = this.x_cishu;
        this.x_cishu = x_cishu;
        this.firePropertyChange("x_cishu", old, x_cishu);
    }

    public Integer getD_zhangdan() {
        return d_zhangdan;
    }

    public void setD_zhangdan(Integer d_zhangdan) {
        Integer old = this.d_zhangdan;
        this.d_zhangdan = d_zhangdan;
        this.firePropertyChange("d_zhangdan", old, d_zhangdan);
    }

    public Integer getD_huankuan() {
        return d_huankuan;
    }

    public void setD_huankuan(Integer d_huankuan) {
        Integer old = this.d_huankuan;
        this.d_huankuan = d_huankuan;
        this.firePropertyChange("d_huankuan", old, d_huankuan);
    }

    public Integer getM_shangxian() {
        return m_shangxian;
    }

    public void setM_shangxian(Integer m_shangxian) {
        Integer old = this.m_shangxian;
        this.m_shangxian = m_shangxian;
        this.firePropertyChange("m_shangxian", old, m_shangxian);
    }

    public Integer getM_xiaxian() {
        return m_xiaxian;
    }

    public void setM_xiaxian(Integer m_xiaxian) {
        Integer old = this.m_xiaxian;
        this.m_xiaxian = m_xiaxian;
        this.firePropertyChange("m_xiaxian", old, m_xiaxian);
    }

    public Integer getM_zonge() {
        return m_zonge;
    }

    public void setM_zonge(Integer m_zonge) {
        Integer old = this.m_zonge;
        this.m_zonge = m_zonge;
        this.firePropertyChange("m_zonge", old, m_zonge);
    }

    public Integer getEcard_jifen() {
        return ecard_jifen;
    }

    public void setEcard_jifen(Integer ecard_jifen) {
        Integer old = this.ecard_jifen;
        this.ecard_jifen = ecard_jifen;
        this.firePropertyChange("ecard_jifen", old, ecard_jifen);
    }

    public String getEcard_type() {
        return ecard_type;
    }

    public void setEcard_type(String ecard_type) {
        String old = this.ecard_type;
        this.ecard_type = ecard_type;
        this.firePropertyChange("ecard_type", old, ecard_type);
    }

    public String getEcard_mark() {
        return ecard_mark;
    }

    public void setEcard_mark(String ecard_mark) {
        String old = this.ecard_mark;
        this.ecard_mark = ecard_mark;
        this.firePropertyChange("ecard_mark", old, ecard_mark);
    }

    public String getEcard_state() {
        return ecard_state;
    }

    public void setEcard_state(String ecard_state) {
        String old = this.ecard_state;
        this.ecard_state = ecard_state;
        this.firePropertyChange("ecard_state", old, ecard_state);
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

    public void setEditType(Integer editType) {
        Integer old = this.editType;
        this.editType = editType;
        this.firePropertyChange("editType", old, editType);
    }

    @Transient
    public Integer getEditType() {
        if (ecard_key == null) {
            editType = 0;
        } else {
            editType = 1;
        }
        return editType;
    }

    @Override
    public int editType() {
        return editType;
    }

    @Override
    public void assignEntityKey(String key) {
        this.ecard_key = key;
        this.new_flag = 1;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.ecard_key != null ? this.ecard_key.hashCode() : 0);
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
        final Ecard other = (Ecard) obj;
        if ((this.ecard_key == null) ? (other.ecard_key != null) : !this.ecard_key.equals(other.ecard_key)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ecard_name + "/" + ecard_bank + "/" + ecard_code.substring(ecard_code.length() - 4, ecard_code.length());
    }

}
