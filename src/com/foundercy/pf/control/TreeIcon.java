package com.foundercy.pf.control;

//////tree��ʾͼ��������/////////////////
public class TreeIcon {
  private String rootIcon;  //��ͼ��
  private String parentIcon;   //���׽ڵ�ͼ��
  private String parentHitIcon; //������չ��ͼ��
  private String childIcon;  //�ӽڵ�ͼ��
  private String childHitIcon;   ////�ӽڵ�����ͼ��
  public TreeIcon()
  {
      rootIcon="";
      parentIcon="";
      parentHitIcon="";
      childIcon="";
      childHitIcon="";
   }

  public String  getRootIcon() { return rootIcon; }
  public void setRootIcon(String icon) { rootIcon = icon; }
  public String  getParentIcon() { return parentIcon; }
  public void setParentIcon(String icon) { parentIcon = icon; }
  public String  getParentHitIcon() { return parentHitIcon; }
  public void setParentHitIcon(String icon) { parentHitIcon = icon; }
  public String  getChildIcon() { return childIcon; }
  public void setChildIcon(String icon) { childIcon = icon; }
  public String  getChildHitIcon() { return childHitIcon; }
  public void setChildHitIcon(String icon) { childHitIcon = icon; }

}
