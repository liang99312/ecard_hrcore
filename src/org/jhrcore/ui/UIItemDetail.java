/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import java.lang.reflect.Field;

/**
 *
 * @author Administrator
 */
public class UIItemDetail {

    private Field field;
    // 绑定名称，比如person.person_code
    private String bindName;
    private String field_caption;
    private int view_width = 20;
    private boolean editable = true;
    private boolean editable_when_new = true;

    @Override
    public String toString() {
        return bindName + ";" + field_caption;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getBindName() {
        return bindName;
    }

    public void setBindName(String bindName) {
        this.bindName = bindName;
    }

    public String getField_caption() {
        return field_caption;
    }

    public void setField_caption(String field_caption) {
        this.field_caption = field_caption;
    }

    public int getView_width() {
        return view_width;
    }

    public void setView_width(int view_width) {
        this.view_width = view_width;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isEditable_when_new() {
        return editable_when_new;
    }

    public void setEditable_when_new(boolean editable_when_new) {
        this.editable_when_new = editable_when_new;
    }
}
