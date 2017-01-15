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
import java.util.EventObject;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import org.jhrcore.client.CommUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.ui.DeptSelectDlg;
import org.jhrcore.ui.TreeSelectMod;

/**
 *
 * @author Administrator
 */
public class TableDeptCellEditor extends AbstractCellEditor implements TableCellEditor {

    private Object cell_value = null;
    private FTable ftable;

    public TableDeptCellEditor() {
        super();
    }

    public TableDeptCellEditor(FTable ftable) {
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

        final JPanel jpanel = new JPanel(new BorderLayout());
        jpanel.setFocusTraversalKeysEnabled(false);
        JButton btnSelelect = new JButton("");
        btnSelelect.setPreferredSize(new Dimension(18, btnSelelect.getPreferredSize().height));
        jpanel.add(textfield, BorderLayout.CENTER);
        jpanel.add(btnSelelect, BorderLayout.EAST);
        btnSelelect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List<DeptCode> depts = UserContext.getDepts(false, false);
                if (ftable.isEditForChangeScheme()) {
                    depts = (List<DeptCode>) CommUtil.fetchEntities("from DeptCode d where d.del_flag=0 order by dept_code");
                }
                DeptSelectDlg dlg = new DeptSelectDlg(depts, cell_value, ftable.isDeptCode_chind_only_flag() ? TreeSelectMod.leafSelectMod : TreeSelectMod.nodeSelectMod);
                ContextManager.locateOnMainScreenCenter(dlg);
                dlg.setVisible(true);
                if (dlg.isClick_ok()) {
                    textfield.setText(dlg.getCurDept().getContent());
                    cell_value = dlg.getCurDept();
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
