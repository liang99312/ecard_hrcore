/*
 * $Id: FToolBar.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import com.foundercy.pf.control.util.SubControlFinder;

/**
 * <p>
 * Title: 工具条
 * </p>
 * <p>
 * Description:工具条
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
public class FToolBar extends JToolBar implements Control, Compound {

	private static final long serialVersionUID = -4790873047569422082L;

	private String id;

	private Control parent;

	private java.util.List subControls = new ArrayList();

	/**
	 * 工具栏默认高度
	 */
	public static final int TOOLBAR_HEIGHT = 44;

	/**
	 * 构造函数
	 */
	public FToolBar() {
		super();
		init();
	}

	/**
	 * 构造函数
	 * 
	 * @param orientation
	 *            设置工具栏的方向，FToolBar.HORIZONTAL－水平，FToolBar.VERTICAL－垂直
	 */
	public FToolBar(int orientation) {
		super(orientation);
		init();
	}

	/**
	 * 初始化控件
	 */
	public void init() {
		super.setFloatable(false);// 设置工具条不可移动
		super.setRollover(true);
		// this.setBorder(BorderFactory.createEtchedBorder());//设置边框
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
	 * 得到控件id
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
	 * 向工具条中增加子控件
	 * 
	 * @param control
	 *            控件
	 */
	public void addControl(Control control) {
		if (control instanceof FButton) {
			FButton btn = (FButton) control;
			Rectangle2D rect = btn.getFontMetrics(btn.getTitleFont())
					.getStringBounds(btn.getTitle(), btn.getGraphics());
			int btnWidth = (int) rect.getWidth() + 20;
			if (btnWidth < TOOLBAR_HEIGHT) {
				btnWidth = TOOLBAR_HEIGHT;
			}
			btn.setPreferredSize(btnWidth + ", " + TOOLBAR_HEIGHT);

			btn.setVerticalTextPosition(SwingConstants.BOTTOM);
			btn.setHorizontalTextPosition(SwingConstants.CENTER);
			btn.setToolTipText(((FButton) control).getTitle());

			/* updated by tianlu 2008-01-08 start */
			// 如果按钮没有提示信息，那么按钮的提示信息设置为按钮的标题
			if (((FButton) control).getToolTipText() == null) {
				btn.setToolTipText(((FButton) control).getTitle());
			}
			// btn.setToolTipText(((FButton) control).getTitle());
			/* updated by tianlu 2008-01-08 end */

			// btn.setMargin(new Insets(5,0,0,0));
		} else if (control instanceof FDropDownButton) {
			FDropDownButton btn = (FDropDownButton) control;
			Rectangle2D rect = btn.getFontMetrics(btn.getTitleFont())
					.getStringBounds(btn.getTitle(), btn.getGraphics());
			int btnWidth = (int) rect.getWidth() + 32;
			if (btnWidth < TOOLBAR_HEIGHT) {
				btnWidth = TOOLBAR_HEIGHT;
			}
			btn.setPreferredSize(new Dimension(TOOLBAR_HEIGHT, TOOLBAR_HEIGHT));
			btn.setMaximumSize(new Dimension(btnWidth, TOOLBAR_HEIGHT));
			btn.setMinimumSize(new Dimension(btnWidth, TOOLBAR_HEIGHT));
			btn.setToolTipText(btn.getTitle());
		}

		if (control instanceof FToolBarSeparator)
			addSeparator();
		else
			add((Component) control);

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
	 * 得到复合控件的所有直接子控件
	 * 
	 * @return 复合控件的所有直接子控件
	 */
	public List getSubControls() {
		if (this.subControls.isEmpty())
			return null;
		else
			return this.subControls;
	}

	/**
	 * 清除全部子控件
	 */
	public void removeAllSubControls() {
		this.removeAll();
		subControls.clear();
	}

	/**
	 * A toolbar-specific separator. An object with dimension but no contents
	 * used to divide buttons on a tool bar into groups.
	 */
	static public class FToolBarSeparator extends JToolBar.Separator implements
			Control {
		private static final long serialVersionUID = -952650274547301223L;

		private String id;

		private Control parent;

		/**
		 * Creates a new toolbar separator with the default size as defined by
		 * the current look and feel.
		 */
		public FToolBarSeparator() {
			super(); // let the UI define the default size
		}

		/**
		 * Creates a new toolbar separator with the specified size.
		 * 
		 * @param size
		 *            the <code>Dimension</code> of the separator
		 */
		public FToolBarSeparator(Dimension size) {
			super(size);
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
		 * 设置id
		 * 
		 * @param id
		 *            控件id
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

}
