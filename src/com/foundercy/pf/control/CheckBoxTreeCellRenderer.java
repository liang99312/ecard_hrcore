/*
 * $Id: CheckBoxTreeCellRenderer.java,v 1.1.1.1 2009/04/07 08:12:32 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.foundercy.pf.util.Resource;

public class CheckBoxTreeCellRenderer extends DefaultTreeCellRenderer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3184057959206464832L;
	private JCheckBox checkBox = new JCheckBox();
	
	private boolean isShowPartCheckStatus = false;		//是否显示半选状态
	private String partCheckIconFilePath = null;		//半选图标文件路径

	public CheckBoxTreeCellRenderer()
	{
		//checkBox.setOpaque(false);
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		Component component = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);
		Object dmt = ((DefaultMutableTreeNode) value).getUserObject();
		//如果对象为PfTreeNode类型
		if(dmt instanceof PfTreeNode) {
			PfTreeNode node = (PfTreeNode) ((DefaultMutableTreeNode) value)
					.getUserObject();
			//设置checkbox显示内容
			checkBox.setText(node.toString());
			//设置checkbox是否选中
			checkBox.setSelected(node.getIsSelect());
			
	//		if (hasFocus)
	//			checkBox.setBackground(new Color(184, 190, 255));
	//		else
	//			checkBox.setBackground(new Color(255, 255, 255));
			if(sel) {
				//设置节点状态为全选
				node.setCheckStatus(PfTreeNode.CHECK_ALL);
				
				//设置checkbox前景色
				checkBox.setForeground(getTextSelectionColor());
				//设置checkbox背景色
				checkBox.setBackground(getBackgroundSelectionColor());
				checkBox.setIcon(null);
			}else {
//				if (node.getIsSelect()) {
//					if(this.isSelectedAllChildren((DefaultMutableTreeNode) value)) {
//						//checkBox.setForeground(Color.WHITE);
//						checkBox.setEnabled(true);
//					} else {
//						//checkBox.setForeground(getTextNonSelectionColor());
//						//checkBox.setBackground(new Color(255, 255, 255));
//						checkBox.setEnabled(false);
//					}
//				}else {
//					checkBox.setEnabled(true);
//				}
				
				//设置节点状态为不选
				node.setCheckStatus(PfTreeNode.CHECK_NO);

				//设置checkbox前景色
				checkBox.setForeground(getTextNonSelectionColor());
				//设置checkbox背景色
				checkBox.setBackground(this.getBackgroundNonSelectionColor());
				checkBox.setIcon(null);
			}

			
			//---------begin: linping, 2008-1-29 --------------
			//如果是半勾选模式
			if(this.isShowPartCheckStatus){
				//如果节点为半勾选节点
		        if (isPartCheck(value)) {
		        	//设置节点为半选中状态
		        	node.setCheckStatus(PfTreeNode.CHECK_PART);
		        	//加载半勾选图标,如果partCheckIconFilePath为空，则用默认图标part_check.jpg
		        	if(partCheckIconFilePath == null){
		        		partCheckIconFilePath = "part_check.jpg";
		        	}
					ImageIcon partCheckIcon = Resource.getImage(partCheckIconFilePath);
					//给checkBox设置半勾选图标
					checkBox.setIcon(partCheckIcon);
				}							
			}
			//---------end: linping, 2008-1-29 --------------

			return checkBox;
		}
		return component;
	}
	
	/**
	 * 子节点是否全部选中
	 * @param node
	 * @return
	 */
//	private boolean isSelectedAllChildren(DefaultMutableTreeNode node) {
//		boolean selectAll = true;
//		int childCounts = node.getChildCount();
//		for (int i = 0; i < childCounts; i++) {
//			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node
//					.getChildAt(i);
//			PfTreeNode value = (PfTreeNode) child.getUserObject();
//			if (!value.getIsSelect()) {
//				selectAll = false;
//				break;
//			}
//		}
//		return selectAll;
//	}

	
	/**
	 * 判断节点是否为半选节点
	 * @param value 节点对象
	 * @return true：半选节点；false：非半选节点
	 * @author linping
	 * @date 2008-1-29
	 */
	private boolean isPartCheck(Object value) {
		//当前节点
		DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) value;

		//当前节点如果为空或者是叶节点，返回false
		if (currentNode == null || currentNode.isLeaf()) {
			return false;
		}

		int selected_child = 0; //被选中的孩子节点
		int child_count = currentNode.getChildCount(); //孩子节点个数
		//统计有多少个选中的孩子节点
		for (int i = 0; i < child_count; i++) {
			if (((PfTreeNode) (((DefaultMutableTreeNode) currentNode
					.getChildAt(i)).getUserObject())).getIsSelect()) {
				selected_child = selected_child + 1;
			}
		}
		//如果选中的孩子节点个数大于0，且小于孩子节点总数，则返回true
		if (selected_child > 0 && selected_child < child_count) {
			return true;
		}

		return false;
	}
	
	/**
	 * 返回是否显示半选状态
	 * @return true：显示半选状态 false：不显示半选状态
	 */
	public boolean isShowPartCheckStatus() {
		return isShowPartCheckStatus;
	}

	/**
	 * 设置是否显示半选状态
	 * @param isShowPartCheckStatus， true：显示半选状态 false：不显示半选状态
	 */
	public void setShowPartCheckStatus(boolean isShowPartCheckStatus) {
		this.isShowPartCheckStatus = isShowPartCheckStatus;
	}

	/**
	 * 得到半选图标文件路径
	 * @return 半选图标文件路径
	 */
	public String getPartCheckIconFilePath() {
		return partCheckIconFilePath;
	}

	/**
	 * 设置半选图标文件路径
	 * @param partCheckIconFilePath：半选图标文件路径
	 */
	public void setPartCheckIconFilePath(String partCheckIconFilePath) {
		this.partCheckIconFilePath = partCheckIconFilePath;
	}

}
