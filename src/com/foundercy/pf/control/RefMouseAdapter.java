/*
 * @(#)RefMouseAdapter.java	1.0 23/03/06
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
/**
 * 鼠标事件响应适配器,为了统一,所有信息都放到框架级别来处理
 * 取消awt的授权代理模式，改用visitor模式方便扩展。
 * @version 1.0
 * @author victor
 * @since java 1.4.2
 */
public class RefMouseAdapter extends MouseAdapter
{
	/**调用者对象*/
	private Object adaptee = null;
	/**调用者提供的方法前缀*/
	private String ctrlId = "";
	public RefMouseAdapter(Object adaptee)
	{
		this.adaptee = adaptee;
	}
	/**
	 * 构造函数,可指定反射方法名
	 * @param adaptee 调用者
	 * @param ctrlId 消息对应控件id
	 */	
	public RefMouseAdapter(Object adaptee,String ctrlId)
	{
		this.adaptee = adaptee;
		if(ctrlId != null && !ctrlId.equalsIgnoreCase(""))
		{
			this.ctrlId = ctrlId;
		}
	}
	/**
	 * 鼠标单击事件
	 */
	public void mouseClicked(MouseEvent e)
	{
		String funcName = ctrlId.equals("")?"mouseClicked":ctrlId+"_MouseClicked";
		Class[] param = new Class[1];
		try
		{
		  param[0] = Class.forName("java.awt.event.MouseEvent");
		}catch(ClassNotFoundException ex)
		{
			System.err.println("error in handling MouseEvent of"+adaptee.getClass().getName()+",please confirm classpath is right.");
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
			ex.printStackTrace();
			return;
		}
		
	}
	/**
	 * 鼠标按下事件
	 */
	public void mousePressed(MouseEvent e)
	{
		String funcName = ctrlId.equals("")?"mousePressed":ctrlId+"_mousePressed";
		Class[] param = new Class[1];
		try
		{
		  param[0] = Class.forName("java.awt.event.MouseEvent");
		}catch(ClassNotFoundException ex)
		{
			System.err.println("error in handling MouseEvent of"+adaptee.getClass().getName()+",please confirm classpath is right.");
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
	 * 鼠标松开事件
	 */
	public void mouseReleased(MouseEvent e)
	{
		String funcName = ctrlId.equals("")?"mouseReleased":ctrlId+"_mouseReleased";
		Class[] param = new Class[1];
		try
		{
		  param[0] = Class.forName("java.awt.event.MouseEvent");
		}catch(ClassNotFoundException ex)
		{
			System.err.println("error in handling MouseEvent of"+adaptee.getClass().getName()+",please confirm classpath is right.");
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
			ex.printStackTrace();
			return;
		}	
	}	
	/**
	 * 鼠标进入事件
	 */
	public void mouseEntered(MouseEvent e)
	{
		String funcName = ctrlId.equals("")?"mouseEntered":ctrlId+"_mouseEntered";
		Class[] param = new Class[1];
		try
		{
		  param[0] = Class.forName("java.awt.event.MouseEvent");
		}catch(ClassNotFoundException ex)
		{
			System.err.println("error in handling MouseEvent of"+adaptee.getClass().getName()+",please confirm classpath is right.");
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
	 * 鼠标退出事件
	 */
	public void mouseExited(MouseEvent e)
	{
		String funcName = ctrlId.equals("")?"mouseExited":ctrlId+"_mouseExited";
		Class[] param = new Class[1];
		try
		{
		  param[0] = Class.forName("java.awt.event.MouseEvent");
		}catch(ClassNotFoundException ex)
		{
			System.err.println("error in handling MouseEvent of"+adaptee.getClass().getName()+",please confirm classpath is right.");
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
