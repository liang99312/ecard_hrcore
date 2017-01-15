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
public enum PayStandMsg {

    ttl001("加入员工："),
    ttl002("加入历史工资员工："),
    ttl003("设置薪酬标准公式："),
    ttl004("薪资业务通知处理"),
    ttl005("薪资"),
    ttl006("导入XLS:"),
    ttl007("设置匹配指标："),
    ttl008("备注："),
    ttl009("      1.您可以选择1-2个字段作为匹配指标"),
    ttl010("      2.您可以通过上/下移来设置匹配顺序"),
    msg001("该操作将会"),
    msg002("加入当前选择人员到标准表"),
    msg003("删除当前选择人员的标准记录"),
    msg004("以工资数据为准更新当前差异"),
    msg005(",确定要继续吗?"),
    msg006("应用成功"),
//    msg007("您选择的不是有效的EXCEL文件"),
    msg008( "未选择任何匹配指标"),
    msg009("匹配指标不能多于2个"),
    msg010("开始读取XLS文件："),
    msg011("导入文件格式有误,请检查表批注、字段批注等"),
    msg012("XLS读取完毕，预处理数据："),
    msg013("导入消息报告如下："),
    msg014(" 成功导入记录： "),
    msg015(" 无匹配项或匹配不到的记录："),
    msg016(" 数据格式有误的记录： "),
    msg017(" 重复记录： "),
    msg018( "条;"),
    msg019("预处理数据完毕，准备导入："),
    msg020("导入准备完毕，开始导入："),
    msg021("预处理数据完毕"),
    msg022("未选择任何公式"),
    msg023("计算成功");

    private String value;

    private PayStandMsg() {
    }

    private PayStandMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayStandMsg.class, this.name(), this.value);
    }
}
