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
public enum ContractTerminateMsg {
    
    ttl001("终止合同"),
    msg001("该合同已终止"),
    msg002("该合同已解除"),
    msg003("该合同尚未到期！"),
    msg004("最大操作记录不允许超过800");
    
    private String value;

    private ContractTerminateMsg() {
    }

    private ContractTerminateMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(ContractTerminateMsg.class, this.name(), this.value);
    }
}
