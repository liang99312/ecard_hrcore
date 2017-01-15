/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.util;

import org.jhrcore.comm.ConfigManager;

/**
 *
 * @author hflj
 */
public class ImportUtil {
    public static String getImportField(String module_code){
        String defaultFieldName = ConfigManager.getConfigManager().getStringFromProperty(module_code+".Import.Field");
        defaultFieldName = (defaultFieldName == null || defaultFieldName.trim().equals("")) ? "a0190" : defaultFieldName;
        defaultFieldName = defaultFieldName.replace("_code_", "");
        return defaultFieldName;
    }
    public static void saveImportField(String module_code,String field){
        ConfigManager.getConfigManager().setProperty(module_code+".Import.Field", field);
        ConfigManager.getConfigManager().save2();
    }
}
