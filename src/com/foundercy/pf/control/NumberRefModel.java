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
 * Title: ���������ģ��
 * </p>
 * <p>
 * Description: ����������Model��Ϣ
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
	private Format format; // ��ʾ��ʽ
	private Double value;
	public NumberRefModel(Format format) {
		this.format = format;
	}
	/**
	 * ���õ�ǰ�ĸ�ʽ�����Դ�FormatManager����ȡ��Ԥ�õĸ�ʽ
	 * 
	 * @param f �µĸ�ʽ
	 */
	public void setFormat(Format f) {
		this.format = f;
	}

	/**
	 * �õ���ǰ�ĸ�ʽ
	 * 
	 * @return ��ǰ�ĸ�ʽ
	 */
	public Format getFormat() {
		return this.format;
	}

	/**
	 * �������ַ������õ���Ӧ��ֵ
	 * 
	 * @param name
	 *            �����ַ���
	 * @return �����ַ�������Ӧ��ֵ
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
	 * ��ֵ���õ���Ӧ�������ַ���
	 * 
	 * @param value
	 *            ֵ
	 * @return ֵ����Ӧ�������ַ���
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
					return format.format(value);//lgc delete ���FDecimalField��ʾֵ�����������
				}else{
//					System.out.print("����ֵ'"+value+"'�ĸ�ʽ����ӦΪ�������ͣ���Ĭ�ϳ�0.00\n");
					return "0.00";
				}
			}
			return format.format(this.round(new BigDecimal(value.toString()),2));
			//return this.round(new BigDecimal(value.toString()),2);
		}
	}
	
	/**
	 * �ṩ��ȷ��С��λ�������봦��
	 * 
	 * @param v
	 *            ��Ҫ�������������
	 * @param scale
	 *            С���������λ
	 * @return ���������Ľ��
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
