/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.ch2_tab;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import org.jhrcore.uimanager.lnf.utils.BEUtils;

/**
 *
 * @author mxliteboss
 */
public class BETabbedPaneUI extends BasicTabbedPaneUI {

    public static ComponentUI createUI(JComponent c) {
        return new BETabbedPaneUI();
    }

    protected void setRolloverTab(int index) {
        int oldRolloverTab = getRolloverTab();
        super.setRolloverTab(index);
        Rectangle r1 = null;
        Rectangle r2 = null;
        if ((oldRolloverTab >= 0) && (oldRolloverTab < this.tabPane.getTabCount())) {
            r1 = getTabBounds(this.tabPane, oldRolloverTab);
        }
        if (index >= 0) {
            r2 = getTabBounds(this.tabPane, index);
        }
        if (r1 != null) {
            if (r2 != null) {
                this.tabPane.repaint(r1.union(r2));
            } else {
                this.tabPane.repaint(r1);
            }
        } else if (r2 != null) {
            this.tabPane.repaint(r2);
        }
    }

    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(x, y);

        boolean isRover = getRolloverTab() == tabIndex;

        boolean isEnableAt = this.tabPane.isEnabledAt(tabIndex);

        switch (tabPlacement) {
            case 2:
                g2d.scale(-1.0D, 1.0D);
                g2d.rotate(Math.toRadians(90.0D));
                paintTabBorderImpl(g2d, isEnableAt, isSelected, isRover, 0, 0, h, w);
                break;
            case 4:
                g2d.translate(w, 0);
                g2d.rotate(Math.toRadians(90.0D));
                paintTabBorderImpl(g2d, isEnableAt, isSelected, isRover, 0, 0, h, w);
                break;
            case 3:
                g2d.translate(0, h);
                g2d.scale(-1.0D, 1.0D);
                g2d.rotate(Math.toRadians(180.0D));
                paintTabBorderImpl(g2d, isEnableAt, isSelected, isRover, 0, 0, w, h);
                break;
            case 1:
            default:
                paintTabBorderImpl(g2d, isEnableAt, isSelected, isRover, 0, 0, w, h);
        }
    }

    private void paintTabBorderImpl(Graphics2D g2d, boolean isEnableAt, boolean isSelected, boolean isRover, int x, int y, int w, int h) {
        if (isSelected) {
            __Icon9Factory__.getInstance().getTabbedPaneBgSelected().draw(g2d, x, y + 1, w, h);
        } else if ((isEnableAt) && (isRover)) {
            __Icon9Factory__.getInstance().getTabbedPaneBgNormal_rover().draw(g2d, x, y + 1, w, h);
        } else {
            __Icon9Factory__.getInstance().getTabbedPaneBgNormal().draw(g2d, x, y + 1, w, h);
        }
    }

    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
        Stroke oldStroke = ((Graphics2D) g).getStroke();
        Stroke sroke = new BasicStroke(1.0F, 0,
                2, 0.0F, new float[]{2.0F, 2.0F}, 0.0F);
        ((Graphics2D) g).setStroke(sroke);

        super.paintContentBorder(g, tabPlacement, selectedIndex);

        ((Graphics2D) g).setStroke(oldStroke);
    }

    protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
        if (tabPlacement == 1) {
            super.paintContentBorderTopEdge(g, tabPlacement, selectedIndex, x, y, w, h);
        }
    }

    protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
        if (tabPlacement == 2) {
            super.paintContentBorderLeftEdge(g, tabPlacement, selectedIndex, x, y, w, h);
        }
    }

    protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
        if (tabPlacement == 3) {
            super.paintContentBorderBottomEdge(g, tabPlacement, selectedIndex, x, y, w, h);
        }
    }

    protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
        if (tabPlacement == 4) {
            super.paintContentBorderRightEdge(g, tabPlacement, selectedIndex, x, y, w, h);
        }
    }

    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        Rectangle tabRect = rects[tabIndex];
        if ((this.tabPane.hasFocus()) && (isSelected)) {
            g.setColor(this.focus);
            int h;
            int x;
            int y;
            int w;
            switch (tabPlacement) {
                case 2:
                    x = tabRect.x + 4;
                    y = tabRect.y + 6;
                    w = tabRect.width - 7;
                    h = tabRect.height - 12;
                    break;
                case 4:
                    x = tabRect.x + 4;
                    y = tabRect.y + 6;
                    w = tabRect.width - 9;
                    h = tabRect.height - 12;
                    break;
                case 3:
                    x = tabRect.x + 6;
                    y = tabRect.y + 4;
                    w = tabRect.width - 12;
                    h = tabRect.height - 9;
                    break;
                case 1:
                default:
                    x = tabRect.x + 6;

                    y = tabRect.y + 4;

                    w = tabRect.width - 12;

                    h = tabRect.height - 8;
            }

            BEUtils.drawDashedRect(g, x, y, w, h);

            g.setColor(new Color(255, 255, 255, 255));

            BEUtils.drawDashedRect(g, x + 1, y + 1, w, h);
        }
    }

    protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected) {
        Rectangle tabRect = this.rects[tabIndex];
        int nudge = 0;
        switch (tabPlacement) {
            case 3:
                nudge = isSelected ? 1 : -1;
                break;
            case 2:
            case 4:
                nudge = tabRect.height % 2;
                break;
            case 1:
            default:
                nudge = -2;
        }
        return nudge;
    }
}