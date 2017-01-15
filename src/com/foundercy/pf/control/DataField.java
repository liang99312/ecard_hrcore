/*
 * filename:  DataField.java
 *
 * Version: 1.0
 *
 * Date: 2005-12-28
 *
 * Copyright notice:  2005 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import javax.swing.JComponent;
;

/**
 * <p>Title: 基本控件接口 </p>
 * <p>Description: 标签+简单控件的形式 </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: foundercy</p>
 * @author yangbo
 * @version 1.0
 */
public interface DataField extends UIControl
{
	/**
	 * 判断控件是否可以编辑
	 * @return 0为不可编辑，1为可编辑
	 */
	boolean isEditable();
	
	/**
	 * 设置控件是否可编辑
	 * @param editable 0为不可编辑，1为可编辑
	 */
	void setEditable(boolean editable);
	
	/**
	 * 得到控件的值
	 * @return 控件的值对象
	 */
	Object getValue();
	
	/**
	 * 设置控件的值
	 * @param value 控件的值对象
	 */
	void setValue(Object value);
	
	/**
	 * 控件的显示是否自适应
	 * @return 0为非自适应，1为自适应
	 */
	boolean isTitleAdapting();
	
	/**
	 * 设置控件的显示是否自适应
	 * @param adapting 0为非自适应，1为自适应
	 */
	void setTitleAdapting(boolean adapting);
	
	/**
	 * 得到标题的显示位置
	 * @return "LEFT"表示标题靠左，"RIGHT"表示标题靠右
	 */
	String getTitlePosition();
	
	/**
	 * 设置标题的显示位置
	 * @param position "LEFT"表示标题靠左，"RIGHT"表示标题靠右
	 */
	void setTitlePosition(String position);
	
	/**
	 * 增加值改变监听器
	 * @param listener 值改变对象
	 */
	void addValueChangeListener(ValueChangeListener listener);
	
	/**
	 * 删除值改变监听器 
	 * @param listerner 值改变对象
	 */
	void removeValueChangeListener(ValueChangeListener listerner);
	
	/**
	 * 得到当前实际操作的控件
	 * @return 控件对象
	 */
	JComponent getEditor();
	
}
