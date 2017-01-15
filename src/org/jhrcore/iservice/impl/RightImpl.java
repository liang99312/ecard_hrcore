/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice.impl;

import java.util.ArrayList;
import java.util.List;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.right.RoleCode;
import org.jhrcore.entity.right.RoleEntity;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.RightService;
import org.jhrcore.serviceproxy.LocatorManager;

/**
 *
 * @author mxliteboss
 */
public class RightImpl {

    private static String service = RightService.class.getSimpleName();

    public static ValidateSQLResult addUser(List<String[]> users) {
        Object obj = LocatorManager.invokeService(service, "addUser", new Object[]{users});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult delUser(String userKeys, String roleKey) {
        Object obj = LocatorManager.invokeService(service, "delUser", new Object[]{userKeys, roleKey});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult defineDeptRight(String dept_code, int mod, String user_key, boolean isSA, String g_rolea01_key) {
        Object obj = LocatorManager.invokeService(service, "defineDeptRight", new Object[]{dept_code, mod, user_key, isSA, g_rolea01_key});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult defineEntityRight(String p_role_key, List<String> roleKeys, List<RoleEntity> entityKeys, int mod) {
        Object obj = LocatorManager.invokeService(service, "defineEntityRight", new Object[]{p_role_key, roleKeys, entityKeys, mod});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult defineCodeRight(String p_role_key, List<String> roleKeys, List<RoleCode> codeKeys, int mod) {
        Object obj = LocatorManager.invokeService(service, "defineCodeRight", new Object[]{p_role_key, roleKeys, codeKeys, mod});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult copyRight(String srcRoleKey, String dstRoleKey, String code) {
        Object obj = LocatorManager.invokeService(service, "copyRight", new Object[]{srcRoleKey, dstRoleKey, code});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult defineFuntionRight(List<String[]> data) {
        Object obj = LocatorManager.invokeService(service, "defineFuntionRight", new Object[]{data});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult defineFieldRight(List<String[]> data) {
        Object obj = LocatorManager.invokeService(service, "defineFieldRight", new Object[]{data});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult defineReportRight(List<String[]> data) {
        Object obj = LocatorManager.invokeService(service, "defineReportRight", new Object[]{data});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult defineUserRole(List<String[]> data) {
        Object obj = LocatorManager.invokeService(service, "defineUserRole", new Object[]{data});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static List getReportRight(String roleKeys) {
        Object obj = LocatorManager.invokeService(service, "getReportRight", new Object[]{roleKeys});
        if (obj != null) {
            return (List) obj;
        }
        return new ArrayList();
    }

    public static ValidateSQLResult cryptA01PassWord() {
        Object obj = LocatorManager.invokeService(service, "cryptA01PassWord", new Object[]{});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }
}
