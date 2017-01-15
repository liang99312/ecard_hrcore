/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.msg;

import org.jhrcore.ui.language.WebHrMessage;

/**
 *
 * @author mxliteboss
 */
public enum CommMsg {

    UNABLETOPRINT("无法连接打印机,请检查打印机是否已连接"),
    UNABLETOALLOCATE("无法分配部门编码"),
    CODELENGTHlIMIT("编码长度限制，无法再分配新编码！"),
    PROCESSING("正在处理中，请稍后..."),
    INFORMATION_MESSAGE("提示"),
    ERROR_MESSAGE("错误"),
    QUESTION_MESSAGE("询问"),
    DEL_MESSAGE("确定要删除选中记录吗?"),
    DELLOGIC_MESSAGE("确定要逻辑删除选中记录吗?"),
    DELSHEER_MESSAGE("确定要彻底删除选中记录吗?"),
    NOPOWER_MESSAGE("权限不足"),
    SAVECONFIRM_MESSAGE("当前记录有改动，是否需要保存"),
    UPDATESUCCESS_MESSAGE("更新成功"),
    UPDATEFAIL_MESSAGE("更新失败"),
    UPDATECONFIRM_MESSAGE("确定要更新？"),
    SAVESUCCESS_MESSAGE("保存成功"),
    ADDSUSSESS_MESSAGE("新增成功"),
    ADDFAIL_MESSAGE("新增失败"),
    DELSUCCESS_MESSAGE("删除成功"),
    DELFAIL_MESSAGE("删除失败"),
    DELNORIGHT_MESSAGE("您不具备当前记录的删除权限，无法删除"),
    ACTIONSUCCESS_MESSAGE("操作成功"),
    ACTIONFAIL_MESSAGE("操作失败"),
    EXPORT_MESSAGE("导入消息报告如下："),//
    EXPORT_SUCCESS("成功导入记录："),//
    EXPORT_UPDATE("成功更新记录:"),//
    EPORT_UPDATEABLE(" 可更新记录："),
    EXPORT_REPEAT(" 重复的记录："),
    EXPORT_EXIST(" 已存在记录： "),
    EXPORT_NOFIND(" 无匹配项或匹配不到或者只导入时数据库已有的记录："),//
    EXPORT_ERROR("数据格式有误(包括不在日期范围)的记录："),
    EXPOET_DBERROR("数据库更新错误的记录："),
    EXPORT_ERRORORNOFIND(" 数据格式有误或无匹配的记录： "),
    EXPORT_EXECUTEERROR(" 执行错误："),
    EXPORT_TIAO("条;"),
    EXPORT_NOCLASSNAME("成功插入记录： 导入表没有班次名称列，不插入数据;"),
    RESTARTSERVER("请重启HRSERVER服务器！"),
    COMMITSUCCESS("提交成功"),
    COMMITCONFIRM_MESSAGE("您确定要提交所选记录？"),
    UNCOMMITCONFIRM_MESSAGE("您确定要取消提交所选记录？"),
    UNCOMMITSUCCESS("取消提交成功"),
    TTLWORKFLOWGRAPH("流程图"),
    MAXROW_MESSAGE("每次最多允许处理800条记录"),
    NOWORKFLOW("您无法发起流程，可能的原因为：\n 1，未建立新增流程；\n 2，不具备发起新增流程的权限"),//
    UPLOADSUCCESS("上传成功！"),
    REVOVERSUCCESS("恢复成功"),
    REMOVEMESSAGE("您确定要移除选择记录？"),
    SAVEFAIL("保存失败"),
    BUILDSUCCESS_MESSAGE("生成成功"),
    BUILDFAIL_MESSAGE("生成失败"),
    ALTERSUCCESS_MESSAGE("修改成功"),
    SELECTXLSFILE_MESSAGE("请选择XLS文件"),
    SELECTFILE_MESSAGE("请选择文件"),
    FILEEXISTS("文件已存在"),
    SELECTPIC_MESSAGE("请选择图片"),
    error_insert_msg("插入失败，错误提示如下："),
    error_update_msg("更新失败，错误提示如下："),
    error_del_msg("删除失败，错误提示如下："),
    error_save_msg("保存失败，错误提示如下："),
    error_cal_msg("计算失败，错误提示如下："),
    validate_sucess("已通过验证"),
    validate_fail("未通过验证"),
    msgCommQuery("常用查询"),
    noWorkFlow("请先建立工作流"),
    START_MESSAGE("开始");
    private String value;

    private CommMsg() {
    }

    private CommMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(CommMsg.class, this.name(), this.value);
    }
}
