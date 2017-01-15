/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.ch3_button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.JTextComponent;
import org.jhrcore.uimanager.lnf.utils.BEUtils;

/**
 *
 * @author mxliteboss
 */
public class BEButtonUI extends BasicButtonUI
{
  private static final BEButtonUI xWindowsButtonUI = new BEButtonUI();

  private NormalColor nomalColor = NormalColor.normal;
  protected int dashedRectGapX;
  protected int dashedRectGapY;
  protected int dashedRectGapWidth;
  protected int dashedRectGapHeight;
  protected Color focusColor;
  private boolean defaults_initialized = false;

  public BEButtonUI setNormalColor(NormalColor nc)
  {
    this.nomalColor = nc;
    return this;
  }

  public static ComponentUI createUI(JComponent c)
  {
    return xWindowsButtonUI;
  }

  protected void installDefaults(AbstractButton b)
  {
    super.installDefaults(b);
    b.setOpaque(false);

    if (!this.defaults_initialized) {
      String pp = getPropertyPrefix();
      this.dashedRectGapX = UIManager.getInt(pp + "dashedRectGapX");
      this.dashedRectGapY = UIManager.getInt(pp + "dashedRectGapY");
      this.dashedRectGapWidth = UIManager.getInt(pp + "dashedRectGapWidth");
      this.dashedRectGapHeight = UIManager.getInt(pp + "dashedRectGapHeight");
      this.focusColor = UIManager.getColor(pp + "focus");
      this.defaults_initialized = true;
    }

    b.setBorder(new XPEmptyBorder(new Insets(3, 3, 3, 3)));
    LookAndFeel.installProperty(b, "rolloverEnabled", Boolean.TRUE);
  }

  protected void uninstallDefaults(AbstractButton b)
  {
    super.uninstallDefaults(b);
    this.defaults_initialized = false;
  }

  protected Color getFocusColor()
  {
    return this.focusColor;
  }

  protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect)
  {
    int width = b.getWidth();
    int height = b.getHeight();
    g.setColor(getFocusColor());

    BEUtils.drawDashedRect(g, this.dashedRectGapX, this.dashedRectGapY, 
      width - this.dashedRectGapWidth, height - this.dashedRectGapHeight);

    g.setColor(new Color(255, 255, 255, 50));

    BEUtils.drawDashedRect(g, this.dashedRectGapX + 1, this.dashedRectGapY + 1, 
      width - this.dashedRectGapWidth, height - this.dashedRectGapHeight);
  }

  public Dimension getPreferredSize(JComponent c)
  {
    Dimension d = super.getPreferredSize(c);

    AbstractButton b = (AbstractButton)c;
    if ((d != null) && (b.isFocusPainted())) {
      if (d.width % 2 == 0) d.width += 1;
      if (d.height % 2 == 0) d.height += 1;
    }
    return d;
  }

  public void paint(Graphics g, JComponent c)
  {
    paintXPButtonBackground(this.nomalColor, g, c);

    super.paint(g, c);
  }

  public static void paintXPButtonBackground(NormalColor nomalColor, Graphics g, JComponent c)
  {
    AbstractButton b = (AbstractButton)c;

    boolean toolbar = b.getParent() instanceof JToolBar;

    if (b.isContentAreaFilled())
    {
      ButtonModel model = b.getModel();

      Dimension d = c.getSize();
      int dx = 0;
      int dy = 0;
      int dw = d.width;
      int dh = d.height;

      Border border = c.getBorder();
      Insets insets;
      if (border != null)
      {
        insets = getOpaqueInsets(border, c);
      }
      else
      {
        insets = c.getInsets();
      }
      if (insets != null)
      {
        dx += insets.left;
        dy += insets.top;
        dw -= insets.left + insets.right;
        dh -= insets.top + insets.bottom;
      }

      if (toolbar)
      {
        if ((model.isRollover()) || (model.isPressed()))
        {
          if ((c instanceof JToggleButton))
            __Icon9Factory__.getInstance().getToggleButtonIcon_RoverGreen().draw((Graphics2D)g, dx, dy, dw, dh);
          else
            __Icon9Factory__.getInstance().getButtonIcon_PressedOrange().draw((Graphics2D)g, dx, dy, dw, dh);
        }
        else if (model.isSelected())
        {
          __Icon9Factory__.getInstance().getToggleButtonIcon_CheckedGreen().draw((Graphics2D)g, dx, dy, dw, dh);
        }

      }
      else
      {
        try
        {
          if (((model.isArmed()) && (model.isPressed())) || (model.isSelected())) {
            __Icon9Factory__.getInstance().getButtonIcon_PressedOrange().draw((Graphics2D)g, dx, dy, dw, dh);
          }
          else if (!model.isEnabled())
            __Icon9Factory__.getInstance().getButtonIcon_DisableGray().draw((Graphics2D)g, dx, dy, dw, dh);
          else if (model.isRollover()) {
            __Icon9Factory__.getInstance().getButtonIcon_rover().draw((Graphics2D)g, dx, dy, dw, dh);
          }
          else if (nomalColor == NormalColor.green)
          {
            __Icon9Factory__.getInstance().getButtonIcon_NormalGreen().draw((Graphics2D)g, dx, dy, dw, dh);
          }
          else if (nomalColor == NormalColor.red)
          {
            __Icon9Factory__.getInstance().getButtonIcon_NormalRed().draw((Graphics2D)g, dx, dy, dw, dh);
          }
          else if (nomalColor == NormalColor.blue)
          {
            __Icon9Factory__.getInstance().getButtonIcon_NormalBlue().draw((Graphics2D)g, dx, dy, dw, dh);
          }
          else if (nomalColor == NormalColor.lightBlue)
          {
            __Icon9Factory__.getInstance().getButtonIcon_NormalLightBlue().draw((Graphics2D)g, dx, dy, dw, dh);
          }
          else
          {
            __Icon9Factory__.getInstance().getButtonIcon_NormalGray().draw((Graphics2D)g, dx, dy, dw, dh);
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
  }

  private static Insets getOpaqueInsets(Border b, Component c)
  {
    if (b == null) {
      return null;
    }
    if (b.isBorderOpaque())
      return b.getBorderInsets(c);
    if ((b instanceof CompoundBorder)) {
      CompoundBorder cb = (CompoundBorder)b;
      Insets iOut = getOpaqueInsets(cb.getOutsideBorder(), c);
      if ((iOut != null) && (iOut.equals(cb.getOutsideBorder().getBorderInsets(c))))
      {
        Insets iIn = getOpaqueInsets(cb.getInsideBorder(), c);
        if (iIn == null)
        {
          return iOut;
        }

        return new Insets(iOut.top + iIn.top, iOut.left + iIn.left, 
          iOut.bottom + iIn.bottom, iOut.right + iIn.right);
      }

      return iOut;
    }

    return null;
  }

  public static enum NormalColor
  {
    normal, 

    green, 

    red, 

    lightBlue, 

    blue;
  }

  public static class XPEmptyBorder extends EmptyBorder
    implements UIResource
  {
    public XPEmptyBorder(Insets m)
    {
      super(m.top,m.left + 2, m.bottom + 2, m.right + 2);
    }

    public Insets getBorderInsets(Component c)
    {
      return getBorderInsets(c, getBorderInsets());
    }

    public Insets getBorderInsets(Component c, Insets insets)
    {
      insets = super.getBorderInsets(c, insets);

      Insets margin = null;
      if ((c instanceof AbstractButton)) {
        Insets m = ((AbstractButton)c).getMargin();

        if (((c.getParent() instanceof JToolBar)) && 
          (!(c instanceof JRadioButton)) && 
          (!(c instanceof JCheckBox)) && 
          ((m instanceof InsetsUIResource))) {
          insets.top -= 2;
          insets.left -= 2;
          insets.bottom -= 2;
          insets.right -= 2;
        } else {
          margin = m;
        }
      } else if ((c instanceof JToolBar)) {
        margin = ((JToolBar)c).getMargin();
      } else if ((c instanceof JTextComponent)) {
        margin = ((JTextComponent)c).getMargin();
      }
      if (margin != null) {
        margin.top += 2;
        margin.left += 2;
        margin.bottom += 2;
        margin.right += 2;
      }
      return insets;
    }
  }
}