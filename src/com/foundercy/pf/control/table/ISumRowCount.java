package com.foundercy.pf.control.table;

import javax.swing.table.TableModel;

/**
 * �ϼ��кϼ��㷨�ӿ�
 * @author jerry
 */
public interface ISumRowCount {
	
	/**
	 * С���㷨
	 * @param tableModel ���ģ��
	 * @param columnIndex ������
	 */
	public Number partSumRowCount(TableModel tableModel, int columnIndex);
	
}
