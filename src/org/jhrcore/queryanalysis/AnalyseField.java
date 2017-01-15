/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.queryanalysis;

import java.rmi.server.UID;


/**
 *
 * @author mxliteboss
 */
public class AnalyseField implements Comparable{
    private String entity_name;//表名
    private String entity_caption;//表的描述
    private String field_name;//字段名
    public String field_caption;//字段描述
    private String analyse_type;//统计轴：X,Y
    private String stat_type;//统计方式：普通；求和；计数；最大；最小；平均
    private String stat_operator;//count;sum;avg;max;min
    private String field_value;//当为field_type为2时，存储对应编码值；为3时对应分段条件；为4是对应年月值
    private int field_type = 0;//当为field_type为0:统计字段;1：该字段为a01_key或deptcode_key;2：Code型字段;3：分段字段；4：年月等特殊字段;-1:合计行
    private String field_desc;
    private String formula_sql;
    private String af_key;
    private boolean ym_flag = false;//是否年月
    private boolean end_flag = false;//用于标识是否是末级编码
    private boolean user_define = false;
    private boolean first_field = false;
    private int decimal_len = 0;
    public String getAnalyse_type() {
        return analyse_type;
    }

    public void setAnalyse_type(String analyse_type) {
        this.analyse_type = analyse_type;
    }

    public String getField_caption() {
        return field_caption;
    }

    public void setField_caption(String field_caption) {
        this.field_caption = field_caption;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getStat_type() {
        return stat_type;
    }

    public void setStat_type(String stat_type) {
        this.stat_type = stat_type;
    }

    public String getEntity_caption() {
        return entity_caption;
    }

    public void setEntity_caption(String entity_caption) {
        this.entity_caption = entity_caption;
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        this.entity_name = entity_name;
    }
    @Override
    public String toString(){
        return field_caption;
    }

    public String getStat_operator() {
        return stat_operator;
    }

    public void setStat_operator(String stat_operator) {
        this.stat_operator = stat_operator;
    }

    public int getField_type() {
        return field_type;
    }

    public void setField_type(int field_type) {
        this.field_type = field_type;
    }

    public String getField_value() {
        return field_value;
    }

    public void setField_value(String field_value) {
        this.field_value = field_value;
    }

    @Override
    public int compareTo(Object o) {
        return this.field_value.compareTo(((AnalyseField)o).getField_value());
    }

    public String getField_desc() {
        return field_desc;
    }

    public void setField_desc(String field_desc) {
        this.field_desc = field_desc;
    }

    public String getFormula_sql() {
        return formula_sql;
    }

    public void setFormula_sql(String formula_sql) {
        this.formula_sql = formula_sql;
    }

    public boolean isUser_define() {
        return user_define;
    }

    public void setUser_define(boolean user_define) {
        this.user_define = user_define;
    }

    public boolean isFirst_field() {
        return first_field;
    }

    public void setFirst_field(boolean first_field) {
        this.first_field = first_field;
    }

    public int getDecimal_len() {
        return decimal_len;
    }

    public void setDecimal_len(int decimal_len) {
        this.decimal_len = decimal_len;
    }

    public String getAf_key() {
        if(af_key == null)
            return new UID().toString();
        return af_key;
    }

    public void setAf_key(String af_key) {
        this.af_key = af_key;
    }

    public boolean isEnd_flag() {
        return end_flag;
    }

    public void setEnd_flag(boolean end_flag) {
        this.end_flag = end_flag;
    }

    public boolean isYm_flag() {
        return ym_flag;
    }

    public void setYm_flag(boolean ym_flag) {
        this.ym_flag = ym_flag;
    }
    
}
