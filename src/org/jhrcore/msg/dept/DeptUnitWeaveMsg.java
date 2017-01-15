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
public enum DeptUnitWeaveMsg {

    tt001("生成新编制"),
    tt002("生成编制周期"),
    tt003("设置岗位编制字段"),
    tt004("部门编制统计人数条件"),
    tt005("编制方案设置"),
    msg001("年"),
    msg002("季"),
    msg003("月"),  
    msg004("新编制生成完毕"), 
    msg005("请选择生成周期！"),  
    msg006("该周期已生成编制数据"),   
    msg007("未选择参考周期"),    
    msg008("请删除子节点"),    
    msg009("该编制周期已生成编制数据,不允许删除!"),
    msg010("条件不可为空!"),
    msg011("请选择岗位编制字段！"),
    msg012("岗位编制字段仅能选择一个！"),
    msg013("设置成功"),
    msg014("岗位"),
    msg016("岗位编制字段树"),
    msg017("请在“单位编制”参数设置中，设置岗位编制字段！"),
    msg018("您确定要计算吗"),
    msg019("计算成功！"),
    msg020("确定要删除当前周期的所有部门编制以及岗位编制吗？"),
    msg021("请先设置编制方案!"),
    msg022("单位"),
    msg023("引入成功"),
    msg024("单位编制方案"),
    msg025("未审核"),
    msg026("确定要审核通过当前周期编制吗?"),
    msg027("确定要取消审核当前周期编制吗?"),
    msg028("部门编制条件"),
    msg029("单位");
    
    private String value;

    private DeptUnitWeaveMsg() {
    }

    private DeptUnitWeaveMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(DeptUnitWeaveMsg.class, this.name(), this.value);
    }
}
