/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.change;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import org.jhrcore.entity.IKey;
import org.jhrcore.entity.KeyInterface;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "���µ��丽����ʽ��", moduleName = "���¹���")
public class ChangeMethod extends Model implements Serializable, KeyInterface, IKey {

    public String changeMethod_key;
    // ��Ա��������
    private String appendix_name;
    // ��Ա������������
    @FieldAnnotation(visible = true, displayName = "����", isEditable = false)
    public String appendix_displayname;
    @FieldAnnotation(visible = true, displayName = "����ʽ", isEditable = true)
    @EnumHint(enumList = "׷���¼�¼;�������¼�¼;��������", nullable = false)
    public String method = "׷���¼�¼";
    public ChangeScheme changeScheme;
    public transient int new_flag = 0;

    @Id
    public String getChangeMethod_key() {
        return changeMethod_key;
    }

    public void setChangeMethod_key(String changeMethod_key) {
        this.changeMethod_key = changeMethod_key;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changeScheme_key")
    public ChangeScheme getChangeScheme() {
        return changeScheme;
    }

    public void setChangeScheme(ChangeScheme changeScheme) {
        this.changeScheme = changeScheme;
    }

    public String getAppendix_displayname() {
        return appendix_displayname;
    }

    public void setAppendix_displayname(String appendix_displayname) {
        this.appendix_displayname = appendix_displayname;
    }

    public String getAppendix_name() {
        return appendix_name;
    }

    public void setAppendix_name(String appendix_name) {
        this.appendix_name = appendix_name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Transient
    public int getNew_flag() {
        return new_flag;
    }

    public void setNew_flag(int new_flag) {
        this.new_flag = new_flag;
    }

    @Override
    public void assignEntityKey(String key) {
        this.changeMethod_key = key;
        this.new_flag = 1;
    }

    @Override
    @Transient
    public long getKey() {
        return this.new_flag;
    }
}
