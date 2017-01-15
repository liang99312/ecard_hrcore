/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.AutoNoRule;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.RyChgLog;
import org.jhrcore.entity.SysParameter;
import org.jhrcore.entity.VirtualDeptPersonLog;
import org.jhrcore.entity.change.ChangeScheme;
import org.jhrcore.entity.query.QueryScheme;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.PersonService;
import org.jhrcore.serviceproxy.LocatorManager;

/**
 *
 * @author hflj
 */
public class RSImpl {

    private static String service = PersonService.class.getSimpleName();

    public static List getDocumentPerson(List<String> depts, Set<String> a0190s, String person_key) {
        List result = new ArrayList();
        Object obj = LocatorManager.invokeService(service, "getDocumentPerson", new Object[]{depts, a0190s, person_key});
        if (obj != null) {
            return (List) obj;
        }
        return result;
    }
    
    public static void deleteFile(String path) {
        LocatorManager.invokeService(service, "deleteFile", new Object[]{path});
    }

    public static ValidateSQLResult uploadFile(byte[] buffer, String file_path) {
        Object obj = LocatorManager.invokeService(service, "uploadFile", new Object[]{buffer, file_path});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveAnnexCheck(List entityList) {
        Object obj = LocatorManager.invokeService(service, "saveAnnexCheck", new Object[]{entityList});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult delAppendix(String tableName, List<String[]> aKeys) {
        Object obj = LocatorManager.invokeService(service, "delAppendix", new Object[]{tableName, aKeys});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult updateAppendix(String tableName, List<String[]> aKeys) {
        Object obj = LocatorManager.invokeService(service, "updateAppendix", new Object[]{tableName, aKeys});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult addAppendix(Object object) {
        Object obj = LocatorManager.invokeService(service, "addAppendix", new Object[]{object});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static void changePersonG10(List<String[]> a01s) {
        Object obj = LocatorManager.invokeService(service, "changePersonG10", new Object[]{a01s});
        if (obj != null) {
            return;
        }
    }

    public static ValidateSQLResult recoveryChanges(List recList, String change_style) {
        Object obj = LocatorManager.invokeService(service, "recoveryChanges", new Object[]{recList, change_style});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult recoveryChange(Object object) {
        Object obj = LocatorManager.invokeService(service, "recoveryChange", new Object[]{object});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult delChangeScheme(List<ChangeScheme> schemes) {
        Object obj = LocatorManager.invokeService(service, "delChangeScheme", new Object[]{schemes});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveEmpNoRule(AutoNoRule anr, String old_key) {
        Object obj = LocatorManager.invokeService(service, "saveEmpNoRule", new Object[]{anr, old_key});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult cancelRegisterFlow(List<String> cKeys) {
        Object obj = LocatorManager.invokeService(service, "cancelRegisterFlow", new Object[]{cKeys});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult delNewPerson(List<String> dKeys) {
        Object obj = LocatorManager.invokeService(service, "delNewPerson", new Object[]{dKeys});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult addPersonForNoCheck(List<String> list, RyChgLog logModel) {
        Object obj = LocatorManager.invokeService(service, "addPersonForNoCheck", new Object[]{list, logModel});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult UpdateA01s(List a01s) {
        Object obj = LocatorManager.invokeService(service, "UpdateA01s", new Object[]{a01s});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }


    public static ValidateSQLResult addAppendixs(List apppendixs) {
        Object obj = LocatorManager.invokeService(service, "addAppendixs", new Object[]{apppendixs});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult changePersonNo(List<String[]> nos, String no_field) {
        Object obj = LocatorManager.invokeService(service, "changePersonNo", new Object[]{nos, no_field});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static Hashtable<String, DeptCode> getEmpJdDept(String a01KeyStr) {
        Object obj = LocatorManager.invokeService(service, "getEmpJdDept", new Object[]{a01KeyStr});
        if (obj != null) {
            return (Hashtable<String, DeptCode>) obj;
        }
        return new Hashtable<String, DeptCode>();
    }

    public static ValidateSQLResult saveChangeScheme(ChangeScheme changeScheme, QueryScheme queryScheme, boolean tableUpdate, String roleKey) {
        Object obj = LocatorManager.invokeService(service, "saveChangeScheme", new Object[]{changeScheme, queryScheme, tableUpdate, roleKey});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static byte[] downloadFile(String pic_path, List<String> list) {
        Object obj = LocatorManager.invokeService(service, "downloadFile", new Object[]{pic_path, list});
        if (obj != null) {
            return (byte[]) obj;
        }
        return null;
    }

    public static byte[] downloadPicture(String pic_path) {
        Object obj = LocatorManager.invokeService(service, "downloadPicture", new Object[]{pic_path});
        if (obj != null) {
            return (byte[]) obj;
        }
        return null;
    }

    public static ValidateSQLResult delPersonFromLog(List<String> log_keys, RyChgLog rcl) {
        Object obj = LocatorManager.invokeService(service, "delPersonFromLog", new Object[]{log_keys, rcl});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult outVirtualDept(List<String> a01Keys, VirtualDeptPersonLog modelLog) {
        Object obj = LocatorManager.invokeService(service, "outVirtualDept", new Object[]{a01Keys, modelLog});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult createPersonPYM(int type, boolean isAll, String s_where, List<String> a01_keys) {
        Object obj = LocatorManager.invokeService(service, "createPersonPYM", new Object[]{type, isAll, s_where, a01_keys});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult setEmpDocuFilePath(String path, SysParameter sp) {
        Object obj = LocatorManager.invokeService(service, "setEmpDocuFilePath", new Object[]{path, sp});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult deleteEmpDocu(List<String> edKeys) {
        Object obj = LocatorManager.invokeService(service, "deleteEmpDocu", new Object[]{edKeys});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveRegisterDesign(List paras, List showSchemes, List depts) {
        Object obj = LocatorManager.invokeService(service, "saveRegisterDesign", new Object[]{paras, showSchemes, depts});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveEmpReigster(Object a01, List appendix, RyChgLog rcl, String entityName) {
        Object obj = LocatorManager.invokeService(service, "saveEmpReigster", new Object[]{a01, appendix, rcl, entityName});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static boolean isNullFloder(String f_path) {
        Object obj = LocatorManager.invokeService(service, "isNullFloder", new Object[]{f_path});
        if (obj != null) {
            return (Boolean) obj;
        }
        return false;
    }

    public static void deletePicture(String pic_path) {
        LocatorManager.invokeService(service, "deletePicture", new Object[]{pic_path});
    }

    public static void uploadPicture(byte[] p_byte, String pic_path) {
        LocatorManager.invokeService(service, "uploadPicture", new Object[]{p_byte, pic_path});
    }
}
