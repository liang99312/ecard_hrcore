/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf;

import java.awt.Color;

/**
 *
 * @author mxliteboss
 */
public class BeautyEyeLNFHelper {

    public static boolean debug = false;
    public static boolean translucencyAtFrameInactive = true;
    public static Color activeCaptionTextColor = new Color(0, 0, 0);
    public static Color commonBackgroundColor = new Color(250, 250, 250);
    public static Color commonForegroundColor = new Color(60, 60, 60);
    public static Color commonFocusedBorderColor = new Color(162, 162, 162);
    public static Color commonDisabledForegroundColor = new Color(172, 168, 153);
    public static Color commonSelectionBackgroundColor = new Color(2, 129, 216);
    public static Color commonSelectionForegroundColor = new Color(255, 255, 255);
    public static boolean setMaximizedBoundForFrame = true;

    public static void implLNF() {
        org.jhrcore.uimanager.lnf.ch17_split.__UI__.uiImpl();//∑÷∏Ù¿∏
        org.jhrcore.uimanager.lnf.ch20_filechooser.__UI__.uiImpl();
        org.jhrcore.uimanager.lnf.ch2_tab.__UI__.uiImpl();
        org.jhrcore.uimanager.lnf.ch22_checkbox.__UI__.uiImpl();
        org.jhrcore.uimanager.lnf.ch8_toolbar.__UI__.uiImpl();
        org.jhrcore.uimanager.lnf.ch3_button.__UI__.uiImpl();
    }
}
