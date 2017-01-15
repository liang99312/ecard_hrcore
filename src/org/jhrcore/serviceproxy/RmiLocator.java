package org.jhrcore.serviceproxy;

import java.lang.reflect.Method;
import java.rmi.Naming;

import org.apache.log4j.Logger;
import org.jhrcore.client.UserContext;
import org.jhrcore.comm.ConfigManager;

public class RmiLocator implements ILocator {

    public static String rmiPort = "1299";
    private static final Logger log = Logger.getLogger(RmiLocator.class);
    private static String serverName = "127.0.0.1";

    public static void setServerName(String serverName) {
        RmiLocator.serverName = serverName;
    }

    public Object lookupServiceOf(String serviceName) {
        return lookupServiceOf(serverName, serviceName);

    }

    public Object lookupControlCenterServiceOf(String serviceName) {
        return lookupServiceOf(ConfigManager.getConfigManager().getProperty("server.ControlCenter"), serviceName);
    }

    public Object lookupServiceOf(String serverName, String serviceName) {
        UserContext.last_oper_time = System.currentTimeMillis();
        try {
            return Naming.lookup("//" + serverName + ":" + rmiPort + "/" + serviceName);
        } catch (Exception e) {
            log.error(serverName + ":" + rmiPort + ":" + serviceName + " 服务调用失败\n" + e);
        }
        return null;
    }

    @Override
    public void setServicePara(String ip, String port) {
        if (port == null || port.trim().length() == 0) {
            port = ConfigManager.getConfigManager().getProperty("base.ApplicationServerPort");
        }
        if (port != null && port.trim().length() > 0) {
            RmiLocator.rmiPort = port;
        }
        if (ip == null || ip.trim().length() == 0) {
            ip = ConfigManager.getConfigManager().getProperty("base.ApplicationServer");
        }
        if (ip != null && ip.trim().length() > 0) {
            RmiLocator.serverName = ip;
        }
        ConfigManager.getConfigManager().setProperty("base.ApplicationServer", RmiLocator.serverName);
        ConfigManager.getConfigManager().setProperty("base.ApplicationServerPort", RmiLocator.rmiPort);
        ConfigManager.getConfigManager().save2();
    }

    @Override
    public Object invokeCurService(String serviceName, String methodName, Object[] params) {
        long time1 = System.currentTimeMillis();
        long time2 = time1;
        try {
            Object ins = lookupServiceOf(serviceName);
            if (ins == null) {
                return null;
            }
            Class aclass = ins.getClass();
            Method[] methods = aclass.getMethods();
            Method amethod = null;
            int pLen = params.length;
            for (Method mthd : methods) {
                if (mthd.getName().equals(methodName)) {
                    Class<?>[] cs = mthd.getParameterTypes();
                    if (cs.length != pLen) {
                        continue;
                    }
                    amethod = mthd;
                    break;
                }
            }
            if (amethod == null) {
                return null;
            }
            Object obj = amethod.invoke(ins, params);
            time2 = System.currentTimeMillis();
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex);
        } finally {
//            final long time = (time2 - time1) / 1000;
//            if (time > 10 && !"logEvent".equals(methodName)) {
//                String str = serviceName + " " + methodName + " "+UserContext.getCurPerson()+" ";
//                for (Object obj : params) {
//                    if (obj == null) {
//                        continue;
//                    }
//                    if (obj instanceof String || obj instanceof Integer || obj instanceof Boolean) {
//                        str += obj.toString().length() > 100 ? obj.toString().substring(0, 100) : obj.toString() + " ";
//                    } else if (obj instanceof java.util.Date) {
//                        str += DateUtil.DateToStr((java.util.Date) obj);
//                    } else if (obj.getClass().getName().startsWith("org.jhrcore.entity")) {
//                        try {
//                            str += obj.getClass().getName() + "_Key:" + PublicUtil.getProperty(obj, EntityBuilder.getEntityKey(obj.getClass()));
//                        } catch (Exception ex) {
//                        }
//                    }
//                }
//                final String logStr = str;
//                CommThreadPool.getClientThreadPool().handleEvent(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        CommImpl.logEvent((int) time, logStr);
//                    }
//                });
//            }
        }
        return null;
    }
}
