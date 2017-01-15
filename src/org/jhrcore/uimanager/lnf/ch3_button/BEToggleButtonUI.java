/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.ch3_button;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToggleButtonUI;
import org.jhrcore.uimanager.lnf.utils.BEUtils;
import org.jhrcore.uimanager.lnf.utils.MySwingUtilities2;
import sun.awt.AppContext;
/**
 *
 * @author mxliteboss
 */
public class BEToggleButtonUI extends BasicToggleButtonUI
{
  private static final Object WINDOWS_TOGGLE_BUTTON_UI_KEY = new Object();

  private BEButtonUI.NormalColor nomalColor = BEButtonUI.NormalColor.normal;

  public static ComponentUI createUI(JComponent b)
  {
    AppContext appContext = AppContext.getAppContext();
    BEToggleButtonUI windowsToggleButtonUI = 
      (BEToggleButtonUI)appContext.get(WINDOWS_TOGGLE_BUTTON_UI_KEY);
    if (windowsToggleButtonUI == null) {
      windowsToggleButtonUI = new BEToggleButtonUI();
      appContext.put(WINDOWS_TOGGLE_BUTTON_UI_KEY, windowsToggleButtonUI);
    }
    return windowsToggleButtonUI;
  }

  protected void installDefaults(AbstractButton b)
  {
    super.installDefaults(b);
    LookAndFeel.installProperty(b, "opaque", Boolean.FALSE);
  }

  public void paint(Graphics g, JComponent c)
  {
    BEButtonUI.paintXPButtonBackground(this.nomalColor, g, c);

    super.paint(g, c);
  }

  protected void paintText(Graphics g, JComponent c, Rectangle textRect, String text)
  {
    AbstractButton b = (AbstractButton)c;
    ButtonModel model = b.getModel();
    FontMetrics fm = 
      MySwingUtilities2.getFontMetrics(c, g);
    int mnemonicIndex = b.getDisplayedMnemonicIndex();

    if (model.isEnabled())
    {
      if (model.isSelected()) {
        g.setColor(UIManager.getColor(getPropertyPrefix() + "focus"));
      }
      else {
        g.setColor(b.getForeground());
      }

      MySwingUtilities2.drawStringUnderlineCharAt(c, g, text, mnemonicIndex, 
        textRect.x + getTextShiftOffset(), 
        textRect.y + fm.getAscent() + getTextShiftOffset());
    }
    else
    {
      g.setColor(b.getBackground().brighter());

      MySwingUtilities2.drawStringUnderlineCharAt(c, g, text, mnemonicIndex, 
        textRect.x, textRect.y + fm.getAscent());
      g.setColor(b.getBackground().darker());

      MySwingUtilities2.drawStringUnderlineCharAt(c, g, text, mnemonicIndex, 
        textRect.x - 1, textRect.y + fm.getAscent() - 1);
    }
  }

  protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect)
  {
    Rectangle bound = b.getVisibleRect();

    int delta = 3;
    int x = bound.x + 3; int y = bound.y + 3;
    int w = bound.width - 6; int h = bound.height - 6;

    g.setColor(UIManager.getColor("ToggleButton.focusLine"));
    BEUtils.drawDashedRect(g, x, y, w, h, 17, 17, 2, 2);

    g.setColor(UIManager.getColor("ToggleButton.focusLineHilight"));
    BEUtils.drawDashedRect(g, x + 1, y + 1, w, h, 17, 17, 2, 2);
  }
}