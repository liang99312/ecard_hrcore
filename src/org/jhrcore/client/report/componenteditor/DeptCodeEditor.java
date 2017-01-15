/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.client.report.componenteditor;

import com.fr.design.gui.core.componenteditor.Editor;
import com.fr.cell.core.layout.LayoutFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JTextField;
import org.jhrcore.client.UserContext;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.ui.DeptSelectDlg;

/**
 *
 * @author Administrator
 */
public class DeptCodeEditor extends Editor {

    public DeptCodeEditor() {
        this(((String) (null)), "部门编码");
    }

    public DeptCodeEditor(Object obj) {
        this(obj, "部门编码");
    }

    public DeptCodeEditor(Object obj, String s) {
        oldValue = "";
        setLayout(LayoutFactory.createBorderLayout());
        textField = new JTextField();
        add(textField, "Center");
        add(btn_sel, "East");
        textField.addKeyListener(textKeyListener);
        btn_sel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DeptSelectDlg dlg = new DeptSelectDlg(UserContext.getDepts(false));
                ContextManager.locateOnScreenCenter(dlg);
                dlg.setVisible(true);
                if (dlg.isClick_ok()) {
                    String s = dlg.getCurDept().getDept_code();
                    textField.setText(s);
                }
            }
        });
        setValue(obj);
        setName(s);
    }

    public int getHorizontalAlignment() {
        return textField.getHorizontalAlignment();
    }

    public void setHorizontalAlignment(int i) {
        textField.setHorizontalAlignment(i);
    }

    public Object getValue() {
        return textField.getText();
    }

    public void setValue(Object obj) {
        if (obj == null) {
            obj = new String("");
        }
        oldValue = obj.toString();
        textField.setText(oldValue);
    }

    public void setEnabled(boolean flag) {
        textField.setEditable(flag);
    }

    public void requestFocus() {
        textField.requestFocus();
    }

    public JTextField getTextField() {
        return textField;
    }

    public boolean accept(Object obj) {
        return obj instanceof String;
    }
    KeyListener textKeyListener = new KeyListener() {

        public void keyTyped(KeyEvent keyevent) {
        }

        public void keyPressed(KeyEvent keyevent) {
        }

        public void keyReleased(KeyEvent keyevent) {
            int i = keyevent.getKeyCode();
            if (i == 27) {
                textField.setText(oldValue);
            } else if (i == 10) {
                fireEditingStopped();
            }
        }
    };
    private JButton btn_sel = new JButton("...");
    private JTextField textField;
    private String oldValue;
}
