package com.foundercy.pf.control;

public class TableConstraints {

	/**
	 * 容器中，控件占用的行数
	 * 
	 * @uml.property name="rows" multiplicity="(0 1)"
	 */
	private int rows;

	/**
	 * 容器中，控件占用的列数
	 * 
	 * @uml.property name="columns" multiplicity="(0 1)"
	 */
	private int columns;
	
	
	/**
	 * 控件高度是否可以根据父控件的高度进行调整。
	 */
	private boolean heightReadjustable = false;


	private boolean witdthReadjustable = true;
	/**
	 * 构造函数
	 * 
	 * @param columns 容器中，控件占用的列数
	 * @param rows 容器中，控件占用的行数
	 */
	public TableConstraints(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
	}
	
	/**
	 * 构造函数
	 * 
	 * @param columns 容器中，控件占用的列数
	 * @param rows 容器中，控件占用的行数
	 * @param heightAdjustable,控件高度是否可以根据父控件的高度进行调整
	 */
	public TableConstraints(int rows, int columns,boolean heightReadjustable,boolean widthReadjustable) {
		this.rows = rows;
		this.columns = columns;
		this.heightReadjustable = heightReadjustable;
		this.witdthReadjustable = widthReadjustable;
	}
	
	/**
	 * 构造函数
	 * 
	 * @param columns 容器中，控件占用的列数
	 * @param rows 容器中，控件占用的行数
	 * @param heightAdjustable,控件高度是否可以根据父控件的高度进行调整
	 */
	public TableConstraints(int rows, int columns,boolean heightReadjustable) {
		this.rows = rows;
		this.columns = columns;
		this.heightReadjustable = heightReadjustable;
	}

	/**
	 * 取得，容器中，控件占用的行数
	 * 
	 * @return 容器中，控件占用的行数
	 * 
	 * @uml.property name="rows"
	 */
	public int getRows() {
		return this.rows;
	}

	/**
	 * 设置，容器中，控件占用的行数
	 * 
	 * @param rows 容器中，控件占用的行数
	 * 
	 * @uml.property name="rows"
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * 取得，容器中，控件占用的列数
	 * 
	 * @return 容器中，控件占用的列数
	 * 
	 * @uml.property name="columns"
	 */
	public int getColumns() {
		return this.columns;
	}

	/**
	 * 设置，容器中，控件占用的列数
	 * 
	 * @param columns 容器中，控件占用的列数
	 * 
	 * @uml.property name="columns"
	 */
	public void setColumns(int columns) {
		this.columns = columns;
	}

	/**
	 * @return 返回 heightAdjustable。
	 */
	public boolean isHeightReadjustable() {
		return heightReadjustable;
	}
	/**
	 * @param heightAdjustable 要设置的 heightAdjustable。
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
