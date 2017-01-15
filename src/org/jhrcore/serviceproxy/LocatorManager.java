package org.jhrcore.serviceproxy;

import org.jhrcore.client.UserContext;
import org.jhrcore.comm.ConfigChangeListener;
import org.jhrcore.comm.ConfigManager;

public class LocatorManager implements ConfigChangeListener, ILocator {

    private static LocatorManager locatorManager = null;
    private ILocator currentLocator;

    public static LocatorManager getLocatorManager() {
        if (locatorManager == null) {
            locatorManager = new LocatorManager();
        }
        ConfigManager.getConfigManager().addConfigChangeListners(locatorManager);
        UserContext.last_oper_time = System.currentTimeMillis();
        return locatorManager;
    }

    public void ConfigChangePerformed() {
        String serviceType = ConfigManager.getConfigManager().getProperty("base.ServiceType");
        if (serviceType != null && serviceType.equals("HTTP")) {
            currentLocator = new HttpLocator();
        } else if (serviceType != null && serviceType.equals("EJB")) {
            currentLocator = new EjbLocator();
        } else {
            currentLocator = new RmiLocator();
        }
    }

    public Object lookupServiceOf(String serviceName) {
        return currentLocator.lookupServiceOf(serviceName);
    }

    public Object lookupControlCenterServiceOf(String serviceName) {
        return currentLocator.lookupControlCenterServiceOf(serviceName);
    }

    public Object lookupServiceOf(String serverName, String serviceName) {
        return currentLocator.lookupServiceOf(serverName, serviceName);
    }

    @Override
    public void setServicePara(String ip, String port) {
        if (currentLocator != null) {
            currentLocator.setServicePara(ip, port);
        }
    }

    public static Object invokeService(String serviceName, String methodName, Object[] params) {
        return LocatorManager.getLocatorManager().invokeCurService(serviceName, methodName, params);
    }

    @Override
    public Object invokeCurService(String serviceName, String methodName, Object[] params) {
        if (currentLocator != null) {
            return currentLocator.invokeCurService(serviceName, methodName, params);
        }
        return null;
    }
}
