/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.task;

import java.lang.reflect.Field;
import javax.swing.JPanel;
import org.jhrcore.client.UserContext;
import org.jhrcore.util.SysUtil;

/**
 *
 * @author mxliteboss
 */
public class CommTask {

    private String defaultModuleName = "";

    public void setDefaultModuleName(String defaultModuleName) {
        this.defaultModuleName = defaultModuleName;
    }

    public String getGroupCode() {
        String moduleCode = getModuleCode();
        if (moduleCode.contains("_")) {
            return moduleCode.substring(0, moduleCode.indexOf("_"));
        }
        return "";
    }

    public Object getClassName() {
        return "";
    }

    public Class getModuleClass() {
        return javax.swing.JPanel.class;
    }

    public JPanel getModulePanel() {
        Class c = getModuleClass();
        try {
            return (JPanel) c.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new javax.swing.JPanel();
    }

    public String getModuleCode() {
        Class c = getModuleClass();
        try {
            Field field = c.getField("module_code");
            Object obj = field.get(null);
            return SysUtil.objToStr(obj);
        } catch (Exception ex) {
//            ex.printStackTrace();
        }
        return "";
    }

    public String getIconName() {
        return "module_1.png";
    }

    public String getGroupName() {
        String groupCode = getGroupCode();
        return UserContext.getFuntionName(groupCode, groupCode);
    }

    @Override
    public String toString() {
        return UserContext.getFuntionName(getModuleCode(), defaultModuleName);
    }
}
