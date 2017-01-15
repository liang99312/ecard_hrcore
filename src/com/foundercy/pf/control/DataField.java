/*
 * filename:  DataField.java
 *
 * Version: 1.0
 *
 * Date: 2005-12-28
 *
 * Copyright notice:  2005 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import javax.swing.JComponent;
;

/**
 * <p>Title: �����ؼ��ӿ� </p>
 * <p>Description: ��ǩ+�򵥿ؼ�����ʽ </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: foundercy</p>
 * @author yangbo
 * @version 1.0
 */
public interface DataField extends UIControl
{
	/**
	 * �жϿؼ��Ƿ���Ա༭
	 * @return 0Ϊ���ɱ༭��1Ϊ�ɱ༭
	 */
	boolean isEditable();
	
	/**
	 * ���ÿؼ��Ƿ�ɱ༭
	 * @param editable 0Ϊ���ɱ༭��1Ϊ�ɱ༭
	 */
	void setEditable(boolean editable);
	
	/**
	 * �õ��ؼ���ֵ
	 * @return �ؼ���ֵ����
	 */
	Object getValue();
	
	/**
	 * ���ÿؼ���ֵ
	 * @param value �ؼ���ֵ����
	 */
	void setValue(Object value);
	
	/**
	 * �ؼ�����ʾ�Ƿ�����Ӧ
	 * @return 0Ϊ������Ӧ��1Ϊ����Ӧ
	 */
	boolean isTitleAdapting();
	
	/**
	 * ���ÿؼ�����ʾ�Ƿ�����Ӧ
	 * @param adapting 0Ϊ������Ӧ��1Ϊ����Ӧ
	 */
	void setTitleAdapting(boolean adapting);
	
	/**
	 * �õ��������ʾλ��
	 * @return "LEFT"��ʾ���⿿��"RIGHT"��ʾ���⿿��
	 */
	String getTitlePosition();
	
	/**
	 * ���ñ������ʾλ��
	 * @param position "LEFT"��ʾ���⿿��"RIGHT"��ʾ���⿿��
	 */
	void setTitlePosition(String position);
	
	/**
	 * ����ֵ�ı������
	 * @param listener ֵ�ı����
	 */
	void addValueChangeListener(ValueChangeListener listener);
	
	/**
	 * ɾ��ֵ�ı������ 
	 * @param listerner ֵ�ı����
	 */
	void removeValueChangeListener(ValueChangeListener listerner);
	
	/**
	 * �õ���ǰʵ�ʲ����Ŀؼ�
	 * @return �ؼ�����
	 */
	JComponent getEditor();
	
}
