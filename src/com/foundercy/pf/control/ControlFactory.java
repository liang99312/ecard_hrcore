package com.foundercy.pf.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.foundercy.pf.control.ConfigTools;
import com.foundercy.pf.control.Control;
import com.foundercy.pf.control.ControlException;
import com.foundercy.pf.util.FileUtil;
//import org.jdom.JDOMException;


/**
 *
 * <p>Title: 基于XML配置的组件创建工厂</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 北京方正春元</p>
 * @author fangyi
 * @version 1.0
 */
public class ControlFactory  {

	/**
	 * 缺省的配置文件名称，需要放在classpath下。
	 */
	private String xmlFileName = null;
	
	private Document doc = null;
	/**
	 * 是否使用缓存技术
	 */
	private boolean cached = false;
	
	public static String CONTROL_CONFIGTYPE_FILE = "com/foundercy/pf/control/controltype.xml";
	
	public static String ID_STRING = "id";
	
	/**
	 * 根据XML配置文件名生成工厂对象，其中reload = false。
	 */
	public ControlFactory(String xml) {
		this.xmlFileName = xml;
	}

	/**
	 * 根据XML配置文件名生成工厂对象，其中reload = false。
	 */
	public ControlFactory(String xml, boolean cached) {
		this.xmlFileName = xml;
		this.cached = cached;
	}

	/**
	 * 根据创建一个指定类型控件的空实例。
	 * @param type 同上
	 * @return String,类名
	 * @throws ControlException
	 */
	protected  Control createControlInstance(String type) throws ControlException {
		
		Document mapdoc=null;
		
		try {
			mapdoc = XMLDocumentManager.getDocument(CONTROL_CONFIGTYPE_FILE,true);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ControlException("无法读取" + CONTROL_CONFIGTYPE_FILE + "控件类型映射文件");			
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new ControlException("读取" + CONTROL_CONFIGTYPE_FILE + "控件类型映射文件出错:");	
		}
		
		Element root = mapdoc.getRootElement();
		Element node = root.element(type);
		if (node == null) {
			throw new ControlException("类型为" + type + "的控件没有定义类型映射，请在文件"
					+ CONTROL_CONFIGTYPE_FILE
					+ "[type_mapping]节点中，定义该类型。");
		}
		
		Control control = null;
		String cname = node.attribute("class").getValue();
		try {
			Class c = Class.forName(cname);
			control = (Control) c.newInstance();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			throw new ControlException(type + "的控件类型映射" + cname
					+ "在classpath中不存在，请检查配置文件的类型定义.");
		} catch (InstantiationException ex) {
			ex.printStackTrace();
			throw new ControlException(type + "的控件在构造时出现错误，请检查类" + cname
					+ "的构造函数是否正确.");
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
			throw new ControlException(type + "的控件在构造时出现错误，请检查类" + cname
					+ "的构造函数是否是非法访问.");
		}
		return control;
	}

	
	/**
	 *  根据配置文件名获得Document对象，如果reload为true则每次重新加载
	 * @param xmlfile XML配置文件的名称。不包含路径，系统在classpath下查找。
	 * @return Document,XML配置文件的内存对象。
	 * @throws ControlException
	 */
	protected  Document getDocument() throws ControlException {
		try {
			if(doc == null)
				doc = XMLDocumentManager.getDocument(this.xmlFileName, isCached());
			return doc;
		} catch (Exception e) {
			throw new ControlException("指定配置文件" + this.xmlFileName + "的格式错误.\n"
					+ e.getMessage());
		}
	}

