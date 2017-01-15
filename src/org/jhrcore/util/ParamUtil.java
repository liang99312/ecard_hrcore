/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.SysParameter;

/**
 *
 * @author mxliteboss
 */
public class ParamUtil {

    public static SysParameter getPara(String[] para) {
        if (para == null || para.length < 3) {
            return new SysParameter();
        }
        return getPara(para[0], para[1], para[2]);
    }

    public static SysParameter getPara(String code, String name, String value) {
        SysParameter sp = (SysParameter) CommUtil.fetchEntityBy("from SysParameter where sysParameter_key='" + code + "'");
        if (sp == null) {
            sp = ParamUtil.createCommPara(code, name, value);
        }
        return sp;
    }

    public static SysParameter createCommPara(String key, String name, String value) {
        SysParameter sp = new SysParameter();
        sp.setSysParameter_key(key);
        sp.setSysparameter_code(key);
        sp.setSysparameter_name(name);
        sp.setSysparameter_value(value);
        CommUtil.saveOrUpdate(sp);
        return sp;
    }

    public static List<String> getSplitFields(String[] para) {
        return getSplitFields(getPara(para));
    }

    public static List<String> getSplitFields(SysParameter sp) {
        String value = SysUtil.objToStr(sp == null ? "" : sp.getSysparameter_value());
        if (value.equals("")) {
            return new ArrayList();
        }
        return Arrays.asList(value.split(";"));
    }
}
