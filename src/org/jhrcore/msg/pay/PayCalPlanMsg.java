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
public enum PayCalPlanMsg {

    ttl001("ͳ�ƶ�������"),
    ttl002("�鿴������ϸ"),
    ttl003("������˷�����Ŀ"),
    ttl004("����н��ƻ�"),
    ttl005("��ѡ���ֶ�"),
    ttl006("����������Ŀ����"),
    ttl007("����н�������ƻ���"),
    ttl008("�����ƻ�����"),
    msg001("�ƻ���û�ж�Ӧ�ķ���"),
    msg002("��ѡ�ƻ����������"),
    msg003("�ƻ����´������ɾ��"),
    msg004("ȷ��Ҫɾ����ǰѡ��ļƻ���"),
    msg005("ȷ��Ҫɾ����ǰѡ��ļƻ��"),
    msg006("�´�ɹ�"),
    msg007("ȡ���ɹ�"),
    msg008("��ѡ��н��ƻ�!"),
    msg009("���һ��������������ϵ�ƻ�"),
    msg010("û������ƻ�"),
    msg011("û��ѡ����������"),
    msg012("û���������"),
    msg013("���һ����ѡ�������·ݺʹ���"),
    msg014("��Ŀ�����Ѿ�����"),
    msg015("��Ŀ���Ʋ���Ϊ��"),
    msg016("��ѡ��н����ϵ"),
    msg017("���±���Ϊ6λ����"),
    msg018("��������Ϊ1��12֮�������"),
    msg019("��û���ɼƻ�����ѡ�񡮴����¼ƻ�����ʽ"),
    msg020("��һ����ѡ��н����ϵ"),
    msg021("�ű��仯���Ƿ�У�鱣��"),
    msg022("����Ŀ�ѹ����ƻ�������ɾ��"),
    msg023("���Ʋ���Ϊ��"),
    msg024("������ʾ��"),
    msg025("н����ϵ�ƻ��Ѵ��ڣ�"),
    msg026("���һ�����ƻ�ѡ��"),
    msg027("û���½��ƻ�"),
    msg028("û�ж�Ӧ����");

    private String value;

    private PayCalPlanMsg() {
    }

    private PayCalPlanMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayCalPlanMsg.class, this.name(), this.value);
    }
}
