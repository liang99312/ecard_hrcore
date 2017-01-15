/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.ui;

import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author Administrator
 */
public class BooleanConverter extends Converter {
    public Object convertForward(Object arg) {
        return (arg == null || !((Boolean)arg)) ? "·ñ" : "ÊÇ";
    }

    public Object convertReverse(Object arg) {
        return "ÊÇ".equals(arg)? true : false;        
    }
}
