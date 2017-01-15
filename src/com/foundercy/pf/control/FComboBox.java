/*
 * filename:  FComboBox.java
 *
 * Version: 1.0
 *
 * Date: 2005-12-30
 *
 * Copyright notice:  2005 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * <p>
 * Title: 标题+下拉框控件
 * </p>
 * <p>
 * Description:基本控件FCombox
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 北京方正春元科技发展有限公司
 * </p>
 * <p>
 * Company:北京方正春元科技发展有限公司
 * </p>
 * 
 * @author yangbo
 * @author 黄节 2008年4月7日整理
 * @version 1.0
 */

public class FComboBox extends AbstractRefDataField {

	private static final long serialVersionUID = -1L;

	/** 常量： 组合列表框不被选中的索引 */
	private static final int NONE_SELECTED_INDEX = 0;

	/**
	 * 用于用户选择的组合列表框
	 */
	private JComboBox comboBox;

	// comboBox编辑框
	private JTextField textField = null;

	// comboBox编辑框输入内容
	private String editText = "";

	/**
	 * 构造函数
	 */
	public FComboBox() {
		allLayout();
		setRefModel(new DefaultFComboBoxModel(""));
	}

	/**
	 * 构造函数
	 * 
	 * @param title
	 *            控件的显示标题
	 */
	public FComboBox(String title) {
		super.setTitle(title);
		allLayout();
		setRefModel(new DefaultFComboBoxModel(""));
	}

	/**
	 * 取得,参照模型
	 * 
	 * @return 参照模型
	 */
	public RefModel getRefModel() {
		return getModel();
	}

	/**
	 * 设置，字符串参照模型
	 * 
	 * @param modelString
	 *            字符串参照模型
	 */
	public void setRefModel(String modelString) {
		if (modelString != null && !modelString.trim().equals(""))
			setRefModel(new DefaultFComboBoxModel(modelString));
	}

	/**
	 * 设置参照模型
	 * 
	 * @param values
	 *            comboBox中的列表项的值
	 */
	public void setRefModel(Vector values) {
		setRefModel(values, (String[]) null);
	}

	/**
	 * 设置参照模型
	 * 
	 * @param values
	 *            comboBox中的列表项的值
	 * @param displayValues
	 *            comboBox中的列表项的，显示字符串
	 */
	public void setRefModel(Vector values, String[] displayValues) {
		setRefModel(new DefaultFComboBoxModel(values, displayValues));
	}

	/**
	 * 设置参照模型
	 * 
	 * @param dncbm
	 *            comboBox的参照模型
	 */
	public void setRefModel(FComboBoxModel dncbm) {

		FComboBoxModel model = ((null != dncbm) ? dncbm
				: (new DefaultFComboBoxModel()));

		this.comboBox.setModel(model);

		if (model.getSize() > 0)
			this.comboBox.setSelectedIndex(NONE_SELECTED_INDEX);
	}

