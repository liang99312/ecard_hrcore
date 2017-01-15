/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice;

import java.rmi.RemoteException;
import java.util.HashMap;

/**
 *
 * @author lenovo
 */
public interface DpicService extends SuperService{
    public byte[] downloadPicture(String pic_path) throws RemoteException;
    public HashMap<String,String> getDbConfig() throws RemoteException;
}
