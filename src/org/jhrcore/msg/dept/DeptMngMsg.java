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
public enum DeptMngMsg {

    msgQuery("查找:"),
    msgAllPersion("全部人员"),
    msgDept("部门"),
    msgSolutionNull("方案名不可为空"),
    msgCodeEnd("只能选择末级编码"),
    msgNewPosition("生成新编制"),
    msgPasteSucc("粘贴成功"),
    msgCopyDept("是否同时复制岗位附表信息?"),
    msgDeptCannotDel("已删除部门下不可建立部门"),
    msgDeptNameNotnull("部门名称不可为空"),
    msgDeptCodeParent("部门编码必须以父级编码为前缀"),
    msgDeptCodeNumber("部门编码必须是数字"),
    msgDeptCodeRule("部门编码长度不符合编码规则"),
    msgDeptCodeExist("该部门编码已经存在"),
    msgDeptInvented("虚拟部门下面只能建立虚拟部门"),
    msgDeptRankNo("当前排序号已存在,保存该部门将顺序调整后续部门的排序号，是否继续？"),
    msgRecordNotExceed("一次操作记录不能超过500条！"),
    msgTreeRoot("所有部门"),
    msgDeptCanNotRevocat("不允许选择撤销部门及其下级部门"),
    msgDeptSetup("请先设置新部门"),
    msgDeptSetupFail("未设置部门"),
    msgDeptLevelSetsucc("部门级次设置成功!"),
    msgDeptLevelSetFail("部门级次设置失败!"),
    msgRebuidSuccess("生成成功,需刷新缓存或重新登录查看效果"),
    msgReductionSortSucce("还原排序成功"),
    msgNotSelectDepart("未选择目标部门"),
    msgInvalidChange("无效变更"),
    msgUnableToallocate("无法分配部门编码"),
    msgTargetSector("目标部门为已有人员的末级部门，不允许转移"),
    msgDeptLeavelSmall("当前部门级次太小！"),
    msgTargetisFinal("目标部门必须为末级部门"),
    msgSysForIC("请到工具的系统设置中设置IC岗位字段"),
    msgWriteCard("写卡成功"),
    msgOrderNull("排序号不可为空"),
    msgOrderWrong("排序号长度错误，当前选择部门的排序号长度必须为："),
    msgOrderNum("排序号必须为数字"),
    msgcardReadeDKQ("您目前设置的读卡器为 新中新DKQ，重新登录软件后方可生效"),
    msgcardReadeICR("您目前设置的读卡器为 国腾ICR100，重新登录软件后方可生效"),
    msgCardReadeIc("您目前设置的读卡器为 一般IC卡读写器，重新登录软件后方可生效"),
    msgCardReadeMf("您目前设置的读卡器为 MF-800读写器，重新登录软件后方可生效"),
    msgDeptLeave("非末级部门!"),
    msgDeptStaff("该部门有员工,无法删除"),
    msgDeptHaveStaff("该部门有借调人员,无法撤销"),
    msgDeleteDept("确定要删除选中部门及其所有子部门吗？"),
    ttlExportDlg("导入消息"),
    ttlDeptMiddle("中间部门设置"),
    ttlDeptCheck("部门调整"),
    ttlDeptLog("部门变动日志"),
    ttlDeptDel("部门撤销"),
    ttlDeptSort("部门排序调整"),
    ttlDeptTran("部门转移"),
    ttlDeptUnit("部门合并"),
    ttlAddAppendix("新增附表"),
    msg001("顶级部门不允许操作"),
    msg002("该部门有借调人员,无法撤销"),
    msg004("只有末级部门才允许被合并"),
    msg005("部门编码不能为空"),
    msg007("部门编码不符合规则"),
    msg008("该编码已经存在"),
    msg009("级次设置中未设置该级次的编码长度"),
    msg010("此操作将会删除所选部门及其关联记录，确定要删除吗"),
    msg012("级次设置失败，最大编码位数过多"),
    msg014("所有岗位"),
    msg015("部门导入模板"),
    msg016("级次设置"),
    msg017("代码级数设置最多可设置二十级。"),
    msg019("自定义已选项"),
    msg020("新增中间部门字段"),
    msg021("中间部门字段"),
    msgMore("更多");
    

    
    private String value;

    private DeptMngMsg() {
    }

    private DeptMngMsg(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(DeptMngMsg.class, this.name(), this.value);
    }
}
