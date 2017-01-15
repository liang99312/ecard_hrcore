/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.sanalyse;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.A01;
import org.jhrcore.entity.base.EntityDef;
import org.jhrcore.ui.ModelFrame;

/**
 *
 * @author Administrator
 */
public class SAnalyseUtil {
    public static SAnalyseScheme a01AnalyseScheme;
    public static SAnalyseScheme c21AnalyseScheme;
    
    public static void createAnalyse(JButton c, String module_code,String title){
        SAnalyseScheme analyseScheme = getAnalyseScheme(module_code);
        analyseScheme.getAnalyseFields().clear();
        analyseScheme.getOrderFields().clear();
        analyseScheme.setQueryScheme(null);
        analyseScheme.setDept_flag(false);
        analyseScheme.setQuerySchemeDept(null);
        if(analyseScheme == null){
            return;
        }
        SAnalysePanel pnl = new SAnalysePanel(getAnalyseScheme(module_code));
        ModelFrame.showModel((JFrame) JOptionPane.getFrameForComponent(c), pnl, true, title);
    }
    
    public static SAnalyseScheme getAnalyseScheme(String module_code){
        if ("EmpAnalyse".equals(module_code)) {
            if (a01AnalyseScheme == null) {
                a01AnalyseScheme = getAnalyseScheme_a01();
            }
            return a01AnalyseScheme;
        }else{
            return null;
        }
    }
    
    public static SAnalyseScheme getAnalyseScheme_a01(){
        SAnalyseScheme analyseScheme = new SAnalyseScheme();
        analyseScheme.setMain_class(A01.class);
        List<EntityDef> list = new ArrayList<EntityDef>();
        List edList = CommUtil.fetchEntities("from EntityDef ed join fetch ed.entityClass ec where (ec.entityType_code in('0','ANNEX') or ed.entityName='DeptCode') order by ec.order_no,ed.order_no");
        for(Object obj:edList){
            EntityDef ed = (EntityDef) obj;
            ed.setPackageName("org.jhrcore.entity");
            if ("DeptCode".equalsIgnoreCase(ed.getEntityName())) {
                analyseScheme.getWhereMap().put(ed.getEntityName(), "A01.deptCode_key=" + ed.getEntityName() + ".deptCode_key");
            } else {
                if (!"a01".equalsIgnoreCase(ed.getEntityName())) {
                    analyseScheme.getWhereMap().put(ed.getEntityName(), "A01.a01_key=" + ed.getEntityName() + ".a01_key");
                }
            }
            list.add(ed);
        }
        analyseScheme.setEntityDefs(list);
        return analyseScheme;
    }
    
}
