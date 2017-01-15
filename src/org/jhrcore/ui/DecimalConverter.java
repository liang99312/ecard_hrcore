/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.ui;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author wangzhenhua
 */
public class DecimalConverter extends Converter {
    private DecimalFormat df;
    
    public DecimalConverter(String format){
        df = new DecimalFormat(format);
    }
    
    public Object convertForward(Object arg) {
        return df.format(arg);
    }

    @Override
    public Object convertReverse(Object arg) {
        try {
            return df.parse(arg.toString());
        } catch (ParseException ex) {
            Logger.getLogger(DecimalConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}