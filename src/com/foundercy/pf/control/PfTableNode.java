package com.foundercy.pf.control;

public class PfTableNode
{
	private String showContent = ""; //显示值
	private String value = ""; //实际值
	private boolean isSelect=false; //是否在checkbox中被选中
	
	public PfTableNode()
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
    
    public String toString()
    {
    	return this.showContent;
    }
}
