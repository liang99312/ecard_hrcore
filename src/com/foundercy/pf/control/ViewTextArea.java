package com.foundercy.pf.control;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.foundercy.pf.util.Resource;

/**
 * <p>
 * Title: �ı�Ԥ���ؼ�
 * </p>
 * <p>
 * Description: �ı�Ԥ���ؼ�
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * <p>
 * Company: ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * 
 * @author �ƽ� 2008��4��14������
 * @version 1.0
 */
public class ViewTextArea extends AbstractDataField {
	private static final long serialVersionUID = 1L;

	private JTextField dataField;

	private JButton btnTree = null;

	private JScrollPane scrollPane;

	/**
	 * ���캯��
	 */
	public ViewTextArea() {
		allLayout();
	}

	/**
	 * �������ÿؼ���ֵ����Ҫ����ʵ�֡� ������ʵ��ʱ�����ؿ��ǵ�ǰ�̣߳��Ƿ�Swing�¼������߳�
	 * 
	 * @param value
	 *            ���õ�ֵ
	 */
	public void setValue(Object value) {
		if (value == null) {
			dataField.setText(null);
		} else {
			dataField.setText(value.toString());
		}
	}

	/**
	 * �õ��ؼ���ֵ
	 * 
	 * @return �ؼ���ֵ
	 */
	public Object getValue() {
		return dataField.getText();
	}

	/**
	 * �������ÿؼ��Ƿ���á�
	 * 
	 * @param enabled
	 *            �ؼ��Ƿ����
	 */
	public void setEnabled(boolean enabled) {
		this.dataField.setEnabled(enabled);
		this.btnTree.setEnabled(enabled);
	}

	/**
	 * ȡ�ÿؼ��Ƿ����
	 * 
	 * @return true�����ã�false��������
	 */
	public boolean isEnabled() {
		return this.dataField.isEnabled();
	}

	/**
	 * ���ý���Ϊ��ǰ�Ŀؼ�
	 */
	protected void setFocusNow() {
		btnTree.requestFocus();
	}

	/**
	 * �õ���ǰʵ�ʲ����Ŀؼ�
	 * 
	 * @return �ؼ�����
	 */
	public JComponent getEditor() {
		dataField = new JTextField();
		Dimension d = new Dimension(150, 20);
		dataField.setPreferredSize(d);
		btnTree = new JButton(Resource.getImage("tree.gif"));
		Dimension treeSelecte = new Dimension(20, 20);
		btnTree.setPreferredSize(treeSelecte);
		btnTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JDialog dialog = new JDialog();
				dialog.setTitle("��ϸ��Ϣ");
				dialog.setSize(300, 200);
				dialog.setModal(true);
				JTextArea area = new JTextArea();
				scrollPane = new JScrollPane(area,
						JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				area.setLineWrap(true);
				area.setText(dataField.getText());
				area.addFocusListener(new FocusAdapter() {
					public void focusLost(FocusEvent e) {
						JTextArea textArea = (JTextArea) e.getSource();
						dataField.setText(textArea.getText());
					}
				});
				dialog.getContentPane().add(scrollPane);
				Dimension screenSize = Toolkit.getDefaultToolkit()
						.getScreenSize();
				Dimension frameSize = dialog.getSize();
				if (frameSize.height > screenSize.height) {
					frameSize.height = screenSize.height;
				}
				if (frameSize.width > screenSize.width) {
					frameSize.width = screenSize.width;
				}
				dialog.setLocation((screenSize.width - frameSize.width) / 2,
						(screenSize.height - frameSize.height) / 2);
				dialog.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
					}
				});
				dialog.setVisible(true);
			}
		});
		Box panelData = Box.createHorizontalBox();
		panelData.add(dataField);
		panelData.add(btnTree);
		return panelData;
	}

}
