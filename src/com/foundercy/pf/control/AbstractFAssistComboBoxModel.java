package com.foundercy.pf.control;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListDataListener;

import com.foundercy.pf.util.DealString;

public class AbstractFAssistComboBoxModel implements FAssistComboBoxModel
{
	protected List data;  //所有数据
	protected String pkField;  //实际值字段
	protected String[] nameField; //显示值字段组合
	protected String level;  // 级次字段
	private String searchCondition = "";//查询条件
	private String coaId;// COA_ID
	private Map ctrlElementValues = null;//关联关系
	
	private DefaultComboBoxModel dcbm;

	private Vector fComboBoxItems;
	
	protected String dataSource = "";

	/**
	 * 构造函数
	 *
	 */
	public AbstractFAssistComboBoxModel() {
		this((Vector)null);
	}

	/**
	 * 构造函数
	 *
	 * @param values 列表项的值列表
	 */
	public AbstractFAssistComboBoxModel(Vector values) {
		this(values,(String[])null);
	}

	/**
	 * 构造函数
	 * 列表项的值列表，与列表项的显示字符串列表，的顺序应该是一一对应的
	 *
	 * @param values 列表项的值列表
	 * @param displayValues 列表项的显示字符串列表
	 */
	public AbstractFAssistComboBoxModel(Vector values,String[] displayValues) {
		setRefModel(values,displayValues);
	}

	/**
	 * 构造函数
	 * @param model 字符串参照模型
	 * @param valueType 字符串参照模型中，值的类型
	 */
	public AbstractFAssistComboBoxModel(String model) {
		setRefModel(model);
	}

	/**
	 * 设置，字符串参照模型
	 * @param model 字符串参照模型
	 * @param valueType 字符串参照模型中，值的类型
	 */
	public void setRefModel(String model) {

		if (null != model) {
			String[][] t = DealString.buildString(model);

			//构造，列表项的值列表
			if (null != t) {
				int len = t.length;

				Vector values = new Vector(len);
				for (int i = 0; i < len; i++) {
					values.addElement(t[i][0]);
				}
				//构造，列表项的显示字符串列表
				String[] displayValues = new String[len];

				for (int i = 0; i < len; i++) {
					displayValues[i] = t[i][1];
				}

				setRefModel(values, displayValues);
			}
		}
	}

	/**
	 * 设置参照模型
	 *
	 * @param values 列表项的值列表
	 */
	public void setRefModel(Vector values) {
		setRefModel(values,(String[])null);
	}

	/**
	 * 设置参照模型
	 * 
	 * @param values 列表项的值列表
	 * @param displayValues 列表项的显示字符串列表
	 */
	public void setRefModel(Vector values,String[] displayValues) {

		values = noneNullVector(values);

		if(null == displayValues) {
			displayValues = toDisplayValues(values);
		}else if(values.size() > displayValues.length) {
			throw new IllegalArgumentException("the number of values is larger than the number of displayValues!");
		}

		if(null == this.dcbm) {
			this.dcbm = new DefaultComboBoxModel();
		} else {
			this.dcbm.removeAllElements();
		}

		int num = values.size();
		if(null == this.fComboBoxItems) {
			this.fComboBoxItems = new Vector(num);
		} else {
			this.fComboBoxItems.removeAllElements();
		}

		FComboBoxItem ncbi = null;
		for(int i = 0;i < num;i++) {
			ncbi = new FComboBoxItem(displayValues[i],values.elementAt(i));
			this.fComboBoxItems.addElement(ncbi);
			this.dcbm.addElement(ncbi);
		}
		
	}

	/**
	 * 由名字字符串，得到对应的值
	 *
	 * @param name 名字字符串
	 * @return 名字字符串，对应的值
	 */
	public Object[] getValueByName(String name) {
		Vector v = new Vector();
		if(null != name && null != this.fComboBoxItems) {
			int num = this.fComboBoxItems.size();
			Object item = null;
			for(int i = 0;i < num;i++) {
				item = this.fComboBoxItems.elementAt(i);
				name = name.trim();
				if(name.equals(toDisplayValue(item))) {
					v.addElement(toRealValue(item));
				}
			}
		}
		return 0 != v.size()?v.toArray():null;
	}

	/**
	 * 由值，得到对应的名字字符串
	 *
	 * @param value 值
	 * @return 值，对应的名字字符串
	 */
	public String getNameByValue(Object value) {
		String rtn = null;
		if(value instanceof FComboBoxItem) {
			rtn = toDisplayValue(value);
		} else {
			if(null != this.fComboBoxItems) {
				int num = this.fComboBoxItems.size();
				Object item = null;
				for(int i = 0;i < num;i++) {
					item = this.fComboBoxItems.elementAt(i);
					if(isEqual(value,toRealValue(item))) {
						rtn = toDisplayValue(item);
						break;
					}
				}
			}
		}
		return rtn;
	}
	/**
	 * 得到被选中列表项的值
	 * 
	 * @return 列表项的值
	 */
	public Object getSelectedRealValue() {
		return toRealValue(this.dcbm.getSelectedItem());
		
	}
	/**
	 * 根据列表项的索引，得到列表项的值
	 * @param index 列表项的索引
	 * @return 列表项的值
	 */
	public Object getRealValueAt(int index) {
		return toRealValue(this.dcbm.getElementAt(index));
	}

	// implements javax.swing.ComboBoxModel
	/**
	 * Set the value of the selected item. The selected item may be null.
	 * <p>
	 * @param anObject The combo box value or null for no selection.
	 */
	public void setSelectedItem(Object anItem) {
		FComboBoxItem ncbi = null;
		if(anItem instanceof FComboBoxItem) {
			ncbi = (FComboBoxItem)anItem;
		} else {
			ncbi = toFComboBoxItem(anItem);
		}
		this.dcbm.setSelectedItem(ncbi);
	}

