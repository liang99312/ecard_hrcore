/*
 * filename:  FTextField.java
 *
 * Version: 1.0
 *
 * Date: 2005-12-30
 *
 * Copyright notice:  2005 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * <p>
 * Title: ����+�ı���ؼ�
 * </p>
 * <p>
 * Description:����+�ı���ؼ�
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
public class FTextField extends AbstractDataField {
	/**
	 * ��汾���
	 */
	private static final long serialVersionUID = 2858042143456372416L;

	private LengthSupportTextField textField;

	/**
	 * ��������
	 */
	public static final String LEFT = "left";

	/**
	 * ��������
	 */
	public static final String RIGHT = "right";

	/**
	 * ��������
	 */
	public static final String CENTER = "center";

	private int maxLength = -1; // �ı����������󳤶ȣ�-1Ϊ������

	/**
	 * ���캯��
	 */
	public FTextField() {
		this(null);
	}

	/**
	 * ���캯��
	 * 
	 * @param sTitle
	 *            �ؼ�����
	 */
	public FTextField(String sTitle) {
		super.setTitle(sTitle);
		allLayout();

	}

	/**
	 * ���캯��
	 * 
	 * @param titleVisible
	 *            �����Ƿ�ɼ�
	 */
	public FTextField(boolean titleVisible) {
		super.setTitleVisible(titleVisible);
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
	public FTextField(String sTitle, int maxLength) {
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
	 * �ѽ����������������
	 */
	public void requestFocus() {
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
				textField = new LengthSupportTextField(this.maxLength);
			} else {
				textField = new LengthSupportTextField();
			}

			textField.getDocument().addDocumentListener(new DocumentListener() {

				public void changedUpdate(DocumentEvent e) {
					FTextField.this.fireValueChange(null, textField.getText());
				}

				public void insertUpdate(DocumentEvent e) {
					FTextField.this.fireValueChange(null, textField.getText());
				}

				public void removeUpdate(DocumentEvent e) {
					FTextField.this.fireValueChange(null, textField.getText());

				}

			});
			// lgc add start 07-4-28
			textField.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						textField.transferFocus();
					}
				}

			});

			// lgc add end 07-4-28

		}

		textField.setColumns(8);
		return textField;
	}

	/**
	 * �����ı����е����ֶ��뷽ʽ
	 * 
	 * @param alignment
	 *            FTextField.LEFT������룻FTextField.RIGHT���Ҷ��룻FTextField.CENTER�����ж���
	 * 
	 */
	public void setAlignment(String alignment) {
		if (alignment == null)
			return;
		if (alignment.equalsIgnoreCase(LEFT)) {
			this.textField.setHorizontalAlignment(JTextField.LEFT);
		}
		if (alignment.equalsIgnoreCase(RIGHT)) {
			this.textField.setHorizontalAlignment(JTextField.RIGHT);
		}
		if (alignment.equalsIgnoreCase(CENTER)) {
			this.textField.setHorizontalAlignment(JTextField.CENTER);
		}
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
	 *            ���ÿؼ���ֵ
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
		return this.textField.getText().trim();
	}

}
