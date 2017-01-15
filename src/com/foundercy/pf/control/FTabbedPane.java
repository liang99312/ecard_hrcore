/*
 * $Id: FTabbedPane.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JTabbedPane;

import com.foundercy.pf.control.util.SubControlFinder;

/**
 * <p>
 * Title: FTabbedPane选项卡控件
 * </p>
 * <p>
 * Description:FTabbedPane选项卡控件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 北京方正春元科技发展有限公司
 * </p>
 * <p>
 * Company: 北京方正春元科技发展有限公司
 * </p>
 * 
 * @version $Revision: 1.1.1.1 $
 * @author 黄节 2008年4月14日整理
 * @since java 1.4
 */
public class FTabbedPane extends JTabbedPane implements Control, Compound {

	private static final long serialVersionUID = 1467997656649115812L;

	private String id;

	private Control parent;

	private java.util.List subControls = new ArrayList();

	/**
	 * 构造函数
	 */
	public FTabbedPane() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param id
	 *            控件id
	 */
	public FTabbedPane(String id) {
		super();
		this.id = id;
	}

	/**
	 * 构造函数
	 * 
	 * @param tabPlacement
	 *            标签的位置，JTabbedPane.LEFT－靠左，JTabbedPane.RIGHT－靠右，JTabbedPane.TOP－靠上，JTabbedPane.BOTTOM－靠底部
	 */
	public FTabbedPane(int tabPlacement) {
		super(tabPlacement);
	}

	/**
	 * 构造函数
	 * 
	 * @param tabPlacement
	 *            标签的位置，JTabbedPane.LEFT－靠左，JTabbedPane.RIGHT－靠右，JTabbedPane.TOP－靠上，JTabbedPane.BOTTOM－靠底部
	 * @param tabLayoutPolicy
	 *            当标签不符合页面时的处理策略，可是设置为以下两值：JTabbedPane.WRAP_TAB_LAYOUT和TabbedPane.SCROLL_TAB_LAYOUT
	 */
	public FTabbedPane(int tabPlacement, int tabLayoutPolicy) {
		super(tabPlacement, tabLayoutPolicy);
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

	/**
	 * 增加子控件
	 * 
	 * @param control
	 *            子控件
	 */
	public void addControl(Control control) {
		if (control instanceof FTitledPanel) {
			super.add(((FTitledPanel) control).getTitle(), (Component) control);
			this.subControls.add(control);
		}
	}

	/**
	 * 增加子控件,并指定标题
	 * 
	 * @param title
	 *            标题
	 * @param control
	 *            子控件
	 */
	public void addControl(String title, Control control) {
		super.add(title, (Component) control);
		this.subControls.add(control);
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
		super.add((Component) control, contraint);
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

}
