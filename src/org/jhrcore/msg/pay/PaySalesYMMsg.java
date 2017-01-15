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
public enum PaySalesYMMsg {

    ttl001("计算月记录"),
    ttl002("计算销售提成");


    
    private String value;

    private PaySalesYMMsg() {
    }

    private PaySalesYMMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PaySalesStoreMsg.class, this.name(), this.value);
    }
}
