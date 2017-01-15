/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import org.jhrcore.entity.Code;
import org.jhrcore.entity.CommMap;
import org.jhrcore.entity.RyChgLog;
import org.jhrcore.entity.base.FieldDef;
import org.jhrcore.entity.right.RoleCode;
import org.jhrcore.entity.salary.ValidateSQLResult;

/**
 *
 * @author mxliteboss
 */
public interface SysService extends SuperService {

    public ValidateSQLResult updateFun(byte[] p_byte) throws RemoteException;

    public ValidateSQLResult uploadFile(byte[] p_byte, String fileName) throws RemoteException;

    public ValidateSQLResult closeService() throws RemoteException;

    public ValidateSQLResult updateVersion() throws RemoteException;

    public List fetchBackSchemes() throws RemoteException;

    public List getBackVersionByCode(String moduleCode) throws RemoteException;

    public List getBackData(String verNo, String quickStr, String qsStr) throws RemoteException;

    public ValidateSQLResult backData(String moduleCode, String s_where) throws RemoteException;

    public ValidateSQLResult importRes(List langList, List baseList, List resList) throws RemoteException;

    public ValidateSQLResult updateBaseToRes() throws RemoteException;

    public ValidateSQLResult updateLocalRes() throws RemoteException;

    public ValidateSQLResult changeCodeGrade(List<String[]> list) throws RemoteException;

    public ValidateSQLResult buildCodePYM(List<String[]> codes) throws RemoteException;

    public ValidateSQLResult saveCode(Code code, RoleCode rc) throws RemoteException;

    public ValidateSQLResult updateCode(Code code) throws RemoteException;

    public ValidateSQLResult delCode(Code code, int grades) throws RemoteException;

    public ValidateSQLResult saveLogField(List list) throws RemoteException;

    public ValidateSQLResult saveFieldFormat(List<FieldDef> fds) throws RemoteException;

    public ValidateSQLResult saveFieldRegula(Object obj) throws RemoteException;

    public ValidateSQLResult delSystemField(Object obj) throws RemoteException;

    public ValidateSQLResult saveSystemChange(List saveList, List updateList, List<String> delKeys) throws RemoteException;

    public ValidateSQLResult buildFieldPYM(String entity_key) throws RemoteException;

    public HashMap getSchemeNum(List<String> list_scheme_table, String where_sql) throws RemoteException;

    public void saveUserLog(Object li) throws RemoteException;

    public ValidateSQLResult saveUsers(List<String> keys, CommMap comm) throws RemoteException;
}
