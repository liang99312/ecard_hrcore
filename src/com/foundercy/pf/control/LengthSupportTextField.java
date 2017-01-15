package com.foundercy.pf.control;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;


/**
 * 实现输入对输入字符长度进行控件的类
 */
public class LengthSupportTextField extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2977940458740545418L;

	private int thisLen = 0;

	private int maxLen = Integer.MAX_VALUE;
	private transient boolean controlAddToTable = false;
	public LengthSupportTextField() {
		this.addAncestorListener(new AncestorListener(){
			public void ancestorAdded(AncestorEvent event) {
				if(getCaretPosition() == getDocument().getLength()) {
					selectAll();
				}
				controlAddToTable = true;
				requestFocus();
			}
			public void ancestorMoved(AncestorEvent event) {
			}
			public void ancestorRemoved(AncestorEvent event) {
			}
		});	
		this.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e) {
				if(getCaretPosition() == getDocument().getLength() && !controlAddToTable) {
					selectAll();
				}
				controlAddToTable = false;
			}
			public void focusLost(FocusEvent e) {
				setCaretPosition(getDocument().getLength());
			}
		});
	}
	public LengthSupportTextField(int maxLen) {
		this();
		this.maxLen = maxLen;
	}

	public void setMaxLen(int max) {
		this.maxLen = max;
	}

	protected Document createDefaultModel() {
		return new LengthLimitedDocument();
	}

	class LengthLimitedDocument extends PlainDocument {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3249533759349569986L;

		public void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException {
			if (str == null || thisLen >= maxLen
					|| thisLen + str.getBytes().length > maxLen) {
				return;
			}
			thisLen += str.getBytes().length;//避免中文问题
			super.insertString(offs, str, a);
		}

		public void remove(int offs, int len) throws BadLocationException {
			super.remove(offs, len);
			//必须放在删除后才能正常获取长度
			thisLen = getText(0, getLength()).getBytes().length;
		}
	}

}
