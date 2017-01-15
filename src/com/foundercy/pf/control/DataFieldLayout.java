package com.foundercy.pf.control;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.geom.Rectangle2D;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

/**
 * 
 * DataField�򵥿ؼ��Ĳ��ֹ�����
 */
public class DataFieldLayout implements LayoutManager {

	/** ����������addLayoutComponent������������ǵ�һ���ؼ� */
	public static final String FIRST = "first";
	/** ����������addLayoutComponent������������ǵڶ����ؼ� */
	public static final String SECOND = "second";
	
	/** ���ֹ������еĵ�һ���ؼ� */
	private Component firstComponent;
	/** ���ֹ������еĵڶ����ؼ� */
	private Component secondComponent;

	/**
	 * �Բ��ֹ������еĵ�һ���ؼ������ĳߴ磬�ڿؼ���ռ�õı���
	 * 
	 * @uml.property name="layoutScale" multiplicity="(0 1)"
	 */
	private float layoutScale = 0.3f;

	/**
	 * ��񲼾��У��ؼ��ĺ�����
	 * 
	 * @uml.property name="hgap" multiplicity="(0 1)"
	 */
	private int hgap = 0;

	/**
	 * ��񲼾��У��ؼ�������ҳ�߾�
	 * 
	 * @uml.property name="vmargin" multiplicity="(0 1)"
	 */
	private int vmargin = 0;

	/**
	 * ��񲼾��У��ؼ��ĺ���ҳ�߾�
	 * 
	 * @uml.property name="hmargin" multiplicity="(0 1)"
	 */
	private int hmargin = 0;
	
	private boolean titleAdapter = false;
	

	public boolean isTitleAdapter()
	{
		return titleAdapter;
	}

	public void setTitleAdapter(boolean titleAdapter)
	{
		this.titleAdapter = titleAdapter;
	}

	/**
	 * ���캯��
	 */
	public DataFieldLayout() {
		this(0.3f);
	}
	
	/**
	 * ���캯��
	 * @param layoutScale �Բ��ֹ������еĵ�һ���ؼ������ĳߴ磬�ڿؼ���ռ�õı���
	 */
	public DataFieldLayout(float layoutScale) {
		setDivider(layoutScale);
	}

	/**
	 * �Բ��ֹ������еĵ�һ���ؼ����������ĳߴ磬�ڿؼ���ռ�õı���
	 * 
	 * @param layoutScale �ڿؼ���ռ�õı��������������ȡֵ��Χ��0-1�����ܵ���0����1��
	 * ���磬setLayoutScale(0.5f)����ô���Բ��ֹ������еĵ�һ���ؼ��ĳߴ�Ϊ�����ؼ���һ��
	 * 
	 * @uml.property name="layoutScale"
	 */
	public void setDivider(float layoutScale) {

		if (layoutScale > 1.0f || layoutScale < 0f) {
			throw new IllegalArgumentException(
				"layout scale exceed the range supportted");
		}
		this.layoutScale = layoutScale;
	}

	/**
	 * �Բ��ֹ������еĵ�һ���ؼ���ȡ�����ĳߴ磬�ڿؼ���ռ�õı���
	 * 
	 * @return �ڿؼ���ռ�õı���
	 * 
	 * @uml.property name="layoutScale"
	 */
	public float getDivider() {
		return this.layoutScale;
	}

	/**
	 * ȡ�ñ�񲼾��У��ؼ��ĺ�����
	 * @return ��񲼾��У��ؼ��ĺ�����
	 * 
	 * @uml.property name="hgap"
	 */
	public int getHgap() {
		return this.hgap;
	}

	/**
	 * ���ñ�񲼾��У��ؼ��ĺ�����
	 * @param hgap ��񲼾��У��ؼ��ĺ�����
	 * 
	 * @uml.property name="hgap"
	 */
	public void setHgap(int hgap) {
		this.hgap = hgap;
	}

