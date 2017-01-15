package com.foundercy.pf.control;

import java.util.List;

public interface Compound
{
	/**
	 * 得到子控件列表
	 * @return 子控件对象数组
	 */
	List getSubControls();
	
	/**
	 * 得到子控件
	 * @return 子控件对象
	 */
	Control getSubControl(String id);
	
	/**
	 * 增加子控件
	 * @param control
	 */
	void addControl(Control control);
	
	/**
	 * 增加子控件,同时增加约束
	 * @param control
	 */
	void addControl(Control control,Object contraint);
	
	
}
