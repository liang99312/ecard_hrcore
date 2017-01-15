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
public enum PaySalesStoreMsg {

    ttl001("计算方案"),
    ttl002("月数据方案"),
    ttl003("新增"),
    msg001("没选择对象"),
    msg002("确定调入人员？"),
    msg003("确定调出人员？"),
    msg004("调出成功"),
    msg005("条件不可为空!"),
    msg006("确定人员班组？"),
    msg007("变动成功"),
    msg008("调入成功"),
    msg009("确定应用所有人员的变动？"),
    msg010("应用成功"),
    msg011("门店名称不能为空！"),
    msg012("已存在这个名称的门店！"),
    msg013("门店另存失败"),
    msg014("门店另存成功"),
    msg015("该组已存在这个名称的门店！"),
    msg016("找不到标识为（PaySalesStore.sales_store）的功能名称记录！"),
    msg017("新增门店失败"),
    msg018("门店删除成功"),
    msg019("请选择门店"),
    msg020("班组已关联人员，不允许删除");

    
    private String value;

    private PaySalesStoreMsg() {
    }

    private PaySalesStoreMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PaySalesStoreMsg.class, this.name(), this.value);
    }
}
