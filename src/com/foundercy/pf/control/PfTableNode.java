package com.foundercy.pf.control;

public class PfTableNode
{
	private String showContent = ""; //��ʾֵ
	private String value = ""; //ʵ��ֵ
	private boolean isSelect=false; //�Ƿ���checkbox�б�ѡ��
	
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
