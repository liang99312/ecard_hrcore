/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.ch8_toolbar;

import java.awt.Color;
import java.awt.Insets;
import javax.swing.UIManager;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import org.jhrcore.uimanager.lnf.BeautyEyeLNFHelper;

/**
 *
 * @author mxliteboss
 */
public class __UI__ {

    public static void uiImpl() {
        UIManager.put("ToolBar.isPaintPlainBackground", Boolean.FALSE);

        UIManager.put("ToolBar.shadow", new ColorUIResource(new Color(180, 183, 187)));

        UIManager.put("ToolBar.highlight", new ColorUIResource(new Color(180, 183, 187)));//new ColorUIResource(Color.white));
        UIManager.put("ToolBar.dockingBackground", new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));
        UIManager.put("ToolBar.floatingBackground", new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));
        UIManager.put("ToolBar.background", new ColorUIResource(BeautyEyeLNFHelper.commonBackgroundColor));
        UIManager.put("ToolBar.foreground", new ColorUIResource(BeautyEyeLNFHelper.commonForegroundColor));
        //ÐÞ¸ÄBORDER INSET
        UIManager.put("ToolBar.border",
                new BorderUIResource(new BEToolBarUI.ToolBarBorder(UIManager.getColor("ToolBar.shadow"),
                UIManager.getColor("ToolBar.highlight"), new Insets(2,1,3,0))));//6, 0, 11, 0))));

        UIManager.put("ToolBarSeparatorUI",
                BEToolBarSeparatorUI.class.getName());
        UIManager.put("ToolBarUI", BEToolBarUI.class.getName());
    }
}
