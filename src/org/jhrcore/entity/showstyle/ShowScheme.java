/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.showstyle;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.FieldAnnotation;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;

/**
 *
 * @author Administrator
 */
@Entity
@ClassAnnotation(displayName = "显示方案表", moduleName = "系统维护")
public class ShowScheme extends Model implements Serializable, IKey, KeyInterface {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String showScheme_key;
    @FieldAnnotation(visible = true, displayName = "名称", groupName = "Default")
    public String showScheme_name;
    @FieldAnnotation(visible = false, displayName = "实体名", groupName = "Default")
    protected String entity_name;
    @FieldAnnotation(visible = false, displayName = "默认方案", groupName = "Default")
    protected boolean used_flag = false;
    @FieldAnnotation(visible = false, displayName = "当前使用标识", groupName = "Default")
    public String default_flag = "0";
    public Set<ShowSchemeDetail> showSchemeDetails;
    @FieldAnnotation(visible = false, displayName = "浏览字段允许录入标记", groupName = "Default")
    protected boolean field_right_flag = false;
    private String person_code;
    public Set<ShowSchemeOrder> showSchemeOrders;
    @FieldAnnotation(visible = false, displayName = "是否分组", groupName = "Default")
    private boolean group_flag = false;
    public transient int new_flag = 0;

    @Id
    public String getShowScheme_key() {
        return showScheme_key;
    }

    public void setShowScheme_key(String showScheme_key) {
        String old = this.showScheme_key;
        this.showScheme_key = showScheme_key;
        this.firePropertyChange("showScheme_key", old, showScheme_key);
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        String old = this.entity_name;
        this.entity_name = entity_name;
        this.firePropertyChange("entity_name", old, entity_name);
    }

    @OneToMany(mappedBy = "showScheme", fetch = FetchType.LAZY)
    @OrderBy("order_no")
    public Set<ShowSchemeDetail> getShowSchemeDetails() {
        if (showSchemeDetails == null) {
            showSchemeDetails = new HashSet();
        }
        return showSchemeDetails;
    }

    public void setShowSchemeDetails(Set<ShowSchemeDetail> showSchemeDetails) {
        this.showSchemeDetails = showSchemeDetails;
    }

    public String getShowScheme_name() {
        return showScheme_name;
    }

    public void setShowScheme_name(String showScheme_name) {
        String old = this.showScheme_name;
        this.showScheme_name = showScheme_name;
        this.firePropertyChange("showScheme_name", old, showScheme_name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ShowScheme other = (ShowScheme) obj;
        if ((this.showScheme_key == null) ? (other.showScheme_key != null) : !this.showScheme_key.equals(other.showScheme_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.showScheme_key != null ? this.showScheme_key.hashCode() : 0);
        return hash;
    }

    public boolean isUsed_flag() {
        return used_flag;
    }

    public void setUsed_flag(Boolean used_flag) {
        Boolean old = this.used_flag;
        this.used_flag = used_flag;
        this.firePropertyChange("used_flag", old, used_flag);
    }

    public boolean isField_right_flag() {
        return field_right_flag;
    }

    public void setField_right_flag(Boolean field_right_flag) {
        Boolean old = this.field_right_flag;
        this.field_right_flag = field_right_flag;
        this.firePropertyChange("field_right_flag", old, field_right_flag);
    }

    public String getDefault_flag() {
        return default_flag;
    }

    public void setDefault_flag(String default_flag) {
        String old = this.default_flag;
        this.default_flag = default_flag;
        this.firePropertyChange("default_flag", old, default_flag);
    }

//    public boolean isLogin_used_flag() {
//        return login_used_flag;
//    }
//
//    public void setLogin_used_flag(Boolean login_used_flag) {
//        Boolean old = this.login_used_flag;
//        this.login_used_flag = login_used_flag;
//        this.firePropertyChange("login_used_flag", old, login_used_flag);
//    }
    public String getPerson_code() {
        return person_code;
    }

    public void setPerson_code(String person_code) {
        String old = this.person_code;
        this.person_code = person_code;
        this.firePropertyChange("person_code", old, person_code);
    }

    @OneToMany(mappedBy = "showScheme", fetch = FetchType.LAZY)
    public Set<ShowSchemeOrder> getShowSchemeOrders() {
        if (showSchemeOrders == null) {
            showSchemeOrders = new HashSet<ShowSchemeOrder>();
        }
        return showSchemeOrders;
    }

    public void setShowSchemeOrders(Set<ShowSchemeOrder> showSchemeOrders) {
        this.showSchemeOrders = showSchemeOrders;
    }

    @Override
    public String toString() {
        return showScheme_name;
    }

    public boolean isGroup_flag() {
        return group_flag;
    }

    public void setGroup_flag(boolean group_flag) {
        boolean old = this.group_flag;
        this.group_flag = group_flag;
        this.firePropertyChange("group_flag", old, group_flag);
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.new_flag = 1;
        this.showScheme_key = key;
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
