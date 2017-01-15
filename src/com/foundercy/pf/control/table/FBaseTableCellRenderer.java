package com.foundercy.pf.control.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.Format;
import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.foundercy.pf.control.AbstractDataField;
import com.foundercy.pf.control.FCheckBox;
import com.foundercy.pf.control.FRadioGroup;
import com.foundercy.pf.control.NumberRefModel;
import com.foundercy.pf.control.RadioModel;
import com.foundercy.pf.control.RefModel;

/**
 * <p>Title:表格中的单元格表达类 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author fangyi
 * @version 1.0
 */
public class FBaseTableCellRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2903080358712577784L;

	public static Color evenColor = Color.white;

	public static Color oddColor = new Color(250,250,250);
	
	//public static Color oddColor = Color.gray.brighter().brighter();

	/**
	 * 
	 * @uml.property name="isColorDivided" multiplicity="(0 1)"
	 */
	private boolean isColorDivided = false;

	/**
	 * 
	 * @uml.property name="refModel"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private RefModel refModel = null;

	/**
	 * 
	 * @uml.property name="dataField"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private AbstractDataField dataField = null;

	/**
	 * 
	 * @uml.property name="format" multiplicity="(0 1)"
	 */
	private Format format = null;

	/**
	 * 
	 * @uml.property name="dataFields"
	 * @uml.associationEnd qualifier="new:java.lang.Integer xingx.smartbiz.control.pub.AbstractDataField"
	 * multiplicity="(0 1)"
	 */
	private HashMap dataFields = new HashMap();

	/**
	 * 是否存在合计行
	 */
	private boolean showSumRow = false;
	
	private boolean sumRowAtTop = true;

	private int align = -1;

	private Color back = null;

	private Color fore = null;

	/**
	 * 
	 * @uml.property name="bold" multiplicity="(0 1)"
	 */
	private boolean bold = false;

	public FBaseTableCellRenderer() {
		//this.set
	}

	public FBaseTableCellRenderer(RefModel refModel) {
		this.refModel = refModel;
	}

	public FBaseTableCellRenderer(Format format) {
		this.format = format;
	}

	
	/**
	 * 
	 * @param table 	 当前操作的表格JTable对象
	 * @param value      单元格对应的数据模型的值
	 * @param isSelected 是否是选中的行
	 * @param hasFocus  当前焦点是否在当前单元格上
	 * @param row 		数据模型的行号（在FBaseTableRowCellRenderer中作了转换处理）
	 * @param column 	数据模型的列号（在FBaseTableRowCellRenderer中作了转换处理）
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		
		Color bgColor, fgColor;
		
		bgColor = null;
		fgColor = null;
		
		Font cellFont = null;
		
		String tipsText = null;
		
		//业务数据逻辑行号
		int modelRow = row;
		
		//业务数据列名称
		String columnName = "";
		
		//取得用户自定义单元格属性
		
		FBaseTable baseTable = null;
		if(table instanceof FExpandTable){
			
			
			FExpandTable expandTable =(FExpandTable)table;
			baseTable = expandTable.getBaseTable();
			
			//视图行转换数据模型行号
			modelRow = baseTable.convertTableRowToModelRow(row);
			FBaseTableColumn tableColumn = (FBaseTableColumn)expandTable.getColumnModel().getColumn(column);
			
			//取得当前的列名称
			columnName =tableColumn.getId();
			
			//取得背景颜色
			bgColor = baseTable.getCellAttribute().getBackgroup(modelRow, columnName);
			//取得前景颜色
			fgColor =baseTable.getCellAttribute().getForegroup(modelRow, columnName);
			
			//取得字体
			cellFont =baseTable.getCellAttribute().getFont(modelRow, columnName);
			
			//取得提示语言
			tipsText = baseTable.getCellAttribute().getTookTipText(modelRow);
			
		}
		
		//取得视图的颜色配置
		if(bgColor == null){
			bgColor = this.getBackColor();
		}
		
		if(fgColor == null){
			fgColor = this.getForeColor();
		}
		
		//如果未设置，采用默认表格的颜色配置
		if (bgColor == null) {
			if (row % 2 == 0) {
				bgColor = evenColor;
			} else {
				bgColor = oddColor;
			}

		} 
		
		if (fgColor == null) {
			fgColor = table.getForeground();
		} 
		
		
		
		/**************************************控件显示***********************************/
		if (this.dataField != null
			/**
			 * 合计旁边不显示复选框，实现此需求，改动此处
			 * @author jerry
			 */
			/*********************begin**********************/
				&& !(value instanceof SumRowValueTypes)) {
			/*********************end************************/
			//如果Checkbox直接显示控件。
			
			AbstractDataField field = this.dataField;
			if (field != null) {
				if (this.dataField instanceof FCheckBox) {
					
					field = new FCheckBox();


				} else if (this.dataField instanceof FRadioGroup) {
					//如果是Radio直接显示该控件
					field = new FRadioGroup();
					((FRadioGroup) field)
							.setRefModel((RadioModel) ((FRadioGroup) this.dataField)
									.getRefModel());
				}
				
			}
			if (field != null) {
				if (isSelected) {
					field.setForeground(table.getSelectionForeground());
					field.setBackground(table.getSelectionBackground());
				} else {
					field.setForeground(fgColor);
					field.setBackground(bgColor);
				}
				
				if (hasFocus) {
					field.setForeground(table.getForeground());
					field.setBackground(table.getBackground());		
				} 
				if(cellFont != null){
					field.setFont(cellFont);
				}
				field.setValue(value);
				
				if(tipsText != null){
					field.setToolTipText(tipsText);
				}
				
				//field.setToolTipText(text)
				
				field.setTitleVisible(false);
				return field;
			}
		}

		/**************************************颜色显示***********************************/
		if (value != null && value instanceof java.awt.Color) {

		}

		/**************************************图片显示***********************************/
		//待增加

		/**************************************文本显示***********************************/
		int alg = this.getTextAlignment();
		if (alg != SwingConstants.CENTER && alg != SwingConstants.LEFT
				&& alg != SwingConstants.RIGHT) {
			alg = this.getDefaultAlignment(value);
		}

		//获得显示的值,根据模型获得显示的值。
		Object showObject = value;
		/**
		 * 合计旁边不显示复选框，实现此需求，改动此处
		 * @author jerry
		 */
		/***********************begin*************************/
		if(showObject instanceof SumRowValueTypes && ((SumRowValueTypes)showObject).getObject() ==null ){
			showObject = null;
		}
		/***********************end***************************/
		if (refModel != null ) {
			String showName = null;
			
			
			if(refModel instanceof FFormulaRefModel){
				//公式列模型处理
				
				FFormulaRefModel formulaRefModel = (FFormulaRefModel)refModel;
				
				if(formulaRefModel.getBaseTable() == null){
					formulaRefModel.setBaseTable(baseTable);
				}
				
				showName = (String) ((FFormulaRefModel)refModel).getValueByFormula(modelRow, columnName);
			}else{
				if(showObject != null ){
					showName = refModel.getNameByValue(showObject);
				}
			}
			if (showName != null) {
				showObject = showName;
			} else {
				showObject = null;
			}
		}
		try {
			if (format != null && showObject != null) {
				if (showObject instanceof Number) {
					showObject = format.format(new Double(((Number) showObject)
							.doubleValue()));
				} else {
					showObject = format.format(showObject);
				}
			}
		} catch (Exception ex) {
		}

		//设置对齐方式
		this.setHorizontalAlignment(alg);

		//设置选择后的颜色
		if (isSelected) {
		      this.setBackground(table.getSelectionBackground());
		      this.setForeground(table.getSelectionForeground());
		} else {
			this.setForeground(fgColor);
			this.setBackground(bgColor);
		}
		if (hasFocus) {
			this.setForeground(table.getForeground());
			this.setBackground(table.getBackground());		
		} 
		if(cellFont != null){
			this.setFont(cellFont);
		}else{
			//设置字体的Bold属性
			java.util.Map fontMap = table.getFont().getAttributes();
			if (bold || (this.showSumRow && row == table.getRowCount() - 1)) {
				fontMap.put(java.awt.font.TextAttribute.WEIGHT, new Float(2.0));
			}
			this.setFont(Font.getFont(fontMap));
		}
		//设置文本。
		if (showObject != null)
			this.setText(showObject.toString());
		else
			this.setText("");

		if(tipsText == null){
			//设置鼠标提示
			if (showObject != null) {
				this.setToolTipText(showObject.toString());
			}
		}else{
			this.setToolTipText(tipsText);
		}
		
		return this;
	}

	/**
	 * 
	 * @uml.property name="format"
	 */
	public void setFormat(Format format) {
		this.format = format;
	}

	/**
	 * 
	 * @uml.property name="format"
	 */
	public Format getFormat() {
		return this.format;
	}

	public int getDefaultAlignment(Object value) {
		if (value == null) {
			return SwingConstants.LEFT;
		}
		if (value instanceof Number) {
			return SwingConstants.RIGHT;
		}
		if (value instanceof Boolean) {
			return SwingConstants.CENTER;
		}
		if(this.refModel!=null && this.refModel instanceof NumberRefModel){
			return SwingConstants.RIGHT;
		}
		return SwingConstants.LEFT;
	}

	public void setTextAlignment(int align) {
		this.align = align;
	}

	public int getTextAlignment() {
		return this.align;
	}

	public Color getBackColor() {
		return this.back;
	}

	public void setBackColor(Color color) {
		this.back = color;
	}

	public Color getForeColor() {
		return this.fore;
	}

	public void setForeColor(Color color) {
		this.fore = color;
	}

	/**
	 * 
	 * @uml.property name="bold"
	 */
	public void setBold(boolean bold) {
		this.bold = bold;
	}

	public boolean isBold() {
		return this.bold;
	}

	/**
	 * 
	 * @uml.property name="refModel"
	 */
	public void setRefModel(RefModel model) {
		this.refModel = model;
	}

	/**
	 * 
	 * @uml.property name="refModel"
	 */
	public RefModel getRefModel() {
		return this.refModel;
	}

	/**
	 * 
	 * @uml.property name="dataField"
	 */
	public void setDataField(AbstractDataField field) {
		this.dataField = field;
	}

	/**
	 * 
	 * @uml.property name="dataField"
	 */
	public AbstractDataField getDataField() {
		return this.dataField;
	}

	public boolean isIsColorDivided() {
		return isColorDivided;
	}

	/**
	 * 
	 * @uml.property name="isColorDivided"
	 */
	public void setIsColorDivided(boolean isColorDivided) {
		this.isColorDivided = isColorDivided;
	}

	/**
	 * 设置是否存在合计行
	 * @param showSumRow
	 */
	public void setShowSumRow(boolean showSumRow) {
		this.showSumRow = showSumRow;
	}

	/**
	 * 判断是否存在合计行
	 * @return
	 */
	public boolean isShowSumRow() {
		return this.showSumRow;
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
	
}
