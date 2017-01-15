package com.foundercy.pf.control;

import java.util.EventObject;

/**
 * 控件的值，发生改变时，触发的值改变事件。
 * 本类是该事件的事件对象
 */

public class ValueChangeEvent extends EventObject {


	/**
	 * 
	 */
	private static final long serialVersionUID = -662976116034944952L;

	//值改变时，改变前的旧值
	private Object oldValue = null;

	//值改变时，改变后的新值
	private Object newValue = null;


//-----构造函数
	/**
	 * 构造函数
	 * 
	 * @param source 触发事件的控件
	 */
	public ValueChangeEvent(Object source) {
		this(source,null,null);
	}

	/**
	 * 构造函数
	 * 
	 * @param source 触发事件的控件
	 * @param oldValue 值改变时，改变前的旧值
	 * @param newValue 值改变时，改变后的新值
	 */
	public ValueChangeEvent(Object source,Object oldValue,Object newValue) {
		super(source);

		setOldValue(oldValue);
		setNewValue(newValue);		
	}

/**
 * 
 * 设置改变前的旧值
 * @param oldValue 改变前的旧值
 */
public void setOldValue(Object oldValue) {
	this.oldValue = oldValue;
}

	/*
	 * 取得改变前的旧值
	 * 
	 * @return 改变前的旧值
	 */
	public final Object getOldValue() {
		return this.oldValue;
	}

	/*
	 * 设置改变后的新值
	 * 
	 * @param newValue 改变后的新值
	 */
	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}

	/*
	 * 取得改变后的新值
	 * 
	 * @return 改变后的新值
	 */
	public final Object getNewValue() {
		return this.newValue;
	}

}
