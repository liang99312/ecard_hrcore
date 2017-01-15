/*
 * $Id: FCheckBoxTableHeaderRenderer.java,v 1.1.1.1 2009/04/07 08:12:34 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
/**
 * @version $Revision: 1.1.1.1 $
 * @author a
 * @since java 1.4
 */
public class FCheckBoxTableHeaderRenderer extends FBaseTableHeaderRenderer {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 6988275293021717564L;

	//	private JPanel panel = new JPanel();
	private Box panel = Box.createHorizontalBox();
	
	private JCheckBox checkBox = new JCheckBox();
	
	public FCheckBoxTableHeaderRenderer() {
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 boolean hasFocus,
                                                 int row, int column) {

		panel.add(checkBox);
//		panel.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		panel.setPreferredSize(new Dimension(18,18));
		Color c = new JLabel().getBackground();
	    panel.setBorder(BorderFactory.createBevelBorder(javax.swing.border.
	            BevelBorder.RAISED,c,c.darker()
	            ));
		panel.setOpaque(false);
		
    	return panel;
	}
	
	public JCheckBox getCheckBox() {
		return this.checkBox;
	}

}