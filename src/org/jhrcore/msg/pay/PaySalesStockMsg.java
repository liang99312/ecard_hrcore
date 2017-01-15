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
public enum PaySalesStockMsg {

    ttl001("ѡ����Ʒ"),
    ttl002("���ٲ���"),
    ttl003("��Ʒ����¼"),
    ttl004("ˢ���»�����Ϣ"),
    ttl005("���ٲ���"),
    msg001("��ѡ����Ʒ"),
    msg002("��ѡ���ŵ�"),
    msg003("��ѡ����Ҫ�������ϵ"),
    msg004("ȷ��Ҫ����ѡ����ϵ���ֹ�������"),
    msg005("����ɹ�");
    private String value;

    private PaySalesStockMsg() {
    }

    private PaySalesStockMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PaySalesStockMsg.class, this.name(), this.value);
    }
}
