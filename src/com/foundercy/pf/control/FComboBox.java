/*
 * filename:  FComboBox.java
 *
 * Version: 1.0
 *
 * Date: 2005-12-30
 *
 * Copyright notice:  2005 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * <p>
 * Title: ����+������ؼ�
 * </p>
 * <p>
 * Description:�����ؼ�FCombox
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * <p>
 * Company:����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * 
 * @author yangbo
 * @author �ƽ� 2008��4��7������
 * @version 1.0
 */

public class FComboBox extends AbstractRefDataField {

	private static final long serialVersionUID = -1L;

	/** ������ ����б�򲻱�ѡ�е����� */
	private static final int NONE_SELECTED_INDEX = 0;

	/**
	 * �����û�ѡ�������б��
	 */
	private JComboBox comboBox;

	// comboBox�༭��
	private JTextField textField = null;

	// comboBox�༭����������
	private String editText = "";

	/**
	 * ���캯��
	 */
	public FComboBox() {
		allLayout();
		setRefModel(new DefaultFComboBoxModel(""));
	}

	/**
	 * ���캯��
	 * 
	 * @param title
	 *            �ؼ�����ʾ����
	 */
	public FComboBox(String title) {
		super.setTitle(title);
		allLayout();
		setRefModel(new DefaultFComboBoxModel(""));
	}

	/**
	 * ȡ��,����ģ��
	 * 
	 * @return ����ģ��
	 */
	public RefModel getRefModel() {
		return getModel();
	}

	/**
	 * ���ã��ַ�������ģ��
	 * 
	 * @param modelString
	 *            �ַ�������ģ��
	 */
	public void setRefModel(String modelString) {
		if (modelString != null && !modelString.trim().equals(""))
			setRefModel(new DefaultFComboBoxModel(modelString));
	}

	/**
	 * ���ò���ģ��
	 * 
	 * @param values
	 *            comboBox�е��б����ֵ
	 */
	public void setRefModel(Vector values) {
		setRefModel(values, (String[]) null);
	}

	/**
	 * ���ò���ģ��
	 * 
	 * @param values
	 *            comboBox�е��б����ֵ
	 * @param displayValues
	 *            comboBox�е��б���ģ���ʾ�ַ���
	 */
	public void setRefModel(Vector values, String[] displayValues) {
		setRefModel(new DefaultFComboBoxModel(values, displayValues));
	}

	/**
	 * ���ò���ģ��
	 * 
	 * @param dncbm
	 *            comboBox�Ĳ���ģ��
	 */
	public void setRefModel(FComboBoxModel dncbm) {

		FComboBoxModel model = ((null != dncbm) ? dncbm
				: (new DefaultFComboBoxModel()));

		this.comboBox.setModel(model);

		if (model.getSize() > 0)
			this.comboBox.setSelectedIndex(NONE_SELECTED_INDEX);
	}

