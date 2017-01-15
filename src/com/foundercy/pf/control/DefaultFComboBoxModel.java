/*
 * filename:  DefaultFComboBoxModel.java
 *
 * Version: 1.0
 *
 * Date: 2005-12-30
 *
 * Copyright notice:  2005 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListDataListener;

import com.foundercy.pf.util.DealString;

/**
 * <p>
 * Title: 下拉框数据模型
 * </p>
 * <p>
 * Description:下拉框数据模型
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005 北京方正春元科技发展有限公司
 * </p>
 * <p>
 * Company:北京方正春元科技发展有限公司
 * </p>
 * 
 * @author 黄节 2008年4月7日整理
 * @version 1.0
 */

public class DefaultFComboBoxModel implements FComboBoxModel {
	private DefaultComboBoxModel dcbm;

	private Vector fComboBoxItems;

	/**
	 * 构造函数
	 * 
	 */
	public DefaultFComboBoxModel() {
		this((Vector) null);
	}

	/**
	 * 构造函数
	 * 
	 * @param values
	 *            列表项的值列表
	 */
	public DefaultFComboBoxModel(Vector values) {
		this(values, (String[]) null);
	}

	/**
	 * 构造函数 列表项的值列表，与列表项的显示字符串列表，的顺序应该是一一对应的
	 * 
	 * @param values
	 *            列表项的值列表
	 * @param displayValues
	 *            列表项的显示字符串列表
	 */
	public DefaultFComboBoxModel(Vector values, String[] displayValues) {
		setRefModel(values, displayValues);
	}

	/**
	 * 构造函数
	 * 
	 * @param model
	 *            字符串参照模型
	 */
	public DefaultFComboBoxModel(String model) {
		setRefModel(model);
	}

	/**
	 * 设置，字符串参照模型
	 * 
	 * @param model
	 *            字符串参照模型
	 */
	public void setRefModel(String model) {

		if (null != model) {
			String[][] t = DealString.buildString(model);

			// 构造，列表项的值列表
			if (null != t) {
				int len = t.length;

				Vector values = new Vector(len);
				for (int i = 0; i < len; i++) {
					values.addElement(t[i][0]);
				}
				// 构造，列表项的显示字符串列表
				String[] displayValues = new String[len];

				for (int i = 0; i < len; i++) {
					displayValues[i] = t[i][1];
				}

				setRefModel(values, displayValues);
			}
		}
	}

	/**
	 * 设置参照模型
	 * 
	 * @param values
	 *            列表项的值列表
	 */
	public void setRefModel(Vector values) {
		setRefModel(values, (String[]) null);
	}

	/**
	 * 设置参照模型
	 * 
	 * @param values
	 *            列表项的值列表
	 * @param displayValues
	 *            列表项的显示字符串列表
	 */
	public void setRefModel(Vector values, String[] displayValues) {

		values = noneNullVector(values);

		if (null == displayValues) {
			displayValues = toDisplayValues(values);
		} else if (values.size() > displayValues.length) {
			throw new IllegalArgumentException(
					"the number of values is larger than the number of displayValues!");
		}

		if (null == this.dcbm) {
			this.dcbm = new DefaultComboBoxModel();
		} else {
			this.dcbm.removeAllElements();
		}

		int num = values.size();
		if (null == this.fComboBoxItems) {
			this.fComboBoxItems = new Vector(num);
		} else {
			this.fComboBoxItems.removeAllElements();
		}

		FComboBoxItem ncbi = null;
		for (int i = 0; i < num; i++) {
			ncbi = new FComboBoxItem(displayValues[i], values.elementAt(i));
			this.fComboBoxItems.addElement(ncbi);
			this.dcbm.addElement(ncbi);
		}
	}
    
	/**
	 * 由名字字符串，得到对应的值
	 * 
	 * @param name
	 *            名字字符串
	 * @return 名字字符串，对应的值
	 */
	public Object[] getValueByName(String name) {
		Vector v = new Vector();
		if (null != name && null != this.fComboBoxItems) {
			int num = this.fComboBoxItems.size();
			Object item = null;
			for (int i = 0; i < num; i++) {
				item = this.fComboBoxItems.elementAt(i);
				if (name.equals(toDisplayValue(item))) {
					v.addElement(toRealValue(item));
				}
			}
		}
		return 0 != v.size() ? v.toArray() : null;
	}

