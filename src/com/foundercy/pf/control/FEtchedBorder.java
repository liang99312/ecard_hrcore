/*
 * $Id: FEtchedBorder.java,v 1.1.1.1 2009/04/07 08:12:33 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.JToolBar;
import javax.swing.border.AbstractBorder;
import javax.swing.text.JTextComponent;

/**
 * <p>Title: �ָ��߱߿�</p>
 * ����߿���<code>EtchedBorder</code>�ݱ����.
 * ��������߿���������߿���ͨ�����ÿ�������ѡ����ʾ����ʾ.
 * ���,����ͨ��������������,ֻ��ʾһ������,ʹ��������ʾ�ı�����Ϊһ���ָ���.
 * <p>
 * @version  $Revision: 1.1.1.1 $
 * @author   a
 */
public class FEtchedBorder extends AbstractBorder {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4242849235603740484L;

	public static final int RAISED  = 0;

    public static final int LOWERED = 1;

    private int etchedType;
    private boolean topOn;
    private boolean leftOn;
    private boolean bottomOn;
    private boolean rightOn;

    /**
     * ����һ�����߱߿�,ֻ�ж�������ʾ
     */
    public FEtchedBorder() {
        this(LOWERED);
    }

    /**
     * ����һ���߿�,ֻ�ж�������ʾ,��ָ����͹����
     * @param etchType ��͹����
     */
    public FEtchedBorder(int etchType) {
        super();
        setEtchedType(etchType);
        topOn = true;
        leftOn = false;
        rightOn = false;
        bottomOn = false;
    }

    /**
     * ���ذ�͹����
     * @see #setEtchedType
     */
    public int getEtchedType() {
        return etchedType;
    }

    /**
     * ���ð�͹����
     * @param etchType either <code>RAISED</code> or <code>LOWERED</code>.
     * @see #getEtchedType
     * @see #RAISED
     * @see #LOWERED
     */
    public void setEtchedType(int etchType) {
        this.etchedType = etchType;
    }

    /**
     * ���ض������Ƿ���ʾ
     */
    public boolean isTopSideVisible() {
        return topOn;
    }

    /**
     * ���ö������Ƿ���ʾ
     */
    public void setTopSideVisible(boolean visible) {
        topOn = visible;
    }

    /**
     * ���صױ����Ƿ���ʾ
     */
    public boolean isBottomSideVisible() {
        return bottomOn;
    }

    /**
     * ���õױ����Ƿ���ʾ
     */
    public void setBottomSideVisible(boolean visible) {
        bottomOn = visible;
    }

    /**
     * ����������Ƿ���ʾ
     */
    public boolean isLeftSideVisible() {
        return leftOn;
    }

    /**
     * ����������Ƿ���ʾ
     */
    public void setLeftSideVisible(boolean visible) {
        leftOn = visible;
    }

    /**
     * �����ұ����Ƿ���ʾ
     */
    public boolean isRightSideVisible() {
        return rightOn;
    }

    /**
     * �����ұ����Ƿ���ʾ
     */
    public void setRightSideVisible(boolean visible) {
        rightOn = visible;
    }

    /**
     * ���ر߿���ؼ��ļ��
     */
    public Insets getBorderInsets(Component c) {
        Insets insets = new Insets(
            topOn? 2: 0,
            leftOn? 2: 0,
            bottomOn? 2: 0,
            rightOn? 2: 0);
        Insets margin = null;
        if (c instanceof AbstractButton) {
            AbstractButton b = (AbstractButton)c;
            margin = b.getMargin();
        } else if (c instanceof JToolBar) {
            JToolBar t = (JToolBar)c;
            margin = t.getMargin();
        } else if (c instanceof JTextComponent) {
            JTextComponent t = (JTextComponent)c;
            margin = t.getMargin();
        }
        if (margin != null) {
            insets.top += margin.top;
            insets.left += margin.left;
            insets.bottom += margin.bottom;
            insets.right += margin.right;
        }
        return insets;
    }


