/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice;

import java.rmi.RemoteException;
import java.util.List;
import org.jhrcore.entity.right.RoleCode;
import org.jhrcore.entity.right.RoleEntity;
import org.jhrcore.entity.salary.ValidateSQLResult;

/**
 *
 * @author hflj
 */
public interface RightService extends SuperService {

    public ValidateSQLResult addUser(List<String[]> users) throws RemoteException;

    public ValidateSQLResult delUser(String userKeys, String roleKey) throws RemoteException;

    public ValidateSQLResult defineDeptRight(String dept_code, int mod, String user_key, boolean isSA, String g_rolea01_key) throws RemoteException;

    public ValidateSQLResult defineEntityRight(String p_role_key, List<String> roleKeys, List<RoleEntity> entityKeys, int mod) throws RemoteException;

    public ValidateSQLResult defineCodeRight(String p_role_key, List<String> roleKeys, List<RoleCode> codeKeys, int mod) throws RemoteException;

    public ValidateSQLResult copyRight(String srcRoleKey, String dstRoleKey, String code) throws RemoteException;

    public ValidateSQLResult defineFuntionRight(List<String[]> data) throws RemoteException;

    public ValidateSQLResult defineFieldRight(List<String[]> data) throws RemoteException;

    public ValidateSQLResult defineReportRight(List<String[]> data) throws RemoteException;

    public ValidateSQLResult defineUserRole(List<String[]> data) throws RemoteException;

    public List getReportRight(String roleKeys) throws RemoteException;

    public ValidateSQLResult cryptA01PassWord() throws RemoteException;
}
