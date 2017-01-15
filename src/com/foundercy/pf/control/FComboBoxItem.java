/*
 * filename:  FComboBoxItem.java
 *
 * Version: 1.0
 *
 * Date: 2006-1-10
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

/**
 * <p>Title: 下拉框值对象 </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: foundercy</p>
 * @author yangbo
 * @version 1.0
 */

public class FComboBoxItem
{
	/**
	 * 值
	 */
	private Object value;

	/**
	 * 对应值，待显示的字符串
	 */
	private String displayValue;


	/**
	 * 构造函数
	 * @param value 值
	 */
	public FComboBoxItem(Object value) {
		this(null != value?value.toString():"",value);
	}

	/**
	 * 构造函数
	 * 
	 * @param displayValue 对应值，待显示的字符串
	 * @param value 值
	 */
	public FComboBoxItem(String displayValue,Object value) {
		setDisplayValue(displayValue);
		setValue(value);
	}

	/**
	 * 取得值
	 * @return 值
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * 设置值
	 * @param value 值
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * 设置，对应值，待显示的字符串
	 * @param displayValue 对应值，待显示的字符串
	 */
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	/**
	 * 覆盖toString方法
	 * 
	 * @return 对应值，待显示的字符串
	 */
	public String toString() {
		return this.displayValue;
	}
}