	/**
	 * 由值，得到对应的名字字符串
	 * 
	 * @param value
	 *            值
	 * @return 值，对应的名字字符串
	 */
	public String getNameByValue(Object value) {
		// 对于FComboBox特殊处理,如果没有找到则为用户录入的字符
		String rtn = null;
		if (value instanceof FComboBoxItem) {
			rtn = toDisplayValue(value);
		} else {
			if (null != this.fComboBoxItems) {
				int num = this.fComboBoxItems.size();
				Object item = null;
				for (int i = 0; i < num; i++) {
					item = this.fComboBoxItems.elementAt(i);
					if (isEqual(value, toRealValue(item))) {
						rtn = toDisplayValue(item);
						//break;
						return rtn;//modified by fangyi 2008-05-27
					}
				}
				//modified by fangyi 2008-05-27,to auto add the value/name to RefModel.
				if(value!=null && !value.equals("")) {
					this.insertElementAt(value,value.toString(),0);
					rtn = value.toString();
				}
			}
		}
		return rtn;
	}

	/**
	 * 得到被选中列表项的值
	 * 
	 * @return 列表项的值
	 */
	public Object getSelectedRealValue() {
		return toRealValue(this.dcbm.getSelectedItem());

	}

	/**
	 * 根据列表项的索引，得到列表项的值
	 * 
	 * @param index
	 *            列表项的索引
	 * @return 列表项的值
	 */
	public Object getRealValueAt(int index) {
		return toRealValue(this.dcbm.getElementAt(index));
	}

	// implements javax.swing.ComboBoxModel
	/**
	 * Set the value of the selected item. The selected item may be null.
	 * <p>
	 * 
	 * @param anItem
	 *            The combo box value or null for no selection.
	 */
	public void setSelectedItem(Object anItem) {
		FComboBoxItem ncbi = null;
		if (anItem instanceof FComboBoxItem) {
			ncbi = (FComboBoxItem) anItem;
		} else {
			ncbi = toFComboBoxItem(anItem);
		}
		//自动增加手工录入的内容
		if(anItem != null && ncbi == null && !anItem.equals("")){
			ncbi = new FComboBoxItem(anItem.toString(),anItem);
			this.fComboBoxItems.insertElementAt(ncbi,0);
			this.dcbm.insertElementAt(ncbi,0);
		}		
		this.dcbm.setSelectedItem(ncbi);
	}

	/**
	 * implements javax.swing.ComboBoxModel
	 * 
	 * @return 选中的选项
	 */
	public Object getSelectedItem() {
		// return toRealValue(this.dcbm.getSelectedItem());
		return this.dcbm.getSelectedItem();
	}

	/**
	 * implements javax.swing.ListModel
	 * 
	 * @param index
	 *            获取某个位置上的选项
	 * @return 返回某个选项
	 */
	public Object getElementAt(int index) {
		// return toRealValue(this.dcbm.getElementAt(index));
		return this.dcbm.getElementAt(index);
	}

	/**
	 * implements javax.swing.ListModel
	 * 
	 * @return 返回选项的个数
	 */
	public int getSize() {
		return this.dcbm.getSize();
	}

	/**
	 * Adds a listener to the list that's notified each time a change to the
	 * data model occurs.
	 * 
	 * @param l
	 *            the <code>ListDataListener</code> to be added
	 */
	public void addListDataListener(ListDataListener l) {
		this.dcbm.addListDataListener(l);
	}

	/**
	 * Removes a listener from the list that's notified each time a change to
	 * the data model occurs.
	 * 
	 * @param l
	 *            the <code>ListDataListener</code> to be removed
	 */
	public void removeListDataListener(ListDataListener l) {
		this.dcbm.removeListDataListener(l);
	}

	/** 由值，从保存列表项的Vector中，取得FComboBoxItem对象 */
	private FComboBoxItem toFComboBoxItem(Object value) {
		FComboBoxItem rtn = null;
		if (null != this.fComboBoxItems) {
			int num = this.fComboBoxItems.size();
			Object item = null;
			for (int i = 0; i < num; i++) {
				item = this.fComboBoxItems.elementAt(i);
				if (isEqual(value, toRealValue(item))) {
					rtn = (FComboBoxItem) item;
					break;
				}
			}
		}
		return rtn;
	}

	// 判断两个对象是否相等
	// 此处不使用Object.equals(Object o)方法的原因是：
	// 如果o1，o2中任意有一个为null时，Object.equals(Object o)方法会抛出空指针异常
	// 在此方法中,o1和o2都为null时，返回值为true，仅当有一个为null时，返回值为false
	// 当o1和o2都不为null时，使用o1.equals(o2)作为返回值
	private boolean isEqual(Object o1, Object o2) {
		return null != o1 ? (null != o2 ? o1.toString().trim().equals(
				o2.toString().trim()) : false) : (null == o2);
	}

