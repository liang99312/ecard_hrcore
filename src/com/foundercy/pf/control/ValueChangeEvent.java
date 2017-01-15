package com.foundercy.pf.control;

import java.util.EventObject;

/**
 * �ؼ���ֵ�������ı�ʱ��������ֵ�ı��¼���
 * �����Ǹ��¼����¼�����
 */

public class ValueChangeEvent extends EventObject {


	/**
	 * 
	 */
	private static final long serialVersionUID = -662976116034944952L;

	//ֵ�ı�ʱ���ı�ǰ�ľ�ֵ
	private Object oldValue = null;

	//ֵ�ı�ʱ���ı�����ֵ
	private Object newValue = null;


//-----���캯��
	/**
	 * ���캯��
	 * 
	 * @param source �����¼��Ŀؼ�
	 */
	public ValueChangeEvent(Object source) {
		this(source,null,null);
	}

	/**
	 * ���캯��
	 * 
	 * @param source �����¼��Ŀؼ�
	 * @param oldValue ֵ�ı�ʱ���ı�ǰ�ľ�ֵ
	 * @param newValue ֵ�ı�ʱ���ı�����ֵ
	 */
	public ValueChangeEvent(Object source,Object oldValue,Object newValue) {
		super(source);

		setOldValue(oldValue);
		setNewValue(newValue);		
	}

/**
 * 
 * ���øı�ǰ�ľ�ֵ
 * @param oldValue �ı�ǰ�ľ�ֵ
 */
public void setOldValue(Object oldValue) {
	this.oldValue = oldValue;
}

	/*
	 * ȡ�øı�ǰ�ľ�ֵ
	 * 
	 * @return �ı�ǰ�ľ�ֵ
	 */
	public final Object getOldValue() {
		return this.oldValue;
	}

	/*
	 * ���øı�����ֵ
	 * 
	 * @param newValue �ı�����ֵ
	 */
	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}

	/*
	 * ȡ�øı�����ֵ
	 * 
	 * @return �ı�����ֵ
	 */
	public final Object getNewValue() {
		return this.newValue;
	}

}
