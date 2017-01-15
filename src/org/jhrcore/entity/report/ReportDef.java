/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.report;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.FieldAnnotation;
import org.jhrcore.entity.base.ModuleInfo;
import org.jhrcore.entity.right.RoleReport;
import javax.persistence.Transient;
import org.jhrcore.entity.annotation.ClassAnnotation;

/**
 *
 * @author Administrator
 */
@Entity
@ClassAnnotation(displayName = "报表记录表", moduleName = "报表管理")
public class ReportDef extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String reportDef_key;
    public ModuleInfo moduleInfo;
    @FieldAnnotation(visible = true, displayName = "分组名称", groupName = "基本信息")
    public String report_class;
    @FieldAnnotation(visible = true, displayName = "报表名称", groupName = "基本信息")
    public String report_name;
    @FieldAnnotation(visible = true, displayName = "排序号", groupName = "基本信息")
    public int order_no = 0;
    @FieldAnnotation(visible = true, displayName = "报表编码", groupName = "基本信息")
    public String report_code;
    // 保存该报表的数据源设置，每个报表的保留独立的数据源
    //@FieldAnnotation(visible = false, displayName = "报表数据源")
//    @Column(length = 8000)
//    public String datasource;
    private Set<RoleReport> roleReports;
    public transient int fun_flag = 0;
    public List<ReportField> reportFields = new ArrayList<ReportField>();
    public transient int new_flag = 0;
    private Blob report_cpt_blob;
    private Blob datasource_blob;

    @Id
    public String getReportDef_key() {
        return reportDef_key;
    }

    public void setReportDef_key(String reportDef_key) {
        String old = this.reportDef_key;
        this.reportDef_key = reportDef_key;
        this.firePropertyChange("reportDef_key", old, reportDef_key);
    }

    public Blob getDatasource_blob() {
        return datasource_blob;
    }

    public void setDatasource_blob(Blob datasource_blob) {
        this.datasource_blob = datasource_blob;
    }

    public Blob getReport_cpt_blob() {
        return report_cpt_blob;
    }

    public void setReport_cpt_blob(Blob report_cpt_blob) {
        this.report_cpt_blob = report_cpt_blob;
    }

    @Transient
    public String[] getFieldCaptions() {
        List<String> list = new ArrayList<String>();
        for (ReportField qaf : reportFields) {
            String caption = qaf.getField_caption() == null ? "" : qaf.getField_caption();
            if (!qaf.getStat_type().equals("普通")) {
                caption = caption + "(" + qaf.getStat_type() + ")";
            }
            list.add(caption);
        }
        String[] tmp = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            tmp[i] = list.get(i);
        }
        return tmp;
    }

    public String getReport_class() {
        return report_class;
    }

    public void setReport_class(String report_class) {
        String old = this.report_class;
        this.report_class = report_class;
        this.firePropertyChange("report_class", old, report_class);
    }

    public String getReport_name() {
        return report_name;
    }

    public void setReport_name(String report_name) {
        String old = this.report_name;
        this.report_name = report_name;
        this.firePropertyChange("report_name", old, report_name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReportDef other = (ReportDef) obj;
        if ((this.reportDef_key == null) ? (other.reportDef_key != null) : !this.reportDef_key.equals(other.reportDef_key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.reportDef_key != null ? this.reportDef_key.hashCode() : 0);
        return hash;
    }

    @Transient
    public int getFun_flag() {
        return fun_flag;
    }

    public void setFun_flag(int fun_flag) {
        int old = this.fun_flag;
        this.fun_flag = fun_flag;
        this.firePropertyChange("fun_flag", old, fun_flag);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reportDef", fetch = FetchType.LAZY)
    public Set<RoleReport> getRoleReports() {
        return roleReports;
    }

    public void setRoleReports(Set<RoleReport> roleReports) {
        this.roleReports = roleReports;
    }

    @OneToMany(mappedBy = "reportDef", fetch = FetchType.LAZY)
    public List<ReportField> getReportFields() {
        return reportFields;
    }

    public void setReportFields(List<ReportField> reportFields) {
        this.reportFields = reportFields;
    }

    @Override
    public String toString() {
        return this.report_name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_key")
    public ModuleInfo getModuleInfo() {
        return moduleInfo;
    }

    public void setModuleInfo(ModuleInfo moduleInfo) {
        this.moduleInfo = moduleInfo;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        int old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }

    public String getReport_code() {
        return report_code;
    }

    public void setReport_code(String report_code) {
        String old = this.report_code;
        this.report_code = report_code;
        this.firePropertyChange("report_code", old, report_code);
    }

    @Override
    public void assignEntityKey(String key) {
        this.reportDef_key = key;
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
}
