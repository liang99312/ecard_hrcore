/*
 * filename:  IntegerDataFieldException.java
 *
 * Version: 1.0
 *
 * Date: 2005-1-6
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

/**
 * <p>Title: 整数控件的异常类 </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: foundercy</p>
 * @author yangbo
 * @version 1.0
 */
public class IntegerDataFieldException extends DataFieldException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8907589991648417146L;
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
	public IntegerDataFieldException(DataField dataField, int type) {
		this(null,dataField,type);
	}
	
	/**
	 * 构造函数
	 * @param message 异常信息
	 * @param dataField 发生异常的简单输入控件
	 */
	public IntegerDataFieldException(String message, DataField dataField) {
		this(message,dataField,INVALID_VALUE);
	}

	/**
	 * 构造函数
	 * @param message 异常信息
	 * @param dataField 发生异常的简单输入控件
	 * @param type 异常类类型
	 */
	public IntegerDataFieldException(String message, DataField dataField, int type) {
		this(message,null,dataField,type);
	}
	
	/**
	 * 构造函数
	 * @param message 异常信息
	 * @param cause 触发次异常的源异常
	 * @param dataField 发生异常的简单输入控件
	 * @param type 异常类类型
	 */
	public IntegerDataFieldException(String message, Throwable cause, DataField dataField, int type) {
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

