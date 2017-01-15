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
 * С���ؼ����쳣��
 */
public class FDecimalDataException extends DataFieldException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6085879432826047108L;
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
	public FDecimalDataException(DataField dataField, int type) {
		this(null,dataField,type);
	}
	
	/**
	 * ���캯��
	 * @param message �쳣��Ϣ
	 * @param dataField �����쳣�ļ�����ؼ�
	 * @param type �쳣������
	 */
	public FDecimalDataException(String message, DataField dataField, int type) {
		this(message,null,dataField,type);
	}
	
	/**
	 * ���캯��
	 * @param message �쳣��Ϣ
	 * @param cause �������쳣��Դ�쳣
	 * @param dataField �����쳣�ļ�����ؼ�
	 * @param type �쳣������
	 */
	public FDecimalDataException(String message, Throwable cause, DataField dataField, int type) {
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