/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.EnumHint;

/**
 *
 * @author Administrator
 */
@Entity
@ClassAnnotation(displayName = "公告信息表", moduleName = "系统维护")
public class SysNotice extends Model implements Serializable, KeyInterface, IKey {

    @FieldAnnotation(visible = false)
    public String sysNotice_key;
    @FieldAnnotation(visible = true, displayName = "标题", isEditable = true)
    public String title;
    @FieldAnnotation(visible = true, displayName = "公告内容", isEditable = true, view_width = 80)
    public String context;
    @FieldAnnotation(visible = true, displayName = "发布人", isEditable = true)
    public String author;
    @FieldAnnotation(visible = true, displayName = "起日期", isEditable = true)
    public Date datefrom;
    @FieldAnnotation(visible = true, displayName = "止日期", isEditable = true)
    public Date dateto;
    @FieldAnnotation(visible = true, displayName = "系统发布日期", isEditable = true)
    public Date subdate;
    @FieldAnnotation(visible = true, displayName = "信息类型", isEditable = true)
    @EnumHint(enumList = "普通;文档;超链接", nullable = false)
    public String type;
    @FieldAnnotation(visible = true, displayName = "链接地址", isEditable = true, view_width = 50)
    public String url;
    @FieldAnnotation(visible = true, displayName = "直接打开", isEditable = true)
    public boolean open_flag = false;
    @FieldAnnotation(visible = true, displayName = "发布标识", isEditable = true)
    public boolean used = true;
    public DeptCode deptCode;
    public SysNoticeType sysNoticeType;
    public transient int new_flag = 0;

    @Id
    public String getSysNotice_key() {
        return sysNotice_key;
    }

    public void setSysNotice_key(String sysNotice_key) {
        String old = this.sysNotice_key;
        this.sysNotice_key = sysNotice_key;
        this.firePropertyChange("sysNotice_key", old, sysNotice_key);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        String old = this.author;
        this.author = author;
        this.firePropertyChange("author", old, author);
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        String old = this.context;
        this.context = context;
        this.firePropertyChange("context", old, context);
    }

    public Date getDatefrom() {
        return datefrom;
    }

    public void setDatefrom(Date dateFrom) {
        Date old = this.datefrom;
        this.datefrom = dateFrom;
        this.firePropertyChange("datefrom", old, dateFrom);
    }

    public Date getDateto() {
        return dateto;
    }

    public void setDateto(Date dateTo) {
        Date old = this.dateto;
        this.dateto = dateTo;
        this.firePropertyChange("dateto", old, dateTo);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deptCode_key")
    public DeptCode getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(DeptCode deptCode) {
        this.deptCode = deptCode;
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

    public Date getSubdate() {
        return subdate;
    }

    public void setSubdate(Date subDate) {
        Date old = this.subdate;
        this.subdate = subDate;
        this.firePropertyChange("subdate", old, subDate);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        String old = this.title;
        this.title = title;
        this.firePropertyChange("title", old, title);
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        boolean old = this.used;
        this.used = used;
        this.firePropertyChange("used", old, used);
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        new_flag = 1;
        this.sysNotice_key = key;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sysNoticeType_key")
    public SysNoticeType getSysNoticeType() {
        return sysNoticeType;
    }

    public void setSysNoticeType(SysNoticeType sysNoticeType) {
        this.sysNoticeType = sysNoticeType;
    }

    public boolean isOpen_flag() {
        return open_flag;
    }

    public void setOpen_flag(boolean open_flag) {
        boolean old = this.open_flag;
        this.open_flag = open_flag;
        this.firePropertyChange("open_flag", old, open_flag);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        String old = this.type;
        this.type = type;
        this.firePropertyChange("type", old, type);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return title;
    }
}
