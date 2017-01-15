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
 * ����¼���Ӧ������,Ϊ��ͳһ,������Ϣ���ŵ���ܼ���������
 * ȡ��awt����Ȩ����ģʽ������visitorģʽ������չ��
 * @version 1.0
 * @author victor
 * @since java 1.4.2
 */
public class RefMouseAdapter extends MouseAdapter
{
	/**�����߶���*/
	private Object adaptee = null;
	/**�������ṩ�ķ���ǰ׺*/
	private String ctrlId = "";
	public RefMouseAdapter(Object adaptee)
	{
		this.adaptee = adaptee;
	}
	/**
	 * ���캯��,��ָ�����䷽����
	 * @param adaptee ������
	 * @param ctrlId ��Ϣ��Ӧ�ؼ�id
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
	 * ��굥���¼�
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
	 * ��갴���¼�
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
	 * ����ɿ��¼�
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
	 * �������¼�
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
	 * ����˳��¼�
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
