package com.foundercy.pf.control.table;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * GroupableTableHeader�̳���JTableHeader��Ϊ���ͷ�ṹ�Ĺ�����
 * @author linping
 * @date 2008-02-20
 */
public class GroupableTableHeader extends JTableHeader {
	private static final long serialVersionUID = -3888170790415293190L;

	public Vector columnGroups = null; //���ͷVector
	
	private int signHeight=-1;//����ָ������ĸ߶�
	
	private GroupableTableHeaderUI aUI; //���ͷUI

	public GroupableTableHeader(TableColumnModel model) {
		super(model);
		aUI=new GroupableTableHeaderUI(signHeight);
		setUI(aUI);
		setReorderingAllowed(false); //��������������
		setRequestFocusEnabled(true); //�����ý���
	}

	/**
	 * ��Ӻϲ���
	 * @param ColumnGroup ���ͷ��
	 */
	public void addColumnGroup(ColumnGroup g) {
		if (columnGroups == null) {
			columnGroups = new Vector();
		}
		// �����ͷ�м��뵽columnGroups��
		columnGroups.addElement(g);
	}

	public void addFBaseTableColumn(FBaseTableColumn col) {
		if (columnGroups == null) {
			columnGroups = new Vector();
		}
		// �����ͷ�м��뵽columnGroups��
		columnGroups.addElement(col);
	}
	
	/**
	 * ���columnGroups
	 */
	public void clearColumnGroups() {
		columnGroups = null;
	}

	/**
	 * �õ�columnGroups����
	 * @return columnGroups����
	 */
	public ColumnGroup[] getColumnGroups() {
		ColumnGroup[] retg = null;
		//���columnGroups��Ϊ��
		if (columnGroups.size() > 0) {
			retg = new ColumnGroup[columnGroups.size()];
			//��columnGroups���������������
			columnGroups.copyInto(retg);
		}
		
		return retg;
	}

	/**
	 * ȡ�������ڶ��ͷ��
	 * @param col ����
	 * @return ���кϲ���Enumeration
	 */
	public Enumeration getColumnGroups(TableColumn col) {
		//���columnGroupsΪ�գ��򷵻�null
		if (columnGroups == null)
			return null;
		Enumeration em = columnGroups.elements();
		//��ȡÿ����ͷ�У����ر������ڶ��ͷ��
		while (em.hasMoreElements()) {
			ColumnGroup cGroup = (ColumnGroup) em.nextElement();
			Vector v_ret = (Vector) cGroup.getColumnGroups(col, new Vector());
			//���v_ret��Ϊ�գ��򷵻�v_ret�����Ԫ��
			if (v_ret != null) {
				return v_ret.elements();
			}
		}
		
		return null;
	}

	/**
	 * �����м��
	 */
	public void setColumnMargin() {
		//���columnGroupsΪ�գ��򷵻�
		if (columnGroups == null)
			return;
		//�õ���ģ�ͼ��
		int columnMargin = getColumnModel().getColumnMargin();
		Enumeration em = columnGroups.elements();
		//����ÿ�����ͷ�м��
		while (em.hasMoreElements()) {
			ColumnGroup cGroup = (ColumnGroup) em.nextElement();
			cGroup.setColumnMargin(columnMargin);
		}
	}

	/**
	 * �����Ƿ�������������
	 */
	public void setReorderingAllowed(boolean b) {
		reorderingAllowed = b;
	}
	/**
	 * �õ�����߶�
	 * @return ����߶�
	 */
	public int getSignHeight() {
		return signHeight;
	}

	/**
	 * ���ñ���߶�
	 * @param signHeight ����߶�
	 */
	public void setSignHeight(int signHeight) {
		this.signHeight = signHeight;
		setUI(new GroupableTableHeaderUI(signHeight));
	}
	
	/**
	 * �õ���ͷ�߶�
	 * @return ��ͷ�߶�
	 */
	public int getHeaderHeight() {
		//�����ͷ��Ϊ�գ��򷵻ر�ͷ���߶�
		if (aUI!=null)
			return aUI.getMaxHeight();
		else //�����ͷΪ�գ��򷵻�-1
			return -1;
	}
}
