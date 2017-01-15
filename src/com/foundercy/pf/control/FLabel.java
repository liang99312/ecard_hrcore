/*
 * $Id: FLabel.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import java.awt.Font;

import javax.swing.JLabel;

import com.foundercy.pf.util.Tools;

/**
 * <p>
 * Title: 标题控件
 * </p>
 * <p>
 * Description:标题控件
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
public class FLabel extends JLabel implements Control {
	private static final long serialVersionUID = 10000L;

	private String id;

	private Control parent;

	private String fontName = "宋体"; // 字体名称

	private int fontStyle = 0; // 字体样式

	private int fontSize = 16; // 字体大小

	/**
	 * 构造函数
	 */
	public FLabel() {
		super();
	}

	/**
	 * 设置标签文字
	 * 
	 * @param title
	 *            标签文字
	 */
	public void setTitle(String title) {
		super.setText(title);
	}

	/**
	 * 得到标签文字
	 * 
	 * @return 标签文字
	 */
	public String getTitle() {
		return super.getText();
	}

	/**
	 * 设置控件id
	 * 
	 * @param id
	 *            控件id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 得到控件id
	 * 
	 * @return 控件id
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
	 * 设置字体
	 */
	private void setTitleFont() {
		this.setTitleFont(this.fontName, this.fontStyle, this.fontSize);
	}

	/**
	 * 设置标题字体
	 * 
	 * @param fontName
	 *            字体名
	 * @param fontStyle
	 *            子系统类型，风格
	 * @param fontSize
	 *            字体大小
	 * 
	 */
	public void setTitleFont(String fontName, int fontStyle, int fontSize) {
		this.setFont(new Font(fontName, fontStyle, fontSize));
	}

	/**
	 * 获取字体类型
	 * 
	 * @return 字体对象
	 */
	public Font getTitleFont() {
		return this.getFont();
	}

	/**
	 * 设置标题的颜色
	 * 
	 * @param sColor
	 *            #RRGGBB颜色字符串
	 */
	public void setTitleColor(String sColor) {
		this.setForeground(Tools.stringToColor(sColor));
	}

}
