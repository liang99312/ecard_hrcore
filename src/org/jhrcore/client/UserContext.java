/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.client;

import org.jhrcore.util.SysUtil;
import org.jhrcore.util.ClientIpCheck;
import org.jhrcore.util.DbUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import org.apache.log4j.Logger;
import org.jhrcore.client.report.ReportPanel;
import org.jhrcore.comm.CodeManager;
import org.jhrcore.comm.ConfigManager;
import org.jhrcore.entity.A01PassWord;
import org.jhrcore.entity.Code;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.SysParameter;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.base.EntityDef;
import org.jhrcore.entity.base.ModuleInfo;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.right.FuntionRight;
import org.jhrcore.entity.right.Role;
import org.jhrcore.entity.right.RoleA01;
import org.jhrcore.entity.right.RoleCode;
import org.jhrcore.entity.right.RoleEntity;
import org.jhrcore.iservice.impl.CommImpl;
import org.jhrcore.rebuild.EntityBuilder;
import org.jhrcore.ui.language.WebHrMessage;

/**
 *该类表示当前登录用户的相关信息
 * @author Administrator
 */
public class UserContext {

    public static String person_key = "-1"; // 当前登录用户的人员主键
    public static String person_code;//当前登录用户的人员编码   *******
    public static String person_name;//当前登录用户的人员名称
    public static String content = ""; //当前登录用户的部门
    public static String dept_code = "1";//当前登录用户的部门代码
    public static String password = ""; //当前登录用户的密码         *******
    public static String role_id = "-1"; // 当前登录用户的角色主键
    public static String rolea01_key = "-1";//当前用户角色ID
    public static Role cur_role = null;//当前登录用户对应的角色
    public static String passKey = "-1";//当前登录用户对应的A01PASSWORD表Key
    public static String file_path = "";
    private static String person_ip = null;
    private static String person_mac = null;
    public static HashSet<String> funtionRights = new HashSet<String>();
    public static Hashtable<String, Integer> reportRights = new Hashtable<String, Integer>();
    private static Hashtable<String, FuntionRight> funtion_keys = new Hashtable<String, FuntionRight>();
    public static Hashtable<String, Integer> fieldRights = new Hashtable<String, Integer>();//当前用户的权限字段集合
    public static Hashtable<String, RoleEntity> entityRights = new Hashtable<String, RoleEntity>();//当前用户的编码权限，索引为编码主键
    public static Hashtable<String, RoleCode> codeRights = new Hashtable<String, RoleCode>();//当前用户的编码权限，索引为编码主键
    private static Hashtable<String, SysParameter> sys_paras = new Hashtable<String, SysParameter>();
    public static Hashtable<String, ModuleInfo> modules = new Hashtable<String, ModuleInfo>();
    private static Hashtable<String, DeptCode> dcKeys = new Hashtable<String, DeptCode>();
    public static String dept_right_str = "1=1";//部门权限串
    public static String dept_right_rea_str = "1=1";//部门权限串
    public static String person_right_str = "select deptCode_key from DeptCode";//人员权限（相对部门）串
    public static String role_right_str = "";//角色权限串(显示该人员对应角色及其下级角色)
    public static A01PassWord a01PassWord;
    public static String sql_dialect = "";
    private static List<DeptCode> depts = new ArrayList<DeptCode>();
    public static List<String> dept_codes = new ArrayList<String>();
    private static List<String> sysModules = null;
    public static List<RoleA01> roles = new ArrayList<RoleA01>();
    public static boolean show_dept_code_flag = true;//是否显示部门编码
    public static String sysManName = "sa";
    private static String person_class_right_str = "'-1'";
    public static String conn_string = "+";
    private static Logger log = Logger.getLogger(UserContext.class.getSimpleName());
    public static boolean isSA = false;
    public static long last_oper_time = 0;//是否在线操作的标识,通过用户是否调用服务来判断
    public static boolean show_g10_flag = false;//是否显示岗位
    public static List funtion_list = new ArrayList();
    public static int msgNo = 0;
    public static String k_table = "";
    public static String language = "简体中文";
    public static String language_CN = "简体中文";
    public static List languages = new ArrayList();
    public static String codeShowSaveReport = "UI.showSaveReport";//保存时是否显示提示
    public static String codeShowViewModule = "UI.showViewModule";//是否仅显示权限菜单
    public static String codeImportViewField = "System.import_updateable";

