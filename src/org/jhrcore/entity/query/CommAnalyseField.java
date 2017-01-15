/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.query;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author mxliteboss
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@ClassAnnotation(displayName = "统计方案字段基类表", moduleName = "系统维护")
public class CommAnalyseField extends Model implements Serializable, KeyInterface, IKey {

    @FieldAnnotation(visible = false)
    public String commAnalyseField_key;
    @FieldAnnotation(visible = false)
    public String entity_name;
    @FieldAnnotation(visible = true, displayName = "表名称", groupName = "基本信息", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public String entity_caption;
    @FieldAnnotation(visible = false)
    public String field_name;
    @FieldAnnotation(visible = true, displayName = "项目名称", groupName = "基本信息", isEditable = true, editableWhenNew = true, editableWhenEdit = true)
    public String field_caption;
    // 统计方式
    @FieldAnnotation(visible = true, displayName = "统计方式", groupName = "基本信息", isEditable = true, editableWhenNew = true, editableWhenEdit = true)
    @EnumHint(enumList = "求和;计数;平均;最大;最小", nullable = false)
    public String stat_type = "求和";
    @FieldAnnotation(visible = true, displayName = "统计轴", groupName = "基本信息", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public String cal_type = "X";
    @FieldAnnotation(visible = true, displayName = "是否自定义", groupName = "基本信息", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public boolean part_field = false;
    @FieldAnnotation(visible = true, displayName = "序号", groupName = "基本信息", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public int order_no = 0;
    public transient int new_flag = 0;
    //用于验证SQL语句是否合法
    public transient boolean used = false;
    @FieldAnnotation(visible = true, displayName = "小数位数", groupName = "基本信息", isEditable = true, editableWhenNew = true, editableWhenEdit = true)
    public int decimal_len = 2;
    @FieldAnnotation(visible = true, displayName = "是否自定义", groupName = "基本信息", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public boolean user_define = false;
    @FieldAnnotation(visible = true, displayName = "项目计算表达式", groupName = "基本信息", isEditable = false, editableWhenNew = false, editableWhenEdit = false)
    public String formula_text;
    @FieldAnnotation(visible = false, displayName = "项目计算表达式SQL", groupName = "基本信息")
    public String formula_sql;
    public CommAnalyseScheme commAnalyseScheme;

    @Id
    public String getCommAnalyseField_key() {
        return commAnalyseField_key;
    }

    public void setCommAnalyseField_key(String commAnalyseField_key) {
        this.commAnalyseField_key = commAnalyseField_key;
    }

    public String getCal_type() {
        return cal_type;
    }

    public void setCal_type(String cal_type) {
        this.cal_type = cal_type;
    }

    public int getDecimal_len() {
        return decimal_len;
    }

    public void setDecimal_len(int decimal_len) {
        this.decimal_len = decimal_len;
    }

    public String getEntity_caption() {
        return entity_caption;
    }

    public void setEntity_caption(String entity_caption) {
        this.entity_caption = entity_caption;
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        this.entity_name = entity_name;
    }

    public String getField_caption() {
        return field_caption;
    }

    public void setField_caption(String field_caption) {
        this.field_caption = field_caption;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getFormula_sql() {
        return formula_sql;
    }

    public void setFormula_sql(String formula_sql) {
        this.formula_sql = formula_sql;
    }

    public String getFormula_text() {
        return formula_text;
    }

    public void setFormula_text(String formula_text) {
        this.formula_text = formula_text;
    }
    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    public boolean isPart_field() {
        return part_field;
    }

    public void setPart_field(boolean part_field) {
        this.part_field = part_field;
    }

    public String getStat_type() {
        return stat_type;
    }

    public void setStat_type(String stat_type) {
        this.stat_type = stat_type;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isUser_define() {
        return user_define;
    }

    public void setUser_define(boolean user_define) {
        this.user_define = user_define;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commAnalyseScheme_key")
    public CommAnalyseScheme getCommAnalyseScheme() {
        return commAnalyseScheme;
    }

    public void setCommAnalyseScheme(CommAnalyseScheme commAnalyseScheme) {
        this.commAnalyseScheme = commAnalyseScheme;
    }

    @Override
    public void assignEntityKey(String key) {
        this.commAnalyseField_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }
}
