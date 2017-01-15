/*
 * filename:  FDecimalDataException.java
 *
 * Version: 1.0
 *
 * Date: 2005-12-28
 *
 * Copyright notice:  2005 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

/**
 * 小数控件的异常类
 */
public class FDecimalDataException extends DataFieldException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6085879432826047108L;
	/** 异常类类型:无效的值 */
	public static final int INVALID_VALUE = 0;
	/** 异常类类型:小于最小值 */
	public static final int TOO_SMALL_VALUE = 1;
	/** 异常类类型:大于最大值 */
	public static final int TOO_BIG_VALUE = 2;
	private int type;

	
	/**
	 * 构造函数
	 * @param dataField 发生异常的简单输入控件
	 * @param type 异常类类型
	 */
	public FDecimalDataException(DataField dataField, int type) {
		this(null,dataField,type);
	}
	
	/**
	 * 构造函数
	 * @param message 异常信息
	 * @param dataField 发生异常的简单输入控件
	 * @param type 异常类类型
	 */
	public FDecimalDataException(String message, DataField dataField, int type) {
		this(message,null,dataField,type);
	}
	
	/**
	 * 构造函数
	 * @param message 异常信息
	 * @param cause 触发次异常的源异常
	 * @param dataField 发生异常的简单输入控件
	 * @param type 异常类类型
	 */
	public FDecimalDataException(String message, Throwable cause, DataField dataField, int type) {
		super(message, cause,dataField);
		setType(type);
	}

	/**
	 * 得到异常类类型
	 * 
	 * @return 异常类类型
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * 设置异常类类型
	 * 
	 * @param type 异常类类型
	 */
	public void setType(int type) {
		if (INVALID_VALUE != type
			&& TOO_SMALL_VALUE != type
			&& TOO_BIG_VALUE != type) {
			throw new DataFieldException("unsupportted type", getDataField());
		}

		this.type = type;
	}

}