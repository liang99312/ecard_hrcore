/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.report;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.jhrcore.entity.annotation.ClassAnnotation;
import javax.persistence.Transient;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;

/**
 *
 * @author Administrator
 */
@Entity
@ClassAnnotation(displayName = "����ģ�������", moduleName = "�������")
public class ReportModule extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    public String reportModule_key;
    public String reportDef_key;
    public String module_flag;//ģ���ʶ
    private String code = "comm";//ʹ��ģʽ��comm��ϵͳģ��ͨ�ã�PayCalPlan:н��ƻ�����
    public transient long new_flag = 0;

    @Id
    public String getReportModule_key() {
        return reportModule_key;
    }

    public void setReportModule_key(String reportModule_key) {
        this.reportModule_key = reportModule_key;
    }

    public String getModule_flag() {
        return module_flag;
    }

    public void setModule_flag(String module_flag) {
        this.module_flag = module_flag;
    }

    @Transient
    public long getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(long new_flag) {
        this.new_flag = new_flag;
    }

    public String getReportDef_key() {
        return reportDef_key;
    }

    public void setReportDef_key(String reportDef_key) {
        this.reportDef_key = reportDef_key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void assignEntityKey(String key) {
        this.reportModule_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }
}
