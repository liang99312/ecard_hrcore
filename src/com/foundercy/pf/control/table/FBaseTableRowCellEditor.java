package com.foundercy.pf.control.table;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Hashtable;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import javax.swing.AbstractCellEditor;

public class FBaseTableRowCellEditor extends AbstractCellEditor implements TableCellEditor {

	//表格行的编辑控件Editor存储区
	protected Hashtable editors;
	
	/**
	 * editor 单元格当前的编辑控件
	 * defaultEditor 列默认的编辑控件
	 */
	protected TableCellEditor editor, defaultEditor;
	
	public FBaseTableRowCellEditor(){ 
		editors = new Hashtable();
		this.defaultEditor = new FBaseTableCellEditor();
	}
	
	public FBaseTableRowCellEditor(TableCellEditor _editor){ 
		editors = new Hashtable();
		this.defaultEditor = _editor;
	}
	/**
	 * 设置指定行的编辑控件Editor
	 * @param row  业务数据模型的行号
	 * @param _editor  编辑控件
	 */
	protected void setEditorAt(int row,TableCellEditor _editor){
		
		if(_editor == null)return;
		
		if(editors == null){
			editors = new Hashtable();
		}
		
		this.editors.put(new Integer(row), _editor);
	}
	
	
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		
		return editor.getTableCellEditorComponent(table, value, isSelected, row, column);
		
	}

	public void addCellEditorListener(CellEditorListener l) {
		if(editor == null){
			editor = defaultEditor;
		}
		//super.addCellEditorListener(l);
		
		this.editor.addCellEditorListener(l);		
	}

	public void cancelCellEditing() {
		if(editor == null){
			editor = defaultEditor;
		}
		this.editor.cancelCellEditing();
	}

	public Object getCellEditorValue() {
		
		if(editor == null){
			editor = defaultEditor;
		}
		return this.editor.getCellEditorValue();
	}

	public boolean isCellEditable(EventObject anEvent) {
		
		if(anEvent == null){
			this.editor= this.defaultEditor;
		}else{
			JTable table = (JTable)anEvent.getSource();
			
			selectEditor(null,table);
			//selectEditor((MouseEvent)anEvent,table);
		}
		
		return this.editor.isCellEditable(anEvent);
	}

	public void removeCellEditorListener(CellEditorListener l) {
		if(editor == null){
			editor = defaultEditor;
		}
		//super.removeCellEditorListener(l);
		this.editor.removeCellEditorListener(l);
	}

	public boolean shouldSelectCell(EventObject anEvent) {
		if(anEvent == null){
			this.editor= this.defaultEditor;
		}else{
			JTable table = (JTable)anEvent.getSource();
			selectEditor((MouseEvent)anEvent,table);
		}
		
		//转换当前的的编辑控件
		return this.editor.shouldSelectCell(anEvent);
	}

	public boolean stopCellEditing() {
		if (editor == null) {
		      editor = defaultEditor;
		}
		
		//return super.stopCellEditing();
		return this.editor.stopCellEditing();
	}
	
	/**
	 * 选择当前的单元格对应的编辑器
	 * @param e  		鼠标响应事件
	 * @param table		当前操作表格
	 */
	private void selectEditor(MouseEvent e,JTable table) {
	    int row;
	    if (e == null) {
	      row = table.getSelectionModel().getAnchorSelectionIndex();
	    } else {
	      row = table.rowAtPoint(e.getPoint());
	    }
	    
	    //转换为数据模型的行
	    
	    if(table instanceof FExpandTable){
			FBaseTable baseTable = ((FExpandTable)table).getBaseTable();
			row = baseTable.convertTableRowToModelRow(row);
		}
	    
	    editor = (TableCellEditor)editors.get(new Integer(row));
	    if (editor == null) {
	      editor = defaultEditor;
	    }
	  }

	protected void fireEditingStopped(){
		
		//System.out.println("fireEditingStopped");
		super.fireEditingStopped();
	}
}
