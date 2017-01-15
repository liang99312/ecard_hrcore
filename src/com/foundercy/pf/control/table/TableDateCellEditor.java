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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.util.Date;
import java.util.EventObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import org.jhrcore.util.DateUtil;
import org.jhrcore.ui.DateChooser;
import org.jhrcore.ui.MaskDateFormatter;
import org.jhrcore.util.ComponentUtil;

/**
 *
 * @author Administrator
 */
public class TableDateCellEditor extends AbstractCellEditor implements TableCellEditor {

    private Object cell_value = null;
    private JPanel pnlDateEditor = null;
    private JFormattedTextField picker;
    private MaskDateFormatter mf1 = null;
    private String format_str = "yyyy-MM-dd";
    private JButton btnSelect;

    private JPanel getPnlDateEditor(Object value) {
//        if (pnlDateEditor != null) {
//            cell_value = value;
//            picker.setText(mf1.valueToStr(value));
//            //picker.setValue(cell_value);
//            return pnlDateEditor;
//        }
        pnlDateEditor = new JPanel(new BorderLayout());
        pnlDateEditor.setBorder(null);
        pnlDateEditor.setFocusTraversalKeysEnabled(false);

        //      final JFormattedTextField picker = new JFormattedTextField(mf1);
        try {
            mf1 = new MaskDateFormatter(getDateFormatStr(format_str), format_str);
        } catch (ParseException ex) {
            Logger.getLogger(TableDateCellEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
//        String sformat = "yyyy-MM-dd";
//        DateFormat format = new SimpleDateFormat(sformat);
        //DateFormatter df = new DateFormatter(format);
        picker = new JFormattedTextField(mf1);
        picker.setBorder(null);
        picker.setText(mf1.valueToStr(value));
        btnSelect = new JButton("...");
        btnSelect.setPreferredSize(new Dimension(18, 22));
        //picker.setValue(value);
        picker.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                //commitEdit();
            }
        });

        btnSelect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!format_str.toLowerCase().contains("d")) {
                    return;
                }
                DateChooser dateChooser = new DateChooser(format_str);
                dateChooser.setDate2(DateUtil.StrToDate(picker.getText(), mf1.getFormat(), new Date()));
                JDialog dialog = new JDialog(JOptionPane.getFrameForComponent(btnSelect), "日期时间选择", true);
                dateChooser.setDialog(dialog);
                dialog.setLocationRelativeTo(btnSelect);
                dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
                dialog.getContentPane().add(dateChooser, BorderLayout.CENTER);
                dialog.pack();
                dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
                if (dateChooser.isPicked()) {
                    picker.setText(mf1.valueToStr(dateChooser.getDate()));
                    //picker.setValue(dateChooser.getDate());
                    cell_value = dateChooser.getDate();
                }
            }
        });

        pnlDateEditor.add(picker, BorderLayout.CENTER);
        pnlDateEditor.add(btnSelect, BorderLayout.EAST);
        return pnlDateEditor;
    }

    private void commitEdit() {
        String tmp = (String) picker.getText();
        // System.out.println("tmp:"+tmp);
        if (mf1.getReplace_key().replace("#", "_").equals(tmp)) {
            // System.out.println("tmp1:"+mf1.getReplace_key().replace("#", "_"));
            cell_value = null;
            return;
        }
        Date date = DateUtil.StrToDate(tmp, mf1.getFormat());
        if (date == null) {
            JOptionPane.showMessageDialog(picker, "错误的日期格式!");
            try {
                picker.setText(mf1.getReplace_key().replace("#", "_"));
                picker.requestFocus();
            } catch (IllegalArgumentException ex) {
            }
        } else {
            //picker.setValue(date);
            picker.setText(mf1.getDate_format().format(date));
        }
        cell_value = date;
    }

    public TableDateCellEditor(String sformat) {
        super();
        this.format_str = sformat;
    }

    private String getDateFormatStr(String format) {
        String result = format.replace("y", "#");
        result = result.replace("m", "#");
        result = result.replace("M", "#");
        result = result.replace("d", "#");
        result = result.replace("H", "#");
        result = result.replace("s", "#");
        result = result.replace("S", "#");
        result = result.replace("h", "#");
        return result;
    }

    @Override
    public Object getCellEditorValue() {
        commitEdit();
        return cell_value;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        cell_value = value;
        JPanel pnl = getPnlDateEditor(value);
        ComponentUtil.setRightEditComponent(table, row, column, picker);
        ComponentUtil.setRightEditComponent(table, row, column, btnSelect);
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
