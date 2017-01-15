/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.ch17_split;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import org.jhrcore.uimanager.lnf.utils.BEUtils;
import sun.swing.DefaultLookup;
/**
 *
 * @author mxliteboss
 */
public class BESplitPaneDivider extends BasicSplitPaneDivider
{
  private int oneTouchSize;
  protected final Color TOUCH_BUTTON_COLOR = new Color(58, 135, 173);
  protected static final int TOUCH_DECRATED_BUTTON_W = 5;
  protected static final int TOUCH_DECRATED_BUTTON_H = 30;
  protected static final Color TOUCH_DECRATED_BUTTON_COLOR = new Color(180, 180, 180);

  protected static final Color TOUCH_DECRATED_BUTTON_HILIGHT_COLOR = Color.white;

  public BESplitPaneDivider(BasicSplitPaneUI ui)
  {
    super(ui);

    this.oneTouchSize = DefaultLookup.getInt(ui.getSplitPane(), ui, 
      "SplitPane.oneTouchButtonSize", 6);
  }

  public void paint(Graphics g)
  {
    Color bgColor = this.splitPane.hasFocus() ? 
      UIManager.getColor("SplitPane.shadow") : getBackground();
    Dimension size = getSize();
    Graphics2D g2 = (Graphics2D)g;
    BEUtils.setAntiAliasing((Graphics2D)g, true);

    if (bgColor != null)
    {
      int orient = this.splitPane.getOrientation();
      if (orient == 1)
      {
        int halfWidth = size.width / 2;
        int halfHeight = size.height / 2;

        Stroke oldStroke = ((Graphics2D)g).getStroke();
        Stroke sroke = new BasicStroke(1.0F, 0, 
          2, 0.0F, new float[] { 2.0F, 2.0F }, 0.0F);
        ((Graphics2D)g).setStroke(sroke);

        g.setColor(TOUCH_DECRATED_BUTTON_COLOR);

        g.drawLine(halfWidth + 0, 0, halfWidth + 0, size.height);

        g.setColor(TOUCH_DECRATED_BUTTON_HILIGHT_COLOR);
        g.drawLine(halfWidth + 1, 0, halfWidth + 1, size.height);

        ((Graphics2D)g).setStroke(oldStroke);
//取消该UI自带的SPLIT中间图标
//        int decratedButton_w = 5;
//        int decratedButton_h = 30;
//        int diverTouchStartX = halfWidth - decratedButton_w / 2;
//        __Icon9Factory__.getInstance().getSplitTouchBg1()
//          .draw((Graphics2D)g, diverTouchStartX, halfHeight - decratedButton_h / 2, 
//          decratedButton_w, decratedButton_h);
      }
      else
      {
        int halfHeight = size.height / 2;
        int halfWidth = size.width / 2;

        Stroke oldStroke = ((Graphics2D)g).getStroke();
        Stroke sroke = new BasicStroke(1.0F, 0, 
          2, 0.0F, new float[] { 2.0F, 2.0F }, 0.0F);
        ((Graphics2D)g).setStroke(sroke);

        g.setColor(TOUCH_DECRATED_BUTTON_COLOR);

        g.drawLine(0, halfHeight + 0, size.width, halfHeight + 0);

        g.setColor(TOUCH_DECRATED_BUTTON_HILIGHT_COLOR);
        g.drawLine(0, halfHeight + 1, size.width, halfHeight + 1);

        ((Graphics2D)g).setStroke(oldStroke);
//取消该UI自带的SPLIT中间图标
//        int decratedButton_w = 5;
//        int decratedButton_h = 30;
//        int diverTouchStartY = halfHeight - decratedButton_w / 2;
//        __Icon9Factory__.getInstance().getSplitTouchBg1()
//          .draw((Graphics2D)g, halfWidth - decratedButton_h, diverTouchStartY, 
//          decratedButton_h, decratedButton_w);
      }

      BEUtils.setAntiAliasing((Graphics2D)g, false);
    }

    super.paint(g);
  }

  protected JButton createLeftOneTouchButton()
  {
    JButton b = new JButton()
    {
      public void setBorder(Border b)
      {
      }

      public void paint(Graphics g)
      {
        if (BESplitPaneDivider.this.splitPane != null)
        {
          int[] xs = new int[3];
          int[] ys = new int[3];

          g.setColor(getBackground());
          g.fillRect(0, 0, getWidth(), getHeight());

          g.setColor(BESplitPaneDivider.this.TOUCH_BUTTON_COLOR);

          BEUtils.setAntiAliasing((Graphics2D)g, true);

          if (BESplitPaneDivider.this.orientation == 0)
          {
            int blockSize = Math.min(getHeight(), BESplitPaneDivider.this.oneTouchSize);
            xs[0] = blockSize;
            xs[1] = 0;
            xs[2] = (blockSize << 1);
            ys[0] = 0;
            int tmp111_109 = blockSize; ys[2] = tmp111_109; ys[1] = tmp111_109;
            g.drawPolygon(xs, ys, 3);
          }
          else
          {
            int blockSize = Math.min(getWidth(), BESplitPaneDivider.this.oneTouchSize);
            int tmp146_144 = blockSize; xs[2] = tmp146_144; xs[0] = tmp146_144;
            xs[1] = 0;
            ys[0] = 0;
            ys[1] = blockSize;
            ys[2] = (blockSize << 1);
          }
          g.fillPolygon(xs, ys, 3);

          BEUtils.setAntiAliasing((Graphics2D)g, false);
        }
      }

      public boolean isFocusTraversable()
      {
        return false;
      }
    };
    b.setMinimumSize(new Dimension(this.oneTouchSize, this.oneTouchSize));
    b.setCursor(Cursor.getPredefinedCursor(0));
    b.setFocusPainted(false);
    b.setBorderPainted(false);
    b.setRequestFocusEnabled(false);
    return b;
  }

  protected JButton createRightOneTouchButton()
  {
    JButton b = new JButton() {
      public void setBorder(Border border) {
      }
      public void paint(Graphics g) {
        if (BESplitPaneDivider.this.splitPane != null) {
          int[] xs = new int[3];
          int[] ys = new int[3];

          g.setColor(getBackground());
          g.fillRect(0, 0, getWidth(), 
            getHeight());

          BEUtils.setAntiAliasing((Graphics2D)g, true);

          if (BESplitPaneDivider.this.orientation == 0) {
            int blockSize = Math.min(getHeight(), BESplitPaneDivider.this.oneTouchSize);
            xs[0] = blockSize;
            xs[1] = (blockSize << 1);
            xs[2] = 0;
            ys[0] = blockSize;
            int tmp100_99 = 0; ys[2] = tmp100_99; ys[1] = tmp100_99;
          }
          else {
            int blockSize = Math.min(getWidth(), BESplitPaneDivider.this.oneTouchSize);
            int tmp127_126 = 0; xs[2] = tmp127_126; xs[0] = tmp127_126;
            xs[1] = blockSize;
            ys[0] = 0;
            ys[1] = blockSize;
            ys[2] = (blockSize << 1);
          }

          g.setColor(BESplitPaneDivider.this.TOUCH_BUTTON_COLOR);

          g.fillPolygon(xs, ys, 3);

          BEUtils.setAntiAliasing((Graphics2D)g, false);
        }
      }

      public boolean isFocusTraversable() {
        return false;
      }
    };
    b.setMinimumSize(new Dimension(this.oneTouchSize, this.oneTouchSize));
    b.setCursor(Cursor.getPredefinedCursor(0));
    b.setFocusPainted(false);
    b.setBorderPainted(false);
    b.setRequestFocusEnabled(false);
    return b;
  }
}