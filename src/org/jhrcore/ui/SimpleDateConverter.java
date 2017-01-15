/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author Administrator
 */
public class SimpleDateConverter extends Converter {

    private SimpleDateFormat dateFormat;
    private Binding binding;

    public SimpleDateConverter(SimpleDateFormat dateFormat, Binding binding) {
        this.dateFormat = dateFormat;
        this.binding = binding;
    }

    public SimpleDateConverter(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Object convertForward(Object arg) {
        return dateFormat.format((Date) arg);
    }

    public Object convertReverse(Object arg) {
        if (binding != null) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    binding.refresh();
                }
            });
        }
        if (arg == null || arg.toString().equals("")) {
            return new Date();
        }
        try {
            if (arg.toString().length() == 6) {
                return new SimpleDateFormat("yyMMdd").parse(arg.toString());
            } else if (arg.toString().length() == 8) {
                return new SimpleDateFormat("yyyyMMdd").parse(arg.toString());
            } else if (arg.toString().length() == 10 && arg.toString().contains("-")) {
                return new SimpleDateFormat("yyyy-MM-dd").parse(arg.toString());
            } else if (arg.toString().length() == 11) {
                return new SimpleDateFormat("yyyyƒÍMM‘¬dd»’").parse(arg.toString());
            }
        } catch (ParseException ex) {
            Logger.getLogger(SimpleDateConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            return dateFormat.parse(arg.toString());
        } catch (ParseException ex) {
            Logger.getLogger(SimpleDateConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Date();
    }
}
