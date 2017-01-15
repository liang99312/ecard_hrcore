/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.UIManager;
/**
 *
 * @author mxliteboss
 */
public class BEUtils {
    public static ImageIcon filterWithRescaleOp(ImageIcon iconBottom, float redFilter, float greenFilter, float blueFilter, float alphaFilter)
  {
    try
    {
      int w = iconBottom.getIconWidth(); int h = iconBottom.getIconHeight();

      BufferedImage bi = new BufferedImage(w, h, 2);
      Graphics2D gg = (Graphics2D)bi.getGraphics();
      gg.drawImage(iconBottom.getImage(), 0, 0, w, h, null);

      float[] scales = { redFilter, greenFilter, blueFilter, alphaFilter };
      float[] offsets = new float[4];
      RescaleOp rop = new RescaleOp(scales, offsets, null);

      rop.filter(bi, bi);
      return new ImageIcon(bi);
    }
    catch (Exception e)
    {
//      LogHelper.error("filterWithRescaleOp³ö´íÁË£¬" + e.getMessage() + ",iconBottom=" + iconBottom);
    }return new ImageIcon();
  }

  public static void draw4RecCorner(Graphics g, int x, int y, int w, int h, int ¦Â, Color c)
  {
    Color oldColor = g.getColor();

    g.setColor(c);

    g.drawLine(x, y, x + ¦Â, y);
    g.drawLine(x + (w - ¦Â), y, x + w, y);

    g.drawLine(x, y, x, y + ¦Â);
    g.drawLine(x, y + (h - ¦Â), x, y + h);

    g.drawLine(x, y + h, x + ¦Â, y + h);
    g.drawLine(x + (w - ¦Â), y + h, x + w, y + h);

    g.drawLine(x + w, y + h, x + w, y + (h - ¦Â));
    g.drawLine(x + w, y + ¦Â, x + w, y);
    g.setColor(oldColor);
  }

  public static void componentsOpaque(Component[] comps, boolean opaque)
  {
    if (comps == null)
      return;
    Component[] arrayOfComponent = comps; int j = comps.length; for (int i = 0; i < j; i++) { Component c = arrayOfComponent[i];

      if ((c instanceof Container))
      {
        if ((c instanceof JComponent))
          ((JComponent)c).setOpaque(opaque);
        componentsOpaque(((Container)c).getComponents(), opaque);
      }
      else if ((c instanceof JComponent)) {
        ((JComponent)c).setOpaque(opaque);
      }
    }
  }

  public static void setAntiAliasing(Graphics2D g2, boolean antiAliasing)
  {
    if (antiAliasing)
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
        RenderingHints.VALUE_ANTIALIAS_ON);
    else
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
        RenderingHints.VALUE_ANTIALIAS_OFF);
  }

  public static void fillTriangle(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, Color c)
  {
    int[] x = new int[3]; int[] y = new int[3];

    x[0] = x1; x[1] = x2; x[2] = x3;
    y[0] = y1; y[1] = y2; y[2] = y3;
    int n = 3;

    Polygon p = new Polygon(x, y, n);

    g.setColor(c);
    g.fillPolygon(p);
  }

  public static void drawDashedRect(Graphics g, int x, int y, int width, int height)
  {
    drawDashedRect(g, x, y, width, height, 6, 6, 2, 2);
  }

  public static void drawDashedRect(Graphics g, int x, int y, int width, int height, int arcWidth, int arcHeight, int separator_solid, int separator_space)
  {
    setAntiAliasing((Graphics2D)g, true);

    Stroke oldStroke = ((Graphics2D)g).getStroke();
    Stroke sroke = new BasicStroke(1.0F, 0, 
      2, 0.0F, new float[] { separator_solid, separator_space }, 0.0F);
    ((Graphics2D)g).setStroke(sroke);

    g.drawRoundRect(x, y, 
      width - 1, height - 1, 
      arcWidth, arcHeight);

    ((Graphics2D)g).setStroke(oldStroke);
    setAntiAliasing((Graphics2D)g, false);
  }

  public static void drawDashedRect(Graphics g, int x, int y, int width, int height, int step, boolean top, boolean left, boolean bottom, boolean right)
  {
    int drawStep = step == 0 ? 1 : 2 * step;
    int drawLingStep = step == 0 ? 1 : step;

    for (int vx = x; vx < x + width; vx += drawStep)
    {
      if (top)
        g.fillRect(vx, y, drawLingStep, 1);
      if (bottom) {
        g.fillRect(vx, y + height - 1, drawLingStep, 1);
      }

    }

    for (int vy = y; vy < y + height; vy += drawStep)
    {
      if (left)
        g.fillRect(x, vy, 1, drawLingStep);
      if (right)
        g.fillRect(x + width - 1, vy, 1, drawLingStep);
    }
  }

  public static Color getColor(Color basic, int r, int g, int b)
  {
    return new Color(getColorInt(basic.getRed() + r), 
      getColorInt(basic.getGreen() + g), 
      getColorInt(basic.getBlue() + b), 
      getColorInt(basic.getAlpha()));
  }

  public static Color getColor(Color basic, int r, int g, int b, int a)
  {
    return new Color(getColorInt(basic.getRed() + r), 
      getColorInt(basic.getGreen() + g), 
      getColorInt(basic.getBlue() + b), 
      getColorInt(basic.getAlpha() + a));
  }

  public static int getColorInt(int rgb)
  {
    return rgb > 255 ? 255 : rgb < 0 ? 0 : rgb;
  }

  public static int getStrPixWidth(FontMetrics fm, String str)
  {
    return fm.stringWidth(str);
  }

  public static int getStrPixWidth(Font f, String str)
  {
    return getStrPixWidth(Toolkit.getDefaultToolkit().getFontMetrics(f), str);
  }

  public static TexturePaint createTexturePaint(Image image)
  {
    int imageWidth = image.getWidth(null);
    int imageHeight = image.getHeight(null);
    BufferedImage bi = new BufferedImage(imageWidth, imageHeight, 2);
    Graphics2D g2d = bi.createGraphics();
    g2d.drawImage(image, 0, 0, null);
    g2d.dispose();
    return new TexturePaint(bi, new Rectangle(0, 0, imageWidth, imageHeight));
  }

  public static int getInt(Object key, int defaultValue)
  {
    Object value = UIManager.get(key);

    if ((value instanceof Integer))
    {
      return ((Integer)value).intValue();
    }
    if ((value instanceof String))
    {
      try
      {
        return Integer.parseInt((String)value);
      }
      catch (NumberFormatException localNumberFormatException)
      {
      }
    }
    return defaultValue;
  }

  public static void fillTextureRoundRec(Graphics2D g2, Color baseColor, int x, int y, int w, int h, int arc)
  {
    fillTextureRoundRec(g2, baseColor, 
      x, y, w, h, arc, 35);
  }

  public static void fillTextureRoundRec(Graphics2D g2, Color baseColor, int x, int y, int w, int h, int arc, int colorDelta)
  {
    setAntiAliasing(g2, true);

    Paint oldpaint = g2.getPaint();
    GradientPaint gp = new GradientPaint(x, y, 
      getColor(baseColor, colorDelta, colorDelta, colorDelta), 
      x, y + h, baseColor);

    g2.setPaint(gp);
    g2.fillRoundRect(x, y, w, h, arc, arc);
    g2.setPaint(oldpaint);
    setAntiAliasing(g2, false);
  }
}
