/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foundercy.pf.control.table;

import java.awt.Component;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import org.jhrcore.util.ComponentUtil;

/**
 *
 * @author Administrator
 */
public class TableStringCellEditor extends AbstractCellEditor implements TableCellEditor {

    private JTextField textField = new JTextField();

    public TableStringCellEditor(int align_ment) {
        super();
        textField.setBorder(null);
        textField.setHorizontalAlignment(align_ment);
    }

    @Override
    public Object getCellEditorValue() {
        return textField.getText();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        ComponentUtil.setRightEditComponent(table, row, column, textField);
        textField.setText(value == null ? "" : value.toString());
        textField.selectAll();
        return textField;
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
