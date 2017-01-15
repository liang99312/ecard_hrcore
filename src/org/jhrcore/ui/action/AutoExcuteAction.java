/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.jhrcore.client.CommUtil;
import org.jhrcore.client.report.ReportPanel;
import org.jhrcore.entity.AutoExcuteScheme;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.util.MsgUtil;

/**
 *
 * @author Administrator
 */
public class AutoExcuteAction extends AbstractAction {

    private AutoExcuteScheme aes;
    private ValidateSQLResult excute;

    public AutoExcuteAction(AutoExcuteScheme aes) {
        super(aes.getScheme_name());
        this.aes = aes;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String sql = ReportPanel.getNewQuery(aes.getFormula(), null);
        excute = CommUtil.excuteSQL(sql);
        if (excute.getResult() == 0) {
            MsgUtil.showInfoMsg("Ö´ÐÐ³É¹¦");
        }
    }
}
