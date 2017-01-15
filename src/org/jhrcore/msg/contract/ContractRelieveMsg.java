/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.msg.contract;

import org.jhrcore.ui.language.WebHrMessage;

/**
 *
 * @author mxliteboss
 */
public enum ContractRelieveMsg {

    ttl001("请选择"),
    ttl002("合同变更操作 "),
    msg001("解除合同"),
    msg002("取消解除合同"),
    msg003("该合同已到期，请进行终止操作"),
    msg004("该合同已解除"),
    msg005("该合同未被解除"),
    msg006("最大操作记录不允许超过800");
    private String value;

    private ContractRelieveMsg() {
    }

    private ContractRelieveMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(ContractRelieveMsg.class, this.name(), this.value);
    }
}
