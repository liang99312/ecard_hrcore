/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.importxls;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.TimeZone;
import jxl.*;
import jxl.read.biff.BiffException;
import org.jhrcore.entity.ExportDetail;
import org.jhrcore.entity.ExportScheme;
import org.jhrcore.util.UtilTool;
import org.jhrcore.entity.base.TempFieldInfo;

/**
 *
 * @author Administrator
 */
public class ReadXLS {

    public static XlsImportInfo importXls(File f) throws Exception {
        TimeZone gmt = TimeZone.getTimeZone("GMT");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(gmt);
        format.setLenient(false);
        XlsImportInfo xlsImportInfo = new XlsImportInfo();
//        try {
        FileInputStream fis = new FileInputStream(f);
        jxl.Workbook rwb = Workbook.getWorkbook(fis);
        jxl.Sheet sh = rwb.getSheet(0);
        String[] fields = new String[sh.getColumns()];
        ExportScheme exportScheme = (ExportScheme) UtilTool.createUIDEntity(ExportScheme.class);
        xlsImportInfo.setExportScheme(exportScheme);
        exportScheme.setScheme_name(sh.getCell(0, 0).getContents());
        exportScheme.setEntity_name(sh.getCell(0, 0).getCellFeatures().getComment());
        int rowCount = sh.getRows();
        List<ExportDetail> details = new ArrayList<ExportDetail>();
        for (int col = 0; col < sh.getColumns(); col++) {
            ExportDetail exportDetail = (ExportDetail) UtilTool.createUIDEntity(ExportDetail.class);
            exportDetail.setField_caption(sh.getCell(col, 1).getContents());
            exportDetail.setField_name(sh.getCell(col, 1).getCellFeatures().getComment());
            exportDetail.setExportScheme(exportScheme);
            exportDetail.setOrder_no(col);
            details.add(exportDetail);
            fields[col] = exportDetail.getField_name();
        }
        exportScheme.setExportDetails(details);
        List<Hashtable<String, String>> values = new ArrayList<Hashtable<String, String>>();
        for (int i = 2; i < rowCount; i++) {
            Hashtable<String, String> row = new Hashtable<String, String>();
            for (int j = 0; j < sh.getColumns(); j++) {
                if (sh.getCell(j, i).getType() == CellType.DATE) {
                    DateCell dc = (DateCell) sh.getCell(j, i);
                    row.put(fields[j], format.format(dc.getDate()));
                } else {
                    row.put(fields[j], sh.getCell(j, i).getContents());
                }
            }
            values.add(row);
        }
        xlsImportInfo.setValues(values);
        rwb.close();
        fis.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return xlsImportInfo;
    }

    public static XlsImportInfo importXls(File f, String type) {
        TimeZone gmt = TimeZone.getTimeZone("GMT");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(gmt);
        format.setLenient(false);
        XlsImportInfo xlsImportInfo = new XlsImportInfo();
        try {
            FileInputStream fis = new FileInputStream(f);
            jxl.Workbook rwb = Workbook.getWorkbook(fis);
            jxl.Sheet sh = rwb.getSheet(0);
            String[] fields = new String[sh.getColumns()];
            ExportScheme exportScheme = (ExportScheme) UtilTool.createUIDEntity(ExportScheme.class);
            xlsImportInfo.setExportScheme(exportScheme);
            exportScheme.setScheme_name(sh.getCell(0, 0).getContents());
            exportScheme.setEntity_name(sh.getCell(0, 0).getCellFeatures().getComment());
            int rowCount = sh.getRows();
            List<ExportDetail> details = new ArrayList<ExportDetail>();
            Hashtable<String, TempFieldInfo> classMap = new Hashtable<String, TempFieldInfo>();

            for (int col = 0; col < sh.getColumns(); col++) {
                ExportDetail exportDetail = (ExportDetail) UtilTool.createUIDEntity(ExportDetail.class);
                exportDetail.setField_caption(sh.getCell(col, 1).getContents());
                //将表字段类型映射成哈希表 以供查询
                String[] str = sh.getCell(col, 1).getCellFeatures().getComment().split("#");
                exportDetail.setField_name(str[0]);//列属性解析
                TempFieldInfo fieldAt = new TempFieldInfo();
                fieldAt.setField_type(str[1]);
                fieldAt.setField_width(Integer.parseInt(str[2]));
                fieldAt.setField_scale(Integer.parseInt(str[3]));
                classMap.put(str[0], fieldAt);
                exportDetail.setExportScheme(exportScheme);
                exportDetail.setOrder_no(col);
                details.add(exportDetail);
                fields[col] = exportDetail.getField_name();
            }
            exportScheme.setClassMap(classMap);
            exportScheme.setExportDetails(details);
            List<Hashtable<String, String>> values = new ArrayList<Hashtable<String, String>>();
            for (int i = 2; i < rowCount; i++) {
                Hashtable<String, String> row = new Hashtable<String, String>();
                for (int j = 0; j < sh.getColumns(); j++) {
                    if (sh.getCell(j, i).getType() == CellType.DATE) {
                        DateCell dc = (DateCell) sh.getCell(j, i);
                        row.put(fields[j], format.format(dc.getDate()));
                    } else {
                        row.put(fields[j], sh.getCell(j, i).getContents());
                    }
                }
                values.add(row);
            }
            xlsImportInfo.setValues(values);
            rwb.close();
            fis.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (BiffException e) {
            return null;
        } catch (IOException e) {
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
        return xlsImportInfo;
    }
}
