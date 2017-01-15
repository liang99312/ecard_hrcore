/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.entity.right;

import org.jhrcore.entity.base.ModuleInfo;

/**
 *
 * @author Owner
 */
public class RoleRightTemp {
    private int temp_key;
    public String temp_name;
    private ModuleInfo moduleInfo;
    //0:无权限；1：完全权限；2：部分权限
    public int fun_flag = 0;
    private boolean select_flag = false;

    public int getTemp_key() {
        return temp_key;
    }

    public void setTemp_key(int temp_key) {
        this.temp_key = temp_key;
    }
  
    public int getFun_flag() {
        return fun_flag;
    }

    public void setFun_flag(int fun_flag) {
        this.fun_flag = fun_flag;
    }


    public String getTemp_name() {
        return temp_name;
    }

    public void setTemp_name(String temp_name) {
        this.temp_name = temp_name;
    }

    public boolean isSelect_flag() {
        return select_flag;
    }

    public void setSelect_flag(boolean select_flag) {
        this.select_flag = select_flag;
    }

    public ModuleInfo getModuleInfo() {
        return moduleInfo;
    }

    public void setModuleInfo(ModuleInfo moduleInfo) {
        this.moduleInfo = moduleInfo;
    }
    
    @Override
    public String toString(){
        return temp_name;
    }
}
