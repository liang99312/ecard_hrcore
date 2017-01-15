/*
 * filename:  FRadioGroup.java
 *
 * Version: 1.0
 *
 * Date: 2006-2-6
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */

package com.foundercy.pf.control;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JRadioButton;

import com.foundercy.pf.util.DealString;

/**
 * <p>
 * Title: ��ѡ�ؼ�
 * </p>
 * <p>
 * Description: ��ǩ+��ѡ��ؼ�����ʽ
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * <p>
 * Company: ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * 
 * @author yangbo
 * @author �ƽ� 2008��4��10������
 * @version 1.0
 */
public class FRadioGroup extends AbstractRefDataField {
	private static final long serialVersionUID = -6209855860782475193L;

	/**
	 * ��ֱ״̬
	 */
	public final static int VERTICAL = 1; // ��ֱ״̬

	/**
	 * ˮƽ״̬
	 */
	public final static int HORIZON = 0; // ˮƽ״̬

	private Box panel;

	private JRadioButton[] radios; // ��ѡ��ť��

	private RadioModel model; // ����ģ��

	private int layout = 0; // Ĭ����ˮƽ���ַ�ʽ

	private Object oldValue = null;

	/**
	 * ���캯��
	 */
	public FRadioGroup() {
		allLayout();
	}

	/**
	 * ���캯��
	 * 
	 * @param title
	 *            �ؼ�����
	 */
	public FRadioGroup(String title) {
		super.setTitle(title);
		allLayout();
	}

	/**
	 * ���캯��
	 * 
	 * @param title
	 *            ����
	 * @param radioType
	 *            �༭�򲿼��е�ѡ��ť���ַ�ʽ,ˮƽΪFRadioGroup.HORIZON,��ֱΪFRadioGroup.VERTICAL
	 */
	public FRadioGroup(String title, int radioType) {
		this.layout = radioType;
		super.setTitle(title);
		allLayout();
	}

	/**
	 * ���캯��
	 * 
	 * @param title
	 *            ����
	 * @param radioType
	 *            �༭�򲿼��е�ѡ��ť���ַ�ʽ,ˮƽΪFRadioGroup.HORIZON,��ֱΪFRadioGroup.VERTICAL
	 * @param modelString
	 */
	public FRadioGroup(String title, int radioType, String modelString) {
		this.layout = radioType;
		super.setTitle(title);
		allLayout();
		this.setRefModel(modelString);
	}

	/**
	 * ��ѡ��ť�Ƿ����
	 * 
	 * @return trueΪ���ã�falseΪ������
	 */
	public boolean isEditable() {
		boolean editable = false;
		for (int i = 0; radios != null && i < radios.length; i++) {
			if (radios[i].isEnabled()) {
				editable = true;
			}
		}
		return editable;
	}

	/**
	 * �õ��ؼ���ֵ
	 * 
	 * @return ֵ����
	 */
	public Object getValue() {
		for (int i = 0; radios != null && i < radios.length; i++) {
			if (radios[i].isSelected()) {
				return model.getValueAt(i);
			}
		}
		return null;
	}

	/**
	 * ���ð�ť�Ƿ�ɱ༭
	 * 
	 * @param editable
	 *            trueΪ�ɱ༭��falseΪ���ɱ༭
	 */
	public void setEditable(boolean editable) {
		for (int i = 0; radios != null && i < radios.length; i++) {
			radios[i].setEnabled(editable);
		}
	}

	/**
	 * ���ù��
	 */
	protected void setFocusNow() {
		for (int i = 0; radios != null && i < radios.length; i++) {
			if (radios[i].isEnabled()) {
				radios[i].requestFocus();
			}
		}
	}

	/**
	 * �������ÿؼ���ֵ ������ʵ��ʱ�����ؿ��ǵ�ǰ�̣߳��Ƿ�Swing�¼������߳�
	 * 
	 * @param value
	 *            ���õ�ֵ
	 */
	public void setValue(Object value) {
		if (model == null)
			return;
		// Object oldValue = this.getValue();
		for (int i = 0; radios != null && i < radios.length; i++) {
			if (model.getValueAt(i).toString().equals(value)) {
				if (!radios[i].isSelected())
					radios[i].setSelected(true);
			} else {
				if (radios[i].isSelected())
					radios[i].setSelected(false);
			}
		}
		// this.fireValueChange(oldValue,value);
	}

	/**
	 * ����Ҳ�ؼ�
	 * 
	 * @return �Ҳ�ؼ�
	 */
	public JComponent getEditor() {
		if (panel == null) {
			if (this.getRadioLayout() == 0)
				panel = Box.createHorizontalBox();
			else
				panel = Box.createVerticalBox();

			panel.setOpaque(false);
			this.setRefModel(new RadioModel());
		}
		return panel;
	}

	/**
	 * ��������ģ��
	 * 
	 * @param model
	 *            �µ�����ģ��
	 */
	public void setRefModel(RadioModel model) {
		this.model = model;
		resetUI();
	}

	/**
	 * ���ÿؼ��������ֵ����ʾ����
	 * 
	 * @param modelString
	 *            �ɸ������ֵ����ʾ������ɵ��ַ������硰1#�޳�&2#���&3#��Ȩ����
	 *            ÿһ���Ӳ�������ʵ��ֵ����ʾ���ݣ��á�#���ָ������Ӳ���֮���á�&���ָ�
	 */
	public void setRefModel(String modelString) {
		RadioModel m = new RadioModel();
		m.setData(DealString.buildString(modelString));
		this.setRefModel(m);
	}

