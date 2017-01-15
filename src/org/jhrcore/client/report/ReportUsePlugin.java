/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.client.report;

import org.jhrcore.ui.task.CommTask;

/**
 *
 * @author mxliteboss
 */
public class ReportUsePlugin extends CommTask {

    @Override
    public Class getModuleClass() {
        return ReportUsePanel.class;
    }

    @Override
    public String getGroupCode() {
        return "Report";
    }
}
