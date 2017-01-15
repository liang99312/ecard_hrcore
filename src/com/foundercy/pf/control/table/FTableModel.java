package com.foundercy.pf.control.table;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.comm.CodeManager;
import org.jhrcore.comm.FieldTrigerManager;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.entity.Code;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.rebuild.EntityBuilder;

public class FTableModel extends AbstractTableModel {

    public static final long serialVersionUID = -1L;
    private List<Object> objects = new ArrayList<Object>();
    private List columnList = new ArrayList();
    private Class<?> entityClass;
    private Object black_object = null;
    private boolean editable = false;
    private String key_field;// 网格所显示的类的主键名称
    private List<RowChangeListner> listRowChangeListners = new ArrayList<RowChangeListner>();
    private boolean notShowZero = false;
    private List<String> disableCells = new ArrayList<String>();
    // 设置网格那些单元格不可编辑，如“1,2”、“2,3”，表示第一行第二列和第二行第三列这两个单元格不可编辑

    public void setDisableCells(List<String> temp_disableCells) {
        disableCells.clear();
        disableCells.addAll(temp_disableCells);
    }

    public void setNotShowZero(boolean notShowZero) {
        this.notShowZero = notShowZero;
    }

    public Object getBlack_object() {
        return black_object;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
        Enumeration<FColumnStat> e = ht_columnStat.elements();
        while (e.hasMoreElements()) {
            e.nextElement().setStat_val(0);
        }
        this.fireTableDataChanged();
    }

    public void addRowChangeListner(RowChangeListner listner) {
        listRowChangeListners.add(listner);
    }

    public void removeRowChangeListner(RowChangeListner listner) {
        listRowChangeListners.remove(listner);
    }

    public FTableModel() {
        super();
    }

    public void setColumnList(List columnList) {
        this.columnList = columnList;
    }

    public List getColumnList() {
        return this.columnList;
    }

    /**
     * 得到表格的总行数
     */
    public int getRowCount() {
        if (objects != null) {
            return Math.max(1, objects.size());
        } else {
            return 0;
        }
    }

    /**
     * 得到表格的列数
     */
    public int getColumnCount() {
        if (columnList != null) {
            return columnList.size();
        } else {
            return 0;
        }
    }

    /**
     * 得到列名称
     */
    public String getColumnName(int column) {
        if (column >= columnList.size()) {
            return "";
        }
        return ((FTableColumnItem) columnList.get(column)).getId();
    }

    /**
     * lindx add
     */
    public int findColumn(String columnName) {
        FTableColumnItem item = null;
        for (int i = 0; i < columnList.size(); i++) {
            item = (FTableColumnItem) columnList.get(i);

            if (columnName.equals(item.getId())) {
                return i;
            }
        }
        return -1;
    }
    private int fetch_size = 30;
    // 保存从其他表引入的值，索引为 表名@行号，HashMap为:字段名、值
    private Hashtable<String, HashMap> ht_OtherTable = new Hashtable<String, HashMap>();
    // 保存从其他表获取数据时使用的SQL语句，例如：
    // select f01,f02 from Tab1 where tab1_key
    // 该语句拼上当前行的对象主键为完整的获取数据语句。
    private Hashtable<String, String> ht_OtherTableSql = new Hashtable<String, String>();

    public Hashtable<String, HashMap> getHt_OtherTable() {
        return ht_OtherTable;
    }

    public Hashtable<String, String> getHt_OtherTableSql() {
        return ht_OtherTableSql;
    }

    public void setHt_OtherTableSql(Hashtable<String, String> ht_OtherTableSql) {
        this.ht_OtherTableSql = ht_OtherTableSql;
    }

