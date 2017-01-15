/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.apache.log4j.Logger;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.showstyle.ShowScheme;
import org.jhrcore.entity.annotation.FieldAnnotation;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.showstyle.ShowSchemeDetail;
import org.jhrcore.entity.showstyle.ShowSchemeGroup;
import org.jhrcore.rebuild.EntityBuilder;

/**
 *
 * @author Administrator
 * 
 */
public class UIItemGroup {

    private static Logger log = Logger.getLogger(UIItemGroup.class.getName());
    private String groupName;
    private List<UIItemDetail> bindDetails = new ArrayList<UIItemDetail>();
    public static Hashtable<String, List<ShowSchemeGroup>> exist_groups = new Hashtable<String, List<ShowSchemeGroup>>();

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<UIItemDetail> getBindDetails() {
        return bindDetails;
    }

    public void setBindDetails(List<UIItemDetail> bindDetails) {
        this.bindDetails = bindDetails;
    }

    public static List<UIItemDetail> getBindDetailListOf(Class the_class) {
        List<Field> list_field = EntityBuilder.getDeclareFieldListOf(the_class, EntityBuilder.COMM_FIELD_VISIBLE);
        List<UIItemDetail> f_detail = new ArrayList<UIItemDetail>();
        for (Field field : list_field) {
            FieldAnnotation annotation = field.getAnnotation(FieldAnnotation.class);
            UIItemDetail bindDetail = new UIItemDetail();
            bindDetail.setField(field);
            bindDetail.setBindName(field.getName());
            bindDetail.setField_caption(annotation.displayName() == null ? "" : annotation.displayName());
            f_detail.add(bindDetail);
        }
        return f_detail;
    }

    public static UIItemDetail getBindDetailOf(Class the_class, String field_name) {
        UIItemDetail bindDetail = new UIItemDetail();
        try {
            Field field = the_class.getDeclaredField(field_name);
            FieldAnnotation annotation = field.getAnnotation(FieldAnnotation.class);
            bindDetail.setField(field);
            bindDetail.setBindName(field.getName());
            bindDetail.setField_caption(annotation.displayName() == null ? "" : annotation.displayName());
        } catch (NoSuchFieldException ex) {
            log.error(ex);
        } catch (SecurityException ex) {
            log.error(ex);
        }
        return bindDetail;
    }

