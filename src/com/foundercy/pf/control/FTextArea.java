/*
 * filename:  FTextArea.java
 *
 * Version: 1.0
 *
 * Date: 2005-12-30
 *
 * Copyright notice:  2005 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * <p>
 * Title: ����+�����ı���ؼ�
 * </p>
 * <p>
 * Description: ����+�����ı���ؼ�
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * <p>
 * Company: ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * 
 * @author yangbo
 * @author �ƽ� 2008��4��10������
 * @version 1.0
 */
public class FTextArea extends AbstractDataField {
	private static final long serialVersionUID = -3646192268014591383L;

	private JTextArea textArea;

	/**
	 * ���캯��
	 */
	public FTextArea() {
		allLayout();
	}

	/**
	 * ���캯��
	 * 
	 * @param sTitle
	 *            �ؼ�����
	 */
	public FTextArea(String sTitle) {
		super.setTitle(sTitle);
		allLayout();
	}

	/**
	 * ���캯��
	 * 
	 * @param titleVisible
	 */
	public FTextArea(boolean titleVisible) {
		super.setTitleVisible(titleVisible);
		allLayout();
	}

	protected void setFocusNow() {
		textArea.requestFocus();
	}

	/**
	 * �õ���ǰʵ�ʲ����Ŀؼ�
	 * 
	 * @return �ؼ�����
	 */
	public JComponent getEditor() {

		if (textArea == null) {
			textArea = new JTextArea();
			textArea.setLineWrap(true);
			textArea.getDocument().addDocumentListener(new DocumentListener() {

				public void changedUpdate(DocumentEvent e) {
					FTextArea.this.fireValueChange(null, textArea.getText());
				}

				public void insertUpdate(DocumentEvent e) {
					FTextArea.this.fireValueChange(null, textArea.getText());
				}

				public void removeUpdate(DocumentEvent e) {
					FTextArea.this.fireValueChange(null, textArea.getText());

				}

			});
			textArea.setLineWrap(true);
		}
		return textArea;
	}

	/**
	 * �������ÿؼ���ֵ����
	 * 
	 * @param value
	 *            Ҫ���ÿؼ���ֵ
	 */
	public void setValue(Object value) {
		if (value == null) {
			textArea.setText(null);
		} else {
			textArea.setText(value.toString());
		}
	}

	/**
	 * �õ��ؼ���ֵ����
	 * 
	 * @return �ؼ�ֵ����
	 */
	public Object getValue() {
		return textArea.getText();
	}

	/**
	 * ���ñ༭���Ƿ����
	 * 
	 * @param enabled
	 *            falseΪ���ɱ༭��trueΪ�ɱ༭
	 */
	public void setEnabled(boolean enabled) {
		this.textArea.setEnabled(enabled);
	}

	/**
	 * ��ȡ�༭���Ƿ����
	 * 
	 * @return falseΪ���ɱ༭��trueΪ�ɱ༭
	 */
	public boolean isEnabled() {
		return this.textArea.isEnabled();
	}

	/**
	 * ���ñ༭���Ƿ��Զ�����
	 * 
	 * @param lineWrap
	 *            falseΪ���Զ����У�trueΪ�Զ�����
	 */
	public void setLineWrap(boolean lineWrap) {
		textArea.setLineWrap(lineWrap);
	}

	/**
	 * ��ȡ�༭���Ƿ��Զ�����
	 * 
	 * @return falseΪ���Զ����У�trueΪ�Զ�����
	 */
	public boolean isLineWrap() {
		return textArea.getLineWrap();
	}

	/**
	 * �жϿؼ��Ƿ���Ա༭
	 * 
	 * @return 0Ϊ���ɱ༭��1Ϊ�ɱ༭
	 */
	public boolean isEditable() {
		return this.textArea.isEditable();
	}

	/**
	 * ���ÿؼ��Ƿ�ɱ༭
	 * 
	 * @param editable
	 *            0Ϊ���ɱ༭��1Ϊ�ɱ༭
	 */
	public void setEditable(boolean editable) {
		this.textArea.setEditable(editable);
		if (!editable)
			this.textArea.setBackground(UIManager.getColor("Panel.background"));
	}

}
