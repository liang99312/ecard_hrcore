/*
 * filename:  RefModel.java
 *
 * Version: 1.0
 *
 * Date: 2006-1-17
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

/**
 * <p>Title: 基本数据模型接口 </p>
 * <p>Description: 所有基本数据模型将实现该接口 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: foundercy</p>
 * @author yangbo
 * @version 1.0
 */

public interface RefModel
{
	
	  /**
	   * 由名字字符串，得到对应的值
	   *
	   * @param name 名字字符串
	   * @return 名字字符串，对应的值
	   */
	  public Object[] getValueByName(String name);

	  /**
	   * 由值，得到对应的名字字符串
	   *
	   * @param value 值
	   * @return 值，对应的名字字符串
	   */
	  public String getNameByValue(Object value);
	  
	  
}
