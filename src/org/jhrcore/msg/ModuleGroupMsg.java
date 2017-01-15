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
public enum ModuleGroupMsg {
    //----------机构----------------
    Dept_Management("机构管理"),
    Position_Management("岗位管理"),
    Establishment_Management("编制管理"),
    //----------人事----------------
    Inter_Manger("信息管理"),
    Change_Mange("变动管理"),
    Query_Analysis("查询分析"),
    //----------薪资----------------
    Pay_Base("基础设置"),
    Pay_Budget("薪酬预算"),
    Pay_Plan("薪酬计划"),
    Pay_Cal("薪酬核算"),
    Pay_Analysis("查询分析"),
    Index("首页"),
    Exam_Results("考核结果"),  
    Basic_Design("基本设置"),
    Performance_Plan("绩效计划"),
    Performance_Imple("绩效实施"),
    Appraisal_Program("考核方案"),
    Appraisal_Imple("绩效考核"),
    Perform_Commun("绩效反馈"),    
    Train_Act("培训活动"),
    Train_Plan("培训计划"),
    Train_Needs("培训需求"),
    Train_Resource("培训资源管理"),
    Talent_Pool("人才库"),
    Recruit_Management("招聘管理"),
    Recruit_Require("招聘需求"),
    Data_Sink("数据接收"),
    Attendance_Result("考勤结果"),
    Manual_Timing("手工考勤"),
    Attendance_Data("考勤数据"),
    Basic_Settings("基础设置"),
    Kaoqin_leave("请假管理"),
    Kaoqin_overtime("加班管理"),
    Fund_Butget("经费预算"),
    Fund_Supply("津贴管理"),
    Fund_Osupply("其它经费"),
    Fund_Settings("基础设置"),
    Ident_Result("鉴定结果"),
    Ident_Implement("鉴定实施"),
    Accreditation_Scheme("鉴定计划"),
    Base_Installation("基础设置");
    private String value;

    private ModuleGroupMsg() {
    }

    private ModuleGroupMsg(String value) {
        this.value = value;

    }

    @Override
    public String toString() {
        return WebHrMessage.getMsgString(ModuleGroupMsg.class, this.name(), this.value);
    }
}
