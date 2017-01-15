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
 * Title: ���ͷ
 * </p>
 * <p>
 * Description: ������ͷ�Ĺ���
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * <p>
 * Company: ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * 
 * @author linping
 * @author �ƽ� 2008��4��14������
 * @version 1.0
 */
public class ColumnGroup implements Control, Compound {

	private FBaseTableHeaderRenderer renderer; // ��ͷ��ʾ��ʽ

	protected Vector v; // v�����������

	private String id; // ���ͷ��id

	private String title; // ����

	private Control parentControl; // ��control�ؼ�

	private int margin = 0; // �м��

	/**
	 * ���ͷ���캯��
	 */
	public ColumnGroup() {
		// ���rendererΪ�գ���renderer����FBaseTableHeaderRenderer��Ⱦ��
		if (renderer == null) {
			this.renderer = new FBaseTableHeaderRenderer();

		}
		v = new Vector();
	}

	/**
	 * �������Ķ��ͷ���캯��
	 * 
	 * @param title
	 *            ����
	 */
	public ColumnGroup(String title) {
		this(null, title);
	}

	/**
	 * ���������ݺͱ����ʾ��ʽ
	 * 
	 * @param renderer
	 *            �����ʾ��ʽ
	 * @param title
	 *            ������
	 */
	public ColumnGroup(TableCellRenderer renderer, String title) {
		// ���rendererΪ�գ������FBaseTableHeaderRenderer
		if (renderer == null) {
			this.renderer = new FBaseTableHeaderRenderer();

		} else { // ���renderer��Ϊ�գ������ָ����renderer
			this.renderer = (FBaseTableHeaderRenderer) renderer;
		}
		this.title = title;
		v = new Vector();
	}

	/**
	 * ȡ�ö��ͷ��
	 * 
	 * @param c
	 *            ����
	 * @param g
	 *            Vector
	 * @return ���ͷ��vector
	 */
	public Vector getColumnGroups(TableColumn c, Vector g) {
		// ����ǰ�м��뵽g������
		g.addElement(this);
		// ���v�������Ѱ���c�У��򷵻�g����
		if (v.contains(c))
			return g;
		Enumeration enumeration = v.elements();
		// ��ȡv����
		while (enumeration.hasMoreElements()) {
			Object obj = enumeration.nextElement();
			// �����Ϊ���ͷ��
			if (obj instanceof ColumnGroup) {
				Vector groups = (Vector) ((ColumnGroup) obj).getColumnGroups(c,
						(Vector) g.clone());
				// ������ͷ�в�Ϊ�գ��򷵻ظö��ͷ��
				if (groups != null)
					return groups;
			}
		}
		return null;
	}

	/**
	 * ȡ�ñ�ͷ��Ԫ�񻭲�
	 * 
	 * @return ��ͷ��Ԫ�񻭲�
	 */
	public TableCellRenderer getHeaderRenderer() {
		return renderer;
	}

	/**
	 * ȡ�ñ�ͷ����
	 * 
	 * @return ��ͷ����
	 */
	public Object getHeaderValue() {
		return title;
	}

	/**
	 * ȡ����Ԫ�ظ���
	 * 
	 * @return ��Ԫ�ظ���
	 */
	public int getSize() {
		// ���v����Ϊ���򷵻�0�����򷵻�v������С
		return v == null ? 0 : v.size();
	}

	/**
	 * ȡ�ñ���С
	 * 
	 * @param table
	 *            ��
	 * @return ����С
	 */
	public Dimension getSize(JTable table) {
		// ȡ�ñ��Ŀؼ�
		Component comp = renderer.getTableCellRendererComponent(table,
				getHeaderValue(), false, false, -1, -1);
		// ȡ�ñ��߶�
		int height = comp.getPreferredSize().height;
		// �����
		int width = 0;
		Enumeration enumeration = v.elements();
		// ��ȡv��������
		while (enumeration.hasMoreElements()) {
			Object obj = enumeration.nextElement();
			// ����Ǳ���
			if (obj instanceof TableColumn) {
				TableColumn aColumn = (TableColumn) obj;
				// �����ڸ��п���м��
				width += aColumn.getWidth();
				width += margin;
			} else {
				// ����Ƕ��ͷ�У������϶��ͷ�п�
				width += ((ColumnGroup) obj).getSize(table).width;
			}
		}

		// ���ر���С
		return new Dimension(width, height);
	}

