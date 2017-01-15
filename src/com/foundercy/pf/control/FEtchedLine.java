/*
 * $Id: FEtchedLine.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import javax.swing.JComponent;

/**
 * <p>
 * Title: ˮƽ���߿ؼ�
 * </p>
 * <p>
 * Description:ˮƽ���߿ؼ�
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
public class FEtchedLine extends JComponent implements Control {

	private static final long serialVersionUID = 8182334228402251650L;

	private String id;

	private Control parent;

	/**
	 * ����һ��ˮƽ���߿ؼ�
	 */
	public FEtchedLine() {
		super();
		this.setBorder(new FEtchedBorder());
	}

	/**
	 * ����һ��ˮƽ�߿ؼ�,��ָ����͹����
	 * 
	 * @param etchType
	 *            �������������ͣ�͹��ģ�RAISED����͹��LOWERED
	 */
	public FEtchedLine(int etchType) {
		super();
		this.setBorder(new FEtchedBorder(etchType));
	}

	/**
	 * �õ��ؼ�id
	 * 
	 * @return ��ťid
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * ���ÿؼ�id
	 * 
	 * @param id
	 *            ��ťid
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

}
