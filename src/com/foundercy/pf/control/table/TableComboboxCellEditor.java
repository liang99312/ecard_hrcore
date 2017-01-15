/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foundercy.pf.control.table;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellEditor;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.SwingBindings;
import org.jhrcore.entity.annotation.ObjectListHint;
import org.jhrcore.ui.BeanPanel;
import org.jhrcore.util.ComponentUtil;

/**
 *
 * @author Administrator
 */
public class TableComboboxCellEditor extends AbstractCellEditor implements TableCellEditor {

    private Object cell_value = null;
    private List<Object> lut_objects = new ArrayList();
    private FTable ftable;
    private String fieldName;
    private ObjectListHint objectListHint;

    public TableComboboxCellEditor(List lut_objects, FTable ftable, String fieldName, ObjectListHint objectListHint) {
        super();
        this.ftable = ftable;
        this.lut_objects = lut_objects;
        this.objectListHint = objectListHint;
    }

    public Object getCellEditorValue() {
        return cell_value;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        cell_value = value;
        final JComboBox cmBox = new JComboBox();
        if (objectListHint != null && ftable != null) {
            lut_objects = BeanPanel.getObjHintData(objectListHint, ftable.getObjects().get(ftable.getCurrentRowIndex()), fieldName);
        }
        SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE, lut_objects, cmBox).bind();
        ComponentUtil.setRightEditComponent(table, row, column, cmBox);
        cmBox.setSelectedIndex(lut_objects.indexOf(cell_value));
        cmBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cell_value = cmBox.getSelectedItem();
            }
        });
        cmBox.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "ENTER");
        if (ftable != null) {
            cmBox.getActionMap().put("ENTER", ftable.getMyEnterAction());
        }
        return cmBox;
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
