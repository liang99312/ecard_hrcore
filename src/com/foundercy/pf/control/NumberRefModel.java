/*
 * filename:  NumberRefModel.java
 *
 * Version: 1.0
 *
 * Date: 2006-1-18
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import java.math.BigDecimal;
import java.text.Format;

/**
 * <p>
 * Title: 整型输入框模型
 * </p>
 * <p>
 * Description: 整型输入框的Model信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: foundercy
 * </p>
 * 
 * @author yangbo
 * @version 1.0
 */

public class NumberRefModel implements RefModel
{
	private Format format; // 显示格式
	private Double value;
	public NumberRefModel(Format format) {
		this.format = format;
	}
	/**
	 * 设置当前的格式，可以从FormatManager类中取得预置的格式
	 * 
	 * @param f 新的格式
	 */
	public void setFormat(Format f) {
		this.format = f;
	}

	/**
	 * 得到当前的格式
	 * 
	 * @return 当前的格式
	 */
	public Format getFormat() {
		return this.format;
	}

	/**
	 * 由名字字符串，得到对应的值
	 * 
	 * @param name
	 *            名字字符串
	 * @return 名字字符串，对应的值
	 */
	public Object[] getValueByName(String name) {
		Object[] ret = new Object[1];
		if (format == null) {
			ret[0] = name;
		} else {
			try {
				ret[0] = format.parseObject(name);
			} catch (Exception ex) {
			}
		}
		return ret;
	}

	/**
	 * 由值，得到对应的名字字符串
	 * 
	 * @param value
	 *            值
	 * @return 值，对应的名字字符串
	 */
	public String getNameByValue(Object value) {
		if (value == null || value.equals(""))
			return "";

		if (format == null) {
			return value.toString();
		} else {
			if (!(value instanceof Number)) {
				// double value = Double.parseDouble(value);
				boolean flag = true;
				try {
					new Double((String) value);
				} catch (NumberFormatException e) {
					flag = false;
				}
				if(flag){
					value = this.round(new BigDecimal(value.toString()),2);
					//return this.round(new BigDecimal(value.toString()),2);
					return format.format(value);//lgc delete 解决FDecimalField显示值四舍五入错误
				}else{
//					System.out.print("传入值'"+value+"'的格式错误，应为数字类型，现默认成0.00\n");
					return "0.00";
				}
			}
			return format.format(this.round(new BigDecimal(value.toString()),2));
			//return this.round(new BigDecimal(value.toString()),2);
		}
	}
	
	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	private BigDecimal round(BigDecimal v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal one = new BigDecimal("1");
		return v.divide(one, scale, BigDecimal.ROUND_HALF_UP);
	}
	public static void main(String[] args){
		String str = FormatManager.getDateFormat().format("20040513"
				);
		System.out.println(str);
//		Object ii = null;
//		try
//		{
//			ii = (FormatManager.getIntegerNumberFormat().parseObject(str));
//		} catch (Exception ex)
//		{
//			ex.printStackTrace();
//		}
//		if (ii != null)
//			System.out.println(ii);
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}

}
