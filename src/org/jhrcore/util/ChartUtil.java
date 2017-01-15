/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;

/**
 *
 * @author Administrator
 */
public class ChartUtil {

    /** 
     * 配置字体  
     * @param chart JFreeChart 对象 
     */
    public static void configFont(JFreeChart chart) {
        // 配置字体  
        Font xfont = new Font("宋体", Font.PLAIN, 12);// X轴  
        Font yfont = new Font("宋体", Font.PLAIN, 12);// Y轴  
        Font kfont = new Font("宋体", Font.PLAIN, 12);// 底部  
        Font titleFont = new Font("隶书", Font.BOLD, 18); // 图片标题  
        if(chart.getPlot() instanceof CategoryPlot){ //柱状图或者线性
            CategoryPlot plot = chart.getCategoryPlot();// 图形的绘制结构对象        
            plot.setRangeGridlinesVisible(true);
            plot.setRangeCrosshairVisible(true);
//            plot.setRangePannable(true);
                        
            // 图片标题  
            chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));
            // 底部  
            if(chart.getLegend() != null) chart.getLegend().setItemFont(kfont);
            // X 轴  
            CategoryAxis domainAxis = plot.getDomainAxis();            
            domainAxis.setLabelFont(xfont);// 轴标题  
            domainAxis.setTickLabelFont(xfont);// 轴数值    
            //domainAxis.setTickLabelPaint(Color.BLUE); // 字体颜色  
            //domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // 横轴上的label斜显示   
            
            // Y 轴  
            ValueAxis rangeAxis = plot.getRangeAxis();
            rangeAxis.setLabelFont(yfont);
            //rangeAxis.setLabelPaint(Color.BLUE); // 字体颜色              
            rangeAxis.setTickLabelFont(yfont);                        
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            rangeAxis.setTickMarkInsideLength(-20);
            rangeAxis.setTickMarkOutsideLength(20);
            
            if(plot.getRenderer() instanceof AreaRenderer){                
                AreaRenderer areaRenderer = (AreaRenderer)plot.getRenderer();                  
                areaRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		areaRenderer.setBaseItemLabelsVisible(true);
            } else if(plot.getRenderer() instanceof LineAndShapeRenderer){
                LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer)plot.getRenderer();
                lineandshaperenderer.setBaseShapesVisible(true);
                lineandshaperenderer.setDrawOutlines(true);
                lineandshaperenderer.setUseFillPaint(true);
                lineandshaperenderer.setBaseFillPaint(Color.white);
                lineandshaperenderer.setSeriesStroke(0, new BasicStroke(3F));
                lineandshaperenderer.setSeriesOutlineStroke(0, new BasicStroke(2.0F));
                lineandshaperenderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-5D, -5D, 10D, 10D));
                lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		lineandshaperenderer.setBaseItemLabelsVisible(true);
            } else if(plot.getRenderer() instanceof BarRenderer){
                BarRenderer barrenderer = (BarRenderer)plot.getRenderer();
		barrenderer.setDrawBarOutline(false);
		barrenderer.setMaximumBarWidth(0.1);
                barrenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		barrenderer.setBaseItemLabelsVisible(true);
            }
        } else if(chart.getPlot() instanceof PiePlot){ //饼状图            
            // 图片标题  
            chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));
            // 底部  
            if(chart.getLegend() != null) chart.getLegend().setItemFont(kfont);
            PiePlot plot = (PiePlot) chart.getPlot();// 图形的绘制结构对象   
            plot.setLabelFont(xfont);
        } else if(chart.getPlot() instanceof XYPlot){ //时间曲线
            // 图片标题  
            chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));
            // 底部  
            if(chart.getLegend() != null) chart.getLegend().setItemFont(kfont);
            
            XYPlot plot = (XYPlot) chart.getPlot();
            // X 轴  
            ValueAxis domainAxis = plot.getDomainAxis();
            domainAxis.setLabelFont(xfont);// 轴标题  
            domainAxis.setTickLabelFont(xfont);// 轴数值    
            domainAxis.setTickLabelPaint(Color.BLUE); // 字体颜色              
            // Y 轴  
            ValueAxis rangeAxis = plot.getRangeAxis();
            rangeAxis.setLabelFont(yfont);
            rangeAxis.setLabelPaint(Color.BLUE); // 字体颜色  
            rangeAxis.setTickLabelFont(yfont);
        }
    }
}
