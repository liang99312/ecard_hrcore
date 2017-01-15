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
 * Title:���������
 * </p>
 * <p>
 * Description: ����+�ı���ؼ�
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * <p>
 * Company: ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * 
 * @author �ƽ� 2008��4��14������
 * @version 1.0
 */
public class FPasswordField extends AbstractDataField {

	private static final long serialVersionUID = 6610031647382759084L;

	private FJPassword textField;

	private int maxLength = -1; // �ı����������󳤶ȣ�-1Ϊ������

	/**
	 * ���캯��
	 */
	public FPasswordField() {
		allLayout();
	}

	/**
	 * ���캯��
	 * 
	 * @param sTitle
	 *            �ؼ�����
	 */
	public FPasswordField(String sTitle) {
		super.setTitle(sTitle);
		allLayout();
	}

	/**
	 * ���캯��
	 * 
	 * @param sTitle
	 *            �ؼ�����
	 * @param maxLength
	 *            ��󳤶�
	 */
	public FPasswordField(String sTitle, int maxLength) {
		super.setTitle(sTitle);
		this.maxLength = maxLength;
		allLayout();
	}

	/**
	 * �õ������������󳤶�
	 * 
	 * @return ��󳤶�
	 */
	public int getMaxLength() {
		return this.maxLength;
	}

	/**
	 * ���������������󳤶�
	 * 
	 * @param maxLength
	 *            ��󳤶�
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
		textField.setMaxLen(maxLength);
	}

	/**
	 * ���ý���Ϊ��ǰ�ؼ�
	 */
	protected void setFocusNow() {
		textField.requestFocus();
	}

	/**
	 * ȫѡ�ı�������
	 */
	public void selectAll() {
		textField.selectAll();
	}

	/**
	 * �õ���ǰʵ�ʲ����Ŀؼ�
	 * 
	 * @return �ؼ�����
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
	 * ���ÿؼ��Ƿ�ɱ༭�����ڸ���JComponent�޴˷�������˽�������
	 * 
	 * @param editable
	 *            falseΪ���ɱ༭��trueΪ�ɱ༭
	 */
	public void setEditable(boolean editable) {
		this.textField.setEditable(editable);
	}

	/**
	 * �жϿؼ��Ƿ���Ա༭
	 * 
	 * @return falseΪ���ɱ༭��trueΪ�ɱ༭
	 */
	public boolean isEditable() {
		return this.textField.isEditable();
	}

	/**
	 * �������ÿؼ���ֵ����
	 * 
	 * @param value
	 *            �ؼ���ֵ����
	 */
	public void setValue(Object value) {
		if (value == null) {
			this.textField.setText(null);
		} else {
			this.textField.setText(value.toString());
		}
	}

	/**
	 * �õ��ؼ���ֵ����
	 * 
	 * @return �ؼ�ֵ����
	 */
	public Object getValue() {
		return this.getText();
	}

	/**
	 * ȡ���ı������ַ���
	 * 
	 * @return �ı������ַ���
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
	 * ʵ������������ַ����Ƚ��пؼ�����
	 */
	class FJPassword extends JPasswordField {

		private static final long serialVersionUID = 7361455466812355438L;

		private int thisLen = 0;

		private int maxLen = Integer.MAX_VALUE;

		/**
		 * ���캯��
		 */
		public FJPassword() {
			super();
		}

		/**
		 * ���캯��
		 * 
		 * @param cols
		 *            ������
		 * @param maxLen
		 *            ��󳤶�
		 */
		public FJPassword(int cols, int maxLen) {
			super(cols);
			this.maxLen = maxLen;
		}

		/**
		 * ���캯��
		 * 
		 * @param maxLen
		 *            ��󳤶�
		 */
		public FJPassword(int maxLen) {
			super(0);
			this.maxLen = maxLen;
		}

		/**
		 * ������󳤶�
		 * 
		 * @param max
		 *            ��󳤶�
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
