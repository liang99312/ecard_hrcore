/*
 * filename:  DateRefModel.java
 *
 * Version: 1.0
 *
 * Date: 2006-2-20
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.text.MaskFormatter;

public class DateRefModel implements RefModel
{
	
	private String formatStr = "yyyy-MM-dd";
	
	private Format format = new SimpleDateFormat(formatStr); // ʵ�ʴ�����еĸ�ʽ
	private MaskFormatter maskFormatter =null;
	
	/**
	 * �ж��������Ƿ����
	 * add by yangbo start 2008-03-24
	 */
	public Format showFormat = new SimpleDateFormat("yyyy��MM��dd��"); // �û��˵���ʾ��ʽ
	/**
	 * �ж��������Ƿ����
	 * add by yangbo end 2008-03-24
	 */
	
	public MaskFormatter getMaskFormatter(){
		
		if(maskFormatter==null){
			try {
				maskFormatter = new MaskFormatter("####��##��##��");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		maskFormatter.setPlaceholderCharacter('_');

		return maskFormatter;
	}
	public void setFormatStr(String formatStr) {
		
		this.formatStr = formatStr;
		format = new SimpleDateFormat(formatStr);
		
		if(formatStr.equalsIgnoreCase("yyyy-MM-dd") 
				|| formatStr.equalsIgnoreCase("dd-MM-yyyy")
				|| formatStr.equalsIgnoreCase("MM-dd-yyyy")
				|| formatStr.equalsIgnoreCase("MM/dd/yyyy")
				|| formatStr.equalsIgnoreCase("yyyy/MM/dd")
				|| formatStr.equalsIgnoreCase("dd/MM/yyyy")
				){
			 showFormat = new SimpleDateFormat("yyyy��MM��dd��");
			 try {
				maskFormatter = new MaskFormatter("####��##��##��");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(formatStr.equalsIgnoreCase("yyyy-MM") 
				|| formatStr.equalsIgnoreCase("MM-yyyy")
				|| formatStr.equalsIgnoreCase("MM/yyyy")
				|| formatStr.equalsIgnoreCase("yyyy/MM")
				){
			 showFormat = new SimpleDateFormat("yyyy��MM��");
			 try {
				maskFormatter = new MaskFormatter("####��##��");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * ���õ�ǰ�ĸ�ʽ�����Դ�FormatManager����ȡ��Ԥ�õĸ�ʽ
	 * 
	 * @param f �µĸ�ʽ
	 */
	public void setFormat(Format f)
	{
		this.format = f;
		
	}

	/**
	 * �õ�������еĸ�ʽ
	 * 
	 * @return ��ǰ�ĸ�ʽ��Ϣ
	 */
	public Format getFormat()
	{
		return this.format;
	}
	
	/**
	 * ���õ�ǰ����ʾ��ʽ�����Դ�FormatManager����ȡ��Ԥ�õĸ�ʽ
	 * 
	 * @param f �µ���ʾ��ʽ
	 */
	public void setShowFormat(Format f)
	{
		this.showFormat = f;
	}

	/**
	 * �õ���ǰ����ʾ��ʽ
	 * 
	 * @return ��ǰ����ʾ��ʽ
	 */
	public Format getShowFormat()
	{
		return this.showFormat;
	}
	
	/**
	 * �������ַ������õ���Ӧ��ֵ
	 * 
	 * @param name �����ַ���
	 * @return �����ַ�������Ӧ��ֵ
	 */
	public Object[] getValueByName(String name)
	{
		Object[] ret = new Object[1];
		if (format == null)
		{
			ret[0] = name;
		} else
		{
			try
			{
				ret[0] = format.format(getDateByShowValue(name));
			} 
			catch (Exception ex)
			{
			}
		}
		return ret;
	}

	/**
	 * ��ֵ���õ���Ӧ�������ַ���
	 * 
	 * @param value ֵ
	 * @return ֵ����Ӧ�������ַ���
	 */
	public String getNameByValue(Object value)
	{
		
		
		if(showFormat == null)
		{
			return value.toString();
		}
		else
		{
			Date date = null;
			if(value instanceof Date){
				date = (Date)value;
				
			}else{
			
				date =  getDateByActuralValue(value.toString());
			}
			if(date == null){
				return null;
			}
			return showFormat.format(date);
		}
	}
	
	/**
	 * ͨ����ʾ�����ڵõ������Ͷ���
	 * @param showValue �û�����ʾ������
	 * @return ���ڶ���
	 */
	private Date getDateByShowValue(String showValue)
	{
		Date date = null;
		try
		{
			date = (Date)showFormat.parseObject(showValue);
		}
		catch(Exception ex)
		{
			//ex.printStackTrace();
		}
		return date;
	}
	
	/**
	 * ͨ��ʵ�ֵ������ַ��������ڶ���
	 * @param value �����ַ����������ڿ��е���һ��
	 * @return ���ڶ���
	 */
	private Date getDateByActuralValue(String value)
	{
		Date date = null;
		
		if(value == null || value.trim().equals(""))
			return null;
		try
		{   
			/*added by lixufeng 20061206 start*/
			//this is modifed by lixufeng inorder to set the default value in the Ftable control
			if (format == null) {
				format = FormatManager.getDateFormat();
		    }
			/*added by lixufeng 20061206 end*/
			
			date = (Date)format.parseObject(value);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return date;		
	}
	
	
	
}
