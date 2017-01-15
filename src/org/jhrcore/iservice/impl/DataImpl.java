/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.DataService;
import org.jhrcore.serviceproxy.LocatorManager;

/**
 *
 * @author mxliteboss
 */
public class DataImpl {

    private static String service = DataService.class.getSimpleName();

    public static HashSet<String> getNot_triger_packages() {
        Object obj = LocatorManager.invokeService(service, "getNot_triger_packages", new Object[]{});
        if (obj != null) {
            return (HashSet<String>) obj;
        }
        return new HashSet();
    }

    public static ValidateSQLResult saveObj(Object object) {
        Object obj = LocatorManager.invokeService(service, "saveObj", new Object[]{object});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult update(Object object, String hql) {
        Object obj = LocatorManager.invokeService(service, "update", new Object[]{object, hql});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult validateTriger(String triger_text, Object bean, Object old_val, Object new_val) {
        Object obj = LocatorManager.invokeService(service, "validateTriger", new Object[]{triger_text, bean, old_val, new_val});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static Object fetchEntityBy(String hql) {
        Object obj = LocatorManager.invokeService(service, "fetchEntityBy", new Object[]{hql});
        if (obj != null) {
            return obj;
        }
        return null;
    }

    public static ArrayList getEntitysBy(String hql) {
        Object obj = LocatorManager.invokeService(service, "getEntitysBy", new Object[]{hql});
        if (obj != null) {
            return (ArrayList) obj;
        }
        return new ArrayList();
    }

    public static ArrayList getEntitysByKey(String hql, List<String> keys, boolean isInt) {
        Object obj = LocatorManager.invokeService(service, "getEntitysByKey", new Object[]{hql, keys, isInt});
        if (obj != null) {
            return (ArrayList) obj;
        }
        return new ArrayList();
    }

    public static ArrayList getEntitysBy(String hql, int pageInd, int count) {
        Object obj = LocatorManager.invokeService(service, "getEntitysBy", new Object[]{hql, pageInd, count});
        if (obj != null) {
            return (ArrayList) obj;
        }
        return new ArrayList();
    }

    public static ArrayList selectSQL(String sql, Boolean fetch_head, Integer max_size) {
        Object obj = LocatorManager.invokeService(service, "selectSQL", new Object[]{sql, fetch_head, max_size});
        if (obj != null) {
            return (ArrayList) obj;
        }
        return new ArrayList();
    }

    public static ArrayList selectSQLByKey(String sql, String sql2, List<String> keys, boolean isInt) {
        Object obj = LocatorManager.invokeService(service, "selectSQLByKey", new Object[]{sql, sql2, keys, isInt});
        if (obj != null) {
            return (ArrayList) obj;
        }
        return new ArrayList();
    }

    public static boolean exists(String hql) {
        Object obj = LocatorManager.invokeService(service, "exists", new Object[]{hql});
        if (obj != null) {
            return (Boolean) obj;
        }
        return true;
    }

    public static List getDb_tables(String t_str) {
        Object obj = LocatorManager.invokeService(service, "getDb_tables", new Object[]{t_str});
        if (obj != null) {
            return (ArrayList) obj;
        }
        return new ArrayList();
    }
    //Ö´ÐÐ

    public static ValidateSQLResult excuteSQLs(List<String> sqls) {
        Object obj = LocatorManager.invokeService(service, "excuteSQLs", new Object[]{sqls});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult excuteSQL(String sql) {
        Object obj = LocatorManager.invokeService(service, "excuteSQL", new Object[]{sql});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult excuteSQLs(String sql, String split_char) {
        Object obj = LocatorManager.invokeService(service, "excuteSQLs", new Object[]{sql, split_char});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult excuteSQLs(String sql, List keys, boolean isInt, String split_char, String extra_char) {
        Object obj = LocatorManager.invokeService(service, "excuteSQLs", new Object[]{sql, keys, isInt, split_char, extra_char});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult excuteHQL(String hql) {
        Object obj = LocatorManager.invokeService(service, "excuteHQL", new Object[]{hql});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult excuteHQLs(String hql, String split_char) {
        Object obj = LocatorManager.invokeService(service, "excuteHQLs", new Object[]{hql, split_char});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult excuteHQLs(String sql, List keys, boolean isInt, String extra_char, String split_char) {
        Object obj = LocatorManager.invokeService(service, "excuteHQLs", new Object[]{sql, keys, isInt, extra_char, split_char});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }
    //É¾³ý

    public static ValidateSQLResult deleteObj(Object o) {
        Object obj = LocatorManager.invokeService(service, "deleteObj", new Object[]{o});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult deleteObjs(String tableName, String fieldName, List<String> keys) {
        Object obj = LocatorManager.invokeService(service, "deleteObjs", new Object[]{tableName, fieldName, keys});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveSet(HashSet set) {
        Object obj = LocatorManager.invokeService(service, "saveSet", new Object[]{set});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveList(List list) {
        Object obj = LocatorManager.invokeService(service, "saveList", new Object[]{list});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }
    //¸üÐÂ

    public static ValidateSQLResult update(Object uobj) {
        Object obj = LocatorManager.invokeService(service, "update", new Object[]{uobj});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult saveOrUpdate(Object dobj) {
        Object obj = LocatorManager.invokeService(service, "saveOrUpdate", new Object[]{dobj});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult excuteSQL_jdbc(String sql) {
        Object obj = LocatorManager.invokeService(service, "excuteSQL_jdbc", new Object[]{sql});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult excuteSQLs_jdbc(String sql, String split) {
        Object obj = LocatorManager.invokeService(service, "excuteSQLs_jdbc", new Object[]{sql, split});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult validateSQL(String sql, boolean update) {
        Object obj = LocatorManager.invokeService(service, "validateSQL", new Object[]{sql, update});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult validateHQL(String sql, boolean update) {
        Object obj = LocatorManager.invokeService(service, "validateHQL", new Object[]{sql, update});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }
}
