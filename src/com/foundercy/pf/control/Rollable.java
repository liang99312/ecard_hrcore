/*
 * filename:  Rollable.java
 *
 * Version: 1.0
 *
 * Date: 2006-3-23
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

/**
 * <p>Title: 翻页控件抽象逻辑接口 </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: foundercy</p>
 * @author yangbo
 * @version 1.0
 */
public interface Rollable
{
	/**
	 * 到指定页
	 * @param pageIndex 页的序号，从1开始
	 * @param pageRows 每页的显示记录数
	 * @return 总记录数
	 */
	public int jump(int pageIndex, int pageRows); 
	
}
