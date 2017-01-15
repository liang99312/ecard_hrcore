/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.mutil;

import com.fr.cell.editor.AbstractCellEditor;
import com.fr.report.ParameterReport;
import com.fr.report.WorkBook;
import com.fr.report.io.ExcelExporter;
import com.fr.report.io.Exporter;
import com.fr.report.io.PDFExporter;
import com.fr.report.io.TemplateImporter;
import com.fr.report.io.WordExporter;
import com.fr.report.parameter.Parameter;
import com.fr.view.PreviewPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.DateFormat;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.log4j.Logger;
import org.jhrcore.client.CommUtil;
import org.jhrcore.comm.HrLog;
import org.jhrcore.client.UserContext;
import org.jhrcore.client.report.FillDialog;
import org.jhrcore.client.report.FtReportUtil;
import org.jhrcore.client.report.ReportPanel;
import org.jhrcore.util.PublicUtil;
import org.jhrcore.comm.ConfigManager;
import org.jhrcore.entity.base.ModuleInfo;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.report.ReportDef;
import org.jhrcore.entity.report.ReportGroup;
import org.jhrcore.entity.report.ReportNo;
import org.jhrcore.entity.report.ReportParaSet;
import org.jhrcore.entity.report.ReportXlsCondition;
import org.jhrcore.entity.report.ReportXlsDetail;
import org.jhrcore.entity.report.ReportXlsScheme;
import org.jhrcore.iservice.impl.ReportImpl;
import org.jhrcore.msg.CommMsg;
import org.jhrcore.rebuild.EntityBuilder;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.ui.ShowProcessDlg;
import org.jhrcore.ui.TreeSelectMod;
import org.jhrcore.ui.VerticalSizableLayout;
import org.jhrcore.ui.renderer.HRRendererView;
import org.jhrcore.util.ComponentUtil;
import org.jhrcore.util.ExportUtil;
import org.jhrcore.util.FileChooserUtil;
import org.jhrcore.util.ImageUtil;
import org.jhrcore.util.SysUtil;

/**
 *
 * @author mxliteboss
 */
public class ReportUtil {

    private static Logger log = Logger.getLogger(ReportUtil.class);
    private static Hashtable<String, String[]> reportParaFields = new Hashtable<String, String[]>();

    static {
        ReportUtil.reportParaFields.put("A01", new String[]{"a0190"});
        ReportUtil.reportParaFields.put("BasePersonChange", new String[]{"order_no", "a01.a0190"});
        ReportUtil.reportParaFields.put("Da_a01", new String[]{"order_no", "a01.a0190"});
        ReportUtil.reportParaFields.put("L_send", new String[]{"a01.a0190"});
    }

    public static JTree getReportTree(TreeModel rm) {
        JTree tree = new JTree(rm);
        return getReportTree(tree);
    }

    public static JTree getReportTree(JTree tree) {
        HRRendererView.getReportMap().initTree(tree, TreeSelectMod.nodeCheckChildFollowMod);
        return tree;
    }

    public static void initReportModel(List modules, List<ReportDef> reports) {
        if (modules == null || reports == null) {
            return;
        }
        Hashtable<String, ModuleInfo> moduleKeys = new Hashtable<String, ModuleInfo>();
        for (Object obj : modules) {
            ModuleInfo mi = (ModuleInfo) obj;
            moduleKeys.put(mi.getModule_key(), mi);
        }
        List data = CommUtil.selectSQL("select rd.reportDef_key,rd.report_class,rd.report_name,rd.order_no,rd.module_key,rd.report_code from ReportDef rd order by rd.report_class,rd.order_no");
        for (Object obj : data) {
            Object[] objs = (Object[]) obj;
            ReportDef rd = new ReportDef();
            rd.setReportDef_key(objs[0].toString());
            rd.setReport_name(objs[2] == null ? "" : objs[2].toString().trim());
            rd.setReport_class(objs[1] == null ? "" : objs[1].toString().trim());
            rd.setReport_code(objs[4] == null ? "" : objs[4].toString());
            rd.setOrder_no(Integer.valueOf(objs[3].toString()));
            ModuleInfo mi = moduleKeys.get(SysUtil.objToStr(objs[4]));
            if (mi == null) {
                continue;
            }
            rd.setModuleInfo(mi);
            reports.add(rd);
//            if (objs[4] != null) {
//                rd.setModuleInfo(moduleKeys.get(objs[4].toString()));
//                reports.add(rd);
//            }
        }
    }

