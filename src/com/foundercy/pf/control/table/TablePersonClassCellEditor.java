/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foundercy.pf.control.table;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.entity.base.EntityDef;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.ui.PersonClassSelectDlg;

/**
 *
 * @author Administrator
 */
public class TablePersonClassCellEditor extends AbstractCellEditor implements TableCellEditor {

    private Object cell_value = null;
    private FTable ftable;
    public TablePersonClassCellEditor() {
        super();
    }

    public TablePersonClassCellEditor(FTable ftable) {
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
        final JTextField textfield = new JTextField();
        textfield.setText(cell_value == null ? "" : cell_value.toString());
        textfield.setEditable(false);
        textfield.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                    textfield.setText("");
                    cell_value = null;
                }
            }
        });
        
        final JPanel jpanel = new JPanel(new BorderLayout());
        jpanel.setFocusTraversalKeysEnabled(false);
        JButton btnSelelect = new JButton("");
        btnSelelect.setPreferredSize(new Dimension(18, btnSelelect.getPreferredSize().height));
        jpanel.add(textfield, BorderLayout.CENTER);
        jpanel.add(btnSelelect, BorderLayout.EAST);
        btnSelelect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Object cur_val = ftable.getCurrentRow();
                String key = "";
                PersonClassSelectDlg dlg = new PersonClassSelectDlg();
                dlg.checkedPerson_calss(key);
                ContextManager.locateOnMainScreenCenter(dlg);
                dlg.setVisible(true);
                if (dlg.isClick_ok()) {
                    String keys = dlg.GetPerson_calss_key();
                    ArrayList<EntityDef> lt = dlg.GetPerson_calss_list();
                    textfield.setText(lt.toString());                    
                    cell_value = lt;
                    PublicUtil.setValueBy2(cur_val, "a0191s", keys);
                    PublicUtil.setValueBy2(cur_val, "entityDefs", lt);
                }
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
