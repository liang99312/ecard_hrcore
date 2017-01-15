/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.query;

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
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *
 * @author mxliteboss
 */
@Entity
@ClassAnnotation(displayName = "统计分段方案详细表", moduleName = "系统维护")
public class QueryPartPara extends Model implements Serializable, KeyInterface, IKey {

    private static final long serialVersionUID = 1L;
    @FieldAnnotation(visible = false)
    public String queryPartPara_key;
    @FieldAnnotation(visible = true, displayName = "分段描述", groupName = "Default")
    public String para_name;
    public String para_mean;
    @FieldAnnotation(visible = true, displayName = "分段内容", groupName = "Default")
    //建议将分段以字符串形式存入，形如：年龄>10;年龄<20;
    public String para_meaning;
    @FieldAnnotation(visible = true, displayName = "表达式", groupName = "Default")
    public String para_text;
    private int order_no = 0;
    private String para_sql;
    public transient int new_flag = 0;
    public QueryPart queryPart;

    @Id
    public String getQueryPartPara_key() {
        return queryPartPara_key;
    }

    public void setQueryPartPara_key(String queryPartPara_key) {
        String old = this.queryPartPara_key;
        this.queryPartPara_key = queryPartPara_key;
        this.firePropertyChange("queryPartPara_key", old, queryPartPara_key);
    }

    public String getPara_mean() {
        return para_mean;
    }

    public void setPara_mean(String para_mean) {
        String old = this.para_mean;
        this.para_mean = para_mean;
        this.firePropertyChange("para_mean", old, para_mean);
    }

    public String getPara_meaning() {
        return para_meaning;
    }

    public void setPara_meaning(String para_meaning) {
        String old = this.para_meaning;
        this.para_meaning = para_meaning;
        this.firePropertyChange("para_meaning", old, para_meaning);
    }

    public String getPara_name() {
        return para_name;
    }

    public void setPara_name(String para_name) {
        String old = this.para_name;
        this.para_name = para_name;
        this.firePropertyChange("para_name", old, para_name);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "queryPart_key")
    public QueryPart getQueryPart() {
        return queryPart;
    }

    public void setQueryPart(QueryPart queryPart) {
        this.queryPart = queryPart;
    }

    public String getPara_text() {
        return para_text;
    }

    public void setPara_text(String para_text) {
        String old = this.para_text;
        this.para_text = para_text;
        this.firePropertyChange("para_text", old, para_text);
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        int old = this.order_no;
        this.order_no = order_no;
        this.firePropertyChange("order_no", old, order_no);
    }

    @Override
    public void assignEntityKey(String key) {
        this.queryPartPara_key = key;
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

    @Override
    public String toString() {
        return para_name;
    }

    public String getPara_sql() {
        return para_sql;
    }

    public void setPara_sql(String para_sql) {
        String old = this.para_sql;
        this.para_sql = para_sql;
        this.firePropertyChange("para_sql", old, para_sql);
    }
}
