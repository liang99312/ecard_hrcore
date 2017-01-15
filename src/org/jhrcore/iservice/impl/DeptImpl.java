/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice.impl;

import java.util.Hashtable;
import java.util.List;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.DeptChgLog;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.RyChgLog;
import org.jhrcore.entity.change.ChangeScheme;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.DeptService;
import org.jhrcore.serviceproxy.LocatorManager;

/**
 *
 * @author hflj
 */
public class DeptImpl {

    private static String service = DeptService.class.getSimpleName();

    public static ValidateSQLResult AddDept(DeptCode dept, RyChgLog rcl) {
        Object obj = LocatorManager.invokeService(service, "AddDept", new Object[]{dept, rcl});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult delDept(String deptKey, RyChgLog rcl) {
        Object obj = LocatorManager.invokeService(service, "delDept", new Object[]{deptKey, rcl});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult editDept(Object dept, String edit_code) {
        Object obj = LocatorManager.invokeService(service, "editDept", new Object[]{dept, edit_code});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult resumeDept(List<String> deptKeys, RyChgLog rcl) {
        Object obj = LocatorManager.invokeService(service, "resumeDept", new Object[]{deptKeys, rcl});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult delG10(String key, String type) {
        Object obj = LocatorManager.invokeService(service, "delG10", new Object[]{key, type});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult checkWeave(List list, ChangeScheme cs) {
        Object obj = LocatorManager.invokeService(service, "checkWeave", new Object[]{list, cs});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult checkWeaveForReg(List list) {
        Object obj = LocatorManager.invokeService(service, "checkWeaveForReg", new Object[]{list});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult calWeave(String year) {
        Object obj = LocatorManager.invokeService(service, "calWeave", new Object[]{year});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult delDeptWeave(List<String> keys) {
        Object obj = LocatorManager.invokeService(service, "delDeptWeave", new Object[]{keys});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult createG10Weave(List g10s, String cycKey, String process) {
        Object obj = LocatorManager.invokeService(service, "createG10Weave", new Object[]{g10s, cycKey, process});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult copyG10(Hashtable hash, int i) {
        Object obj = LocatorManager.invokeService(service, "copyG10", new Object[]{hash, i});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveDeptSort(String parent_code, List<String[]> childdepts) {
        Object obj = LocatorManager.invokeService(service, "saveDeptSort", new Object[]{parent_code, childdepts});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult recoveryDeptSort(String parent_code, String method) {
        Object obj = LocatorManager.invokeService(service, "recoveryDeptSort", new Object[]{parent_code, method});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult importDept(List saveDepts, String sqls) {
        Object obj = LocatorManager.invokeService(service, "importDept", new Object[]{saveDepts, sqls});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult delDeptChgA01s(List depta01s) {
        Object obj = LocatorManager.invokeService(service, "delDeptChgA01s", new Object[]{depta01s});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult delDeptPhysical(String deptKeyStr, String user_code) {
        Object obj = LocatorManager.invokeService(service, "delDeptPhysical", new Object[]{deptKeyStr, user_code});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult changeDeptFullName(List<String[]> save_dept) {
        Object obj = LocatorManager.invokeService(service, "changeDeptFullName", new Object[]{save_dept});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult transDept(List<DeptCode> depts, String src_code, String dst_code, DeptChgLog logg, Hashtable<String, String> save_codes) {
        Object obj = LocatorManager.invokeService(service, "transDept", new Object[]{depts, src_code, dst_code, logg, save_codes});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult unitDept(List<DeptCode> depts, DeptChgLog logg) {
        Object obj = LocatorManager.invokeService(service, "unitDept", new Object[]{depts, logg});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult setGrade(int grade, int old_len, int new_len, DeptChgLog dlog) {
        Object obj = LocatorManager.invokeService(service, "setGrade", new Object[]{grade, old_len, new_len, dlog});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult addDeptAppendix(Object dobj) {
        Object obj = LocatorManager.invokeService(service, "addDeptAppendix", new Object[]{dobj});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }
}
