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
public enum EmpParamMsg {

    ttl001("�ֶ��б�"),
    ttl002("ѡ���ֶ�"),
    msg001("����ɹ������µ�¼����󷽿���Ч!");

    
    private String value;

    private EmpParamMsg() {
    }

    private EmpParamMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(EmpParamMsg.class, this.name(), this.value);
    }
}
