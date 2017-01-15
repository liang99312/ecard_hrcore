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
 * <p>Title:����еĵ�Ԫ������ </p>
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
	 * �Ƿ���ںϼ���
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
	 * @param table 	 ��ǰ�����ı��JTable����
	 * @param value      ��Ԫ���Ӧ������ģ�͵�ֵ
	 * @param isSelected �Ƿ���ѡ�е���
	 * @param hasFocus  ��ǰ�����Ƿ��ڵ�ǰ��Ԫ����
	 * @param row 		����ģ�͵��кţ���FBaseTableRowCellRenderer������ת������
	 * @param column 	����ģ�͵��кţ���FBaseTableRowCellRenderer������ת������
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		
		Color bgColor, fgColor;
		
		bgColor = null;
		fgColor = null;
		
		Font cellFont = null;
		
		String tipsText = null;
		
		//ҵ�������߼��к�
		int modelRow = row;
		
		//ҵ������������
		String columnName = "";
		
		//ȡ���û��Զ��嵥Ԫ������
		
		FBaseTable baseTable = null;
		if(table instanceof FExpandTable){
			
			
			FExpandTable expandTable =(FExpandTable)table;
			baseTable = expandTable.getBaseTable();
			
			//��ͼ��ת������ģ���к�
			modelRow = baseTable.convertTableRowToModelRow(row);
			FBaseTableColumn tableColumn = (FBaseTableColumn)expandTable.getColumnModel().getColumn(column);
			
			//ȡ�õ�ǰ��������
			columnName =tableColumn.getId();
			
			//ȡ�ñ�����ɫ
			bgColor = baseTable.getCellAttribute().getBackgroup(modelRow, columnName);
			//ȡ��ǰ����ɫ
			fgColor =baseTable.getCellAttribute().getForegroup(modelRow, columnName);
			
			//ȡ������
			cellFont =baseTable.getCellAttribute().getFont(modelRow, columnName);
			
			//ȡ����ʾ����
			tipsText = baseTable.getCellAttribute().getTookTipText(modelRow);
			
		}
		
		//ȡ����ͼ����ɫ����
		if(bgColor == null){
			bgColor = this.getBackColor();
		}
		
		if(fgColor == null){
			fgColor = this.getForeColor();
		}
		
		//���δ���ã�����Ĭ�ϱ�����ɫ����
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
		
		
		
		/**************************************�ؼ���ʾ***********************************/
		if (this.dataField != null
			/**
			 * �ϼ��Ա߲���ʾ��ѡ��ʵ�ִ����󣬸Ķ��˴�
			 * @author jerry
			 */
			/*********************begin**********************/
				&& !(value instanceof SumRowValueTypes)) {
			/*********************end************************/
			//���Checkboxֱ����ʾ�ؼ���
			
			AbstractDataField field = this.dataField;
			if (field != null) {
				if (this.dataField instanceof FCheckBox) {
					
					field = new FCheckBox();


				} else if (this.dataField instanceof FRadioGroup) {
					//�����Radioֱ����ʾ�ÿؼ�
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

		/**************************************��ɫ��ʾ***********************************/
		if (value != null && value instanceof java.awt.Color) {

		}

		/**************************************ͼƬ��ʾ***********************************/
		//������

		/**************************************�ı���ʾ***********************************/
		int alg = this.getTextAlignment();
		if (alg != SwingConstants.CENTER && alg != SwingConstants.LEFT
				&& alg != SwingConstants.RIGHT) {
			alg = this.getDefaultAlignment(value);
		}

		//�����ʾ��ֵ,����ģ�ͻ����ʾ��ֵ��
		Object showObject = value;
		/**
		 * �ϼ��Ա߲���ʾ��ѡ��ʵ�ִ����󣬸Ķ��˴�
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
				//��ʽ��ģ�ʹ���
				
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

		//���ö��뷽ʽ
		this.setHorizontalAlignment(alg);

		//����ѡ������ɫ
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
			//���������Bold����
			java.util.Map fontMap = table.getFont().getAttributes();
			if (bold || (this.showSumRow && row == table.getRowCount() - 1)) {
				fontMap.put(java.awt.font.TextAttribute.WEIGHT, new Float(2.0));
			}
			this.setFont(Font.getFont(fontMap));
		}
		//�����ı���
		if (showObject != null)
			this.setText(showObject.toString());
		else
			this.setText("");

		if(tipsText == null){
			//���������ʾ
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
	 * �����Ƿ���ںϼ���
	 * @param showSumRow
	 */
	public void setShowSumRow(boolean showSumRow) {
		this.showSumRow = showSumRow;
	}

	/**
	 * �ж��Ƿ���ںϼ���
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
