/*
 * filename:  FComboBoxModel.java
 *
 * Version: 1.0
 *
 * Date: 2006-1-10
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import javax.swing.ComboBoxModel;

/**
 * <p>Title: ����������ģ�� </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: foundercy</p>
 * @author yangbo
 * @version 1.0
 */
public interface FComboBoxModel extends RefModel ,ComboBoxModel
{
	/**
	 * �õ���ѡ���б����ֵ
	 * @return �б����ֵ
	 */
	public Object getSelectedRealValue();

	
	/**
	 * �����б�����������õ��б����ֵ
	 * @param index �б��������
	 * @return �б����ֵ
	 */
	public Object getRealValueAt(int index);
}
