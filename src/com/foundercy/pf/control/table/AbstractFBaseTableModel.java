package com.foundercy.pf.control.table;


import javax.swing.table.AbstractTableModel;
/**
 * <p>Title:表格类的抽象模型。 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author fangyi
 * @version 1.0
 */

public abstract class AbstractFBaseTableModel extends AbstractTableModel {

  public AbstractFBaseTableModel() {
  }
  /**
   * 写入数据
   * @param value
   * @param rowIndex
   * @param columnIndex
   */
  public void setValueAt(Object value, int rowIndex, int columnIndex) {
    this.setValueAt(value,rowIndex,this.getColumnName(columnIndex));
  }
  /**
   * 读取数据
   * @param rowIndex
   * @param columnIndex
   * @return
   */
  public Object getValueAt(int rowIndex,int columnIndex) {
    return this.getValueAt(rowIndex,this.getColumnName(columnIndex));
  }

  /**
   * 根据列名称和行获得该单元的值
   * @param rowIndex
   * @param columnName
   * @return
   */
  public abstract Object getValueAt(int rowIndex,String columnName);

  /**
   * 留给子类重载
   * @param value
   * @param rowIndex
   * @param columName
   */
  public void setValueAt(Object value, int rowIndex, String columName) {}
  /**
   *
   * @param row
   * @param col
   * @return
   */
  public boolean isCellEditable(int row,int col) {
    return this.isCellEditable(row,this.getColumnName(col));
  }
  /**
   * 留给子类实现，缺省返回true;
   * @param row
   * @param columnName
   * @return
   */
  public abstract boolean isCellEditable(int row,String columnName);
}