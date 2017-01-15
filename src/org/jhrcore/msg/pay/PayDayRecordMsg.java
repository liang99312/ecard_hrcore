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
public enum PayDayRecordMsg {

    ttl001("调转人员"),
    ttl002("设置可填报天数"),
    ttl003("新增个人班报信息"),
    ttl004("公式编辑"),
    ttl005("条件设置"),
    ttl006("正在导入数据"),
    ttl007("产生校验报告"),
    ttl008("数据预处理"),
    ttl009("读取数据"),
    ttl010("模板"),
//    ttl011("选择导入文件"),
    ttl012("报分奖惩"),
    msg001("请选择人员"),
    msg002("一次最多能选择800人"),
    msg003("可填报天数必须大于0"),
    msg004("已提交数据禁止再次提交"),
    msg005("提交月份数据不存在"),
    msg006("确定要提交这些记录吗"),
    msg007("确定要对这些记录取消提交吗"),
    msg008("已提交数据禁止修改"),
    msg009("请输入月份：格式（201201）\n"),
    msg010("月份输入错误!"),
    msg011("月份数据已经存在!"),
    msg012("新月份报分表生成成功！"),
    msg013("请选择导入文件"),
    msg014("您选择的不是有效的EXCEL文件"),
    msg015("导入文件中不包含日期(pre_date)列或人员编号(a01.a0190)"),
    msg016("数据库更新错误"),
    msg017(" 成功插入："),
    msg018(" 更新插入："),
    msg019(" 执行错误："),
    msg020("条；"),
    msg021("导入消息报告如下："),
    msg022(" 可更新记录："),
    msg023(" 重复的记录："),
    msg024(" 数据格式有误或无匹配的记录： "),
    msg025(" 成功更新记录："),
    msg026(" 重复的记录："),
    msg027(" 数据格式有误或无匹配的记录： "),
    msg028("请选择日期"),
    msg029("请选择调转的部门"),
    msg030("调转成功");
    private String value;

    private PayDayRecordMsg() {
    }

    private PayDayRecordMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayDayRecordMsg.class, this.name(), this.value);
    }
}
