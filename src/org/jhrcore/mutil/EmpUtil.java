/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.mutil;

import com.fr.view.core.DateUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.entity.A01;
import org.jhrcore.entity.BasePersonChange;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.RyChgLog;
import org.jhrcore.entity.SysParameter;
import org.jhrcore.util.UtilTool;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.base.EntityDef;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.change.ChangeField;
import org.jhrcore.entity.change.ChangeItem;
import org.jhrcore.entity.change.ChangeMethod;
import org.jhrcore.entity.change.ChangeScheme;
import org.jhrcore.entity.showstyle.ShowScheme;
import org.jhrcore.entity.showstyle.ShowSchemeDetail;
import org.jhrcore.entity.showstyle.ShowSchemeGroup;
import org.jhrcore.rebuild.EntityBuilder;
import org.jhrcore.ui.BeanPanel;
import org.jhrcore.ui.UIItemGroup;

/**
 *
 * @author hflj
 */
public class EmpUtil {

    private static Logger log = Logger.getLogger(EmpUtil.class.getName());
    public static String spLimitCode = "Register.limit";
    public static String spTimeCode = "Register.time";
    public static String IDToEmpFields = "Register.id_cmp";
    public static String IDToEmpPhoto = "Register.save_photo";
    public static String IDFieldEditable = "Register.com_field_edit";
    public static String ICGWField = "ICCardPosition";
    public static String ICNOField = "person_card_no";
    public static String changeSchemeCode = "ServiceEmpScheme";
    private static List<ChangeScheme> change_scheme_list = new ArrayList<ChangeScheme>();

    public static Hashtable<String, SysParameter> getParas() {
        List list = CommUtil.fetchEntities("from SysParameter s where s.sysParameter_key in('" + IDToEmpFields + "','" + IDToEmpPhoto + "','" + IDFieldEditable + "','" + ICGWField + "','" + ICNOField + "')");
        Hashtable<String, SysParameter> result = new Hashtable();
        Hashtable<String, SysParameter> temp = new Hashtable();
        for (Object obj : list) {
            SysParameter sp = (SysParameter) obj;
            String key = sp.getSysParameter_key();
            temp.put(key, sp);
        }
        SysParameter sp = temp.get(IDToEmpFields);
        if (sp == null) {
            sp = new SysParameter();
            sp.setSysParameter_key(IDToEmpFields);
            sp.setSysparameter_name("二代证与人员信息对应标识");
            sp.setSysparameter_value("personName:a0101;personSex:a0107;personBorn:a0111;personIDCardNo:a0177");
            CommUtil.saveOrUpdate(sp);
        }
        result.put(IDToEmpFields, sp);
        sp = temp.get(IDToEmpPhoto);
        if (sp == null) {
            sp = new SysParameter();
            sp.setSysParameter_key(IDToEmpPhoto);
            sp.setSysparameter_name("读二代证是否保存照片");
            sp.setSysparameter_value("0");
            CommUtil.saveOrUpdate(sp);
        }
        result.put(IDToEmpPhoto, sp);
        sp = temp.get(IDFieldEditable);
        if (sp == null) {
            sp = new SysParameter();
            sp.setSysParameter_key(IDFieldEditable);
            sp.setSysparameter_name("对应的人事信息不可编辑");
            sp.setSysparameter_value("0");
            CommUtil.saveOrUpdate(sp);
        }
        result.put(IDFieldEditable, sp);
        sp = temp.get(ICGWField);
        if (sp == null) {
            sp = new SysParameter();
            sp.setSysParameter_key(ICGWField);
            sp.setSysparameter_name("IC岗位字段设置");
            sp.setSysparameter_value("1");
            CommUtil.saveOrUpdate(sp);
        }
        result.put(ICGWField, sp);
        sp = temp.get(ICNOField);
        if (sp == null) {
            sp = new SysParameter();
            sp.setSysParameter_key(ICNOField);
            sp.setSysparameter_name("员工IC卡号");
            sp.setSysparameter_value("");
            CommUtil.saveOrUpdate(sp);
        }
        result.put(ICNOField, sp);
        return result;
    }

