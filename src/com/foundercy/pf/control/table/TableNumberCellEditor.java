/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foundercy.pf.control.table;

import java.awt.Component;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.EventObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import org.jhrcore.util.ComponentUtil;

/**
 *
 * @author Administrator
 */
public class TableNumberCellEditor extends AbstractCellEditor implements TableCellEditor {

    private JTextField textField = new JTextField();
    private DecimalFormat df = null;
    private boolean is_int = false;

    public TableNumberCellEditor(int align_ment, String format, boolean is_int) {
        super();
        if (format != null && !format.equals("")) {
            df = new DecimalFormat(format);
        }
        this.is_int = is_int;
        textField.setBorder(null);
        textField.setHorizontalAlignment(align_ment);
    }

    @Override
    public Object getCellEditorValue() {
        if (textField.getText() == null || textField.getText().equals("")) {
            return 0;
        }
        try {
            if (is_int) {
                if(!textField.getText().matches("^[0-9]*$")){
                    JOptionPane.showMessageDialog(null, "请输入整数!");
                    return textField.getText();
                }
                return Integer.valueOf(textField.getText());
            } else {
                if (df != null) {
                    return df.parse(textField.getText());
                } else {
                    if(!textField.getText().matches("^[0-9]*\\.?[0-9]*$")){
                        JOptionPane.showMessageDialog(null, "请输入数值!");
                        return textField.getText();
                    }
                    return Float.valueOf(textField.getText());
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(TableNumberCellEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        ComponentUtil.setRightEditComponent(table, row, column, textField);
        if (df == null) {
            textField.setText(value == null ? "" : value.toString());
        } else {
            if (value != null) {
                textField.setText(df.format(value));
            } else {
                textField.setText("");
            }
        }
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
