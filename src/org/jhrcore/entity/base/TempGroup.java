/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.entity.base;

import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author Owner
 */
public class TempGroup {
    @FieldAnnotation(visible = true, displayName = "ÎÄ¼þÂ·¾¶",view_width=75)
    public String group_name;
    public int change_flag = 0;

    public int getChange_flag() {
        return change_flag;
    }

    public void setChange_flag(int change_flag) {
        this.change_flag = change_flag;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
    @Override
    public String toString(){
        return group_name;
    }       
}
