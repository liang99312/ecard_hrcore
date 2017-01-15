/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.iservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author hflj
 */
public interface SuperService extends Remote {
    public long getServerStartTime() throws RemoteException;
}
