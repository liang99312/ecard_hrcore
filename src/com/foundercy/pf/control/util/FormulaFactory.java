package com.foundercy.pf.control.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;


public class FormulaFactory {

	public static String FIELD_NAME = "fieldName";
	public static String FIELD_TYPE ="fieldType";
	public static String FIELD_ROWINDEX = "rowIndex";
	public static String FIELD_COLUMNINDEX = "columnIndex";
	
	public static String CURRENT_FIELD_NAME = "#FIELD#";
	
	public static String CURRENT_FIELD_ROWINDEX = "#ROW#";
	//公式正式脚本
	private String formulaScript;
	
	//公式对应的参数
	private Vector formulaParameters;
	
	public FormulaFactory(String formulaScript){
		formulaParameters = new Vector();
		//公式表达式进行解析
		this.parserFormula(formulaScript);
	}
	
	
	public Vector getFormulaParameters() {
		return formulaParameters;
	}


	/**
	 * 公式解析
	 * @param sormulaScript
	 */
	private void parserFormula(String formulaScript){
		formulaParameters = new Vector();
		StringBuffer sb = new StringBuffer();
		
		int iPos = 0;
		
		int iLeft = formulaScript.indexOf("{", iPos);
		
		while(iLeft >-1){
			
			sb.append(formulaScript.substring(iPos, iLeft));
			
			int iRight = formulaScript.indexOf("}", iPos);
			
			if(iRight >-1){
				String fieldScript = formulaScript.substring(iLeft+1, iRight);
				
				//解析单元格定义脚本  {fieldName:rowIndex}
				
				int i = fieldScript.indexOf(":");
				
				String fieldName = "";
				String rowIndex = "";
				
				if(i>-1){
					fieldName = fieldScript.substring(0,i);
					
					rowIndex = fieldScript.substring(i+1,fieldScript.length());
					
					
				}else{
					fieldName = fieldScript;
				}
				
				Map fieldPara = new HashMap();
				if("".equals(fieldName)){
					fieldName = "#FIELD#";
				}
				
				if("".equals(rowIndex)){
					
					rowIndex = "#ROW#";
				}
				
				fieldPara.put(FIELD_NAME, fieldName);
				fieldPara.put(FIELD_ROWINDEX, rowIndex);
				
				formulaParameters.add(fieldPara);
				
				sb.append(fieldName+"_"+rowIndex);
				
				iPos = iRight+1;
			
				iLeft = formulaScript.indexOf("{", iPos);
			}else{
				
				iPos = formulaScript.length();
			}
		}
		
		sb.append(formulaScript.substring(iPos, formulaScript.length()));
		
		
		this.formulaScript = "Object resultVal = "+sb.toString();
		
		
	}
	
	
	/**
	 * 根据参数返回计算结果 
	 * @param parameters
	 * @return
	 */
/*
	public Object calculate(Vector paraValues,String currentField,String currentRow){
		Object resultObj = null;
		Interpreter i = new Interpreter(); 
		String currentFormula = this.formulaScript;
		currentFormula=currentFormula.replaceAll(CURRENT_FIELD_NAME, currentField);
		currentFormula=currentFormula.replaceAll(CURRENT_FIELD_ROWINDEX, currentRow);
		
		// 实例化一个BeanShell解释器
		try {
			i.eval("import parse.*;");
			Iterator paraIt= paraValues.iterator();
			while(paraIt.hasNext()){
				Map paramMap = (HashMap)paraIt.next();
				
				String paraName = (String)paramMap.get("PARA_NAME");
				
				String paraType = (String)paramMap.get("PARA_TYPE");
				String declare = paraType +" "+paraName;
				
				i.eval(declare);
				
				
				Object paraValue = paramMap.get("PARA_VALUE");
				
				if(paraValue != null){
					
					String assign_value= paraName+ "="+ paraValue; 
					i.eval(assign_value);          
				}else{
					System.out.println("caculateByFormula():"+ paraName+ "参数名不符或改参数不存在"); 
				}
				
			}
			i.eval(currentFormula);
			
			resultObj = i.get("resultVal");
			
			
		} catch (EvalError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return resultObj;
		
		
	}
*/
	public static void main(String[] args){
		FormulaFactory formula = new FormulaFactory("({xh:122}*{jine:100})-{salray}+{:100}+{}-{:}");
		System.out.println(formula.formulaScript);
	}
}
