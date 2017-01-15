/*
 * $Id: FTabbedPane.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JTabbedPane;

import com.foundercy.pf.control.util.SubControlFinder;

/**
 * <p>
 * Title: FTabbedPaneѡ��ؼ�
 * </p>
 * <p>
 * Description:FTabbedPaneѡ��ؼ�
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * <p>
 * Company: ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * 
 * @version $Revision: 1.1.1.1 $
 * @author �ƽ� 2008��4��14������
 * @since java 1.4
 */
public class FTabbedPane extends JTabbedPane implements Control, Compound {

	private static final long serialVersionUID = 1467997656649115812L;

	private String id;

	private Control parent;

	private java.util.List subControls = new ArrayList();

	/**
	 * ���캯��
	 */
	public FTabbedPane() {
		super();
	}

	/**
	 * ���캯��
	 * 
	 * @param id
	 *            �ؼ�id
	 */
	public FTabbedPane(String id) {
		super();
		this.id = id;
	}

	/**
	 * ���캯��
	 * 
	 * @param tabPlacement
	 *            ��ǩ��λ�ã�JTabbedPane.LEFT������JTabbedPane.RIGHT�����ң�JTabbedPane.TOP�����ϣ�JTabbedPane.BOTTOM�����ײ�
	 */
	public FTabbedPane(int tabPlacement) {
		super(tabPlacement);
	}

	/**
	 * ���캯��
	 * 
	 * @param tabPlacement
	 *            ��ǩ��λ�ã�JTabbedPane.LEFT������JTabbedPane.RIGHT�����ң�JTabbedPane.TOP�����ϣ�JTabbedPane.BOTTOM�����ײ�
	 * @param tabLayoutPolicy
	 *            ����ǩ������ҳ��ʱ�Ĵ�����ԣ���������Ϊ������ֵ��JTabbedPane.WRAP_TAB_LAYOUT��TabbedPane.SCROLL_TAB_LAYOUT
	 */
	public FTabbedPane(int tabPlacement, int tabLayoutPolicy) {
		super(tabPlacement, tabLayoutPolicy);
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
	 * �õ��ؼ�ID
	 * 
	 * @return �ؼ�ID
	 */
	public String getId() {
		return this.id;
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
	 * �����ӿؼ�
	 * 
	 * @param control
	 *            �ӿؼ�
	 */
	public void addControl(Control control) {
		if (control instanceof FTitledPanel) {
			super.add(((FTitledPanel) control).getTitle(), (Component) control);
			this.subControls.add(control);
		}
	}

	/**
	 * �����ӿؼ�,��ָ������
	 * 
	 * @param title
	 *            ����
	 * @param control
	 *            �ӿؼ�
	 */
	public void addControl(String title, Control control) {
		super.add(title, (Component) control);
		this.subControls.add(control);
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
		super.add((Component) control, contraint);
		this.subControls.add(control);
	}

	/**
	 * ��ȡ�ø��Ͽؼ���ֱ���ӿؼ����������ﱲ���µĺ��ӣ�
	 * 
	 * @return �ø��Ͽؼ���ֱ���ӿؼ����������ﱲ���µĺ��ӣ�
	 */
	public java.util.List getSubControls() {
		if (this.subControls.isEmpty())
			return null;
		else
			return this.subControls;
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

}
