/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.report;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@ClassAnnotation(displayName = "��������ֶζ�Ӧ��", moduleName = "�������")
public class ReportParaSet extends Model implements Serializable, KeyInterface, IKey {

    @FieldAnnotation(visible = false, displayName = "ID", groupName = "������Ϣ")
    public String reportParaSet_key;
    @FieldAnnotation(visible = false, displayName = "����ID", groupName = "������Ϣ")
    public String reportDef_key;
    @FieldAnnotation(visible = true, displayName = "���������", groupName = "������Ϣ")
    public String paraname;
    @FieldAnnotation(visible = true, displayName = "���Ŷ�Ӧ�ֶ���", groupName = "������Ϣ")
    public String parafield;
    public transient int new_flag = 0;

    @Id
    public String getReportParaSet_key() {
        return reportParaSet_key;
    }

    public void setReportParaSet_key(String reportParaSet_key) {
        this.reportParaSet_key = reportParaSet_key;
    }

    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }

    public String getParafield() {
        return parafield;
    }

    public void setParafield(String parafield) {
        this.parafield = parafield;
    }

    public String getParaname() {
        return paraname;
    }

    public void setParaname(String paraname) {
        this.paraname = paraname;
    }

    public String getReportDef_key() {
        return reportDef_key;
    }

    public void setReportDef_key(String reportDef_key) {
        this.reportDef_key = reportDef_key;
    }

    @Override
    public void assignEntityKey(String key) {
        this.reportParaSet_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return new_flag;
    }
}