    private Object otherTableValue(Object obj, FTableColumnItem item, int rowIndex) {
        String fullFieldName = item.getId();
        String tableName = fullFieldName.substring(1, fullFieldName.indexOf("."));
        String fieldName = fullFieldName.substring(fullFieldName.indexOf(".") + 1);
        HashMap hm = ht_OtherTable.get(tableName + "@" + rowIndex);
        if (hm == null) {
            String sql = ht_OtherTableSql.get(tableName);
            if (sql == null) {
                return null;
            }
            String entityName = entityClass.getSimpleName();
            String other_tname = "";//加个别名
            String temp_t = sql.substring(sql.indexOf("from"), sql.indexOf("where")).replace("from", "").trim();
            String[] temp_ts = temp_t.split(",");
            for (String temp_table : temp_ts) {
                temp_table = temp_table.trim();
                if (temp_table.indexOf(" ") > 0 && entityName.equalsIgnoreCase(temp_table.substring(0, temp_table.indexOf(" ")))) {
                    other_tname = temp_table.substring(temp_table.lastIndexOf(" ") + 1, temp_table.length()) + ".";
                }
            }
            String tmp_sel_fields = sql.substring(0, sql.indexOf("from"));
            String[] sel_fields = tmp_sel_fields.split(",");
            //去掉别名，用于对应网格字段名
            for (int i = 0; i < sel_fields.length; i++) {
                String temp_f = sel_fields[i];
                if (temp_f.indexOf(".") > 0) {
                    temp_f = temp_f.substring(temp_f.indexOf(".") + 1);
                    sel_fields[i] = temp_f;
                }
            }
            String keys = "";
            int min = Math.max(0, rowIndex - fetch_size);
            int max = Math.min(objects.size(), rowIndex + fetch_size);
            for (int i = min; i < max; i++) {
                if (ht_OtherTable.get(tableName + "@" + i) != null) {
                    continue;
                }
                Object obj_2 = objects.get(i);
                if (obj_2 instanceof String) {
                    keys = keys + ",'" + obj_2 + "'";
                } else {
                    Object key_obj = PublicUtil.getProperty(obj_2, key_field);
                    keys = keys + ",'" + key_obj + "'";
                }
            }
            ArrayList objs = null;
            if (keys.equals("")) {
                objs = new ArrayList();
            } else {
                keys = keys.substring(1);
                String tmp_sql = "select " + other_tname + key_field + "," + sql + " in (" + keys + ")";
                objs = CommUtil.selectSQL(tmp_sql);
            }
            for (int i = min; i < max; i++) {
                if (ht_OtherTable.get(tableName + "@" + i) == null) {
                    HashMap tmp_hm = new HashMap();
                    ht_OtherTable.put(tableName + "@" + i, tmp_hm);
                    Object obj_2 = objects.get(i);
                    for (int m = 0; m < objs.size(); m++) {
                        Object[] tmp_objs = (Object[]) objs.get(m);
                        Object key_val = obj_2;
                        if (key_val == null) {
                            continue;
                        }
                        if (!(key_val instanceof String)) {
                            key_val = PublicUtil.getProperty(obj_2, this.key_field);
                        }
                        if (key_val.equals(tmp_objs[0])) {
                            for (int ind = 1; ind < tmp_objs.length; ind++) {
                                tmp_hm.put(sel_fields[ind - 1].trim(), tmp_objs[ind]);
                            }
                        }
                    }
                    if (i == rowIndex) {
                        hm = tmp_hm;
                    }
                }
            }
        }
        if (hm == null) {
            return null;
        }
        if (fieldName.endsWith("_code_")) {
            fieldName = fieldName.replace("_code_", "");
            Object data = hm.get(fieldName);
            if (data == null || data instanceof Code) {
                return data;
            }
            TempFieldInfo tfi = EntityBuilder.getTempFieldInfoByName(fullFieldName);
            if (tfi == null) {
                return null;
            }
            return CodeManager.getCodeManager().getCodeBy(tfi.getCode_type_name(), hm.get(fieldName) == null ? null : hm.get(fieldName).toString());
        } else {
            return hm.get(fieldName);
        }
//        return hm.get(fieldName);
    }

