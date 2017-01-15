/*
 * $Id: FPasswordField.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * <p>
 * Title:密码输入框
 * </p>
 * <p>
 * Description: 标题+文本框控件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 北京方正春元科技发展有限公司
 * </p>
 * <p>
 * Company: 北京方正春元科技发展有限公司
 * </p>
 * 
 * @author 黄节 2008年4月14日整理
 * @version 1.0
 */
public class FPasswordField extends AbstractDataField {

	private static final long serialVersionUID = 6610031647382759084L;

	private FJPassword textField;

	private int maxLength = -1; // 文本框可输入最大长度，-1为无限制

	/**
	 * 构造函数
	 */
	public FPasswordField() {
		allLayout();
	}

	/**
	 * 构造函数
	 * 
	 * @param sTitle
	 *            控件标题
	 */
	public FPasswordField(String sTitle) {
		super.setTitle(sTitle);
		allLayout();
	}

	/**
	 * 构造函数
	 * 
	 * @param sTitle
	 *            控件标题
	 * @param maxLength
	 *            最大长度
	 */
	public FPasswordField(String sTitle, int maxLength) {
		super.setTitle(sTitle);
		this.maxLength = maxLength;
		allLayout();
	}

	/**
	 * 得到允许输入的最大长度
	 * 
	 * @return 最大长度
	 */
	public int getMaxLength() {
		return this.maxLength;
	}

	/**
	 * 设置允许输入的最大长度
	 * 
	 * @param maxLength
	 *            最大长度
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
		textField.setMaxLen(maxLength);
	}

	/**
	 * 设置焦点为当前控件
	 */
	protected void setFocusNow() {
		textField.requestFocus();
	}

	/**
	 * 全选文本框内容
	 */
	public void selectAll() {
		textField.selectAll();
	}

	/**
	 * 得到当前实际操作的控件
	 * 
	 * @return 控件对象
	 */
	public JComponent getEditor() {
		if (textField == null) {
			if (this.maxLength > -1) {
				textField = new FJPassword(this.maxLength);
			} else {
				textField = new FJPassword();
			}

			textField.getDocument().addDocumentListener(new DocumentListener() {

				public void changedUpdate(DocumentEvent e) {
					FPasswordField.this.fireValueChange(null,
							FPasswordField.this.getText());
				}

				public void insertUpdate(DocumentEvent e) {
					FPasswordField.this.fireValueChange(null,
							FPasswordField.this.getText());
				}

				public void removeUpdate(DocumentEvent e) {
					FPasswordField.this.fireValueChange(null,
							FPasswordField.this.getText());

				}

			});
		}
		textField.setColumns(8);
		// lgc add start 07-4-28
		textField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					textField.transferFocus();
				}
			}

		});
		return textField;
	}

	/**
	 * 设置控件是否可编辑，由于父类JComponent无此方法，因此进行重载
	 * 
	 * @param editable
	 *            false为不可编辑，true为可编辑
	 */
	public void setEditable(boolean editable) {
		this.textField.setEditable(editable);
	}

	/**
	 * 判断控件是否可以编辑
	 * 
	 * @return false为不可编辑，true为可编辑
	 */
	public boolean isEditable() {
		return this.textField.isEditable();
	}

	/**
	 * 立即设置控件的值对象
	 * 
	 * @param value
	 *            控件的值对象
	 */
	public void setValue(Object value) {
		if (value == null) {
			this.textField.setText(null);
		} else {
			this.textField.setText(value.toString());
		}
	}

	/**
	 * 得到控件的值对象
	 * 
	 * @return 控件值对象
	 */
	public Object getValue() {
		return this.getText();
	}

	/**
	 * 取得文本框中字符串
	 * 
	 * @return 文本框中字符串
	 */
	public String getText() {
		char[] ca = this.textField.getPassword();
		if (ca != null && ca.length > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < ca.length; i++) {
				sb.append(ca[i]);
			}
			return sb.toString();
		}
		return "";
	}

	/**
	 * 实现输入对输入字符长度进行控件的类
	 */
	class FJPassword extends JPasswordField {

		private static final long serialVersionUID = 7361455466812355438L;

		private int thisLen = 0;

		private int maxLen = Integer.MAX_VALUE;

		/**
		 * 构造函数
		 */
		public FJPassword() {
			super();
		}

		/**
		 * 构造函数
		 * 
		 * @param cols
		 *            多少列
		 * @param maxLen
		 *            最大长度
		 */
		public FJPassword(int cols, int maxLen) {
			super(cols);
			this.maxLen = maxLen;
		}

		/**
		 * 构造函数
		 * 
		 * @param maxLen
		 *            最大长度
		 */
		public FJPassword(int maxLen) {
			super(0);
			this.maxLen = maxLen;
		}

		/**
		 * 设置最大长度
		 * 
		 * @param max
		 *            最大长度
		 * 
		 */
		public void setMaxLen(int max) {
			this.maxLen = max;
		}

		protected Document createDefaultModel() {
			return new LengthLimitedDocument();
		}

		class LengthLimitedDocument extends PlainDocument {

			private static final long serialVersionUID = -2422088376337511028L;

			/**
			 * Inserts some content into the document. Inserting content causes
			 * a write lock to be held while the actual changes are taking
			 * place, followed by notification to the observers on the thread
			 * that grabbed the write lock.
			 * <p>
			 * This method is thread safe, although most Swing methods are not.
			 * Please see <A
			 * HREF="http://java.sun.com/products/jfc/swingdoc-archive/threads.html">Threads
			 * and Swing</A> for more information.
			 * 
			 * @param offs
			 *            the starting offset >= 0
			 * @param str
			 *            the string to insert; does nothing with null/empty
			 *            strings
			 * @param a
			 *            the attributes for the inserted content
			 * @exception BadLocationException
			 *                the given insert position is not a valid position
			 *                within the document
			 * @see Document#insertString
			 */
			public void insertString(int offs, String str, AttributeSet a)
					throws BadLocationException {
				if (str == null || thisLen >= maxLen)
					return;
				if (thisLen + str.length() > maxLen)
					str = str.substring(0, maxLen - thisLen);
				thisLen += str.length();
				super.insertString(offs, str, a);
			}

			/**
			 * Removes some content from the document. Removing content causes a
			 * write lock to be held while the actual changes are taking place.
			 * Observers are notified of the change on the thread that called
			 * this method.
			 * <p>
			 * This method is thread safe, although most Swing methods are not.
			 * Please see <A
			 * HREF="http://java.sun.com/products/jfc/swingdoc-archive/threads.html">Threads
			 * and Swing</A> for more information.
			 * 
			 * @param offs
			 *            the starting offset >= 0
			 * @param len
			 *            the number of characters to remove >= 0
			 * @exception BadLocationException
			 *                the given remove position is not a valid position
			 *                within the document
			 * @see Document#remove
			 */
			public void remove(int offs, int len) throws BadLocationException {
				thisLen -= len;
				super.remove(offs, len);
			}
		}

	}

}
