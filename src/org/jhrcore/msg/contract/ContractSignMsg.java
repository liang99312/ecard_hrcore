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
public enum ContractSignMsg {

    ttl001("合同编号生成规则设置："),
    ttl002("合同签订"),
//    ttl003("批量签订合同"),
    msg001("生效成功"),
    msg002("请选择人员"),
    msg003("一次最多能选择800人"),
    msg004("未选择任何人员或选择人员已有合同");
    

    
    private String value;

    private ContractSignMsg() {
    }

    private ContractSignMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(ContractSignMsg.class, this.name(), this.value);
    }
}
