package com.foundercy.pf.control;

import java.util.List;

public interface Compound
{
	/**
	 * �õ��ӿؼ��б�
	 * @return �ӿؼ���������
	 */
	List getSubControls();
	
	/**
	 * �õ��ӿؼ�
	 * @return �ӿؼ�����
	 */
	Control getSubControl(String id);
	
	/**
	 * �����ӿؼ�
	 * @param control
	 */
	void addControl(Control control);
	
	/**
	 * �����ӿؼ�,ͬʱ����Լ��
	 * @param control
	 */
	void addControl(Control control,Object contraint);
	
	
}
