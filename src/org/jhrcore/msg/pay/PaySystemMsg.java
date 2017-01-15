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
public enum PaySystemMsg {

    ttl001("������ϵ"),
    ttl002("���Ŷ�������"),
    ttl003("��ʽ�༭"),
    ttl004("�±�����"),
    ttl005("����Դ����"),
    ttl006("��Ŀ"),
    ttl007("��Ŀѡ��1"),
    ttl008("��Ŀѡ��2"),
    ttl009("���ɹ����±�"),
    ttl010("н����ϵ��"),
    msg001("δ���ò�����ϵ"),
    msg002("����ϵ�������ϼ���ϵ���������������������ø���ϵ��Ҫ����"),
    msg003("��ϵ���Ʋ���Ϊ�գ�"),
    msg004("�����Ѵ���������Ƶ���ϵ��"),
    msg005("��ϵ���ʧ��"),
    msg006("��ϵ���ɹ�"),
    msg007("�����ò�����ϵ"),
    msg008("�Ѵ���������Ƶ���ϵ��"),
    msg009("������ϵʧ��"),
    msg010("�˲�����ɾ������ϵ�µ����й������ݣ�ȷ��Ҫɾ����"),
    msg011("��ϵɾ���ɹ�"),
    msg012("��ѡ�������ֶ�"),
    msg013("ȷ��Ҫȡ��������"),
    msg014("������ʾ��"),
    msg015("ȷ��Ҫȡ��������"),
    msg016("�ñ���ڹ�ʽ�ֶΣ��޷��Ƴ�"),
    msg017("�����ӱ���ڹ������ݣ�ȷ��ɾ����"),
    msg018("��������Ϊ��!"),
    msg019("����ϵ��δ�����±��Ƿ��������ɣ�"),
    msg020("ȷ����������ã�");
    private String value;

    private PaySystemMsg() {
    }

    private PaySystemMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PaySystemMsg.class, this.name(), this.value);
    }
}
