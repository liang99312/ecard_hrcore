/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import java.awt.Toolkit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author mxliteboss
 */
public class MaxLengthDocument extends PlainDocument {

    private int maxChars;

    public MaxLengthDocument(int max) {
        maxChars = max;
    }
    @Override
    public void insertString(int offset, String s, AttributeSet a) throws BadLocationException {
        if (getLength() + s.length() > maxChars) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        super.insertString(offset, s, a);
    }
}