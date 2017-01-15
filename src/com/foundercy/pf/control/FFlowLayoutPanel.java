/*
 * $Id: FFlowLayoutPanel.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.foundercy.pf.control.util.SubControlFinder;

/**
 * <p>
 * Title: 流布局控件容器
 * </p>
 * <p>
 * Description: 流布局控件容器
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
public class FFlowLayoutPanel extends JPanel implements Control, Compound {

	private static final long serialVersionUID = -3220036536406033847L;

	private String id;

	private Control parent;

	private java.util.List subControls = new ArrayList();

	/**
	 * 构造函数
	 */
	public FFlowLayoutPanel() {
		super();
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 4));
	}

	/**
	 * 增加子控件
	 * 
	 * @param control
	 *            FFlowLayoutPanel中的子控件
	 */
	public void addControl(Control control) {
		this.addControl(control, null);
	}

	/**
	 * 增加子控件,同时增加约束
	 * 
	 * @param control
	 *            控件
	 * @param contraint
	 *            约束
	 */
	public void addControl(Control control, Object contraint) {
		super.add((Component) control, (TableConstraints) contraint);
		this.subControls.add(control);
	}

	/**
	 * 获取该复合控件的直接子控件（不包含孙辈以下的孩子）
	 * 
	 * @return 该复合控件的直接子控件（不包含孙辈以下的孩子）
	 */
	public java.util.List getSubControls() {
		if (this.subControls.isEmpty())
			return null;
		else
			return this.subControls;
	}

	/**
	 * 根据 id 得到子控件
	 * 
	 * @param id
	 *            控件id
	 * @return 控件
	 */
	public Control getSubControl(String id) {
		return SubControlFinder.getSubControlBySerialId(this, id);
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 得到id
	 * 
	 * @return id
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

	/**
	 * 设置边框是否可见
	 * 
	 * @param visible
	 *            true-可见，false-不可见
	 */
	public void setBorderVisible(boolean visible) {
		if (visible) {
			this.setBorder(BorderFactory.createEtchedBorder());
		} else {
			this.setBorder(null);
		}
	}

	/**
	 * 设置控件对齐方式
	 * 
	 * @param alignment
	 *            FlowLayout.LEFT = 0; FlowLayout.CENTER = 1; FlowLayout.RIGHT =
	 *            2
	 */
	public void setAlignment(int alignment) {
		this.setLayout(new FlowLayout(alignment, 5, 4));
	}
}
