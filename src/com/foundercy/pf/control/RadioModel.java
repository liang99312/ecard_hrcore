/*
 * filename:  RadioModel.java
 *
 * Version: 1.0
 *
 * Date: 2006-2-6
 *
 * Copyright notice:  2006 by Founder Sprint 1st CO. Ltd
 */

package com.foundercy.pf.control;


/**
 * <p>Title: 单选控件数据模型 </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: foundercy</p>
 * @author
 * @version 1.0
 */
public class RadioModel implements RefModel
{
	private Object[][] data = new Object[][] { { "0", "Yes" }, { "1", "No" } };

  public RadioModel() {

  }

  /**
   * 得到单行按钮个数
   * @return 按钮个数
   */
  public int getRadioCount() {
     if(data == null || data.length == 0)
       return 0;
     else
       return data.length;
  }
  /**
   * 得到按钮的值
   * @param radioIndex 按钮的序号
   * @return 按钮的值
   */
  public Object getValueAt(int radioIndex) {
    if(data == null || data.length == 0 || radioIndex>=data.length)
      return null;
    return data[radioIndex][0];

  }

	/** 
	 * 得到按钮的显示值实际值数组
	 * @return 对象数组
	 */
	public Object[][] getData() {
		return data;
	}

	/**
	 * 设置按钮的显示值实际值数组
	 * @param data 对象数组
	 */
	public void setData(Object[][] data) {
		this.data = data;
	}

  /**
   * 通过名字获得符合该名字的值
   * @param name 显示名称
   * @return 值对象
   */
  public Object[] getValueByName(String name) {
    for(int i=0; data!=null && i<data.length; i++) {
      if(data[i][1]!=null && data[i][1].equals(name))
        return new Object[]{data[i][0]};
    }
    return null;
  }

  /**
   * 通过值获得显示的名字
   * @param value 实际值对象
   * @return 显示名称
   */
  public String getNameByValue(Object value) {
    if(value == null) return null;
    for(int i=0; data!=null && i<data.length; i++) {
      if(data[i][0]!=null && data[i][0].equals(value))
        return (String)data[i][1];
    }
    return null;

  }
}
