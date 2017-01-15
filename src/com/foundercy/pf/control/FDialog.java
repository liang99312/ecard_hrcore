/*
 * $Id: FDialog.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.util.ArrayList;

import javax.swing.JDialog;

import com.foundercy.pf.control.util.SubControlFinder;

/**
 * <p>
 * Title: 对话框控件
 * </p>
 * <p>
 * Description:
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
 * 
 */
public class FDialog extends JDialog implements Control, Compound {

	private static final long serialVersionUID = 5596399214669230142L;

	private java.util.List subControls = new ArrayList();

	private String id;

	private Control parent;

	/**
	 * 构造函数 创建对话框控件
	 */
	public FDialog() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param owner
	 *            对话框的拥有者
	 */
	public FDialog(Frame owner) {
		super(owner);
	}

	/**
	 * 构造函数
	 * 
	 * @param owner
	 *            对话框的拥有者
	 * @param modal
	 *            是否是模态
	 */
	public FDialog(Frame owner, boolean modal) {
		super(owner, null, modal);
	}

	/**
	 * 构造函数
	 * 
	 * @param owner
	 *            对话框的拥有者
	 * @param title
	 *            对话框标题
	 */
	public FDialog(Frame owner, String title) {
		super(owner, title, false);
	}

	/**
	 * 构造函数
	 * 
	 * @param owner
	 *            对话框的拥有者
	 * @param title
	 *            对话框标题
	 * @param modal
	 *            是否是模态
	 */
	public FDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);

	}

	/**
	 * 构造函数
	 * 
	 * @param owner
	 *            对话框的拥有者
	 * @param title
	 *            对话框标题
	 * @param modal
	 *            是否是模态
	 * @param gc
	 *            目标界面图案，为空是与对话框拥有者界面相同
	 * 
	 */
	public FDialog(Frame owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);

	}

	/**
	 * 构造函数
	 * 
	 * @param owner
	 *            对话框的拥有者
	 */
	public FDialog(Dialog owner) {
		super(owner, false);
	}

	/**
	 * 构造函数
	 * 
	 * @param owner
	 *            对话框的拥有者
	 * @param modal
	 *            是否是模态
	 */
	public FDialog(Dialog owner, boolean modal) {
		super(owner, null, modal);
	}

	/**
	 * 构造函数
	 * 
	 * @param owner
	 *            对话框的拥有者
	 * @param title
	 *            对话框标题
	 */
	public FDialog(Dialog owner, String title) {
		super(owner, title, false);
	}

	/**
	 * 构造函数
	 * 
	 * @param owner
	 *            对话框的拥有者
	 * @param title
	 *            对话框标题
	 * @param modal
	 *            是否是模态
	 */
	public FDialog(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);

	}

	/**
	 * 构造函数
	 * 
	 * @param owner
	 *            对话框的拥有者
	 * @param title
	 *            对话框标题
	 * @param modal
	 *            是否是模态
	 * @param gc
	 *            目标界面图案，为空是与对话框拥有者界面相同
	 * 
	 */
	public FDialog(Dialog owner, String title, boolean modal,
			GraphicsConfiguration gc) {

		super(owner, title, modal, gc);

	}

	/**
	 * 增加子控件
	 * 
	 * @param control
	 *            子控件
	 */
	public void addControl(Control control) {
		this.addControl(control, new TableConstraints(1, 1, false));
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
		super.getContentPane().add((Component) control,
				(TableConstraints) contraint);
		this.subControls.add(control);
	}


	/**
	 * 得到复合控件的所有直接子控件
	 * 
	 * @return 复合控件的所有直接子控件
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
	 * 设置按钮id
	 * 
	 * @param id
	 *            按钮id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 得到按钮id
	 * 
	 * @return 按钮id
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
