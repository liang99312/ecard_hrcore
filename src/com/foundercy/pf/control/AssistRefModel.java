/*
 * filename:  AssistRefModel.java
 *
 * Version: 1.0
 *
 * Date: 2006-1-17
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import java.util.List;

/**
 * <p>Title: 辅助录入抽象类 </p>
 * <p>Description: 辅助录入输入框控件从此派生 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: foundercy</p>
 * @author yangbo
 * @version 1.0
 */

public abstract class AssistRefModel implements RefModel
{
	public AssistRefModel()
	{
		
	}
	
	/**
	 * 得到所有待显示数据
	 * @return 数据数组
	 */
	 abstract protected List getData();
}
