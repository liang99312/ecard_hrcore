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
public enum PayStandMsg {

    ttl001("����Ա����"),
    ttl002("������ʷ����Ա����"),
    ttl003("����н���׼��ʽ��"),
    ttl004("н��ҵ��֪ͨ����"),
    ttl005("н��"),
    ttl006("����XLS:"),
    ttl007("����ƥ��ָ�꣺"),
    ttl008("��ע��"),
    ttl009("      1.������ѡ��1-2���ֶ���Ϊƥ��ָ��"),
    ttl010("      2.������ͨ����/����������ƥ��˳��"),
    msg001("�ò�������"),
    msg002("���뵱ǰѡ����Ա����׼��"),
    msg003("ɾ����ǰѡ����Ա�ı�׼��¼"),
    msg004("�Թ�������Ϊ׼���µ�ǰ����"),
    msg005(",ȷ��Ҫ������?"),
    msg006("Ӧ�óɹ�"),
//    msg007("��ѡ��Ĳ�����Ч��EXCEL�ļ�"),
    msg008( "δѡ���κ�ƥ��ָ��"),
    msg009("ƥ��ָ�겻�ܶ���2��"),
    msg010("��ʼ��ȡXLS�ļ���"),
    msg011("�����ļ���ʽ����,�������ע���ֶ���ע��"),
    msg012("XLS��ȡ��ϣ�Ԥ�������ݣ�"),
    msg013("������Ϣ�������£�"),
    msg014(" �ɹ������¼�� "),
    msg015(" ��ƥ�����ƥ�䲻���ļ�¼��"),
    msg016(" ���ݸ�ʽ����ļ�¼�� "),
    msg017(" �ظ���¼�� "),
    msg018( "��;"),
    msg019("Ԥ����������ϣ�׼�����룺"),
    msg020("����׼����ϣ���ʼ���룺"),
    msg021("Ԥ�����������"),
    msg022("δѡ���κι�ʽ"),
    msg023("����ɹ�");

    private String value;

    private PayStandMsg() {
    }

    private PayStandMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayStandMsg.class, this.name(), this.value);
    }
}
