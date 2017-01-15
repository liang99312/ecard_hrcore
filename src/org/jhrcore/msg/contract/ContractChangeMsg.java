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
public enum ContractChangeMsg {

    ttl001("合同变更"),
    ttl002("变更合同失败"),
    msg001("请选择变更合同"),
    msg002("变更合同成功");
  
    private String value;

    private ContractChangeMsg() {
    }

    private ContractChangeMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(ContractChangeMsg.class, this.name(), this.value);
    }
}
