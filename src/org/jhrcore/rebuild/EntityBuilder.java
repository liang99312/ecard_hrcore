package org.jhrcore.rebuild;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import java.util.Set;
import javax.persistence.GenerationType;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import javax.persistence.FetchType;
import org.apache.log4j.Logger;
import org.jhrcore.util.SysUtil;
import org.jhrcore.client.UserContext;

import org.jhrcore.util.PublicUtil;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.EnumHint;
import org.jhrcore.entity.annotation.FieldAnnotation;
import org.jhrcore.entity.annotation.ObjectListHint;
import org.jhrcore.entity.base.EntityClass;
import org.jhrcore.entity.base.EntityDef;
import org.jhrcore.entity.base.FieldDef;
import org.jhrcore.entity.base.ModuleInfo;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.ui.language.WebHrMessage;
import org.jhrcore.util.FormatUtil;

public class EntityBuilder {

    public static int COMM_FIELD_ALL = 0;
    public static int COMM_FIELD_VISIBLE = 1;
    public static int COMM_FIELD_VISIBLE_ALL = 2;
//    public static Hashtable<String, Object> ht_field_right = null;
    // 表示是否是创建编码(Code)类型的字段，对于关联编码的字段，第一次创建的时候create_code_field = false;
    // 仅创建字符串类型字段，然后把create_code_field设置成true，来创建编码类型字段，编码类型字段在设置的时候
    // 同时设置编码字段
    public static boolean create_code_field = false;
    private static int base_order_no = 0;
    // 保存从某一个基类继承下来的类
    private static Hashtable<String, List<String>> ht_subclasses = new Hashtable<String, List<String>>();
    // 保存实体显示名称和类名的对应
    private static Hashtable<String, String> ht_entity_names = new Hashtable<String, String>();
    // 保存类名到类全称的对应，比如BasePerson, org.jhrcore.entity.BasePerson
    private static Hashtable<String, String> ht_entity_classes = new Hashtable<String, String>();
    public static Hashtable<String, Hashtable<String, TempFieldInfo>> ht_field_infos = new Hashtable<String, Hashtable<String, TempFieldInfo>>();
    public static HashSet<String> ht_fields = new HashSet<String>();//保存数据重构字段
    public static boolean init_class_path = false;//是否在重构时将类加载到当前运行路径，主要用于TOMCAT等web容器的类加载
    private static Logger log = Logger.getLogger(EntityBuilder.class.getName());
    private static boolean g10_flag = false;
    private final static Hashtable<String, String> packageMap = new Hashtable<String, String>();
    private final static Hashtable<String, String> superEntityMap = new Hashtable<String, String>();
    private final static Hashtable<String, CtClass> ctTypeMap = new Hashtable<String, CtClass>();
    private static StringBuilder defaultMethBuilder = new StringBuilder();
    private static StringBuilder setterBuilder = new StringBuilder();
    private final static Hashtable<String, String[]> entityDefaultFields = new Hashtable();

    static {
        ctTypeMap.put("int", CtClass.intType);
        ctTypeMap.put("float", CtClass.floatType);
        ctTypeMap.put("boolean", CtClass.booleanType);
        //指定各模块的包路径
        packageMap.put("PAY", "org.jhrcore.entity.salary.");
        packageMap.put("PX", "org.jhrcore.entity.peixun.");
        packageMap.put("PX2", "org.jhrcore.entity.train.");
        packageMap.put("KAOQIN", "org.jhrcore.entity.kaoqin.");
        packageMap.put("K", "org.jhrcore.entity.kaoqin.");
        packageMap.put("JIJIAN", "org.jhrcore.entity.jijian.");
        packageMap.put("BX", "org.jhrcore.entity.insurance.");
        packageMap.put("ZP", "org.jhrcore.entity.job.");
        packageMap.put("JX", "org.jhrcore.entity.jixiao.");
        packageMap.put("LDDE", "org.jhrcore.entity.ldde.");
        packageMap.put("ZY", "org.jhrcore.entity.skills.");
        packageMap.put("DA", "org.jhrcore.entity.dangan.");
        packageMap.put("LB", "org.jhrcore.entity.lbyp.");
        packageMap.put("LBYP", "org.jhrcore.entity.lbyp.");
        packageMap.put("V", "org.jhrcore.entity.vacation.");
        packageMap.put("Z", "org.jhrcore.entity.rszp.");
        packageMap.put("PAYSALES", "org.jhrcore.entity.salary.");
        packageMap.put("RSZP", "org.jhrcore.entity.rszp.");
        packageMap.put("DINGDAN", "org.jhrcore.entity.dingdan.");
        packageMap.put("DD", "org.jhrcore.entity.dingdan.");
        packageMap.put("TWFD", "org.jhrcore.entity.twfund.");
        packageMap.put("XTYW", "org.jhrcore.entity.base.backdata.");
        packageMap.put("TWKH", "org.jhrcore.entity.twkh.");
        packageMap.put("TWFL", "org.jhrcore.entity.twfl.");
        packageMap.put("TWJP", "org.jhrcore.entity.twjp.");
        packageMap.put("TWFUND", "org.jhrcore.entity.twfund.");
        packageMap.put("TWPY", "org.jhrcore.entity.twpy.");
        packageMap.put("SELFAPPLY", "org.jhrcore.entity.self.apply.");
        packageMap.put("ECARD", "org.jhrcore.entity.ecard.");

        //-------------------------------------------------------------
        //指定各业务模型的父类，用于JAVA重写表
        // 人事管理, 人员类别  
        superEntityMap.put("CLASS", "org.jhrcore.entity.A01");
        // 人事管理, 人员附表
        superEntityMap.put("ANNEX", "org.jhrcore.entity.BasePersonAppendix");
        // 部门机构管理, 部门附表
        superEntityMap.put("DEPT", "org.jhrcore.entity.BaseDeptAppendix");
        // 岗位管理, 岗位附表
        superEntityMap.put("GWZJ", "org.jhrcore.entity.BasePositionAppendix");
        // 劳动合同, 其他协议
        superEntityMap.put("HT", "org.jhrcore.entity.BaseContractAppendix");
        // 工资子集表
        superEntityMap.put("GZZJ", "org.jhrcore.entity.salary.Pay");
        // 工资总额表
        superEntityMap.put("GZZE", "org.jhrcore.entity.salary.PayDept");
        // 薪酬对照表
        superEntityMap.put("XCDZ", "org.jhrcore.entity.salary.BasePayCompare");
        // 招聘附表
        superEntityMap.put("ZPZJ", "org.jhrcore.entity.job.Zp_resumeAppendix");
        superEntityMap.put("ZPWPFB", "org.jhrcore.entity.job.Zp_wpfb");
        superEntityMap.put("ZPSTDFB", "org.jhrcore.entity.job.Zp_stdfb");
        superEntityMap.put("ZPWZZD", "org.jhrcore.entity.job.Zp_wzzd");
        // 招聘附表
        superEntityMap.put("ZPFB", "org.jhrcore.entity.rszp.Z_appendix");
        //保险 核算表
        superEntityMap.put("BXHS", "org.jhrcore.entity.insurance.In_account");
        //人事调动表
        superEntityMap.put("RSDD", "org.jhrcore.entity.BasePersonChange");
        //业务通知单表
        superEntityMap.put("YWTZ", "org.jhrcore.entity.Notice");
        superEntityMap.put("SJBF", "org.jhrcore.entity.base.backdata.SysBackData");
        //高校绩效工作量表和科研表
        superEntityMap.put("GXJXGZL", "org.jhrcore.entity.gxjx.Gxjx_gzl");
        superEntityMap.put("GXJXKY", "org.jhrcore.entity.gxjx.Gxjx_ky");
        //自助申请表
        superEntityMap.put("SELFAPPLYFB", "org.jhrcore.entity.self.apply.BaseSelfApply");
        //新汶劳动定额表
        superEntityMap.put("XWDE", "org.jhrcore.entity.ldde.D_basexw");
        //绩效部门和员工数据采集表
        superEntityMap.put("JXPLANDEPTCOL", "org.jhrcore.entity.jixiao.J_plandeptCol");
        superEntityMap.put("JXPLANEMPCOL", "org.jhrcore.entity.jixiao.J_plana01Col");

        entityDefaultFields.put("DeptCode", new String[]{"dept_code", "content"});
        entityDefaultFields.put("PayDeptBack", new String[]{"dept_code", "content"});
        entityDefaultFields.put("A01", new String[]{"a0190", "a0101", "a0191"});
        entityDefaultFields.put("C21", new String[]{"a0190", "a0101", "a0191"});
        entityDefaultFields.put("Da_fenlei", new String[]{"fenlei_name", "fenlei_hao"});
        entityDefaultFields.put("P_topic", new String[]{"topic_name", "topic_group"});
        entityDefaultFields.put("P_course", new String[]{"course_name"});
        entityDefaultFields.put("Px_course", new String[]{"course_no", "course_mc"});
        entityDefaultFields.put("Px_teacher", new String[]{"teacher_name"});
        entityDefaultFields.put("P_dept", new String[]{"dept_name"});
        entityDefaultFields.put("G10", new String[]{"position_name"});
        entityDefaultFields.put("K_kaoqin_a01", new String[]{"kaoqin_a01_hao"});
        entityDefaultFields.put("PayAppPlan", new String[]{"plan_name"});
        entityDefaultFields.put("PayAppDef", new String[]{"app_name"});
        entityDefaultFields.put("Da_a01", new String[]{"a0190", "a0101"});
        entityDefaultFields.put("L_good", new String[]{"good_name"});
        entityDefaultFields.put("Zy_plan", new String[]{"plan_name"});
        entityDefaultFields.put("Zy_batchcenter", new String[]{"center_name"});
        entityDefaultFields.put("EntityDef", new String[]{"entityCaption", "entityName"});
        entityDefaultFields.put("FieldDef", new String[]{"field_name", "field_caption", "code_type_name"});
        entityDefaultFields.put("SysNotice", new String[]{"title", "author", "subdate"});
        entityDefaultFields.put("D_gxtype", new String[]{"d_name"});
        entityDefaultFields.put("In_payunit", new String[]{"uname"});

    }

