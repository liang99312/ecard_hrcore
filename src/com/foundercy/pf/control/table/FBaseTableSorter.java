package com.foundercy.pf.control.table;

import java.util.Date;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.foundercy.pf.control.AbstractRefDataField;
import com.foundercy.pf.control.AssistRefModel;
///import com.foundercy.pf.control.FDecimalField;
///import com.foundercy.pf.control.FIntegerField;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author fangyi
 * @version 1.0
 */

public class FBaseTableSorter extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1963766787807930091L;

	/**
	 * 
	 * @uml.property name="indexes"
	 * @uml.associationEnd multiplicity="(0 -1)"
	 */
	// ���������ֶ�
	private int indexes[];

	/**
	 * 
	 * @uml.property name="sortingColumns"
	 * @uml.associationEnd elementType="java.lang.Integer" multiplicity="(0 -1)"
	 */
	// ����������
	private Vector sortingColumns = new Vector();

	// �������־
	private boolean ascending = true;

	// �ȽϽ��
	private int compares;
        
        private int f_row_index = 0;

	/**
	 * 
	 * @uml.property name="model"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	// ���μ�����ı��ģ��
	private TableModel model = null;
    private boolean enable_sort = false;

    public void setEnable_sort(boolean enable_sort) {
        this.enable_sort = enable_sort;
    }

	// �Ƿ���ʾ�кŵı�־
	// private boolean rownumVisible = true;

	public FBaseTableSorter() {

	}

	public FBaseTableSorter(TableModel model) {
		setModel(model);
	}

	/**
	 * ����ͼ�е�����ת��Ϊģ�͵�����
	 * 
	 * @param viewIndex
	 * @return
	 */
	public int convertViewRowIndexToModel(int viewIndex) {
		if (viewIndex < 0 || viewIndex >= indexes.length)
			return -1;
		return this.indexes[viewIndex];
	}

	/**
	 * ��ģ�͵�����ת��Ϊ��ͼ������
	 * 
	 * @param modelIndex
	 * @return
	 */
	public int convertModelRowIndexToView(int modelIndex) {
		for (int i = 0; indexes != null && i < indexes.length; i++) {
			if (indexes[i] == modelIndex) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 
	 * @uml.property name="model"
	 */
	public void setModel(TableModel model) {
		this.model = model;
		if (model == null)
			return;
		this.model.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent ev) {
				if (ev.getType() == TableModelEvent.UPDATE) {
					if (!checkModel()) {
						reallocateIndexes();
						fireTableChanged(ev);
						return;
					}
					fireTableChanged(ev);
					return;
				}
				if (ev.getType() == TableModelEvent.INSERT) {
					if (ev.getFirstRow() == indexes.length) {
						appendAnIndex();
						fireTableRowsInserted(indexes.length - 1,
								indexes.length - 1);
						return;
					} else {
						int index = convertModelRowIndexToView(ev.getFirstRow());
						insertAnIndex(ev.getFirstRow());
						fireTableRowsInserted(index, index);
						return;
					}

				}
				if (ev.getType() == TableModelEvent.DELETE) {
					int index = deleteAnIndex(ev.getFirstRow());
					fireTableRowsDeleted(index, index);
					return;
				}
				reallocateIndexes();
				fireTableDataChanged();
			}
		});
		reallocateIndexes();
	}

	/**
	 * @return
	 * 
	 * @uml.property name="model"
	 */
	public TableModel getModel() {
		return this.model;
	}

	/**
	 * 
	 * @return
	 */
	public int getRowCount() {
		if (model == null)
			return 0;
		return model.getRowCount();
	}

	/**
	 * 
	 * @return
	 */
	public int getColumnCount() {
		if (model == null)
			return 0;
		return model.getColumnCount();
	}

	/**
	 * 
	 * @param col
	 * @return
	 */
	public String getColumnName(int col) {
		if (model == null)
			return null;
		return model.getColumnName(col);
	}

	/**
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public Object getValueAt(int row, int col) {
		return model.getValueAt(this.indexes[row], col);
	}

	/**
	 * set value into the model;
	 * 
	 * @param value
	 * @param row
	 * @param col
	 */
	public void setValueAt(Object value, int row, int col) {
		checkModel();
		if (this.indexes.length > 0)
			model.setValueAt(value, this.indexes[row], col);
	}

	/**
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isCellEditable(int row, int col) {
		return model.isCellEditable(this.indexes[row], col);
	}

	// �Ƚ�����ĳһ�еĴ�С
	private int compareRowsByColumn(int row1, int row2, FBaseTableColumn column) {
		
		TableModel data = model;
		
		//�����Уɣ�ȡ���ж�Ӧ������ģ���к�
		int columnIndex =super.findColumn(column.getId());
		
		// Check for nulls.
		Object o1 = data.getValueAt(row1, columnIndex);
		Object o2 = data.getValueAt(row2, columnIndex);

		// If both values are null, return 0.
		if (o1 == null && o2 == null) {
			return 0;
		} else if (o1 == null) { // Define null less than everything.
			return -1;
		} else if (o2 == null) {
			return 1;
		}
		
		//lindx ���� �ж�FBaseColumn��dataField����
//		if(column.getEditDataField() instanceof AbstractRefDataField){
			
//			AbstractRefDataField dataField = (AbstractRefDataField)column.getEditDataField();
/*			
			if(column.getEditDataField() instanceof FDecimalField){
				//System.out.println("���ݽ������");
				//װ��ΪDouble����
				try {
					o1 = new Double(o1.toString());
					
				} catch (Exception ex) {
					o1 = new Double(0);
				}
				
				try {
					o2 = new Double(o2.toString());
				} catch (Exception ex) {
					o2 = new Double(0);
				}
				
			}else if (column.getEditDataField() instanceof FIntegerField){
				//�����FIntegerField�ؼ���ת��ΪLong
				
				try {
					o1 = new Long(o1.toString());
				} catch (Exception ex) {
					o1 = new Long(0);
				}
				
				try {
					o2 = new Long(o2.toString());
				} catch (Exception ex) {
					o2 = new Long(0);
				}
				
			}else */
