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
import java.awt.event.KeyEvent;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.SwingBindings;
import org.jhrcore.util.SysUtil;
import org.jhrcore.entity.annotation.ObjectListHint;
import org.jhrcore.ui.CodeSelectDialog;
import org.jhrcore.ui.listener.IPickCodeSelectListener;
import org.jhrcore.ui.renderer.TempCodeEditor;
import org.jhrcore.ui.renderer.ComboBoxCellRenderer;
import org.jhrcore.util.ComponentUtil;

/**
 *
 * @author Administrator
 */
public class TableCodeCellEditor extends AbstractCellEditor implements TableCellEditor {

    private Object cell_value = null;
    private final FTable ftable;
    private int horizontalAlignment = JTextField.LEFT;
    private ObjectListHint objectListHint;

    public TableCodeCellEditor(FTable ftable, ObjectListHint objectListHint) {
        super();
        this.objectListHint = objectListHint;
        this.ftable = ftable;
    }

    public TableCodeCellEditor(FTable ftable, ObjectListHint objectListHint, int horizontalAlignment) {
        super();
        this.objectListHint = objectListHint;
        this.ftable = ftable;
        this.horizontalAlignment = horizontalAlignment;
    }

    @Override
    public Object getCellEditorValue() {
        return cell_value;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        cell_value = value;
        final TempCodeEditor editor = TempCodeEditor.getCodeEditor(ftable.getObjects().get(row), TableCodeCellEditor.this.objectListHint, cell_value);
        if (editor.isSingle()) {
            final JComboBox cmBox = new JComboBox();
            SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE, editor.getShowCodes(), cmBox).bind();
            ComponentUtil.setRightEditComponent(table, row, column, cmBox, editor.isEnable());
            cmBox.setSelectedItem(editor.getCell_value());
            cmBox.setRenderer(new ComboBoxCellRenderer(horizontalAlignment));
            cmBox.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (cmBox.getSelectedItem() == null) {
                        return;
                    }
                    cell_value = cmBox.getSelectedItem();
                }
            });
            cmBox.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "ENTER");
            cmBox.getActionMap().put("ENTER", ftable.getMyEnterAction());
            return cmBox;
        }
        final JTextField textfield = new JTextField();
        textfield.setText(SysUtil.objToStr(editor.getCell_value()));
        textfield.setHorizontalAlignment(horizontalAlignment);
        textfield.setEditable(false);
        final JPanel jpanel = new JPanel(new BorderLayout());
        jpanel.setFocusTraversalKeysEnabled(false);
        JButton btnSelelect = new JButton("...");
        ComponentUtil.setRightEditComponent(table, row, column, btnSelelect, editor.isEnable());
        btnSelelect.setPreferredSize(new Dimension(18, btnSelelect.getPreferredSize().height));
        jpanel.add(textfield, BorderLayout.CENTER);
        jpanel.add(btnSelelect, BorderLayout.EAST);
        btnSelelect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                CodeSelectDialog csDialog = new CodeSelectDialog(editor.getCodes(), editor.getCode_type(), editor.getCell_value());
                csDialog.addPickCodeSelectListener(new IPickCodeSelectListener() {

                    @Override
                    public void pickCode(DefaultMutableTreeNode node) {
                        if (node != null) {
                            if (node.getUserObject() == null) {
                                return;
                            }
                            editor.setCell_value(node.getUserObject());
                            cell_value = editor.getCell_value();
                            textfield.setText(cell_value.toString());
                        }
                    }
                });
                csDialog.setTitle("«Î—°‘Ò");
                csDialog.setLocationRelativeTo(jpanel);
                csDialog.setVisible(true);
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
