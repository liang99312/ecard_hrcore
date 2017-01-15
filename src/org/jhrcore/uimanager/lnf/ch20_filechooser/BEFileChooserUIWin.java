/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.ch20_filechooser;

import com.sun.java.swing.plaf.windows.WindowsFileChooserUI;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.plaf.ComponentUI;

/**
 *
 * @author mxliteboss
 */
public class BEFileChooserUIWin extends WindowsFileChooserUI {

    public BEFileChooserUIWin(JFileChooser filechooser) {
        super(filechooser);
    }

    public static ComponentUI createUI(JComponent c) {
        return new BEFileChooserUIWin((JFileChooser) c);
    }

    public void paint(Graphics g, JComponent c) {
        g.setColor(c.getBackground());
        g.fillRect(0, 0, c.getWidth(), c.getHeight());
    }

    protected JPanel createList(JFileChooser fc) {
        JPanel p = super.createList(fc);

        if (p.getComponentCount() > 0) {
            Component scollPane = p.getComponent(0);
            if ((scollPane != null) && ((scollPane instanceof JScrollPane))) {
                JViewport vp = ((JScrollPane) scollPane).getViewport();
                if (vp != null) {
                    Component fileListView = vp.getView();

                    if ((fileListView != null) && ((fileListView instanceof JList))) {
                        ((JList) fileListView).setFixedCellHeight(-1);
                    }
                }
            }
        }

        return p;
    }
}