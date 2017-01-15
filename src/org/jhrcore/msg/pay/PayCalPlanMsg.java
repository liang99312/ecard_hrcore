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
public enum PayCalPlanMsg {

    ttl001("统计对象设置"),
    ttl002("查看工资明细"),
    ttl003("工资审核分类项目"),
    ttl004("生成薪酬计划"),
    ttl005("请选择字段"),
    ttl006("新增分类项目名称"),
    ttl007("生成薪酬审批计划向导"),
    ttl008("新增计划名称"),
    msg001("计划下没有对应的方案"),
    msg002("所选计划不允许操作"),
    msg003("计划已下达，不允许删除"),
    msg004("确定要删除当前选择的计划？"),
    msg005("确定要删除当前选择的计划项？"),
    msg006("下达成功"),
    msg007("取消成功"),
    msg008("请选择薪酬计划!"),
    msg009("最后一步：引入其他体系计划"),
    msg010("没有引入计划"),
    msg011("没有选择引入年月"),
    msg012("没有引入次数"),
    msg013("最后一步：选择引入月份和次数"),
    msg014("项目名称已经存在"),
    msg015("项目名称不能为空"),
    msg016("请选择薪酬体系"),
    msg017("年月必须为6位数字"),
    msg018("次数必须为1到12之间的数字"),
    msg019("还没生成计划，请选择‘创建新计划’方式"),
    msg020("第一步：选择薪酬体系"),
    msg021("脚本变化，是否校验保存"),
    msg022("该项目已关联计划，不能删除"),
    msg023("名称不可为空"),
    msg024("错误提示："),
    msg025("薪酬体系计划已存在！"),
    msg026("最后一步：计划选择"),
    msg027("没有新建计划"),
    msg028("没有对应方案");

    private String value;

    private PayCalPlanMsg() {
    }

    private PayCalPlanMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayCalPlanMsg.class, this.name(), this.value);
    }
}
