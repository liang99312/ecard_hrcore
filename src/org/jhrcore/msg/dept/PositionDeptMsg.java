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
public enum PositionDeptMsg {

    msg001("岗位名称不可为空"),
    msg002("该岗位编码已经存在"),
    msg003("请选择部门节点！"),
    msg004("请选择末级部门！"),
    msg005("引入职位"),    
    msg006("引入成功！"),    
    msg007("请切换到岗位页"),    
    msg008("未选择岗位！"),    
    msg009("复制成功"),
    msg011("是否同时复制岗位附表信息?"),
    msg012("请切换到岗位附表页"),    
    msg013("无法粘贴，请粘贴同一附表"),    
    msg014("粘贴成功"),
    msg015("未选择任何对象!"),
    msg016("确定要删除吗？"),
    msg018("新增附表：");
  
    private String value;

    private PositionDeptMsg() {
    }

    private PositionDeptMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PositionDeptMsg.class, this.name(), this.value);
    }
}
