/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.msg.pay;

import org.jhrcore.ui.language.WebHrMessage;

/**
 *
 * @author mxliteboss
 */
public enum PayCalApprovalMsg {

    ttl001("�鿴������ϸ"),
    msg001("�����ѷ��ţ��������������أ�");
    private String value;

    private PayCalApprovalMsg() {
    }

    private PayCalApprovalMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayCalApprovalMsg.class, this.name(), this.value);
    }
}
