package com.foundercy.pf.control;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.border.Border;

import com.foundercy.pf.control.util.SubControlFinder;

/**
 * <p>
 * Title: ���������
 * </p>
 * <p>
 * Description:���������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005 ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * <p>
 * Company:����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * 
 * @author �ƽ� 2008��4��10������
 * @version 1.0
 */
public class FScrollPane extends JScrollPane implements Control, Compound {
	private static final long serialVersionUID = -3015559972055407653L;

	private String id;

	private Control parent;

	private java.util.List subControls = new ArrayList();

	private boolean borderVisible;

	private Border border = null;

	/**
	 * ���캯��
	 */
	public FScrollPane() {
		super();
		border = this.getBorder();
	}

	/**
	 * ���캯��
	 * 
	 * @param view
	 *            ���ؼ�
	 */
	public FScrollPane(Component view) {
		super(view);
		border = this.getBorder();
	}

	/**
	 * ���캯��
	 * 
	 * @param view
	 *            ���ؼ�
	 * @param vsbPolicy
	 *            ˮƽ��������ʾ���ԣ�e.g. VERTICAL_SCROLLBAR_AS_NEEDED
	 * @param hsbPolicy
	 *            ��ֱ��������ʾ���ԣ�e.g. HORIZONTAL_SCROLLBAR_AS_NEEDED
	 */
	public FScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
		super(view, vsbPolicy, hsbPolicy);
		border = this.getBorder();
	}

	/**
	 * ���캯��
	 * 
	 * @param vsbPolicy
	 *            ˮƽ��������ʾ���ԣ�e.g. VERTICAL_SCROLLBAR_AS_NEEDED
	 * @param hsbPolicy
	 *            ��ֱ��������ʾ���ԣ�e.g. HORIZONTAL_SCROLLBAR_AS_NEEDED
	 */
	public FScrollPane(int vsbPolicy, int hsbPolicy) {
		super(vsbPolicy, hsbPolicy);
		border = this.getBorder();
	}

	/**
	 * �õ��ؼ�ID
	 * 
	 * @return �ؼ�ID
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * ���ÿؼ�ID
	 * 
	 * @param id
	 *            �ؼ�ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * �õ������ÿؼ�����һ��ؼ�
	 * 
	 * @return �ؼ�����
	 */
	public Control getParentControl() {
		return this.parent;
	}

	/**
	 * ������һ��ؼ�
	 * 
	 * @param parent
	 *            �ؼ�����
	 */
	public void setParentControl(Control parent) {
		this.parent = parent;
	}

	/**
	 * �����ӿؼ�,ͬʱ����Լ��
	 * 
	 * @param control
	 *            �ؼ�
	 * @param contraint
	 *            Լ��
	 */
	public void addControl(Control control, Object contraint) {
	}

	/**
	 * �����ӿؼ� �˿ؼ���ֻ���������ӿؼ�
	 * 
	 * @param control
	 *            JSplitPane�е��ӿؼ�
	 */
	public void addControl(Control control) {
		this.setViewportView((Component) control);
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.subControls.add(control);
	}

	/**
	 * ���� id �õ��ӿؼ�
	 * 
	 * @param id
	 *            �ؼ�id
	 * @return �ؼ�
	 */
	public Control getSubControl(String id) {
		return SubControlFinder.getSubControlBySerialId(this, id);
	}

	/**
	 * ��ȡ�ø��Ͽؼ���ֱ���ӿؼ����������ﱲ���µĺ��ӣ�
	 * 
	 * @return �ø��Ͽؼ���ֱ���ӿؼ����������ﱲ���µĺ��ӣ�
	 */
	public List getSubControls() {
		if (this.subControls.isEmpty())
			return null;
		else
			return this.subControls;
	}

	/**
	 * ��ѯ�߿��Ƿ�ɼ�
	 * 
	 * @return true���ɼ���false�����ɼ�
	 * 
	 */
	public boolean isBorderVisible() {
		return borderVisible;
	}

	/**
	 * ���ñ߿��Ƿ�ɼ�
	 * 
	 * @param borderVisible
	 *            true���ɼ���false�����ɼ�
	 * 
	 */
	public void setBorderVisible(boolean borderVisible) {
		this.borderVisible = borderVisible;
		if (!borderVisible) {
			this.setBorder(null);
			this.getViewport().setBorder(null);
		} else {
			this.setBorder(this.border);
		}
	}
}
