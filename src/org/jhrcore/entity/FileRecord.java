/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity;

import com.jgoodies.binding.beans.Model;
import java.io.File;
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
@ClassAnnotation(displayName = "公共文件记录表")
public class FileRecord extends Model implements Serializable, KeyInterface, IKey {

    @FieldAnnotation(visible = false, displayName = "ID")
    public String fileRecore_key;
    @FieldAnnotation(visible = true, displayName = "文件名")
    public String file_name;
    @FieldAnnotation(visible = true, displayName = "文件类型")
    public String file_type;
    @FieldAnnotation(visible = true, displayName = "文件")
    public String file_path;
    @FieldAnnotation(visible = true, displayName = "上传时间")
    public Date c_date;
    @FieldAnnotation(visible = true, displayName = "上传用户")
    public String c_user;
    @FieldAnnotation(visible = false, displayName = "上传用户")
    public String c_userno;
    @FieldAnnotation(visible = false, displayName = "业务ID")
    public String src_id;
    @FieldAnnotation(visible = true, displayName = "服务器路径")
    public String disk_path = "";
    @FieldAnnotation(visible = false, displayName = "业务标识")
    public String src_code;
    public transient File file;
    public transient int new_flag = 0;

    @Id
    public String getFileRecore_key() {
        return fileRecore_key;
    }

    public void setFileRecore_key(String fileRecore_key) {
        this.fileRecore_key = fileRecore_key;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
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

    public String getDisk_path() {
        return disk_path;
    }

    public void setDisk_path(String disk_path) {
        this.disk_path = disk_path;
    }

    @Transient
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }

    public String getSrc_id() {
        return src_id;
    }

    public void setSrc_id(String src_id) {
        this.src_id = src_id;
    }

    public String getC_userno() {
        return c_userno;
    }

    public void setC_userno(String c_userno) {
        this.c_userno = c_userno;
    }

    public String getSrc_code() {
        return src_code;
    }

    public void setSrc_code(String src_code) {
        this.src_code = src_code;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    @Override
    public String toString() {
        return file_name;
    }

    @Override
    public void assignEntityKey(String key) {
        this.fileRecore_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }
}
