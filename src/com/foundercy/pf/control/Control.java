/*
 * filename:  Control.java
 *
 * Version: 1.0
 *
 * Date: 2005-12-28
 *
 * Copyright notice:  2005 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

/**
 * <p>Title: 控件基本接口 </p>
 * <p>Description: 平台中创建的控件均实现该接口 </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: foundercy</p>
 * @author yangbo
 * @version 1.0
 */
public interface Control
{
	/**
	 * 设置控件ID
	 * @param id 控件ID
	 */
	void setId(String id);
	
	/**
	 * 得到控件ID
	 * @return 控件ID
	 */
	String getId();
	
	/**
	 * 得到包含该控件的上一层控件
	 * @return 控件对象
	 */
	Control getParentControl();
	
	/**
	 * 设置上一层控件
	 * @param parent 控件对象
	 */
	void setParentControl(Control parent);	
}
