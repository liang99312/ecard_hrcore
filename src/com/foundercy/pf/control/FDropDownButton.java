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
 * Title: 下拉组合按钮
 * </p>
 * <p>
 * Description:下拉组合按钮
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 北京方正春元科技发展有限公司
 * </p>
 * <p>
 * Company: 北京方正春元科技发展有限公司
 * </p>
 * 
 * @author 黄节 2008年4月15日整理
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
	 * 构造函数
	 */
	public FDropDownButton() {
		this(new JButton());
	}

	/**
	 * 构造函数
	 * 
	 * @param button
	 *            设置主控按键
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
	 * 移动或者重新绘制控件
	 * 
	 * @param x
	 *            起始水平位置
	 * @param y
	 *            起始垂直位置
	 * @param w
	 *            宽度
	 * @param h
	 *            高度
	 * 
	 */
	public void reshape(int x, int y, int w, int h) {
		super.reshape(x, y, w, h);
		mainButton.setBounds(0, 0, w - 12, h);
		arrowButton.setBounds(w - 12, 0, 12, h);
	}

	/**
	 * 设置下拉按钮
	 * 
	 * @param subItems
	 *            下拉按钮列表
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
	 * 得到子下拉按钮
	 * 
	 * @return 下拉按钮列表
	 */
	public JMenuItem[] getSubItems() {
		return this.subItems;
	}

	/**
	 * 下拉按钮事件
	 * 
	 * @param evt
	 *            按钮事件
	 */
	private void dropDownButton_actionPerformed(ActionEvent evt) {
		if (this.subItems != null && this.subItems.length > 0) {
			pop.show(arrowButton, arrowButton.getWidth()
					- pop.getPreferredSize().width, arrowButton.getHeight());
		}
	}

	/**
	 * 添加操作监听
	 * 
	 * @param al
	 *            操作监听
	 */
	public void addActionListener(ActionListener al) {
		this.mainButton.addActionListener(al);
	}

	/**
	 * 设置按钮图标
	 * 
	 * @param iconFile
	 *            按钮图标名
	 */
	public void setIcon(String iconFile) {
		this.mainButton.setIcon(Resource.getImage(iconFile));
		this.mainButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		this.mainButton.setHorizontalTextPosition(SwingConstants.CENTER);
	}

	/**
	 * 得到按钮标题
	 * 
	 * @return 按钮标题
	 */
	public String getTitle() {
		return this.mainButton.getText();
	}

	/**
	 * 设置按钮title
	 * 
	 * @param title
	 *            按钮标题
	 */
	public void setTitle(String title) {
		this.mainButton.setText(title);
	}

	/**
	 * 得到按钮字体
	 * 
	 * @return 按钮字体对象
	 */
	public Font getTitleFont() {
		return this.mainButton.getFont();
	}

	/**
	 * 设置ToolTipText
	 * 
	 * @param text
	 *            按钮提示信息
	 */
	public void setToolTipText(String text) {
		this.mainButton.setToolTipText(text);
	}

	/**
	 * 得到控件id
	 * 
	 * @return 控件id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 设置控件id
	 * 
	 * @param id
	 *            控件id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 得到包含该控件的上一层控件
	 * 
	 * @return 控件对象
	 */
	public Control getParentControl() {
		return this.parent;
	}

	/**
	 * 设置上一层控件
	 * 
	 * @param parent
	 *            控件对象
	 */
	public void setParentControl(Control parent) {
		this.parent = parent;
	}

	/**
	 * 自定义下拉图标
	 * 
	 */
	class MyMetalComboBoxIcon extends MetalComboBoxIcon {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1771210107599091235L;

		/**
		 * 绘制图片
		 * 
		 * @param c
		 *            控件对象
		 * @param g
		 *            绘图对象
		 * @param x
		 *            起始水平位置
		 * @param y
		 *            起始垂直位置
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
		 * 获取图片宽度
		 * 
		 * @return 图片宽度
		 * 
		 */
		public int getIconWidth() {
			return 10;
		}

		/**
		 * 获取图片高度
		 * 
		 * @return 图片高度
		 * 
		 */
		public int getIconHeight() {
			return 5;
		}
	}

}
