/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.jhrcore.client.CommUtil;
import org.jhrcore.comm.HrLog;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.base.TempFieldInfo;

/**
 *
 * @author mxliteboss
 * @see
 */
public class DbUtil {

    private static int sq_len = 800;//in ����������ݵ�Ԫ����
    private static HrLog log = new HrLog(DbUtil.class.getName());

    /**
     * ��Բ�ͬ�����ݿ⣬��ö�Ӧ���Զ����ɲ��ظ����ַ�������
     * @param db_type�����ݿ�����
     * @return ��Ӧ���ݿ���
     */
    public static String getUIDForDb(String db_type) {
        if (db_type.equals("sqlserver")) {
            return "newid()";
        }
        return "sys_guid()";
    }

    public static String getDateForDb(String db_type) {
        if (db_type.equals("sqlserver")) {
            return "getDate()";
        }
        return "sysdate()";
    }

    public static String getPlusStr(String db_type) {
        if (db_type.equals("sqlserver")) {
            return "+";
        }
        return "||";
    }

    public static String getUIDForDb() {
        return getUIDForDb(UserContext.getSql_dialect());
    }

    /**
     * �������ݿ����ͣ�db_type���ж���֪��tableName�Ƿ�����뵱ǰ���ݿ�
     * @param tableName������
     * @param db_type�����ݿ�����
     * @return���Ƿ����
     */
    public static boolean isTableExists(String tableName, String db_type) {
        boolean table_exists = false;
        String sql = isTableExistSQL(tableName, db_type);
        int cal_table_exists = Integer.valueOf(CommUtil.selectSQL(sql).get(0).toString());
        if (cal_table_exists != 0) {
            table_exists = true;
        }
        return table_exists;
    }

