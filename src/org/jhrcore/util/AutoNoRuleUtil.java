/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.AutoNoRule;

/**
 *
 * @author lenovo
 */
public class AutoNoRuleUtil {

    public static String getAutoNo(AutoNoRule anr, int reach, Hashtable<String, String> params) {
        String new_no = "";
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String ymd = df.format(date);
        String prefix = anr.isAdd_perfix() ? ((anr.getT_perfix() == null || anr.getT_perfix().trim().equals("")) ? "''" : anr.getT_perfix()) : "''";
        String tmp_s = "";
        if (anr.getNo_unit().equals("顺序编码")) {
        } else if (anr.getNo_unit().equals("按年")) {
            tmp_s = ymd.substring(0, 4);
        } else if (anr.getNo_unit().equals("按月")) {
            tmp_s = ymd.substring(0, 6);
        } else if (anr.getNo_unit().equals("按天")) {
            tmp_s = ymd;
        }
        prefix = prefix.replace("@年份", "'" + ymd.substring(0, 4) + "'");
        prefix = prefix.replace("@月份", "'" + ymd.substring(4, 6) + "'");
        prefix = prefix.replace("@日期", "'" + ymd.substring(6) + "'");
        if (params != null) {
            for (String key : params.keySet()) {
                prefix = prefix.replace(key, params.get(key));
            }
        }
        prefix = prefix + tmp_s;
        int anr_no = anr.getInit_no();
        if (reach == -1) {
            anr_no += anr.getInc_no();
        } else {
            anr_no += reach;
        }
        new_no = "" + anr_no;
        while (new_no.length() < (anr.getNo_lenth())) {
            new_no = "0" + new_no;
        }
        new_no = prefix + new_no;
        return new_no;
    }
    

}
