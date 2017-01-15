/*
 * filename:  UIControl.java
 *
 * Version: 1.0
 *
 * Date: 2005-12-28
 *
 * Copyright notice:  2005 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import java.awt.Color;

/**
 * <p>Title: 可视控件接口 </p>
 * <p>Description: 所有的可视控件都实现该接口 </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: foundercy</p>
 * @author yangbo
 * @version 1.0
 */
public interface UIControl extends Control
{
	/**
	 * 得到控件的标题
	 * @return 标题内容
	 */
	String getTitle();
	
	/**
	 * 设置控件标题
	 * @param title 标题内容
	 */
	void setTitle(String title);
	
	/**
	 * 判断标题是否可见
	 * @return 0表示不可见，1表示可见 
	 */
	boolean isTitleVisible();
	
	/**
	 * 设置标题是否可见
	 * @param visible 0表示不可见，1表示可见
	 */
	void setTitleVisible(boolean visible);
	
	/**
	 * 得到标题的颜色
	 * @return #RRGGBB颜色字符串
	 */
	Color getTitleColor();
	
	/**
	 * 设置标题的颜色
	 * @param color #RRGGBB颜色字符串
	 */
	void setTitleColor(String color);
	
	/**
	 * 判断是否必须录入
	 * @return
	 */
	boolean is_must_input();
	
	/**
	 * 设置是否必须录入
	 * @param is_must_input
	 */
	void setIs_must_input(boolean is_must_input);
}
