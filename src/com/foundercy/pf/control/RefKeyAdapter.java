/*
 * @(#)RefKeyAdapter 1.0 25/05/06
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
/**
 * �����¼���Ӧʵ����,Ϊ��ͳһ,������Ϣ���ŵ���ܼ���������
 * ȡ��awt����Ȩ����ģʽ������visitorģʽ������չ��
 * @version 1.0
 * @author victor
 * @since java 1.4.2
 */
public class RefKeyAdapter extends KeyAdapter
{
	/**�����߶���*/
	private Object adaptee = null;
	/**�������ṩ�ķ���*/
	private String funcName = "keyPressed";
	public RefKeyAdapter(Object adaptee)
	{
		this.adaptee = adaptee;
	}
	/**
	 * ���캯��,��ָ�����䷽����
	 * @param adaptee ������
	 * @param funcName ���䷽��
	 */
	public RefKeyAdapter(Object adaptee,String funcName)
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
	public void keyPressed(KeyEvent e)
	{
		funcName = "keyPressed";
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
	public void keyReleased(KeyEvent e)
	{
		funcName = "keyReleased";
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
	public void keyTyped(KeyEvent e)
	{
		funcName = "keyTyped";
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
