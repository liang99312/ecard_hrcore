/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import org.jhrcore.ui.ModelFrame;

/**
 *
 * @author mxliteboss
 */
public class CloseAction extends AbstractAction {

    private JDialog dlg;
    private boolean isModelFrame = true;

    public static void doCloseAction(final JButton btnClose) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Component c = btnClose;
                int i = 15;
                while (c != null && i > 0) {
                    if (c instanceof JDialog) {
                        doCloseAction((JDialog) c, btnClose);
                        break;
                    } else if (c instanceof ModelFrame) {
                        doCloseAction(true, btnClose);
                        break;
                    }
                    c = c.getParent();
                    i--;
                }
            }
        });
    }

    public static void doCloseAction(JDialog dlg, JButton btnClose) {
        CloseAction action = new CloseAction(dlg);
        btnClose.addActionListener(action);
    }

    public static void doCloseAction(boolean isModelFrame, JButton btnClose) {
        CloseAction action = new CloseAction(isModelFrame);
        btnClose.addActionListener(action);
    }

    public CloseAction(JDialog dlg) {
        this.dlg = dlg;
    }

    public CloseAction(boolean isModelFrame) {
        this.isModelFrame = isModelFrame;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (dlg != null) {
            dlg.dispose();
        } else if (isModelFrame) {
            ModelFrame.close();
        }
    }
}
