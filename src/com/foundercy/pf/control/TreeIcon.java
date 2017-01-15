package com.foundercy.pf.control;

//////tree显示图标设置类/////////////////
public class TreeIcon {
  private String rootIcon;  //跟图标
  private String parentIcon;   //父亲节点图标
  private String parentHitIcon; //父亲扩展后图标
  private String childIcon;  //子节点图标
  private String childHitIcon;   ////子节点点击后图标
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
