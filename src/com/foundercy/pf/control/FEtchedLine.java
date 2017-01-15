/*
 * $Id: FEtchedLine.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import javax.swing.JComponent;

/**
 * <p>
 * Title: 水平凹线控件
 * </p>
 * <p>
 * Description:水平凹线控件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 北京方正春元科技发展有限公司
 * </p>
 * <p>
 * Company: 北京方正春元科技发展有限公司
 * </p>
 * 
 * @author 黄节 2008年4月14日整理
 * @version $Revision: 1.1.1.1 $
 */
public class FEtchedLine extends JComponent implements Control {

	private static final long serialVersionUID = 8182334228402251650L;

	private String id;

	private Control parent;

	/**
	 * 创建一个水平凹线控件
	 */
	public FEtchedLine() {
		super();
		this.setBorder(new FEtchedBorder());
	}

	/**
	 * 创建一个水平线控件,并指定凹凸类型
	 * 
	 * @param etchType
	 *            可以是下面类型：凸起的－RAISED，不凸起－LOWERED
	 */
	public FEtchedLine(int etchType) {
		super();
		this.setBorder(new FEtchedBorder(etchType));
	}

	/**
	 * 得到控件id
	 * 
	 * @return 按钮id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 设置控件id
	 * 
	 * @param id
	 *            按钮id
	 */
	public void setId(String id) {
		this.id = id;
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