	/**
	 * ��������UI
	 */
	private void resetUI() {
		if (this.getRefModel() == null)
			return;
		// Comments added on 2006-05-12
		// if (panel == null) {
		if (this.getRadioLayout() == 0)
			panel = Box.createHorizontalBox();
		else
			panel = Box.createVerticalBox();
		panel.setOpaque(false);
		// }else {
		// panel.removeAll();
		// }
		// ButtonGroup group = new ButtonGroup();
		radios = new JRadioButton[model.getRadioCount()];
		MyFocusAdapter fa = new MyFocusAdapter();
		for (int i = 0; i < model.getRadioCount(); i++) {
			radios[i] = new JRadioButton(model.getNameByValue(
					model.getValueAt(i)).toString());
			radios[i].setOpaque(false);
			radios[i].addFocusListener(fa);
			radios[i].setPreferredSize(new Dimension());
			panel.add(radios[i]);
			final int m = i;
			radios[i].addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent itemEvent) {
					if (itemEvent.getStateChange() == ItemEvent.DESELECTED) {
						// if(!radio.isSelected()) {
						Object oldValue = FRadioGroup.this.model.getValueAt(m);
						Object newValue = FRadioGroup.this.getValue();
						/**
						 * �޸ĵ�ѡ��ť��ѡ��ʱ���ٴε���˰�ť��ѡ��״̬��ʧ���Ķ��˴�
						 * 
						 * @author jerry
						 */
						/** *********************begin******************* */
						if (oldValue.equals("0") && newValue == null) {
							newValue = oldValue;
						}
						/** *********************end********************* */
						FRadioGroup.this.fireValueChange(oldValue, newValue);
						FRadioGroup.this.oldValue = newValue;
						// }
					}
					if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
						// Object oldValue = FRadioGroup.this.getValue();
						Object newValue = FRadioGroup.this.model.getValueAt(m);
						if (oldValue == null) {
							FRadioGroup.this
									.fireValueChange(oldValue, newValue);
						}
					}
				}
			});
		}

		for (int i = 0; radios != null && i < radios.length; i++) {
			final int m = i;
			radios[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					for (int j = 0; radios != null && j < radios.length; j++) {
						if (m != j) {
							if (radios[j].isSelected())
								radios[j].setSelected(false);
						}
						/**
						 * �޸ĵ�ѡ��ť��ѡ��ʱ���ٴε���˰�ť��ѡ��״̬��ʧ���Ķ��˴�
						 * 
						 * @author jerry
						 */
						/** *****************begin*************** */
						if (m == j) {
							radios[j].setSelected(true);
						}
						/** *****************end**************** */
					}

				}
			});
		}
		this.validate();
		// added on 2006-05-12
		this.removeAll();
		this.allLayout();
		//
	}

	/**
	 * �õ�����ģ��
	 * 
	 * @return ����ģ��
	 */
	public RefModel getRefModel() {
		return this.model;
	}

	/**
	 * �õ����ַ�ʽ
	 * 
	 * @return ���ַ�ʽ
	 */
	public int getRadioLayout() {
		return this.layout;
	}

	/**
	 * ���ò��ַ�ʽ
	 * 
	 * @param layout
	 *            ���ַ�ʽ
	 */
	public void setRadioLayout(int layout) {
		this.layout = layout;
		this.resetUI();
	}

	/**
	 * �����������Button.
	 * 
	 * @param index
	 *            ��ť���
	 * @return ��ť����
	 */
	public JRadioButton getRadioByIndex(int index) {
		if (radios == null || index >= radios.length || index < 0) {
			return null;
		} else {
			return radios[index];
		}
	}

	class MyFocusAdapter extends FocusAdapter {
		/**
		 * ��ʧ����ʱ����Ӧ�¼�
		 * 
		 * @param ev
		 *            �����¼�
		 * 
		 */
		public void focusLost(FocusEvent ev) {
			JRadioButton radio = (JRadioButton) ev.getSource();
			Object to = ev.getOppositeComponent();
			for (int i = 0; i < radios.length; i++) {
				if (radios[i] != radio && radios[i] == to)
					return;
			}
			fireDataFieldFocusLost(ev);
		}

		/**
		 * ��ȡ����ʱ����Ӧ�¼�
		 * 
		 * @param ev
		 *            �����¼�
		 * 
		 */
		public void focusGained(FocusEvent ev) {
			JRadioButton radio = (JRadioButton) ev.getSource();
			Object from = ev.getOppositeComponent();
			for (int i = 0; i < radios.length; i++) {
				if (radios[i] != radio && radios[i] == from)
					return;
			}
			fireDataFieldFocusGained(ev);
		}
	}

	/**
	 * Ϊ���еİ�������ǰ��ɫ
	 * 
	 * @param color
	 *            Ҫ���õ���ɫ
	 * 
	 */
	public void setForeground(java.awt.Color color) {
		for (int i = 0; radios != null && i < radios.length; i++) {
			radios[i].setForeground(color);
		}
	}

	/**
	 * �õ���ť������
	 * 
	 * @return ��ť������
	 */
	public JRadioButton[] getRadios() {
		return radios;
	}
}
