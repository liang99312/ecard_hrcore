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
public enum PaySalesDetailMsg {

    ttl001("挑选人员"),
    ttl002("状态：新记录"),
    ttl003("请选择"),
    ttl004("快速查找"),
    ttl005("商品选择："),
    ttl006("计算销售提成"),
    ttl007("修改明细信息"),
    ttl008("快速输入查询字段设置"),
    ttl009("快速输入查询字段设置"),
    ttl010("销售单录入"),
    ttl011("调整参与销售人员"),
    msg001("找不到相关人员"),
    msg002("确定移除选择人员？"),
//    msg003("找不到服务"),
    msg004("状态：已保存"),
    msg005("状态：新记录"),
    msg006("没有选择门店"),
    msg007("请到销售提成界面的工具中快速输入查询字段设置"),
    msg008("请输入人员查询信息"),
    msg009("请选择商品"),
    msg010("请输入有效的销售数量"),
    msg011("请输入正确的年月"),
    msg012("当前没有选择记录"),
    msg013("工资计算成功");
    private String value;

    private PaySalesDetailMsg() {
    }

    private PaySalesDetailMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PaySalesStockMsg.class, this.name(), this.value);
    }
}
