/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.entity.commparameter;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.FieldAnnotation;
/**
 *
 * @author DB2INST3
 */
@Entity
public class QueryCommPara extends Model implements Serializable,IKey,KeyInterface {
    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String queryCommPara_key;
    public String para_name;
    public String para_caption;
    public String para_field_type;
    public String para_entity;
    public String para_entity_caption;
    public String para_code;
    public transient int new_flag = 0;

    @Id
    public String getQueryCommPara_key() {
        return queryCommPara_key;
    }

    public void setQueryCommPara_key(String queryCommPara_key) {
        String old = this.queryCommPara_key;
        this.queryCommPara_key = queryCommPara_key;
        this.firePropertyChange("queryCommPara_key", old, queryCommPara_key);
    }

    public String getPara_caption() {
        return para_caption;
    }

    public void setPara_caption(String para_caption) {
        String old = this.para_caption;
        this.para_caption = para_caption;
        this.firePropertyChange("para_caption", old, para_caption);
    }

    public String getPara_code() {
        return para_code;
    }

    public void setPara_code(String para_code) {
        String old = this.para_code;
        this.para_code = para_code;
        this.firePropertyChange("para_code", old, para_code);
    }

    public String getPara_entity() {
        return para_entity;
    }

    public void setPara_entity(String para_entity) {
        String old = this.para_entity;
        this.para_entity = para_entity;
        this.firePropertyChange("para_entity", old, para_entity);
    }

    public String getPara_entity_caption() {
        return para_entity_caption;
    }

    public void setPara_entity_caption(String para_entity_caption) {
        String old = this.para_entity_caption;
        this.para_entity_caption = para_entity_caption;
        this.firePropertyChange("para_entity_caption", old, para_entity_caption);
    }

    public String getPara_field_type() {
        return para_field_type;
    }

    public void setPara_field_type(String para_field_type) {
        String old = this.para_field_type;
        this.para_field_type = para_field_type;
        this.firePropertyChange("para_field_type", old, para_field_type);
    }

    public String getPara_name() {
        return para_name;
    }

    public void setPara_name(String para_name) {
        String old = this.para_name;
        this.para_name = para_name;
        this.firePropertyChange("para_name", old, para_name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QueryCommPara other = (QueryCommPara) obj;
        if ((this.queryCommPara_key == null) ? (other.queryCommPara_key != null) : !this.queryCommPara_key.equals(other.queryCommPara_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + (this.queryCommPara_key != null ? this.queryCommPara_key.hashCode() : 0);
        return hash;
    }

   
    @Override
    public String toString() {
        return para_caption;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.new_flag=1;
        this.queryCommPara_key=key;
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

}
