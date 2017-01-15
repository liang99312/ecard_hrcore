/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "公告信息类别表", moduleName = "系统维护")
public class SysNoticeType extends Model implements Serializable, KeyInterface, IKey {

    @FieldAnnotation(visible = false, displayName = "类别ID", isEditable = true)
    public String sysNoticeType_key;
    @FieldAnnotation(visible = true, displayName = "编码", isEditable = false)
    public String code;
    @FieldAnnotation(visible = true, displayName = "名称", isEditable = true)
    public String content;
    @FieldAnnotation(visible = true, displayName = "是否系统模板", isEditable = false)
    public boolean sys_type = false;//是系统类型，不允许被删除
    private int order_no = 0;
    @FieldAnnotation(visible = false, displayName = "展现类", isEditable = false)
    private transient String form_class;
    private Set<SysNotice> sysNotices;
    public transient int new_flag = 0;

    @Id
    public String getSysNoticeType_key() {
        return sysNoticeType_key;
    }

    public void setSysNoticeType_key(String sysNoticeType_key) {
        this.sysNoticeType_key = sysNoticeType_key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sysNoticeType", fetch = FetchType.LAZY)
    public Set<SysNotice> getSysNotices() {
        return sysNotices;
    }

    public void setSysNotices(Set<SysNotice> sysNotices) {
        this.sysNotices = sysNotices;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }

    public boolean isSys_type() {
        return sys_type;
    }

    public void setSys_type(boolean sys_type) {
        this.sys_type = sys_type;
    }
    @Transient
    public String getForm_class() {
        return form_class;
    }

    public void setForm_class(String form_class) {
        this.form_class = form_class;
    }

    @Override
    public void assignEntityKey(String key) {
        this.sysNoticeType_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }

    @Override
    public String toString() {
        return content;
    }
}
