/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.zui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.TexturePaint;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import org.jhrcore.util.ImageUtil;

/**
 *
 * @author Administrator
 */
public class SplitPanel extends JPanel {

    private Image backgroundRightImage = ImageUtil.getImage("slidebtn_bg.jpg");
    private Icon leftImage = ImageUtil.getIcon("split_left.jpg");
    private Icon rightImage = ImageUtil.getIcon("split_right.jpg");
    private TexturePaint paint = FreeUtil.createTexturePaint(backgroundRightImage);
    public JLabel lbResizeHandler;
    private FreeHeader header;

    public SplitPanel(FreeHeader header) {
        this.header = header;
        init();
    }

    protected Border createBorder() {
        return BorderFactory.createEmptyBorder(300, 0, 0, 0);
    }

    private void init() {
        this.setPreferredSize(new Dimension(backgroundRightImage.getWidth(null), 0));
        this.setOpaque(true);
        this.setBorder(createBorder());
        lbResizeHandler = new JLabel(leftImage);
        lbResizeHandler.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                header.changeShrink();
                lbResizeHandler.setIcon(header.isShrinked() ? rightImage : leftImage);
            }
        });
        lbResizeHandler.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(lbResizeHandler, BorderLayout.CENTER);
        this.addMouseListener(new MouseAdapter() {

            Point lastPoint = null;

            @Override
            public void mousePressed(MouseEvent e) {
                if (!header.isShrinked()) {
                    lastPoint = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!header.isShrinked()) {
                    if (lastPoint != null) {
                        JComponent parent = (JComponent) header.getParent();
                        Dimension size = parent.getPreferredSize();
                        Point thisPoint = e.getPoint();
                        int xMovement = lastPoint.x-thisPoint.x ;
                        size.width -= xMovement;
                        size.width = Math.max(size.width, FreeUtil.LIST_SHRINKED_WIDTH);
                        parent.setPreferredSize(size);
                        header.revalidateParent();
                    }
                }
                lastPoint = null;
            }
        });
//    
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(paint);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public JLabel getLbResizeHandler() {
        return lbResizeHandler;
    }

    public void setLbResizeHandler(JLabel lbResizeHandler) {
        this.lbResizeHandler = lbResizeHandler;
    }
}
