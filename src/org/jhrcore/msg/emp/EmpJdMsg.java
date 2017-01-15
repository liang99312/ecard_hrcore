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
public enum EmpJdMsg {

    ttl002("设置人员容器匹配字段:"),
    ttl003("请选择"),
    ttl004("人员超编，禁止调动，明细如下："),
    ttl005("流程处理"),
    ttl006("开始"),
    ttl007("结束"),
    ttl008("附表信息"),
    ttl009("变动项目"),
    ttl010("变动主表"),
    ttl011("挑选人员"),
    ttl012("替换："),
    ttl013("字段列表"),
    ttl014("导入人员信息"),
    ttl015("同时录入多张附表"),
    ttl016("批量新增人事附表"),
    ttl017("新增附表："),
    ttl018("设置显示附表："),
    ttl019("选择模板："),
    msg001("请选择变动人员"),
    msg002("当前选择人员无法执行当前调动"),
    msg003("已经在变动中"),
    msg004("这些人员"),
    msg005("读取Excel表出错:"),
    msg006("导入表中存在空列或者空表头"),
    msg007("导入文件中不包含姓名列"),
    msg008("导入文件中不包含合同状态列"),
    msg009("导入文件中不包含匹配列"),
    msg010("在不自动生成序号的情况下文件中必须包含字段a_id"),
    msg050("在自动生成序号的情况下文件中不能包含字段a_id"),
//    msg011("导入消息报告如下："),
//    msg012(" 成功导入记录： "),
//    msg013(" 成功更新记录： "),
//    msg014(" 无匹配项或匹配不到或者只导入时数据库已有的记录："),
//    msg015(" 数据格式有误的记录： "),
//    msg016(" 数据库更新错误的记录："),
//    msg017("条;"),
//    msg018("条。"),
    msg019("人员附表"),
    msg020("合同表"),
    msg021("合同附表"),
    msg022("导入模板"),
    msg023("您可能未复制人员"),
    msg024("每次复制不允许超过"),
    msg025("人员容器记录不允许超过"),
    msg026("未选择任何字段"),
    msg027("您选择的不是有效的EXCEL文件"),
    msg028("导入文件中不包含匹配列或匹配列不在用户权限范围内"),
    msg029("导入文件格式有误,请检查表批注、字段批注等"),
    msg030(" 可查询到的记录："),
    msg031(" 导入表中的记录："),
    msg032(" 错误和未找到记录："),
    msg033("当前人员已经在调动中"),
    msg034("部门或人员类别为空"),
    msg035("是否继续进行调动？"),
    msg036("隐藏卡片"),
    msg037("显示卡片"),
    msg038("审批通过"),
    msg039("保存方案失败"),
    msg040("替换成功"),
    msg041("所有字段"),
    msg042("请先设置调入/出模板"),
    msg043("请在工具栏中选择显示附表并选择该附表"),
    msg044("请选择附表页面"),
    msg045("只有调入人员才可进行此操作"),
    msg046("只有正常人员才可进行此操作"),
    msg047("未提交"),
    msg048("未选择任何模板"),
    msg049("黑名单人员不允许做人员类别变动");
    private String value;

    private EmpJdMsg() {
    }

    private EmpJdMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(EmpJdMsg.class, this.name(), this.value);
    }
}
