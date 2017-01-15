package org.jhrcore.util;

import com.foundercy.pf.control.table.FBaseTableColumn;
import com.foundercy.pf.control.table.FTable;
import com.foundercy.pf.control.table.FTableModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.log4j.Logger;
import org.jhrcore.entity.ExportDetail;
import org.jhrcore.entity.ExportScheme;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.ui.EditorFactory;

public class ExportUtil {
    
    private static Logger log = Logger.getLogger(ExportUtil.class.getName());
    
    public static void exportAnalyseData(String file_path, ExportScheme curExportScheme, FTable fTable) {
        file_path = file_path.replace("\\", "/");
        file_path = file_path.replace(" ", "");
        File file = null;
        Hashtable<String, WritableCellFormat> formats = getCommCellFormats();
        WritableWorkbook workbook;
        Hashtable<String, WritableCellFormat> format_keys = new Hashtable<String, WritableCellFormat>();
        for (ExportDetail ed : curExportScheme.getExportDetails()) {
            if (ed.getField_type().toLowerCase().equals("int") || ed.getField_type().toLowerCase().equals("integer")) {
                format_keys.put(ed.getField_name(), formats.get("int"));
            } else if (ed.getField_type().toLowerCase().equals("float") || ed.getField_type().toLowerCase().equals("bigdecimal")) {
                format_keys.put(ed.getField_name(), formats.get("float"));
            }
        }
        try {
            file = new File(file_path);
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("First Sheet", 0);
            Label label = new Label(0, 0, curExportScheme.getScheme_titile(), formats.get("title"));
            WritableCellFeatures cellFeatures = new WritableCellFeatures();
            cellFeatures.setComment(curExportScheme.getEntity_name());
            label.setCellFeatures(cellFeatures);
            sheet.addCell(label);
            sheet.mergeCells(0, 0, Math.max(curExportScheme.getExportDetails().size() - 1, 0), 0);
            
            int j = 0;
            List<ExportDetail> exportDetails = new ArrayList<ExportDetail>();
            exportDetails.addAll(curExportScheme.getExportDetails());
            SysUtil.sortListByInteger(exportDetails, "order_no");
            for (ExportDetail exportDetail : exportDetails) {
                for (int i = 0; i < fTable.getColumnModel().getColumnCount(); i++) {
                    FBaseTableColumn column = fTable.getColumnModel().getColumn(i);
                    if (column.getId().equals(exportDetail.getField_name())) {
                        sheet.setColumnView(j, column.getPreferredWidth() / 5);
                        break;
                    }
                }
                label = new Label(j, 1, exportDetail.getField_caption(), formats.get("title1"));
                cellFeatures = new WritableCellFeatures();
                cellFeatures.setComment(exportDetail.getField_name());
                label.setCellFeatures(cellFeatures);
                sheet.addCell(label);
                j++;
            }
            int i = 2;
            for (Object obj : fTable.getAllObjects()) {
                int col = 0;
                for (ExportDetail exportDetail : exportDetails) {
                    String fieldName = exportDetail.getField_name();
                    Object tmp_obj;
                    if (fieldName.startsWith("#")) {
                        FTableModel ftm = (FTableModel) fTable.getModel();
                        String field = fieldName.substring(1, fieldName.indexOf(".")) + "@" + (i - 2);
                        tmp_obj = ftm.getHt_OtherTable().get(field).get(fieldName.substring(fieldName.indexOf(".") + 1));
                    } else {
                        tmp_obj = EditorFactory.getValueBy(obj, fieldName);
                    }
                    WritableCellFormat format = format_keys.get(exportDetail.getField_name());
                    if (format == null) {
                        label = new Label(col, i, tmp_obj == null ? "" : tmp_obj.toString(), formats.get("text"));
                        sheet.addCell(label);
                    } else {
                        sheet.addCell(new jxl.write.Number(col, i, new Float(tmp_obj == null ? "0.0" : tmp_obj.toString()).floatValue(), format));
                    }
                    col++;
                }
                i++;
            }
            workbook.write();
            workbook.close();
            Runtime.getRuntime().exec("cmd /c \"" + file_path + "\"");
        } catch (WriteException ex) {
            log.error(ex);
        } catch (IOException ex) {
            log.error(ex);
        }
    }
    
