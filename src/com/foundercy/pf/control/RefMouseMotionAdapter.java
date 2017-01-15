package com.foundercy.pf.control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.lang.reflect.Method;

/**
 * ����¼���Ӧ������,Ϊ��ͳһ,������Ϣ���ŵ���ܼ���������
 * ȡ��awt����Ȩ����ģʽ������visitorģʽ������չ��
 * @version 1.0
 * @author liusp
 * @since java 1.4.2
 */

public class RefMouseMotionAdapter extends MouseMotionAdapter {
	
	/**�����߶���*/
	private Object adaptee = null;
	/**�������ṩ�ķ���ǰ׺*/
	private String ctrlId = "";
	public RefMouseMotionAdapter(Object adaptee)
	{
		this.adaptee = adaptee;
	}
	/**
	 * ���캯��,��ָ�����䷽����
	 * @param adaptee ������
	 * @param funcName ���䷽��
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
	 * ����ƶ��¼�
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
	 * �����ҷ�¼�
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
