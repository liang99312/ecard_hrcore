/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.ch20_filechooser;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalBorders.ScrollPaneBorder;

/**
 *
 * @author mxliteboss
 */
public class __UI__ {
    public static void uiImpl()
  {
    UIManager.put("FileChooser.listViewBorder", 
      new BorderUIResource(new ScrollPaneBorder()));

    UIManager.put("ToolBar.shadow", new ColorUIResource(new Color(249, 248, 243)));
  }

  public static void uiImpl_win()
  {
    UIManager.put("FileChooserUI", 
      BEFileChooserUIWin.class.getName());
  }

  public static void uiImpl_cross()
  {
    UIManager.put("FileChooserUI", 
      BEFileChooserUICross.class.getName());
  }
}
