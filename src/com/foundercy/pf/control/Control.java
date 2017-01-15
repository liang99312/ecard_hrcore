/*
 * filename:  Control.java
 *
 * Version: 1.0
 *
 * Date: 2005-12-28
 *
 * Copyright notice:  2005 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

/**
 * <p>Title: �ؼ������ӿ� </p>
 * <p>Description: ƽ̨�д����Ŀؼ���ʵ�ָýӿ� </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: foundercy</p>
 * @author yangbo
 * @version 1.0
 */
public interface Control
{
	/**
	 * ���ÿؼ�ID
	 * @param id �ؼ�ID
	 */
	void setId(String id);
	
	/**
	 * �õ��ؼ�ID
	 * @return �ؼ�ID
	 */
	String getId();
	
	/**
	 * �õ������ÿؼ�����һ��ؼ�
	 * @return �ؼ�����
	 */
	Control getParentControl();
	
	/**
	 * ������һ��ؼ�
	 * @param parent �ؼ�����
	 */
	void setParentControl(Control parent);	
}
