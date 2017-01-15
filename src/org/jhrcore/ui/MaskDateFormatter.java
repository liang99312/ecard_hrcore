/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author mxliteboss
 */
public class MaskDateFormatter extends MaskFormatter {

    private String replace_key;
    private String format = "";
    private SimpleDateFormat date_format;

    public static MaskDateFormatter getFormatter(String format) {
        try {
            return new MaskDateFormatter(getDateFormatStr(format), format);
        } catch (Exception ex) {
        }
        return null;
    }

    public MaskDateFormatter(String mask, String format) throws ParseException {
        super(mask);
        this.replace_key = mask;
        this.format = format;
        this.date_format = new SimpleDateFormat(format);
        date_format.setLenient(false);
        date_format.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        this.setPlaceholderCharacter('_');
    }

    private static String getDateFormatStr(String format) {
        String result = format.replace("y", "#");
        result = result.replace("m", "#");
        result = result.replace("M", "#");
        result = result.replace("d", "#");
        result = result.replace("H", "#");
        result = result.replace("s", "#");
        result = result.replace("S", "#");
        result = result.replace("h", "#");
        return result;
    }

    public String valueToStr(Object value) {
        String val = "";
        if (value == null) {
            val = replace_key.replace("#", "_");
        } else {
            try {
                val = date_format.format(value);
            } catch (Exception e) {
                val = replace_key.replace("#", "_");
            }
        }
        return val;
    }

    public SimpleDateFormat getDate_format() {
        return date_format;
    }

    public void setDate_format(SimpleDateFormat date_format) {
        this.date_format = date_format;
        this.date_format.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getReplace_key() {
        return replace_key;
    }

    public void setReplace_key(String replace_key) {
        this.replace_key = replace_key;
    }
}