    /**
     * 该方法用于通过传入的字段全名来获得系统tempfieldinfo属性
     * @param full_field_name：字段全称，如：C21.a0101
     * @return
     */
    public static TempFieldInfo getTempFieldInfoByName(String full_field_name) {
        if (full_field_name == null) {
            return null;
        }
        if (full_field_name.startsWith("#")) {
            full_field_name = full_field_name.substring(1);
        }
        String[] fields = full_field_name.split("\\.");
        int len = fields.length;
        if (len <= 1) {
            return null;
        }
        len = len - 2;
        String entityName = fields[len].substring(0, 1).toUpperCase() + fields[len].substring(1);
        len = len + 1;
        return getTempFieldInfoByName(entityName, fields[len], false);
    }

    /**Field_mark
     * 该方法用于通过传入的表名、字段名来获得系统tempfieldinfo属性
     * @param entityName：表名
     * @param field_name：字段名
     * @return
     */
    public static TempFieldInfo getTempFieldInfoByName(String entityName, String field_name, boolean full_name) {
        if (entityName == null || field_name == null) {
            return null;
        }
        Hashtable<String, TempFieldInfo> fields = ht_field_infos.get(entityName);
        if (fields == null) {
            fields = new Hashtable<String, TempFieldInfo>();
            String fullEntityName;
            if (full_name) {
                fullEntityName = entityName;
            } else {
                fullEntityName = ht_entity_classes.get(entityName);
            }
            if (fullEntityName == null) {
                return null;
            }
            try {
                Class cs = Class.forName(fullEntityName);
                List<TempFieldInfo> infos = getCommFieldInfoListOf(cs, EntityBuilder.COMM_FIELD_ALL);
                for (TempFieldInfo tfi : infos) {
                    fields.put(tfi.getField_name(), tfi);
                }
            } catch (ClassNotFoundException ex) {
                log.error(ex);
                return null;
            }
            ht_field_infos.put(entityName, fields);
        }
        return fields.get(field_name);
    }

