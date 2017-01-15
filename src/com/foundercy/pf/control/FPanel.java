/*
 * $Id: FPanel.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import java.awt.Component;
import java.awt.PopupMenu;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.EmptyBorder;

import com.foundercy.pf.control.util.SubControlFinder;

/**
 * <p>
 * Title: 控件容器类
 * </p>
 * <p>
 * Description:控件容器类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 北京方正春元科技发展有限公司
 * </p>
 * <p>
 * Company: 北京方正春元科技发展有限公司
 * </p>
 * 
 * @author Fangyi
 * @author 黄节 2008年4月14日整理
 * @version $Revision: 1.1.1.1 $
 */
public class FPanel extends AbstractCompound {
	private static final long serialVersionUID = 1L;

	// private java.util.List subControls = new ArrayList();

	private int topInset = 0;

	private int leftInset = 0;

	private int bottomInset = 0;

	private int rightInset = 0;

	/**
	 * 构造函数
	 */
	public FPanel() {
		// setBorder();
	}

	/**
	 * 增加子控件
	 * 
	 * @param control
	 *            FPanel中的子控件
	 */
	public void addControl(Control control) {
		// 如果被设置成行优先布局，则缺省给本控件加上“占1行1列”的约束
		if (this.getLayout() instanceof RowPreferedLayout) {
			this.addControl(control, new TableConstraints(1, 1, true));
		} else {
			if (control instanceof Component) {
				super.add((Component) control);
			} else {
				// 其他类型的对象将被丢弃。
			}
		}
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
		if (control instanceof Component) {
			super.add((Component) control, contraint);
		} else {
			// 其他类型的对象将被丢弃。
		}
		// this.subControls.add(control);
	}

	/**
	 * 添加组件
	 * 
	 * @param comp
	 *            被添加的部件
	 * 
	 * @see javax.swing.JComponent#revalidate()
	 * 
	 * @return 被添加的控件
	 */
	public Component add(Component comp) {
		if (comp instanceof Control) {
			this.addControl((Control) comp);
		} else {
			super.add(comp);
		}
		return comp;
	}

	/**
	 * 添加组件
	 * 
	 * @param comp
	 *            被添加的部件
	 * @param index
	 *            插入的位置，如果填入-1就添加到最后
	 * 
	 * @return 被添加的控件
	 * @see javax.swing.JComponent#revalidate()
	 */
	public Component add(Component comp, int index) {
		// super.add(comp, index);
		if (comp instanceof Control) {
			this.addControl((Control) comp);
		} else {
			super.add(comp, index);
		}
		return comp;
	}

	/**
	 * 增加子控件,同时增加约束
	 * 
	 * @param comp
	 *            控件
	 * @param constraints
	 *            约束
	 */
	public void add(Component comp, Object constraints) {
		// super.add(comp, constraints);
		if (comp instanceof Control) {
			this.addControl((Control) comp, constraints);
		} else {
			super.add(comp, constraints);
		}
	}

	/**
	 * 增加子控件,同时增加约束
	 * 
	 * @param comp
	 *            控件
	 * @param constraints
	 *            约束
	 * @param index
	 *            插入的位置，如果填入-1就添加到最后
	 */
	public void add(Component comp, Object constraints, int index) {
		// super.add(comp, constraints, index);
		if (comp instanceof Control) {
			this.addControl((Control) comp, constraints);
		} else {
			super.add(comp, constraints);
		}
	}

	/**
	 * 添加右键弹出菜单
	 * 
	 * @param popup
	 *            右键弹出菜单
	 * 
	 */
	public void add(PopupMenu popup) {
		super.add(popup);
	}

	/**
	 * 增加子控件
	 * 
	 * @param name
	 *            控件名字
	 * @param comp
	 *            控件
	 * 
	 * @return 被添加的控件
	 * 
	 */
	public Component add(String name, Component comp) {
		// super.add(name, comp);
		if (comp instanceof Control) {
			this.addControl((Control) comp);
		} else {
			super.add(name, comp);
		}
		return comp;
	}

	/**
	 * 该复合控件的直接子控件（不包含孙辈以下的孩子）
	 * 
	 * @return 所有子控件（不包含孙辈以下的孩子）
	 */
	public List getSubControls() {

		Component[] comps = super.getComponents();

		ArrayList controls = new ArrayList();
		for (int i = 0; comps != null && i < comps.length; i++) {

			if (comps[i] instanceof Control) {
				controls.add(comps[i]);
			}
		}
		return controls;
		//    	
		// if (this.subControls.isEmpty())
		// return null;
		// else
		// return this.subControls;
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
	 * 清除全部子控件
	 */
	public void removeAllSubControls() {
		super.removeAll();
	}

	/**
	 * 设置上边间距
	 * 
	 * @param topInset
	 *            上边间距
	 */
	public void setTopInset(int topInset) {
		this.topInset = topInset;
		setBorder();
	}

	/**
	 * 得到上边间距
	 * 
	 * @return 上边间距
	 */
	public int getTopInset() {
		return this.topInset;
	}

	/**
	 * 设置左边间距
	 * 
	 * @param leftInset
	 *            左边间距
	 */
	public void setLeftInset(int leftInset) {
		this.leftInset = leftInset;
		setBorder();
	}

	/**
	 * 得到左边间距
	 * 
	 * @return 左边间距
	 */
	public int getLeftInset() {
		return this.leftInset;
	}

	/**
	 * 设置底边间距
	 * 
	 * @param bottomInset
	 *            底边间距
	 */
	public void setBottomInset(int bottomInset) {
		this.bottomInset = bottomInset;
		setBorder();
	}

	/**
	 * 得到底边间距
	 * 
	 * @return 底边间距
	 */
	public int getBottomInset() {
		return this.bottomInset;
	}

	/**
	 * 设置右边间距
	 * 
	 * @param rightInset
	 *            右边间距
	 */
	public void setRightInset(int rightInset) {
		this.rightInset = rightInset;
		setBorder();
	}

	/**
	 * 得到右边间距
	 * 
	 * @return 右边间距
	 */
	public int getRightInset() {
		return this.rightInset;
	}

	/**
	 * 设置控件边间距
	 */
	public void setBorder() {
		this.setBorder(new EmptyBorder(this.getTopInset(), this.getLeftInset(),
				this.getBottomInset(), this.getRightInset()));
	}

	/**
	 * 如果存在于FInternalFramePane中，关闭时调用close方法，提供给报表继承
	 * 
	 * @author 黄节 2007年9月24日添加
	 */
	public void close() {
	}
}
