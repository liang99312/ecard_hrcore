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
public enum PayDayRecordMsg {

    ttl001("��ת��Ա"),
    ttl002("���ÿ������"),
    ttl003("�������˰౨��Ϣ"),
    ttl004("��ʽ�༭"),
    ttl005("��������"),
    ttl006("���ڵ�������"),
    ttl007("����У�鱨��"),
    ttl008("����Ԥ����"),
    ttl009("��ȡ����"),
    ttl010("ģ��"),
//    ttl011("ѡ�����ļ�"),
    ttl012("���ֽ���"),
    msg001("��ѡ����Ա"),
    msg002("һ�������ѡ��800��"),
    msg003("��������������0"),
    msg004("���ύ���ݽ�ֹ�ٴ��ύ"),
    msg005("�ύ�·����ݲ�����"),
    msg006("ȷ��Ҫ�ύ��Щ��¼��"),
    msg007("ȷ��Ҫ����Щ��¼ȡ���ύ��"),
    msg008("���ύ���ݽ�ֹ�޸�"),
    msg009("�������·ݣ���ʽ��201201��\n"),
    msg010("�·��������!"),
    msg011("�·������Ѿ�����!"),
    msg012("���·ݱ��ֱ����ɳɹ���"),
    msg013("��ѡ�����ļ�"),
    msg014("��ѡ��Ĳ�����Ч��EXCEL�ļ�"),
    msg015("�����ļ��в���������(pre_date)�л���Ա���(a01.a0190)"),
    msg016("���ݿ���´���"),
    msg017(" �ɹ����룺"),
    msg018(" ���²��룺"),
    msg019(" ִ�д���"),
    msg020("����"),
    msg021("������Ϣ�������£�"),
    msg022(" �ɸ��¼�¼��"),
    msg023(" �ظ��ļ�¼��"),
    msg024(" ���ݸ�ʽ�������ƥ��ļ�¼�� "),
    msg025(" �ɹ����¼�¼��"),
    msg026(" �ظ��ļ�¼��"),
    msg027(" ���ݸ�ʽ�������ƥ��ļ�¼�� "),
    msg028("��ѡ������"),
    msg029("��ѡ���ת�Ĳ���"),
    msg030("��ת�ɹ�");
    private String value;

    private PayDayRecordMsg() {
    }

    private PayDayRecordMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayDayRecordMsg.class, this.name(), this.value);
    }
}
