package com.foundercy.pf.control.table;

import javax.swing.table.TableModel;

/**
 * 合计行合计算法接口
 * @author jerry
 */
public interface ISumRowCount {
	
	/**
	 * 小记算法
	 * @param tableModel 表格模型
	 * @param columnIndex 列索引
	 */
	public Number partSumRowCount(TableModel tableModel, int columnIndex);
	
}
