/*
 * $Id: FDialog.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.util.ArrayList;

import javax.swing.JDialog;

import com.foundercy.pf.control.util.SubControlFinder;

/**
 * <p>
 * Title: �Ի���ؼ�
 * </p>
 * <p>
 * Description:
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
 * 
 */
public class FDialog extends JDialog implements Control, Compound {

	private static final long serialVersionUID = 5596399214669230142L;

	private java.util.List subControls = new ArrayList();

	private String id;

	private Control parent;

	/**
	 * ���캯�� �����Ի���ؼ�
	 */
	public FDialog() {
		super();
	}

	/**
	 * ���캯��
	 * 
	 * @param owner
	 *            �Ի����ӵ����
	 */
	public FDialog(Frame owner) {
		super(owner);
	}

	/**
	 * ���캯��
	 * 
	 * @param owner
	 *            �Ի����ӵ����
	 * @param modal
	 *            �Ƿ���ģ̬
	 */
	public FDialog(Frame owner, boolean modal) {
		super(owner, null, modal);
	}

	/**
	 * ���캯��
	 * 
	 * @param owner
	 *            �Ի����ӵ����
	 * @param title
	 *            �Ի������
	 */
	public FDialog(Frame owner, String title) {
		super(owner, title, false);
	}

	/**
	 * ���캯��
	 * 
	 * @param owner
	 *            �Ի����ӵ����
	 * @param title
	 *            �Ի������
	 * @param modal
	 *            �Ƿ���ģ̬
	 */
	public FDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);

	}

	/**
	 * ���캯��
	 * 
	 * @param owner
	 *            �Ի����ӵ����
	 * @param title
	 *            �Ի������
	 * @param modal
	 *            �Ƿ���ģ̬
	 * @param gc
	 *            Ŀ�����ͼ����Ϊ������Ի���ӵ���߽�����ͬ
	 * 
	 */
	public FDialog(Frame owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);

	}

	/**
	 * ���캯��
	 * 
	 * @param owner
	 *            �Ի����ӵ����
	 */
	public FDialog(Dialog owner) {
		super(owner, false);
	}

	/**
	 * ���캯��
	 * 
	 * @param owner
	 *            �Ի����ӵ����
	 * @param modal
	 *            �Ƿ���ģ̬
	 */
	public FDialog(Dialog owner, boolean modal) {
		super(owner, null, modal);
	}

	/**
	 * ���캯��
	 * 
	 * @param owner
	 *            �Ի����ӵ����
	 * @param title
	 *            �Ի������
	 */
	public FDialog(Dialog owner, String title) {
		super(owner, title, false);
	}

	/**
	 * ���캯��
	 * 
	 * @param owner
	 *            �Ի����ӵ����
	 * @param title
	 *            �Ի������
	 * @param modal
	 *            �Ƿ���ģ̬
	 */
	public FDialog(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);

	}

	/**
	 * ���캯��
	 * 
	 * @param owner
	 *            �Ի����ӵ����
	 * @param title
	 *            �Ի������
	 * @param modal
	 *            �Ƿ���ģ̬
	 * @param gc
	 *            Ŀ�����ͼ����Ϊ������Ի���ӵ���߽�����ͬ
	 * 
	 */
	public FDialog(Dialog owner, String title, boolean modal,
			GraphicsConfiguration gc) {

		super(owner, title, modal, gc);

	}

	/**
	 * �����ӿؼ�
	 * 
	 * @param control
	 *            �ӿؼ�
	 */
	public void addControl(Control control) {
		this.addControl(control, new TableConstraints(1, 1, false));
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
		super.getContentPane().add((Component) control,
				(TableConstraints) contraint);
		this.subControls.add(control);
	}


	/**
	 * �õ����Ͽؼ�������ֱ���ӿؼ�
	 * 
	 * @return ���Ͽؼ�������ֱ���ӿؼ�
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
	 * ���ð�ťid
	 * 
	 * @param id
	 *            ��ťid
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * �õ���ťid
	 * 
	 * @return ��ťid
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
