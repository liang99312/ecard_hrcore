/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.renderer;

import javax.swing.JTree;
import org.jhrcore.entity.base.EntityDef;
import org.jhrcore.entity.change.ChangeScheme;

/**
 *
 * @author mxliteboss
 */
public class HRRendererView {

    public static RenderderMap rm = new RenderderMap();
    //调配方案渲染策略

    public static RenderderMap getChangeSchemeMap() {
        rm.setIcon(ChangeScheme.class, "entityDefIcon.png");
        rm.setIcon(String.class, "entityClassIcon.png");
        return rm;
    }
    
    public static RenderderMap getRoleMap() {
        rm.setIcon("Role", "code");
        rm.setIcon("ROOT", "entityClassIcon.png");
        return rm;
    }

    public static RenderderMap getZyPlanMap() {
        rm.setIcon("Zy_plan", "independDeptIcon.png");
        rm.setIcon("String", "entityClassIcon.png");
        return rm;
    }

    public static RenderderMap getReportMap() {
        rm.setIcon("ReportDef", "fieldN");
        rm.setIcon("RoleRightTemp", "node");
        rm.setIcon("String", "entityClass");
        rm.setIcon("ModuleInfo", "entityClass");
        return rm;
    }
    
    public static RenderderMap getCommMap_file() {
        rm.setIcon("ROOT", "entityClassIcon.png");
        return rm;
    }

    public static RenderderMap getCommMap() {
        rm.setIcon(String.class, "entityClassIcon.png");
        rm.setIcon("ROOT", "entityClassIcon.png");
        return rm;
    }

    public static RenderderMap getPayCompareMap() {
        rm.setIcon("ROOT", "independDeptIcon.png");
        rm.setIcon(EntityDef.class, "node");
        return rm;
    }

    public static RenderderMap getFunMap(JTree tree) {
        rm.setIcon(tree, "lvl|0", "moduleInfoIcon.png");
        rm.setIcon(tree, "lvl|1", "entityClassIcon.png");
        rm.setIcon(tree, "class|AutoExcuteScheme", "fieldDefIconN.png");
        rm.setIcon(tree, "", "entityDefIcon.png");
        return rm;
    }

    public static RenderderMap getFunRightMap(JTree tree) {
        rm.setIcon(tree, "fun_flag|0;lvl|0", "refuse_right.png;moduleInfoIcon.png");
        rm.setIcon(tree, "fun_flag|1;lvl|0", "give_right.png;moduleInfoIcon.png");
        rm.setIcon(tree, "fun_flag|2;lvl|0", "view_right.png;moduleInfoIcon.png");
        rm.setIcon(tree, "fun_flag|0;lvl|1", "refuse_right.png;entityClassIcon.png");
        rm.setIcon(tree, "fun_flag|1;lvl|1", "give_right.png;entityClassIcon.png");
        rm.setIcon(tree, "fun_flag|2;lvl|1", "view_right.png;entityClassIcon.png");
        rm.setIcon(tree, "fun_flag|0", "refuse_right.png;entityDefIcon.png");
        rm.setIcon(tree, "fun_flag|1", "give_right.png;entityDefIcon.png");
        rm.setIcon(tree, "fun_flag|2", "view_right.png;entityDefIcon.png");
        return rm;
    }

    public static RenderderMap getFieldRightMap(JTree tree) {
        rm.setIcon(tree, "fun_flag|0;lvl|0", "refuse_right.png;moduleInfoIcon.png");
        rm.setIcon(tree, "fun_flag|1;lvl|0", "give_right.png;moduleInfoIcon.png");
        rm.setIcon(tree, "fun_flag|2;lvl|0", "view_right.png;moduleInfoIcon.png");

        rm.setIcon(tree, "fun_flag|0;lvl|1", "refuse_right.png;entityClassIcon.png");
        rm.setIcon(tree, "fun_flag|1;lvl|1", "give_right.png;entityClassIcon.png");
        rm.setIcon(tree, "fun_flag|2;lvl|1", "view_right.png;entityClassIcon.png");

        rm.setIcon(tree, "fun_flag|0;lvl|2", "refuse_right.png;entityDefIcon.png");
        rm.setIcon(tree, "fun_flag|1;lvl|2", "give_right.png;entityDefIcon.png");
        rm.setIcon(tree, "fun_flag|2;lvl|2", "view_right.png;entityDefIcon.png");

        rm.setIcon(tree, "fun_flag|0;lvl|3", "refuse_right.png;fieldDefGroupIcon.png");
        rm.setIcon(tree, "fun_flag|1;lvl|3", "give_right.png;fieldDefGroupIcon.png");
        rm.setIcon(tree, "fun_flag|2;lvl|3", "view_right.png;fieldDefGroupIcon.png");
        return rm;
    }

