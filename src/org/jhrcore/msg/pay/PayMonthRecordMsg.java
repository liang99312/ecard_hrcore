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
public enum PayMonthRecordMsg {

    ttl001("���ɻ��ܱ�"),
    ttl002("��ʽ�༭"),
    msg001("��ѡ������"),
    msg002("��ʼ���ڲ��ܴ��ڽ�������"),
    msg003("��ѡ����"),
    msg004("��ʼʱ�䲻�ܴ��ڽ���ʱ��"),
    msg005("�޹�ʽ���������"),
    msg006("����ɹ�"),
    msg007("�ο��·ݱ���Ϊ6λ���֣��磺200801");
    
    private String value;

    private PayMonthRecordMsg() {
    }

    private PayMonthRecordMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayMonthRecordMsg.class, this.name(), this.value);
    }
}
