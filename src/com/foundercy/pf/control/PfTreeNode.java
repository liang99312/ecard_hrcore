package com.foundercy.pf.control;

public class PfTreeNode
{
	public static final String CHECK_NO = "0"; //0����ѡ
	public static final String CHECK_PART = "1"; //1����ѡ
	public static final String CHECK_ALL = "2"; //2��ȫѡ

	private String showContent = ""; //��ʾֵ
	private String value = ""; //ʵ��ֵ
	private boolean isLeaf = false; //�Ƿ�ΪҶ�ڵ�
	private boolean isSelect=false; //�Ƿ���checkbox�б�ѡ��
	private Object customerObject = null; //�û�����
	private String checkStatus = CHECK_NO; //��ѡ��״̬��0����ѡ��1����ѡ��2��ȫѡ��
	
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
     * �õ�ѡ��״̬
     * @return ѡ��״̬
     */
	public String getCheckStatus() {
		return checkStatus;
	}

	/**
	 * ����ѡ��״̬
	 * @param checkStatus ѡ��״̬
	 */
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

}
