package com.foundercy.pf.control.table;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * <p>Title:�ϼ�ģ�� </p>
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
	
//	public final static String TOTAL_TITLE = "�ϼƣ�";
	
	private boolean totalExisted = false;
	
	/**
	 * �Ƿ���ʾ������(С��)
	 */
	private boolean showSumRow = false;
	/**
	 * �Ƿ���ʾ�ϼ��У��ܼƣ�
	 * @author jerry
	 */
	private boolean showSumRowAll = false;
	/**
	 * �ϼ����Ƿ������������ݶ����������ture, ���ڶ����������false�����ڵײ���
	 */
	private boolean sumRowAtTop = true;
	/**
	 * �ܼ����Ƿ������������ݶ����������ture, ���ڶ����������false�����ڵײ�
	 * @author jerry
	 */
	private boolean sumRowAllAtTop = true;
	/**
	 * С������ϼ��е�λ�ù�ϵ��trueΪ�ϼ���С��֮�ϣ�falseΪС���ںϼ�֮��
	 * @author jerry
	 */
	private boolean isAllTopPart = true;
	
	/**
	 * �ϼ���״ֵ̬
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
				
				//����ϼ��е���ռ������
				int iTopRowOfSum =0;		//�����ͼ�����
				int iBottomRowOfSum = 0; 	//�ײ��ϼ�����
				
				if(showSumRow && sumRowAllAtTop) iTopRowOfSum++;
				
				if(showSumRow && !sumRowAllAtTop) iBottomRowOfSum++;
				
				if(showSumRowAll && sumRowAllAtTop)iTopRowOfSum++;
				
				if(showSumRowAll && !sumRowAllAtTop)iBottomRowOfSum++;
				
				//ˢ�ºϼ���
				if(iTopRowOfSum>0)fireTableRowsUpdated(0, iTopRowOfSum-1);
				
				if(iBottomRowOfSum>0)fireTableRowsUpdated(getRowCount()-iBottomRowOfSum-iTopRowOfSum-1, getRowCount()-1);
				
				
			}
		});
	}

	public TableModel getModel() {
		return this.model;
	}
	
	/**
	 * �������
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
	 * �������
	 */
	public int getColumnCount() {
		if (model == null)
			return 0;
		return model.getColumnCount();
	}

	/**
	 * ����ϼ��еķ��ص�ֵ�������ı�"�ϼ�"���ͺϼ�����
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
            
		if (model == null)
			return null;
		
		//System.out.println(" �У�"+rowIndex);
		rowIndex = this.convertViewRowToModelRow(rowIndex);
		
		if(rowIndex ==-1){
			//С����
			FBaseTableColumn column = columnModel.getColumn(model.getColumnName(columnIndex));
			return getSumRowPartCount(model, columnIndex, column);
			//return this.getSumRowCountByStute(this.result, model, columnIndex, column);
		}else if(rowIndex ==-2){
			//�ܼ���
			
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
	 * �ϼ�ģ�͵���ͼ��ת��Ϊ����ģ����
	 * @param row   ��������ͼ�к�
	 * @return    -1:С���С���-2���ܼ���  -3 ��Ч�� >=0 �����С���   
	 * @author 	lindx 
	 */
	protected int convertViewRowToModelRow(int row){
		
		//ȡ������ģ�͵ļ�¼����
		int iSize = this.model.getRowCount();
		
		
		if(this.result ==GetSumRowStute.noSumRow){
			//û�кϼ��� 
			
			if(row>=0 && row<iSize){
				return row;
			}
			
			return -3;
		}
		
		//1��ֻ��С���У���С�������ϲ� 0����С����
		if(this.result == GetSumRowStute.onlyPartSumRowTop){
			
			
			if(row==0)return -1;
			
			if(row>=1 && row<=iSize){  //�����з�Χ{1��iSize}
				return row-1;
			}
			
			return -3;
		}
		
		//2��ֻ��С���У���С�����ڵײ�  iSize����С����
		if(this.result == GetSumRowStute.onlyPartSumRowBottom){
			//ֻ��С���У���С�������ϲ�
			
			if(row==iSize)return -1;
			
			if(row>=0 && row<iSize){  //�����з�Χ{0��iSize-1}
				return row;
			}
			
			return -3;
		}
		
		//3��ֻ���ܼ��У����ܼ������ϲ� 0�����ܼ���
		if(this.result == GetSumRowStute.onlyAllSumRowTop){
			
			
			if(row==0)return -2;
			
			if(row>=1 && row<=iSize){  //�����з�Χ{1��iSize}
				return row-1;
			}
			
			return -3;
		}
		
		//4��ֻ���ܼ��У����ܼ������ϲ� iSize�����ܼ���
		if(this.result == GetSumRowStute.onlyAllSumRowBottom){
			
			if(row==iSize)return -2;
			
			if(row>=0 && row<iSize){  //�����з�Χ{0��iSize-1}
				return row;
			}
			
			return -3;
		}
		
		//5�������ܼ�������С���У����ܼ��к�С�������ϲ����ܼ�����С����֮��
		//  0 �����ܼ���  1����С����  2,iSize+1�� ��������
		if(this.result == GetSumRowStute.sumRowTopAllPart){
			
			if(row==0)return -2;
			
			if(row==1)return -1;
			
			if(row>=2 && row<=iSize+1){  //�����з�Χ{2��iSize+1}
				return row-2;
			}
			
			return -3;
		}
		
		//6�������ܼ�������С���У����ܼ��к�С�������ϲ����ܼ�����С����֮��
		//  1 �����ܼ���  0����С����  2,iSize+1�� ��������
		if(this.result == GetSumRowStute.sumRowTopPartAll){
			
			if(row==1)return -2;
			
			if(row==0)return -1;
			
			if(row>=2 && row<=iSize+1){  //�����з�Χ{2��iSize+1}
				return row-2;
			}
			
			return -3;
		}
		
		
		//7�������ܼ�������С���У����ܼ������ϲ���С�����ڵײ�
		//  0 �����ܼ���  iSize+1����С����  1,iSize�� ��������
		if(this.result == GetSumRowStute.sumRowAllPart){
			
			if(row==0)return -2;
			
			if(row==iSize+1)return -1;
			
			if(row>=1 && row<=iSize){  //�����з�Χ{1��iSize}
				return row-1;
			}
			
			return -3;
		}
		
		//8�������ܼ�������С���У����ܼ����ڵײ���С�������ϲ�
		//  0 �����ܼ���  iSize+1����С����  1,iSize�� ��������
		if(this.result == GetSumRowStute.sumRowPartAll){
			
			if(row==0)return -1;
			
			if(row==iSize+1)return -2;
			
			if(row>=1 && row<=iSize){  //�����з�Χ{1��iSize}
				return row-1;
			}
			
			return -3;
		}

		//9�������ܼ�������С���У����ܼ����ڵײ���С�������ϲ�
		//  iSize+1 �����ܼ��� 0 ����С����  1,iSize�� ��������
		if(this.result == GetSumRowStute.sumRowPartAll){
			
			if(row==0)return -1;
			
			if(row==iSize+1)return -2;
			
			if(row>=1 && row<=iSize){  //�����з�Χ{1��iSize}
				return row-1;
			}
			
			return -3;
		}
		
		//10�������ܼ�������С���У����ܼ��к�С�����ڵײ����ܼ�����С����֮��
		//  iSize �����ܼ��� iSize+1 ����С����  0,iSize-1�� ��������
		if(this.result == GetSumRowStute.sumRowBottomAllPart){
			
			if(row==iSize)return -2;
			
			if(row==iSize+1)return -1;
			
			if(row>=0 && row<=iSize-1){  //�����з�Χ{0��iSize-1}
				return row;
			}
			
			return -3;
		}
		
		//11�������ܼ�������С���У����ܼ��к�С�����ڵײ����ܼ�����С����֮��
		//  iSize+1 �����ܼ��� iSize ����С����  0,iSize-1�� ��������
		if(this.result == GetSumRowStute.sumRowBottomPartAll){
			
			if(row==iSize)return -1;
			
			if(row==iSize+1)return -2;
			
			if(row>=0 && row<=iSize-1){  //�����з�Χ{0��iSize-1}
				return row;
			}
			
			return -3;
		}
		
		return row;
	}
	
	
	/**
	 * �ϼ�������ģ���к�ת��Ϊ�����к�
	 * @param modelRow  �ϼ�������ģ���к�
	 * @return 
	 */
	public int convertModelRowToViewRow(int modelRow){
		
		//ȡ������ģ�͵ļ�¼����
		int iSize = this.model.getRowCount();
		
		//����кŲ���[0,iSize-1]��Χ�ڣ�����Ϊ0
		
		if(modelRow >=iSize || modelRow <0)modelRow =iSize-1;
		
		if(this.result ==GetSumRowStute.noSumRow){
			//û�кϼ��� 
			return modelRow;
		}
		
		
		if(this.result ==GetSumRowStute.onlyPartSumRowTop
				|| this.result ==GetSumRowStute.onlyAllSumRowTop
				|| this.result ==GetSumRowStute.sumRowAllPart
				|| this.result ==GetSumRowStute.sumRowPartAll){
			
			//ֻ��С���У������ܼ��У����ϲ�  0���Ǻϼ���
			
			return modelRow+1;
		}
		
		if(this.result ==GetSumRowStute.sumRowTopAllPart
				|| this.result ==GetSumRowStute.sumRowTopPartAll
				){
			//С���к��ܼ������ϲ�  0��1���Ǻϼ���
			
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
	 * ����С���еĺϼ�ֵ
	 * @author jerry
	 * @param tableModel ���ģ��
	 * @param columnIndex ������
	 * @return SumRowValueTypes �ϼ�ֵ���������� �ϼ�ֵ
	 */
	private SumRowValueTypes getSumRowPartCount(TableModel tableModel, int columnIndex, FBaseTableColumn column) {
		
		//add by bigdog 080422 ����϶�������ʱ
		//���ֿհ׵�����
		if (column == null)
			return new SumRowValueTypes(null);
		
		if (column.isTotal()) { 
			/**
			 * ֧�����ƺϼ����㷨���˴��������޸�
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
	 * �����ܼ��еĺϼ�ֵ
	 * @author jerry
	 * @param tableModel ���ģ��
	 * @param columnIndex ������
	 * @return SumRowValueTypes �ϼ�ֵ���������� �ϼ�ֵ
	 */
	private SumRowValueTypes getSumRowAllCount(TableModel tableModel, int columnIndex, FBaseTableColumn column) {
		
		if (column.isAllTotal()) {
			return new SumRowValueTypes(column.getSumRowData());
		}
		return new SumRowValueTypes(null);	
		
	}
	
	/**
	 * ���ݺϼ���״ֵ̬���غϼ�ֵ
	 * @author jerry
	 * @param result �ϼ���״ֵ̬
	 * @param tableModel ���ģ��
	 * @param rowIndex ������
	 * @param columnIndex ������
	 * @return SumRowValueTypes �ϼ�ֵ���������� �ϼ�ֵ
	 */
	private Object getSumRowCountByStute(int result, TableModel tableModel, int columnIndex, FBaseTableColumn column) {
		
		return result == GetSumRowStute.onlyPartSumRowTop?this.getSumRowPartCount(tableModel, columnIndex, column):this.getSumRowAllCount(tableModel, columnIndex, column);
		
		
	}
	
	/**
	 * �������ںϼ������������кű仯
	 * @author jerry
	 * @param isShowSumRow �Ƿ���С����
	 * @param isShowSumRowAll �Ƿ����ܼ���
	 * @param isSumRowAtTop С�����Ƿ����ϲ�
	 * @param isSumRowAllAtTop �ܼ����Ƿ����ϲ�
	 * @param isAllTopPart �ܼ����Ƿ���С���е��ϲ�
	 * @return int �кű仯ֵ
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
	//���غϼƵ�����
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
					result.put(colID, "�ϼ�");
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