package org.jhrcore.zui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import org.jhrcore.util.ImageUtil;

public class FreeTabComponent extends JPanel {

    private String backgroundSelectedImageURL = "tab_header_selected_background.png";
    private Image selectedLeftImage = ImageUtil.getImage("tab_header_selected_background_left.png");
    private Image selectedRightImage = ImageUtil.getImage("tab_header_selected_background_right.png");
    private String backgroundUnselectedImageURL = "tab_header_background.png";
    private Image unselectedLeftImage = ImageUtil.getImage("tab_header_background_left.png");
    private Image unselectedRightImage = ImageUtil.getImage("tab_header_background_right.png");
    private TexturePaint selectedPaint = FreeUtil.createTexturePaint(backgroundSelectedImageURL);
    private TexturePaint unselectedPaint = FreeUtil.createTexturePaint(backgroundUnselectedImageURL);
    private Icon icon = ImageUtil.getIcon("tab_close.png");
    private Icon pressedIcon = ImageUtil.getIcon("tab_close_pressed.png");
    private JButton btnClose = new JButton();
    private JLabel lbTitle = new JLabel();
    private FreeTabbedPane tab = null;
    private Color selectedTitleColor = Color.black;
    private Color unselectedTitleColor = new Color(120, 120, 125);
    private Border border = BorderFactory.createEmptyBorder(0, 5, 0, 5);
    private String panelKey = "";

    public FreeTabComponent(FreeTabbedPane tab) {
        this.tab = tab;
        init();
        setupEvents();
    }

    private void init() {
        btnClose.setIcon(icon);
        btnClose.setPressedIcon(pressedIcon);
        btnClose.setToolTipText("¹Ø±Õ");
        btnClose.setMargin(FreeUtil.ZERO_INSETS);
        btnClose.setFocusPainted(false);
        btnClose.setBorder(BorderFactory.createEmptyBorder(0, 3, 1, 3));
        btnClose.setContentAreaFilled(false);
        lbTitle.setOpaque(false);
        lbTitle.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));
        lbTitle.setVerticalAlignment(SwingConstants.CENTER);
        lbTitle.setFont(FreeUtil.FONT_12_BOLD);

        this.setLayout(new BorderLayout());
        this.add(btnClose, BorderLayout.EAST);
        this.add(this.lbTitle, BorderLayout.CENTER);
        this.setBorder(border);
        this.setOpaque(false);
    }

    private void setupEvents() {
        btnClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                closeTab();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (isTabSelected()) {
            g2d.drawImage(selectedLeftImage, 0, 0, null);
            g2d.setPaint(selectedPaint);
            int x = selectedLeftImage.getWidth(null);
            int y = 0;
            int width = getWidth() - x - selectedRightImage.getWidth(null);
            int height = getHeight();
            g2d.fillRect(x, y, width, height);
            g2d.drawImage(selectedRightImage, x + width, 0, null);
        } else {
            g2d.drawImage(unselectedLeftImage, 0, 0, null);
            g2d.setPaint(unselectedPaint);
            int x = unselectedLeftImage.getWidth(null);
            int y = 0;
            int width = getWidth() - x - unselectedRightImage.getWidth(null);
            int height = getHeight();
            g2d.fillRect(x, y, width, height);
            g2d.drawImage(unselectedRightImage, x + width, 0, null);

            //draw a line in the bottom.
            g2d.setColor(FreeUtil.TAB_BOTTOM_LINE_COLOR);
            int lineY = this.getHeight() - 1;
            g2d.drawLine(0, lineY, getWidth(), lineY);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int width = super.getPreferredSize().width;
//        if (!this.isTabSelected()) {
//            width = Math.max(width, tab.getPreferredUnselectedTabWidth());
//        }
        int height = tab.getPreferredTabHeight();
        return new Dimension(width, height);
    }

    public boolean isTabSelected() {
        int index = tab.indexOfTabComponent(this);
        int selectedIndex = tab.getSelectedIndex();
        return selectedIndex == index;
    }

    public void setTitle(String title) {
        this.lbTitle.setText(title);
    }

    public void updateSelection(boolean selected) {
        if (selected) {
            this.lbTitle.setForeground(selectedTitleColor);
        } else {
            this.lbTitle.setForeground(unselectedTitleColor);
        }
//        this.btnClose.setVisible(selected);
    }

    private void closeTab() {
        int index = tab.indexOfTabComponent(this);
        tab.removeTabAt(index);
        tab.getPanelKey().remove(panelKey);
    }

    public void setPanelKey(String panelKey) {
        this.panelKey = panelKey;
    }

    public String getPanelKey() {
        return panelKey;
    }
}
