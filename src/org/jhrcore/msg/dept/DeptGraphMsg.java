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
public enum DeptGraphMsg {

    msg001("当前部门："),
    msg002(" 当前岗位："),
    msg003("人员查看"),
    msg004("人"),
//    msg005("您选择的不是有效的图片文件"),
//    msg006("图片已存在，是否覆盖？"),
//    msg007("选择导出文件"),
    msg008("当前记录数：");
    
    private String value;

    private DeptGraphMsg() {
    }

    private DeptGraphMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(DeptGraphMsg.class, this.name(), this.value);
    }
}
