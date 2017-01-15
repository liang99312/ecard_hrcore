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
public enum EmpRegisterMsg {

    ttlUnCommit("未提交审核"),
    ttlCommit("已提交审核"),
    ttlUnStore("未入库"),
    ttlStore("已入库"),
    ttlProcess("待处理"),
    ttlProcessed("已处理"),
    ttlCompleted("已完成"),
    ttlRevoked("已撤销"),
    ttlProcessEvents("待处理业务"),
    ttlProcessedEvents("已处理业务"),
    ttl001("人员编号生成规则设置:"),
    ttl002("导入人员信息:"),
    ttl003("入职人员编辑"),
//    ttl004("保存失败，提示信息如下："),
    ttl005("该身份证号码已存在,请选择操作："),
    ttl006("查看已存在身份证号码人员"),
    ttl007("引入"),
//    ttl009("删除失败"),
    ttl010("入职登记设置"),
    msg001("请选择类型为已提交审核"),
    msg002("确定要取消当前选择人员的审核吗?"),
    msg003("只有未提交审核的人员才允许删除"),
    msg004("请选择未提交审核选项"),
    msg005("请选择未入库选项"),
    msg006("人员超编，禁止入职，明细如下："),
    msg007("是否继续进行入职?"),
    msg008("确定要将当前选择人员提交审核吗?"),
    msg009("当前人员已入职，请到【资料管理】模块修改人事信息"),
    msg010("流程中人员信息不允许修改"),
    msg011("请先到入职登记设置中设置适用人员类别"),
    msg012("请先到入职登记设置中设置入职参数"),
    msg013("请先到入职登记设置中设置录入字段方案"),
    msg014("确定要删除选择的规则吗？"),
    msg015("前缀存在语法错误，无法生成新编码，保存失败!"),
    msg016("当前规则："),
    msg017("初始值："),
    msg018(" 序号位数："),
    msg019(" 增长值："),
    msg020(" 编码方式："),
    msg021("新号码："),
    msg022("规则有改动，是否需要保存"),
    msg023("未设置部门"),
    msg024("未设置人员类别"),
    msg025("当前初始值不能小于数据库当前值，否则导致生成序号重复!"),
    msg026("未选择任何人员类别!"),
    msg027("第一步：入职登记参数设置"),
    msg028("最后一步：设置入职部门（可选）"),
    msg029("第二步：录入字段设置"),
    msg030("人员编号不能为空"),
    msg031("人员姓名不能为空"),
    msg032("非末级部门不能新增人员"),
    msg033("请选择部门"),
    msg034("图片格式必须为JPG、PNG、GIF、BMP中一种"),
    msg035("所选图片大小超过规定大小！"),
    msg036("身份证号码错误!"),
    msg037("您不拥有任何入职人员类别的权限，不允许进行入职登记！"),
    msg038("没有找到读卡器"),
    msg039("读卡失败"),
    msg040("该部门下没有岗位"),
    msg041("选择的部门不在允许入职部门范围内"),
    msg042("开始日期输入错误"),
    msg043("结束日期错误"),
    msg044(",日期必须为1-31以内的数字");
    private String value;

    private EmpRegisterMsg() {
    }

    private EmpRegisterMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(EmpRegisterMsg.class, this.name(), this.value);
    }
}
