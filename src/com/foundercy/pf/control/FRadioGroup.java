/*
 * filename:  FRadioGroup.java
 *
 * Version: 1.0
 *
 * Date: 2006-2-6
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */

package com.foundercy.pf.control;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JRadioButton;

import com.foundercy.pf.util.DealString;

/**
 * <p>
 * Title: 单选控件
 * </p>
 * <p>
 * Description: 标签+单选框控件的形式
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
public class FRadioGroup extends AbstractRefDataField {
	private static final long serialVersionUID = -6209855860782475193L;

	/**
	 * 垂直状态
	 */
	public final static int VERTICAL = 1; // 垂直状态

	/**
	 * 水平状态
	 */
	public final static int HORIZON = 0; // 水平状态

	private Box panel;

	private JRadioButton[] radios; // 单选按钮组

	private RadioModel model; // 数据模型

	private int layout = 0; // 默认是水平布局方式

	private Object oldValue = null;

	/**
	 * 构造函数
	 */
	public FRadioGroup() {
		allLayout();
	}

	/**
	 * 构造函数
	 * 
	 * @param title
	 *            控件标题
	 */
	public FRadioGroup(String title) {
		super.setTitle(title);
		allLayout();
	}

	/**
	 * 构造函数
	 * 
	 * @param title
	 *            标题
	 * @param radioType
	 *            编辑框部件中单选按钮布局方式,水平为FRadioGroup.HORIZON,垂直为FRadioGroup.VERTICAL
	 */
	public FRadioGroup(String title, int radioType) {
		this.layout = radioType;
		super.setTitle(title);
		allLayout();
	}

	/**
	 * 构造函数
	 * 
	 * @param title
	 *            标题
	 * @param radioType
	 *            编辑框部件中单选按钮布局方式,水平为FRadioGroup.HORIZON,垂直为FRadioGroup.VERTICAL
	 * @param modelString
	 */
	public FRadioGroup(String title, int radioType, String modelString) {
		this.layout = radioType;
		super.setTitle(title);
		allLayout();
		this.setRefModel(modelString);
	}

	/**
	 * 单选按钮是否可用
	 * 
	 * @return true为可用，false为不可用
	 */
	public boolean isEditable() {
		boolean editable = false;
		for (int i = 0; radios != null && i < radios.length; i++) {
			if (radios[i].isEnabled()) {
				editable = true;
			}
		}
		return editable;
	}

	/**
	 * 得到控件的值
	 * 
	 * @return 值对象
	 */
	public Object getValue() {
		for (int i = 0; radios != null && i < radios.length; i++) {
			if (radios[i].isSelected()) {
				return model.getValueAt(i);
			}
		}
		return null;
	}

	/**
	 * 设置按钮是否可编辑
	 * 
	 * @param editable
	 *            true为可编辑，false为不可编辑
	 */
	public void setEditable(boolean editable) {
		for (int i = 0; radios != null && i < radios.length; i++) {
			radios[i].setEnabled(editable);
		}
	}

	/**
	 * 设置光标
	 */
	protected void setFocusNow() {
		for (int i = 0; radios != null && i < radios.length; i++) {
			if (radios[i].isEnabled()) {
				radios[i].requestFocus();
			}
		}
	}

	/**
	 * 立即设置控件的值 子类在实现时，不必考虑当前线程，是否Swing事件处理线程
	 * 
	 * @param value
	 *            设置的值
	 */
	public void setValue(Object value) {
		if (model == null)
			return;
		// Object oldValue = this.getValue();
		for (int i = 0; radios != null && i < radios.length; i++) {
			if (model.getValueAt(i).toString().equals(value)) {
				if (!radios[i].isSelected())
					radios[i].setSelected(true);
			} else {
				if (radios[i].isSelected())
					radios[i].setSelected(false);
			}
		}
		// this.fireValueChange(oldValue,value);
	}

	/**
	 * 获得右侧控件
	 * 
	 * @return 右侧控件
	 */
	public JComponent getEditor() {
		if (panel == null) {
			if (this.getRadioLayout() == 0)
				panel = Box.createHorizontalBox();
			else
				panel = Box.createVerticalBox();

			panel.setOpaque(false);
			this.setRefModel(new RadioModel());
		}
		return panel;
	}

	/**
	 * 设置数据模型
	 * 
	 * @param model
	 *            新的数据模型
	 */
	public void setRefModel(RadioModel model) {
		this.model = model;
		resetUI();
	}

	/**
	 * 设置控件各子项的值和显示内容
	 * 
	 * @param modelString
	 *            由各子项的值和显示内容组成的字符串，如“1#赞成&2#否决&3#弃权”，
	 *            每一个子部件包括实际值和显示内容，用“#”分隔，各子部件之间用“&”分隔
	 */
	public void setRefModel(String modelString) {
		RadioModel m = new RadioModel();
		m.setData(DealString.buildString(modelString));
		this.setRefModel(m);
	}

	/**
	 * 重新设置UI
	 */
	private void resetUI() {
		if (this.getRefModel() == null)
			return;
		// Comments added on 2006-05-12
		// if (panel == null) {
		if (this.getRadioLayout() == 0)
			panel = Box.createHorizontalBox();
		else
			panel = Box.createVerticalBox();
		panel.setOpaque(false);
		// }else {
		// panel.removeAll();
		// }
		// ButtonGroup group = new ButtonGroup();
		radios = new JRadioButton[model.getRadioCount()];
		MyFocusAdapter fa = new MyFocusAdapter();
		for (int i = 0; i < model.getRadioCount(); i++) {
			radios[i] = new JRadioButton(model.getNameByValue(
					model.getValueAt(i)).toString());
			radios[i].setOpaque(false);
			radios[i].addFocusListener(fa);
			radios[i].setPreferredSize(new Dimension());
			panel.add(radios[i]);
			final int m = i;
			radios[i].addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent itemEvent) {
					if (itemEvent.getStateChange() == ItemEvent.DESELECTED) {
						// if(!radio.isSelected()) {
						Object oldValue = FRadioGroup.this.model.getValueAt(m);
						Object newValue = FRadioGroup.this.getValue();
						/**
						 * 修改单选按钮被选中时，再次点击此按钮，选中状态消失，改动此处
						 * 
						 * @author jerry
						 */
						/** *********************begin******************* */
						if (oldValue.equals("0") && newValue == null) {
							newValue = oldValue;
						}
						/** *********************end********************* */
						FRadioGroup.this.fireValueChange(oldValue, newValue);
						FRadioGroup.this.oldValue = newValue;
						// }
					}
					if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
						// Object oldValue = FRadioGroup.this.getValue();
						Object newValue = FRadioGroup.this.model.getValueAt(m);
						if (oldValue == null) {
							FRadioGroup.this
									.fireValueChange(oldValue, newValue);
						}
					}
				}
			});
		}

		for (int i = 0; radios != null && i < radios.length; i++) {
			final int m = i;
			radios[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					for (int j = 0; radios != null && j < radios.length; j++) {
						if (m != j) {
							if (radios[j].isSelected())
								radios[j].setSelected(false);
						}
						/**
						 * 修改单选按钮被选中时，再次点击此按钮，选中状态消失，改动此处
						 * 
						 * @author jerry
						 */
						/** *****************begin*************** */
						if (m == j) {
							radios[j].setSelected(true);
						}
						/** *****************end**************** */
					}

				}
			});
		}
		this.validate();
		// added on 2006-05-12
		this.removeAll();
		this.allLayout();
		//
	}

	/**
	 * 得到数据模型
	 * 
	 * @return 数据模型
	 */
	public RefModel getRefModel() {
		return this.model;
	}

	/**
	 * 得到布局方式
	 * 
	 * @return 布局方式
	 */
	public int getRadioLayout() {
		return this.layout;
	}

	/**
	 * 设置布局方式
	 * 
	 * @param layout
	 *            布局方式
	 */
	public void setRadioLayout(int layout) {
		this.layout = layout;
		this.resetUI();
	}

	/**
	 * 根据索引获得Button.
	 * 
	 * @param index
	 *            按钮序号
	 * @return 按钮对象
	 */
	public JRadioButton getRadioByIndex(int index) {
		if (radios == null || index >= radios.length || index < 0) {
			return null;
		} else {
			return radios[index];
		}
	}

	class MyFocusAdapter extends FocusAdapter {
		/**
		 * 丢失焦点时的响应事件
		 * 
		 * @param ev
		 *            焦点事件
		 * 
		 */
		public void focusLost(FocusEvent ev) {
			JRadioButton radio = (JRadioButton) ev.getSource();
			Object to = ev.getOppositeComponent();
			for (int i = 0; i < radios.length; i++) {
				if (radios[i] != radio && radios[i] == to)
					return;
			}
			fireDataFieldFocusLost(ev);
		}

		/**
		 * 获取焦点时的响应事件
		 * 
		 * @param ev
		 *            焦点事件
		 * 
		 */
		public void focusGained(FocusEvent ev) {
			JRadioButton radio = (JRadioButton) ev.getSource();
			Object from = ev.getOppositeComponent();
			for (int i = 0; i < radios.length; i++) {
				if (radios[i] != radio && radios[i] == from)
					return;
			}
			fireDataFieldFocusGained(ev);
		}
	}

	/**
	 * 为所有的按键设置前景色
	 * 
	 * @param color
	 *            要设置的颜色
	 * 
	 */
	public void setForeground(java.awt.Color color) {
		for (int i = 0; radios != null && i < radios.length; i++) {
			radios[i].setForeground(color);
		}
	}

	/**
	 * 得到按钮对象组
	 * 
	 * @return 按钮对象组
	 */
	public JRadioButton[] getRadios() {
		return radios;
	}
}
