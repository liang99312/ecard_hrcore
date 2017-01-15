/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.iservice;

import java.rmi.RemoteException;
import java.util.Properties;

/**
 *
 * @author Administrator
 */
public interface ConfigService extends SuperService {
    public Properties getProperties() throws RemoteException;
    public void saveProperties(Properties properties) throws RemoteException;
}
