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

    ttl001("��ѡ��"),
    ttl002("��ͬ������� "),
    msg001("�����ͬ"),
    msg002("ȡ�������ͬ"),
    msg003("�ú�ͬ�ѵ��ڣ��������ֹ����"),
    msg004("�ú�ͬ�ѽ��"),
    msg005("�ú�ͬδ�����"),
    msg006("��������¼��������800");
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
