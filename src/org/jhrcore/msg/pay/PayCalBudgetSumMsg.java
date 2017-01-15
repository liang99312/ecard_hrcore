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
public enum PayCalBudgetSumMsg {

    ttl001("人员工资预算项目设置"),
    ttl002("生成新月份预算汇总"),
    msg001("该月份工资预算汇总已存在，是否重新生成？"),
    msg002("没有计算项目");
    
    private String value;

    private PayCalBudgetSumMsg() {
    }

    private PayCalBudgetSumMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayCalBudgetSumMsg.class, this.name(), this.value);
    }
}
