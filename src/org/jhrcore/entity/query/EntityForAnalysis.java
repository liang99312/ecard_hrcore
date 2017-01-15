/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.query;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author Administrator
 *
 * 表示可以用来进行查询和分析的实体
 */
@Entity
@Table(name = "EntityForAnalysis")
public class EntityForAnalysis extends Model implements Serializable, KeyInterface, IKey {

    @FieldAnnotation(visible = false)
    public String entityForAnalysis_key;
    // 实体名称
    private String entity_caption;
    // 实体的类全称
    private String entity_fullname;
    // 顺序号
    private int order_no = 0;
    public transient int new_flag = 0;

    @Id
    public String getEntityForAnalysis_key() {
        return entityForAnalysis_key;
    }

    public void setEntityForAnalysis_key(String entityForAnalysis_key) {
        String old = this.entityForAnalysis_key;
        this.entityForAnalysis_key = entityForAnalysis_key;
        this.firePropertyChange("entityForAnalysis_key", old, entityForAnalysis_key);
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        int old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }

    @Override
    public String toString() {
        return entity_caption;
    }

    public String getEntity_caption() {
        return entity_caption;
    }

    public void setEntity_caption(String entity_caption) {
        String old = this.entity_caption;
        this.entity_caption = entity_caption;
        this.firePropertyChange("entity_caption", old, entity_caption);
    }

    public String getEntity_fullname() {
        return entity_fullname;
    }

    public void setEntity_fullname(String entity_fullname) {
        String old = this.entity_fullname;
        this.entity_fullname = entity_fullname;
        this.firePropertyChange("entity_fullname", old, entity_fullname);
    }

    @Override
    public void assignEntityKey(String key) {
        this.entityForAnalysis_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EntityForAnalysis other = (EntityForAnalysis) obj;
        if ((this.entityForAnalysis_key == null) ? (other.entityForAnalysis_key != null) : !this.entityForAnalysis_key.equals(other.entityForAnalysis_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.entityForAnalysis_key != null ? this.entityForAnalysis_key.hashCode() : 0);
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
}
