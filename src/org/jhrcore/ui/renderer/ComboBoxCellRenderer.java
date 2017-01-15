/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.renderer;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.jhrcore.util.ColorUtil;

/**
 *
 * @author wangzhenhua
 */
public class ComboBoxCellRenderer extends JLabel implements ListCellRenderer {

    private int horizontalAlignment = JLabel.LEFT;

    public ComboBoxCellRenderer() {
        setOpaque(true);
    }

    public ComboBoxCellRenderer(int horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        this.setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        this.setHorizontalAlignment(horizontalAlignment);
        this.setText(value == null ? "" : value.toString());
        this.setBackground(isSelected ? ColorUtil.selBackgroundColor : ColorUtil.commBackgroundColor);
        this.setForeground(ColorUtil.commForegroundColor);
        return this;
    }
}
