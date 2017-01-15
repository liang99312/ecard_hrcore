/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import org.jhrcore.entity.FileRecord;
import org.jhrcore.entity.salary.ValidateSQLResult;

/**
 *
 * @author mxliteboss
 */
public interface FileService extends SuperService {

    public ValidateSQLResult uploadNewFile(byte[] buffer, FileRecord fr) throws RemoteException;

    public ValidateSQLResult uploadOverFile(byte[] buffer, FileRecord fr) throws RemoteException;

    public ValidateSQLResult uploadFiles(List<byte[]> buffer, FileRecord fr) throws RemoteException;

    public byte[] downloadFileByID(String recoreId) throws RemoteException;

    public HashMap<String, byte[]> downloadFileBySrcID(String srcId) throws RemoteException;

    public FileRecord getRecordByID(String recoreId) throws RemoteException;

    public List<FileRecord> getRecordBySrcID(String srcId) throws RemoteException;

    public ValidateSQLResult deleteFileByID(String recoreId) throws RemoteException;

    public ValidateSQLResult deleteFileBySrcID(String srcId) throws RemoteException;
}
