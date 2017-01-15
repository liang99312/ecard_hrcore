/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.uimanager.lnf.utils;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 *
 * @author mxliteboss
 */
public class MySwingUtilities2 {
     public static FontMetrics getFontMetrics(JComponent c, Graphics g)
  {
    return (FontMetrics)invokeSwingUtilities2StaticMethod("getFontMetrics", 
      new Class[] { JComponent.class, Graphics.class }, 
      new Object[] { c, g });
  }

  public static FontMetrics getFontMetrics(JComponent c, Graphics g, Font font)
  {
    return (FontMetrics)invokeSwingUtilities2StaticMethod("getFontMetrics", 
      new Class[] { JComponent.class, Graphics.class, Font.class }, 
      new Object[] { c, g, font });
  }

  public static int stringWidth(JComponent c, FontMetrics fm, String string)
  {
    return ((Integer)invokeSwingUtilities2StaticMethod("stringWidth", 
      new Class[] { JComponent.class, FontMetrics.class, String.class }, 
      new Object[] { c, fm, string })).intValue();
  }

  public static void drawString(JComponent c, Graphics g, String text, int x, int y)
  {
    invokeSwingUtilities2StaticMethod("drawString", 
      new Class[] { JComponent.class, Graphics.class, String.class, Integer.TYPE, Integer.TYPE }, 
      new Object[] { c, g, text, Integer.valueOf(x), Integer.valueOf(y) });
  }

  static boolean isPrinting(Graphics g)
  {
    return ((Boolean)invokeSwingUtilities2StaticMethod("isPrinting", 
      new Class[] { Graphics.class }, 
      new Object[] { g })).booleanValue();
  }

  private static boolean drawTextAntialiased(JComponent c)
  {
    return ((Boolean)invokeSwingUtilities2StaticMethod("drawTextAntialiased", 
      new Class[] { JComponent.class }, 
      new Object[] { c })).booleanValue();
  }

  public static boolean drawTextAntialiased(boolean aaText)
  {
    return ((Boolean)invokeSwingUtilities2StaticMethod("drawTextAntialiased", 
      new Class[] { Boolean.TYPE }, 
      new Object[] { Boolean.valueOf(aaText) })).booleanValue();
  }

  public static Graphics2D getGraphics2D(Graphics g)
  {
    return (Graphics2D)invokeSwingUtilities2StaticMethod("getGraphics2D", 
      new Class[] { Graphics.class }, 
      new Object[] { g });
  }

  public static void drawStringUnderlineCharAt(JComponent c, Graphics g, String text, int underlinedIndex, int x, int y)
  {
    invokeSwingUtilities2StaticMethod("drawStringUnderlineCharAt", 
      new Class[] { JComponent.class, Graphics.class, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE }, 
      new Object[] { c, g, text, Integer.valueOf(underlinedIndex), Integer.valueOf(x), Integer.valueOf(y) });
  }

  public static String clipStringIfNecessary(JComponent c, FontMetrics fm, String string, int availTextWidth)
  {
    string = (String)invokeSwingUtilities2StaticMethod("clipStringIfNecessary", 
      new Class[] { JComponent.class, FontMetrics.class, String.class, Integer.TYPE }, 
      new Object[] { c, fm, string, Integer.valueOf(availTextWidth) });
    return string;
  }

  public static String clipString(JComponent c, FontMetrics fm, String string, int availTextWidth)
  {
    string = (String)invokeSwingUtilities2StaticMethod("clipString", 
      new Class[] { JComponent.class, FontMetrics.class, String.class, Integer.TYPE }, 
      new Object[] { c, fm, string, Integer.valueOf(availTextWidth) });

    return string;
  }

  public static Object invokeSwingUtilities2StaticMethod(String methodName, Class[] paramsType, Object[] paramsValue)
  {
    return ReflectHelper.invokeStaticMethod(ReflectHelper.getClass(getSwingUtilities2ClassName()), 
      methodName, paramsType, paramsValue);
  }

  public static String getSwingUtilities2ClassName()
  {
    if (JVM.current().isOrLater(16)) {
      return "sun.swing.SwingUtilities2";
    }

    return "com.sun.java.swing.SwingUtilities2";
  }
}
