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
public enum PositionGraphMsg {

//    msg001("您选择的不是有效的图片文件"),
//    msg002("图片已存在，是否覆盖？"),    
//    msg003("选择导出文件"),  
    msg004(" 当前岗位："),
    msg005( "人员查看"),
    msg006(" 当前记录数："),
    msg007("岗位关系");   

    private String value;

    private PositionGraphMsg() {
    }

    private PositionGraphMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PositionGraphMsg.class, this.name(), this.value);
    }
}