	// implements javax.swing.ComboBoxModel
	public Object getSelectedItem() {
//		return toRealValue(this.dcbm.getSelectedItem());
		return this.dcbm.getSelectedItem();
	}

	// implements javax.swing.ListModel
	public Object getElementAt(int index) {
//		return toRealValue(this.dcbm.getElementAt(index));
		return this.dcbm.getElementAt(index);
	}

	// implements javax.swing.ListModel
	public int getSize() {
		return this.dcbm.getSize();
	}

	/**
	 * Adds a listener to the list that's notified each time a change
	 * to the data model occurs.
	 *
	 * @param l the <code>ListDataListener</code> to be added
	 */
	public void addListDataListener(ListDataListener l) {
		this.dcbm.addListDataListener(l);
	}

	/**
	 * Removes a listener from the list that's notified each time a
	 * change to the data model occurs.
	 *
	 * @param l the <code>ListDataListener</code> to be removed
	 */
	public void removeListDataListener(ListDataListener l) {
		this.dcbm.removeListDataListener(l);
	}

	/** 由值，从保存列表项的Vector中，取得FComboBoxItem对象 */
	private FComboBoxItem toFComboBoxItem(Object value) {
		FComboBoxItem rtn = null;
		if(null != this.fComboBoxItems) {
			int num = this.fComboBoxItems.size();
			Object item = null;
			for(int i = 0;i < num;i++) {
				item = this.fComboBoxItems.elementAt(i);
				if(isEqual(value,toRealValue(item))) {
					rtn = (FComboBoxItem)item;
					break;
				}
			}
		}
		return rtn;
	}

	//判断两个对象是否相等
	//此处不使用Object.equals(Object o)方法的原因是：
	//如果o1，o2中任意有一个为null时，Object.equals(Object o)方法会抛出空指针异常
	//在此方法中,o1和o2都为null时，返回值为true，仅当有一个为null时，返回值为false
	//当o1和o2都不为null时，使用o1.equals(o2)作为返回值
	private boolean isEqual(Object o1,Object o2) {
		return null != o1?(null != o2?o1.toString().trim().equals(o2.toString().trim()):false):(null == o2);
	}

	/** 由一个FComboBoxItem对象，得到对应的值 */
	private Object toRealValue(Object fComboBoxItem) {
		Object rtn = null;
		if(fComboBoxItem instanceof FComboBoxItem) {
			rtn = ((FComboBoxItem)fComboBoxItem).getValue();
		} else if(null != fComboBoxItem) {
			throw new IllegalArgumentException("toRealValue:the type of item must be FComboBoxItem");
		}
		return rtn;
	}

	/** 由一个FComboBoxItem对象，得到对应的显示字符串 */
	private String toDisplayValue(Object fComboBoxItem) {
		String rtn = null;
		if(fComboBoxItem instanceof FComboBoxItem) {
			rtn = ((FComboBoxItem)fComboBoxItem).toString();
		} else if(null != fComboBoxItem) {
			throw new IllegalArgumentException("toDisplayValue:the type of item must be FComboBoxItem");
		}
		return rtn;
	}

	/** 由列表项的值列表，调用toString()方法，生成列表项的显示字符串列表 */
	private String[] toDisplayValues(Vector values) {
		int num = values.size();
		String[] rtn = new String[num];
		Object o = null;
		for(int i = 0;i < num;i++) {
			o = values.elementAt(i);
			rtn[i] = null != o ?o.toString():"";
		}
		return rtn;
	}

	/** 确保一个Vector不为null */
	private Vector noneNullVector(Vector v) {
		return null != v?v:new Vector();
	}

	public String getDataSource()
	{
		return dataSource;
	}

	/**
	 * 设置数据源
	 */
	public void setDataSource(String dataSource)
	{
		this.dataSource = dataSource;
		this.excuteQuery();
		Vector vector = new Vector();
		String[] displayValues = new String[data.size()];
		for(int i = 0; i < data.size(); i++)
		{
			
			Map map = (Map)data.get(i);
			
//Updated on 2006-08-31,辅助录入下拉框数据不分级次显示(暂定)。		
//			int levelNum = (Integer.valueOf(map.get(level).toString())).intValue();
			vector.add(map.get(pkField));
			StringBuffer showValue = new StringBuffer();
//			for(int p = levelNum; p > 1; p--)
//			{
//				showValue.append("    ");  // 根据级次来设置空格的数目
//			}
			for(int j = 0; j < nameField.length; j++)
			{
				showValue.append(map.get(nameField[j]));
				showValue.append(" ");
			}
			displayValues[i] = showValue.substring(0, showValue.length()-1);
		}
		this.setRefModel(vector, displayValues);
	}
	
	/**
	 * 执行查询，得到相应数据，在子类中实现
	 *
	 */
	public void excuteQuery()
	{
		
	}
	
	public String getPkField()
	{
		return pkField;
	}
	
	public String[] getNameField()
	{
		return nameField;
	}
	
	public List getData()
	{
		return data;
	}
	
	public String getSearchCondition() {
		return this.searchCondition;
	}
	
	public void setSearchCondition(String searchConditon) {
		this.searchCondition = searchConditon;
	}
	
	public Map getCtrlElementValues() {
		return this.ctrlElementValues;
	}
	
	public void setCtrlElementValues(Map ctrlElementValues) {
		this.ctrlElementValues = ctrlElementValues;
	}
	
	public String getCoaId() {
		return this.coaId;
	}
	
	public void setCoaId(String coaId) {
		this.coaId = coaId;
	}
	
}
