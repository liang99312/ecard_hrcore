package org.jhrcore.iservice;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.jhrcore.entity.AutoNoRule;
import org.jhrcore.entity.CommMap;
import org.jhrcore.entity.ExportScheme;
import org.jhrcore.entity.FormulaScheme;
import org.jhrcore.entity.RyChgLog;
import org.jhrcore.entity.base.LoginUser;
import org.jhrcore.entity.query.CommAnalyseScheme;
import org.jhrcore.entity.query.QueryAnalysisScheme;
import org.jhrcore.entity.query.QueryPart;
import org.jhrcore.entity.query.QueryScheme;
import org.jhrcore.entity.right.FuntionRight;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.entity.showstyle.ShowScheme;

public interface CommService extends SuperService {

    public String getServerIp() throws RemoteException;

    //web服务器的Ip
    public String getWebServerIp() throws RemoteException;

    //web服务器的端口
    public String getWebServerPort() throws RemoteException;

    //获得数据库方言
    public String getSQL_dialect() throws RemoteException;

    public Date getServerDate() throws RemoteException;

    // 根据自动编号规则编码获取最新的号码, b_inc表示在获取最新号码的时候的增长值，0表示不增长
    public String fetchNewNoBy(String autoNoRule_key, int b_inc, Hashtable<String, String> params) throws RemoteException;
// 根据自动编号规则编码获取最新的号码, b_inc表示在获取最新号码的时候的增长值，0表示不增长

    public String fetchNewNoBy(AutoNoRule anr, Hashtable<String, String> params) throws RemoteException;

    public Properties getSysProperties() throws RemoteException;

    public void uploadFileByStream(InputStream ips, String dest) throws RemoteException;

    public void uploadFile(byte[] p_byte, String path) throws RemoteException;

    public byte[] downloadFile(String ab_path) throws RemoteException;

    public boolean deleteFiles(List<String> list_paths) throws RemoteException;

    public ValidateSQLResult saveQueryScheme(QueryScheme qs) throws RemoteException;

    public ValidateSQLResult saveExportScheme(ExportScheme es) throws RemoteException;

    public ValidateSQLResult connectServer(String type, String user_code) throws RemoteException;

    public ValidateSQLResult disconnect(List<Object> users, String msg) throws RemoteException;

    public ValidateSQLResult sendMsg(List<Object> users, String msg) throws RemoteException;

    public List<LoginUser> getLoginUsers(Object cur_dept, String dept_right_sql) throws RemoteException;

    public List<String> getSysModules() throws RemoteException;

    public String[] getSA() throws RemoteException;

    public ValidateSQLResult saveShowSchemeGroup(List new_groups, String user_code, String module_code) throws RemoteException;

    public ValidateSQLResult saveShowScheme(ShowScheme ss, String code) throws RemoteException;

    public ValidateSQLResult saveColumnSumScheme(String code, String user_code, List cols) throws RemoteException;

    public ValidateSQLResult delFormulaDetail(String formula_key, List<String> detail_keys) throws RemoteException;

    public ValidateSQLResult saveFormulaDetail(Set details) throws RemoteException;

    public ValidateSQLResult saveAnalyseScheme(CommAnalyseScheme as, QueryScheme qs, QueryPart part) throws RemoteException;

    public ValidateSQLResult saveQueryAnalysisScheme(QueryAnalysisScheme as, QueryScheme qs) throws RemoteException;

    public ValidateSQLResult saveQueryPart(QueryPart qp) throws RemoteException;

    public ValidateSQLResult saveQueryExtraField(List objs, List existKeys) throws RemoteException;

    public ValidateSQLResult saveExtraFieldOrder(List<String[]> orders) throws RemoteException;

    public ValidateSQLResult updateFunRights(List<FuntionRight> rights) throws RemoteException;

    public ValidateSQLResult delShowScheme(ShowScheme es) throws RemoteException;

    public String getServer_file_path() throws RemoteException;

    public ValidateSQLResult saveParameters(List list) throws RemoteException;

    public Hashtable<String, String> getSecuryInfo(boolean fetchVer) throws RemoteException;

    public Object login(String person_code, String pswd) throws RemoteException;

    public void logEvent(int time, String logStr) throws RemoteException;

    public List getSysCodes() throws RemoteException;

    public List getSysModule(boolean fetchClass, boolean fetchEntity, boolean fetchField) throws RemoteException;

    public List getSysTrigerField(String entityNames, boolean isTrigerManager) throws RemoteException;
    
    public List fetchNewNoByAndAuto(String autoNoRule_key, int b_inc, Hashtable<String, String> params,Map<String,Integer> map) throws RemoteException;
}
