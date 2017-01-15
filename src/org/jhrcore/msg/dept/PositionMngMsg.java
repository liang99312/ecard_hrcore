/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.msg.dept;

import org.jhrcore.ui.language.WebHrMessage;

/**
 *
 * @author mxliteboss
 */
public enum PositionMngMsg {

    msg001("名称不可为空"),   
    msg002("请先删除子节点的职务!"),   
    msg003("该职务已被岗位使用，无法删除！");

    private String value;

    private PositionMngMsg() {
    }

    private PositionMngMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PositionMngMsg.class, this.name(), this.value);
    }
}
