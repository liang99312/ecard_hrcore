/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.rebuild;

import org.jhrcore.entity.annotation.ClassAnnotation;

/**
 *
 * @author Administrator
 */
public class TempEntityDef {

    private String entity_class_fullname;
    private String entity_class_name;
    private String entity_class_caption;
    private String condition;

    public TempEntityDef() {
    }

    public TempEntityDef(Class c, String condition) {
        ClassAnnotation ca = (ClassAnnotation) c.getAnnotation(ClassAnnotation.class);
        this.entity_class_fullname = c.getName();
        this.entity_class_name = c.getSimpleName();
        if(ca!=null)
            this.entity_class_caption = ca.displayName();
        this.condition = condition;
    }

    public TempEntityDef(String entity_class_fullname, String entity_class_name, String entity_class_caption, String condition) {
        this.entity_class_fullname = entity_class_fullname;
        this.entity_class_name = entity_class_name;
        this.entity_class_caption = entity_class_caption;
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getEntity_class_fullname() {
        return entity_class_fullname;
    }

    public void setEntity_class_fullname(String entity_class_fullname) {
        this.entity_class_fullname = entity_class_fullname;
    }

    @Override
    public String toString() {
        return entity_class_caption;
    }

    public String getEntity_class_caption() {
        return entity_class_caption;
    }

    public void setEntity_class_caption(String entity_class_caption) {
        this.entity_class_caption = entity_class_caption;
    }

    public String getEntity_class_name() {
        return entity_class_name;
    }

    public void setEntity_class_name(String entity_class_name) {
        this.entity_class_name = entity_class_name;
    }
}
