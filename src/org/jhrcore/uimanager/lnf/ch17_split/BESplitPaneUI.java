/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.ch17_split;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
/**
 *
 * @author mxliteboss
 */
public class BESplitPaneUI extends BasicSplitPaneUI
{
  public static ComponentUI createUI(JComponent x)
  {
    return new BESplitPaneUI();
  }

  public BasicSplitPaneDivider createDefaultDivider()
  {
    return new BESplitPaneDivider(this);
  }
}