/*
 * filename:  FCheckBox.java
 *
 * Version: 1.0
 *
 * Date: 2005-12-30
 *
 * Copyright notice:  2005 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

/**
 * <p>
 * Title: 标题+单选框控件
 * </p>
 * <p>
 * Description:标题+单选框控件
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
public class FCheckBox extends AbstractDataField {
	private static final long serialVersionUID = 5612417387837779197L;

	private JCheckBox checkBox;

	private Object checkedValue = new Boolean(true); // 被选中时的值为True

	private Object unCheckedValue = new Boolean(false); // 未选中时值为false

	/**
	 * 构造函数
	 */
	public FCheckBox() {
		allLayout();
	}

	/**
	 * 构造函数
	 * 
	 * @param sTitle
	 *            控件标题
	 */
	public FCheckBox(String sTitle) {
		super.setTitle(sTitle);
		allLayout();
	}

	/**
	 * 设置当前焦点为该控件
	 */
	protected void setFocusNow() {
		checkBox.requestFocus();
	}

	/**
	 * 得到当前实际操作的控件
	 * 
	 * @return 控件对象
	 */
	public JComponent getEditor() {
		if (checkBox == null) {
			checkBox = new JCheckBox();
			checkBox.setOpaque(false);

			checkBox.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent ev) {
					fireDataFieldFocusLost(ev);
				}

				public void focusGained(FocusEvent ev) {
					fireDataFieldFocusGained(ev);
				}
			});
			checkBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent itemEvent) {
					if (ItemEvent.SELECTED == itemEvent.getStateChange()) {
						fireValueChange(unCheckedValue, checkedValue);

					} else if (ItemEvent.DESELECTED == itemEvent
							.getStateChange()) {
						fireValueChange(checkedValue, unCheckedValue);
					}
				}
			});
		}

		return checkBox;
	}

	/**
	 * 立即设置控件的值对象，并触发相应的值改变对象
	 * 
	 * @param value
	 *            要设置控件的值
	 */
	public void setValue(Object value) {
		if (!(value instanceof Boolean))
			value = new Boolean((String) value);
		if (value == null) {
			checkBox.setSelected(false);
			fireValueChange(checkedValue, unCheckedValue);
			return;
		}
		if (value.equals(checkedValue)) {
			checkBox.setSelected(true);
			fireValueChange(unCheckedValue, checkedValue);
			return;
		}
		if (value.equals(unCheckedValue))
			checkBox.setSelected(false);
		fireValueChange(unCheckedValue, unCheckedValue);
	}

	/**
	 * 得到控件的值对象
	 * 
	 * @return 控件的值对象
	 */
	public Object getValue() {

		if (checkBox.isSelected())
			return this.checkedValue;
		else
			return this.unCheckedValue;

	}

	/**
	 * 判断编辑框是否可用
	 * 
	 * @return false为不可编辑，true为可编辑
	 */
	public boolean isEnabled() {
		return this.checkBox.isEnabled();
	}

	/**
	 * 设置单选框是否可用
	 * 
	 * @param enabled
	 *            false为不可编辑，true为可编辑
	 */
	public void setEnabled(boolean enabled) {
		this.checkBox.setEnabled(enabled);
	}

	/**
	 * 判断控件是否可以编辑
	 * 
	 * @return false为不可编辑，true为可编辑
	 */
	public boolean isEditable() {
		return this.isEnabled();
	}

	/**
	 * 设置控件是否可编辑
	 * 
	 * @param editable
	 *            false为不可编辑，true为可编辑
	 */
	public void setEditable(boolean editable) {
		this.setEnabled(editable);
	}

}