    public static List<DeptCode> getMemoryDept(boolean needfetch) {
        if (needfetch || depts.isEmpty()) {
            getDepts();
        }
        return depts;
    }

    public static Hashtable<String, DeptCode> getDcKeys() {
        if (dcKeys.isEmpty()) {
            for (DeptCode dc : getMemoryDept(false)) {
                dcKeys.put(dc.getDeptCode_key(), dc);
            }
        }
        return dcKeys;
    }

    public static boolean hasModuleRight(String module_code) {
        if (sysModules == null) {
            sysModules = CommUtil.getSysModules();
            log.error("modules:" + sysModules);
        }
        if (sysModules == null || sysModules.isEmpty()) {
            return false;
        }
        return sysModules.contains(module_code);
    }

    public static String getSamDept_right_rea_str(String rechar) {
        if (isSA) {
            return "1=1";
        }
        if (rechar == null || rechar.trim().equals("")) {
            return " (" + dept_right_rea_str.replace("d.del_flag=0 and", "") + ") ";
        }
        return " (" + dept_right_rea_str.replace("d.del_flag=0 and", "").replace("d.", rechar + ".") + ") ";
    }

    public static String getDept_right_rea_str(String rechar) {
        if (isSA) {
            return "1=1";
        }
        if (rechar == null || rechar.trim().equals("")) {
            return " (" + dept_right_rea_str + ") ";
        }
        return " (" + dept_right_rea_str.replace("d.", rechar + ".") + ") ";
    }

    /**
     * 该方法用于获取系统部门列表
     * @param fetch_del：是否带上删除部门
     * @return
     */
    public static List<DeptCode> getDepts(boolean fetch_del, boolean fetch_virtual) {
        getMemoryDept(false);
        List<DeptCode> r_depts = new ArrayList<DeptCode>();
        fetch_virtual = !fetch_virtual;
        if (fetch_del) {
            for (DeptCode dc : UserContext.depts) {
                if (dc.isVirtual() && fetch_virtual) {
                    continue;
                }
                r_depts.add(dc);
            }
        } else {
            for (DeptCode dc : UserContext.depts) {
                if (dc.isDel_flag()) {
                    continue;
                }
                if (dc.isVirtual() && fetch_virtual) {
                    continue;
                }
                r_depts.add(dc);
            }
        }
        return r_depts;
    }

    /**
     * 该方法用于获取系统部门列表
     * @param fetch_del：是否带上删除部门
     * @return
     */
    public static List<DeptCode> getDepts(boolean fetch_del) {
        return getDepts(fetch_del, true);
    }

    public static boolean existDept(String dept_code) {
        if (sysManName.equals(UserContext.getPerson_code())) {
            return true;
        }
        if (dept_code == null || "".equals(dept_code)) {
            return true;
        }
        for (String str : dept_codes) {
            if (dept_code.startsWith(str) || str.startsWith(dept_code)) {
                return true;
            }
        }
        return false;
    }

    public static A01PassWord getUserA01PassWord() {
        if (sysManName.equals(UserContext.getPerson_code())) {
            return null;
        }
        if (a01PassWord == null) {
            a01PassWord = (A01PassWord) CommUtil.fetchEntityBy("from A01PassWord apw join fetch apw.a01 where apw.a01.a01_key = '" + person_key + "'");
        }
        return a01PassWord;
    }

    public static String getPerson_class_right_str(Class person_class, String pre_char) {
        if (!person_class.getSimpleName().equals("A01")) {
            ClassAnnotation ca = (ClassAnnotation) person_class.getAnnotation(ClassAnnotation.class);
            return ((pre_char == null || pre_char.trim().equals("")) ? "bp" : pre_char) + ".a0191='" + ca.displayName() + "'";
        }
        if (isSA) {
            return "1=1";
        }
        List<EntityDef> entitys = SysUtil.getPersonClass();
        person_class_right_str = "'-1'";
        for (EntityDef ed : entitys) {
            if (ed.getEntityName().equals("A01")) {
                continue;
            }
            if (!hasEntityViewRight(ed.getEntityName())) {
                continue;
            }
            person_class_right_str = person_class_right_str + ",'" + ed.getEntityCaption() + "'";
        }
        return ((pre_char == null || pre_char.trim().equals("")) ? "bp" : pre_char) + ".a0191 in(" + person_class_right_str + ")";
    }

