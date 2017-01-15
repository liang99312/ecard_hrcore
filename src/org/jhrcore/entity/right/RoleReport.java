/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.right;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.report.ReportDef;
import javax.persistence.Transient;

/**
 *
 * @author yangzhou
 */
@Entity
public class RoleReport extends Model implements Serializable, KeyInterface, IKey {

    public String role_report_key;
    private Role role;
    private ReportDef reportDef;
    private int fun_flag = 0;
    public transient int new_flag = 0;

    @Id
    public String getRole_report_key() {
        return role_report_key;
    }

    public void setRole_report_key(String role_report_key) {
        String old = this.role_report_key;
        this.role_report_key = role_report_key;
        this.firePropertyChange("role_report_key", old, role_report_key);
    }

    public int getFun_flag() {
        return fun_flag;
    }

    public void setFun_flag(int fun_flag) {
        int old = this.fun_flag;
        this.fun_flag = fun_flag;
        this.firePropertyChange("fun_flag", old, fun_flag);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportDef_key")
    public ReportDef getReportDef() {
        return reportDef;
    }

    public void setReportDef(ReportDef reportDef) {
        this.reportDef = reportDef;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_key")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public void assignEntityKey(String key) {
        this.new_flag = 1;
        this.role_report_key = key;
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
        final RoleReport other = (RoleReport) obj;
        if ((this.role_report_key == null) ? (other.role_report_key != null) : !this.role_report_key.equals(other.role_report_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.role_report_key != null ? this.role_report_key.hashCode() : 0);
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
