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
public enum PayCalBudgetA01Msg {

    ttl001("����Ԥ�����뷽������"),
    ttl002("��Ŀ������Դ����"),
    ttl003( "��Ա����Ԥ����Ŀ����"),
    ttl004("��ʽ����"),
    ttl005("���ɸ��˹���Ԥ��"),
    ttl006("��ѡ���ֶ�"),
    ttl007("��ʾ"),
    ttl008("ѯ��"),
    ttl009("����Ԥ�㹫ʽ�༭"),
    ttl010("������Ŀ����"),
    ttl011("������Ŀ"),
    ttl012("��������"),
    msg001("����ɹ�"),
//    msg002("����ʧ��"),
    msg003("�޼����������Ŀ"),
    msg004("�ű��仯���Ƿ�У�鱣��"),
    msg005("������ʾ��"),
    msg006("ȷ��Ҫȡ��������"),
    msg007("PayBudgetA01.a01_key = ��Ա������Ϣ��.a01_key and ���Ż�����Ϣ��.deptCode_key=��Ա������Ϣ��.deptCode_key"),
    msg008("PayBudgetA01.a01_key = ��Ա������Ϣ��.a01_key"),
    msg009("�����뷽������"),
    msg010("����������Ϊ��"),
    msg011("�ο��·ݱ���Ϊ6λ���֣��磺200801"),
    msg012("����Ԥ�����뷽��δ����"),
    msg013("���·ݹ���Ԥ���Ѵ���"),
    msg014("û��������Դ�·�");
    
    private String value;

    private PayCalBudgetA01Msg() {
    }

    private PayCalBudgetA01Msg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayCalBudgetA01Msg.class, this.name(), this.value);
    }
}
