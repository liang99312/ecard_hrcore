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
 * <p>Title: ����XML���õ������������</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: ����������Ԫ</p>
 * @author fangyi
 * @version 1.0
 */
public class ControlFactory  {

	/**
	 * ȱʡ�������ļ����ƣ���Ҫ����classpath�¡�
	 */
	private String xmlFileName = null;
	
	private Document doc = null;
	/**
	 * �Ƿ�ʹ�û��漼��
	 */
	private boolean cached = false;
	
	public static String CONTROL_CONFIGTYPE_FILE = "com/foundercy/pf/control/controltype.xml";
	
	public static String ID_STRING = "id";
	
	/**
	 * ����XML�����ļ������ɹ�����������reload = false��
	 */
	public ControlFactory(String xml) {
		this.xmlFileName = xml;
	}

	/**
	 * ����XML�����ļ������ɹ�����������reload = false��
	 */
	public ControlFactory(String xml, boolean cached) {
		this.xmlFileName = xml;
		this.cached = cached;
	}

	/**
	 * ���ݴ���һ��ָ�����Ϳؼ��Ŀ�ʵ����
	 * @param type ͬ��
	 * @return String,����
	 * @throws ControlException
	 */
	protected  Control createControlInstance(String type) throws ControlException {
		
		Document mapdoc=null;
		
		try {
			mapdoc = XMLDocumentManager.getDocument(CONTROL_CONFIGTYPE_FILE,true);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ControlException("�޷���ȡ" + CONTROL_CONFIGTYPE_FILE + "�ؼ�����ӳ���ļ�");			
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new ControlException("��ȡ" + CONTROL_CONFIGTYPE_FILE + "�ؼ�����ӳ���ļ�����:");	
		}
		
		Element root = mapdoc.getRootElement();
		Element node = root.element(type);
		if (node == null) {
			throw new ControlException("����Ϊ" + type + "�Ŀؼ�û�ж�������ӳ�䣬�����ļ�"
					+ CONTROL_CONFIGTYPE_FILE
					+ "[type_mapping]�ڵ��У���������͡�");
		}
		
		Control control = null;
		String cname = node.attribute("class").getValue();
		try {
			Class c = Class.forName(cname);
			control = (Control) c.newInstance();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			throw new ControlException(type + "�Ŀؼ�����ӳ��" + cname
					+ "��classpath�в����ڣ����������ļ������Ͷ���.");
		} catch (InstantiationException ex) {
			ex.printStackTrace();
			throw new ControlException(type + "�Ŀؼ��ڹ���ʱ���ִ���������" + cname
					+ "�Ĺ��캯���Ƿ���ȷ.");
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
			throw new ControlException(type + "�Ŀؼ��ڹ���ʱ���ִ���������" + cname
					+ "�Ĺ��캯���Ƿ��ǷǷ�����.");
		}
		return control;
	}

	
	/**
	 *  ���������ļ������Document�������reloadΪtrue��ÿ�����¼���
	 * @param xmlfile XML�����ļ������ơ�������·����ϵͳ��classpath�²��ҡ�
	 * @return Document,XML�����ļ����ڴ����
	 * @throws ControlException
	 */
	protected  Document getDocument() throws ControlException {
		try {
			if(doc == null)
				doc = XMLDocumentManager.getDocument(this.xmlFileName, isCached());
			return doc;
		} catch (Exception e) {
			throw new ControlException("ָ�������ļ�" + this.xmlFileName + "�ĸ�ʽ����.\n"
					+ e.getMessage());
		}
	}

	/**
	 * ����һ��Element����һ��Control���󣬲��õݹ��㷨��
	 * @param node��һ��XML�Ľڵ㣬��Ӧһ���ؼ���
	 * @return Control�������
	 * @throws java.lang.Exception
	 */
	public Control createControl(Element node) throws ControlException {

		if (node == null) return null;

		String name = node.getName();

		Control c = createControlInstance(name);

		// ���øø��Ͽؼ�������
		ConfigTools.setAttributes(c, node);

		//����Ǹ��Ͽؼ�������õݹ��㷨��
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
	 * ���ݿؼ�ID������һ���ؼ����ú����Զ����������͡�
	 * @param type ͬ��
	 * @return Control�������
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
		throw new ControlException("�����ļ�" + this.xmlFileName + "�в�����IDΪ" + id
				+ "�Ŀؼ�.");
	}

	/**
	 * ���������ļ�������
	 * @param fileName
	 */
	public void setXMLFile(String fileName) {
		this.xmlFileName = fileName;
	}

	/**
	 * ��������ļ�������
	 * @return
	 */
	public String getXMLFile() {
		return this.xmlFileName;
	}

	/**
	 * �ж��Ƿ�ʹ�û��漼����false���û���
	 * @return
	 */
	public boolean isCached() {
		return cached;
	}

	/**
	 * �����Ƿ�ʹ�û��漼��
	 * @param reload
	 * 
	 * @uml.property name="reload"
	 */
	public void setCached(boolean reload) {
		this.cached = reload;
	};
//	/**
//	 * ����ҵ�����
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
 * <p>Company: ���������¸���</p>
 * @author ����
 * @version 1.0
 */

class XMLDocumentManager {

  private static HashMap cach = new HashMap();
//  private static IXmlFileGetter xmlFileGetterService = null;
  

  /**
   * ����ָ��URL�����Ӧ��Document��
   * @param url
   * @param useCach �Ƿ�ʹ�û���
   * @return
   * @throws IOException
 * @throws DocumentException 
   * @throws JDOMException
   */
  public static Document getDocument(String url, boolean useCach) throws IOException, DocumentException
       {
    //ʹ�û��棬���һ����д��ڣ���ֱ�ӷ��ء�
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
//   * д�������ļ�
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
   * �ӱ��ػ��Document
   * @param url
   * @return
   * @throws IOException
 * @throws DocumentException 
   * @throws JDOMException
   */
  private static Document getDocumentAtLocal(String url) throws
      IOException, DocumentException {
  	//����Jar�е��ļ�
	  byte[] byteFile = FileUtil.file2Bytes(url);
      String content = new String(byteFile, "GBK");
//  	String content = FileUtil.file2String(url);
    if (content == null) {
      throw new IOException("Can't read file " + url);
    }
    Document doc = DocumentHelper.parseText(content);
//	  //by sunwenjun 060510 �ͻ����������ļ����ڷ����
//	  URL in = Resource.class.getResource("/");
//	  String path = in.getPath();
//	  url = path + url;
//	  SAXReader saxReader = new SAXReader();
//      Document doc = saxReader.read(new File(url));
    return doc;
  }
  
//  /**
//   * �ӷ���˻��Document
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
   * ���һ�������ļ���XML����Դ�ĸ���ʹ�û��档
   * @param url��ClassPath�е��ļ����ƣ��������·����
   * @param useCache �Ƿ�ʹ�û���
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
   * ���һ�������ļ���XML����Դ�ĸ�����ʹ�û��档
   * @param url��ClassPath�е��ļ����ƣ��������·����
   * @return
 * @throws JDOMException
 * @throws IOException
 * @throws DocumentException 
   */
  public static Element getRootElement(String url) throws IOException, DocumentException{
  	return getRootElement(url,false);
  }

}