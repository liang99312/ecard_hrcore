package com.foundercy.pf.control.table;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.foundercy.pf.control.Control;
import com.foundercy.pf.control.table.FBaseTableHeaderRenderer;
import com.foundercy.pf.control.Compound;
/**
 * <p>
 * Title: 多表头
 * </p>
 * <p>
 * Description: 负责多表头的构建
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 北京方正春元科技发展有限公司
 * </p>
 * <p>
 * Company: 北京方正春元科技发展有限公司
 * </p>
 * 
 * @author linping
 * @author 黄节 2008年4月14日整理
 * @version 1.0
 */
public class ColumnGroup implements Control, Compound {

	private FBaseTableHeaderRenderer renderer; // 表头显示方式

	protected Vector v; // v向量保存表列

	private String id; // 多表头列id

	private String title; // 标题

	private Control parentControl; // 父control控件

	private int margin = 0; // 列间距

	/**
	 * 多表头构造函数
	 */
	public ColumnGroup() {
		// 如果renderer为空，则renderer采用FBaseTableHeaderRenderer渲染器
		if (renderer == null) {
			this.renderer = new FBaseTableHeaderRenderer();

		}
		v = new Vector();
	}

	/**
	 * 带列名的多表头构造函数
	 * 
	 * @param title
	 *            列名
	 */
	public ColumnGroup(String title) {
		this(null, title);
	}

	/**
	 * 设置列内容和表格显示方式
	 * 
	 * @param renderer
	 *            表格显示方式
	 * @param title
	 *            列内容
	 */
	public ColumnGroup(TableCellRenderer renderer, String title) {
		// 如果renderer为空，则采用FBaseTableHeaderRenderer
		if (renderer == null) {
			this.renderer = new FBaseTableHeaderRenderer();

		} else { // 如果renderer不为空，则采用指定的renderer
			this.renderer = (FBaseTableHeaderRenderer) renderer;
		}
		this.title = title;
		v = new Vector();
	}

	/**
	 * 取得多表头列
	 * 
	 * @param c
	 *            表列
	 * @param g
	 *            Vector
	 * @return 多表头列vector
	 */
	public Vector getColumnGroups(TableColumn c, Vector g) {
		// 将当前列加入到g向量中
		g.addElement(this);
		// 如果v向量中已包含c列，则返回g向量
		if (v.contains(c))
			return g;
		Enumeration enumeration = v.elements();
		// 读取v向量
		while (enumeration.hasMoreElements()) {
			Object obj = enumeration.nextElement();
			// 如果列为多表头列
			if (obj instanceof ColumnGroup) {
				Vector groups = (Vector) ((ColumnGroup) obj).getColumnGroups(c,
						(Vector) g.clone());
				// 如果多表头列不为空，则返回该多表头列
				if (groups != null)
					return groups;
			}
		}
		return null;
	}

	/**
	 * 取得表头单元格画布
	 * 
	 * @return 表头单元格画布
	 */
	public TableCellRenderer getHeaderRenderer() {
		return renderer;
	}

	/**
	 * 取得表头标题
	 * 
	 * @return 表头标题
	 */
	public Object getHeaderValue() {
		return title;
	}

	/**
	 * 取得子元素个数
	 * 
	 * @return 子元素个数
	 */
	public int getSize() {
		// 如果v向量为空则返回0，否则返回v向量大小
		return v == null ? 0 : v.size();
	}

	/**
	 * 取得表格大小
	 * 
	 * @param table
	 *            表
	 * @return 表格大小
	 */
	public Dimension getSize(JTable table) {
		// 取得表格的控件
		Component comp = renderer.getTableCellRendererComponent(table,
				getHeaderValue(), false, false, -1, -1);
		// 取得表格高度
		int height = comp.getPreferredSize().height;
		// 表格宽度
		int width = 0;
		Enumeration enumeration = v.elements();
		// 读取v向量内容
		while (enumeration.hasMoreElements()) {
			Object obj = enumeration.nextElement();
			// 如果是表列
			if (obj instanceof TableColumn) {
				TableColumn aColumn = (TableColumn) obj;
				// 表宽等于各列宽加列间隔
				width += aColumn.getWidth();
				width += margin;
			} else {
				// 如果是多表头列，表宽加上多表头列宽
				width += ((ColumnGroup) obj).getSize(table).width;
			}
		}

		// 返回表格大小
		return new Dimension(width, height);
	}

	/**
	 * 在多表头中删除Column
	 * 
	 * @param ptg
	 *            多表头
	 * @param tc
	 *            表列
	 * @return true:删除成功 false：删除失败
	 */
	public boolean removeColumn(ColumnGroup ptg, TableColumn tc) {
		// 删除结果标志
		boolean retFlag = false;
		// 多表头列所含列数
		int size = ptg.v.size();
		// 如果表列不为空
		if (tc != null) {
			// 读取多表头列所含表列
			for (int i = 0; i < size; i++) {
				Object tmpObj = ptg.v.get(i);
				if (tmpObj instanceof ColumnGroup) {
					retFlag = removeColumn((ColumnGroup) tmpObj, tc);
					// 如果找到返回
					if (retFlag)
						break;
				} else if (tmpObj instanceof TableColumn) {
					// 判断是否查找的对象
					if (tmpObj == tc) {
						ptg.v.remove(i);
						retFlag = true;
						break;
					}
				}
			}
		}
		return retFlag;
	}

