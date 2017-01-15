package org.jhrcore.serviceproxy;

import java.lang.reflect.Method;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.jhrcore.client.UserContext;
import org.jhrcore.comm.ConfigManager;

public class EjbLocator implements ILocator {

    private static String ejbPort = "1099";
    private static String ip = "127.0.0.1";
    private static final Logger log = Logger.getLogger(EjbLocator.class);
    private Properties props;

    public Object lookupServiceOf(String serviceName) {
        return lookupServiceOf(ConfigManager.getConfigManager().getProperty("base.ApplicationServer"), serviceName);
    }

    public Object lookupControlCenterServiceOf(String serviceName) {
        return lookupServiceOf(ConfigManager.getConfigManager().getProperty("server.ControlCenter"), serviceName);
    }

    public Object lookupServiceOf(String serverName, String serviceName) {
        InitialContext ctx = null;
        props.setProperty("java.naming.provider.url", serverName + ":" + ejbPort);
        try {
            ctx = new InitialContext(props);
            if ("SalaryService".equals(serviceName) && "sqlserver".equals(UserContext.sql_dialect)) {
                return ctx.lookup("SalaryServiceImplForSQL/remote");
            } else if ("PersonService".equals(serviceName) && "sqlserver".equals(UserContext.sql_dialect)) {
                return ctx.lookup("PersonServiceImplForSQL/remote");
            }
            return ctx.lookup(serviceName + "Impl/remote");
        } catch (NamingException e) {
            e.printStackTrace();
            log.error(e);
        }
        return null;
    }

    public EjbLocator() {
        super();
        props = new Properties();
        props.setProperty("java.naming.factory.initial",
                "org.jnp.interfaces.NamingContextFactory");
        //props.setProperty("java.naming.provider.url", "localhost:1099");
        props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
    }

    public Object lookupServiceOf2(String serviceName) {
        InitialContext ctx = null;
        props.setProperty("java.naming.provider.url", serviceName + ":" + ejbPort);
        try {
            ctx = new InitialContext(props);
            if ("SalaryService".equals(serviceName) && "sqlserver".equals(UserContext.sql_dialect)) {
                return ctx.lookup("SalaryServiceImplForSQL/remote");
            } else if ("PersonService".equals(serviceName) && "sqlserver".equals(UserContext.sql_dialect)) {
                return ctx.lookup("PersonServiceImplForSQL/remote");
            }
            return ctx.lookup(serviceName + "Impl/remote");
        } catch (NamingException e) {
            log.error(e);
        }
        return null;
    }

    @Override
    public void setServicePara(String ip, String port) {
        EjbLocator.ip = ip;
        EjbLocator.ejbPort = port;
    }

    @Override
    public Object invokeCurService(String serviceName, String methodName, Object[] params) {
        try {
            Object ins = LocatorManager.getLocatorManager().lookupServiceOf(serviceName);
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
            return amethod.invoke(ins, params);
        } catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }
}
