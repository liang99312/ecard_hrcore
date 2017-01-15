/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.iservice;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.List;
import org.jhrcore.entity.RyChgLog;
import org.jhrcore.entity.salary.ValidateSQLResult;

/**
 *
 * @author mxliteboss
 */
public interface ImportService extends SuperService{
    public ValidateSQLResult importData(String ex_sql,HashSet save_objs) throws RemoteException;
    public ValidateSQLResult importA01Data(String ex_sql,HashSet save_objs,RyChgLog rc) throws RemoteException;
    public ValidateSQLResult importCommData(HashSet save_objs,HashSet update_objs,String comm_code) throws RemoteException;
    public ValidateSQLResult saveData(String ex_sql,List save_objs) throws RemoteException;
    public ValidateSQLResult saveObjData(HashSet save_objs, HashSet update_objs) throws RemoteException ;
    public ValidateSQLResult saveObjData(HashSet save_objs, HashSet update_objs,String ex_sql) throws RemoteException ;
}