    public static ReportDef getReportDef(String reportDef_key, List reports) {
        if (reportDef_key == null || reports == null || reports.isEmpty()) {
            return null;
        }
        for (Object obj : reports) {
            ReportDef rd = (ReportDef) obj;
            if (rd.getReportDef_key().equals(reportDef_key)) {
                return rd;
            }
        }
        return null;
    }

    public static void download_datasource(ReportDef reportDef, boolean forexcute) {
        byte[] imageBytes = ReportImpl.getReport_datasource(reportDef.getReportDef_key(), ConfigManager.getConfigManager().getProperty("base.ApplicationServer"));
        if (imageBytes == null) {
            return;
        }
        File file = new File(System.getProperty("user.home") + "/resources/datasource.xml");
        if (forexcute) {
            file = new File(System.getProperty("user.home") + "/exec/resources/datasource.xml");
        }
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        BufferedOutputStream output = null;
        try {
            output = new BufferedOutputStream(new FileOutputStream(file));
            output.write(imageBytes);
        } catch (Exception e) {
            HrLog.error(ReportUtil.class, e);
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (Exception ex) {
                }
            }
        }
    }

    public static JPanel getPanelByParas(Parameter[] parameters, HashMap<String, AbstractCellEditor> nameHash) {
        JPanel pnl = new JPanel();
        JPanel jpanel1 = new javax.swing.JPanel();
        pnl.add(jpanel1, "Center");
        jpanel1.setLayout(new VerticalSizableLayout());
        int pre_height = 0;
        if (parameters != null && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                boolean bexists = false;
                for (int j = 0; j < i; j++) {
                    if (com.fr.base.core.ComparatorUtils.equals(parameter.getName(), parameters[j].getName())) {
                        bexists = true;
                        break;
                    }
                }
                if (bexists) {
                    continue;
                }
                javax.swing.JTextField jtextfield = new javax.swing.JTextField();
                jtextfield.setText("" + parameter.getValue());
                javax.swing.JPanel jpanel2 = new javax.swing.JPanel();
                jpanel2.setLayout(com.fr.cell.core.layout.LayoutFactory.createBorderLayout());
                jpanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(parameter.getName() + ":"));
                AbstractCellEditor cellEditor_0 = FtReportUtil.createParameterEditor(parameter);
                jpanel2.add(cellEditor_0.getCellEditorComponent(null, null), "Center");
                jpanel2.setPreferredSize(new java.awt.Dimension(180, jpanel2.getPreferredSize().height));
                jpanel1.add(jpanel2, parameter.isMulti() ? "L2" : "single_line");
                pre_height = pre_height + (parameter.isMulti() ? 220 : 40) + 5;
                nameHash.put(parameter.getName(), cellEditor_0);
            }
        }
        jpanel1.setPreferredSize(new java.awt.Dimension(220, pre_height));
        return pnl;
    }

    public static void exportExcel(final PreviewPane previewPane, final JButton btnExport) {
        JPopupMenu exportMenu = new JPopupMenu();
        exportMenu.add(new JMenuItem(new AbstractAction("PDF", ImageUtil.getIcon("doc_pdf.png")) {

            @Override
            public void actionPerformed(ActionEvent e) {
                exportData("pdf", btnExport, previewPane);
            }
        }));
        exportMenu.add(new JMenuItem(new AbstractAction("Excel", ImageUtil.getIcon("page_white_excel.png")) {

            @Override
            public void actionPerformed(ActionEvent e) {
                exportData("xls", btnExport, previewPane);
            }
        }));
        exportMenu.add(new JMenuItem(new AbstractAction("Word", ImageUtil.getIcon("page_white_word.png")) {

            @Override
            public void actionPerformed(ActionEvent e) {
                exportData("doc", btnExport, previewPane);
            }
        }));
        exportMenu.show(btnExport, 0, 20);
    }

    /**
     * 该方法为导出主函数，通过传入导出类型参数type和报表参数导出相应文档
     * @param type：导出类型：pdf;doc;xls
     * @param btnExport:导出窗口依赖控件
     * @param previewPane：报表参数
     */
    private static void exportData(final String type, JComponent btnExport, final PreviewPane previewPane) {
        File file = FileChooserUtil.getFileForExport(CommMsg.SELECTFILE_MESSAGE, type);
        if (file == null) {
            return;
        }
        final String file_name = file.getPath();
        ShowProcessDlg.startProcess(JOptionPane.getFrameForComponent(previewPane), "正在导出数据，请稍后...");
        Runnable run = new Runnable() {

            @Override
            public void run() {
                try {
                    Exporter exporter = new PDFExporter();
                    if (type.equals("xls")) {
                        exporter = new ExcelExporter();
                    } else if (type.equals("doc")) {
                        exporter = new WordExporter();
                    }
                    ReportPanel.enable_locate_dict = false;
                    FileOutputStream fileOutputStream = new FileOutputStream(file_name);
                    if (ReportPanel.excuteMod == ReportPanel.designMod) {
                        //设计模式导出
                        int pageNum = previewPane.getReportPageSet().size();
                        WorkBook workbook = new WorkBook();
                        ParameterReport workSheet = new ParameterReport();
                        int row_offset = 0;
                        for (int j = 0; j < pageNum; j++) {
                            row_offset = ReportPanel.createWorkSheet(previewPane, j, workSheet, row_offset);
                        }
                        workbook.addReport(workSheet);
                        exporter.export(fileOutputStream, workbook);
                    } else {
                        exporter.export(fileOutputStream, previewPane.currentTemplate.execute(previewPane.parameterMap));
                    }
                    fileOutputStream.close();
                    ShowProcessDlg.endProcess();
                    JOptionPane.showMessageDialog(
                            null, "导出成功");
                } catch (Exception ex) {
                    ShowProcessDlg.endProcess();
                    JOptionPane.showMessageDialog(null, "导出失败，错误原因：" + ex.getMessage());
                    HrLog.error(ReportUtil.class, ex);
                } finally {
                    ReportPanel.enable_locate_dict = true;
                }
            }
        };
        new Thread(run).start();
    }

    public static Parameter[] getReportParameters(ReportDef reportDef) {
        byte[] imageBytes = ReportImpl.getReport_cpt(reportDef.getReportDef_key());
        if (imageBytes == null) {
            return null;
        }
        ReportPanel.enable_locate_dict = false;
        InputStream is = new ByteArrayInputStream(imageBytes);
        TemplateImporter templateImporter = new TemplateImporter();
        try {
            WorkBook workSheet = templateImporter.generate(is);
            Parameter[] ps = workSheet.getParameters();
            return ps;
        } catch (Exception ex) {
            HrLog.error(ReportUtil.class, ex);
        } finally {
            ReportPanel.enable_locate_dict = true;
        }
        return null;
    }

    public static Hashtable<String, ReportParaSet> initReportNoParameter(ReportDef reportDef, Parameter[] ps) {
        List paraFields = CommUtil.fetchEntities("from ReportParaSet pps where pps.reportDef_key='" + reportDef.getReportDef_key() + "'");
        Hashtable<String, ReportParaSet> paraKeys = new Hashtable<String, ReportParaSet>();
        HashSet<String> paraFieldKeys = new HashSet<String>();
        for (Object obj : paraFields) {
            ReportParaSet pps = (ReportParaSet) obj;
            paraKeys.put(pps.getParaname(), pps);
            paraFieldKeys.add(pps.getParafield());
        }
        for (Parameter para : ps) {
            ReportParaSet pps = paraKeys.get(para.getName());
            String field = "";
            if (pps == null) {
                pps = new ReportParaSet();
                pps.setReportParaSet_key(reportDef.getReportDef_key() + "_" + para);
                pps.setReportDef_key(reportDef.getReportDef_key());
                for (int i = 1; i <= 5; i++) {
                    field = "para" + i;
                    if (!paraFieldKeys.contains(field)) {
                        paraFieldKeys.add(field);
                        break;
                    }
                }
                if (field.equals("")) {
                    continue;
                }
                pps.setParaname(para.getName());
                pps.setParafield(field);
                CommUtil.saveOrUpdate(pps);
            }
        }
        return paraKeys;
    }

    public static void initReport(ReportDef reportDef) {
        if (reportDef == null) {
            return;
        }
        Object obj = CommUtil.fetchEntityBy("select rd.report_name,rd.report_code from ReportDef rd where rd.reportDef_key='" + reportDef.getReportDef_key() + "'");
        Object[] objs = (Object[]) obj;
        reportDef.setReport_name(objs[0].toString());
        reportDef.setReport_code(objs[1] == null ? "" : objs[1].toString());
    }

    public static void initReportParameter(HashMap parameter, ReportNo rn) {
        if (rn == null || rn.getReportNo_key() == null || rn.getReportDef_key() == null) {
            return;
        }
        List list = CommUtil.fetchEntities("from ReportParaSet rps where rps.reportDef_key='" + rn.getReportDef_key() + "'");
        for (Object obj : list) {
            ReportParaSet paraSet = (ReportParaSet) obj;
            String pName = paraSet.getParaname();
            String pField = paraSet.getParafield();
            if (pField == null || pField.trim().equals("")) {
                continue;
            }
            pField = pField.replace("@", "").replace("部门", "dept_code2").replace("年/月/季度", "ym");
            Object pValue = PublicUtil.getProperty(rn, pField);
            if (pValue == null) {
                continue;
            }
            parameter.put(pName, pValue);
        }
    }

    public static List<ReportGroup> getReportGroups() {
        String hql = "from ReportGroup rg";
        if (!UserContext.isSA) {
            hql += " where exists(select 1 from ReportDept rd where rg.reportGroup_key=rd.reportGroup_key and (rd.suserNo='" + UserContext.person_code + "' or rd.tuserNo='" + UserContext.person_code + "')) or exists(select 1 from ReportNo rn where rn.reportGroup_key=rg.reportGroup_key and  buserNo='" + UserContext.person_code + "')";
        }
        hql += " order by r_type";
        List list = CommUtil.fetchEntities(hql);
        return list;
    }

    public static void readXlsScheme(ReportXlsScheme scheme) {
        if (scheme == null || scheme.getNew_flag() == 1) {
            return;
        }
        String schemeKey = scheme.getReportXlsScheme_key();
        String hql = "from ReportXlsDetail f where  f.reportXlsScheme.reportXlsScheme_key ='"
                + schemeKey + "'  order by f.order_no ";
        Set details = new HashSet(CommUtil.fetchEntities(hql));
        scheme.setReportXlsDetails(details);
        hql = "from ReportXlsCondition t where t.reportXlsScheme.reportXlsScheme_key='"
                + schemeKey + "'   order by t.order_no";
        Set conditons = new HashSet(CommUtil.fetchEntities(hql));
        scheme.setReportXlsConditions(conditons);
    }

    public static List<ReportXlsCondition> getConditions(ReportXlsScheme scheme, String type) {
        List<ReportXlsCondition> cons = new ArrayList<ReportXlsCondition>();
        for (ReportXlsCondition con : scheme.getReportXlsConditions()) {
            if (type.equals(con.getCondition_type())) {
                cons.add(con);
            }
        }
        return cons;
    }

    public static void exportExcel(ReportXlsScheme scheme, boolean open, JFrame jf) {
        FillDialog dlg = new FillDialog(scheme);
        ContextManager.locateOnMainScreenCenter(dlg);
        dlg.setVisible(true);
        if (!dlg.isClick_ok()) {
            return;
        }
        String s_where = dlg.getCon_sql();
        List<ReportXlsCondition> orders = ReportUtil.getConditions(scheme, "ORDER");
        //再导出数据      
        File file = FileChooserUtil.getXLSExportFile(CommMsg.SELECTXLSFILE_MESSAGE);
        if (file == null) {
            return;
        }
        String ordersql = "";
        String order = "";
        String field = "";
        for (ReportXlsCondition tab : orders) {
            field = " " + tab.getCol() + " ";
            order = tab.getOrder_type().equals("升序") ? " ASC " : " DESC ";
            ordersql += field + order + " ,";
        }
        if (ordersql.trim().length() > 0) {
            ordersql = ordersql.substring(0, ordersql.length() - 1);
            s_where += "  ORDER BY " + ordersql;
        }
        ReportUtil.exportExcel(jf, file, false, s_where, scheme, open);
    }

    /**
     *
     *导出模板
     * 
     */
    public static void exportExcel(final JFrame jf, final File selectedFile, final boolean needNo, final String sql, final ReportXlsScheme scheme, final boolean open) {
        ShowProcessDlg.startProcess(jf);
        jf.setEnabled(false);
        final String title = scheme.getScheme_title();
        final List<ReportXlsDetail> details = new ArrayList();
        String key = "";
        for (ReportXlsDetail rd : scheme.getReportXlsDetails()) {
            if (rd.isUsed()) {
                details.add(rd);
            }
            if (rd.isId_flag()) {
                key += rd.getCol() + "#";
            }
        }
        SysUtil.sortListByInteger(details, "order_no");
        final String key1 = key;
        Runnable run = new Runnable() {

            @Override
            public void run() {
                boolean isopen = false;
                try {
                    File file = null;
                    String file_path = selectedFile.getPath();
                    if (!file_path.toLowerCase().endsWith(".xls") && !file_path.toLowerCase().endsWith(".xlsx")) {
                        file_path = file_path + ".xls";
                    }
                    file = new File(file_path);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    Hashtable<String, WritableCellFormat> commFormats = ExportUtil.getCommCellFormats();
                    WritableWorkbook workbook = Workbook.createWorkbook(file);
                    WritableSheet sheet = workbook.createSheet("First Sheet", 0);
                    Label label = new Label(0, 0, title, commFormats.get("title"));
                    WritableCellFeatures cellFeatures = new WritableCellFeatures();
                    cellFeatures.setComment(scheme.getEntity_name() + "@" + key1);
                    label.setCellFeatures(cellFeatures);
                    sheet.addCell(label);
                    sheet.mergeCells(0, 0, Math.max(details.size() - (needNo ? 0 : 1), 0), 0);//list size
                    int j = 0;
                    if (needNo) {
                        label = new Label(j, 1, "序号", commFormats.get("title1"));
                        cellFeatures = new WritableCellFeatures();
                        cellFeatures.setComment("序号");
                        label.setCellFeatures(cellFeatures);
                        sheet.addCell(label);
                        j++;
                    }
                    String strSql = "SELECT ";

                    for (ReportXlsDetail rd : details) {
                        sheet.setColumnView(j, 120 / 5);
                        if (null == rd.getCol_name() || rd.getCol_name().trim().length() == 0) {  //如果有别名则显示别名,没有则显示列名
                            label = new Label(j, 1, rd.getCol(), commFormats.get("title1"));
                        } else {
                            label = new Label(j, 1, rd.getCol_name(), commFormats.get("title1"));
                        }
                        cellFeatures = new WritableCellFeatures();
                        cellFeatures.setComment(rd.getCol() + "#" + rd.getCol_type() + "#"
                                + rd.getCol_len() + "#" + rd.getCol_scale());
                        label.setCellFeatures(cellFeatures);
                        sheet.addCell(label);
                        j++;
                        strSql = strSql + " " + rd.getCol() + " ,";
                    }

                    strSql = strSql.substring(0, strSql.length() - 1) + " FROM " + scheme.getEntity_name() + " WHERE 1=1 " + sql;
                    //拼接sql 
                    List<Object[]> list = (List<Object[]>) CommUtil.selectSQL(strSql);
                    int i = 2;     //excel第三行
                    String type = "";

                    WritableCellFormat dateFormat = new WritableCellFormat(new DateFormat("yyyy-MM-dd"));
                    dateFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
                    WritableCellFormat timeFormat = new WritableCellFormat(new DateFormat("HH:mm:ss"));
                    timeFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
                    WritableCellFormat stampFormat = new WritableCellFormat(new DateFormat("yyyy-MM-dd HH:mm:ss"));
                    stampFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
                    WritableCellFormat wcfN = new WritableCellFormat(NumberFormats.TEXT);
                    wcfN.setBorder(Border.ALL, BorderLineStyle.THIN);

                    for (Object[] obj : list) {
                        WritableCell cell = null;
                        int col = 0;
                        for (int k = 0; k < obj.length; k++) {
                            ReportXlsDetail tab = (ReportXlsDetail) details.get(k);
                            type = tab.getCol_type();
                            if (null != obj[k] && type.equals("DATE")) {
                                cell = new jxl.write.DateTime(col, i, (Date) obj[k], dateFormat);
                            } else if (null != obj[k] && type.equals("TIME")) {
                                cell = new jxl.write.DateTime(col, i, (Date) obj[k], timeFormat);
                            } else if (null != obj[k] && type.equals("TIMESTAMP")) {
                                cell = new jxl.write.DateTime(col, i, (Date) obj[k], stampFormat);
                            } else if (null != obj[k] && type.equals("NUMBER")) {
                                label = new Label(col, i, obj[k] == null ? "" : obj[k].toString(), wcfN);
                            } else {
                                label = new Label(col, i, obj[k] == null ? "" : obj[k].toString(), commFormats.get("text"));
                            }
                            if (cell != null) {
                                sheet.addCell(cell);
                            } else if (label != null) {
                                sheet.addCell(label);
                            }
                            col++;
                            cell = null;
                            label = null;
                        }
                        i++;
                    }
                    workbook.write();
                    workbook.close();
                    if (open) {
                        Runtime.getRuntime().exec("cmd /c \"" + file_path + "\"");
                    }
                } catch (Exception ex) {
                    if (ex instanceof FileNotFoundException) {
                        isopen = true;
                    }
                    log.error(ex);
                } finally {
                    ShowProcessDlg.endProcess();
                    jf.setEnabled(true);
                    jf.setVisible(true);
                    if (isopen) {
                        JOptionPane.showMessageDialog(null, "另一个程序正在使用此文件，进程无法访问", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
        new Thread(run).start();
    }

    public static HashMap getReportParaMap(List list) {
        HashMap hm = new HashMap();
        if (list != null && list.size() > 0) {
            List<String> a0190s = new ArrayList<String>();
            List<String> wfNos = new ArrayList<String>();
            List<String> daNos = new ArrayList<String>();
            List<String> keys = new ArrayList<String>();
            Class c = list.get(0).getClass();
            Hashtable<String, String> fields = new Hashtable<String, String>();
            String[] paraFields = reportParaFields.get(c.getSimpleName());
            if (paraFields != null) {
                for (String f : paraFields) {
                    TempFieldInfo tfi = EntityBuilder.getTempFieldInfoByName(c.getName(), f, true);
                    if (tfi != null) {
                        fields.put(f, tfi.getCaption_name());
                    }
                }
            }
            if (c.getSuperclass().getName().startsWith("org.jhrcore.entity")) {
                c = c.getSuperclass();
                paraFields = reportParaFields.get(c.getSimpleName());
                if (paraFields != null) {
                    for (String f : paraFields) {
                        TempFieldInfo tfi = EntityBuilder.getTempFieldInfoByName(c.getName(), f, true);
                        if (tfi != null) {
                            fields.put(f, tfi.getCaption_name());
                        }
                    }
                }
            }
            fields.put(EntityBuilder.getEntityKey(c), "key");
            for (String field : fields.keySet()) {
                List<String> datas = new ArrayList();
                for (Object obj : list) {
                    Object data = PublicUtil.getProperty(obj, field);
                    if (data == null) {
                        continue;
                    }
                    datas.add(data + "");
                }
                hm.put(fields.get(field), datas.toArray());
            }
        }
        return hm;
    }

    public static List getCommReportList(String module_code) {
        module_code = module_code.replace(";", "','");
        List list = CommUtil.selectSQL("select rd.reportDef_key,rd.report_class,rd.report_name from ReportDef rd,ReportModule rm where rd.reportDef_key=rm.reportDef_key and rm.code='comm' and rm.module_flag='" + module_code + "' order by rd.order_no");

        ModuleInfo module = new ModuleInfo();
        module.setModule_code(module_code);
        module.setModule_name("所有报表");
        List<ReportDef> data = new ArrayList<ReportDef>();
        for (Object obj : list) {
            Object[] objs = (Object[]) obj;
            ReportDef rd = new ReportDef();
            rd.setReportDef_key(objs[0].toString());
            rd.setReport_class(objs[1] == null ? "" : objs[1].toString());
            rd.setReport_name(objs[2].toString());
            rd.setModuleInfo(module);
            data.add(rd);
        }
        return data;
    }

    public static List getWorkFlowReportList(String workFlowDef_key) {
        List list = CommUtil.selectSQL("select rd.reportDef_key,rd.report_class,rd.report_name from ReportDef rd,WfReport rm where rd.reportDef_key=rm.reportDef_key and rm.workflowdef_key='" + workFlowDef_key + "' order by rd.order_no");
        ModuleInfo module = new ModuleInfo();
        module.setModule_name("所有报表");
        List<ReportDef> data = new ArrayList<ReportDef>();
        for (Object obj : list) {
            Object[] objs = (Object[]) obj;
            ReportDef rd = new ReportDef();
            rd.setReportDef_key(objs[0].toString());
            rd.setReport_class(objs[1] == null ? "" : objs[1].toString());
            rd.setReport_name(objs[2].toString());
            rd.setModuleInfo(module);
            data.add(rd);
        }
        return data;
    }

    public static void buildWorkFlowReportMenu(AbstractButton c, String workFlowDef_key, HashMap hm) {
        List data = getWorkFlowReportList(workFlowDef_key);
        if (data.isEmpty()) {
            return;
        }
        Hashtable<String, List<ReportDef>> reportKeys = new Hashtable<String, List<ReportDef>>();
        for (Object obj : data) {
            ReportDef rd = (ReportDef) obj;
            if (UserContext.hasReportRight(rd.getReportDef_key())) {
                String key = (rd.getReport_class() == null || rd.getReport_class().trim().equals("")) ? "" : rd.getReport_class();
                List<ReportDef> reports = reportKeys.get(key);
                if (reports == null) {
                    reports = new ArrayList<ReportDef>();
                }
                reports.add(rd);
                reportKeys.put(key, reports);
            }
        }
        if (reportKeys.keySet().size() > 0) {
            final JPopupMenu pm = new JPopupMenu();
            Enumeration enumt = reportKeys.keys();
            while (enumt.hasMoreElements()) {
                List<ReportDef> reports = (List<ReportDef>) reportKeys.get(enumt.nextElement().toString());
                ReportDef rd = reports.get(0);
                String key = (rd.getReport_class() == null || rd.getReport_class().trim().equals("")) ? "" : rd.getReport_class();
                if (key.equals("")) {
                    for (ReportDef report : reports) {
                        JMenuItem mi = new JMenuItem(report.getReport_name());
                        pm.add(mi);
                        buildReportItemAction(c, mi, report, hm);
                        ComponentUtil.setIcon(mi, "report");
                    }
                } else {
                    JMenu mm = new JMenu(key);
                    for (ReportDef report : reports) {
                        JMenuItem mi = new JMenuItem(report.getReport_name());
                        mm.add(mi);
                        buildReportItemAction(c, mi, report, hm);
                        ComponentUtil.setIcon(mi, "report");
                    }
                    pm.add(mm);
                    ComponentUtil.setIcon(mm, "blank");
                }
            }
            pm.show(c, 0, 30);
        }
    }

    public static void buildCommReportMenu(AbstractButton c, String module_code, HashMap hm) {
        module_code = module_code.replace(";", "','");
        List data = getCommReportList(module_code);
        if (data.isEmpty()) {
            return;
        }
        Hashtable<String, List<ReportDef>> reportKeys = new Hashtable<String, List<ReportDef>>();
        for (Object obj : data) {
            ReportDef rd = (ReportDef) obj;
            if (UserContext.hasReportRight(rd.getReportDef_key())) {
                String key = (rd.getReport_class() == null || rd.getReport_class().trim().equals("")) ? "" : rd.getReport_class();
                List<ReportDef> reports = reportKeys.get(key);
                if (reports == null) {
                    reports = new ArrayList<ReportDef>();
                }
                reports.add(rd);
                reportKeys.put(key, reports);
            }
        }
        if (reportKeys.keySet().size() > 0) {
            final JPopupMenu pm = new JPopupMenu();
            Enumeration enumt = reportKeys.keys();
            while (enumt.hasMoreElements()) {
                List<ReportDef> reports = (List<ReportDef>) reportKeys.get(enumt.nextElement().toString());
                ReportDef rd = reports.get(0);
                String key = (rd.getReport_class() == null || rd.getReport_class().trim().equals("")) ? "" : rd.getReport_class();
                if (key.equals("")) {
                    for (ReportDef report : reports) {
                        JMenuItem mi = new JMenuItem(report.getReport_name());
                        pm.add(mi);
                        buildReportItemAction(c, mi, report, hm);
                        ComponentUtil.setIcon(mi, "report");
                    }
                } else {
                    JMenu mm = new JMenu(key);
                    for (ReportDef report : reports) {
                        JMenuItem mi = new JMenuItem(report.getReport_name());
                        mm.add(mi);
                        buildReportItemAction(c, mi, report, hm);
                        ComponentUtil.setIcon(mi, "report");
                    }
                    pm.add(mm);
                    ComponentUtil.setIcon(mm, "blank");
                }
            }
            pm.show(c, 0, 30);
        }
    }

    private static void buildReportItemAction(AbstractButton c, JMenuItem mi, ReportDef rd, HashMap hm) {
        final HashMap hm1 = hm;
        final ReportDef rd1 = rd;
        final AbstractButton c1 = c;
        mi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ReportPanel.excute_report((JFrame) JOptionPane.getFrameForComponent(c1), rd1, hm1);
            }
        });
    }
}
