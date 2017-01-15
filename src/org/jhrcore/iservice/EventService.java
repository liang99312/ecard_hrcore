/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice;

import java.rmi.RemoteException;
import org.jhrcore.entity.salary.ValidateSQLResult;

/**
 *
 * @author mxliteboss
 */
public interface EventService extends SuperService {

    public ValidateSQLResult getEventState(String workId) throws RemoteException;
}
