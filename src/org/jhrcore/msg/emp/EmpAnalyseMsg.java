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
public enum EmpAnalyseMsg {

    ttl002("查看人员具体信息"),
    ttl001("当前记录数："),
    ttl003("第二步：设置统计方案相关参数"),
    ttl004("统计分析向导"),
    ttl005("统计条件: "),
    ttl006("分段设置"),
    ttl007("新增方案向导"),
    ttl008("编辑方案向导"),
    ttl009("方案共享设置"),
    msg001("请选择统计类别"),
    msg002("保存方案失败"),
    msg003("你确定要删除方案吗？"),
    msg004("共享方案不允许编辑"),
    msg005("横向无对比项，执行纵向对比"),
    msg006("部门名称");
    private String value;

    private EmpAnalyseMsg() {
    }

    private EmpAnalyseMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(EmpAnalyseMsg.class, this.name(), this.value);
    }
}
