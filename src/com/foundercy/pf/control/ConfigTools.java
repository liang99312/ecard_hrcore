package com.foundercy.pf.control;

/**
 * <p>Title:�����������ù��� </p>
 * <p>Description:�ö��������ѭJavaBean�����淶�����Ҹö��������ֵ���Ǽ���������</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: ���������¸���</p>
 * @author: fangyi
 * @version 1.0
 */
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.StringTokenizer;

import org.dom4j.Attribute;
import org.dom4j.Element;


public class ConfigTools {

  public static final String SET_STRING = "set";
  public static final String REFERENCE_NAME = "ref";
  
  public static final String COLUMN_GAP = "columnGap";//ָ���̶��м��
  public static final String ROW_GAP = "rowGap";//ָ���̶��м��
  public static final String ROW_HEIGHT = "rowHeight";//ָ���иߣ�ÿ�еĸ߶ȣ�
  
  public static final String COLUMN_WIDTH= "columnWidth";//ָ���п�ÿ�еĿ�ȣ�
  public static final String COLUMNS_ATTR_NAME = "columns";//ָ���̶�����
  public static final String COLS_ATTR_NAME = "cols";//�ؼ�ռ�õ�����
  public static final String ROWS_ATTR_NAME = "rows";//�ؼ�ռ�õ�����
  /**
   * Ϊ�ӽ�HTMLд��ϰ�ߣ������µ�����colspan��rowspan��������cols��rows�÷���ͬ��
   */
  public static final String COLSPAN_ATTR_NAME = "colspan";//�ؼ�ռ�õ�����
  public static final String ROWSPAN_ATTR_NAME = "rowspan";//�ؼ�ռ�õ�����
  
  public static final String HEIGHTREADJUSTABLE_ATTR_NAME = "heightReadjustable";//�ؼ��߶��Ƿ���Ը��ݸ��ؼ��ĸ߶Ƚ��е���
  public static final String WIDTHREADJUSTABLE_ATTR_NAME = "widthReadjustable";//�ؼ�����Ƿ���Ը��ݸ��ؼ��ĸ߶Ƚ��е���
  
  public ConfigTools() {
  }

  /**
   * �����������������ö�������ԡ�
   * @param control
   * @param config
   */
  public synchronized static void setAttributes(Control control,
                                                Element node) {
    //����attrHash�����ԡ�
    List attrs = node.attributes();
    String attrName = null;
    for (int i = 0; attrs != null & i < attrs.size(); i++) {
      attrName =  ((Attribute)attrs.get(i)).getName();
      if (attrName.equalsIgnoreCase(COLUMN_GAP) || 
    	  attrName.equalsIgnoreCase(ROW_GAP) || 
    	  attrName.equalsIgnoreCase(ROW_HEIGHT) || 
    	  attrName.equalsIgnoreCase(COLUMN_WIDTH) || 
    	  attrName.equalsIgnoreCase(COLUMNS_ATTR_NAME) || 
    	  attrName.equalsIgnoreCase(COLS_ATTR_NAME) ||
    	  attrName.equalsIgnoreCase(ROWS_ATTR_NAME) ||
    	  attrName.equalsIgnoreCase(COLSPAN_ATTR_NAME) ||
    	  attrName.equalsIgnoreCase(ROWSPAN_ATTR_NAME) ||
    	  attrName.equalsIgnoreCase(HEIGHTREADJUSTABLE_ATTR_NAME)||
    	  attrName.equalsIgnoreCase(WIDTHREADJUSTABLE_ATTR_NAME)) {
    	  
    	  continue;
      }
      
      try {
        invokeAttrSettingMethod(control,(Attribute)attrs.get(i));
      }catch(ControlAttributeException ex) {
//        ex.printStackTrace();
      }
    }
  }

  /**
   * ��һ���������õ�����
   * @param control
   * @param attr
   */
  private synchronized static void invokeAttrSettingMethod(Control control,
      Attribute attr) throws ControlAttributeException {

    String methodName = SET_STRING + attr.getName().substring(0, 1).toUpperCase() +
        attr.getName().substring(1);
    if(methodName.equalsIgnoreCase(SET_STRING + REFERENCE_NAME))
      return;

    String[] values = getParameterValueString(attr);

    if (values == null || values.length == 0)
      return;


    Method[] ms = getMethodByName(control, methodName, values.length);

    for (int i = 0; ms != null && i < ms.length; i++) {
      Object[] realValues = null;
      try {
        realValues = match(ms[i], values);
      }
      catch (Exception ex) {
        continue;
      }
      try {
        ms[i].invoke(control, realValues);
        return;
      }
      catch (Exception ex) {
        continue;
      }
    }

      throw new ControlAttributeException("���ÿؼ�"+control.getClass()+"[id:"+control.getId()+"]����"+attr.getName()+"�����ú���"+methodName+"(...)ʧ�ܡ�");
  }

