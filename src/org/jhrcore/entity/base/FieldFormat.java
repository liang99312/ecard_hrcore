/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.entity.base;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName="系统字段格式化表",moduleName="系统维护")
public class FieldFormat extends Model implements Serializable,IKey ,KeyInterface {
    private static final long serialVersionUID = 1L;
    public String fieldFormat_key;
    public String format;
    private String field_type;
    public transient int new_flag = 0;
    @Id
    public String getFieldFormat_key() {
        return fieldFormat_key;
    }

    public void setFieldFormat_key(String fieldFormat_key) {
        this.fieldFormat_key = fieldFormat_key;
    }

    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        this.field_type = field_type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }
    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.new_flag = 1;
        this.fieldFormat_key = key;
    }
    @Override
    public String toString(){
        return format;
    }
}
