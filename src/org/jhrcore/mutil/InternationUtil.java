/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.mutil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTree;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.UtilTool;
import org.jhrcore.entity.base.InternationBase;
import org.jhrcore.entity.base.InternationRes;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.entity.base.InternationLang;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jhrcore.entity.right.FuntionRight;
import org.jhrcore.iservice.impl.SysImpl;
import org.jhrcore.ui.CheckTreeNode;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.util.SysUtil;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author mxliteboss
 */
public class InternationUtil {

    public static InternationBase createInternationBase(String module_code, String fun_code, String fieldType, String title) {
        InternationBase fd1 = (InternationBase) UtilTool.createUIDEntity(InternationBase.class);
        fd1.setRes_key((module_code.equals("") ? "" : (module_code + ".")) + fun_code);
        fd1.setRes_text(title);
        fd1.setRes_type(fieldType);
        fd1.setUsed(true);
        fd1.setRes_flag("菜单");
        if (fieldType.equals("Msg")) {
            fd1.setRes_flag("消息");
        } else if (fieldType.equals("JLabel")) {
            fd1.setRes_flag("标签");
        } else if (fieldType.equals("JPanel") || fieldType.equals("JList") || fieldType.equals("JTabbedPane")) {
            fd1.setRes_flag("边框");
        } else if (fieldType.equals("Entity")) {
            fd1.setRes_flag("重构表");
        } else if (fieldType.equals("Field")) {
            fd1.setRes_flag("重构字段");
        } else if (fieldType.equals("Module")) {
            fd1.setRes_flag("重构模块");
        } else if (fieldType.equals("Class")) {
            fd1.setRes_flag("重构业务");
        } else if (fieldType.equals("Code")) {
            fd1.setRes_flag("编码");
        }

        return fd1;
    }

    public static ValidateSQLResult saveInternationBase(List list) {
        List insertList = new ArrayList();
        StringBuilder updateBuffer = new StringBuilder();
        for (Object obj : list) {
            InternationBase base = (InternationBase) obj;
            InternationBase interbase = (InternationBase) CommUtil.fetchEntityBy("from InternationBase b where  b.res_key='" + base.res_key + "'");
            if (null != interbase) {//如果存在且中文不一致  则需要更新
                if (null != interbase.getRes_text() && !interbase.getRes_text().equals(base.res_text)) {
                    String res_text = checkOut(base.res_text);
                    updateBuffer.append("update InternationBase  set InternationBase.res_text ='").append(res_text).
                            append("' where InternationBase.res_key='").append(base.res_key).append("'; ");//更新已存在的
                    updateBuffer.append("update InternationRes  set InternationRes.res_value='' where InternationRes.res_key ='").
                            append(base.res_key).append("';");//更新国际化资源文件
                } else {
                    continue;
                }
            } else {//新增
                insertList.add(base);//新增
            }
        }
        CommUtil.saveList(insertList);
        ValidateSQLResult result = CommUtil.excuteHQLs(updateBuffer.toString(), ";");
        return result;
    }

    public static ValidateSQLResult saveInternationRes(List list) {
        List insertList = new ArrayList();
        StringBuilder updateBuffer = new StringBuilder();
        for (Object obj : list) {
            InternationRes res = (InternationRes) obj;
            InternationRes interRes = (InternationRes) CommUtil.fetchEntityBy("from InternationRes b where  b.res_key='"
                    + res.res_key + "' and b.language='" + res.getLanguage() + "'");
            if (null != interRes) {//如果存在且中文不一致  则需要更新
                if (null != interRes.getRes_value() && interRes.getRes_value().equals(res.getRes_value())) {
                    continue;
                } else {
                    String res_value = checkOut(res.res_value);
                    updateBuffer.append("update InternationRes  set InternationRes.res_value='").append(res_value).append("' where InternationRes.res_key ='").
                            append(res.res_key).append("' and InternationRes.language='").
                            append(res.getLanguage()).append("';");//更新国际化资源文件
                    continue;
                }
            } else {//新增
                insertList.add(res);//新增
            }
        }
        CommUtil.saveList(insertList);
        ValidateSQLResult result = CommUtil.excuteHQLs(updateBuffer.toString(), ";");
        return result;
    }

    private static String checkOut(String sqlStr) {
        if (sqlStr.contains("'")) {
            sqlStr = sqlStr.replaceAll("'", "''");
        }
        return sqlStr;
    }

