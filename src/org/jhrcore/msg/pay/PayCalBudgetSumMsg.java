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
public enum PayCalBudgetSumMsg {

    ttl001("��Ա����Ԥ����Ŀ����"),
    ttl002("�������·�Ԥ�����"),
    msg001("���·ݹ���Ԥ������Ѵ��ڣ��Ƿ��������ɣ�"),
    msg002("û�м�����Ŀ");
    
    private String value;

    private PayCalBudgetSumMsg() {
    }

    private PayCalBudgetSumMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayCalBudgetSumMsg.class, this.name(), this.value);
    }
}
