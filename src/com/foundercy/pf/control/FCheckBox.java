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
 * Title: ����+��ѡ��ؼ�
 * </p>
 * <p>
 * Description:����+��ѡ��ؼ�
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
public class FCheckBox extends AbstractDataField {
	private static final long serialVersionUID = 5612417387837779197L;

	private JCheckBox checkBox;

	private Object checkedValue = new Boolean(true); // ��ѡ��ʱ��ֵΪTrue

	private Object unCheckedValue = new Boolean(false); // δѡ��ʱֵΪfalse

	/**
	 * ���캯��
	 */
	public FCheckBox() {
		allLayout();
	}

	/**
	 * ���캯��
	 * 
	 * @param sTitle
	 *            �ؼ�����
	 */
	public FCheckBox(String sTitle) {
		super.setTitle(sTitle);
		allLayout();
	}

	/**
	 * ���õ�ǰ����Ϊ�ÿؼ�
	 */
	protected void setFocusNow() {
		checkBox.requestFocus();
	}

	/**
	 * �õ���ǰʵ�ʲ����Ŀؼ�
	 * 
	 * @return �ؼ�����
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
	 * �������ÿؼ���ֵ���󣬲�������Ӧ��ֵ�ı����
	 * 
	 * @param value
	 *            Ҫ���ÿؼ���ֵ
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
	 * �õ��ؼ���ֵ����
	 * 
	 * @return �ؼ���ֵ����
	 */
	public Object getValue() {

		if (checkBox.isSelected())
			return this.checkedValue;
		else
			return this.unCheckedValue;

	}

	/**
	 * �жϱ༭���Ƿ����
	 * 
	 * @return falseΪ���ɱ༭��trueΪ�ɱ༭
	 */
	public boolean isEnabled() {
		return this.checkBox.isEnabled();
	}

	/**
	 * ���õ�ѡ���Ƿ����
	 * 
	 * @param enabled
	 *            falseΪ���ɱ༭��trueΪ�ɱ༭
	 */
	public void setEnabled(boolean enabled) {
		this.checkBox.setEnabled(enabled);
	}

	/**
	 * �жϿؼ��Ƿ���Ա༭
	 * 
	 * @return falseΪ���ɱ༭��trueΪ�ɱ༭
	 */
	public boolean isEditable() {
		return this.isEnabled();
	}

	/**
	 * ���ÿؼ��Ƿ�ɱ༭
	 * 
	 * @param editable
	 *            falseΪ���ɱ༭��trueΪ�ɱ༭
	 */
	public void setEditable(boolean editable) {
		this.setEnabled(editable);
	}

}