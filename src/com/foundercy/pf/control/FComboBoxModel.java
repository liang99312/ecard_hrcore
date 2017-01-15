/*
 * filename:  FComboBoxModel.java
 *
 * Version: 1.0
 *
 * Date: 2006-1-10
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import javax.swing.ComboBoxModel;

/**
 * <p>Title: 下拉框数据模型 </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: foundercy</p>
 * @author yangbo
 * @version 1.0
 */
public interface FComboBoxModel extends RefModel ,ComboBoxModel
{
	/**
	 * 得到被选中列表项的值
	 * @return 列表项的值
	 */
	public Object getSelectedRealValue();

	
	/**
	 * 根据列表项的索引，得到列表项的值
	 * @param index 列表项的索引
	 * @return 列表项的值
	 */
	public Object getRealValueAt(int index);
}
