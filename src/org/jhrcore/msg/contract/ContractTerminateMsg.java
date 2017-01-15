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
    
    ttl001("��ֹ��ͬ"),
    msg001("�ú�ͬ����ֹ"),
    msg002("�ú�ͬ�ѽ��"),
    msg003("�ú�ͬ��δ���ڣ�"),
    msg004("��������¼��������800");
    
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
