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
public enum DeptWeaveMsg {
    
    tt001("新增岗位编制"),
    msg001("只能选择末级编码"),  
    msg002("所有"),  
    msg003("所有岗位"),   
    msg004("方案名不可为空"),    
    msg005("请输入方案名"),   
    msg006("确定要删除该方案?"),   
    msg007("请选择引入周期!"),    
    msg008("引入编制为空或引入编制值无效！"),    
    msg009("引入字段为空！"),
    msg010("引入成功！"),
    msg011("确实要删除这些编制？"),
    msg012("请在“单位编制”参数设置中，设置岗位编制字段！"),
    msg013("部门"),
    msg014("岗位编制总和超过了部门编制"),
    msg015("禁止录入"),
    msg016("单位"),
    msg017("已审核");
    
    private String value;

    private DeptWeaveMsg() {
    }

    private DeptWeaveMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(DeptWeaveMsg.class, this.name(), this.value);
    }
}
