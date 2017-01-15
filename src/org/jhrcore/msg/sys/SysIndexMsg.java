/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.msg.sys;

import org.jhrcore.ui.language.WebHrMessage;

/**
 *
 * @author mxliteboss
 */
public enum SysIndexMsg {

    msgRefch("ˢ��"),
    msgModuleManger("ģ�����"),
    msgModule("����ģ��"),
    msgIndexLog("��Դ����ϵͳ"),
    msgCancel("ע��"),
    msgAbout("����"),
    msgHelp("����"),
    msgIndex("��ҳ"),
    msgSelfhelp("����"),
    msgprompting("�ҵ�����"),
    msgNotice("���ѷ���"),
    msgNum("����"),
    ttlCompnotice("��˾����"),
    ttlTitle("����"),
    ttlRelDate("��������"),
    ttlMail("�ҵ��ʼ�"),
    ttlTheme("����"),
    ttlSendDate("����ʱ��"),
    ttlPending("��������"),
    ttlEventType("�¼�����"),
    ttlEventNum("�¼���"),
    ttlMsg("�ҵĶ���"),
    ttlMsgContent("��������"),
    ttlMsgSender("������"),
    ttlMsgDate("����ʱ��"),
    ttlReport("���ñ���"),
    ttlReportName("��������"),
    ttlReportDate("����ʱ��");
    private String value;

    private SysIndexMsg() {
    }

    private SysIndexMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(SysIndexMsg.class, this.name(), this.value);
    }
}
