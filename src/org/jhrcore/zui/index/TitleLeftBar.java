package org.jhrcore.zui.index;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.TexturePaint;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import org.jhrcore.zui.FreeTabbedPane;
import org.jhrcore.zui.FreeUtil;
import org.jhrcore.util.ImageUtil;

public class TitleLeftBar extends JPanel {

    private Image selectedLeftImage = ImageUtil.getImage("indexleft.jpg");
    private Image selectedRightImage = ImageUtil.getImage("indexright.jpg");
    private TexturePaint selectedPaint = FreeUtil.createTexturePaint("indexcenter.jpg");
    private Icon icon = ImageUtil.getIcon("tb_sj.gif");
    private JButton btnClose = new JButton();
    private JLabel lbTitle = new JLabel();
    private FreeTabbedPane tab = null;
    private Color selectedTitleColor = new Color(120, 120, 125);
    private Color unselectedTitleColor = Color.black;
    private Border border = BorderFactory.createEmptyBorder(0, 10, 0, 10);

    public TitleLeftBar() {
        init();
    }

    private void init() {
        lbTitle.setOpaque(false);
        lbTitle.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));
        lbTitle.setVerticalAlignment(SwingConstants.CENTER);
        //lbTitle.setFont(FreeUtil.FONT_12_BOLD);
        lbTitle.setIcon(icon);
        this.setLayout(new BorderLayout());
        this.add(this.lbTitle, BorderLayout.CENTER);
        this.setBorder(border);
        this.setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (true) {
            g2d.drawImage(selectedLeftImage, 0, 0, null);
            g2d.setPaint(selectedPaint);
            int x = selectedLeftImage.getWidth(null);
            int y = 0;
            int width = getWidth() - x - selectedRightImage.getWidth(null);
            int height = getHeight();
            g2d.fillRect(x, y, width, height);
            g2d.drawImage(selectedRightImage, x + width, 0, null);
        }
    }

//    @Override
//    public Dimension getPreferredSize() {
//        int width = super.getPreferredSize().width;
//        if (!this.isTabSelected()) {
//            width = Math.min(width, tab.getPreferredUnselectedTabWidth());
//        }
//        int height = tab.getPreferredTabHeight();
//        return new Dimension(width, height);
//    }
//    public boolean isTabSelected() {
////        int index = tab.indexOfTabComponent(this);
//        int selectedIndex = tab.getSelectedIndex();
//        return selectedIndex == index;
//    }
    public void setTitle(String title) {
        this.lbTitle.setText(title);
    }

    public void updateSelection(boolean selected) {
        if (selected) {
            this.lbTitle.setForeground(selectedTitleColor);
        } else {
            this.lbTitle.setForeground(unselectedTitleColor);
        }
        this.btnClose.setVisible(selected);
    }

    private void closeTab() {
        int index = tab.indexOfTabComponent(this);
        tab.removeTabAt(index);
    }
}
