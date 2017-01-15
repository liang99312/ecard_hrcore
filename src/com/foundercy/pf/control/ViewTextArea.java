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
 * Title: 文本预览控件
 * </p>
 * <p>
 * Description: 文本预览控件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 北京方正春元科技发展有限公司
 * </p>
 * <p>
 * Company: 北京方正春元科技发展有限公司
 * </p>
 * 
 * @author 黄节 2008年4月14日整理
 * @version 1.0
 */
public class ViewTextArea extends AbstractDataField {
	private static final long serialVersionUID = 1L;

	private JTextField dataField;

	private JButton btnTree = null;

	private JScrollPane scrollPane;

	/**
	 * 构造函数
	 */
	public ViewTextArea() {
		allLayout();
	}

	/**
	 * 立即设置控件的值，需要子类实现。 子类在实现时，不必考虑当前线程，是否Swing事件处理线程
	 * 
	 * @param value
	 *            设置的值
	 */
	public void setValue(Object value) {
		if (value == null) {
			dataField.setText(null);
		} else {
			dataField.setText(value.toString());
		}
	}

	/**
	 * 得到控件的值
	 * 
	 * @return 控件的值
	 */
	public Object getValue() {
		return dataField.getText();
	}

	/**
	 * 立即设置控件是否可用。
	 * 
	 * @param enabled
	 *            控件是否可用
	 */
	public void setEnabled(boolean enabled) {
		this.dataField.setEnabled(enabled);
		this.btnTree.setEnabled(enabled);
	}

	/**
	 * 取得控件是否可用
	 * 
	 * @return true，可用；false，不可用
	 */
	public boolean isEnabled() {
		return this.dataField.isEnabled();
	}

	/**
	 * 设置焦点为当前的控件
	 */
	protected void setFocusNow() {
		btnTree.requestFocus();
	}

	/**
	 * 得到当前实际操作的控件
	 * 
	 * @return 控件对象
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
				dialog.setTitle("详细信息");
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