    public static void initTempTable(Session s, String tableName, String db_type) throws Exception {
        String sql = isTableExistSQL(tableName, db_type);
        int cal_table_exists = Integer.valueOf(s.createSQLQuery(sql).list().get(0).toString());
        if (cal_table_exists != 0) {
            try {
                s.createSQLQuery("drop table " + tableName).executeUpdate();
                s.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static boolean isTableExists(Session s, String tableName, String db_type) throws Exception {
        boolean table_exists = false;
        String sql = isTableExistSQL(tableName, db_type);
        int cal_table_exists = Integer.valueOf(s.createSQLQuery(sql).list().get(0).toString());
        if (cal_table_exists != 0) {
            table_exists = true;
        }
        return table_exists;
    }

    public static String isTableExistSQL(String tableName, String db_type) {
        if (db_type.equals("oracle")) {
            return "select count(*)  from user_tables where table_name='" + tableName.toUpperCase() + "' ";
//            return "select count(*)  from all_tables where table_name='" + tableName.toUpperCase() + "' ";
        } else if (db_type.equals("sqlserver")) {
            return "select count(*) from sysobjects where name='" + tableName + "' and xtype = 'U'";
        } else if (db_type.equals("db2")) {
            return "select count(*) from sysibm.systables where TID<>0 and name='" + tableName + "'";
        }
        return "";
    }

    /**
     * �÷������ڷ����ж�ָ�����ֶ��Ƿ���������ݿ��SQL
     * @param tableName������
     * @param fieldName:�ֶ���
     * @param db_type�����ݿ�����
     * @return ����SQL����ƥ����󷵻�""
     */
    public static String isFieldExistSQL(String tableName, String fieldName, String db_type) {
        if (tableName == null || fieldName == null || db_type == null) {
            return "";
        }
        if (db_type.equals("oracle")) {
            return "SELECT count(1) from user_tab_columns where column_name = '" + fieldName.toUpperCase() + "' and table_name = '" + tableName.toUpperCase() + "'";
        } else if (db_type.equals("sqlserver")) {
            return "select count(*) from syscolumns where id=object_id('" + tableName + "') and name='" + fieldName + "'";
        }
        return "";
    }

    /**
     * ����ͳ�����ͻ�ö�Ӧ�����ݿ�ͳ�ƺ���
     * @param stat_type
     * @return ��Ӧ������
     */
    public static String getAnlayseOperator(String stat_type) {
        if (stat_type == null) {
            return "";
        }
        if (stat_type.equals("����")) {
            return "count";
        } else if (stat_type.equals("ƽ��")) {
            return "avg";
        } else if (stat_type.equals("���")) {
            return "sum";
        } else if (stat_type.equals("���")) {
            return "max";
        } else if (stat_type.equals("��С")) {
            return "min";
        }
        return "";
    }

    /**
     * ���ݴ�������ݿⷽ�Ի�����ݿ���
     * @param sql_dialect�����ݿⷽ��
     * @return ��Ӧ������
     */
    public static String SQL_dialect_check(String sql_dialect) {
        String result = "";
        if (sql_dialect == null || sql_dialect.trim().equals("")) {
            return result;
        }
        if (sql_dialect.contains("SQLServerDialect")) {
            result = "sqlserver";
        } else if (sql_dialect.contains("DB2")) {
            result = "db2";
        } else if (sql_dialect.contains("Oracle")) {
            result = "oracle";
        } else if (sql_dialect.contains("MySQL")) {
            result = "mysql";
        }
        return result;
    }

    /**
     * ���ݴ�������ݿ����ͻ�ö�Ӧ���ж�ֵΪ�յ����ݿ⺯��
     * @param db_type�����ݿ�����
     * @return ��Ӧ������
     */
    public static String getNull_strForDB(String db_type) {
        String result = "";
        if (db_type.equals("oracle")) {
            result = "nvl";
        } else if (db_type.equals("sqlserver")) {
            result = "isnull";
        } else if (db_type.equals("mysql")) {
            result = "ifnull";
        } else if (db_type.equals("db2")) {
            result = "coalesce";
        }
        return result;
    }
    
    public static String getInteger_strForDB(String db_type) {
        String result = "";
        if (db_type.equals("oracle")) {
            result = "int";
        } else if (db_type.equals("sqlserver")) {
            result = "int";
        } else if (db_type.equals("mysql")) {
            result = "SIGNED";
        } else if (db_type.equals("db2")) {
            result = "int";
        }
        return result;
    }

    public static String getSubStr(String db_type) {
        if (db_type.equals("sqlserver")) {
            return "substring";
        }
        return "substr";
    }

    /**
     * ���ݴ�������ݿ����ͻ�ö�Ӧ�Ļ���ֶγ��ȵ����ݿ⺯��
     * @param db_type�����ݿ�����
     * @return ��Ӧ������
     */
    public static String getLength_strForDB(String db_type) {
        String result = "";
        if (db_type.equals("sqlserver")) {
            result = "len";
        } else {
            result = "length";
        }
        return result;
    }

    public static String getQueryForMID(String sql, List<String> keys) {
        return getQueryForMID(sql, keys, "");
    }

    public static String getCreateFieldSQL2(String db_type, TempFieldInfo tfi) {
        String field_name = tfi.getField_name().toLowerCase().replace("_code_", "");
        String field_type = tfi.getField_type().toLowerCase();
        String cal_field_sql = field_name;
        if (field_type.equals("string") || field_type.equals("code")) {
            if (db_type.equals("oracle")) {
                cal_field_sql += " VARCHAR2(" + tfi.getField_width() + ")";
            } else {
                cal_field_sql += " VARCHAR(" + tfi.getField_width() + ")";
            }
        } else if (field_type.equals("integer") || field_type.equals("int")) {
            cal_field_sql += " INT";
        } else if (field_type.equals("float") || field_type.equals("bigdecimal")) {
            if (db_type.equals("oracle")) {
                cal_field_sql += " NUMBER(" + tfi.getField_width() + "," + tfi.getField_scale() + ")";
            } else if (db_type.equals("sqlserver")) {
                cal_field_sql += " numeric(" + tfi.getField_width() + "," + tfi.getField_scale() + ")";
            } else if (db_type.equals("db2")) {
                cal_field_sql += " decimal(" + tfi.getField_width() + "," + tfi.getField_scale() + ")";
            }
        } else if (field_type.equals("boolean")) {
            if (db_type.equals("oracle")) {
                cal_field_sql += " NUMBER(1)";
            } else if (db_type.equals("sqlserver")) {
                cal_field_sql += " tinyint";
            } else if (db_type.equals("db2")) {
                cal_field_sql += " smallint";
            }
        } else if (field_type.equals("date")) {
            if (db_type.equals("oracle")) {
                cal_field_sql += " DATE";
            } else if (db_type.equals("sqlserver")) {
                cal_field_sql += " datetime";
            } else if (db_type.equals("db2")) {
                cal_field_sql += " timestamp";
            }
        }
        return cal_field_sql;
    }

    public static String getCreateFieldSQL(String db_type, TempFieldInfo tfi) {
        String field_name = tfi.getField_name().toLowerCase().replace("_code_", "");
        String field_type = tfi.getField_type().toLowerCase();
        String cal_field_sql = tfi.getEntity_name() + "_" + field_name;
        if (field_type.equals("string") || field_type.equals("code")) {
            if (db_type.equals("oracle")) {
                cal_field_sql += " VARCHAR2(" + tfi.getField_width() + ")";
            } else {
                cal_field_sql += " VARCHAR(" + tfi.getField_width() + ")";
            }
        } else if (field_type.equals("integer") || field_type.equals("int")) {
            cal_field_sql += " INT";
        } else if (field_type.equals("float") || field_type.equals("bigdecimal")) {
            if (db_type.equals("oracle")) {
                cal_field_sql += " NUMBER(" + tfi.getField_width() + "," + tfi.getField_scale() + ")";
            } else if (db_type.equals("sqlserver")) {
                cal_field_sql += " numeric(" + tfi.getField_width() + "," + tfi.getField_scale() + ")";
            } else if (db_type.equals("db2")) {
                cal_field_sql += " decimal(" + tfi.getField_width() + "," + tfi.getField_scale() + ")";
            }
        } else if (field_type.equals("boolean")) {
            if (db_type.equals("oracle")) {
                cal_field_sql += " NUMBER(1)";
            } else if (db_type.equals("sqlserver")) {
                cal_field_sql += " tinyint";
            } else if (db_type.equals("db2")) {
                cal_field_sql += " smallint";
            }
        } else if (field_type.equals("date")) {
            if (db_type.equals("oracle")) {
                cal_field_sql += " DATE";
            } else if (db_type.equals("sqlserver")) {
                cal_field_sql += " datetime";
            } else if (db_type.equals("db2")) {
                cal_field_sql += " timestamp";
            }
        }
        return cal_field_sql;
    }

    /**
     * �÷������ڶ��ڶ�ID in�����ƴ�ӣ�
     * @param sql:ǰ�泣��SQL
     * @param keys���ַ���IDList
     * @param extra_char�������ַ������糣��SQL�а������(���ɼӲ����ַ�)
     * @return
     */
    public static String getQueryForMID(String sql, List<String> keys, String extra_char) {
        return getQueryForMID(sql, keys, ";", extra_char);
    }

    public static String getQueryForMID(String sql, List<String> keys, String split_char, String extra_char) {
        if (keys == null || keys.isEmpty()) {
            return sql + "('@')" + extra_char;
        }
        StringBuilder ex_str = new StringBuilder();
        int len = keys.size();
        int mod_len = len / sq_len;
        int re_len = mod_len + (len % sq_len == 0 ? 0 : 1);
        for (int i = 0; i < re_len; i++) {
            StringBuilder str = new StringBuilder();
//            str.append("'@@@'");
            if (i < mod_len) {
                for (int j = 0; j < sq_len; j++) {
                    str.append(",'");
                    str.append(keys.get(i * sq_len + j));
                    str.append("'");
                }
            } else {
                for (int j = 0; j < sq_len; j++) {
                    int ind = i * sq_len + j;
                    if (ind >= len) {
                        break;
                    }
                    str.append(",'");
                    str.append(keys.get(ind));
                    str.append("'");
                }
            }
            ex_str.append(sql);
            ex_str.append("(");
            ex_str.append(str.toString().substring(1));
            ex_str.append(")");
            ex_str.append(extra_char);
            ex_str.append(split_char);
        }
        return ex_str.toString();
    }

    public static List<String> getQueryForMID_List(String sql, List<String> keys, int sql_len) {
        List<String> result_list = new ArrayList<String>();
        if (keys == null || keys.isEmpty()) {
            result_list.add(sql + "('@')");
            return result_list;
        }
        int len = keys.size();
        int mod_len = len / sql_len;
        int re_len = mod_len + (len % sql_len == 0 ? 0 : 1);
        for (int i = 0; i < re_len; i++) {
            StringBuilder ex_str = new StringBuilder();
            StringBuilder str = new StringBuilder();
            if (i < mod_len) {
                for (int j = 0; j < sql_len; j++) {
                    str.append(",'");
                    str.append(keys.get(i * sql_len + j));
                    str.append("'");
                }
            } else {
                for (int j = 0; j < sql_len; j++) {
                    int ind = i * sql_len + j;
                    if (ind >= len) {
                        break;
                    }
                    str.append(",'");
                    str.append(keys.get(ind));
                    str.append("'");
                }
            }
            ex_str.append(sql);
            ex_str.append("(");
            ex_str.append(str.toString().substring(1));
            ex_str.append(")");
            result_list.add(ex_str.toString());
        }
        return result_list;
    }

    public static String getQueryForMID(String sql, List<String> keys, boolean isInt) {
        return getQueryForMID(sql, keys, "", "", isInt);
    }

    public static String getQueryForMID(String sql, List<String> keys, String split_char, boolean isInt) {
        return getQueryForMID(sql, keys, split_char, "", isInt);
    }

    public static String getQueryForMID(String sql, List<String> keys, String split_char, String extra_char, boolean isInt) {
        if (isInt) {
            if (keys == null || keys.isEmpty()) {
                return sql + "(-1)" + extra_char;
            }
            StringBuffer ex_str = new StringBuffer();
            int len = keys.size();
            int mod_len = len / sq_len;
            int re_len = mod_len + (len % sq_len == 0 ? 0 : 1);
            for (int i = 0; i < re_len; i++) {
                StringBuffer str = new StringBuffer();
                str.append("'@@@'");
                if (i < mod_len) {
                    for (int j = 0; j < sq_len; j++) {
                        str.append(",");
                        str.append(keys.get(i * sq_len + j));
                    }
                } else {
                    for (int j = 0; j < sq_len; j++) {
                        int ind = i * sq_len + j;
                        if (ind >= len) {
                            break;
                        }
                        str.append(",");
                        str.append(keys.get(ind));
                    }
                }
                ex_str.append(sql);
                ex_str.append("(");
                ex_str.append(str.toString().substring(1));
                ex_str.append(")");
                ex_str.append(extra_char);
                ex_str.append(split_char);
            }
            return ex_str.toString();
        } else {
            return getQueryForMID(sql, keys, split_char, extra_char);
        }
    }

    public static String tranSQL(String sql) {
        return tranSQL(sql, "rule");
    }

    public static String tranSQL(String sql, String rule) {
        if (UserContext.sql_dialect.equals("oracle") && rule != null && !rule.trim().equals("")) {
            if (sql.startsWith("select")) {
                sql = "select /*+" + rule + "*/ " + sql.substring(6);
            }
        }
        return sql;
    }

    public static String getInstrForMID(String sql, List<String[]> keys, String extra_char, String split_char) {
        return getInstrForMID2(sql, keys, extra_char, split_char, sq_len);
    }

    public static String getInstrForMID2(String sql, List<String[]> keys, String extra_char, String split_char, int sq_len2) {
        if (keys == null || keys.isEmpty()) {
            return "";
        }
        if (extra_char.equals("") && UserContext.sql_dialect.equals("oracle")) {
            extra_char = " from dual";
        }
        int row_len = keys.get(0).length;
        StringBuffer ex_str = new StringBuffer();
        int len = keys.size();
        int mod_len = len / sq_len2;
        int re_len = mod_len + (len % sq_len2 == 0 ? 0 : 1);
        for (int i = 0; i < re_len; i++) {
            StringBuffer str = new StringBuffer();
            if (i < mod_len) {
                for (int j = 0; j < sq_len2; j++) {
                    String[] r_data = keys.get(i * sq_len2 + j);
                    str.append("(select ");
                    for (int k = 0; k < row_len; k++) {
                        str.append(r_data[k]).append(" as a").append(k);
                        str.append(",");
                    }
                    str.deleteCharAt(str.length() - 1);
                    str.append(extra_char);
                    str.append(") union ");
                }
            } else {
                for (int j = 0; j < sq_len2; j++) {
                    int ind = i * sq_len2 + j;
                    if (ind >= len) {
                        break;
                    }
                    str.append("(select ");
                    String[] r_data = keys.get(ind);
                    for (int k = 0; k < row_len; k++) {
                        str.append(r_data[k]).append(" as a").append(k);
                        str.append(",");
                    }
                    str.deleteCharAt(str.length() - 1);
                    str.append(extra_char);
                    str.append(") union ");
                }
            }
            ex_str.append(sql);
            ex_str.append(str.toString().substring(0, str.length() - 6));
//            ex_str.append("select * from (").append(str.toString().substring(0, str.length() - 6)).append(") aa");
            ex_str.append(split_char);
        }
        return ex_str.toString();
    }

    /**
     * ͨ��ָ����������һ�����ݿ�����
     * @param dbType�����ݿ����ͣ�ORACLE/MSSQL/MYSQL
     * @param ip�����ݿ�IP
     * @param port�����ݿ�˿�
     * @param databaseName�����ݿ���
     * @param username���û���
     * @param password������
     * @return ���õ����ӻ�null
     */
    public static Connection buildConnection(String dbType, String ip, String port, String databaseName, String username, String password) {
        String driverName = "";
        String url = "";
        String type = dbType.toUpperCase();
        if (type.equals("ORACLE")) {
            driverName = "oracle.jdbc.driver.OracleDriver";
            url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + databaseName;
        } else if (type.equals("MSSQL")) {
            driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            url = "jdbc:sqlserver://" + ip + ":" + port + ";DatabaseName=" + databaseName;
        } else if (type.equals("MYSQL")) {
            driverName = "com.mysql.jdbc.Driver";
            url = "jdbc:mysql://" + ip + "/" + databaseName;
        }
        if (driverName.equals("") || url.equals("")) {
            return null;
        }
        return buildConnection(driverName, url, username, password);
    }

    public static Connection buildConnection(String driverName, String url, String username, String password) {
        Connection conn = null;
        try {
            Class.forName(driverName).newInstance();
            conn = DriverManager.getConnection(url, username,
                    password);
            if (conn != null) {
                log.info("��Ϣ��*****���ӳɹ�*****");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
        return conn;
    }

    /**
     * �ر�ָ�����ݿ�����
     * @param conn��ָ�����ݿ�����
     */
    public static void closeConnection(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            if (!conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            log.error(e);
        } finally {
            if (conn != null) {
                conn = null;
            }
        }
    }

    /**
     * �÷������ڲ���ָ�����������ݿ������Ƿ�ɹ��������Ƿ����ӳɹ������Զ��ر�����
     * @param dbType�����ݿ����ͣ�ORACLE/MSSQL/MYSQL
     * @param ip�����ݿ�IP
     * @param port�����ݿ�˿�
     * @param databaseName�����ݿ���
     * @param username���û���
     * @param password������
     * @return true:���ӳɹ���false������ʧ��
     */
    public static boolean testConnection(String dbType, String ip, String port, String databaseName, String username, String password) {
        Connection conn = buildConnection(dbType, ip, port, databaseName, username, password);
        boolean connected = false;
        if (conn != null) {
            connected = true;
        }
        DbUtil.closeConnection(conn);
        return connected;
    }

    public static List<String[]> tranKeys(List<String> keys) {
        return tranKeys(keys, false);
    }

    /**
     * �÷������ڽ�һ��LIST<String>����ת����һ��LIST<STRING[]>����Ҫ����DBUTIL.getInstrForMID����
     * @param keys��ָ������ֵ����
     * @param isInt���Ƿ�ΪINT
     * @return��ת�����LIST<STRING[]>������
     */
    public static List<String[]> tranKeys(List<String> keys, boolean isInt) {
        List<String[]> result = new ArrayList<String[]>();
        if (isInt) {
            for (String key : keys) {
                result.add(new String[]{key});
            }
        } else {
            for (String key : keys) {
                result.add(new String[]{"'" + key + "'"});
            }
        }
        return result;
    }

    public static String indexOfStr(String src_text, String index_text) {
        return indexOfStr(src_text, index_text, 1);
    }

    public static String indexOfStr(String src_text, String index_text, int ind) {
        if (UserContext.sql_dialect.equals("sqlserver")) {
            return " charindex(" + index_text + "," + src_text + ")=" + ind;
        } else if (UserContext.sql_dialect.equals("oracle")) {
            return " instr(" + src_text + "," + index_text + ")=" + ind;
        } else if (UserContext.sql_dialect.equals("db2")) {
            return " locate(" + index_text + "," + src_text + ")=" + ind;
        }
        return "1=0";
    }
    
   public static String containsOfStr(String src_text, String index_text) {
        if (UserContext.sql_dialect.equals("sqlserver")) {
            return " charindex(" + index_text + "," + src_text + ")>0";
        } else if (UserContext.sql_dialect.equals("oracle")) {
            return " instr(" + src_text + "," + index_text + ")>0";
        } else if (UserContext.sql_dialect.equals("db2")) {
            return " locate(" + index_text + "," + src_text + ")>0";
        }
        return "1=0";
    } 
    
    
    public static String tranStrForSQL(String str) {
        if (str == null) {
            return null;
        }
        return "'" + str.replace("'", "''") + "'";
    }

    public static String getNotNullSQL(String field) {
        if (UserContext.sql_dialect.equals("sqlserver")) {
            return "(" + field + " is not null and  " + field + "!='')";
        } else {
            return "trim(" + field + ") is not null";
        }
    }

    public static String getNullSQL(String field) {
        if (UserContext.sql_dialect.equals("sqlserver")) {
            return "(" + field + " is null or " + field + "='')";
        } else {
            return "trim(" + field + ") is null";
        }
    }

    public static String getSQLByFields(List<String> fields) {
        String result = "";
        if (fields != null && fields.size() > 0) {
            for (String field : fields) {
                result += "," + field;
            }
            result = result.substring(1);
        }
        return result;
    }

    public static String getSQLByFields(String[] fields) {
        String result = "";
        if (fields != null && fields.length > 0) {
            for (String field : fields) {
                result += "," + field;
            }
            result = result.substring(1);
        }
        return result;
    }
}
