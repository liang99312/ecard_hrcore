/*
 * filename:  FTimeChooser.java
 *
 * Version: 1.0
 *
 * Date: 2006-2-20
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.foundercy.pf.util.Resource;

/**
 * <p>
 * Title: ʱ��ѡ�����ؼ�
 * </p>
 * <p>
 * Description: ʱ��ѡ�����ؼ�
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * <p>
 * Company: ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * 
 * @deprecated ��FTimeChooser�滻
 * @author yangbo
 * @author �ƽ� 2008��4��17������
 * @version 1.0
 */
public class FDateTimeChooser extends AbstractRefDataField {
	private static final long serialVersionUID = -6802952323157063095L;

	private JTextField dateField;

	JButton btnTime = null;

	private DateTimeRefModel refModel = new DateTimeRefModel();

	private Format format = FormatManager.getDateTimeFormat();

	/**
	 * ���캯��
	 * 
	 */
	public FDateTimeChooser() {
		allLayout();
	}

	/**
	 * ���캯��
	 * 
	 * @param title
	 *            ����
	 */
	public FDateTimeChooser(String title) {
		super.setTitle(title);
		allLayout();
	}

	/**
	 * �õ���ǰ���ڴ洢��ʽ
	 * 
	 * @return ��ʽ��Ϣ
	 */
	public Format getFormat() {
		return this.format;
	}

	/**
	 * ���õ�ǰ���ڴ洢��ʽ
	 * 
	 * @param format
	 *            ��ʽ��Ϣ
	 */
	public void setFormat(Format format) {
		this.format = format;
	}

	/**
	 * �õ�����ģ��
	 * 
	 * @return ����ģ��
	 */
	public RefModel getRefModel() {
		return refModel;
	}

	/**
	 * ��������ģ��
	 * 
	 * @param model
	 *            ����ģ��
	 */
	public void setRefModel(RefModel model) {
		this.refModel = (DateTimeRefModel) model;
	}

	/**
	 * ���ý���Ϊ��ǰ�Ŀؼ�
	 */
	protected void setFocusNow() {
		btnTime.requestFocus();
	}

	/**
	 * �õ���ǰʵ�ʲ����Ŀؼ�
	 * 
	 * @return �ؼ�����
	 */
	public JComponent getEditor() {
		dateField = new JTextField();
		// dateField.setEditable(false);
		// dateField.setDocument(new DateDocument(dateField));
		// dateField.setEditable(false);
		btnTime = new JButton(Resource.getImage("tree.gif"));
		Dimension timeSelecte = new Dimension(20, 20);
		btnTime.setPreferredSize(timeSelecte);
		btnTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// �õ���ֵ
				Object oldValue = getValue();
				FDateTimeSetDialog dlg = new FDateTimeSetDialog((JFrame) null,
						"�������ô���", true, oldValue);
				dlg.show();
				String temps = dlg.getNextDate();
				if (temps != null) {
					dateField.setText(temps);
					// try {
					// ((DateDocument)
					// dateField.getDocument()).isSetValue(true);
					// ((DateDocument) dateField.getDocument()).insertString(0,
					// temps, null);
					// ((DateDocument)
					// dateField.getDocument()).isSetValue(false);
					// } catch (Exception ex) {
					// ex.printStackTrace();
					// }
				}
				// �õ���ֵ
				Object newValue = getValue();
				// ����ValueChange�¼�
				fireValueChange(oldValue, newValue);
				//
			}
		});
		Box panelDate = Box.createHorizontalBox();

		Border border = dateField.getBorder();

		dateField.setBorder(null);
		panelDate.add(dateField);
		panelDate.add(btnTime);

		panelDate.setBorder(border);
		return panelDate;
	}

	/**
	 * �������ÿؼ���ֵ����
	 * 
	 * @param value
	 *            Ҫ���ÿؼ���ֵ
	 */
	public void setValue(Object value) {

		refModel.setFormat(format);
		this.dateField.setText(value.toString());

	}

	/**
	 * �õ��ؼ���ֵ����
	 * 
	 * @return �ؼ�ֵ����
	 */
	public Object getValue() {
		if (this.dateField.getText() == null
				|| this.dateField.getText().equals("")) {
			return null;
		} else {
			try {
				// refModel.setFormat(format);
				// Object[] object =
				// refModel.getValueByName(this.dateField.getText());
				// return object[0];
				return this.dateField.getText();
			} catch (Exception ex) {
				return null;
			}
		}
	}

	/**
	 * ���ÿؼ��Ƿ�ɱ༭�����ڸ���JComponent�޴˷�������˽�������
	 * 
	 * @param editable
	 *            falseΪ���ɱ༭��trueΪ�ɱ༭
	 */
	public void setEditable(boolean editable) {
		this.dateField.setEditable(editable);
	}

	/**
	 * �жϿؼ��Ƿ���Ա༭
	 * 
	 * @return falseΪ���ɱ༭��trueΪ�ɱ༭
	 */
	public boolean isEditable() {
		return this.dateField.isEditable();
	}

	/**
	 * ͬʱ���������Ͱ�ťΪ���û򲻿���
	 * 
	 * @param enabled
	 *            falseΪ�����ã�trueΪ����
	 */
	public void setEnabled(boolean enabled) {
		this.dateField.setEditable(enabled);
		this.btnTime.setEnabled(enabled);

	}

}
