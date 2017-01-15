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
public enum EmpDocumentationMsg {

    ttl001("导出文档"),
    ttl002("电子文档路径设置："),
    ttl003("档案合并："),
    ttl004("批量新增文档"),
    ttl005("当前人员："),
    ttl006("重命名"),
    ttl007("获取图片"),
    ttl008("新增路径"),
    ttl009("部门选择"),
    ttl010("文档类别"),
    msg001("此操作将会删除选择人员除工资以外的所有信息，确定要继续吗？"),
    msg002("确定要删除选择的日志吗"),
    msg003("彻底删除成功"),
//    msg004("服务调用失败"),
    msg005("恢复成功"),
    msg006("恢复失败"),
    msg007("一次最多合并800条记录"),
    msg008("确定要合并这些档案？"),
    msg009("合并成功"),
    msg010("请选择人员"),
    msg011("请选择文档分类"),
    msg012("是否采集图像？"),
    msg013("文件名不能为空"),
    msg014("文件名已存在"),
    msg015("图像采集成功！"),
    msg016("确定要删除这些文档及记录？"),
    msg017("该分类有子分类，不允许删除"),
    msg018("该分类关联个人文档，不允许删除"),
    msg019("分类名称不能为空！"),
    msg020("已存在这个名称的文档分类！"),
    msg021("已存在这个编码的文档分类！"),
    msg022("找不到文件"),
    msg023("找不到设备"),
    msg024("存在重名文件，是否覆盖"),
    msg025("文件存放路径错误"),
    msg026("本次上传共选择文件："),
    msg027("上传成功："),
    msg028("上传失败："),
    msg029("文件对应人员不在选择部门的："),
    msg030("文件名不符合规则的："),
    msg031("以下是详细日志："),
    msg032("个"),
    msg033("人员编号："),
    msg034(" 文件:"),
    msg035("导入成功！"),
    msg036("文件路径错误，导入终止！"),
    msg037("路径不能为空"),
    msg038("路径无效"),
    msg039("该路径下有文件，不允许删除"),
    msg040("确定删除选中的路径？"),
    msg041("设置成功"),
    msg042("设置失败"),
    msg043("没有相关的记录"),
    msg044("成功导出"),
    msg045("不存在文件的"),
    msg046("条;"),
    msg047("所有人员");
    private String value;

    private EmpDocumentationMsg() {
    }

    private EmpDocumentationMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(EmpDocumentationMsg.class, this.name(), this.value);
    }
}
