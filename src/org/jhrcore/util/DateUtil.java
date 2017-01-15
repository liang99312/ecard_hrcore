/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.jhrcore.client.CommUtil;
import org.jhrcore.client.UserContext;

/**
 *主要用于关于日期和字符串的转换处理
 * @author mxliteboss
 */
public class DateUtil {

    private static Logger log = Logger.getLogger(DateUtil.class.getName());
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final TimeZone tz = TimeZone.getTimeZone("GMT+8:00");

    static {
        dateFormat.setTimeZone(tz);
        dateFormat.setLenient(false);
    }

    //验证年合法性
    public static boolean isValidYear(String s) {
        return isValidDate(s, "yyyy");
    }
    
    
  public static String getDateWeek(Date d)
  {
    SimpleDateFormat f = new SimpleDateFormat("E");
    return f.format(d);
  }

    //验证日期合法性
    public static boolean isValidDate(String s) {
        return isValidDate(s, "yyyy-MM-dd");
    }

    /**验证日期合法性
     *
     * @param s:验证的日期字符串
     * @param format_str：格式化字符串
     * @return：是；否
     */
    public static boolean isValidDate(String s, String format_str) {
        DateFormat df = new SimpleDateFormat(format_str);
        df.setTimeZone(tz);
        df.setLenient(false);
        try {
            df.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    public static String getTodayStr() {
        return DateToStr(new Date());
    }

    public static boolean check_pay_month(String pay_month) {
        boolean pay_month_flag = false;
        if (pay_month == null) {
            pay_month_flag = true;
        } else {
            if (pay_month.length() != 6) {
                pay_month_flag = true;
            } else {
                Pattern p = Pattern.compile("^[0-9]+$");
                Matcher m = p.matcher(pay_month);
                if (!m.matches()) {
                    pay_month_flag = true;
                } else {
                    int month = Integer.valueOf(pay_month.substring(pay_month.length() - 2));
                    if (month > 12 || month == 0) {
                        pay_month_flag = true;
                    }
                }

            }

        }
        return pay_month_flag;
    }

    public static List<String> getMonthByTimes(String startMonth, String endMonth) {
        Calendar start = Calendar.getInstance();
        start.set(Calendar.YEAR, Integer.valueOf(startMonth.substring(0, 4)));
        start.set(Calendar.MONTH, Integer.valueOf(startMonth.substring(4)) - 1);
        start.set(Calendar.DAY_OF_MONTH, 1);
        Calendar end = Calendar.getInstance();
        end.set(Calendar.YEAR, Integer.valueOf(endMonth.substring(0, 4)));
        end.set(Calendar.MONTH, Integer.valueOf(endMonth.substring(4)) - 1);
        start.set(Calendar.DAY_OF_MONTH, 2);
        return getMonthByTimes(start, end);
    }

    public static String getLastMonth(String curMonth) {
        return getLastNumMonth(curMonth, 1);
    }

    public static String getLastNumMonth(String curMonth, int number) {//获得上第几个月份
        return getNumMonth(curMonth, -number);
    }

    public static String getNextMonth(String curMonth) {
        return getNextNumMonth(curMonth, 1);
    }

    public static Date getNextMonth(Date curMonth) {
        return getNumMonth(curMonth, 1);
    }

    public static String getNextNumMonth(String curMonth, int number) {//获得下第几个月份
        return getNumMonth(curMonth, number);
    }

    public static String getNumMonth(String curMonth, int number) {//获得第几个月份//number可以为正负 正表示未来的第number个月份，负表示过去的第number个月份
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.valueOf(curMonth.substring(0, 4)));
        c.set(Calendar.MONTH, Integer.valueOf(curMonth.substring(4)) + (number - 1));
        String mm = new SimpleDateFormat("yyyyMM").format(c.getTime());
        return mm;
    }

    public static Date getNumMonth(Date curMonth, int number) {//获得第几个月份//number可以为正负 正表示未来的第number个月份，负表示过去的第number个月份
        String cur_month = new SimpleDateFormat("yyyyMM").format(curMonth);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.valueOf(cur_month.substring(0, 4)));
        c.set(Calendar.MONTH, Integer.valueOf(cur_month.substring(4)) + (number - 1));
        return c.getTime();
    }

