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

    msg001("ȷ��ҪӦ�ú�ͬ��"),
    msg002("Ӧ�óɹ�");
    
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
