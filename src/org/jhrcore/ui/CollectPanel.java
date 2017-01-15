package org.jhrcore.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.media.Buffer;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.RGBFormat;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;
import org.jhrcore.util.ImageUtil;

public class CollectPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger("CollectPanel");
	private static Player player;
	private static JFrame fmCollect = null;
	private Component comp;
	public Component getComp() {
		return comp;
	}


	public static void showCollectFrame(){
		if (fmCollect != null){
			fmCollect.setState(JFrame.NORMAL);
			return;
		}
		fmCollect = new JFrame("视频采集窗口");
		fmCollect.setResizable(false);
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		final CollectPanel collectPanel = new CollectPanel();
		collectPanel.buildToolbar(toolbar);
		fmCollect.add(collectPanel, BorderLayout.CENTER);
		fmCollect.add(toolbar, BorderLayout.SOUTH);
		fmCollect.setSize(500, 580);
		fmCollect.setAlwaysOnTop(true);
		fmCollect.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fmCollect.addWindowListener(new WindowAdapter(){
            @Override
			public void windowClosing(WindowEvent arg0) {
				CollectPanel.close();
				fmCollect = null;
			}			
		});
		fmCollect.setVisible(true);
	}

	public void buildToolbar(JToolBar toolbar){
		lbl_info = new JLabel("");
		toolbar.add(lbl_info);
		toolbar.addSeparator();
		JButton btnCollect = new JButton("采集", ImageUtil.getIcon("add.png"));
		toolbar.add(btnCollect);
		toolbar.addSeparator();
		JButton btnClose = new JButton("关闭");
		toolbar.add(btnClose);
		
		btnClose.addActionListener(new ActionListener(){
            @Override
			public void actionPerformed(ActionEvent arg0) {				
				if (fmCollect == null)
					return;
				fmCollect.dispose();
				CollectPanel.close();
				fmCollect = null;
			}			
		});
		btnCollect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				collect();
				fmCollect.setState(JFrame.ICONIFIED);
				/*
				if (fmCollect == null)
					return;
				fmCollect.dispose();
				CollectPanel.close();
				fmCollect = null;*/
			}			
		});
		btnCollect.registerKeyboardAction(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				collect();
				fmCollect.setState(JFrame.ICONIFIED);
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
	}	
	
	private Buffer buf = null;
	private Image img = null;
	private BufferToImage btoi = null;
	private JLabel lbl_info;
	
	protected void collect() {
		java.awt.image.BufferedImage tmp_bufImage = getCurrentImage();
        try {
			ImageIO.write(tmp_bufImage, "jpg", new File(System.getProperty("user.home") + "/" + "register.jpg"));
		} catch (IOException e) {
			log.error(e);
		}		
	}


	public java.awt.image.BufferedImage getCurrentImage() {
		FrameGrabbingControl fgc =
            (FrameGrabbingControl) player.getControl(
                "javax.media.control.FrameGrabbingControl");
        buf = fgc.grabFrame(); // Convert it to an image      
        btoi = new BufferToImage((VideoFormat) buf.getFormat());
        img = btoi.createImage(buf); // show the image     
        java.awt.image.BufferedImage tmp_bufImage = new java.awt.image.BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
        tmp_bufImage.getGraphics().drawImage(img, 0, 0, null);
		return tmp_bufImage;
	}

	public static void close(){
		if (player != null){
			player.close();
			player.deallocate();
			player = null;
		}
	}

	public CollectPanel() {
		super(new BorderLayout());
		openDevice();
        this.addComponentListener(new ComponentListener(){

			public void componentHidden(ComponentEvent e) {
				
			}

			public void componentMoved(ComponentEvent e) {
				
			}

			public void componentResized(ComponentEvent e) {
				if (comp == null)
					return;
				Dimension d1 = comp.getPreferredSize();
				Dimension d2 = CollectPanel.this.getSize();
				
				double mul = 1;
				if ((0.1 * d1.width / d2.width) > (0.1 * d1.height / d2.height)){
					mul = 1.0 * d2.width / d1.width;
				} else {
					mul = 1.0 * d2.height / d1.height;
				}
				d1.width  = (int)(d1.width * mul);
				d1.height = (int)(d1.height * mul);
				
				comp.setBounds((d2.width - d1.width) / 2, (d2.height - d1.height) / 2, d1.width, d1.height);				
			}

			public void componentShown(ComponentEvent e) {
				
			}
        	
        });
	}


	public void openDevice() {
        String str2 = "vfw:Microsoft WDM Image Capture (Win32):0";
//        String str2 = "vfw:Logitech USB Video Camera:2";
        player = null;
		CaptureDeviceInfo di = null;
        MediaLocator ml = null;
        di = CaptureDeviceManager.getDevice(str2);
        ml = di.getLocator();
//        ml = autoDetect();
        try
        {
            player = Manager.createRealizedPlayer(ml);
            player.start();
            
            if ((comp = player.getVisualComponent()) != null)
            {
                add(comp, BorderLayout.CENTER);
            }
            
        } catch (Exception e) {
            log.error(e);
        }
    }

    private MediaLocator autoDetect() {//自动识别功能函数
        MediaLocator ml = null; //视频采集设备对应的MediaLocator
        VideoFormat currentFormat = null;//用户定制获得视频采集设备支持的格式
        Format setFormat = null;//用户定制视频采集设备输出的格式
        Format[] videoFormats = null;//视频采集设备支持的所有格式


        System.out.println(" AutoDetect for VFW");
        //获得当前所有设备列表
        Vector deviceList = CaptureDeviceManager.getDeviceList(null);



        if (deviceList != null) {
//　　　　根据设备列表，找出可用设备名称
            for (int i = 0; i < deviceList.size(); i++) {
                CaptureDeviceInfo di = (CaptureDeviceInfo) deviceList.elementAt(i);
                //如果设备名称以vfw开头
                if (di.getName().startsWith("vfw:")) {
                    //获得所有支持RGB格式　
                    videoFormats = di.getFormats();
                    for (int j = 0; j < videoFormats.length; j++) {
                        //我们只需要第一种RGB格式
                        if (videoFormats[j] instanceof RGBFormat) {
                            currentFormat = (RGBFormat) videoFormats[i];
                            break;
                        }
                    }
                    if (currentFormat == null) {
                        System.err.println("Search For RGBFormat Failed");
                        System.exit(-1);
                    }
                    //通过设备，获得MediaLocator，这个很重要
                    ml = di.getLocator();
                }

            }
        } else {
            System.err.println( "No Capture Device OK");
            System.exit(-1);
        }
        return ml;
    }


	
	public static void main(String[] args) {
		CollectPanel.showCollectFrame();
	}
}