	/**
	 * 根据一个Element创建一个Control对象，采用递归算法。
	 * @param node：一个XML的节点，对应一个控件。
	 * @return Control子类对象。
	 * @throws java.lang.Exception
	 */
	public Control createControl(Element node) throws ControlException {

		if (node == null) return null;

		String name = node.getName();

		Control c = createControlInstance(name);

		// 设置该复合控件的属性
		ConfigTools.setAttributes(c, node);

		//如果是复合控件，则采用递归算法。
		if (c instanceof Compound) {
			List children = node.elements();
			for (int i = 0; children != null && i < children.size(); i++) {
				Control control = null;
				Element child = (Element) children.get(i);
				String ref = child.attributeValue(ConfigTools.REFERENCE_NAME);
				if (ref != null && !ref.trim().equals("")) {
					 control = this.createControlById(ref);
					 ConfigTools.setAttributes(control, child);
				} else {
					control = this.createControl(child);
				}
				if (control != null) {
					((Compound) c).addControl(control);
					control.setParentControl(c);
				}
			}
		}
		return c;
	}

	/**
	 * 根据控件ID，创建一个控件，该函数自动发现其类型。
	 * @param type 同上
	 * @return Control子类对象。
	 * @throws ControlException
	 */
	public Control createControlById(String id) throws ControlException {


		Document doc = this.getDocument();
		
		Element root = doc.getRootElement();

		List l = root.elements();
		
		Attribute rootAttr = root.attribute(ID_STRING);
		if (rootAttr != null && rootAttr.getValue().trim().equals(id.trim())) {
			return this.createControl(root);
		} else {

			for (int i = 0; l != null && i < l.size(); i++) {
	
				Element node = (Element) l.get(i);
	
				Attribute attr = node.attribute(ID_STRING);
	
				if (attr == null)
					continue;
	
				if (attr.getValue().trim().equalsIgnoreCase(id.trim()))
	
					return this.createControl(node);
			}
		}	
		throw new ControlException("配置文件" + this.xmlFileName + "中不存在ID为" + id
				+ "的控件.");
	}

	/**
	 * 设置配置文件的名称
	 * @param fileName
	 */
	public void setXMLFile(String fileName) {
		this.xmlFileName = fileName;
	}

	/**
	 * 获得配置文件的名称
	 * @return
	 */
	public String getXMLFile() {
		return this.xmlFileName;
	}

	/**
	 * 判断是否使用缓存技术，false不用缓存
	 * @return
	 */
	public boolean isCached() {
		return cached;
	}

	/**
	 * 设置是否使用缓存技术
	 * @param reload
	 * 
	 * @uml.property name="reload"
	 */
	public void setCached(boolean reload) {
		this.cached = reload;
	};
//	/**
//	 * 创建业务界面
//	 * @return
//	 * @throws ControlException
//	 */
//	public Page createBusinessPage() throws ControlException {
//		Document doc = this.getDocument(this.xmlFileName);
//		Element root = doc.getRootElement();
//		Control page = this.createControl(root);
//		XMLBusinessPage page1 = (XMLBusinessPage) page;
//		page1.initEventMapping();
//		if (page1.getWidth() != 0 && page1.getHeight() != 0) {
//			page1.setPreferredSize(new Dimension(page1.getWidth(), page1
//					.getHeight()));
//		}
//		return (Page) page;
//	}
//
//	public Page createBusinessPageWithoutEvents() throws ControlException {
//		Document doc = this.getDocument(this.xmlFileName);
//		Element root = doc.getRootElement();
//		root.removeChild("event_map");
//		Control page = this.createControl(root);
//		XMLBusinessPage page1 = (XMLBusinessPage) page;
//		if (page1.getWidth() != 0 && page1.getHeight() != 0) {
//			page1.setPreferredSize(new Dimension(page1.getWidth(), page1
//					.getHeight()));
//		}
//		return (Page) page;
//	}

}

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 北京世纪新干线</p>
 * @author 方益
 * @version 1.0
 */

class XMLDocumentManager {

  private static HashMap cach = new HashMap();
//  private static IXmlFileGetter xmlFileGetterService = null;
  

