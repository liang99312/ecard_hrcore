/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import java.util.Hashtable;
import javax.swing.Icon;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.change.ChangeField;
import org.jhrcore.entity.change.ChangeItem;

/**
 *
 * @author hflj
 */
public class RenderderUtil {

    private final static Hashtable<String, Icon> icons = new Hashtable<String, Icon>();
    private final static Hashtable<String, String> classTags = new Hashtable<String, String>();

    static {
        //字段图标(整形)
        icons.put("fieldI", ImageUtil.getIcon("fieldDefIconI.png"));
        //字段图标(字符型)
        icons.put("fieldS", ImageUtil.getIcon("fieldDefIconS.png"));
        //字段图标(数字型)
        icons.put("fieldN", ImageUtil.getIcon("fieldDefIconN.png"));
        //字段图标(日期型)
        icons.put("fieldD", ImageUtil.getIcon("fieldDefIconD.png"));
        //字段图标(布尔型)
        icons.put("fieldB", ImageUtil.getIcon("fieldDefIconB.png"));
        //字段图标(修改状态)
        icons.put("fieldNew", ImageUtil.getIcon("new.png"));
        //表图标
        icons.put("entity", ImageUtil.getIcon("entityDefIcon.png"));
        //业务图标
        icons.put("entityClass",ImageUtil.getIcon("entityClassIcon.png"));
        //叶子节点图标
        icons.put("leaf", ImageUtil.getIcon("lastDeptIcon.png"));
        //根节点图标
        icons.put("root", ImageUtil.getIcon("independDeptIcon.png"));
        //非叶子、根节点图标
        icons.put("node", ImageUtil.getIcon("childDeptIcon.png"));
        //非叶子、根节点展开图标
        icons.put("nodeopen", ImageUtil.getIcon("openDeptIcon.png"));
        // 虚拟部门图标
        icons.put("virtualDept", ImageUtil.getIcon("virtualDeptIcon.png"));
        // 逻辑删除部门图标
        icons.put("delDept", ImageUtil.getIcon("delDeptIcon.png"));
        //编码图标
        icons.put("code", ImageUtil.getIcon("codeIcon.png"));
        //岗位图标
        icons.put("g10", ImageUtil.getIcon("man.png"));
        classTags.put("org.jhrcore.entity.showstyle.ShowFieldGroup", "entity");
        classTags.put("java.lang.String", "entity");
        classTags.put("org.jhrcore.entity.salary.PayAppDef", "fieldN");
        classTags.put("org.jhrcore.entity.salary.PayComField", "fieldN");
        classTags.put("org.jhrcore.entity.kaoqin.K_class", "fieldN");
        classTags.put("org.jhrcore.entity.kaoqin.K_leave_type", "fieldN");
        classTags.put("org.jhrcore.entity.kaoqin.K_set", "entityClass");
        classTags.put("org.jhrcore.entity.kaoqin.K_shift_set", "entityClass");
        classTags.put("org.jhrcore.entity.kaoqin.K_unit", "node");
        classTags.put("org.jhrcore.entity.EntityDef", "entity");
        classTags.put("org.jhrcore.entity.right.Role", "code");
        classTags.put("org.jhrcore.entity.salary.PaySystem", "code");
        classTags.put("org.jhrcore.entity.Code", "code");
        classTags.put("org.jhrcore.entity.report.ReportDef", "fieldN");
        classTags.put("org.jhrcore.entity.SysGroup", "root");
        classTags.put("org.jhrcore.entity.jixiao.J_scheme", "leaf");
        classTags.put("org.jhrcore.entity.jixiao.J_objtype", "node");
        classTags.put("org.jhrcore.entity.ldde.D_gxtype", "node");
        classTags.put("org.jhrcore.entity.ldde.D_codetype", "node");
        classTags.put("org.jhrcore.entity.ModuleInfo", "node");

    }

    public static String getIconTag(Object obj) {
        if (obj instanceof TempFieldInfo) {
            TempFieldInfo fieldDef = (TempFieldInfo) obj;
            return getTagByType(fieldDef.getField_type());
        } else if (obj instanceof ChangeField) {
            ChangeField fieldDef = (ChangeField) obj;
            return getTagByType(fieldDef.getField_type());
        } else if (obj instanceof ChangeItem) {
            ChangeItem fieldDef = (ChangeItem) obj;
            return getTagByType(fieldDef.getField_type());
        } else if (obj instanceof DeptCode) {
            DeptCode dept = (DeptCode) obj;
            if (dept.getParent_code().equals("ROOT")) {
                return "root";
            } else if (dept.isDel_flag()) {
                return "delDept";
            } else if (dept.isVirtual()) {
                return "virtualDept";
            } else if (dept.isEnd_flag()) {
                return "leaf";
            } else {
                return "node";
            }
        } else {
            return classTags.get(obj.getClass().getName());
        }
    }

    private static String getTagByType(String field_type) {
        field_type = field_type == null ? "" : field_type.toLowerCase();
        if (field_type.equals("integer") || field_type.equals("int")) {
            return "fieldI";
        } else if (field_type.equals("date")) {
            return "fieldD";
        } else if (field_type.equals("boolean")) {
            return "fieldB";
        } else if (field_type.equals("float") || field_type.equals("bigdecimal") || field_type.equals("double")) {
            return "fieldN";
        } else {
            return "fieldS";
        }
    }

    public static Icon getIcon(Object obj) {
        String tag = getIconTag(obj);
        if (tag == null) {
            return null;
        }
        return icons.get(tag);
    }

    public static Icon getIconByTag(String tag) {
        if (tag == null) {
            return null;
        }
        Icon icon = icons.get(tag);
        if (icon == null) {
            icon = ImageUtil.getIcon(tag);
        }
        return icon;
    }
}
