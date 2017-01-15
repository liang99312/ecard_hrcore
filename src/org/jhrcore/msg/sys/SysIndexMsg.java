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

    msgRefch("刷新"),
    msgModuleManger("模板管理"),
    msgModule("功能模块"),
    msgIndexLog("资源管理系统"),
    msgCancel("注销"),
    msgAbout("关于"),
    msgHelp("帮助"),
    msgIndex("首页"),
    msgSelfhelp("自助"),
    msgprompting("我的提醒"),
    msgNotice("提醒方案"),
    msgNum("人数"),
    ttlCompnotice("公司公告"),
    ttlTitle("标题"),
    ttlRelDate("发布日期"),
    ttlMail("我的邮件"),
    ttlTheme("主题"),
    ttlSendDate("发送时间"),
    ttlPending("待办事宜"),
    ttlEventType("事件类型"),
    ttlEventNum("事件数"),
    ttlMsg("我的短信"),
    ttlMsgContent("短信内容"),
    ttlMsgSender("发送人"),
    ttlMsgDate("发送时间"),
    ttlReport("常用报表"),
    ttlReportName("报表名称"),
    ttlReportDate("推送时间");
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
