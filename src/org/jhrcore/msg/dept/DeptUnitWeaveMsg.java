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
public enum DeptUnitWeaveMsg {

    tt001("�����±���"),
    tt002("���ɱ�������"),
    tt003("���ø�λ�����ֶ�"),
    tt004("���ű���ͳ����������"),
    tt005("���Ʒ�������"),
    msg001("��"),
    msg002("��"),
    msg003("��"),  
    msg004("�±����������"), 
    msg005("��ѡ���������ڣ�"),  
    msg006("�����������ɱ�������"),   
    msg007("δѡ��ο�����"),    
    msg008("��ɾ���ӽڵ�"),    
    msg009("�ñ������������ɱ�������,������ɾ��!"),
    msg010("��������Ϊ��!"),
    msg011("��ѡ���λ�����ֶΣ�"),
    msg012("��λ�����ֶν���ѡ��һ����"),
    msg013("���óɹ�"),
    msg014("��λ"),
    msg016("��λ�����ֶ���"),
    msg017("���ڡ���λ���ơ����������У����ø�λ�����ֶΣ�"),
    msg018("��ȷ��Ҫ������"),
    msg019("����ɹ���"),
    msg020("ȷ��Ҫɾ����ǰ���ڵ����в��ű����Լ���λ������"),
    msg021("�������ñ��Ʒ���!"),
    msg022("��λ"),
    msg023("����ɹ�"),
    msg024("��λ���Ʒ���"),
    msg025("δ���"),
    msg026("ȷ��Ҫ���ͨ����ǰ���ڱ�����?"),
    msg027("ȷ��Ҫȡ����˵�ǰ���ڱ�����?"),
    msg028("���ű�������"),
    msg029("��λ");
    
    private String value;

    private DeptUnitWeaveMsg() {
    }

    private DeptUnitWeaveMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(DeptUnitWeaveMsg.class, this.name(), this.value);
    }
}
