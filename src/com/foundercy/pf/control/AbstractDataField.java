/*
 * filename:  AbstractDataField.java
 *
 * Version: 1.0
 *
 * Date: 2005-12-30
 *
 * Copyright notice:  2005 by Founder Sprint 1st CO. Ltd
 */
package com.foundercy.pf.control;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.EventListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.foundercy.pf.util.Tools;

//import com.foundercy.pf.util.Tools;

/**
 * <p>Title: �����ؼ������� </p>
 * <p>Description: ����+�ؼ��ĳ����� </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: foundercy</p>
 * @author yangbo
 * @version 1.0
 */
public abstract class AbstractDataField extends JPanel implements DataField
{
	private String id;
	private Control parent;
	private Object value;
	//private boolean adapting = true;  // �Ƿ�����Ӧ
	private String leftOrRight = "LEFT";  // �ؼ�����ʾ���"LEFT"Ϊ���⿿��"RIGHT"Ϊ���⿿��
	private JComponent editor;  // �ؼ��д��༭�Ĳ���
	private JLabel titleLabel;  // ������ʾ����ı�ǩ
	private DataFieldLayout dfLayout;  // �ؼ��Ĳ��ֹ�����
	private int layoutHgap = 0;  // DataFieldLayout�пؼ��ļ�϶
	private String fontName = "����";  // ��������
	private int fontStyle = 0;  // ������ʽ
	private int fontSize = 16;  // �����С
	private JScrollPane scrollPane = new JScrollPane();
	private boolean is_must_input;
	
	public AbstractDataField()
	{
		titleLabel = new JLabel("");  // �����ؼ��ı�ǩ
//		editor = getEditor();  // �õ��༭����
		this.dfLayout = new DataFieldLayout();  // �������ֶ���
	}
	
	/**
	 * ���ÿؼ�ID
	 * @param id �ؼ�ID
	 */
	public void setId(String id)
	{
		this.id = id;
	}
	
	/**
	 * �õ��ؼ�ID
	 * @return �ؼ�ID
	 */
	public String getId()
	{
		return this.id;
	}
	
	/**
	 * �õ������ÿؼ�����һ��ؼ�
	 * @return �ؼ�����
	 */
	public Control getParentControl()
	{
		return this.parent;
	}
	
	/**
	 * ������һ��ؼ�
	 * @param parent �ؼ�����
	 */
	public void setParentControl(Control parent)
	{
		this.parent = parent;
	}

	/**
	 * �õ��ؼ��ı���
	 * @return ��������
	 */
	public String getTitle()
	{
		return this.titleLabel.getText();
	}
	
	/**
	 * ���ÿؼ�����
	 * @param title ��������
	 */
	public void setTitle(String title)
	{
		if(title == null){
			this.titleLabel.setText("");
		}
		this.titleLabel.setText(title);
	}
	
	/**
	 * �жϱ����Ƿ�ɼ�
	 * @return false��ʾ���ɼ���true��ʾ�ɼ� 
	 */
	public boolean isTitleVisible()
	{
		return this.titleLabel.isVisible();
	}
	
	/**
	 * ���ñ����Ƿ�ɼ�
	 * @param visible false��ʾ���ɼ���true��ʾ�ɼ�
	 */
	public void setTitleVisible(boolean visible)
	{
		this.titleLabel.setVisible(visible);
	}
	
	/**
	 * �õ��������ɫ
	 * @return #RRGGBB��ɫ�ַ���
	 */
	public Color getTitleColor()
	{
		return this.titleLabel.getForeground();
	}
	
	/**
	 * ���ñ������ɫ
	 * @param color #RRGGBB��ɫ�ַ���
	 */
	public void setTitleColor(String sColor)
	{
		this.titleLabel.setForeground(Tools.stringToColor(sColor));
	}
	
	/**
	 * �жϿؼ��Ƿ���Ա༭
	 * @return falseΪ���ɱ༭��trueΪ�ɱ༭
	 */
	public boolean isEditable()
	{
		return false;
	}
	
	/**
	 * ���ÿؼ��Ƿ�ɱ༭������Ӧ�Դ˺�����������
	 * @param editable falseΪ���ɱ༭��trueΪ�ɱ༭
	 */
	public void setEditable(boolean editable)
	{
		
	}
	
	/**
	 * �õ��ؼ���ֵ
	 * @return �ؼ���ֵ����
	 */
	public Object getValue()
	{
		return this.value;
	}
	
	/**
	 * ���ÿؼ���ֵ
	 * @param value �ؼ���ֵ����
	 */
	public void setValue(Object value)
	{
		this.value = value;
	}
	
	/**
	 * �ؼ�����ʾ�Ƿ�����Ӧ
	 * @return falseΪ������Ӧ��trueΪ����Ӧ
	 */
	public boolean isTitleAdapting()
	{
		return this.dfLayout.isTitleAdapter();
	}
	
