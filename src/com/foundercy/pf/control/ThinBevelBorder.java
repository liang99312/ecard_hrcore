package com.foundercy.pf.control;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.border.BevelBorder;
public class ThinBevelBorder extends BevelBorder
   { /**
	 * 
	 */
	private static final long serialVersionUID = -6171421619928936681L;
	public ThinBevelBorder(int bevelType)
     {
		super(bevelType);
     }

	public ThinBevelBorder(int bevelType, Color highlight, Color shadow){
       super(bevelType, highlight, shadow); }
     public ThinBevelBorder(int bevelType, Color highlightOuterColor, Color highlightInnerColor, Color shadowOuterColor, Color shadowInnerColor)
     {
       super(bevelType, highlightOuterColor, highlightInnerColor, shadowOuterColor, shadowInnerColor);
     }
     protected void paintRaisedBevel(Component c, Graphics g, int x, int y, int width, int height)
     {
       try
       {
         Color oldColor = g.getColor();
         int h = height;
         int w = width;
         g.translate(x, y);
         g.setColor(getHighlightInnerColor(c));
        g.drawLine(0, 0, 0, h - 3);

         g.drawLine(1, 0, w - 3, 0);
         g.setColor(getShadowInnerColor(c));
         g.drawLine(1, h - 1, w - 1, h - 1);
         g.drawLine(w - 1, 1, w - 1, h - 2);
         g.translate( -x, -y);
         g.setColor(oldColor);
       }
       catch (NullPointerException e) { }
     }
     protected void paintLoweredBevel(Component c, Graphics g, int x, int y, int width, int height)
     {
       try
       {
         Color oldColor = g.getColor();
         int h = height;
         int w = width;
         g.translate(x, y);
         g.setColor(getShadowInnerColor(c));
         g.drawLine(0, 0, 0, h - 1);
         g.drawLine(1, 0, w - 1, 0);
         g.setColor(getHighlightOuterColor(c));
        g.drawLine(1, h - 1, w - 1, h - 1);

         g.drawLine(w - 1, 1, w - 1, h - 2);
         g.translate( -x, -y);
         g.setColor(oldColor);
       }
       catch (NullPointerException e) { }
     }
}


