/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice.impl;

import java.util.List;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.report.ReportDef;
import org.jhrcore.entity.report.ReportGroup;
import org.jhrcore.entity.report.ReportLog;
import org.jhrcore.entity.report.ReportXlsScheme;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.ReportService;
import org.jhrcore.serviceproxy.LocatorManager;
import org.w3c.dom.Document;

/**
 *
 * @author hflj
 */
public class ReportImpl {

    private static String service = ReportService.class.getSimpleName();

    public static byte[] getReportForDocument(Document doc, String rmiIp) {
        Object obj = LocatorManager.invokeService(service, "getReportForDocument", new Object[]{doc, rmiIp});
        if (obj != null) {
            return (byte[]) obj;
        }
        return null;
    }

    public static byte[] getReport_cpt(String report_def_key) {
        Object obj = LocatorManager.invokeService(service, "getReport_cpt", new Object[]{report_def_key});
        if (obj != null) {
            return (byte[]) obj;
        }
        return null;
    }

    public static byte[] getReport_datasource(String report_def_key, String rmiIp) {
        Object obj = LocatorManager.invokeService(service, "getReport_datasource", new Object[]{report_def_key, rmiIp});
        if (obj != null) {
            return (byte[]) obj;
        }
        return null;
    }

    public static byte[] getBase_datasource(String rmiIp) {
        Object obj = LocatorManager.invokeService(service, "getBase_datasource", new Object[]{rmiIp});
        if (obj != null) {
            return (byte[]) obj;
        }
        return null;
    }

    public static ValidateSQLResult saveReport(ReportDef reportDef, byte[] db_source, byte[] cpt, boolean isNew, String roleKey) {
        Object obj = LocatorManager.invokeService(service, "saveReport", new Object[]{reportDef, db_source, cpt, isNew, roleKey});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult serRole_key(ReportDef reportDef, String roleKey) {
        Object obj = LocatorManager.invokeService(service, "serRole_key", new Object[]{reportDef, roleKey});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult delReport(ReportDef reportDef) {
        Object obj = LocatorManager.invokeService(service, "delReport", new Object[]{reportDef});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult addReportToModule(String module_flag, List<String> reportKeys) {
        Object obj = LocatorManager.invokeService(service, "addReportToModule", new Object[]{module_flag, reportKeys});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult setReportNoUser(List<String> noKeys, ReportLog modLog) {
        Object obj = LocatorManager.invokeService(service, "setReportNoUser", new Object[]{noKeys, modLog});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult delReportNo(List<String> noKeys, ReportLog modLog) {
        Object obj = LocatorManager.invokeService(service, "delReportNo", new Object[]{noKeys, modLog});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult createReportNo(DeptCode dc, ReportGroup rgroup, String ym, List<ReportDef> reports) {
        Object obj = LocatorManager.invokeService(service, "createReportNo", new Object[]{dc, rgroup, ym, reports});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult copyRule(List<String> ruleKeys, List<String> reportKeys) {
        Object obj = LocatorManager.invokeService(service, "copyRule", new Object[]{ruleKeys, reportKeys});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult addReportsToGroup(ReportGroup rgroup, List<String> reportKeys) {
        Object obj = LocatorManager.invokeService(service, "addReportsToGroup", new Object[]{rgroup, reportKeys});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult addDeptsToGroup(ReportGroup rgroup, List<String> deptKeys) {
        Object obj = LocatorManager.invokeService(service, "addDeptsToGroup", new Object[]{rgroup, deptKeys});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult createReportData(List<String> noKeys, ReportLog modLog) {
        Object obj = LocatorManager.invokeService(service, "createReportData", new Object[]{noKeys, modLog});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult updateReportNoState(List<String> noKeys, ReportLog modLog, String old_type, String new_type, String group_key, String ym) {
        Object obj = LocatorManager.invokeService(service, "updateReportNoState", new Object[]{noKeys, modLog, old_type, new_type, group_key, ym});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveXlsScheme(ReportXlsScheme scheme, boolean isNew) {
        Object obj = LocatorManager.invokeService(service, "saveXlsScheme", new Object[]{scheme, isNew});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static int getTabColumn(String mc) {
        Object obj = LocatorManager.invokeService(service, "getTabColumn", new Object[]{mc});
        if (obj != null) {
            return (Integer) obj;
        }
        return 2;
    }

    public static boolean isSecuryed() {
        Object obj = LocatorManager.invokeService(service, "isSecuryed", new Object[]{});
        if (obj != null) {
            return (Boolean) obj;
        }
        return false;
    }

    public static ValidateSQLResult reportCert(String code) {
        Object obj = LocatorManager.invokeService(service, "reportCert", new Object[]{code});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static byte[] getReportSetting(String id) {
        Object obj = LocatorManager.invokeService(service, "getReportSetting", new Object[]{id});
        if (obj != null) {
            return (byte[]) obj;
        }
        return null;
    }

    public static ValidateSQLResult saveReportSetting(String id, byte[] source) {
        Object obj = LocatorManager.invokeService(service, "saveReportSetting", new Object[]{id, source});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }
}
