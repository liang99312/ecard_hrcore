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
public enum PayCompareMsg {

    ttl001("�༭���ձ�����"),
    ttl002("�����������"),
    ttl003("н����ձ�ģ��"),
    ttl004("������ձ���Ϣ"),
//    msg001("��ѡ��Ĳ�����Ч��EXCEL�ļ�"),
    msg002("��ѡ����ձ�"),
    msg003("������Ϣ�������£�"),
    msg004(" �ɹ������¼�� "),
    msg005(" ���ݸ�ʽ����ļ�¼�� "),
    msg006("����"),
    msg007("������ӱ���");
    private String value;

    private PayCompareMsg() {
    }

    private PayCompareMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayCompareMsg.class, this.name(), this.value);
    }
}
