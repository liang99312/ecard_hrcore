package org.jhrcore.util;

import com.foundercy.pf.control.table.FTable;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.rmi.server.UID;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jhrcore.client.CommUtil;
import org.jhrcore.comm.HrLog;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.SysParameter;
import org.jhrcore.entity.annotation.ClassAnnotation;
import org.jhrcore.entity.base.EntityDef;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.showstyle.ShowScheme;
import org.jhrcore.entity.showstyle.ShowSchemeOrder;
import org.jhrcore.msg.CommMsg;
import org.jhrcore.rebuild.EntityBuilder;

/**
 * 
 * @author yangzhou
 */
public class SysUtil {

    public static final boolean DESC = false;
    public static final boolean ASC = true;
    public static String a01SearchField = "A01.a0190;A01.a0101;A01.a0177;A01.pydm;";
    private static char[] zimu = {'a', 'b', 'c', 'd', 'e', 'f', 'g',
        'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z'};

    /**
     * �÷������ڻ�ȡ���ٶ�λʱ��ƥ����䣬��Ҫ����ռλ��*�����⣬����A��������*ʱ��Ϊ like '%A%',����like 'A'
     * @param text:����������ı�
     * @return���������ƥ�����
     */
    public static String getQuickSearchText(String text) {
        if (text == null) {
            return "";
        }
        String result = text.toUpperCase();
        boolean need_change = text.contains("*");
        result = result.replace("*", "_");
        if (!need_change) {
            result = "%" + result + "%";
        }
        return result;
    }

    public static String getA01SearchSQL(String text) {
        return getA01SearchSQL(text, "A01");
    }

    public static String getA01SearchSQL(String text, String prechar) {
        prechar = prechar == null ? "A01" : prechar;
        return getQuickSearchSQL(a01SearchField.replace("A01.", prechar + "."), text);
    }

    public static String getQuickSearchSQL(String fieldStr, String text) {
        if (text == null || fieldStr == null || fieldStr.length() == 0) {
            return "";
        }
        String[] fields = fieldStr.split(";");
        return getQuickSearchSQL(fields, text);
    }

    /**
     * �÷������ڻ�ȡ���ٶ�λʱ��ƥ��SQL
     * @param fields:ƥ����ֶ�����
     * @param text:����������ı�
     * @return���������ƥ��SQL
     */
    public static String getQuickSearchSQL(String[] fields, String text) {
        String result = "";
        if (text == null || fields == null || fields.length == 0) {
            return "1=0";
        }
        if (!text.contains("%") && !text.contains("_")) {
            text = getQuickSearchText(text);
        }
        for (String field : fields) {
            result += " or upper(" + field + ") like '" + text + "'";
        }
        return "(" + result.substring(4) + ")";
    }

    public static String tranField(String fieldName) {
        if (fieldName == null) {
            return null;
        }
        if (fieldName.toUpperCase().endsWith("_CODE_") && fieldName.length() > 6) {
            fieldName = fieldName.substring(0, fieldName.length() - 6);
        }
        return fieldName;
    }

    public static EntityDef createEntityDefByClass(Class c) {
        EntityDef ed = new EntityDef();
        ed.setEntity_key(new UID().toString());
        ed.setEntityName(c.getSimpleName());
        ClassAnnotation ca = (ClassAnnotation) c.getAnnotation(ClassAnnotation.class);
        if (ca != null) {
            ed.setEntityCaption(ca.displayName());
        }
        ed.setPackageName(c.getName().substring(0, c.getName().lastIndexOf(".") + 1));
        return ed;
    }
    //�������Ĳ��ż��뵽����

    public static void addDeptToMemory(DeptCode deptcode) {
        if (deptcode == null) {
            return;
        }
        if (deptcode.getParent_code().equals("ROOT")) {
            UserContext.getMemoryDept(false).add(deptcode);
        } else {
            int size = UserContext.getMemoryDept(false).size();
            int ind = -1;
            for (int i = 0; i < size; i++) {
                DeptCode dept = (DeptCode) UserContext.getMemoryDept(false).get(i);
                if (dept.getDept_code().equals(deptcode.getDept_code())) {
                    dept = deptcode;
                    break;
                } else {
                    Double this_code = (Double) SysUtil.objToDouble(deptcode.getDept_code());
                    if (dept.getDept_code().equals(deptcode.getParent_code())) {
                        ind = i + 1;
                    } else {
                        Double dept_code = null;
                        int len = deptcode.getDept_code().length();
                        int len_code = dept.getDept_code().length();
                        if (len_code > len) {
                            dept_code = (Double) SysUtil.objToDouble(dept.getDept_code().substring(0, len));
                        } else if (len_code == len) {
                            dept_code = (Double) SysUtil.objToDouble(dept.getDept_code());
                        } else {
                            dept_code = (Double) SysUtil.objToDouble(dept.getDept_code());
                            this_code = (Double) SysUtil.objToDouble(deptcode.getDept_code().substring(0, len_code));
                        }
                        ind = i;
                        if (dept_code > this_code) {
                            break;
                        }
                        if (i == size - 1) {
                            ind = i + 1;
                        }
                    }
                }
            }

            if (ind != -1) {
                UserContext.getMemoryDept(false).add(ind, deptcode);
            }
        }
    }

    /**
     * �÷������ڻ���������Ź�����ϵʱ��paysystem_no�����������ݿ�����paysystem_no+1;
     * @return
     */
    public static int getNewCalSystemNo() {
        int i = 1;
        String hql = "select max(system_no) from CalSystem ps ";
        Object obj = CommUtil.fetchEntityBy(hql);
        if (obj != null) {
            i = Integer.valueOf(obj.toString()) + 1;
        }
        return i;
    }

    /**
     * �÷������ڻ������н����ϵʱ��paysystem_no�����������ݿ�����paysystem_no+1;
     * @return
     */
    public static int getNewPaySystemNo() {
        int i = 1;
        String hql = "select max(system_no) from PaySystem ps ";
        Object obj = CommUtil.fetchEntityBy(hql);
        if (obj != null) {
            i = Integer.valueOf(obj.toString()) + 1;
        }
        return i;
    }

    public static int getNewNoticeSchemeNo() {
        int i = 1;
        String hql = "select max(order_no) from NoticeScheme ns ";
        Object obj = CommUtil.fetchEntityBy(hql);
        if (obj != null) {
            i = Integer.valueOf(obj.toString()) + 1;
        }
        return i;
    }

    /**
     * ���������ַ����Ƿ�Ϊ��ȷ���·�
     * @param pay_month��ƥ���ַ���
     * @return��ƥ����
     */
    public static boolean check_month(String pay_month) {
        boolean pay_month_flag = true;
        if (pay_month == null) {
            pay_month_flag = false;
        } else {
            if (pay_month.length() != 6) {
                pay_month_flag = false;
            } else {
                Pattern p = Pattern.compile("^[0-9]+$");
                Matcher m = p.matcher(pay_month);
                if (!m.matches()) {
                    pay_month_flag = false;
                } else {
                    int month = Integer.valueOf(pay_month.substring(pay_month.length() - 2));
                    if (month > 12 || month == 0) {
                        pay_month_flag = false;
                    }
                }
            }

        }
        return pay_month_flag;
    }

