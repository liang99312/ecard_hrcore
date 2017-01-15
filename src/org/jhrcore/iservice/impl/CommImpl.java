/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.AutoNoRule;
import org.jhrcore.entity.ExportScheme;
import org.jhrcore.entity.FormulaScheme;
import org.jhrcore.entity.base.LoginUser;
import org.jhrcore.entity.query.CommAnalyseScheme;
import org.jhrcore.entity.query.QueryAnalysisScheme;
import org.jhrcore.entity.query.QueryPart;
import org.jhrcore.entity.query.QueryScheme;
import org.jhrcore.entity.right.FuntionRight;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.entity.showstyle.ShowScheme;
import org.jhrcore.iservice.CommService;
import org.jhrcore.serviceproxy.LocatorManager;

/**
 *
 * @author mxliteboss
 */
public class CommImpl {

    private static String service = CommService.class.getSimpleName();

    public static ValidateSQLResult saveAnalyseScheme(CommAnalyseScheme as, QueryScheme qs, QueryPart part) {
        Object obj = LocatorManager.invokeService(service, "saveAnalyseScheme", new Object[]{as, qs, part});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static byte[] downloadFile(String ab_path) {
        Object obj = LocatorManager.invokeService(service, "downloadFile", new Object[]{ab_path});
        if (obj != null) {
            return (byte[]) obj;
        }
        return null;
    }

    public static ValidateSQLResult saveColumnSumScheme(String code, String user_code, List cols) {
        Object obj = LocatorManager.invokeService(service, "saveColumnSumScheme", new Object[]{code, user_code, cols});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static String getServerIp() {
        Object obj = LocatorManager.invokeService(service, "getServerIp", new Object[]{});
        if (obj != null) {
            return (String) obj;
        }
        return "unknown";
    }

    //web服务器的Ip
    public static String getWebServerIp() {
        Object obj = LocatorManager.invokeService(service, "getWebServerIp", new Object[]{});
        if (obj != null) {
            return (String) obj;
        }
        return getServerIp();
    }

    //web服务器的端口
    public static String getWebServerPort() {
        Object obj = LocatorManager.invokeService(service, "getWebServerPort", new Object[]{});
        if (obj != null) {
            return (String) obj;
        }
        return "8080";
    }

    //获得数据库方言
    public static String getSQL_dialect() {
        Object obj = LocatorManager.invokeService(service, "getSQL_dialect", new Object[]{});
        if (obj != null) {
            return (String) obj;
        }
        return "org.hibernate.dialect.SQLServerDialect";
    }

    public static Date getServerDate() {
        Object obj = LocatorManager.invokeService(service, "getServerDate", new Object[]{});
        if (obj != null) {
            return (Date) obj;
        }
        return new Date();
    }

    // 根据自动编号规则编码获取最新的号码, b_inc表示在获取最新号码的时候的增长值，0表示不增长
    public static String fetchNewNoBy(String autoNoRule_key, int b_inc, Hashtable<String, String> params) {
        Object obj = LocatorManager.invokeService(service, "fetchNewNoBy", new Object[]{autoNoRule_key, b_inc, params});
        if (obj != null) {
            return (String) obj;
        }
        return "";
    }
    
    public static List fetchNewNoByAndAuto(String autoNoRule_key, int b_inc, Hashtable<String, String> params,Map<String,Integer> map) {
        Object obj = LocatorManager.invokeService(service, "fetchNewNoByAndAuto", new Object[]{autoNoRule_key, b_inc, params,map});
        if (obj != null) {
            return (List) obj;
        }
        return new ArrayList();
    }
    
// 根据自动编号规则编码获取最新的号码, b_inc表示在获取最新号码的时候的增长值，0表示不增长

    public static String fetchNewNoBy(AutoNoRule anr, Hashtable<String, String> params) {
        Object obj = LocatorManager.invokeService(service, "fetchNewNoBy", new Object[]{anr, params});
        if (obj != null) {
            return (String) obj;
        }
        return null;
    }

    public static Properties getSysProperties() {
        Object obj = LocatorManager.invokeService(service, "getSysProperties", new Object[]{});
        if (obj != null) {
            return (Properties) obj;
        }
        return new Properties();
    }

    public static void uploadFileByStream(InputStream ips, String dest) {
        LocatorManager.invokeService(service, "uploadFileByStream", new Object[]{ips, dest});
    }

    public static void uploadFile(byte[] p_byte, String path) {
        LocatorManager.invokeService(service, "uploadFile", new Object[]{p_byte, path});
    }

    public static boolean deleteFiles(List<String> list_paths) {
        Object obj = LocatorManager.invokeService(service, "deleteFiles", new Object[]{list_paths});
        if (obj != null) {
            return (Boolean) obj;
        }
        return false;
    }

    public static ValidateSQLResult saveQueryScheme(QueryScheme qs) {
        Object obj = LocatorManager.invokeService(service, "saveQueryScheme", new Object[]{qs});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveExportScheme(ExportScheme es) {
        Object obj = LocatorManager.invokeService(service, "saveExportScheme", new Object[]{es});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult connectServer(String type, String user_code) {
        Object obj = LocatorManager.invokeService(service, "connectServer", new Object[]{type, user_code});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult disconnect(List<Object> users, String msg) {
        Object obj = LocatorManager.invokeService(service, "disconnect", new Object[]{users, msg});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult sendMsg(List<Object> users, String msg) {
        Object obj = LocatorManager.invokeService(service, "sendMsg", new Object[]{users, msg});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static List<LoginUser> getLoginUsers(Object cur_dept, String dept_right_sql) {
        Object obj = LocatorManager.invokeService(service, "getLoginUsers", new Object[]{cur_dept, dept_right_sql});
        if (obj != null) {
            return (List<LoginUser>) obj;
        }
        return new ArrayList();
    }

    public static List<String> getSysModules() {
        Object obj = LocatorManager.invokeService(service, "getSysModules", new Object[]{});
        if (obj != null) {
            return (List<String>) obj;
        }
        return new ArrayList();
    }

    public static String[] getSA() {
        Object obj = LocatorManager.invokeService(service, "getSA", new Object[]{});
        if (obj != null) {
            return (String[]) obj;
        }
        return new String[]{"sa", "sa123"};
    }

    public static ValidateSQLResult saveShowSchemeGroup(List new_groups, String user_code, String module_code) {
        Object obj = LocatorManager.invokeService(service, "saveShowSchemeGroup", new Object[]{new_groups, user_code, module_code});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveShowScheme(ShowScheme ss, String code) {
        Object obj = LocatorManager.invokeService(service, "saveShowScheme", new Object[]{ss, code});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult delFormulaDetail(String formula_key, List<String> detail_keys) {
        Object obj = LocatorManager.invokeService(service, "delFormulaDetail", new Object[]{formula_key, detail_keys});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveFormulaDetail(Set details) {
        Object obj = LocatorManager.invokeService(service, "saveFormulaDetail", new Object[]{details});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveQueryAnalysisScheme(QueryAnalysisScheme as, QueryScheme qs) {
        Object obj = LocatorManager.invokeService(service, "saveQueryAnalysisScheme", new Object[]{as, qs});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveQueryPart(QueryPart qp) {
        Object obj = LocatorManager.invokeService(service, "saveQueryPart", new Object[]{qp});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveQueryExtraField(List objs, List existKeys) {
        Object obj = LocatorManager.invokeService(service, "saveQueryExtraField", new Object[]{objs, existKeys});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveExtraFieldOrder(List<String[]> orders) {
        Object obj = LocatorManager.invokeService(service, "saveExtraFieldOrder", new Object[]{orders});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult updateFunRights(List<FuntionRight> rights) {
        Object obj = LocatorManager.invokeService(service, "updateFunRights", new Object[]{rights});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult delShowScheme(ShowScheme es) {
        Object obj = LocatorManager.invokeService(service, "delShowScheme", new Object[]{es});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static String getServer_file_path() {
        Object obj = LocatorManager.invokeService(service, "getServer_file_path", new Object[]{});
        if (obj != null) {
            return obj.toString();
        }
        return "";
    }

    public static ValidateSQLResult saveParameters(List list) {
        Object obj = LocatorManager.invokeService(service, "saveParameters", new Object[]{list});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static Hashtable<String, String> getSecuryInfo(boolean fetchVer) {
        Object obj = LocatorManager.invokeService(service, "getSecuryInfo", new Object[]{fetchVer});
        if (obj != null) {
            return (Hashtable<String, String>) obj;
        }
        return new Hashtable<String, String>();
    }

    public static Object login(String person_code, String pswd) {
        Object obj = LocatorManager.invokeService(service, "login", new Object[]{person_code, pswd});
        if (obj != null) {
            return obj;
        }
        return "-1";
    }

    public static long getServerStartTime() {
        Object obj = LocatorManager.invokeService(service, "getServerStartTime", new Object[]{});
        if (obj != null) {
            return (Long) obj;
        }
        return -1;
    }

    public static void logEvent(int time, String logStr) {
        LocatorManager.invokeService(service, "logEvent", new Object[]{time, logStr});
    }

    public static List getSysCodes() {
        Object obj = LocatorManager.invokeService(service, "getSysCodes", new Object[]{});
        if (obj != null) {
            return (List) obj;
        }
        return new ArrayList();
    }

    public static List getSysModule(boolean fetchClass, boolean fetchEntity, boolean fetchField) {
        Object obj = LocatorManager.invokeService(service, "getSysModule", new Object[]{fetchClass, fetchEntity, fetchField});
        if (obj != null) {
            return (List) obj;
        }
        return new ArrayList();
    }

    public static List getSysTrigerField(String entityNames, boolean isTrigerManager) {
        Object obj = LocatorManager.invokeService(service, "getSysTrigerField", new Object[]{entityNames, isTrigerManager});
        if (obj != null) {
            return (List) obj;
        }
        return new ArrayList();
    }
}
