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
public class ReportNoPlugin extends CommTask {

    @Override
    public Class getModuleClass() {
        return ReportNoPanel.class;
    }

    @Override
    public String getGroupCode() {
        return "Report";
    }
}
