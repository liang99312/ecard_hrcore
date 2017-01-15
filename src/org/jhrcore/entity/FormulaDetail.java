/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.jhrcore.entity.annotation.FieldAnnotation;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;
/**
 *
 * @author DB2INST3
 */
@Entity
@ClassAnnotation(displayName = "公式表", moduleName = "系统维护")
public class FormulaDetail extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String formulaDetail_key;
    private String detail_name;
    @FieldAnnotation(visible = true, displayName = "公式名称", groupName = "Default")
    public String detail_caption;
    private String entity_name;
    private String entity_caption;
    @FieldAnnotation(visible = true, displayName = "启用", groupName = "Default")
    public boolean use_flag = false;
    @FieldAnnotation(visible = true, displayName = "公式脚本", groupName = "Default")
    public String formula;
    @FieldAnnotation(visible = true, displayName = "公式描述", groupName = "Default")
    // 公式含义，即中文显示
    public String formula_meaning;
    private FormulaScheme formulaScheme;
    @FieldAnnotation(visible = true, displayName = "序号", groupName = "Default")
    public Integer order_no = 1;
    public transient int new_flag = 0;

    @Id
    public String getFormulaDetail_key() {
        return formulaDetail_key;
    }

    public void setFormulaDetail_key(String formulaDetail_key) {
        String old = this.formulaDetail_key;
        this.formulaDetail_key = formulaDetail_key;
        this.firePropertyChange("formulaDetail_key", old, formulaDetail_key);
    }

    public boolean isUse_flag() {
        return use_flag;
    }

    public void setUse_flag(boolean use_flag) {
        boolean old_value = this.use_flag;
        this.use_flag = use_flag;
        this.firePropertyChange("use_flag", old_value, this.use_flag);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formulaScheme_key")
    public FormulaScheme getFormulaScheme() {
        return formulaScheme;
    }

    public void setFormulaScheme(FormulaScheme formulaScheme) {
        this.formulaScheme = formulaScheme;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FormulaDetail other = (FormulaDetail) obj;
        if ((this.formulaDetail_key == null) ? (other.formulaDetail_key != null) : !this.formulaDetail_key.equals(other.formulaDetail_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.formulaDetail_key != null ? this.formulaDetail_key.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return detail_caption;
    }

    @Override
    public void assignEntityKey(String key) {
        this.formulaDetail_key = key;
        this.new_flag = 1;
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
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    public String getDetail_caption() {
        return detail_caption;
    }

    public void setDetail_caption(String detail_caption) {
        String old = this.detail_caption;
        this.detail_caption = detail_caption;
        this.firePropertyChange("detail_caption", old, detail_caption);
    }

    public String getDetail_name() {
        return detail_name;
    }

    public void setDetail_name(String detail_name) {
        String old = this.detail_name;
        this.detail_name = detail_name;
        this.firePropertyChange("detail_name", old, detail_name);
    }

    public String getEntity_caption() {
        return entity_caption;
    }

    public void setEntity_caption(String entity_caption) {
        String old = this.entity_caption;
        this.entity_caption = entity_caption;
        this.firePropertyChange("entity_caption", old, entity_caption);
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        String old = this.entity_name;
        this.entity_name = entity_name;
        this.firePropertyChange("entity_name", old, entity_name);
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        String old = this.formula;
        this.formula = formula;
        this.firePropertyChange("formula", old, formula);
    }

    public String getFormula_meaning() {
        return formula_meaning;
    }

    public void setFormula_meaning(String formula_meaning) {
        String old = this.formula_meaning;
        this.formula_meaning = formula_meaning;
        this.firePropertyChange("formula_meaning", old, formula_meaning);
    }

    public Integer getOrder_no() {
        if(order_no == null)
            return 0;
        return order_no;
    }

    public void setOrder_no(Integer order_no) {
        Integer old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }
    
    
}
