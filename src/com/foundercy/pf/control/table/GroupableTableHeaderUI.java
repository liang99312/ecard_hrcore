package com.foundercy.pf.control.table;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.foundercy.pf.control.table.FBaseTableCellRenderer;
import com.foundercy.pf.control.table.FBaseTableHeaderRenderer;


/**
 * 多表头TableHeaderUI
 * @author linping
 * @date 2008-02-20
 */
public class GroupableTableHeaderUI extends BasicTableHeaderUI {
	private int m_height;// 为指定的高度、用于锁定的表

	private int maxHeight = 0;// 记录最大高度

	/**
	 * 设置多表头高度
	 * @param height 多表头高度
	 */
	public GroupableTableHeaderUI(int height) {
		if (height != -1)
			m_height = height;
	}

	/**
	 * 创建表头
	 * @param width 多表头高度
	 * @return 多表头大小
	 */
	private Dimension createHeaderSize(long width) {
		//取得表头列模型
		TableColumnModel columnModel = header.getColumnModel();
		//表头宽度加上所有列间距
		width += columnModel.getColumnMargin() * columnModel.getColumnCount();
		//如果表宽大于Integer最大值，则设置表宽等于Integer.MAX_VALUE
		if (width > Integer.MAX_VALUE) {
			width = Integer.MAX_VALUE;
		}
		//取得表头高度
		int iHeight = getHeaderHeight();
//		如果表高大于maxHeight，则设置表高等于maxHeight
		if (iHeight > maxHeight){
			maxHeight = iHeight;
		}

		return new Dimension((int) width, getHeaderHeight());
	}

	/**
	 * 获得该多表头下的基本列数, 递归
	 * @param cg 多表头
	 * @param iCount 个数
	 * @return 多表头下的基本列数
	 */
	private int getColCountUnderColGroup(ColumnGroup cg, int iCount) {
		Vector v = cg.v;
		for (int i = 0; i < v.size(); i++) {
			Object obj = v.elementAt(i);
			if (obj instanceof ColumnGroup)
				iCount = getColCountUnderColGroup((ColumnGroup) obj, iCount);
			else
				iCount++;
		}
		return iCount;
	}

	// 取得总共的高度
	public int getHeaderHeight() {
		int height = 0;
		TableColumnModel columnModel = header.getColumnModel();
		for (int column = 0; column < columnModel.getColumnCount(); column++) {
			TableColumn aColumn = columnModel.getColumn(column);
			TableCellRenderer renderer= aColumn.getHeaderRenderer();
			//
			if (renderer == null) {
				renderer = new FBaseTableCellRenderer();
				// {
				// public Component getTableCellRendererComponent(
				// JTable table, Object value, boolean isSelected,
				// boolean hasFocus, int row, int column) {
				// JTableHeader header = table.getTableHeader();
				// if (header != null) {
				// setForeground(header.getForeground());
				// setBackground(header.getBackground());
				// setFont(header.getFont());
				// }
				// setHorizontalAlignment(JLabel.CENTER);
				// setText((value == null) ? "" : value.toString());
				// Color c = new JLabel().getBackground();
				// setBorder(BorderFactory.createBevelBorder(
				// javax.swing.border.BevelBorder.RAISED, c, c
				// .darker()));
				// return this;
				// }
				// };
			}
			//
			Component comp = renderer.getTableCellRendererComponent(header
					.getTable(), aColumn.getHeaderValue(), false, false, -1,
					column);
			int cHeight = comp.getPreferredSize().height;
			Enumeration enumeration = ((GroupableTableHeader) header)
					.getColumnGroups(aColumn);
			if (enumeration != null) {
				while (enumeration.hasMoreElements()) {
					ColumnGroup cGroup = (ColumnGroup) enumeration
							.nextElement();
					cHeight += cGroup.getSize(header.getTable()).height;
				}
			}
			height = Math.max(height, cHeight);
		}
		height = Math.max(height, m_height);
		if (height > maxHeight)
			maxHeight = height;
		return height;
	}

	public Dimension getPreferredSize(JComponent c) {
		long width = 0;
		Enumeration enumeration = header.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			width = width + aColumn.getWidth();
		}

