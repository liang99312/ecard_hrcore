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
 * Title: ��ť
 * </p>
 * <p>
 * Description: ��ť
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
public class FButton extends JButton implements Control {
	private static final long serialVersionUID = 10000L;

	private String id;

	private Control parent;

	private String fontName = "����"; // ��������

	private int fontStyle = 0; // ������ʽ

	private int fontSize = 16; // �����С

	private Border defaultBorder = super.getBorder();

	/**
	 * ���캯��
	 */
	public FButton() {
		super();
		init();
	}

	/**
	 * ���캯��
	 * 
	 * @param id
	 *            ������id
	 * @param title
	 *            �����ı���
	 */
	public FButton(String id, String title) {
		super(title, null);
		setId(id);
		init();
	}

	/**
	 * ��ʼ���ؼ�
	 */
	public void init() {
	}

	/**
	 * �õ���ťid
	 * 
	 * @return ��ťid
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * ���ð�ťid
	 * 
	 * @param id
	 *            ��ťid
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * �õ���ť����
	 * 
	 * @return ��ť����
	 */
	public String getTitle() {
		return super.getText();
	}

	/**
	 * ���ð�ť����
	 * 
	 * @param title
	 *            ��ť����
	 */
	public void setTitle(String title) {
		super.setText(title);
	}

	/**
	 * ���ð�ťͼ��
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
				super.setText("�ޱ���");
			}
		}
		setIconTextGap(0);
	}

	/**
	 * ���ð�ť��С
	 * 
	 * @param preferredSizeStr
	 *            ��ʽΪ: "width,height"
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
	 * ����Ĭ������
	 */
	private void setTitleFont() {
		this.setTitleFont(this.fontName, this.fontStyle, this.fontSize);
	}

	/**
	 * ��������
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
	 * �õ�����
	 * 
	 * @return �������
	 */
	public Font getTitleFont() {
		return this.getFont();
	}

	/**
	 * �����Ƿ���ʾ�߿�
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
