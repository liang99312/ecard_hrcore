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
public enum PaySalesStoreMsg {

    ttl001("���㷽��"),
    ttl002("�����ݷ���"),
    ttl003("����"),
    msg001("ûѡ�����"),
    msg002("ȷ��������Ա��"),
    msg003("ȷ��������Ա��"),
    msg004("�����ɹ�"),
    msg005("��������Ϊ��!"),
    msg006("ȷ����Ա���飿"),
    msg007("�䶯�ɹ�"),
    msg008("����ɹ�"),
    msg009("ȷ��Ӧ��������Ա�ı䶯��"),
    msg010("Ӧ�óɹ�"),
    msg011("�ŵ����Ʋ���Ϊ�գ�"),
    msg012("�Ѵ���������Ƶ��ŵ꣡"),
    msg013("�ŵ����ʧ��"),
    msg014("�ŵ����ɹ�"),
    msg015("�����Ѵ���������Ƶ��ŵ꣡"),
    msg016("�Ҳ�����ʶΪ��PaySalesStore.sales_store���Ĺ������Ƽ�¼��"),
    msg017("�����ŵ�ʧ��"),
    msg018("�ŵ�ɾ���ɹ�"),
    msg019("��ѡ���ŵ�"),
    msg020("�����ѹ�����Ա��������ɾ��");

    
    private String value;

    private PaySalesStoreMsg() {
    }

    private PaySalesStoreMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PaySalesStoreMsg.class, this.name(), this.value);
    }
}
