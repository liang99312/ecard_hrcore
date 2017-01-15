/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.ch22_checkbox;

import java.awt.Component;
import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.UIResource;

/**
 *
 * @author mxliteboss
 */
public class __UI__ {

    public static void uiImpl() {
//    UIManager.put("CheckBox.margin", new InsetsUIResource(4, 3, 4, 3));
//    UIManager.put("RadioButton.margin", new InsetsUIResource(4, 3, 4, 3));

//    UIManager.put("RadioButton.background", new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));
//    UIManager.put("CheckBox.background", new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));
//
//    UIManager.put("CheckBox.foreground", new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));
//    UIManager.put("RadioButton.foreground", new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));

        UIManager.put("RadioButton.icon", new RadioButtonIcon());
        UIManager.put("CheckBox.icon", new CheckBoxIcon());

        UIManager.put("RadioButton.margin", new InsetsUIResource(0, 1, 1, 0));
        UIManager.put("CheckBox.margin", new InsetsUIResource(0, 1, 1, 0));
    }

    private static class CheckBoxIcon
            implements Icon, Serializable {

        public void paintIcon(Component c, Graphics g, int x, int y) {
            JCheckBox cb = (JCheckBox) c;
            ButtonModel model = cb.getModel();

            if (model.isSelected()) {
                if (!model.isEnabled()) {
                    g.drawImage(__IconFactory__.getInstance().getCheckBoxButtonIcon_disable().getImage(), x, y, null);
                } else if (model.isPressed()) {
                    g.drawImage(__IconFactory__.getInstance().getCheckBoxButtonIcon_pressed().getImage(), x, y, null);
                } else {
                    g.drawImage(__IconFactory__.getInstance().getCheckBoxButtonIcon_normal().getImage(), x, y, null);
                }

            } else if (!model.isEnabled()) {
                g.drawImage(__IconFactory__.getInstance().getCheckBoxButtonIcon_unchecked_disable().getImage(), x, y, null);
            } else if (model.isPressed()) {
                g.drawImage(__IconFactory__.getInstance().getCheckBoxButtonIcon_unchecked_pressed().getImage(), x, y, null);
            } else {
                g.drawImage(__IconFactory__.getInstance().getCheckBoxButtonIcon_unchecked_normal().getImage(), x, y, null);
            }
        }

        public int getIconWidth() {
            return 16;
        }

        public int getIconHeight() {
            return 16;
        }
    }

    private static class RadioButtonIcon
            implements Icon, UIResource, Serializable {

        public void paintIcon(Component c, Graphics g, int x, int y) {
            AbstractButton b = (AbstractButton) c;
            ButtonModel model = b.getModel();

            if (model.isSelected()) {
                if (!model.isEnabled()) {
                    g.drawImage(__IconFactory__.getInstance().getRadioButtonIcon_disable().getImage(), x, y, null);
                } else if (model.isPressed()) {
                    g.drawImage(__IconFactory__.getInstance().getRadioButtonIcon_pressed().getImage(), x, y, null);
                } else {
                    g.drawImage(__IconFactory__.getInstance().getRadioButtonIcon_normal().getImage(), x, y, null);
                }

            } else if (!model.isEnabled()) {
                g.drawImage(__IconFactory__.getInstance().getRadioButtonIcon_unchecked_disable().getImage(), x, y, null);
            } else if (model.isPressed()) {
                g.drawImage(__IconFactory__.getInstance().getRadioButtonIcon_unchecked_pressed().getImage(), x, y, null);
            } else {
                g.drawImage(__IconFactory__.getInstance().getRadioButtonIcon_unchecked_normal().getImage(), x, y, null);
            }
        }

        public int getIconWidth() {
            return 16;
        }

        public int getIconHeight() {
            return 16;
        }
    }
}
