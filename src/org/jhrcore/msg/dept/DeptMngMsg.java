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

    msgQuery("����:"),
    msgAllPersion("ȫ����Ա"),
    msgDept("����"),
    msgSolutionNull("����������Ϊ��"),
    msgCodeEnd("ֻ��ѡ��ĩ������"),
    msgNewPosition("�����±���"),
    msgPasteSucc("ճ���ɹ�"),
    msgCopyDept("�Ƿ�ͬʱ���Ƹ�λ������Ϣ?"),
    msgDeptCannotDel("��ɾ�������²��ɽ�������"),
    msgDeptNameNotnull("�������Ʋ���Ϊ��"),
    msgDeptCodeParent("���ű�������Ը�������Ϊǰ׺"),
    msgDeptCodeNumber("���ű������������"),
    msgDeptCodeRule("���ű��볤�Ȳ����ϱ������"),
    msgDeptCodeExist("�ò��ű����Ѿ�����"),
    msgDeptInvented("���ⲿ������ֻ�ܽ������ⲿ��"),
    msgDeptRankNo("��ǰ������Ѵ���,����ò��Ž�˳������������ŵ�����ţ��Ƿ������"),
    msgRecordNotExceed("һ�β�����¼���ܳ���500����"),
    msgTreeRoot("���в���"),
    msgDeptCanNotRevocat("������ѡ�������ż����¼�����"),
    msgDeptSetup("���������²���"),
    msgDeptSetupFail("δ���ò���"),
    msgDeptLevelSetsucc("���ż������óɹ�!"),
    msgDeptLevelSetFail("���ż�������ʧ��!"),
    msgRebuidSuccess("���ɳɹ�,��ˢ�»�������µ�¼�鿴Ч��"),
    msgReductionSortSucce("��ԭ����ɹ�"),
    msgNotSelectDepart("δѡ��Ŀ�겿��"),
    msgInvalidChange("��Ч���"),
    msgUnableToallocate("�޷����䲿�ű���"),
    msgTargetSector("Ŀ�겿��Ϊ������Ա��ĩ�����ţ�������ת��"),
    msgDeptLeavelSmall("��ǰ���ż���̫С��"),
    msgTargetisFinal("Ŀ�겿�ű���Ϊĩ������"),
    msgSysForIC("�뵽���ߵ�ϵͳ����������IC��λ�ֶ�"),
    msgWriteCard("д���ɹ�"),
    msgOrderNull("����Ų���Ϊ��"),
    msgOrderWrong("����ų��ȴ��󣬵�ǰѡ���ŵ�����ų��ȱ���Ϊ��"),
    msgOrderNum("����ű���Ϊ����"),
    msgcardReadeDKQ("��Ŀǰ���õĶ�����Ϊ ������DKQ�����µ�¼����󷽿���Ч"),
    msgcardReadeICR("��Ŀǰ���õĶ�����Ϊ ����ICR100�����µ�¼����󷽿���Ч"),
    msgCardReadeIc("��Ŀǰ���õĶ�����Ϊ һ��IC����д�������µ�¼����󷽿���Ч"),
    msgCardReadeMf("��Ŀǰ���õĶ�����Ϊ MF-800��д�������µ�¼����󷽿���Ч"),
    msgDeptLeave("��ĩ������!"),
    msgDeptStaff("�ò�����Ա��,�޷�ɾ��"),
    msgDeptHaveStaff("�ò����н����Ա,�޷�����"),
    msgDeleteDept("ȷ��Ҫɾ��ѡ�в��ż��������Ӳ�����"),
    ttlExportDlg("������Ϣ"),
    ttlDeptMiddle("�м䲿������"),
    ttlDeptCheck("���ŵ���"),
    ttlDeptLog("���ű䶯��־"),
    ttlDeptDel("���ų���"),
    ttlDeptSort("�����������"),
    ttlDeptTran("����ת��"),
    ttlDeptUnit("���źϲ�"),
    ttlAddAppendix("��������"),
    msg001("�������Ų��������"),
    msg002("�ò����н����Ա,�޷�����"),
    msg004("ֻ��ĩ�����Ų������ϲ�"),
    msg005("���ű��벻��Ϊ��"),
    msg007("���ű��벻���Ϲ���"),
    msg008("�ñ����Ѿ�����"),
    msg009("����������δ���øü��εı��볤��"),
    msg010("�˲�������ɾ����ѡ���ż��������¼��ȷ��Ҫɾ����"),
    msg012("��������ʧ�ܣ�������λ������"),
    msg014("���и�λ"),
    msg015("���ŵ���ģ��"),
    msg016("��������"),
    msg017("���뼶�������������ö�ʮ����"),
    msg019("�Զ�����ѡ��"),
    msg020("�����м䲿���ֶ�"),
    msg021("�м䲿���ֶ�"),
    msgMore("����");
    

    
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
