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
public enum PaySalesItemMsg {

    ttl001("������Ʒ���"),
    ttl002("������Ʒ"),
    ttl003("�༭"),
    ttl004("���ٲ���"),
    ttl005("ѡ��㼶"),
    msg001("���Ʋ���Ϊ��"),
    msg002("�������Ѵ���"),
    msg003("������������Ʒ����ֹɾ��"),
    msg004("��Ʒ���Ʋ���Ϊ��"),
    msg005("��Ʒ�����Ѵ���"),
    msg006("��Ʒ�����Ѵ���"),
    msg007("������Ʋ���Ϊ��"),
    msg008("��������Ѵ���");

    private String value;

    private PaySalesItemMsg() {
    }

    private PaySalesItemMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PaySalesItemMsg.class, this.name(), this.value);
    }
}