    /**
     * ���Ʊ߿�,��д���෽��
     * <p>
     * @param comp  the component for which this border is being painted.
     * @param g     the paint graphics
     * @param x     the x position of theh painted border.
     * @param y     the y position of theh painted border.
     * @param width the width of the painted border.
     * @param height    the height of the painted border.
     */
    public void paintBorder(
            Component comp, Graphics g,
            int x, int y, int width, int height) {

        Color oldColor = g.getColor();
        Color color = comp.getBackground();
        Color brighter = color.brighter();
        Color darker = color.darker();
        boolean raised = (etchedType == RAISED);
        if (raised) {
            Color ctmp = brighter;
            brighter = darker;
            darker = ctmp;
        }

        int outerLineStartX = 0;
        int outerLineStartY = 0;
        int outerLineEndX = 0;
        int outerLineEndY = 0;
        int innerLineStartX = 0;
        int innerLineStartY = 0;
        int innerLineEndX = 0;
        int innerLineEndY = 0;

        if (topOn) {
            outerLineStartY = outerLineEndY = y +height/2;
            innerLineStartY = innerLineEndY = y+1 +height/2;
            if (leftOn) {
                outerLineStartX = x;
                innerLineStartX = x+1;
            } else {
                outerLineStartX = x;
                innerLineStartX = x;
            }
            if (rightOn) {
                outerLineEndX = x + width - 2;
                innerLineEndX = x + width - 3;
            } else {
                outerLineEndX = x + width - 1;
                innerLineEndX = x + width - 1;
            }
            g.setColor( darker );
            g.drawLine( outerLineStartX, outerLineStartY, outerLineEndX, outerLineEndY);
            g.setColor( brighter  );
            g.drawLine( innerLineStartX, innerLineStartY, innerLineEndX, innerLineEndY);
        }

        if (rightOn) {
            outerLineStartX = outerLineEndX = x + width - 1 -width/2;
            innerLineStartX = innerLineEndX = x + width - 2 -width/2;
            if (topOn) {
                outerLineStartY = y;
                innerLineStartY = y + 1;
            } else {
                outerLineStartY = y;
                innerLineStartY = y;
            }
            if (bottomOn) {
                outerLineEndY = y + height - 2;
                innerLineEndY = y + height - 3;
            } else {
                outerLineEndY = y + height - 1;
                innerLineEndY = y + height - 1;
            }
            g.setColor( brighter );
            g.drawLine( outerLineStartX, outerLineStartY, outerLineEndX, outerLineEndY);
            g.setColor( darker );
            g.drawLine( innerLineStartX, innerLineStartY, innerLineEndX, innerLineEndY);
        }

        if (bottomOn) {
            outerLineStartY = outerLineEndY = y+height-1;
            innerLineStartY = innerLineEndY = y+height-2;
            if (leftOn) {
                outerLineStartX = x;
                innerLineStartX = x;
            } else {
                outerLineStartX = x;
                innerLineStartX = x;
            }
            if (rightOn) {
                outerLineEndX = x + width - 1;
                innerLineEndX = x + width - 2;
            } else {
                outerLineEndX = x + width - 1;
                innerLineEndX = x + width - 1;
            }
            g.setColor( brighter  );
            g.drawLine( outerLineStartX, outerLineStartY, outerLineEndX, outerLineEndY);
            g.setColor( darker );
            g.drawLine( innerLineStartX, innerLineStartY, innerLineEndX, innerLineEndY);
        }

        if (leftOn) {
            outerLineStartX = outerLineEndX = x;
            innerLineStartX = innerLineEndX = x+1;
            if (topOn) {
                outerLineStartY = y + 1;
                innerLineStartY = y + 2;
            } else {
                outerLineStartY = y;
                innerLineStartY = y;
            }
            if (bottomOn) {
                outerLineEndY = y + height - 3;
                innerLineEndY = y + height - 3;
            } else {
                outerLineEndY = y + height - 1;
                innerLineEndY = y + height - 1;
            }
            g.setColor( darker );
            g.drawLine( outerLineStartX, outerLineStartY, outerLineEndX, outerLineEndY);
            g.setColor( brighter );
            g.drawLine( innerLineStartX, innerLineStartY, innerLineEndX, innerLineEndY);
        }

        g.setColor( oldColor );
    }

}
