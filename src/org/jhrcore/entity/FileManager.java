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
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;
import javax.persistence.Transient;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "文件管理信息表", moduleName = "系统维护")
public class FileManager extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String fileManager_key;
    @FieldAnnotation(visible = true, displayName = "文件名", view_width = 30)
    public String file_name;
    @FieldAnnotation(visible = true, displayName = "文件类型")
    public String file_type;
    @FieldAnnotation(visible = true, displayName = "文件大小(K)")
    public float file_size = 0;
    @FieldAnnotation(visible = true, displayName = "文件夹名称")
    public String file_folder;
    @FieldAnnotation(visible = true, displayName = "文件路径")
    public String file_path;
    @FieldAnnotation(visible = false, displayName = "文件所属类型")
    private String manager_type;
    @FieldAnnotation(visible = false, displayName = "文件匹配码")
    private String manager_code;
    @FieldAnnotation(visible = true, displayName = "用户")
    public String user_name;
    @FieldAnnotation(visible = true, displayName = "上传时间")
    public Date add_time;
    private transient File file;
    public transient int new_flag = 0;

    @Id
    public String getFileManager_key() {
        return fileManager_key;
    }

    public void setFileManager_key(String fileManager_key) {
        String old = this.fileManager_key;
        this.fileManager_key = fileManager_key;
        this.firePropertyChange("fileManager_key", old, fileManager_key);
    }

    public Date getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Date add_time) {
        Date old = this.add_time;
        this.add_time = add_time;
        this.firePropertyChange("add_time", old, add_time);
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        String old = this.file_name;
        this.file_name = file_name;
        this.firePropertyChange("file_name", old, file_name);
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        String old = this.file_path;
        this.file_path = file_path;
        this.firePropertyChange("file_path", old, file_path);
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        String old = this.file_type;
        this.file_type = file_type;
        this.firePropertyChange("file_type", old, file_type);
    }

    public String getManager_code() {
        return manager_code;
    }

    public void setManager_code(String manager_code) {
        String old = this.manager_code;
        this.manager_code = manager_code;
        this.firePropertyChange("manager_code", old, manager_code);
    }

    public String getManager_type() {
        return manager_type;
    }

    public void setManager_type(String manager_type) {
        String old = this.manager_type;
        this.manager_type = manager_type;
        this.firePropertyChange("manager_type", old, manager_type);
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        String old = this.user_name;
        this.user_name = user_name;
        this.firePropertyChange("user_name", old, user_name);
    }

    public String getFile_folder() {
        return file_folder;
    }

    public void setFile_folder(String file_folder) {
        String old = this.file_folder;
        this.file_folder = file_folder;
        this.firePropertyChange("file_folder", old, file_folder);
    }

    @Override
    public String toString() {
        return this.file_name;
    }

    @Override
    public void assignEntityKey(String key) {
        new_flag = 1;
        this.fileManager_key = key;
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
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public float getFile_size() {
        return file_size;
    }

    public void setFile_size(float file_size) {
        float old = this.file_size;
        this.file_size = file_size;
        this.firePropertyChange("file_size", old, file_size);
    }
}
