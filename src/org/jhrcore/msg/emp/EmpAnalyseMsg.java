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
public enum EmpAnalyseMsg {

    ttl002("�鿴��Ա������Ϣ"),
    ttl001("��ǰ��¼����"),
    ttl003("�ڶ���������ͳ�Ʒ�����ز���"),
    ttl004("ͳ�Ʒ�����"),
    ttl005("ͳ������: "),
    ttl006("�ֶ�����"),
    ttl007("����������"),
    ttl008("�༭������"),
    ttl009("������������"),
    msg001("��ѡ��ͳ�����"),
    msg002("���淽��ʧ��"),
    msg003("��ȷ��Ҫɾ��������"),
    msg004("������������༭"),
    msg005("�����޶Ա��ִ������Ա�"),
    msg006("��������");
    private String value;

    private EmpAnalyseMsg() {
    }

    private EmpAnalyseMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(EmpAnalyseMsg.class, this.name(), this.value);
    }
}
