/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.sanalyse;

/**
 *
 * @author Administrator
 */
public class SAnalyseField {
    
    public String entity_name;
    public String field_name;
    public String field_content;
    public String field_style;
    public String field_type;
    public String code_name;

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        this.entity_name = entity_name;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getField_style() {
        return field_style;
    }

    public void setField_style(String field_style) {
        this.field_style = field_style;
    }

    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        this.field_type = field_type;
    }

    public String getField_content() {
        return field_content;
    }

    public void setField_content(String field_content) {
        this.field_content = field_content;
    }

    public String getCode_name() {
        return code_name;
    }

    public void setCode_name(String code_name) {
        this.code_name = code_name;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + (this.entity_name != null ? this.entity_name.hashCode() : 0);
        hash = 19 * hash + (this.field_name != null ? this.field_name.hashCode() : 0);
        hash = 19 * hash + (this.field_style != null ? this.field_style.hashCode() : 0);
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
        final SAnalyseField other = (SAnalyseField) obj;
        if ((this.entity_name == null) ? (other.entity_name != null) : !this.entity_name.equals(other.entity_name)) {
            return false;
        }
        if ((this.field_name == null) ? (other.field_name != null) : !this.field_name.equals(other.field_name)) {
            return false;
        }
        if ((this.field_style == null) ? (other.field_style != null) : !this.field_style.equals(other.field_style)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        return this.field_content;
    }
}
