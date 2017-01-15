package com.foundercy.pf.control;


/**
 * ������ؼ����쳣����
 */

public class DataFieldException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5531179818216545387L;
	private DataField dataField = null;

	/**
	 * ���캯��
	 * @param dataField �����쳣�ļ�����ؼ�
	 */
	public DataFieldException(DataField dataField) {
		this(null,dataField);
	}
	
	/**
	 * ���캯��
	 * @param message �쳣��Ϣ
	 * @param dataField �����쳣�ļ�����ؼ�
	 */
	public DataFieldException(String message, DataField dataField) {
		this(message,null,dataField);
	}
	
	/**
	 * ���캯��
	 * @param message �쳣��Ϣ
	 * @param cause �������쳣��Դ�쳣
	 * @param dataField �����쳣�ļ�����ؼ�
	 */
	public DataFieldException(String message, Throwable cause, DataField dataField) {
		super(message, cause);
		this.dataField = dataField;
	}

	/**
	 * ȡ�ã������쳣�ļ�����ؼ�
	 * @return �����쳣�ļ�����ؼ�
	 */
	public DataField getDataField() {
		return this.dataField;
	}

}

