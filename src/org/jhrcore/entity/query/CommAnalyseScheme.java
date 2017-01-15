/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.query;

import com.jgoodies.binding.beans.Model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@ClassAnnotation(displayName = "统计方案基类表", moduleName = "系统维护")
public class CommAnalyseScheme extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String commAnalyseScheme_key;
    @FieldAnnotation(visible = true, displayName = "方案名称", groupName = "Default")
    public String scheme_name;
    @FieldAnnotation(visible = true, displayName = "分组名称", groupName = "Default")
    public String group_name;
    public String a0191;
    public int analyse_type = 1;
    public String scheme_type = "部门对比";
    @FieldAnnotation(visible = true, displayName = "是否共享", groupName = "Default")
    @EnumHint(enumList = "是;否")
    public String share_flag = "否";
    @FieldAnnotation(visible = true, displayName = "共享方式", groupName = "Default")
    @EnumHint(enumList = "所有人;手动指定")
    public String share_type = "否";
    public transient QueryScheme queryScheme;
    public transient boolean fetch_scheme_flag = false;
    public List<CommAnalyseField> commAnalyseFields = new ArrayList<CommAnalyseField>();
    public transient int new_flag = 0;
    public String user_code;

    @Id
    public String getCommAnalyseScheme_key() {
        return commAnalyseScheme_key;
    }

    public void setCommAnalyseScheme_key(String commAnalyseScheme_key) {
        this.commAnalyseScheme_key = commAnalyseScheme_key;
    }

    @Transient
    public List<String> getCalCaptions() {
        List<String> list = new ArrayList<String>();
//        for (CommAnalyseField qaf : commAnalyseFields) {
//            String caption = qaf.getField_caption() == null ? "" : qaf.getField_caption();
//            if (!qaf.getStat_type().equals("普通")) {
//                caption = caption + "(" + qaf.getStat_type() + ")";
//                list.add(caption);
//            }
//        }
        return list;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        String old = this.group_name;
        this.group_name = group_name;
        this.firePropertyChange("group_name", old, group_name);
    }

    public String getScheme_name() {
        return scheme_name;
    }

    public void setScheme_name(String scheme_name) {
        String old = this.scheme_name;
        this.scheme_name = scheme_name;
        this.firePropertyChange("scheme_name", old, scheme_name);
    }

    public String getA0191() {
        return a0191;
    }

    public void setA0191(String a0191) {
        String old = this.a0191;
        this.a0191 = a0191;
        this.firePropertyChange("a0191", old, a0191);
    }

    public int getAnalyse_type() {
        return analyse_type;
    }

    public void setAnalyse_type(int analyse_type) {
        this.analyse_type = analyse_type;
    }

    @Override
    public String toString() {
        return this.scheme_name;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        String old = this.user_code;
        this.user_code = user_code;
        this.firePropertyChange("user_code", old, user_code);
    }

    public String getShare_flag() {
        return share_flag;
    }

    public void setShare_flag(String share_flag) {
        String old = this.share_flag;
        this.share_flag = share_flag;
        this.firePropertyChange("share_flag", old, share_flag);
    }

    @Override
    public void assignEntityKey(String key) {
        this.commAnalyseScheme_key = key;
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
        this.new_flag = new_flag;
    }

    @Transient
    public boolean isFetch_scheme_flag() {
        return fetch_scheme_flag;
    }

    public void setFetch_scheme_flag(boolean fetch_scheme_flag) {
        this.fetch_scheme_flag = fetch_scheme_flag;
    }

    @Transient
    public QueryScheme getQueryScheme() {
        return queryScheme;
    }

    public void setQueryScheme(QueryScheme queryScheme) {
        this.queryScheme = queryScheme;
    }

    @OneToMany(mappedBy = "commAnalyseScheme", fetch = FetchType.LAZY)
    public List<CommAnalyseField> getCommAnalyseFields() {
        return commAnalyseFields;
    }

    public void setCommAnalyseFields(List<CommAnalyseField> commAnalyseFields) {
        this.commAnalyseFields = commAnalyseFields;
    }

    public String getScheme_type() {
        return scheme_type;
    }

    public void setScheme_type(String scheme_type) {
        this.scheme_type = scheme_type;
    }

    public String getShare_type() {
        return share_type;
    }

    public void setShare_type(String share_type) {
        this.share_type = share_type;
    }

    @Transient
    public String getFieldClassName() {
        return "org.jhrcore.entity.query.CommAnalyseField";
    }
}