    public static ValidateSQLResult exportXml(File file) {
        ValidateSQLResult vs = new ValidateSQLResult();
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding("UTF-8");
        Map<String, String> inMap = new HashMap<String, String>();
        inMap.put("type", "text/xsl");
        document.addProcessingInstruction("recipe", inMap);
        // root element
        Element rootElement = document.addElement("Internation");
        rootElement.addComment("Internation  data");
        // son element
        //*****************************语种信息*****************************************//   
        List list = CommUtil.fetchEntities("from InternationLang");
        for (Object obj : list) {
            InternationLang res = (InternationLang) obj;
            Element langElement = rootElement.addElement("lang");
            Element keyElement = langElement.addElement("locale");
            keyElement.setText(res.locale);
            Element nameElement = langElement.addElement("explain");
            nameElement.setText(res.explain == null ? "" : res.explain);
            Element res_flag = langElement.addElement("orderNum");
            res_flag.setText(String.valueOf(res.orderNum));
            Element default_flag = langElement.addElement("defaultLang");
            default_flag.setText(res.defaultLang == null ? "" : res.defaultLang);
        }
        //*****************************基本信息*****************************************//   
        list = CommUtil.fetchEntities("from InternationBase ");
        Element baseElement = rootElement.addElement("InternationBase");
        for (Object obj : list) {
            InternationBase base = (InternationBase) obj;
            Element keyElement = baseElement.addElement("base");
            keyElement.setText(base.getRes_text());
            keyElement.addAttribute("res_key", base.getRes_key());
            keyElement.addAttribute("res_flag", base.getRes_flag());
            keyElement.addAttribute("res_type", base.getRes_type());
            keyElement.addAttribute("used", base.isUsed() ? "1" : "0");
        }
        //*****************************翻译数据*****************************************//      
        list = CommUtil.fetchEntities("from InternationRes r order by r.language");
        if (list.size() > 0) {
            String language = "";
            Element resElement = null;
            for (Object obj : list) {
                InternationRes res = (InternationRes) obj;
                if (!language.equals(res.getLanguage())) {
                    language = res.getLanguage();
                    resElement = rootElement.addElement("InternationRes");
                    resElement.addAttribute("language", language);
                }
                Element keyElement = resElement.addElement("res");
                keyElement.addAttribute("res_key", res.getRes_key());
                keyElement.setText(SysUtil.objToStr(res.getRes_value()));
            }
        }
        try {
            // 美化格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            FileOutputStream fos = new FileOutputStream(file);
            XMLWriter output = new XMLWriter(fos, format);
            output.write(document);
            output.close();
        } catch (IOException ex) {
            vs.setResult(1);
            vs.setMsg(ex.getMessage());
            if (ex instanceof FileNotFoundException) {
                vs.setMsg("另一个程序正在使用此文件，进程无法访问");
            }
        }
        return vs;
    }

    public static ValidateSQLResult importXml(File file) {
        List listLang = new ArrayList();
        List listBase = new ArrayList();
        List listRes = new ArrayList();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            org.w3c.dom.Document doc = db.parse(file);
            NodeList list = doc.getElementsByTagName("lang");
            int len = list.getLength();
            for (int i = 0; i < len; i++) {
                Node node = list.item(i);
                NodeList nl = node.getChildNodes();
                InternationLang lang = new InternationLang();
                int nlLen = nl.getLength();
                for (int j = 0; j < nlLen; j++) {
                    Node cNode = nl.item(j);
                    String field = cNode.getNodeName();
                    if (field.startsWith("#")) {
                        continue;
                    }
                    PublicUtil.setValueBy2(lang, field, cNode.getTextContent());
                }
                lang.setInternationLang_key(lang.getLocale());
                listLang.add(lang);
            }
            list = doc.getElementsByTagName("base");
            len = list.getLength();
            for (int i = 0; i < len; i++) {
                Node node = list.item(i);
                InternationBase ib = new InternationBase();
                NamedNodeMap map = node.getAttributes();
                ib.setRes_key(map.getNamedItem("res_key").getNodeValue());
                ib.setRes_flag(map.getNamedItem("res_flag").getNodeValue());
                ib.setRes_type(map.getNamedItem("res_type").getNodeValue());
                ib.setUsed(SysUtil.objToBoolean(map.getNamedItem("used").getNodeValue()));
                ib.setInternationBase_key(ib.getRes_key());
                ib.setRes_text(node.getTextContent());
                listBase.add(ib);
            }
            list = doc.getElementsByTagName("res");
            len = list.getLength();
            for (int i = 0; i < len; i++) {
                Node node = list.item(i);
                String language = node.getParentNode().getAttributes().getNamedItem("language").getNodeValue();
                if (language == null) {
                    continue;
                }
                String resKey = node.getAttributes().getNamedItem("res_key").getNodeValue();
                String resValue = node.getTextContent();
                if (resKey == null) {
                    continue;
                }
                InternationRes res = new InternationRes();
                res.setRes_key(resKey);
                res.setRes_value(resValue);
                res.setLanguage(language);
                listRes.add(res);
            }
        } catch (Exception ex) {
        }
        return SysImpl.importRes(listLang, listBase, listRes);
    }

    public static void addCommFunNode(JTree funTree) {
        FuntionRight fr = new FuntionRight();
        fr.setFun_module_flag("ModuleGroup");
        fr.setFun_name("模块分组");
        CheckTreeNode node = new CheckTreeNode(fr);
        ((CheckTreeNode) funTree.getModel().getRoot()).add(node);
        fr = new FuntionRight();
        fr.setFun_module_flag("Comm");
        fr.setFun_name("公共消息");
        node = new CheckTreeNode(fr);
        ((CheckTreeNode) funTree.getModel().getRoot()).add(node);
        funTree.updateUI();
    }
}