    public static void putInitClass() {
        try {
            Set<String> cs = SysUtil.getAllClassNames("org.jhrcore.entity");
            for (String fullClassName : cs) {
                ht_entity_classes.put(fullClassName.substring(fullClassName.lastIndexOf(".") + 1), fullClassName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void buildEntities(List<ModuleInfo> list) {
        for (ModuleInfo moduleInfo : list) {
            UserContext.modules.put(moduleInfo.getModule_key(), moduleInfo);
            UserContext.modules.put(moduleInfo.getModule_code(), moduleInfo);
            for (int i = 0; i < moduleInfo.getEntityClasss().size(); i++) {
                EntityClass entityClass = (EntityClass) moduleInfo.getEntityClasss().toArray()[i];
                for (int j = 0; j < entityClass.getEntityDefs().size(); j++) {
                    EntityDef entityDef = (EntityDef) entityClass.getEntityDefs().toArray()[j];
                    buildEntity(entityDef);
                }
            }
        }
        ClassPool cp = ClassPool.getDefault();
        for (ModuleInfo mi : list) {
            String packageName = getPackage(mi);
            for (int i = 0; i < mi.getEntityClasss().size(); i++) {
                EntityClass entityClass = (EntityClass) mi.getEntityClasss().toArray()[i];
                for (int j = 0; j < entityClass.getEntityDefs().size(); j++) {
                    EntityDef entityDef = (EntityDef) entityClass.getEntityDefs().toArray()[j];
                    if (!entityDef.isSuccess_build()) {
                        continue;
                    }
                    String entityName = packageName + entityDef.getEntityName();
                    CtClass entity_ctclass = null;
                    try {
                        entity_ctclass = cp.get(entityName);
                    } catch (NotFoundException e1) {
//                        System.err.println(entityName+";"+e1);
                        log.error(e1);
                    }
                    if (entity_ctclass == null) {
                        continue;
                    }
                    try {
                        entity_ctclass.toClass();
                        //log.error("addClass:" + entity_ctclass.getName());
                    } catch (CannotCompileException e) {
                        e.printStackTrace();
                        log.error(e);
                        continue;
                    }
                }
            }
        }
    }

    public static Hashtable<String, String> getHt_entity_classes() {
        return ht_entity_classes;
    }

    public static Hashtable<String, String> getHt_entity_names() {
        return ht_entity_names;
    }

    public static Hashtable<String, List<String>> getHt_subclasses() {
        return ht_subclasses;
    }

    public static void putClassInherited(String superClass, String subClass) {
        List<String> list = ht_subclasses.get(superClass);
        if (list == null) {
            list = new ArrayList<String>();
            ht_subclasses.put(superClass, list);
        }
        list.add(subClass);
    }

    private static String getSuperEntity(EntityDef entityDef) {
        base_order_no = 1000;
        String entityType = entityDef.getEntityClass().getEntityType_code();
        entityType = entityType == null ? "" : entityType.toUpperCase();
        String superEntity = superEntityMap.get(entityType);
        if (superEntity != null) {
            return superEntity;
        }
        base_order_no = 0;
        return "com.jgoodies.binding.beans.Model";
    }

    private static CtClass getFieldCtClass(FieldDef fieldDef) {
        String type_name = fieldDef.getField_type();
        boolean code_flag = fieldDef.getCode_type_name() != null && !"String".equals(fieldDef.getCode_type_name()) && "String".equals(type_name);
        if (code_flag && create_code_field) {
            type_name = "org.jhrcore.entity.Code";
        } else if (fieldDef.getEntityDef().getEntityClass().getEntityType_code().equals("RSDD") && (fieldDef.getField_name().equals("old_deptCode") || fieldDef.getField_name().equals("new_deptCode"))) {
            type_name = "org.jhrcore.entity.DeptCode";
        } else if (fieldDef.getEntityDef().getEntityClass().getEntityType_code().equals("RSDD") && (fieldDef.getField_name().equals("old_g10") || fieldDef.getField_name().equals("new_g10"))) {
            type_name = "org.jhrcore.entity.G10";
        }
        return getFieldCtClass(type_name);
    }

    private static CtClass getFieldCtClass(String type_name) {
        CtClass cs = ctTypeMap.get(type_name);
        if (cs == null) {
            ClassPool cp = ClassPool.getDefault();
            try {
                ctTypeMap.put("String", cp.get("java.lang.String"));
                ctTypeMap.put("Date", cp.get("java.util.Date"));
                ctTypeMap.put("Float", cp.get("java.math.BigDecimal"));
                ctTypeMap.put("Boolean", cp.get("java.lang.Boolean"));
                ctTypeMap.put("Integer", cp.get("java.lang.Integer"));
                cs = ctTypeMap.get(type_name);
                if (cs == null) {
                    cs = cp.get(type_name);
                    ctTypeMap.put(type_name, cs);
                }
            } catch (Exception ex) {
            }
        }
        return cs;
    }

    public static String getPackage(EntityDef entityDef) {
        if (entityDef.getPackageName() != null && !entityDef.getPackageName().trim().equals("")) {
            return entityDef.getPackageName();
        }
        return getPackage(entityDef.getEntityClass().getModuleInfo());
    }

    public static String getPackage(ModuleInfo mi) {
        String pack = SysUtil.objToStr(mi.getPackageName()).trim();
        if (pack.equals("")) {
            String module_code = mi.getModule_code();
            module_code = SysUtil.objToStr(module_code).toUpperCase();
            pack = packageMap.get(module_code);
            pack = pack == null ? "org.jhrcore.entity." : pack;
        }
        if (!pack.endsWith(".")) {
            pack += ".";
        }
        if (!pack.endsWith(".")) {
            pack = pack + ".";
        }
        return pack;
    }

    private static void addStringMember(Annotation annotation, ConstPool pool, String memberName, String value) {
        annotation.addMemberValue(memberName, new StringMemberValue(value, pool));
    }

    private static void addBooleanMember(Annotation annotation, ConstPool pool, String memberName, boolean value) {
        annotation.addMemberValue(memberName, new BooleanMemberValue(value, pool));
    }

    private static void addIntMember(Annotation annotation, ConstPool pool, String memberName, int value) {
        annotation.addMemberValue(memberName, new IntegerMemberValue(pool, value));
    }

    private static void buildEntity(EntityDef entityDef) {
        ht_entity_names.put(entityDef.getEntityCaption(), entityDef.getEntityName());
        String packageName = getPackage(entityDef);
        String entityName = entityDef.getEntityName();
        String className = packageName + entityName;
        ht_entity_classes.put(entityName, className);
        ht_entity_classes.put(entityName.toUpperCase(), className);
        ClassPool cp = ClassPool.getDefault();
        CtClass entity_ctclass = null;
        String superclass = getSuperEntity(entityDef);
        try {
            ClassFile classFile = null;
            putClassInherited(superclass, className);
            if (init_class_path) {
                cp.insertClassPath(new ClassClassPath(cp.getClass()));
            }
            if (cp.find(className) == null) {
                entity_ctclass = cp.makeClass(className, cp.get(superclass));
                entity_ctclass.addInterface(cp.get("java.io.Serializable"));
                classFile = entity_ctclass.getClassFile();
                ConstPool constPool = classFile.getConstPool();
                AnnotationsAttribute entity_attr = new AnnotationsAttribute(
                        constPool, AnnotationsAttribute.visibleTag);
                Annotation entity_annotation = new Annotation(
                        "javax.persistence.Entity", constPool);
                entity_attr.addAnnotation(entity_annotation);
                entity_annotation = new Annotation("org.jhrcore.entity.annotation.ClassAnnotation", constPool);
                String displayName = SysUtil.objToStr(entityDef.getEntityCaption());
                String moduleName = SysUtil.objToStr(entityDef.getEntityClass().getModuleInfo().getModule_name());
                addStringMember(entity_annotation, constPool, "displayName", displayName);
                addStringMember(entity_annotation, constPool, "moduleName", moduleName);
                entity_attr.addAnnotation(entity_annotation);
                classFile.addAttribute(entity_attr);
                classFile.setVersionToJava5();
            } else {
                base_order_no = 0;
                entity_ctclass = cp.get(className);
            }

        } catch (Exception e) {
            log.error(e);
        }
        if (entity_ctclass == null) {
            return;
        }
        try {
            PublicUtil.addSuccess_property(entityName);
            entityDef.setSuccess_build(true);
            //System.out.println("build entity<<<<<<<<<:" + entityName);

            ConstPool pool = entity_ctclass.getClassFile().getConstPool();
            int field_index = 0;
            defaultMethBuilder.setLength(0);
            while (field_index < entityDef.getFieldDefs().size()) {
                FieldDef fieldDef = (FieldDef) entityDef.getFieldDefs().toArray()[field_index];
                String field_name = fieldDef.getField_name();
                ht_fields.add(entityName.toUpperCase() + "." + field_name.toUpperCase());
                PublicUtil.addSuccess_property(entityName + "." + field_name);
                if (field_name.equals("a01_key") && !entityDef.getEntityClass().getModuleInfo().getModule_code().equals("XTYW")) {
                    field_index++;
                    continue;
                }
                if (create_code_field) {
                    field_name = field_name + "_code_";
                }
                CtField field = null;
                for (CtField tmp_ctField : entity_ctclass.getFields()) {
                    if (tmp_ctField.getName().equals(field_name)) {
                        field = tmp_ctField;
                        break;
                    }
                }
                AnnotationsAttribute field_attr = new AnnotationsAttribute(pool,
                        AnnotationsAttribute.visibleTag);
                Annotation field_annotation;
                boolean isNewField = field == null;
                boolean isDept = fieldDef.getEntityDef().getEntityClass().getEntityType_code().equals("RSDD") && (field_name.equalsIgnoreCase("old_deptCode") || field_name.equalsIgnoreCase("new_deptCode"));
                boolean isG10 = fieldDef.getEntityDef().getEntityClass().getEntityType_code().equals("RSDD") && (fieldDef.getField_name().equals("old_g10") || fieldDef.getField_name().equals("new_g10"));
                String field_type = fieldDef.getField_type().toLowerCase();
                if (isNewField) {
                    CtClass field_type_class = getFieldCtClass(fieldDef);
                    CtClass[] NO_ARGS = {};
                    CtClass[] SET_STR_ARGS = {field_type_class};
                    try {
                        field = new CtField(field_type_class, field_name,
                                entity_ctclass);
                    } catch (CannotCompileException e1) {
                        log.error(e1);
                    }
                    field.setModifiers(Modifier.PUBLIC);
//                    if (field_name.equals("id") || field_name.endsWith("_key")) {
                    if (field_name.equalsIgnoreCase(entityName + "_key")) {
//                        if (!field_name.toUpperCase().equals("A_ID")) {
                        field_annotation = new Annotation("javax.persistence.Id", pool);
                        field_attr.addAnnotation(field_annotation);
                        CtClass typeClazz = null;
                        try {
                            typeClazz = cp.get(GenerationType.class.getName());
                        } catch (NotFoundException e) {
                            log.error(e);
                        }
                        field_annotation = new Annotation(
                                "javax.persistence.GeneratedValue", pool);
                        EnumMemberValue enumMemberValue = new EnumMemberValue(pool);
                        enumMemberValue.setType(typeClazz.getName());
                        enumMemberValue.setValue(GenerationType.IDENTITY.name());
                        field_annotation.addMemberValue("strategy", enumMemberValue);
                        field_attr.addAnnotation(field_annotation);
                    }
//                    }
                    try {
                        entity_ctclass.addField(field);
                    } catch (CannotCompileException e1) {
                        log.error(e1);
                    }
                    String fieldMeth = field_name.substring(0, 1).toUpperCase() + field_name.substring(1);
                    CtMethod meth = new CtMethod(field_type_class, "get" + fieldMeth, NO_ARGS, entity_ctclass);
                    if (create_code_field) {
                        AnnotationsAttribute meth_attr = new AnnotationsAttribute(pool,
                                AnnotationsAttribute.visibleTag);
                        Annotation meth_annotation = new Annotation(
                                "javax.persistence.Transient", pool);
                        meth_attr.addAnnotation(meth_annotation);
                        meth.getMethodInfo2().addAttribute(meth_attr);
                    } else if (isDept || isG10) { // 如果是部门或者岗位类型,则增加影射注解
                        AnnotationsAttribute meth_attr = new AnnotationsAttribute(pool,
                                AnnotationsAttribute.visibleTag);
                        Annotation meth_annotation = new Annotation(
                                "javax.persistence.ManyToOne", pool);

                        CtClass typeClazz = null;
                        try {
                            typeClazz = cp.get(FetchType.class.getName());
                        } catch (NotFoundException e) {
                            log.error(e);
                        }
                        EnumMemberValue enumMemberValue = new EnumMemberValue(pool);
                        enumMemberValue.setType(typeClazz.getName());
                        enumMemberValue.setValue(FetchType.EAGER.name());
                        meth_annotation.addMemberValue("fetch", enumMemberValue);
                        meth_attr.addAnnotation(meth_annotation);
                        meth_annotation = new Annotation("javax.persistence.JoinColumn", pool);
                        addStringMember(meth_annotation, pool, "name", field_name + "_key");
                        meth_attr.addAnnotation(meth_annotation);
                        meth.getMethodInfo2().addAttribute(meth_attr);
                    }
                    // //////////////////////////////////////////////////////////////

                    meth.setModifiers(Modifier.PUBLIC);
                    try {
                        if (!create_code_field) {
                            setterBuilder.append("{return ").append(field_name).append(";}");
                            String defaultValue = getDefault_value(field_type, fieldDef);
                            if (defaultValue != null) {
                                if (defaultValue.contains("#result")) {
                                    defaultMethBuilder.append("{if (").append(field_name).append("== null){ ").append(defaultValue.substring(0, defaultValue.indexOf("#result")).replace("#", "")).append(field_name).append("=").append(defaultValue.substring(defaultValue.indexOf("#result") + 7).replace("=", "")).append(";}}\n");
                                } else {
                                    if (field_type.equals("float")) {
                                        defaultMethBuilder.append("{if (").append(field_name).append("== null||").append(field_name).append(".equals(java.math.BigDecimal.valueOf(0d))){").append(field_name).append("=").append(defaultValue.replace("#", "")).append(";}}\n");
                                    } else {
                                        defaultMethBuilder.append("{if (").append(field_name).append("== null||").append(field_name).append(".equals(java.lang.Integer.valueOf(0))){").append(field_name).append("=").append(defaultValue.replace("#", "")).append(";}}\n");
                                    }
                                }
                            }
                        } else {
                            String default_value = getDefault_value("code", fieldDef);//fieldDef.getDefault_value() == null ? "" : fieldDef.getDefault_value().trim();
                            setterBuilder.append("{if (").append(field_name).append("== null)").append(field_name).append("=org.jhrcore.comm.CodeManager.getCodeManager().getCodeBy(\"").append(fieldDef.getCode_type_name()).append("\",").append(fieldDef.getField_name()).append(");return ").append(field_name).append(";}");
                            if (default_value != null && !default_value.trim().equals("")) {
                                if (default_value.contains("#")) {
                                    default_value = default_value.substring(1);
                                    if (default_value.contains("#result")) {
                                        String d_value = default_value.substring(default_value.indexOf("#result") + 7).replace("=", "");
                                        d_value = d_value.endsWith("\"") ? d_value.substring(0, d_value.length() - 1) : d_value;
                                        defaultMethBuilder.append("{if (").append(field_name).append("== null){ ").append(default_value.substring(0, default_value.indexOf("#result")).replace("#", "")).append(field_name).append("=").append(d_value).append(";}}\n");
                                    } else {
                                        defaultMethBuilder.append("{if (").append(field_name).append("== null){").append(field_name).append("=").append(default_value.replace("#", "").replace("\"", "")).append(";}}\n");
                                    }
                                } else {
                                    defaultMethBuilder.append("{if (").append(field_name).append("== null){").append(field_name).append("=org.jhrcore.comm.CodeManager.getCodeManager().getCodeBy(\"").append(fieldDef.getCode_type_name()).append("\",").append(default_value).append(");\n}}");
                                }
                                defaultMethBuilder.append("{if (").append(field_name).append("!= null) {").append(field_name.substring(0, field_name.length() - 6)).append("=").append(field_name).append(".getCode_id();\n}}");
                            }
                        }
                        meth.setBody(setterBuilder.toString());
                        entity_ctclass.addMethod(meth);
                    } catch (CannotCompileException e) {
                        log.error(entityDef.getEntityName() + ";" + field_name);
                        log.error(e);
                    } finally {
                        setterBuilder.setLength(0);
                    }

                    meth = new CtMethod(CtClass.voidType, "set" + fieldMeth, SET_STR_ARGS, entity_ctclass);
                    meth.setModifiers(Modifier.PUBLIC);
                    try {
                        setterBuilder.append("{").append(field_type_class.getName()).append(" old_value = ").append(field_name).append(";").append(field_name).append("=($1);");
                        if (create_code_field) {
                            setterBuilder.append("set").append(fieldMeth.substring(0, fieldMeth.length() - 6)).append("(").append(field_name).append("==null? \"\" : ").append(field_name).append(".getCode_id());");
                        }
                        setterBuilder.append(" this.firePropertyChange(\"").append(field_name).append("\", old_value,($1));}");
                        meth.setBody(setterBuilder.toString());
                        entity_ctclass.addMethod(meth);
                    } catch (CannotCompileException e) {
                        log.error(e);
                    } finally {
                        setterBuilder.setLength(0);
                    }
                }
                field_annotation = new Annotation("org.jhrcore.entity.annotation.FieldAnnotation", pool);
                String displayName = fieldDef.getField_caption() == null ? " " : fieldDef.getField_caption();

                boolean bvisible = !fieldDef.getField_name().endsWith("_key") && fieldDef.isVisible() && fieldDef.isUsed_flag();//&& (tmp_field_right > 0);
                boolean code_flag = !isDept && fieldDef.getCode_type_name() != null && !"".equals(fieldDef.getCode_type_name().trim()) && "String".equals(fieldDef.getField_type());
                if (code_flag && !create_code_field) {
                    bvisible = false;
                }
                boolean bEditable = fieldDef.isEditable();
                boolean bEditalbeNew = fieldDef.isEditablenew();
                boolean bEditableEdit = fieldDef.isEditableedit();
                boolean bVisibleNew = fieldDef.isVisiblenew();
                boolean bVisibleEdit = fieldDef.isVisibleedit();
                int fieldWidth = fieldDef.getField_width();
                int fieldScale = fieldDef.getField_scale();
                String pym = SysUtil.objToStr(fieldDef.getPym());
                String field_align = (fieldDef.getField_align() == null || fieldDef.getField_align().trim().equals("")) ? "左对齐" : fieldDef.getField_align();
                String format = SysUtil.objToStr(fieldDef.getFormat());
                if (fieldDef.getField_type().equals("Float")) {
                    if (format.equals("")) {
                        format = FormatUtil.getFormatStrByDecimalLen(fieldDef.getField_scale());
                    }
                } else if (fieldDef.getField_type().equals("Date")) {
                    format = format.equals("") ? "yyyy-MM-dd" : format;
                }
                String fieldMark = SysUtil.objToStr(fieldDef.getField_mark());
                addStringMember(field_annotation, pool, "displayName", displayName);
                addStringMember(field_annotation, pool, "pym", pym);
                addStringMember(field_annotation, pool, "format", format);
                addStringMember(field_annotation, pool, "field_mark", fieldMark);
                addStringMember(field_annotation, pool, "field_align", field_align);
                addIntMember(field_annotation, pool, "order_no", fieldDef.getOrder_no() + base_order_no);
                addIntMember(field_annotation, pool, "fieldWidth", fieldWidth);
                addIntMember(field_annotation, pool, "view_width", fieldDef.getView_width());
                addIntMember(field_annotation, pool, "field_scale", fieldScale);
                addBooleanMember(field_annotation, pool, "visible", bvisible);
                addBooleanMember(field_annotation, pool, "not_null", fieldDef.isNot_null());
                addBooleanMember(field_annotation, pool, "isEditable", bEditable);
                addBooleanMember(field_annotation, pool, "editableWhenNew", bEditalbeNew);
                addBooleanMember(field_annotation, pool, "editableWhenEdit", bEditableEdit);
                addBooleanMember(field_annotation, pool, "visibleWhenNew", bVisibleNew);
                addBooleanMember(field_annotation, pool, "visibleWhenEdit", bVisibleEdit);
                if (!create_code_field) {
                    addStringMember(field_annotation, pool, "default_value", SysUtil.objToStr(fieldDef.getDefault_value()));
                }
                field_attr.addAnnotation(field_annotation);
                if (create_code_field || field_name.equals("a0191")) {//用于下拉显示人员类别，By：杨周
                    field_annotation = new Annotation("org.jhrcore.entity.annotation.ObjectListHint", pool);
                    if (field_name.equals("a0191")) {
                        addStringMember(field_annotation, pool, "hqlForObjectList",
                                "select e.entityCaption from EntityDef e where e.entityClass.entityType_code='CLASS'");
                    } else {
                        addStringMember(field_annotation, pool, "hqlForObjectList",
                                "from Code c where c.code_type =" + fieldDef.getCode_type_name());
                        addBooleanMember(field_annotation, pool, "nullable", true);
                    }
                    field_attr.addAnnotation(field_annotation);
                    if (create_code_field) {
                        field_annotation = new Annotation("javax.persistence.Transient", pool);
                    } else {
                        field_annotation = new Annotation("javax.persistence.Column", pool);
                        addIntMember(field_annotation, pool, "length", fieldWidth);
                    }
                } else {
                    field_annotation = new Annotation("javax.persistence.Column", pool);
                    if (field_type.equals("string")) {
                        addIntMember(field_annotation, pool, "length", fieldWidth);
                    } else if (field_type.equals("float")) {
                        addIntMember(field_annotation, pool, "precision", fieldWidth);
                        addIntMember(field_annotation, pool, "scale", fieldScale);
                    }
                }
                field_attr.addAnnotation(field_annotation);
                field.getFieldInfo().addAttribute(field_attr);
                if (code_flag && !create_code_field) {
                    create_code_field = true;
                    continue;
                }
                create_code_field = false;
                field_index++;
            }
//            System.out.println("toString>>>:" + entityDef.getTo_String());
            CtClass string_class = getFieldCtClass("String");
            try {
                if (entityDef.isInit_flag()) {
                    String init_script = entityDef.getInit_script();
                    if (init_script != null && !init_script.trim().equals("")) {
                        if (!init_script.trim().endsWith(";")) {
                            init_script += ";";
                        }
                        init_script = init_script.replace("e.", "this.");
                        defaultMethBuilder.insert(0, init_script + "\n");
                    }
                }
                if (!defaultMethBuilder.toString().trim().equals("")) {
                    String key = "";
                    String method = "";
                    CtMethod[] fields = entity_ctclass.getMethods();
                    for (CtMethod field : fields) {
                        if (field.getName().startsWith("get") && field.getName().endsWith("_key")) {
                            List list = field.getMethodInfo().getAttributes();
                            for (Object obj : list) {
                                if (obj instanceof AnnotationsAttribute) {
                                    AnnotationsAttribute attr = (AnnotationsAttribute) obj;
                                    Annotation[] ans = attr.getAnnotations();
                                    for (Annotation an : ans) {
                                        if ("javax.persistence.Id".equals(an.getTypeName())) {
                                            key = field.getName();
                                            method = "set" + field.getName().substring(3);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (!key.trim().equals("")) {
                        CtMethod meth = null;
                        try {
                            meth = entity_ctclass.getDeclaredMethod("assignEntityKey");
                        } catch (Exception ex) {
//                    ex.printStackTrace();
                        }
                        AnnotationsAttribute field_attr = new AnnotationsAttribute(pool,
                                AnnotationsAttribute.visibleTag);
                        Annotation an = new Annotation("java.lang.Override", pool);
                        field_attr.addAnnotation(an);
                        boolean isNew = meth == null;
                        if (isNew) {
                            meth = new CtMethod(CtClass.voidType, "assignEntityKey", new CtClass[]{string_class}, entity_ctclass);
                        }
                        defaultMethBuilder.append("this.new_flag=1;");
                        className = entityDef.getEntityName();
                        defaultMethBuilder.append("this.").append(method).append("($1);");
                        if (!superclass.equals("com.jgoodies.binding.beans.Model")) {
                            defaultMethBuilder.insert(0, "super.assignEntityKey($1);");
                        }
                        defaultMethBuilder.insert(0, "{");
                        defaultMethBuilder.append("}");
                        meth.setBody(defaultMethBuilder.toString());
                        meth.setModifiers(Modifier.PUBLIC);
                        meth.getMethodInfo().addAttribute(field_attr);
                        if (isNew) {
                            entity_ctclass.addMethod(meth);
                        }
                    }
                }
            } catch (Exception ex) {
                log.error(entityName + ";" + ex);
            } finally {
                defaultMethBuilder.setLength(0);
            }
            String to_String = entityDef.getTo_String();
            if (to_String != null && !to_String.equals("")) {
                CtClass[] NO_ARGS = {};
                CtMethod meth = new CtMethod(string_class, "toString", NO_ARGS,
                        entity_ctclass);
                meth.setModifiers(Modifier.PUBLIC);
                try {
                    meth.setBody("{return \"\"+" + to_String + ";}");
                    entity_ctclass.addMethod(meth);
                } catch (CannotCompileException e) {
                    log.error(e);
                }
            }
//            System.out.println("toString<<<:" + entityDef.getTo_String());
            entity_ctclass.debugWriteFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static String getDefault_value(String field_type, FieldDef fieldDef) {
        String default_value = fieldDef.getDefault_value();
        if (default_value == null || default_value.trim().equals("")) {
            if (field_type.equals("integer")) {
                return "java.lang.Integer.valueOf(0)";
            } else if (field_type.equals("float")) {
                return "java.math.BigDecimal.valueOf(0.0)";
            }
            return null;
        }
        if (default_value != null && default_value.startsWith("#")) {
            //default_value = default_value.substring(1);
            if (!default_value.contains("#result") && field_type.equals("code")) {
                default_value += "_code_";
            }
            return default_value;
        } else if (field_type.equals("integer")) {
            return "java.lang.Integer.valueOf(" + SysUtil.objToInteger(default_value) + ")";
        } else if (field_type.equals("float")) {
            return "java.math.BigDecimal.valueOf(" + SysUtil.objToFloat(default_value) + ")";
        } else if (field_type.equals("boolean")) {
            String tmp = default_value == null ? "" : default_value.toLowerCase();
            default_value = (tmp.equals("1") || tmp.equals("t") || tmp.equals("true")) ? "true" : "false";
            return "java.lang.Boolean.valueOf(" + default_value + ")";
        } else if (field_type.equals("date")) {
            return "org.jhrcore.client.DateUtil.StrToDate(\"" + default_value + "\")";
        }
        return "\"" + default_value + "\"";
    }

//    // 返回按照分组归类的字段列表
//    public static Hashtable<String, List<Field>> getCommFieldHashtableOf(
//            Class<? extends Object> aclass, int field_type) {
//        Hashtable<String, List<Field>> hashTable = new Hashtable<String, List<Field>>();
//        List<Field> list_tmp = getCommFieldListOf(aclass, field_type);
//        for (Field field : list_tmp) {
//            FieldAnnotation fieldAnnotation = field.getAnnotation(FieldAnnotation.class);
//            String groupName = fieldAnnotation == null ? "" : fieldAnnotation.groupName();
//            List<Field> group_list = hashTable.get(groupName);
//            if (group_list == null) {
//                group_list = new ArrayList<Field>();
//                hashTable.put(groupName, group_list);
//            }
//            group_list.add(field);
//        }
//        return hashTable;
//    }
    public static List<String> getSQLFieldNameListOf(
            Class<? extends Object> aclass) {
        List<Field> fieldList = getDeclareFieldListOf(aclass, EntityBuilder.COMM_FIELD_ALL, false);
        List<String> fields = new ArrayList();
        for (Field field : fieldList) {
            if (field.getType().getName().startsWith("org.jhrcore.entity")) {
                continue;
            }
            if (Modifier.isTransient(field.getModifiers())) {
                continue;
            }
            fields.add(field.getName());
        }
        return fields;
    }

    public static List<String> getCommFieldNameListOf(
            Class<? extends Object> aclass, int field_type) {
        return getCommFieldNameListOf(aclass, field_type, false);
    }

    public static List<String> getCommFieldNameListOf(
            Class<? extends Object> aclass, int field_type, boolean isNew) {
        List<String> list_fieldname = new ArrayList<String>();
        if (aclass.getSuperclass()!=null && !aclass.getSuperclass().getSimpleName().equals(aclass.getSimpleName())) {
            list_fieldname.addAll(getDeclareFieldNameListOf(aclass.getSuperclass(), field_type, isNew));
        }
        list_fieldname.addAll(getDeclareFieldNameListOf(aclass, field_type, isNew));
        return list_fieldname;
    }

    public static List<String> getDeclareFieldNameListOf(
            Class<? extends Object> aclass, int field_type) {
        return getDeclareFieldNameListOf(aclass, field_type, false);
    }

    public static List<String> getDeclareFieldNameListOf(
            Class<? extends Object> aclass, int field_type, boolean isNew) {
        List<String> list_fieldname = new ArrayList<String>();
        List<Field> list_field = EntityBuilder.getDeclareFieldListOf(aclass, field_type, isNew);
        for (Field field : list_field) {
            list_fieldname.add(field.getName());
        }
        return list_fieldname;
    }

    public static List<Field> getFieldListOf(
            Class<? extends Object> aclass, List<String> fields) {
        ArrayList<Field> fieldList = new ArrayList<Field>();
        Class<?> theClass = aclass;

        for (String s : fields) {
            try {
                fieldList.add(theClass.getField(s));
            } catch (NoSuchFieldException ex) {
                ex.printStackTrace();
                log.error(ex);
            } catch (SecurityException ex) {
                ex.printStackTrace();
                log.error(ex);
            }
        }
        sortFields(fieldList);
        return fieldList;
    }

    public static List<Field> getCommFieldListOf(
            Class<? extends Object> aclass, int field_type) {
        return getCommFieldListOf(aclass, field_type, false);
    }

    public static List<Field> getDeclareFieldListOf(
            Class<? extends Object> aclass, int field_type) {
        return getDeclareFieldListOf(aclass, field_type, false);
    }

    public static List<Field> getDeclareFieldListOf(
            Class<? extends Object> aclass, int field_type, boolean isNew) {
        ArrayList<Field> fieldList = new ArrayList<Field>();
        HashSet<String> existFields = new HashSet();
        Class<?> theClass = aclass;
        for (Field f : theClass.getDeclaredFields()) {
            String fieldName = f.getName();
            if (existFields.contains(fieldName)) {
                continue;
            }
            Class typeClass = f.getType();
            String typeName = typeClass.getSimpleName();
            if (fieldName.equals("serialVersionUID") || fieldName.equals("changeSupport") || fieldName.equals("vetoSupport")) {
                continue;
            }
            if (typeName.equals("Collection") || typeName.equals("Set") || typeName.equals("List")) {
                continue;
            }
            FieldAnnotation fieldAnnotation = f.getAnnotation(FieldAnnotation.class);
            if (field_type == COMM_FIELD_VISIBLE || field_type == COMM_FIELD_VISIBLE_ALL) {
                if (fieldAnnotation == null || !fieldAnnotation.visible() || UserContext.getFieldRight(aclass, fieldName) == 0) {
                    continue;
                }
                if (field_type == COMM_FIELD_VISIBLE) {
                    if (typeClass.getName().contains("entity") && !typeName.equals("Code")) {
                        continue;
                    }
                }
            }
            if (isNew) {
                if (fieldAnnotation != null && !fieldAnnotation.visibleWhenNew()) {
                    continue;
                }
            } else {
                if (fieldAnnotation != null && !fieldAnnotation.visibleWhenEdit()) {
                    continue;
                }
            }
//            boolean b_hasIncluded = false;
//            for (Field tmp_f : fieldList) {
//                if (tmp_f.getName().equals(fieldName)) {
//                    b_hasIncluded = true;
//                    break;
//                }
//            }
//            if (!b_hasIncluded) {
            fieldList.add(f);
            existFields.add(fieldName);
//            }
        }

        sortFields(fieldList);
        return fieldList;
    }

    public static List<Field> getCommFieldListOf(
            Class<? extends Object> aclass, int field_type, boolean isNew) {
        ArrayList<Field> fieldList = new ArrayList<Field>();
        if (!aclass.getSuperclass().getSimpleName().equals(aclass.getSimpleName())) {
            fieldList.addAll(getDeclareFieldListOf(aclass.getSuperclass(), field_type, isNew));
        }
        fieldList.addAll(getDeclareFieldListOf(aclass, field_type, isNew));
        sortFields(fieldList);
        return fieldList;
    }

    public static List<TempFieldInfo> getCommFieldInfoListOf(
            Class<? extends Object> aclass) {
        return getCommFieldInfoListOf(aclass, EntityBuilder.COMM_FIELD_VISIBLE);
    }
    //考虑当前类的所有字段

    public static List<TempFieldInfo> getCommFieldInfoListOf(
            Class<? extends Object> aclass, int field_type) {
        List<TempFieldInfo> list_fieldname = new ArrayList<TempFieldInfo>();
        ClassAnnotation ca = aclass.getAnnotation(ClassAnnotation.class);
        if (!aclass.getSuperclass().getSimpleName().equals(aclass.getSimpleName())) {
            List<TempFieldInfo> list = getDeclareFieldInfoListOf(aclass.getSuperclass(), field_type);
            for (TempFieldInfo tfi : list) {
                tfi.setEntity_caption(ca == null ? "" : ca.displayName());
                tfi.setEntity_name(aclass.getSimpleName());
                WebHrMessage.initTfiInitation(tfi);
                list_fieldname.add(tfi);
            }
        }
        list_fieldname.addAll(getDeclareFieldInfoListOf(aclass, field_type));
        return list_fieldname;
    }

    public static List<TempFieldInfo> getDeclareFieldInfoListOf(
            Class<? extends Object> aclass, int field_type) {
        List<TempFieldInfo> list_fieldname = new ArrayList<TempFieldInfo>();
        List<Field> list_field = EntityBuilder.getDeclareFieldListOf(aclass, field_type, false);
        ClassAnnotation ca = (ClassAnnotation) aclass.getAnnotation(ClassAnnotation.class);
        String entity_caption = ca == null ? "" : ca.displayName();
        int i = 0;
        for (Field field : list_field) {
            TempFieldInfo fi = new TempFieldInfo();
            fi.setEntity_name(aclass.getSimpleName());
            fi.setEntity_caption(entity_caption);
            fi.setField_name(field.getName());
            fi.setField(field);
            fi.setField_type(field.getType().getSimpleName());
            fi.setCode_type_name("");
            FieldAnnotation fa = field.getAnnotation(FieldAnnotation.class);
            fi.setCaption_name(fa == null ? field.getName() : fa.displayName());
            fi.setField_scale(fa == null ? 0 : fa.field_scale());
            fi.setPym(fa == null ? "" : fa.pym());
            fi.setField_width(fa == null ? 20 : fa.fieldWidth());
            fi.setView_width(fa == null ? 20 : fa.view_width());
            fi.setOrder_no(fa == null ? i : fa.order_no());
            fi.setDefault_value(fa == null ? null : fa.default_value());
            fi.setFormat(fa == null ? null : fa.format());
            fi.setField_mark(fa == null ? "" : fa.field_mark());
            i++;
            ObjectListHint objHint = field.getAnnotation(ObjectListHint.class);
            if (objHint != null) {
                if (objHint.hqlForObjectList().startsWith("from Code ")) {
                    String hql = objHint.hqlForObjectList();
                    fi.setCode_type_name(hql.substring(hql.indexOf("=") + 1));
                }
            }
            WebHrMessage.initTfiInitation(fi);
            list_fieldname.add(fi);
        }
        return list_fieldname;
    }

    private static void sortFields(List<Field> fieldList) {
        //////////////对字段进行排序---------->
        Collections.sort(fieldList, new Comparator() {

            @Override
            public int compare(Object arg0, Object arg1) {
                Field field0 = (Field) arg0;
                Field field1 = (Field) arg1;

                FieldAnnotation fa0 = (FieldAnnotation) field0.getAnnotation(FieldAnnotation.class);
                FieldAnnotation fa1 = (FieldAnnotation) field1.getAnnotation(FieldAnnotation.class);

                Integer order_no0 = fa0 == null ? 0 : fa0.order_no();
                Integer order_no1 = fa1 == null ? 0 : fa1.order_no();

                return order_no0.compareTo(order_no1);
            }
        });
        //////////////对字段进行排序-
    }

    // the_class2，包括他本身且属于module_name模块的类定义列表
    // upperEnitity： 表示上一级的实体名称； upperCondition：表示之前的连接条件
    // mutil_to_one：true表示上一个类与当前类是多对1关系，false表示上一个类与当前类是1对多关系
    public static void getEntityDef(List<TempEntityDef> list_entities, Class the_class2,
            String module_name, String upperEnitity, String upperEnitityDisplay, String upperCondition, boolean mutil_to_one) {
        //List<TempEntityDef> list_entities = new ArrayList<TempEntityDef>();
        ClassAnnotation ca = (ClassAnnotation) the_class2.getAnnotation(ClassAnnotation.class);
        if (ca == null) {
            return;
        }
        String keyField = "";
        String masterEntity = "";
        if (mutil_to_one) {
            masterEntity = upperEnitity;
        } else {
            masterEntity = the_class2.getSimpleName();
        }
//        if (masterEntity.equals("A01")) {
//            keyField = "a01_key";
//        } else {
        keyField = masterEntity.substring(0, 1).toLowerCase() + masterEntity.substring(1) + "_key";
//        }

        String condition = upperEnitityDisplay + "." + keyField + " = " + ((ca == null) ? the_class2.getName() : ca.displayName() + "." + keyField) + "\n";
        if (upperCondition != null && !upperCondition.equals("")) {
            condition = upperCondition + " and " + condition;
        }

        if (module_name.equals(ca.moduleName())) {
            for (TempEntityDef ed2 : list_entities) {
                if (ed2.getEntity_class_name().equals(the_class2.getSimpleName())) {
                    return;
                }
            }
            TempEntityDef ed = new TempEntityDef();
            ed.setEntity_class_fullname(the_class2.getName());
            ed.setEntity_class_name(the_class2.getSimpleName());
            ed.setEntity_class_caption(ca.displayName());
            ed.setCondition(condition);
            list_entities.add(ed);
        }

        for (Field field : the_class2.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            FieldAnnotation fieldAnnotation = field.getAnnotation(FieldAnnotation.class);
            if (fieldAnnotation == null || !fieldAnnotation.visible()) {
                continue;
            }


            if (field.getType().getSimpleName().equals("Set") || field.getType().getSimpleName().equals("List")) {
                Type genericType = field.getGenericType();

                ParameterizedType pType = (ParameterizedType) genericType;
                Type[] arguments = pType.getActualTypeArguments();
                for (Type type : arguments) {
                    Class clazz = (Class) type;
                    if (clazz.getSimpleName().startsWith("Base")) {
                        // 如果该集合包含的对象是基类，则获取他的继承类
                        List<String> list_subClass = EntityBuilder.getHt_subclasses().get(clazz.getName());
                        if (list_subClass != null) {
                            for (String subClass : list_subClass) {
                                try {
                                    Class aClass = Class.forName(subClass);
                                    getEntityDef(list_entities, aClass,
                                            module_name, the_class2.getSimpleName(), ca.displayName(), condition, true);
                                } catch (ClassNotFoundException ex) {
                                    ex.printStackTrace();
                                    log.error(ex);
                                }
                            }
                        }
                    } else {
                        getEntityDef(list_entities, clazz,
                                module_name, the_class2.getSimpleName(), ca.displayName(), condition, true);
                    }
                }
            } else {
                // 包含entity说明是实体类
                if (field.getType().getName().contains("entity") && !field.getType().getSimpleName().equals("Code")) {
                    if ("s_g10".equalsIgnoreCase(field.getName())) {
                        //如果是G10里的s_g10，避免死循环
                        if (!g10_flag) {
                            g10_flag = true;
                        } else {
                            continue;
                        }
                    }
                    getEntityDef(list_entities, field.getType(),
                            module_name, the_class2.getSimpleName(), ca.displayName(), condition, false);
                }
            }
        }
    }

    public static String getEntityKey(Class aClass) {
        while (aClass.getSuperclass().getName().contains("entity")) {
            aClass = aClass.getSuperclass();
        }

        String ent_field = aClass.getSimpleName().substring(0, 1).toLowerCase() + aClass.getSimpleName().substring(1);
        if ("fieldDef".equals(ent_field)) {
            return "field_key";
        }
        if ("entityDef".equals(ent_field)) {
            return "entity_key";
        }
        if ("moduleInfo".equals(ent_field)) {
            return "module_key";
        }
        return ent_field + "_key";
    }

    public static String getEntityKey(String EntityClass) {
        if (EntityClass.equals("FieldDef")) {
            return "field_key";
        }
        String fullEntityClass = EntityBuilder.getHt_entity_classes().get(EntityClass);
        try {
            Class aClass = Class.forName(fullEntityClass);
            while (aClass.getSuperclass().getName().contains("entity")) {
                aClass = aClass.getSuperclass();
            }
            EntityClass = aClass.getSimpleName();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            log.error(ex);
        }

        String ent_field = EntityClass.substring(0, 1).toLowerCase() + EntityClass.substring(1);
        return ent_field + "_key";
    }

    public static String getEntityField(String EntityClass) {
        String ent_field = EntityClass.substring(0, 1).toLowerCase() + EntityClass.substring(1);
        if (EntityClass.equals("A01")) {
            ent_field = "a01";
        }
        return ent_field;
    }

    // className是否包含fieldName字段
    public static boolean containsField(String className, String fieldName) {
        String fullEntityClass = EntityBuilder.getHt_entity_classes().get(className);
        try {
            Class aClass = Class.forName(fullEntityClass);
            for (Field f : aClass.getDeclaredFields()) {
                if (f.getName().equals(fieldName)) {
                    return true;
                }
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            log.error(ex);
        }
        return false;
    }

    /**
     * 该方法用于根据传入的字段信息重写对应CLASS字段
     * @param entity_ctclass：指定class
     * @param tfi：字段信息
     * @param isEditable：是否可编辑
     */
    public static void buildField(CtClass entity_ctclass, TempFieldInfo tfi, boolean isEditable) {
        ClassPool cp = ClassPool.getDefault();
        Class field_class = tfi.getField().getType();
        String field_name = tfi.getField_name().replace("_code_", "");
        ConstPool pool = entity_ctclass.getClassFile().getConstPool();
        if (create_code_field) {
            field_name = field_name + "_code_";
        } else if (field_class.getSimpleName().equals("Code")) {
            field_class = String.class;
        }
        boolean bfieldExists = false;
        for (CtField tmp_ctField : entity_ctclass.getFields()) {
            if (tmp_ctField.getName().equals(field_name)) {
                bfieldExists = true;
                break;
            }
        }
        if (bfieldExists) {
            return;
        }
        CtClass field_type_class = null;
        try {
            field_type_class = cp.get(field_class.getName());
        } catch (Exception e) {
            return;
        }
        CtField ctfield = null;
        try {
            ctfield = new CtField(field_type_class, field_name,
                    entity_ctclass);
            ctfield.setModifiers(Modifier.PUBLIC);
            entity_ctclass.addField(ctfield);
        } catch (CannotCompileException e1) {
            log.error(e1);
        }
        CtClass[] NO_ARGS = {};
        CtClass[] SET_STR_ARGS = {field_type_class};

        String tmp_method_prefix = "get";
        if (field_class.getName().equals("boolean")) {
            tmp_method_prefix = "is";
        }
        String fieldMeth = field_name.substring(0, 1).toUpperCase() + field_name.substring(1);
        CtMethod meth = new CtMethod(field_type_class, tmp_method_prefix + fieldMeth, NO_ARGS, entity_ctclass);
        if (field_name.equals("deptCode") || field_name.equals("old_deptCode") || field_name.equals("new_deptCode")) { // 如果是部门类型,则增加影射注解
            AnnotationsAttribute meth_attr = new AnnotationsAttribute(pool,
                    AnnotationsAttribute.visibleTag);
            Annotation meth_annotation = new Annotation(
                    "javax.persistence.ManyToOne", pool);

            CtClass typeClazz = null;
            try {
                typeClazz = cp.get(FetchType.class.getName());
            } catch (NotFoundException e) {
                log.error(e);
            }
            EnumMemberValue enumMemberValue = new EnumMemberValue(pool);
            enumMemberValue.setType(typeClazz.getName());
            enumMemberValue.setValue(FetchType.EAGER.name());
            meth_annotation.addMemberValue("fetch", enumMemberValue);
            meth_attr.addAnnotation(meth_annotation);
            meth_annotation = new Annotation("javax.persistence.JoinColumn", pool);
            addStringMember(meth_annotation, pool, "name", field_name.toLowerCase() + "_key");
            meth_attr.addAnnotation(meth_annotation);
            meth.getMethodInfo2().addAttribute(meth_attr);
        }
        if (create_code_field) {
            AnnotationsAttribute meth_attr = new AnnotationsAttribute(pool,
                    AnnotationsAttribute.visibleTag);
            Annotation meth_annotation = new Annotation(
                    "javax.persistence.Transient", pool);
            meth_attr.addAnnotation(meth_annotation);
            meth.getMethodInfo2().addAttribute(meth_attr);
        }
        meth.setModifiers(Modifier.PUBLIC);
        String field_type = field_class.getSimpleName().toLowerCase();
        try {
            if (!create_code_field) {
                if (field_type.equals("integer")) {
                    setterBuilder.append("{if (").append(field_name).append("== null) ").append(field_name).append("=java.lang.Integer.valueOf(0);return ").append(field_name).append(";}");
                } else if (field_type.equals("float") || field_type.equals("double") || field_type.equals("bigdecimal")) {
                    setterBuilder.append("{if (").append(field_name).append("== null) ").append(field_name).append("=java.math.BigDecimal.valueOf(0.0);return ").append(field_name).append(";}");
                } else {
                    setterBuilder.append("{return ").append(field_name).append(";}");
                }
            } else {
                setterBuilder.append("{" + "if (").append(field_name).append("== null)").append(field_name).append("=org.jhrcore.comm.CodeManager.getCodeManager().getCodeBy(" + "\"").append(tfi.getCode_type_name()).append("\",").append(field_name.replace("_code_", "")).append(");" + "return ").append(field_name).append(";}");
            }
            meth.setBody(setterBuilder.toString());
            entity_ctclass.addMethod(meth);
        } catch (CannotCompileException e) {
            log.error(e);
        } finally {
            setterBuilder.setLength(0);
        }

        meth = new CtMethod(CtClass.voidType, "set" + fieldMeth, SET_STR_ARGS, entity_ctclass);
        meth.setModifiers(Modifier.PUBLIC);
        try {
            setterBuilder.append("{").append(field_class.getName()).append(" old_value = ").append(field_name).append(";").append(field_name).append("=($1);");
            if (create_code_field) {
                setterBuilder.append("set").append(fieldMeth.substring(0, fieldMeth.length() - 6)).append("(").append(field_name).append("==null? \"\" : ").append(field_name).append(".getCode_id());");
            }
            setterBuilder.append(" this.firePropertyChange(\"").append(field_name).append("\", old_value,($1));}");
            meth.setBody(setterBuilder.toString());
            entity_ctclass.addMethod(meth);
        } catch (CannotCompileException e) {
            log.error(e);
        } finally {
            setterBuilder.setLength(0);
        }
        AnnotationsAttribute field_attr = new AnnotationsAttribute(pool,
                AnnotationsAttribute.visibleTag);
        Annotation field_annotation = null;
        if (create_code_field) {
            field_annotation = new Annotation(
                    "org.jhrcore.entity.annotation.ObjectListHint", pool);
            addStringMember(field_annotation, pool, "hqlForObjectList", "from Code c where c.code_type =" + tfi.getCode_type_name());
            addBooleanMember(field_annotation, pool, "nullable", true);
            field_attr.addAnnotation(field_annotation);
            field_annotation = new Annotation("javax.persistence.Transient", pool);
            field_attr.addAnnotation(field_annotation);
        } else {
            field_annotation = new Annotation(
                    "javax.persistence.Column", pool);
            if (field_type.equals("string")) {
                addIntMember(field_annotation, pool, "length", tfi.getField_width());
            }
            if (field_type.equals("float")) {
                addIntMember(field_annotation, pool, "precision", tfi.getField_width());
                addIntMember(field_annotation, pool, "scale", tfi.getField_scale());
            }
            field_attr.addAnnotation(field_annotation);
            EnumHint enumHint = (EnumHint) tfi.getField().getAnnotation(EnumHint.class);
            if (enumHint != null) {
                // 如果原来的对象包含对象注解,则增加对象注解
                field_annotation = new Annotation(
                        "org.jhrcore.entity.annotation.EnumHint", pool);
                addStringMember(field_annotation, pool, "enumList", enumHint.enumList());
                field_attr.addAnnotation(field_annotation);
            }
            ObjectListHint objectListHint = (ObjectListHint) tfi.getField().getAnnotation(ObjectListHint.class);
            if (objectListHint != null) {
                // 如果原来的对象包含对象注解,则增加对象注解
                field_annotation = new Annotation(
                        "org.jhrcore.entity.annotation.ObjectListHint", pool);
                addStringMember(field_annotation, pool, "hqlForObjectList", objectListHint.hqlForObjectList());
                field_attr.addAnnotation(field_annotation);
            }
        }
        field_annotation = new Annotation(
                "org.jhrcore.entity.annotation.FieldAnnotation", pool);
        addBooleanMember(field_annotation, pool, "isEditable", isEditable);
        addStringMember(field_annotation, pool, "displayName", SysUtil.objToStr(tfi.getCaption_name()));
        addBooleanMember(field_annotation, pool, "visible", create_code_field || !"Code".equals(tfi.getField_type()));
        if (tfi.getFormat() != null) {
            addStringMember(field_annotation, pool, "format", tfi.getFormat());
        }
        field_attr.addAnnotation(field_annotation);
        ctfield.getFieldInfo().addAttribute(field_attr);
        if ("Code".equals(tfi.getField_type()) && !create_code_field) {
            create_code_field = true;
            buildField(entity_ctclass, tfi, isEditable);
        }
        setterBuilder.setLength(0);
        create_code_field = false;
    }

    public static void buildInfo(Class c, List<TempFieldInfo> allInfos, List<TempFieldInfo> defaultInfos, int method) {
        List<TempFieldInfo> infos = EntityBuilder.getCommFieldInfoListOf(c, method);
        if (allInfos != null) {
            allInfos.addAll(infos);
        }
        if (defaultInfos != null) {
            defaultInfos.addAll(infos);
        }
    }

    public static void buildInfo(Class c, List<TempFieldInfo> allInfos, List<TempFieldInfo> defaultInfos) {
        buildInfo(c, allInfos, defaultInfos, EntityBuilder.COMM_FIELD_VISIBLE);
    }

    public static void buildInfo(Class c, List<TempFieldInfo> allInfos, List<TempFieldInfo> defaultInfos, String start_name) {
        List<TempFieldInfo> infos = EntityBuilder.getCommFieldInfoListOf(c, EntityBuilder.COMM_FIELD_VISIBLE);
        String entityName = c.getSimpleName();
        start_name = start_name.equals("") ? "" : (start_name + ".");
        if (defaultInfos == null) {
            for (TempFieldInfo tfi : infos) {
                tfi.setField_name(start_name + tfi.getField_name());
                allInfos.add(tfi);
            }
            return;
        }
        String[] defaultFields = entityDefaultFields.get(entityName);
        if (defaultFields == null) {
            for (TempFieldInfo tfi : infos) {
                defaultInfos.add(tfi);
            }
        } else {
            List<String> fields = Arrays.asList(defaultFields);
            for (TempFieldInfo tfi : infos) {
                if (fields.contains(tfi.getField_name())) {
                    defaultInfos.add(tfi);
                }
            }
        }
        for (TempFieldInfo tfi : infos) {
            tfi.setField_name(start_name + tfi.getField_name());
            allInfos.add(tfi);
        }
    }

    public static void buildInfoToDefault(List<TempFieldInfo> allInfos, List<TempFieldInfo> defaultInfos, String fieldNames) {
        if (fieldNames == null || defaultInfos == null || allInfos == null) {
            return;
        }
        List<String> fields = Arrays.asList(fieldNames.split(";"));
        for (TempFieldInfo tfi : allInfos) {
            if (fields.contains(tfi.getField_name())) {
                defaultInfos.add(tfi);
            }
        }
    }
}
