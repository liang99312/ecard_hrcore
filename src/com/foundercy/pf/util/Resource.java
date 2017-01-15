package com.foundercy.pf.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.ImageIcon;


/**
 * @author Administrator
 *
 * 统一资源管理类 用于图片声音等的获取
 */
public class Resource 
{
  //实现静态缓存资源(images & xml)
  //public static XMLData resourceMap = new XMLData();
  //图片默认目录
  public static String imageDir = "images";
  //当前运行的状态(调试还是运行)
  public static boolean isDebug = true;
  
  public Resource() {}
  /*
   * 根据文件名获取图片路径下的图片
   * @param filePath 文件相对路径
   * @return ImageIcon对象
   */
  public static ImageIcon getImage(String filePath) 
  {
	return null;	
  }  
}
