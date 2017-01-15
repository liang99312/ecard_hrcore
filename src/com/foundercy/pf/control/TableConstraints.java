package com.foundercy.pf.control;

public class TableConstraints {

	/**
	 * �����У��ؼ�ռ�õ�����
	 * 
	 * @uml.property name="rows" multiplicity="(0 1)"
	 */
	private int rows;

	/**
	 * �����У��ؼ�ռ�õ�����
	 * 
	 * @uml.property name="columns" multiplicity="(0 1)"
	 */
	private int columns;
	
	
	/**
	 * �ؼ��߶��Ƿ���Ը��ݸ��ؼ��ĸ߶Ƚ��е�����
	 */
	private boolean heightReadjustable = false;


	private boolean witdthReadjustable = true;
	/**
	 * ���캯��
	 * 
	 * @param columns �����У��ؼ�ռ�õ�����
	 * @param rows �����У��ؼ�ռ�õ�����
	 */
	public TableConstraints(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
	}
	
	/**
	 * ���캯��
	 * 
	 * @param columns �����У��ؼ�ռ�õ�����
	 * @param rows �����У��ؼ�ռ�õ�����
	 * @param heightAdjustable,�ؼ��߶��Ƿ���Ը��ݸ��ؼ��ĸ߶Ƚ��е���
	 */
	public TableConstraints(int rows, int columns,boolean heightReadjustable,boolean widthReadjustable) {
		this.rows = rows;
		this.columns = columns;
		this.heightReadjustable = heightReadjustable;
		this.witdthReadjustable = widthReadjustable;
	}
	
	/**
	 * ���캯��
	 * 
	 * @param columns �����У��ؼ�ռ�õ�����
	 * @param rows �����У��ؼ�ռ�õ�����
	 * @param heightAdjustable,�ؼ��߶��Ƿ���Ը��ݸ��ؼ��ĸ߶Ƚ��е���
	 */
	public TableConstraints(int rows, int columns,boolean heightReadjustable) {
		this.rows = rows;
		this.columns = columns;
		this.heightReadjustable = heightReadjustable;
	}

	/**
	 * ȡ�ã������У��ؼ�ռ�õ�����
	 * 
	 * @return �����У��ؼ�ռ�õ�����
	 * 
	 * @uml.property name="rows"
	 */
	public int getRows() {
		return this.rows;
	}

	/**
	 * ���ã������У��ؼ�ռ�õ�����
	 * 
	 * @param rows �����У��ؼ�ռ�õ�����
	 * 
	 * @uml.property name="rows"
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * ȡ�ã������У��ؼ�ռ�õ�����
	 * 
	 * @return �����У��ؼ�ռ�õ�����
	 * 
	 * @uml.property name="columns"
	 */
	public int getColumns() {
		return this.columns;
	}

	/**
	 * ���ã������У��ؼ�ռ�õ�����
	 * 
	 * @param columns �����У��ؼ�ռ�õ�����
	 * 
	 * @uml.property name="columns"
	 */
	public void setColumns(int columns) {
		this.columns = columns;
	}

	/**
	 * @return ���� heightAdjustable��
	 */
	public boolean isHeightReadjustable() {
		return heightReadjustable;
	}
	/**
	 * @param heightAdjustable Ҫ���õ� heightAdjustable��
	 */
	public void setHeightReadjustable(boolean heightReAdjustable) {
		this.heightReadjustable = heightReAdjustable;
	}
	public boolean isWitdthReadjustable() {
		return witdthReadjustable;
	}
	public void setWitdthReadjustable(boolean witdthReadjustable) {
		this.witdthReadjustable = witdthReadjustable;
	}
}
