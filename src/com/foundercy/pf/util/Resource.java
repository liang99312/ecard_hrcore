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
 * ͳһ��Դ������ ����ͼƬ�����ȵĻ�ȡ
 */
public class Resource 
{
  //ʵ�־�̬������Դ(images & xml)
  //public static XMLData resourceMap = new XMLData();
  //ͼƬĬ��Ŀ¼
  public static String imageDir = "images";
  //��ǰ���е�״̬(���Ի�������)
  public static boolean isDebug = true;
  
  public Resource() {}
  /*
   * �����ļ�����ȡͼƬ·���µ�ͼƬ
   * @param filePath �ļ����·��
   * @return ImageIcon����
   */
  public static ImageIcon getImage(String filePath) 
  {
	return null;	
  }  
}
