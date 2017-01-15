/*
 * @(#)DataString.java	1.0 14/03/06
 *
 * Copyright 2006 by Founder Sprint 1st, Inc. All rights reserved.
 */
package com.foundercy.pf.util;

import java.security.MessageDigest;
import java.util.*;

/**
 * 字符串处理通用类
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
   * 字符串替换操作（只替换所有的oldString）
   * @param originString 源字符串
   * @param oldString 待替换的字符串
   * @param newString 替换成该字符串
   * @version jdk1.3 below
   * @return 替换后的字符串
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
   * 字符串替换操作（只替换最先发现的oldString）
   * @param originString 源字符串
   * @param oldString 待替换的字符串
   * @param newString 替换成该字符串
   * @return 替换后的字符串
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
   * 转换为ISO-8859-1字符串
   * @param tempSql 待处理的字符串
   * @return 处理后的字符串
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
   * 转换为GBK字符串
   * @param tempSql 待处理的字符串
   * @return 处理后的字符串
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
   * 从与“(001)预算内”相同样式的字符串中获得“代码”与“名称”
   * @param strTmp 待处理的字符串
   * @return 字符串数组：“代码”与“名称”
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
   * 从与“(001)预算内”相同样式的字符串中获得“代码”
   * @param strTmp  待处理的字符串
   * @return “代码”
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
   * 替换HTML文本中大于小于符号
   * @param in  待处理的字符串
   * @return 处理后的字符串
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
   * 过滤字符串中的'符号，并用''替换
   * @param allstr 待处理的字符串
   * @return 处理后的字符串
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
   * 字节转换成十六进制字符串
   * @param b 字节
   * @return 十六进制字符串
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
   * 加密字符串
   * @param in 待处理的字符串
   * @return md5加密字符串
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
   * 检测数字的小数点是否满足要求
   * @param orgString 输入的数字 如，10000.10 、100
   * @param bitNumber 小数点位数，如2
   * @return 是否满足要求
   */
  public static boolean validateNumber(String orgString, int bitNumber)
  {
    if (!isNumber(orgString))
    {
      return false;
    }
    StringTokenizer strT = new StringTokenizer(orgString,".");
    if (strT.countTokens() == 1)
    { // 像这样的数10000
      return true;
    }
    if (strT.countTokens() == 2)
    { // 像这样的数10000.10
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
 * 验证字符串是否是数字类型
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
   * 日期类型格式化（2002-1-1 格式化成  2002-01-01）
   * @param dateString 要处理的日期
   * @return 处理后的日期，如果输入的是非日期格式，比如2002a-a-f 将仍旧返回此格式
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
        throw new Exception("非法的日期字符串!");
      }
      if (! (month > 0 && month < 13))
      {
        throw new Exception("非法的日期字符串!");
      }
      else
      {
        monStr = (month < 10)?("0"+month):String.valueOf(month);
      }
      if (! (day > 0 && day < 32))
      {
        throw new Exception("非法的日期字符串!");
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
   * 将集合转换成由分号分开的字符串,以分号结尾
   * @param c 数据集合
   * @return 由分号分开的字符串
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
   * 根据“1＝AAA，2＝BBB，3＝CCC”字符串生成一个二位数组:{{1,AAA},{2,BBB},{3,CCC}}
   *
   * @param arrayStr 格式化的字符串
   * @return 解析后的二维数组
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
	 * 解析字符串，得到字符串数组
	 * @param str 字符串
	 * @param delim 分隔符
	 * @return 解析后的字符串数组
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