  /**
   * 根据指定URL获得相应的Document。
   * @param url
   * @param useCach 是否使用缓存
   * @return
   * @throws IOException
 * @throws DocumentException 
   * @throws JDOMException
   */
  public static Document getDocument(String url, boolean useCach) throws IOException, DocumentException
       {
    //使用缓存，并且缓存中存在，则直接返回。
    if(useCach && cach.containsKey(url)) {
        return (Document) cach.get(url);
    }
    else {
      Document doc;
//      if (Global.isDebug) {
    	  doc = getDocumentAtLocal(url);
//      } else {
//    	  doc = getDocumentAtServer(url);
//      }
      cach.put(url, doc);
      return doc;
    }
  }
//  /**
//   * 写入配置文件
//   * @param url
//   * @param doc
//   */
//  public static void writeDocument(String url, Document doc) {
//    try {
//        XMLOutputter out = new XMLOutputter();
//        out.setNewlines(true);
//        out.setIndent(true);
//        out.setIndentSize(4);
//        out.setEncoding("gb2312");
//        out.setTrimText(true);
//        url = FileTool.getFilePath(url);
//        String fileString = out.outputString(doc);
//        FileTool.writeFile(url, fileString, false);
//      }
//      catch (Exception ex) {
//        ex.printStackTrace();
//      }
//  }

  /**
   * 从本地获得Document
   * @param url
   * @return
   * @throws IOException
 * @throws DocumentException 
   * @throws JDOMException
   */
  private static Document getDocumentAtLocal(String url) throws
      IOException, DocumentException {
  	//处理Jar中的文件
	  byte[] byteFile = FileUtil.file2Bytes(url);
      String content = new String(byteFile, "GBK");
//  	String content = FileUtil.file2String(url);
    if (content == null) {
      throw new IOException("Can't read file " + url);
    }
    Document doc = DocumentHelper.parseText(content);
//	  //by sunwenjun 060510 客户界面配置文件放在服务端
//	  URL in = Resource.class.getResource("/");
//	  String path = in.getPath();
//	  url = path + url;
//	  SAXReader saxReader = new SAXReader();
//      Document doc = saxReader.read(new File(url));
    return doc;
  }
  
//  /**
//   * 从服务端获得Document
//   * @param url
//   * @return
//   * @throws IOException
//   * @throws DocumentException 
//   * @throws JDOMException
//   */
//  private static Document getDocumentAtServer(String url) throws
//      IOException, DocumentException {
//	  if(xmlFileGetterService == null){
//		  BeanFactory factory = BeanFactoryUtil.getBeanFactory(
//					"com/foundercy/pf/pub/ctrlfac/conf/ctrlfacconf.xml");
//	  	  xmlFileGetterService = (IXmlFileGetter)factory.getBean("sys.xmlFileGetterService");
//	  }	  
//  	String str = xmlFileGetterService.getDocumentAtServer(url);
//  	Document doc = DocumentHelper.parseText(str);
//    return doc;
//  }

  /**
 * @param string
 * @return
 * @throws JDOMException
 * @throws IOException
 * @throws DocumentException 
 */
public static Document getDocument(String url) throws IOException, DocumentException{
	return getDocument(url,false);
}
/**
   * 获得一个配置文件（XML）资源的根，使用缓存。
   * @param url，ClassPath中的文件名称（包括相对路径）
   * @param useCache 是否使用缓存
   * @return
 * @throws JDOMException
 * @throws IOException
 * @throws DocumentException 
   */
  public static Element getRootElement(String url, boolean useCache) throws IOException, DocumentException{
	Document doc = getDocument(url,useCache);
  	return doc.getRootElement();
  }
  
  /**
   * 获得一个配置文件（XML）资源的根，不使用缓存。
   * @param url，ClassPath中的文件名称（包括相对路径）
   * @return
 * @throws JDOMException
 * @throws IOException
 * @throws DocumentException 
   */
  public static Element getRootElement(String url) throws IOException, DocumentException{
  	return getRootElement(url,false);
  }

}