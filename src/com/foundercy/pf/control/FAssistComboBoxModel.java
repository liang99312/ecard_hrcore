package com.foundercy.pf.control;

public interface FAssistComboBoxModel extends FComboBoxModel
{
	/**
	 * 得到数据源
	 * @return 数据源的内容
	 */
	public String getDataSource();
	
	/**
	 * 设置数据源
	 * @return 数据源的内容
	 */
	public void setDataSource(String source);	

}
