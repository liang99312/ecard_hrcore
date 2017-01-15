/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.mutil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jhrcore.client.CommUtil;

/**
 *
 * @author Administrator
 */
public class GbUtil {
    public static String getNewCode(String parent_code){
        String y_str = "00";
        List code_list = CommUtil.selectSQL("select max(gb_code) from Gb_type where gb_code like '" + parent_code +"%' and gb_code != '" + parent_code +"'");
        if(code_list.isEmpty() || code_list == null || code_list.get(0) == null){
            return parent_code + "001";
        }else{
            String mx_code = code_list.get(0).toString();
            int p_len = parent_code.length();
            mx_code = mx_code.substring(p_len);
            int n = Integer.valueOf(mx_code);
            n = n+1;
            String re = y_str + String.valueOf(n);
            re = re.substring(re.length()-3, re.length());
            return parent_code + re;
        }
    }
    public static Object getFieldObject(Object obj, String field_name,Class aclass) {
        Object object = null;
        try {
            Method method = aclass.getMethod("get" + field_name.substring(0, 1).toUpperCase() + field_name.substring(1), new Class[]{});
            object = method.invoke(obj, new Object[]{});
        } catch (InvocationTargetException ex) {
            Logger.getLogger(GbUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (SecurityException e1) {
            e1.printStackTrace();
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        }
        return object;
    }
}
