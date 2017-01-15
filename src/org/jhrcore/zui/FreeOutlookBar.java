package org.jhrcore.zui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.TexturePaint;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import org.jhrcore.util.ImageUtil;

public class FreeOutlookBar extends JPanel {

  //  private Image backgroundSelectedLeft = ImageUtil.getImage("leftnavbg.jpg");
//    private Image backgroundImage = ImageUtil.getImage("index_45.jpg");//首页左侧顶部工具栏背景
//    private TexturePaint paint = FreeUtil.createTexturePaint("index_45.jpg");
    //新界面
    private String backgroundSelectedImageURL = "menu_op_c.jpg"; //默认中间渲染
    private Image selectedLeftImage = ImageUtil.getImage("menu_op_l.jpg");//默认左侧
    private Image selectedRightImage = ImageUtil.getImage("menu_op_r.jpg");//默认右侧
    private String backgroundUnselectedImageURL = "menu_c.jpg";
    private Image unselectedLeftImage = ImageUtil.getImage("menu_l.jpg");
    private Image unselectedRightImage = ImageUtil.getImage("menu_r.jpg");
    private TexturePaint selectedPaint = FreeUtil.createTexturePaint(backgroundSelectedImageURL);
    private TexturePaint unselectedPaint = FreeUtil.createTexturePaint(backgroundUnselectedImageURL);
    private Icon icon = ImageUtil.getIcon("menu_circle.png");
    private Icon icon_s = ImageUtil.getIcon("menu_circle_h.png");
    //结束
    //private TexturePaint selectedPaint = FreeUtil.createTexturePaint(backgroundSelectedLeft);
    private JLabel lbHandler = new JLabel();
    private Border handlerBorder = BorderFactory.createEmptyBorder(0, 0, 0, 16);
    private Border handlerShrinkedBorder = BorderFactory.createEmptyBorder(0, 0, 0, 22);
    private JLabel lbIcon = new JLabel();
    private JLabel lbTitle = new JLabel();
    private boolean selected = false;
    private Color titleColor = new Color(15, 64, 94);//菜单栏 标题  分组标题大写
    private Color selectedTitleColor = Color.white;
    private MouseListener mouseListener = new MouseAdapter() {

        @Override
        public void mouseReleased(MouseEvent e) {
            if (((JComponent) e.getSource()).contains(e.getPoint())) {
                changeStatus();
            }
        }
    };
    private FreeOutlookPane pane = null;
    private JScrollPane scroll = new JScrollPane(this);
  //  private Icon icon = ImageUtil.getIcon("menu1_bgarrow.gif");
//    private Icon selectedIcon = ImageUtil.getIcon("menu_circle.png");

    public FreeOutlookBar(FreeOutlookPane pane) {
        this.pane = pane;
        init();
    }

    public JComponent getContentComponent() {
        return scroll;
    }

    private void init() {
        this.setLayout(new BorderLayout());
        this.add(lbHandler, BorderLayout.EAST);
        lbIcon.setVerticalAlignment(SwingConstants.CENTER);
        lbIcon.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));
        this.add(lbIcon, BorderLayout.WEST);
        lbTitle.setVerticalAlignment(SwingConstants.CENTER);
        lbTitle.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        //lbTitle.setFont(FreeUtil.FONT_12_PLAIN);
        lbTitle.setForeground(titleColor);
        this.add(lbTitle, BorderLayout.CENTER);
        this.setBackground(FreeUtil.TASKCOLOR);
        this.lbHandler.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.lbTitle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.lbIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.lbHandler.addMouseListener(mouseListener);
        this.lbTitle.addMouseListener(mouseListener);
        this.lbIcon.addMouseListener(mouseListener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (this.isSelected()) {
            g2d.drawImage(selectedLeftImage, 0, 0, null);
            g2d.setPaint(selectedPaint);
            lbIcon.setIcon(icon_s);
            int x = selectedLeftImage.getWidth(null);
            int y = 0;
            int width = getWidth() - x - selectedRightImage.getWidth(null);
            int height = this.getHeight();
            g2d.fillRect(x, y, width, height);
            g2d.drawImage(selectedRightImage, x + width, 0, null);
        } else {
            g2d.drawImage(unselectedLeftImage, 0, 0, null);
            g2d.setPaint(unselectedPaint);
            lbIcon.setIcon(icon);
            int x = unselectedLeftImage.getWidth(null);
            int y = 0;
            int width = getWidth() - x - unselectedRightImage.getWidth(null);
            int height = this.getHeight();
            g2d.fillRect(x, y, width, height);
            g2d.drawImage(unselectedRightImage, x + width, 0, null);

            //draw a line in the bottom.
      //      g2d.setColor(FreeUtil.TAB_BOTTOM_LINE_COLOR);
          //  int lineY = this.getHeight() - 1;
          //  g2d.drawLine(0, lineY, getWidth(), lineY);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(super.getPreferredSize().width, 32);//backgroundImage.getHeight(null));
    }

    public void setSelected(boolean selected) {
        if (selected != this.selected) {
            if (!isSelected()) {
                pane.closeAllBars();
            }
            this.selected = selected;
            if (selected) {
                lbTitle.setForeground(selectedTitleColor);
            } else {
                lbTitle.setForeground(titleColor);
            }
            pane.updateLayoutConstraint(this.getContentComponent(), selected);
            pane.setAdditionalPaneVisible(!selected);
            pane.updateUI();
        }
    }

    public void changeStatus() {
        this.setSelected(!this.isSelected());
    }

    public boolean isSelected() {
        return selected;
    }

    public void setTitle(String title) {
        this.lbTitle.setText(title);
        this.lbTitle.setToolTipText(title);
        this.lbHandler.setToolTipText(title);
        this.lbIcon.setToolTipText(title);
    }

    public String getTitle() {
        return this.lbTitle.getText();
    }

    public FreeOutlookPane getFreeOutlookPane() {
        return pane;
    }

    public void headerShrinkChanged(boolean headShrinked) {
        if (headShrinked) {
            this.lbHandler.setBorder(handlerShrinkedBorder);
        } else {
            this.lbHandler.setBorder(handlerBorder);
        }
    }
}
