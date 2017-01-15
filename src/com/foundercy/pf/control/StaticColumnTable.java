/*
 * 创建日期 2004-6-12
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.foundercy.pf.control;

/**
 * @author fangyi
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class StaticColumnTable extends Table {
	
	private RowPreferedLayout layout;

	/**
	 * 固定列表格构造函数
	 * @param columns
	 */
	public StaticColumnTable(int columns,RowPreferedLayout layout){
		
		if(columns >= 1) {
			this.columns = columns;
		}
		this.rows = 0;
		
		this.layout = layout;
	}
	
	/**
	 * 增加一行
	 */
	public void addRow() {
		this.rows++;
	}
	
	/**
	 * to get an suitable postion for columns in this row;
	 * @param index
	 * @param cols
	 * @return
	 */
	public int getEnabledColumnInRow(int row, int startColumn, int cols) {
		int enabledIndex = -1;
		for(int i=startColumn; i< this.columns; i++){
			if(this.getCellAt(row,i).isArranged())
				continue;
			//本行列数已经不够
			if( (i+cols) > this.columns)
				
				break;
			boolean ok = true;
			for(int j = i; j<cols ;j++){
				if(this.getCellAt(row,j).isArranged()) {
					ok=false;
					break;
				}
			}
			if(ok) {
				enabledIndex = i;
				break;
			}
		}
		//System.out.println(enabledIndex);
		return enabledIndex;
	}	
	/**
	 * @param columns 要设置的 columns。
	 */
	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getColumnWidth() {
		return this.layout.getColumnWidth();
	}



	public int getRowHeight() {
		return this.layout.getRowHeight();
	}

}
