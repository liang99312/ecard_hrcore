/*
 * $Id: LocalMemoUtil.java,v 1.1.1.1 2009/04/07 08:12:35 mxliteboss Exp $
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.control.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * 本地记忆工具类
 * 
 * @author a
 * @version $Revision: 1.1.1.1 $
 */
public class LocalMemoUtil {
	
	public static final String FILE_POSTFIX = ".mem";
	
	public static final String TABLE_TYPE = "FTable";
	
	private String localMemoryDir = "C:/Memory/";
	
	public LocalMemoUtil() {
		this(null);
	}
	
	public LocalMemoUtil(String localMemoryDir) {
		if (localMemoryDir!=null && !localMemoryDir.trim().equals("")) {
			this.localMemoryDir = localMemoryDir;
		}
	}
	
	/**
	 * @return Returns the localMemoryDir.
	 */
	public String getLocalMemoryDir() {
		return localMemoryDir;
	}

	/**
	 * @param localMemoryDir The localMemoryDir to set.
	 */
	public void setLocalMemoryDir(String localMemoryDir) {
		this.localMemoryDir = localMemoryDir;
	}
	
	/**
	 * 保存指定ID的设置串
	 * @param setType
	 * @param setID
	 * @param setString
	 * @return
	 */
	public boolean saveSet(String setType, String setID, String setString) {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new StringReader(setString));
			File rootDir = new File(this.localMemoryDir);
			if (!rootDir.exists())
				rootDir.mkdirs();
			
			File typeDir = new File(this.localMemoryDir + setType + "/");
			if (!typeDir.exists())
				typeDir.mkdirs();
			
			out = new PrintWriter(new BufferedWriter(
					new FileWriter(this.localMemoryDir + setType + "/" + setID + FILE_POSTFIX)));
			int lineCount = 1;
			while ((setString = in.readLine()) != null) {
				lineCount++;
				out.println(setString);
			}
		} catch (IOException ioe) {
			System.out.println("本地记忆时，写本地文件出错。");
			ioe.printStackTrace();
			return false;
		} finally {
			try {
				if (out!=null) out.close();
				if (in!=null) in.close();
	    	} catch (IOException ioe) {
	    		ioe.printStackTrace();
	    	}
		}
		return true;
	}
	
	/**
	 * 返回指定ID的设置串
	 * 
	 * @param setType
	 * @param setID
	 * @return
	 */
	public String getSet(String setType, String setID) {
		String retrunStr = "";
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(
					this.localMemoryDir + setType + "/" + setID + FILE_POSTFIX));
			
			String s = new String();
			
			while ((s = in.readLine()) != null)
				retrunStr += s;
		} catch (FileNotFoundException fe) {
//			System.out.println("本地记忆时，未找到指定文件。");
//			fe.printStackTrace();
		} catch (IOException ioe) {
			System.out.println("本地记忆时，读本地文件出错。");
//			ioe.printStackTrace();
		} finally {
			try {
				if (in != null) in.close();
	    	} catch (IOException ioe) {
	    		ioe.printStackTrace();
	    	}
		}
		return retrunStr;
	}
	
	/**
	 * 基于模板应用个性设置
	 * 
	 * @param modelString
	 * @param setString
	 */
	public String calcSet(String modelString, String setString) {
		String resultStr = "";
		try {
			Document modelDoc = DocumentHelper.parseText(modelString);
			Element modelRoot = modelDoc.getRootElement();
			//
			Document resultDoc = DocumentHelper.createDocument();
			Element resultRoot = resultDoc.addElement(modelRoot.getName());
			List mrAttrs = modelRoot.attributes();
			for (int m=0; mrAttrs!=null && m<mrAttrs.size(); m++) {
				Attribute att = (Attribute)mrAttrs.get(m);
				resultRoot.addAttribute(att.getName(), att.getValue());
			}
			//
			Document setDoc = DocumentHelper.parseText(setString);
			Element setRoot = setDoc.getRootElement();
			List modelEles = modelRoot.elements();
			List setEles = setRoot.elements();
			for (int i=0; setEles!=null && i<setEles.size(); i++) {
				Element setEle = (Element)setEles.get(i);
				String seId = setEle.attributeValue("id");
				for (int j=0; modelEles!=null && j<modelEles.size(); j++) {
					Element modelEle = (Element)modelEles.get(j);
					String meId = modelEle.attributeValue("id");
					if (meId.equals(seId)) {
						List seAttrs = setEle.attributes();
						List meAttrs = modelEle.attributes();
						for (int k=0; seAttrs!=null && k<seAttrs.size(); k++) {
							Attribute seAttr = (Attribute)seAttrs.get(k);
							boolean isFoundSameAttr = false;
							for (int m=0; meAttrs!=null && m<meAttrs.size(); m++) {
								Attribute meAttr = (Attribute)meAttrs.get(m);
								if (seAttr.getName().equals(meAttr.getName())) {
									meAttr.setValue(seAttr.getValue());
									isFoundSameAttr = true;
									break;
								}
							}
							if (!isFoundSameAttr) {
								modelEle.addAttribute(seAttr.getName(), seAttr.getValue());
							}
						}
						//
						if (!modelRoot.getName().equals(modelEle.getName())){
							resultRoot.add((Element)modelEle.clone());
						}
						//
					}
				}
			}
			resultStr = convertToStr(resultDoc);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		
		return resultStr;
	}

	/**
	 * 将doc对象转程XML格式
	 * 
	 * @throws Exception错误信息
	 */
	public static String convertToStr(Document doc) {
		StringBuffer str = new StringBuffer();
		StringWriter stringwriter = null;
		XMLWriter writer = null;
		OutputFormat format = null;
		try {
			format = OutputFormat.createPrettyPrint();
			format.setEncoding("GBK");
			writer = new XMLWriter(format);
			stringwriter = new StringWriter();
			writer.setWriter(stringwriter);
			writer.write(doc);
	
			BufferedReader reader = new BufferedReader(new StringReader(stringwriter.toString()));
			String context = reader.readLine();
			while (context != null) {
				if (context.length() > 0)
					str.append(context + "\n");
				context = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			 stringwriter = null;
			 writer = null;
			 format = null;
		}

		return str.toString();
	}
	
}