    public static Date getNumMonthFirstDay(Date curMonth, int number) {//获得第几个月份//number可以为正负 正表示未来的第number个月份，负表示过去的第number个月份
        String cur_month = new SimpleDateFormat("yyyyMM").format(curMonth);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.valueOf(cur_month.substring(0, 4)));
        c.set(Calendar.MONTH, Integer.valueOf(cur_month.substring(4)) + (number - 1));
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    public static String getThisMonth() {
        Calendar c = Calendar.getInstance();
        c.setTime(CommUtil.getServerDate());
        String mm = new SimpleDateFormat("yyyyMM").format(c.getTime());
        return mm;
    }

    public static List<String> getMonthByTimes(Calendar start, Calendar end) {
        List<String> years = new ArrayList<String>();
        while (!start.after(end)) {
            int month = start.get(Calendar.MONTH) + 1;
            if (month < 10) {
                years.add(start.get(Calendar.YEAR) + "0" + month);
            } else {
                years.add(start.get(Calendar.YEAR) + "" + month);
            }
            start.add(Calendar.MONTH, 1);
        }
        return years;
    }

    public static String DateToStr(Date dt, String format) {
        if (dt == null) {
            return "";
        }
        if (format == null) {
            format = "yyyy-MM-dd";
        }
        DateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(tz);
        df.setLenient(false);
        return df.format(dt);
    }

    public static String DateToStr(Date dt) {
        if (dt == null) {
            return "";
        }
        return dateFormat.format(dt);
    }

    public static Date StrToDate(String str) {
        if (str == null || str.equals("") || str.contains("'")) {
            return null;
        }
        String format = "yyyy-MM-dd";
        if (str.contains("-") || str.contains(":")) {
            String[] ls = str.split("-");
            if (ls.length==0||ls[0].length() > 4) {
                return null;
            }
            if (ls.length == 2) {
                format = "yyyy-MM";
            }
            if (str.contains(":")) {
                String[] ls1 = str.split(":");
                if (ls1.length == 1) {
                    format += " HH";
                } else if (ls1.length == 2) {
                    format += " HH:mm";
                } else if (ls1.length == 3) {
                    format += " HH:mm:ss";
                }
            }
        } else if (str.contains("年") || str.contains("月") || str.contains("日") || str.contains("时") || str.contains("分")) {
            format = "";
            if (str.contains("年")) {
                format += "yyyy年";
            } else if (str.contains("月")) {
                format += "MM月";
            } else if (str.contains("日")) {
                format += "dd日";
            } else if (str.contains("时")) {
                format += "HH时";
            } else if (str.contains("分")) {
                format += "mm分";
            } else if (str.contains("秒")) {
                format += "ss秒";
            }
        } else {
            int len = str.length();
            if (len == 6) {
                format = "yyyyMM";
            } else if (len == 8) {
                format = "yyyyMMdd";
            } else if (len == 13) {
                format = "yyyy-MM-dd HH";
            } else if (len == 16) {
                format = "yyyy-MM-dd HH:mm";
            } else if (len == 19) {
                format = "yyyy-MM-dd HH:mm:ss";
            }
        }
        DateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(tz);
        df.setLenient(false);
        try {
            return df.parse(str);
        } catch (ParseException e) {
            log.error(e);
            return null;
        }
    }

    public static Date StrToDate(String str, String format_str) {
        return StrToDate(str, format_str, null);
    }

    public static Date StrToDate(String str, String format_str, Date default_date) {
        if (str == null || str.equals("")) {
            return null;
        }
        DateFormat format2 = new SimpleDateFormat(format_str);
        format2.setTimeZone(tz);
        format2.setLenient(false);
        try {
            return format2.parse(str);
        } catch (ParseException e) {
            log.error(e);
            return default_date;
        }
    }

    public static String toStringForQuery(Date date) {
        return toStringForQuery(date, "yyyy-MM-dd");
    }

    public static String toStringForQuery(Date date, String format_str) {
        return toStringForQuery(date, format_str, UserContext.sql_dialect);
    }

    public static String toStringForQuery(Date date, String format_str, String db_type) {
        return toStringForQuery(date, format_str, db_type, -1);
    }

    public static String toStringForQuery(Date date, int type) {
        return toStringForQuery(date, "yyyy-MM-dd HH:mm:ss", UserContext.sql_dialect, type);
    }

    public static String toStringForQuery(Date date, String format_str, int type) {
        return toStringForQuery(date, format_str, UserContext.sql_dialect, type);
    }

