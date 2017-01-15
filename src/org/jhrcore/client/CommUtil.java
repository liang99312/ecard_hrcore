package org.jhrcore.client;

import org.jhrcore.util.PublicUtil;
import org.jhrcore.util.DateUtil;
import java.awt.Cursor;
import java.io.File;
import java.rmi.Remote;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.jhrcore.serviceproxy.LocatorManager;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.comm.*;
import org.jhrcore.entity.AutoNoRule;
import org.jhrcore.entity.FileRecord;
import org.jhrcore.entity.base.FieldDef;
import org.jhrcore.rebuild.EntityBuilder;
import org.jhrcore.entity.base.LoginUser;
import org.jhrcore.entity.query.QueryScheme;
import org.jhrcore.iservice.impl.CommImpl;
import org.jhrcore.iservice.impl.DataImpl;
import org.jhrcore.iservice.impl.FileImpl;
import org.jhrcore.iservice.impl.RSImpl;
import org.jhrcore.util.FileUtil;
import org.jhrcore.util.UtilTool;

public class CommUtil {

    private static Logger log = Logger.getLogger(CommUtil.class.getName());
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Hashtable<String, Remote> services = new Hashtable<String, Remote>();
//    private static long serverTime = 0;

    public static Remote getService(String serverName) {
        return getService(serverName, true);
    }

    public static Remote getService(String serverName, boolean showError) {
        long time1 = System.currentTimeMillis();
        Remote remote = services.get(serverName);
        UserContext.last_oper_time = System.currentTimeMillis();
        if (remote == null) {
            remote = (Remote) LocatorManager.getLocatorManager().lookupServiceOf(serverName);
            if (remote == null) {
                if (showError) {
//                    JOptionPane.showMessageDialog(null, "���ݷ������ʧ��");
                }
            } else {
                services.put(serverName, remote);
            }
        } else {
//            SuperService ss = (SuperService) remote;
//            long time = -1;
//            try {
//                time = ss.getServerStartTime();
//            } catch (Exception e) {
//                time = -1;
//                log.error(e);
//            }
//            if(time == 0){
//                JOptionPane.showMessageDialog(null, "HR������δ�����ɹ������Ժ��½!");
//                System.exit(0);
//                return null;
//            }else if(serverTime!=0&&serverTime!=-1&&serverTime!=time){
//                JOptionPane.showMessageDialog(null, "HR�����Ѿ�����������Ϊ��������һ�£������µ�½!");
//                System.exit(0);
//                return null;
//            }
//            serverTime = time;
        }
        long time2 = System.currentTimeMillis();
        if ((time2 - time1) > 100) {
            log.error("Service:" + df.format(new Date()) + " ��ʱ��" + (time2 - time1) + "S");
        }
        return remote;
    }

    public static long getServerStartTime() {
        return CommImpl.getServerStartTime();
    }

    public static Date getServerDate() {
        return CommImpl.getServerDate();
    }
    //hql��ѯ������һ��List

