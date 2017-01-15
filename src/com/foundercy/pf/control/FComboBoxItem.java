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
 * <p>Title: ������ֵ���� </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: foundercy</p>
 * @author yangbo
 * @version 1.0
 */

public class FComboBoxItem
{
	/**
	 * ֵ
	 */
	private Object value;

	/**
	 * ��Ӧֵ������ʾ���ַ���
	 */
	private String displayValue;


	/**
	 * ���캯��
	 * @param value ֵ
	 */
	public FComboBoxItem(Object value) {
		this(null != value?value.toString():"",value);
	}

	/**
	 * ���캯��
	 * 
	 * @param displayValue ��Ӧֵ������ʾ���ַ���
	 * @param value ֵ
	 */
	public FComboBoxItem(String displayValue,Object value) {
		setDisplayValue(displayValue);
		setValue(value);
	}

	/**
	 * ȡ��ֵ
	 * @return ֵ
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * ����ֵ
	 * @param value ֵ
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * ���ã���Ӧֵ������ʾ���ַ���
	 * @param displayValue ��Ӧֵ������ʾ���ַ���
	 */
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	/**
	 * ����toString����
	 * 
	 * @return ��Ӧֵ������ʾ���ַ���
	 */
	public String toString() {
		return this.displayValue;
	}
}
