/*
 * filename:  Rollable.java
 *
 * Version: 1.0
 *
 * Date: 2006-3-23
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

/**
 * <p>Title: ��ҳ�ؼ������߼��ӿ� </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: foundercy</p>
 * @author yangbo
 * @version 1.0
 */
public interface Rollable
{
	/**
	 * ��ָ��ҳ
	 * @param pageIndex ҳ����ţ���1��ʼ
	 * @param pageRows ÿҳ����ʾ��¼��
	 * @return �ܼ�¼��
	 */
	public int jump(int pageIndex, int pageRows); 
	
}
