package org.jhrcore.ui;

import com.foundercy.pf.control.table.FTable;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import java.awt.Image;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.sf.fjreport.statusbar.JStatusBar;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.SysParameter;
import org.jhrcore.util.ImageUtil;

public class ContextManager {

    private static JFrame mainFrame;
    private static JStatusBar statusBar;
    private static JPanel upperPane;
    private static String hrTitle = null;//
    private static String defaultHrTitle = "信用卡管理系统V9.0";
    private static SysParameter titlePara;

    public static String getHrTitle() {
        if (hrTitle == null) {
            titlePara = (SysParameter) CommUtil.fetchEntityBy("from SysParameter s where s.sysParameter_key='System.HRTitle'");
            if (titlePara == null) {
                titlePara = new SysParameter();
                titlePara.setSysParameter_key("System.HRTitle");
                titlePara.setSysparameter_value(defaultHrTitle);
                CommUtil.saveOrUpdate(titlePara);
            }
            hrTitle = titlePara.getSysparameter_value() == null ? "" : titlePara.getSysparameter_value();
            hrTitle = hrTitle.trim().equals("") ? defaultHrTitle : hrTitle;
        }
        return hrTitle;
    }

    public static void setHrTitle(String hrTitle) {
        getMainFrame().setTitle(getMainFrame().getTitle().replace(ContextManager.hrTitle, hrTitle));
        ContextManager.hrTitle = hrTitle;
        titlePara.setSysparameter_value(hrTitle);
        CommUtil.updateEntity(titlePara);
    }

    public static JFrame getMainFrame() {
//        if (mainFrame == null) {
//            return new JFrame();
//        }
        return mainFrame;
    }

    public static void setMainFrame(JFrame mainFrame) {
        ContextManager.mainFrame = mainFrame;
    }

    public static void locateOnScreenCenter(Component component) {
        if(component instanceof JDialog){
            JDialog jd=(JDialog)component;
            jd.setIconImage(ImageUtil.getIconImage());
        }
        Dimension paneSize = component.getSize();
        Dimension screenSize = component.getToolkit().getScreenSize();
        component.setLocation(
                (screenSize.width - paneSize.width) / 2,
                (screenSize.height - paneSize.height) / 2);
    }

    public static void locateOnMainScreenCenter(Component component) {
        if(component instanceof JDialog){
            JDialog jd=(JDialog)component;
            jd.setIconImage(ImageUtil.getIconImage());
        }
        Dimension paneSize = component.getSize();
        Dimension screenSize = component.getToolkit().getScreenSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gds = ge.getScreenDevices();
        if (gds.length >= 1) {
            screenSize = gds[0].getDefaultConfiguration().getBounds().getSize();
        }
        component.setLocation(
                (screenSize.width - paneSize.width) / 2,
                (screenSize.height - paneSize.height) / 2);
    }

    public static void refreshStatusBar() {
        if (statusBar == null) {
            statusBar = new JStatusBar(new int[]{10, 10, 10, 0, 0});
        }
        statusBar.setContent(1, "");
    }

    public static void setStatusBar(String text) {
        setStatusBar(1, text);
    }

    public static void setStatusBar(int col, String text) {
        if (col > 4) {
            return;
        }
        refreshStatusBar();
        statusBar.setContent(col, text);
        statusBar.updateUI();
    }

    public static void setStatusBar(FTable ftable) {
        if (ftable != null) {
            statusBar.setContent(1, "当前记录数：　" + ftable.getObjects().size());
        } else {
            statusBar.setContent(1, "");
        }
        statusBar.updateUI();
    }

    public static void setStatusBar(int num) {
        refreshStatusBar();
        statusBar.setContent(1, "当前记录数：　" + num);
        statusBar.updateUI();
    }

    public static void setMainFrameTitle(String content) {
        if (content == null) {
            getMainFrame().setTitle(hrTitle);
        } else {
            getMainFrame().setTitle(hrTitle + "          当前部门：" + content);
        }
    }

    public static JStatusBar getStatusBar() {
        if (statusBar == null) {
            statusBar = new JStatusBar(new int[]{10, 10, 10, 0, 0});
        }
        return statusBar;
    }

    public static void setStatusBar(JStatusBar statusBar) {
        ContextManager.statusBar = statusBar;
    }

    public static JPanel getUpperPane() {
        return upperPane;
    }

    public static void setUpperPane(JPanel upperPane) {
        ContextManager.upperPane = upperPane;
    }
}
