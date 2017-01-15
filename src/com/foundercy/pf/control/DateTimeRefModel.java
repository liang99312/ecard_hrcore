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
	private Format format; // 实际存入库中的格式
	private Format showFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 用户端的显示格式
	
	/**
	 * 设置当前的格式，可以从FormatManager类中取得预置的格式
	 * 
	 * @param f 新的格式
	 */
	public void setFormat(Format f)
	{
		this.format = f;
	}

	/**
	 * 得到存入库中的格式
	 * 
	 * @return 当前的格式信息
	 */
	public Format getFormat()
	{
		return this.format;
	}
	
	/**
	 * 设置当前的显示格式，可以从FormatManager类中取得预置的格式
	 * 
	 * @param f 新的显示格式
	 */
	public void setShowFormat(Format f)
	{
		this.showFormat = f;
	}

	/**
	 * 得到当前的显示格式
	 * 
	 * @return 当前的显示格式
	 */
	public Format getShowFormat()
	{
		return this.showFormat;
	}
	
	/**
	 * 由名字字符串，得到对应的值
	 * 
	 * @param name 名字字符串
	 * @return 名字字符串，对应的值
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
	 * 由值，得到对应的名字字符串
	 * 
	 * @param value 值
	 * @return 值，对应的名字字符串
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
	 * 通过显示的日期得到日期型对象
	 * @param showValue 用户端显示的日期
	 * @return 日期对象
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
	 * 通过实现的日期字符串得日期对象
	 * @param value 日期字符串，与存放在库中的相一致
	 * @return 日期对象
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
