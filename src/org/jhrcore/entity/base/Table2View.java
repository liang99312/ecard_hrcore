/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.entity.base;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;


import com.jgoodies.binding.beans.Model;

@Entity
public class Table2View  extends Model implements Serializable{
    private String table2View_key;
    
    private String tablename;
    private String viewname;
    private String roleid;
    private String viewscript;
    private boolean success = false;

    @Id
    public String getTable2View_key() {
        return table2View_key;
    }

    public void setTable2View_key(String table2View_key) {
        this.table2View_key = table2View_key;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getViewname() {
        return viewname;
    }

    public void setViewname(String viewname) {
        this.viewname = viewname;
    }

    public String getViewscript() {
        return viewscript;
    }

    public void setViewscript(String viewscript) {
        this.viewscript = viewscript;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    
}
