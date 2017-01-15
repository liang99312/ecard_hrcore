/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.mutil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.jhrcore.comm.HrLog;
import org.jhrcore.entity.A01;
import org.jhrcore.entity.A01Chg;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.annotation.FieldAnnotation;
import org.jhrcore.query3.TempEntity;
import org.jhrcore.query3.TempField;
import org.jhrcore.rebuild.EntityBuilder;

/**
 *
 * @author hflj
 */
public class QueryUtil {

    public static List<Object[]> createEntities(Class entity_class) {
        List<Object[]> data = new ArrayList<Object[]>();
        String superClassName = entity_class.getSuperclass().getName();
        String className = entity_class.getName();
        boolean isPay = superClassName.equals("org.jhrcore.entity.salary.Pay") || className.equals("org.jhrcore.entity.salary.Pay");
        if (isPay) {
            List<String> list_subclass = EntityBuilder.getHt_subclasses().get("org.jhrcore.entity.salary.Pay");
            if (list_subclass != null) {
                for (String subclass_name : list_subclass) {
                    Class sub_class;
                    try {
                        sub_class = Class.forName(subclass_name);
                        if (sub_class.getSimpleName().equals(entity_class.getSimpleName())) {
                            continue;
                        }

                        ClassAnnotation ca0 = (ClassAnnotation) sub_class.getAnnotation(ClassAnnotation.class);
                        data.add(new Object[]{sub_class, ca0 == null ? sub_class.getName() : ca0.displayName(), sub_class.getSimpleName(), "5"});
                    } catch (ClassNotFoundException ex) {
                        HrLog.error(QueryUtil.class, ex);
                    }
                }
            }
        }
        if (isPay) {
            data.add(new Object[]{A01.class, "人员信息表", "A01", "5"});
            data.add(new Object[]{DeptCode.class, "部门信息表", "DeptCode", "5"});
        } else if (entity_class.getSimpleName().equals("HT01")) {
            data.add(new Object[]{A01.class, "人员信息", "A01", "5"});
            data.add(new Object[]{DeptCode.class, "部门信息表", "DeptCode", "5"});
        } else if (entity_class.getSimpleName().equals("BasePersonChange") || (entity_class.getSuperclass() != null && entity_class.getSuperclass().getSimpleName().equals("BasePersonChange"))) {
            data.add(new Object[]{A01Chg.class, "变动附表", "A01Chg", "3"});
        } else if (entity_class.getSimpleName().equals("In_info")) {
            data.add(new Object[]{A01.class, "人员信息", "A01", "5"});
        } else if (entity_class.getSuperclass() != null && "In_account".equals(entity_class.getSuperclass().getSimpleName())) {
            data.add(new Object[]{A01.class, "人员信息", "A01", "5"});
        }
        return data;
    }
    // level:关系层次，用来限制不考虑2层以上的集合关系

