/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.client.report;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Administrator
 */
public abstract class ReportTableModel extends AbstractTableModel{
    public abstract String getRowName(int rownum);
}
