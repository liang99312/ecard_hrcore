/*
 * @(#)DataString.java	1.0 14/03/06
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.util;

import java.security.MessageDigest;
import java.util.*;

/**
 * �ַ�������ͨ����
 * @version 1.0
 * @author victor
 * @since java 1.4.1
 */
public class DealString
{

  public DealString()
  {
  }
  /**
   * �ַ����滻������ֻ�滻���е�oldString��
   * @param originString Դ�ַ���
   * @param oldString ���滻���ַ���
   * @param newString �滻�ɸ��ַ���
   * @version jdk1.3 below
   * @return �滻����ַ���
   */
  public static String replace(String originString, String oldString,
                               String newString)
  {
    String getstr = originString;
    while (getstr.indexOf(oldString) > -1)
    {
      getstr = getstr.substring(0, getstr.indexOf(oldString)) + newString +
          getstr.substring(getstr.indexOf(oldString) + oldString.length(),
                           getstr.length());
    }
    return getstr;
  }

  /**
   * �ַ����滻������ֻ�滻���ȷ��ֵ�oldString��
   * @param originString Դ�ַ���
   * @param oldString ���滻���ַ���
   * @param newString �滻�ɸ��ַ���
   * @return �滻����ַ���
   */
  public static String replaceOneTime(String originString, String oldString,
                                      String newString)
  {
    String getstr = originString;
    getstr = getstr.substring(0, getstr.indexOf(oldString)) + newString +
        getstr.substring(getstr.indexOf(oldString) + oldString.length(),
                         getstr.length());
    return getstr;
  }