	/**
	 * �ڶ��ͷ��ɾ��Column
	 * 
	 * @param ptg
	 *            ���ͷ
	 * @param tc
	 *            ����
	 * @return true:ɾ���ɹ� false��ɾ��ʧ��
	 */
	public boolean removeColumn(ColumnGroup ptg, TableColumn tc) {
		// ɾ�������־
		boolean retFlag = false;
		// ���ͷ����������
		int size = ptg.v.size();
		// ������в�Ϊ��
		if (tc != null) {
			// ��ȡ���ͷ����������
			for (int i = 0; i < size; i++) {
				Object tmpObj = ptg.v.get(i);
				if (tmpObj instanceof ColumnGroup) {
					retFlag = removeColumn((ColumnGroup) tmpObj, tc);
					// ����ҵ�����
					if (retFlag)
						break;
				} else if (tmpObj instanceof TableColumn) {
					// �ж��Ƿ���ҵĶ���
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
	 * �б�ͷ��ɾ��ColumnGrp
	 * 
	 * @param ptg
	 *            ���ͷ
	 * @param tg
	 *            ���ͷ
	 * @return ɾ���ɹ�����true,���򷵻�false
	 */
	public boolean removeColumnGrp(ColumnGroup ptg, ColumnGroup tg) {
		boolean retFlag = false;
		if (tg != null) {
			// ���ͷ����������
			int size = ptg.v.size();
			// ѭ����ȡv��Ԫ��
			for (int i = 0; i < size; i++) {
				Object tmpObj = ptg.v.get(i);
				// �����ColumnGroup����
				if (tmpObj instanceof ColumnGroup) {
					// �ж��Ƿ���ҵĶ���
					if (tmpObj == tg) {
						ptg.v.remove(i);
						retFlag = true;
						break;
					} else {
						retFlag = removeColumnGrp((ColumnGroup) tmpObj, tg);
						// ����ҵ�����
						if (retFlag)
							break;
					}
				}
				// �����TableColumn����
				else if (tmpObj instanceof TableColumn) {
					break;
				}
			}
		}
		return retFlag;
	}

	/**
	 * �����б߾�
	 * 
	 * @param margin
	 *            �б߾�
	 */
	public void setColumnMargin(int margin) {
		this.margin = margin;
		Enumeration enumeration = v.elements();
		// ����ÿ�м��
		while (enumeration.hasMoreElements()) {
			Object obj = enumeration.nextElement();
			// ���ΪColumnGroup����
			if (obj instanceof ColumnGroup) {
				((ColumnGroup) obj).setColumnMargin(margin);
			}
		}
	}

	/**
	 * ���ñ����ʾ��ʽ
	 * 
	 * @param renderer
	 *            �����ʾ��ʽ
	 */
	public void setHeaderRenderer(TableCellRenderer renderer) {
		// ���renderer��Ϊ�գ������ñ����ʾ��ʽΪrenderer
		if (renderer != null) {
			this.renderer = (FBaseTableHeaderRenderer) renderer;
		}
	}

	/**
	 * ȡ�������ӿؼ�
	 * 
	 * @return �����ӿؼ�
	 */
	public List getSubControls() {
		// ���vΪ�գ��򷵻�
		if (v == null) {
			return null;
		}

		ArrayList list = new ArrayList();
		int size = v.size(); // vԪ�ظ���
		// ѭ����ȡv�а����Ŀؼ�
		for (int i = 0; i < size; i++) {
			Control control = (Control) v.get(i);
			// ���ؼ����뵽list��
			list.add(control);
		}

		// ���������ӿؼ�
		return list;
	}

	/**
	 * ȡ���ӿؼ�
	 * 
	 * @param id
	 *            �ӿؼ�id
	 * @return �ӿؼ�
	 */
	public Control getSubControl(String id) {
		// ���id��vΪ�գ��򷵻ؿ�
		if (id == null || v == null)
			return null;

		Object obj = null;
		int size = v.size(); // vԪ�ظ���
		// ѭ����ȡvԪ��
		for (int i = 0; i < size; i++) {
			obj = v.get(i);
			// ���Ԫ��ΪFBaseTableColumn����
			if (obj instanceof FBaseTableColumn) {
				FBaseTableColumn col = (FBaseTableColumn) obj;
				// ���Ԫ��id�����id��ȣ��򷵻�
				if (id.equalsIgnoreCase((String) col.getIdentifier())) {
					return col;
				}
			}
			// ���Ԫ��ΪColumnGroup����
			else if (obj instanceof ColumnGroup) {
				ColumnGroup columnGroup = (ColumnGroup) obj;
				// ���Ԫ��id�����id��ȣ��򷵻�
				if (id.equalsIgnoreCase(columnGroup.getId())) {
					return columnGroup;
				}
			}
		}

		return null;
	}

	/**
	 * ���ӿؼ����ؼ�������column����ColumnGroup
	 * 
	 * @param control
	 *            �ؼ�
	 */
	public void addControl(Control control) {
		// ���controlΪ�գ��򷵻�
		if (control == null) {
			return;
		}

		// ��control���뵽v������
		v.addElement(control);

		// ���ø��ؼ�
		control.setParentControl(this);
	}

	/**
	 * ���ӿؼ����ؼ�������column����ColumnGroup
	 * 
	 * @param control
	 *            �ؼ�
	 * @param contraint
	 *            Լ��
	 */
	public void addControl(Control control, Object contraint) {
	}

	/**
	 * ȡ��id
	 * 
	 * @return id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * ����id
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * �õ�������
	 * 
	 * @return ������
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * ����������
	 * 
	 * @param title
	 *            ������
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * ȡ�ø�control�ؼ�
	 * 
	 * @return ��control�ؼ�
	 */
	public Control getParentControl() {
		return parentControl;
	}

	/**
	 * ���ø�control�ؼ�
	 * 
	 * @param parentControl
	 *            ��control�ؼ�
	 */
	public void setParentControl(Control parentControl) {
		this.parentControl = parentControl;
	}

}
