/*
 * $Id: FBlankPanel.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import javax.swing.JPanel;

/**
 * <p>
 * Title: �հ������
 * </p>
 * <p>
 * Description: �հ������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005-2008 ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * <p>
 * Company: ����������Ԫ�Ƽ���չ���޹�˾
 * </p>
 * 
 * @author Fangyi
 * @author �ƽ� 2008��4��10������
 * @version $Revision: 1.1.1.1 $
 */
public class FBlankPanel extends JPanel implements Control {
	private static final long serialVersionUID = -8614967567388214741L;

	/**
	 * ���캯��
	 */
	public FBlankPanel() {
	}

	/**
	 * ��ȡid��Ϊʵ�ֽӿڣ���ʵ���ô���
	 * 
	 * @return �ؼ�id
	 * 
	 */
	public String getId() {
		return null;
	}

	/**
	 * ��ȡ���ؼ���Ϊʵ�ֽӿڣ���ʵ���ô���
	 * 
	 * @return ���ؼ�
	 * 
	 */
	public Control getParentControl() {
		return null;
	}

	/**
	 * ���ÿؼ�id��Ϊʵ�ֽӿڣ���ʵ���ô���
	 * 
	 * @param id
	 *            �ؼ�id
	 * 
	 */
	public void setId(String id) {
	}

	/**
	 * ���ø��ؼ���Ϊʵ�ֽӿڣ���ʵ���ô���
	 * 
	 * @param parent
	 *            ���ؼ�
	 */
	public void setParentControl(Control parent) {
	}

}
