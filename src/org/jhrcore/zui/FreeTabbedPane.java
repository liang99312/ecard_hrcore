package org.jhrcore.zui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jhrcore.ui.task.IModulePanel;
import org.jhrcore.util.ImageUtil;

public class FreeTabbedPane extends JTabbedPane {

    private int preferredUnselectedTabWidth = 80;
    private int preferredTabHeight = ImageUtil.getIcon("tab_header_background_1.png").getIconHeight();
    private Hashtable<String, Component> panelKey = new Hashtable<String, Component>();
    private JMenuItem miClose = new JMenuItem("关闭");
    private JMenuItem miCloseOthers = new JMenuItem("关闭其他");
    private JMenuItem miCloseAll = new JMenuItem("关闭所有");
    private int closeThisTab = 0;
    private int closeOtherTab = 1;
    private int closeAllTab = 2;

    public FreeTabbedPane() {
        init();
    }

    private void init() {
        this.setFont(FreeUtil.FONT_12_BOLD);
        this.setForeground(FreeUtil.DEFAULT_TEXT_COLOR);
        this.setBorder(null);
        this.setFocusable(false);
        this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        this.setOpaque(false);
        this.setUI(new FreeTabbedPaneUI(this));
        this.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                //inform each tab component that select changed.
                updateTabComponents();
            }
        });
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    JPopupMenu pp = new JPopupMenu();
                    pp.add(miClose);
                    pp.add(miCloseOthers);
                    pp.add(miCloseAll);
                    pp.show(FreeTabbedPane.this, e.getX(), e.getY());
                }
            }
        });
        ActionListener alClose = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                closeTab(closeThisTab);
            }
        };
        miClose.addActionListener(alClose);
        miCloseOthers.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                closeTab(closeOtherTab);
            }
        });
        miCloseAll.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                closeTab(closeAllTab);
            }
        });
    }

//    public void addTab(String title, Component component) {
//        super.addTab(title, component);
//        int index = this.getTabCount() - 1;
//        FreeTabComponent tabComponent = new FreeTabComponent(this);
//        tabComponent.setTitle(title);
//        this.setTabComponentAt(index, tabComponent);
//        this.setToolTipTextAt(index, title);
//        updateTabComponents();
//    }
    public void addTab(String title, String model_key, Component component) {
        super.addTab(title, component);
        int index = this.getTabCount() - 1;
        FreeTabComponent tabComponent = new FreeTabComponent(this);
        tabComponent.setPanelKey(model_key);
        tabComponent.setTitle(title);
        this.setTabComponentAt(index, tabComponent);
        this.setToolTipTextAt(index, title);
        updateTabComponents();
        panelKey.put(model_key, component);
    }

    public void closeAllTab() {
        closeTab(closeAllTab);
    }

    private void closeTab(int method) {
        int index = this.getSelectedIndex();
        if (method == closeThisTab) {
            removeTab(index);
        } else if (method == closeAllTab) {
            int size = this.getTabCount();
            for (int i = (size - 1); i >= 0; i--) {
                removeTab(i);
            }
        } else {
            int size = this.getTabCount();
            for (int i = (size - 1); i >= 0; i--) {
                if (i == index) {
                    continue;
                }
                removeTab(i);
            }
        }
    }

    private void removeTab(int tabIndex) {
        FreeTabComponent fc = (FreeTabComponent) this.getTabComponentAt(tabIndex);
        Component c = this.getComponentAt(tabIndex);
        if (c instanceof IModulePanel) {
            try {
                ((IModulePanel) c).pickClose();
            } catch (Exception ex) {
            }
        }
        this.removeTabAt(tabIndex);
        this.getPanelKey().remove(fc.getPanelKey());
    }

    public int getPreferredTabHeight() {
        return preferredTabHeight;
    }

    private void updateTabComponents() {
        int selectedIndex = this.getSelectedIndex();
        for (int i = 0; i < this.getTabCount(); i++) {
            Component c = this.getTabComponentAt(i);
            if (c instanceof FreeTabComponent) {
                FreeTabComponent component = (FreeTabComponent) c;
                boolean selected = selectedIndex == i;
                component.updateSelection(selected);
            }
        }
    }

    public int getPreferredUnselectedTabWidth() {
        return preferredUnselectedTabWidth;
    }

    public Hashtable<String, Component> getPanelKey() {
        return panelKey;
    }
}