    public static ModuleInfo getModuleInfo(String module_code) {
        ModuleInfo mi = modules.get(module_code);
        if (mi == null) {
            List list = CommUtil.fetchEntities("from ModuleInfo mi where mi.module_code='" + module_code + "' or mi.module_key='" + module_code + "'");
            for (Object obj : list) {
                ModuleInfo module = (ModuleInfo) obj;
                modules.put(module.getModule_code(), module);
                modules.put(module.getModule_key(), module);
            }
        }
        mi = modules.get(module_code);
        return mi;
    }

    public static String getRole_key() {
        if (cur_role != null) {
            return cur_role.getRole_key();
        } else {
            return "";
        }
    }

    public static boolean isShow_dept_code_flag() {
        return show_dept_code_flag;
    }

    public static void setShow_dept_code_flag(boolean show_dept_code_flag) {
        UserContext.show_dept_code_flag = show_dept_code_flag;
    }

    private static List<DeptCode> getDepts() {
        depts.clear();
        String hql = "from DeptCode d where " + dept_right_str.replace("d.del_flag=0", "1=1") + " order by d.px_code ";
        Hashtable<String, DeptCode> deptKeys = new Hashtable<String, DeptCode>();
        List list = CommUtil.fetchEntities(hql);
        for (Object obj : list) {
            DeptCode dc = (DeptCode) obj;
            depts.add(dc);
            deptKeys.put(dc.getDeptCode_key(), dc);
        }
        
        return depts;
    }

    public static String getSql_dialect() {
        if (sql_dialect.equals("")) {
            sql_dialect = DbUtil.SQL_dialect_check(CommUtil.getSQL_dialect());
            conn_string = "||";
            if (sql_dialect.equals("sqlserver")) {
                conn_string = "+";
            }
        }
        return sql_dialect;
    }

    public static SysParameter getSys_para(String para_code) {
        return sys_paras.get(para_code);
    }

    public static void putSys_para(String para_code, SysParameter sp) {
        sys_paras.put(para_code, sp);
    }

    public static String getFile_path() {
        String path = System.getProperty("user.dir");
        file_path = path + "/file/";
        return file_path;
    }

    public static String getServer_File_path() {
        return CommImpl.getServer_file_path();
    }

