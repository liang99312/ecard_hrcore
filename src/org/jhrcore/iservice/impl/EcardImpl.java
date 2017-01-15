/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice.impl;

import java.util.List;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.EcardService;
import org.jhrcore.serviceproxy.LocatorManager;

/**
 *
 * @author mxliteboss
 */
public class EcardImpl {
    
    private static String service = EcardService.class.getSimpleName();
    
    public static ValidateSQLResult calcHuiKuan(String ym,String state,List<String> keys) {
        Object obj = LocatorManager.invokeService(service, "calcHuiKuan", new Object[]{ym,state,keys});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }
    
    public static ValidateSQLResult calcXiaoFei(String ym,String state,List<String> keys) {
        Object obj = LocatorManager.invokeService(service, "calcXiaoFei", new Object[]{ym,state,keys});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }
}
