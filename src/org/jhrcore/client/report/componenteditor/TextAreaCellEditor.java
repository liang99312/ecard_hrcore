/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.client.report.componenteditor;

import com.fr.cell.Grid;
import com.fr.cell.editor.AbstractCellEditor;
import com.fr.report.CellElement;
import java.awt.Component;
import javax.swing.JTextArea;

/**
 *
 * @author Administrator
 */
public class TextAreaCellEditor extends AbstractCellEditor {

    private transient JTextArea textField = new JTextArea();

    public TextAreaCellEditor() {
        super();
    }

    @Override
    public Object getCellEditorValue() throws Exception {
        if (textField.getText() == null)
            return "";
        return textField.getText().split(",");
    }

    @Override
    public Component getCellEditorComponent(Grid arg0, CellElement arg1) {
        return textField;
    }
}
