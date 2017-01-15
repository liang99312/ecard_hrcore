/*
 * $Id: FFlowLayoutPanel.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.foundercy.pf.control.util.SubControlFinder;

/**
 * <p>
 * Title: �����ֿؼ�����
 * </p>
 * <p>
 * Description: �����ֿؼ�����
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * <p>
 * Company: ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * 
 * @author �ƽ� 2008��4��14������
 * @version $Revision: 1.1.1.1 $
 */
public class FFlowLayoutPanel extends JPanel implements Control, Compound {

	private static final long serialVersionUID = -3220036536406033847L;

	private String id;

	private Control parent;

	private java.util.List subControls = new ArrayList();

	/**
	 * ���캯��
	 */
	public FFlowLayoutPanel() {
		super();
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 4));
	}

	/**
	 * �����ӿؼ�
	 * 
	 * @param control
	 *            FFlowLayoutPanel�е��ӿؼ�
	 */
	public void addControl(Control control) {
		this.addControl(control, null);
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
		super.add((Component) control, (TableConstraints) contraint);
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

	/**
	 * ����id
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * �õ�id
	 * 
	 * @return id
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
	 * ���ñ߿��Ƿ�ɼ�
	 * 
	 * @param visible
	 *            true-�ɼ���false-���ɼ�
	 */
	public void setBorderVisible(boolean visible) {
		if (visible) {
			this.setBorder(BorderFactory.createEtchedBorder());
		} else {
			this.setBorder(null);
		}
	}

	/**
	 * ���ÿؼ����뷽ʽ
	 * 
	 * @param alignment
	 *            FlowLayout.LEFT = 0; FlowLayout.CENTER = 1; FlowLayout.RIGHT =
	 *            2
	 */
	public void setAlignment(int alignment) {
		this.setLayout(new FlowLayout(alignment, 5, 4));
	}
}
