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
public enum PaySalesStockMsg {

    ttl001("选择商品"),
    ttl002("快速查找"),
    ttl003("商品入库记录"),
    ttl004("刷新月汇总信息"),
    ttl005("快速查找"),
    msg001("请选择商品"),
    msg002("请选择门店"),
    msg003("请选择需要计算的体系"),
    msg004("确定要计算选择体系的手工点名？"),
    msg005("计算成功");
    private String value;

    private PaySalesStockMsg() {
    }

    private PaySalesStockMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PaySalesStockMsg.class, this.name(), this.value);
    }
}
