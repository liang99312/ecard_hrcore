/*
 * filename:  UIControl.java
 *
 * Version: 1.0
 *
 * Date: 2005-12-28
 *
 * Copyright notice:  2005 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import java.awt.Color;

/**
 * <p>Title: ���ӿؼ��ӿ� </p>
 * <p>Description: ���еĿ��ӿؼ���ʵ�ָýӿ� </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: foundercy</p>
 * @author yangbo
 * @version 1.0
 */
public interface UIControl extends Control
{
	/**
	 * �õ��ؼ��ı���
	 * @return ��������
	 */
	String getTitle();
	
	/**
	 * ���ÿؼ�����
	 * @param title ��������
	 */
	void setTitle(String title);
	
	/**
	 * �жϱ����Ƿ�ɼ�
	 * @return 0��ʾ���ɼ���1��ʾ�ɼ� 
	 */
	boolean isTitleVisible();
	
	/**
	 * ���ñ����Ƿ�ɼ�
	 * @param visible 0��ʾ���ɼ���1��ʾ�ɼ�
	 */
	void setTitleVisible(boolean visible);
	
	/**
	 * �õ��������ɫ
	 * @return #RRGGBB��ɫ�ַ���
	 */
	Color getTitleColor();
	
	/**
	 * ���ñ������ɫ
	 * @param color #RRGGBB��ɫ�ַ���
	 */
	void setTitleColor(String color);
	
	/**
	 * �ж��Ƿ����¼��
	 * @return
	 */
	boolean is_must_input();
	
	/**
	 * �����Ƿ����¼��
	 * @param is_must_input
	 */
	void setIs_must_input(boolean is_must_input);
}
