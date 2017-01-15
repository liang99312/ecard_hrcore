/*
 * @(#)RefComponentAdapter 1.0 25/05/06
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;


import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.Method;
/**
 * �����¼���Ӧʵ����,Ϊ��ͳһ,������Ϣ���ŵ���ܼ���������
 * ȡ��awt����Ȩ����ģʽ������visitorģʽ������չ��
 * @version 1.0
 * @author victor
 * @since java 1.4.2
 */
public class RefComponentAdapter extends ComponentAdapter
{
	/**�����߶���*/
	private Object adaptee = null;
	/**�������ṩ�ķ���*/
	private String funcName = "componentResized";
	public RefComponentAdapter(Object adaptee)
	{
		this.adaptee = adaptee;
	}
	/**
	 * ���캯��,��ָ�����䷽����
	 * @param adaptee ������
	 * @param funcName ���䷽��
	 */
	public RefComponentAdapter(Object adaptee,String funcName)
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
	public void componentResized(ComponentEvent e)
	{
		funcName = "componentResized";
		Class[] param = new Class[1];
		try
		{
		  param[0] = Class.forName("java.awt.event.KeyEvent");
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
			return ;			
		}
		catch(Exception ex)
		{
			return;
		}		
	}
	/**
	 * ���䷽��,������������
	 */
	public void componentMoved(ComponentEvent e)
	{
		funcName = "componentMoved";
		Class[] param = new Class[1];
		try
		{
		  param[0] = Class.forName("java.awt.event.KeyEvent");
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
			return ;			
		}
		catch(Exception ex)
		{
			return;
		}		
	}
	/**
	 * ���䷽��,������������
	 */
	public void componentShown(ComponentEvent e)
	{
		funcName = "componentShown";
		Class[] param = new Class[1];
		try
		{
		  param[0] = Class.forName("java.awt.event.KeyEvent");
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
			return ;			
		}
		catch(Exception ex)
		{
			return;
		}		
	}
	/**
	 * ���䷽��,������������
	 */
	public void componentHidden(ComponentEvent e)
	{
		funcName = "componentHidden";
		Class[] param = new Class[1];
		try
		{
		  param[0] = Class.forName("java.awt.event.KeyEvent");
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
			return ;			
		}
		catch(Exception ex)
		{
			return;
		}		
	}	
}
