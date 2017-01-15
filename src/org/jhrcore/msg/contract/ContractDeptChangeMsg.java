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
public enum ContractDeptChangeMsg {

    msg001("确定要应用合同？"),
    msg002("应用成功");
    
    private String value;

    private ContractDeptChangeMsg() {
    }

    private ContractDeptChangeMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(ContractDeptChangeMsg.class, this.name(), this.value);
    }
}
