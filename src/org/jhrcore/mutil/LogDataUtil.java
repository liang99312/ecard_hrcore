/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.mutil;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.SysParameter;
import org.jhrcore.util.SysUtil;

/**
 *
 * @author mxliteboss
 */
public class LogDataUtil {

    public static String code = "SysLogData";

    public static Hashtable<String,Object> getLogFields() {
        Hashtable ht = new Hashtable();
        List paras = CommUtil.fetchEntities("from SysParameter sp where sp.sysParameter_key like '" + code + "%'");
        for (Object obj : paras) {
            SysParameter sp = (SysParameter) obj;
            String key = sp.getSysParameter_key().replace(code + ".", "");
            String value = SysUtil.objToStr(sp.getSysparameter_value());
            if (key.startsWith("field")) {
                String[] values = value.split(";");
                ht.put(key, Arrays.asList(values));
            } else {
                ht.put(key, value);
            }
        }
        return ht;
    }
}
