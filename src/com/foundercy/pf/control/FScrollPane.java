package com.foundercy.pf.control;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.border.Border;

import com.foundercy.pf.control.util.SubControlFinder;

/**
 * <p>
 * Title: 滑动条面板
 * </p>
 * <p>
 * Description:滑动条面板
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005 北京方正春元科技发展有限公司
 * </p>
 * <p>
 * Company:北京方正春元科技发展有限公司
 * </p>
 * 
 * @author 黄节 2008年4月10日整理
 * @version 1.0
 */
public class FScrollPane extends JScrollPane implements Control, Compound {
	private static final long serialVersionUID = -3015559972055407653L;

	private String id;

	private Control parent;

	private java.util.List subControls = new ArrayList();

	private boolean borderVisible;

	private Border border = null;

	/**
	 * 构造函数
	 */
	public FScrollPane() {
		super();
		border = this.getBorder();
	}

	/**
	 * 构造函数
	 * 
	 * @param view
	 *            父控件
	 */
	public FScrollPane(Component view) {
		super(view);
		border = this.getBorder();
	}

	/**
	 * 构造函数
	 * 
	 * @param view
	 *            父控件
	 * @param vsbPolicy
	 *            水平滑动条显示策略，e.g. VERTICAL_SCROLLBAR_AS_NEEDED
	 * @param hsbPolicy
	 *            垂直滑动条显示策略，e.g. HORIZONTAL_SCROLLBAR_AS_NEEDED
	 */
	public FScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
		super(view, vsbPolicy, hsbPolicy);
		border = this.getBorder();
	}

	/**
	 * 构造函数
	 * 
	 * @param vsbPolicy
	 *            水平滑动条显示策略，e.g. VERTICAL_SCROLLBAR_AS_NEEDED
	 * @param hsbPolicy
	 *            垂直滑动条显示策略，e.g. HORIZONTAL_SCROLLBAR_AS_NEEDED
	 */
	public FScrollPane(int vsbPolicy, int hsbPolicy) {
		super(vsbPolicy, hsbPolicy);
		border = this.getBorder();
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
	 * 设置控件ID
	 * 
	 * @param id
	 *            控件ID
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

	/**
	 * 增加子控件,同时增加约束
	 * 
	 * @param control
	 *            控件
	 * @param contraint
	 *            约束
	 */
	public void addControl(Control control, Object contraint) {
	}

	/**
	 * 增加子控件 此控件中只能有两个子控件
	 * 
	 * @param control
	 *            JSplitPane中的子控件
	 */
	public void addControl(Control control) {
		this.setViewportView((Component) control);
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.subControls.add(control);
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
	 * 获取该复合控件的直接子控件（不包含孙辈以下的孩子）
	 * 
	 * @return 该复合控件的直接子控件（不包含孙辈以下的孩子）
	 */
	public List getSubControls() {
		if (this.subControls.isEmpty())
			return null;
		else
			return this.subControls;
	}

	/**
	 * 查询边框是否可见
	 * 
	 * @return true－可见，false－不可见
	 * 
	 */
	public boolean isBorderVisible() {
		return borderVisible;
	}

	/**
	 * 设置边框是否可见
	 * 
	 * @param borderVisible
	 *            true－可见，false－不可见
	 * 
	 */
	public void setBorderVisible(boolean borderVisible) {
		this.borderVisible = borderVisible;
		if (!borderVisible) {
			this.setBorder(null);
			this.getViewport().setBorder(null);
		} else {
			this.setBorder(this.border);
		}
	}
}
