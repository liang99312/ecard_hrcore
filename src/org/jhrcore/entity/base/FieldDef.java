package org.jhrcore.entity.base;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;
import com.jgoodies.binding.beans.Model;
import javax.persistence.Transient;
import javax.persistence.Table;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.ObjectListHint;

@Entity
@Table(name = "SYSTEM")
@ClassAnnotation(displayName = "字段信息表")
public class FieldDef extends Model implements Serializable, IKey, KeyInterface {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String field_key;
    @FieldAnnotation(visible = true, displayName = "字段顺序", groupName = "Default")
    public int order_no = 1;
    @FieldAnnotation(visible = true, displayName = "字段名称", groupName = "Default", isEditable = true, editableWhenEdit = false, editableWhenNew = true)
    public String field_name;
    @FieldAnnotation(visible = true, displayName = "字段标题", groupName = "Default")
    public String field_caption;
    @FieldAnnotation(visible = true, displayName = "字段类型", groupName = "Default", isEditable = true, editableWhenEdit = false, editableWhenNew = true)
    @EnumHint(enumList = "Integer;String;Float;Date;Boolean", nullable = false)
    public String field_type = "String";
    @FieldAnnotation(visible = true, displayName = "字段宽度", groupName = "Default", editableWhenEdit = false)
    public int field_width = 20;
    @FieldAnnotation(visible = true, displayName = "小数位数", groupName = "Default", editableWhenEdit = false)
    public int field_scale = 0;
    @FieldAnnotation(visible = true, displayName = "显示宽度", groupName = "Default", editableWhenEdit = true)
    public int view_width = 20;
    @FieldAnnotation(visible = true, displayName = "格式化", groupName = "Default")
    @ObjectListHint(hqlForObjectList = "select format from FieldFormat where field_type=[field_type]", editType = "对话框", nullable = true)
    public String format = "";
    @FieldAnnotation(visible = true, displayName = "编码类型", groupName = "Default")
    @ObjectListHint(hqlForObjectList = "select code_name from Code where parent_id = 'ROOT' and used=1", editType = "对话框", nullable = true)
    public String code_type_name;  // 编码类型名称
    @FieldAnnotation(visible = true, displayName = "字段活性", groupName = "Default", visibleWhenNew = false, editableWhenNew = false, editableWhenEdit = false)
    @EnumHint(enumList = "关键字;系统固定项;自定义固定项;自定义已选项;自定义备选项", nullable = false)
    public String field_mark = "自定义已选项";
    @FieldAnnotation(visible = true, displayName = "对齐方式", groupName = "Default")
    @EnumHint(enumList = "左对齐;居中;右对齐", nullable = false)
    public String field_align = "左对齐";
    ////////////////////////////////////////////////////////////////////
    @FieldAnnotation(visible = true, displayName = "是否必填", groupName = "Default")
    public boolean not_null = false;
    @FieldAnnotation(visible = true, displayName = "取值唯一", groupName = "Default")
    public boolean unique_flag = false;
    @FieldAnnotation(visible = true, displayName = "允许保存", groupName = "Default")
    public boolean save_flag = false;
    @FieldAnnotation(visible = true, displayName = "保存时检查", groupName = "Default")
    public boolean not_null_save_check = false;
    @FieldAnnotation(visible = true, displayName = "启用", groupName = "Default")
    public boolean relation_flag = false;
    @FieldAnnotation(visible = true, displayName = "新增触发", groupName = "Default")
    public boolean relation_add_flag = false;
    @FieldAnnotation(visible = true, displayName = "编辑触发", groupName = "Default")
    public boolean relation_edit_flag = false;
    @FieldAnnotation(visible = true, displayName = "保存触发", groupName = "Default")
    public boolean relation_save_flag = false;
    @FieldAnnotation(visible = true, displayName = "关联更新脚本", groupName = "Default")
    public String relation_text;
    @FieldAnnotation(visible = true, displayName = "关联提示脚本", groupName = "Default")
    public String regula_text;
    @FieldAnnotation(visible = true, displayName = "保存时校验", groupName = "Default")
    public boolean regula_save_check = false;
    @FieldAnnotation(visible = true, displayName = "提示信息", groupName = "Default")
    public String regula_msg;
    @FieldAnnotation(visible = true, displayName = "启用", groupName = "Default")
    public boolean regula_use_flag = false;
    @FieldAnnotation(visible = true, displayName = "允许保存", groupName = "Default")
    public boolean regula_save_flag = false;
    ///////////////////////////////////////////////////////////////////
    @FieldAnnotation(visible = false, displayName = "是否可见", groupName = "Default")
    public boolean visible = true;
    @FieldAnnotation(visible = false, displayName = "是否可编辑", groupName = "Default")
    public boolean editable = true;
    @FieldAnnotation(visible = false, displayName = "新增时是否可编辑", groupName = "Default")
    public boolean editablenew = true;
    @FieldAnnotation(visible = false, displayName = "编辑时是否可编辑", groupName = "Default")
    public boolean editableedit = true;
    @FieldAnnotation(visible = false, displayName = "新增时是否可见", groupName = "Default")
    public boolean visiblenew = true;
    @FieldAnnotation(visible = false, displayName = "编辑时是否可见", groupName = "Default")
    public boolean visibleedit = true;
    @FieldAnnotation(visible = false, displayName = "已选", groupName = "Default")
    public boolean used_flag = true;
    @FieldAnnotation(visible = false, displayName = "表名", groupName = "Default")
    public EntityDef entityDef;
    //0:无变化；1：新增；2：编辑；3：删除
    public transient int fun_flag = 0;
//    //0:无变化；1：新增；2：编辑；3：删除
//    private transient int change_flag = 0;
    @FieldAnnotation(visible = true, displayName = "拼音码", view_width = 100, groupName = "Default", isEditable = false)
    public String pym;
    @FieldAnnotation(visible = true, displayName = "默认值", groupName = "Default",view_width=50)
    public String default_value = "";
    @FieldAnnotation(visible = false, displayName = "已选", groupName = "Default")
    public transient String select_flag;
    public transient int new_flag = 0;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        String old = this.format;
        this.format = format;
        this.firePropertyChange("format", old, format);
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        boolean old = this.editable;
        this.editable = editable;
        this.firePropertyChange("editable", old, editable);
    }

    public boolean isNot_null() {
        return not_null;
    }

    public void setNot_null(boolean not_null) {
        boolean old = this.not_null;
        this.not_null = not_null;
        this.firePropertyChange("not_null", old, not_null);
    }

    @Override
    public String toString() {
        return field_caption;
    }

    public String getCode_type_name() {
        return code_type_name;
    }

    public void setCode_type_name(String code_type_name) {
        String old_value = this.code_type_name;
        this.code_type_name = code_type_name;
        this.firePropertyChange("code_type_name", old_value, this.code_type_name);
    }

    @Id
    public String getField_key() {
        return field_key;
    }

    public void setField_key(String field_key) {
        String old = this.field_key;
        this.field_key = field_key;
        this.firePropertyChange("field_key", old, field_key);
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        String old = this.field_name;
        this.field_name = field_name;
        this.firePropertyChange("field_name", old, field_name);
    }

    public String getField_caption() {
        return field_caption;
    }

    public void setField_caption(String field_caption) {
        String old = this.field_caption;
        this.field_caption = field_caption;
        this.firePropertyChange("field_caption", old, field_caption);
    }

    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        String old = this.field_type;
        this.field_type = field_type;
        this.firePropertyChange("field_type", old, field_type);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        boolean old = this.visible;
        this.visible = visible;
        this.firePropertyChange("visible", old, visible);
    }

    @ManyToOne
    @JoinColumn(name = "entity_key")
    public EntityDef getEntityDef() {
        return entityDef;
    }

    public void setEntityDef(EntityDef entityDef) {
        this.entityDef = entityDef;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        int old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }

    public boolean isUsed_flag() {
        return used_flag;
    }

    public void setUsed_flag(boolean used_flag) {
        boolean old = this.used_flag;
        this.used_flag = used_flag;
        this.firePropertyChange("used_flag", old, used_flag);
    }

    public String getField_mark() {
        return field_mark;
    }

    public void setField_mark(String field_mark) {
        String old = this.field_mark;
        this.field_mark = field_mark;
        this.firePropertyChange("field_mark", old, field_mark);
    }

    @Transient
    public int getFun_flag() {
        return fun_flag;
    }

    public void setFun_flag(int fun_flag) {
        this.fun_flag = fun_flag;
    }

    public boolean isEditableedit() {
        return editableedit;
    }

    public void setEditableedit(boolean editableEdit) {
        boolean old = this.editableedit;
        this.editableedit = editableEdit;
        this.firePropertyChange("editableedit", old, editableEdit);
    }

    public boolean isEditablenew() {
        return editablenew;
    }

    public void setEditablenew(boolean editableNew) {
        boolean old = this.editablenew;
        this.editablenew = editableNew;
        this.firePropertyChange("editablenew", old, editableNew);
    }

    public boolean isVisibleedit() {
        return visibleedit;
    }

    public void setVisibleedit(boolean visibleEdit) {
        boolean old = this.visibleedit;
        this.visibleedit = visibleEdit;
        this.firePropertyChange("visibleedit", old, visibleEdit);
    }

    public boolean isVisiblenew() {
        return visiblenew;
    }

    public void setVisiblenew(boolean visibleNew) {
        boolean old = this.visiblenew;
        this.visiblenew = visibleNew;
        this.firePropertyChange("visiblenew", old, visibleNew);
    }

    public int getField_scale() {
        return field_scale;
    }

    public void setField_scale(int field_scale) {
        int old = this.field_scale;
        this.field_scale = field_scale;
        this.firePropertyChange("field_scale", old, field_scale);
    }

    public int getField_width() {
        return field_width;
    }

    public void setField_width(int field_width) {
        int old = this.field_width;
        this.field_width = field_width;
        this.firePropertyChange("field_width", old, field_width);
    }

    public String getDefault_value() {
        return default_value;
    }

    public void setDefault_value(String default_value) {
        String old = this.default_value;
        this.default_value = default_value;
        this.firePropertyChange("default_value", old, default_value);
    }

    public int getView_width() {
        return view_width;
    }

    public void setView_width(int view_width) {
        int old = this.view_width;
        this.view_width = view_width;
        this.firePropertyChange("view_width", old, view_width);
    }

    @Transient
    public String getSelect_flag() {
        return select_flag;
    }

    public void setSelect_flag(String select_flag) {
        String old = this.select_flag;
        this.select_flag = select_flag;
        this.firePropertyChange("select_flag", old, select_flag);
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.field_key = key;
        this.new_flag = 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FieldDef other = (FieldDef) obj;
        if ((this.field_key == null) ? (other.field_key != null) : !this.field_key.equals(other.field_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.field_key != null ? this.field_key.hashCode() : 0);
        return hash;
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

    public String getPym() {
        return pym;
    }

    public void setPym(String pym) {
        String old = this.pym;
        this.pym = pym;
        this.firePropertyChange("pym", old, pym);
    }

    public String getField_align() {
        return field_align;
    }

    public void setField_align(String field_align) {
        String old = this.field_align;
        this.field_align = field_align;
        this.firePropertyChange("field_align", old, field_align);
    }

    public boolean isSave_flag() {
        return save_flag;
    }

    public void setSave_flag(boolean save_flag) {
        boolean old = this.save_flag;
        this.save_flag = save_flag;
        this.firePropertyChange("save_flag", old, save_flag);
    }

    public boolean isUnique_flag() {
        return unique_flag;
    }

    public void setUnique_flag(boolean unique_flag) {
        boolean old = this.unique_flag;
        this.unique_flag = unique_flag;
        this.firePropertyChange("unique_flag", old, unique_flag);
    }

    public boolean isRelation_flag() {
        return relation_flag;
    }

    public void setRelation_flag(boolean relation_flag) {
        boolean old = this.relation_flag;
        this.relation_flag = relation_flag;
        this.firePropertyChange("relation_flag", old, relation_flag);
    }

    public String getRelation_text() {
        return relation_text;
    }

    public void setRelation_text(String relation_text) {
        String old = this.relation_text;
        this.relation_text = relation_text;
        this.firePropertyChange("relation_text", old, relation_text);
    }

    public String getRegula_msg() {
        return regula_msg;
    }

    public void setRegula_msg(String regula_msg) {
        String old = this.regula_msg;
        this.regula_msg = regula_msg;
        this.firePropertyChange("regula_msg", old, regula_msg);
    }

    public boolean isRegula_save_flag() {
        return regula_save_flag;
    }

    public void setRegula_save_flag(boolean regula_save_flag) {
        boolean old = this.regula_save_flag;
        this.regula_save_flag = regula_save_flag;
        this.firePropertyChange("regula_save_flag", old, regula_save_flag);
    }

    public String getRegula_text() {
        return regula_text;
    }

    public void setRegula_text(String regula_text) {
        String old = this.regula_text;
        this.regula_text = regula_text;
        this.firePropertyChange("regula_text", old, regula_text);
    }

    public boolean isRegula_use_flag() {
        return regula_use_flag;
    }

    public void setRegula_use_flag(boolean regula_use_flag) {
        boolean old = this.regula_use_flag;
        this.regula_use_flag = regula_use_flag;
        this.firePropertyChange("regula_use_flag", old, regula_use_flag);
    }

    public boolean isRelation_add_flag() {
        return relation_add_flag;
    }

    public void setRelation_add_flag(boolean relation_add_flag) {
        boolean old = this.relation_add_flag;
        this.relation_add_flag = relation_add_flag;
        this.firePropertyChange("relation_add_flag", old, relation_add_flag);
    }

    public boolean isRelation_edit_flag() {
        return relation_edit_flag;
    }

    public void setRelation_edit_flag(boolean relation_edit_flag) {
        boolean old = this.relation_edit_flag;
        this.relation_edit_flag = relation_edit_flag;
        this.firePropertyChange("relation_edit_flag", old, relation_edit_flag);
    }

    public boolean isRelation_save_flag() {
        return relation_save_flag;
    }

    public void setRelation_save_flag(boolean relation_save_flag) {
        boolean old = this.relation_save_flag;
        this.relation_save_flag = relation_save_flag;
        this.firePropertyChange("relation_save_flag", old, relation_save_flag);
    }

    public boolean isNot_null_save_check() {
        return not_null_save_check;
    }

    public void setNot_null_save_check(boolean not_null_save_check) {
        boolean old = this.not_null_save_check;
        this.not_null_save_check = not_null_save_check;
        this.firePropertyChange("not_null_save_check", old, not_null_save_check);
    }

    public boolean isRegula_save_check() {
        return regula_save_check;
    }

    public void setRegula_save_check(boolean regula_save_check) {
        boolean old = this.regula_save_check;
        this.regula_save_check = regula_save_check;
        this.firePropertyChange("regula_save_check", old, regula_save_check);
    }
}
