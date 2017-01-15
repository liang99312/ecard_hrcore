package com.foundercy.pf.control.table;

import java.awt.Component;
import java.util.Hashtable;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 * <p>Title: �к��е���ʵ���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author fangyi
 * @version 1.0
 */
public class FBaseTableRowCellRenderer implements TableCellRenderer {

	//��������洢��  KEYֵΪ�к�
	protected Hashtable renderers;
	
	/**
	 * renderer ��ǰ��Ԫ��������   
	 * defaultRenderer  ��ǰ�е�Ĭ�������
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
	 * ����е������  
	 * @param row  ����ģ�͵Ķ�Ӧ���к�  
	 * @param _renderer  ָ���������
	 */
	protected void setRendererAt(int row,TableCellRenderer _renderer){
		
		if(_renderer == null)return;
		
		if(renderers == null){
			renderers = new Hashtable();
		}
		
		renderers.put(new Integer(row),_renderer);
	}
	
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		//����ǰ�ı����ͼ��תΪ����ģ����
		int modelRow = row;
		int modelCol = column;
		
	    if(table instanceof FExpandTable){
	    	
	    	FExpandTable expandTable =(FExpandTable)table;
	    	
			FBaseTable baseTable = expandTable.getBaseTable();
			
			//��ͼ��ת������ģ���к�
			modelRow = baseTable.convertTableRowToModelRow(row);
			
			//ȡ�ö�Ӧ���ж���
			FBaseTableColumn tableColumn = (FBaseTableColumn)expandTable.getColumnModel().getColumn(column);
			//������IDȡ������ģ���к�
			modelCol = baseTable.getColumnModelIndex(tableColumn.getId());
			
		}
	    
		renderer = (TableCellRenderer)renderers.get(new Integer(modelRow));
	    if (renderer == null) {
	      renderer = defaultRenderer;
	    }
	    
	    if(renderer ==null){
	    	
	    	return null;
	    }
	    //System.out.println(row+"��"+modelRow);
	    return renderer.getTableCellRendererComponent(table,
	             value, isSelected, hasFocus, row, column);
		
	}

	
}
