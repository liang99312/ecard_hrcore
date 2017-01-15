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

	//����еı༭�ؼ�Editor�洢��
	protected Hashtable editors;
	
	/**
	 * editor ��Ԫ��ǰ�ı༭�ؼ�
	 * defaultEditor ��Ĭ�ϵı༭�ؼ�
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
	 * ����ָ���еı༭�ؼ�Editor
	 * @param row  ҵ������ģ�͵��к�
	 * @param _editor  �༭�ؼ�
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
		
		//ת����ǰ�ĵı༭�ؼ�
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
	 * ѡ��ǰ�ĵ�Ԫ���Ӧ�ı༭��
	 * @param e  		�����Ӧ�¼�
	 * @param table		��ǰ�������
	 */
	private void selectEditor(MouseEvent e,JTable table) {
	    int row;
	    if (e == null) {
	      row = table.getSelectionModel().getAnchorSelectionIndex();
	    } else {
	      row = table.rowAtPoint(e.getPoint());
	    }
	    
	    //ת��Ϊ����ģ�͵���
	    
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
