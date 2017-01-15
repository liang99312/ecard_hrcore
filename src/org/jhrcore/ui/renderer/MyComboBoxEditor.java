/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.renderer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.jhrcore.util.ColorUtil;

/**
 *
 * @author wangzhenhua
 */
public class MyComboBoxEditor implements ComboBoxEditor {

    private JTextField text_field = new JTextField();
    private Object value;

    public JTextField getText_field() {
        return text_field;
    }

    public MyComboBoxEditor() {
        text_field.setEditable(false);
        text_field.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        text_field.setForeground(ColorUtil.commForegroundColor);
        text_field.setBackground(ColorUtil.commBackgroundColor);
        text_field.setOpaque(true);
    }

    public void setItem(Object anObject) {
        if (anObject != null) {
            text_field.setText(anObject.toString());
        } else {
            text_field.setText("");
        }
        value = anObject;
    }

    public Component getEditorComponent() {
        text_field.setEditable(false);
        text_field.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
//        text_field.setForeground(Color.BLACK);
//        text_field.setBackground(Color.WHITE);
        return text_field;
    }

    public Object getItem() {
        return value;
    }

    public void selectAll() {
        text_field.selectAll();
    }

    public void addActionListener(ActionListener l) {
        text_field.addActionListener(l);
    }

    public void removeActionListener(ActionListener l) {
        text_field.removeActionListener(l);
    }
}
