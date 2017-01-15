/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.foundercy.pf.control.table;

/**
 *
 * @author wangzhenhua
 */
public interface ITableCellEditable {
    // 用于控制网格单元的可编辑性，
    // 0 : 默认行为 -1 : 不能编辑 1 : 能编辑
    public int getCellEditable(Object obj, String fileName);
}
