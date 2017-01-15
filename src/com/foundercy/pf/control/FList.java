/*
 * $Id: FList.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import java.util.Vector;

import javax.swing.JList;
import javax.swing.ListModel;

/**
 * <p>
 * Title: 下拉列表控件
 * </p>
 * <p>
 * Description:下拉列表控件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 北京方正春元科技发展有限公司
 * </p>
 * <p>
 * Company: 北京方正春元科技发展有限公司
 * </p>
 * 
 * @author 黄节 2008年4月10日整理
 * @version $Revision: 1.1.1.1 $
 * 
 */
public class FList extends JList implements Control {

	private static final long serialVersionUID = 1967354635353999704L;

	private String id;

	private Control parent;

	/**
	 * 构造函数
	 */
	public FList() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param dataModel
	 *            数据模型
	 */
	public FList(ListModel dataModel) {
		super(dataModel);
	}

	/**
	 * 构造函数
	 * 
	 * @param listData
	 *            列表数据
	 */
	public FList(final Object[] listData) {
		super(listData);
	}

	/**
	 * 构造函数
	 * 
	 * @param listData
	 *            列表数据
	 */
	public FList(final Vector listData) {
		super(listData);
	}

	/**
	 * 设置控件ID
	 * 
	 * @param id
	 *            控件ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 得到控件ID
	 * 
	 * @return 控件ID
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 得到包含该控件的上一层控件
	 * 
	 * @return 控件对象
	 */
	public Control getParentControl() {
		return this.parent;
	}

	/**
	 * 设置上一层控件
	 * 
	 * @param parent
	 *            控件对象
	 */
	public void setParentControl(Control parent) {
		this.parent = parent;
	}

}
