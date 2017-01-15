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
 * 键盘事件响应实现类,为了统一,所有信息都放到框架级别来处理
 * 取消awt的授权代理模式，改用visitor模式方便扩展。
 * @version 1.0
 * @author victor
 * @since java 1.4.2
 */
public class RefKeyAdapter extends KeyAdapter
{
	/**调用者对象*/
	private Object adaptee = null;
	/**调用者提供的方法*/
	private String funcName = "keyPressed";
	public RefKeyAdapter(Object adaptee)
	{
		this.adaptee = adaptee;
	}
	/**
	 * 构造函数,可指定反射方法名
	 * @param adaptee 调用者
	 * @param funcName 反射方法
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
	 * 反射方法,调用者自完善
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
	 * 反射方法,调用者自完善
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
	 * 反射方法,调用者自完善
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
