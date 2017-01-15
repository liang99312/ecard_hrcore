/*
 * filename:  RefModel.java
 *
 * Version: 1.0
 *
 * Date: 2006-1-17
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

/**
 * <p>Title: ��������ģ�ͽӿ� </p>
 * <p>Description: ���л�������ģ�ͽ�ʵ�ָýӿ� </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: foundercy</p>
 * @author yangbo
 * @version 1.0
 */

public interface RefModel
{
	
	  /**
	   * �������ַ������õ���Ӧ��ֵ
	   *
	   * @param name �����ַ���
	   * @return �����ַ�������Ӧ��ֵ
	   */
	  public Object[] getValueByName(String name);

	  /**
	   * ��ֵ���õ���Ӧ�������ַ���
	   *
	   * @param value ֵ
	   * @return ֵ����Ӧ�������ַ���
	   */
	  public String getNameByValue(Object value);
	  
	  
}