	/** 由一个FComboBoxItem对象，得到对应的值 */
	private Object toRealValue(Object fComboBoxItem) {
		Object rtn = null;
		if (fComboBoxItem instanceof FComboBoxItem) {
			rtn = ((FComboBoxItem) fComboBoxItem).getValue();
		} else if (null != fComboBoxItem) {
			throw new IllegalArgumentException(
					"toRealValue:the type of item must be FComboBoxItem");
		}
		return rtn;
	}

	/** 由一个FComboBoxItem对象，得到对应的显示字符串 */
	private String toDisplayValue(Object fComboBoxItem) {
		String rtn = null;
		if (fComboBoxItem instanceof FComboBoxItem) {
			rtn = ((FComboBoxItem) fComboBoxItem).toString();
		} else if (null != fComboBoxItem) {
			throw new IllegalArgumentException(
					"toDisplayValue:the type of item must be FComboBoxItem");
		}
		return rtn;
	}

	/** 由列表项的值列表，调用toString()方法，生成列表项的显示字符串列表 */
	private String[] toDisplayValues(Vector values) {
		int num = values.size();
		String[] rtn = new String[num];
		Object o = null;
		for (int i = 0; i < num; i++) {
			o = values.elementAt(i);
			rtn[i] = null != o ? o.toString() : "";
		}
		return rtn;
	}

	/** 确保一个Vector不为null */
	private Vector noneNullVector(Vector v) {
		return null != v ? v : new Vector();
	}

	/**
	 * 在指定位置插入一项
	 * 
	 * @param value
	 * @param index
	 */
	public void insertElementAt(Object value, int index) {
		if (null == this.dcbm)
			this.dcbm = new DefaultComboBoxModel();
		if (null == this.fComboBoxItems)
			this.fComboBoxItems = new Vector();

		FComboBoxItem cbi = new FComboBoxItem(value);
		this.dcbm.insertElementAt(cbi, index);
		this.fComboBoxItems.insertElementAt(cbi, index);
	}

	/**
	 * 在指定位置插入一项
	 * 
	 * @param value
	 * @param displayValue
	 * @param index
	 */
	public void insertElementAt(Object value, String displayValue, int index) {
		if (null == this.dcbm)
			this.dcbm = new DefaultComboBoxModel();
		if (null == this.fComboBoxItems)
			this.fComboBoxItems = new Vector();

		FComboBoxItem cbi = new FComboBoxItem(displayValue, value);
		this.dcbm.insertElementAt(cbi, index);
		this.fComboBoxItems.insertElementAt(cbi, index);
	}

	/**
	 * 更改指定位置一项
	 * 
	 * @param newValue
	 *            要更改的选项的值，原始类型为String类型
	 * @param index
	 *            要更改的选项的位置
	 */
	public void updateElementAt(Object newValue, int index) {
		this.dcbm.removeElementAt(index);
		this.fComboBoxItems.removeElementAt(index);

		FComboBoxItem cbi = new FComboBoxItem(newValue);
		this.dcbm.insertElementAt(cbi, index);
		this.fComboBoxItems.insertElementAt(cbi, index);
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
	public void updateElementAt(Object newValue, String newDisplayValue,
			int index) {
		this.dcbm.removeElementAt(index);
		this.fComboBoxItems.removeElementAt(index);

		FComboBoxItem cbi = new FComboBoxItem(newDisplayValue, newValue);
		this.dcbm.insertElementAt(cbi, index);
		this.fComboBoxItems.insertElementAt(cbi, index);
	}

	/**
	 * 删除指定项目
	 * 
	 * @param anIndex
	 */
	public void removeElementAt(int anIndex) {
		this.dcbm.removeElementAt(anIndex);
		this.fComboBoxItems.removeElementAt(anIndex);
	}

	/**
	 * 删除指定位置一项
	 * 
	 * @param anObject
	 */
	public void removeElement(Object anObject) {
		this.dcbm.removeElement(anObject);
		this.fComboBoxItems.removeElement(anObject);
	}

	/**
	 * 删除所有项目
	 */
	public void removeAllElements() {
		this.dcbm.removeAllElements();
		this.fComboBoxItems.removeAllElements();
	}

	/**
	 * 得到所有项目个数
	 */
	public int getItemsCount() {
		return this.fComboBoxItems == null ? 0 : this.fComboBoxItems.size();
	}
}
