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
public enum DeptWeaveMsg {
    
    tt001("������λ����"),
    msg001("ֻ��ѡ��ĩ������"),  
    msg002("����"),  
    msg003("���и�λ"),   
    msg004("����������Ϊ��"),    
    msg005("�����뷽����"),   
    msg006("ȷ��Ҫɾ���÷���?"),   
    msg007("��ѡ����������!"),    
    msg008("�������Ϊ�ջ��������ֵ��Ч��"),    
    msg009("�����ֶ�Ϊ�գ�"),
    msg010("����ɹ���"),
    msg011("ȷʵҪɾ����Щ���ƣ�"),
    msg012("���ڡ���λ���ơ����������У����ø�λ�����ֶΣ�"),
    msg013("����"),
    msg014("��λ�����ܺͳ����˲��ű���"),
    msg015("��ֹ¼��"),
    msg016("��λ"),
    msg017("�����");
    
    private String value;

    private DeptWeaveMsg() {
    }

    private DeptWeaveMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(DeptWeaveMsg.class, this.name(), this.value);
    }
}
