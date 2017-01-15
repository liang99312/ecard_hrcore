package com.foundercy.pf.control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.lang.reflect.Method;

/**
 * 鼠标事件响应适配器,为了统一,所有信息都放到框架级别来处理
 * 取消awt的授权代理模式，改用visitor模式方便扩展。
 * @version 1.0
 * @author liusp
 * @since java 1.4.2
 */

public class RefMouseMotionAdapter extends MouseMotionAdapter {
	
	/**调用者对象*/
	private Object adaptee = null;
	/**调用者提供的方法前缀*/
	private String ctrlId = "";
	public RefMouseMotionAdapter(Object adaptee)
	{
		this.adaptee = adaptee;
	}
	/**
	 * 构造函数,可指定反射方法名
	 * @param adaptee 调用者
	 * @param funcName 反射方法
	 */	
	public RefMouseMotionAdapter(Object adaptee,String ctrlId)
	{
		this.adaptee = adaptee;
		if(ctrlId != null && !ctrlId.equalsIgnoreCase(""))
		{
			this.ctrlId = ctrlId;
		}
	}
	
	/**
	 * 鼠标移动事件
	 */
	public void mouseMoved(MouseEvent e)
	{
		String funcName = ctrlId.equals("")?"mouseMoved":ctrlId+"_mouseMoved";
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
	 * 鼠标拖曳事件
	 */
	public void mouseDragged(MouseEvent e)
	{
		String funcName = ctrlId.equals("")?"mouseDragged":ctrlId+"_mouseDragged";
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
