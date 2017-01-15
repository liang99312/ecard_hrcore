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
public enum EmpParamMsg {

    ttl001("字段列表"),
    ttl002("选择字段"),
    msg001("保存成功，重新登录软件后方可生效!");

    
    private String value;

    private EmpParamMsg() {
    }

    private EmpParamMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(EmpParamMsg.class, this.name(), this.value);
    }
}
