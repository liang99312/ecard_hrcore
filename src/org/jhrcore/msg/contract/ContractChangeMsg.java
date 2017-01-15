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

    ttl001("��ͬ���"),
    ttl002("�����ͬʧ��"),
    msg001("��ѡ������ͬ"),
    msg002("�����ͬ�ɹ�");
  
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
