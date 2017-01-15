/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.change;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author Administrator
 * 变动方案中来自人员附表的字段。
 */
@Entity
@ClassAnnotation(displayName = "人事调配字段表", moduleName = "人事管理")
public class ChangeField extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String changeField_key;
    // 人员附表名称
    private String appendix_name;
    // 人员附表中文名称
    @FieldAnnotation(visible = true, displayName = "表名", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public String appendix_displayname;
    // 人员附表属性
    private String appendix_field;
    // 人员附表属性中文名称
    @FieldAnnotation(visible = true, displayName = "字段名", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public String appendix_field_displayName;
    // 从人员表引入的字段名
    private String import_field;
    // 从人员表引入的字段中文名
    @FieldAnnotation(visible = true, displayName = "数据来源项目", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public String import_field_displayName;
    @FieldAnnotation(visible = true, displayName = "项目类型", isEditable = true, editableWhenNew = true, editableWhenEdit = true)
    @EnumHint(enumList = "引入;输入", nullable = false)
    public transient String imported = "引入";
    // 是否是引入字段
    private boolean from_import = false;
    private String import_name;
    @FieldAnnotation(visible = true, displayName = "数据来源", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public String import_displayname;
    @FieldAnnotation(visible = true, displayName = "是否共用")
    public boolean comm_flag = true;
    @FieldAnnotation(visible = false, displayName = "处理方式")
    @EnumHint(enumList = "追加;更新")//用来标示该字段是插入新记录还是更新最新记录
    public String c_type = "追加";
    private ChangeScheme changeScheme;
    public transient int new_flag = 0;
    private String field_type;

    @Id
    public String getChangeField_key() {
        return changeField_key;
    }

    public void setChangeField_key(String changeField_key) {
        String old = this.changeField_key;
        this.changeField_key = changeField_key;
        this.firePropertyChange("changeField_key", old, changeField_key);
    }

    @ManyToOne
    @JoinColumn(name = "changeScheme_key")
    public ChangeScheme getChangeScheme() {
        return changeScheme;
    }

    public void setChangeScheme(ChangeScheme changeScheme) {
        this.changeScheme = changeScheme;
    }

    public boolean isFrom_import() {
        return from_import;
    }

    public void setFrom_import(boolean from_import) {
        boolean old = this.from_import;
        this.from_import = from_import;
        this.firePropertyChange("from_import", old, from_import);
    }

    public String getAppendix_displayname() {
        return appendix_displayname;
    }

    public void setAppendix_displayname(String appendix_displayname) {
        String old = this.appendix_displayname;
        this.appendix_displayname = appendix_displayname;
        this.firePropertyChange("appendix_displayname", old, appendix_displayname);
    }

    public String getAppendix_field() {
        return appendix_field;
    }

    public void setAppendix_field(String appendix_field) {
        String old = this.appendix_field;
        this.appendix_field = appendix_field;
        this.firePropertyChange("appendix_field", old, appendix_field);
    }

    public String getAppendix_field_displayName() {
        return appendix_field_displayName;
    }

    public void setAppendix_field_displayName(String appendix_field_displayName) {
        String old = this.appendix_field_displayName;
        this.appendix_field_displayName = appendix_field_displayName;
        this.firePropertyChange("appendix_field_displayName", old, appendix_field_displayName);
    }

    public String getAppendix_name() {
        return appendix_name;
    }

    public void setAppendix_name(String appendix_name) {
        String old = this.appendix_name;
        this.appendix_name = appendix_name;
        this.firePropertyChange("appendix_name", old, appendix_name);
    }

    public String getImport_field() {
        return import_field;
    }

    public void setImport_field(String import_field) {
        String old = this.import_field;
        this.import_field = import_field;
        this.firePropertyChange("import_field", old, import_field);
    }

    public String getImport_field_displayName() {
        return import_field_displayName;
    }

    public void setImport_field_displayName(String import_field_displayName) {
        String old_value = this.import_field_displayName;
        this.import_field_displayName = import_field_displayName;
        this.firePropertyChange("import_field_displayName", old_value, this.import_field_displayName);
    }

    @Override
    public String toString() {
        return appendix_field_displayName;
    }

    @Override
    public void assignEntityKey(String key) {
        this.changeField_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
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
    public String getImported() {
        if (this.from_import) {
            imported = "引入";
        } else {
            imported = "输入";
        }
        return imported;
    }

    public void setImported(String imported) {
        String old = this.imported;
        this.imported = imported;
        this.from_import = imported.equals("引入");
        this.firePropertyChange("imported", old, imported);
    }

    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        String old = this.field_type;
        this.field_type = field_type;
        this.firePropertyChange("field_type", old, field_type);
    }

    public String getImport_displayname() {
        return import_displayname;
    }

    public void setImport_displayname(String import_displayname) {
        String old = this.import_displayname;
        this.import_displayname = import_displayname;
        this.firePropertyChange("import_displayname", old, import_displayname);
    }

//    public String getDeal_type() {
//        return deal_type;
//    }
//
//    public void setDeal_type(String deal_type) {
//        String old = this.deal_type;
//        this.deal_type = deal_type;
//        this.firePropertyChange("deal_type", old, deal_type);
//    }
    public String getImport_name() {
        return import_name;
    }

    public void setImport_name(String import_name) {
        String old = this.import_name;
        this.import_name = import_name;
        this.firePropertyChange("import_name", old, import_name);
    }

    public boolean isComm_flag() {
        return comm_flag;
    }

    public void setComm_flag(boolean comm_flag) {
        boolean old = this.comm_flag;
        this.comm_flag = comm_flag;
        this.firePropertyChange("comm_flag", old, comm_flag);
    }

    public String getC_type() {
        return c_type;
    }

    public void setC_type(String c_type) {
        String old = this.c_type;
        this.c_type = c_type;
        this.firePropertyChange("c_type", old, c_type);
    }
}