    /**
     * ���������ڸ�������ıȽ�ֵ����������з���������ֵ����λ��
     * @param col��Ϊ-1ʱ��ʾ����Ĭ�ϵ��ֶαȽϣ���Ϊ-1ʱ��ʾ�ȶԵ�ǰ��
     * @param val���Ƚ�ֵ
     * @param last_search_val����һ���Ƚ�ֵ�����������Ƿ���Ҫ�������ʼλ�ý��бȽ�
     * @param last_locate_position����һ���Ƚ�λ��
     * @param ftable���������
     * @param default_fields��Ĭ�ϱȶ��ֶ�
     * @return �����������к��룬���û���ҵ�������-1.
     */
    public static int locateEmp(int col, String val, String last_search_val, int last_locate_position, FTable ftable, List<String> default_fields) {
        val = val.toUpperCase();
        last_search_val = last_search_val.toUpperCase();
//        if (!last_search_val.equals(val)) {
//            last_locate_position = -1;
//        }
        val = val.replace('.', '#');
        val = val.replaceAll("#", "\\\\.");
        val = val.replace('*', '#');
        val = val.replaceAll("#", ".*");
        val = val.replace('?', '#');
        val = val.replaceAll("#", ".?");
//        val = "^" + val + "$";
//        last_search_val = val;
        Object[] objs = ftable.getAllObjects().toArray();
        int row = -1;
        try {

            for (int i = (last_locate_position + 1); i < objs.length; i++) {
                Object obj = objs[ftable.getSorter().getSortedIndex()[i]];//objs[i];
                Object field_val;
                Pattern p;
                Matcher m;
                // ����ǰ�ָ���ж�λ
                if (col != -1) {
                    field_val = ftable.getModel().getValueAt(i, col);
                    field_val = field_val == null ? "" : field_val.toString().trim().toUpperCase();
                    if (field_val.equals(val)) {
                        row = i;
                        break;
                    }
                    p = Pattern.compile(val);
                    m = p.matcher(field_val.toString());
                    if (m.find()) {
                        row = i;
                        break;
                    }
                    continue;
                } else {
                    for (int j = 0; j < default_fields.size(); j++) {
                        field_val = PublicUtil.getProperty(obj, default_fields.get(j));
                        field_val = field_val == null ? "" : field_val.toString().trim().toUpperCase();
                        p = Pattern.compile(val);
                        m = p.matcher(field_val.toString());
                        if (m.find()) {
                            row = i;
                            return row;
                        }
                    }
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return row;
    }

    public static void sortListByInteger(List data, final String sortName) {
        if (data == null || data.isEmpty()) {
            return;
        }
        Collections.sort(data, new Comparator() {

            @Override
            public int compare(Object arg0, Object arg1) {
                return objToInteger(PublicUtil.getProperty(arg0, sortName)).compareTo(objToInteger(PublicUtil.getProperty(arg1, sortName)));
            }
        });
    }

    public static List<DefaultMutableTreeNode> locateEmp(JTree Tree, String val) {
        List<DefaultMutableTreeNode> selectNodes = new ArrayList<DefaultMutableTreeNode>();
        if (val == null || val.trim().equals("")) {
            return selectNodes;
        }
        val = val.toUpperCase();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) Tree.getModel().getRoot();
        Enumeration deptEnum = node.depthFirstEnumeration();
//        Pattern p = Pattern.compile(val);
        while (deptEnum.hasMoreElements()) {
            DefaultMutableTreeNode tmpNode = (DefaultMutableTreeNode) deptEnum.nextElement();
            if (tmpNode == node) {
                continue;
            }
            Object object = tmpNode.getUserObject();
            if (object == null || object.toString() == null) {
                continue;
            }
            String field_val = object.toString().toUpperCase();
            if (field_val.contains(val)) {
                if (!selectNodes.contains(tmpNode)) {
                    if (field_val.equals(val)) {
                        selectNodes.add(0, tmpNode);
                    } else {
                        selectNodes.add(tmpNode);
                    }
                }
                continue;
            }
            String pinyin = PinYinMa.ctoE(field_val).toUpperCase();
            if (pinyin != null && pinyin.contains(val)) {
                if (!selectNodes.contains(tmpNode)) {
                    if (pinyin.equals(val)) {
                        selectNodes.add(0, tmpNode);
                    } else {
                        selectNodes.add(tmpNode);
                    }
                }
            }
//            Matcher m;
//            int fisrt = field_val.indexOf("[");
//            if (fisrt != -1) {
//                field_val = field_val.substring(0, fisrt);
//            }
//            m = p.matcher(field_val);
//            if (m.find()) {
//                if (!selectNodes.contains(tmpNode)) {
//                    selectNodes.add(tmpNode);
//                    continue;
//                }
//            }
        }
        return selectNodes;
    }

    public static List locateEmp(List list, String val) {
        List selectNodes = new ArrayList();
        Pattern p = Pattern.compile(val);
        for (Object obj : list) {
            Object object = obj;
            String field_val = null;
            Matcher m;
            field_val = object.toString();
            int fisrt = field_val.indexOf("[");
            if (fisrt != -1) {
                field_val = field_val.substring(0, fisrt);
            }
            if (field_val != null) {
                m = p.matcher(field_val);
                if (m.find()) {
                    if (!selectNodes.contains(object)) {
                        selectNodes.add(object);
                    }
                }
            }
            String pinyin = PinYinMa.ctoE(field_val);
            if (pinyin != null && pinyin.toLowerCase().contains(val.toLowerCase())) {
                if (!selectNodes.contains(object)) {
                    selectNodes.add(object);
                }
            }
        }
        return selectNodes;
    }

    /**
     * �˷������ڻ�������
     * @param orders���Ѿ����ڵ������
     * @param start_num����ʼ���
     * @return�����յ������
     */
    public static int getOrder_no(HashSet<Integer> orders, int start_num) {
        int result = start_num;
        while (orders.contains(result)) {
            result++;
        }
        return result;
    }

    /**
     * ����������
     * @param showScheme����ʾ����
     * @param start_name����ı���,���Ϊ�գ�����Ϊû�б���
     * @param order_sql���������������
     * @param all_fields:��ǰ������ʾ�������ֶ�
     * @return
     */
    public static String getOrderString(ShowScheme showScheme, String start_name, String order_sql, List<TempFieldInfo> all_fields) {
        String final_order_sql = "";
        if (showScheme == null || showScheme.getShowSchemeOrders() == null) {
            return order_sql;
        }
        if (!start_name.equals("")) {
            start_name = start_name + ".";
        }
        List<ShowSchemeOrder> orders = new ArrayList<ShowSchemeOrder>();
        orders.addAll(showScheme.getShowSchemeOrders());
        SysUtil.sortListByInteger(orders, "order_no");
        for (ShowSchemeOrder sso : orders) {
            String fieldName = sso.getField_name().replace("_code_", "");
            for (TempFieldInfo tfi : all_fields) {
                if (tfi.getField_name().replace("_code_", "").equals(fieldName)) {
                    final_order_sql += start_name + fieldName + " " + sso.getField_order() + ",";
                    break;
                }
            }
        }
        if (!final_order_sql.equals("")) {
            final_order_sql = final_order_sql.substring(0, final_order_sql.length() - 1);
        } else {
            return order_sql;
        }
        return final_order_sql;
    }

    /**
     * ����������
     * @param showScheme����ʾ����
     * @param order_sql���������������
     * @param all_fields:��ǰ������ʾ�������ֶ�
     * @return
     */
    public static String getSQLOrderString(ShowScheme showScheme, String order_sql, List<TempFieldInfo> all_fields) {
        String final_order_sql = "";
        if (showScheme == null || showScheme.getShowSchemeOrders() == null) {
            return order_sql;
        }
        List<ShowSchemeOrder> orders = new ArrayList<ShowSchemeOrder>();
        orders.addAll(showScheme.getShowSchemeOrders());
        SysUtil.sortListByInteger(orders, "order_no");
        for (ShowSchemeOrder sso : orders) {
            String s_fieldName = sso.getField_name().replace("_code_", "");
            for (TempFieldInfo tfi : all_fields) {
                String fieldName = tfi.getField_name().replace("_code_", "");
                String[] fieldNames = fieldName.replace("#", "").split("\\.");
                if (fieldName.equals(s_fieldName)) {
                    Class c = tfi.getField().getDeclaringClass();
                    if (c.getSimpleName().equals("A01")) {
                        String entityName = c.getSimpleName();
                        final_order_sql += entityName + "." + fieldNames[fieldNames.length - 1] + " " + sso.getField_order() + ",";
                    } else {
                        final_order_sql += tfi.getEntity_name() + "." + fieldNames[fieldNames.length - 1] + " " + sso.getField_order() + ",";
                    }
                    break;
                }
            }
        }
        if (!final_order_sql.equals("")) {
            final_order_sql = final_order_sql.substring(0, final_order_sql.length() - 1);
        } else {
            return order_sql;
        }
        return final_order_sql;
    }

    /**
     * ����������
     * @param showScheme����ʾ����
     * @param order_sql���������������
     * @param all_fields:��ǰ������ʾ�������ֶ�
     * @return
     */
    public static String getSQLOrderByFields(ShowScheme showScheme, String order_sql, List<String> fields, String start_name) {
        String final_order_sql = "";
        if (showScheme == null || showScheme.getShowSchemeOrders() == null) {
            return order_sql;
        }
        List<ShowSchemeOrder> orders = new ArrayList<ShowSchemeOrder>();
        orders.addAll(showScheme.getShowSchemeOrders());
        SysUtil.sortListByInteger(orders, "order_no");
        for (ShowSchemeOrder sso : orders) {
            String s_fieldName = tranField(sso.getField_name()).replace("#", "");
            for (String fieldName : fields) {
                fieldName = tranField(fieldName).replace("#", "");
                String[] fieldNames = fieldName.split("\\.");
                String entityName = start_name;
                if (fieldName.equals(s_fieldName)) {
                    if (fieldName.contains(".")) {
                        entityName = fieldNames[fieldNames.length - 2];
                    }
                    entityName = entityName.equals("") ? "" : (entityName + ".");
                    final_order_sql += entityName + fieldNames[fieldNames.length - 1] + " " + sso.getField_order() + ",";
                    break;
                }
            }
        }
        if (!final_order_sql.equals("")) {
            final_order_sql = final_order_sql.substring(0, final_order_sql.length() - 1);
        } else {
            return order_sql;
        }
        return final_order_sql;
    }
    private static SysParameter positionGrade = null;

    /**
     * ��ְ�Ǽ�ʱ�ֶ�Ȩ�޿��أ�0���������û�������Ȩ���ֶΣ�1�������û�������Ȩ���ֶ�
     */
    public static SysParameter getRegisterOpenRight() {
        SysParameter obj = (SysParameter) CommUtil.fetchEntityBy("from SysParameter sys where sys.sysparameter_code='Emp.RegisterOpen'");
        if (obj == null) {
            obj = new SysParameter();
            obj.setSysParameter_key("Emp.RegisterOpen");
            obj.setSysparameter_code("Emp.RegisterOpen");
            obj.setSysparameter_name("��ְ�Ǽ��ֶ�Ȩ�޿���");
            obj.setSysparameter_value("0");
            CommUtil.saveOrUpdate(obj);
        }
        return obj;
    }

    /**
     * ��ְ�Ǽ�ʱ�ֶ�Ȩ�޿��أ�0���������û�������Ȩ���ֶΣ�1�������û�������Ȩ���ֶ�
     */
    public static SysParameter getBookType() {
        SysParameter obj = (SysParameter) CommUtil.fetchEntityBy("from SysParameter sys where sys.sysparameter_code='Train.BookType'");
        if (obj == null) {
            obj = new SysParameter();
            obj.setSysParameter_key("Train.BookType");
            obj.setSysparameter_code("Train.BookType");
            obj.setSysparameter_name("��ѵ�̲����");
            obj.setSysparameter_value("");
            CommUtil.saveOrUpdate(obj);
        }
        return obj;
    }

    /**
     * ���ż�������
     */
    public static SysParameter getPositionGrade() {
        if (positionGrade == null) {
            positionGrade = (SysParameter) CommUtil.fetchEntityBy("from SysParameter sys where sys.sysparameter_code='Position.position_grade'");
            if (positionGrade == null) {
                positionGrade = new SysParameter();
                positionGrade.setSysParameter_key("Position.position_grade");
                positionGrade.setSysparameter_code("Position.position_grade");
                positionGrade.setSysparameter_name("��λ��������");
                positionGrade.setSysparameter_value("1;2;2;2;2;2;2;2;2;2;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;");
                CommUtil.saveOrUpdate(positionGrade);
            }
        }
        return positionGrade;
    }
    private static List<EntityDef> class_list;

    public static List<EntityDef> getPersonClass() {
        return getPersonClass(true);
    }

    public static List<EntityDef> getPersonClass(boolean fetchA01) {
        if (class_list == null) {
            class_list = (List<EntityDef>) CommUtil.fetchEntities(
                    "from EntityDef ed  where ed.entityClass.entityType_code='CLASS' order by ed.order_no");
        }
        List result = new ArrayList<EntityDef>();
        for (Object obj : class_list) {
            EntityDef ed = (EntityDef) obj;
            String entityName = ed.getEntityName();
            if (UserContext.hasEntityViewRight(entityName)) {
                result.add(ed);
            }
        }
        if (class_list.size() >= 1 && fetchA01) {
            if (UserContext.hasEntityViewRight("A01")) {
                EntityDef ed = new EntityDef();
                ed.setEntityName("A01");
                ed.setEntityCaption("������Ա");
                result.add(ed);
            }
        }
        return result;
    }

    public static String getNewCode(int num, int len) {
        String result = num + "";
        int r_len = result.length();
        while (r_len < len) {
            result = "0" + result;
            r_len++;
        }
        return result;
    }

    public static String getNewRoleCode(String parent_code) {
        String tmp2 = "000000000000000000000000000000000000000000";
        String fetch_code = parent_code;
        if (parent_code == null || parent_code.equalsIgnoreCase("ROOT") || parent_code.equals("")) {
            fetch_code = "ROOT";
        }
        String tmp = (String) CommUtil.fetchEntityBy("select max(role_code) from Role r where r.parent_code='" + fetch_code + "' and r.role_key<>'&&&'");
        if (tmp == null) {
            tmp2 = "01";
        } else {
            tmp2 = tmp.substring(fetch_code.equals("ROOT") ? 0 : parent_code.length());
            int order = (Integer.valueOf(tmp2) + 1);
            tmp2 = order < 10 ? ("0" + order) : (order + "");
        }
//        tmp2 = tmp2.substring(tmp2.length() - 2);
        return (fetch_code.equals("ROOT") ? "" : parent_code) + tmp2;
    }

    public static String getNewFuntionCode(String parent_code) {
        String tmp = null;
        if (parent_code == null || parent_code.equalsIgnoreCase("ROOT")) {
            tmp = (String) CommUtil.fetchEntityBy("select max(fun_code) from FuntionRight ");
        } else {
            tmp = (String) CommUtil.fetchEntityBy("select max(fun_code) from FuntionRight r where r.fun_parent_code='" + parent_code + "'");
        }
        return getNewFuntionCode(parent_code, tmp);
    }

    public static String getNewFuntionCode(String parent_code, String tmp) {
        String tmp2 = "000000000000000000000000000000000000000000";
        if (tmp == null) {
            tmp2 += "1";
        } else {
            int len = tmp.length();
            if (len > 2) {
                int tmp_num = Integer.valueOf(tmp.substring(len - 2)) + 1;
                tmp2 += tmp_num;
            } else {
                tmp2 += (Integer.valueOf(tmp) + 1);
            }
        }
        tmp2 = tmp2.substring(tmp2.length() - 2);
        return parent_code + tmp2;
    }

    public static String getNewCode(String parent_code, HashSet<String> list, int len, int start_num) {
        String tmp = getZeroString(len, start_num);
        int tmp_num = start_num + 1;
        while (true) {
            if (tmp == null) {
                break;
            }
            if (list == null) {
                break;
            }
            if (!list.contains((parent_code + tmp).toUpperCase())) {
                break;
            }
            tmp = getZeroString(len, tmp_num);
            tmp_num++;
            if (tmp.equals("-1")) {
                return null;
            }
        }
        if (tmp == null) {
            MsgUtil.showInfoMsg(CommMsg.CODELENGTHlIMIT);
            return null;
        }
        return parent_code + tmp;
    }

    private static String getZeroString(int len, int num) {
        String tmp = "";
        int len1 = len - (Integer.toHexString(num)).length();
        if (len1 < 0) {
            return null;
        }
        for (int i = 0; i < len1; i++) {
            tmp += "0";
        }
        tmp += Integer.toHexString(num);
        return tmp;
    }

    public static String getNewCode(String parent_code) {
        String tmp2 = "000000000000000000000000000000000000000000";
        String tmp = "";
        tmp = (String) CommUtil.fetchEntityBy("select max(code_id) from Code c where c.parent_id='" + parent_code + "'");
        if (tmp == null) {
            tmp2 += "1";
        } else {
            tmp2 += (Integer.valueOf(tmp) + 1);
        }
        if (parent_code.equals("ROOT")) {
            tmp2 = tmp2.substring(tmp2.length() - 2);
        } else {
            tmp2 = parent_code + tmp2.substring(tmp2.length() - 2);
        }
        return tmp2;
    }

    public static String getNewCodeType() {
        String tmp2 = "000000000000000000000000000000000000000000";
        String tmp = "";
        tmp = (String) CommUtil.fetchEntityBy("select max(code_rule) from CodeType");
        if (tmp == null) {
            tmp2 += "1";
        } else {
            tmp2 += (Integer.valueOf(tmp) + 1);
        }
        tmp2 = tmp2.substring(tmp2.length() - 2);
        return tmp2;
    }

    public static int getPositionCodeLength(int level) {
        SysParameter sp = getPositionGrade();
        int len = 0;
        String[] tmp = sp.getSysparameter_value().split(";");
        for (int i = 0; i < level; i++) {
            len = len + Integer.valueOf(tmp[i]);
        }
        return len;
    }

    //��ȡ�ַ���������ʹ���ֽ�λ��ȡ
    public static String subByteStr(String value, int len) {
        if (value.equals("")) {
            return value;
        }
        char[] ss = value.toCharArray();
        int i = 0;
        int str_len = value.length();
        int result_len = str_len;
        if (str_len * 2 < len) {
            len = str_len * 2;
        }
        for (int j = 0; j < str_len; j++) {
            if (ss[j] >= 32 && ss[j] < 127) {
                i++;
            } else {
                i = i + 2;
            }
            if (i > len) {
                break;
            }
            result_len = j + 1;
        }
        char[] tmp = new char[result_len];
        for (int j = 0; j < result_len; j++) {
            tmp[j] = ss[j];
        }
        return String.valueOf(tmp);
    }

    public static int BoolToInt(boolean b) {
        return b ? 1 : 0;
    }

    public static Object BoolToStr(
            boolean b) {
        return b ? "Y" : "N";
    }
    //objת����string,Ĭ��ֵΪ""

    public static String objToStr(Object obj) {
        return objToStr(obj, "");
    }
//	objת����string,Ĭ��ֵΪdefault_value,Ĭ��ֵ����objΪnullʱ�ķ��ؽ��

    public static String objToStr(Object obj, String default_value) {
        return obj == null ? default_value : obj.toString();
    }
    //objת����integer

    public static int objToInt(Object code) {
        return objToInt(code, 0);
    }
    //	objת����integer,Ĭ��Ϊdefault_value

    public static int objToInt(Object code, int default_value) {
        try {
            return new Integer(Integer.parseInt(objToStr(code).replace(" ", ""))).intValue();
        } catch (Exception e) {
            return default_value;
        }
    }

    public static BigInteger objToBigInt(Object code) {
        return objToBigInt(code, new BigInteger("" + 0));
    }

    public static BigInteger objToBigInt(Object code, BigInteger default_value) {
        try {
            return new BigInteger(objToStr(code));
        } catch (Exception e) {
            return default_value;
        }
    }

    public static Integer objToInteger(Object code) {
        return objToInteger(code, 0);
    }
    //	objת����integer,Ĭ��Ϊdefault_value

    public static Integer objToInteger(Object code, Integer default_value) {
        try {
            return new Integer(Integer.parseInt(objToStr(code).replace(" ", ""))).intValue();
        } catch (Exception e) {
            return default_value;
        }
    }
//	objת����dobule

    public static Double objToDouble(Object code) {
        return objToDouble(code, Double.valueOf(0));
    }
//	objת����double,Ĭ��Ϊdefault_value

    public static Double objToDouble(Object code, Double default_value) {
        try {
            return Double.parseDouble(objToStr(code));
        } catch (Exception e) {
            return default_value;
        }
    }
//	objת����float

    public static Float objToFloat(Object code) {
        return objToFloat(code, Float.valueOf(0));
    }
//	objת����float,Ĭ��Ϊdefault_value

    public static Float objToFloat(Object code, Float default_value) {
        try {
            return Float.parseFloat(objToStr(code));
        } catch (Exception e) {
            return default_value;
        }
    }
//	objת����bigdecimal

    public static BigDecimal objToBigDecimal(Object code) {
        return objToBigDecimal(code, BigDecimal.valueOf(0));
    }
//	objת����bigdecimal,Ĭ��Ϊdefault_value

    public static BigDecimal objToBigDecimal(Object code, BigDecimal default_value) {
        try {
            //return BigDecimal.valueOf(Float.parseFloat(objToStr(code)));
            return BigDecimal.valueOf(Double.parseDouble(objToStr(code)));
        } catch (Exception e) {
            return default_value;
        }
    }
    //obj ת����boolean

    public static boolean objToBoolean(Object code) {
        if (code == null) {
            return false;
        }
        if ("1".equals(code.toString()) || "true".equals(code.toString()) || "��".equals(code.toString())) {
            return true;
        }
        return false;
    }

    public static boolean isFloat(String code) {
        try {
            Float.parseFloat(code);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNum(String code) {
        try {
            //Long.parseLong(code);
            Double.parseDouble(code);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //���������λ����
    public static String getNewChildG10CodeOf(String parent_code) {
        if (parent_code == null) {
            parent_code = "";
        }
        String tmp = "";
        int parent_code_len = 2;
        Object obj = (String) CommUtil.fetchEntityBy("select max(code) from G10 d where d.code like '" + parent_code + "%'");
        if (obj == null || obj.toString().trim().equals("")) {
            tmp = "1";
        } else {
            tmp = obj.toString();
            tmp = "" + (Integer.valueOf(tmp.substring(tmp.length() - parent_code_len, tmp.length())) + 1);
        }
        while (tmp.length() < parent_code_len) {
            tmp = "0" + tmp;
        }
        return parent_code + tmp;
    }

    public static String getNewChildG10NextCodeOf(String code) {
        int parent_code_len = 2;
        String parent_code = code.substring(0, code.length() - parent_code_len);
        String tmp = "";
        tmp = "" + (Integer.valueOf(code.substring(code.length() - parent_code_len, code.length())) + 1);
        while (tmp.length() < parent_code_len) {
            tmp = "0" + tmp;
        }
        return parent_code + tmp;
    }

    //����������ű���
    public static String getNewChildDeptCode1Of(String parent_code, int parent_level, SysParameter sp) {
        return getNewChildDeptCode1Of(parent_code, parent_level, sp, null);
    }
    //����������ű���

    public static String getNewChildDeptCode1Of(String parent_code, int parent_level, SysParameter sp, String old_code) {
        if (parent_code == null) {
            parent_code = "";
        }
        String tmp = "";
        String[] code_lens = sp.getSysparameter_value().split(";");
        int parent_code_len = Integer.valueOf(code_lens[parent_level]);
        if (parent_level == 0) {
            tmp = "1";
        } else {
            Object obj = null;
            if (old_code != null) {
                int ind = -1;
                int len = old_code.length();
                for (int i = 0; i < code_lens.length; i++) {
                    len = len - Integer.valueOf(code_lens[i]);
                    if (len < 0) {
                        break;
                    }
                    if (len == 0) {
                        ind = i;
                        break;
                    }
                }
                if (ind != -1) {
                    String code = old_code.substring(old_code.length() - Integer.valueOf(code_lens[ind]));
                    int value = Integer.valueOf(code);
                    tmp = value + "";
                    if (tmp.length() <= parent_code_len) {
                        while (tmp.length() < parent_code_len) {
                            tmp = "0" + tmp;
                        }
                        tmp = parent_code + tmp;
                        if (!CommUtil.exists("select 1 from DeptCode where dept_code='" + tmp + "'")) {
                            return tmp;
                        }
                    }
                    tmp = "";
                }
            }
            List list = CommUtil.fetchEntities("select dept_code from DeptCode d where d.parent_code='" + parent_code + "'");
            int value = 1;
            tmp = getNo(parent_code, value, parent_code_len);
            while (tmp != null && list.contains(tmp)) {
                value++;
                tmp = getNo(parent_code, value, parent_code_len);
            }
            return tmp;
//            if (list == null || list.isEmpty()) {
//                tmp = "1";
//            } else {
//
//                tmp = obj.toString();
//                tmp = "" + (Integer.valueOf(tmp.substring(tmp.length() - parent_code_len, tmp.length())) + 1);
//                if (tmp.length() > parent_code_len) {
//                    JOptionPane.showMessageDialog(null, "�޷����䲿�ű���");
//                    return null;
//                }
//            }
        }
        while (tmp.length() < parent_code_len) {
            tmp = "0" + tmp;
        }
        return parent_code + tmp;
    }

    private static String getNo(String parent_code, int value, int parent_code_len) {
        String tmp = value + "";
        if (tmp.length() > parent_code_len) {
//            JOptionPane.showMessageDialog(null, "�޷����䲿�ű���");
            MsgUtil.showInfoMsg(CommMsg.UNABLETOALLOCATE);
            return null;
        }
        while (tmp.length() < parent_code_len) {
            tmp = "0" + tmp;
        }
        tmp = parent_code + tmp;
        return tmp;
    }

    public static boolean isRightIdentityNum(String IdentityNo) {
        IdentityNo = IdentityNo.toUpperCase();
        boolean result = true;
        if (IdentityNo.length() == 15) {
            for (int i = 0; i < 15; i++) {
                if (IdentityNo.charAt(i) < '0' || IdentityNo.charAt(i) > '9') {
                    result = false;
                }
            }
        } else if (IdentityNo.length() == 18) {
            for (int i = 0; i < 17; i++) {
                if (IdentityNo.charAt(i) < '0' || IdentityNo.charAt(i) > '9') {
                    result = false;
                }
            }
            if (!((IdentityNo.charAt(17) >= '0' && IdentityNo.charAt(17) <= '9') || IdentityNo.charAt(17) == 'X')) {
                result = false;
            }
        } else {
            result = false;
        }
        return result;
    }

    /**
     * �÷�������У�����֤�������Ч�ԣ����в���֤���֤��У��λ��Ч��
     * @param IdentityNo�����֤����
     * @return����Ч�ԣ�true,false)
     */
    public static boolean isRightIdentity(String IdentityNo) {
        IdentityNo = IdentityNo.toUpperCase();
        if (IdentityNo.length() == 10) {
            return XGIDCheck.checkId(IdentityNo);
        }
        int re = checkCertiCode(IdentityNo);
        boolean result = true;
        if (re != 0) {
            result = false;
        }
//        if (isRightIdentityNum(IdentityNo)) {
//            if (IdentityNo.length() == 15) {
//                int y = Integer.parseInt(IdentityNo.substring(6, 8));
//                int m = Integer.parseInt(IdentityNo.substring(8, 10));
//                int d = Integer.parseInt(IdentityNo.substring(10, 12));
//                if (m < 1 || m > 12 || d < 1 || d > 31 || ((m == 4 || m == 6 || m == 9 || m == 11) && d > 30) || (m == 2 && (((y + 1900) % 4 > 0 && d > 28) || d > 29))) {
//                    result = false;//���֤��������
//                }
//            } else if (IdentityNo.length() == 18) {
//                int y = Integer.parseInt(IdentityNo.substring(6, 10));
//                int m = Integer.parseInt(IdentityNo.substring(10, 12));
//                int d = Integer.parseInt(IdentityNo.substring(12, 14));
//                int[] xx = {2, 4, 8, 5, 10, 9, 7, 3, 6, 1, 2, 4, 8, 5, 10, 9, 7};
//                char[] yy = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
//                int mm = 0;
//                int[] gg = new int[18];
//                if (y < 1900 || m < 1 || m > 12 || d < 1 || d > 31 || ((m == 4 || m == 6 || m == 9 || m == 11) && d > 30) || (m == 2 && ((y % 4 > 0 && d > 28) || d > 29))) {
//                    result = false;
//                }
//                else {
//                    for (int i = 1; i < 18; i++) {
//                        int j = 17 - i;
//                        gg[i - 1] = Integer.parseInt(IdentityNo.substring(j, j + 1));
//                    }
//                    for (int i = 0; i < 17; i++) {
//                        mm += xx[i] * gg[i];
//                    }
//                    mm = mm % 11;
//                    char c = IdentityNo.charAt(17);
//                    if (c != yy[mm]) {
//                        result = false;
//                    }
//                }
//            }
//        } else {
//            result = false;
//        }

        return result;
    }

//    public static boolean isRightIdentity2(String IdentityNo) {
//        IdentityNo = IdentityNo.toUpperCase();
//        boolean result = true;
//        if (isRightIdentityNum(IdentityNo)) {
//            if (IdentityNo.length() == 15) {
////                int y = Integer.parseInt(IdentityNo.substring(6, 8));
////                int m = Integer.parseInt(IdentityNo.substring(8, 10));
////                int d = Integer.parseInt(IdentityNo.substring(10, 12));
////                if (m < 1 || m > 12 || d < 1 || d > 31 || ((m == 4 || m == 6 || m == 9 || m == 11) && d > 30) || (m == 2 && (((y + 1900) % 4 > 0 && d > 28) || d > 29))) {
////                    result = false;//���֤��������
////                }
//            } else if (IdentityNo.length() == 18) {
////                int y = Integer.parseInt(IdentityNo.substring(6, 10));
////                int m = Integer.parseInt(IdentityNo.substring(10, 12));
////                int d = Integer.parseInt(IdentityNo.substring(12, 14));
////                int[] xx = {2, 4, 8, 5, 10, 9, 7, 3, 6, 1, 2, 4, 8, 5, 10, 9, 7};
////                char[] yy = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
////                int mm = 0;
////                int[] gg = new int[18];
////                if (y < 1900 || m < 1 || m > 12 || d < 1 || d > 31 || ((m == 4 || m == 6 || m == 9 || m == 11) && d > 30) || (m == 2 && ((y % 4 > 0 && d > 28) || d > 29))) {
////                    result = false;
////                } else {
////                    for (int i = 1; i < 18; i++) {
////                        int j = 17 - i;
////                        gg[i - 1] = Integer.parseInt(IdentityNo.substring(j, j + 1));
////                    }
////                    for (int i = 0; i < 17; i++) {
////                        mm += xx[i] * gg[i];
////                    }
////                    mm = mm % 11;
////                    char c = IdentityNo.charAt(17);
////                    if (c != yy[mm]) {
////                        result = false;
////                    }
////                }
//            } else {
//                result = false;
//            }
//        } else {
//            result = false;
//        }
//
//        return result;
//    }
    public static Date getBirthFromIdentityCard(String IdentityNo) {
        Date date = null;
        try {
            if (IdentityNo == null || IdentityNo.length() < 15) {
                return null;
            }
            String tmpDate = null;
            if (IdentityNo.length() == 15) {
                tmpDate = "19" + IdentityNo.substring(6, 8) + "-" + IdentityNo.substring(8, 10) + "-" + IdentityNo.substring(10, 12);
            } else if (IdentityNo.length() == 18) {
                tmpDate = IdentityNo.substring(6, 10) + "-" + IdentityNo.substring(10, 12) + "-" + IdentityNo.substring(12, 14);
            }
            DateFormat df = DateFormat.getDateInstance();
            date = df.parse(tmpDate);

        } catch (ParseException ex) {
            HrLog.error(SysUtil.class, ex);
        }
        return date;
    }

    public static String getSexFromIdentityCard(String IdentityNo) {
        String tmp = "δ֪";
        if (IdentityNo.length() == 15) {
            int sex = Integer.parseInt(IdentityNo.substring(14, 15));
            if (sex % 2 != 0) {
                tmp = "��";
            } else {
                tmp = "Ů";
            }
        } else if (IdentityNo.length() == 18) {
            int sex = Integer.parseInt(IdentityNo.substring(16, 17));
            if (sex % 2 != 0) {
                tmp = "��";
            } else {
                tmp = "Ů";
            }
        }
        return tmp;
    }

    public static int getAgeFromIdentityCard(String IdentityNo) {
        int age = 0;
        if (IdentityNo == null || IdentityNo.length() < 15) {
            return 0;
        }
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        int year = 0;
        int month = 0;
        int day = 0;
        if (IdentityNo.length() == 15) {
            year = Integer.valueOf(IdentityNo.substring(6, 8)) + 1900;
            month = Integer.valueOf(IdentityNo.substring(8, 10));
            day = Integer.valueOf(IdentityNo.substring(10, 12));
        } else if (IdentityNo.length() == 18) {
            year = Integer.valueOf(IdentityNo.substring(6, 10));
            month = Integer.valueOf(IdentityNo.substring(10, 12));
            day = Integer.valueOf(IdentityNo.substring(12, 13));
        }
        age = Integer.valueOf(((today.get(today.YEAR) - year) * 12 + (today.get(today.MONTH) - month)) / 12);
        if (today.get(Calendar.DAY_OF_MONTH) > day) {
            age = age + 1;
        }
        return age;
    }

    public static String BianHao(int index) {
        String result = "";
        if (index >= 0 && index <= 9) {
            result = "0" + String.valueOf(index);
        } else if (index >= 10 && index <= 99) {
            result = String.valueOf(index);
        } else if (index >= 100 && index <= 359) {
            int t_index = index - 100;
            int a = t_index / 26;
            int b = t_index % 26;
            result = String.valueOf(a) + zimu[b];
        } else if (index >= 360 && index <= 1295) {
            int t_index = index - 360;
            int a = t_index / 36;
            int b = t_index % 36;
            String str = "";
            if (b >= 0 && b <= 9) {
                str = String.valueOf(b);
            } else {
                str += zimu[b - 10];
            }
            result = zimu[a] + str;
        }
        return result;
    }

    public static void sortListByStr(List list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Object obj = list.get(0);
        final String key = EntityBuilder.getEntityKey(obj.getClass());
        sortListByStr(list, key);
    }

    public static void sortListByStr(List list, final String key) {
        sortListByStr(list, key, true);
    }
    /*
     * ASC �����ֶε�����ʽ ASC=true ����
     * 
     */

    public static void sortListByStr(List list, final String key, final boolean ASC) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Collections.sort(list, new Comparator() {

            @Override
            public int compare(Object arg0, Object arg1) {
                Object key1 = PublicUtil.getProperty(arg0, key);
                Object key2 = PublicUtil.getProperty(arg1, key);
                if (ASC) {
                    return (key1 == null ? "0" : key1.toString()).compareTo((key2 == null ? "1" : key2.toString()));
                } else {
                    return (key2 == null ? "0" : key2.toString()).compareTo((key1 == null ? "1" : key1.toString()));
                }
            }
        });
    }

    public static void sortObjList(List<Object[]> list, final int col_index, final boolean ASC) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (((Object[]) list.get(0)).length <= col_index) {
            return;
        }
        Collections.sort(list, new Comparator() {

            @Override
            public int compare(Object obj1, Object obj2) {
                Object o1[] = (Object[]) obj1;
                Object o2[] = (Object[]) obj2;
                Object key1 = o1[col_index];
                Object key2 = o2[col_index];
                if (ASC) {
                    return (key1 == null ? "0" : key1.toString()).compareTo((key2 == null ? "1" : key2.toString()));
                } else {
                    return (key2 == null ? "0" : key2.toString()).compareTo((key1 == null ? "1" : key1.toString()));
                }
            }
        });
    }

    public static void sortStrList(List<String> list) {
        sortStrList(list, true);
    }
    /*
     *  ��List�����д�ŵ�String ���Ͱ��������򣬲��Ҵ����˿�ֵ�����
     *  �൱��Collections.sort();
     *  Collections.sort()������������ֵ�п�ֵʱ����
     */

    public static void sortStrList(List<String> list, final boolean ASC) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Collections.sort(list, new Comparator() {

            @Override
            public int compare(Object key1, Object key2) {
                if (ASC) {
                    return (key1 == null ? "0" : key1.toString()).compareTo((key2 == null ? "1" : key2.toString()));
                } else {
                    return (key2 == null ? "0" : key2.toString()).compareTo((key1 == null ? "1" : key1.toString()));
                }
            }
        });
    }

    public static void sortArrays(List<String[]> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Collections.sort(list, new Comparator() {

            @Override
            public int compare(Object arg0, Object arg1) {
                String key1 = ((String[]) arg0)[0];
                String key2 = ((String[]) arg1)[0];
                return key1.compareTo(key2);
            }
        });
    }

    public static boolean isRightCard(String samId) {
        boolean flag = false;
//        if(UserContext.idCard_sams == null || UserContext.idCard_sams.size() == 0){
//            UserContext.idCard_sams = (List<String>) CommUtil.selectSQL("select id_value from SamId");
//        }
//        String str = PublicUtil.getkeyBeanofStr(samId + "webhr2010");
        // List list = CommUtil.selectSQL("select id_value from SamId");
//        flag = UserContext.idCard_sams.contains(str);
        return flag;
    }

    private static boolean checkIDParityBit(String certiCode) {
        boolean flag = false;
        if (certiCode == null || "".equals(certiCode)) {
            return false;
        }
        int ai[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
        if (certiCode.length() == 18) {
            int i = 0;
            for (int k = 0; k < 18; k++) {
                char c = certiCode.charAt(k);
                int j;
                if (c == 'X') {
                    j = 10;
                } else if (c <= '9' || c >= '0') {
                    j = c - 48;
                } else {
                    return flag;
                }
                i += j * ai[k];
            }

            if (i % 11 == 1) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * ������ڸ�ʽ
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    private static boolean checkDate(String year, String month, String day) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
        try {
            String s3 = year + month + day;
            simpledateformat.setLenient(false);
            simpledateformat.parse(s3);
        } catch (java.text.ParseException parseexception) {
            return false;
        }
        return true;
    }

    /**
     * У�����֤
     *
     * @param certiCode
     *            ��У�����֤
     * @return 0--У��ɹ�; 1--λ������; 2--���ո�ʽ���� ; 3--У��λ���� ; 4--�����쳣;5--�ַ��쳣;
     * @param certiCode
     * @return
     */
    private static int checkCertiCode(String certiCode) {
        try {
            if (certiCode == null || certiCode.length() != 15
                    && certiCode.length() != 18) {
                return 1;
            }
            String s1;
            String s2;
            String s3;

            if (certiCode.length() == 15) {

                if (!checkFigure(certiCode)) {
                    return 5;
                }

                s1 = "19" + certiCode.substring(6, 8);
                s2 = certiCode.substring(8, 10);
                s3 = certiCode.substring(10, 12);

                if (!checkDate(s1, s2, s3)) {
                    return 2;
                }
            }

            if (certiCode.length() == 18) {
                if (!checkFigure(certiCode.substring(0, 17))) {
                    return 5;
                }

                s1 = certiCode.substring(6, 10);
                s2 = certiCode.substring(10, 12);
                s3 = certiCode.substring(12, 14);

                if (!checkDate(s1, s2, s3)) {
                    return 2;
                }
                if (!checkIDParityBit(certiCode)) {
                    return 3;
                }
            }
        } catch (Exception exception) {

            return 4;
        }
        return 0;
    }

    /**
     * �ж����֤�����Ƿ���Ч
     * @param idCard
     * @return
     */
    public static boolean isIdCard(String idCard) {
        if (checkCertiCode(idCard) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ����ַ����Ƿ�ȫΪ����
     * @param certiCode
     * @return
     */
    private static boolean checkFigure(String certiCode) {
        try {
            Long.parseLong(certiCode);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * �÷������ڴ�����״�ṹList<String>,���磺101,10101,1010101,���˵����õ�����
     * @param data����״�ṹList
     * @param onlyChild���Ƿ�ֻҪ�Ӽ����ݣ���������Ϊtrueʱ������ֻҪ1010101��Ϊfalseʱ�����ֻҪ101
     * @return 
     */
    public static List<String> tranTreeStr(List<String> data, boolean onlyChild) {
        SysUtil.sortStrList(data);
        List<String> result = new ArrayList<String>();
        List<String> removes = new ArrayList<String>();
        if (onlyChild) {
            for (String key : data) {
                for (String exist : result) {
                    if (key.startsWith(exist)) {
                        removes.add(exist);
                    }
                }
                result.removeAll(removes);
                removes.clear();
                result.add(key);
            }
        } else {
            for (String key : data) {
                boolean exists = false;
                for (String exist : result) {
                    if (key.startsWith(exist)) {
                        exists = true;
                        removes.add(key);
                        break;
                    }
                }
                if (exists) {
                    continue;
                }
                result.add(key);
            }
        }
        return result;
    }

    public static Set<String> getAllClassNames(String pckgname) {
        Set<String> classes = new HashSet<String>();
        try {
            classes.addAll(getClassNames(pckgname));
        } catch (Exception ex) {
        }
        return classes;
    }

    public static Set<String> getClassNames(String pack) {

        // ��һ��class��ļ���  
        Set<String> classes = new LinkedHashSet<String>();
        // �Ƿ�ѭ������  
        boolean recursive = true;
        // ��ȡ�������� �������滻  
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        // ����һ��ö�ٵļ��� ������ѭ�����������Ŀ¼�µ�things  
        Enumeration<URL> dirs;
        try {
            dirs = loader.getResources(packageDirName);
            // ѭ��������ȥ  
            while (dirs.hasMoreElements()) {
                // ��ȡ��һ��Ԫ��  
                URL url = dirs.nextElement();
                // �õ�Э�������  
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    // ��ȡ��������·��  
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
//                     ���ļ��ķ�ʽɨ���������µ��ļ� ����ӵ�������  
                    findClassNamesInPackageByFile(packageName, filePath,
                            recursive, classes, loader);
                } else if ("jar".equals(protocol)) {
                    // �����jar���ļ�  
                    // ����һ��JarFile  
                    JarFile jar;
                    try {
                        // ��ȡjar  
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        // �Ӵ�jar�� �õ�һ��ö����  
                        Enumeration<JarEntry> entries = jar.entries();
                        // ͬ���Ľ���ѭ������  
                        while (entries.hasMoreElements()) {
                            // ��ȡjar���һ��ʵ�� ������Ŀ¼ ��һЩjar����������ļ� ��META-INF���ļ�  
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();                              // �������/��ͷ��  
                            if (name.charAt(0) == '/') {
                                // ��ȡ������ַ���  
                                name = name.substring(1);
                            }
                            // ���ǰ�벿�ֺͶ���İ�����ͬ  
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
//                                // �����"/"��β ��һ����  
//                                if (idx != -1) {
//                                    // ��ȡ���� ��"/"�滻��"."  
//                                    packageName = name.substring(0, idx).replace('/', '.');
//                                }
                                // ������Ե�����ȥ ������һ����  
                                if ((idx != -1) || recursive) {
                                    // �����һ��.class�ļ� ���Ҳ���Ŀ¼  
                                    if (name.endsWith(".class") && !entry.isDirectory() && !name.contains("$")) {
                                        classes.add(name.substring(0, name.length() - 6).replace('/', '.'));
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        // log.error("��ɨ���û�������ͼʱ��jar����ȡ�ļ�����");  
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    private static void findClassNamesInPackageByFile(String packageName,
            String packagePath, final boolean recursive, Set<String> classes, ClassLoader loader) {
        // ��ȡ�˰���Ŀ¼ ����һ��File  
        File dir = new File(packagePath);
        // ��������ڻ��� Ҳ����Ŀ¼��ֱ�ӷ���  
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("�û�������� " + packageName + " ��û���κ��ļ�");  
            return;
        }
        // ������� �ͻ�ȡ���µ������ļ� ����Ŀ¼  
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // �Զ�����˹��� �������ѭ��(������Ŀ¼) ��������.class��β���ļ�(����õ�java���ļ�)  

            public boolean accept(File file) {
                return (recursive && file.isDirectory())
                        || (file.getName().endsWith(".class") && !file.getName().contains("$"));
            }
        });
        // ѭ�������ļ�  
        for (File file : dirfiles) {
            // �����Ŀ¼ �����ɨ��  
            if (file.isDirectory()) {
                findClassNamesInPackageByFile(packageName + "."
                        + file.getName(), file.getAbsolutePath(), recursive,
                        classes, loader);
            } else {
                // �����java���ļ� ȥ�������.class ֻ��������  
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                classes.add((packageName + '.' + className).replace('/', '.').replace("\\", "."));
            }
        }
    }
    /*
     * ȡ��ĳһ��·������������
     */

    public static Set<Class<?>> getAllClasses(String pckgname) {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        try {
            classes.addAll(getClasses(pckgname));
        } catch (Exception ex) {
        }
        return classes;
    }

    public static Set<Class<?>> getClasses(String pack) {

        // ��һ��class��ļ���  
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        // �Ƿ�ѭ������  
        boolean recursive = true;
        // ��ȡ�������� �������滻  
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        // ����һ��ö�ٵļ��� ������ѭ�����������Ŀ¼�µ�things  
        Enumeration<URL> dirs;
        try {
            dirs = loader.getResources(packageDirName);
            // ѭ��������ȥ  
            while (dirs.hasMoreElements()) {
                // ��ȡ��һ��Ԫ��  
                URL url = dirs.nextElement();
                // �õ�Э�������  
                String protocol = url.getProtocol();
                // ��������ļ�����ʽ�����ڷ�������  
                if ("file".equals(protocol)) {
                    // ��ȡ��������·��  
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
//                     ���ļ��ķ�ʽɨ���������µ��ļ� ����ӵ�������  
                    findAndAddClassesInPackageByFile(packageName, filePath,
                            recursive, classes, loader);
                } else if ("jar".equals(protocol)) {
                    // �����jar���ļ�  
                    // ����һ��JarFile  
                    JarFile jar;
                    try {
                        // ��ȡjar  
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        // �Ӵ�jar�� �õ�һ��ö����  
                        Enumeration<JarEntry> entries = jar.entries();
                        // ͬ���Ľ���ѭ������  
                        while (entries.hasMoreElements()) {
                            // ��ȡjar���һ��ʵ�� ������Ŀ¼ ��һЩjar����������ļ� ��META-INF���ļ�  
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();                              // �������/��ͷ��  
                            if (name.charAt(0) == '/') {
                                // ��ȡ������ַ���  
                                name = name.substring(1);
                            }
                            // ���ǰ�벿�ֺͶ���İ�����ͬ  
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // �����"/"��β ��һ����  
                                if (idx != -1) {
                                    // ��ȡ���� ��"/"�滻��"."  
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                // ������Ե�����ȥ ������һ����  
                                if ((idx != -1) || recursive) {
                                    // �����һ��.class�ļ� ���Ҳ���Ŀ¼  
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        // ȥ�������".class" ��ȡ����������  
                                        String className = name.substring(
                                                packageName.length() + 1, name.length() - 6);
                                        try {
                                            // ��ӵ�classes  
                                            classes.add(Class.forName(packageName + '.'
                                                    + className));
                                        } catch (ClassNotFoundException e) {
                                            // log  
                                            // .error("����û��Զ�����ͼ����� �Ҳ��������.class�ļ�");  
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        // log.error("��ɨ���û�������ͼʱ��jar����ȡ�ļ�����");  
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    private static void findAndAddClassesInPackageByFile(String packageName,
            String packagePath, final boolean recursive, Set<Class<?>> classes, ClassLoader loader) {
        // ��ȡ�˰���Ŀ¼ ����һ��File  
        File dir = new File(packagePath);
        // ��������ڻ��� Ҳ����Ŀ¼��ֱ�ӷ���  
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("�û�������� " + packageName + " ��û���κ��ļ�");  
            return;
        }
        // ������� �ͻ�ȡ���µ������ļ� ����Ŀ¼  
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // �Զ�����˹��� �������ѭ��(������Ŀ¼) ��������.class��β���ļ�(����õ�java���ļ�)  

            public boolean accept(File file) {
                return (recursive && file.isDirectory())
                        || (file.getName().endsWith(".class"));
            }
        });
        // ѭ�������ļ�  
        for (File file : dirfiles) {
            // �����Ŀ¼ �����ɨ��  
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "."
                        + file.getName(), file.getAbsolutePath(), recursive,
                        classes, loader);
            } else {
                // �����java���ļ� ȥ�������.class ֻ��������  
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                try {
                    classes.add(loader.loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    // log.error("����û��Զ�����ͼ����� �Ҳ��������.class�ļ�");  
                    e.printStackTrace();
                }
            }
        }
    }

    public static BufferedImage getBufferedImageBybytes(byte[] imgbyte) {
        if (imgbyte == null || imgbyte.length == 0) {
            return null;
        }
        InputStream is = new ByteArrayInputStream(imgbyte);
        BufferedImage origImage = null;
        try {
            origImage = ImageIO.read(is);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return origImage;
    }

    public static final byte[] hexToBytes(String s) {
        byte[] bytes;
        bytes = new byte[s.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16);
        }
        return bytes;
    }
}
