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
public enum PaySalesDetailMsg {

    ttl001("��ѡ��Ա"),
    ttl002("״̬���¼�¼"),
    ttl003("��ѡ��"),
    ttl004("���ٲ���"),
    ttl005("��Ʒѡ��"),
    ttl006("�����������"),
    ttl007("�޸���ϸ��Ϣ"),
    ttl008("���������ѯ�ֶ�����"),
    ttl009("���������ѯ�ֶ�����"),
    ttl010("���۵�¼��"),
    ttl011("��������������Ա"),
    msg001("�Ҳ��������Ա"),
    msg002("ȷ���Ƴ�ѡ����Ա��"),
//    msg003("�Ҳ�������"),
    msg004("״̬���ѱ���"),
    msg005("״̬���¼�¼"),
    msg006("û��ѡ���ŵ�"),
    msg007("�뵽������ɽ���Ĺ����п��������ѯ�ֶ�����"),
    msg008("��������Ա��ѯ��Ϣ"),
    msg009("��ѡ����Ʒ"),
    msg010("��������Ч����������"),
    msg011("��������ȷ������"),
    msg012("��ǰû��ѡ���¼"),
    msg013("���ʼ���ɹ�");
    private String value;

    private PaySalesDetailMsg() {
    }

    private PaySalesDetailMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PaySalesStockMsg.class, this.name(), this.value);
    }
}