    public static RenderderMap getReportRightMap(JTree tree) {
        rm.setIcon(tree, "fun_flag|0;lvl|0", "refuse_right.png;moduleInfoIcon.png");
        rm.setIcon(tree, "fun_flag|1;lvl|0", "give_right.png;moduleInfoIcon.png");
        rm.setIcon(tree, "fun_flag|2;lvl|0", "view_right.png;moduleInfoIcon.png");

        rm.setIcon(tree, "fun_flag|0;lvl|1", "refuse_right.png;entityClassIcon.png");
        rm.setIcon(tree, "fun_flag|1;lvl|1", "give_right.png;entityClassIcon.png");
        rm.setIcon(tree, "fun_flag|2;lvl|1", "view_right.png;entityClassIcon.png");

        rm.setIcon(tree, "fun_flag|0;class|ReportDef", "refuse_right.png;report.png");
        rm.setIcon(tree, "fun_flag|1;class|ReportDef", "give_right.png;report.png");
        rm.setIcon(tree, "fun_flag|2;class|ReportDef", "view_right.png;report.png");

        rm.setIcon(tree, "fun_flag|0", "refuse_right.png;entityDefIcon.png");
        rm.setIcon(tree, "fun_flag|1", "give_right.png;entityDefIcon.png");
        rm.setIcon(tree, "fun_flag|2", "view_right.png;entityDefIcon.png");
        return rm;
    }

    public static RenderderMap getReportMap(JTree tree) {
        rm.setIcon(tree, "lvl|0", "moduleInfoIcon.png");
        rm.setIcon(tree, "class|ReportDef", "report.png");
        rm.setIcon(tree, "class|WorkFlowDef", "report.png");
        rm.setIcon(tree, "class|WorkFlowClass", "entityClassIcon.png");
        rm.setIcon(tree, "lvl|1", "entityClassIcon.png");
        rm.setIcon(tree, "", "entityDefIcon.png");
        return rm;
    }

    public static RenderderMap getDeptRightMap(JTree tree) {
        rm.clearTag(tree);
        rm.setIcon(tree, "lvl|0", "independDeptIcon.png");
        rm.setIcon(tree, "fun_flag|0;lvl|1", "refuse_right.png;independDeptIcon.png");
        rm.setIcon(tree, "fun_flag|1;lvl|1", "give_right.png;independDeptIcon.png");
        rm.setIcon(tree, "fun_flag|2;lvl|1", "view_right.png;independDeptIcon.png");

        rm.setIcon(tree, "fun_flag|0;virtual|true", "refuse_right.png;virtualDept");
        rm.setIcon(tree, "fun_flag|1;virtual|true", "give_right.png;virtualDept");
        rm.setIcon(tree, "fun_flag|2;virtual|true", "view_right.png;virtualDept");

        rm.setIcon(tree, "fun_flag|0;end_flag|true", "refuse_right.png;leaf");
        rm.setIcon(tree, "fun_flag|1;end_flag|true", "give_right.png;leaf");
        rm.setIcon(tree, "fun_flag|2;end_flag|true", "view_right.png;leaf");

        rm.setIcon(tree, "fun_flag|0", "refuse_right.png;node");
        rm.setIcon(tree, "fun_flag|1", "give_right.png;node");
        rm.setIcon(tree, "fun_flag|2", "view_right.png;node");
        return rm;
    }