    public static ArrayList<?> fetchEntities(String s) {
//        System.out.println("s:"+s);
        long time1 = System.currentTimeMillis();
        ArrayList list = new ArrayList();
//        DataService us = null;
        try {
            CommThreadPool.getClientThreadPool().handleEvent(new Runnable() {

                @Override
                public void run() {
                    try {
                        if (ContextManager.getMainFrame() != null) {
                            ContextManager.getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            Object obj = LocatorManager.invokeService("DataService", "getEntitysBy", new Object[]{s});
            if (obj != null) {
                list.addAll((List) obj);
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            if (ContextManager.getMainFrame() != null) {
                ContextManager.getMainFrame().setCursor(Cursor.getDefaultCursor());
            }
        }
        long time2 = System.currentTimeMillis();
        if ((time2 - time1) > 100000) {
            log.error(" ��ʱ��" + (time2 - time1) + "ms,SQL:" + s);
        }
        return list;
    }

    public static ArrayList<?> fetchEntities(String s, List<String> keys) {
        return fetchEntities(s, keys, false);
    }

    public static ArrayList<?> fetchEntities(String s, List<String> keys, boolean isInt) {
        long time1 = System.currentTimeMillis();
        try {
            if (ContextManager.getMainFrame() != null) {
                ContextManager.getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }
            return DataImpl.getEntitysByKey(s, keys, isInt);
        } catch (Exception e) {
            log.error(e);
        } finally {
            if (ContextManager.getMainFrame() != null) {
                ContextManager.getMainFrame().setCursor(Cursor.getDefaultCursor());
            }
            long time2 = System.currentTimeMillis();
            if ((time2 - time1) > 100000) {
                log.error(" ��ʱ��" + (time2 - time1) + "ms,SQL:" + s);
            }
        }
        return new ArrayList();
    }
    //Hql��ѯ������һ������

    public static Object fetchEntityBy(String hql) {
        return DataImpl.fetchEntityBy(hql);
    }
    //���ҳ��ѯ
//

    public static ArrayList<?> fetchEntitysBy(String hql, int firstIndex, int count) {
        return DataImpl.getEntitysBy(hql, firstIndex, count);
    }

    /**
     * ִ��һ��Sql��䣬����List��List�ж���ΪObject[]����: String sql = "select
     * person_name,person_code from baseperson "; List list =
     * CommUtil.selectSql(sql); for(Object obj:list){ Object[] value =
     * (Object[])obj; ... }
     */
    public static ArrayList<?> selectSQL(String sql, boolean fetch_head, int max_size) {
        long time1 = System.currentTimeMillis();
        try {
            if (ContextManager.getMainFrame() != null) {
                ContextManager.getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }
            return DataImpl.selectSQL(sql, fetch_head, max_size);
        } catch (Exception e) {
            log.error(e);
        } finally {
            long time2 = System.currentTimeMillis();
            if ((time2 - time1) > 100000) {
                log.error(" ��ʱ��" + (time2 - time1) + "ms,SQL:" + sql);
            }
            if (ContextManager.getMainFrame() != null) {
                ContextManager.getMainFrame().setCursor(Cursor.getDefaultCursor());
            }
        }
        return new ArrayList();
    }

    public static ArrayList<?> selectSQL(String sql, boolean fetch_head) {
        log.error("��ʼʱ�䣺" + DateUtil.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        long time1 = System.currentTimeMillis();
        try {
            if (ContextManager.getMainFrame() != null) {
                ContextManager.getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }
            return DataImpl.selectSQL(sql, fetch_head, 0);
        } catch (Exception e) {
            log.error(e);
        } finally {
            long time2 = System.currentTimeMillis();
            if ((time2 - time1) > 100000) {
                log.error(" ��ʱ��" + (time2 - time1) + "ms,SQL:" + sql);
            }
            if (ContextManager.getMainFrame() != null) {
                ContextManager.getMainFrame().setCursor(Cursor.getDefaultCursor());
            }
        }
        return new ArrayList();
    }

    public static List getDb_tables(String t_str) {
        long time1 = System.currentTimeMillis();
        try {
            if (ContextManager.getMainFrame() != null) {
                ContextManager.getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }
            return DataImpl.getDb_tables(t_str);
        } catch (Exception e) {
            log.error(e);
        } finally {
            long time2 = System.currentTimeMillis();
            if ((time2 - time1) > 100000) {
                log.error(" ��ʱ��" + (time2 - time1) + "ms");
            }
            if (ContextManager.getMainFrame() != null) {
                ContextManager.getMainFrame().setCursor(Cursor.getDefaultCursor());
            }
        }
        return new ArrayList();
    }

    public static ArrayList<?> selectSQL(String sql, List<String> keys) {
        return selectSQL(sql, keys, false);
    }

    public static ArrayList<?> selectSQL(String sql, String sql2, List<String> keys) {
        return selectSQL(sql, sql2, keys, false);
    }

    public static ArrayList<?> selectSQL(String sql, List<String> keys, boolean isInt) {
        return selectSQL(sql, "", keys, isInt);
    }

    public static ArrayList<?> selectSQL(String sql, String sql2, List<String> keys, boolean isInt) {
        try {
            if (ContextManager.getMainFrame() != null) {
                ContextManager.getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }
            return DataImpl.selectSQLByKey(sql, sql2, keys, isInt);
        } catch (Exception e) {
            log.error(e);
        } finally {
            if (ContextManager.getMainFrame() != null) {
                ContextManager.getMainFrame().setCursor(Cursor.getDefaultCursor());
            }
        }
        return new ArrayList();
    }

    public static ArrayList<?> selectSQL(String sql) {
        return selectSQL(sql, false);
    }

    public static ArrayList<?> selectSQL(String sql, int max_size) {
        return selectSQL(sql, false, max_size);
    }

    /**
     * �ж�һ��������Ƿ����,�˷������ǿɿ����ڷ������ʧ��ʱ���ܵ��½����ʵ�ʲ�һ��
     *
     * @param hql
     * @return���жϽ����Ĭ��Ϊtrue
     */
    public static boolean exists(String hql) {
        return DataImpl.exists(hql);
    }

    public static ValidateSQLResult saveOrUpdate(Object obj) {
        try {
            ValidateSQLResult result1 = entity_triger(obj, true);
            if (result1 != null) {
                return result1;
            }
            return DataImpl.saveOrUpdate(obj);
        } catch (Exception e) {
            log.error(e);
        }
        return getServiceErrorResult();
    }
    //ִ��һ��Hql���

    public static ValidateSQLResult excuteHQL(String hql) {
        return DataImpl.excuteHQL(hql);
    }
    //ִ��һ��Hql���

    public static ValidateSQLResult excuteHQLs(String hql, String split_char) {
        return DataImpl.excuteHQLs(hql, split_char);
    }

    public static ValidateSQLResult excuteHQLs(String sql, List<String> keys, String extra_char) {
        return excuteHQLs(sql, keys, false, ";", extra_char);
    }

    public static ValidateSQLResult excuteHQLs(String sql, List<String> keys, String split_char, String extra_char) {
        return excuteHQLs(sql, keys, false, split_char, extra_char);
    }

    public static ValidateSQLResult excuteHQLs(String sql, List<String> keys, boolean isInt, String split_char, String extra_char) {
        return DataImpl.excuteHQLs(sql, keys, isInt, split_char, extra_char);
    }

    public static ValidateSQLResult getServiceErrorResult() {
        ValidateSQLResult result = new ValidateSQLResult();
        result.setResult(1);
        result.setMsg("���ݷ������ʧ��");
        return result;
    }

    public static ValidateSQLResult excuteSQL(String sql) {
        return DataImpl.excuteSQL(sql);
    }

    /**
     * ִ��һ����䣬�����ڸ������ݿ⣬Ҳ������֤SQL����֤ʱ���������1=0��������ִ�й���commit���б仯����ʼ��commit
     *
     * @param sql:Ҫִ�е���䴮
     * @param split_char���ָ���
     * @return��ִ�н����msg�а���������Ϣ �������磺select ...;update...;insert
     * into..;�Զ��Ÿ�����Ȼ��ִ�У�excuteSQLs(sql,";")������ִ�ж����
     */
    public static ValidateSQLResult excuteSQLs(String sql, String split_char) {
        return DataImpl.excuteSQLs(sql, split_char);
    }

    public static ValidateSQLResult excuteSQLs(List<String> sqls) {
        return DataImpl.excuteSQLs(sqls);
    }

    public static ValidateSQLResult excuteSQLs(String sql, List<String> keys) {
        return excuteSQLs(sql, keys, false, ";", "");
    }

    public static ValidateSQLResult excuteSQLs(String sql, List<String> keys, String extra_char) {
        return excuteSQLs(sql, keys, false, ";", extra_char);
    }

    public static ValidateSQLResult excuteSQLs(String sql, List<String> keys, String split_char, String extra_char) {
        return excuteSQLs(sql, keys, false, split_char, extra_char);
    }

    public static ValidateSQLResult excuteSQLs(String sql, List<String> keys, boolean isInt, String split_char, String extra_char) {
        return DataImpl.excuteSQLs(sql, keys, isInt, split_char, extra_char);
    }
    //ɾ��һ������

    public static ValidateSQLResult deleteEntity(Object o) {
        ValidateSQLResult result = DataImpl.deleteObj(o);
        if (result.getResult() == 0) {
            HrLog.infoData(o, null, null, null, "$3");
        }
        return result;
    }

    public static ValidateSQLResult deleteObjs(final String tableName, final String fieldName, final List<String> keys) {
        return DataImpl.deleteObjs(tableName, fieldName, keys);
    }
    //����һ������

    public static ValidateSQLResult updateEntity(Object obj) {
        ValidateSQLResult result1 = entity_triger(obj, false);
        if (result1 != null) {
            return result1;
        }
        return DataImpl.update(obj);
    }

    public static ValidateSQLResult entity_triger(Object e, boolean isNew) {
        return entity_triger(new Hashtable<String, List<FieldDef>>(), e, isNew, null);
    }

    public static ValidateSQLResult entity_triger(Hashtable<String, List<FieldDef>> field_keys, Object e, boolean isNew) {
        return entity_triger(field_keys, e, isNew, null);
    }

    public static ValidateSQLResult entity_triger(Hashtable<String, List<FieldDef>> field_keys, Object e, boolean isNew, Object old_obj) {
        String entityName = e.getClass().getName();
        if (!entityName.startsWith("org.jhrcore.entity")) {
            return null;
        }
        ValidateSQLResult result = new ValidateSQLResult();
        result.setResult(1);
        result.setMsg("����ʧ��");
        HashSet<String> not_triger_packages = DataImpl.getNot_triger_packages();
        for (String packageName : not_triger_packages) {
            if (entityName.startsWith(packageName)) {
                return null;
            }
        }
        List<FieldDef> fieldDefs = field_keys.get(e.getClass().getSimpleName());
        if (fieldDefs == null) {
            Class c = e.getClass();
//            String entityNames = "'" + c.getSimpleName() + "'";
            String entityNames = c.getSimpleName();
            while (!c.getName().endsWith(".Model") && !c.getName().endsWith(".Object")) {
//                entityNames = entityNames + ",'" + c.getSimpleName() + "'";
                entityNames = entityNames + ";" + c.getSimpleName();
                c = c.getSuperclass();
            }
            fieldDefs = (List<FieldDef>) CommImpl.getSysTrigerField(entityNames, false);// CommUtil.fetchEntities("from FieldDef fd where fd.entityDef.entityName in(" + entityNames + ") and (fd.not_null_save_check=1 or fd.regula_save_check=1) order by fd.order_no");
            field_keys.put(e.getClass().getSimpleName(), fieldDefs);
        }
        if (fieldDefs.size() > 0) {
            String key_field = EntityBuilder.getEntityKey(e.getClass());
            Object key_val = PublicUtil.getProperty(e, key_field);
            if (!isNew && (old_obj == null)) {
                old_obj = CommUtil.fetchEntityBy("from " + e.getClass().getSimpleName() + " where " + key_field + "=\'" + (key_val == null ? "-1" : key_val.toString()) + "\'");
            }
            for (Object tmp_obj : fieldDefs) {
                FieldDef fd = (FieldDef) tmp_obj;
                Object new_val = PublicUtil.getProperty(e, fd.getField_name());
                Object old_val = PublicUtil.getProperty(old_obj, fd.getField_name());
                if (fd.isNot_null_save_check()) {
                    if (!FieldTrigerManager.getFieldTrigerManager().validateunotnull(fd.getField_name(), e, old_val, new_val)) {
                        result.setMsg(fd.getField_caption() + "Ϊ�ջ�Ψһ��Υ��ϵͳ���򣬲������棡");
                        return result;
                    }
                }
                if (fd.isRegula_save_check()) {
                    if (!FieldTrigerManager.getFieldTrigerManager().validate(fd.getField_name(), e, old_obj, new_val)) {
                        result.setMsg(fd.getField_caption() + "Υ���������򣬲������棡");
                        return result;
                    }
                }
            }
        }
        return null;
    }
    //����һ������

    public static ValidateSQLResult saveEntity(Object obj) {
        ValidateSQLResult result1 = entity_triger(obj, true);
        if (result1 != null) {
            return result1;
        }
        ValidateSQLResult result = DataImpl.saveObj(obj);
        if (result.getResult() == 0) {
            HrLog.infoData(obj, null, null, null, "$1");
        }
        return result;
    }

    //����һ��HashSet���ڱ������������ʱ�����齫�������HashSet��һ���Ա��棬���������������ʱ��
    public static ValidateSQLResult saveSet(HashSet<?> set) {
        ValidateSQLResult result = null;
        for (Object obj : set) {
            result = entity_triger(obj, true);
            if (result != null) {
                return result;
            }
        }
        return DataImpl.saveSet(set);
    }

    /**
     * ����һ��List���ڱ������������ʱ�����齫�������HashSet��һ���Ա��棬���������������ʱ��
     */
    public static ValidateSQLResult saveList(List set) {
        ValidateSQLResult result = null;
        for (Object obj : set) {
            result = entity_triger(obj, true);
            if (result != null) {
                return result;
            }
        }
        return DataImpl.saveList(set);
    }

    /**
     * �÷������ڻ�ȡHR���ݿⷽ�ԣ��˷��������ɿ����ڷ����ȡʧ��ʱ����ֵ������ʵ��ֵ��һ��
     *
     * @return�����ݿⷽ�ԣ�Ĭ��Ϊsqlserver
     */
    public static String getSQL_dialect() {
        return CommImpl.getSQL_dialect();
    }

    public static String fetchNewNoBy(String autoNoRule_key, int b_inc, Hashtable<String, String> params) {
        return CommImpl.fetchNewNoBy(autoNoRule_key, b_inc, params);
    }
   
    public static List fetchNewNoByAndAotu(String autoNoRule_key, int b_inc, Hashtable<String, String> params,Map<String,Integer> map) {
        return CommImpl.fetchNewNoByAndAuto(autoNoRule_key, b_inc, params,map);
    }
    
    public static String fetchNewNoBy(String autoNoRule_key, int b_inc) {
        return fetchNewNoBy(autoNoRule_key, b_inc, null);
    }

    public static String fetchNewNoBy(AutoNoRule anr, Hashtable<String, String> params) {
        return CommImpl.fetchNewNoBy(anr, params);
    }

    public static String getServerIp() {
        return CommImpl.getServerIp();
    }

    public static ValidateSQLResult validateSQL(String sql) {
        return DataImpl.validateSQL(sql, false);
    }

    public static ValidateSQLResult validateSQL(String sql, boolean update) {
        return DataImpl.validateSQL(sql, update);
    }

    public static ValidateSQLResult validateHQL(String sql, boolean update) {
        return DataImpl.validateHQL(sql, update);
    }

    public static ValidateSQLResult excuteSQL_jdbc(String sql) {
        return DataImpl.excuteSQL_jdbc(sql);
    }

    public static ValidateSQLResult excuteSQLs_jdbc(String sql, String split) {
        return DataImpl.excuteSQLs_jdbc(sql, split);
    }

    public static ValidateSQLResult connectServer(String type, String user_code) {
        return CommImpl.connectServer(type, user_code);
    }

    public static List<String> getSysModules() {
        return CommImpl.getSysModules();
    }

    public static String getWebServerIp() {
        return CommImpl.getWebServerIp();
    }

    public static String getWebServerPort() {
        return CommImpl.getWebServerPort();
    }

    public static List<LoginUser> getLoginUsers(Object cur_dept, String dept_right_sql) {
        return CommImpl.getLoginUsers(cur_dept, dept_right_sql);
    }

    public static ValidateSQLResult saveParameters(List list) {
        return CommImpl.saveParameters(list);
    }

    public static Hashtable<String, String> getSecuryInfo(boolean fetchVer) {
        return CommImpl.getSecuryInfo(fetchVer);
    }

    public static Object login(String person_code, String pswd) {
        return CommImpl.login(person_code, pswd);
    }

    public static ValidateSQLResult saveQueryScheme(QueryScheme qs) {
        return CommImpl.saveQueryScheme(qs);
    }

    public static ValidateSQLResult uploadFile(File file, String path) {
        return RSImpl.uploadFile(FileUtil.readFileToByte(file), path);
    }

    public static ValidateSQLResult uploadNewFile(FileRecord fr) {
        return FileImpl.uploadNewFile(FileUtil.readFileToByte(fr.getFile()), fr);
    }

    public static FileRecord createFileRecord(File file) {
        FileRecord fr = (FileRecord) UtilTool.createUIDEntity(FileRecord.class);
        fr.setFile(file);
        fr.setFile_name(file.getName());
        int ind = file.getName().lastIndexOf(".");
        fr.setFile_type("");
        fr.setFile_path(fr.getFileRecore_key());
        if (ind >= 0) {
            fr.setFile_type(file.getName().substring(ind + 1));
            fr.setFile_path(fr.getFileRecore_key() + "." + fr.getFile_type());
        }
        fr.setC_user(UserContext.person_name);
        fr.setC_userno(UserContext.person_code);
        return fr;
    }
}