    /**
     * 该方法用于根据行索引获取行对象
     * @param indexs：行索引数组
     * @return：对应行的实际对象型数据
     */
    public List<Object> getAllSelectObjectsByIndexs(int[] indexs) {
        int len = indexs.length;
        int obj_len = objects.size();
        List retList = new ArrayList();
        if (obj_len == 0) {
            return retList;
        }
        for (int i = 0; i < len; i++) {
            lazyFetch(indexs[i]);
        }
        for (int i : indexs) {
            if (i < obj_len) {
                retList.add(objects.get(i));
            }
        }
        return retList;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public List<Object> getAllObjects(boolean fetch_other_data) {
        for (int i = 0; i < objects.size(); i++) {
            lazyFetch(i);
        }
        return objects;
    }

    public List<Object> getAllObjects() {
        return getAllObjects(true);
    }

    public Object lazyFetch(int rowIndex, boolean fetch_other_data) {
        if (objects.isEmpty()) {
            return null;
        }
        Object obj = objects.get(rowIndex);
        if (obj instanceof String || obj instanceof Integer) {
            String lazyFetchHql = "";
            if (entityClass == null) {
                lazyFetchHql = PublicUtil.getProps_value().getProperty("objdata");
                if (lazyFetchHql == null) {
                    return new Object[]{obj};
                }
            } else {
                lazyFetchHql = PublicUtil.getProps_value().getProperty(entityClass.getName());
            }
            if (lazyFetchHql == null) {
                return null;
            }
            //主键是字符串 或 整形
            String keys = "";
            int min = Math.max(0, rowIndex - fetch_size);
            int max = Math.min(objects.size(), rowIndex + fetch_size);
            if (obj instanceof String) {
                for (int i = min; i < max; i++) {
                    Object obj_2 = objects.get(i);
                    if (obj_2 instanceof String) {
                        keys = keys + ",'" + obj_2 + "'";
                    }
                }
            } else {
                for (int i = min; i < max; i++) {
                    Object obj_2 = objects.get(i);
                    if (obj_2 instanceof Integer) {
                        keys = keys + "," + obj_2 + "";
                    }
                }
            }
            List objs = null;
            if (keys.equals("")) {
                objs = new ArrayList();
            } else {
                objs = CommUtil.fetchEntities(lazyFetchHql + " (" + keys.substring(1) + ")");
            }
            if (entityClass == null) {
                for (int i = min; i < max; i++) {
                    Object obj_2 = objects.get(i);
                    if (obj_2 instanceof String) {
                        for (int m = 0; m < objs.size(); m++) {
                            obj = objs.get(m);
                            if (obj_2.equals(((Object[]) obj)[0])) {
                                objects.set(i, obj);
                                break;
                            }
                        }

                    }
                }
            } else {
                for (int i = min; i < max; i++) {
                    Object obj_2 = objects.get(i);
                    if (obj_2 instanceof String) {
                        for (int m = 0; m < objs.size(); m++) {
                            obj = objs.get(m);
                            if (obj_2.equals(PublicUtil.getProperty(obj, this.key_field))) {
                                objects.set(i, obj);
                                break;
                            }
                        }

                    } else if (obj_2 instanceof Integer) {
                        for (int m = 0; m < objs.size(); m++) {
                            obj = objs.get(m);
                            if (((Integer) obj_2).intValue() == SysUtil.objToInt(PublicUtil.getProperty(obj, this.key_field))) {
                                objects.set(i, obj);
                                break;
                            }
                        }
                    }
                }
            }


            obj = objects.get(rowIndex);
            if (fetch_other_data) {
                for (Object item : columnList) {
                    FTableColumnItem col_item = (FTableColumnItem) item;
                    String fullFieldName = col_item.getId();
                    if (fullFieldName.startsWith("#")) {
                        otherTableValue(null, col_item, rowIndex);
                    }
                }
            }
        }
        return obj;
    }

    public Object lazyFetch(int rowIndex) {
        return lazyFetch(rowIndex, true);
    }

    private Object convertZeroToNull(Object tmp_obj) {
        if (tmp_obj == null) {
            return tmp_obj;
        }
        if (!(tmp_obj instanceof BigDecimal || tmp_obj instanceof Integer || tmp_obj instanceof Float || tmp_obj instanceof Double)) {
            return tmp_obj;
        }
        double tmp_sum = 0;
        if (tmp_obj instanceof BigDecimal) {
            tmp_sum = ((BigDecimal) tmp_obj).doubleValue();
        } else if (tmp_obj instanceof Integer) {
            tmp_sum = ((Integer) tmp_obj).doubleValue();
        } else if (tmp_obj instanceof Float) {
            tmp_sum = ((Float) tmp_obj).doubleValue();
        } else if (tmp_obj instanceof Double) {
            tmp_sum = ((Double) tmp_obj).doubleValue();
        }
        if (notShowZero && Math.abs(tmp_sum) < 0.000000001) {
            return null;
        } else {
            return tmp_obj;
        }
    }

    /**
     * 根据行列信息等到表格的值
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (objects.isEmpty()) {
            return null;
        }

        Object obj = lazyFetch(rowIndex);

        if (obj == null) {
            return null;
        }

        if (obj instanceof Object[]) {
            Object[] objs = (Object[]) obj;
            return convertZeroToNull(objs[columnIndex]);
        }

        if (columnIndex < 0 || columnIndex >= columnList.size()) {
            return null;
        }

        FTableColumnItem item = (FTableColumnItem) columnList.get(columnIndex);
        //String colName = item.getId();

        List<String> field_list = item.getField_list();

        if (item.getId().startsWith("#")) {
            return convertZeroToNull(otherTableValue(obj, item, rowIndex));
        }

        Object cell_value = obj;
        if (cell_value != null) {
            if (entityClass != null) {
                Class aclass = entityClass;
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
                        ex.printStackTrace();
                        System.out.println("field_name:" + field_name);
                    }
                }
            }
        }
        return convertZeroToNull(cell_value);
    }

    /**
     * 增加表格的一列信息
     *
     * @param item
     */
    public int addColumn(FTableColumnItem item) {
        for (int i = 0; i < columnList.size(); i++) {
            if (((FTableColumnItem) columnList.get(i)).getId().equals(
                    item.getId())) {
                return -1;
            }
        }
        this.columnList.add(item);
        return columnList.size() - 1;

    }

    /**
     * 删除表格的一列信息
     *
     * @param item
     */
    public void removeColumn(FTableColumnItem item) {
        for (int i = 0; i < columnList.size(); i++) {
            if (((FTableColumnItem) columnList.get(i)).getId().equals(
                    item.getId())) {
                columnList.remove(i);
                break;
            }

        }
    }

    /**
     * 设置表格数据模型
     *
     * @param list
     */
    public void setData(List list, boolean isCheck) {
        if (!isCheck) {
            objects = list;
        } else {
            objects = new ArrayList();
            for (int i = 0; list != null && i < list.size(); i++) {
                Map map = (Map) list.get(i);
                map.put("isCheck", false);
                objects.add(map);
            }
        }
        fireTableDataChanged();
    }

    /**
     * 得到表格数据
     *
     * @return 表格数据列表
     */
    public List getData() {
        return this.objects;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    private List<String> disable_fields = null;
    private ITableCellEditable iTableCellEditable = null;

    public ITableCellEditable getiTableCellEditable() {
        return iTableCellEditable;
    }

    public void setITableCellEditable(ITableCellEditable iTableCellEditable) {
        this.iTableCellEditable = iTableCellEditable;
    }

    public List<String> getDisable_fields() {
        return disable_fields;
    }

    public void setDisable_fields(List<String> disable_fields) {
        this.disable_fields = disable_fields;
    }

    /**
     * 是否可以编辑单元格
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (objects.isEmpty()) {
            return false;
        }
        if (((FTableColumnItem) columnList.get(columnIndex)).isLargeText()) {
            return true;
        }
        if (disableCells.contains(String.valueOf(rowIndex) + "," + String.valueOf(columnIndex))) {
            return false;
        }
        if (iTableCellEditable != null) {
            Object obj = rowIndex >= objects.size() ? null : objects.get(rowIndex);
            int i_editable = iTableCellEditable.getCellEditable(obj, ((FTableColumnItem) columnList.get(columnIndex)).getId());
            if (i_editable != 0) {
                return i_editable > 0 ? true : false;
            }
        }

        if (disable_fields != null && (disable_fields.contains(((FTableColumnItem) columnList.get(columnIndex)).getId()) || disable_fields.contains(((FTableColumnItem) columnList.get(columnIndex)).getId().replace("_code_", "")))) {
            return false;
        }
        if (((FTableColumnItem) columnList.get(columnIndex)).getId().contains(".")) {
            return false;
        }
        boolean b_new_record = rowIndex >= objects.size();//.get(rowIndex) == black_object;        
        if (!editable) {
            return false;
        }
        if (b_new_record) {
            return ((FTableColumnItem) columnList.get(columnIndex)).isEditable_when_new();
        } else {
            return ((FTableColumnItem) columnList.get(columnIndex)).isEditable();
        }
    }

    /**
     * 是否可以编辑单元格
     */
    public boolean isCellEditable2(int rowIndex, int columnIndex) {
        if (((FTableColumnItem) columnList.get(columnIndex)).isLargeText()) {
            return true;
        }
        if (disableCells.contains(String.valueOf(rowIndex) + "," + String.valueOf(columnIndex))) {
            return false;
        }
        if (iTableCellEditable != null) {
            Object obj = rowIndex >= objects.size() ? null : objects.get(rowIndex);
            int i_editable = iTableCellEditable.getCellEditable(obj, ((FTableColumnItem) columnList.get(columnIndex)).getId());
            if (i_editable != 0) {
                return i_editable > 0 ? true : false;
            }
        }
        if (disable_fields != null && disable_fields.contains(((FTableColumnItem) columnList.get(columnIndex)).getId())) {
            return false;
        }

        if (((FTableColumnItem) columnList.get(columnIndex)).getId().contains(".")) {
            return false;
        }
        boolean b_new_record = rowIndex >= objects.size();//.get(rowIndex) == black_object;

        if (b_new_record) {
            return ((FTableColumnItem) columnList.get(columnIndex)).isEditable_when_new();
        } else {
            return ((FTableColumnItem) columnList.get(columnIndex)).isEditable();
        }
    }
    private Hashtable<String, FColumnStat> ht_columnStat = new Hashtable<String, FColumnStat>();

    public Hashtable<String, FColumnStat> getHt_columnStat() {
        return ht_columnStat;
    }

    private void setValueAt2(Object data_obj, Object aValue, Object old_val, int rowIndex, int columnIndex) {
        Object bean = data_obj;
        FTableColumnItem item = (FTableColumnItem) columnList.get(columnIndex);
        FColumnStat fColumnStat = ht_columnStat.get(item.getId());
        if (fColumnStat != null) {
            fColumnStat.changeStatBy(old_val, aValue);
        }
        List<String> field_list = item.getField_list();
        Class aclass = entityClass;
        for (int i = 0; i < field_list.size() - 1; i++) {
            String field_name = field_list.get(i);
            try {
                Method method = aclass.getMethod("get" + field_name.substring(0, 1).toUpperCase() + field_name.substring(1), new Class[]{});
                data_obj = method.invoke(data_obj, new Object[]{});
                if (data_obj == null) {
                    break;
                }
                aclass = data_obj.getClass();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        String field_name = field_list.get(field_list.size() - 1);
        Object bean_old_value = old_val;
        String change_name = field_name;
        if (field_name.endsWith("_code_")) {
            change_name = field_name.substring(0, field_name.length() - 6);
            bean_old_value = PublicUtil.getProperty(bean, change_name);
        }
        try {
            Class field_class = aclass.getField(field_name).getType();
            Method method = aclass.getMethod("set" + field_name.substring(0, 1).toUpperCase() + field_name.substring(1), new Class[]{field_class});
            if ((aValue == null || aValue.toString().equals("")) && (field_class.getSimpleName().equalsIgnoreCase("Float") || field_class.getSimpleName().equalsIgnoreCase("Double") || field_class.getSimpleName().equalsIgnoreCase("BigDecimal") || field_class.getSimpleName().equals("Integer") || field_class.getSimpleName().equals("int"))) {
                aValue = 0;
            }
            if (field_class.getSimpleName().equalsIgnoreCase("Float")) {
                aValue = Float.valueOf(aValue.toString());
            } else if (field_class.getSimpleName().equalsIgnoreCase("Double")) {
                aValue = Double.valueOf(aValue.toString());
            } else if (field_class.getSimpleName().equalsIgnoreCase("BigDecimal")) {
                aValue = BigDecimal.valueOf(Double.valueOf(aValue.toString()));
            } else if (field_class.getSimpleName().equals("Integer") || field_class.getSimpleName().equals("int")) {
                aValue = Integer.valueOf(aValue.toString());
            }
            data_obj = method.invoke(data_obj, new Object[]{aValue});
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        Object bean_new_value = aValue;
        if (field_name.endsWith("_code_")) {
            bean_new_value = PublicUtil.getProperty(bean, change_name);
        }
        FieldTrigerManager.getFieldTrigerManager().triger(change_name, bean, bean_old_value, bean_new_value);
    }

    /**
     * 设置单元格的值
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (objects.isEmpty()) {
            return;
        }
        if (entityClass == null) {
            Object[] objs = (Object[]) objects.get(rowIndex);
            objs[columnIndex] = aValue;
            return;
        }
        Object data_obj = objects.get(rowIndex);
        Object bean = data_obj;
        Object old_val = this.getValueAt(rowIndex, columnIndex);
        setValueAt2(bean, aValue, old_val, rowIndex, columnIndex);
        FTableColumnItem item = (FTableColumnItem) columnList.get(columnIndex);
        List<String> field_list = item.getField_list();
        if (!FieldTrigerManager.getFieldTrigerManager().validateunotnull(field_list.get(field_list.size() - 1), bean, old_val, aValue) || !FieldTrigerManager.getFieldTrigerManager().validate(field_list.get(field_list.size() - 1), bean, old_val, aValue)) {
            setValueAt2(bean, old_val, aValue, rowIndex, columnIndex);
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);
        if (old_val == aValue) {
            return;
        }
        if (old_val != null && old_val.equals(aValue)) {
            return;
        }
        if (aValue != null && aValue.equals(old_val)) {
            return;
        }
        for (RowChangeListner listner : listRowChangeListners) {
            listner.rowChanged(objects.get(rowIndex));
        }
    }

    /**
     * 在表格末尾增加一个空行
     *
     */
    public void addBlankRow() {
        Object obj = null;
        try {
            obj = entityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (this.objects == null) {
            this.objects = new ArrayList();
        }
        if (obj != null) {
            objects.add(obj);
        }
        fireTableDataChanged();
    }

    /**
     * 在表格指定位置增加一个空行
     *
     */
    public void addBlankRow(int index) {
        if (index >= objects.size()) {
            this.addBlankRow();
        } else {
            Object obj = null;
            try {
                obj = entityClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (this.objects == null) {
                this.objects = new ArrayList();
            }
            if (obj != null) {
                objects.add(index, obj);
            }
            fireTableDataChanged();
        }
    }

    /**
     * 在当前表格的末尾增加一行值
     *
     * @param map
     *            行信息值对
     */
    public void addRow(Map map) {
        if (this.objects == null) {
            this.objects = new ArrayList();
        }
        this.objects.add(map);
        fireTableDataChanged();
    }

    /**
     * 增加一行的值
     *
     * @param map
     *            行信息值对
     * @param index
     *            加入的位置
     */
    public void addRow(Map map, int index) {
        if (this.objects == null) {
            this.objects = new ArrayList();
        }
        this.objects.add(index, map);
        fireTableDataChanged();
    }

    /**
     * 修改一行
     *
     * @param map
     *            新的Map
     * @param index
     *            位置
     */
    public void modifyRow(Map map, int index) {
        Map modifyMap = (Map) (this.objects.get(index));
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            Object value = map.get(key);
            modifyMap.put(key, value);
        }
        fireTableDataChanged();
    }

    public void clear() {
        getHt_OtherTable().clear();
        objects.clear();
        fireTableDataChanged();
    }

    public void deleteRow(int index) {
        if (index >= objects.size()) {
            return;
        }
        getHt_OtherTable().clear();
        objects.remove(index);
        fireTableDataChanged();
    }

    public void deleteBy(List list) {
        getHt_OtherTable().clear();
        objects.removeAll(list);
        fireTableDataChanged();
    }

    public void setCheckBoxSelectedAtRow(int rowIndex, boolean isSelected) {
        Map recordMap = (Map) objects.get(rowIndex);
        recordMap.put("isCheck", new Boolean(isSelected));
        this.fireTableRowsUpdated(rowIndex, rowIndex);
        // this.fireTableCellUpdated(rowIndex,0);
    }

    /**
     * 获得行选择信息
     *
     * @param rowIndex
     * @return
     */
    public boolean isCheckBoxSelectedAtRow(int rowIndex) {
        Map recordMap = (Map) objects.get(rowIndex);
        Object obj = recordMap.get("isCheck");
        if (obj == null || obj.equals("")) {
            return false;
        }
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        }
        return true;
    }

    public void setAllCheckBoxsSelected(boolean isSelected) {
        for (int i = 0; objects != null && i < objects.size(); i++) {
            Map recordMap = (Map) objects.get(i);
            recordMap.put("isCheck", new Boolean(isSelected));
        }
        fireTableDataChanged();
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
        key_field = EntityBuilder.getEntityKey(this.entityClass);
        try {
            black_object = entityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void addObjects(List tmp_list) {
        for (Object obj : tmp_list) {
            if (!objects.contains(obj)) {
                objects.add(obj);
            }
        }
        this.fireTableDataChanged();
    }

    public void addObject(Object obj) {
        if (!objects.contains(obj)) {
            objects.add(obj);
        }
        this.fireTableDataChanged();
    }

    public void addObject(Object obj, int index) {
        //用于添加对象到指定行
        if (!objects.contains(obj)) {
            objects.add(index, obj);
        }
        this.fireTableDataChanged();
    }
}
