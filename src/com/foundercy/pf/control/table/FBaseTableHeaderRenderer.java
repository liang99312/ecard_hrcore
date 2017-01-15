package com.foundercy.pf.control.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author mayh
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class FBaseTableHeaderRenderer extends DefaultTableCellRenderer {

    /**
     * 
     */
    private static final long serialVersionUID = -2722285413120527840L;
    /**
     * 
     * @uml.property name="title" multiplicity="(0 1)"
     */
    private String title = null;
    /**
     * 
     * @uml.property name="bold" multiplicity="(0 1)"
     */
    private boolean bold = false;
    /**
     * 
     * @uml.property name="titleVisible" multiplicity="(0 1)"
     */
    private boolean titleVisible = true;

    public FBaseTableHeaderRenderer() {
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setBackground((new JLabel()).getBackground());
        this.setForeground((new JLabel()).getForeground());
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected,
            boolean hasFocus,
            int row, int column) {

        if (titleVisible) {
            if (title == null) {
                this.setText(value.toString());
            } else {
                this.setText(title);
            }
        } else {
            this.setText(" ");
        }

        Color c = this.getBackground();
        if (c == null) {
            c = table.getTableHeader().getBackground();
        }
        this.setPreferredSize(new Dimension(20, 18));
        this.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, c, c.darker()));
        return this;
    }

    /**
     * 
     * @uml.property name="title"
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @uml.property name="title"
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * 
     * @uml.property name="titleVisible"
     */
    public void setTitleVisible(boolean visible) {
        this.titleVisible = visible;
    }

    public boolean isTitleVisible() {
        return this.titleVisible;
    }

    /**
     * 
     * @uml.property name="bold"
     */
    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isBold() {
        return this.bold;
    }
}