/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.query;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;
import org.jhrcore.entity.base.EntityDef;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "统计分段方案表", moduleName = "系统维护")
public class QueryPart extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String queryPart_key;
    private String entity_name;
    @FieldAnnotation(visible = true, displayName = "表名", groupName = "Default")
    public String entity_caption;
    @FieldAnnotation(visible = true, displayName = "分段名", groupName = "Default")
    public String part_name;
    public List<QueryPartPara> queryPartParas = new ArrayList<QueryPartPara>();
    private String user_code;
    private boolean visible = true;
    public transient EntityDef cur_entity;
    public transient List<EntityDef> entitys;
    public transient int new_flag = 0;

    @Id
    public String getQueryPart_key() {
        return queryPart_key;
    }

    public void setQueryPart_key(String queryPart_key) {
        String old = this.queryPart_key;
        this.queryPart_key = queryPart_key;
        this.firePropertyChange("queryPart_key", old, queryPart_key);
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

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        String old = this.part_name;
        this.part_name = part_name;
        this.firePropertyChange("part_name", old, part_name);
    }

    @OneToMany(mappedBy = "queryPart", fetch = FetchType.LAZY)
    public List<QueryPartPara> getQueryPartParas() {
        if (queryPartParas == null) {
            queryPartParas = new ArrayList();
        }
        return queryPartParas;
    }

    public void setQueryPartParas(List<QueryPartPara> queryPartParas) {
        this.queryPartParas = queryPartParas;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        String old = this.user_code;
        this.user_code = user_code;
        this.firePropertyChange("user_code", old, user_code);
    }

    @Transient
    public List<EntityDef> getEntitys() {
        return entitys;
    }

    public void setEntitys(List<EntityDef> entitys) {
        this.entitys = entitys;
    }

    @Transient
    public EntityDef getCur_entity() {
        return cur_entity;
    }

    public void setCur_entity(EntityDef cur_entity) {
        this.cur_entity = cur_entity;
    }

    @Override
    public void assignEntityKey(String key) {
        this.queryPart_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return part_name;
    }
}