	/**
	 * ȡ�ñ�񲼾��У��ؼ��ĺ���ҳ�߾�
	 * @return ��񲼾��У��ؼ��ĺ���ҳ�߾�
	 * 
	 * @uml.property name="hmargin"
	 */
	public int getHmargin() {
		return this.hmargin;
	}

	/**
	 * ���ñ�񲼾��У��ؼ��ĺ���ҳ�߾�
	 * @param hmargin ��񲼾��У��ؼ��ĺ���ҳ�߾�
	 * 
	 * @uml.property name="hmargin"
	 */
	public void setHmargin(int hmargin) {
		this.hmargin = hmargin;
	}

	/**
	 * ȡ�ñ�񲼾��У��ؼ�������ҳ�߾�
	 * @return ��񲼾��У��ؼ�������ҳ�߾�
	 * 
	 * @uml.property name="vmargin"
	 */
	public int getVmargin() {
		return this.vmargin;
	}

	/**
	 * ���ñ�񲼾��У��ؼ�������ҳ�߾�
	 * @param vmargin ��񲼾��У��ؼ�������ҳ�߾�
	 * 
	 * @uml.property name="vmargin"
	 */
	public void setVmargin(int vmargin) {
		this.vmargin = vmargin;
	}

	/**
	 * If the layout manager uses a per-component string,
	 * adds the component <code>comp</code> to the layout,
	 * associating it 
	 * with the string specified by <code>name</code>.
	 * 
	 * @param name the string to be associated with the component
	 * @param comp the component to be added
	 */
	public void addLayoutComponent(String name, Component comp) {
		synchronized (comp.getTreeLock()) {
		}
		if(FIRST.equals(name)) {
			this.firstComponent = comp;
		}else if(SECOND.equals(name)) {
			this.secondComponent = comp;
		}else if(null == name) {
			if(null == this.firstComponent) {
				this.firstComponent = comp;
			}else if(null == this.secondComponent) {
				this.secondComponent = comp;
			}else {
				throw new IllegalArgumentException("cannot add to layout:the number of Components must be <= 2");
			}
		}else {
			throw new IllegalArgumentException("cannot add to layout: unknown constraint: " + name);
		}
	}

	/**
	 * Removes the specified component from the layout.
	 * @param comp the component to be removed
	 */
	public void removeLayoutComponent(Component comp) {
		synchronized (comp.getTreeLock()) {
			if(comp == this.firstComponent) {
				this.firstComponent = null;
			}else if(comp == this.secondComponent) {
				this.secondComponent = null;
			}
		}

	}

	/**
	 * Calculates the preferred size dimensions for the specified 
	 * container, given the components it contains.
	 * @param parent the container to be laid out
	 *  
	 * @see #minimumLayoutSize
	 */
	public Dimension preferredLayoutSize(Container parent) {
		synchronized (parent.getTreeLock()) {

			//��ÿؼ��У���͸ߵ����ֵ
			int w = 0;
			int h = 0;

			Dimension d1 = null != this.firstComponent?this.firstComponent.getPreferredSize():new Dimension();
			Dimension d2 = null != this.secondComponent?this.secondComponent.getPreferredSize():new Dimension();

			if (null != this.firstComponent && this.firstComponent.isVisible()
				&& null != this.secondComponent && this.secondComponent.isVisible()) {

					w = (int) (d1.width / this.layoutScale);
					h = d2.height < d1.height ? d2.height : d1.height;

			} else if (null != this.firstComponent && (null == this.secondComponent || !this.secondComponent.isVisible())) {
				w = d1.width;
				h = d1.height;
				
			} else if (null != this.secondComponent && (null == this.firstComponent || !this.firstComponent.isVisible())) {
				w = d2.width;
				h = d2.height;
			} else {
				throw new IllegalArgumentException("cannot preferredLayoutSize:the number of Components is zero or all Components is invisible");
			}

			Insets insets = parent.getInsets();

			return new Dimension(
				insets.left + insets.right + w + hgap + 2 * hmargin,
				insets.top + insets.bottom + h  + 2 * vmargin);
		}
	}

