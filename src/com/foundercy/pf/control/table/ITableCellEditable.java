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
    // ���ڿ�������Ԫ�Ŀɱ༭�ԣ�
    // 0 : Ĭ����Ϊ -1 : ���ܱ༭ 1 : �ܱ༭
    public int getCellEditable(Object obj, String fileName);
}
