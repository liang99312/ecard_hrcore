/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.ch8_toolbar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import javax.swing.JComponent;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToolBarSeparatorUI;

/**
 *
 * @author mxliteboss
 */
public class BEToolBarSeparatorUI extends BasicToolBarSeparatorUI {

    public static ComponentUI createUI(JComponent c) {
        return new BEToolBarSeparatorUI();
    }

    public Dimension getPreferredSize(JComponent c) {
        Dimension size = ((JToolBar.Separator) c).getSeparatorSize();

        if (size != null) {
            size = size.getSize();
        } else {
            size = new Dimension(6, 6);

            if (((JSeparator) c).getOrientation() == 1) {
                size.height = 0;
            } else {
                size.width = 0;
            }
        }
        return size;
    }

    public Dimension getMaximumSize(JComponent c) {
        Dimension pref = getPreferredSize(c);
        if (((JSeparator) c).getOrientation() == 1) {
            return new Dimension(pref.width, 32767);
        }
        return new Dimension(32767, pref.height);
    }

    public void paint(Graphics g, JComponent c) {
        boolean vertical = ((JSeparator) c).getOrientation() == 1;
        Dimension size = c.getSize();

//        Stroke oldStroke = ((Graphics2D) g).getStroke();
//        Stroke sroke = new BasicStroke(1.0F, 0,
//                2, 0.0F, new float[]{2.0F, 2.0F}, 0.0F);
//        ((Graphics2D) g).setStroke(sroke);

        Color temp = g.getColor();
        UIDefaults table = UIManager.getLookAndFeelDefaults();
        Color shadow = table.getColor("ToolBar.shadow");
//        Color highlight = table.getColor("ToolBar.highlight");
        if (vertical) {
            int x = size.width / 2 - 1;

            g.setColor(shadow);
            g.drawLine(x + 1, 2, x + 1, size.height - 2);
        } else {
            int y = size.height / 2 - 1;
            g.setColor(shadow);
            g.drawLine(2, y, size.width - 2, y);

//            g.setColor(highlight);
//            g.drawLine(2, y + 1, size.width - 2, y + 1);
        }
        g.setColor(temp);

//        ((Graphics2D) g).setStroke(oldStroke);
    }
}