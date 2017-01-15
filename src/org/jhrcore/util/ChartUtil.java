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
     * ��������  
     * @param chart JFreeChart ���� 
     */
    public static void configFont(JFreeChart chart) {
        // ��������  
        Font xfont = new Font("����", Font.PLAIN, 12);// X��  
        Font yfont = new Font("����", Font.PLAIN, 12);// Y��  
        Font kfont = new Font("����", Font.PLAIN, 12);// �ײ�  
        Font titleFont = new Font("����", Font.BOLD, 18); // ͼƬ����  
        if(chart.getPlot() instanceof CategoryPlot){ //��״ͼ��������
            CategoryPlot plot = chart.getCategoryPlot();// ͼ�εĻ��ƽṹ����        
            plot.setRangeGridlinesVisible(true);
            plot.setRangeCrosshairVisible(true);
//            plot.setRangePannable(true);
                        
            // ͼƬ����  
            chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));
            // �ײ�  
            if(chart.getLegend() != null) chart.getLegend().setItemFont(kfont);
            // X ��  
            CategoryAxis domainAxis = plot.getDomainAxis();            
            domainAxis.setLabelFont(xfont);// �����  
            domainAxis.setTickLabelFont(xfont);// ����ֵ    
            //domainAxis.setTickLabelPaint(Color.BLUE); // ������ɫ  
            //domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // �����ϵ�labelб��ʾ   
            
            // Y ��  
            ValueAxis rangeAxis = plot.getRangeAxis();
            rangeAxis.setLabelFont(yfont);
            //rangeAxis.setLabelPaint(Color.BLUE); // ������ɫ              
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
        } else if(chart.getPlot() instanceof PiePlot){ //��״ͼ            
            // ͼƬ����  
            chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));
            // �ײ�  
            if(chart.getLegend() != null) chart.getLegend().setItemFont(kfont);
            PiePlot plot = (PiePlot) chart.getPlot();// ͼ�εĻ��ƽṹ����   
            plot.setLabelFont(xfont);
        } else if(chart.getPlot() instanceof XYPlot){ //ʱ������
            // ͼƬ����  
            chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));
            // �ײ�  
            if(chart.getLegend() != null) chart.getLegend().setItemFont(kfont);
            
            XYPlot plot = (XYPlot) chart.getPlot();
            // X ��  
            ValueAxis domainAxis = plot.getDomainAxis();
            domainAxis.setLabelFont(xfont);// �����  
            domainAxis.setTickLabelFont(xfont);// ����ֵ    
            domainAxis.setTickLabelPaint(Color.BLUE); // ������ɫ              
            // Y ��  
            ValueAxis rangeAxis = plot.getRangeAxis();
            rangeAxis.setLabelFont(yfont);
            rangeAxis.setLabelPaint(Color.BLUE); // ������ɫ  
            rangeAxis.setTickLabelFont(yfont);
        }
    }
}
