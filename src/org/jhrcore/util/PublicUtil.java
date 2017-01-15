package org.jhrcore.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.log4j.Logger;
import org.jhrcore.comm.CodeManager;
import org.jhrcore.entity.Code;
import org.jhrcore.entity.annotation.ObjectListHint;
import org.jhrcore.entity.base.TempFieldInfo;

public class PublicUtil {

    private static Logger log = Logger.getLogger(PublicUtil.class.getSimpleName());
    //用于记录已经成功创建的表和字段
    private static HashSet<String> success_build_properties = new HashSet<String>();

    public static void addSuccess_property(String property_name) {
        success_build_properties.add(property_name);
    }

    public static boolean isSuccess_build(String property_name) {
        return success_build_properties.contains(property_name);
    }

    public static void removeProperty(String property_name) {
        success_build_properties.remove(property_name);
    }

    public static void setValueBy2(Object data_obj, String fieldName, Object val_obj) {
        List<String> tmp_list = Arrays.asList(fieldName.split("\\."));
        Class aclass = data_obj.getClass();
        for (int i = 0; i < tmp_list.size() - 1; i++) {
            String field_name = tmp_list.get(i);
            try {
                Method method = aclass.getMethod("get" + field_name.substring(0, 1).toUpperCase() + field_name.substring(1), new Class[]{});
                data_obj = method.invoke(data_obj, new Object[]{});
                if (data_obj == null) {
                    break;
                }
                aclass = data_obj.getClass();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        Class f = null;
        String field_name = tmp_list.get(tmp_list.size() - 1);
        try {
            Class field_class = aclass.getField(field_name).getType();
//            f = field_class;
            if (val_obj != null) {
                String classname = field_class.getSimpleName().toLowerCase();
                if (!field_class.getName().equals(val_obj.getClass().getName())) {
                    if (classname.equals("int")) {
                        val_obj = SysUtil.objToInt(val_obj);
                    } else if (classname.equals("integer")) {
                        val_obj = SysUtil.objToInteger(val_obj);
                    } else if (classname.equals("float")) {
                        val_obj = SysUtil.objToFloat(val_obj);
                    } else if (classname.equals("bigdecimal")) {
                        val_obj = SysUtil.objToBigDecimal(val_obj);
                    } else if (classname.equals("string")) {
                        val_obj = SysUtil.objToStr(val_obj);
                    } else if (classname.equals("boolean")) {
                        val_obj = SysUtil.objToBoolean(val_obj);
                    }
                }
            }
            Method method = aclass.getMethod("set" + field_name.substring(0, 1).toUpperCase() + field_name.substring(1), new Class[]{field_class});
            data_obj = method.invoke(data_obj, new Object[]{val_obj});
        } catch (Exception e) {
            log.error("field_name:" + field_name + ";" + e.getMessage());
            System.out.println("field_name:" + field_name);//+";"+f.getName()+";"+val_obj.getClass().getName());
        }
    }

    public static Object getProperty(Object bean, String field_names) {
        List<String> field_list = Arrays.asList(field_names.split("\\."));
        Object cell_value = bean;
        if (cell_value != null) {
            Class aclass = bean.getClass();
            for (String field_name : field_list) {
                try {
                    String tmp = "get";
                    Field field = aclass.getField(field_name);
                    if (field.getType().getName().equals("boolean")) {
                        tmp = "is";
                    }
                    Method method = aclass.getMethod(tmp + field_name.substring(0, 1).toUpperCase() + field_name.substring(1), new Class[]{});
                    cell_value = method.invoke(cell_value, new Object[]{});
                    if (cell_value == null) {
                        break;
                    }
                    aclass = cell_value.getClass();
                } catch (Exception ex) {
                    System.out.println("field_name:" + field_name);
                    log.error(ex);
                    ex.printStackTrace();
                }
            }
        }
        return cell_value;
    }
    // 用于保存一些通用属性，比如可以替换ObjectListHint的hqlForObjectList
    private static Properties props_value = new Properties();

    // 用于两个对象的属性拷贝，参数分别表示源对象、目标对象、源属性列表、目标属性列表
    public static void copyProperties(Object src_obj, Object dst_obj, List<String> src_fields, List<String> dst_fields) {
        if (src_obj == null || dst_obj == null) {
            return;
        }
        if (src_fields.size() != dst_fields.size()) {
            return;
        }

//        Class src_class = src_obj.getClass();
//        Class dst_class = dst_obj.getClass();
        for (int i = 0; i < src_fields.size(); i++) {
            String src_field_name = src_fields.get(i);
            String dst_field_name = dst_fields.get(i);
            Object tmp_obj = getProperty(src_obj, src_field_name);
            setValueBy2(dst_obj, dst_field_name, tmp_obj);
//            Method method = null;
//            try {
//                method = src_class.getMethod("get" + src_field_name.substring(0, 1).toUpperCase() + src_field_name.substring(1), new Class[]{});
//            } catch (Exception ex) {
//                log.error(ex);
//            }
//            try{
//                if(method == null){
//                    method = src_class.getMethod("is" + src_field_name.substring(0, 1).toUpperCase() + src_field_name.substring(1), new Class[]{});
//                }
//                Object tmp_obj = method.invoke(src_obj, new Object[]{});
//                Class field_class = src_class.getField(src_field_name).getType();
//                method = dst_class.getMethod("set" + dst_field_name.substring(0, 1).toUpperCase() + dst_field_name.substring(1), new Class[]{field_class});
//                method.invoke(dst_obj, new Object[]{tmp_obj});
//            } catch (Exception ex) {
//                System.out.println("src_field_name:"+src_field_name);
//                ex.printStackTrace();
//                log.error(ex);
//            }
        }
    }

    // 用于两个对象的属性是否一样，参数分别表示源对象、目标对象、源属性列表、目标属性列表、源字段格式化、目标字段格式化
    public static boolean sameProperties(Object src_obj, Object dst_obj,
            List<String> src_fields, List<String> dst_fields, Hashtable<String, String> src_table, Hashtable<String, String> dst_table) {
        if (src_obj == null && dst_obj == null) {
            return true;
        }
        if (src_fields.size() != dst_fields.size()) {
            return false;
        }
        if (src_table == null) {
            src_table = new Hashtable<String, String>();
        }

        if (dst_table == null) {
            dst_table = new Hashtable<String, String>();
        }

        for (int i = 0; i < src_fields.size(); i++) {
            String src_field_name = src_fields.get(i);
            String dst_field_name = dst_fields.get(i);
            Object src_tmp_obj = getProperty(src_obj, src_field_name);
            Object dst_tmp_obj = getProperty(dst_obj, dst_field_name);
            String src_string = "";
            String dst_string = "";
            if (src_tmp_obj == null) {
            } else if (src_tmp_obj instanceof Date) {
                if (src_table.get(src_field_name) != null) {
                    src_string = (new SimpleDateFormat(src_table.get(src_field_name))).format((Date) src_tmp_obj);
                } else {
                    src_string = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(src_tmp_obj);
                }
            } else if (src_tmp_obj instanceof Float || src_tmp_obj instanceof BigDecimal) {
                if (src_table.get(src_field_name) != null) {
                    src_string = (new DecimalFormat(src_table.get(src_field_name))).format(src_tmp_obj);
                } else {
                    src_string = src_tmp_obj.toString();
                }
            } else {
                src_string = src_tmp_obj.toString();
            }
            if (dst_tmp_obj == null) {
            } else if (dst_tmp_obj instanceof Date) {
                if (dst_table.get(dst_field_name) != null) {
                    dst_string = (new SimpleDateFormat(dst_table.get(dst_field_name))).format((Date) dst_tmp_obj);
                } else {
                    dst_string = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dst_tmp_obj);
                }
            } else if (dst_tmp_obj instanceof Float || dst_tmp_obj instanceof BigDecimal) {
                if (dst_table.get(dst_field_name) != null) {
                    dst_string = (new DecimalFormat(dst_table.get(dst_field_name))).format(dst_tmp_obj);
                } else {
                    dst_string = dst_tmp_obj.toString();
                }
            } else {
                dst_string = dst_tmp_obj.toString();
            }
            if (!src_string.equals(dst_string)) {
                System.out.println(src_field_name + "---" + dst_field_name);
                System.out.println(src_string + "---" + dst_string);
                return false;
            }
        }
        return true;
    }

    public static void person_copyProperties(Object src_obj, Object dst_obj, List<String> src_fields, List<String> dst_fields, List<TempFieldInfo> all_infos) {
        if (src_fields.size() != dst_fields.size()) {
            return;
        }

        Class src_class = src_obj.getClass();
        Class dst_class = dst_obj.getClass();
        Hashtable<String, TempFieldInfo> field_keys = new Hashtable<String, TempFieldInfo>();
        for (TempFieldInfo tfi : all_infos) {
            field_keys.put(tfi.getField_name().toUpperCase(), tfi);
        }
        for (int i = 0; i < src_fields.size(); i++) {
            String src_field_name = src_fields.get(i);
            String dst_field_name = dst_fields.get(i);
            try {
                Method method = src_class.getMethod("get" + src_field_name.substring(0, 1).toUpperCase() + src_field_name.substring(1), new Class[]{});
                Object tmp_obj = method.invoke(src_obj, new Object[]{});
                tmp_obj = tmp_obj.toString().replace(" ", "");
                Class field_class = String.class;
                if (dst_field_name.endsWith("_code_")) {
                    field_class = Code.class;
                    String code_type = "@@@";
                    TempFieldInfo tfi = field_keys.get(dst_field_name.toUpperCase());
                    if (tfi != null && tfi.getCode_type_name() != null && !tfi.getCode_type_name().trim().equals("")) {
                        code_type = tfi.getCode_type_name();
                    }
                    Object obj = CodeManager.getCodeManager().getCodeBy(code_type, tmp_obj == null ? "@@@" : tmp_obj.toString());
                    if (obj == null) {
                        obj = CodeManager.getCodeManager().getCodeBy(code_type, tmp_obj == null ? "@@@" : "0" + tmp_obj.toString());
                    }
                    tmp_obj = obj;
                }
                if (("personborn".equalsIgnoreCase(src_field_name) || "PersonUserLifeBegin".equalsIgnoreCase(src_field_name)
                        || "PersonUserLifeEnd".equalsIgnoreCase(src_field_name)) && tmp_obj != null) {
                    TempFieldInfo f_tfi = field_keys.get(dst_field_name.toUpperCase());
                    if ("date".equalsIgnoreCase(f_tfi.getField_type())) {
                        field_class = Date.class;
                    }
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    Date d = null;
                    try {
                        d = format.parse(tmp_obj.toString());
                    } catch (ParseException ex) {
                        log.error(ex);
                    }
                    method = dst_class.getMethod("set" + dst_field_name.substring(0, 1).toUpperCase() + dst_field_name.substring(1), new Class[]{field_class});
                    method.invoke(dst_obj, new Object[]{d});
                } else {
                    method = dst_class.getMethod("set" + dst_field_name.substring(0, 1).toUpperCase() + dst_field_name.substring(1), new Class[]{field_class});
                    method.invoke(dst_obj, new Object[]{tmp_obj});
                }
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
    }

    // 将value中的出现 [key] 的地方替换成属性值
    public static String replaceProperty(String value, String key) {
        String tmp = value;
        if (key != null && props_value.getProperty(key) != null) {
            tmp = tmp.replace(key, props_value.getProperty(key));
        }
        return tmp;
    }
    // 将value中的出现 @属性名 的地方替换成属性值

    public static String replaceProperty(String value) {
        String tmp = value;
        for (Object obj : props_value.keySet()) {
            tmp = tmp.replace(obj.toString(), props_value.getProperty(obj.toString()));
        }
        return tmp;
    }

    public static Properties getProps_value() {
        return props_value;
    }

    public static void setProps_value(Properties props_value) {
        PublicUtil.props_value = props_value;
    }

    public static Object createInstanceOf(Class<?> aClass) {
        try {
            return aClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }

    // 根据类名和字段名返回该字段关联的编码类型, field_name可以是编码类型字段也可以是原始字段
    public static String getCode_typeBy(String fullClassName, String field_name) {
        String code_type = "";
        if (!field_name.endsWith("_code_")) {
            field_name = field_name + "_code_";
        }
        try {
            Class aclass = Class.forName(fullClassName);
            Field field = aclass.getField(field_name);
            ObjectListHint objectListHint = field.getAnnotation(ObjectListHint.class);
            if (objectListHint != null) {
                code_type = objectListHint.hqlForObjectList().substring(objectListHint.hqlForObjectList().indexOf("=") + 1);
            }
        } catch (NoSuchFieldException ex) {
            log.error(ex);
        } catch (SecurityException ex) {
            log.error(ex);
        } catch (ClassNotFoundException ex) {
            log.error(ex);
        }
        return code_type;
    }

    /**
     * 该方法用于根据字段类型改变当前属性值，用以适应数据库规则
     * @param tmp_obj：当前属性值
     * @param new_field_type：字段类型
     * @return 新值
     */
    public static Object getDefaultValueForType(Object tmp_obj, String new_field_type) {
        return getDefaultValueForType(tmp_obj, new_field_type, null);
    }

    /**
     * 该方法用于根据字段类型改变当前属性值，用以适应数据库规则，并可直接拼接至SQL语句
     * @param tmp_obj：当前属性值/默认值
     * @param new_field_type：字段类型
     * @param db_type：数据库类型（sqlserver/oracle/db2）
     * @return 新值
     */
    public static Object getDefaultSQLValueForType(Object tmp_obj, String new_field_type, String db_type) {
        if (new_field_type == null) {
            return tmp_obj;
        }
        if (new_field_type.equalsIgnoreCase("String")) {
            tmp_obj = tmp_obj == null ? null : "'" + tmp_obj.toString() + "'";
        } else {
            if (new_field_type.equalsIgnoreCase("Integer")) {
                tmp_obj = SysUtil.objToInteger(tmp_obj);
                if (tmp_obj == null) {
                    tmp_obj = 0;
                }
            } else if (new_field_type.equalsIgnoreCase("int")) {
                tmp_obj = SysUtil.objToInt(tmp_obj);
                if (tmp_obj == null) {
                    tmp_obj = 0;
                }
            } else if (new_field_type.equalsIgnoreCase("Float")) {
                tmp_obj = SysUtil.objToFloat(tmp_obj);
                if (tmp_obj == null) {
                    tmp_obj = 0.0;
                }
            } else if (new_field_type.equalsIgnoreCase("Date")) {
                if (tmp_obj != null && !(tmp_obj instanceof Date)) {
                    tmp_obj = DateUtil.StrToDate(tmp_obj.toString());
                }
                if (tmp_obj != null) {
                    tmp_obj = DateUtil.toStringForQuery((Date) tmp_obj, "yyyy-MM-dd HH:mm:ss", db_type);
                }
            } else if (new_field_type.equalsIgnoreCase("BigDicemal")) {
                if (tmp_obj == null) {
                    tmp_obj = 0;
                }
                tmp_obj = SysUtil.objToBigDecimal(tmp_obj);
            } else if (new_field_type.equalsIgnoreCase("Boolean")) {
                tmp_obj = SysUtil.objToBoolean(tmp_obj) ? 1 : 0;
            }
        }
        return tmp_obj;
    }

    /**
     * 该方法用于根据新/原字段类型来改变当前属性值，用以适应数据库规则
     * @param tmp_obj：当前属性值
     * @param new_field_type：新字段类型
     * @param old_field_type：原字段类型，若为NULL，则表示根据新字段类型决定是否需要改变属性值，如果不为NULL，主要用于人事变动中的特殊应用
     * @return 新值
     */
    public static Object getDefaultValueForType(Object tmp_obj, String new_field_type, String old_field_type) {
        if (new_field_type == null) {
            return tmp_obj;
        }
        if (new_field_type.equals(old_field_type)) {
        } else if (new_field_type.equalsIgnoreCase("String")) {
            tmp_obj = tmp_obj == null ? null : tmp_obj.toString();
        } else if (tmp_obj != null) {
            if (new_field_type.equalsIgnoreCase("Integer")) {
                tmp_obj = SysUtil.objToInteger(tmp_obj);
                if (tmp_obj == null) {
                    tmp_obj = 0;
                }
            } else if (new_field_type.equalsIgnoreCase("int")) {
                tmp_obj = SysUtil.objToInt(tmp_obj);
                if (tmp_obj == null) {
                    tmp_obj = 0;
                }
            } else if (new_field_type.equalsIgnoreCase("Float")) {
                tmp_obj = SysUtil.objToFloat(tmp_obj);
                if (tmp_obj == null) {
                    tmp_obj = 0.0;
                }
            } else if (new_field_type.equalsIgnoreCase("Date")) {
                tmp_obj = DateUtil.StrToDate(tmp_obj.toString());
            } else if (new_field_type.equalsIgnoreCase("BigDicemal")) {
                if (tmp_obj == null) {
                    tmp_obj = 0;
                }
                tmp_obj = SysUtil.objToBigDecimal(tmp_obj);
            }
        }
        return tmp_obj;
    }

    /**
     * 获得包下的类
     *
     * @param packageName
     *            The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        ArrayList<Class> classes = new ArrayList<Class>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            String p = "";
            if (resource.getFile().indexOf("!") >= 0) {// 在其他的jar文件中
                p = resource.getFile().substring(0,
                        resource.getFile().indexOf("!")).replaceAll("%20", "");
            } else {// 在classes目录中
                p = resource.getFile();
            }
            if (p.startsWith("file:/")) {
                p = p.substring(6);
            }
            if (p.toLowerCase().endsWith(".jar")) {

                JarFile jarFile = new JarFile(p);
                Enumeration<JarEntry> enums = jarFile.entries();
                while (enums.hasMoreElements()) {
                    JarEntry entry = (JarEntry) enums.nextElement();
                    String n = entry.getName();

                    if (n.endsWith(".class")) {
                        n = n.replaceAll("/", ".").substring(0, n.length() - 6);
                        if (n.startsWith(packageName)) {

                            classes.add(Class.forName(n));

                        }
                    }

                }
            } else {
                dirs.add(new File(p));
            }

        }

        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }
//    /**
//     * 根据对象和字段获取对象的属性
//     * @param obj:被获取属性对象
//     * @param field_name：字段名
//     * @return
//     * @throws ClassNotFoundException
//     * @throws IOException 
//     */
//    public static Object getValueByField_name(Object obj, String field_name) throws ClassNotFoundException, Exception{
//        Class aclass = obj.getClass();
//        Object object = null;
//        Method method = aclass.getMethod("get" + field_name.substring(0, 1).toUpperCase() + field_name.substring(1), new Class[]{});
//        object = method.invoke(obj, new Object[]{});
//        return object;
//    }

    /**
     * 查找一个文件夹下的文件
     *
     * @param directory
     *            The base directory
     * @param packageName
     *            The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    public static List<Class> findClasses(File directory, String packageName)
            throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "."
                        + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName
                        + '.'
                        + file.getName().substring(0,
                        file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public static void main(String[] args) {
//        Class[] cs = null;
//        try {
//            cs = getClasses("org.jhrcore.entity");
//            for (Class c : cs) {
//                Entity e = (Entity) c.getAnnotation(Entity.class);
//                if(e==null)
//                    continue;
//                Field field = c.getDeclaredField(c.getSimpleName().substring(0,1).toLowerCase()+c.getSimpleName().substring(1)+"_key");
//                if(field==null)
//                    continue;
//
//            }
//        } catch (NoSuchFieldException ex) {
//            ex.printStackTrace();
//        } catch (SecurityException ex) {
//            ex.printStackTrace();
//        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
    }

    /*
     * 下面这些S11-S44实际上是一个4*4的矩阵，在原始的C实现中是用#define 实现的， 这里把它们实现成为static
     * final是表示了只读，切能在同一个进程空间内的多个 Instance间共享
     */
    static final int S11 = 7;
    static final int S12 = 12;
    static final int S13 = 17;
    static final int S14 = 22;
    static final int S21 = 5;
    static final int S22 = 9;
    static final int S23 = 14;
    static final int S24 = 20;
    static final int S31 = 4;
    static final int S32 = 11;
    static final int S33 = 16;
    static final int S34 = 23;
    static final int S41 = 6;
    static final int S42 = 10;
    static final int S43 = 15;
    static final int S44 = 21;
    static final byte[] PADDING = {-128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0};

    /*
     * 下面的三个成员是keyBean计算过程中用到的3个核心数据，在原始的C实现中 被定义到keyBean_CTX结构中
     */
    private static long[] state = new long[4]; // state (ABCD)
    private static long[] count = new long[2]; // number of bits, modulo 2^64 (lsb
    // first)
    private static byte[] buffer = new byte[64]; // input buffer

    /*
     * digestHexStr是keyBean的唯一一个公共成员，是最新一次计算结果的 16进制ASCII表示.
     */
    public static String digestHexStr;

    /*
     * digest,是最新一次计算结果的2进制内部表示，表示128bit的keyBean值.
     */
    private static byte[] digest = new byte[16];

    /*
     * getkeyBeanofStr是类keyBean最主要的公共方法，入口参数是你想要进行keyBean变换的字符串
     * 返回的是变换完的结果，这个结果是从公共成员digestHexStr取得的．
     */
    public static String getkeyBeanofStr(String inbuf) {
        keyBeanInit();
        keyBeanUpdate(inbuf.getBytes(), inbuf.length());
        keyBeanFinal();
        digestHexStr = "";
        for (int i = 0; i < 16; i++) {
            digestHexStr += byteHEX(digest[i]);
        }
        return digestHexStr;
    }

    // 这是keyBean这个类的标准构造函数，JavaBean要求有一个public的并且没有参数的构造函数

    /* keyBeanInit是一个初始化函数，初始化核心变量，装入标准的幻数 */
    private static void keyBeanInit() {
        count[0] = 0L;
        count[1] = 0L;
        // /* Load magic initialization constants.
        state[0] = 0x67452301L;
        state[1] = 0xefcdab89L;
        state[2] = 0x98badcfeL;
        state[3] = 0x10325476L;
        return;
    }

    /*
     * F, G, H ,I 是4个基本的keyBean函数，在原始的keyBean的C实现中，由于它们是
     * 简单的位运算，可能出于效率的考虑把它们实现成了宏，在java中，我们把它们 实现成了private方法，名字保持了原来C中的。
     */
    private static long F(long x, long y, long z) {
        return (x & y) | ((~x) & z);
    }

    private static long G(long x, long y, long z) {
        return (x & z) | (y & (~z));
    }

    private static long H(long x, long y, long z) {
        return x ^ y ^ z;
    }

    private static long I(long x, long y, long z) {
        return y ^ (x | (~z));
    }

    /*
     * FF,GG,HH和II将调用F,G,H,I进行近一步变换 FF, GG, HH, and II transformations for
     * rounds 1, 2, 3, and 4. Rotation is separate from addition to prevent
     * recomputation.
     */
    private static long FF(long a, long b, long c, long d, long x, long s, long ac) {
        a += F(b, c, d) + x + ac;
        a = ((int) a << s) | ((int) a >>> (32 - s));
        a += b;
        return a;
    }

    private static long GG(long a, long b, long c, long d, long x, long s, long ac) {
        a += G(b, c, d) + x + ac;
        a = ((int) a << s) | ((int) a >>> (32 - s));
        a += b;
        return a;
    }

    private static long HH(long a, long b, long c, long d, long x, long s, long ac) {
        a += H(b, c, d) + x + ac;
        a = ((int) a << s) | ((int) a >>> (32 - s));
        a += b;
        return a;
    }

    private static long II(long a, long b, long c, long d, long x, long s, long ac) {
        a += I(b, c, d) + x + ac;
        a = ((int) a << s) | ((int) a >>> (32 - s));
        a += b;
        return a;
    }

    /*
     * keyBeanUpdate是keyBean的主计算过程，inbuf是要变换的字节串，inputlen是长度，这个
     * 函数由getkeyBeanofStr调用，调用之前需要调用keyBeaninit，因此把它设计成private的
     */
    private static void keyBeanUpdate(byte[] inbuf, int inputLen) {
        int i, index, partLen;
        byte[] block = new byte[64];
        index = (int) (count[0] >>> 3) & 0x3F;
        // /* Update number of bits */
        if ((count[0] += (inputLen << 3)) < (inputLen << 3)) {
            count[1]++;
        }
        count[1] += (inputLen >>> 29);
        partLen = 64 - index;
        // Transform as many times as possible.
        if (inputLen >= partLen) {
            keyBeanMemcpy(buffer, inbuf, index, 0, partLen);
            keyBeanTransform(buffer);
            for (i = partLen; i + 63 < inputLen; i += 64) {
                keyBeanMemcpy(block, inbuf, 0, i, 64);
                keyBeanTransform(block);
            }
            index = 0;
        } else {
            i = 0;
        }
        // /* Buffer remaining input */
        keyBeanMemcpy(buffer, inbuf, index, i, inputLen - i);
    }

    /*
     * keyBeanFinal整理和填写输出结果
     */
    private static void keyBeanFinal() {
        byte[] bits = new byte[8];
        int index, padLen;
        // /* Save number of bits */
        Encode(bits, count, 8);
        // /* Pad out to 56 mod 64.
        index = (int) (count[0] >>> 3) & 0x3f;
        padLen = (index < 56) ? (56 - index) : (120 - index);
        keyBeanUpdate(PADDING, padLen);
        // /* Append length (before padding) */
        keyBeanUpdate(bits, 8);
        // /* Store state in digest */
        Encode(digest, state, 16);
    }

    /*
     * keyBeanMemcpy是一个内部使用的byte数组的块拷贝函数，从input的inpos开始把len长度的
     * 字节拷贝到output的outpos位置开始
     */
    private static void keyBeanMemcpy(byte[] output, byte[] input, int outpos,
            int inpos, int len) {
        int i;
        for (i = 0; i < len; i++) {
            output[outpos + i] = input[inpos + i];
        }
    }

    /*
     * keyBeanTransform是keyBean核心变换程序，有keyBeanUpdate调用，block是分块的原始字节
     */
    private static void keyBeanTransform(byte block[]) {
        long a = state[0], b = state[1], c = state[2], d = state[3];
        long[] x = new long[16];
        Decode(x, block, 64);
        /* Round 1 */
        a = FF(a, b, c, d, x[0], S11, 0xd76aa478L); /* 1 */
        d = FF(d, a, b, c, x[1], S12, 0xe8c7b756L); /* 2 */
        c = FF(c, d, a, b, x[2], S13, 0x242070dbL); /* 3 */
        b = FF(b, c, d, a, x[3], S14, 0xc1bdceeeL); /* 4 */
        a = FF(a, b, c, d, x[4], S11, 0xf57c0fafL); /* 5 */
        d = FF(d, a, b, c, x[5], S12, 0x4787c62aL); /* 6 */
        c = FF(c, d, a, b, x[6], S13, 0xa8304613L); /* 7 */
        b = FF(b, c, d, a, x[7], S14, 0xfd469501L); /* 8 */
        a = FF(a, b, c, d, x[8], S11, 0x698098d8L); /* 9 */
        d = FF(d, a, b, c, x[9], S12, 0x8b44f7afL); /* 10 */
        c = FF(c, d, a, b, x[10], S13, 0xffff5bb1L); /* 11 */
        b = FF(b, c, d, a, x[11], S14, 0x895cd7beL); /* 12 */
        a = FF(a, b, c, d, x[12], S11, 0x6b901122L); /* 13 */
        d = FF(d, a, b, c, x[13], S12, 0xfd987193L); /* 14 */
        c = FF(c, d, a, b, x[14], S13, 0xa679438eL); /* 15 */
        b = FF(b, c, d, a, x[15], S14, 0x49b40821L); /* 16 */
        /* Round 2 */
        a = GG(a, b, c, d, x[1], S21, 0xf61e2562L); /* 17 */
        d = GG(d, a, b, c, x[6], S22, 0xc040b340L); /* 18 */
        c = GG(c, d, a, b, x[11], S23, 0x265e5a51L); /* 19 */
        b = GG(b, c, d, a, x[0], S24, 0xe9b6c7aaL); /* 20 */
        a = GG(a, b, c, d, x[5], S21, 0xd62f105dL); /* 21 */
        d = GG(d, a, b, c, x[10], S22, 0x2441453L); /* 22 */
        c = GG(c, d, a, b, x[15], S23, 0xd8a1e681L); /* 23 */
        b = GG(b, c, d, a, x[4], S24, 0xe7d3fbc8L); /* 24 */
        a = GG(a, b, c, d, x[9], S21, 0x21e1cde6L); /* 25 */
        d = GG(d, a, b, c, x[14], S22, 0xc33707d6L); /* 26 */
        c = GG(c, d, a, b, x[3], S23, 0xf4d50d87L); /* 27 */
        b = GG(b, c, d, a, x[8], S24, 0x455a14edL); /* 28 */
        a = GG(a, b, c, d, x[13], S21, 0xa9e3e905L); /* 29 */
        d = GG(d, a, b, c, x[2], S22, 0xfcefa3f8L); /* 30 */
        c = GG(c, d, a, b, x[7], S23, 0x676f02d9L); /* 31 */
        b = GG(b, c, d, a, x[12], S24, 0x8d2a4c8aL); /* 32 */
        /* Round 3 */
        a = HH(a, b, c, d, x[5], S31, 0xfffa3942L); /* 33 */
        d = HH(d, a, b, c, x[8], S32, 0x8771f681L); /* 34 */
        c = HH(c, d, a, b, x[11], S33, 0x6d9d6122L); /* 35 */
        b = HH(b, c, d, a, x[14], S34, 0xfde5380cL); /* 36 */
        a = HH(a, b, c, d, x[1], S31, 0xa4beea44L); /* 37 */
        d = HH(d, a, b, c, x[4], S32, 0x4bdecfa9L); /* 38 */
        c = HH(c, d, a, b, x[7], S33, 0xf6bb4b60L); /* 39 */
        b = HH(b, c, d, a, x[10], S34, 0xbebfbc70L); /* 40 */
        a = HH(a, b, c, d, x[13], S31, 0x289b7ec6L); /* 41 */
        d = HH(d, a, b, c, x[0], S32, 0xeaa127faL); /* 42 */
        c = HH(c, d, a, b, x[3], S33, 0xd4ef3085L); /* 43 */
        b = HH(b, c, d, a, x[6], S34, 0x4881d05L); /* 44 */
        a = HH(a, b, c, d, x[9], S31, 0xd9d4d039L); /* 45 */
        d = HH(d, a, b, c, x[12], S32, 0xe6db99e5L); /* 46 */
        c = HH(c, d, a, b, x[15], S33, 0x1fa27cf8L); /* 47 */
        b = HH(b, c, d, a, x[2], S34, 0xc4ac5665L); /* 48 */
        /* Round 4 */
        a = II(a, b, c, d, x[0], S41, 0xf4292244L); /* 49 */
        d = II(d, a, b, c, x[7], S42, 0x432aff97L); /* 50 */
        c = II(c, d, a, b, x[14], S43, 0xab9423a7L); /* 51 */
        b = II(b, c, d, a, x[5], S44, 0xfc93a039L); /* 52 */
        a = II(a, b, c, d, x[12], S41, 0x655b59c3L); /* 53 */
        d = II(d, a, b, c, x[3], S42, 0x8f0ccc92L); /* 54 */
        c = II(c, d, a, b, x[10], S43, 0xffeff47dL); /* 55 */
        b = II(b, c, d, a, x[1], S44, 0x85845dd1L); /* 56 */
        a = II(a, b, c, d, x[8], S41, 0x6fa87e4fL); /* 57 */
        d = II(d, a, b, c, x[15], S42, 0xfe2ce6e0L); /* 58 */
        c = II(c, d, a, b, x[6], S43, 0xa3014314L); /* 59 */
        b = II(b, c, d, a, x[13], S44, 0x4e0811a1L); /* 60 */
        a = II(a, b, c, d, x[4], S41, 0xf7537e82L); /* 61 */
        d = II(d, a, b, c, x[11], S42, 0xbd3af235L); /* 62 */
        c = II(c, d, a, b, x[2], S43, 0x2ad7d2bbL); /* 63 */
        b = II(b, c, d, a, x[9], S44, 0xeb86d391L); /* 64 */
        state[0] += a;
        state[1] += b;
        state[2] += c;
        state[3] += d;
    }

    /*
     * Encode把long数组按顺序拆成byte数组，因为java的long类型是64bit的， 只拆低32bit，以适应原始C实现的用途
     */
    private static void Encode(byte[] output, long[] input, int len) {
        int i, j;
        for (i = 0, j = 0; j < len; i++, j += 4) {
            output[j] = (byte) (input[i] & 0xffL);
            output[j + 1] = (byte) ((input[i] >>> 8) & 0xffL);
            output[j + 2] = (byte) ((input[i] >>> 16) & 0xffL);
            output[j + 3] = (byte) ((input[i] >>> 24) & 0xffL);
        }
    }

    /*
     * Decode把byte数组按顺序合成成long数组，因为java的long类型是64bit的，
     * 只合成低32bit，高32bit清零，以适应原始C实现的用途
     */
    private static void Decode(long[] output, byte[] input, int len) {
        int i, j;

        for (i = 0, j = 0; j < len; i++, j += 4) {
            output[i] = b2iu(input[j]) | (b2iu(input[j + 1]) << 8)
                    | (b2iu(input[j + 2]) << 16) | (b2iu(input[j + 3]) << 24);
        }
        return;
    }

    /*
     * b2iu是我写的一个把byte按照不考虑正负号的原则的”升位”程序，因为java没有unsigned运算
     */
    public static long b2iu(byte b) {
        return b < 0 ? b & 0x7F + 128 : b;
    }

    /*
     * byteHEX()，用来把一个byte类型的数转换成十六进制的ASCII表示，
     * 因为java中的byte的toString无法实现这一点，我们又没有C语言中的 sprintf(outbuf,"%02X",ib)
     */
    public static String byteHEX(byte ib) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
            'B', 'C', 'D', 'E', 'F'};
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    public static void copyProperties(Object obj, String[] fields, Object[] data) {
        copyProperties(obj, Arrays.asList(fields), data);
    }

    public static void copyProperties(Object obj, List<String> fields, Object[] data) {
        int cols = fields.size();
        int dataCols = data.length;
        for (int i = 0; i < cols; i++) {
            String field = fields.get(i);
            if (dataCols <= i) {
                break;
            }
            if (field.equals("")) {
                continue;
            }
            PublicUtil.setValueBy2(obj, field, data[i]);
        }
    }
}
