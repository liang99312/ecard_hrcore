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
 * <p>Title: ��ѡ�ؼ�����ģ�� </p>
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
   * �õ����а�ť����
   * @return ��ť����
   */
  public int getRadioCount() {
     if(data == null || data.length == 0)
       return 0;
     else
       return data.length;
  }
  /**
   * �õ���ť��ֵ
   * @param radioIndex ��ť�����
   * @return ��ť��ֵ
   */
  public Object getValueAt(int radioIndex) {
    if(data == null || data.length == 0 || radioIndex>=data.length)
      return null;
    return data[radioIndex][0];

  }

	/** 
	 * �õ���ť����ʾֵʵ��ֵ����
	 * @return ��������
	 */
	public Object[][] getData() {
		return data;
	}

	/**
	 * ���ð�ť����ʾֵʵ��ֵ����
	 * @param data ��������
	 */
	public void setData(Object[][] data) {
		this.data = data;
	}

  /**
   * ͨ�����ֻ�÷��ϸ����ֵ�ֵ
   * @param name ��ʾ����
   * @return ֵ����
   */
  public Object[] getValueByName(String name) {
    for(int i=0; data!=null && i<data.length; i++) {
      if(data[i][1]!=null && data[i][1].equals(name))
        return new Object[]{data[i][0]};
    }
    return null;
  }

  /**
   * ͨ��ֵ�����ʾ������
   * @param value ʵ��ֵ����
   * @return ��ʾ����
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