    public static RenderderMap getDeptMap(JTree tree) {
        rm.setIcon(tree, "lvl|0", "independDeptIcon.png");
        rm.setIcon(tree, "class|DeptCode;parent_code|ROOT", "independDeptIcon.png");
        rm.setIcon(tree, "class|DeptCode;del_flag|true", "delDeptIcon.png");
        rm.setIcon(tree, "class|DeptCode;virtual|true", "virtualDept");
        rm.setIcon(tree, "class|DeptCode;end_flag|true", "leaf");
        rm.setIcon(tree, "class|PayDeptBack;parent_code|ROOT", "independDeptIcon.png");
        rm.setIcon(tree, "class|PayDeptBack;del_flag|true", "delDeptIcon.png");
        rm.setIcon(tree, "class|PayDeptBack;virtual|true", "virtualDept");
        rm.setIcon(tree, "class|PayDeptBack;end_flag|true", "leaf");
        rm.setIcon(tree, "class|G10", "man.png");
        rm.setIcon(tree, "", "node");
        return rm;
    }
    
    public static RenderderMap getEcardMap(JTree tree) {
        rm.setIcon(tree, "lvl|0", "independDeptIcon.png");
        rm.setIcon(tree, "class|Ecard", "card.png");
        rm.setIcon(tree, "", "node");
        return rm;
    }

    public static RenderderMap getRebuildMap(JTree tree) {
        rm.setIcon(tree, "class|String", "fieldDefGroupIcon.png");
        rm.setIcon(tree, "class|ModuleInfo", "moduleInfoIcon.png");
        rm.setIcon(tree, "class|EntityClass", "entityClassIcon.png");
        rm.setIcon(tree, "class|TempGroup;change_flag|0", "fieldDefGroupIcon.png");
        rm.setIcon(tree, "class|TempGroup", "entityChangeIcon.png;fieldDefGroupIcon.png");
        rm.setIcon(tree, "class|EntityDef;fun_flag|1", "new.png;entityDefIcon.png");
        rm.setIcon(tree, "class|EntityDef;fun_flag|2", "entityChangeIcon.png;entityDefIcon.png");
        rm.setIcon(tree, "class|EntityDef;fun_flag|3", "del1.png;entityDefIcon.png");
        rm.setIcon(tree, "class|EntityDef", "entityDefIcon.png");
        rm.setIcon(tree, "class|FieldDef;fun_flag|1", "new.png");
        rm.setIcon(tree, "class|FieldDef;fun_flag|2", "entityChangeIcon.png");
        rm.setIcon(tree, "class|FieldDef;fun_flag|3", "del1.png");
        rm.setIcon(tree, "class|FieldDef;field_type|Integer", "fieldDefIconI.png");
        rm.setIcon(tree, "class|FieldDef;field_type|int", "fieldDefIconI.png");
        rm.setIcon(tree, "class|FieldDef;field_type|String", "fieldDefIconS.png");
        rm.setIcon(tree, "class|FieldDef;field_type|Code", "fieldDefIconS.png");
        rm.setIcon(tree, "class|FieldDef;field_type|Float", "fieldDefIconN.png");
        rm.setIcon(tree, "class|FieldDef;field_type|float", "fieldDefIconN.png");
        rm.setIcon(tree, "class|FieldDef;field_type|BigDecimal", "fieldDefIconN.png");
        rm.setIcon(tree, "class|FieldDef;field_type|Double", "fieldDefIconN.png");
        rm.setIcon(tree, "class|FieldDef;field_type|Date", "fieldDefIconD.png");
        rm.setIcon(tree, "class|FieldDef;field_type|Boolean", "fieldDefIconB.png");
        rm.setIcon(tree, "class|Code;parent_id|ROOT", "independDeptIcon.png");
        rm.setIcon(tree, "class|Code;", "codeIcon.png");
        return rm;
    }

    public static RenderderMap getPayDefMap(JTree tree) {
        rm.setIcon(tree, "class|EntityDef", "entityDefIcon.png");
        rm.setIcon(tree, "class|String", "entityClassIcon.png");
        rm.setIcon(tree, "class|PayDef;field_type|Integer", "fieldDefIconI.png");
        rm.setIcon(tree, "class|PayDef;field_type|int", "fieldDefIconI.png");
        rm.setIcon(tree, "class|PayDef;field_type|String", "fieldDefIconS.png");
        rm.setIcon(tree, "class|PayDef;field_type|Code", "fieldDefIconS.png");
        rm.setIcon(tree, "class|PayDef;field_type|Float", "fieldDefIconN.png");
        rm.setIcon(tree, "class|PayDef;field_type|float", "fieldDefIconN.png");
        rm.setIcon(tree, "class|PayDef;field_type|BigDecimal", "fieldDefIconN.png");
        rm.setIcon(tree, "class|PayDef;field_type|Double", "fieldDefIconN.png");
        rm.setIcon(tree, "class|PayDef;field_type|Date", "fieldDefIconD.png");
        rm.setIcon(tree, "class|PayDef;field_type|Boolean", "fieldDefIconB.png");
        rm.setIcon(tree, "", "node");
        return rm;

    }

