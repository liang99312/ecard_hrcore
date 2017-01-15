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
 * ���Ӹ��Ͽؼ���
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
	private String fontName = "����";  // ��������
	private int fontStyle = 0;  // ������ʽ
	private int fontSize = 12;  // �����С
	private boolean is_must_input;
	
	/**
	 * ���ÿؼ�ID
	 * @param id �ؼ�ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * �õ��ؼ�ID
	 * @return �ؼ�ID
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * �õ������ÿؼ�����һ��ؼ�
	 * @return �ؼ�����
	 */
	public Control getParentControl() {
		return this.parent;
	}
	
	/**
	 * ������һ��ؼ�
	 * @param parent �ؼ�����
	 */
	public void setParentControl(Control parent) {
		this.parent = parent;
	}

	/**
	 * �õ��ؼ��ı���
	 * @return ��������
	 */
	public String getTitle(){
		return this.title;
	}

	/**
	 * ���ÿؼ�����
	 * @param title ��������
	 */
	public void setTitle(String title){
		this.title = title;
		setTitledBorder();
	}

	/**
	 * �жϱ����Ƿ�ɼ�
	 * @return false��ʾ���ɼ���true��ʾ�ɼ� 
	 */
	public boolean isTitleVisible(){
		return this.isTitleVisible;
	}

	/**
	 * ���ñ����Ƿ�ɼ�
	 * @param visible false��ʾ���ɼ���true��ʾ�ɼ�
	 */
	public void setTitleVisible(boolean visible){
		this.isTitleVisible = visible;
	}

	/**
	 * �õ��������ɫ
	 * @return #RRGGBB��ɫ�ַ���
	 */
	public Color getTitleColor(){
		return this.titleColor;
	}
	
	/**
	 * ���ñ������ɫ
	 * @param color #RRGGBB��ɫ�ַ���
	 */
	public void setTitleColor(String sColor)
	{
		// TODO
	}
	
	/**
     * ���ÿ�Ƭ����
     * @param columns ����ռ�õ�����
     */
	public void showTitle(int columns) {
		/*���� panel*/
		if (getTitle() != null && !getTitle().equals("")) {
			JLabel titleLabel= new JLabel(getTitle());
			titleLabel.setFont(this.getTitleFont());
			JPanel titlePanel = new JPanel();
			titlePanel.add(titleLabel);
			this.add(titlePanel, new TableConstraints(2, columns, false));
		}
		
	}
	
	/**
	 * ����б���,��Ϊ�ؼ�����TitledBorder
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
	 * ������������
	 * @param name ��������
	 */
	public void setFontName(String name)
	{
		this.fontName = name;
	}
	
	/**
	 * �õ���������
	 * @return ��������
	 */
	public String getFontName()
	{
		return this.fontName;
	}
	
	/**
	 * ����������ʽ
	 * @param style ������ʽ
	 */
	public void setFontStyle(int style)
	{
		this.fontStyle = style;
	}
	
	/**
	 * �õ�������ʽ
	 * @return ������ʽ
	 */
	public int getFontStyle()
	{
		return this.fontStyle;
	}
	
	/**
	 * ���������С
	 * @param size �����С
	 */
	public void setFontSize(int size)
	{
		this.fontSize = size;
	}
	
	/**
	 * �õ������С
	 * @return �����С
	 */
	public int getFontSize()
	{
		return this.fontSize;
	}
	
	/**
	 * �õ���������
	 */
	public Font getTitleFont()
	{	
		return new Font(fontName, fontStyle, fontSize);
	}
	
	/**
	 * �ж��Ƿ����¼��
	 * @return
	 */
	public boolean is_must_input() {
		return this.is_must_input;
	}
	
	/**
	 * �����Ƿ����¼��
	 * @param is_must_input
	 */
	public void setIs_must_input(boolean is_must_input) {
		this.is_must_input = is_must_input;
	}

}
