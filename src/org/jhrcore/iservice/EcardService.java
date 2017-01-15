/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice;

import java.rmi.RemoteException;
import java.util.List;
import org.jhrcore.entity.salary.ValidateSQLResult;

/**
 *
 * @author Administrator
 */
public interface EcardService extends SuperService {
    
    public ValidateSQLResult calcHuiKuan(String ym,String state,List<String> keys) throws RemoteException;
    
    public ValidateSQLResult calcXiaoFei(String ym,String state,List<String> keys) throws RemoteException;
}
