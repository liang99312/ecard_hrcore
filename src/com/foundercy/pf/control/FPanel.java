/*
 * $Id: FPanel.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import java.awt.Component;
import java.awt.PopupMenu;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.EmptyBorder;

import com.foundercy.pf.control.util.SubControlFinder;

/**
 * <p>
 * Title: �ؼ�������
 * </p>
 * <p>
 * Description:�ؼ�������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * <p>
 * Company: ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * 
 * @author Fangyi
 * @author �ƽ� 2008��4��14������
 * @version $Revision: 1.1.1.1 $
 */
public class FPanel extends AbstractCompound {
	private static final long serialVersionUID = 1L;

	// private java.util.List subControls = new ArrayList();

	private int topInset = 0;

	private int leftInset = 0;

	private int bottomInset = 0;

	private int rightInset = 0;

	/**
	 * ���캯��
	 */
	public FPanel() {
		// setBorder();
	}

	/**
	 * �����ӿؼ�
	 * 
	 * @param control
	 *            FPanel�е��ӿؼ�
	 */
	public void addControl(Control control) {
		// ��������ó������Ȳ��֣���ȱʡ�����ؼ����ϡ�ռ1��1�С���Լ��
		if (this.getLayout() instanceof RowPreferedLayout) {
			this.addControl(control, new TableConstraints(1, 1, true));
		} else {
			if (control instanceof Component) {
				super.add((Component) control);
			} else {
				// �������͵Ķ��󽫱�������
			}
		}
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
		if (control instanceof Component) {
			super.add((Component) control, contraint);
		} else {
			// �������͵Ķ��󽫱�������
		}
		// this.subControls.add(control);
	}

	/**
	 * ������
	 * 
	 * @param comp
	 *            ����ӵĲ���
	 * 
	 * @see javax.swing.JComponent#revalidate()
	 * 
	 * @return ����ӵĿؼ�
	 */
	public Component add(Component comp) {
		if (comp instanceof Control) {
			this.addControl((Control) comp);
		} else {
			super.add(comp);
		}
		return comp;
	}

	/**
	 * ������
	 * 
	 * @param comp
	 *            ����ӵĲ���
	 * @param index
	 *            �����λ�ã��������-1����ӵ����
	 * 
	 * @return ����ӵĿؼ�
	 * @see javax.swing.JComponent#revalidate()
	 */
	public Component add(Component comp, int index) {
		// super.add(comp, index);
		if (comp instanceof Control) {
			this.addControl((Control) comp);
		} else {
			super.add(comp, index);
		}
		return comp;
	}

	/**
	 * �����ӿؼ�,ͬʱ����Լ��
	 * 
	 * @param comp
	 *            �ؼ�
	 * @param constraints
	 *            Լ��
	 */
	public void add(Component comp, Object constraints) {
		// super.add(comp, constraints);
		if (comp instanceof Control) {
			this.addControl((Control) comp, constraints);
		} else {
			super.add(comp, constraints);
		}
	}

	/**
	 * �����ӿؼ�,ͬʱ����Լ��
	 * 
	 * @param comp
	 *            �ؼ�
	 * @param constraints
	 *            Լ��
	 * @param index
	 *            �����λ�ã��������-1����ӵ����
	 */
	public void add(Component comp, Object constraints, int index) {
		// super.add(comp, constraints, index);
		if (comp instanceof Control) {
			this.addControl((Control) comp, constraints);
		} else {
			super.add(comp, constraints);
		}
	}

	/**
	 * ����Ҽ������˵�
	 * 
	 * @param popup
	 *            �Ҽ������˵�
	 * 
	 */
	public void add(PopupMenu popup) {
		super.add(popup);
	}

	/**
	 * �����ӿؼ�
	 * 
	 * @param name
	 *            �ؼ�����
	 * @param comp
	 *            �ؼ�
	 * 
	 * @return ����ӵĿؼ�
	 * 
	 */
	public Component add(String name, Component comp) {
		// super.add(name, comp);
		if (comp instanceof Control) {
			this.addControl((Control) comp);
		} else {
			super.add(name, comp);
		}
		return comp;
	}

	/**
	 * �ø��Ͽؼ���ֱ���ӿؼ����������ﱲ���µĺ��ӣ�
	 * 
	 * @return �����ӿؼ����������ﱲ���µĺ��ӣ�
	 */
	public List getSubControls() {

		Component[] comps = super.getComponents();

		ArrayList controls = new ArrayList();
		for (int i = 0; comps != null && i < comps.length; i++) {

			if (comps[i] instanceof Control) {
				controls.add(comps[i]);
			}
		}
		return controls;
		//    	
		// if (this.subControls.isEmpty())
		// return null;
		// else
		// return this.subControls;
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
	 * ���ȫ���ӿؼ�
	 */
	public void removeAllSubControls() {
		super.removeAll();
	}

	/**
	 * �����ϱ߼��
	 * 
	 * @param topInset
	 *            �ϱ߼��
	 */
	public void setTopInset(int topInset) {
		this.topInset = topInset;
		setBorder();
	}

	/**
	 * �õ��ϱ߼��
	 * 
	 * @return �ϱ߼��
	 */
	public int getTopInset() {
		return this.topInset;
	}

	/**
	 * ������߼��
	 * 
	 * @param leftInset
	 *            ��߼��
	 */
	public void setLeftInset(int leftInset) {
		this.leftInset = leftInset;
		setBorder();
	}

	/**
	 * �õ���߼��
	 * 
	 * @return ��߼��
	 */
	public int getLeftInset() {
		return this.leftInset;
	}

	/**
	 * ���õױ߼��
	 * 
	 * @param bottomInset
	 *            �ױ߼��
	 */
	public void setBottomInset(int bottomInset) {
		this.bottomInset = bottomInset;
		setBorder();
	}

	/**
	 * �õ��ױ߼��
	 * 
	 * @return �ױ߼��
	 */
	public int getBottomInset() {
		return this.bottomInset;
	}

	/**
	 * �����ұ߼��
	 * 
	 * @param rightInset
	 *            �ұ߼��
	 */
	public void setRightInset(int rightInset) {
		this.rightInset = rightInset;
		setBorder();
	}

	/**
	 * �õ��ұ߼��
	 * 
	 * @return �ұ߼��
	 */
	public int getRightInset() {
		return this.rightInset;
	}

	/**
	 * ���ÿؼ��߼��
	 */
	public void setBorder() {
		this.setBorder(new EmptyBorder(this.getTopInset(), this.getLeftInset(),
				this.getBottomInset(), this.getRightInset()));
	}

	/**
	 * ���������FInternalFramePane�У��ر�ʱ����close�������ṩ������̳�
	 * 
	 * @author �ƽ� 2007��9��24�����
	 */
	public void close() {
	}
}
