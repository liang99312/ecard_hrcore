/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.ch8_toolbar;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Stroke;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicToolBarUI;
import org.jhrcore.uimanager.ninepatch4j.NinePatch;

/**
 *
 * @author mxliteboss
 */
public class BEToolBarUI extends BasicToolBarUI
{
  public static ComponentUI createUI(JComponent c)
  {
    return new BEToolBarUI();
  }

  protected void installDefaults()
  {
    setRolloverBorders(true);

    super.installDefaults();
  }

  public boolean isUseParentPaint()
  {
    return (this.toolBar != null) && 
      (!(this.toolBar.getBackground() instanceof UIResource));
  }

  protected Border createRolloverBorder()
  {
    return new EmptyBorder(3, 3, 3, 3);
  }

  protected Border createNonRolloverBorder()
  {
    return new EmptyBorder(3, 3, 3, 3);
  }

  public void paint(Graphics g, JComponent c)
  {
    boolean isPaintPlainBackground = false;
    String isPaintPlainBackgroundKey = "ToolBar.isPaintPlainBackground";

    Object isPaintPlainBackgroundObj = c.getClientProperty(isPaintPlainBackgroundKey);

    if (isPaintPlainBackgroundObj == null)
      isPaintPlainBackgroundObj = Boolean.valueOf(UIManager.getBoolean(isPaintPlainBackgroundKey));
    if (isPaintPlainBackgroundObj != null) {
      isPaintPlainBackground = ((Boolean)isPaintPlainBackgroundObj).booleanValue();
    }

    if ((isPaintPlainBackground) || (isUseParentPaint()))
    {
      super.paint(g, c);
    }
    else
    {
      NinePatch np = __Icon9Factory__.getInstance().getToolBarBg_NORTH();

      Container parent = this.toolBar.getParent();
      if (parent != null)
      {
        LayoutManager lm = parent.getLayout();
        if ((lm instanceof BorderLayout))
        {
          Object cons = ((BorderLayout)lm).getConstraints(this.toolBar);
          if (cons != null)
          {
            if (cons.equals("North"))
              np = __Icon9Factory__.getInstance().getToolBarBg_NORTH();
            else if (cons.equals("South"))
              np = __Icon9Factory__.getInstance().getToolBarBg_SOUTH();
            else if (cons.equals("West"))
              np = __Icon9Factory__.getInstance().getToolBarBg_WEST();
            else if (cons.equals("East"))
              np = __Icon9Factory__.getInstance().getToolBarBg_EAST();
          }
        }
      }
      np.draw((Graphics2D)g, 0, 0, c.getWidth(), c.getHeight());
    }
  }

//  protected Border getRolloverBorder(AbstractButton b)
//  {
//    return new BEButtonUI.XPEmptyBorder(new Insets(3, 3, 3, 3));
//  }

  protected ContainerListener createToolBarContListener()
  {
    return new ToolBarContListenerJb2011();
  }