    public static RenderderMap getFieldTypeMap(JTree tree) {
        rm.setIcon(tree, "class|EntityDef", "entityDefIcon.png");
        rm.setIcon(tree, "class|String", "entityClassIcon.png");

        rm.setIcon(tree, "class|FieldDef;field_type|Integer", "fieldDefIconI.png");
        rm.setIcon(tree, "class|FieldDef;field_type|int", "fieldDefIconI.png");
        rm.setIcon(tree, "class|FieldDef;field_type|String", "fieldDefIconS.png");
        rm.setIcon(tree, "class|FieldDef;field_type|Code", "fieldDefIconS.png");
        rm.setIcon(tree, "class|FieldDef;field_type|Float", "fieldDefIconN.png");
        rm.setIcon(tree, "class|FieldDef;field_type|float", "fieldDefIconN.png");
        rm.setIcon(tree, "class|FieldDef;field_type|BigDecimal", "fieldDefIconN.png");
        rm.setIcon(tree, "class|FieldDef;field_type|Double", "fieldDefIconN.png");
        rm.setIcon(tree, "class|FieldDef;field_type|Date", "fieldDefIconD.png");
        rm.setIcon(tree, "class|FieldDef;field_type|Boolean", "fieldDefIconB.png");

        rm.setIcon(tree, "class|TempFieldInfo;field_type|Integer", "fieldDefIconI.png");
        rm.setIcon(tree, "class|TempFieldInfo;field_type|int", "fieldDefIconI.png");
        rm.setIcon(tree, "class|TempFieldInfo;field_type|String", "fieldDefIconS.png");
        rm.setIcon(tree, "class|TempFieldInfo;field_type|Code", "fieldDefIconS.png");
        rm.setIcon(tree, "class|TempFieldInfo;field_type|Float", "fieldDefIconN.png");
        rm.setIcon(tree, "class|TempFieldInfo;field_type|float", "fieldDefIconN.png");
        rm.setIcon(tree, "class|TempFieldInfo;field_type|BigDecimal", "fieldDefIconN.png");
        rm.setIcon(tree, "class|TempFieldInfo;field_type|Double", "fieldDefIconN.png");
        rm.setIcon(tree, "class|TempFieldInfo;field_type|Date", "fieldDefIconD.png");
        rm.setIcon(tree, "class|TempFieldInfo;field_type|Boolean", "fieldDefIconB.png");

        rm.setIcon(tree, "", "node");
        return rm;
    }

    public static RenderderMap getParaFieldTypeMap(JTree tree) {
        getFieldTypeMap(tree);
        rm.setIcon(tree, "para_field_type|Integer", "fieldDefIconI.png");
        rm.setIcon(tree, "para_field_type|int", "fieldDefIconI.png");
        rm.setIcon(tree, "para_field_type|String", "fieldDefIconS.png");
        rm.setIcon(tree, "para_field_type|Code", "fieldDefIconS.png");
        rm.setIcon(tree, "para_field_type|Float", "fieldDefIconN.png");
        rm.setIcon(tree, "para_field_type|float", "fieldDefIconN.png");
        rm.setIcon(tree, "para_field_type|BigDecimal", "fieldDefIconN.png");
        rm.setIcon(tree, "para_field_type|Double", "fieldDefIconN.png");
        rm.setIcon(tree, "para_field_type|Date", "fieldDefIconD.png");
        rm.setIcon(tree, "para_field_type|Boolean", "fieldDefIconB.png");
        rm.setIcon(tree, "", "node");
        return rm;
    }

    public static RenderderMap getZPPlanMap(JTree tree) {
        rm.setIcon(tree, "class|Zp_plan", "codeIcon.png");
        rm.setIcon(tree, "", "entityClassIcon.png");
        return rm;
    }

