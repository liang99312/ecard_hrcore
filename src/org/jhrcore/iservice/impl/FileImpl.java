/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.iservice.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.FileRecord;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.FileService;
import org.jhrcore.serviceproxy.LocatorManager;

/**
 *
 * @author mxliteboss
 */
public class FileImpl {

    private static String service = FileService.class.getSimpleName();

    public static ValidateSQLResult uploadNewFile(byte[] buffer, FileRecord fr) {
        Object obj = LocatorManager.invokeService(service, "uploadNewFile", new Object[]{buffer, fr});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult uploadOverFile(byte[] buffer, FileRecord fr) {
        Object obj = LocatorManager.invokeService(service, "uploadOverFile", new Object[]{buffer, fr});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult uploadFiles(List<byte[]> buffer, FileRecord fr) {
        Object obj = LocatorManager.invokeService(service, "uploadFiles", new Object[]{buffer, fr});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static byte[] downloadFileByID(String recoreId) {
        Object obj = LocatorManager.invokeService(service, "downloadFileByID", new Object[]{recoreId});
        if (obj != null) {
            return (byte[]) obj;
        }
        return null;
    }

    public static HashMap<String, byte[]> downloadFileBySrcID(String srcId) {
        Object obj = LocatorManager.invokeService(service, "downloadFileBySrcID", new Object[]{srcId});
        if (obj != null) {
            return (HashMap<String, byte[]>) obj;
        }
        return new HashMap();
    }

    public static FileRecord getRecordByID(String recoreId) {
        Object obj = LocatorManager.invokeService(service, "getRecordByID", new Object[]{recoreId});
        if (obj != null) {
            return (FileRecord) obj;
        }
        return null;
    }

    public static List<FileRecord> getRecordBySrcID(String srcId) {
        Object obj = LocatorManager.invokeService(service, "getRecordBySrcID", new Object[]{srcId});
        if (obj != null) {
            return (List<FileRecord>) obj;
        }
        return new ArrayList();
    }

    public static ValidateSQLResult deleteFileByID(String recoreId) {
        Object obj = LocatorManager.invokeService(service, "deleteFileByID", new Object[]{recoreId});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }

    public static ValidateSQLResult deleteFileBySrcID(String srcId) {
        Object obj = LocatorManager.invokeService(service, "deleteFileBySrcID", new Object[]{srcId});
        if (obj != null) {
            return (ValidateSQLResult) obj;
        }
        return CommUtil.getServiceErrorResult();
    }
}
