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
public enum EmpChangeMsg {

    ttl001("�����޸���н����"),
    msg001("δѡ���κα䶯��ѡ��ı䶯����������"),
    msg002("ȷ��Ҫ������ǰѡ���������"),
    msg003("���̳����ɹ�!"),
    msg004("����"),
    msg005("�Ҳ���IC����Ӧ��Ա"),
    msg006("��ѡ�����䶯���"),
    msg007("�ñ䶯��������Ҫ����"),
    msg008("���������������500��"),
    msg009("�ñ䶯���������������޷���ԭ"),
    msg010("ȷ��Ҫ��ԭ�˴α䶯��"),
    msg011( "��ԭ�ɹ�"),
    msg012("�������󣬴���ԭ�����Ϊ��\n1.�����һ�α䶯�޷���ԭ\n2.��ǰѡ����Ա����δ��ɵı䶯"),
    msg013("������Ա");
    
    private String value;

    private EmpChangeMsg() {
    }

    private EmpChangeMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(EmpChangeMsg.class, this.name(), this.value);
    }
}
