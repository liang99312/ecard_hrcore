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
public enum EmpChangeMsg {

    ttl001("批量修改起薪日期"),
    msg001("未选择任何变动或选择的变动不允许撤销！"),
    msg002("确定要撤销当前选择的流程吗？"),
    msg003("流程撤销成功!"),
    msg004("错误"),
    msg005("找不到IC卡对应人员"),
    msg006("请选择具体变动类别"),
    msg007("该变动方案不需要审批"),
    msg008("单次审批最大允许500条"),
    msg009("该变动存在流程审批，无法还原"),
    msg010("确定要还原此次变动吗？"),
    msg011( "还原成功"),
    msg012("操作错误，错误原因可能为：\n1.非最后一次变动无法还原\n2.当前选择人员存在未完成的变动"),
    msg013("所有人员");
    
    private String value;

    private EmpChangeMsg() {
    }

    private EmpChangeMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(EmpChangeMsg.class, this.name(), this.value);
    }
}
