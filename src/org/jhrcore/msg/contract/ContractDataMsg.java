/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.msg.contract;

import org.jhrcore.ui.language.WebHrMessage;

/**
 *
 * @author mxliteboss
 */
public enum ContractDataMsg {

    ttl001("合同编号生成规则设置："),
    ttl002("批量签订合同附属协议"),
    ttl003("新增附表："),
    ttl004("合同基本信息"),
    ttl005("合同变动信息"),
    ttl006("合同附表信息"),
    msg001("当前记录有改动，是否需要保存"),
    msg002("已存在合同附件是否要覆盖？"),
    msg003("没有附件存在"),
    msg004("FileManager表记录删除失败"),
    msg005("没有附件存在"),
    msg006("该人员尚未签订合同");
    
    private String value;

    private ContractDataMsg() {
    }

    private ContractDataMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(ContractDataMsg.class, this.name(), this.value);
    }
}