    public static void createEntityFieldList(Class<?> the_class2, String superBindName, String superDisplayBindName,
            String superClassNames, String superFieldClasses, int level, boolean single, boolean include_set_field, Class entity_class, List<TempEntity> listTempEntity) {
        if (level > 100) {
            return;
        }
        if (the_class2.getName().equals(entity_class.getSuperclass().getName())) {
            return;
        }
        for (TempEntity te0 : listTempEntity) {
            if (te0.getEntityClass().equals(the_class2.getName())) {
                return;
            }
        }
        TempEntity te = new TempEntity();
        if (!the_class2.getName().equals("org.jhrcore.entity.salary.Pay")) {
            //if ( level > 1 && !the_class2.getSuperclass().getName().equals("org.jhrcore.entity.salary.Pay"))
            if (!(entity_class.getName().equals("org.jhrcore.entity.salary.C21") && the_class2.getName().equals("org.jhrcore.entity.salary.PaySystem"))) {
                listTempEntity.add(te);
            }
        }
        Class<?> tmp_class = the_class2;
        ClassAnnotation ca0 = (ClassAnnotation) tmp_class.getAnnotation(ClassAnnotation.class);
        te.setEntityClass(tmp_class.getName());
        te.setEntityCaption(ca0 == null ? tmp_class.getName() : ca0.displayName());
        while (tmp_class.getName().startsWith("org.jhrcore.entity")) {
            for (Field field : tmp_class.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if (field.getModifiers() >= Modifier.TRANSIENT) {
                    continue;
                }
                FieldAnnotation fieldAnnotation = field.getAnnotation(FieldAnnotation.class);
                if (fieldAnnotation == null) {
                    continue;
                }
                if (field.getType().getSimpleName().equals("Set") || field.getType().getSimpleName().equals("List")) {
                    if (single) {
                        continue;
                    }
                    if (level > 1) {
                        continue;
                    }
                    if (!include_set_field) {
                        continue;
                    }
                    Type genericType = field.getGenericType();
                    ParameterizedType pType = (ParameterizedType) genericType;
                    Type[] arguments = pType.getActualTypeArguments();
                    for (Type type : arguments) {
                        Class clazz = (Class) type;
                        if (clazz.getSimpleName().startsWith("Base") && !clazz.getSimpleName().equals("BasePersonChange")) {
                            // 如果该集合包含的对象是基类，则获取他的继承类
                            List<String> list_subClass = EntityBuilder.getHt_subclasses().get(clazz.getName());
                            if (list_subClass != null) {
                                for (String subClass : list_subClass) {
                                    try {
                                        Class aClass = Class.forName(subClass);
                                        createEntityFieldList(aClass, "", fieldAnnotation.displayName(), superClassNames + ";" + aClass.getSimpleName(), superFieldClasses.equals("") ? "3" : (superFieldClasses + ";3"), level + 1, single, include_set_field, entity_class, listTempEntity);
                                    } catch (ClassNotFoundException ex) {
                                        HrLog.error(QueryUtil.class, ex);
                                    }
                                }
                            }
                        } else {
                            createEntityFieldList(clazz, "", fieldAnnotation.displayName(), superClassNames + ";" + clazz.getSimpleName(), superFieldClasses.equals("") ? "3" : (superFieldClasses + ";3"), level + 1, single, include_set_field, entity_class, listTempEntity);
                        }
                    }
                } else {
                    if (!fieldAnnotation.visible() && !field.getType().getName().contains("entity")) {
                        continue;
                    }
                    TempField tempField = new TempField();
                    tempField.setEntityClass(the_class2);
                    tempField.setField_name(field.getName());
                    tempField.setDisplay_name(fieldAnnotation.displayName());
                    tempField.setBindName(superBindName.equals("") ? tempField.getField_name() : superBindName + "." + tempField.getField_name());
                    tempField.setDisplayBindName(superDisplayBindName.equals("") ? tempField.getDisplay_name() : superDisplayBindName + "." + tempField.getDisplay_name());
                    tempField.setFieldType(field.getType().getSimpleName());
                    tempField.setEntityNames(superClassNames);
                    tempField.setFormat(fieldAnnotation.format());
                    if (field.getType().getName().contains("entity") && !field.getType().getSimpleName().equals("Code")) {
                        tempField.setFieldClasses(superFieldClasses.equals("") ? "2" : (superFieldClasses + ";2"));
                    } else {
                        tempField.setFieldClasses(superFieldClasses.equals("") ? "1" : (superFieldClasses + ";1"));
                    }
                    if (!field.getType().getName().contains("entity") || field.getType().getSimpleName().equals("Code")) {
                        te.getTempFields().add(tempField);
                    }
                    // 包含entity说明是我们定义的类，递归列举该类的字段
                    if (field.getType().getName().contains("entity") && !field.getType().getSimpleName().equals("Code") && !field.getType().getName().equals(entity_class.getName()) //&& entity_class == the_class2
                            ) {
                        if (single) {
                            continue;
                        }
                        createEntityFieldList(field.getType(), tempField.getBindName(), tempField.getDisplayBindName(), superClassNames + ";" + field.getType().getSimpleName(), superFieldClasses.equals("") ? "2" : (superFieldClasses + ";2"), level + 1, single, include_set_field, entity_class, listTempEntity);
                    }
                }
            }
            //if ( level > 1)
            if (level > 1 && !tmp_class.getSuperclass().getName().equals("org.jhrcore.entity.salary.Pay")) {
                break;
            }
            //如果要包含父类的字段，把下面注释去掉
            tmp_class = tmp_class.getSuperclass();
        }

        if (the_class2.getSimpleName().equals("BasePersonChange")) {
            return;
        }
        if (single) {
            return;
        }
        List<String> list = EntityBuilder.getHt_subclasses().get(the_class2.getName());
        if (list != null) {
            for (String tmp_class_name : list) {
                Class clazz = null;
                try {
                    clazz = Class.forName(tmp_class_name);
                } catch (ClassNotFoundException ex) {
                    HrLog.error(QueryUtil.class, ex);
                }
                if (clazz != null) {
                    ca0 = (ClassAnnotation) clazz.getAnnotation(ClassAnnotation.class);
                    if (the_class2.getName().equals("org.jhrcore.entity.salary.Pay")) {
                        if (clazz.getName().equals("org.jhrcore.entity.salary.C21")) {
                            superClassNames = "C21";
                            superFieldClasses = "";
                        } else {
                            superClassNames = "C21" + ";" + clazz.getSimpleName();
                            superFieldClasses = superFieldClasses.equals("") ? "3" : (superFieldClasses + ";3");
                        }
                    } else {
                        superClassNames = superClassNames + ";" + clazz.getSimpleName();
                        superFieldClasses = superFieldClasses.equals("") ? "3" : (superFieldClasses + ";3");
                    }
                    createEntityFieldList(clazz, "", ca0 == null ? clazz.getName() : ca0.displayName(), superClassNames, superFieldClasses, level + 1, single, include_set_field, entity_class, listTempEntity);
                }
            }
        }
    }

    public static String tranExpStr(String str, int max) {
        String result = str;
        for (int i = max; i > 0; i--) {
            result = result.replace(i + "", getTempNumStr(i));
        }
        for (int i = max; i > 0; i--) {
            result = result.replace(getTempNumStr(i), getTempStr(i));
        }
        return result;
    }

    private static String getTempNumStr(int len) {
        String result = "";
        for (int i = 0; i < len; i++) {
            result += "@";
        }
        return result;
    }

    public static String getTempStr(int len) {
        return "[" + len + "]";
    }
}
