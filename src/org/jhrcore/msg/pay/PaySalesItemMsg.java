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
public enum PaySalesItemMsg {

    ttl001("新增商品类别"),
    ttl002("新增商品"),
    ttl003("编辑"),
    ttl004("快速查找"),
    ttl005("选择层级"),
    msg001("名称不能为空"),
    msg002("该名称已存在"),
    msg003("此类别已添加商品，禁止删除"),
    msg004("商品名称不能为空"),
    msg005("商品名称已存在"),
    msg006("商品编码已存在"),
    msg007("类别名称不能为空"),
    msg008("类别名称已存在");

    private String value;

    private PaySalesItemMsg() {
    }

    private PaySalesItemMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PaySalesItemMsg.class, this.name(), this.value);
    }
}