  /**
   * �������ƻ�����е�public������
   * @param control
   * @param methodName
   * @return
   */
  private synchronized static Method[] getMethodByName(Object control,
      String methodName, int paraNum) {
    java.util.Vector v = new java.util.Vector();
    Method[] ms = control.getClass().getMethods();
    for (int i = 0; ms != null & i < ms.length; i++) {
      if (ms[i].getName().equalsIgnoreCase(methodName) &&
          ms[i].getParameterTypes() != null &&
          ms[i].getParameterTypes().length == paraNum) {
        v.add(ms[i]);
      }
    }
    if (v.size() == 0)
      return null;
    Method[] ret = new Method[v.size()];
    v.copyInto(ret);
    return ret;
  }

  /**
   * ��������ֵ������Ե�����ֵ��
   * �������ļ�������ֵ������refmodel="1"
   * @param attr
   * @return
   */
  private synchronized static String[] getParameterValueString(Attribute attr) {

    if(attr.getValue().equals("")) {
      return new String[]{""};
    }
    //","�����޸�.
    StringTokenizer st = new StringTokenizer(attr.getValue(), ";");
    String[] values = new String[st.countTokens()];
    int i = 0;
    while (st.hasMoreElements()) {
      String v = (String) st.nextToken();
      if (v.equalsIgnoreCase("blank"))
        v = "";
      else if (v.equalsIgnoreCase("null"))
        v = null;
      values[i] = v;
      i++;
    }
    return values;
  }

  /**
   *
   * @param m
   * @param values
   * @return
   * @throws java.lang.Exception
   */
  private synchronized static Object[] match(Method m, String[] values) throws
      ControlAttributeException ,Exception{

    Object[] pvs = new Object[values.length];

    Class[] cls = m.getParameterTypes();

    for (int i = 0; values != null && i < values.length; i++) {
      if (values[i] != null) {
        pvs[i] = narrowObjectForClass(cls[i], values[i]);
      }
      else {
        pvs[i] = null;
      }
    }
    return pvs;
  }
  /**
   * ��һ��String���ַ������ݣ�ת��Ϊָ�������͡�
   *
   * @param cls
   *            Ҫת��������
   * @param value
   *            ��ת�����ַ���
   * @return ת���������
   */
  public synchronized static Object narrowObjectForClass(Class cls,
      String value) {

    //��������ֵΪnull,�򷵻�null;
    if (value == null)
      return null;

    //��������ֵΪ�ո�������ַ����⣬��ֱ�ӷ��ؿ�
    if (!cls.equals(String.class) && value.trim().length() == 0)
      return null;

    //������������������java.lang.Object�������κ�ת����
    if (cls.equals(Object.class)) {
      return value;
    }

    if (cls.equals(String.class)) {
      return value;
    }
    if (cls.equals(int.class) || cls.equals(Integer.class)) {
      return new Integer(value);
    }
    if (cls.equals(boolean.class) || cls.equals(Boolean.class)) {
      if (value.trim().equals("0"))
        value = "false";
      else if (value.trim().equalsIgnoreCase("true"))
        value = "true";
      else if (value.trim().equalsIgnoreCase("false"))
        value = "false";
      else
        value = "true";
      return new Boolean(value);
    }
    if (cls.equals(float.class) || cls.equals(Float.class)) {
      return new Float(value);
    }
    if (cls.equals(short.class) || cls.equals(Short.class)) {
      return new Short(value);
    }
    if (cls.equals(long.class) || cls.equals(Long.class)) {
      return new Long(value);
    }
    if (cls.equals(double.class) || cls.equals(Double.class)) {
      return new Double(value);
    }
    if (cls.equals(char.class) || cls.equals(Character.class)) {
      return new Character(value.charAt(0));
    }
    if (cls.equals(byte.class) || cls.equals(Byte.class)) {
      return new Byte(value);
    }
    if (cls.equals(BigDecimal.class)) {
      return new BigDecimal(value);
    }
    if (cls.equals(BigInteger.class)) {
      return new BigInteger(value);
    }
    throw new IllegalArgumentException(
        "can not narrowObjectForClass:unsupported type----" + cls.getName());
  }

}