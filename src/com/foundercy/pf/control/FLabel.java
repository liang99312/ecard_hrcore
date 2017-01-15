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
 * Title: ����ؼ�
 * </p>
 * <p>
 * Description:����ؼ�
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * <p>
 * Company: ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * 
 * @author �ƽ� 2008��4��14������
 * @version $Revision: 1.1.1.1 $
 */
public class FLabel extends JLabel implements Control {
	private static final long serialVersionUID = 10000L;

	private String id;

	private Control parent;

	private String fontName = "����"; // ��������

	private int fontStyle = 0; // ������ʽ

	private int fontSize = 16; // �����С

	/**
	 * ���캯��
	 */
	public FLabel() {
		super();
	}

	/**
	 * ���ñ�ǩ����
	 * 
	 * @param title
	 *            ��ǩ����
	 */
	public void setTitle(String title) {
		super.setText(title);
	}

	/**
	 * �õ���ǩ����
	 * 
	 * @return ��ǩ����
	 */
	public String getTitle() {
		return super.getText();
	}

	/**
	 * ���ÿؼ�id
	 * 
	 * @param id
	 *            �ؼ�id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * �õ��ؼ�id
	 * 
	 * @return �ؼ�id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * �õ������ÿؼ�����һ��ؼ�
	 * 
	 * @return �ؼ�����
	 */
	public Control getParentControl() {
		return this.parent;
	}

	/**
	 * ������һ��ؼ�
	 * 
	 * @param parent
	 *            �ؼ�����
	 */
	public void setParentControl(Control parent) {
		this.parent = parent;
	}

	/**
	 * ������������
	 * 
	 * @param name
	 *            ��������
	 */
	public void setFontName(String name) {
		this.fontName = name;
		setTitleFont();
	}

	/**
	 * �õ���������
	 * 
	 * @return ��������
	 */
	public String getFontName() {
		return this.fontName;
	}

	/**
	 * ����������ʽ
	 * 
	 * @param style
	 *            ������ʽ
	 */
	public void setFontStyle(int style) {
		this.fontStyle = style;
		setTitleFont();
	}

	/**
	 * �õ�������ʽ
	 * 
	 * @return ������ʽ
	 */
	public int getFontStyle() {
		return this.fontStyle;
	}

	/**
	 * ���������С
	 * 
	 * @param size
	 *            �����С
	 */
	public void setFontSize(int size) {
		this.fontSize = size;
		setTitleFont();
	}

	/**
	 * �õ������С
	 * 
	 * @return �����С
	 */
	public int getFontSize() {
		return this.fontSize;
	}

	/**
	 * ��������
	 */
	private void setTitleFont() {
		this.setTitleFont(this.fontName, this.fontStyle, this.fontSize);
	}

	/**
	 * ���ñ�������
	 * 
	 * @param fontName
	 *            ������
	 * @param fontStyle
	 *            ��ϵͳ���ͣ����
	 * @param fontSize
	 *            �����С
	 * 
	 */
	public void setTitleFont(String fontName, int fontStyle, int fontSize) {
		this.setFont(new Font(fontName, fontStyle, fontSize));
	}

	/**
	 * ��ȡ��������
	 * 
	 * @return �������
	 */
	public Font getTitleFont() {
		return this.getFont();
	}

	/**
	 * ���ñ������ɫ
	 * 
	 * @param sColor
	 *            #RRGGBB��ɫ�ַ���
	 */
	public void setTitleColor(String sColor) {
		this.setForeground(Tools.stringToColor(sColor));
	}

}
