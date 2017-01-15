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
 * Title: 标题+文本框控件
 * </p>
 * <p>
 * Description:标题+文本框控件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 北京方正春元科技发展有限公司
 * </p>
 * <p>
 * Company: 北京方正春元科技发展有限公司
 * </p>
 * 
 * @author yangbo
 * @author 黄节 2008年4月10日整理
 * @version 1.0
 */
public class FTextField extends AbstractDataField {
	/**
	 * 类版本序号
	 */
	private static final long serialVersionUID = 2858042143456372416L;

	private LengthSupportTextField textField;

	/**
	 * 靠左排列
	 */
	public static final String LEFT = "left";

	/**
	 * 靠右排列
	 */
	public static final String RIGHT = "right";

	/**
	 * 居中排列
	 */
	public static final String CENTER = "center";

	private int maxLength = -1; // 文本框可输入最大长度，-1为无限制

	/**
	 * 构造函数
	 */
	public FTextField() {
		this(null);
	}

	/**
	 * 构造函数
	 * 
	 * @param sTitle
	 *            控件标题
	 */
	public FTextField(String sTitle) {
		super.setTitle(sTitle);
		allLayout();

	}

	/**
	 * 构造函数
	 * 
	 * @param titleVisible
	 *            标题是否可见
	 */
	public FTextField(boolean titleVisible) {
		super.setTitleVisible(titleVisible);
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
	public FTextField(String sTitle, int maxLength) {
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
	 * 把焦点设置在输入框上
	 */
	public void requestFocus() {
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
	 * 设置文本框中的文字对齐方式
	 * 
	 * @param alignment
	 *            FTextField.LEFT－左对齐；FTextField.RIGHT－右对齐；FTextField.CENTER－居中对齐
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
	 *            设置控件的值
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
		return this.textField.getText().trim();
	}

}
