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
public enum PayDeptMonthMsg {

    ttl001("部门调整"),
    ttl002("部门工资体系设置"),
    ttl003("没有需要计算的公式"),
    ttl004("读取数据"),
    ttl005("数据预处理"),
    ttl006("正在导入数据"),
    ttl007("产生校验报告"),
    ttl008("部门工资表（"),
    ttl009(")导入模板"),
    ttl010("最后一步：发放对象查看"),
    ttl011("部门工资新表生成"),
    ttl012("第一步：设置新表相关参数"),
    ttl013("公式编辑"),
    ttl014("发放对象设置"),
    msg001("部门工资信息有改动，是否需要保存"),
    msg002("成功"),
    msg003("取消发放"),
    msg004("发放"),
    msg005("取消"),
    msg006("工资计算成功"),
    msg007("未选择任何字段"),
    msg008("请输入方案名："),
    msg009("方案名不可为空!"),
    msg010("确认要删除方案吗？"),
    msg011("数据库不存在引入记录"),
    msg012("不能引入当前月份数据"),
    msg013("未选择任何工资记录"),
    msg014("当前选择工资记录已结算/发放，不允许引入"),
    msg015("成功引入数据"),
    msg016("导入数据和当前选择工资表不匹配!"),
    msg017("导入文件中不包含匹配列"),
    msg018("导入消息报告如下："),
    msg019(" 成功导入记录： "),
    msg020(" 无匹配项或匹配不到的记录："),
    msg021(" 数据格式有误的记录： "),
    msg022("条;"),
    msg023("应用成功"),
    msg024("生成新表成功"),
    msg025("请先建立部门工资体系!"),
    msg026("工资系统未设置新表!"),
    msg027("输入月份有误"),
    msg028("参考月份必须为6位数字，如：200801"),
    msg029("参考次数必须为大于0的整数"),
    msg030("工资系统不存在该月份数据"),
    msg031("工资系统未设置发放对象方案!"),
    msg032("输入月份有误"),
    msg033("确定要删除该体系吗？"),
    msg034("部门体系"),
    msg035("引用了该体系，不允许删除"),
    msg036("无体系可引用！"),
    msg037("未选择任何体系节点");
    private String value;

    private PayDeptMonthMsg() {
    }

    private PayDeptMonthMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayDeptMonthMsg.class, this.name(), this.value);
    }
}
