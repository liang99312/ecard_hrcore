package com.foundercy.pf.control.table;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * <p>Title: 行号模型</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author fangyi
 * @version 1.0
 */
public class FBaseTableRowNoModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5007436707148392852L;

	/**
	 * 是否显示合计行(小计)
	 */
	private boolean showSumRow = false;
	/**
	 * 是否显示合计行（总计）
	 * @author jerry
	 */
	private boolean showSumRowAll = false;
	/**
	 * 合计行是否置于所有数据顶部。如果是ture, 则在顶部；如果是false，则在底部。
	 */
	private boolean sumRowAtTop = true;
	/**
	 * 总计行是否置于所有数据顶部。如果是ture, 则在顶部；如果是false，则在底部
	 * @author jerry
	 */
	private boolean sumRowAllAtTop = true;
	/**
	 * 小计行与合计行的位置关系，true为合计在小计之上，false为小计在合计之上
	 * @author jerry
	 */
	private boolean isAllTopPart = true;
	
	/**
	 * 
	 * @uml.property name="rowNoTitle" multiplicity="(0 1)"
	 */
	private String rowNoTitle = null;

	/**
	 * 
	 * @uml.property name="model"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private TableModel model = null;

	public FBaseTableRowNoModel(TableModel model) {

		this.setModel(model);

	}

	/**
	 * 设置一个表格模型，在此表格模型上增加行号
	 * @param model
	 * 
	 * @uml.property name="model"
	 */
	public void setModel(TableModel model) {

		this.model = model;

		if (this.model == null)
			return;

		this.model.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent ev) {
				//System.out.println(ev.getSource());
				fireTableChanged(ev);
			}
		});
	}

	/**
	 * 获得表格数据模型
	 * @return
	 * 
	 * @uml.property name="model"
	 */
	public TableModel getModel() {
		return this.model;
	}

	/**
	 *
	 * @param row
	 * @param col
	 * @return
	 */
	public final static String TOTAL_TITLE = "小计";
	
	/**
	 * 总计行行号显示名称
	 * @author jerry
	 */
	public final static String SUMROW_TITLE = "合计";
	
	public Object getValueAt(int row, int col) {

		if (col == 0) {
			if (this.rowNoTitle != null)
				return this.rowNoTitle;
			
			/************************begin********************/
			GetSumRowStute sumRowStute = new GetSumRowStute(this.isShowSumRow(), this.isShowSumRowAll(), this.isSumRowAtTop(), this.isSumRowAllAtTop(), this.isAllTopPart());
			int result = sumRowStute.getSumRowStute();
			if(result != GetSumRowStute.noSumRow) {
				return this.getSumRowCountByStute(result, row);
			}
			
			/************************end**********************/
//			if (this.isShowSumRow()) {
//				if (this.isSumRowAtTop()) {
//					if (row == 0) 
//						return TOTAL_TITLE;
//					else  
//						return new Integer(row);
//				} else {
//					if (row == this.getRowCount()-1) 
//						return TOTAL_TITLE;
//				}
//			}

			return new Integer(row + 1);

		}

		if (model == null)
			return null;
		
		return model.getValueAt(row, col - 1);
		
	}

	/**
	 *
	 * @param value
	 * @param row
	 * @param col
	 */
	public void setValueAt(Object value, int row, int col) {
		if (col == 0)
			return;

		if (model == null)
			return;

		model.setValueAt(value, row, col - 1);

	}

	public int getRowCount() {

		if (model == null)
			return 0;

		return model.getRowCount();

	}

	public int getColumnCount() {

		if (model == null)
			return 1;

		return model.getColumnCount() + 1;

	}

	/**
	 * 获得列名称
	 * @param col
	 * @return
	 */
	public String getColumnName(int col) {

		if (col == 0)
			return FBaseTable.ROWNUMBER_COLUMN_NAME;

		if (model == null)
			return null;

		return model.getColumnName(col - 1);
	}

	/**
	 * 表格的单元是否可编辑
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isCellEditable(int row, int col) {

		if (col == 0)
			return false;

		if (model == null)
			return false;

		return model.isCellEditable(row, col - 1);
	}

	/**
	 * 
	 * @uml.property name="rowNoTitle"
	 */
	public void setRowNoTitle(String title) {
		this.rowNoTitle = title;
	}

	/**
	 * 
	 * @uml.property name="rowNoTitle"
	 */
	public String getRowNoTitle() {
		return this.rowNoTitle;
	}

	/**
	 * @return
	 */
	protected boolean isShowSumRow() {
		return showSumRow;
	}

	/**
	 * @param showSumRow
	 */
	protected void setShowSumRow(boolean showSumRow) {
		this.showSumRow = showSumRow;
	}

	/**
	 * @return Returns the sumRowAtTop.
	 */
	public boolean isSumRowAtTop() {
		return sumRowAtTop;
	}

	/**
	 * @param sumRowAtTop The sumRowAtTop to set.
	 */
	public void setSumRowAtTop(boolean sumRowAtTop) {
		this.sumRowAtTop = sumRowAtTop;
	}

	public boolean isShowSumRowAll() {
		return showSumRowAll;
	}

	public void setShowSumRowAll(boolean showSumRowAll) {
		this.showSumRowAll = showSumRowAll;
	}

	public boolean isSumRowAllAtTop() {
		return sumRowAllAtTop;
	}

	protected void setSumRowAllAtTop(boolean sumRowAllAtTop) {
		this.sumRowAllAtTop = sumRowAllAtTop;
	}

	public boolean isAllTopPart() {
		return isAllTopPart;
	}

	protected void setAllTopPart(boolean isAllTopPart) {
		this.isAllTopPart = isAllTopPart;
	}
	
	/**
	 * 根据合计行状态值返回合计行标志
	 * @author jerry
	 * @param rowIndex 行索引
	 * @return Object 合计行标志
	 */
	private Object getSumRowCountByStute(int result, int rowIndex) {
		
		if(result == GetSumRowStute.onlyPartSumRowTop || result == GetSumRowStute.onlyAllSumRowTop) {
			if(rowIndex == 0) {
				return result == GetSumRowStute.onlyPartSumRowTop?TOTAL_TITLE:SUMROW_TITLE;
			}else {
				return new Integer(rowIndex);
			}
		}
		if((result == GetSumRowStute.onlyPartSumRowBottom || result == GetSumRowStute.onlyAllSumRowBottom)&& rowIndex == this.getRowCount()-1) {
			return result == GetSumRowStute.onlyPartSumRowBottom?TOTAL_TITLE:SUMROW_TITLE;
		}
		if(result == GetSumRowStute.sumRowAllPart || result == GetSumRowStute.sumRowPartAll) {
			if(rowIndex == 0) {
				return result == GetSumRowStute.sumRowAllPart?SUMROW_TITLE:TOTAL_TITLE;
			}else if(rowIndex == this.getRowCount()-1) {
				return result == GetSumRowStute.sumRowAllPart?TOTAL_TITLE:SUMROW_TITLE;
			}else {
				return new Integer(rowIndex);
			}
		}
		if(result == GetSumRowStute.sumRowTopAllPart || result == GetSumRowStute.sumRowTopPartAll) {
			if(rowIndex == 0) {
				return result == GetSumRowStute.sumRowTopAllPart?SUMROW_TITLE:TOTAL_TITLE;
			}else if(rowIndex == 1) {
				return result == GetSumRowStute.sumRowTopAllPart?TOTAL_TITLE:SUMROW_TITLE;
			}else {
				return new Integer(rowIndex-1);
			}
		}
		if(result == GetSumRowStute.sumRowBottomAllPart || result == GetSumRowStute.sumRowBottomPartAll) {
			if(rowIndex == this.getRowCount()-2) {
				return result == GetSumRowStute.sumRowBottomAllPart?SUMROW_TITLE:TOTAL_TITLE;
			}
			if(rowIndex == this.getRowCount()-1) {
				return result == GetSumRowStute.sumRowBottomAllPart?TOTAL_TITLE:SUMROW_TITLE;
			}
		}
		return new Integer(rowIndex+1);
		
	}

}