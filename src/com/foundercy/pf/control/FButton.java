/*
 * $Id: FButton.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.Border;

import com.foundercy.pf.util.Resource;

/**
 * <p>
 * Title: 按钮
 * </p>
 * <p>
 * Description: 按钮
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
public class FButton extends JButton implements Control {
	private static final long serialVersionUID = 10000L;

	private String id;

	private Control parent;

	private String fontName = "宋体"; // 字体名称

	private int fontStyle = 0; // 字体样式

	private int fontSize = 16; // 字体大小

	private Border defaultBorder = super.getBorder();

	/**
	 * 构造函数
	 */
	public FButton() {
		super();
		init();
	}

	/**
	 * 构造函数
	 * 
	 * @param id
	 *            按键的id
	 * @param title
	 *            按键的标题
	 */
	public FButton(String id, String title) {
		super(title, null);
		setId(id);
		init();
	}

	/**
	 * 初始化控件
	 */
	public void init() {
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
	 * 设置按钮id
	 * 
	 * @param id
	 *            按钮id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 得到按钮标题
	 * 
	 * @return 按钮标题
	 */
	public String getTitle() {
		return super.getText();
	}

	/**
	 * 设置按钮标题
	 * 
	 * @param title
	 *            按钮标题
	 */
	public void setTitle(String title) {
		super.setText(title);
	}

	/**
	 * 设置按钮图标
	 * 
	 * @param iconFile
	 */
	public void setIcon(String iconFile) {
		Icon icon = Resource.getImage(iconFile);
		if (icon != null) {
			super.setIcon(icon);
		} else {
			super.setIcon(Resource.getImage("images/NULL.gif"));
			if (super.getText() == null || super.getText().equalsIgnoreCase("")) {
				super.setText("无标题");
			}
		}
		setIconTextGap(0);
	}

	/**
	 * 设置按钮大小
	 * 
	 * @param preferredSizeStr
	 *            格式为: "width,height"
	 */
	public void setPreferredSize(String preferredSizeStr) {
		int commaIndex = preferredSizeStr.indexOf(",");
		int width = 1;
		int height = 1;
		if (commaIndex >= 0) {
			width = Integer.parseInt(preferredSizeStr.substring(0, commaIndex)
					.trim());
			height = Integer.parseInt(preferredSizeStr
					.substring(commaIndex + 1).trim());
			super.setPreferredSize(new Dimension(width, height));
			super.setMaximumSize(new Dimension(width, height));
			super.setMinimumSize(new Dimension(width, height));
		}
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
	 * 设置字体名称
	 * 
	 * @param name
	 *            字体名称
	 */
	public void setFontName(String name) {
		this.fontName = name;
		setTitleFont();
	}

	/**
	 * 得到字体名称
	 * 
	 * @return 字体名称
	 */
	public String getFontName() {
		return this.fontName;
	}

	/**
	 * 设置字体样式
	 * 
	 * @param style
	 *            字体样式
	 */
	public void setFontStyle(int style) {
		this.fontStyle = style;
		setTitleFont();
	}

	/**
	 * 得到字体样式
	 * 
	 * @return 字体样式
	 */
	public int getFontStyle() {
		return this.fontStyle;
	}

	/**
	 * 设置字体大小
	 * 
	 * @param size
	 *            字体大小
	 */
	public void setFontSize(int size) {
		this.fontSize = size;
		setTitleFont();
	}

	/**
	 * 得到字体大小
	 * 
	 * @return 字体大小
	 */
	public int getFontSize() {
		return this.fontSize;
	}

	/**
	 * 设置默认字体
	 */
	private void setTitleFont() {
		this.setTitleFont(this.fontName, this.fontStyle, this.fontSize);
	}

	/**
	 * 设置字体
	 * 
	 * @param fontName
	 *            name the font name.
	 * @param fontStyle
	 *            style the style constant for the <code>Font</code>
	 * @param fontSize
	 *            size the point size of the <code>Font</code>
	 */
	public void setTitleFont(String fontName, int fontStyle, int fontSize) {
		this.setFont(new Font(fontName, fontStyle, fontSize));
	}

	/**
	 * 得到字体
	 * 
	 * @return 字体对象
	 */
	public Font getTitleFont() {
		return this.getFont();
	}

	/**
	 * 设置是否显示边框
	 * 
	 * @param visible
	 */
	public void setBorderVisible(boolean visible) {
		if (!visible) {
			this.setBorder(null);
		} else {
			this.setBorder(defaultBorder);
		}
	}

}
