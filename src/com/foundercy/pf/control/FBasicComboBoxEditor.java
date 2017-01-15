/*
 * @(#)BasicComboBoxEditor.java	1.25 03/01/23
 *
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.foundercy.pf.control;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Method;

import javax.swing.ComboBoxEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 * The default editor for editable combo boxes. The editor is implemented as a JTextField.
 * 重新继承ComboBoxEditor类,是为了解决表格中,可编辑的FComboBox接收TAB事件时,表格焦点失去问题.
 * 不要轻易废除该类.
 * @version 1.25 01/23/03
 * @author Arnaud Weber，方益
 * @author Mark Davidson
 */
public class FBasicComboBoxEditor implements ComboBoxEditor,FocusListener {
    protected JTextField editor;
    private Object oldValue;

    public FBasicComboBoxEditor() {
        editor = new BorderlessTextField("",9);
        editor.setBorder(null);
    }

    public Component getEditorComponent() {
        return editor;
    }

    /** 
     * Sets the item that should be edited. 
     *
     * @param anObject the displayed value of the editor
     */
    public void setItem(Object anObject) {
        if ( anObject != null )  {
            editor.setText(anObject.toString());
            
            oldValue = anObject;
        } else {
            editor.setText("");
        }
    }

    public Object getItem() {
        Object newValue = editor.getText();
        
        if (oldValue != null && !(oldValue instanceof String))  {
            // The original value is not a string. Should return the value in it's
            // original type.
            if (newValue.equals(oldValue.toString()))  {
                return oldValue;
            } else {
                // Must take the value from the editor and get the value and cast it to the new type.
                Class cls = oldValue.getClass();
                try {
                    Method method = cls.getMethod("valueOf", new Class[]{String.class});
                    newValue = method.invoke(oldValue, new Object[] { editor.getText()});
                } catch (Exception ex) {
                    // Fail silently and return the newValue (a String object)
                }
            }
        }
        return newValue;
    }

    public void selectAll() {
        editor.selectAll();
        editor.requestFocus();
    }

    // This used to do something but now it doesn't.  It couldn't be
    // removed because it would be an API change to do so.
    public void focusGained(FocusEvent e) {}
    
    // This used to do something but now it doesn't.  It couldn't be
    // removed because it would be an API change to do so.
    public void focusLost(FocusEvent e) {

    }

    public void addActionListener(ActionListener l) {
        editor.addActionListener(l);
    }

    public void removeActionListener(ActionListener l) {
        editor.removeActionListener(l);
    }

    class BorderlessTextField extends JTextField {
        public BorderlessTextField(String value,int n) {
            super(value,n);
            /**
             * 解决FComboBox每次开始编辑时,覆盖上次编辑的值.
             */
			this.addAncestorListener(new AncestorListener() {
				public void ancestorAdded(AncestorEvent event) {
					getEditorComponent().getParent().requestFocus();
					selectAll();
				}
				public void ancestorMoved(AncestorEvent event) {
				}

				public void ancestorRemoved(AncestorEvent event) {
				}
			});
			
			this.addKeyListener(new KeyAdapter(){
				public void keyTyped(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER
							|| e.getKeyChar() == '\n') {
						JTextField comp = (JTextField) getEditorComponent();
						if (comp.hasFocus()) {
							if (JTable.class.isAssignableFrom(BorderlessTextField.this
									.getParent().getParent().getClass())) {
								comp.getParent().getParent().requestFocus();
							} else
								comp.transferFocus();

						}
					}
				}
				
			});
        }

        // workaround for 4530952
        public void setText(String s) {
            if (getText().equals(s)) {
                return;
            }
            super.setText(s);
        }

        public void setBorder(Border b) {}
        
        protected void processKeyEvent(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_TAB){
				JTextField comp = (JTextField) getEditorComponent();
				if (comp.hasFocus()) {
					if (JTable.class.isAssignableFrom(BorderlessTextField.this
							.getParent().getParent().getClass())) {
						comp.getParent().getParent().requestFocus();
					} else
						comp.transferFocus();

				}
			}else {
				super.processKeyEvent(e);
			}
		}
        
    }

    
    /**
     * A subclass of BasicComboBoxEditor that implements UIResource.
     * BasicComboBoxEditor doesn't implement UIResource
     * directly so that applications can safely override the
     * cellRenderer property with BasicListCellRenderer subclasses.
     * <p>
     * <strong>Warning:</strong>
     * Serialized objects of this class will not be compatible with
     * future Swing releases. The current serialization support is
     * appropriate for short term storage or RMI between applications running
     * the same version of Swing.  As of 1.4, support for long term storage
     * of all JavaBeans<sup><font size="-2">TM</font></sup>
     * has been added to the <code>java.beans</code> package.
     * Please see {@link java.beans.XMLEncoder}.
     */
    public static class UIResource extends FBasicComboBoxEditor
    implements javax.swing.plaf.UIResource {
    }
}