    public static Hashtable<String, WritableCellFormat> getCommCellFormats() {
        Hashtable<String, WritableCellFormat> formats = new Hashtable<String, WritableCellFormat>();
        WritableCellFormat intFormat = new WritableCellFormat(NumberFormats.INTEGER);
        WritableCellFormat floatFormat = new WritableCellFormat(NumberFormats.FLOAT);
        WritableCellFormat textFormat = new WritableCellFormat();
        WritableCellFormat intFormatErr = new WritableCellFormat(NumberFormats.INTEGER);
        WritableCellFormat floatFormatErr = new WritableCellFormat(NumberFormats.FLOAT);
        WritableCellFormat textFormatErr = new WritableCellFormat();
        WritableCellFormat titleFormat = new WritableCellFormat();
        WritableCellFormat titleFormat1 = new WritableCellFormat();
        WritableFont cellFont = new jxl.write.WritableFont(WritableFont.ARIAL, 10,
                WritableFont.NO_BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableFont titleFont = new jxl.write.WritableFont(WritableFont.ARIAL, 20,
                WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableFont titleFont1 = new jxl.write.WritableFont(WritableFont.ARIAL, 10,
                WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        formats.put("int", intFormat);
        formats.put("float", floatFormat);
        formats.put("text", textFormat);
        formats.put("interr", intFormatErr);
        formats.put("floaterr", floatFormatErr);
        formats.put("texterr", textFormatErr);
        formats.put("title", titleFormat);
        formats.put("title1", titleFormat1);
        try {
            //标题格式
            titleFormat.setFont(titleFont);
            titleFormat.setAlignment(jxl.format.Alignment.CENTRE);
            titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            //副标题格式
            titleFormat1.setFont(titleFont1);
            titleFormat1.setAlignment(jxl.format.Alignment.CENTRE);
            titleFormat1.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            //int型数据格式
            intFormat.setFont(cellFont);
            intFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            //float型数据格式
            floatFormat.setFont(cellFont);
            floatFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            //int型错误数据格式
            intFormatErr.setFont(cellFont);
            intFormatErr.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            intFormatErr.setBackground(Colour.RED);
            //float型错误数据格式
            floatFormatErr.setFont(cellFont);
            floatFormatErr.setBackground(Colour.RED);
            floatFormatErr.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            //普通text数据格式
            textFormat.setFont(cellFont);
            textFormat.setAlignment(jxl.format.Alignment.LEFT);
            textFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            //普通text错误数据格式
            textFormatErr.setFont(cellFont);
            textFormatErr.setBackground(Colour.RED);
            textFormatErr.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
        } catch (WriteException ex) {
            log.error(ex);
        }
        return formats;
    }

    /**
     * 导出模板
     * @param file_path：导出路径
     * @param curExportScheme：导出使用的显示方案
     * @param fTable：用来提供数据的网格
     */
    public static void export(String file_path, ExportScheme curExportScheme, FTable fTable) {
        file_path = file_path.replace("\\", "/");
        file_path = file_path.replace(" ", "");
        File file = null;
        WritableWorkbook workbook;
        Hashtable<String, WritableCellFormat> formats = getCommCellFormats();
        Hashtable<String, WritableCellFormat> format_keys = new Hashtable<String, WritableCellFormat>();
        for (ExportDetail ed : curExportScheme.getExportDetails()) {
            if (ed.getField_type().toLowerCase().equals("int") || ed.getField_type().toLowerCase().equals("integer")) {
                format_keys.put(ed.getField_name(), formats.get("int"));
            } else if (ed.getField_type().toLowerCase().equals("float") || ed.getField_type().toLowerCase().equals("bigdecimal")) {
                format_keys.put(ed.getField_name(), formats.get("float"));
            }
        }
        try {
            file = new File(file_path);
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("First Sheet", 0);
            Label label = new Label(0, 0, curExportScheme.getScheme_titile(), formats.get("title"));
            WritableCellFeatures cellFeatures = new WritableCellFeatures();
            cellFeatures.setComment(curExportScheme.getEntity_name());
            label.setCellFeatures(cellFeatures);
            sheet.addCell(label);
            sheet.mergeCells(0, 0, Math.max(curExportScheme.getExportDetails().size() - 1, 0), 0);
            int j = 0;
            List<ExportDetail> exportDetails = new ArrayList<ExportDetail>();
            exportDetails.addAll(curExportScheme.getExportDetails());
            SysUtil.sortListByInteger(exportDetails, "order_no");
            for (ExportDetail exportDetail : exportDetails) {
                for (int i = 0; i < fTable.getColumnModel().getColumnCount(); i++) {
                    FBaseTableColumn column = fTable.getColumnModel().getColumn(i);
                    if (column.getId().equals(exportDetail.getField_name())) {
                        sheet.setColumnView(j, column.getPreferredWidth() / 5);
                        break;
                    }
                }
                label = new Label(j, 1, exportDetail.getField_caption(), formats.get("title1"));
                cellFeatures = new WritableCellFeatures();
                cellFeatures.setComment(exportDetail.getField_name());
                label.setCellFeatures(cellFeatures);
                sheet.addCell(label);
                j++;
            }
            int i = 2;
            for (Object obj : fTable.getAllObjects()) {
                int col = 0;
                for (ExportDetail exportDetail : exportDetails) {
                    String fieldName = exportDetail.getField_name();
                    Object tmp_obj;
                    if (fieldName.startsWith("#")) {
                        FTableModel ftm = (FTableModel) fTable.getModel();
                        String field = fieldName.substring(1, fieldName.indexOf(".")) + "@" + (i - 2);
                        tmp_obj = ftm.getHt_OtherTable().get(field).get(fieldName.substring(fieldName.indexOf(".") + 1));
                    } else {
                        tmp_obj = EditorFactory.getValueBy(obj, fieldName);
                    }
                    WritableCellFormat format = format_keys.get(exportDetail.getField_name());
                    if (format == null) {
                        label = new Label(col, i, tmp_obj == null ? "" : tmp_obj.toString(), formats.get("text"));
                        sheet.addCell(label);
                    } else {
                        sheet.addCell(new jxl.write.Number(col, i, new Float(tmp_obj == null ? "0.0" : tmp_obj.toString()).floatValue(), format));
                    }
                    col++;
                }
                i++;
            }
            workbook.write();
            workbook.close();
            Runtime.getRuntime().exec("cmd /c \"" + file_path + "\"");
        } catch (WriteException ex) {
            log.error(ex);
        } catch (IOException ex) {
            log.error(ex);
        }
    }

    /**
     * Excel导出错误报告
     * @param file_path：报告路径
     * @param curExportScheme：显示方案
     * @param repeat_data：重复或者无匹配项的数据
     * @param error_data：错误数据
     * @param error_keys：错误索引列表，以行为索引
     */
    public static void export(String file_path, ExportScheme curExportScheme, List<Hashtable<String, String>> repeat_data, List<Hashtable<String, String>> error_data, Hashtable<Integer, List<String>> error_keys) {
        export(file_path, curExportScheme, repeat_data, error_data, error_keys, null);
    }

    /**
     * Excel导出错误报告
     * @param file_path：报告路径
     * @param curExportScheme：显示方案
     * @param repeat_data：重复或者无匹配项的数据
     * @param error_data：错误数据
     * @param error_keys：错误索引列表，以行为索引
     */
    public static void export(String file_path, ExportScheme curExportScheme, List<Hashtable<String, String>> repeat_data, List<Hashtable<String, String>> error_data, Hashtable<Integer, List<String>> error_keys, List<Hashtable<String, Object>> error_update_data) {
        try {
            if (repeat_data.isEmpty() && error_data.isEmpty() && (error_update_data == null || error_update_data.isEmpty())) {
                return;
            }
            if (!file_path.toLowerCase().endsWith(".xls")) {
                file_path = file_path + ".xls";
            }
            Hashtable<String, WritableCellFormat> formats = getCommCellFormats();
            File file = new File(file_path);
            WritableWorkbook workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("First Sheet", 0);
            Label label = new Label(0, 0, curExportScheme.getScheme_titile(), formats.get("title"));
            WritableCellFeatures cellFeatures = new WritableCellFeatures();
            cellFeatures.setComment(curExportScheme.getEntity_name());
            label.setCellFeatures(cellFeatures);
            sheet.addCell(label);
            sheet.mergeCells(0, 0, Math.max(curExportScheme.getExportDetails().size() - 1, 0), 0);
            int j = 0;
            List<ExportDetail> exportDetails = new ArrayList<ExportDetail>();
            exportDetails.addAll(curExportScheme.getExportDetails());
            SysUtil.sortListByInteger(exportDetails, "order_no");
            for (ExportDetail exportDetail : exportDetails) {
                label = new Label(j, 1, exportDetail.getField_caption(), formats.get("title1"));
                cellFeatures = new WritableCellFeatures();
                cellFeatures.setComment(exportDetail.getField_name());
                label.setCellFeatures(cellFeatures);
                sheet.addCell(label);
                j++;
            }
            int i = 1;
            if (repeat_data.size() > 0) {
                i++;
                label = new Label(0, i, "以下是重复数据(或无匹配项、无权限项)，无法导入", formats.get("title1"));
                cellFeatures = new WritableCellFeatures();
                cellFeatures.setComment(curExportScheme.getEntity_name());
                label.setCellFeatures(cellFeatures);
                sheet.addCell(label);
                sheet.mergeCells(0, i, Math.max(curExportScheme.getExportDetails().size() - 1, 0), i);
                i = writeXLS(repeat_data, exportDetails, sheet, i, null);
            }
            if (error_data.size() > 0) {
                i++;
                label = new Label(0, i, "以下是错误数据，无法导入，红色部分为错误项", formats.get("title1"));
                cellFeatures = new WritableCellFeatures();
                cellFeatures.setComment(curExportScheme.getEntity_name());
                label.setCellFeatures(cellFeatures);
                sheet.addCell(label);
                sheet.mergeCells(0, i, Math.max(curExportScheme.getExportDetails().size() - 1, 0), i);
                i = writeXLS(error_data, exportDetails, sheet, i, error_keys);
            }
            if (error_update_data != null && error_update_data.size() > 0) {
                i++;
                label = new Label(0, i, "以下是数据库更新错误数据", formats.get("title1"));
                cellFeatures = new WritableCellFeatures();
                cellFeatures.setComment(curExportScheme.getEntity_name());
                label.setCellFeatures(cellFeatures);
                sheet.addCell(label);
                sheet.mergeCells(0, i, Math.max(curExportScheme.getExportDetails().size() - 1, 0), i);
                writeXLS(error_update_data, exportDetails, sheet, i, error_keys);
            }
            workbook.write();
            workbook.close();
            Runtime.getRuntime().exec("cmd /c \"" + file_path + "\"");
        } catch (IOException ex) {
            log.error(ex);
        } catch (WriteException ex) {
            log.error(ex);
        }
    }
    
    private static int writeXLS(List write_data, List<ExportDetail> exportDetails, WritableSheet sheet, int i, Hashtable<Integer, List<String>> error_keys) {
        int k = 0;
        boolean show_error = !(error_keys == null || error_keys.isEmpty());
        Hashtable<String, WritableCellFormat> formats = getCommCellFormats();
        for (Object data : write_data) {
            i++;
            Hashtable<String, Object> row_data = (Hashtable<String, Object>) data;
//        for (Hashtable<String, String> row_data : write_data) {
            int col = 0;
            List<String> fields = null;
            if (show_error) {
                fields = error_keys.get(k);
            }
            for (ExportDetail exportDetail : exportDetails) {
                WritableCellFormat format2 = null;
                Object tmp_obj = row_data.get(exportDetail.getField_name());
                WritableCell cell = null;
                Label label = null;
                if (show_error && fields != null && fields.contains(exportDetail.getField_name())) {
                    if (exportDetail.getField_type() == null) {
                        format2 = formats.get("texterr");
                        label = new Label(col, i, tmp_obj == null ? "" : tmp_obj.toString(), format2);
                    } else if (exportDetail.getField_type().toLowerCase().equals("int") || exportDetail.getField_type().toLowerCase().equals("integer")) {
                        format2 = formats.get("interr");
                        Object obj = SysUtil.objToInteger(tmp_obj.toString(), null);
                        if (obj == null) {
                            label = new Label(col, i, tmp_obj.toString(), format2);
                        } else {
                            cell = new jxl.write.Number(col, i, (Integer) obj, format2);
                        }
                    } else if (exportDetail.getField_type().toLowerCase().equals("float") || exportDetail.getField_type().toLowerCase().equals("bigdecimal")) {
                        format2 = formats.get("floaterr");
                        Object obj = SysUtil.objToFloat(tmp_obj.toString(), null);
                        if (obj == null) {
                            label = new Label(col, i, tmp_obj.toString(), format2);
                        } else {
                            cell = new jxl.write.Number(col, i, (Float) obj, format2);
                        }
                    } else {
                        format2 = formats.get("texterr");
                        label = new Label(col, i, tmp_obj == null ? "" : tmp_obj.toString(), format2);
                    }
                } else {
                    if (exportDetail.getField_type() == null) {
                        format2 = formats.get("text");
                        label = new Label(col, i, tmp_obj == null ? "" : tmp_obj.toString(), format2);
                    } else if (exportDetail.getField_type().toLowerCase().equals("int") || exportDetail.getField_type().toLowerCase().equals("integer")) {
                        format2 = formats.get("int");
                        Object obj = SysUtil.objToInt(tmp_obj.toString());
                        if (obj == null) {
                            label = new Label(col, i, "", format2);
                        } else {
                            cell = new jxl.write.Number(col, i, (Integer) obj, format2);
                        }
                    } else if (exportDetail.getField_type().toLowerCase().equals("float") || exportDetail.getField_type().toLowerCase().equals("bigdecimal")) {
                        format2 = formats.get("float");
                        Object obj = SysUtil.objToFloat(tmp_obj == null ? "" : tmp_obj.toString());
                        if (obj == null) {
                            label = new Label(col, i, "", format2);
                        } else {
                            cell = new jxl.write.Number(col, i, (Float) obj, format2);
                        }
                    } else {
                        format2 = formats.get("text");
                        label = new Label(col, i, tmp_obj == null ? "" : tmp_obj.toString(), format2);
                    }
                }
                try {
                    if (cell != null) {
                        sheet.addCell(cell);
                    } else if (label != null) {
                        sheet.addCell(label);
                    }
                } catch (WriteException ex) {
                    ex.printStackTrace();
                }
                col++;
            }
            k++;
        }
        return i;
    }
    
    public static void export(String file_path, ExportScheme curExportScheme, List<Hashtable<String, String>> repeat_data) {
        try {
            if (repeat_data.size() == 0) {
                return;
            }
            if (!file_path.toLowerCase().endsWith(".xls")) {
                file_path = file_path + ".xls";
            }
            Hashtable<String, WritableCellFormat> formats = getCommCellFormats();
            File file = new File(file_path);
            WritableWorkbook workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("First Sheet", 0);
            Label label = new Label(0, 0, curExportScheme.getScheme_titile(), formats.get("title"));
            WritableCellFeatures cellFeatures = new WritableCellFeatures();
            cellFeatures.setComment(curExportScheme.getEntity_name());
            label.setCellFeatures(cellFeatures);
            sheet.addCell(label);
            sheet.mergeCells(0, 0, Math.max(curExportScheme.getExportDetails().size() - 1, 0), 0);
            int j = 0;
            List<ExportDetail> exportDetails = new ArrayList<ExportDetail>();
            exportDetails.addAll(curExportScheme.getExportDetails());
            SysUtil.sortListByInteger(exportDetails, "order_no");
            for (ExportDetail exportDetail : exportDetails) {
                label = new Label(j, 1, exportDetail.getField_caption(), formats.get("title1"));
                cellFeatures = new WritableCellFeatures();
                cellFeatures.setComment(exportDetail.getField_name());
                label.setCellFeatures(cellFeatures);
                sheet.addCell(label);
                j++;
            }
            int i = 2;
            if (repeat_data.size() > 0) {
                label = new Label(0, i, "以下是可插入数据", formats.get("title1"));
                cellFeatures = new WritableCellFeatures();
                cellFeatures.setComment(curExportScheme.getEntity_name());
                label.setCellFeatures(cellFeatures);
                writeXLS(repeat_data, exportDetails, sheet, i, null);
            }
            workbook.write();
            workbook.close();
            Runtime.getRuntime().exec("cmd /c \"" + file_path + "\"");
        } catch (IOException ex) {
            log.error(ex);
        } catch (WriteException ex) {
            log.error(ex);
        }
    }
    
    public static void export(String file_path, List<TempFieldInfo> fields, List data) {
        exportData(file_path, fields, data, false, "");
    }

    public static void export(String file_path, List<TempFieldInfo> fields, List data, boolean protected_flag, String password) {
        exportData(file_path, fields, data, protected_flag, password);
    }

    /**
     * 导出模板
     * @param file_path：导出路径
     * @param curExportScheme：导出使用的显示方案
     * @param fTable：用来提供数据的网格
     */
    public static void exportData(String file_path, List<TempFieldInfo> fields, List data, boolean protected_flag, String password) {
        file_path = file_path.replace("\\", "/");
        file_path = file_path.replace(" ", "");
        File file = null;
        WritableWorkbook workbook;
        Hashtable<String, WritableCellFormat> formats = getCommCellFormats();
        Hashtable<String, WritableCellFormat> format_keys = new Hashtable<String, WritableCellFormat>();
        for (TempFieldInfo ed : fields) {
            if (ed.getField_type().toLowerCase().equals("int") || ed.getField_type().toLowerCase().equals("integer")) {
                format_keys.put(ed.getField_name(), formats.get("int"));
            } else if (ed.getField_type().toLowerCase().equals("float") || ed.getField_type().toLowerCase().equals("bigdecimal")) {
                format_keys.put(ed.getField_name(), formats.get("float"));
            }
        }
        try {
            file = new File(file_path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("First Sheet", 0);
            Label label = new Label(0, 0, "", formats.get("title"));
            WritableCellFeatures cellFeatures = new WritableCellFeatures();
            cellFeatures.setComment(fields.get(0).getEntity_name());
            label.setCellFeatures(cellFeatures);
            sheet.addCell(label);
            sheet.mergeCells(0, 0, Math.max(fields.size() - 1, 0), 0);
            int j = 0;
            for (TempFieldInfo tfi : fields) {
                sheet.setColumnView(j, tfi.getField_width());
                label = new Label(j, 1, tfi.getCaption_name(), formats.get("title1"));
                cellFeatures = new WritableCellFeatures();
                cellFeatures.setComment(tfi.getField_name());
                label.setCellFeatures(cellFeatures);
                sheet.addCell(label);
                j++;
            }
            int i = 2;
            int cols = fields.size();
            for (Object obj : data) {
                Object[] objs = (Object[]) obj;
                for (int k = 0; k < cols; k++) {
                    Object tmp_obj = objs[k];
                    label = new Label(k, i, tmp_obj == null ? "" : tmp_obj.toString(), formats.get("text"));
                    sheet.addCell(label);
                }
                i++;
            }
            if (protected_flag) {
                sheet.getSettings().setProtected(true);
                sheet.getSettings().setPassword(password);
            }
            workbook.write();
            workbook.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex);
        }
    }

    public static ExportDetail addField(TempFieldInfo tfi, int i, ExportScheme es) {
        ExportDetail ed = (ExportDetail) UtilTool.createUIDEntity(ExportDetail.class);
        ed.setEntity_caption(tfi.getEntity_caption());
        ed.setEntity_name(tfi.getEntity_name());
        ed.setField_name(tfi.getField_name());
        ed.setField_caption(tfi.getCaption_name());
        ed.setField_type(tfi.getField_type());
        ed.setOrder_no(i);
        ed.setExportScheme(es);
        return ed;
    }
}
