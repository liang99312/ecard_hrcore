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
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeRefModel implements RefModel
{
	private Format format; // ʵ�ʴ�����еĸ�ʽ
	private Format showFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // �û��˵���ʾ��ʽ
	
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
			return showFormat.format(getDateByActuralValue(value.toString()));
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
			ex.printStackTrace();
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
		try
		{
			date = (Date)format.parseObject(value);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return date;		
	}
	
	
	
}
