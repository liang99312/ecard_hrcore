/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import org.jhrcore.entity.AutoNoRule;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.RyChgLog;
import org.jhrcore.entity.SysParameter;
import org.jhrcore.entity.change.ChangeScheme;
import org.jhrcore.entity.VirtualDeptPersonLog;
import org.jhrcore.entity.query.QueryScheme;
import org.jhrcore.entity.salary.ValidateSQLResult;
/**
 *
 * @author Administrator
 */
public interface PersonService extends SuperService {

    public ValidateSQLResult addAppendix(Object obj) throws RemoteException;

    public ValidateSQLResult addAppendixs(List apppendixs) throws RemoteException;

    /**
     * ��ְ�Ǽ���Ա��⣨����Ҫ������
     * @param list�������ԱKeyList
     * @param logModel����־ģ�ͣ���������û������Ϣ
     * @return���������
     * @throws RemoteException
     */
    public ValidateSQLResult addPersonForNoCheck(List<String> list, RyChgLog logModel) throws RemoteException;

    public ValidateSQLResult cancelRegisterFlow(List<String> a01Keys) throws RemoteException;

    public ValidateSQLResult changePersonNo(List<String[]> nos, String no_field) throws RemoteException;

    public void changePersonG10(List<String[]> a01s) throws RemoteException;

    public ValidateSQLResult createPersonPYM(int type, boolean isAll, String s_where, List<String> a01_keys) throws RemoteException;

    public byte[] downloadPicture(String pic_path) throws RemoteException;

    public void deletePicture(String pic_path) throws RemoteException;
    
    public ValidateSQLResult delNewPerson(List<String> a01_keys) throws RemoteException;

    /**
     * �˷������ڸ���Ŀ����־����ɾ����Ա�����������⣩������Ϣ������¼һ������ɾ����־
     * @param log_keys����־Key list
     * @param RyChgLog�������û�
     * @return���������
     * @throws RemoteException
     */
    public ValidateSQLResult delPersonFromLog(List<String> log_keys, RyChgLog rcl) throws RemoteException;

    public byte[] downloadFile(String pic_path, List<String> list) throws RemoteException;

    public ValidateSQLResult deleteEmpDocu(List<String> edKeys) throws RemoteException;

    public void deleteFile(String path) throws RemoteException;

    public ValidateSQLResult delAppendix(String tableName, List<String[]> aKeys) throws RemoteException;

    public ValidateSQLResult updateAppendix(String tableName, List<String[]> aKeys) throws RemoteException;

    public ValidateSQLResult delChangeScheme(List<ChangeScheme> schemes) throws RemoteException;
    
    public List getDocumentPerson(List<String> depts, Set<String> a0190s, String person_key) throws RemoteException;

    public boolean isNullFloder(String f_path) throws RemoteException;

    public ValidateSQLResult outVirtualDept(List<String> a01Keys, VirtualDeptPersonLog modelLog) throws RemoteException;

    public ValidateSQLResult recoveryChange(Object change) throws RemoteException;

    public ValidateSQLResult saveChangeScheme(ChangeScheme changeScheme, QueryScheme queryScheme, boolean isNew, String roleKeys) throws RemoteException;

    public ValidateSQLResult saveRegisterDesign(List paras, List showSchemes, List depts) throws RemoteException;

    public ValidateSQLResult saveEmpNoRule(AutoNoRule anr, String old_no) throws RemoteException;

    public ValidateSQLResult saveEmpReigster(Object a01, List appendix, RyChgLog rcl, String entityName) throws RemoteException;

    //��������
    public ValidateSQLResult saveAnnexCheck(List entityList) throws RemoteException;

    public ValidateSQLResult setEmpDocuFilePath(String path, SysParameter sp) throws RemoteException;

    //�����޸ĵ���Ա
    public ValidateSQLResult UpdateA01s(List a01s) throws RemoteException;

    public ValidateSQLResult uploadFile(byte[] p_byte, String file_path) throws RemoteException;

    public void uploadPicture(byte[] p_byte, String pic_path) throws RemoteException;

    public Hashtable<String, DeptCode> getEmpJdDept(String a01KeyStr) throws RemoteException;

    public ValidateSQLResult recoveryChanges(List recList, String change_style) throws RemoteException;
}
