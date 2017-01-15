/*
 * $Id: FList.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import java.util.Vector;

import javax.swing.JList;
import javax.swing.ListModel;

/**
 * <p>
 * Title: �����б�ؼ�
 * </p>
 * <p>
 * Description:�����б�ؼ�
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * <p>
 * Company: ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * 
 * @author �ƽ� 2008��4��10������
 * @version $Revision: 1.1.1.1 $
 * 
 */
public class FList extends JList implements Control {

	private static final long serialVersionUID = 1967354635353999704L;

	private String id;

	private Control parent;

	/**
	 * ���캯��
	 */
	public FList() {
		super();
	}

	/**
	 * ���캯��
	 * 
	 * @param dataModel
	 *            ����ģ��
	 */
	public FList(ListModel dataModel) {
		super(dataModel);
	}

	/**
	 * ���캯��
	 * 
	 * @param listData
	 *            �б�����
	 */
	public FList(final Object[] listData) {
		super(listData);
	}

	/**
	 * ���캯��
	 * 
	 * @param listData
	 *            �б�����
	 */
	public FList(final Vector listData) {
		super(listData);
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

}