	/**
	 * �õ��򵥿ؼ��У������û������JComponent���
	 * 
	 * @return �����û������JComponent���
	 */
	public JComponent getEditor() {
		if (this.comboBox == null) {
			this.comboBox = new JComboBox();

			// ���FComboBox�ڱ���б༭ʱ,��TAB��,���Ľ���ʧȥ����
			this.comboBox.setEditor(new FBasicComboBoxEditor());

			// textField = (JTextField)
			// comboBox.getEditor().getEditorComponent();
			// ������ValueChange�¼�
			this.comboBox.addItemListener(new ItemListener() {
				private Object oldValue;

				public void itemStateChanged(ItemEvent e) {
					if (ItemEvent.SELECTED == e.getStateChange()) {
						if (e.getItem() instanceof FComboBoxItem) {
							Object newValue = ((FComboBoxItem) e.getItem())
									.getValue();

							fireValueChange(oldValue, newValue);
						}
					} else if (ItemEvent.DESELECTED == e.getStateChange()) {
						try {
							JComboBox box = (JComboBox) e.getSource();
							if (box.getSelectedIndex() == -1) {
								textField.setText(editText);
							}
							oldValue = ((FComboBoxItem) e.getItem()).getValue();
						} catch (Exception ex) {
							oldValue = "";
						}
					}
				}
			});
			// this.comboBox.getEditor().getEditorComponent().addFocusListener(
			this.comboBox.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
				}

				public void focusLost(FocusEvent e) {
					// editText = textField.getText();
					if (comboBox.isEditable()) {
						JTextField field = (JTextField) comboBox.getEditor()
								.getEditorComponent();
						editText = field.getText();
					}
				}
			});

			// lgc add start 07-4-28
			this.comboBox.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER
							|| e.getKeyCode() == KeyEvent.VK_TAB) {
						comboBox.transferFocus();
					} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						comboBox.setPopupVisible(true);
					}

				}
			});
			/*
			 * this.textField.addKeyListener(new KeyAdapter() { public void
			 * keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			 * comboBox.transferFocus(); editText = textField.getText(); } } });
			 * this.textField.addAncestorListener(new AncestorListener() {
			 * public void ancestorAdded(AncestorEvent event) {
			 * comboBox.requestFocus(); textField.selectAll(); } public void
			 * ancestorMoved(AncestorEvent event) { }
			 * 
			 * public void ancestorRemoved(AncestorEvent event) { } });
			 */
		}

		// lgc add end 07-4-28

		return this.comboBox;
	}

	/**
	 * �õ��ؼ���ֵ
	 * 
	 * @return �ؼ���ֵ
	 */
	public Object getValue() {
		// if (this.comboBox.getSelectedIndex() == -1) {
		// return this.editText;
		
		if (isEditable()) {
			// ȡ��ComboBox�ı����е�ֵ
			JTextField field = (JTextField) comboBox.getEditor()
					.getEditorComponent();
			String tempText = field.getText();
			Object[] values = this.getRefModel().getValueByName(tempText);
			// �����ģ�����Ҳ�����Ӧ��ֵ������䵽�ؼ��У�
			if (values == null || values.length == 0) {
				editText = tempText;
				DefaultFComboBoxModel model = (DefaultFComboBoxModel) this
						.getRefModel();
				model.setSelectedItem(editText);
				return editText;
			} else {
				return values[0];
			}
		} else {
			return getModel().getSelectedRealValue();
		}
	}

	/**
	 * �õ��ı������ַ���
	 * 
	 * @return �ı������ַ�����String��
	 */
	public String getText() {
		/**
		 * modify �����ѡ���޷��õ����ı��������ֵ
		 * 
		 * @author jerry 20071225 start
		 */
		return ((JTextField) this.comboBox.getEditor().getEditorComponent())
				.getText();
		// return this.getRefModel().getNameByValue(this.getValue());
		/**
		 * modify �����ѡ���޷��õ����ı��������ֵ
		 * 
		 * @author jerry 20071225 end
		 */
	}

	/**
	 * Ϊ�ı��������ַ���
	 * 
	 * @param text
	 *            Ҫ���õ��ַ���
	 * 
	 */
	public void setText(String text) {
		((JTextField) this.comboBox.getEditor().getEditorComponent())
				.setText(text);
	}

	/** �õ�comboBox��ģ�� */
	private FComboBoxModel getModel() {
		// return (FComboBoxModel) this.comboBox.getModel();
		if (this.comboBox.getModel() instanceof FComboBoxModel)
			return (FComboBoxModel) this.comboBox.getModel();
		else
			return null;
	}

	/**
	 * �������ÿؼ���ֵ����Ҫ����ʵ�֡� ������ʵ��ʱ�����ؿ��ǵ�ǰ�̣߳��Ƿ�Swing�¼������߳�
	 * 
	 * @param value
	 *            ���õ�ֵ
	 */
	public void setValue(Object value) {
		// add by liwenquan 20080415 begin
		try {
			// ���������ַ������͵Ķ����������ת���쳣����
			if (null != value) {
				this.editText = String.valueOf(value);
			} else {
				this.editText = "";
			}
			this.comboBox.getModel().setSelectedItem(value);
		} catch (Exception e) {
			this.editText = "";
		}

		// add by liwenquan 20080415 end

	}

	/**
	 * ����ѡ����������ѡ����Ŀ
	 * 
	 * @param index
	 *            ѡ������
	 */
	public void setSelectedIndex(int index) {
		this.comboBox.setSelectedIndex(index);
	}

	/**
	 * ��ȡѡ�е�ѡ������
	 * 
	 * @return ѡ�е�ѡ������
	 */
	public int getSelectedIndex() {
		return this.comboBox.getSelectedIndex();
	}

	/** �������ÿؼ�Ϊ��ǰ���뽹�㡣* */
	public void setFocusNow() {
		this.comboBox.requestFocus();
	}

	/**
	 * ���ý��㵽�˿ؼ���
	 */
	public void requestFocus() {
		this.comboBox.requestFocus();
	}

	/**
	 * �������ÿؼ��Ƿ���á�
	 * 
	 * @param dataFieldEnabled
	 *            �ؼ��Ƿ����
	 */
	public void setEnabled(boolean dataFieldEnabled) {
		this.comboBox.setEnabled(dataFieldEnabled);
	}

	/**
	 * ȡ�ÿؼ��Ƿ����
	 * 
	 * @return true�����ã�false��������
	 */
	public boolean isEnabled() {
		return this.comboBox.isEnabled();
	}

	/**
	 * ���ÿؼ��Ƿ�ɱ༭�����ڸ���JComponent�޴˷�������˽�������
	 * 
	 * @param editable
	 *            0Ϊ���ɱ༭��1Ϊ�ɱ༭
	 */
	public void setEditable(boolean editable) {
		this.comboBox.setEditable(editable);
	}

	/**
	 * �жϿؼ��Ƿ���Ա༭
	 * 
	 * @return 0Ϊ���ɱ༭��1Ϊ�ɱ༭
	 */
	public boolean isEditable() {
		return this.comboBox.isEditable();
	}

	/**
	 * ��ָ��λ�ò���һ��ѡ��
	 * 
	 * @param value
	 *            ����һ��ѡ�ԭʼ����ΪString����
	 * @param index
	 *            Ҫ�����λ��
	 */
	public void insertItemAt(Object value, int index) {
		if (this.comboBox.getModel() instanceof DefaultFComboBoxModel) {
			((DefaultFComboBoxModel) this.comboBox.getModel()).insertElementAt(
					value, index);
		}
	}

	/**
	 * ��ָ��λ�ò���һ��ѡ��
	 * 
	 * @param value
	 *            �����ѡ���ֵ��ԭʼ����ΪString����
	 * @param displayValue
	 *            ����ѡ�����ʾֵ
	 * @param index
	 *            Ҫ�����λ��
	 */
	public void insertItemAt(Object value, String displayValue, int index) {
		if (this.comboBox.getModel() instanceof DefaultFComboBoxModel) {
			((DefaultFComboBoxModel) this.comboBox.getModel()).insertElementAt(
					value, displayValue, index);
		}
	}

	/**
	 * ����ָ��λ��һ��
	 * 
	 * @param newValue
	 *            Ҫ���ĵ�ѡ���ֵ��ԭʼ����ΪString����
	 * @param index
	 *            Ҫ���ĵ�ѡ���λ��
	 */
	public void updateItemAt(Object newValue, int index) {
		if (this.comboBox.getModel() instanceof DefaultFComboBoxModel) {
			((DefaultFComboBoxModel) this.comboBox.getModel()).updateElementAt(
					newValue, index);
		}
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
	public void updateItemAt(Object newValue, String newDisplayValue, int index) {
		if (this.comboBox.getModel() instanceof DefaultFComboBoxModel) {
			((DefaultFComboBoxModel) this.comboBox.getModel()).updateElementAt(
					newValue, newDisplayValue, index);
		}
	}

	/**
	 * ɾ��ָ����Ŀ ע: ���ô˷����ᴥ��ValueChangedEvent
	 * 
	 * @param anObject
	 *            Ҫɾ����ѡ�ԭʼ����ΪFComboBoxItem����
	 */
	public void removeItem(Object anObject) {
		if (this.comboBox.getModel() instanceof DefaultFComboBoxModel) {
			((DefaultFComboBoxModel) this.comboBox.getModel())
					.removeElement(anObject);
		}
	}

	/**
	 * ɾ��ָ��λ��һ�� ע: ���ô˷����ᴥ��ValueChangedEvent
	 * 
	 * @param anIndex
	 *            Ҫɾ����ѡ������
	 */
	public void removeItemAt(int anIndex) {
		if (this.comboBox.getModel() instanceof DefaultFComboBoxModel) {
			((DefaultFComboBoxModel) this.comboBox.getModel())
					.removeElementAt(anIndex);
		}
	}

	/**
	 * ɾ��������Ŀ
	 */
	public void removeAllItems() {
		if (this.comboBox.getModel() instanceof DefaultFComboBoxModel) {
			((DefaultFComboBoxModel) this.comboBox.getModel())
					.removeAllElements();
		}
	}

	/**
	 * �õ���Ŀ����
	 * 
	 * @return ������Ŀ����
	 */
	public int getItemsCount() {
		if (this.comboBox.getModel() instanceof DefaultFComboBoxModel) {
			return ((DefaultFComboBoxModel) this.comboBox.getModel())
					.getItemsCount();
		}
		return 0;
	}

	/**
	 * ���ص�ǰ��ѡ��
	 * 
	 * @return ��ǰ��ѡ��
	 */
	public Object getSelectedItem() {
		return this.comboBox.getSelectedItem();
	}

	/**
	 * ����Ͽ���ʾ��������ѡ������Ϊ�����еĶ��󣨴�ComboBox�л�ȡ�Ķ���
	 * 
	 * @param obj
	 *            Ҫ��ʾ�Ķ���
	 */
	public void setSelectedItem(Object obj) {
		this.comboBox.setSelectedItem(obj);
	}

	/**
	 * �����ŵ�ѡ��
	 * 
	 * @param index
	 *            ���
	 * @return ���ػ�õ�ѡ�FComboBoxItem��
	 */
	public Object getItemAt(int index) {
		return this.comboBox.getItemAt(index);
	}

	/**
	 * ����Ͽ���ʾ��������ѡ������Ϊ�����еĶ����Լ�������FComboBoxItem����
	 * 
	 * @param obj
	 *            Ҫѡ�е�ѡ�ԭʼ����ΪFComboBoxItem����
	 */
	public void setSelectedItemByNewItemValue(Object obj) {
		FComboBoxItem fbc = (FComboBoxItem) obj;
		FComboBoxModel model = (FComboBoxModel) this.comboBox.getModel();
		for (int i = 0; i < model.getSize(); i++) {
			if (fbc.getValue().toString().equals(
					((FComboBoxItem) this.getItemAt(i)).getValue().toString())) {
				this.setSelectedIndex(i);
			}
		}
	}

	/**
	 * ����Ͽ���ʾ��������ѡ������Ϊ�����еĶ���String����
	 * 
	 * @param str
	 *            Ҫ��ʾ��ѡ���ֵ
	 */
	public void setSelectedItemByNewItemValue(String str) {
		FComboBoxModel model = (FComboBoxModel) this.comboBox.getModel();
		for (int i = 0; i < model.getSize(); i++) {
			if (str.equals(((FComboBoxItem) this.getItemAt(i)).getValue()
					.toString())) {
				this.setSelectedIndex(i);
			}
		}
	}

}
