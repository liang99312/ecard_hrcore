/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.serviceproxy;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import org.apache.log4j.Logger;
import org.jhrcore.client.UserContext;
import org.jhrcore.serviceproxy.http.HttpTransport;
import org.jhrcore.comm.ConfigManager;
import org.jhrcore.rebuild.EntityBuilder;
import org.jhrcore.serviceproxy.http.HttpTransportUtil;
import org.jhrcore.util.DateUtil;
import org.jhrcore.util.PublicUtil;

/**
 *
 * @author Administrator
 */
public class HttpLocator implements ILocator {

    private static String serverName = "127.0.0.1";
    private static String port = "8080";
    private static Logger log = Logger.getLogger(HttpLocator.class);

    @Override
    public Object invokeCurService(String serviceName, String methodName, Object[] params) {
        return invokeHttpService(serviceName, methodName, params);
    }

    public static Object invokeHttpService(String serviceName, String methodName, Object[] params) {
        long time1 = System.currentTimeMillis();
        long time2 = time1;
        try {
            HttpURLConnection uc = HttpTransportUtil.getUrlConnection("http://" + serverName + ":" + port + "/webhr/HrServlet");
            HttpTransport t = new HttpTransport();
            t.setServiceName(serviceName);
            t.setServiceMethod(methodName);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(params.length);
            for (Object obj : params) {
                oos.writeObject(obj);
            }
            t.setTransportObject(bos.toByteArray());
            HttpTransportUtil.sendToOutputstream(uc.getOutputStream(), t);

            t = HttpTransportUtil.loadFromInputStream(uc.getInputStream());
            time2 = System.currentTimeMillis();
            if (!t.isSuccess()) {
                return null;
            } else {
                return t.getResult();
            }
        } catch (Exception ex) {
            String str = serviceName + " " + methodName + " " + UserContext.person_code + " ";
            for (Object obj : params) {
                if (obj == null) {
                    continue;
                }
                if (obj instanceof String || obj instanceof Integer || obj instanceof Boolean) {
                    str += obj.toString().length() > 100 ? obj.toString().substring(0, 100) : obj.toString() + " ";
                } else if (obj instanceof java.util.Date) {
                    str += DateUtil.DateToStr((java.util.Date) obj);
                } else if (obj.getClass().getName().startsWith("org.jhrcore.entity")) {
                    try {
                        str += obj.getClass().getName() + "_Key:" + PublicUtil.getProperty(obj, EntityBuilder.getEntityKey(obj.getClass()));
                    } catch (Exception e) {
                    }
                }
            }
            log.error("http://" + serverName + ":" + port + "/" + str + ":" + ex);
        } finally {
//            final long time = (time2 - time1) / 1000;
//            if (time > 20 && !"logEvent".equals(methodName)) {
//                String str = serviceName + " " + methodName + " " + UserContext.person_code + " ";
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

    @Override
    public Object lookupServiceOf(String serviceName) {
        try {
            String serviceClass = "org.jhrcore.httpservice." + serviceName + "HttpImpl";
            Class aclass = Class.forName(serviceClass);
            Object ins = aclass.newInstance();
            return ins;
        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    public Object lookupControlCenterServiceOf(String serviceName) {
        return null;
    }

    @Override
    public Object lookupServiceOf(String serverName, String serviceName) {
        return lookupServiceOf(serviceName);
    }

    public static void setServerName(String serverName) {
        HttpLocator.serverName = serverName;
    }

    @Override
    public void setServicePara(String ip, String port) {
        if (port == null) {
            port = ConfigManager.getConfigManager().getProperty("base.ApplicationServerPort");
        }
        if (port != null) {
            HttpLocator.port = port;
        }
        if (ip == null) {
            ip = ConfigManager.getConfigManager().getProperty("base.ApplicationServer");
        }
        if (ip != null) {
            HttpLocator.serverName = ip;
        }
        ConfigManager.getConfigManager().setProperty("base.ApplicationServer", ip);
        ConfigManager.getConfigManager().setProperty("base.ApplicationServerPort", port);
        ConfigManager.getConfigManager().save2();
    }
}
