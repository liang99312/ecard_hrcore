/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.Code;
import org.jhrcore.entity.CommMap;
import org.jhrcore.entity.RyChgLog;
import org.jhrcore.entity.base.FieldDef;
import org.jhrcore.entity.right.RoleCode;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.SysService;
import org.jhrcore.serviceproxy.LocatorManager;

/**
 *
 * @author mxliteboss
 */
public class SysImpl {

    private static Logger log = Logger.getLogger(SysImpl.class);
    private static String service = SysService.class.getSimpleName();

    public static ValidateSQLResult updateFun(File file) {
        try {
            byte[] buffer = new byte[(int) file.length()];
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
            input.read(buffer, 0, buffer.length);
            input.close();
            Object obj = LocatorManager.invokeService(service, "updateFun", new Object[]{buffer});
            if (obj != null) {
                return (ValidateSQLResult) obj;
            }
        } catch (Exception ex) {
            log.error(ex);
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult uploadFile(File file, String fileName) {
        try {
            byte[] buffer = new byte[(int) file.length()];
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
            input.read(buffer, 0, buffer.length);
            input.close();
            Object obj = LocatorManager.invokeService(service, "uploadFile", new Object[]{buffer, fileName});
            if (obj != null) {
                return (ValidateSQLResult) obj;
            }
        } catch (Exception ex) {
            log.error(ex);
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult closeService() {
        Object obj = LocatorManager.invokeService(service, "closeService", new Object[]{});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult updateVersion() {
        Object obj = LocatorManager.invokeService(service, "updateVersion", new Object[]{});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static List fetchBackSchemes() {
        Object obj = LocatorManager.invokeService(service, "fetchBackSchemes", new Object[]{});
        if (obj != null) {
            return (List) obj;
        }
        return new ArrayList();
    }

    public static List getBackVersionByCode(String moduleCode) {
        Object obj = LocatorManager.invokeService(service, "getBackVersionByCode", new Object[]{moduleCode});
        if (obj != null) {
            return (List) obj;
        }
        return new ArrayList();
    }

    public static List getBackData(String verNo, String quickStr, String qsStr) {
        Object obj = LocatorManager.invokeService(service, "getBackData", new Object[]{verNo, quickStr, qsStr});
        if (obj != null) {
            return (List) obj;
        }
        return new ArrayList();
    }

    public static ValidateSQLResult backData(String moduleCode, String where) {
        Object obj = LocatorManager.invokeService(service, "backData", new Object[]{moduleCode, where});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult importRes(List langList, List baseList, List resList) {
        Object obj = LocatorManager.invokeService(service, "importRes", new Object[]{langList, baseList, resList});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult updateBaseToRes() {
        Object obj = LocatorManager.invokeService(service, "updateBaseToRes", new Object[]{});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult updateLocalRes() {
        Object obj = LocatorManager.invokeService(service, "updateLocalRes", new Object[]{});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult changeCodeGrade(List<String[]> list) {
        Object obj = LocatorManager.invokeService(service, "changeCodeGrade", new Object[]{list});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult buildCodePYM(List<String[]> codes) {
        Object obj = LocatorManager.invokeService(service, "buildCodePYM", new Object[]{codes});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveCode(Code code, RoleCode rc) {
        Object obj = LocatorManager.invokeService(service, "saveCode", new Object[]{code, rc});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveLogField(List list) {
        Object obj = LocatorManager.invokeService(service, "saveLogField", new Object[]{list});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveFieldFormat(List<FieldDef> fds) {
        Object obj = LocatorManager.invokeService(service, "saveFieldFormat", new Object[]{fds});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult delSystemField(Object dobj) {
        Object obj = LocatorManager.invokeService(service, "delSystemField", new Object[]{dobj});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveSystemChange(List saveList, List updateList, List<String> delKeys) {
        Object obj = LocatorManager.invokeService(service, "saveSystemChange", new Object[]{saveList, updateList, delKeys});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult buildFieldPYM(String entity_key) {
        Object obj = LocatorManager.invokeService(service, "buildFieldPYM", new Object[]{entity_key});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult upNotice(RyChgLog rcl, String systemKey, String schemeKey, List<String> noticeKeys) {
        Object obj = LocatorManager.invokeService(service, "upNotice", new Object[]{rcl, systemKey, schemeKey, noticeKeys});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static HashMap getSchemeNum(List<String> list_scheme_table, String where_sql) {
        Object obj = LocatorManager.invokeService(service, "getSchemeNum", new Object[]{list_scheme_table, where_sql});
        if (obj != null) {
            return (HashMap) obj;
        }
        return new HashMap();
    }

    public static void saveUserLog(Object li) {
        LocatorManager.invokeService(service, "saveUserLog", new Object[]{li});
    }

    public static ValidateSQLResult updateCode(Code code) {
        Object obj = LocatorManager.invokeService(service, "updateCode", new Object[]{code});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult delCode(Code code, int grades) {
        Object obj = LocatorManager.invokeService(service, "delCode", new Object[]{code, grades});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveFieldRegula(Object dobj) {
        Object obj = LocatorManager.invokeService(service, "saveFieldRegula", new Object[]{dobj});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveUsers(List<String> keys, CommMap comm) {
        Object obj = LocatorManager.invokeService(service, "saveUsers", new Object[]{keys, comm});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }
}
