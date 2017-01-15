/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.msg.pay;

import org.jhrcore.ui.language.WebHrMessage;

/**
 *
 * @author mxliteboss
 */
public enum PayMonthRecordMsg {

    ttl001("生成汇总表"),
    ttl002("公式编辑"),
    msg001("请选择年月"),
    msg002("开始日期不能大于结束日期"),
    msg003("请选择部门"),
    msg004("开始时间不能大于结束时间"),
    msg005("无公式，不须计算"),
    msg006("计算成功"),
    msg007("参考月份必须为6位数字，如：200801");
    
    private String value;

    private PayMonthRecordMsg() {
    }

    private PayMonthRecordMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayMonthRecordMsg.class, this.name(), this.value);
    }
}
