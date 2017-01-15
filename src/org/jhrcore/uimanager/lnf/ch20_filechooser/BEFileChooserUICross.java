/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.ch20_filechooser;

import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalFileChooserUI;

/**
 *
 * @author mxliteboss
 */
public class BEFileChooserUICross extends MetalFileChooserUI {

    public BEFileChooserUICross(JFileChooser filechooser) {
        super(filechooser);
    }

    public static ComponentUI createUI(JComponent c) {
        return new BEFileChooserUICross((JFileChooser) c);
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