/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.msg.pay;

import org.jhrcore.ui.language.WebHrMessage;

/**
 *
 * @author mxliteboss
 */
public enum PayCompareMsg {

    ttl001("编辑对照表名称"),
    ttl002("引入关联编码"),
    ttl003("薪酬对照表模板"),
    ttl004("导入对照表信息"),
//    msg001("您选择的不是有效的EXCEL文件"),
    msg002("请选择对照表"),
    msg003("导入消息报告如下："),
    msg004(" 成功导入记录： "),
    msg005(" 数据格式有误的记录： "),
    msg006("条；"),
    msg007("请先添加编码");
    private String value;

    private PayCompareMsg() {
    }

    private PayCompareMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayCompareMsg.class, this.name(), this.value);
    }
}
