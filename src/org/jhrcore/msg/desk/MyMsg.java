/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.msg.desk;

import org.jhrcore.ui.language.WebHrMessage;

/**
 *
 * @author mxliteboss
 */
public enum MyMsg {

    START_MESSAGE("¿ªÊ¼");
    private String value;

    private MyMsg() {
    }

    private MyMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(MyMsg.class, this.name(), this.value);
    }
}
