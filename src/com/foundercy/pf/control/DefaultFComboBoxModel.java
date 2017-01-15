/*
 * filename:  DefaultFComboBoxModel.java
 *
 * Version: 1.0
 *
 * Date: 2005-12-30
 *
 * Copyright notice:  2005 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListDataListener;

import com.foundercy.pf.util.DealString;

/**
 * <p>
 * Title: ����������ģ��
 * </p>
 * <p>
 * Description:����������ģ��
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005 ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * <p>
 * Company:����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * 
 * @author �ƽ� 2008��4��7������
 * @version 1.0
 */

public class DefaultFComboBoxModel implements FComboBoxModel {
	private DefaultComboBoxModel dcbm;

	private Vector fComboBoxItems;

	/**
	 * ���캯��
	 * 
	 */
	public DefaultFComboBoxModel() {
		this((Vector) null);
	}

	/**
	 * ���캯��
	 * 
	 * @param values
	 *            �б����ֵ�б�
	 */
	public DefaultFComboBoxModel(Vector values) {
		this(values, (String[]) null);
	}

	/**
	 * ���캯�� �б����ֵ�б����б������ʾ�ַ����б���˳��Ӧ����һһ��Ӧ��
	 * 
	 * @param values
	 *            �б����ֵ�б�
	 * @param displayValues
	 *            �б������ʾ�ַ����б�
	 */
	public DefaultFComboBoxModel(Vector values, String[] displayValues) {
		setRefModel(values, displayValues);
	}

	/**
	 * ���캯��
	 * 
	 * @param model
	 *            �ַ�������ģ��
	 */
	public DefaultFComboBoxModel(String model) {
		setRefModel(model);
	}

