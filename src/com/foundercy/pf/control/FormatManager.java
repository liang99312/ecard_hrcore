package com.foundercy.pf.control;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author fangyi
 * @version 1.0
 */

public class FormatManager {

  /**
   * �����������ļ���ʹ��
   */
  public static final String DATE = "date";

  public static final String TIME = "time";

  public static final String DATETIME = "datetime";

  public static final String LOCAL_CURRENCY = "local_currency";

  public static final String ASSIST_CURRENCY = "assist_currency";

  public static final String INTEGER_NUMBER = "integer";

  public static final String DECIMAL_NUMBER = "decimal";

  public static final String PERCENT = "percent";

  public static final String PERMILL = "permill";

  /**
   * ��ʽ��
   */
  private static Format dateFormat =null;

  private static Format timeFormat =null;

  private static Format dateTimeFormat = null;

  private static Format localCurrencyFormat = null;

  private static Format assistCurrencyFormat = null;

  private static Format integerNumberFormat = null;

  private static Format decimalNumberFormat = null;

  private static Format percentFormat = null;

  private static Format permillFormat = null;


  public FormatManager() {
  }
  /**
   * ��������͸�ʽ��
   * @return
   */
  public static synchronized Format getDateFormat() {
    if(dateFormat == null)  {
//      dateFormat = new SimpleDateFormat("yyyy��MM��dd��");
    	dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    return dateFormat;
  }
   
  /**
   * ���������͸�ʽ��
   * @param format
   */
  public static synchronized void setDateFormat(DateFormat format) {
    dateFormat = format;
  }
  
  /**
   * ���ʱ���͸�ʽ��
   * @return
   */
  public static synchronized Format getTimeFormat() {
    if(timeFormat == null)  {
      timeFormat = new SimpleDateFormat("HH:mm:ss");
    }
    return timeFormat;
  }
  /**
   * ����ʱ���͸�ʽ��
   * @param format
   */
  public static synchronized void setTimeFormat(DateFormat format) {
    timeFormat = format;
  }
  /**
   * �������+ʱ���͸�ʽ��
   * @return
   */
  public static synchronized Format getDateTimeFormat() {
    if(dateTimeFormat == null)  {
      dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    return dateTimeFormat;
  }

  /**
   * ��������+ʱ���͸�ʽ��
   * @return
   */
  public static synchronized void setDateTimeFormat(DateFormat format) {
    dateTimeFormat = format;
  }

  /**
   * ��ñ��Ҹ�ʽ��
   * @return
   */
  public static synchronized Format getLocalCurrencyFormat() {
    if(localCurrencyFormat == null)  {
      localCurrencyFormat = DecimalFormat.getCurrencyInstance();
   }
    return localCurrencyFormat;
  }
  /**
   * ���ñ��Ҹ�ʽ��
   * @param format
   */
  public static synchronized void setLocalCurrencyFormat(DecimalFormat format) {
    localCurrencyFormat = format;
  }
  /**
   * ��ø��Ҹ�ʽ��
   * @return
   */
  public static synchronized Format getAssistCurrencyFormat() {
    if(assistCurrencyFormat == null)  {
      assistCurrencyFormat=  DecimalFormat.getCurrencyInstance(java.util.Locale.US);
   }
    return assistCurrencyFormat;
  }
  /**
   *���ø��Ҹ�ʽ��
   * @param format
   */
  public static synchronized void AssistCurrencyFormat(DecimalFormat format) {
    assistCurrencyFormat = format;
  }
  /**
   * ���ø��Ҹ�ʽ��
   * @return
   */
  public static synchronized Format getIntegerNumberFormat() {
    if(integerNumberFormat == null)  {
      integerNumberFormat = new DecimalFormat("#,##0");
   }
    return integerNumberFormat;
  }
  /**
   * �����������ݸ�ʽ��
   * @param format
   */
  public static synchronized void setIntegerNumberFormat(DecimalFormat format) {
    integerNumberFormat = format;
  }
  /**
   * ���С�����͵ĸ�ʽ��
   * @return
   */
  public static synchronized Format getDecimalNumberFormat() {
    if(decimalNumberFormat == null)  {
      decimalNumberFormat = new DecimalFormat("#,##0.00");
   }
    return decimalNumberFormat;
  }

  /**
   * ����С�����͵ĸ�ʽ��
   * @param format
   */
  public static synchronized void setDecimalNumberFormat(DecimalFormat format) {
    decimalNumberFormat = format;
  }
  /**
   * ��ðٷ������͸�ʽ��
   */
  public static synchronized Format getPercentFormat() {
    if(percentFormat == null)  {
      percentFormat = new DecimalFormat("0.0###%");
   }
    return percentFormat;
  }
  /**
   * ���ðٷ������͸�ʽ��
   * @param format
   */
  public static synchronized void setPercentFormat(DecimalFormat format) {
    percentFormat = format;
  }

  /**
   * ���ǧ�������͸�ʽ��
   */
  public static synchronized Format getPermillFormat() {
    if(permillFormat == null)  {
      permillFormat = new DecimalFormat("##0.0###\u2030");
   }
    return permillFormat;
  }
  /**
   * ����ǧ�������͸�ʽ��
   * @param format
   */
  public static synchronized void setPermillFormat(DecimalFormat format) {
    permillFormat = format;
  }

}