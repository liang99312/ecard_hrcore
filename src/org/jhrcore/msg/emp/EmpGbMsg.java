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
public enum EmpGbMsg {

    ttl001("干部类别"),
    ttl002("人员岗位调整"),
    ttl003("个人文档管理"),
    ttl004("同时录入多张附表"),
    ttl005("批量新增人事附表"),
    ttl006("导入干部信息"),
    ttl007("同时查看多张附表"),
    ttl008("干部增删日志"),
    ttl009("设置附表审批"),
    ttl010("身份证号码重复，不允许读入身份证信息:"),
    ttl011("新增附表："),
//    ttl012("流程详细信息"),
    ttl013("流程图"),
    ttl014("已审核"),
    ttl015("未提交"),
    ttl016("待处理"),
    ttl017("员工序号调整"),
    ttl018("请输入类别名称"),
    msg001("所有类别"),
    msg002("请到系统设置里面设置二代证与干部信息对应"),
    msg003("没有找到读卡器"),
    msg004("读卡失败"),
    msg005("身份证跟干部不符，是否需要替换"),
    msg006("请选择干部"),
    msg007("请切换到干部基本信息界面"),
    msg008("请切换到附表界面选择附表"),
    msg009("有改动，是否需要保存"),
    msg010("保存成功并重启HRSERVER服务器！"),
    msg011("请切换到干部附表界面"),
    msg012("请选择未提交的记录"),
    msg013("请先建立该附表下的工作流"),
    msg014("您确定要提交所选记录？"),
    msg015("请选择已提交的记录"),
    msg016("您确定要取消提交所选记录？"),
    msg017("您不具备当前记录的删除权限，无法删除"),
    msg018("确定要删除当前选择的记录吗"),
    msg019("身份证号码为空"),
    msg020("该身份证号码无效"),
    msg021("身份证号码错误!"),
    msg022("未提交"),
    msg023("全选"),
    msg024("当前干部信息"),
    msg025("当前附表信息"),
    msg026("全部"),
    msg027("请保存新建类别"),
    msg028("请选择父节点"),
    msg029("类别名称不可为空"),
    msg030("该类别名称已存在"),
    msg031("您确定要删除该类别以及所有子节点类别吗?"),
    msg032("删除类别失败"),
    msg033("未选择任何条件!");
    private String value;

    private EmpGbMsg() {
    }

    private EmpGbMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(EmpGbMsg.class, this.name(), this.value);
    }
}
