/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.msg.contract;

import org.jhrcore.ui.language.WebHrMessage;

/**
 *
 * @author mxliteboss
 */
public enum ContractDataMsg {

    ttl001("��ͬ������ɹ������ã�"),
    ttl002("����ǩ����ͬ����Э��"),
    ttl003("��������"),
    ttl004("��ͬ������Ϣ"),
    ttl005("��ͬ�䶯��Ϣ"),
    ttl006("��ͬ������Ϣ"),
    msg001("��ǰ��¼�иĶ����Ƿ���Ҫ����"),
    msg002("�Ѵ��ں�ͬ�����Ƿ�Ҫ���ǣ�"),
    msg003("û�и�������"),
    msg004("FileManager���¼ɾ��ʧ��"),
    msg005("û�и�������"),
    msg006("����Ա��δǩ����ͬ");
    
    private String value;

    private ContractDataMsg() {
    }

    private ContractDataMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(ContractDataMsg.class, this.name(), this.value);
    }
}
