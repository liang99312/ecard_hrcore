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
public enum EmpChangeSchemeMsg {

    ttl001("变动字段编辑性设置"),
    ttl002("变动主表"),
    ttl003("变动项目"),
    ttl004("引用表信息"),
    ttl005("字段列表"),
    msg001("未选择任何方案"),
    msg002("确定要删除选择的变动模板吗"),
    msg003("失败"),
    msg004("新增调配方案"),
    msg005("编辑调配方案"),
    msg006("第一步：设置变动字段"),
    msg007("[变动名称]不能为空"),
    msg008("变动已存在"),
    msg009("未选择任何变动字段"),
    msg010("最后一步：设置适用对象（可选）"),
    msg011("请设置引入来源"),
    msg012("数据类型不一致，可能导致引入错误，确定要引入吗？"),
    msg014("第四步：设置附表处理业务"),
    msg015("第三步：设置附表处理业务"),
    msg016("未设置变动后人员类别"),
    msg017("请选择变动前的人员类别"),
    msg018("所有类别"),
    msg019("第二步：设置变动后的人员类别"),
    msg022("第二步：设置附表处理业务"),
    msg023("第五步：设置附表更新业务（可选）"),
    msg024("第四步：设置附表更新业务（可选）"),
    msg025("变动模板编辑向导"),
    msg026("原"),
    msg027("新"),
    msg028("更新"),
    msg029("当前人员已经在调动中");

    private String value;

    private EmpChangeSchemeMsg() {
    }

    private EmpChangeSchemeMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(EmpChangeSchemeMsg.class, this.name(), this.value);
    }
}
