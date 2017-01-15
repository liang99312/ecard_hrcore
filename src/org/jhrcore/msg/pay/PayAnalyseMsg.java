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
public enum PayAnalyseMsg {

    ttl001("分段设置"),
    ttl002("统计条件: "),
    ttl003("方案共享设置"),
    ttl004("显示方案"),
    ttl005("新增方案向导"),
    ttl006("编辑方案向导"),
    ttl007("工资年月"),
    ttl008("运算符"),
    ttl009("函数"),
    ttl010("部门穿透"),
    ttl011("自定义项目"),
    ttl012("详细信息"),
    ttl013("个人穿透"),
    ttl014("请选择考核指标(标准)"),
    ttl015("正在生成结果......"),
    ttl016("年"),
    ttl017("月 和 "),
    ttl018("月"),
    ttl019("异动对比表 "),
    ttl020("导出EXCEL"),
    ttl021("报表预览"),
    ttl022("关闭"),
    ttl023("新建方案"),
    ttl024("另存方案"),
    ttl025("添加人员"),
    ttl026("过滤条件"),
    ttl027("月 到 "),
    ttl028("部门"),
    ttl029("员工"),
    ttl030("合计"),
    ttl031("分布"),
    ttl032("表 "),
    ttl033("统计方式:"),
    ttl034("最后一步：设置过滤条件"),
    ttl035("第四步：设置统计参考项目"),
    ttl036("第三步：设置统计项目"),
    ttl037("第一步：设置统计方案名称"),
    msg001("你确定要删除方案吗？"),
    msg002("共享方案不允许编辑"),
    msg003("横向无对比项，执行纵向对比"),
    msg004("HR相关建议："),
    msg005("1, 由于选取编码的长度过多导致的语句拼接过长，建议减少编码长度."),
    msg006("2, 由于分段函数分段过多导致的语句拼接过长，建议减少分段个数."),
    msg007("3, 由于分段函数错误导致的语句执行错误，建议检查分段个数."),
    msg008("4, 由于自定义项目错误导致的语句执行错误，建议检查自定义项目."),
    msg009("请输入项目名称!"),
    msg010("计算表达式不能为空!"),
    msg011("     当前记录数："),
    msg012("开始月份不可迟于结束月份"),
    msg013("改变统计方式默认值将被清空，是否改变？"),
    msg014("没有可统计数据"),
    msg015("请选择统计类别"),
    msg016("第二步：设置统计相关参数"),
    msg017("请选择方案!"),
    msg018("当前方案为空，无法保存!"),
    msg019("方案名称不能为空!"),
    msg020("保存方案成功!"),
    msg021("至少选择一个部门!"),
    msg022("至少选择一个部门或者一个员工!"),
    msg023("请选择统计字段!"),
    msg024("请选择统计项"),
    msg025("请选择横向统计条件"),
    msg026("请选择纵向统计条件"),
    msg027("仅自定义项目才可编辑"),
    msg028("方案名已经存在");
    private String value;

    private PayAnalyseMsg() {
    }

    private PayAnalyseMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(PayAnalyseMsg.class, this.name(), this.value);
    }
}
