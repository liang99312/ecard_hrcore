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
public enum PositionDeptMsg {

    msg001("��λ���Ʋ���Ϊ��"),
    msg002("�ø�λ�����Ѿ�����"),
    msg003("��ѡ���Žڵ㣡"),
    msg004("��ѡ��ĩ�����ţ�"),
    msg005("����ְλ"),    
    msg006("����ɹ���"),    
    msg007("���л�����λҳ"),    
    msg008("δѡ���λ��"),    
    msg009("���Ƴɹ�"),
    msg011("�Ƿ�ͬʱ���Ƹ�λ������Ϣ?"),
    msg012("���л�����λ����ҳ"),    
    msg013("�޷�ճ������ճ��ͬһ����"),    
    msg014("ճ���ɹ�"),
    msg015("δѡ���κζ���!"),
    msg016("ȷ��Ҫɾ����"),
    msg018("��������");
  
    private String value;

    private PositionDeptMsg() {
    }

    private PositionDeptMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PositionDeptMsg.class, this.name(), this.value);
    }
}
