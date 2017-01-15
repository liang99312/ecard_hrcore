package org.jhrcore.zui.index;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import org.jhrcore.msg.dept.DeptMngMsg;
import org.jhrcore.zui.FreeUtil;
import org.jhrcore.ui.listener.IPickWindowObjListener;
import org.jhrcore.util.ImageUtil;

public class TitleBarPnl extends JPanel {

    private Image unselectedLeftImage = ImageUtil.getImage("index_56.jpg");
    private Image unselectedRightImage = ImageUtil.getImage("index_65.jpg");
    private TexturePaint unselectedPaint = FreeUtil.createTexturePaint("index_62.jpg");
//    private JButton btnMore = new JButton("更多>>");
    private JButton btnMore = new JButton( DeptMngMsg.msgMore.toString()+">>");
   
    private Border border = BorderFactory.createEmptyBorder(0, 5, 0, 5);
    private int preferredUnselectedTabWidth = 80;
    private int preferredTabHeight = ImageUtil.getIcon("tab_header_background.png").getIconHeight();
    private TitleLeftBar leftbar = new TitleLeftBar();
    private String title = "标题栏";
    private List<IPickWindowObjListener> listeners = new ArrayList();

    public void addPickWindowObjListener(IPickWindowObjListener listener) {
        listeners.add(listener);
    }

    public void delPickWindowObjListener(IPickWindowObjListener listener) {
        listeners.remove(listener);
    }

    public TitleBarPnl(String title) {
        this.title = title;
        init();
    }

    private void init() {
        this.setBackground(new Color(207, 224, 239));
        btnMore.setMargin(FreeUtil.ZERO_INSETS);
        btnMore.setFocusPainted(false);
        btnMore.setBorder(BorderFactory.createEmptyBorder(0, 3, 1, 3));
        btnMore.setContentAreaFilled(false);
        btnMore.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMore.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (IPickWindowObjListener listener : listeners) {
                    listener.pickObj(e);
                }
            }
        });
        btnMore.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                btnMore.setForeground(Color.BLUE);
            }

            /**
             * {@inheritDoc}
             */
            public void mouseExited(MouseEvent e) {
                btnMore.setForeground(Color.BLACK);
            }
        });
        JPanel butpnl = new JPanel(new GridLayout(1, 2));
        butpnl.setOpaque(false);
        butpnl.add(btnMore);
        this.setLayout(new BorderLayout());
        this.add(butpnl, BorderLayout.EAST);
        leftbar.setTitle(title);
        this.add(leftbar, BorderLayout.WEST);
        this.setBorder(border);
        this.setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
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
    }

    @Override
    public Dimension getPreferredSize() {
        int width = super.getPreferredSize().width;
        if (true) {
            width = Math.min(width, preferredUnselectedTabWidth);
        }
        int height = preferredTabHeight;
        return new Dimension(width, height);
    }
    public void setTitle(String title){
        leftbar.setTitle(title);
    }
}