	/**
	 * ���ÿؼ�����ʾ�Ƿ�����Ӧ
	 * @param adapting falseΪ������Ӧ��trueΪ����Ӧ
	 */
	public void setTitleAdapting(boolean adapting)
	{
//		this.adapting = adapting;
//		if (adapting == true)
//		{
//		float f; 
//	    Rectangle2D rect = this.getFontMetrics(this.getTitleFont())
//	      .getStringBounds(this.getTitle(),this.getGraphics());
//	     double w = rect.getWidth();	          
//         dfLayout.setTitleAdapter(true);
//         dfLayout.setLeftWidth((int)w);
//		}
//		else
//		{
//			dfLayout.setTitleAdapter(false);
//		}
       	this.dfLayout.setTitleAdapter(adapting);
	}
	
	/**
	 * �õ��ؼ���ͬ���ֵķָ�����
	 * @return �ָ�����
	 */
	public float getProportion()
	{
		return this.dfLayout.getDivider();
	}
	
	/**
	 * ���ÿؼ���ͬ���ֵķָ�����
	 * @param proportion �ָ�����
	 */
	public void setProportion(float proportion)
	{
		//this.proportion = proportion;
		this.dfLayout.setDivider(proportion);
	}
	
	/**
	 * �õ��������ʾλ��
	 * @return "LEFT"��ʾ���⿿��"RIGHT"��ʾ���⿿��
	 */
	public String getTitlePosition()
	{
		return this.leftOrRight;
	}
	
	/**
	 * ���ñ������ʾλ��
	 * @param position "LEFT"��ʾ���⿿��"RIGHT"��ʾ���⿿��
	 */
	public void setTitlePosition(String leftOrRight)
	{
		this.leftOrRight = leftOrRight;
		dfLayout.removeLayoutComponent(this.titleLabel);
		if (editor instanceof JTextArea) {
			dfLayout.removeLayoutComponent(this.scrollPane);
		} else {
			dfLayout.removeLayoutComponent(this.editor);
		}
		if(this.leftOrRight.equalsIgnoreCase("LEFT"))
		{
		    dfLayout.addLayoutComponent("first",titleLabel);
		    if (editor instanceof JTextArea) {
				dfLayout.addLayoutComponent("second",this.scrollPane);
			} else {
				dfLayout.addLayoutComponent("second",editor);
			}
		}
		else
		{
		    dfLayout.addLayoutComponent("second",titleLabel);
		    if (editor instanceof JTextArea) {
				dfLayout.addLayoutComponent("first",this.scrollPane);
			} else {
				dfLayout.addLayoutComponent("first",editor);
			}
		}		
	}
		
	/**
	 * ����һ���ؼ�ֵ�ı���¼������ӿ�
	 *
	 * @param vcl �������¼������ӿ�
	 */
	public void addValueChangeListener(ValueChangeListener vcl) {
		if(null != vcl) {
			this.listenerList.add(ValueChangeListener.class,vcl);
		}
	}

	/**
	 * ɾ��һ���ؼ�ֵ�ı���¼������ӿ�
	 *
	 * @param vcl ��ɾ���¼������ӿ�
	 */
	public void removeValueChangeListener(ValueChangeListener vcl) {
		if(null != vcl) {
			this.listenerList.remove(ValueChangeListener.class,vcl);
		}
	}

	/**
	 * �����ؼ�ֵ�ı���¼� 
	 */
	protected void fireValueChange(Object oldValue,Object newValue) {

		if(!isEqual(oldValue,newValue)) {
			ValueChangeEvent vce = new ValueChangeEvent(this,oldValue,newValue);

			EventListener[] els = this.listenerList.getListeners(ValueChangeListener.class);

			int elsLen = els.length;

			for(int i = 0;i < elsLen;i++) {
				((ValueChangeListener)els[i]).valueChanged(vce);
			}
		}
	}

	/**
	 * ���ӽ��������
	 *
	 * @param fl �����ӵĽ��������
	 */
	public void addDataFieldFocusListener(FocusListener fl) {
		if(null != fl) {
			this.listenerList.add(FocusListener.class,fl);
		}
	}

	/**
	 * ɾ�����������
	 *
	 * @param fl ��ɾ���Ľ��������
	 */
	public void removeDataFieldFocusListener(FocusListener fl) {
		if(null != fl) {
			this.listenerList.remove(FocusListener.class,fl);
		}
	}

	/**
	 * �����ؼ������õ��¼� 
	 */
	protected void fireDataFieldFocusGained(FocusEvent fe) {

		EventListener[] els = this.listenerList.getListeners(FocusListener.class);

		int elsLen = els.length;

		for (int i = 0; i < elsLen; i++) {
			((FocusListener) els[i]).focusGained(fe);
		}

	}

