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
 * <p>Title: 基本控件抽象类 </p>
 * <p>Description: 标题+控件的抽象类 </p>
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
	//private boolean adapting = true;  // 是否自适应
	private String leftOrRight = "LEFT";  // 控件的显示风格，"LEFT"为标题靠左，"RIGHT"为标题靠右
	private JComponent editor;  // 控件中待编辑的部分
	private JLabel titleLabel;  // 用于显示标题的标签
	private DataFieldLayout dfLayout;  // 控件的布局管理器
	private int layoutHgap = 0;  // DataFieldLayout中控件的间隙
	private String fontName = "宋体";  // 字体名称
	private int fontStyle = 0;  // 字体样式
	private int fontSize = 16;  // 字体大小
	private JScrollPane scrollPane = new JScrollPane();
	private boolean is_must_input;
	
	public AbstractDataField()
	{
		titleLabel = new JLabel("");  // 创建控件的标签
//		editor = getEditor();  // 得到编辑对象
		this.dfLayout = new DataFieldLayout();  // 创建布局对象
	}
	
	/**
	 * 设置控件ID
	 * @param id 控件ID
	 */
	public void setId(String id)
	{
		this.id = id;
	}
	
	/**
	 * 得到控件ID
	 * @return 控件ID
	 */
	public String getId()
	{
		return this.id;
	}
	
	/**
	 * 得到包含该控件的上一层控件
	 * @return 控件对象
	 */
	public Control getParentControl()
	{
		return this.parent;
	}
	
	/**
	 * 设置上一层控件
	 * @param parent 控件对象
	 */
	public void setParentControl(Control parent)
	{
		this.parent = parent;
	}

	/**
	 * 得到控件的标题
	 * @return 标题内容
	 */
	public String getTitle()
	{
		return this.titleLabel.getText();
	}
	
	/**
	 * 设置控件标题
	 * @param title 标题内容
	 */
	public void setTitle(String title)
	{
		if(title == null){
			this.titleLabel.setText("");
		}
		this.titleLabel.setText(title);
	}
	
	/**
	 * 判断标题是否可见
	 * @return false表示不可见，true表示可见 
	 */
	public boolean isTitleVisible()
	{
		return this.titleLabel.isVisible();
	}
	
	/**
	 * 设置标题是否可见
	 * @param visible false表示不可见，true表示可见
	 */
	public void setTitleVisible(boolean visible)
	{
		this.titleLabel.setVisible(visible);
	}
	
	/**
	 * 得到标题的颜色
	 * @return #RRGGBB颜色字符串
	 */
	public Color getTitleColor()
	{
		return this.titleLabel.getForeground();
	}
	
	/**
	 * 设置标题的颜色
	 * @param color #RRGGBB颜色字符串
	 */
	public void setTitleColor(String sColor)
	{
		this.titleLabel.setForeground(Tools.stringToColor(sColor));
	}
	
	/**
	 * 判断控件是否可以编辑
	 * @return false为不可编辑，true为可编辑
	 */
	public boolean isEditable()
	{
		return false;
	}
	
	/**
	 * 设置控件是否可编辑，子类应对此函数进行重载
	 * @param editable false为不可编辑，true为可编辑
	 */
	public void setEditable(boolean editable)
	{
		
	}
	
	/**
	 * 得到控件的值
	 * @return 控件的值对象
	 */
	public Object getValue()
	{
		return this.value;
	}
	
	/**
	 * 设置控件的值
	 * @param value 控件的值对象
	 */
	public void setValue(Object value)
	{
		this.value = value;
	}
	
	/**
	 * 控件的显示是否自适应
	 * @return false为非自适应，true为自适应
	 */
	public boolean isTitleAdapting()
	{
		return this.dfLayout.isTitleAdapter();
	}
	
	/**
	 * 设置控件的显示是否自适应
	 * @param adapting false为非自适应，true为自适应
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
	 * 得到控件不同部分的分隔比例
	 * @return 分隔比例
	 */
	public float getProportion()
	{
		return this.dfLayout.getDivider();
	}
	
	/**
	 * 设置控件不同部分的分隔比例
	 * @param proportion 分隔比例
	 */
	public void setProportion(float proportion)
	{
		//this.proportion = proportion;
		this.dfLayout.setDivider(proportion);
	}
	
	/**
	 * 得到标题的显示位置
	 * @return "LEFT"表示标题靠左，"RIGHT"表示标题靠右
	 */
	public String getTitlePosition()
	{
		return this.leftOrRight;
	}
	
	/**
	 * 设置标题的显示位置
	 * @param position "LEFT"表示标题靠左，"RIGHT"表示标题靠右
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
	 * 增加一个控件值改变的事件监听接口
	 *
	 * @param vcl 被增加事件监听接口
	 */
	public void addValueChangeListener(ValueChangeListener vcl) {
		if(null != vcl) {
			this.listenerList.add(ValueChangeListener.class,vcl);
		}
	}

	/**
	 * 删除一个控件值改变的事件监听接口
	 *
	 * @param vcl 被删除事件监听接口
	 */
	public void removeValueChangeListener(ValueChangeListener vcl) {
		if(null != vcl) {
			this.listenerList.remove(ValueChangeListener.class,vcl);
		}
	}

	/**
	 * 触发控件值改变的事件 
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
	 * 增加焦点监听器
	 *
	 * @param fl 被增加的焦点监听器
	 */
	public void addDataFieldFocusListener(FocusListener fl) {
		if(null != fl) {
			this.listenerList.add(FocusListener.class,fl);
		}
	}

	/**
	 * 删除焦点监听器
	 *
	 * @param fl 被删除的焦点监听器
	 */
	public void removeDataFieldFocusListener(FocusListener fl) {
		if(null != fl) {
			this.listenerList.remove(FocusListener.class,fl);
		}
	}

	/**
	 * 触发控件焦点获得的事件 
	 */
	protected void fireDataFieldFocusGained(FocusEvent fe) {

		EventListener[] els = this.listenerList.getListeners(FocusListener.class);

		int elsLen = els.length;

		for (int i = 0; i < elsLen; i++) {
			((FocusListener) els[i]).focusGained(fe);
		}

	}

	/**
	 * 触发控件焦点离开的事件
	 * @param fe 鼠标事件
	 */
	protected void fireDataFieldFocusLost(FocusEvent fe) {

		EventListener[] els = this.listenerList.getListeners(FocusListener.class);

		int elsLen = els.length;

		for (int i = 0; i < elsLen; i++) {
			((FocusListener) els[i]).focusLost(fe);
		}

	}

	//判断两个对象是否相等
	//此处不使用Object.equals(Object o)方法的原因是：
	//如果o1，o2中任意有一个为null时，Object.equals(Object o)方法会抛出空指针异常
	//在此方法中,o1和o2都为null时，返回值为true，仅当有一个为null时，返回值为false
	//当o1和o2都不为null时，使用o1.equals(o2)作为返回值
	protected boolean isEqual(Object o1,Object o2) {
		return null != o1?(null != o2?o1.equals(o2):false):(null == o2);
	}

	/**
	 * 设置控件为当前输入焦点
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

	/** 立即设置控件为当前输入焦点，需要子类实现。
	 * 子类在实现时，不必考虑当前线程，是否Swing事件处理线程
	 */
	protected abstract void setFocusNow();
	
	
	/**
	 * 得到当前实际操作的控件
	 * @return 控件对象
	 */
	public JComponent getEditor()
	{
		return null;
	}
	
	
	/**
	 * 设置字体名称
	 * @param name 字体名称
	 */
	public void setFontName(String name)
	{
		this.fontName = name;
	}
	
	/**
	 * 得到字体名称
	 * @return 字体名称
	 */
	public String getFontName()
	{
		return this.fontName;
	}
	
	/**
	 * 设置字体样式
	 * @param style 字体样式
	 */
	public void setFontStyle(int style)
	{
		this.fontStyle = style;
	}
	
	/**
	 * 得到字体样式
	 * @return 字体样式
	 */
	public int getFontStyle()
	{
		return this.fontStyle;
	}
	
	/**
	 * 设置字体大小
	 * @param size 字体大小
	 */
	public void setFontSize(int size)
	{
		this.fontSize = size;
	}
	
	/**
	 * 得到字体大小
	 * @return 字体大小
	 */
	public int getFontSize()
	{
		return this.fontSize;
	}
	
	/**
	 * 设置字体
	 */
	public void setTitleFont(Font font)
	{
		if (font != null)
		{
			this.titleLabel.setFont(font);
		}
	}
	
	/**
	 * 得到字体
	 */
	public Font getTitleFont()
	{
			return this.titleLabel.getFont();
	}
	
	protected void allLayout()
	{
		setLayout(this.dfLayout);
		
		this.dfLayout.setHgap(this.layoutHgap);
		editor = getEditor();// 得到编辑对象
		
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
	 * 判断是否必须录入
	 * @return
	 */
	public boolean is_must_input() {
		return this.is_must_input;
	}
	
	/**
	 * 设置是否必须录入
	 * @param is_must_input
	 */
	public void setIs_must_input(boolean is_must_input) {
		this.is_must_input = is_must_input;
	}
}
