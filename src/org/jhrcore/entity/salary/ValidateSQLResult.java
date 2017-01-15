/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.entity.salary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author wangzhenhua
 */
public class ValidateSQLResult  implements Serializable{
    private int result = 0;
    private int error_result = 0;//错误结果多少
    private String msg = "";//返回消息，一般用于返回错误的SQL消息
    private int insert_result = 0;//插入记录数
    private int update_result = 0;//更新记录数
    private int del_result = 0;//删除记录数
    private int not_match_result = 0;//无匹配项目数
    private int multi_same_result = 0;//重复项目数
    private List<String> error_comp_keys = new ArrayList<String>();
    private List<String> not_match_keys = new ArrayList<String>();
    private List<String> multi_same_keys = new ArrayList<String>();
    private String resultKey;
    private Date start_date;
    private Date end_date;
    public List<String> getMulti_same_keys() {
        return multi_same_keys;
    }

    public void setMulti_same_keys(List<String> multi_same_keys) {
        this.multi_same_keys = multi_same_keys;
    }

    public List<String> getNot_match_keys() {
        return not_match_keys;
    }

    public void setNot_match_keys(List<String> not_match_keys) {
        this.not_match_keys = not_match_keys;
    }
    
    public int getMulti_same_result() {
        return multi_same_result;
    }

    public void setMulti_same_result(int multi_same_result) {
        this.multi_same_result = multi_same_result;
    }
    
    public int getNot_match_result() {
        return not_match_result;
    }
    
    public void setNot_match_result(int not_match_result) {
        this.not_match_result = not_match_result;
    }
    
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getError_result() {
        return error_result;
    }

    public void setError_result(int error_result) {
        this.error_result = error_result;
    }

    public int getInsert_result() {
        return insert_result;
    }

    public void setInsert_result(int insert_result) {
        this.insert_result = insert_result;
    }

    public int getUpdate_result() {
        return update_result;
    }

    public void setUpdate_result(int update_result) {
        this.update_result = update_result;
    }

    public int getDel_result() {
        return del_result;
    }

    public void setDel_result(int del_result) {
        this.del_result = del_result;
    }

    public List<String> getError_comp_keys() {
        return error_comp_keys;
    }

    public void setError_comp_keys(List<String> error_comp_keys) {
        this.error_comp_keys = error_comp_keys;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getResultKey() {
        return resultKey;
    }

    public void setResultKey(String resultKey) {
        this.resultKey = resultKey;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }
    
}