	/**
	 * �����ؼ������뿪���¼�
	 * @param fe ����¼�
	 */
	protected void fireDataFieldFocusLost(FocusEvent fe) {

		EventListener[] els = this.listenerList.getListeners(FocusListener.class);

		int elsLen = els.length;

		for (int i = 0; i < elsLen; i++) {
			((FocusListener) els[i]).focusLost(fe);
		}

	}

	//�ж����������Ƿ����
	//�˴���ʹ��Object.equals(Object o)������ԭ���ǣ�
	//���o1��o2��������һ��Ϊnullʱ��Object.equals(Object o)�������׳���ָ���쳣
	//�ڴ˷�����,o1��o2��Ϊnullʱ������ֵΪtrue��������һ��Ϊnullʱ������ֵΪfalse
	//��o1��o2����Ϊnullʱ��ʹ��o1.equals(o2)��Ϊ����ֵ
	protected boolean isEqual(Object o1,Object o2) {
		return null != o1?(null != o2?o1.equals(o2):false):(null == o2);
	}

	/**
	 * ���ÿؼ�Ϊ��ǰ���뽹��
	 *
	 */
	final public void setFocus() {
		if( !SwingUtilities.isEventDispatchThread() ){
		    SwingUtilities.invokeLater( new Runnable() {
		        public void run() {
		            setFocusNow();
		        }
		    });
		}else{
		    setFocusNow();
		}
	}

	/** �������ÿؼ�Ϊ��ǰ���뽹�㣬��Ҫ����ʵ�֡�
	 * ������ʵ��ʱ�����ؿ��ǵ�ǰ�̣߳��Ƿ�Swing�¼������߳�
	 */
	protected abstract void setFocusNow();
	
	
	/**
	 * �õ���ǰʵ�ʲ����Ŀؼ�
	 * @return �ؼ�����
	 */
	public JComponent getEditor()
	{
		return null;
	}
	
	
	/**
	 * ������������
	 * @param name ��������
	 */
	public void setFontName(String name)
	{
		this.fontName = name;
	}
	
	/**
	 * �õ���������
	 * @return ��������
	 */
	public String getFontName()
	{
		return this.fontName;
	}
	
	/**
	 * ����������ʽ
	 * @param style ������ʽ
	 */
	public void setFontStyle(int style)
	{
		this.fontStyle = style;
	}
	
	/**
	 * �õ�������ʽ
	 * @return ������ʽ
	 */
	public int getFontStyle()
	{
		return this.fontStyle;
	}
	
	/**
	 * ���������С
	 * @param size �����С
	 */
	public void setFontSize(int size)
	{
		this.fontSize = size;
	}
	
	/**
	 * �õ������С
	 * @return �����С
	 */
	public int getFontSize()
	{
		return this.fontSize;
	}
	
	/**
	 * ��������
	 */
	public void setTitleFont(Font font)
	{
		if (font != null)
		{
			this.titleLabel.setFont(font);
		}
	}
	
	/**
	 * �õ�����
	 */
	public Font getTitleFont()
	{
			return this.titleLabel.getFont();
	}
	
	protected void allLayout()
	{
		setLayout(this.dfLayout);
		
		this.dfLayout.setHgap(this.layoutHgap);
		editor = getEditor();// �õ��༭����
		
		if(this.leftOrRight.equalsIgnoreCase("LEFT"))
		{
			this.add(DataFieldLayout.FIRST,titleLabel);
	        if (editor instanceof JTextArea) {
	        	scrollPane.getViewport().add(editor);
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollPane.setVisible(true);
				this.add(DataFieldLayout.SECOND,scrollPane);
	        } else {
	        	this.add(DataFieldLayout.SECOND,editor);
	        }
			
		}
		else {
			this.add(DataFieldLayout.SECOND,titleLabel);
	        if (editor instanceof JTextArea) {
	        	scrollPane.getViewport().add(editor);
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollPane.setVisible(true);
				this.add(DataFieldLayout.FIRST,scrollPane);
	        } else {
	        	this.add(DataFieldLayout.FIRST,editor);
	        }
		}

	}

	public int getLayoutHgap() {
		return layoutHgap;
	}

	public void setLayoutHgap(int layoutHgap) {
		this.layoutHgap = layoutHgap;
	}
	
	/**
	 * �ж��Ƿ����¼��
	 * @return
	 */
	public boolean is_must_input() {
		return this.is_must_input;
	}
	
	/**
	 * �����Ƿ����¼��
	 * @param is_must_input
	 */
	public void setIs_must_input(boolean is_must_input) {
		this.is_must_input = is_must_input;
	}
}
