/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.entity.base;

import com.jgoodies.binding.beans.Model;
import org.jhrcore.entity.annotation.FieldAnnotation;

/**
 *HRUI参数类
 * @author hflj
 */
public class UIDef extends Model {

    @FieldAnnotation(visible = false, displayName = "Id", groupName = "通用卡片")
    public String uIDef_key;
    @FieldAnnotation(visible = true, displayName = "列数", groupName = "通用卡片")
    public int cols = 2;
    @FieldAnnotation(visible = true, displayName = "列宽", groupName = "通用卡片")
    public int width = 0;
    @FieldAnnotation(visible = true, displayName = "列间距", groupName = "通用卡片")
    public int colspan = 15;
    @FieldAnnotation(visible = true, displayName = "行间距", groupName = "通用卡片")
    public int rowspan = 3;
    @FieldAnnotation(visible = true, displayName = "显示零值", groupName = "通用网格")
    public boolean showZero = true;
    @FieldAnnotation(visible = true, displayName = "上边距", groupName = "通用网格")
    public int colspac = 0;
    @FieldAnnotation(visible = true, displayName = "左边距", groupName = "通用网格")
    public int rowspac = 0;
    @FieldAnnotation(visible = true, displayName = "高度", groupName = "通用网格")
    public int height = 0;
    @FieldAnnotation(visible = true, displayName = "字体大小", groupName = "界面参数")
    public int font_size = 0;

    public int getFont_size() {
        return font_size;
    }

    public void setFont_size(int font_size) {
        int old = this.font_size;
        this.font_size = font_size;
        this.firePropertyChange("font_size", old, font_size);
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        int old = this.cols;
        this.cols = cols;
        this.firePropertyChange("cols", old, cols);
    }

    public int getColspan() {
        return colspan;
    }

    public void setColspan(int colspan) {
        int old = this.colspan;
        this.colspan = colspan;
        this.firePropertyChange("colspan", old, colspan);
    }

    public int getRowspan() {
        return rowspan;
    }

    public void setRowspan(int rowspan) {
        int old = this.rowspan;
        this.rowspan = rowspan;
        this.firePropertyChange("rowspan", old, rowspan);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        int old = this.width;
        this.width = width;
        this.firePropertyChange("width", old, width);
    }

    public boolean isShowZero() {
        return showZero;
    }

    public void setShowZero(boolean showZero) {
        this.showZero = showZero;
    }

    public int getColspac() {
        return colspac;
    }

    public void setColspac(int colspac) {
        int old = this.colspac;
        this.colspac = colspac;
        this.firePropertyChange("colspac", old, colspac);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        int old = this.height;
        this.height = height;
        this.firePropertyChange("height", old, height);
    }

    public int getRowspac() {
        return rowspac;
    }

    public void setRowspac(int rowspac) {
        int old = this.rowspac;
        this.rowspac = rowspac;
        this.firePropertyChange("rowspac", old, rowspac);
    }

    public String getuIDef_key() {
        return uIDef_key;
    }

    public void setuIDef_key(String uIDef_key) {
        this.uIDef_key = uIDef_key;
    }
    
}