  public static class ToolBarBorder extends AbstractBorder
    implements UIResource, SwingConstants
  {
    protected Color shadow;
    protected Color highlight;
    protected Insets insets;

    public ToolBarBorder(Color shadow, Color highlight, Insets insets)
    {
      this.highlight = highlight;
      this.shadow = shadow;
      this.insets = insets;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
    {
      g.translate(x, y);

      if (((JToolBar)c).isFloatable())
      {
        boolean vertical = ((JToolBar)c).getOrientation() == 1;

        Stroke oldStroke = ((Graphics2D)g).getStroke();
        Stroke sroke = new BasicStroke(1.0F, 0, 
          2, 0.0F, new float[] { 1.0F, 1.0F }, 0.0F);
        ((Graphics2D)g).setStroke(sroke);

        if (!vertical)
        {
          int gap_top = 8; int gap_bottom = 8;
          if (c.getComponentOrientation().isLeftToRight())
          {
            int drawX = 3;
            drawTouchH(g, drawX, gap_top - 1, drawX, height - gap_bottom - 1);
          }
          else
          {
            int drawX = 2;
            drawTouchH(g, width - drawX, gap_top - 1, width - drawX, height - gap_bottom - 1);
          }
        }
        else
        {
          int gap_left = 8; int gap_right = 8;
          int drawY = 3;
          drawTouchV(g, gap_left - 1, drawY, width - gap_right - 1, drawY);
        }

        ((Graphics2D)g).setStroke(oldStroke);
      }

      g.translate(-x, -y);
    }

    private void drawTouchH(Graphics g, int x1, int y1, int x2, int y2)
    {
      g.setColor(this.shadow);
      g.drawLine(x1, y1, x1, y2 - 1);

      g.setColor(this.highlight);
      g.drawLine(x1 + 1, y1 + 1, x1 + 1, y2);

      g.setColor(this.shadow);
      g.drawLine(x1 + 2, y1, x1 + 2, y2 - 1);

      g.setColor(this.highlight);
      g.drawLine(x1 + 3, y1 + 1, x1 + 3, y2);
    }

    private void drawTouchV(Graphics g, int x1, int y1, int x2, int y2)
    {
      g.setColor(this.shadow);
      g.drawLine(x1, y1, x2 - 1, y2);

      g.setColor(this.highlight);
      g.drawLine(x1 + 1, y1 + 1, x2, y2 + 1);

      g.setColor(this.shadow);
      g.drawLine(x1, y1 + 2, x2 - 1, y2 + 2);

      g.setColor(this.highlight);
      g.drawLine(x1 + 1, y1 + 3, x2, y2 + 3);
    }

    public Insets getBorderInsets(Component c)
    {
      Insets DEFAILT_IS = this.insets;
      Insets is = DEFAILT_IS;
      if ((c instanceof JToolBar))
      {
        Container parent = c.getParent();
        if (parent != null)
        {
          LayoutManager lm = parent.getLayout();
          if ((lm instanceof BorderLayout))
          {
            Object cons = ((BorderLayout)lm).getConstraints((JToolBar)c);
            if (cons != null)
            {
              if (cons.equals("North"))
                is = DEFAILT_IS;
              else if (cons.equals("South"))
                is = new Insets(DEFAILT_IS.bottom, 0, DEFAILT_IS.top, 0);
              else if (cons.equals("West"))
                is = new Insets(0, DEFAILT_IS.top, 0, DEFAILT_IS.bottom);
              else if (cons.equals("East")) {
                is = new Insets(0, DEFAILT_IS.bottom, 0, DEFAILT_IS.top);
              }
            }
          }
        }
      }
      return getBorderInsets(c, is);
    }

    public Insets getBorderInsets(Component c, Insets insets)
    {
      if (((JToolBar)c).isFloatable())
      {
        int gripInset = 9;
        if (((JToolBar)c).getOrientation() == 0)
        {
          if (c.getComponentOrientation().isLeftToRight())
            insets.left = gripInset;
          else
            insets.right = gripInset;
        }
        else
          insets.top = gripInset;
      }
      return insets;
    }
  }

  protected class ToolBarContListenerJb2011
    implements ContainerListener
  {
    protected ToolBarContListenerJb2011()
    {
    }

    public void componentAdded(ContainerEvent evt)
    {
      Component c = evt.getChild();

      if (BEToolBarUI.this.toolBarFocusListener != null) {
        c.addFocusListener(BEToolBarUI.this.toolBarFocusListener);
      }

      if (BEToolBarUI.this.isRolloverBorders()) {
        BEToolBarUI.this.setBorderToRollover(c);
      }
      else
      {
        BEToolBarUI.this.setBorderToNonRollover(c);
      }
    }

    public void componentRemoved(ContainerEvent evt)
    {
      Component c = evt.getChild();

      if (BEToolBarUI.this.toolBarFocusListener != null) {
        c.removeFocusListener(BEToolBarUI.this.toolBarFocusListener);
      }

      BEToolBarUI.this.setBorderToNormal(c);
    }
  }
}