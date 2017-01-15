/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foundercy.pf.control.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import org.jhrcore.ui.FormulaTextDialog;
import org.jhrcore.util.ComponentUtil;
import org.jhrcore.util.SysUtil;

/**
 *
 * @author mxliteboss
 */
public class TableAreaCellEditor extends AbstractCellEditor implements TableCellEditor {

    private Object cell_value = null;
    private int align_ment = 0;

    public TableAreaCellEditor(int align_ment) {
        super();
        this.align_ment = align_ment;
    }

    @Override
    public Object getCellEditorValue() {
        return cell_value;
    }

    @Override
    public Component getTableCellEditorComponent(final JTable table, Object value, boolean isSelected, int row, int column) {
        cell_value = value;
        JTextField textField = new JTextField();
        textField.setBorder(null);
        textField.setHorizontalAlignment(align_ment);
        final JButton btnSelect = new JButton("...");
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBorder(null);
        pnl.setFocusTraversalKeysEnabled(false);
        pnl.add(textField, BorderLayout.CENTER);
        pnl.add(btnSelect, BorderLayout.EAST);
        final JButton btn = new JButton();
        ComponentUtil.setRightEditComponent(table, row, column, btn);
        String text = SysUtil.objToStr(value);
        text = text.length() > 40 ? (text.substring(0, 10) + "...") : text;
        textField.setText(text);
        textField.setEditable(false);
        textField.setBackground(pnl.getBackground());
        if (btn.isEnabled()) {
            textField.setBackground(Color.WHITE);
        }
        btnSelect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                FExpandTable t = (FExpandTable) table;
                boolean editable = ((FTableModel) t.getBaseTable().getModel()).isEditable();
                FormulaTextDialog dlg = new FormulaTextDialog(SysUtil.objToStr(cell_value), "", editable ? btn.isEnabled() : false);
                dlg.setLocationRelativeTo(btnSelect);
                dlg.setVisible(true);
                if (editable) {
                    if (dlg.isClick_ok()) {
                        cell_value = dlg.getText();
                    }
                }
            }
        });
        return pnl;
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
