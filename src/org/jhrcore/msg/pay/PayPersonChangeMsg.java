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
public enum PayPersonChangeMsg {

    ttl001("Ӧ�óɹ�"),
    ttl002("ɾ������"),
    ttl003("Ӧ��ʱ������"),
    ttl004(" ����:"),
    ttl005("��Ա�����䶯"),
    ttl006("������Ŀ�䶯"),
    ttl007("���Ŷ����ѯ"),
    ttl008("һ�����ڶ���ϵ"),
    ttl009("���ʱ䶯��־"),
//    ttl010("Ӧ��ʧ��"),
    ttl011("�䶯��ϸ��Ϣ��"),
    msg001("ȷ��ҪӦ�õ�ǰ���ʲ�����"),
    msg002("ȷ��Ҫɾ��ѡ��Ĺ��ʼ�¼��"),
    msg003("�˲��������ڵ�ƽ����������ȷ��Ҫִ����"),
    msg004("��ѡ����ϵ����������"),
    msg005("�˲�����ɾ����ǰѡ����ϵ�·ݹ��ʣ������ɻָ���ȷ��Ҫִ����"),
    msg006("��ǰ��ϵû��δ���Ź���"),
    msg007("�뵽����-Ӧ��ʱ������"),
    msg008("û�п���Ӧ�õļ�¼������δ����н���ڵ���"),
    msg009("����"),
    msg010("Ӧ�óɹ�!"),
    msg011("����������δ����н���ڶ�δ��Ӧ�õ���"),
    msg012("��ԭʧ�ܣ�"),
//    msg013("�������ʧ�ܣ�"),
    msg014("�ѷ��ŵĹ������ݲ��ܻ�ԭ!"),
    msg015("ȷ��Ҫ���ݵ�ǰѡ�е���־����ԭ���ʱ䶯��"),
    msg016("��ԭ�ɹ�"),
    msg017("�ֶ����Ͳ�һ�£�"),
    msg018("���š���Ա��𡢹���ͣ����־Ϊϵͳ�����������ɾ��!"),
    msg019("ȷ��Ҫ����Щ�˼��뵽���¹���������"),
    msg020("ȷ��Ҫ����Щ�˴ӱ��¹����������Ƴ���"),
    msg021("Ӧ�óɹ�"),
    msg022("��ǰѡ��ļ�¼���ѷ��ţ�������ɾ��"),
    msg023("Ӧ�óɹ������������ѷ���δӦ�õ�");
    private String value;

    private PayPersonChangeMsg() {
    }

    private PayPersonChangeMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayPersonChangeMsg.class, this.name(), this.value);
    }
}
