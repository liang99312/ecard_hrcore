/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.jhrcore.entity.salary.ValidateSQLResult;

/**
 *
 * @author hflj
 */
public interface DataService extends SuperService {
//查询

    public Object fetchEntityBy(String hql) throws RemoteException;

    public ArrayList getEntitysBy(String hql) throws RemoteException;

    public ArrayList getEntitysByKey(String hql, List<String> keys, boolean isInt) throws RemoteException;

    public ArrayList getEntitysBy(String hql, int pageInd, int count) throws RemoteException;

    public ArrayList selectSQL(String sql, Boolean fetch_head, Integer max_size) throws RemoteException;

    public ArrayList selectSQLByKey(String sql, String sql2, List<String> keys, boolean isInt) throws RemoteException;

    public boolean exists(String hql) throws RemoteException;

    public List getDb_tables(String t_str) throws RemoteException;
    //执行

    public ValidateSQLResult excuteSQL(String sql) throws RemoteException;

    public ValidateSQLResult excuteSQLs(List<String> sqls) throws RemoteException;

    public ValidateSQLResult excuteSQLs(String sql, String split_char) throws RemoteException;

    public ValidateSQLResult excuteSQLs(String sql, List keys, boolean isInt, String split_char, String extra_char) throws RemoteException;

    public ValidateSQLResult excuteHQL(String hql) throws RemoteException;

    public ValidateSQLResult excuteHQLs(String hql, String split_char) throws RemoteException;

    public ValidateSQLResult excuteHQLs(String sql, List keys, boolean isInt, String split_char, String extra_char) throws RemoteException;
    //删除

    public ValidateSQLResult deleteObj(Object o) throws RemoteException;

    public ValidateSQLResult deleteObjs(String tableName, String fieldName, List<String> keys) throws RemoteException;
    //保存

    public ValidateSQLResult saveObj(Object o) throws RemoteException;

    public ValidateSQLResult saveSet(HashSet set) throws RemoteException;

    public ValidateSQLResult saveList(List list) throws RemoteException;
    //更新

    public ValidateSQLResult update(Object obj) throws RemoteException;

    public ValidateSQLResult saveOrUpdate(Object obj) throws RemoteException;

    public ValidateSQLResult excuteSQL_jdbc(String sql) throws RemoteException;

    public ValidateSQLResult excuteSQLs_jdbc(String sql, String split) throws RemoteException;

    public ValidateSQLResult validateSQL(String sql, boolean update) throws RemoteException;

    public ValidateSQLResult validateHQL(String sql, boolean update) throws RemoteException;

    /**
     * 验证触发语句是否正确
     * @param triger_text:触发语句
     * @param bean:触发对象
     * @param old_val:老值
     * @param new_val：新值
     * @return:验证结果
     */
    public ValidateSQLResult validateTriger(String triger_text, Object bean, Object old_val, Object new_val) throws RemoteException;

    public HashSet<String> getNot_triger_packages() throws RemoteException;

    public ValidateSQLResult update(Object obj, String hql) throws RemoteException;
}
