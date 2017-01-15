package com.foundercy.pf.control.table;

/**
 * 公式对应的模型
 */
import java.text.Format;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.TableModel;

import com.foundercy.pf.control.FormatManager;
import com.foundercy.pf.control.RefModel;
import com.foundercy.pf.control.util.FormulaFactory;


public class FFormulaRefModel implements RefModel {
	
	/**
	 * 公式表达式 
	 */
	private String formula = "#";
	
	//对应的表格
	private FBaseTable baseTable = null;
	
	
	private FormulaFactory formulaFactory = null;
	
	private Format format = NumberFormat.getNumberInstance();
	
	
	/**
	 * 取得表达式
	 * @return
	 */
	public String getFormula() {
		return formula;
	}

	/**
	 * 设置公式表达式
	 * @param formula
	 */
	public void setFormula(String formula) {
		this.formula = formula;
		formulaFactory = new FormulaFactory(formula);
		
	}

	
	/**
	 * 设置显示格式
	 * @param format
	 */
	public void setFormat(Format format) {
		this.format = format;
	}

	

	
	public FBaseTable getBaseTable() {
		return baseTable;
	}

	public void setBaseTable(FBaseTable baseTable) {
		this.baseTable = baseTable;
	}

	/**
	 * 取得当前单元格对应的公式运算结果值
	 * @param value :int[]数组对象  int[0]:行号   int[1]:列号
	 * @return 当前单元格的公式运算结果值
	 */

	public String getNameByValue(Object value) {
		return null;
	}

	
	public Object[] getValueByName(String name) {
		return new String[]{"123"};
	}
	
	/**
	 * 根据公式取得当前单元格的计算值
	 * @param rowIndex  	当前业务数据对应的逻辑行号
	 * @param columnIndex  	当前业务数据对应的列名
	 * @return
	 */
	public String getValueByFormula(int rowIndex,String columnName){
		
		if(rowIndex <0) return "";
		
		if(formulaFactory == null){
			formulaFactory = new FormulaFactory(formula);
		}
		
		Vector paraValues = new Vector();			//公式对应参数值集合
		
		//根据参数取得对应的值
		Vector parameters = formulaFactory.getFormulaParameters();
		
		Iterator paramIt = parameters.iterator();
		while(paramIt.hasNext()){
			Map paramMap = (Map)paramIt.next();
			
			//取得列名
			String fieldName = (String)paramMap.get(FormulaFactory.FIELD_NAME);
			
			if(FormulaFactory.CURRENT_FIELD_NAME.equals(fieldName)){
				
				fieldName = columnName;
			}
			
			//取得行号
			String fieldRow = (String)paramMap.get(FormulaFactory.FIELD_ROWINDEX);
			
			if(FormulaFactory.CURRENT_FIELD_ROWINDEX.equals(fieldRow)){
				
				fieldRow = String.valueOf(rowIndex);
			}
			
			Object objValue = getValueAt(Integer.parseInt(fieldRow),fieldName);
			
			Map paraValue = new HashMap();
				
			paraValue.put("PARA_NAME", fieldName+"_"+fieldRow);
			paraValue.put("PARA_VALUE", objValue);
			
			try{
				
				Double.parseDouble(objValue.toString());
				paraValue.put("PARA_TYPE", "double");
			}catch(Exception e){
				paraValue.put("PARA_TYPE", "String");
				paraValue.put("PARA_VALUE", "\""+objValue + "\"");
				
			}
			paraValues.add(paraValue);
			
		}
		
		Object obj =null;//formulaFactory.calculate(paraValues,columnName,String.valueOf(rowIndex));
		//System.out.println("计算行 "+rowIndex + " 列：" + columnIndex +"的值为"+obj);
		if(obj instanceof Number){
			//
			Format format =FormatManager.getDecimalNumberFormat();
			obj = format.format(obj);
		}
		
		if(obj == null){
			return null;
		}else{
			return obj.toString();
		}
	}
	
	/**
	 * 取得指定列、行对应的业务数据值
	 */
	private Object getValueAt(int rowIndex, String columnName)
	{	
		TableModel tableModel = baseTable.getModel();
		
		int columnIndex = baseTable.getColumnModelIndex(columnName);
		
		if(rowIndex >=tableModel.getRowCount() || rowIndex <0){
			return "0";
		}
		if(columnIndex >=tableModel.getColumnCount() || columnIndex <0){
			return "0";
		}
		
		Object objValue =tableModel.getValueAt(rowIndex, columnIndex);
		
		//System.out.println("取得行："+rowIndex +" 列："+columnName +" 的值 "+objValue.toString());
		

		return objValue;
	}
	
	

}
