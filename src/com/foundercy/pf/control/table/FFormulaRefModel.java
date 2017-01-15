package com.foundercy.pf.control.table;

/**
 * ��ʽ��Ӧ��ģ��
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
	 * ��ʽ���ʽ 
	 */
	private String formula = "#";
	
	//��Ӧ�ı��
	private FBaseTable baseTable = null;
	
	
	private FormulaFactory formulaFactory = null;
	
	private Format format = NumberFormat.getNumberInstance();
	
	
	/**
	 * ȡ�ñ��ʽ
	 * @return
	 */
	public String getFormula() {
		return formula;
	}

	/**
	 * ���ù�ʽ���ʽ
	 * @param formula
	 */
	public void setFormula(String formula) {
		this.formula = formula;
		formulaFactory = new FormulaFactory(formula);
		
	}

	
	/**
	 * ������ʾ��ʽ
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
	 * ȡ�õ�ǰ��Ԫ���Ӧ�Ĺ�ʽ������ֵ
	 * @param value :int[]�������  int[0]:�к�   int[1]:�к�
	 * @return ��ǰ��Ԫ��Ĺ�ʽ������ֵ
	 */

	public String getNameByValue(Object value) {
		return null;
	}

	
	public Object[] getValueByName(String name) {
		return new String[]{"123"};
	}
	
	/**
	 * ���ݹ�ʽȡ�õ�ǰ��Ԫ��ļ���ֵ
	 * @param rowIndex  	��ǰҵ�����ݶ�Ӧ���߼��к�
	 * @param columnIndex  	��ǰҵ�����ݶ�Ӧ������
	 * @return
	 */
	public String getValueByFormula(int rowIndex,String columnName){
		
		if(rowIndex <0) return "";
		
		if(formulaFactory == null){
			formulaFactory = new FormulaFactory(formula);
		}
		
		Vector paraValues = new Vector();			//��ʽ��Ӧ����ֵ����
		
		//���ݲ���ȡ�ö�Ӧ��ֵ
		Vector parameters = formulaFactory.getFormulaParameters();
		
		Iterator paramIt = parameters.iterator();
		while(paramIt.hasNext()){
			Map paramMap = (Map)paramIt.next();
			
			//ȡ������
			String fieldName = (String)paramMap.get(FormulaFactory.FIELD_NAME);
			
			if(FormulaFactory.CURRENT_FIELD_NAME.equals(fieldName)){
				
				fieldName = columnName;
			}
			
			//ȡ���к�
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
		//System.out.println("������ "+rowIndex + " �У�" + columnIndex +"��ֵΪ"+obj);
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
	 * ȡ��ָ���С��ж�Ӧ��ҵ������ֵ
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
		
		//System.out.println("ȡ���У�"+rowIndex +" �У�"+columnName +" ��ֵ "+objValue.toString());
		

		return objValue;
	}
	
	

}
