/*
 * �������� 2004-6-12
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package com.foundercy.pf.control;

import java.util.HashMap;

/**
 * @author fangyi
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public abstract class Table {
	
	protected int columns = 1;
	protected int rows = 1;
	private HashMap rowHeightMap = new HashMap();
	private HashMap columnWidthMap = new HashMap();
	private HashMap cellMap = new HashMap();

	public TableCell getCellAt(int row, int column) {
		
		if (row < 0 || column < 0 || column >= columns || row >= rows)
			return null;
		
		//��Hash���д���Key
		String key = "" + row + "_" + column;
		
		TableCell cell = (TableCell)cellMap.get(key);
		
		if(cell == null) {
			cell = new TableCell();
			cellMap.put(key,cell);
		}
		return cell;
	}
	
	public int getColumns()  {
		return columns;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getRowHeight(int row) {
		if(row >=0 && row<rows) {
			
			Integer key = new Integer(row);
			
			Integer height = (Integer)rowHeightMap.get(key);
			
			if(height == null){
				height = new Integer(this.getRowHeight());
				rowHeightMap.put(key,height);
			}
			return height.intValue();
			
		}else {
			return 0;
		}
	}
	public int getColumnWidth(int column) {
		if(column >=0 && column<columns) {
			
			Integer key = new Integer(column);
			
			Integer width = (Integer)columnWidthMap.get(key);
			
			if(width == null){
				width = new Integer(this.getColumnWidth());
//				System.out.println("get:"+width);
				columnWidthMap.put(key,width);
			}
			return width.intValue();
			
		}else {
			return 0;
		}
	}	
	public void setRowHeight(int row,int height) {
		if (row >= 0 && row < rows) {
			Integer key = new Integer(row);
			Integer h = new Integer(height);
			
			rowHeightMap.put(key,h);
		}
	}
	
	public void setColumnWidth(int column,int width) {
		if (column >= 0 && column < columns) {
			Integer key = new Integer(column);
			Integer w = new Integer(width);
//			System.out.println("get:"+width);
			columnWidthMap.put(key,w);
		}
	}
	
	public boolean isHeightReAdjustable(int row) {
		
		boolean readjustable = true;
		for(int i=0;i<this.columns; i++){
			TableCell tc = this.getCellAt(row,i);
			if(!tc.isHeightReadjustable() && tc.isArranged()){
				readjustable = false;
				break;
			}
			
		}
		return readjustable;
	}	

	public boolean isWidthReAdjustable(int column) {
		
		boolean readjustable = true;
		for(int i=0;i<this.rows; i++){
			TableCell tc = this.getCellAt(i,column);
			if(!tc.isWidthReadjustable() && tc.isArranged()){
				readjustable = false;
				break;
			}
			
		}
		return readjustable;
	}		
	/**
	 * @param columns Ҫ���õ� columns��
	 */
	public void setColumns(int columns) {
		
	}
	/**
	 * @param columns Ҫ���õ� columns��
	 */
	public void setRows(int rows) {
		
	}
	
	public void addRow() {
		
	}
	public void addColumn() {
		
	}
	
	public abstract int getColumnWidth();


	public abstract int getRowHeight();
}
