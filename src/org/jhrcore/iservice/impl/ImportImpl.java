/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice.impl;

import java.util.HashSet;
import java.util.List;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.RyChgLog;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.ImportService;
import org.jhrcore.serviceproxy.LocatorManager;

/**
 *
 * @author mxliteboss
 */
public class ImportImpl {

    private static String service = ImportService.class.getSimpleName();

    public static ValidateSQLResult importData(String ex_sql, HashSet save_objs) {
        Object obj = LocatorManager.invokeService(service, "importData", new Object[]{ex_sql, save_objs});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult importA01Data(String ex_sql, HashSet save_objs, RyChgLog rc) {
        Object obj = LocatorManager.invokeService(service, "importA01Data", new Object[]{ex_sql, save_objs, rc});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult importCommData(HashSet save_objs, HashSet update_objs, String comm_code) {
        Object obj = LocatorManager.invokeService(service, "importCommData", new Object[]{save_objs, update_objs, comm_code});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveData(String ex_sql, List save_objs) {
        Object obj = LocatorManager.invokeService(service, "saveData", new Object[]{ex_sql, save_objs});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveObjData(HashSet save_objs, HashSet update_objs) {
        Object obj = LocatorManager.invokeService(service, "saveObjData", new Object[]{save_objs, update_objs});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveObjData(HashSet save_objs, HashSet update_objs, String ex_sql) {
        Object obj = LocatorManager.invokeService(service, "saveObjData", new Object[]{save_objs, update_objs, ex_sql});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }
}
