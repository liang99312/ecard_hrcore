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
public enum ContractResignMsg {

    ttl001("������ǩ��ͬ"),
    ttl002("��ͬ��ǩ"),
    ttl003("��ͬǩ��"),
    ttl004("��ʷ��ͬ��¼"),
    msg001("��ѡ����Ա"),
    msg002("һ�������ѡ��800��");

    private String value;

    private ContractResignMsg() {
    }

    private ContractResignMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(ContractResignMsg.class, this.name(), this.value);
    }
}
