/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.msg.emp;

import org.jhrcore.ui.language.WebHrMessage;

/**
 *
 * @author mxliteboss
 */
public enum EmpGbMsg {

    ttl001("�ɲ����"),
    ttl002("��Ա��λ����"),
    ttl003("�����ĵ�����"),
    ttl004("ͬʱ¼����Ÿ���"),
    ttl005("�����������¸���"),
    ttl006("����ɲ���Ϣ"),
    ttl007("ͬʱ�鿴���Ÿ���"),
    ttl008("�ɲ���ɾ��־"),
    ttl009("���ø�������"),
    ttl010("���֤�����ظ���������������֤��Ϣ:"),
    ttl011("��������"),
//    ttl012("������ϸ��Ϣ"),
    ttl013("����ͼ"),
    ttl014("�����"),
    ttl015("δ�ύ"),
    ttl016("������"),
    ttl017("Ա����ŵ���"),
    ttl018("�������������"),
    msg001("�������"),
    msg002("�뵽ϵͳ�����������ö���֤��ɲ���Ϣ��Ӧ"),
    msg003("û���ҵ�������"),
    msg004("����ʧ��"),
    msg005("���֤���ɲ��������Ƿ���Ҫ�滻"),
    msg006("��ѡ��ɲ�"),
    msg007("���л����ɲ�������Ϣ����"),
    msg008("���л����������ѡ�񸽱�"),
    msg009("�иĶ����Ƿ���Ҫ����"),
    msg010("����ɹ�������HRSERVER��������"),
    msg011("���л����ɲ��������"),
    msg012("��ѡ��δ�ύ�ļ�¼"),
    msg013("���Ƚ����ø����µĹ�����"),
    msg014("��ȷ��Ҫ�ύ��ѡ��¼��"),
    msg015("��ѡ�����ύ�ļ�¼"),
    msg016("��ȷ��Ҫȡ���ύ��ѡ��¼��"),
    msg017("�����߱���ǰ��¼��ɾ��Ȩ�ޣ��޷�ɾ��"),
    msg018("ȷ��Ҫɾ����ǰѡ��ļ�¼��"),
    msg019("���֤����Ϊ��"),
    msg020("�����֤������Ч"),
    msg021("���֤�������!"),
    msg022("δ�ύ"),
    msg023("ȫѡ"),
    msg024("��ǰ�ɲ���Ϣ"),
    msg025("��ǰ������Ϣ"),
    msg026("ȫ��"),
    msg027("�뱣���½����"),
    msg028("��ѡ�񸸽ڵ�"),
    msg029("������Ʋ���Ϊ��"),
    msg030("����������Ѵ���"),
    msg031("��ȷ��Ҫɾ��������Լ������ӽڵ������?"),
    msg032("ɾ�����ʧ��"),
    msg033("δѡ���κ�����!");
    private String value;

    private EmpGbMsg() {
    }

    private EmpGbMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(EmpGbMsg.class, this.name(), this.value);
    }
}
