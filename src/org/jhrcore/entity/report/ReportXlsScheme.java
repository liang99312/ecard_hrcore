/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.report;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@ClassAnnotation(displayName = "导出方案表", moduleName = "报表管理")
public class ReportXlsScheme extends Model implements Serializable, KeyInterface, IKey {

    public String reportXlsScheme_key;
    private String scheme_name;
    private String person_code;
    public String scheme_title;
    private String entity_name;
    private String scheme_type = "0";//0：系统方案；1：用户自定义方案
    @FieldAnnotation(visible = true, displayName = "条件表达式", groupName = "Default")
    public String condition_expression;
    private Set<ReportXlsDetail> reportXlsDetails;
    private Set<ReportXlsCondition> reportXlsConditions;
    public transient int new_flag = 0;

    @Id
    public String getReportXlsScheme_key() {
        return reportXlsScheme_key;
    }

    public void setReportXlsScheme_key(String reportXlsScheme_key) {
        this.reportXlsScheme_key = reportXlsScheme_key;
    }

    @OneToMany(mappedBy = "reportXlsScheme", fetch = FetchType.LAZY)
    public Set<ReportXlsDetail> getReportXlsDetails() {
        if (reportXlsDetails == null) {
            reportXlsDetails = new HashSet();
        }
        return reportXlsDetails;
    }

    public void setReportXlsDetails(Set<ReportXlsDetail> reportXlsDetails) {
        this.reportXlsDetails = reportXlsDetails;
    }
    @OneToMany(mappedBy = "reportXlsScheme", fetch = FetchType.LAZY)
    public Set<ReportXlsCondition> getReportXlsConditions() {
        if (reportXlsConditions == null) {
            reportXlsConditions = new HashSet();
        }
        return reportXlsConditions;
    }

    public void setReportXlsConditions(Set<ReportXlsCondition> reportXlsConditions) {
        this.reportXlsConditions = reportXlsConditions;
    }
    
    public String getCondition_expression() {
        return condition_expression;
    }

    public void setCondition_expression(String condition_expression) {
        this.condition_expression = condition_expression;
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        this.entity_name = entity_name;
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
        this.person_code = person_code;
    }

    public String getScheme_name() {
        return scheme_name;
    }

    public void setScheme_name(String scheme_name) {
        this.scheme_name = scheme_name;
    }

    public String getScheme_title() {
        return scheme_title;
    }

    public void setScheme_title(String scheme_title) {
        this.scheme_title = scheme_title;
    }

    public String getScheme_type() {
        return scheme_type;
    }

    public void setScheme_type(String scheme_type) {
        this.scheme_type = scheme_type;
    }
    @Override
    public String toString(){
        return this.scheme_name;
    }
    @Override
    public void assignEntityKey(String key) {
        this.reportXlsScheme_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }
}
