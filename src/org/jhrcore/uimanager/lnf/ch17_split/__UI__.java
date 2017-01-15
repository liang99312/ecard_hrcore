/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.ch17_split;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
/**
 *
 * @author mxliteboss
 */
public class __UI__ {
    public static void uiImpl()
  {
    UIManager.put("SplitPane.shadow", new ColorUIResource(new Color(200, 200, 200)));

    UIManager.put("SplitPane.background", new ColorUIResource(new Color(250, 250, 250)));
//取消该UI自带BORDER，使用系统默认，效果更好些
//    UIManager.put("SplitPane.border", new BorderUIResource(new ScrollPaneBorder()));
    UIManager.put("SplitPaneUI", BESplitPaneUI.class.getName());

    UIManager.put("SplitPaneDivider.draggingColor", new ColorUIResource(new Color(0, 0, 0, 50)));

    UIManager.put("SplitPane.oneTouchButtonSize", Integer.valueOf(4));

    UIManager.put("SplitPane.dividerSize", Integer.valueOf(7));

    UIManager.put("SplitPaneDivider.border", new SplitPaneDividerBorder());
  }
}
