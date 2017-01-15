package com.foundercy.pf.control.table;


import java.util.HashMap;
import java.util.Vector;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableColumnModel;

/**
 * <p>Title: �����ģ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author fangyi
 * @version 1.0
 */

public class FBaseTableColumnModel {

	/**
	 * 
	 * @uml.property name="compounds"
	 * @uml.associationEnd elementType="xingx.smartbiz.control.table.NLTableColumn" multiplicity=
	 * "(0 -1)"
	 */
	//�洢�еİ�����ϵ���������ձ���С�
	protected Vector compounds = new Vector();

	/**
	 * 
	 * @uml.property name="rootToLeaveHash"
	 * @uml.associationEnd qualifier="root:xingx.smartbiz.control.table.NLTableColumn
	 * xingx.smartbiz.control.table.NLTableColumn" multiplicity="(0 1)"
	 */
	private HashMap rootToLeaveHash = new HashMap();

	/**
	 * 
	 * @uml.property name="leaveToRootHash"
	 * @uml.associationEnd qualifier="col:xingx.smartbiz.control.table.NLTableColumn xingx.smartbiz.control.table.NLTableColumn"
	 * multiplicity="(0 1)"
	 */
	private HashMap leaveToRootHash = new HashMap();

	/**
	 * 
	 * @uml.property name="lColModel"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	//��߱�����
	private DefaultTableColumnModel lColModel;

	/**
	 * 
	 * @uml.property name="rColModel"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	//�ұ߱�����
	private DefaultTableColumnModel rColModel;

  //public final static int COLUMN_HEIGHT = 22;

public FBaseTableColumnModel() {
    super();
    setLeftModel(new DefaultTableColumnModel());
    setRightModel(new DefaultTableColumnModel());
  }

  /**
   * ����������������ģ�͡�
   * @param model
   */
  public void setLeftModel(DefaultTableColumnModel model) {
    this.lColModel = model;
    //����һ���к���
    FBaseTableColumn col = new FBaseTableColumn();
    col.setId(FBaseTable.ROWNUMBER_COLUMN_NAME);
    col.setHeaderValue(" ");
    col.setModelIndex(0);
    col.setCellRenderer(new FBaseTableRowCellRenderer(new FBaseTableIndexCellRenderer()));
    col.setResizable(false);
    col.setTitleVisible(false);
    lColModel.addColumn(col);
    compounds.add(col);
    
    this.lColModel.addColumnModelListener(new TableColumnModelListener() {

        public void columnAdded(TableColumnModelEvent tableColumnModelEvent) {
        }

        public void columnRemoved(TableColumnModelEvent tableColumnModelEvent) {
        }

        public void columnMoved(TableColumnModelEvent tableColumnModelEvent) {
        }

        public void columnMarginChanged(ChangeEvent changeEvent) {
        }

        public void columnSelectionChanged(ListSelectionEvent listSelectionEvent) {
        	if(lColModel.getSelectedColumnCount()>0) {
        		rColModel.getSelectionModel().clearSelection();
        	}
        }
      });
  }

  /**
   * �����Ҳ������ģ�͡�
   * @param model
   */
  public void setRightModel(DefaultTableColumnModel model) {
    this.rColModel = model;
    this.rColModel.addColumnModelListener(new TableColumnModelListener() {

      public void columnAdded(TableColumnModelEvent tableColumnModelEvent) {
      }

      public void columnRemoved(TableColumnModelEvent tableColumnModelEvent) {
      }

      public void columnMoved(TableColumnModelEvent tableColumnModelEvent) {
      }

      public void columnMarginChanged(ChangeEvent changeEvent) {
      }

      public void columnSelectionChanged(ListSelectionEvent listSelectionEvent) {
    		if(rColModel.getSelectedColumnCount()>0) {
    			lColModel.getSelectionModel().clearSelection();
    		}
      }
    });
  }