    public static BasePersonChange setImportValueBy(ChangeScheme changeScheme, BasePersonChange basePersonChange, int old_value, int new_value) {
        for (ChangeItem ci : changeScheme.getChangeItems()) {
            String fieldName = ci.getFieldName();
            try {
                Method method = null;
                fieldName = "old_" + ci.getFieldName();
                method = basePersonChange.getClass().getMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), new Class[]{int.class});
                method.invoke(basePersonChange, new Object[]{old_value});
                fieldName = "new_" + ci.getFieldName();
                method = basePersonChange.getClass().getMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), new Class[]{int.class});
                method.invoke(basePersonChange, new Object[]{new_value});
            } catch (SecurityException ex) {
                log.error(ex);
            } catch (IllegalArgumentException e) {
                log.error(e);
            } catch (IllegalAccessException e) {
                log.error(e);
            } catch (NoSuchMethodException e) {
                log.error(e);
            } catch (InvocationTargetException e) {
                log.error(e);
            }
        }
        return basePersonChange;
    }

    public static ChangeScheme getChangeScheme(String key) {
        return (ChangeScheme) CommUtil.fetchEntityBy("from ChangeScheme cs left join fetch cs.changeItems left join fetch cs.changeFields left join fetch cs.changeMethods where cs.changeScheme_key='" + key + "'");
    }

    public static boolean hasSchemeViewRight(ChangeScheme cs) {
        if (cs == null) {
            return false;
        }
        String key = cs.getChangeScheme_key();
        if (key == null || key.equals("EmpScheme_Add") || key.equals("EmpScheme_Del")) {
            return false;
        }
        return UserContext.hasFunctionRight(EmpUtil.changeSchemeCode + ".scheme_" + key + "_view");
    }

    public static boolean hasSchemeUseRight(ChangeScheme cs) {
        if (cs == null) {
            return false;
        }
        String key = cs.getChangeScheme_key();
        if (key == null || key.equals("EmpScheme_Add") || key.equals("EmpScheme_Del")) {
            return false;
        }
        return UserContext.hasFunctionRight(EmpUtil.changeSchemeCode + ".scheme_" + key + "_use");
    }

    public static List<ChangeScheme> getViewChangeSchemeList() {
        List<ChangeScheme> list = new ArrayList();
        for (ChangeScheme cs : getChange_scheme_list()) {
            if (hasSchemeViewRight(cs)) {
                list.add(cs);
            }
        }
        return list;
    }

    public static List<ChangeScheme> getUseChangeSchemeList() {
        List<ChangeScheme> list = new ArrayList();
        for (ChangeScheme cs : getChange_scheme_list()) {
            if (hasSchemeUseRight(cs)) {
                list.add(cs);
            }
        }
        return list;
    }

    public static List<ChangeScheme> getChange_scheme_list() {
        if (change_scheme_list.isEmpty()) {
            change_scheme_list = (List<ChangeScheme>) CommUtil.fetchEntities("from ChangeScheme a left join fetch a.changeItems left join fetch a.changeFields left join fetch a.changeMethods order by changeScheme_no");
        }
        return change_scheme_list;
    }

    public static List getUserChangeSchemeList() {
        HashSet<String> work_flow_keys = new HashSet<String>();
        if (!UserContext.isSA) {
            String sql = "select wfc.workflowclass_key from workflowclass wfc,workflowdef wfd,workflowa01 wfa,A01PassWord apw where wfc.workflowclass_key=wfd.workflowclass_key and wfd.workflowdef_key=wfa.workflowdef_key and wfa.a01password_key=apw.a01password_key and wfa.wf_state='开始' and apw.a01_key='" + UserContext.person_key + "'";
            List data = CommUtil.selectSQL(sql);
            for (Object obj : data) {
                work_flow_keys.add(obj.toString().replace("EmpChangeScheme.", ""));
            }
        }
        List result = new ArrayList();
        List list = getChange_scheme_list();
        for (Object obj : list) {
            ChangeScheme c = (ChangeScheme) obj;
            if ("借调".equals(c.getScheme_type())) {
                continue;
            }
            if (!hasSchemeUseRight(c)) {
                continue;
            }
            if (c.isCheck_flag()) {
                if (!UserContext.isSA && !work_flow_keys.contains(c.getChangeScheme_key())) {
                    continue;
                }
            }
            result.add(obj);
        }
        return result;
    }

    public static HashMap getReportParaMap(List list) {
        HashMap hm = new HashMap();
        if (list != null && list.size() > 0) {
            List<String> a0190s = new ArrayList<String>();
            List<String> wfNos = new ArrayList<String>();
            List<String> daNos = new ArrayList<String>();
            List<String> keys = new ArrayList<String>();
            Class c = list.get(0).getClass();
            for (Object obj : list) {
                if (obj instanceof A01) {
                    a0190s.add(((A01) obj).getA0190());
                } else if (obj instanceof BasePersonChange) {
                    BasePersonChange bpc = (BasePersonChange) obj;
                    a0190s.add(bpc.getA01().getA0190());
                    wfNos.add(bpc.getOrder_no());
                    wfNos.add(bpc.getPart_no());
                } else {
                    a0190s.add(obj.toString());
                }
                keys.add(PublicUtil.getProperty(obj, EntityBuilder.getEntityKey(c)) + "");
            }
            hm.put("人员编号", a0190s.toArray());
            hm.put("批次号", wfNos.toArray());
            hm.put("档案号", daNos.toArray());
            hm.put("key", keys);
        }
        return hm;
    }

    public static HashMap getReportParaMap(List list, Date d1, Date d2) {
        HashMap hm = new HashMap();
        if (list != null && list.size() > 0) {
            List<String> a0190s = new ArrayList<String>();
            for (Object obj : list) {
                if (obj instanceof A01) {
                    a0190s.add(((A01) obj).getA0190());
                } else {
                    a0190s.add(obj.toString());
                }
            }
            hm.put("人员编号", a0190s.toArray());
            hm.put("开始日期", d1);
            hm.put("结束日期", d2);
        }
        return hm;
    }

    public static HashMap getReportParaMap_bjf(List<String> a0190s, String d1) {
        HashMap hm = new HashMap();
        if (a0190s != null && a0190s.size() > 0) {
            hm.put("人员编号", a0190s.toArray());
            hm.put("打印日期", d1);
        }
        return hm;
    }

    public static HashMap getReportParaMap_zy(List<String> tickets) {
        HashMap hm = new HashMap();
        if (tickets != null && tickets.size() > 0) {
            hm.put("准考证号", tickets.toArray());
        }
        return hm;
    }

    public static HashMap getReportParaMap_ht(String a0190s) {
        HashMap hm = new HashMap();
        hm.put("人员编号", a0190s);
        return hm;
    }

    public static HashMap getReportParaMap_hts(List<String> a0190s) {
        HashMap hm = new HashMap();
        hm.put("人员编号", a0190s.toArray());
        return hm;
    }

    public static RyChgLog getCommUserLog() {
        RyChgLog rcl = (RyChgLog) UtilTool.createUIDEntity(RyChgLog.class);
        rcl.setChg_ip(UserContext.getPerson_ip());
        rcl.setChg_mac(UserContext.getPerson_mac());
        rcl.setChg_user(UserContext.person_name + "{" + UserContext.person_code + "}");
        rcl.setChg_date(new Date());
        rcl.setA0101(UserContext.person_name);
        rcl.setA0190(UserContext.person_code);
        rcl.setA01_key(UserContext.person_key);
        return rcl;
    }

    public static RyChgLog getCommZpAddLog() {
        RyChgLog rcl = (RyChgLog) UtilTool.createUIDEntity(RyChgLog.class);
        rcl.setChg_ip(UserContext.getPerson_ip());
        rcl.setChg_mac(UserContext.getPerson_mac());
        rcl.setChg_user(UserContext.person_name + "{" + UserContext.person_code + "}");
        rcl.setChg_type("招聘入职");
        rcl.setChg_field("a0193");
        rcl.setChangeScheme_key("EmpScheme_Add");
        rcl.setBeforestate("招聘");
        rcl.setChg_date(CommUtil.getServerDate());
        return rcl;
    }

    public static RyChgLog getCommRyAddLog() {
        RyChgLog rcl = (RyChgLog) UtilTool.createUIDEntity(RyChgLog.class);
        rcl.setChg_ip(UserContext.getPerson_ip());
        rcl.setChg_mac(UserContext.getPerson_mac());
        rcl.setChg_user(UserContext.person_name + "{" + UserContext.person_code + "}");
        rcl.setChg_date(new Date());
        rcl.setAfterstate("新增(未入库)");
        rcl.setChg_type("入职新增");
        rcl.setChg_field("a0193");
        rcl.setChangeScheme_key("EmpScheme_Add");
        return rcl;
    }

    /**
     * 该方法用于判断当前部门是否允许入职
     * @param list：允许入职的部门LIST
     * @param curren_dept：当前部门
     * @return：是否允许入职
     */
    public static boolean isAllowRegister(List list, DeptCode curren_dept) {
        if (list != null && list.size() > 0) {
            boolean allow = false;
            for (Object obj : list) {
                if (curren_dept.getDept_code().startsWith(obj.toString())) {
                    allow = true;
                    break;
                }
            }
            return allow;
        }
        return true;
    }

    public static boolean isAllowShowRegister(List list, DeptCode curren_dept) {
        if (list != null && list.size() > 0) {
            boolean allow = false;
            for (Object obj : list) {
                if (curren_dept.getDept_code().startsWith(obj.toString()) || obj.toString().startsWith(curren_dept.getDept_code())) {
                    allow = true;
                    break;
                }
            }
            if (!allow) {
                return false;
            }
        }
        return true;
    }

    public static ShowSchemeDetail createShowDetail(String entityName, String field_name, String group_name, int i, List groups) {
        ShowSchemeDetail ssd = (ShowSchemeDetail) UtilTool.createUIDEntity(ShowSchemeDetail.class);
        ssd.setField_name(field_name);
        ssd.setField_group(group_name);
        ssd.setOrder_no(i);
        ssd.setEntity_name(entityName);
        ShowSchemeGroup ssg = new ShowSchemeGroup();
        ssg.setEntity_name(entityName);
        ssg.setField_group(group_name);
        ssg.setField_name(field_name);
        ssg.setOrder_no(i);
        ssg.setPerson_code(UserContext.person_code);
        groups.add(ssg);
        return ssd;
    }

    public static A01 getA01(String a01_key) {
        Object obj = CommUtil.fetchEntityBy("from A01 a join fetch a.deptCode where a.a01_key='" + a01_key + "'");
        if (obj == null) {
            return null;
        }
        return (A01) obj;
    }

    public static void refreshUIByChange(Object obj, BeanPanel beanPanel, ChangeScheme cs) {
        if (obj != null) {
            BasePersonChange bpc = (BasePersonChange) obj;
            if (bpc.getChangescheme_key() == null) {
                return;
            }
            if (cs == null) {
                return;
            }
            Class bpc_class = null;
            String entityName = "org.jhrcore.entity.PersonChange_" + cs.getChangeScheme_no();
            try {
                bpc_class = Class.forName(entityName);
            } catch (Exception e) {
                return;
            }
            entityName = bpc_class.getSimpleName();
            List<ShowSchemeGroup> groups = UIItemGroup.exist_groups.get("EmpChange_" + entityName);
            if (groups == null) {
                groups = new ArrayList<ShowSchemeGroup>();
            } else {
                groups.clear();
            }
            beanPanel.setBean(obj);
            ShowScheme ss = (ShowScheme) UtilTool.createUIDEntity(ShowScheme.class);
            List<TempFieldInfo> change_infos = EntityBuilder.getCommFieldInfoListOf(BasePersonChange.class, EntityBuilder.COMM_FIELD_VISIBLE_ALL);
            Set<ShowSchemeDetail> details = new HashSet<ShowSchemeDetail>();
            ss.setGroup_flag(true);
            int ind = 0;
            List<String> baseFields = new ArrayList<String>();
            baseFields.add("chg_state");
            baseFields.add("chg_type");
            baseFields.add("apply_date");
            baseFields.add("reason");
            baseFields.add("action_date");
            baseFields.add("pay_date");
            baseFields.add("chg_user");
            if (cs.contains("deptCode")) {
                baseFields.add("change_ht_flag");
            }
            for (ChangeField cf : cs.getChangeFields()) {
                if (cf.getAppendix_name().equals("BasePersonChange")) {
                    baseFields.add(cf.getAppendix_field().replace("_code_", ""));
                }
            }
            for (TempFieldInfo tfi : change_infos) {
                if (tfi.getField_name().equals("order_no")) {
                    continue;
                }
                if (!baseFields.contains(tfi.getField_name().replace("_code_", ""))) {
                    continue;
                }
                ind++;
                details.add(EmpUtil.createShowDetail(entityName, tfi.getField_name(), "变动主表", ind, groups));
            }
            ss.setEntity_name("EmpChange_" + bpc_class.getSimpleName());
            ss.setShowSchemeDetails(details);
            List<TempFieldInfo> cis = new ArrayList<TempFieldInfo>();
            for (ChangeItem ci : cs.getChangeItems()) {
                if (ci.getFieldName().equals("a0191")) {
                    details.add(EmpUtil.createShowDetail(entityName, "old_" + ci.getFieldName(), "变动项目", ind, groups));
                    ind++;
                    details.add(EmpUtil.createShowDetail(entityName, "new_" + ci.getFieldName(), "变动项目", ind, groups));
                    ind++;
                    continue;
                }
                TempFieldInfo tfi = EntityBuilder.getTempFieldInfoByName("A01", ci.getFieldName(), false);
                if (tfi == null) {
                    continue;
                }
                cis.add(tfi);
            }
            SysUtil.sortListByInteger(cis, "order_no");
            for (TempFieldInfo tfi : cis) {
                details.add(EmpUtil.createShowDetail(entityName, "old_" + tfi.getField_name(), "变动项目", ind, groups));
                ind++;
                details.add(EmpUtil.createShowDetail(entityName, "new_" + tfi.getField_name(), "变动项目", ind, groups));
                ind++;
            }
            for (ChangeField cf : cs.getChangeFields()) {
                if (cf.getAppendix_name().equals("BasePersonChange")) {
                    continue;
                }
                details.add(EmpUtil.createShowDetail(entityName, cf.getAppendix_field(), cf.getAppendix_displayname(), ind, groups));
                ind++;
            }
            beanPanel.setShow_scheme(ss);
            List<String> fields = EntityBuilder.getCommFieldNameListOf(bpc_class, EntityBuilder.COMM_FIELD_VISIBLE_ALL);
            beanPanel.setFields(fields);
            UIItemGroup.exist_groups.put("EmpChange_" + entityName, groups);
        }
        beanPanel.bind();
    }

    public static List<String> getCheckAppendixTable() {
        String val = "";
        SysParameter sp = (SysParameter) CommUtil.fetchEntityBy("from SysParameter sp where sp.sysParameter_key = 'Emp.annexCheck' ");
        if (sp != null && sp.getSysparameter_value() != null) {
            val = sp.getSysparameter_value();
        }
        return Arrays.asList(val.split(";"));
    }

    public static String getA0191_string() {
        String result = "('-1'";
        Object obj = CommUtil.fetchEntityBy("from SysParameter sp where sp.sysParameter_key = 'Register.person_class'");
        if (obj == null) {
            return "('-1')";
        }
        SysParameter sp = (SysParameter) obj;
        String a0191s = sp.getSysparameter_value();
        String[] person_classes = a0191s.split(";");
        for (String tmp : person_classes) {
            try {

                Class c = Class.forName("org.jhrcore.entity." + tmp);
                ClassAnnotation ca = (ClassAnnotation) c.getAnnotation(ClassAnnotation.class);
                result = result + ",'" + ca.displayName() + "'";
            } catch (ClassNotFoundException ex) {
                log.error(ex);
            }
        }
        result = result + ")";
        return result;
    }

    public static Hashtable<String, String> tranMethod(BasePersonChange bpc, ChangeScheme cs, Set<String> entitys) {
        Hashtable<String, String> methodKeys = new Hashtable<String, String>();
        for (String entity : entitys) {
            String method = "";
            for (ChangeMethod cm : cs.getChangeMethods()) {
                if (cm.getAppendix_name().equals(entity)) {
                    method = cm.getMethod();
                    break;
                }
            }
            methodKeys.put(entity, (method == null || method.trim().equals("")) ? "追加新记录" : method);
        }
        String appendixType = bpc.getAppendix_type();
        if (appendixType != null) {
            String[] strs = appendixType.split(";");
            for (String str : strs) {
                String[] methods = str.split("\\|");
                if (methods.length < 2) {
                    continue;
                }
                methodKeys.put(methods[0], methods[1]);
            }
        }
        return methodKeys;
    }

    public static String getPersonClass(String a0191) {
        List<EntityDef> person_classes = SysUtil.getPersonClass();
        for (EntityDef ed : person_classes) {
            if (ed.getEntityCaption().equals(a0191)) {
                return ed.getEntityName();
            }
        }
        return "A01";
    }

    public static boolean canRegister(String type) {
        if ("register".equals(type) || "submit".equals(type)) {
            SysParameter spLimit = null;
            SysParameter spTime = null;
            List list = CommUtil.fetchEntities("from SysParameter where sysParameter_key in('" + EmpUtil.spLimitCode + "','" + EmpUtil.spTimeCode + "')");
            for (Object obj : list) {
                SysParameter para = (SysParameter) obj;
                if (para.getSysParameter_key().equals(EmpUtil.spLimitCode)) {
                    spLimit = para;
                } else if (para.getSysParameter_key().equals(EmpUtil.spTimeCode)) {
                    spTime = para;
                }
            }
            if (spLimit == null || spTime == null) {
                return true;
            }
            String limit = spLimit.getSysparameter_value();
            int limit_type = SysUtil.objToInt(limit, -1);
            if (limit_type <= 0 || limit_type > 3) {
                return true;
            }
            if (type.equals("register") && limit_type == 2) {
                return true;
            }
            if (type.equals("submit") && limit_type == 1) {
                return true;
            }
            String value = spTime.getSysparameter_value();
            String code = spTime.getSysparameter_code();
            if (value == null || value.trim().equals("") || code == null) {
                return true;
            }
            String[] strs = value.split(";");
            Date date = CommUtil.getServerDate();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            Calendar c_start = Calendar.getInstance();
            Calendar c_end = Calendar.getInstance();
            if ("0".equals(code)) {
                c_start.setTime(DateUtil.StrToDate(strs[0]));
                c_end.setTime(DateUtil.StrToDate(strs[1]));
            } else {
                c_start.setTime(date);
                c_end.setTime(date);
                c_start.set(Calendar.DAY_OF_MONTH, 1);
                c_end.set(Calendar.DAY_OF_MONTH, 1);
                int e_month = c_end.get(Calendar.MONTH);
                c_end.add(Calendar.DAY_OF_MONTH, Integer.valueOf(strs[1]) - 1);
                if (c_end.get(Calendar.MONTH) != e_month) {
                    c_end.set(Calendar.DAY_OF_MONTH, 1);
                    c_end.add(Calendar.DAY_OF_MONTH, -1);
                }
                e_month = c_start.get(Calendar.MONTH);
                c_start.add(Calendar.DAY_OF_MONTH, Integer.valueOf(strs[0]) - 1);
                if (c_start.get(Calendar.MONTH) != e_month) {
                    c_start.set(Calendar.DAY_OF_MONTH, 1);
                    c_start.add(Calendar.DAY_OF_MONTH, -1);
                }
            }
            c_start.set(Calendar.HOUR_OF_DAY, 0);
            c_start.set(Calendar.MINUTE, 0);
            c_start.set(Calendar.SECOND, 0);
            c_end.set(Calendar.HOUR_OF_DAY, 23);
            c_end.set(Calendar.MINUTE, 59);
            c_end.set(Calendar.SECOND, 59);
            if (c.before(c_start) || c.after(c_end)) {
                String msg = "系统允许的入职时间范围为：" + DateUtil.DateToStr(c_start.getTime()) + " 至 " + DateUtil.DateToStr(c_end.getTime()) + "，\n当前时间不在系统设置的入职时间范围内，不允许进行入职操作";
                JOptionPane.showMessageDialog(null, msg);
                return false;
            }
            return true;
        }
        return true;
    }
}
