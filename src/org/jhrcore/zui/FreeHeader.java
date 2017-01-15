package org.jhrcore.zui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.TexturePaint;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import org.jhrcore.util.ImageUtil;

public class FreeHeader extends JPanel {

    public static final Icon RIGHT_ARROW_ICON = ImageUtil.getIcon("shrink_handler_right.png");
    public static final Icon LEFT_ARROW_ICON = ImageUtil.getIcon("fd.gif");
    private Color titleColor = new Color(215, 215, 216);
    private boolean shrinked = false;
    private Icon backgroundImageIcon = ImageUtil.getIcon("left_title_box.jpg");
    private Image backgroundLeftImage = ImageUtil.getImage("left_title_box.jpg");
    private Image backgroundRightImage = ImageUtil.getImage("left_title_box.jpg");
    private TexturePaint paint = FreeUtil.createTexturePaint("left_title_box.jpg");
    private int preferredHeight = backgroundImageIcon.getIconHeight();
    private JLabel lbResizeHandler = new JLabel(ImageUtil.getIcon("resize_handler.png"));
    private JLabel lbShrinkHandler = new JLabel(this.getShrinkIcon(shrinked));
    private JLabel lb = new JLabel("¹¦ÄÜÄ£¿é");
    private JLabel lbTitle = new JLabel();
    private int normalPreferredWidth = 0;
    public MouseListener shrinkListener = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            changeShrink();
        }
    };

    public FreeHeader() {
        init();
    }
    protected Border createBorder() {
        return BorderFactory.createEmptyBorder(0, 0, 0, 0);
    }

    private void init() {
        this.setBorder(createBorder());
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        lb.setForeground(new Color(32, 98, 146));
        lb.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 12));
        this.add(lb, getShrinkHandlerLayoutConstraint());

//        this.add(lbResizeHandler, getResizeHandlerLayoutConstraint());
        this.add(lbShrinkHandler, getResizeHandlerLayoutConstraint()); //getShrinkHandlerLayoutConstraint
        this.setPreferredSize(new Dimension(250, 50));

        JComponent centerComponent = getCenterComponent();
        if (centerComponent != null) {
            this.add(centerComponent, BorderLayout.CENTER);
        }
        lbShrinkHandler.addMouseListener(shrinkListener);
        lbTitle.setFont(FreeUtil.FONT_14_BOLD);
        lbTitle.setForeground(titleColor);
        lbTitle.setBorder(BorderFactory.createEmptyBorder(2, 8, 0, 0));

        updateCursor();

        //give a empty border so it is more easier to click this icon.
        lbShrinkHandler.setBorder(BorderFactory.createEmptyBorder(0, 8, 2, 5));
    }

    protected JComponent getCenterComponent() {
        return lbTitle;
    }

    protected Object getResizeHandlerLayoutConstraint() {
        return BorderLayout.WEST;
    }

    protected Object getShrinkHandlerLayoutConstraint() {
        return BorderLayout.EAST;
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        //fill background.
        g2d.setPaint(paint);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        //draw left image.
        g2d.drawImage(backgroundLeftImage, 0, 0, null);

        //draw right image.
        int x = this.getWidth() - backgroundRightImage.getWidth(null);
        int y = 0;
        g2d.drawImage(backgroundRightImage, x, y, null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(super.getPreferredSize().width, preferredHeight);
    }

    public void revalidateParent() {
        if (getParent() instanceof JComponent) {
            ((JComponent) getParent()).revalidate();
        }
    }

    public void changeShrink() {
        setShrink(!isShrinked());
    }

    public void setShrink(boolean shrinked) {
        if (shrinked != this.shrinked) {
            Container parent = getParent();
            Dimension size = parent.getPreferredSize();
            if (shrinked) {
                normalPreferredWidth = size.width;
                size = new Dimension(getShrinkedWidth(), size.height);
            } else {
                int width = normalPreferredWidth;
                int height = parent.getPreferredSize().height;
                size = new Dimension(width, height);
            }
            parent.setPreferredSize(size);
            lbShrinkHandler.setIcon(getShrinkIcon(shrinked));
            revalidateParent();
            this.shrinked = shrinked;
            updateCursor();
            this.lbTitle.setVisible(!shrinked);
            lbResizeHandler.setVisible(!shrinked);
        }
    }

    protected ImageIcon getShrinkIcon(boolean shrinked) {
        if (shrinked) {
            return (ImageIcon) LEFT_ARROW_ICON;
        } else {
            return (ImageIcon) RIGHT_ARROW_ICON;
        }
    }

    private void updateCursor() {
        if (shrinked) {
            this.lbResizeHandler.setCursor(Cursor.getDefaultCursor());
        } else {
            this.lbResizeHandler.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
        }
    }

    public boolean isShrinked() {
        return this.shrinked;
    }

    public void setTitle(String title) {
        lbTitle.setText(title);
    }

    public String getTitle() {
        return lbTitle.getText();
    }

    protected int getShrinkedWidth() {
        return FreeUtil.LIST_SHRINKED_WIDTH;
    }
}
