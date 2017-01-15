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
public class StaticRowTable extends Table {
	
	/**
	 * 固定列表格构造函数
	 * @param columns
	 */
	public StaticRowTable(int rows){
		if(rows >= 1) {
			this.rows = rows;
		}
		this.columns = 0;
	}
	
	/**
	 * 增加一行
	 */
	public void addColumn() {
		this.columns++;
	}
	
	/**
	 * to get an suitable postion for columns in this row;
	 * @param index
	 * @param cols
	 * @return
	 */
	public int getEnabledRowInColumn(int column, int startRow, int rs) {
		int enabledIndex = -1;
		for(int i=startRow; i< this.rows; i++){
			if(this.getCellAt(i,column).isArranged())
				continue;
			//本行列数已经不够
			if( (i+rs) > this.rows)
				
				break;
			boolean ok = true;
			for(int j = i; j<rs ;j++){
				if(this.getCellAt(j,column).isArranged()) {
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
	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumnWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getRowHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
