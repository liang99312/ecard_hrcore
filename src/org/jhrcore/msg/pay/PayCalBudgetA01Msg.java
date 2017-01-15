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
public enum PayCalBudgetA01Msg {

    ttl001("工资预算引入方案设置"),
    ttl002("项目数据来源设置"),
    ttl003( "人员工资预算项目设置"),
    ttl004("公式计算"),
    ttl005("生成个人工资预算"),
    ttl006("请选择字段"),
    ttl007("提示"),
    ttl008("询问"),
    ttl009("工资预算公式编辑"),
    ttl010("引入项目设置"),
    ttl011("所有项目"),
    ttl012("新增方案"),
    msg001("计算成功"),
//    msg002("计算失败"),
    msg003("无计算或引入项目"),
    msg004("脚本变化，是否校验保存"),
    msg005("错误提示："),
    msg006("确定要取消引入吗"),
    msg007("PayBudgetA01.a01_key = 人员基本信息表.a01_key and 部门基本信息表.deptCode_key=人员基本信息表.deptCode_key"),
    msg008("PayBudgetA01.a01_key = 人员基本信息表.a01_key"),
    msg009("请输入方案名："),
    msg010("方案名不可为空"),
    msg011("参考月份必须为6位数字，如：200801"),
    msg012("工资预算引入方案未设置"),
    msg013("该月份工资预算已存在"),
    msg014("没有数据来源月份");
    
    private String value;

    private PayCalBudgetA01Msg() {
    }

    private PayCalBudgetA01Msg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayCalBudgetA01Msg.class, this.name(), this.value);
    }
}
