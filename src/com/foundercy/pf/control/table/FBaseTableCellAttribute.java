package com.foundercy.pf.control.table;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class FBaseTableCellAttribute{
	
	//单元格字体存储区
	private Hashtable cellFonts;
	
	//单元格前景颜色存储区
	private Hashtable cellForeColors;
	
	//单元格背景颜色存储区
	private Hashtable cellBackColors;
	
	//单元格行提示语句
	private Hashtable cellToolTips;
	
	private static String ROW_KEY = "_ROW";
	
	public FBaseTableCellAttribute(){
		cellFonts = new Hashtable();
		cellForeColors = new Hashtable();
		cellBackColors = new Hashtable();
		cellToolTips = new Hashtable();
	}
	
	protected Font getFont(int row, String column) {
		//System.out.println("get font "+row +":"+column);
		Map fontMap = (Map)cellFonts.get(new Integer(row));
		
		if(fontMap == null){
			return null;
		}
		
		Font font =(Font)fontMap.get(column);
		
		if(font == null){
			font = (Font)fontMap.get(ROW_KEY);
		}
		
		return font;
	}

	protected void setFont(Font font, int row) {
		
		Map fontMap = (Map)cellFonts.get(new Integer(row));
		if(fontMap == null)fontMap = new HashMap();
		if(font == null){
			fontMap.remove(ROW_KEY);
		}else{
			fontMap.put(ROW_KEY, font);
		}
		cellFonts.put(new Integer(row), fontMap);
		
	}

	protected void setFont(Font font, int row, String column) {
		
		
		if(column == null)return;
		
		Map fontMap = (Map)cellFonts.get(new Integer(row));
		if(fontMap == null)fontMap = new HashMap();
		if(font == null){
			fontMap.remove(column);
		}else{
			fontMap.put(column, font);
		}
		cellFonts.put(new Integer(row), fontMap);
		
	}

	protected Color getBackgroup(int row, String column) {
		Map colorMap = (Map)cellBackColors.get(new Integer(row));
		
		if(colorMap == null){
			return null;
		}
		
		Color color =(Color)colorMap.get(column);
		
		if(color == null){
			color = (Color)colorMap.get(ROW_KEY);
		}
		
		return color;
	}

	protected Color getForegroup(int row, String column) {
		Map colorMap = (Map)cellForeColors.get(new Integer(row));
		
		if(colorMap == null){
			return null;
		}
		
		Color color =(Color)colorMap.get(column);
		
		if(color == null){
			color = (Color)colorMap.get(ROW_KEY);
		}
		
		return color;
	}

	protected void setBackgroup(Color color, int row) {
		Map colorMap = (Map)cellBackColors.get(new Integer(row));
		if(colorMap == null)colorMap = new HashMap();
		
		if(color == null){
			colorMap.remove(ROW_KEY);
		}else{
			colorMap.put(ROW_KEY, color);
		}
		cellBackColors.put(new Integer(row), colorMap);
		
	}

	protected void setBackgroup(Color color, int row, String column) {
		Map colorMap = (Map)cellBackColors.get(new Integer(row));
		if(colorMap == null)colorMap = new HashMap();
		if(color == null){
			colorMap.remove(column);
		}else{
			colorMap.put(column, color);
		}
		cellBackColors.put(new Integer(row), colorMap);
		
	}

	protected void setForegroup(Color color, int row) {
		Map colorMap = (Map)cellForeColors.get(new Integer(row));
		if(colorMap == null)colorMap = new HashMap();
		if(color == null){
			colorMap.remove(ROW_KEY);
		}else{
			colorMap.put(ROW_KEY, color);
		}
		cellForeColors.put(new Integer(row), colorMap);
		
	}

	protected void setForegroup(Color color, int row, String column) {
		Map colorMap = (Map)cellForeColors.get(new Integer(row));
		if(colorMap == null)colorMap = new HashMap();
		if(color == null){
			colorMap.remove(column);
		}else{
			colorMap.put(column, color);
		}
		cellForeColors.put(new Integer(row), colorMap);
		
	}
	
	
	protected void setToolTipText(String tips,int row){
		this.cellToolTips.put(new Integer(row), tips);
	}
	
	/**
	 * 取得表格行的提示语句
	 * @param row	数据模型的行号
	 * @return
	 */
	protected String getTookTipText(int row){
		String tips = (String)cellToolTips.get(new Integer(row));
		
		return tips;
	}
	
	protected void clearAllAttribute(){
		cellForeColors.clear();
		cellForeColors.clear();
		cellBackColors.clear();
	}
	
}
