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
public enum EmpDocumentationMsg {

    ttl001("�����ĵ�"),
    ttl002("�����ĵ�·�����ã�"),
    ttl003("�����ϲ���"),
    ttl004("���������ĵ�"),
    ttl005("��ǰ��Ա��"),
    ttl006("������"),
    ttl007("��ȡͼƬ"),
    ttl008("����·��"),
    ttl009("����ѡ��"),
    ttl010("�ĵ����"),
    msg001("�˲�������ɾ��ѡ����Ա�����������������Ϣ��ȷ��Ҫ������"),
    msg002("ȷ��Ҫɾ��ѡ�����־��"),
    msg003("����ɾ���ɹ�"),
//    msg004("�������ʧ��"),
    msg005("�ָ��ɹ�"),
    msg006("�ָ�ʧ��"),
    msg007("һ�����ϲ�800����¼"),
    msg008("ȷ��Ҫ�ϲ���Щ������"),
    msg009("�ϲ��ɹ�"),
    msg010("��ѡ����Ա"),
    msg011("��ѡ���ĵ�����"),
    msg012("�Ƿ�ɼ�ͼ��"),
    msg013("�ļ�������Ϊ��"),
    msg014("�ļ����Ѵ���"),
    msg015("ͼ��ɼ��ɹ���"),
    msg016("ȷ��Ҫɾ����Щ�ĵ�����¼��"),
    msg017("�÷������ӷ��࣬������ɾ��"),
    msg018("�÷�����������ĵ���������ɾ��"),
    msg019("�������Ʋ���Ϊ�գ�"),
    msg020("�Ѵ���������Ƶ��ĵ����࣡"),
    msg021("�Ѵ������������ĵ����࣡"),
    msg022("�Ҳ����ļ�"),
    msg023("�Ҳ����豸"),
    msg024("���������ļ����Ƿ񸲸�"),
    msg025("�ļ����·������"),
    msg026("�����ϴ���ѡ���ļ���"),
    msg027("�ϴ��ɹ���"),
    msg028("�ϴ�ʧ�ܣ�"),
    msg029("�ļ���Ӧ��Ա����ѡ���ŵģ�"),
    msg030("�ļ��������Ϲ���ģ�"),
    msg031("��������ϸ��־��"),
    msg032("��"),
    msg033("��Ա��ţ�"),
    msg034(" �ļ�:"),
    msg035("����ɹ���"),
    msg036("�ļ�·�����󣬵�����ֹ��"),
    msg037("·������Ϊ��"),
    msg038("·����Ч"),
    msg039("��·�������ļ���������ɾ��"),
    msg040("ȷ��ɾ��ѡ�е�·����"),
    msg041("���óɹ�"),
    msg042("����ʧ��"),
    msg043("û����صļ�¼"),
    msg044("�ɹ�����"),
    msg045("�������ļ���"),
    msg046("��;"),
    msg047("������Ա");
    private String value;

    private EmpDocumentationMsg() {
    }

    private EmpDocumentationMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(EmpDocumentationMsg.class, this.name(), this.value);
    }
}
