/*
 * $Id: AbstractCompound.java,v 1.1.1.1 2009/04/07 08:12:32 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * 可视复合控件类
 * 
 * @version $Revision: 1.1.1.1 $
 * @author yangbo
 * @see 
 * @since java 1.4
 */

public abstract class AbstractCompound extends JPanel implements UIControl, Compound {
	
	private String id;
	private Control parent;
	
	private String title;
	private Color titleColor; 
	private boolean isTitleVisible;
	private String fontName = "宋体";  // 字体名称
	private int fontStyle = 0;  // 字体样式
	private int fontSize = 12;  // 字体大小
	private boolean is_must_input;
	
	/**
	 * 设置控件ID
	 * @param id 控件ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 得到控件ID
	 * @return 控件ID
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * 得到包含该控件的上一层控件
	 * @return 控件对象
	 */
	public Control getParentControl() {
		return this.parent;
	}
	
	/**
	 * 设置上一层控件
	 * @param parent 控件对象
	 */
	public void setParentControl(Control parent) {
		this.parent = parent;
	}

	/**
	 * 得到控件的标题
	 * @return 标题内容
	 */
	public String getTitle(){
		return this.title;
	}

	/**
	 * 设置控件标题
	 * @param title 标题内容
	 */
	public void setTitle(String title){
		this.title = title;
		setTitledBorder();
	}

	/**
	 * 判断标题是否可见
	 * @return false表示不可见，true表示可见 
	 */
	public boolean isTitleVisible(){
		return this.isTitleVisible;
	}

	/**
	 * 设置标题是否可见
	 * @param visible false表示不可见，true表示可见
	 */
	public void setTitleVisible(boolean visible){
		this.isTitleVisible = visible;
	}

	/**
	 * 得到标题的颜色
	 * @return #RRGGBB颜色字符串
	 */
	public Color getTitleColor(){
		return this.titleColor;
	}
	
	/**
	 * 设置标题的颜色
	 * @param color #RRGGBB颜色字符串
	 */
	public void setTitleColor(String sColor)
	{
		// TODO
	}
	
	/**
     * 设置卡片标题
     * @param columns 标题占用的列数
     */
	public void showTitle(int columns) {
		/*标题 panel*/
		if (getTitle() != null && !getTitle().equals("")) {
			JLabel titleLabel= new JLabel(getTitle());
			titleLabel.setFont(this.getTitleFont());
			JPanel titlePanel = new JPanel();
			titlePanel.add(titleLabel);
			this.add(titlePanel, new TableConstraints(2, columns, false));
		}
		
	}
	
	/**
	 * 如果有标题,则为控件设置TitledBorder
	 */
	public void setTitledBorder(){
		if (getTitle() != null && !getTitle().equals("")) {
			this.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(), getTitle(), 
					TitledBorder.DEFAULT_JUSTIFICATION, 
					TitledBorder.DEFAULT_POSITION,
					this.getTitleFont()));
		}
	}
	
	/**
	 * 设置字体名称
	 * @param name 字体名称
	 */
	public void setFontName(String name)
	{
		this.fontName = name;
	}
	
	/**
	 * 得到字体名称
	 * @return 字体名称
	 */
	public String getFontName()
	{
		return this.fontName;
	}
	
	/**
	 * 设置字体样式
	 * @param style 字体样式
	 */
	public void setFontStyle(int style)
	{
		this.fontStyle = style;
	}
	
	/**
	 * 得到字体样式
	 * @return 字体样式
	 */
	public int getFontStyle()
	{
		return this.fontStyle;
	}
	
	/**
	 * 设置字体大小
	 * @param size 字体大小
	 */
	public void setFontSize(int size)
	{
		this.fontSize = size;
	}
	
	/**
	 * 得到字体大小
	 * @return 字体大小
	 */
	public int getFontSize()
	{
		return this.fontSize;
	}
	
	/**
	 * 得到标题字体
	 */
	public Font getTitleFont()
	{	
		return new Font(fontName, fontStyle, fontSize);
	}
	
	/**
	 * 判断是否必须录入
	 * @return
	 */
	public boolean is_must_input() {
		return this.is_must_input;
	}
	
	/**
	 * 设置是否必须录入
	 * @param is_must_input
	 */
	public void setIs_must_input(boolean is_must_input) {
		this.is_must_input = is_must_input;
	}

}
