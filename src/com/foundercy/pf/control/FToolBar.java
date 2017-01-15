/*
 * $Id: FToolBar.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import com.foundercy.pf.control.util.SubControlFinder;

/**
 * <p>
 * Title: ������
 * </p>
 * <p>
 * Description:������
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
public class FToolBar extends JToolBar implements Control, Compound {

	private static final long serialVersionUID = -4790873047569422082L;

	private String id;

	private Control parent;

	private java.util.List subControls = new ArrayList();

	/**
	 * ������Ĭ�ϸ߶�
	 */
	public static final int TOOLBAR_HEIGHT = 44;

	/**
	 * ���캯��
	 */
	public FToolBar() {
		super();
		init();
	}

	/**
	 * ���캯��
	 * 
	 * @param orientation
	 *            ���ù������ķ���FToolBar.HORIZONTAL��ˮƽ��FToolBar.VERTICAL����ֱ
	 */
	public FToolBar(int orientation) {
		super(orientation);
		init();
	}

	/**
	 * ��ʼ���ؼ�
	 */
	public void init() {
		super.setFloatable(false);// ���ù����������ƶ�
		super.setRollover(true);
		// this.setBorder(BorderFactory.createEtchedBorder());//���ñ߿�
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
	 * �õ��ؼ�id
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
	 * �򹤾����������ӿؼ�
	 * 
	 * @param control
	 *            �ؼ�
	 */
	public void addControl(Control control) {
		if (control instanceof FButton) {
			FButton btn = (FButton) control;
			Rectangle2D rect = btn.getFontMetrics(btn.getTitleFont())
					.getStringBounds(btn.getTitle(), btn.getGraphics());
			int btnWidth = (int) rect.getWidth() + 20;
			if (btnWidth < TOOLBAR_HEIGHT) {
				btnWidth = TOOLBAR_HEIGHT;
			}
			btn.setPreferredSize(btnWidth + ", " + TOOLBAR_HEIGHT);

			btn.setVerticalTextPosition(SwingConstants.BOTTOM);
			btn.setHorizontalTextPosition(SwingConstants.CENTER);
			btn.setToolTipText(((FButton) control).getTitle());

			/* updated by tianlu 2008-01-08 start */
			// �����ťû����ʾ��Ϣ����ô��ť����ʾ��Ϣ����Ϊ��ť�ı���
			if (((FButton) control).getToolTipText() == null) {
				btn.setToolTipText(((FButton) control).getTitle());
			}
			// btn.setToolTipText(((FButton) control).getTitle());
			/* updated by tianlu 2008-01-08 end */

			// btn.setMargin(new Insets(5,0,0,0));
		} else if (control instanceof FDropDownButton) {
			FDropDownButton btn = (FDropDownButton) control;
			Rectangle2D rect = btn.getFontMetrics(btn.getTitleFont())
					.getStringBounds(btn.getTitle(), btn.getGraphics());
			int btnWidth = (int) rect.getWidth() + 32;
			if (btnWidth < TOOLBAR_HEIGHT) {
				btnWidth = TOOLBAR_HEIGHT;
			}
			btn.setPreferredSize(new Dimension(TOOLBAR_HEIGHT, TOOLBAR_HEIGHT));
			btn.setMaximumSize(new Dimension(btnWidth, TOOLBAR_HEIGHT));
			btn.setMinimumSize(new Dimension(btnWidth, TOOLBAR_HEIGHT));
			btn.setToolTipText(btn.getTitle());
		}

		if (control instanceof FToolBarSeparator)
			addSeparator();
		else
			add((Component) control);

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
	 * �õ����Ͽؼ�������ֱ���ӿؼ�
	 * 
	 * @return ���Ͽؼ�������ֱ���ӿؼ�
	 */
	public List getSubControls() {
		if (this.subControls.isEmpty())
			return null;
		else
			return this.subControls;
	}

	/**
	 * ���ȫ���ӿؼ�
	 */
	public void removeAllSubControls() {
		this.removeAll();
		subControls.clear();
	}

	/**
	 * A toolbar-specific separator. An object with dimension but no contents
	 * used to divide buttons on a tool bar into groups.
	 */
	static public class FToolBarSeparator extends JToolBar.Separator implements
			Control {
		private static final long serialVersionUID = -952650274547301223L;

		private String id;

		private Control parent;

		/**
		 * Creates a new toolbar separator with the default size as defined by
		 * the current look and feel.
		 */
		public FToolBarSeparator() {
			super(); // let the UI define the default size
		}

		/**
		 * Creates a new toolbar separator with the specified size.
		 * 
		 * @param size
		 *            the <code>Dimension</code> of the separator
		 */
		public FToolBarSeparator(Dimension size) {
			super(size);
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
		 * ����id
		 * 
		 * @param id
		 *            �ؼ�id
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

}
