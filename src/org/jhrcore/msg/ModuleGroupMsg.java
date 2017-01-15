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
    //----------����----------------
    Dept_Management("��������"),
    Position_Management("��λ����"),
    Establishment_Management("���ƹ���"),
    //----------����----------------
    Inter_Manger("��Ϣ����"),
    Change_Mange("�䶯����"),
    Query_Analysis("��ѯ����"),
    //----------н��----------------
    Pay_Base("��������"),
    Pay_Budget("н��Ԥ��"),
    Pay_Plan("н��ƻ�"),
    Pay_Cal("н�����"),
    Pay_Analysis("��ѯ����"),
    Index("��ҳ"),
    Exam_Results("���˽��"),  
    Basic_Design("��������"),
    Performance_Plan("��Ч�ƻ�"),
    Performance_Imple("��Чʵʩ"),
    Appraisal_Program("���˷���"),
    Appraisal_Imple("��Ч����"),
    Perform_Commun("��Ч����"),    
    Train_Act("��ѵ�"),
    Train_Plan("��ѵ�ƻ�"),
    Train_Needs("��ѵ����"),
    Train_Resource("��ѵ��Դ����"),
    Talent_Pool("�˲ſ�"),
    Recruit_Management("��Ƹ����"),
    Recruit_Require("��Ƹ����"),
    Data_Sink("���ݽ���"),
    Attendance_Result("���ڽ��"),
    Manual_Timing("�ֹ�����"),
    Attendance_Data("��������"),
    Basic_Settings("��������"),
    Kaoqin_leave("��ٹ���"),
    Kaoqin_overtime("�Ӱ����"),
    Fund_Butget("����Ԥ��"),
    Fund_Supply("��������"),
    Fund_Osupply("��������"),
    Fund_Settings("��������"),
    Ident_Result("�������"),
    Ident_Implement("����ʵʩ"),
    Accreditation_Scheme("�����ƻ�"),
    Base_Installation("��������");
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
