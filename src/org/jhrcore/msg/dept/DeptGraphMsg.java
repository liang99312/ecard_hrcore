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
public enum DeptGraphMsg {

    msg001("��ǰ���ţ�"),
    msg002(" ��ǰ��λ��"),
    msg003("��Ա�鿴"),
    msg004("��"),
//    msg005("��ѡ��Ĳ�����Ч��ͼƬ�ļ�"),
//    msg006("ͼƬ�Ѵ��ڣ��Ƿ񸲸ǣ�"),
//    msg007("ѡ�񵼳��ļ�"),
    msg008("��ǰ��¼����");
    
    private String value;

    private DeptGraphMsg() {
    }

    private DeptGraphMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(DeptGraphMsg.class, this.name(), this.value);
    }
}