    public static void createUserFolder() {
        String path = System.getProperty("user.dir");
        file_path = path + "/file/";
        File dirFile;
        boolean bFile = false;
        try {
            dirFile = new File(file_path);
            bFile = dirFile.exists();
            if (!bFile) {
                bFile = dirFile.mkdir();
                if (bFile == true) {
                } else {
                    System.exit(1);
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    public static int verifyUser() {

        isSA = false;
        /**
         *1:权限用户
         *0:用户名密码错误
         *-1：用户名不存在
         **/
        password = password == null ? "" : password.trim();
        Object loginresult = CommUtil.login(person_code, password);
        if ("-1".equals(loginresult) || "0".equals(loginresult)) {
            return Integer.valueOf(loginresult.toString());
        }
        getSql_dialect();
        Object[] objs = (Object[]) loginresult;
        if ("1".equals(objs[0].toString())) {
            isSA = true;
            sysManName = person_code;
            dept_right_str = "d.del_flag=0";
            dept_right_rea_str = "d.del_flag=0";
            person_right_str = "select deptCode_key from DeptCode where del_flag=0";
            person_name = "系统管理员";
        } else {
            passKey = objs[1].toString();
            person_key = objs[2].toString();
            person_name = objs[3].toString();
            content = objs[4].toString();
            dept_code = objs[5].toString();
            String sql = "select r.role_key,r.role_code,r.role_name,ra.rolea01_key from RoleA01 ra,Role r where ra.role_key=r.role_key and ra.a01password_key='" + passKey + "'";
            List data = CommUtil.selectSQL(sql);
            for (Object obj : data) {
                Object[] r_obj = (Object[]) obj;
                Role r = new Role();
                r.setRole_key(r_obj[0].toString());
                r.setRole_code(r_obj[1].toString());
                r.setRole_name(r_obj[2].toString());
                RoleA01 ra = new RoleA01();
                ra.setRole(r);
                ra.setRoleA01_key(r_obj[3].toString());
                roles.add(ra);
            }
            if (roles.size() > 0) {
                Hashtable<String, RoleA01> roleA01Keys = new Hashtable<String, RoleA01>();
                for (RoleA01 ra : roles) {
                    roleA01Keys.put(ra.getRole().getRole_key(), ra);
                }
                String roleid = ConfigManager.getConfigManager().getProperty("User.role_id");
                if (roleid == null || roleid.trim().equals("") || !roleA01Keys.keySet().contains(roleid)) {
                    roleid = roles.get(0).getRole().getRole_key();
                }
                RoleA01 ra = roleA01Keys.get(roleid);
                cur_role = ra.getRole();
                role_id = cur_role.getRole_key();
                rolea01_key = ra.getRoleA01_key();
            }
        }
        return 1;
    }

    public static boolean hasRoleRight(String role_key) {
        if (isSA) {
            return true;
        }
        if (role_key == null || role_key.equals("")) {
            return false;
        }
        for (RoleA01 r : roles) {
            if (r.getRole().getRole_key().equals(role_key)) {
                return true;
            }
        }
        return false;
    }

    public static FuntionRight getFunByKey(String key) {
        for (Object obj : funtion_list) {
            FuntionRight fr = (FuntionRight) obj;
            if (fr.getFuntionRight_key().equals(key)) {
                return fr;
            }
        }
        return null;
    }

    public static String getPerson_ip() {
        if (person_ip == null) {
            person_ip = ClientIpCheck.getIP();
        }
        return person_ip;
    }

    public static String getPerson_mac() {
        if (person_mac == null) {
            person_mac = ClientIpCheck.getMac();
        }
        return person_mac;
    }

    public static void getUserRight() {
        cur_role = (Role) CommUtil.fetchEntityBy("from Role where role_key='" + role_id + "'");
        if (cur_role != null) {
            role_right_str = "r.role_code like '" + cur_role.getRole_code() + "%'";
        } else {
            role_right_str = "1=0";
        }
        //初始化功能权限
        initFuntionRight();
    }

    public static void initRights() {
        //初始化报表权限
        initReportRight();
        //初始化字段权限
        initFieldRight();
        //初始化部门权限
        initDeptRight();
        //初始化编码权限
        initCodeRight();
        //初始化表权限
        initEntityRight();
    }

    public static String getEntityRightSQL(String entity_name, String pre_sql, String start_name) {
        if (entity_name == null) {
            return pre_sql;
        }
        if (isSA) {
            return pre_sql;
        }
        RoleEntity re = entityRights.get(entity_name);
        if (re == null) {
            return pre_sql;
        }
        String sql = re.getRight_sql();
        sql = ((sql == null || sql.trim().equals("")) ? "1=1" : ("(" + sql + ")"));
        return pre_sql + " and " + sql.replace("@@", start_name);
    }

    public static void initEntityRight() {
        if ("-1".equals(role_id)) {
            return;
        }
        entityRights.clear();
        List list = CommUtil.selectSQL("select rc.add_flag,rc.edit_flag,rc.del_flag,rc.view_flag,rc.right_sql,rc.queryScheme_key,ed.entityName,rc.edit_sql,rc.querySchemeEdit_key from RoleEntity rc,tabname ed where rc.entity_key=ed.entity_key and rc.role_key='" + role_id + "'");
        for (Object obj : list) {
            Object[] objs = (Object[]) obj;
            RoleEntity re = new RoleEntity();
            re.setAdd_flag(SysUtil.objToBoolean(objs[0]));
            re.setEdit_flag(SysUtil.objToBoolean(objs[1]));
            re.setDel_flag(SysUtil.objToBoolean(objs[2]));
            re.setView_flag(SysUtil.objToBoolean(objs[3]));
            re.setRight_sql((objs[4] == null || objs[4].toString().trim().equals("")) ? "1=1" : objs[4].toString());
            if (objs[5] != null) {
                re.setQueryScheme_key(objs[5].toString());
            }
            re.setEdit_sql((objs[7] == null || objs[7].toString().trim().equals("")) ? "" : objs[7].toString());
            if (objs[8] != null) {
                re.setQuerySchemeEdit_key(objs[8].toString());
            }
            entityRights.put(objs[6].toString().toUpperCase(), re);
        }
        String p_sql = "select rc.right_sql,ed.entityName from RoleEntity rc,Role r,Role r1,tabname ed  where rc.role_key=r.role_key and rc.entity_key=ed.entity_key and rc.right_sql is not null and "
                + " r1.role_key='" + role_id + "' and r.role_key!=r1.role_key";
        if (sql_dialect.equals("sqlserver")) {
            p_sql += " and charindex(r.role_code,r1.role_code)=1";
        } else if (sql_dialect.equals("oracle")) {
            p_sql += " and instr(r1.role_code,r.role_code)=1";
        } else if (sql_dialect.equals("db2")) {
            p_sql += " and locate(r.role_code,r1.role_code)=1";
        } else if (sql_dialect.equals("mysql")) {
            p_sql += " and locate(r1.role_code,r.role_code)=1";
        }
        List p_list = CommUtil.selectSQL(p_sql);
        Hashtable<String, String> rsqlKeys = new Hashtable<String, String>();
        for (Object obj : p_list) {
            Object[] objs = (Object[]) obj;
            String right_sql = objs[0] == null ? "" : objs[0].toString().trim();
            if (right_sql.equals("")) {
                continue;
            }
            String entityName = objs[1].toString().toUpperCase();
            String r_sql = rsqlKeys.get(entityName);
            if (r_sql == null) {
                r_sql = "(" + right_sql + ")";
            } else {
                r_sql += " and (" + right_sql + ")";
            }
            rsqlKeys.put(entityName, r_sql);
        }
        for (String key : rsqlKeys.keySet()) {
            RoleEntity re = entityRights.get(key);
            if (re == null) {
                re = new RoleEntity();
                re.setRight_sql(rsqlKeys.get(key));
                entityRights.put(key, re);
            } else {
                String sql = rsqlKeys.get(key);
                if (re.getRight_sql() != null) {
                    sql += " and (" + re.getRight_sql() + ")";
                }
                re.setRight_sql(sql);
            }
        }
    }
    //初始化编码权限

    public static void initCodeRight() {
        if ("-1".equals(role_id)) {
            return;
        }
        codeRights.clear();
        List list = CommUtil.selectSQL("select role_code_key,add_flag,edit_flag,del_flag,view_flag,right_sql,code_key,edit_sql from RoleCode rc where rc.role_key='" + role_id + "'");
        for (Object obj : list) {
            Object[] objs = (Object[]) obj;
            RoleCode rc = new RoleCode();
            rc.setRole_code_key(objs[0].toString());
            Code c = CodeManager.getCodeManager().getCodeByKey(objs[6].toString());
            if (c == null) {
                continue;
            }
            rc.setCode(c);
            codeRights.put(rc.getCode().getCode_id(), rc);
            rc.setAdd_flag("1".equals(objs[1].toString()));
            rc.setEdit_flag("1".equals(objs[2].toString()));
            rc.setDel_flag("1".equals(objs[3].toString()));
            rc.setView_flag("1".equals(objs[4].toString()));
            rc.setRight_sql(SysUtil.objToStr(objs[5]));
            rc.setEdit_sql(SysUtil.objToStr(objs[7]));
            String[] code_limits = null;
            String[] edit_limits = null;
            if (!rc.getRight_sql().equals("")) {
                code_limits = rc.getRight_sql().split(";");
            }
            if (!rc.getEdit_sql().equals("")) {
                edit_limits = rc.getEdit_sql().split(";");
            }
            CodeManager.getCodeManager().limitCodeForRight(rc.getCode().getCode_type(), code_limits, edit_limits);
        }
    }
    //初始化功能权限

    public static void initFuntionRight() {
        funtion_list = CommUtil.fetchEntities("from FuntionRight fr where fr.granted=1 order by fun_code");
        for (Object obj : funtion_list) {
            FuntionRight funtionRight = (FuntionRight) obj;
            if (funtionRight.getFun_module_flag() != null) {
                funtion_keys.put(funtionRight.getFun_module_flag(), funtionRight);
            }
            funtion_keys.put(funtionRight.getFuntionRight_key(), funtionRight);
        }
        if ("-1".equals(role_id)) {
            return;
        }
        funtionRights.clear();
        List list = CommUtil.selectSQL("select rf.funtionright_key from rolefuntion rf where rf.role_key='" + role_id + "'");
        for (Object obj : list) {
            FuntionRight funtionRight = funtion_keys.get(obj.toString());
            if (funtionRight == null) {
                continue;
            }
            funtionRights.add(funtionRight.getFun_module_flag());
        }
    }
    //初始化报表权限

    public static void initReportRight() {
        ReportPanel.initUserViewList();
        if ("-1".equals(role_id)) {
            return;
        }
        reportRights.clear();
        List list = CommUtil.selectSQL("select rd.reportDef_key,rr.fun_flag from rolereport rr,reportdef rd where rr.reportDef_key=rd.reportDef_key and rr.role_key='" + role_id + "'");
        for (Object obj : list) {
            Object[] objs = (Object[]) obj;
            reportRights.put(objs[0].toString(), SysUtil.objToInt(objs[1]));
        }
    }
    //初始化字段权限

    public static void initFieldRight() {
        if ("-1".equals(role_id)) {
            return;
        }
        List roleFields = CommUtil.selectSQL("select rf.field_name,rf.fun_flag from rolefield rf where rf.role_key='" + role_id + "'");
        fieldRights.clear();
        for (Object obj : roleFields) {
            Object[] objs = (Object[]) obj;
            fieldRights.put(objs[0].toString().toUpperCase(), SysUtil.objToInt(objs[1]));
        }
    }

    public static List<String> getDept_codes() {
        return dept_codes;
    }
    //初始化部门权限，并将权限部门加入缓存

    public static void initDeptRight() {
        if (isSA) {
            //getDepts();
            return;
        }
        dept_codes.clear();
        List list = CommUtil.selectSQL("select d.dept_code from deptcode d,roledept rd,rolea01 ra where d.deptCode_key=rd.deptcode_key and rd.rolea01_key=ra.rolea01_key and ra.role_key='" + role_id + "' and ra.a01password_key='" + passKey + "' and d.del_flag=0");
        String strWhere = "";
        String strWhere1 = "";
        for (Object obj : list) {
            dept_codes.add(obj.toString());
            if (sql_dialect.equals("sqlserver")) {
                strWhere = strWhere + " OR (d.dept_code LIKE '" + obj.toString() + "%" + "' or charindex(d.dept_code, '" + obj.toString() + "')=1)";
            } else if (sql_dialect.equals("oracle")) {
                strWhere = strWhere + " OR (d.dept_code LIKE '" + obj.toString() + "%" + "' or instr( '" + obj.toString() + "',d.dept_code)=1)";
            } else if (sql_dialect.equals("db2")) {
                strWhere = strWhere + " OR (d.dept_code LIKE '" + obj.toString() + "%" + "' or LOCATE(d.dept_code, '" + obj.toString() + "')=1)";
            }
            strWhere1 = strWhere1 + " OR d.dept_code LIKE '" + obj.toString() + "%'";
        }
        if (!strWhere.equals("")) {
            dept_right_str = "d.del_flag=0 and (" + strWhere.substring(4) + ")";
        } else {
            dept_right_str = "1=0";
        }
        if (!strWhere1.equals("")) {
            dept_right_rea_str = "d.del_flag=0 and (" + strWhere1.substring(4) + ")";
            person_right_str = strWhere1.substring(4);
            person_right_str = "select deptCode_key from DeptCode d where " + person_right_str;
        } else {
            person_right_str = "'-1'";
        }
        //getDepts();
    }

    public static void setLanguage(String language) {
        UserContext.language = language;
        EntityBuilder.ht_field_infos.clear();
        WebHrMessage.initInitationRes(language);
    }

    public static String getFuntionName(String fun_menu_code, String default_value) {
        default_value = default_value == null ? "" : default_value;
        FuntionRight result = funtion_keys.get(fun_menu_code);
        if (result != null) {
            default_value = result.getFun_name();
        }
        String value = WebHrMessage.getString(fun_menu_code);
        value = value == null ? default_value : value;
        return value;
    }

    public static String getFuntionName(String fun_menu_code) {
        return getFuntionName(fun_menu_code, null);
    }

    public static int getReportRight(String reportDef_key) {
        int i = 0;
        if (UserContext.sysManName.equals(person_code)) {
            return 1;
        }
        if (reportRights.get(reportDef_key) != null) {
            i = reportRights.get(reportDef_key);
        }
        return i;
    }

    public static boolean hasReportRight(String reportDef_key) {
        if (UserContext.sysManName.equals(person_code)) {
            return true;
        }
        if (reportRights.get(reportDef_key) != null) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否拥有当前表的新增权限
     * @param entity_name：表名
     * @return
     */
    public static boolean hasEntityAddRight(String entity_name) {
        if (entity_name == null || entity_name.equals("")) {
            return false;
        }
        if (UserContext.sysManName.equals(person_code)) {
            return true;
        }
        entity_name = entity_name.toUpperCase();
        if (hasEntityRight(entity_name)) {
            RoleEntity re = entityRights.get(entity_name);
            return re.isAdd_flag();
        }
        return false;
    }

    /**
     * 判断是否拥有当前表的编辑权限
     * @param entity_name：表名
     * @return
     */
    public static boolean hasEntityEditRight(String entity_name) {
        if (entity_name == null || entity_name.equals("")) {
            return false;
        }
        if (UserContext.sysManName.equals(person_code)) {
            return true;
        }
        entity_name = entity_name.toUpperCase();
        if (hasEntityRight(entity_name)) {
            RoleEntity re = entityRights.get(entity_name);
            return re.isEdit_flag();
        }
        return false;
    }

    /**
     * 判断是否拥有当前表的删除权限
     * @param entity_name：表名
     * @return
     */
    public static boolean hasEntityDelRight(String entity_name) {
        if (entity_name == null || entity_name.equals("")) {
            return false;
        }
        if (UserContext.sysManName.equals(person_code)) {
            return true;
        }
        entity_name = entity_name.toUpperCase();
        if (hasEntityRight(entity_name)) {
            RoleEntity re = entityRights.get(entity_name);
            return re.isDel_flag();
        }
        return false;
    }

    /**
     * 判断是否拥有当前表的查看权限
     * @param entity_name：表名
     * @return
     */
    public static boolean hasEntityViewRight(String entity_name) {
        if (UserContext.sysManName.equals(person_code)) {
            return true;
        }
        if (entity_name == null) {
            return false;
        }
        entity_name = entity_name.toUpperCase();
        if (hasEntityRight(entity_name)) {
            RoleEntity re = entityRights.get(entity_name);
            return re.isView_flag();
        }
        return false;
    }

    private static boolean hasEntityRight(String entity_name) {
        if (UserContext.sysManName.equals(person_code)) {
            return true;
        }
        if (entity_name == null) {
            return false;
        }
        entity_name = entity_name.toUpperCase();
        if (entityRights.keySet().contains(entity_name)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否拥有当前编码的新增权限
     * @param entity_name：编码主键
     * @return
     */
    public static boolean hasCodeAddRight(Code code) {
        if (UserContext.sysManName.equals(person_code)) {
            return true;
        }
        RoleCode re = codeRights.get(code.getCode_tag().substring(0, 2));
        if (re != null) {
            if (!re.isAdd_flag()) {
                return false;
            }
            if (re.getEdit_sql() != null && !re.getEdit_sql().trim().equals("")) {
                String[] tmp = re.getEdit_sql().split("\\;");
                for (String key : tmp) {
                    if (code.getCode_id().startsWith(key)) {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否拥有当前编码的编辑权限
     * @param entity_name：编码主键
     * @return
     */
    public static boolean hasCodeEditRight(Code code) {
        if (UserContext.sysManName.equals(person_code)) {
            return true;
        }
        RoleCode re = codeRights.get(code.getCode_tag().substring(0, 2));
        if (re != null) {
            if (!re.isEdit_flag()) {
                return false;
            }
            if (re.getEdit_sql() != null && !re.getEdit_sql().trim().equals("")) {
                String[] tmp = re.getEdit_sql().split("\\;");
                for (String key : tmp) {
                    if (code.getCode_id().startsWith(key)) {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否拥有当前编码的查看权限
     * @param entity_name：编码主键
     * @return
     */
    public static boolean hasCodeViewRight(Code code) {
        if (UserContext.sysManName.equals(person_code)) {
            return true;
        }
        if (code.getCode_tag().length() < 2) {
            return false;
        }
        RoleCode re = codeRights.get(code.getCode_tag().substring(0, 2));
        if (re != null) {
            if (!re.isView_flag()) {
                return false;
            }
            if (code.getParent_id().equalsIgnoreCase("ROOT")) {
                return true;
            }
            if (re.getRight_sql() != null && !re.getRight_sql().trim().equals("")) {
                String[] tmp = re.getRight_sql().split("\\;");
                for (String key : tmp) {
                    if (code.getCode_id().startsWith(key)) {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否拥有当前编码的删除权限
     * @param entity_name：编码主键
     * @return
     */
    public static boolean hasCodeDelRight(Code code) {
        if (UserContext.sysManName.equals(person_code)) {
            return true;
        }
        RoleCode re = codeRights.get(code.getCode_tag().substring(0, 2));
        if (re != null) {
            if (!(re.isDel_flag() && re.isEdit_flag() && re.isView_flag())) {
                return false;
            }
            if (re.getEdit_sql() != null && !re.getEdit_sql().trim().equals("")) {
                String[] tmp = re.getEdit_sql().split("\\;");
                for (String key : tmp) {
                    if (code.getCode_id().startsWith(key)) {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public static boolean hasFieldRight(String field_name) {
        if (UserContext.sysManName.equals(person_code)) {
            return true;
        }
        if (fieldRights.get(field_name) != null) {
            return true;
        }
        return false;
    }

    public static int getFieldRight(Class c, String field_name) {
        if (UserContext.sysManName.equals(person_code)) {
            return 1;
        }
        if (c == null || field_name == null) {
            return 0;
        }
        String entityName = c.getSimpleName().toUpperCase();
        field_name = SysUtil.tranField(field_name.toUpperCase());
        if (EntityBuilder.ht_fields.contains(entityName + "." + field_name)) {
            Integer i = fieldRights.get(entityName + "." + field_name);
            if (i != null) {
                return i;
            }
            return 0;
        } else if (c.getSuperclass().getName().startsWith("org.jhrcore.entity")) {
            entityName = c.getSuperclass().getSimpleName().toUpperCase();
            if (EntityBuilder.ht_fields.contains(entityName + "." + field_name)) {
                Integer i = fieldRights.get(entityName + "." + field_name);
                if (i != null) {
                    return i;
                }
                return 0;
            }
        }
        return 1;
    }

    public static int getFieldRight(String field_name) {
        if (UserContext.isSA) {
            return 1;
        }
        int i = 0;
        field_name = SysUtil.tranField(field_name);
        if (fieldRights.get(field_name.toUpperCase()) == null) {
            int ind = field_name.indexOf(".");
            String entityName = field_name.substring(0, ind);
            if (!EntityBuilder.getHt_entity_names().containsValue(entityName)) {
                return 1;
            }

            String full_entity_name = EntityBuilder.getHt_entity_classes().get(entityName);
            if (full_entity_name == null || "".equals(full_entity_name)) {
                return 0;
            }
            try {
                Class c = Class.forName(full_entity_name);
                if (c == null) {
                    return 0;
                }
                return getFieldRight(c, field_name.substring(ind + 1));
            } catch (ClassNotFoundException ex) {
                log.error(ex);
            }
        } else {
            i = fieldRights.get(field_name.toUpperCase());
        }
        return i;
    }

    public static boolean hasFunctionRight(String fun_menu_code) {
        if (UserContext.sysManName.equals(person_code)) {
            return true;
        }
        return funtionRights.contains(fun_menu_code);
    }

    public static void addUserFuntion(String key) {
        Object obj = CommUtil.fetchEntityBy("from FuntionRight fr where fr.fun_module_flag='" + key + "'");
        if (obj != null) {
            addUserFuntion((FuntionRight) obj);
        }
    }

    public static void addUserFuntion(FuntionRight fr) {
        funtionRights.add(fr.getFun_module_flag());
        funtion_keys.put(fr.getFun_module_flag(), fr);
        funtion_keys.put(fr.getFuntionRight_key(), fr);
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserContext.password = password;
    }

    public static String getCurPerson() {
        return person_name + "{" + person_code + "}";
    }

    public static String getPerson_code() {
        return person_code;
    }

    public static void setPerson_code(String person_code) {
        UserContext.person_code = person_code;
    }

    public static Hashtable<String, Integer> getReportRights() {
        return reportRights;
    }

    public static Hashtable<String, FuntionRight> getFuntion_keys() {
        return funtion_keys;
    }

    public static String getK_table() {
        return k_table;
    }
    public static List<TempFieldInfo> getWriteFieldInfosByEntity(Class c) {
        List<TempFieldInfo> infos = EntityBuilder.getCommFieldInfoListOf(c);
        List<TempFieldInfo> result = new ArrayList<TempFieldInfo>();
        for (TempFieldInfo tfi : infos) {
            if (getFieldRight(c, tfi.getField_name()) == 1) {
                result.add(tfi);
            }
        }
        return result;
    }

    public static List<String> getWriteFieldsByEntity(Class c) {
        List<TempFieldInfo> infos = EntityBuilder.getCommFieldInfoListOf(c);
        List<String> result = new ArrayList<String>();
        for (TempFieldInfo tfi : infos) {
            if (getFieldRight(c, tfi.getField_name()) == 1) {
                result.add(tfi.getField_name());
            }
        }
        return result;
    }
}
