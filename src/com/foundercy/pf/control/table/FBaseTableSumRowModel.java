package com.foundercy.pf.control.table;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * <p>Title:合计模型 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author fangyi
 * @version 1.0
 */

public class FBaseTableSumRowModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8440861911392693358L;

	private FBaseTableColumnModel columnModel = null;
	
//	public final static String TOTAL_TITLE = "合计：";
	
	private boolean totalExisted = false;
	
	/**
	 * 是否显示汇总行(小计)
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
	 * 合计行状态值
	 * @author jerry
	 */
	private int result = 0;
	
	/**
	 * 
	 * @uml.property name="model"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private TableModel model = null;

	/**
	 * 
	 * @uml.property name="sumTitle" multiplicity="(0 1)"
	 */

	public FBaseTableSumRowModel(TableModel model) {
		this.setModel(model);

	}

	/**
	 * 
	 * @uml.property name="model"
	 */
	public void setModel(TableModel model) {
		this.model = model;
		this.model.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent ev) {
				fireTableChanged(ev);
				//FBaseTableSumRowModel.this.fireTableDataChanged();
				
				//计算合计行的所占的行数
				int iTopRowOfSum =0;		//顶部和计行数
				int iBottomRowOfSum = 0; 	//底部合计行数
				
				if(showSumRow && sumRowAllAtTop) iTopRowOfSum++;
				
				if(showSumRow && !sumRowAllAtTop) iBottomRowOfSum++;
				
				if(showSumRowAll && sumRowAllAtTop)iTopRowOfSum++;
				
				if(showSumRowAll && !sumRowAllAtTop)iBottomRowOfSum++;
				
				//刷新合计行
				if(iTopRowOfSum>0)fireTableRowsUpdated(0, iTopRowOfSum-1);
				
				if(iBottomRowOfSum>0)fireTableRowsUpdated(getRowCount()-iBottomRowOfSum-iTopRowOfSum-1, getRowCount()-1);
				
				
			}
		});
	}

	public TableModel getModel() {
		return this.model;
	}
	
	/**
	 * 获得行数
	 * @return
	 */
	public int getRowCount() {
		
		int rowCount = 0;
		if(model != null){
			rowCount =model.getRowCount();
		}
		
		if(this.showSumRow){
			rowCount ++;
		}
		
		if(this.showSumRowAll){
			rowCount ++;
		}
		
		return rowCount;
		
		/**********************end*********************/
	}

	/**
	 * 获得列数
	 */
	public int getColumnCount() {
		if (model == null)
			return 0;
		return model.getColumnCount();
	}

	/**
	 * 处理合计行的返回的值，包括文本"合计"，和合计数。
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
            
		if (model == null)
			return null;
		
		//System.out.println(" 行："+rowIndex);
		rowIndex = this.convertViewRowToModelRow(rowIndex);
		
		if(rowIndex ==-1){
			//小计行
			FBaseTableColumn column = columnModel.getColumn(model.getColumnName(columnIndex));
			return getSumRowPartCount(model, columnIndex, column);
			//return this.getSumRowCountByStute(this.result, model, columnIndex, column);
		}else if(rowIndex ==-2){
			//总计行
			
			FBaseTableColumn column = columnModel.getColumn(model.getColumnName(columnIndex));
			return this.getSumRowAllCount(model, columnIndex, column);
		}else if(rowIndex>=0){
			//System.out.println("getValueAt("+rowIndex + ","+columnIndex+")");
			return model.getValueAt(rowIndex, columnIndex);
		}else{
			return null;
		}
		
	}

	public Object getAnObjectForColumn(int col) {
		if (model == null)
			return new Object();

		for (int i = 0; i < model.getRowCount(); i++) {
			if (model.getValueAt(i, col) != null)
				return model.getValueAt(i, col);
		}
		return new Object();
	}

	/**
	 * 
	 */
	public boolean isCellEditable(int row, int col) {
		
		row = this.convertViewRowToModelRow(row);
		if(row <= -1){
			return false;
		}else{
			return model.isCellEditable(row, col);
		}
		/***********************end**********************/
		
		
	}
	
	/**
	 * 合计模型的视图行转换为数据模型行
	 * @param row   表格界面视图行号
	 * @return    -1:小计行　　-2：总计行  -3 无效行 >=0 数据行　　   
	 * @author 	lindx 
	 */
	protected int convertViewRowToModelRow(int row){
		
		//取得数据模型的记录行数
		int iSize = this.model.getRowCount();
		
		
		if(this.result ==GetSumRowStute.noSumRow){
			//没有合计行 
			
			if(row>=0 && row<iSize){
				return row;
			}
			
			return -3;
		}
		
		//1、只有小计行，且小计行在上部 0行是小计行
		if(this.result == GetSumRowStute.onlyPartSumRowTop){
			
			
			if(row==0)return -1;
			
			if(row>=1 && row<=iSize){  //数据行范围{1，iSize}
				return row-1;
			}
			
			return -3;
		}
		
		//2、只有小计行，且小计行在底部  iSize行是小计行
		if(this.result == GetSumRowStute.onlyPartSumRowBottom){
			//只有小计行，且小计行在上部
			
			if(row==iSize)return -1;
			
			if(row>=0 && row<iSize){  //数据行范围{0，iSize-1}
				return row;
			}
			
			return -3;
		}
		
		//3、只有总计行，且总计行在上部 0行是总计行
		if(this.result == GetSumRowStute.onlyAllSumRowTop){
			
			
			if(row==0)return -2;
			
			if(row>=1 && row<=iSize){  //数据行范围{1，iSize}
				return row-1;
			}
			
			return -3;
		}
		
		//4、只有总计行，且总计行在上部 iSize行是总计行
		if(this.result == GetSumRowStute.onlyAllSumRowBottom){
			
			if(row==iSize)return -2;
			
			if(row>=0 && row<iSize){  //数据行范围{0，iSize-1}
				return row;
			}
			
			return -3;
		}
		
		//5、既有总计行又有小计行，且总计行和小计行在上部，总计行在小计行之上
		//  0 行是总计行  1行是小计行  2,iSize+1行 是数据行
		if(this.result == GetSumRowStute.sumRowTopAllPart){
			
			if(row==0)return -2;
			
			if(row==1)return -1;
			
			if(row>=2 && row<=iSize+1){  //数据行范围{2，iSize+1}
				return row-2;
			}
			
			return -3;
		}
		
		//6、既有总计行又有小计行，且总计行和小计行在上部，总计行在小计行之下
		//  1 行是总计行  0行是小计行  2,iSize+1行 是数据行
		if(this.result == GetSumRowStute.sumRowTopPartAll){
			
			if(row==1)return -2;
			
			if(row==0)return -1;
			
			if(row>=2 && row<=iSize+1){  //数据行范围{2，iSize+1}
				return row-2;
			}
			
			return -3;
		}
		
		
		//7、既有总计行又有小计行，且总计行在上部，小计行在底部
		//  0 行是总计行  iSize+1行是小计行  1,iSize行 是数据行
		if(this.result == GetSumRowStute.sumRowAllPart){
			
			if(row==0)return -2;
			
			if(row==iSize+1)return -1;
			
			if(row>=1 && row<=iSize){  //数据行范围{1，iSize}
				return row-1;
			}
			
			return -3;
		}
		
		//8、既有总计行又有小计行，且总计行在底部，小计行在上部
		//  0 行是总计行  iSize+1行是小计行  1,iSize行 是数据行
		if(this.result == GetSumRowStute.sumRowPartAll){
			
			if(row==0)return -1;
			
			if(row==iSize+1)return -2;
			
			if(row>=1 && row<=iSize){  //数据行范围{1，iSize}
				return row-1;
			}
			
			return -3;
		}

		//9、既有总计行又有小计行，且总计行在底部，小计行在上部
		//  iSize+1 行是总计行 0 行是小计行  1,iSize行 是数据行
		if(this.result == GetSumRowStute.sumRowPartAll){
			
			if(row==0)return -1;
			
			if(row==iSize+1)return -2;
			
			if(row>=1 && row<=iSize){  //数据行范围{1，iSize}
				return row-1;
			}
			
			return -3;
		}
		
		//10、既有总计行又有小计行，且总计行和小计行在底部，总计行在小计行之上
		//  iSize 行是总计行 iSize+1 行是小计行  0,iSize-1行 是数据行
		if(this.result == GetSumRowStute.sumRowBottomAllPart){
			
			if(row==iSize)return -2;
			
			if(row==iSize+1)return -1;
			
			if(row>=0 && row<=iSize-1){  //数据行范围{0，iSize-1}
				return row;
			}
			
			return -3;
		}
		
		//11、既有总计行又有小计行，且总计行和小计行在底部，总计行在小计行之下
		//  iSize+1 行是总计行 iSize 行是小计行  0,iSize-1行 是数据行
		if(this.result == GetSumRowStute.sumRowBottomPartAll){
			
			if(row==iSize)return -1;
			
			if(row==iSize+1)return -2;
			
			if(row>=0 && row<=iSize-1){  //数据行范围{0，iSize-1}
				return row;
			}
			
			return -3;
		}
		
		return row;
	}
	
	
	/**
	 * 合计行数据模型行号转换为界面行号
	 * @param modelRow  合计行数据模型行号
	 * @return 
	 */
	public int convertModelRowToViewRow(int modelRow){
		
		//取得数据模型的记录行数
		int iSize = this.model.getRowCount();
		
		//如果行号不在[0,iSize-1]范围内，设置为0
		
		if(modelRow >=iSize || modelRow <0)modelRow =iSize-1;
		
		if(this.result ==GetSumRowStute.noSumRow){
			//没有合计行 
			return modelRow;
		}
		
		
		if(this.result ==GetSumRowStute.onlyPartSumRowTop
				|| this.result ==GetSumRowStute.onlyAllSumRowTop
				|| this.result ==GetSumRowStute.sumRowAllPart
				|| this.result ==GetSumRowStute.sumRowPartAll){
			
			//只有小计行（或者总计行）在上部  0行是合计行
			
			return modelRow+1;
		}
		
		if(this.result ==GetSumRowStute.sumRowTopAllPart
				|| this.result ==GetSumRowStute.sumRowTopPartAll
				){
			//小计行和总计行在上部  0、1行是合计行
			
			return modelRow+2;
			
		}else{
			
			return modelRow;
		}	
		
	}
	/**
	 *
	 * @param value
	 * @param row
	 * @param col
	 */
	public void setValueAt(Object value, int row, int col) {
		//System.out.println("Excute model setValue("+value+")");
		if (model == null)
			return;
		
		row = this.convertViewRowToModelRow(row);
		
		if(row == -1){
			return;
		}else{
			model.setValueAt(value, row, col);
		}
		
		
	}

	public FBaseTableColumnModel getColumnModel() {
		return columnModel;
	}

	protected void setColumnModel(FBaseTableColumnModel columnModel) {
		this.columnModel = columnModel;
	}

	protected boolean isTotalExisted() {
		return totalExisted;
	}

	protected void setTotalExisted(boolean totalExisted) {
		this.totalExisted = totalExisted;
	}
	
	protected boolean isShowSumRow() {
		return showSumRow;
	}

	protected void setShowSumRow(boolean showSumRow) {
		this.showSumRow = showSumRow;
		this.setSumRowStute(showSumRow, this.showSumRowAll, this.sumRowAtTop, this.sumRowAllAtTop, this.isAllTopPart);
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
		this.setSumRowStute(this.showSumRow, this.showSumRowAll, sumRowAtTop, this.sumRowAllAtTop, this.isAllTopPart);
	}

	public boolean isShowSumRowAll() {
		return showSumRowAll;
	}

	public void setShowSumRowAll(boolean showSumRowAll) {
		this.showSumRowAll = showSumRowAll;
		this.setSumRowStute(this.showSumRow, showSumRowAll, this.sumRowAtTop, this.sumRowAllAtTop, this.isAllTopPart);
	}

	public boolean isSumRowAllAtTop() {
		return sumRowAllAtTop;
	}

	protected void setSumRowAllAtTop(boolean sumRowAllAtTop) {
		this.sumRowAllAtTop = sumRowAllAtTop;
		this.setSumRowStute(this.showSumRow, this.showSumRowAll, this.sumRowAtTop, sumRowAllAtTop, this.isAllTopPart);
	}

	public boolean isAllTopPart() {
		return isAllTopPart;
	}

	protected void setAllTopPart(boolean isAllTopPart) {
		this.isAllTopPart = isAllTopPart;
		this.setSumRowStute(this.showSumRow, this.showSumRowAll, this.sumRowAtTop, this.sumRowAllAtTop, isAllTopPart);
	}
	
	/**
	 * 计算小计行的合计值
	 * @author jerry
	 * @param tableModel 表格模型
	 * @param columnIndex 列索引
	 * @return SumRowValueTypes 合计值的特殊类型 合计值
	 */
	private SumRowValueTypes getSumRowPartCount(TableModel tableModel, int columnIndex, FBaseTableColumn column) {
		
		//add by bigdog 080422 解决拖动滚动框时
		//出现空白的问题
		if (column == null)
			return new SumRowValueTypes(null);
		
		if (column.isTotal()) { 
			/**
			 * 支持自制合计行算法，此处做如下修改
			 * @author jerry
			 */
			/***************************begin************************/
			if(column.getSumRowCount() == null) {
				column.setSumRowCount(new ISumRowCount() {

					public Number partSumRowCount(TableModel tableModel, int columnIndex) {
						double sum = 0;
						BigDecimal sumB = new BigDecimal(Double.toString(sum));
						for (int i = 0; i < tableModel.getRowCount(); i++) {
							if (tableModel.getValueAt(i, columnIndex) != null) {
		
								Object value = tableModel.getValueAt(i, columnIndex);
								if (value != null&&!value.equals("")) {
									double rv = 0;
									BigDecimal rvB = null;
									try{
										rv = Double.parseDouble(value.toString());
										rvB =new BigDecimal(Double.toString(rv));
									}catch(Exception ex){
										
									}
									sumB=sumB.add(rvB);
									sum += rv;
									sum=sumB.doubleValue();
								}
							}
						}
						return new Double((double) sum);
					}
				});
			}
			
			Double va = (Double)column.getSumRowCount().partSumRowCount(model,columnIndex);
			
			return new SumRowValueTypes(va);
			/*****************************end*******************************/
		}else {
			return new SumRowValueTypes(null);
		}
		
	}
	
	/**
	 * 计算总计行的合计值
	 * @author jerry
	 * @param tableModel 表格模型
	 * @param columnIndex 列索引
	 * @return SumRowValueTypes 合计值的特殊类型 合计值
	 */
	private SumRowValueTypes getSumRowAllCount(TableModel tableModel, int columnIndex, FBaseTableColumn column) {
		
		if (column.isAllTotal()) {
			return new SumRowValueTypes(column.getSumRowData());
		}
		return new SumRowValueTypes(null);	
		
	}
	
	/**
	 * 根据合计行状态值返回合计值
	 * @author jerry
	 * @param result 合计行状态值
	 * @param tableModel 表格模型
	 * @param rowIndex 行索引
	 * @param columnIndex 列索引
	 * @return SumRowValueTypes 合计值的特殊类型 合计值
	 */
	private Object getSumRowCountByStute(int result, TableModel tableModel, int columnIndex, FBaseTableColumn column) {
		
		return result == GetSumRowStute.onlyPartSumRowTop?this.getSumRowPartCount(tableModel, columnIndex, column):this.getSumRowAllCount(tableModel, columnIndex, column);
		
		
	}
	
	/**
	 * 计算由于合计行所产生的行号变化
	 * @author jerry
	 * @param isShowSumRow 是否有小计行
	 * @param isShowSumRowAll 是否有总计行
	 * @param isSumRowAtTop 小计行是否在上部
	 * @param isSumRowAllAtTop 总计行是否在上部
	 * @param isAllTopPart 总计行是否在小计行的上部
	 * @return int 行号变化值
	 */
	public int resultRowChange(boolean isShowSumRow, boolean isShowSumRowAll, boolean isSumRowAtTop, boolean isSumRowAllAtTop, boolean isAllTopPart) {
		
		GetSumRowStute sumRowStute = new GetSumRowStute(isShowSumRow, isShowSumRowAll, isSumRowAtTop, isSumRowAllAtTop, isAllTopPart);
		int result = sumRowStute.getSumRowStute();
		if(result == GetSumRowStute.onlyAllSumRowTop || result == GetSumRowStute.onlyPartSumRowTop || result == GetSumRowStute.sumRowAllPart || result == GetSumRowStute.sumRowPartAll) {
			return 1;
		}
		if(result == GetSumRowStute.sumRowTopAllPart || result == GetSumRowStute.sumRowTopPartAll) {
			return 2;
		}
		return 0;
		
	}
	
	private void setSumRowStute(boolean isShowSumRow, boolean isShowSumRowAll, boolean isSumRowAtTop, boolean isSumRowAllAtTop, boolean isAllTopPart) {
		GetSumRowStute sumRowStute = new GetSumRowStute(isShowSumRow, isShowSumRowAll, isSumRowAtTop, isSumRowAllAtTop, isAllTopPart);
		this.setResult(sumRowStute.getSumRowStute());
	}

	public int getResult() {
		return result;
	}

	protected void setResult(int result) {
		this.result = result;
	}
	
	//Add by bigdog 080408 
	//返回合计的数据
	public Map getAllSumRowData(){
		Map result = null;
		if(this.result != GetSumRowStute.noSumRow){
			result = new HashMap();
			for(int i=0; i<columnModel.getColumnCount(); ++i){
				FBaseTableColumn column = columnModel.getColumn(i);
				String colID = column.getId();
				//column.isAllTotal()
				SumRowValueTypes sr = (SumRowValueTypes)getSumRowPartCount(this.model, i, columnModel.getColumn(i));
				if(i==0)
					result.put(colID, "合计");
				else
					result.put(colID, sr.toString());
			}
		}
		return result;
	}

	//	protected ListSelectionModel getSelectionModel() {
	//		return selectionModel;
	//	}
	//
	//	protected void setSelectionModel(ListSelectionModel selectionModel) {
	//		this.selectionModel = selectionModel;
	//	}

}