    public static RenderderMap getFileClassMap(JTree tree) {
        rm.setIcon(tree, "class|String", "independDeptIcon.png");
        rm.setIcon(tree, "class|EmpDocuClass;parent_code|ROOT", "independDeptIcon.png");
        rm.setIcon(tree, "class|DocuClass;parent_code|ROOT", "independDeptIcon.png");
        rm.setIcon(tree, "", "childDeptIcon.png");
        return rm;
    }

    public static RenderderMap getInSystemMap(JTree tree) {
        rm.setIcon(tree, "class|String", "entityClassIcon.png");
        rm.setIcon(tree, "", "codeIcon.png");
        return rm;
    }

    public static RenderderMap getRSZPMap(JTree tree) {
        rm.setIcon(tree, "class|Z_plan", "codeIcon.png");
        rm.setIcon(tree, "class|Z_channelType", "codeIcon.png");
        rm.setIcon(tree, "class|Z_mailType", "codeIcon.png");
        rm.setIcon(tree, "", "entityClassIcon.png");
        return rm;
    }

    public static RenderderMap getKqMap(JTree tree) {
        rm.setIcon(tree, "class|K_tclass", "entityClassIcon.png");
        rm.setIcon(tree, "class|K_class", "lastDeptIcon.png");
        rm.setIcon(tree, "class|K_unit", "node");
        rm.setIcon(tree, "class|String", "independDeptIcon.png");
        return rm;
    }

    public static RenderderMap getSaleMap(JTree tree) {
        rm.setIcon(tree, "class|PaySystem", "codeIcon.png");
        rm.setIcon(tree, "class|Bonus_item", "lastDeptIcon.png");
        rm.setIcon(tree, "class|Sales_item", "lastDeptIcon");
        rm.setIcon(tree, "class|Sales_store", "codeIcon");
        rm.setIcon(tree, "class|Sales_item_type;p_code|ROOT", "root");
        rm.setIcon(tree, "class|Sales_item_type", "node");
        rm.setIcon(tree, "class|String", "independDeptIcon.png");
        return rm;
    }

    public static RenderderMap getCycMap(JTree tree) {
        rm.setIcon(tree, "", "blue_round.png");
        return rm;
    }

    public static RenderderMap getJXSchemeMap(JTree tree) {
        rm.setIcon(tree, "lvl|0", "redhat-home.png");
        rm.setIcon(tree, "class|J_scheme", "leaf");
        rm.setIcon(tree, "class|J_plan", "leaf");
        rm.setIcon(tree, "", "node");
        return rm;
    }

    public static RenderderMap getJXCellMap(JTree tree) {
        rm.setIcon(tree, "class|SysGroup", "independDeptIcon.png");
        rm.setIcon(tree, "class|J_plan", "leaf");
        rm.setIcon(tree, "class|String", "entityDefIcon.png");
        return rm;
    }

    public static RenderderMap getQuerySchemeMap(JTree tree) {
        rm.setIcon(tree, "lvl|0", "independDeptIcon.png");
        rm.setIcon(tree, "class|String", "entityClassIcon.png");
        rm.setIcon(tree, "class|QueryAnalysisScheme", "codeIcon.png");
        rm.setIcon(tree, "class|EmpQueryAnalyseScheme", "codeIcon.png");
        rm.setIcon(tree, "class|PayQueryAnalyseScheme", "codeIcon.png");
        rm.setIcon(tree, "", "entityDefIcon.png");
        return rm;
    }

    public static RenderderMap getMsgPhoneMap() {
        rm.setIcon("String", "independDeptIcon.png");
        rm.setIcon("ROOT", "entityClassIcon.png");
        return rm;
    }

    public static RenderderMap getPersonMap(JTree tree) {
        rm.setIcon(tree, "lvl|0", "independDeptIcon.png");
        rm.setIcon(tree, "lvl|1", "user.png");
        return rm;
    }
//    public static RenderderMap getJXTaskMap(JTree tree) {
//        rm.setIcon(tree, "lvl|0", "root_task.png");
//        rm.setIcon(tree, "class|J_prjdept", "dept_task.png");
//        rm.setIcon(tree, "class|J_prja01", "a10_task.png");
//        return rm;
//    }
}
