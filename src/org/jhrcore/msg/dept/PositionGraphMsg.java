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
public enum PositionGraphMsg {

//    msg001("��ѡ��Ĳ�����Ч��ͼƬ�ļ�"),
//    msg002("ͼƬ�Ѵ��ڣ��Ƿ񸲸ǣ�"),    
//    msg003("ѡ�񵼳��ļ�"),  
    msg004(" ��ǰ��λ��"),
    msg005( "��Ա�鿴"),
    msg006(" ��ǰ��¼����"),
    msg007("��λ��ϵ");   

    private String value;

    private PositionGraphMsg() {
    }

    private PositionGraphMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PositionGraphMsg.class, this.name(), this.value);
    }
}
