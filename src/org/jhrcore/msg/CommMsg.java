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

    UNABLETOPRINT("�޷����Ӵ�ӡ��,�����ӡ���Ƿ�������"),
    UNABLETOALLOCATE("�޷����䲿�ű���"),
    CODELENGTHlIMIT("���볤�����ƣ��޷��ٷ����±��룡"),
    PROCESSING("���ڴ����У����Ժ�..."),
    INFORMATION_MESSAGE("��ʾ"),
    ERROR_MESSAGE("����"),
    QUESTION_MESSAGE("ѯ��"),
    DEL_MESSAGE("ȷ��Ҫɾ��ѡ�м�¼��?"),
    DELLOGIC_MESSAGE("ȷ��Ҫ�߼�ɾ��ѡ�м�¼��?"),
    DELSHEER_MESSAGE("ȷ��Ҫ����ɾ��ѡ�м�¼��?"),
    NOPOWER_MESSAGE("Ȩ�޲���"),
    SAVECONFIRM_MESSAGE("��ǰ��¼�иĶ����Ƿ���Ҫ����"),
    UPDATESUCCESS_MESSAGE("���³ɹ�"),
    UPDATEFAIL_MESSAGE("����ʧ��"),
    UPDATECONFIRM_MESSAGE("ȷ��Ҫ���£�"),
    SAVESUCCESS_MESSAGE("����ɹ�"),
    ADDSUSSESS_MESSAGE("�����ɹ�"),
    ADDFAIL_MESSAGE("����ʧ��"),
    DELSUCCESS_MESSAGE("ɾ���ɹ�"),
    DELFAIL_MESSAGE("ɾ��ʧ��"),
    DELNORIGHT_MESSAGE("�����߱���ǰ��¼��ɾ��Ȩ�ޣ��޷�ɾ��"),
    ACTIONSUCCESS_MESSAGE("�����ɹ�"),
    ACTIONFAIL_MESSAGE("����ʧ��"),
    EXPORT_MESSAGE("������Ϣ�������£�"),//
    EXPORT_SUCCESS("�ɹ������¼��"),//
    EXPORT_UPDATE("�ɹ����¼�¼:"),//
    EPORT_UPDATEABLE(" �ɸ��¼�¼��"),
    EXPORT_REPEAT(" �ظ��ļ�¼��"),
    EXPORT_EXIST(" �Ѵ��ڼ�¼�� "),
    EXPORT_NOFIND(" ��ƥ�����ƥ�䲻������ֻ����ʱ���ݿ����еļ�¼��"),//
    EXPORT_ERROR("���ݸ�ʽ����(�����������ڷ�Χ)�ļ�¼��"),
    EXPOET_DBERROR("���ݿ���´���ļ�¼��"),
    EXPORT_ERRORORNOFIND(" ���ݸ�ʽ�������ƥ��ļ�¼�� "),
    EXPORT_EXECUTEERROR(" ִ�д���"),
    EXPORT_TIAO("��;"),
    EXPORT_NOCLASSNAME("�ɹ������¼�� �����û�а�������У�����������;"),
    RESTARTSERVER("������HRSERVER��������"),
    COMMITSUCCESS("�ύ�ɹ�"),
    COMMITCONFIRM_MESSAGE("��ȷ��Ҫ�ύ��ѡ��¼��"),
    UNCOMMITCONFIRM_MESSAGE("��ȷ��Ҫȡ���ύ��ѡ��¼��"),
    UNCOMMITSUCCESS("ȡ���ύ�ɹ�"),
    TTLWORKFLOWGRAPH("����ͼ"),
    MAXROW_MESSAGE("ÿ�����������800����¼"),
    NOWORKFLOW("���޷��������̣����ܵ�ԭ��Ϊ��\n 1��δ�����������̣�\n 2�����߱������������̵�Ȩ��"),//
    UPLOADSUCCESS("�ϴ��ɹ���"),
    REVOVERSUCCESS("�ָ��ɹ�"),
    REMOVEMESSAGE("��ȷ��Ҫ�Ƴ�ѡ���¼��"),
    SAVEFAIL("����ʧ��"),
    BUILDSUCCESS_MESSAGE("���ɳɹ�"),
    BUILDFAIL_MESSAGE("����ʧ��"),
    ALTERSUCCESS_MESSAGE("�޸ĳɹ�"),
    SELECTXLSFILE_MESSAGE("��ѡ��XLS�ļ�"),
    SELECTFILE_MESSAGE("��ѡ���ļ�"),
    FILEEXISTS("�ļ��Ѵ���"),
    SELECTPIC_MESSAGE("��ѡ��ͼƬ"),
    error_insert_msg("����ʧ�ܣ�������ʾ���£�"),
    error_update_msg("����ʧ�ܣ�������ʾ���£�"),
    error_del_msg("ɾ��ʧ�ܣ�������ʾ���£�"),
    error_save_msg("����ʧ�ܣ�������ʾ���£�"),
    error_cal_msg("����ʧ�ܣ�������ʾ���£�"),
    validate_sucess("��ͨ����֤"),
    validate_fail("δͨ����֤"),
    msgCommQuery("���ò�ѯ"),
    noWorkFlow("���Ƚ���������"),
    START_MESSAGE("��ʼ");
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
