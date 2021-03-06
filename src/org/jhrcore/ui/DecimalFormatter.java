/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.ui;

import java.text.DecimalFormat;
import java.text.ParseException;
import javax.swing.JFormattedTextField.AbstractFormatter;
import org.jhrcore.util.SysUtil;

/**
 *
 * @author mxliteboss
 */
public class DecimalFormatter extends AbstractFormatter {
    //private String format_text;
    private DecimalFormat df;
    public DecimalFormatter(String format_text){
        super();
        //this.format_text = format_text;
        if (format_text!=null&&!format_text.trim().equals("")) {
            df = new DecimalFormat(format_text);
        }
    }
    @Override
    public Object stringToValue(String text) throws ParseException {
        return SysUtil.objToBigDecimal(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {   
        if(value == null)
            return "";
        return df == null ? (SysUtil.objToDouble(value) == 0 ? "":value.toString()): df.format(value);
    }

}