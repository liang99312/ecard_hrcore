/*
 * $Id: FBlankPanel.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import javax.swing.JPanel;

/**
 * <p>
 * Title: 空白填充类
 * </p>
 * <p>
 * Description: 空白填充类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 北京方正春元科技发展有限公司
 * </p>
 * <p>
 * Company: 北京方正春元科技发展有限公司
 * </p>
 * 
 * @author Fangyi
 * @author 黄节 2008年4月10日整理
 * @version $Revision: 1.1.1.1 $
 */
public class FBlankPanel extends JPanel implements Control {
	private static final long serialVersionUID = -8614967567388214741L;

	/**
	 * 构造函数
	 */
	public FBlankPanel() {
	}

	/**
	 * 获取id（为实现接口，无实际用处）
	 * 
	 * @return 控件id
	 * 
	 */
	public String getId() {
		return null;
	}

	/**
	 * 获取父控件（为实现接口，无实际用处）
	 * 
	 * @return 父控件
	 * 
	 */
	public Control getParentControl() {
		return null;
	}

	/**
	 * 设置控件id（为实现接口，无实际用处）
	 * 
	 * @param id
	 *            控件id
	 * 
	 */
	public void setId(String id) {
	}

	/**
	 * 设置父控件（为实现接口，无实际用处）
	 * 
	 * @param parent
	 *            父控件
	 */
	public void setParentControl(Control parent) {
	}

}
