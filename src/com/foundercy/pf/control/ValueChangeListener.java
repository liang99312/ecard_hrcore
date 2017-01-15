/**
 * 
 */
package com.foundercy.pf.control;

import java.util.EventListener;

/**
 * 控件的值，发生改变时，触发的值改变事件。
 * 本类是该事件的事件监听接口
 */

public interface ValueChangeListener extends EventListener {
	/**
	 * 控件的值发生了改变
	 * 
	 * @param vce 值改变事件的事件对象
	 */
	public abstract void valueChanged(ValueChangeEvent vce);
}

