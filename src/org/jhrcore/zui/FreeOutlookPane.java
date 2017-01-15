package org.jhrcore.zui;

import com.fr.cell.core.layout.TableLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import org.jdesktop.swingx.JXTaskPane;

public class FreeOutlookPane extends JPanel {

    private FreeOutlookHeader header = new FreeOutlookHeader() {

        @Override
        public void setShrink(boolean shrinked) {
            super.setShrink(shrinked);
            shrinkChanged(shrinked);
        }
    };
    private TableLayout barPaneLayout = new TableLayout();
    private JPanel barPane = new JPanel(barPaneLayout);
    private SplitPanel split = new SplitPanel(header);
    private Hashtable<Component, Integer> componentLayoutRows = new Hashtable();
    private JPanel centerPane = new JPanel(new BorderLayout());
    private Color additionalPaneBackground = new Color(207, 224, 239);//180, 209, 235);
     private Color border = new Color(150, 191, 215);//180, 209, 235); 边框的颜色
    private JPanel additionalPane = new JPanel(new BorderLayout());


    public FreeOutlookPane() {
        init();
    }

    private void init() {
        split.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
        centerPane.add(barPane, BorderLayout.NORTH); //菜单列表
        barPaneLayout.insertColumn(0, TableLayout.FILL);
        this.setLayout(new BorderLayout());
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(header, BorderLayout.NORTH);
      
        JScrollPane scroll = new JScrollPane(centerPane);
        scroll.getVerticalScrollBar().setUnitIncrement(15);//控制滚动速度
    
        panel.add(scroll, BorderLayout.CENTER);
        additionalPane.setBackground(additionalPaneBackground);
        additionalPane.setPreferredSize(new Dimension(0, 0));
        additionalPane.setBorder(new Border() {

            //draw only top line.
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.setColor(FreeUtil.OUTLOOK_SPLIT_COLOR);
                g.drawLine(0, 0, width, 0);
            }

            public Insets getBorderInsets(Component c) {
                return new Insets(0, 0, 0, 0);
            }

            public boolean isBorderOpaque() {
                return false;
            }
        });
       
        centerPane.add(additionalPane, BorderLayout.CENTER); //菜单下面板
        this.add(panel, BorderLayout.CENTER);
        this.add(split, BorderLayout.EAST);
    }

    public FreeOutlookBar addBar(JXTaskPane jxtask) {
        FreeOutlookBar bar = new FreeOutlookBar(this);
        bar.setSelected(false);
        bar.setTitle(jxtask.getTitle());
        int rowCount = barPaneLayout.getRow().length;
        barPaneLayout.insertRow(rowCount, TableLayout.PREFERRED);
        barPane.add(bar, "0," + rowCount);
        componentLayoutRows.put(bar, rowCount);
        rowCount++;
        JPanel scroll = new JPanel(new BorderLayout());
        scroll.setBorder(new EmptyBorder(0, 5, 5, 5));
        scroll.setMinimumSize(new Dimension(0, 0));
        scroll.setMaximumSize(new Dimension(50, 50));
        scroll.setBackground(new Color(207,224,239));
        JPanel scroll1 = new JPanel(new BorderLayout());
        scroll1.setBorder (BorderFactory.createMatteBorder (0,1,1,1,border));
        scroll1.setBackground(FreeUtil.TASKCOLOR);
        JPanel scroll2 = new JPanel(new BorderLayout());
        scroll2.setBorder (BorderFactory.createMatteBorder (0,20,1,1,FreeUtil.TASKCOLOR));
        scroll2.add(jxtask.getComponent(0));
        scroll1.add(scroll2);
        scroll.add(scroll1);
        barPaneLayout.insertRow(rowCount, TableLayout.MINIMUM);
        barPane.add(scroll, "0," + rowCount);
        componentLayoutRows.put(bar.getContentComponent(), rowCount);
        return bar;
    }

    public void updateLayoutConstraint(Component component, boolean selected) {
        int rowIndex = componentLayoutRows.get(component);
        double constraint = TableLayout.FILL;
        if (!selected) {
            constraint = TableLayout.MINIMUM;
        }
        barPaneLayout.setRow(rowIndex, constraint);
    }

    public void setAdditionalPaneVisible(boolean visible) {
        centerPane.remove(barPane);
        centerPane.remove(additionalPane);
//        if (visible) {
        centerPane.add(barPane, BorderLayout.NORTH);
        centerPane.add(additionalPane, BorderLayout.CENTER);

//        } else {
//            centerPane.add(barPane, BorderLayout.CENTER);
//        }
        this.updateUI();
    }

    public JComponent getAdditionalPane() {
        return additionalPane;
    }

    public void clearAllBars() {
        closeAllBars();
        for (Component c : componentLayoutRows.keySet()) {
            try {
                barPane.remove(c);
                barPaneLayout.removeLayoutComponent(c);
            } catch (Exception ex) {
                ex.printStackTrace();
                continue;
            }
        }
        componentLayoutRows.clear();
        this.updateUI();
    }

    public void closeAllBars() {
        for (int i = 0; i < barPane.getComponentCount(); i++) {
            Component c = barPane.getComponent(i);
            if (c instanceof FreeOutlookBar) {
                FreeOutlookBar bar = (FreeOutlookBar) c;
                if (bar.isSelected()) {
                    bar.setSelected(false);
                }
            }
        }
    }

    public FreeOutlookBar getSelectedBar() {
        for (int i = 0; i < barPane.getComponentCount(); i++) {
            Component c = barPane.getComponent(i);
            if (c instanceof FreeOutlookBar) {
                FreeOutlookBar bar = (FreeOutlookBar) c;
                if (bar.isSelected()) {
                    return bar;
                }
            }
        }
        return null;
    }

    public void setShrink(boolean shrinked) {
        this.header.setShrink(shrinked);
    }

    public boolean isShrinked() {
        return this.header.isShrinked();
    }

    private void shrinkChanged(boolean shrinked) {
        if (shrinked) {
            split.setCursor(Cursor.getDefaultCursor());
        } else {
            split.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
        }
        for (int i = 0; i < barPane.getComponentCount(); i++) {
            Component c = barPane.getComponent(i);
            if (c instanceof FreeOutlookBar) {
                FreeOutlookBar bar = (FreeOutlookBar) c;
                bar.headerShrinkChanged(shrinked);
            }
        }
    }

    public FreeOutlookHeader getHeader() {
        return this.header;
    }
}