package com.foundercy.pf.control.table;

/**
 * ÐòºÅÁÐµÄÃè»æÆ÷
 */
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class FBaseTableIndexCellRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5368145359376593420L;

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setText(value.toString());
		this.setToolTipText("Row NO. " + value.toString());
		this.setFont(table.getFont());
		this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
						Color.WHITE));
		if (isSelected) {
			this.setBorder(null);
			this.setBackground(table.getSelectionBackground());
			this.setForeground(table.getSelectionForeground());
		} else {
			this.setBackground((new JPanel()).getBackground());
			this.setForeground(table.getForeground());
		}
		return this;
	}
	
	private boolean isRowSelect(JTable table, int row){
		int[] indexes = table.getSelectedRows();
		for(int i=0; indexes!=null&& i<indexes.length; i++){
			if(row == indexes[i]){
				return true;
			}
		}
		return false;
	}
}
