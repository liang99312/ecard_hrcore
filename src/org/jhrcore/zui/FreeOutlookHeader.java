package org.jhrcore.zui;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class FreeOutlookHeader extends FreeHeader {

    private JPanel toolbar = new JPanel(new BorderLayout());//toolbarLayout);

    public FreeOutlookHeader() {
        init();
    }

    private void init() {
        toolbar.setOpaque(false);
        toolbar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        this.add(toolbar, BorderLayout.CENTER);
    }

    @Override
    protected Object getResizeHandlerLayoutConstraint() {
        return BorderLayout.EAST;
    }

    @Override
    protected Object getShrinkHandlerLayoutConstraint() {
        return BorderLayout.WEST;
    }

    @Override
    protected Border createBorder() {
        return BorderFactory.createEmptyBorder(0, 10, 4, 7);//¿ØÖÆ×ó²à¶¥²¿Ãæ°å±ßÔµ
    }

    @Override
    protected ImageIcon getShrinkIcon(boolean shrinked) {
        if (shrinked) {
            return (ImageIcon) RIGHT_ARROW_ICON;
        } else {
            return (ImageIcon) LEFT_ARROW_ICON;
        }
    }

    @Override
    protected JComponent getCenterComponent() {
        return null;
    }

    @Override
    public void setShrink(boolean shrinked) {
        super.setShrink(shrinked);
        toolbar.setVisible(!shrinked);
    }

    @Override
    protected int getShrinkedWidth() {
        return FreeUtil.OUTLOOK_SHRINKED_WIDTH;
    }

    public JPanel getToolBar() {
        return this.toolbar;
    }
}
