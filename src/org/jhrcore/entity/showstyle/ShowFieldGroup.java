/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.showstyle;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "显示方案分组表", moduleName = "系统维护")
public class ShowFieldGroup extends Model implements Serializable, IKey, KeyInterface {

    @FieldAnnotation(visible = false, displayName = "分组ID")
    public String showFieldGroup_key;
    @FieldAnnotation(visible = true, displayName = "分组名")
    public String group_name;
    @FieldAnnotation(visible = false, displayName = "表名")
    public String entity_name;
    @FieldAnnotation(visible = false, displayName = "用户")
    public String person_code;
    public int order_no = 0;
    public transient int new_flag = 0;

    @Id
    public String getShowFieldGroup_key() {
        return showFieldGroup_key;
    }

    public void setShowFieldGroup_key(String showFieldGroup_key) {
        String old = this.showFieldGroup_key;
        this.showFieldGroup_key = showFieldGroup_key;
        this.firePropertyChange("showFieldGroup_key", old, showFieldGroup_key);
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        String old = this.entity_name;
        this.entity_name = entity_name;
        this.firePropertyChange("entity_name", old, entity_name);
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        String old = this.group_name;
        this.group_name = group_name;
        this.firePropertyChange("group_name", old, group_name);
    }

    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }

    public String getPerson_code() {
        return person_code;
    }

    public void setPerson_code(String person_code) {
        String old = this.person_code;
        this.person_code = person_code;
        this.firePropertyChange("person_code", old, person_code);
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.showFieldGroup_key = key;
        this.new_flag = 1;
    }

    @Override
    public String toString() {
        return group_name;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        int old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }
}
