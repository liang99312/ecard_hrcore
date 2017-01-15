package com.foundercy.pf.control.table;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * GroupableTableHeader继承自JTableHeader，为多表头结构的管理者
 * @author linping
 * @date 2008-02-20
 */
public class GroupableTableHeader extends JTableHeader {
	private static final long serialVersionUID = -3888170790415293190L;

	public Vector columnGroups = null; //多表头Vector
	
	private int signHeight=-1;//用于指定标题的高度
	
	private GroupableTableHeaderUI aUI; //多表头UI

	public GroupableTableHeader(TableColumnModel model) {
		super(model);
		aUI=new GroupableTableHeaderUI(signHeight);
		setUI(aUI);
		setReorderingAllowed(false); //不允许重新排列
		setRequestFocusEnabled(true); //允许获得焦点
	}

	/**
	 * 添加合并列
	 * @param ColumnGroup 多表头列
	 */
	public void addColumnGroup(ColumnGroup g) {
		if (columnGroups == null) {
			columnGroups = new Vector();
		}
		// 将多表头列加入到columnGroups中
		columnGroups.addElement(g);
	}

	public void addFBaseTableColumn(FBaseTableColumn col) {
		if (columnGroups == null) {
			columnGroups = new Vector();
		}
		// 将多表头列加入到columnGroups中
		columnGroups.addElement(col);
	}
	
	/**
	 * 清空columnGroups
	 */
	public void clearColumnGroups() {
		columnGroups = null;
	}

	/**
	 * 得到columnGroups数组
	 * @return columnGroups数组
	 */
	public ColumnGroup[] getColumnGroups() {
		ColumnGroup[] retg = null;
		//如果columnGroups不为空
		if (columnGroups.size() > 0) {
			retg = new ColumnGroup[columnGroups.size()];
			//将columnGroups拷贝到结果数组中
			columnGroups.copyInto(retg);
		}
		
		return retg;
	}

	/**
	 * 取得列所在多表头列
	 * @param col 表列
	 * @return 所有合并列Enumeration
	 */
	public Enumeration getColumnGroups(TableColumn col) {
		//如果columnGroups为空，则返回null
		if (columnGroups == null)
			return null;
		Enumeration em = columnGroups.elements();
		//读取每个表头列，返回表列所在多表头列
		while (em.hasMoreElements()) {
			ColumnGroup cGroup = (ColumnGroup) em.nextElement();
			Vector v_ret = (Vector) cGroup.getColumnGroups(col, new Vector());
			//如果v_ret不为空，则返回v_ret里面的元素
			if (v_ret != null) {
				return v_ret.elements();
			}
		}
		
		return null;
	}

	/**
	 * 设置列间距
	 */
	public void setColumnMargin() {
		//如果columnGroups为空，则返回
		if (columnGroups == null)
			return;
		//得到列模型间距
		int columnMargin = getColumnModel().getColumnMargin();
		Enumeration em = columnGroups.elements();
		//设置每个多表头列间距
		while (em.hasMoreElements()) {
			ColumnGroup cGroup = (ColumnGroup) em.nextElement();
			cGroup.setColumnMargin(columnMargin);
		}
	}

	/**
	 * 设置是否允许重排序列
	 */
	public void setReorderingAllowed(boolean b) {
		reorderingAllowed = b;
	}
	/**
	 * 得到标题高度
	 * @return 标题高度
	 */
	public int getSignHeight() {
		return signHeight;
	}

	/**
	 * 设置标题高度
	 * @param signHeight 标题高度
	 */
	public void setSignHeight(int signHeight) {
		this.signHeight = signHeight;
		setUI(new GroupableTableHeaderUI(signHeight));
	}
	
	/**
	 * 得到表头高度
	 * @return 表头高度
	 */
	public int getHeaderHeight() {
		//如果表头不为空，则返回表头最大高度
		if (aUI!=null)
			return aUI.getMaxHeight();
		else //如果表头为空，则返回-1
			return -1;
	}
}
