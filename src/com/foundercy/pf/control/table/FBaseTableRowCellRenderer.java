package com.foundercy.pf.control.table;

import java.awt.Component;
import java.util.Hashtable;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 * <p>Title: 行号列的现实风格</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author fangyi
 * @version 1.0
 */
public class FBaseTableRowCellRenderer implements TableCellRenderer {

	//行描绘器存储区  KEY值为行号
	protected Hashtable renderers;
	
	/**
	 * renderer 当前单元格的描绘器   
	 * defaultRenderer  当前列的默认描绘器
	 */
	protected TableCellRenderer renderer,defaultRenderer;
	
	
	public FBaseTableRowCellRenderer() {
		renderers = new Hashtable();
		this.defaultRenderer = new FBaseTableCellRenderer();
	}
	
	public FBaseTableRowCellRenderer(TableCellRenderer _defaultRenderer) {
		renderers = new Hashtable();
		this.defaultRenderer = _defaultRenderer;
	}
	
	
	/**
	 * 添加行的描绘器  
	 * @param row  数据模型的对应的行号  
	 * @param _renderer  指定的描绘器
	 */
	protected void setRendererAt(int row,TableCellRenderer _renderer){
		
		if(_renderer == null)return;
		
		if(renderers == null){
			renderers = new Hashtable();
		}
		
		renderers.put(new Integer(row),_renderer);
	}
	
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		//将当前的表格视图行转为数据模型行
		int modelRow = row;
		int modelCol = column;
		
	    if(table instanceof FExpandTable){
	    	
	    	FExpandTable expandTable =(FExpandTable)table;
	    	
			FBaseTable baseTable = expandTable.getBaseTable();
			
			//视图行转换数据模型行号
			modelRow = baseTable.convertTableRowToModelRow(row);
			
			//取得对应的列定义
			FBaseTableColumn tableColumn = (FBaseTableColumn)expandTable.getColumnModel().getColumn(column);
			//根据列ID取得数据模型列号
			modelCol = baseTable.getColumnModelIndex(tableColumn.getId());
			
		}
	    
		renderer = (TableCellRenderer)renderers.get(new Integer(modelRow));
	    if (renderer == null) {
	      renderer = defaultRenderer;
	    }
	    
	    if(renderer ==null){
	    	
	    	return null;
	    }
	    //System.out.println(row+"："+modelRow);
	    return renderer.getTableCellRendererComponent(table,
	             value, isSelected, hasFocus, row, column);
		
	}

	
}
