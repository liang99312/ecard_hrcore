/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.ch3_button;

import java.awt.Color;
import java.awt.Insets;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.basic.BasicBorders;
import org.jhrcore.uimanager.lnf.BeautyEyeLNFHelper;

/**
 *
 * @author mxliteboss
 */
public class __UI__ {

    public static void uiImpl() {
        UIManager.put("Button.background", new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));

        UIManager.put("Button.foreground", new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));

        UIManager.put("Button.dashedRectGapX", Integer.valueOf(3));
        UIManager.put("Button.dashedRectGapY", Integer.valueOf(3));
        UIManager.put("Button.dashedRectGapWidth", Integer.valueOf(6));
        UIManager.put("Button.dashedRectGapHeight", Integer.valueOf(6));

        UIManager.put("ButtonUI", BEButtonUI.class.getName());
        UIManager.put("Button.margin", new InsetsUIResource(6, 8, 6, 8));

        UIManager.put("Button.border",
                new BEButtonUI.XPEmptyBorder(new Insets(3, 3, 3, 3)));

        UIManager.put("Button.focus", new ColorUIResource(130, 130, 130));

        UIManager.put("ToggleButton.margin", new Insets(3, 11, 3, 11));
        UIManager.put("ToggleButton.background", new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));
        UIManager.put("ToggleButton.foreground", new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));

        UIManager.put("ToggleButton.focus", new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));
        UIManager.put("ToggleButtonUI", BEToggleButtonUI.class.getName());

        Border toggleButtonBorder = new BorderUIResource(new BasicBorders.MarginBorder());

        UIManager.put("ToggleButton.border", toggleButtonBorder);

        UIManager.put("ToggleButton.focusLine",
                new ColorUIResource(BeautyEyeLNFHelper.commonFocusedBorderColor.darker()));

        UIManager.put("ToggleButton.focusLineHilight", new ColorUIResource(new Color(240, 240, 240)));
    }
}
