/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.List;
import org.jhrcore.entity.DeptChgLog;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.RyChgLog;
import org.jhrcore.entity.salary.ValidateSQLResult;

/**
 *
 * @author hflj
 */
public interface DeptService extends SuperService {
    //��������

    public ValidateSQLResult AddDept(DeptCode dept, RyChgLog rcl) throws RemoteException;
    //����ת��

    public ValidateSQLResult transDept(List<DeptCode> depts, String src_code, String dst_code, DeptChgLog log, Hashtable<String, String> save_codes) throws RemoteException;
    //���źϲ�

    public ValidateSQLResult unitDept(List<DeptCode> depts, DeptChgLog log) throws RemoteException;
    //���ų���

    public ValidateSQLResult delDeptChgA01s(List depta01s) throws RemoteException;
    //��������

    public ValidateSQLResult setGrade(int grade, int old_len, int new_len, DeptChgLog log) throws RemoteException;
    //�߼�ɾ������

    public ValidateSQLResult delDept(String deptKey, RyChgLog rcl) throws RemoteException;
    //�ָ����߼�ɾ��������

    public ValidateSQLResult resumeDept(List<String> deptKeys, RyChgLog rcl) throws RemoteException;
    //����ɾ������

    public ValidateSQLResult delDeptPhysical(String deptKeyStr, String user_code) throws RemoteException;

    public ValidateSQLResult editDept(Object dept, String edit_code) throws RemoteException;

    public ValidateSQLResult changeDeptFullName(List<String[]> save_dept) throws RemoteException;

    public ValidateSQLResult addDeptAppendix(Object obj) throws RemoteException;

    public ValidateSQLResult saveDeptSort(String parent_code, List<String[]> childdepts) throws RemoteException;

    public ValidateSQLResult recoveryDeptSort(String parent_code, String method) throws RemoteException;
    //���Ƹ�λ�Լ���λ����

    public ValidateSQLResult copyG10(Hashtable hash, int i) throws RemoteException;

    public ValidateSQLResult delG10(String key, String type) throws RemoteException;

    public ValidateSQLResult importDept(List saveDepts, String sqls) throws RemoteException;
}
