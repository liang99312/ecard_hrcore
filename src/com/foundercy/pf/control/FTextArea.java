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
 * Title: 标题+多行文本框控件
 * </p>
 * <p>
 * Description: 标题+多行文本框控件
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
public class FTextArea extends AbstractDataField {
	private static final long serialVersionUID = -3646192268014591383L;

	private JTextArea textArea;

	/**
	 * 构造函数
	 */
	public FTextArea() {
		allLayout();
	}

	/**
	 * 构造函数
	 * 
	 * @param sTitle
	 *            控件标题
	 */
	public FTextArea(String sTitle) {
		super.setTitle(sTitle);
		allLayout();
	}

	/**
	 * 构造函数
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
	 * 得到当前实际操作的控件
	 * 
	 * @return 控件对象
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
	 * 立即设置控件的值对象
	 * 
	 * @param value
	 *            要设置控件的值
	 */
	public void setValue(Object value) {
		if (value == null) {
			textArea.setText(null);
		} else {
			textArea.setText(value.toString());
		}
	}

	/**
	 * 得到控件的值对象
	 * 
	 * @return 控件值对象
	 */
	public Object getValue() {
		return textArea.getText();
	}

	/**
	 * 设置编辑框是否可用
	 * 
	 * @param enabled
	 *            false为不可编辑，true为可编辑
	 */
	public void setEnabled(boolean enabled) {
		this.textArea.setEnabled(enabled);
	}

	/**
	 * 获取编辑框是否可用
	 * 
	 * @return false为不可编辑，true为可编辑
	 */
	public boolean isEnabled() {
		return this.textArea.isEnabled();
	}

	/**
	 * 设置编辑框是否自动换行
	 * 
	 * @param lineWrap
	 *            false为不自动换行，true为自动换行
	 */
	public void setLineWrap(boolean lineWrap) {
		textArea.setLineWrap(lineWrap);
	}

	/**
	 * 获取编辑框是否自动换行
	 * 
	 * @return false为不自动换行，true为自动换行
	 */
	public boolean isLineWrap() {
		return textArea.getLineWrap();
	}

	/**
	 * 判断控件是否可以编辑
	 * 
	 * @return 0为不可编辑，1为可编辑
	 */
	public boolean isEditable() {
		return this.textArea.isEditable();
	}

	/**
	 * 设置控件是否可编辑
	 * 
	 * @param editable
	 *            0为不可编辑，1为可编辑
	 */
	public void setEditable(boolean editable) {
		this.textArea.setEditable(editable);
		if (!editable)
			this.textArea.setBackground(UIManager.getColor("Panel.background"));
	}

}
