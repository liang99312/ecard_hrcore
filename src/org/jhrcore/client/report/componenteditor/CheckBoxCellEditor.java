/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.client.report.componenteditor;

import com.fr.cell.Grid;
import com.fr.cell.editor.AbstractCellEditor;
import com.fr.report.CellElement;
import java.awt.Component;
import javax.swing.JCheckBox;

/**
 *
 * @author Administrator
 */
public class CheckBoxCellEditor extends AbstractCellEditor {

    private transient JCheckBox textField = new JCheckBox();

    public CheckBoxCellEditor() {
        super();
        textField.setSelected(false);
    }

    @Override
    public Object getCellEditorValue() throws Exception {
        return textField.isSelected();
    }

    @Override
    public Component getCellEditorComponent(Grid arg0, CellElement arg1) {
        return textField;
    }
}
