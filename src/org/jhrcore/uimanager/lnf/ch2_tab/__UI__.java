/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.ch2_tab;

import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;
import org.jhrcore.uimanager.lnf.BeautyEyeLNFHelper;

/**
 *
 * @author mxliteboss
 */
public class __UI__ {

    public static void uiImpl() {
        UIManager.put("TabbedPane.background", new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));
        UIManager.put("TabbedPane.foreground", new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));

        UIManager.put("TabbedPane.tabsOverlapBorder", Boolean.valueOf(true));
        UIManager.put("TabbedPaneUI", BETabbedPaneUI.class.getName());

        UIManager.put("TabbedPane.tabAreaInsets",
                new InsetsUIResource(3, 20, 2, 20));

        UIManager.put("TabbedPane.contentBorderInsets",
                new InsetsUIResource(2, 0, 3, 0));

        UIManager.put("TabbedPane.selectedTabPadInsets",
                new InsetsUIResource(0, 1, 0, 2));

        UIManager.put("TabbedPane.tabInsets", new InsetsUIResource(7, 15, 9, 15));

        UIManager.put("TabbedPane.focus", new ColorUIResource(130, 130, 130));
        ColorUIResource highlight = new ColorUIResource(BeautyEyeLNFHelper.commonFocusedBorderColor);

        UIManager.put("TabbedPane.highlight", highlight);

        UIManager.put("TabbedPane.shadow", highlight);

        UIManager.put("TabbedPane.darkShadow",
                new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));
    }
}
