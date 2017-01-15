/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.client.report;

import org.jhrcore.ui.task.CommTask;

/**
 *
 * @author Administrator
 */
public class ReportPlugin extends CommTask {
    
    @Override
    public Class getModuleClass() {
        return ReportPanel.class;
    }
    
    @Override
    public String getGroupCode() {
        return "Report";
    }
}
