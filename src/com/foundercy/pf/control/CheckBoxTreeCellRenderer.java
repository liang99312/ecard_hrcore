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
	
	private boolean isShowPartCheckStatus = false;		//�Ƿ���ʾ��ѡ״̬
	private String partCheckIconFilePath = null;		//��ѡͼ���ļ�·��

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
		//�������ΪPfTreeNode����
		if(dmt instanceof PfTreeNode) {
			PfTreeNode node = (PfTreeNode) ((DefaultMutableTreeNode) value)
					.getUserObject();
			//����checkbox��ʾ����
			checkBox.setText(node.toString());
			//����checkbox�Ƿ�ѡ��
			checkBox.setSelected(node.getIsSelect());
			
	//		if (hasFocus)
	//			checkBox.setBackground(new Color(184, 190, 255));
	//		else
	//			checkBox.setBackground(new Color(255, 255, 255));
			if(sel) {
				//���ýڵ�״̬Ϊȫѡ
				node.setCheckStatus(PfTreeNode.CHECK_ALL);
				
				//����checkboxǰ��ɫ
				checkBox.setForeground(getTextSelectionColor());
				//����checkbox����ɫ
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
				
				//���ýڵ�״̬Ϊ��ѡ
				node.setCheckStatus(PfTreeNode.CHECK_NO);

				//����checkboxǰ��ɫ
				checkBox.setForeground(getTextNonSelectionColor());
				//����checkbox����ɫ
				checkBox.setBackground(this.getBackgroundNonSelectionColor());
				checkBox.setIcon(null);
			}

			
			//---------begin: linping, 2008-1-29 --------------
			//����ǰ빴ѡģʽ
			if(this.isShowPartCheckStatus){
				//����ڵ�Ϊ�빴ѡ�ڵ�
		        if (isPartCheck(value)) {
		        	//���ýڵ�Ϊ��ѡ��״̬
		        	node.setCheckStatus(PfTreeNode.CHECK_PART);
		        	//���ذ빴ѡͼ��,���partCheckIconFilePathΪ�գ�����Ĭ��ͼ��part_check.jpg
		        	if(partCheckIconFilePath == null){
		        		partCheckIconFilePath = "part_check.jpg";
		        	}
					ImageIcon partCheckIcon = Resource.getImage(partCheckIconFilePath);
					//��checkBox���ð빴ѡͼ��
					checkBox.setIcon(partCheckIcon);
				}							
			}
			//---------end: linping, 2008-1-29 --------------

			return checkBox;
		}
		return component;
	}
	
	/**
	 * �ӽڵ��Ƿ�ȫ��ѡ��
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
	 * �жϽڵ��Ƿ�Ϊ��ѡ�ڵ�
	 * @param value �ڵ����
	 * @return true����ѡ�ڵ㣻false���ǰ�ѡ�ڵ�
	 * @author linping
	 * @date 2008-1-29
	 */
	private boolean isPartCheck(Object value) {
		//��ǰ�ڵ�
		DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) value;

		//��ǰ�ڵ����Ϊ�ջ�����Ҷ�ڵ㣬����false
		if (currentNode == null || currentNode.isLeaf()) {
			return false;
		}

		int selected_child = 0; //��ѡ�еĺ��ӽڵ�
		int child_count = currentNode.getChildCount(); //���ӽڵ����
		//ͳ���ж��ٸ�ѡ�еĺ��ӽڵ�
		for (int i = 0; i < child_count; i++) {
			if (((PfTreeNode) (((DefaultMutableTreeNode) currentNode
					.getChildAt(i)).getUserObject())).getIsSelect()) {
				selected_child = selected_child + 1;
			}
		}
		//���ѡ�еĺ��ӽڵ��������0����С�ں��ӽڵ��������򷵻�true
		if (selected_child > 0 && selected_child < child_count) {
			return true;
		}

		return false;
	}
	
	/**
	 * �����Ƿ���ʾ��ѡ״̬
	 * @return true����ʾ��ѡ״̬ false������ʾ��ѡ״̬
	 */
	public boolean isShowPartCheckStatus() {
		return isShowPartCheckStatus;
	}

	/**
	 * �����Ƿ���ʾ��ѡ״̬
	 * @param isShowPartCheckStatus�� true����ʾ��ѡ״̬ false������ʾ��ѡ״̬
	 */
	public void setShowPartCheckStatus(boolean isShowPartCheckStatus) {
		this.isShowPartCheckStatus = isShowPartCheckStatus;
	}

	/**
	 * �õ���ѡͼ���ļ�·��
	 * @return ��ѡͼ���ļ�·��
	 */
	public String getPartCheckIconFilePath() {
		return partCheckIconFilePath;
	}

	/**
	 * ���ð�ѡͼ���ļ�·��
	 * @param partCheckIconFilePath����ѡͼ���ļ�·��
	 */
	public void setPartCheckIconFilePath(String partCheckIconFilePath) {
		this.partCheckIconFilePath = partCheckIconFilePath;
	}

}
