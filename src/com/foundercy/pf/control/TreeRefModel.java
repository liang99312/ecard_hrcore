package com.foundercy.pf.control;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class TreeRefModel implements RefModel
{
	protected List data;  //所有数据
	protected String pkField;  //实际值字段
	protected String[] nameField; //显示值字段组合
	protected String currentLevelField; //当前节点级次字段
	protected String parentLevelField; //父节点级次字段
	protected String isLeafField; //是否为叶节点
	
	public TreeRefModel()
	{
		
	}
	
	public void setNameField(String[] nameField) {
		this.nameField = nameField;
	}

	public void setParentLevelField(String parentLevelField) {
		this.parentLevelField = parentLevelField;
	}

	public void setPkField(String pkField) {
		this.pkField = pkField;
	}

	public String getPkField()
	{
		return pkField;
	}
	
	public String[] getNameField()
	{
		return nameField;
	}
	
	public String getCurrentLevelField()
	{
		return currentLevelField;
	}
	
	public String getParentLevelField()
	{
		return parentLevelField;
	}
	
	public void setData(List data)
	{
		this.data = data;
	}
	
	public List getData()
	{
		return data;
	}
	
	public String getIsLeafField()
	{
		return isLeafField;
	}
	
	
	/**
	 * 由名字字符串，得到对应的值
	 * 
	 * @param name
	 *            名字字符串
	 * @return 名字字符串，对应的值
	 */
	public Object[] getValueByName(String name)
	{
		String[] nameArray = parseString(name);
		Object[] ret = new Object[nameArray.length];
		for(int i = 0; i < nameArray.length; i++)
		{
			Map map = getRecordMap(nameField, nameArray[i]);
			ret[i] = map.get(pkField);
		}
		return ret;
	}

	/**
	 * 由值，得到对应的名字字符串
	 * 
	 * @param value
	 *            值
	 * @return 值，对应的名字字符串
	 */
	public String getNameByValue(Object value)
	{
		String ret = "";
		String sValue = (String)value;
		String[] fieldNames = new String[1];
		fieldNames[0] = pkField;
		Map map = getRecordMap(fieldNames, sValue);
		for(int i = 0; i < nameField.length; i++)
		{
			ret = (String)map.get(nameField[i]);
			ret = ret + " ";
		}
		return ret.substring(0, ret.length() - 1);
	}
	
	/**
	 * 通过字段的名和值得到记录Map
	 * @param field 字段名数组
	 * @param strValue 字段值字符串，格式为"字段名1 字段名2 字段名3" 
	 * @return 记录Map
	 */
	private Map getRecordMap(String[] field, String strValue)
	{
		StringBuffer strCurrent = new StringBuffer();
		Map mapCurrent = null;
		if(field.length < 1)
		{
			return null;
		}
		else
		{
			for(int i = 0; i < data.size(); i++)
			{
				mapCurrent = (Map)data.get(i);
				strCurrent = new StringBuffer();
				for(int j = 0; j < field.length; j++)
				{
					strCurrent.append(mapCurrent.get(field[j]).toString());
					strCurrent.append(" ");
				}
				String str = strCurrent.substring(0, strCurrent.length() - 1);
				if (str.equals(strValue))
				{
					break;
				}
								
			}
		}
		return mapCurrent;
		
	}
	
	/**
	 * 解析字符串，得到字符串数组
	 * @param names 字符串
	 * @return 解析后的字符串数组
	 */
	private String[] parseString(String names)
	{
	    if (names == null)
		      return null;
		    StringTokenizer st = new StringTokenizer(names, ";");
		    String[] ret = new String[st.countTokens()];
		    int i = 0;
		    while (st.hasMoreTokens()) {
		    	ret[i] = st.nextToken();
		        i++;
		    }
		    return ret;
	}
	
}
