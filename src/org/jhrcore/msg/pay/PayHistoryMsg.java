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
public enum PayHistoryMsg {

    msg000("");
    private String value;

    private PayHistoryMsg() {
    }

    private PayHistoryMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayHistoryMsg.class, this.name(), this.value);
    }
}
