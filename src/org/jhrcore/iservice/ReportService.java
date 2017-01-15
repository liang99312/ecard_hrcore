/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice;

import java.rmi.RemoteException;
import java.util.List;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.report.ReportDef;
import org.jhrcore.entity.report.ReportGroup;
import org.jhrcore.entity.report.ReportLog;
import org.jhrcore.entity.report.ReportXlsScheme;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.w3c.dom.Document;

/**
 *
 * @author Administrator
 */
public interface ReportService extends SuperService {
    
    public byte[] getReportForDocument(Document doc, String rmiIp) throws RemoteException;

    public byte[] getReport_cpt(String report_def_key) throws RemoteException;

    public byte[] getReport_datasource(String report_def_key, String rmiIp) throws RemoteException;

    public byte[] getBase_datasource(String rmiIp) throws RemoteException;

    public byte[] getReportSetting(String id) throws RemoteException;

    public ValidateSQLResult saveReportSetting(String id, byte[] source) throws RemoteException;

    public ValidateSQLResult saveReport(ReportDef reportDef, byte[] db_source, byte[] cpt, boolean isNew, String roleKey) throws RemoteException;

    public ValidateSQLResult serRole_key(ReportDef reportDef, String roleKey) throws RemoteException;

    public ValidateSQLResult delReport(ReportDef reportDef) throws RemoteException;

    public ValidateSQLResult addReportToModule(String module_flag, List<String> reportKeys) throws RemoteException;

    public ValidateSQLResult setReportNoUser(List<String> noKeys, ReportLog modLog) throws RemoteException;

    public ValidateSQLResult createReportData(List<String> noKeys, ReportLog modLog) throws RemoteException;

    public ValidateSQLResult updateReportNoState(List<String> noKeys, ReportLog modLog, String old_type, String new_type, String group_key, String ym) throws RemoteException;

    public ValidateSQLResult delReportNo(List<String> noKeys, ReportLog modLog) throws RemoteException;

    public ValidateSQLResult copyRule(List<String> ruleKeys, List<String> reportKeys) throws RemoteException;

    public ValidateSQLResult addReportsToGroup(ReportGroup rgroup, List<String> reportKeys) throws RemoteException;

    public ValidateSQLResult addDeptsToGroup(ReportGroup rgroup, List<String> deptKeys) throws RemoteException;

    public ValidateSQLResult createReportNo(DeptCode dc, ReportGroup rgroup, String ym, List<ReportDef> reports) throws RemoteException;

    public int getTabColumn(String mc) throws RemoteException;

    public ValidateSQLResult saveXlsScheme(ReportXlsScheme scheme, boolean isNew) throws RemoteException;
    public boolean isSecuryed() throws RemoteException;
    public ValidateSQLResult reportCert(String code) throws RemoteException;
}
