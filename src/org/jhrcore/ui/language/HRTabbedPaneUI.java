package org.jhrcore.ui.language;

import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalTabbedPaneUI;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public class HRTabbedPaneUI extends MetalTabbedPaneUI{
    
//    private BasicArrowButton btnShow=new BasicArrowButton(SOUTH);
//    private ActionListener actionListener;
    
    @Override
    public void paint(Graphics g, JComponent c) {
        JTabbedPane tmp = (JTabbedPane) c;
//        if(tmp instanceof DockTabbedPane ){
//            ((DockTabbedPane)tmp).setTabLayoutPolicy(1);
//        }else{
        tmp.setTabLayoutPolicy(1);
//        }
//        for (int i = 0; i < tmp.getTabCount(); i++) {
//            tmp.setTitleAt(i, LanguageContext.getLanguageContext().c2e(tmp.getTitleAt(i)));
//        }
        super.paint(g, c);
    }

    public static ComponentUI createUI(JComponent c) {
        return new HRTabbedPaneUI();
    }
//    @Override
//    public void installUI(JComponent c) {
//        if(c instanceof DockTabbedPane){
//            ((DockTabbedPane)c).setTabLayoutPolicy(1);
//        }
//        super.installUI(c);
//    }
//    
//    @Override
//    protected void installComponents() {
//        super.installComponents();
//        if(tabPane instanceof DockTabbedPane){
//            this.tabPane.add(btnShow);
//        }
//    }
//
//    @Override
//    protected void installListeners() {
//        super.installListeners();
//        actionListener=new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JPopupMenu jPopupMenu=new JPopupMenu();
//                int count=tabPane.getTabCount();
//                for (int i = 0; i <count; i++) {
//                    final int index=i; 
//                    String title=tabPane.getTitleAt(index);
//                    if(title==null || "".equals(title)){
//                        if(tabPane.getComponentAt(index) instanceof SingleDockableContainer)
//                            title= ((SingleDockableContainer)tabPane.getComponentAt(index)).getDockable().getDockKey().getTooltip();
//                        else
//                            title=tabPane.getToolTipTextAt(index);
//                    }
//                    title=(title==null||"".equals(title))?((i+1)+""):title;
//                    JMenuItem item=new JMenuItem(title);
//                    if(tabPane.getSelectedIndex()==index){
//                        item.setText(item.getText()+" <-");
//                        item.setOpaque(true);
//                        item.setForeground(Color.blue);
//                    }
//                    item.addActionListener(new ActionListener() {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            tabPane.setSelectedIndex(index);
//                        }
//                    });
//                    jPopupMenu.add(item);
//                }
//                jPopupMenu.show(btnShow, -10, 25);
//            }
//        };
//        btnShow.addActionListener(actionListener);
//    }
//
//    @Override
//    protected void uninstallListeners() {
//        super.uninstallListeners();
//        btnShow.removeActionListener(actionListener);
//    }
//    
//
//    @Override
//    protected void uninstallComponents() {
//        super.uninstallComponents();
//        if(tabPane instanceof DockTabbedPane){
//            this.tabPane.remove(btnShow);
//        }
//
//    }
//
//    @Override
//    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
//        if(tabPane instanceof DockTabbedPane ){
//            return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight)+5;
//        }
//        return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight);
//    }
//
//    @Override
//    protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
//        if(tabPane instanceof DockTabbedPane ){
//            return super.calculateTabWidth(tabPlacement, tabIndex, metrics)+5;
//        }
//        return super.calculateTabWidth(tabPlacement, tabIndex, metrics);
//    }
//
//    
//    @Override
//    public void paint(Graphics g, JComponent c) {
//        JTabbedPane tmp = (JTabbedPane) c;
////        for (int i = 0; i < tmp.getTabCount(); i++) {
////            tmp.setTitleAt(i, LanguageContext.getLanguageContext().c2e(tmp.getTitleAt(i)));
////        }
//        super.paint(g, c);
//        try {
//            if (tabPane instanceof DockTabbedPane && tabPane.getTabPlacement() == TOP) {
//
//                int tabPlacement = tabPane.getTabPlacement();
//                int tabCount = tabPane.getTabCount();
//                Insets insets = tabPane.getInsets();
//                int tx, ty, tw, th; // tab area bounds
//                int cx, cy, cw, ch; // content area bounds
//                Insets contentInsets = getContentBorderInsets(tabPlacement);
//                Rectangle bounds = tabPane.getBounds();
//                int numChildren = tabPane.getComponentCount();
//                if (numChildren > 0) {
//                    switch (tabPlacement) {
//                        case LEFT:
//                            // calculate tab area bounds
//                            tw = calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
//                            th = bounds.height - insets.top - insets.bottom;
//                            tx = insets.left;
//                            ty = insets.top;
//
//                            // calculate content area bounds
//                            cx = tx + tw + contentInsets.left;
//                            cy = ty + contentInsets.top;
//                            cw = bounds.width - insets.left - insets.right - tw
//                                    - contentInsets.left - contentInsets.right;
//                            ch = bounds.height - insets.top - insets.bottom
//                                    - contentInsets.top - contentInsets.bottom;
//                            break;
//                        case RIGHT:
//                            // calculate tab area bounds
//                            tw = calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
//                            th = bounds.height - insets.top - insets.bottom;
//                            tx = bounds.width - insets.right - tw;
//                            ty = insets.top;
//
//                            // calculate content area bounds
//                            cx = insets.left + contentInsets.left;
//                            cy = insets.top + contentInsets.top;
//                            cw = bounds.width - insets.left - insets.right - tw
//                                    - contentInsets.left - contentInsets.right;
//                            ch = bounds.height - insets.top - insets.bottom
//                                    - contentInsets.top - contentInsets.bottom;
//                            break;
//                        case BOTTOM:
//                            // calculate tab area bounds
//                            tw = bounds.width - insets.left - insets.right;
//                            th = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
//                            tx = insets.left;
//                            ty = bounds.height - insets.bottom - th;
//
//                            // calculate content area bounds
//                            cx = insets.left + contentInsets.left;
//                            cy = insets.top + contentInsets.top;
//                            cw = bounds.width - insets.left - insets.right
//                                    - contentInsets.left - contentInsets.right;
//                            ch = bounds.height - insets.top - insets.bottom - th
//                                    - contentInsets.top - contentInsets.bottom;
//                            break;
//                        case TOP:
//                        default:
//                            // calculate tab area bounds
//                            tw = bounds.width - insets.left - insets.right;
//                            th = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
//                            tx = insets.left;
//                            ty = insets.top;
//
//                            // calculate content area bounds
//                            cx = tx + contentInsets.left;
//                            cy = ty + th + contentInsets.top;
//                            cw = bounds.width - insets.left - insets.right
//                                    - contentInsets.left - contentInsets.right;
//                            ch = bounds.height - insets.top - insets.bottom - th
//                                    - contentInsets.top - contentInsets.bottom;
//                    }
//
//
//                    for (int i = 0; i < numChildren; i++) {
//                        Component child = tabPane.getComponent(i);
//                        if (child instanceof BasicArrowButton && (((BasicArrowButton) child).getDirection() == WEST || ((BasicArrowButton) child).getDirection() == EAST)) {
//                            Component scrollbutton = child;
//                            Dimension bsize = scrollbutton.getPreferredSize();
//                            int bx = 0;
//                            int by = 0;
//                            int bw = bsize.width;
//                            int bh = bsize.height;
//                            boolean visible = false;
//
//                            int totalTabWidth = rects[tabCount - 1].x + rects[tabCount - 1].width;
//
//                            if (totalTabWidth > tw) {
//                                visible = true;
//                                bx = (((BasicArrowButton) child).getDirection() == WEST)
//                                        ? bounds.width - insets.left - bsize.width - 15
//                                        : bounds.width - insets.left - 2 * bsize.width - 15;
//                                by = (tabPlacement == TOP ? ty + th - bsize.height : ty);
//                            }
//                            child.setVisible(visible);
//                            if (visible) {
//                                child.setBounds(bx, by, bw, bh);
//                            }
//
//                        } else if (child instanceof BasicArrowButton && ((BasicArrowButton) child).getDirection() == SOUTH) {
//                            Dimension bsize = new Dimension(15, 17);
//                            int bx = 0;
//                            int by = 0;
//                            int bw = bsize.width;
//                            int bh = bsize.height;
////                            boolean visible = false;
////                            int totalTabWidth = rects[tabCount - 1].x + rects[tabCount - 1].width;
////                            if (totalTabWidth > tw) {
////                                visible = true;
////                                bx = bounds.width - insets.left - bsize.width;
////                                by = (tabPlacement == TOP ? ty + th - bsize.height : ty);
////                            }
////                            child.setVisible(visible);
////                            if (visible) {
////                                child.setBounds(bx, by, bw, bh);
////                            }
//                            bx = bounds.width - insets.left - bsize.width;
//                            by = (tabPlacement == TOP ? ty + th - bsize.height : ty);
//                            child.setBounds(bx, by, bw, bh);
//                        }
//                    }
//                }
//
//            }
//        } catch (Exception e) {
//        }
//
//    }
//
//    public static ComponentUI createUI(JComponent c) {
//        return new HRTabbedPaneUI();
//    }
}
