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
public enum EmpChangeSchemeMsg {

    ttl001("�䶯�ֶα༭������"),
    ttl002("�䶯����"),
    ttl003("�䶯��Ŀ"),
    ttl004("���ñ���Ϣ"),
    ttl005("�ֶ��б�"),
    msg001("δѡ���κη���"),
    msg002("ȷ��Ҫɾ��ѡ��ı䶯ģ����"),
    msg003("ʧ��"),
    msg004("�������䷽��"),
    msg005("�༭���䷽��"),
    msg006("��һ�������ñ䶯�ֶ�"),
    msg007("[�䶯����]����Ϊ��"),
    msg008("�䶯�Ѵ���"),
    msg009("δѡ���κα䶯�ֶ�"),
    msg010("���һ�����������ö��󣨿�ѡ��"),
    msg011("������������Դ"),
    msg012("�������Ͳ�һ�£����ܵ����������ȷ��Ҫ������"),
    msg014("���Ĳ������ø�����ҵ��"),
    msg015("�����������ø�����ҵ��"),
    msg016("δ���ñ䶯����Ա���"),
    msg017("��ѡ��䶯ǰ����Ա���"),
    msg018("�������"),
    msg019("�ڶ��������ñ䶯�����Ա���"),
    msg022("�ڶ��������ø�����ҵ��"),
    msg023("���岽�����ø������ҵ�񣨿�ѡ��"),
    msg024("���Ĳ������ø������ҵ�񣨿�ѡ��"),
    msg025("�䶯ģ��༭��"),
    msg026("ԭ"),
    msg027("��"),
    msg028("����"),
    msg029("��ǰ��Ա�Ѿ��ڵ�����");

    private String value;

    private EmpChangeSchemeMsg() {
    }

    private EmpChangeSchemeMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(EmpChangeSchemeMsg.class, this.name(), this.value);
    }
}
