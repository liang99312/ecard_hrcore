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
        //�ֶ�ͼ��(����)
        icons.put("fieldI", ImageUtil.getIcon("fieldDefIconI.png"));
        //�ֶ�ͼ��(�ַ���)
        icons.put("fieldS", ImageUtil.getIcon("fieldDefIconS.png"));
        //�ֶ�ͼ��(������)
        icons.put("fieldN", ImageUtil.getIcon("fieldDefIconN.png"));
        //�ֶ�ͼ��(������)
        icons.put("fieldD", ImageUtil.getIcon("fieldDefIconD.png"));
        //�ֶ�ͼ��(������)
        icons.put("fieldB", ImageUtil.getIcon("fieldDefIconB.png"));
        //�ֶ�ͼ��(�޸�״̬)
        icons.put("fieldNew", ImageUtil.getIcon("new.png"));
        //��ͼ��
        icons.put("entity", ImageUtil.getIcon("entityDefIcon.png"));
        //ҵ��ͼ��
        icons.put("entityClass",ImageUtil.getIcon("entityClassIcon.png"));
        //Ҷ�ӽڵ�ͼ��
        icons.put("leaf", ImageUtil.getIcon("lastDeptIcon.png"));
        //���ڵ�ͼ��
        icons.put("root", ImageUtil.getIcon("independDeptIcon.png"));
        //��Ҷ�ӡ����ڵ�ͼ��
        icons.put("node", ImageUtil.getIcon("childDeptIcon.png"));
        //��Ҷ�ӡ����ڵ�չ��ͼ��
        icons.put("nodeopen", ImageUtil.getIcon("openDeptIcon.png"));
        // ���ⲿ��ͼ��
        icons.put("virtualDept", ImageUtil.getIcon("virtualDeptIcon.png"));
        // �߼�ɾ������ͼ��
        icons.put("delDept", ImageUtil.getIcon("delDeptIcon.png"));
        //����ͼ��
        icons.put("code", ImageUtil.getIcon("codeIcon.png"));
        //��λͼ��
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
