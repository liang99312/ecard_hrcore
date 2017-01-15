/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foundercy.pf.control.table;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;
import org.jhrcore.util.SysUtil;
import org.jhrcore.util.ComponentUtil;

/**
 *
 * @author Administrator
 */
public class TableBooleanCellEditor extends AbstractCellEditor implements TableCellEditor {

    private Object cell_value = null;

    public TableBooleanCellEditor() {
        super();
    }

    public Object getCellEditorValue() {
        return cell_value;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        cell_value = value;
        if (!(cell_value instanceof Boolean)) {
            cell_value = SysUtil.objToBoolean(cell_value);
        }
        final JCheckBox checkBox = new JCheckBox("", false);
        ComponentUtil.setRightEditComponent(table, row, column, checkBox);
        checkBox.setHorizontalAlignment(SwingConstants.CENTER);
        if (cell_value != null) {
            checkBox.setSelected((Boolean) cell_value);
        }
        checkBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                cell_value = checkBox.isSelected();
            }
        });

        return checkBox;
    }

    @Override
    public boolean isCellEditable(EventObject event) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject event) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        fireEditingStopped();
        return true;
    }

    @Override
    public void cancelCellEditing() {
        fireEditingCanceled();
    }
}
