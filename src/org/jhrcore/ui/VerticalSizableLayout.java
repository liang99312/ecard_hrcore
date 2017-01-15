/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class VerticalSizableLayout implements LayoutManager {
    
    private List<Component> list_comp = new ArrayList<Component>();
    
    // "single_line"表示单行显示，否则自动充满剩余空间
    private List<String> list_constraint = new ArrayList<String>(); 
    
    // 行间距
    private int line_interval = 5;
    // 单行高度
    private int line_height = 40;
    
    public void addLayoutComponent(String name, Component comp) {
        list_comp.add(comp);
        list_constraint.add(name);
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Component getLayoutComponent(Object paramObject) {
        return null;
    }

    public void layoutContainer(Container paramContainer) {
        synchronized (paramContainer.getTreeLock()) {
            Dimension localDimension = null;
            Insets localInsets = paramContainer.getInsets();
            int top = localInsets.top;
            int left = localInsets.left;
            int right = localInsets.right;
            int bottom = localInsets.bottom;
            int height = paramContainer.getHeight() - bottom - top;
            int width = paramContainer.getWidth() - left - right;

            int single_line_count = 0;
            for (String tmp_s : list_constraint){
                if ("single_line".equals(tmp_s))
                    single_line_count++;
            }
            // 可变高度控件的高度
            int line_height2 = 1;
            if (list_constraint.size() > single_line_count){
                line_height2 = (height - (list_constraint.size() - 1) * line_interval - single_line_count * line_height) / (list_constraint.size() - single_line_count);
            }
            
            int x = left; int y = top;
            for (int i = 0; i < list_constraint.size(); i++){
                String constraint = list_constraint.get(i);
                Component comp = list_comp.get(i);
                if ("single_line".equals(constraint)){
                    comp.setSize(width, line_height);
                    comp.setBounds(x, y, width, line_height);
                    y = y + line_height;
                } else {
                    comp.setSize(width, line_height2);
                    comp.setBounds(x, y, width, line_height2);
                    y = y + line_height2;
                }
            }
        }
    }

    public Dimension minimumLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            Dimension localDimension = new Dimension(0, 0);
            //boolean bool = parent.getComponentOrientation().isLeftToRight();
            Component localComponent = null;
            Insets localInset = parent.getInsets();
            if ((localComponent = parent.getComponent(0)) != null) {
                Dimension dimension = localComponent.getMinimumSize();

                localDimension.width += dimension.width + localInset.left
                        + localInset.right;
                localDimension.height = dimension.height + localInset.top
                        + localInset.bottom;
            }
            return localDimension;
        }

    }

    public Dimension preferredLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            Dimension localDimension = new Dimension(0, 0);
            //boolean bool = parent.getComponentOrientation().isLeftToRight();
            Component localComponent = null;
            Insets localInset = parent.getInsets();
            if ((localComponent = parent.getComponent(0)) != null) {
                Dimension dimension = localComponent.getMinimumSize();

                localDimension.width += dimension.width + localInset.left
                        + localInset.right;
                localDimension.height = dimension.height + localInset.top
                        + localInset.bottom;
            }
            return localDimension;
        }
    }
}