  /**
   * �������������ģ��
   * @return
   */
  public DefaultTableColumnModel getLeftModel() {
    return this.lColModel;
  }

  /**
   * ����Ҳ��������ģ��
   * @return
   */
  public DefaultTableColumnModel getRightModel() {
    return this.rColModel;
  }

  /**
   * ����һ�У����п����и�����
   * @param aColumn
   */
  public void addColumn(FBaseTableColumn aColumn) {
    //���븴���С�
    this.compounds.add(aColumn);
    //�����Ҳ�ģ�͡�
    FBaseTableColumn[] cols = aColumn.getAllSubTableColumns();

    rootToLeaveHash.put(aColumn, cols);
    for (int i = 0; i < cols.length; i++) {
      cols[i].setPreferredWidth(cols[i].getWidth());
      rColModel.addColumn(cols[i]);
      this.leaveToRootHash.put(cols[i], aColumn);
    }
  }

  /**
   * ������ͼ������ø��С�
   * @param viewIndex
   * @return
   */
  public FBaseTableColumn getColumn(int viewIndex) {
    int lcount = lColModel.getColumnCount() - 1;
    if (viewIndex < lcount) {
      return (FBaseTableColumn) lColModel.getColumn(viewIndex + 1);
    }
    else {
      return (FBaseTableColumn) rColModel.getColumn(viewIndex - lcount);
    }
  }
  /**
   * ������ͼ������ø��С�
   * @param viewIndex
   * @return
   */
  public FBaseTableColumn getColumn(Object identifer) {
	    for(int i=1; i<lColModel.getColumnCount(); i++) {
	    	if(lColModel.getColumn(i).getIdentifier().equals(identifer)){
	    		return (FBaseTableColumn) lColModel.getColumn(i);
	    	}
	    }
	    for(int i=0; i<rColModel.getColumnCount(); i++) {
	    	if(rColModel.getColumn(i).getIdentifier().equals(identifer)){
	    		return (FBaseTableColumn) rColModel.getColumn(i);
	    	}
	    }
	    return null;
  }
  
  /**
   * delete column
   * add time 2008-07-18
   * @param col
   */
  public void removeColumn(FBaseTableColumn col){
	  for(int i=1; i<lColModel.getColumnCount(); i++) {
	    	if(lColModel.getColumn(i).getIdentifier().equals(col.getIdentifier())){
	    		lColModel.removeColumn(col);
	    	}
	    }
	    for(int i=0; i<rColModel.getColumnCount(); i++) {
	    	if(rColModel.getColumn(i).getIdentifier().equals(col.getIdentifier())){
	    		rColModel.removeColumn(col);
	    	}
	    }
	  
  }
  
  
  public int getColumnCount() {
    return lColModel.getColumnCount() - 1 + rColModel.getColumnCount();
  }

  public int getColumnHeight() {
    FBaseTableColumn[] cols = new FBaseTableColumn[this.compounds.size()];
    compounds.copyInto(cols);
    return this.getColumnLayers(cols)*22;
  }

  public int getColumnLayers() {
	    FBaseTableColumn[] cols = new FBaseTableColumn[this.compounds.size()];
	    compounds.copyInto(cols);
	    return this.getColumnLayers(cols);	  
  }
  
  public int getColumnLayers(FBaseTableColumn[] cols) {
    int max = 0;
    for (int i = 0; cols != null && i < cols.length; i++) {
      int layers = this.getColumnLayers(cols[i].getSubTableColumns()) + 1;
      if (layers > max) {
        max = layers;
      }
    }
    return max;
  }

  public FBaseTableColumn[] getBrotherTableColumn(FBaseTableColumn col) {
    FBaseTableColumn root = (FBaseTableColumn) leaveToRootHash.get(col);
    if (root == null)
      return new FBaseTableColumn[] {
          col};
    return (FBaseTableColumn[])this.rootToLeaveHash.get(root);
  }
}