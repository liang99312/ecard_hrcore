/*
 * @(#)RefActionListener 1.0 10/04/06
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
/**
 * �߼��¼���Ӧʵ����,Ϊ��ͳһ,������Ϣ���ŵ���ܼ���������
 * ȡ��awt����Ȩ����ģʽ������visitorģʽ������չ��
 * @version 1.0
 * @author victor
 * @since java 1.4.1
 */
public class RefActionListener implements ActionListener
{
	/**�����߶���*/
	private Object adaptee = null;
	/**�������ṩ�ķ���*/
	private String funcName = "actionPerformed";
	public RefActionListener(Object adaptee)
	{
		this.adaptee = adaptee;
	}
	/**
	 * ���캯��,��ָ�����䷽����
	 * @param adaptee ������
	 * @param funcName ���䷽��
	 */
	public RefActionListener(Object adaptee,String funcName)
	{
		this.adaptee = adaptee;
		if(funcName != null && !funcName.equalsIgnoreCase(""))
		{
		   this.funcName = funcName;
		}
	}
	/**
	 * ���䷽��,������������
	 */
	public void actionPerformed(ActionEvent e)
	{
		Class[] param = new Class[1];
		try
		{
		  param[0] = Class.forName("java.awt.event.ActionEvent");
		}catch(ClassNotFoundException ex)
		{
			System.err.println("error in handling ActionEvent of"+adaptee.getClass().getName()+",please confirm classpath is right.");
			return ;
		}
		try
		{
			Method m = adaptee.getClass().getMethod(funcName,param);
			Object[] args = new Object[1];
			args[0] = e;
			m.invoke(adaptee,args);
		}catch(NoSuchMethodException ex)
		{
			System.err.println("has no method '"+funcName+"' in Class "+adaptee.getClass().getName()+",please provide it.");
			return ;			
		}
		catch(Exception ex)
		{
			System.err.println("error in invoking method '"+funcName+"' of Class"+adaptee.getClass().getName()+",please check again!");
			return;
		}		
	}

}
