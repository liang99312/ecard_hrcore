/*
 * filename:  AbstractRefDataField.java
 *
 * Version: 1.0
 *
 * Date: 2006-1-17
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;


/**
 * <p>Title: 带数据模型的控件抽象类 </p>
 * <p>Description: 标题+带数据模型的控件 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: foundercy</p>
 * @author yangbo
 * @version 1.0
 */

public abstract class AbstractRefDataField extends AbstractDataField
{
	/**
	 * 得到数据模型
	 * @return 数据值模型
	 */
	 public abstract  RefModel getRefModel();
	

}
