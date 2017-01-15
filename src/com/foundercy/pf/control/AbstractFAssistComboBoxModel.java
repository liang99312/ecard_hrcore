package com.foundercy.pf.control;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListDataListener;

import com.foundercy.pf.util.DealString;

public class AbstractFAssistComboBoxModel implements FAssistComboBoxModel
{
	protected List data;  //��������
	protected String pkField;  //ʵ��ֵ�ֶ�
	protected String[] nameField; //��ʾֵ�ֶ����
	protected String level;  // �����ֶ�
	private String searchCondition = "";//��ѯ����
	private String coaId;// COA_ID
	private Map ctrlElementValues = null;//������ϵ
	
	private DefaultComboBoxModel dcbm;

	private Vector fComboBoxItems;
	
	protected String dataSource = "";

	/**
	 * ���캯��
	 *
	 */
	public AbstractFAssistComboBoxModel() {
		this((Vector)null);
	}

	/**
	 * ���캯��
	 *
	 * @param values �б����ֵ�б�
	 */
	public AbstractFAssistComboBoxModel(Vector values) {
		this(values,(String[])null);
	}

	/**
	 * ���캯��
	 * �б����ֵ�б����б������ʾ�ַ����б���˳��Ӧ����һһ��Ӧ��
	 *
	 * @param values �б����ֵ�б�
	 * @param displayValues �б������ʾ�ַ����б�
	 */
	public AbstractFAssistComboBoxModel(Vector values,String[] displayValues) {
		setRefModel(values,displayValues);
	}

	/**
	 * ���캯��
	 * @param model �ַ�������ģ��
	 * @param valueType �ַ�������ģ���У�ֵ������
	 */
	public AbstractFAssistComboBoxModel(String model) {
		setRefModel(model);
	}

	/**
	 * ���ã��ַ�������ģ��
	 * @param model �ַ�������ģ��
	 * @param valueType �ַ�������ģ���У�ֵ������
	 */
	public void setRefModel(String model) {

		if (null != model) {
			String[][] t = DealString.buildString(model);

			//���죬�б����ֵ�б�
			if (null != t) {
				int len = t.length;

				Vector values = new Vector(len);
				for (int i = 0; i < len; i++) {
					values.addElement(t[i][0]);
				}
				//���죬�б������ʾ�ַ����б�
				String[] displayValues = new String[len];

				for (int i = 0; i < len; i++) {
					displayValues[i] = t[i][1];
				}

				setRefModel(values, displayValues);
			}
		}
	}

	/**
	 * ���ò���ģ��
	 *
	 * @param values �б����ֵ�б�
	 */
	public void setRefModel(Vector values) {
		setRefModel(values,(String[])null);
	}

	/**
	 * ���ò���ģ��
	 * 
	 * @param values �б����ֵ�б�
	 * @param displayValues �б������ʾ�ַ����б�
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
	 * �������ַ������õ���Ӧ��ֵ
	 *
	 * @param name �����ַ���
	 * @return �����ַ�������Ӧ��ֵ
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
	 * ��ֵ���õ���Ӧ�������ַ���
	 *
	 * @param value ֵ
	 * @return ֵ����Ӧ�������ַ���
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
	 * �õ���ѡ���б����ֵ
	 * 
	 * @return �б����ֵ
	 */
	public Object getSelectedRealValue() {
		return toRealValue(this.dcbm.getSelectedItem());
		
	}
	/**
	 * �����б�����������õ��б����ֵ
	 * @param index �б��������
	 * @return �б����ֵ
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

	/** ��ֵ���ӱ����б����Vector�У�ȡ��FComboBoxItem���� */
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

	//�ж����������Ƿ����
	//�˴���ʹ��Object.equals(Object o)������ԭ���ǣ�
	//���o1��o2��������һ��Ϊnullʱ��Object.equals(Object o)�������׳���ָ���쳣
	//�ڴ˷�����,o1��o2��Ϊnullʱ������ֵΪtrue��������һ��Ϊnullʱ������ֵΪfalse
	//��o1��o2����Ϊnullʱ��ʹ��o1.equals(o2)��Ϊ����ֵ
	private boolean isEqual(Object o1,Object o2) {
		return null != o1?(null != o2?o1.toString().trim().equals(o2.toString().trim()):false):(null == o2);
	}

	/** ��һ��FComboBoxItem���󣬵õ���Ӧ��ֵ */
	private Object toRealValue(Object fComboBoxItem) {
		Object rtn = null;
		if(fComboBoxItem instanceof FComboBoxItem) {
			rtn = ((FComboBoxItem)fComboBoxItem).getValue();
		} else if(null != fComboBoxItem) {
			throw new IllegalArgumentException("toRealValue:the type of item must be FComboBoxItem");
		}
		return rtn;
	}

	/** ��һ��FComboBoxItem���󣬵õ���Ӧ����ʾ�ַ��� */
	private String toDisplayValue(Object fComboBoxItem) {
		String rtn = null;
		if(fComboBoxItem instanceof FComboBoxItem) {
			rtn = ((FComboBoxItem)fComboBoxItem).toString();
		} else if(null != fComboBoxItem) {
			throw new IllegalArgumentException("toDisplayValue:the type of item must be FComboBoxItem");
		}
		return rtn;
	}

	/** ���б����ֵ�б�����toString()�����������б������ʾ�ַ����б� */
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

	/** ȷ��һ��Vector��Ϊnull */
	private Vector noneNullVector(Vector v) {
		return null != v?v:new Vector();
	}

	public String getDataSource()
	{
		return dataSource;
	}

	/**
	 * ��������Դ
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
			
//Updated on 2006-08-31,����¼�����������ݲ��ּ�����ʾ(�ݶ�)��		
//			int levelNum = (Integer.valueOf(map.get(level).toString())).intValue();
			vector.add(map.get(pkField));
			StringBuffer showValue = new StringBuffer();
//			for(int p = levelNum; p > 1; p--)
//			{
//				showValue.append("    ");  // ���ݼ��������ÿո����Ŀ
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
	 * ִ�в�ѯ���õ���Ӧ���ݣ���������ʵ��
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
