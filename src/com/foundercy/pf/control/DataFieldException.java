package com.foundercy.pf.control;


/**
 * 简单输入控件的异常基类
 */

public class DataFieldException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5531179818216545387L;
	private DataField dataField = null;

	/**
	 * 构造函数
	 * @param dataField 发生异常的简单输入控件
	 */
	public DataFieldException(DataField dataField) {
		this(null,dataField);
	}
	
	/**
	 * 构造函数
	 * @param message 异常信息
	 * @param dataField 发生异常的简单输入控件
	 */
	public DataFieldException(String message, DataField dataField) {
		this(message,null,dataField);
	}
	
	/**
	 * 构造函数
	 * @param message 异常信息
	 * @param cause 触发次异常的源异常
	 * @param dataField 发生异常的简单输入控件
	 */
	public DataFieldException(String message, Throwable cause, DataField dataField) {
		super(message, cause);
		this.dataField = dataField;
	}

	/**
	 * 取得，发生异常的简单输入控件
	 * @return 发生异常的简单输入控件
	 */
	public DataField getDataField() {
		return this.dataField;
	}

}

