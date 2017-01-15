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
 * <p>Title: �����ؼ����쳣�� </p>
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
	/** �쳣������:��Ч��ֵ */
	public static final int INVALID_VALUE = 0;
	/** �쳣������:С����Сֵ */
	public static final int TOO_SMALL_VALUE = 1;
	/** �쳣������:�������ֵ */
	public static final int TOO_BIG_VALUE = 2;

	private int type;


	/**
	 * ���캯��
	 * @param dataField �����쳣�ļ�����ؼ�
	 * @param type �쳣������
	 */
	public IntegerDataFieldException(DataField dataField, int type) {
		this(null,dataField,type);
	}
	
	/**
	 * ���캯��
	 * @param message �쳣��Ϣ
	 * @param dataField �����쳣�ļ�����ؼ�
	 */
	public IntegerDataFieldException(String message, DataField dataField) {
		this(message,dataField,INVALID_VALUE);
	}

	/**
	 * ���캯��
	 * @param message �쳣��Ϣ
	 * @param dataField �����쳣�ļ�����ؼ�
	 * @param type �쳣������
	 */
	public IntegerDataFieldException(String message, DataField dataField, int type) {
		this(message,null,dataField,type);
	}
	
	/**
	 * ���캯��
	 * @param message �쳣��Ϣ
	 * @param cause �������쳣��Դ�쳣
	 * @param dataField �����쳣�ļ�����ؼ�
	 * @param type �쳣������
	 */
	public IntegerDataFieldException(String message, Throwable cause, DataField dataField, int type) {
		super(message, cause,dataField);
		setType(type);
	}

	/**
	 * �õ��쳣������
	 * 
	 * @return �쳣������
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * �����쳣������
	 * 
	 * @param type �쳣������
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