//        if(dataField.getRefModel() instanceof AssistRefModel
//					|| dataField.getRefModel() instanceof ComboBoxModel){
				
					//����Ǹ���¼������������ģ��
				
//					o1 = dataField.getRefModel().getNameByValue(o1);
//					o2 = dataField.getRefModel().getNameByValue(o2);
					//System.out.println("��ǰ�У�"+columnIndex+" ����getNameByValue()����"+o1+":"+o2);
				
//			}
			
//		}
		
		
		/*
		 * We copy all returned values from the getValue call in case an
		 * optimised model is reusing one object to return many values. The
		 * Number subclasses in the JDK are immutable and so will not be used in
		 * this way but other subclasses of Number might want to do this to save
		 * space and avoid unnecessary heap allocation.
		 */

		if (o1 instanceof Number) {
			double d1 = ((Number) o1).doubleValue();
			double d2 = ((Number) o2).doubleValue();
			if (d1 < d2) {
				return -1;
			} else if (d1 > d2) {
				return 1;
			} else {
				return 0;
			}
		} else if (o1 instanceof java.util.Date) {
			Date d1 = (Date) o1;
			long n1 = d1.getTime();
			Date d2 = (Date) o2;
			long n2 = d2.getTime();

			if (n1 < n2) {
				return -1;
			} else if (n1 > n2) {
				return 1;
			} else {
				return 0;
			}
		} else if (o1 instanceof String) {
			String s1 = (String) o1;
			String s2 = (String) o2;
			// int result = s1.compareTo(s2);
			// ���ַ�����������֧����������
			int result = this.compare(s1, s2);

			if (result < 0) {
				return -1;
			} else if (result > 0) {
				return 1;
			} else {
				return 0;
			}
		} else if (o1 == Boolean.class) {
			Boolean bool1 = (Boolean) o1;
			boolean b1 = bool1.booleanValue();
			Boolean bool2 = (Boolean) o2;
			boolean b2 = bool2.booleanValue();

			if (b1 == b2) {
				return 0;
			} else if (b1) { // Define false < true
				return 1;
			} else {
				return -1;
			}
		} else {
			String s1 = o1.toString();
			String s2 = o2.toString();
			// int result = s1.compareTo(s2);
			// ���ַ�����������֧����������
			int result = this.compare(s1, s2);

			if (result < 0) {
				return -1;
			} else if (result > 0) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	private int compare(int row1, int row2) {
		compares++;
		for (int level = 0; level < sortingColumns.size(); level++) {
			FBaseTableColumn column = (FBaseTableColumn) sortingColumns.elementAt(level);
			
			int result = compareRowsByColumn(row1, row2, column);
			if (result != 0) {
				return ascending ? result : -result;
			}
		}
		return 0;
	}

	/**
	 * ���·���������
	 */
	private void reallocateIndexes() {
		int rowCount = model.getRowCount();
		indexes = new int[rowCount];
		for (int row = 0; row < rowCount; row++) {
			indexes[row] = row;
		}
	}

	/**
	 * ��ָ����ģ��������ǰ������һ��ģ��������,������ͼ������
	 * 
	 * @param index
	 */
	private int insertAnIndex(int index) {
		int viewIndex = this.convertModelRowIndexToView(index);
		for (int i = 0; i < indexes.length; i++) {
			if (indexes[i] >= index)
				indexes[i]++;
		}
		int[] newIndexes = new int[indexes.length + 1];
		for (int i = 0; i < newIndexes.length; i++) {
			if (i < viewIndex)
				newIndexes[i] = indexes[i];
			else if (i == viewIndex)
				newIndexes[i] = index;
			else if (i > viewIndex)
				newIndexes[i] = indexes[i - 1];
		}
		this.indexes = newIndexes;
		return viewIndex;
	}

	/**
	 * ɾ��ָ����ģ�������š�
	 * 
	 * @param index
	 */
	private int deleteAnIndex(int index) {

		int viewIndex = this.convertModelRowIndexToView(index);

		for (int i = 0; i < indexes.length; i++) {
			if (indexes[i] > index)
				indexes[i]--;
		}

		int[] newIndexes = new int[indexes.length - 1];

		for (int i = 0; i < newIndexes.length; i++) {
			if (i < viewIndex)
				newIndexes[i] = indexes[i];
			else
				newIndexes[i] = indexes[i + 1];
		}

		this.indexes = newIndexes;

		return viewIndex;
	}

	/**
	 * ��ģ�͵ĺ�������һ��������
	 * 
	 * @return
	 */
	private int appendAnIndex() {
		int[] newIndexes = new int[indexes.length + 1];
		for (int i = 0; i < newIndexes.length - 1; i++) {
			newIndexes[i] = indexes[i];
		}
		newIndexes[newIndexes.length - 1] = newIndexes.length - 1;
		this.indexes = newIndexes;
		return indexes.length - 1;
	}

	/**
	 * ��������š�
	 * 
	 * @return
	 */
	public int[] getSortedIndex() {
		return this.indexes;
	}

	// ���ģ�͵�������ģ�������Ƿ�һ��
	private boolean checkModel() {
		if (indexes.length != model.getRowCount()) {
			return false;
		}
		return true;
	}

	public void sort() {
		checkModel();
		compares = 0;
		// n2sort();
		// qsort(0, indexes.length-1);
		// int[] oldIndexes = indexes;
		// int[] tempIndexes = new int[oldIndexes.length-1];
		// for (int i=0; i<oldIndexes.length-1; i++) {
		// tempIndexes[i] = oldIndexes[i];
		// }
		// indexes = tempIndexes;
		//      
//                if (!enable_sort){
//                    return;
//                }
		shuttlesort((int[]) indexes.clone(), indexes, f_row_index, indexes.length);

		// tempIndexes = indexes;
		// indexes = new int[oldIndexes.length];
		// for (int i=0; i<tempIndexes.length; i++) {
		// indexes[i] = tempIndexes[i];
		// }
		// indexes[oldIndexes.length-1] = getRowCount()-1;
	}

	// This is a home-grown implementation which we have not had time
	// to research - it may perform poorly in some circumstances. It
	// requires twice the space of an in-place algorithm and makes
	// NlogN assigments shuttling the values between the two
	// arrays. The number of compares appears to vary between N-1 and
	// NlogN depending on the initial order but the main reason for
	// using it here is that, unlike qsort, it is stable.
	private void shuttlesort(int from[], int to[], int low, int high) {
		if (high - low < 2) {
			return;
		}
		int middle = (low + high) / 2;
		shuttlesort(to, from, low, middle);
		shuttlesort(to, from, middle, high);

		int p = low;
		int q = middle;

		/*
		 * This is an optional short-cut; at each recursive call, check to see
		 * if the elements in this subset are already ordered. If so, no further
		 * comparisons are needed; the sub-array can just be copied. The array
		 * must be copied rather than assigned otherwise sister calls in the
		 * recursion might get out of sinc. When the number of elements is three
		 * they are partitioned so that the first set, [low, mid), has one
		 * element and and the second, [mid, high), has two. We skip the
		 * optimisation when the number of elements is three or less as the
		 * first compare in the normal merge will produce the same sequence of
		 * steps. This optimisation seems to be worthwhile for partially ordered
		 * lists but some analysis is needed to find out how the performance
		 * drops to Nlog(N) as the initial order diminishes - it may drop very
		 * quickly.
		 */

		if (high - low >= 4 && compare(from[middle - 1], from[middle]) <= 0) {
			for (int i = low; i < high; i++) {
				to[i] = from[i];
			}
			return;
		}
		// A normal merge.
		for (int i = low; i < high; i++) {
			if (q >= high || (p < middle && compare(from[p], from[q]) <= 0)) {
				to[i] = from[p++];
			} else {
				to[i] = from[q++];
			}
		}
	}

	private void swap(int i, int j) {
		int tmp = indexes[i];
		indexes[i] = indexes[j];
		indexes[j] = tmp;
	}

	/**
	 * ����ĳ�н�����������
	 * 
	 * @param column
	 */
	public void sortByColumn(FBaseTableColumn column) {
		sortByColumn(column, true,f_row_index);
	}

	// ����ĳ�н�������
	public void sortByColumn(FBaseTableColumn column, boolean ascending,int f_row_index) {
		this.ascending = ascending;
		sortingColumns.removeAllElements();
		sortingColumns.addElement(column);
                this.f_row_index = f_row_index;
		sort();
		this.fireTableDataChanged();
	}

	/**
	 * �����ƶ�һ��
	 * 
	 * @param viewIndex
	 */
	protected static final int UP = 0;

	protected static final int DOWN = 1;

	public void rowMoveUpOrDown(int moveType, int viewIndex) {
		if (moveType == UP && viewIndex > 0) {
			swap(viewIndex, viewIndex - 1);
		}
		if (moveType == DOWN && viewIndex < getRowCount() - 1) {
			swap(viewIndex, viewIndex + 1);
		}
	}


	/**
	 * ֧�����������compare����
	 * 
	 * @param o1
	 *            ��һ���Ƚ��ַ���
	 * @param o2
	 *            �ڶ����Ƚ��ַ���
	 * @return �ȽϽ��
	 */
	public int compare(String o1, String o2) {
		// ����ַ�������
		int minLength = 0;
		// ���o1�ַ������ȴ���o2����minLength��o2�ַ�������
		if (o1.length() > o2.length()) {
			minLength = o2.length();
		}
		// ���o1�ַ�������С�ڵ���o2����minLength��o1�ַ�������
		else {
			minLength = o1.length();
		}
		// �������ַ�������˳��Ƚ�ÿ���ַ���С
		for (int i = 0; i < minLength; i++) {
			// ȡ���ַ���Ӧ�����
			int codePoint1 = o1.charAt(i);
			int codePoint2 = o2.charAt(i);

			// ���codePoint1������codePoint2
			if (codePoint1 != codePoint2) {
				// ���codePoint1��codePoint2Ϊ�����ַ����򷵻�codePoint1 - codePoint2
				// if (Character.isSupplementaryCodePoint(codePoint1)
				// || Character.isSupplementaryCodePoint(codePoint2)) {
				// return codePoint1 - codePoint2;
				// }

				// ȡ�������ַ�����Ӧ����ƴ���ַ���
				String pinyin1 = pinyin((char) codePoint1);
				String pinyin2 = pinyin((char) codePoint2);

				// ��������ַ����Ǻ��֣���Ƚ��������ִ�С˳��
				if (pinyin1 != null && pinyin2 != null) {
					if (!pinyin1.equals(pinyin2)) {
						return pinyin1.compareTo(pinyin2);
					}
				} else { // ���������һ�����Ǻ��֣��򷵻�codePoint1 - codePoint2
					return codePoint1 - codePoint2;
				}
			}
		}

		return o1.length() - o2.length();
	}
    public static void main(String[] args){
        String tmp = "K1jC��";
        for(char c:tmp.toCharArray()){
            String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c);
            if(pinyins != null)
                System.out.println(pinyins[0].substring(0,1).toUpperCase());
            else
                System.out.println("�޷�����");
        }
       // System.out.println(PinyinHelper.toHanyuPinyinStringArray("JJJ111����".charAt(1))[0].toUpperCase());
    }
	/**
	 * �ַ���ƴ���������־͵õ���һ��ƴ�������Ǻ��֣���return null��
	 * 
	 * @param c���ַ���
	 * @return ����ƴ���ַ���
	 */
	private String pinyin(char c) {
		// ���ַ���תΪƴ���ַ���
		String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c);
		// ���ƴ���ַ���Ϊnull���򷵻�null
		if (pinyins == null) {
			return null;
		}

		// ���غ���ƴ���ַ���
		return pinyins[0];
	}

}