	/** 
	 * Calculates the minimum size dimensions for the specified 
	 * container, given the components it contains.
	 * @param parent the component to be laid out
	 * @see #preferredLayoutSize
	 */
	public Dimension minimumLayoutSize(Container parent) {
		synchronized (parent.getTreeLock()) {
			//��ÿؼ��У���͸ߵ����ֵ
			int w = 0;
			int h = 0;

			Dimension d1 = null != this.firstComponent?this.firstComponent.getMinimumSize():new Dimension();
			Dimension d2 = null != this.secondComponent?this.secondComponent.getMinimumSize():new Dimension();

			if (null != this.firstComponent && this.firstComponent.isVisible()
				&& null != this.secondComponent && this.secondComponent.isVisible()) {

					w = (int) (d1.width / this.layoutScale);
					h = d2.height < d1.height ? d2.height : d1.height;

			} else if (null != this.firstComponent && (null == this.secondComponent || !this.secondComponent.isVisible())) {
				w = d1.width;
				h = d1.height;
				
			} else if (null != this.secondComponent && (null == this.firstComponent || !this.firstComponent.isVisible())) {
				w = d2.width;
				h = d2.height;
			} else {
				throw new IllegalArgumentException("cannot minimumLayoutSize:the number of Components is zero or all Components is invisible");
			}

			Insets insets = parent.getInsets();

			return new Dimension(
				insets.left + insets.right + w + hgap + 2 * hmargin,
				insets.top + insets.bottom + h + 2 * vmargin);
		}
	}

	/** 
	 * Lays out the specified container.
	 * @param parent the container to be laid out 
	 */
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			Insets insets = parent.getInsets();

			if(this.titleAdapter) {
				if(firstComponent instanceof JLabel){
					JLabel label = (JLabel)firstComponent;
				    Rectangle2D rect = label.getFontMetrics(label.getFont())
				      .getStringBounds(label.getText(),label.getGraphics());
				     double w = rect.getWidth();	          
			         //dfLayout.setTitleAdapter(true);
			         this.layoutScale = (float) (w/parent.getWidth());
			         
			        // int a = 0;
				}
			}
			
			//��ÿؼ��У���͸ߵ����ֵ
			int y = insets.top + vmargin;
			int h = parent.getHeight() - (insets.top + insets.bottom) - 2 * vmargin;
			int w = parent.getWidth() - (insets.left + insets.right) - 2 * hmargin;
			if (null != this.firstComponent && this.firstComponent.isVisible()
				&& null != this.secondComponent && this.secondComponent.isVisible()) {

					int x1 = insets.left + hmargin;
					int w1 = (int)(w * this.layoutScale);
					int w2 = w - w1 - hgap;
					//
					if (this.firstComponent instanceof JCheckBox)  w1 = 20;
					if (this.secondComponent instanceof JCheckBox)  w2 = 20;
					//
					this.firstComponent.setBounds(x1, y, w1, h);
					this.secondComponent.setBounds(x1+w1+hgap, y, w2, h);

			} else if (null != this.firstComponent && (null == this.secondComponent || !this.secondComponent.isVisible())) {
				int x1 = insets.left + hmargin;
				int w1 = w;
				//
				if (this.firstComponent instanceof JCheckBox)  w1 = 20;
				//
				this.firstComponent.setBounds(x1, y, w1, h);
				
			} else if (null != this.secondComponent && (null == this.firstComponent || !this.firstComponent.isVisible())) {
				int x2 = insets.left + hmargin;
				int w2 = w;
				//
				if (this.secondComponent instanceof JCheckBox)  w2 = 20;
				//
				this.secondComponent.setBounds(x2, y, w2, h);

			} else {
				throw new IllegalArgumentException("cannot layoutContainer:the number of Components is zero or all Components is invisible");
			}
		}

	}

}