  /**
   * ת��ΪISO-8859-1�ַ���
   * @param tempSql ��������ַ���
   * @return �������ַ���
   */
  public static String ISOCode(String tempSql)
  {

    String returnString = (tempSql == null ? "":tempSql);

    try
    {
      byte[] ascii = returnString.getBytes("GBK");
      returnString = new String(ascii, "ISO-8859-1");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return returnString;
  }

  /**
   * ת��ΪGBK�ַ���
   * @param tempSql ��������ַ���
   * @return �������ַ���
   */
  public static String GBKCode(String tempSql)
  {
    String returnString = (tempSql == null ? "":tempSql);
    try
    {
      byte[] ascii = returnString.getBytes("ISO-8859-1");
      returnString = new String(ascii, "GBK");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return returnString;
  }
  /**
   * ���롰(001)Ԥ���ڡ���ͬ��ʽ���ַ����л�á����롱�롰���ơ�
   * @param strTmp ��������ַ���
   * @return �ַ������飺�����롱�롰���ơ�
   */
  public static String[] getCodeNamefromOne(String strTmp)
  {
    String[] strArr = new String[2];
    strTmp = (strTmp == null) ? "" : strTmp;
    int intIndex = strTmp.indexOf(")");
    if ( (strTmp.indexOf("(") == 0) && (intIndex > 0) &&
        (strTmp.length() > intIndex))
    {
      strArr[0] = strTmp.substring(1, intIndex);
      strArr[1] = strTmp.substring(intIndex + 1);
    }
    else
    {
      strArr = null;
    }
    return strArr;
  }

  /**
   * ���롰(001)Ԥ���ڡ���ͬ��ʽ���ַ����л�á����롱
   * @param strTmp  ��������ַ���
   * @return �����롱
   */
  public static String getCodefromOne(String strTmp)
  {
    strTmp = (strTmp == null) ? "" : strTmp;
    strTmp = strTmp.trim();
    int intIndex = strTmp.indexOf(")");
    if ( (strTmp.indexOf("(") == 0) && (intIndex > 0))
    {
      strTmp = strTmp.substring(1, intIndex);
    }
    return strTmp;
  }

  private static final char[] LT_ENCODE = "&lt;".toCharArray();
  private static final char[] GT_ENCODE = "&gt;".toCharArray();

  /**
   * �滻HTML�ı��д���С�ڷ���
   * @param in  ��������ַ���
   * @return �������ַ���
   */
  public static final String escapeHTMLTags(String in)
  {
    if (in == null)
    {
      return null;
    }
    char ch;
    int i = 0;
    int last = 0;
    char[] input = in.toCharArray();
    int len = input.length;
    StringBuffer out = new StringBuffer( (int) (len * 1.3));
    for (; i < len; i++)
    {
      ch = input[i];

      if (ch > '>')
      {
        continue;
      }
      else if (ch == '<')
      {
        if (i > last)
        {
          out.append(input, last, i - last);
        }
        last = i + 1;
        out.append(LT_ENCODE);
      }
      else if (ch == '>')
      {
        if (i > last)
        {
          out.append(input, last, i - last);
        }
        last = i + 1;
        out.append(GT_ENCODE);
      }
    }
    if (last == 0)
    {
      return in;
    }
    if (i > last)
    {
      out.append(input, last, i - last);
    }
    return out.toString();
  }

  /**
   * �����ַ����е�'���ţ�����''�滻
   * @param allstr ��������ַ���
   * @return �������ַ���
   */
  public static String filterString(String allstr)
  {
    StringBuffer returnString = new StringBuffer(allstr.length());
    char ch = ' ';
    for (int i = 0; i < allstr.length(); i++)
    {
      ch = allstr.charAt(i);
      String lsTemp = "'";
      char lcTemp = lsTemp.charAt(0);
      if (ch == lcTemp)
      {
        returnString.append("''");
      }
      else
      {
        returnString.append(ch);
      }
    }
    return returnString.toString();
  }

  /**
   * �ֽ�ת����ʮ�������ַ���
   * @param b �ֽ�
   * @return ʮ�������ַ���
   */
  public static String byte2hex(byte[] b)
  {
    String hs = "";
    String stmp = "";
    for (int n = 0; n < b.length; n++)
    {
      stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
      if (stmp.length() == 1)
      {
        hs = hs + "0" + stmp;
      }
      else
      {
        hs = hs + stmp;
      }
    }
    return hs;
  }

  /**
   * �����ַ���
   * @param in ��������ַ���
   * @return md5�����ַ���
   */
  public static String EncryptString(String in)
  {
    try
    {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      md5.update(in.trim().getBytes());

      byte[] b = md5.digest();
      return byte2hex(b);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return null;
  }

  /**
   * ������ֵ�С�����Ƿ�����Ҫ��
   * @param orgString ��������� �磬10000.10 ��100
   * @param bitNumber С����λ������2
   * @return �Ƿ�����Ҫ��
   */
  public static boolean validateNumber(String orgString, int bitNumber)
  {
    if (!isNumber(orgString))
    {
      return false;
    }
    StringTokenizer strT = new StringTokenizer(orgString,".");
    if (strT.countTokens() == 1)
    { // ����������10000
      return true;
    }
    if (strT.countTokens() == 2)
    { // ����������10000.10
      strT.nextToken();
      String bitString = strT.nextToken();
      if (bitString.length() <= bitNumber)
      {
        return true;
      }
      else
      {
        return false;
      }
    }
    if (strT.countTokens() > 2)
    {
      return false;
    }
    return false;
  }
/**
 * ��֤�ַ����Ƿ�����������
 * @param number
 * @return
 */
  public static boolean isNumber(String number)
  {
    try
    {
      new java.math.BigDecimal(number);
      return true;
    }
    catch (Exception ex)
    {
      return false;
    }
  }
  /**
   * �������͸�ʽ����2002-1-1 ��ʽ����  2002-01-01��
   * @param dateString Ҫ���������
   * @return ���������ڣ����������Ƿ����ڸ�ʽ������2002a-a-f ���Ծɷ��ش˸�ʽ
   */
  public static String formatDate(String dateString)
  {
    String orgString = dateString;
    int year,month,day;
    String monStr = "";
    String dayStr = "";
    try
    {
      StringTokenizer strT = new StringTokenizer(orgString,"-");
      year = Integer.parseInt(strT.nextToken());
      month = Integer.parseInt(strT.nextToken());
      day = Integer.parseInt(strT.nextToken());
      if (! (year > 1900 && year < 2050))
      {
        throw new Exception("�Ƿ��������ַ���!");
      }
      if (! (month > 0 && month < 13))
      {
        throw new Exception("�Ƿ��������ַ���!");
      }
      else
      {
        monStr = (month < 10)?("0"+month):String.valueOf(month);
      }
      if (! (day > 0 && day < 32))
      {
        throw new Exception("�Ƿ��������ַ���!");
      }
      else
      {
        dayStr = (day < 10)?("0"+day):String.valueOf(day);
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      return orgString;
    }
    return (year + "-" + monStr + "-" + dayStr).toString();
  }

  /**
   * ������ת�����ɷֺŷֿ����ַ���,�ԷֺŽ�β
   * @param c ���ݼ���
   * @return �ɷֺŷֿ����ַ���
   */
  public static String turn2String(Collection c)
  {
    StringBuffer lsReturn = new StringBuffer();
    Iterator it = c.iterator();
    while (it.hasNext())
    {
      Object oTemp = it.next();
      if (oTemp instanceof Collection)
      {
        lsReturn.append(turn2String( (Collection) oTemp));
      }
      else
      {
        lsReturn.append(oTemp.toString());
      }
      lsReturn.append(";");
    }
    return lsReturn.toString();
  }
  
  /**
   * ���ݡ�1��AAA��2��BBB��3��CCC���ַ�������һ����λ����:{{1,AAA},{2,BBB},{3,CCC}}
   *
   * @param arrayStr ��ʽ�����ַ���
   * @return ������Ķ�ά����
   */
  public static synchronized String[][] buildString(String arrayStr) {

    if (arrayStr == null)
      return null;
    StringTokenizer st = new StringTokenizer(arrayStr, "+");
    String[][] ret = new String[st.countTokens()][2];
    int i = 0;
    while (st.hasMoreTokens()) {
      String temp = st.nextToken();
      String[] s = temp.split("#");
      if (s.length >= 2) {
        ret[i][0] = s[0];
        ret[i][1] = s[1];
      } 
      else if (s.length == 0) {
    	  ret[i][0] = "";
          ret[i][1] = "";
      }
      else {
        ret[i][0] = s[0];
        ret[i][1] = s[0];
      }
      i++;
    }
    
    return ret;
  }
  
  	/**
	 * �����ַ������õ��ַ�������
	 * @param str �ַ���
	 * @param delim �ָ���
	 * @return ��������ַ�������
	 */
  	public static String[] parseString2Array(String str, String delim) {
	    if (str == null)
		      return null;
		    StringTokenizer st = new StringTokenizer(str, delim);
		    String[] ret = new String[st.countTokens()];
		    int i = 0;
		    while (st.hasMoreTokens()) {
		    	ret[i] = st.nextToken();
		        i++;
		    }
		    return ret;
	}	

}