	/**
	 * 列表头中删除ColumnGrp
	 * 
	 * @param ptg
	 *            多表头
	 * @param tg
	 *            多表头
	 * @return 删除成功返回true,否则返回false
	 */
	public boolean removeColumnGrp(ColumnGroup ptg, ColumnGroup tg) {
		boolean retFlag = false;
		if (tg != null) {
			// 多表头列所含列数
			int size = ptg.v.size();
			// 循环读取v中元素
			for (int i = 0; i < size; i++) {
				Object tmpObj = ptg.v.get(i);
				// 如果是ColumnGroup类型
				if (tmpObj instanceof ColumnGroup) {
					// 判断是否查找的对象
					if (tmpObj == tg) {
						ptg.v.remove(i);
						retFlag = true;
						break;
					} else {
						retFlag = removeColumnGrp((ColumnGroup) tmpObj, tg);
						// 如果找到返回
						if (retFlag)
							break;
					}
				}
				// 如果是TableColumn类型
				else if (tmpObj instanceof TableColumn) {
					break;
				}
			}
		}
		return retFlag;
	}

	/**
	 * 设置列边距
	 * 
	 * @param margin
	 *            列边距
	 */
	public void setColumnMargin(int margin) {
		this.margin = margin;
		Enumeration enumeration = v.elements();
		// 设置每列间距
		while (enumeration.hasMoreElements()) {
			Object obj = enumeration.nextElement();
			// 如果为ColumnGroup类型
			if (obj instanceof ColumnGroup) {
				((ColumnGroup) obj).setColumnMargin(margin);
			}
		}
	}

	/**
	 * 设置表格显示方式
	 * 
	 * @param renderer
	 *            表格显示方式
	 */
	public void setHeaderRenderer(TableCellRenderer renderer) {
		// 如果renderer不为空，则设置表格显示方式为renderer
		if (renderer != null) {
			this.renderer = (FBaseTableHeaderRenderer) renderer;
		}
	}

	/**
	 * 取得所有子控件
	 * 
	 * @return 所有子控件
	 */
	public List getSubControls() {
		// 如果v为空，则返回
		if (v == null) {
			return null;
		}

		ArrayList list = new ArrayList();
		int size = v.size(); // v元素个数
		// 循环读取v中包含的控件
		for (int i = 0; i < size; i++) {
			Control control = (Control) v.get(i);
			// 将控件加入到list中
			list.add(control);
		}

		// 返回所有子控件
		return list;
	}

	/**
	 * 取得子控件
	 * 
	 * @param id
	 *            子控件id
	 * @return 子控件
	 */
	public Control getSubControl(String id) {
		// 如果id或v为空，则返回空
		if (id == null || v == null)
			return null;

		Object obj = null;
		int size = v.size(); // v元素个数
		// 循环读取v元素
		for (int i = 0; i < size; i++) {
			obj = v.get(i);
			// 如果元素为FBaseTableColumn类型
			if (obj instanceof FBaseTableColumn) {
				FBaseTableColumn col = (FBaseTableColumn) obj;
				// 如果元素id与参数id相等，则返回
				if (id.equalsIgnoreCase((String) col.getIdentifier())) {
					return col;
				}
			}
			// 如果元素为ColumnGroup类型
			else if (obj instanceof ColumnGroup) {
				ColumnGroup columnGroup = (ColumnGroup) obj;
				// 如果元素id与参数id相等，则返回
				if (id.equalsIgnoreCase(columnGroup.getId())) {
					return columnGroup;
				}
			}
		}

		return null;
	}

	/**
	 * 增加控件，控件可以是column或者ColumnGroup
	 * 
	 * @param control
	 *            控件
	 */
	public void addControl(Control control) {
		// 如果control为空，则返回
		if (control == null) {
			return;
		}

		// 将control加入到v向量中
		v.addElement(control);

		// 设置父控件
		control.setParentControl(this);
	}

	/**
	 * 增加控件，控件可以是column或者ColumnGroup
	 * 
	 * @param control
	 *            控件
	 * @param contraint
	 *            约束
	 */
	public void addControl(Control control, Object contraint) {
	}

	/**
	 * 取得id
	 * 
	 * @return id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 得到列内容
	 * 
	 * @return 列内容
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置列内容
	 * 
	 * @param title
	 *            列内容
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 取得父control控件
	 * 
	 * @return 父control控件
	 */
	public Control getParentControl() {
		return parentControl;
	}

	/**
	 * 设置父control控件
	 * 
	 * @param parentControl
	 *            父control控件
	 */
	public void setParentControl(Control parentControl) {
		this.parentControl = parentControl;
	}

}
