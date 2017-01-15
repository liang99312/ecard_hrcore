/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import org.jhrcore.util.RenderderUtil;

/**
 *
 * @author mxliteboss
 */
public class JCheckBoxListRenderer extends JPanel implements ListCellRenderer {
    private boolean[] checkedItems;
    public JCheckBoxListRenderer(boolean[] checkedItems) {
        setOpaque(true);
        this.checkedItems = checkedItems;
    }

    @Override
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setEnabled(true);
        JLabel lbl1 = new JLabel();
        lbl1.setEnabled(true);
        JCheckBox jcb = new JCheckBox();
        lbl1.setText((value == null) ? "" : value.toString());
        String tag = RenderderUtil.getIconTag(value);
        if (tag == null) {
             tag = "node";
        }
        lbl1.setIcon(RenderderUtil.getIconByTag(tag));
        jcb.setSelected(isChecked(index));
        if (isSelected) {
            lbl1.setBackground(new Color(184,207,229));
            lbl1.setForeground(Color.BLUE);
            lbl1.setOpaque(true);
        }
        jcb.setBackground(Color.WHITE);
        pnl.setBackground(Color.WHITE);
        pnl.add(jcb,BorderLayout.WEST);
        pnl.add(lbl1,BorderLayout.CENTER);
        return pnl;
    }
    /**
     * 是否指定item被check。
     * @param index int
     * @return boolean
     */
    public boolean isChecked(int index) {
        return checkedItems[index];
    }
}
