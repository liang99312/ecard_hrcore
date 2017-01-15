/*
 * filename:  AssistRefModel.java
 *
 * Version: 1.0
 *
 * Date: 2006-1-17
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import java.util.List;

/**
 * <p>Title: ����¼������� </p>
 * <p>Description: ����¼�������ؼ��Ӵ����� </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: foundercy</p>
 * @author yangbo
 * @version 1.0
 */

public abstract class AssistRefModel implements RefModel
{
	public AssistRefModel()
	{
		
	}
	
	/**
	 * �õ����д���ʾ����
	 * @return ��������
	 */
	 abstract protected List getData();
}
