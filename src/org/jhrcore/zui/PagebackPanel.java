package org.jhrcore.zui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jhrcore.util.ImageUtil;

/**
*
* @author Administrator
*/
public class PagebackPanel extends JPanel
{

    public PagebackPanel(){
    this.setLayout(new java.awt.BorderLayout());
    }


    @Override
     public void paintComponent(Graphics g)
     {  
      Graphics2D g2d = (Graphics2D) g;
        int x=0,y=0;
        ImageIcon icon=new ImageIcon(ImageUtil.getImage("index_79.jpg"),"logo");
        int imageWidth =icon.getIconWidth();
        int  imageHeight =icon.getIconHeight();
        x = (getSize().width - imageWidth)/2;
        y = (getSize().height - imageHeight)/2;
        g2d.setBackground(new Color(238, 238, 238));
        g2d.clearRect(0,0,getSize().width,getSize().height);//通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
        g2d.drawImage(icon.getImage(), x, y, this);
     }
   }