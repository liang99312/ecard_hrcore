package com.foundercy.pf.control;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class TreeRefModel implements RefModel
{
	protected List data;  //��������
	protected String pkField;  //ʵ��ֵ�ֶ�
	protected String[] nameField; //��ʾֵ�ֶ����
	protected String currentLevelField; //��ǰ�ڵ㼶���ֶ�
	protected String parentLevelField; //���ڵ㼶���ֶ�
	protected String isLeafField; //�Ƿ�ΪҶ�ڵ�
	
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
	 * �������ַ������õ���Ӧ��ֵ
	 * 
	 * @param name
	 *            �����ַ���
	 * @return �����ַ�������Ӧ��ֵ
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
	 * ��ֵ���õ���Ӧ�������ַ���
	 * 
	 * @param value
	 *            ֵ
	 * @return ֵ����Ӧ�������ַ���
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
	 * ͨ���ֶε�����ֵ�õ���¼Map
	 * @param field �ֶ�������
	 * @param strValue �ֶ�ֵ�ַ�������ʽΪ"�ֶ���1 �ֶ���2 �ֶ���3" 
	 * @return ��¼Map
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
	 * �����ַ������õ��ַ�������
	 * @param names �ַ���
	 * @return ��������ַ�������
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
