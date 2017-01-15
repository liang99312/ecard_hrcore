/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.comm;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import org.apache.log4j.Logger;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.base.LogData;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.right.FuntionRight;
import org.jhrcore.iservice.impl.SysImpl;
import org.jhrcore.mutil.LogDataUtil;
import org.jhrcore.rebuild.EntityBuilder;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.util.UtilTool;

/**
 *
 * @author hflj
 */
public class HrLog {

    public final static String TypeInfo = "$";//功能操作
    public final static String TypeInsert = "$1";//新增记录
    public final static String TypeUpdate = "$2";//变更记录
    public final static String TypeDel = "$3";//删除记录
    public final static String TypeEmpty = "";//不记录数据库
    public final static String ActionSelect = "选择";//选择
    public final static String ActionPoint = "点击";//点击
    public final static String ActionInfo = "提示";//提示
    public final static String ActionSelected = "勾选";//勾选
    public final static String MsgSuccess = "提示:更新成功";//更新成功
    public final static String MsgError = "提示:更新失败，原因";//更新失败
    private Logger log = null;
    private static Hashtable<String, List<String>> logFieldKeys = new Hashtable();
    private static Hashtable<String, String> logidKeys = new Hashtable();

    public static void error(Class c, Exception e) {
        Logger.getLogger(c.getName()).error(e.getMessage());
    }

    public static void error(Class c, String msg) {
        Logger.getLogger(c.getName()).error(msg);
    }

    public static void error(String logName, Exception e) {
        Logger.getLogger(logName).error(e.getMessage());
    }

    public static void error(String logName, String msg) {
        Logger.getLogger(logName).error(msg);
    }

    public HrLog(String name) {
        if (name == null || name.trim().equals("")) {
            name = "Sys";
        }
        List<String> list = Arrays.asList(name.split("\\."));
        String moduleVar = list.get(0);
        String moduleName = name;
        int len = moduleVar.length();
        FuntionRight module_fr = UserContext.getFuntion_keys().get(moduleVar);
        if (module_fr != null) {
            moduleName = "";
            for (int i = 0; i < len; i++) {
                FuntionRight fr = UserContext.getFuntion_keys().get(moduleVar.substring(0, i + 1));
                if (fr == null) {
                    continue;
                }
                if (module_fr.getFun_parent_code().startsWith(fr.getFun_code())) {
                    moduleName += "." + fr.getFun_name();
                }
            }
            for (String var : list) {
                String text = UserContext.getFuntionName(moduleVar);
                moduleVar = moduleVar + "." + var;
                if (text.equals("")) {
                    moduleName = moduleName + "." + var;
                    continue;
                }
                moduleName = moduleName + "." + text;
            }
            if (moduleName.equals("")) {
                moduleName = name;
            } else {
                moduleName = moduleName.substring(1);
            }
        }
        log = Logger.getLogger(moduleName);
    }

    public static void infoData(final Object obj, final String field, final String beforestate, final String afterstate, final String type) {
        CommThreadPool.getClientThreadPool().handleEvent(new Runnable() {

            @Override
            public void run() {
                try {
                    if ("-1".equals(UserContext.person_key) && !UserContext.isSA) {
                        return;
                    }
                    if (field == null || type == null) {
                        return;
                    }
                    if (!type.startsWith("$")) {
                        return;
                    }
                    try {
                        if (logFieldKeys.isEmpty()) {
                            Hashtable<String, Object> ht = LogDataUtil.getLogFields();
                            for (String key : ht.keySet()) {
                                if (key.startsWith("field")) {
                                    logFieldKeys.put(key.substring(6), (List) ht.get(key));
                                } else {
                                    logidKeys.put(key.substring(3), (String) ht.get(key));
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    final LogData li = (LogData) UtilTool.createUIDEntity(LogData.class);
                    String entityName = obj.getClass().getSimpleName();
                    List<String> logFields = logFieldKeys.get(entityName);
                    if (logFields == null) {
                        if (obj.getClass().getSuperclass().getName().startsWith("org.jhrcore.entity")) {
                            entityName = obj.getClass().getSuperclass().getSimpleName();
                            logFields = logFieldKeys.get(entityName);
                        }
                    }
                    if (logFields == null || !logFields.contains(SysUtil.tranField(field))) {
                        return;
                    }
                    String id = logidKeys.get(entityName);
                    li.setLogTable(entityName);
                    li.setLogColumn(field);
                    li.setLogEntity(entityName);
                    li.setLogField(field);
                    if (id == null || id.trim().equals("")) {
                        li.setLogName(obj.toString());
                    } else {
                        List<TempFieldInfo> infos = EntityBuilder.getCommFieldInfoListOf(obj.getClass(), EntityBuilder.COMM_FIELD_VISIBLE);
                        for (TempFieldInfo tfi : infos) {
                            if (id.contains("@" + tfi.getField_name())) {
                                id = id.replace("@" + tfi.getField_name(), (String) PublicUtil.getProperty(obj, tfi.getField_name()));
                            }
                        }
                        li.setLogName(id);
                    }
                    li.setData_key(PublicUtil.getProperty(obj, EntityBuilder.getEntityKey(obj.getClass())) + "");
                    TempFieldInfo tfi = EntityBuilder.getTempFieldInfoByName(entityName, field, false);
                    if (tfi != null) {
                        li.setLogEntity(tfi.getEntity_caption());
                        li.setLogField(tfi.getCaption_name());
                        li.setLogTable(tfi.getEntity_name());
                        li.setLogColumn(tfi.getField_name());
                    }
                    String match = type.substring(1);
                    if (match.equals("1")) {
                        li.setLogType("新增记录");
                    } else if (match.equals("2")) {
                        li.setLogType("变更记录");
                    } else if (match.equals("3")) {
                        li.setLogType("删除记录");
                    }
                    li.setBeforestate(beforestate);
                    li.setAfterstate(afterstate);
                    li.setLogIp(UserContext.getPerson_ip());
                    li.setLogMac(UserContext.getPerson_mac());
                    li.setPerson_code(UserContext.person_code);
                    li.setPerson_key(UserContext.person_key);
                    li.setPerson_name(UserContext.person_name);
                    SysImpl.saveUserLog(li);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void info(String msg) {
        if (log != null) {
            log.info(TypeInfo + " " + ActionInfo + " " + msg);
        }
    }

    public void info(ActionEvent e) {
        if (log != null && e != null) {
            log.info(TypeInfo + " " + ActionPoint + " " + e.getActionCommand());
        }
    }

    public void info(EventObject e) {
        if (log != null && e != null) {
            Object obj = e.getSource();
            if (obj == null) {
                return;
            }
            String msg = "";
            if (obj instanceof JList) {
                msg = (((JList) obj).getSelectedValue()) == null ? "" : ((JList) obj).getSelectedValue().toString();
            } else if (obj instanceof JTabbedPane) {
                msg = (((JTabbedPane) obj).getSelectedComponent()) == null ? "" : ((JTabbedPane) obj).getTitleAt(((JTabbedPane) obj).getSelectedIndex());
            }
            if (!msg.equals("")) {
                log.info(TypeInfo + " " + ActionSelect + " ");
            }
        }
    }

    public void error(Exception e) {
        if (log != null && e != null) {
            log.error(e);
        }
    }
}