	/**
	 * 得到简单控件中，用于用户输入的JComponent组件
	 * 
	 * @return 用于用户输入的JComponent组件
	 */
	public JComponent getEditor() {
		if (this.comboBox == null) {
			this.comboBox = new JComboBox();

			// 解决FComboBox在表格中编辑时,按TAB键,表格的焦点失去问题
			this.comboBox.setEditor(new FBasicComboBoxEditor());

			// textField = (JTextField)
			// comboBox.getEditor().getEditorComponent();
			// 监听，ValueChange事件
			this.comboBox.addItemListener(new ItemListener() {
				private Object oldValue;

				public void itemStateChanged(ItemEvent e) {
					if (ItemEvent.SELECTED == e.getStateChange()) {
						if (e.getItem() instanceof FComboBoxItem) {
							Object newValue = ((FComboBoxItem) e.getItem())
									.getValue();

							fireValueChange(oldValue, newValue);
						}
					} else if (ItemEvent.DESELECTED == e.getStateChange()) {
						try {
							JComboBox box = (JComboBox) e.getSource();
							if (box.getSelectedIndex() == -1) {
								textField.setText(editText);
							}
							oldValue = ((FComboBoxItem) e.getItem()).getValue();
						} catch (Exception ex) {
							oldValue = "";
						}
					}
				}
			});
			// this.comboBox.getEditor().getEditorComponent().addFocusListener(
			this.comboBox.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
				}

				public void focusLost(FocusEvent e) {
					// editText = textField.getText();
					if (comboBox.isEditable()) {
						JTextField field = (JTextField) comboBox.getEditor()
								.getEditorComponent();
						editText = field.getText();
					}
				}
			});

			// lgc add start 07-4-28
			this.comboBox.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER
							|| e.getKeyCode() == KeyEvent.VK_TAB) {
						comboBox.transferFocus();
					} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						comboBox.setPopupVisible(true);
					}

				}
			});
			/*
			 * this.textField.addKeyListener(new KeyAdapter() { public void
			 * keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			 * comboBox.transferFocus(); editText = textField.getText(); } } });
			 * this.textField.addAncestorListener(new AncestorListener() {
			 * public void ancestorAdded(AncestorEvent event) {
			 * comboBox.requestFocus(); textField.selectAll(); } public void
			 * ancestorMoved(AncestorEvent event) { }
			 * 
			 * public void ancestorRemoved(AncestorEvent event) { } });
			 */
		}

		// lgc add end 07-4-28

		return this.comboBox;
	}

	/**
	 * 得到控件的值
	 * 
	 * @return 控件的值
	 */
	public Object getValue() {
		// if (this.comboBox.getSelectedIndex() == -1) {
		// return this.editText;
		
		if (isEditable()) {
			// 取得ComboBox文本框中的值
			JTextField field = (JTextField) comboBox.getEditor()
					.getEditorComponent();
			String tempText = field.getText();
			Object[] values = this.getRefModel().getValueByName(tempText);
			// 如果在模型中找不到对应的值，则记忆到控件中；
			if (values == null || values.length == 0) {
				editText = tempText;
				DefaultFComboBoxModel model = (DefaultFComboBoxModel) this
						.getRefModel();
				model.setSelectedItem(editText);
				return editText;
			} else {
				return values[0];
			}
		} else {
			return getModel().getSelectedRealValue();
		}
	}

	/**
	 * 得到文本框中字符串
	 * 
	 * @return 文本框中字符串（String）
	 */
	public String getText() {
		/**
		 * modify 解决复选框无法得到在文本中输入的值
		 * 
		 * @author jerry 20071225 start
		 */
		return ((JTextField) this.comboBox.getEditor().getEditorComponent())
				.getText();
		// return this.getRefModel().getNameByValue(this.getValue());
		/**
		 * modify 解决复选框无法得到在文本中输入的值
		 * 
		 * @author jerry 20071225 end
		 */
	}

	/**
	 * 为文本框设置字符串
	 * 
	 * @param text
	 *            要设置的字符串
	 * 
	 */
	public void setText(String text) {
		((JTextField) this.comboBox.getEditor().getEditorComponent())
				.setText(text);
	}

	/** 得到comboBox的模型 */
	private FComboBoxModel getModel() {
		// return (FComboBoxModel) this.comboBox.getModel();
		if (this.comboBox.getModel() instanceof FComboBoxModel)
			return (FComboBoxModel) this.comboBox.getModel();
		else
			return null;
	}

	/**
	 * 立即设置控件的值，需要子类实现。 子类在实现时，不必考虑当前线程，是否Swing事件处理线程
	 * 
	 * @param value
	 *            设置的值
	 */
	public void setValue(Object value) {
		// add by liwenquan 20080415 begin
		try {
			// 解决传入非字符串类型的对象出现类型转换异常问题
			if (null != value) {
				this.editText = String.valueOf(value);
			} else {
				this.editText = "";
			}
			this.comboBox.getModel().setSelectedItem(value);
		} catch (Exception e) {
			this.editText = "";
		}

		// add by liwenquan 20080415 end

	}

	/**
	 * 根据选项的序号设置选择项目
	 * 
	 * @param index
	 *            选项的序号
	 */
	public void setSelectedIndex(int index) {
		this.comboBox.setSelectedIndex(index);
	}

	/**
	 * 获取选中的选项的序号
	 * 
	 * @return 选中的选项的序号
	 */
	public int getSelectedIndex() {
		return this.comboBox.getSelectedIndex();
	}

	/** 立即设置控件为当前输入焦点。* */
	public void setFocusNow() {
		this.comboBox.requestFocus();
	}

	/**
	 * 设置焦点到此控件上
	 */
	public void requestFocus() {
		this.comboBox.requestFocus();
	}

	/**
	 * 立即设置控件是否可用。
	 * 
	 * @param dataFieldEnabled
	 *            控件是否可用
	 */
	public void setEnabled(boolean dataFieldEnabled) {
		this.comboBox.setEnabled(dataFieldEnabled);
	}

	/**
	 * 取得控件是否可用
	 * 
	 * @return true，可用；false，不可用
	 */
	public boolean isEnabled() {
		return this.comboBox.isEnabled();
	}

	/**
	 * 设置控件是否可编辑，由于父类JComponent无此方法，因此进行重载
	 * 
	 * @param editable
	 *            0为不可编辑，1为可编辑
	 */
	public void setEditable(boolean editable) {
		this.comboBox.setEditable(editable);
	}

	/**
	 * 判断控件是否可以编辑
	 * 
	 * @return 0为不可编辑，1为可编辑
	 */
	public boolean isEditable() {
		return this.comboBox.isEditable();
	}

	/**
	 * 在指定位置插入一条选项
	 * 
	 * @param value
	 *            插入一条选项，原始类型为String类型
	 * @param index
	 *            要插入的位置
	 */
	public void insertItemAt(Object value, int index) {
		if (this.comboBox.getModel() instanceof DefaultFComboBoxModel) {
			((DefaultFComboBoxModel) this.comboBox.getModel()).insertElementAt(
					value, index);
		}
	}

	/**
	 * 在指定位置插入一条选项
	 * 
	 * @param value
	 *            插入的选项的值，原始类型为String类型
	 * @param displayValue
	 *            插入选项的显示值
	 * @param index
	 *            要插入的位置
	 */
	public void insertItemAt(Object value, String displayValue, int index) {
		if (this.comboBox.getModel() instanceof DefaultFComboBoxModel) {
			((DefaultFComboBoxModel) this.comboBox.getModel()).insertElementAt(
					value, displayValue, index);
		}
	}

	/**
	 * 更改指定位置一项
	 * 
	 * @param newValue
	 *            要更改的选项的值，原始类型为String类型
	 * @param index
	 *            要更改的选项的位置
	 */
	public void updateItemAt(Object newValue, int index) {
		if (this.comboBox.getModel() instanceof DefaultFComboBoxModel) {
			((DefaultFComboBoxModel) this.comboBox.getModel()).updateElementAt(
					newValue, index);
		}
	}

	/**
	 * 更改指定位置一项
	 * 
	 * @param newValue
	 *            要更改的选项的值，原始类型为String类型
	 * @param newDisplayValue
	 *            要显示的选项的值
	 * @param index
	 *            要更改的选项的位置
	 */
	public void updateItemAt(Object newValue, String newDisplayValue, int index) {
		if (this.comboBox.getModel() instanceof DefaultFComboBoxModel) {
			((DefaultFComboBoxModel) this.comboBox.getModel()).updateElementAt(
					newValue, newDisplayValue, index);
		}
	}

	/**
	 * 删除指定项目 注: 调用此方法会触发ValueChangedEvent
	 * 
	 * @param anObject
	 *            要删除的选项，原始类型为FComboBoxItem类型
	 */
	public void removeItem(Object anObject) {
		if (this.comboBox.getModel() instanceof DefaultFComboBoxModel) {
			((DefaultFComboBoxModel) this.comboBox.getModel())
					.removeElement(anObject);
		}
	}

	/**
	 * 删除指定位置一项 注: 调用此方法会触发ValueChangedEvent
	 * 
	 * @param anIndex
	 *            要删除的选项的序号
	 */
	public void removeItemAt(int anIndex) {
		if (this.comboBox.getModel() instanceof DefaultFComboBoxModel) {
			((DefaultFComboBoxModel) this.comboBox.getModel())
					.removeElementAt(anIndex);
		}
	}

	/**
	 * 删除所有项目
	 */
	public void removeAllItems() {
		if (this.comboBox.getModel() instanceof DefaultFComboBoxModel) {
			((DefaultFComboBoxModel) this.comboBox.getModel())
					.removeAllElements();
		}
	}

	/**
	 * 得到项目总数
	 * 
	 * @return 返回项目总数
	 */
	public int getItemsCount() {
		if (this.comboBox.getModel() instanceof DefaultFComboBoxModel) {
			return ((DefaultFComboBoxModel) this.comboBox.getModel())
					.getItemsCount();
		}
		return 0;
	}

	/**
	 * 返回当前所选项
	 * 
	 * @return 当前所选项
	 */
	public Object getSelectedItem() {
		return this.comboBox.getSelectedItem();
	}

	/**
	 * 将组合框显示区域中所选项设置为参数中的对象（从ComboBox中获取的对象）
	 * 
	 * @param obj
	 *            要显示的对象
	 */
	public void setSelectedItem(Object obj) {
		this.comboBox.setSelectedItem(obj);
	}

	/**
	 * 获得序号的选项
	 * 
	 * @param index
	 *            序号
	 * @return 返回获得的选项（FComboBoxItem）
	 */
	public Object getItemAt(int index) {
		return this.comboBox.getItemAt(index);
	}

	/**
	 * 将组合框显示区域中所选项设置为参数中的对象（自己构建的FComboBoxItem对象）
	 * 
	 * @param obj
	 *            要选中的选项，原始类型为FComboBoxItem类型
	 */
	public void setSelectedItemByNewItemValue(Object obj) {
		FComboBoxItem fbc = (FComboBoxItem) obj;
		FComboBoxModel model = (FComboBoxModel) this.comboBox.getModel();
		for (int i = 0; i < model.getSize(); i++) {
			if (fbc.getValue().toString().equals(
					((FComboBoxItem) this.getItemAt(i)).getValue().toString())) {
				this.setSelectedIndex(i);
			}
		}
	}

	/**
	 * 将组合框显示区域中所选项设置为参数中的对象（String对象）
	 * 
	 * @param str
	 *            要显示的选项的值
	 */
	public void setSelectedItemByNewItemValue(String str) {
		FComboBoxModel model = (FComboBoxModel) this.comboBox.getModel();
		for (int i = 0; i < model.getSize(); i++) {
			if (str.equals(((FComboBoxItem) this.getItemAt(i)).getValue()
					.toString())) {
				this.setSelectedIndex(i);
			}
		}
	}

}
