/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice.impl;

import java.util.Properties;
import org.jhrcore.iservice.ConfigService;
import org.jhrcore.serviceproxy.LocatorManager;

/**
 *
 * @author mxliteboss
 */
public class ConfigImpl {

    private static String service = ConfigService.class.getSimpleName();

    public static Properties getProperties() {
            Object obj = LocatorManager.invokeService(service, "getProperties", new Object[]{});
            if (obj != null) {
                return (Properties) obj;
            }
        return new Properties();
    }

    public static void saveProperties(Properties properties) {
            Object obj = LocatorManager.invokeService(service, "saveProperties", new Object[]{properties});
            if (obj != null) {
            }
    }
}
