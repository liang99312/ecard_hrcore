/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.msg.dept;

import org.jhrcore.ui.language.WebHrMessage;

/**
 *
 * @author mxliteboss
 */
public enum PositionMngMsg {

    msg001("���Ʋ���Ϊ��"),   
    msg002("����ɾ���ӽڵ��ְ��!"),   
    msg003("��ְ���ѱ���λʹ�ã��޷�ɾ����");

    private String value;

    private PositionMngMsg() {
    }

    private PositionMngMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PositionMngMsg.class, this.name(), this.value);
    }
}