    public static List<UIItemGroup> getBindGroupListOf(Class the_class, ShowScheme show_scheme, List<String> fields) {
        List<UIItemDetail> fgdetails = getUIItemDetails(the_class, fields);
        List<UIItemGroup> fg_list = new ArrayList<UIItemGroup>();
        Hashtable<String, UIItemGroup> groupKeys = new Hashtable();
        if (show_scheme == null || show_scheme.getShowSchemeDetails().isEmpty()) {
            for (UIItemDetail uid : fgdetails) {
                UIItemGroup group = getGroupByName(groupKeys, "未分组", fg_list);
                group.getBindDetails().add(uid);
            }
            return fg_list;
        }
        Hashtable<String, UIItemDetail> detailKeys = new Hashtable();
        for (UIItemDetail uid : fgdetails) {
            detailKeys.put(SysUtil.tranField(uid.getBindName()), uid);
        }
        List<ShowSchemeDetail> details = new ArrayList<ShowSchemeDetail>();
        details.addAll(show_scheme.getShowSchemeDetails());
        SysUtil.sortListByInteger(details, "order_no");
        Hashtable<String, Integer> orders = new Hashtable();
        for (ShowSchemeDetail ssd : details) {
            orders.put(ssd.getEntity_name() + "." + ssd.getField_name(), ssd.getOrder_no());
        }
        if (show_scheme.isGroup_flag()) {
            List<ShowSchemeGroup> groups = exist_groups.get(show_scheme.getEntity_name());
            if (groups == null) {
                groups = new ArrayList<ShowSchemeGroup>();
                List field_list = CommUtil.fetchEntities("from ShowSchemeGroup ssg where ssg.entity_name='" + show_scheme.getEntity_name() + "' and ssg.person_code in('" + UserContext.person_code + "','sa') order by ssg.order_no ");
                for (Object obj : field_list) {
                    ShowSchemeGroup ssg = (ShowSchemeGroup) obj;
                    if (ssg.getPerson_code().equals(UserContext.person_code)) {
                        groups.add(ssg);
                    }
                }
                if (groups.isEmpty()) {
                    for (Object obj : field_list) {
                        ShowSchemeGroup ssg = (ShowSchemeGroup) obj;
                        if (ssg.getPerson_code().equals(UserContext.sysManName)) {
                            groups.add(ssg);
                        }
                    }
                }
                exist_groups.put(show_scheme.getEntity_name(), groups);
            }
//            for (ShowSchemeGroup ssg : groups) {
//                String entityName = ssg.getEntity_name().contains(".") ? ssg.getEntity_name().substring(ssg.getEntity_name().lastIndexOf(".") + 1) : ssg.getEntity_name();
//                Integer order = orders.get(entityName + "." + ssg.getField_name());
//                if (order != null) {
//                    ssg.setOrder_no(order);
//                }
//            }
//            SysUtil.sortListByInteger(groups, "order_no");
            for (ShowSchemeGroup ssg : groups) {
                String key = SysUtil.tranField(ssg.getField_name());
                UIItemDetail uid = detailKeys.get(key);
                detailKeys.remove(key);
                if (uid == null) {
                    continue;
                }
                UIItemGroup group = getGroupByName(groupKeys, ssg.getField_group(), fg_list);
                group.getBindDetails().add(uid);
            }
        }
        for (ShowSchemeDetail ssd : details) {
            String key = SysUtil.tranField(ssd.getField_name());
            UIItemDetail uid = detailKeys.get(key);
            detailKeys.remove(key);
            if (uid == null) {
                continue;
            }
            UIItemGroup group = getGroupByName(groupKeys, "未分组", fg_list);
            group.getBindDetails().add(uid);
        }
        return fg_list;
    }

    private static UIItemGroup getGroupByName(Hashtable<String, UIItemGroup> groupKeys, String groupName, List fg_list) {
        UIItemGroup group = groupKeys.get(groupName);
        if (group == null) {
            group = new UIItemGroup();
            group.setGroupName(groupName);
            fg_list.add(group);
            groupKeys.put(groupName, group);
        }
        return group;
    }

    public static List<UIItemDetail> getUIItemDetails(Class the_class, List<String> fields) {
        List<UIItemDetail> details = new ArrayList();
        for (String fieldName : fields) {
            TempFieldInfo tfi;
            if (fieldName.contains(".")) {
                tfi = EntityBuilder.getTempFieldInfoByName(fieldName);
            } else {
                tfi = EntityBuilder.getTempFieldInfoByName(the_class.getName(), fieldName, true);
            }
            if (tfi == null) {
                continue;
            }
            UIItemDetail bindDetail = new UIItemDetail();
            bindDetail.setField(tfi.getField());
            bindDetail.setBindName(fieldName);
            bindDetail.setField_caption(fieldName);
            FieldAnnotation fieldAnnotation = tfi.getField().getAnnotation(FieldAnnotation.class);
            if (fieldAnnotation != null) {
                int field_editable = (fieldName.contains(".") || fieldName.startsWith("#")) ? 0 : UserContext.getFieldRight(tfi.getEntity_name() + "." + tfi.getField_name());
                bindDetail.setEditable(fieldAnnotation.isEditable() && fieldAnnotation.editableWhenEdit() && field_editable == 1);
                bindDetail.setEditable_when_new(fieldAnnotation.isEditable() && fieldAnnotation.editableWhenNew() && field_editable == 1);
                bindDetail.setView_width(fieldAnnotation.view_width());
                bindDetail.setField_caption(tfi.getCaption_name());
                if (fieldAnnotation.not_null()) {
                    bindDetail.setField_caption(bindDetail.getField_caption() + "(*)");
                }
            }
            details.add(bindDetail);
        }
        return details;
    }
}
