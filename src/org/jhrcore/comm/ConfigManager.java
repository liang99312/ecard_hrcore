package org.jhrcore.comm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jhrcore.util.FileUtil;

public class ConfigManager {

    protected static final Logger log = Logger.getLogger(ConfigManager.class.getSimpleName());
    private Properties props;
    private static ConfigManager configManager;
    private List<ConfigChangeListener> configChangeListners = new ArrayList<ConfigChangeListener>();

    public void addConfigChangeListners(ConfigChangeListener listner) {
        configChangeListners.add(listner);
        listner.ConfigChangePerformed();
    }

    public void removeConfigChangeListners(ConfigChangeListener listner) {
        configChangeListners.remove(listner);
    }

    private void fireConfigChange() {
        Iterator<ConfigChangeListener> it = configChangeListners.iterator();
        while (it.hasNext()) {
            ((ConfigChangeListener) it.next()).ConfigChangePerformed();
        }
    }

    private ConfigManager() {
        super();
        loadPropertiesFrom(System.getProperty("user.home") + "/" + "hr"
                + ".properties");
    }

    public synchronized void setProperty(String propertyName,
            String propertyValue) {
        props.setProperty(propertyName, propertyValue);
    }

    // 获取系统设置，如果没该设置，则从/resources/hr.properties读取
    public synchronized String getProperty(String propertyName) {
        String result = props.getProperty(propertyName);
        if (result == null) {
            Properties tmp = new Properties();
            try {
                tmp.load(ConfigManager.class.getResourceAsStream("/resources/hr.properties"));
                result = tmp.getProperty(propertyName);
                if (result != null) {
                    props.setProperty(propertyName, result);
                    save();
                }
            } catch (IOException e) {
                log.error(e);
            }
        }
        if (result == null) {
            result = new String("");
        }
        return result;
    }

    private synchronized Object getFromProperty(String propertyName,
            Class<?> argType) {
        String prop;
        Object ret = null;

        if ((prop = getProperty(propertyName)) != null) {
            try {
                if (argType == String.class) {
                    ret = prop;
                } else if (argType == Integer.class) {
                    ret = Integer.valueOf(prop);
                } else if (argType == Boolean.class) {
                    ret = Boolean.valueOf("true".equalsIgnoreCase(prop)
                            || "yes".equalsIgnoreCase(prop)
                            || "1".equals(prop));
                }
            } catch (NumberFormatException e) {
                log.error(propertyName + " is an invalid number");
            }
        }
        if (ret == null) {
            log.error("Setting property " + propertyName + " = " + ret);
        }
        return ret;
    }

    public String getStringFromProperty(String propertyName) {
        return (String) getFromProperty(propertyName, String.class);
    }

    public Integer getIntegerFromProperty(String propertyName) {
        return (Integer) getFromProperty(propertyName, Integer.class);
    }

    public Boolean getBooleanFromProperty(String propertyName) {
        return (Boolean) getFromProperty(propertyName, Boolean.class);
    }

    public synchronized void save() {
        File file = new File(System.getProperty("user.home") + "/" + "hr"
                + ".properties");
        try {
            props.store(new FileOutputStream(file), "");
            fireConfigChange();
        } catch (IOException e) {
            log.error("Can not save properties file");
        }
    }

    public synchronized void save2() {
        File file = new File(System.getProperty("user.home") + "/" + "hr"
                + ".properties");
        try {
            props.store(new FileOutputStream(file), "");
        } catch (IOException e) {
            log.error("Can not save properties file");
        }
    }

    public Properties loadProperties(String cfgFileName) {
        Properties properties = new Properties();
        File file = new File(cfgFileName);
        try {
            properties.load(new BufferedInputStream(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        return properties;
    }

    private void loadPropertiesFrom(String cfgFileName) {
        props = new Properties();
        File file = new File(cfgFileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileUtil.CopyFile(ConfigManager.class.getResourceAsStream("/resources/hr.properties"),
                        file);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            props.load(new BufferedInputStream(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConfigManager getConfigManager() {
        if (configManager == null) {
            configManager = new ConfigManager();
        }
        return configManager;
    }

    public static void saveBooleanProperty(String code) {
        String showAllModule = ConfigManager.getConfigManager().getProperty(code);
        showAllModule = "1".equals(showAllModule) ? "0" : "1";
        ConfigManager.getConfigManager().setProperty(code, showAllModule);
        ConfigManager.getConfigManager().save2();
    }

    public Properties getProps() {
        return props;
    }
//
//    public PropertiesEditPanel getEditPanel(String propertyGroups[],
//            String propertyGroupsChineseName[]) {
//        return new PropertiesEditPanel(propertyGroups,
//                propertyGroupsChineseName);
//    }
//
//    public void showEditDlg(String propertyGroups[],
//            String propertyGroupsChineseName[], Rectangle bounds) {
//        final PropertiesEditPanel propertiesEditPanel = new PropertiesEditPanel(
//                propertyGroups, propertyGroupsChineseName);
//        final JDialog dlg = new JDialog();
//        JPanel pnlComm = new JPanel(new FlowLayout());
//        JButton btnOk = new JButton("确定");
//        JButton btnCancel = new JButton("取消");
//        pnlComm.add(btnOk);
//        pnlComm.add(btnCancel);
//        btnOk.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent arg0) {
//                propertiesEditPanel.save();
//                dlg.dispose();
//            }
//        });
//        btnCancel.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent arg0) {
//                dlg.dispose();
//            }
//        });
//        dlg.getContentPane().add(propertiesEditPanel, BorderLayout.CENTER);
//        dlg.getContentPane().add(pnlComm, BorderLayout.SOUTH);
//        dlg.setBounds(bounds);
//        dlg.setModal(true);
//        dlg.setUndecorated(true);
//        dlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        dlg.setVisible(true);
//    }

    public static void main(String[] args) {
        // ConfigManager.getConfigManager().showEditDlg(new
        // String[]{"base","print"}, new String[]{"基础设置","打印设置"}, new
        // Rectangle(0,0,300,500));
//        JFrame fm = new JFrame("test");
//        JToolBar toolbar = new JToolBar();
//        fm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        PropertiesEditPanel panel = ConfigManager.getConfigManager().getEditPanel(new String[]{"base", "print", "image"},
//                new String[]{"基础设置", "打印设置", "标签"});
//        panel.buildToolbar(toolbar);
//        fm.getContentPane().add(panel, "Center");
//        fm.getContentPane().add(toolbar, "North");
//        fm.setSize(500, 400);
//        fm.pack();
//        fm.setVisible(true);
    }
}
