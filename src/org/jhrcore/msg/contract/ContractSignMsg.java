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
public enum ContractSignMsg {

    ttl001("��ͬ������ɹ������ã�"),
    ttl002("��ͬǩ��"),
//    ttl003("����ǩ����ͬ"),
    msg001("��Ч�ɹ�"),
    msg002("��ѡ����Ա"),
    msg003("һ�������ѡ��800��"),
    msg004("δѡ���κ���Ա��ѡ����Ա���к�ͬ");
    

    
    private String value;

    private ContractSignMsg() {
    }

    private ContractSignMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(ContractSignMsg.class, this.name(), this.value);
    }
}
