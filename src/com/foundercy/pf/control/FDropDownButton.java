/*
 * $Id: FDropDownButton.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.metal.MetalComboBoxIcon;
import javax.swing.plaf.metal.MetalLookAndFeel;

import com.foundercy.pf.util.Resource;

/**
 * <p>
 * Title: ������ϰ�ť
 * </p>
 * <p>
 * Description:������ϰ�ť
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * <p>
 * Company: ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * 
 * @author �ƽ� 2008��4��15������
 * @version $Revision: 1.1.1.1 $
 */
public class FDropDownButton extends JToolBar implements Control {
	private static final long serialVersionUID = -9045229720413137507L;

	private String id;

	private Control parent;

	private JButton mainButton;

	private JButton arrowButton;

	private JPopupMenu pop = new JPopupMenu();

	private JMenuItem[] subItems = null;

	/**
	 * ���캯��
	 */
	public FDropDownButton() {
		this(new JButton());
	}

	/**
	 * ���캯��
	 * 
	 * @param button
	 *            �������ذ���
	 */
	public FDropDownButton(JButton button) {
		mainButton = button;
		mainButton.setIconTextGap(0);
		mainButton.setMargin(new Insets(0, 0, 0, 0));
		// mainButton.setBorder(null);

		arrowButton = new JButton(new MyMetalComboBoxIcon());
		// Insets insets = arrowButton.getMargin();
		// arrowButton.setMargin(new Insets(insets.top, 1, insets.bottom, 1));
		// arrowButton.setBorder(null);

		arrowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dropDownButton_actionPerformed(e);
			}
		});

		pop.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				// arrowButton.setBorder(CoolButtonMouseListener.PRESSED_BORDER);
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// arrowButton.setBorder(null);
			}

			public void popupMenuCanceled(PopupMenuEvent e) {
				// TODO Auto-generated method stub
			}
		});
		this.setLayout(null);
		this.setFloatable(false);
		this.setRollover(true);
		add(mainButton);
		add(arrowButton);

	}

	/**
	 * �ƶ��������»��ƿؼ�
	 * 
	 * @param x
	 *            ��ʼˮƽλ��
	 * @param y
	 *            ��ʼ��ֱλ��
	 * @param w
	 *            ���
	 * @param h
	 *            �߶�
	 * 
	 */
	public void reshape(int x, int y, int w, int h) {
		super.reshape(x, y, w, h);
		mainButton.setBounds(0, 0, w - 12, h);
		arrowButton.setBounds(w - 12, 0, 12, h);
	}

	/**
	 * ����������ť
	 * 
	 * @param subItems
	 *            ������ť�б�
	 */
	public void setSubItems(JMenuItem[] subItems) {
		this.subItems = subItems;
		//
		for (int i = 0; i < subItems.length; i++) {
			if (subItems[i].getText().equalsIgnoreCase("separator"))
				pop.addSeparator();
			else
				pop.add(subItems[i]);
		}
	}

	/**
	 * �õ���������ť
	 * 
	 * @return ������ť�б�
	 */
	public JMenuItem[] getSubItems() {
		return this.subItems;
	}

	/**
	 * ������ť�¼�
	 * 
	 * @param evt
	 *            ��ť�¼�
	 */
	private void dropDownButton_actionPerformed(ActionEvent evt) {
		if (this.subItems != null && this.subItems.length > 0) {
			pop.show(arrowButton, arrowButton.getWidth()
					- pop.getPreferredSize().width, arrowButton.getHeight());
		}
	}

	/**
	 * ��Ӳ�������
	 * 
	 * @param al
	 *            ��������
	 */
	public void addActionListener(ActionListener al) {
		this.mainButton.addActionListener(al);
	}

	/**
	 * ���ð�ťͼ��
	 * 
	 * @param iconFile
	 *            ��ťͼ����
	 */
	public void setIcon(String iconFile) {
		this.mainButton.setIcon(Resource.getImage(iconFile));
		this.mainButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		this.mainButton.setHorizontalTextPosition(SwingConstants.CENTER);
	}

	/**
	 * �õ���ť����
	 * 
	 * @return ��ť����
	 */
	public String getTitle() {
		return this.mainButton.getText();
	}

	/**
	 * ���ð�ťtitle
	 * 
	 * @param title
	 *            ��ť����
	 */
	public void setTitle(String title) {
		this.mainButton.setText(title);
	}

	/**
	 * �õ���ť����
	 * 
	 * @return ��ť�������
	 */
	public Font getTitleFont() {
		return this.mainButton.getFont();
	}

	/**
	 * ����ToolTipText
	 * 
	 * @param text
	 *            ��ť��ʾ��Ϣ
	 */
	public void setToolTipText(String text) {
		this.mainButton.setToolTipText(text);
	}

	/**
	 * �õ��ؼ�id
	 * 
	 * @return �ؼ�id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * ���ÿؼ�id
	 * 
	 * @param id
	 *            �ؼ�id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * �õ������ÿؼ�����һ��ؼ�
	 * 
	 * @return �ؼ�����
	 */
	public Control getParentControl() {
		return this.parent;
	}

	/**
	 * ������һ��ؼ�
	 * 
	 * @param parent
	 *            �ؼ�����
	 */
	public void setParentControl(Control parent) {
		this.parent = parent;
	}

	/**
	 * �Զ�������ͼ��
	 * 
	 */
	class MyMetalComboBoxIcon extends MetalComboBoxIcon {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1771210107599091235L;

		/**
		 * ����ͼƬ
		 * 
		 * @param c
		 *            �ؼ�����
		 * @param g
		 *            ��ͼ����
		 * @param x
		 *            ��ʼˮƽλ��
		 * @param y
		 *            ��ʼ��ֱλ��
		 * 
		 */
		public void paintIcon(Component c, Graphics g, int x, int y) {
			JComponent component = (JComponent) c;
			g.setColor(component.isEnabled() ? MetalLookAndFeel
					.getControlInfo() : MetalLookAndFeel.getControlShadow());
			g.drawLine(x + 1, y + 3, x + 7, y + 3);
			g.drawLine(x + 2, y + 4, x + 6, y + 4);
			g.drawLine(x + 3, y + 5, x + 5, y + 5);
			g.drawLine(x + 4, y + 6, x + 4, y + 6);
		}

		/**
		 * ��ȡͼƬ���
		 * 
		 * @return ͼƬ���
		 * 
		 */
		public int getIconWidth() {
			return 10;
		}

		/**
		 * ��ȡͼƬ�߶�
		 * 
		 * @return ͼƬ�߶�
		 * 
		 */
		public int getIconHeight() {
			return 5;
		}
	}

}
