/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice.impl;

import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.EventService;
import org.jhrcore.serviceproxy.LocatorManager;

/**
 *
 * @author mxliteboss
 */
public class EventImpl {

    private static String service = EventService.class.getSimpleName();

    public static ValidateSQLResult getEventState(String workId) {
        Object obj = LocatorManager.invokeService(service, "getEventState", new Object[]{workId});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }
}
