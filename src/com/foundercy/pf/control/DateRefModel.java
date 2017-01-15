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
	
	private Format format = new SimpleDateFormat(formatStr); // 实际存入库中的格式
	private MaskFormatter maskFormatter =null;
	
	/**
	 * 判断年月日是否合理
	 * add by yangbo start 2008-03-24
	 */
	public Format showFormat = new SimpleDateFormat("yyyy年MM月dd日"); // 用户端的显示格式
	/**
	 * 判断年月日是否合理
	 * add by yangbo end 2008-03-24
	 */
	
	public MaskFormatter getMaskFormatter(){
		
		if(maskFormatter==null){
			try {
				maskFormatter = new MaskFormatter("####年##月##日");
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
			 showFormat = new SimpleDateFormat("yyyy年MM月dd日");
			 try {
				maskFormatter = new MaskFormatter("####年##月##日");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(formatStr.equalsIgnoreCase("yyyy-MM") 
				|| formatStr.equalsIgnoreCase("MM-yyyy")
				|| formatStr.equalsIgnoreCase("MM/yyyy")
				|| formatStr.equalsIgnoreCase("yyyy/MM")
				){
			 showFormat = new SimpleDateFormat("yyyy年MM月");
			 try {
				maskFormatter = new MaskFormatter("####年##月");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

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
			//ex.printStackTrace();
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
