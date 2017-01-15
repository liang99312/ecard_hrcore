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
public enum ContractResignMsg {

    ttl001("批量续签合同"),
    ttl002("合同续签"),
    ttl003("合同签订"),
    ttl004("历史合同记录"),
    msg001("请选择人员"),
    msg002("一次最多能选择800人");

    private String value;

    private ContractResignMsg() {
    }

    private ContractResignMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(ContractResignMsg.class, this.name(), this.value);
    }
}
