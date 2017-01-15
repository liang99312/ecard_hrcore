/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.msg.pay;

import org.jhrcore.ui.language.WebHrMessage;

/**
 *
 * @author mxliteboss
 */
public enum PayPersonChangeMsg {

    ttl001("应用成功"),
    ttl002("删除工资"),
    ttl003("应用时间设置"),
    ttl004(" 查找:"),
    ttl005("人员增减变动"),
    ttl006("其他项目变动"),
    ttl007("发放对象查询"),
    ttl008("一个人在多体系"),
    ttl009("工资变动日志"),
//    ttl010("应用失败"),
    ttl011("变动详细信息："),
    msg001("确定要应用当前工资差异吗"),
    msg002("确定要删除选择的工资记录吗"),
    msg003("此操作仅用于调平工资名单，确定要执行吗"),
    msg004("请选择体系及工资年月"),
    msg005("此操作将删除当前选择体系月份工资，并不可恢复，确定要执行吗"),
    msg006("当前体系没有未发放工资"),
    msg007("请到工具-应用时间设置"),
    msg008("没有可以应用的记录，其中未到起薪日期的有"),
    msg009("条。"),
    msg010("应用成功!"),
    msg011("，其中由于未到起薪日期而未能应用的有"),
    msg012("还原失败："),
//    msg013("服务调用失败："),
    msg014("已发放的工资数据不能还原!"),
    msg015("确定要根据当前选中的日志来还原工资变动吗"),
    msg016("还原成功"),
    msg017("字段类型不一致！"),
    msg018("部门、人员类别、工资停发标志为系统设置项，不允许删除!"),
    msg019("确定要将这些人加入到本月工资名单吗"),
    msg020("确定要将这些人从本月工资名单中移除吗"),
    msg021("应用成功"),
    msg022("当前选择的记录均已发放，不允许删除"),
    msg023("应用成功，其中因工资已发而未应用的");
    private String value;

    private PayPersonChangeMsg() {
    }

    private PayPersonChangeMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayPersonChangeMsg.class, this.name(), this.value);
    }
}
