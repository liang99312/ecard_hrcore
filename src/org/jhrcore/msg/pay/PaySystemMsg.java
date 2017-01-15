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
public enum PaySystemMsg {

    ttl001("隶属关系"),
    ttl002("发放对象设置"),
    ttl003("公式编辑"),
    ttl004("新表设置"),
    ttl005("数据源设置"),
    ttl006("项目"),
    ttl007("项目选择1"),
    ttl008("项目选择2"),
    ttl009("生成工资新表"),
    ttl010("薪酬体系树"),
    msg001("未设置部门体系"),
    msg002("该体系的隶属上级体系已设置审批，不能再设置该体系需要审批"),
    msg003("体系名称不能为空！"),
    msg004("该组已存在这个名称的体系！"),
    msg005("体系另存失败"),
    msg006("体系另存成功"),
    msg007("请设置部门体系"),
    msg008("已存在这个名称的体系！"),
    msg009("新增体系失败"),
    msg010("此操作将删除该体系下的所有工资数据，确定要删除吗"),
    msg011("体系删除成功"),
    msg012("请选择引入字段"),
    msg013("确定要取消引入吗？"),
    msg014("错误提示："),
    msg015("确定要取消引入吗"),
    msg016("该表存在公式字段，无法移除"),
    msg017("工资子表存在工资数据，确定删除？"),
    msg018("条件不可为空!"),
    msg019("该体系尚未生成新表，是否现在生成？"),
    msg020("确定保存该设置？");
    private String value;

    private PaySystemMsg() {
    }

    private PaySystemMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PaySystemMsg.class, this.name(), this.value);
    }
}