		return createHeaderSize(width);
	}

	public void paint(Graphics g, JComponent c) {
		Rectangle clipBounds = g.getClipBounds();
		if (header.getColumnModel() == null)
			return;
		((GroupableTableHeader) header).setColumnMargin();
		int column = 0;
		Dimension size = header.getSize();
		Rectangle cellRect = new Rectangle(0, 0, size.width, size.height);
		Hashtable h = new Hashtable();
		int columnMargin = header.getColumnModel().getColumnMargin();
		Enumeration enumeration = header.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			cellRect.height = size.height;
			cellRect.y = 0;
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			Enumeration cGroups = ((GroupableTableHeader) header)
					.getColumnGroups(aColumn);
			if (cGroups != null) {
				int groupHeight = 0;
				while (cGroups.hasMoreElements()) {
					ColumnGroup cGroup = (ColumnGroup) cGroups.nextElement();
					Rectangle groupRect = (Rectangle) h.get(cGroup);
					if (groupRect == null) {
						groupRect = new Rectangle(cellRect);
						Dimension d = cGroup.getSize(header.getTable());
						if (!System.getProperty("java.vm.version").startsWith(
								"1.2")) {
							int iColCount = getColCountUnderColGroup(cGroup, 0); // 获得该多表头下的基本列数(zjb)
							// System.out.println(iColCount);
							groupRect.width = d.width - iColCount
									* columnMargin;
						} else
							groupRect.width = d.width;
						groupRect.height = d.height;
						h.put(cGroup, groupRect);
					}
					paintCell(g, groupRect, cGroup);
					groupHeight += groupRect.height;
					cellRect.height = size.height - groupHeight;
					cellRect.y = groupHeight;
				}
			}
			if (!System.getProperty("java.vm.version").startsWith("1.2"))
				cellRect.width = aColumn.getWidth();
			else
				cellRect.width = aColumn.getWidth() + columnMargin;

			if (cellRect.intersects(clipBounds)) {
				paintCell(g, cellRect, column);
			}
			cellRect.x += cellRect.width;
			column++;
		}
	}

	private void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
		TableColumn aColumn = header.getColumnModel().getColumn(columnIndex);
		TableCellRenderer renderer = aColumn.getHeaderRenderer();
		//
		if (renderer == null) {
			renderer = new FBaseTableHeaderRenderer();
			// {
			// public Component getTableCellRendererComponent(JTable table,
			// Object value, boolean isSelected, boolean hasFocus,
			// int row, int column) {
			// JTableHeader header = table.getTableHeader();
			// if (header != null) {
			// setForeground(header.getForeground());
			// setBackground(header.getBackground());
			// setFont(header.getFont());
			// }
			// setHorizontalAlignment(JLabel.CENTER);
			// setText((value == null) ? "" : value.toString());
			// Color c = new JLabel().getBackground();
			// setBorder(BorderFactory.createBevelBorder(
			// javax.swing.border.BevelBorder.RAISED, c, c
			// .darker()));
			// return this;
			// }
			// };
		}
		//
		// Component component =
		// renderer.getTableCellRendererComponent(header.getTable(),
		// aColumn.getHeaderValue(), false, false, -1, columnIndex);
		//
		String headerValue = aColumn.getHeaderValue().toString();
		Component component = renderer.getTableCellRendererComponent(header
				.getTable(), headerValue, false, false, -1, columnIndex);

		rendererPane.add(component);
		rendererPane.paintComponent(g, component, header, cellRect.x,
				cellRect.y, cellRect.width, cellRect.height, true);
	}

	private void paintCell(Graphics g, Rectangle cellRect, ColumnGroup cGroup) {
		TableCellRenderer renderer = cGroup.getHeaderRenderer();
		//
		if (renderer == null) {
			renderer = new FBaseTableHeaderRenderer();
			/*
			 * { public Component getTableCellRendererComponent(JTable table,
			 * Object value, boolean isSelected, boolean hasFocus, int row, int
			 * column) { JTableHeader header = table.getTableHeader(); if
			 * (header != null) { setForeground(header.getForeground());
			 * setBackground(header.getBackground()); setFont(header.getFont()); }
			 * setHorizontalAlignment(JLabel.CENTER); setText((value == null) ? "" :
			 * value.toString()); Color c = new JLabel().getBackground();
			 * setBorder(BorderFactory.createBevelBorder(
			 * javax.swing.border.BevelBorder.RAISED, c, c .darker())); return
			 * this; } };
			 */
		}
		//
		// Component component =
		// renderer.getTableCellRendererComponent(header.getTable(),
		// cGroup.getHeaderValue(), false, false, -1, -1);
		//
		String headerValue = cGroup.getHeaderValue().toString();
		Component component = renderer.getTableCellRendererComponent(header
				.getTable(), headerValue, false, false, -1, -1);

		rendererPane.add(component);
		rendererPane.paintComponent(g, component, header, cellRect.x,
				cellRect.y, cellRect.width, cellRect.height, true);
	}

	/**
	 * 设置表头高度
	 * @param iHeight 表头高度
	 */
	public void setHeaderHeight(int iHeight) {
		m_height = iHeight;
	}

	/**
	 * 取得表头最大高度
	 * @return 表头最大高度
	 */
	public int getMaxHeight() {
		getHeaderHeight();
		return maxHeight;
	}

	/**
	 * 设置表头最大高度
	 * @param maxHeight 表头最大高度
	 */
	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}
}
