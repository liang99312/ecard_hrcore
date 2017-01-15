/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.mutil;

import java.util.ArrayList;
import java.util.List;
import org.jhrcore.client.CommUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.base.EntityDef;
import org.jhrcore.rebuild.EntityBuilder;

public class SelfApplyUtil {
    
    public static List<EntityDef> getSelfApplyEntitys(){
        ArrayList<EntityDef> list = new ArrayList<EntityDef>();
        String hql = "from EntityDef ed join fetch ed.entityClass where ed.entityClass.entityType_code = 'SELFAPPLYFB' "
                + " order by ed.order_no  ";
        list = (ArrayList<EntityDef>) CommUtil.fetchEntities(hql);
        
        return list;
    }
    
    public static boolean hasSubmitRight(EntityDef entityDef) {
        if(entityDef == null) return false;
        
        if (!UserContext.isSA) {
            String entityAllName = EntityBuilder.getHt_entity_classes().get(entityDef.getEntityName());
            String sql = "select 1 from WorkFlowA01 wfa where wfa.wf_state ='¿ªÊ¼' "
                    + "and wfa.workFlowDef_key in(select wfd.workFlowDef_key from WorkFlowDef wfd "
                    + " where wfd.workFlowClass.workFlowClass_key='" + entityAllName + "') "
                    + "and wfa.a01PassWord.a01.a01_key ='" + UserContext.person_key + "'";
            return CommUtil.exists(sql);
        }
        return true;
    }
}
