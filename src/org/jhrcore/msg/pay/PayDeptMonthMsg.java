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
public enum PayDeptMonthMsg {

    ttl001("���ŵ���"),
    ttl002("���Ź�����ϵ����"),
    ttl003("û����Ҫ����Ĺ�ʽ"),
    ttl004("��ȡ����"),
    ttl005("����Ԥ����"),
    ttl006("���ڵ�������"),
    ttl007("����У�鱨��"),
    ttl008("���Ź��ʱ�"),
    ttl009(")����ģ��"),
    ttl010("���һ�������Ŷ���鿴"),
    ttl011("���Ź����±�����"),
    ttl012("��һ���������±���ز���"),
    ttl013("��ʽ�༭"),
    ttl014("���Ŷ�������"),
    msg001("���Ź�����Ϣ�иĶ����Ƿ���Ҫ����"),
    msg002("�ɹ�"),
    msg003("ȡ������"),
    msg004("����"),
    msg005("ȡ��"),
    msg006("���ʼ���ɹ�"),
    msg007("δѡ���κ��ֶ�"),
    msg008("�����뷽������"),
    msg009("����������Ϊ��!"),
    msg010("ȷ��Ҫɾ��������"),
    msg011("���ݿⲻ���������¼"),
    msg012("�������뵱ǰ�·�����"),
    msg013("δѡ���κι��ʼ�¼"),
    msg014("��ǰѡ���ʼ�¼�ѽ���/���ţ�����������"),
    msg015("�ɹ���������"),
    msg016("�������ݺ͵�ǰѡ���ʱ�ƥ��!"),
    msg017("�����ļ��в�����ƥ����"),
    msg018("������Ϣ�������£�"),
    msg019(" �ɹ������¼�� "),
    msg020(" ��ƥ�����ƥ�䲻���ļ�¼��"),
    msg021(" ���ݸ�ʽ����ļ�¼�� "),
    msg022("��;"),
    msg023("Ӧ�óɹ�"),
    msg024("�����±�ɹ�"),
    msg025("���Ƚ������Ź�����ϵ!"),
    msg026("����ϵͳδ�����±�!"),
    msg027("�����·�����"),
    msg028("�ο��·ݱ���Ϊ6λ���֣��磺200801"),
    msg029("�ο���������Ϊ����0������"),
    msg030("����ϵͳ�����ڸ��·�����"),
    msg031("����ϵͳδ���÷��Ŷ��󷽰�!"),
    msg032("�����·�����"),
    msg033("ȷ��Ҫɾ������ϵ��"),
    msg034("������ϵ"),
    msg035("�����˸���ϵ��������ɾ��"),
    msg036("����ϵ�����ã�"),
    msg037("δѡ���κ���ϵ�ڵ�");
    private String value;

    private PayDeptMonthMsg() {
    }

    private PayDeptMonthMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayDeptMonthMsg.class, this.name(), this.value);
    }
}
