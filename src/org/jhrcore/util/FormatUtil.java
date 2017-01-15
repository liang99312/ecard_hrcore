/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import java.text.DecimalFormat;
import java.util.Hashtable;

/**
 *
 * @author mxliteboss
 */
public class FormatUtil {

    private static Hashtable<Integer, DecimalFormat> formatKeys = new Hashtable<Integer, DecimalFormat>();
    private static Hashtable<Integer, String> formatStrs = new Hashtable<Integer, String>();

    static {
        FormatUtil.formatKeys.put(0, new DecimalFormat("#"));
        FormatUtil.formatKeys.put(1, new DecimalFormat("0.0"));
        FormatUtil.formatKeys.put(2, new DecimalFormat("0.00"));
        FormatUtil.formatKeys.put(3, new DecimalFormat("0.000"));
        FormatUtil.formatKeys.put(4, new DecimalFormat("0.0000"));
        FormatUtil.formatStrs.put(0, "#");
        FormatUtil.formatStrs.put(1, "0.0");
        FormatUtil.formatStrs.put(2, "0.00");
        FormatUtil.formatStrs.put(3, "0.000");
        FormatUtil.formatStrs.put(4, "0.0000");
    }

    public static String getFormatStrByDecimalLen(int decimal_len) {
        decimal_len = decimal_len < 0 ? 0 : decimal_len;
        String str = formatStrs.get(decimal_len);
        if (str == null) {
            str = "0.";
            for (int k = 0; k < decimal_len; k++) {
                str = str + "0";
            }
            formatStrs.put(decimal_len, str);
        }
        return str;
    }

    public static DecimalFormat getFormatByDecimalLen(int decimal_len) {
        DecimalFormat df = formatKeys.get(decimal_len);
        if (df == null) {
            String format_str = "0.";
            if (decimal_len <= 0) {
                df = new DecimalFormat("#");
            } else {
                for (int k = 0; k < decimal_len; k++) {
                    format_str = format_str + "0";
                }
                df = new DecimalFormat(format_str);
            }
            formatKeys.put(decimal_len, df);
        }
        return df;
    }
}
