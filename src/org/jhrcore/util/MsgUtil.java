/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import org.jhrcore.client.UserContext;
import org.jhrcore.comm.ConfigManager;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.msg.CommMsg;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.ui.FormulaTextDialog;

/**
 *
 * @author mxliteboss
 */
public class MsgUtil {

    public static void showInfoMsg(Object msg) {
        showInfoMsg(null, msg);
    }

    public static void showInfoMsg(JComponent c, Object msg) {
        JOptionPane.showMessageDialog(c, msg, SysUtil.objToStr(CommMsg.INFORMATION_MESSAGE), JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showErrorMsg(Object msg) {
        showErrorMsg(null, msg);
    }

    public static void showErrorMsg(JComponent c, Object msg) {
        JOptionPane.showMessageDialog(c, msg, SysUtil.objToStr(CommMsg.ERROR_MESSAGE), JOptionPane.ERROR_MESSAGE);
    }

    public static void showHRSaveSuccessMsg(Component c) {
        boolean save_report_flag = "1".equals(ConfigManager.getConfigManager().getProperty(UserContext.codeShowSaveReport));
        if (save_report_flag) {
            JOptionPane.showMessageDialog(c, CommMsg.SAVESUCCESS_MESSAGE, SysUtil.objToStr(CommMsg.INFORMATION_MESSAGE), JOptionPane.INFORMATION_MESSAGE);
        }
    }
    //保存失败

    public static void showHRSaveErrorMsg(Object msg) {
        showHRMsg(msg, CommMsg.error_save_msg);
    }
    //更新失败

    public static void showHRUpdateErrorMsg(Object msg) {
        showHRMsg(msg, CommMsg.error_save_msg);
    }
    //删除失败

    public static void showHRDelErrorMsg(Object msg) {
        showHRMsg(msg, CommMsg.error_save_msg);
    }
    //计算失败

    public static void showHRCalErrorMsg(Object msg) {
        showHRMsg(msg, CommMsg.error_save_msg);
    }
    //导入消息报告

    public static void showHRValidateReportMsg(Object msg) {
        showHRMsg(msg, CommMsg.EXPORT_MESSAGE);
    }
    //普通消息

    public static void showHRMsg(Object msg, Object title) {
        showHRMsg(msg, title, "");
    }
    //SQL校验，PASS：是否验证通过

    public static void showHRValidateMsg(Object msg, Object title, boolean pass) {
        showHRMsg(msg, title, pass ? CommMsg.validate_sucess.toString() : CommMsg.validate_fail.toString());
    }

    private static void showHRMsg(Object msg, Object title, String syntax_msg) {
        if (msg instanceof ValidateSQLResult) {
            msg = ((ValidateSQLResult) msg).getMsg();
        }
        FormulaTextDialog ftDialog = new FormulaTextDialog(msg.toString(), title.toString(), syntax_msg);
        ContextManager.locateOnMainScreenCenter(ftDialog);
        ftDialog.setVisible(true);
    }

    public static boolean showNotConfirmDialog(Object msg) {
        return showNotConfirmDialog(null, msg);
    }

    public static boolean showNotConfirmDialog(JComponent c, Object msg) {
        return JOptionPane.showConfirmDialog(c,
                msg, SysUtil.objToStr(CommMsg.QUESTION_MESSAGE), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION;
    }

    public static Object showInputDialog(Object msg) {
        return showInputDialog(null, msg);
    }

    public static Object showInputDialog(Object msg, Object initValue) {
        return showInputDialog(null, msg, initValue);
    }

    public static Object showInputDialog(JComponent c, Object msg, Object initValue) {
        return JOptionPane.showInputDialog(JOptionPane.getFrameForComponent(c), msg, initValue);
    }
}