    public static String toStringForQuery(Date date, String format_str, String db_type, int type) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (type == 0) {
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
        } else if (type == 1) {
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
        }
        if (SysUtil.objToStr(format_str).equals("")) {
            format_str = "yyyy-MM-dd";
        }
        SimpleDateFormat dateFormat2 = new SimpleDateFormat(format_str);
        dateFormat2.setTimeZone(tz);
        dateFormat2.setLenient(false);
        String date_str = dateFormat2.format(c.getTime());
        return toStringForQuery(date_str, format_str, db_type, type);
    }

    public static String toStringForQuery(String dateStr, String format_str, String db_type, int type) {
        if ("sqlserver".equals(db_type)) {
            return "'" + dateStr + "'";
        }else if ("mysql".equals(db_type)) {
            format_str = format_str.replace("yyyy", "%Y").replace("MM", "%m").replace("dd", "%d").replace("HH", "%H").replace("hh", "%H").replace("mm", "%i").replace("ss", "%s");
            return "date_format('" + dateStr + "','" + format_str + "')";
        } else {
            format_str = format_str.replace("hh", "HH").replace("HH", "HH24").replace("mm", "MI");
            return "to_date('" + dateStr + "','" + format_str + "')";
        }
    }

    public static String fetchDay(Object date) {
        int index = 0;
        Calendar c = Calendar.getInstance();
        if (date instanceof Calendar) {
            c = (Calendar) date;
        } else if (date instanceof Date) {
            c.setTime((Date) date);
        } else {
            return null;
        }
        index = c.get(Calendar.DAY_OF_WEEK);
        if (index == 1) {
            return "星期日";
        }
        if (index == 2) {
            return "星期一";
        }
        if (index == 3) {
            return "星期二";
        }
        if (index == 4) {
            return "星期三";
        }
        if (index == 5) {
            return "星期四";
        }
        if (index == 6) {
            return "星期五";
        }
        if (index == 7) {
            return "星期六";
        }

        return null;

    }

    /**
     * 该方法用于获取当月第一天对应的日期
     * @return
     */
    public static Date getCurMonthFirstDay() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date getMonthFirstDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date getMonthLastDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 0);
        return c.getTime();
    }

    public static Date getYearFirstDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    public static Date getYearLastDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.MONTH, 11);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 0);
        return c.getTime();
    }

    public static Date getSessionFirstDay(Date d, int session) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        if (session == 1) {
            c.set(Calendar.MONTH, 0);
        } else if (session == 2) {
            c.set(Calendar.MONTH, 3);
        } else if (session == 3) {
            c.set(Calendar.MONTH, 6);
        } else if (session == 4) {
            c.set(Calendar.MONTH, 9);
        } else {
            c.set(Calendar.MONTH, 0);
        }
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    public static Date getSessionLastDay(Date d, int session) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        if (session == 1) {
            c.set(Calendar.MONTH, 2);
        } else if (session == 2) {
            c.set(Calendar.MONTH, 5);
        } else if (session == 3) {
            c.set(Calendar.MONTH, 8);
        } else if (session == 4) {
            c.set(Calendar.MONTH, 11);
        } else {
            c.set(Calendar.MONTH, 0);
        }
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 0);
        return c.getTime();
    }

    public static Date getYesterday(String nowDate) {
        if (nowDate == null || "".equals(nowDate)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(tz);
        format.setLenient(false);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(nowDate));
        } catch (ParseException ex) {
            log.error(ex);
        }
        c.add(Calendar.DAY_OF_MONTH, -1);
        return c.getTime();
    }

    public static Date getNextday(String nowDate) {
        if (nowDate == null || "".equals(nowDate)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(tz);
        format.setLenient(false);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(nowDate));
        } catch (ParseException ex) {
            log.error(ex);
        }
        c.add(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    public static Date getNextDay(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(tz);
        format.setLenient(false);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    public static String getThisYear() {
        Date date = CommUtil.getServerDate();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String mm = new SimpleDateFormat("yyyy").format(c.getTime());
        return mm;
    }

    public static String getLastYear(String ym) {
        return getNumYear(ym, -1);
    }

    public static String getNumYear(String ym, int num) {
        String mm = null;
        boolean pn = false;
        if (ym == null || ym.isEmpty() || ym.length() != 4) {
            pn = true;
        } else {
            try {
                if (!pn) {
                    int i = Integer.valueOf(ym) + num;
                    mm = "" + i;
                }
            } catch (NumberFormatException e) {
                pn = true;
            }
        }
        if (pn) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            mm = new SimpleDateFormat("yyyy").format(c.getTime());
        }
        return mm;
    }

    public static String getNextYear(String ym) {
        return getNumYear(ym, 1);
    }

    public boolean validateYearStr(String str) {
        boolean val_falg = false;
        if (str == null || str.isEmpty()) {
            return val_falg;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(str));
            val_falg = true;
        } catch (ParseException ex) {
            val_falg = false;
        }
        return val_falg;
    }

    public static Date getAnyDate(Date d, int rel_year, int rel_mm, int rel_day) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.YEAR, rel_year);
        c.add(Calendar.MONTH, rel_mm);
        c.add(Calendar.DAY_OF_MONTH, rel_day);
        return c.getTime();
    }

    /*
     *  两个日期之间的隔天数
     */
    public static int getBetweenDay(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }

        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    /*
     * 比较现在时间
     */
    public static int compareNow(Date c_date) {
        if (c_date == null) {
            return -1;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(c_date);
        return cal2.compareTo(cal);
    }

    private static String getDateStr(Date date, String format, boolean max) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (!format.contains("s")) {
            c.set(Calendar.SECOND, max ? 59 : 0);
        }
        if (!format.contains("m")) {
            c.set(Calendar.MINUTE, max ? 59 : 0);
        }
        if (!format.contains("H")) {
            c.set(Calendar.HOUR_OF_DAY, max ? 23 : 0);
        }
        return DateUtil.toStringForQuery(c.getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getDateSQL(String fieldName, String oper, String fieldValue, String format) {
        if (oper.toLowerCase().contains("null")) {
            return fieldName + " " + oper;
        }
        String tmp_2 = "";
        if (UserContext.sql_dialect.equals("sqlserver")) {
            fieldValue = fieldValue.replace("'", "");
            Date date = DateUtil.StrToDate(fieldValue, format);
            String value = DateUtil.toStringForQuery((Date) date, format);
            if (format.equals("yyyy-MM-dd")) {
                tmp_2 = "convert(varchar(10)," + fieldName + ",23) " + oper + " " + value;
            } else if (format.equals("HH:mm:ss")) {
                tmp_2 = "convert(varchar(10)," + fieldName + ",108) " + oper + " " + value;
            } else {
                tmp_2 = "(convert(varchar," + fieldName + ",120)) ";
                if (oper.contains("=") || oper.contains(">") || oper.contains("<")) {
                    tmp_2 = fieldName + " ";
                    if (oper.equals(">")) {
                        value = getDateStr(date, format, true);
                    } else if (oper.equals("<")) {
                        value = getDateStr(date, format, false);
                    } else if (oper.equals("<=")) {
                        value = getDateStr(date, format, true);
                    } else if (oper.equals(">=")) {
                        value = getDateStr(date, format, false);
                    } else if (oper.equals("=")) {
                        oper = "between";
                        value = getDateStr(date, format, false) + " and " + getDateStr(date, format, true);
                    }
                } else {
                    value = getDateStr(date, format, false);
                }
                tmp_2 += oper + " " + value;
            }
        } else if(UserContext.sql_dialect.equals("mysql")){
            if (fieldValue.startsWith("to_date")) {
                fieldValue = fieldValue.substring(9, fieldValue.indexOf(",") - 1);
            }
            tmp_2 = "date_format(" + fieldName + ",'" + format.replace("yyyy", "%Y").replace("MM", "%m").replace("dd", "%d").replace("HH", "%H").replace("hh", "%H").replace("mm", "%i").replace("ss", "%s") + "') "
                    + oper + "'" + fieldValue + "'";
        } else {
            if (fieldValue.startsWith("to_date")) {
                fieldValue = fieldValue.substring(9, fieldValue.indexOf(",") - 1);
            }
            tmp_2 = "to_char(" + fieldName + ",'" + format.replace("hh", "HH").replace("HH", "HH24").replace("mm", "MI") + "') "
                    + oper + "'" + fieldValue + "'";
        }
        return tmp_2;
    }

    public static void main(String arg[]) {
        try {
            Date d = new SimpleDateFormat("yyyyMM").parse("201101");
            System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(getMonthLastDay(d)));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
}
