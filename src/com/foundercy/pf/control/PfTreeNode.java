package com.foundercy.pf.control;

public class PfTreeNode
{
	public static final String CHECK_NO = "0"; //0：不选
	public static final String CHECK_PART = "1"; //1：半选
	public static final String CHECK_ALL = "2"; //2：全选

	private String showContent = ""; //显示值
	private String value = ""; //实际值
	private boolean isLeaf = false; //是否为叶节点
	private boolean isSelect=false; //是否在checkbox中被选中
	private Object customerObject = null; //用户对象
	private String checkStatus = CHECK_NO; //新选中状态（0：不选，1：半选，2：全选）
	
	public PfTreeNode()
	{
		
	}
	
    public boolean  getIsSelect() 
    { 
    	return this.isSelect; 
    }
    
    public void setIsSelect(boolean isSelect) 
    { 
    	this.isSelect = isSelect; 
    }
    
    public void setShowContent(String showContent)
    {
    	this.showContent = showContent;
    }
    
    public String getShowContent()
    {
    	return this.showContent;
    }
    
    public void setValue(String value)
    {
    	this.value = value;
    }
    
    public String getValue()
    {
    	return this.value;
    }
    
    public void setIsLeaf(boolean isLeaf)
    {
    	this.isLeaf = isLeaf;
    }
    
    public boolean getIsLeaf()
    {
    	return this.isLeaf;
    }
    
    public String toString()
    {
    	return this.showContent;
    }
    
    public void setCustomerObject(Object customerObject) {
    	this.customerObject = customerObject;
    }
    
    public Object getCustomerObject() {
    	return this.customerObject;
    }

    /**
     * 得到选中状态
     * @return 选中状态
     */
	public String getCheckStatus() {
		return checkStatus;
	}

	/**
	 * 设置选中状态
	 * @param checkStatus 选中状态
	 */
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

}
