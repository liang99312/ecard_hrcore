/*
 * filename:  FTimeChooser.java
 *
 * Version: 1.0
 *
 * Date: 2006-2-20
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.foundercy.pf.util.Resource;

/**
 * <p>
 * Title: 时间选择器控件
 * </p>
 * <p>
 * Description: 时间选择器控件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 北京方正春元科技发展有限公司
 * </p>
 * <p>
 * Company: 北京方正春元科技发展有限公司
 * </p>
 * 
 * @deprecated 被FTimeChooser替换
 * @author yangbo
 * @author 黄节 2008年4月17日整理
 * @version 1.0
 */
public class FDateTimeChooser extends AbstractRefDataField {
	private static final long serialVersionUID = -6802952323157063095L;

	private JTextField dateField;

	JButton btnTime = null;

	private DateTimeRefModel refModel = new DateTimeRefModel();

	private Format format = FormatManager.getDateTimeFormat();

	/**
	 * 构造函数
	 * 
	 */
	public FDateTimeChooser() {
		allLayout();
	}

	/**
	 * 构造函数
	 * 
	 * @param title
	 *            标题
	 */
	public FDateTimeChooser(String title) {
		super.setTitle(title);
		allLayout();
	}

	/**
	 * 得到当前日期存储格式
	 * 
	 * @return 格式信息
	 */
	public Format getFormat() {
		return this.format;
	}

	/**
	 * 设置当前日期存储格式
	 * 
	 * @param format
	 *            格式信息
	 */
	public void setFormat(Format format) {
		this.format = format;
	}

	/**
	 * 得到数据模型
	 * 
	 * @return 数据模型
	 */
	public RefModel getRefModel() {
		return refModel;
	}

	/**
	 * 设置数据模型
	 * 
	 * @param model
	 *            数据模型
	 */
	public void setRefModel(RefModel model) {
		this.refModel = (DateTimeRefModel) model;
	}

	/**
	 * 设置焦点为当前的控件
	 */
	protected void setFocusNow() {
		btnTime.requestFocus();
	}

	/**
	 * 得到当前实际操作的控件
	 * 
	 * @return 控件对象
	 */
	public JComponent getEditor() {
		dateField = new JTextField();
		// dateField.setEditable(false);
		// dateField.setDocument(new DateDocument(dateField));
		// dateField.setEditable(false);
		btnTime = new JButton(Resource.getImage("tree.gif"));
		Dimension timeSelecte = new Dimension(20, 20);
		btnTime.setPreferredSize(timeSelecte);
		btnTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// 得到旧值
				Object oldValue = getValue();
				FDateTimeSetDialog dlg = new FDateTimeSetDialog((JFrame) null,
						"日期设置窗口", true, oldValue);
				dlg.show();
				String temps = dlg.getNextDate();
				if (temps != null) {
					dateField.setText(temps);
					// try {
					// ((DateDocument)
					// dateField.getDocument()).isSetValue(true);
					// ((DateDocument) dateField.getDocument()).insertString(0,
					// temps, null);
					// ((DateDocument)
					// dateField.getDocument()).isSetValue(false);
					// } catch (Exception ex) {
					// ex.printStackTrace();
					// }
				}
				// 得到新值
				Object newValue = getValue();
				// 触发ValueChange事件
				fireValueChange(oldValue, newValue);
				//
			}
		});
		Box panelDate = Box.createHorizontalBox();

		Border border = dateField.getBorder();

		dateField.setBorder(null);
		panelDate.add(dateField);
		panelDate.add(btnTime);

		panelDate.setBorder(border);
		return panelDate;
	}

	/**
	 * 立即设置控件的值对象
	 * 
	 * @param value
	 *            要设置控件的值
	 */
	public void setValue(Object value) {

		refModel.setFormat(format);
		this.dateField.setText(value.toString());

	}

	/**
	 * 得到控件的值对象
	 * 
	 * @return 控件值对象
	 */
	public Object getValue() {
		if (this.dateField.getText() == null
				|| this.dateField.getText().equals("")) {
			return null;
		} else {
			try {
				// refModel.setFormat(format);
				// Object[] object =
				// refModel.getValueByName(this.dateField.getText());
				// return object[0];
				return this.dateField.getText();
			} catch (Exception ex) {
				return null;
			}
		}
	}

	/**
	 * 设置控件是否可编辑，由于父类JComponent无此方法，因此进行重载
	 * 
	 * @param editable
	 *            false为不可编辑，true为可编辑
	 */
	public void setEditable(boolean editable) {
		this.dateField.setEditable(editable);
	}

	/**
	 * 判断控件是否可以编辑
	 * 
	 * @return false为不可编辑，true为可编辑
	 */
	public boolean isEditable() {
		return this.dateField.isEditable();
	}

	/**
	 * 同时设置输入框和按钮为可用或不可用
	 * 
	 * @param enabled
	 *            false为不可用，true为可用
	 */
	public void setEnabled(boolean enabled) {
		this.dateField.setEditable(enabled);
		this.btnTime.setEnabled(enabled);

	}

}
