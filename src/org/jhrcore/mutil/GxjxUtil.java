/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.mutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import org.jhrcore.client.CommUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.util.SysUtil;

/**
 *
 * @author Administrator
 */
public class GxjxUtil {
    public static List<String> getAppendixTables(){
        List<String> resultList = new ArrayList<String>();
        List list = CommUtil.selectSQL("select entityname from tabname where"
                + " entityclass_key in(select entityclass_key from entityclass where entitytype_code='ANNEX')");
        resultList.addAll(list);
        return resultList;
    }
    
    public static List<String> getTwpyAppTables(){
        List<String> resultList = new ArrayList<String>();
        List list = CommUtil.selectSQL("select entityname from tabname where"
                + " entityclass_key in(select entityclass_key from entityclass where entitytype_code like'GXJX%' and modify_flag=1)");
        resultList.addAll(list);
        return resultList;
    }
    public static Hashtable<String,String> getTabKeys(){
        List list = CommUtil.selectSQL("select n.entityname,t.preEntityName from tabname n,entityclass t where"
                + " n.entityclass_key=t.entityclass_key and t.entitytype_code like'GXJX%' and t.modify_flag=1");
        Hashtable<String,String> hash_keys = new Hashtable<String,String>();
        for(Object obje:list){
            Object[] obj = (Object[])obje;
            hash_keys.put(SysUtil.objToStr(obj[0]), SysUtil.objToStr(obj[1]));
        }
        return hash_keys;
    }
    public static boolean hasSubmitRight() {
        if (!UserContext.isSA) {
            String sql = "select 1 from WorkFlowA01 wfa where wfa.wf_state ='¿ªÊ¼' "
                    + "and wfa.workFlowDef_key in(select wfd.workFlowDef_key from WorkFlowDef wfd where wfd.workFlowClass.workFlowClass_key='org.jhrcore.entity.gxjx.Gxjx_a01') "
                    + "and wfa.a01PassWord.a01.a01_key ='" + UserContext.person_key + "'";
            return CommUtil.exists(sql);
        }
        return true;
    }
    public static List getPlans(String state){
        String hql = "from Gxjx_plan where p_state='"+state+"'";
        List list = CommUtil.fetchEntities(hql);
        return list;
    }
}
