/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foundercy.pf.control.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import org.jfree.ui.PaintSample;

/**
 *
 * @author Administrator
 */
public class TableColorCellEditor extends AbstractCellEditor implements TableCellEditor {

    private Object cell_value = null;
    private FTable ftable;
    public TableColorCellEditor() {
        super();
    }

    public TableColorCellEditor(FTable ftable) {
        super();
        this.ftable = ftable;
    }

    @Override
    public Object getCellEditorValue() {
        return cell_value;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        cell_value = value;
        boolean field_editor_able = true;
        final PaintSample sample = new PaintSample(value!=null?((Color)value):ftable.getBackground());
        sample.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){                    
                    cell_value = null;
                    sample.setPaint(ftable.getBackground());
                }
            }
        });        
        final JPanel jpanel = new JPanel(new BorderLayout());
        jpanel.setFocusTraversalKeysEnabled(false);
        JButton btnSelelect = new JButton("");
        btnSelelect.setEnabled(field_editor_able);
        btnSelelect.setPreferredSize(new Dimension(18, btnSelelect.getPreferredSize().height));
        jpanel.add(sample, BorderLayout.CENTER);
        jpanel.add(btnSelelect, BorderLayout.EAST);
        btnSelelect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(null, "Ñ¡ÔñÑÕÉ«", (Color)cell_value);                   
                if(c != null) sample.setPaint(c);                
                cell_value = c;
            }
        });

        return jpanel;
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