	/**
	 * ���ã��ַ�������ģ��
	 * 
	 * @param model
	 *            �ַ�������ģ��
	 */
	public void setRefModel(String model) {

		if (null != model) {
			String[][] t = DealString.buildString(model);

			// ���죬�б����ֵ�б�
			if (null != t) {
				int len = t.length;

				Vector values = new Vector(len);
				for (int i = 0; i < len; i++) {
					values.addElement(t[i][0]);
				}
				// ���죬�б������ʾ�ַ����б�
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
	 * @param values
	 *            �б����ֵ�б�
	 */
	public void setRefModel(Vector values) {
		setRefModel(values, (String[]) null);
	}

	/**
	 * ���ò���ģ��
	 * 
	 * @param values
	 *            �б����ֵ�б�
	 * @param displayValues
	 *            �б������ʾ�ַ����б�
	 */
	public void setRefModel(Vector values, String[] displayValues) {

		values = noneNullVector(values);

		if (null == displayValues) {
			displayValues = toDisplayValues(values);
		} else if (values.size() > displayValues.length) {
			throw new IllegalArgumentException(
					"the number of values is larger than the number of displayValues!");
		}

		if (null == this.dcbm) {
			this.dcbm = new DefaultComboBoxModel();
		} else {
			this.dcbm.removeAllElements();
		}

		int num = values.size();
		if (null == this.fComboBoxItems) {
			this.fComboBoxItems = new Vector(num);
		} else {
			this.fComboBoxItems.removeAllElements();
		}

		FComboBoxItem ncbi = null;
		for (int i = 0; i < num; i++) {
			ncbi = new FComboBoxItem(displayValues[i], values.elementAt(i));
			this.fComboBoxItems.addElement(ncbi);
			this.dcbm.addElement(ncbi);
		}
	}
    
	/**
	 * �������ַ������õ���Ӧ��ֵ
	 * 
	 * @param name
	 *            �����ַ���
	 * @return �����ַ�������Ӧ��ֵ
	 */
	public Object[] getValueByName(String name) {
		Vector v = new Vector();
		if (null != name && null != this.fComboBoxItems) {
			int num = this.fComboBoxItems.size();
			Object item = null;
			for (int i = 0; i < num; i++) {
				item = this.fComboBoxItems.elementAt(i);
				if (name.equals(toDisplayValue(item))) {
					v.addElement(toRealValue(item));
				}
			}
		}
		return 0 != v.size() ? v.toArray() : null;
	}

	/**
	 * ��ֵ���õ���Ӧ�������ַ���
	 * 
	 * @param value
	 *            ֵ
	 * @return ֵ����Ӧ�������ַ���
	 */
	public String getNameByValue(Object value) {
		// ����FComboBox���⴦��,���û���ҵ���Ϊ�û�¼����ַ�
		String rtn = null;
		if (value instanceof FComboBoxItem) {
			rtn = toDisplayValue(value);
		} else {
			if (null != this.fComboBoxItems) {
				int num = this.fComboBoxItems.size();
				Object item = null;
				for (int i = 0; i < num; i++) {
					item = this.fComboBoxItems.elementAt(i);
					if (isEqual(value, toRealValue(item))) {
						rtn = toDisplayValue(item);
						//break;
						return rtn;//modified by fangyi 2008-05-27
					}
				}
				//modified by fangyi 2008-05-27,to auto add the value/name to RefModel.
				if(value!=null && !value.equals("")) {
					this.insertElementAt(value,value.toString(),0);
					rtn = value.toString();
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
	 * 
	 * @param index
	 *            �б��������
	 * @return �б����ֵ
	 */
	public Object getRealValueAt(int index) {
		return toRealValue(this.dcbm.getElementAt(index));
	}

	// implements javax.swing.ComboBoxModel
	/**
	 * Set the value of the selected item. The selected item may be null.
	 * <p>
	 * 
	 * @param anItem
	 *            The combo box value or null for no selection.
	 */
	public void setSelectedItem(Object anItem) {
		FComboBoxItem ncbi = null;
		if (anItem instanceof FComboBoxItem) {
			ncbi = (FComboBoxItem) anItem;
		} else {
			ncbi = toFComboBoxItem(anItem);
		}
		//�Զ������ֹ�¼�������
		if(anItem != null && ncbi == null && !anItem.equals("")){
			ncbi = new FComboBoxItem(anItem.toString(),anItem);
			this.fComboBoxItems.insertElementAt(ncbi,0);
			this.dcbm.insertElementAt(ncbi,0);
		}		
		this.dcbm.setSelectedItem(ncbi);
	}

	/**
	 * implements javax.swing.ComboBoxModel
	 * 
	 * @return ѡ�е�ѡ��
	 */
	public Object getSelectedItem() {
		// return toRealValue(this.dcbm.getSelectedItem());
		return this.dcbm.getSelectedItem();
	}

	/**
	 * implements javax.swing.ListModel
	 * 
	 * @param index
	 *            ��ȡĳ��λ���ϵ�ѡ��
	 * @return ����ĳ��ѡ��
	 */
	public Object getElementAt(int index) {
		// return toRealValue(this.dcbm.getElementAt(index));
		return this.dcbm.getElementAt(index);
	}

	/**
	 * implements javax.swing.ListModel
	 * 
	 * @return ����ѡ��ĸ���
	 */
	public int getSize() {
		return this.dcbm.getSize();
	}

	/**
	 * Adds a listener to the list that's notified each time a change to the
	 * data model occurs.
	 * 
	 * @param l
	 *            the <code>ListDataListener</code> to be added
	 */
	public void addListDataListener(ListDataListener l) {
		this.dcbm.addListDataListener(l);
	}

	/**
	 * Removes a listener from the list that's notified each time a change to
	 * the data model occurs.
	 * 
	 * @param l
	 *            the <code>ListDataListener</code> to be removed
	 */
	public void removeListDataListener(ListDataListener l) {
		this.dcbm.removeListDataListener(l);
	}

	/** ��ֵ���ӱ����б����Vector�У�ȡ��FComboBoxItem���� */
	private FComboBoxItem toFComboBoxItem(Object value) {
		FComboBoxItem rtn = null;
		if (null != this.fComboBoxItems) {
			int num = this.fComboBoxItems.size();
			Object item = null;
			for (int i = 0; i < num; i++) {
				item = this.fComboBoxItems.elementAt(i);
				if (isEqual(value, toRealValue(item))) {
					rtn = (FComboBoxItem) item;
					break;
				}
			}
		}
		return rtn;
	}

	// �ж����������Ƿ����
	// �˴���ʹ��Object.equals(Object o)������ԭ���ǣ�
	// ���o1��o2��������һ��Ϊnullʱ��Object.equals(Object o)�������׳���ָ���쳣
	// �ڴ˷�����,o1��o2��Ϊnullʱ������ֵΪtrue��������һ��Ϊnullʱ������ֵΪfalse
	// ��o1��o2����Ϊnullʱ��ʹ��o1.equals(o2)��Ϊ����ֵ
	private boolean isEqual(Object o1, Object o2) {
		return null != o1 ? (null != o2 ? o1.toString().trim().equals(
				o2.toString().trim()) : false) : (null == o2);
	}

	/** ��һ��FComboBoxItem���󣬵õ���Ӧ��ֵ */
	private Object toRealValue(Object fComboBoxItem) {
		Object rtn = null;
		if (fComboBoxItem instanceof FComboBoxItem) {
			rtn = ((FComboBoxItem) fComboBoxItem).getValue();
		} else if (null != fComboBoxItem) {
			throw new IllegalArgumentException(
					"toRealValue:the type of item must be FComboBoxItem");
		}
		return rtn;
	}

	/** ��һ��FComboBoxItem���󣬵õ���Ӧ����ʾ�ַ��� */
	private String toDisplayValue(Object fComboBoxItem) {
		String rtn = null;
		if (fComboBoxItem instanceof FComboBoxItem) {
			rtn = ((FComboBoxItem) fComboBoxItem).toString();
		} else if (null != fComboBoxItem) {
			throw new IllegalArgumentException(
					"toDisplayValue:the type of item must be FComboBoxItem");
		}
		return rtn;
	}

	/** ���б����ֵ�б�����toString()�����������б������ʾ�ַ����б� */
	private String[] toDisplayValues(Vector values) {
		int num = values.size();
		String[] rtn = new String[num];
		Object o = null;
		for (int i = 0; i < num; i++) {
			o = values.elementAt(i);
			rtn[i] = null != o ? o.toString() : "";
		}
		return rtn;
	}

	/** ȷ��һ��Vector��Ϊnull */
	private Vector noneNullVector(Vector v) {
		return null != v ? v : new Vector();
	}

	/**
	 * ��ָ��λ�ò���һ��
	 * 
	 * @param value
	 * @param index
	 */
	public void insertElementAt(Object value, int index) {
		if (null == this.dcbm)
			this.dcbm = new DefaultComboBoxModel();
		if (null == this.fComboBoxItems)
			this.fComboBoxItems = new Vector();

		FComboBoxItem cbi = new FComboBoxItem(value);
		this.dcbm.insertElementAt(cbi, index);
		this.fComboBoxItems.insertElementAt(cbi, index);
	}

	/**
	 * ��ָ��λ�ò���һ��
	 * 
	 * @param value
	 * @param displayValue
	 * @param index
	 */
	public void insertElementAt(Object value, String displayValue, int index) {
		if (null == this.dcbm)
			this.dcbm = new DefaultComboBoxModel();
		if (null == this.fComboBoxItems)
			this.fComboBoxItems = new Vector();

		FComboBoxItem cbi = new FComboBoxItem(displayValue, value);
		this.dcbm.insertElementAt(cbi, index);
		this.fComboBoxItems.insertElementAt(cbi, index);
	}

	/**
	 * ����ָ��λ��һ��
	 * 
	 * @param newValue
	 *            Ҫ���ĵ�ѡ���ֵ��ԭʼ����ΪString����
	 * @param index
	 *            Ҫ���ĵ�ѡ���λ��
	 */
	public void updateElementAt(Object newValue, int index) {
		this.dcbm.removeElementAt(index);
		this.fComboBoxItems.removeElementAt(index);

		FComboBoxItem cbi = new FComboBoxItem(newValue);
		this.dcbm.insertElementAt(cbi, index);
		this.fComboBoxItems.insertElementAt(cbi, index);
	}

	/**
	 * ����ָ��λ��һ��
	 * 
	 * @param newValue
	 *            Ҫ���ĵ�ѡ���ֵ��ԭʼ����ΪString����
	 * @param newDisplayValue
	 *            Ҫ��ʾ��ѡ���ֵ
	 * @param index
	 *            Ҫ���ĵ�ѡ���λ��
	 */
	public void updateElementAt(Object newValue, String newDisplayValue,
			int index) {
		this.dcbm.removeElementAt(index);
		this.fComboBoxItems.removeElementAt(index);

		FComboBoxItem cbi = new FComboBoxItem(newDisplayValue, newValue);
		this.dcbm.insertElementAt(cbi, index);
		this.fComboBoxItems.insertElementAt(cbi, index);
	}

	/**
	 * ɾ��ָ����Ŀ
	 * 
	 * @param anIndex
	 */
	public void removeElementAt(int anIndex) {
		this.dcbm.removeElementAt(anIndex);
		this.fComboBoxItems.removeElementAt(anIndex);
	}

	/**
	 * ɾ��ָ��λ��һ��
	 * 
	 * @param anObject
	 */
	public void removeElement(Object anObject) {
		this.dcbm.removeElement(anObject);
		this.fComboBoxItems.removeElement(anObject);
	}

	/**
	 * ɾ��������Ŀ
	 */
	public void removeAllElements() {
		this.dcbm.removeAllElements();
		this.fComboBoxItems.removeAllElements();
	}

	/**
	 * �õ�������Ŀ����
	 */
	public int getItemsCount() {
		return this.fComboBoxItems == null ? 0 : this.fComboBoxItems.size();
	}
}
