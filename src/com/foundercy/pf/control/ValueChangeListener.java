/**
 * 
 */
package com.foundercy.pf.control;

import java.util.EventListener;

/**
 * �ؼ���ֵ�������ı�ʱ��������ֵ�ı��¼���
 * �����Ǹ��¼����¼������ӿ�
 */

public interface ValueChangeListener extends EventListener {
	/**
	 * �ؼ���ֵ�����˸ı�
	 * 
	 * @param vce ֵ�ı��¼����¼�����
	 */
	public abstract void valueChanged(ValueChangeEvent vce);
}

