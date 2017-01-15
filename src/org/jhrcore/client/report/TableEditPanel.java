/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TableEditPanel.java
 *
 * Created on 2011-6-26, 15:48:33
 */
package org.jhrcore.client.report;

import com.fr.base.BaseUtils;
import com.fr.base.FRContext;
import com.fr.base.FRFont;
import com.fr.base.Style;
import com.fr.base.dav.Env;
import com.fr.base.dav.LocalEnv;
import com.fr.cell.GridSelection;
import com.fr.cell.event.GridSelectionChangeEvent;
import com.fr.cell.event.GridSelectionChangeListener;
import com.fr.design.mainframe.DesignParameterJWorkSheet;
import com.fr.dialog.PageSetupDialog;
import com.fr.report.CellElement;
import com.fr.report.ParameterReport;
import com.fr.report.ReportSettings;
import com.fr.report.WorkBook;
import com.fr.report.io.TemplateExporter;
import com.fr.report.io.TemplateImporter;
import com.fr.report.painter.BiasTextPainter;
import com.fr.view.PreviewPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.FontUIResource;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.SysParameter;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.iservice.impl.ReportImpl;
import org.jhrcore.mutil.ReportUtil;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.ui.ModelFrame;
import org.jhrcore.util.ImageUtil;
import org.jhrcore.util.MsgUtil;
import org.jhrcore.util.ObjectToXMLUtil;

/**
 *
 * @author Administrator
 */
public class TableEditPanel extends javax.swing.JPanel {

    private ReportTableModel model;
    private List list_hengxiang;
    private List list_zongxiang;
    private DesignParameterJWorkSheet jWorkSheet;
    private ParameterReport workSheet;
    private int begin_row = 0;// 开始显示题头的行
    private String gridid = null;
    private String gridid2 = null;
    private String bias_str = null;

    public TableEditPanel(String gridid, ReportTableModel model, List<String> list_hengxiang, List<String> list_zongxiang) {
        this();
        this.gridid = gridid;
        initTable(model, list_hengxiang, list_zongxiang);
    }

    public TableEditPanel(String gridid, ReportTableModel model, List<String> list_hengxiang, List<String> list_zongxiang, String title) {
        this();
        this.gridid = gridid;
        begin_row = 2;    //第一行显示大标题，所以网格从第三行开始
        initTable(model, list_hengxiang, list_zongxiang);

        // 显示大标题-->>
        workSheet.merge(0, 0, 0, model.getColumnCount() + list_zongxiang.size());
        workSheet.setCellValue(0, 0, title);
        Style style_center = Style.DEFAULT_STYLE;
        try {
            style_center = (Style) Style.DEFAULT_STYLE.clone();
            style_center = style_center.deriveHorizontalAlignment(0);
        } catch (CloneNotSupportedException ex) {
        }
        FRFont font = FRFont.getInstance(new FontUIResource("宋体", Font.BOLD, 18));
        style_center = style_center.deriveFRFont(font);
        workSheet.getCellElement(0, 0).setStyle(style_center);
        // 显示大标题--<<
    }

    public TableEditPanel(String gridid, String gridid2, String bias_str, ReportTableModel model, List<String> list_hengxiang, List<String> list_zongxiang, String title) {
        this();
        this.gridid = gridid;
        this.gridid2 = gridid2;
        this.bias_str = bias_str;
        begin_row = 2;    //第一行显示大标题，所以网格从第三行开始
        initTable(model, list_hengxiang, list_zongxiang);

        // 显示大标题-->>
        workSheet.merge(0, 0, 0, model.getColumnCount() + list_zongxiang.size());
        workSheet.setCellValue(0, 0, title);
        Style style_center = Style.DEFAULT_STYLE;
        try {
            style_center = (Style) Style.DEFAULT_STYLE.clone();
            style_center = style_center.deriveHorizontalAlignment(0);
        } catch (CloneNotSupportedException ex) {
        }
        FRFont font = FRFont.getInstance(new FontUIResource("宋体", Font.BOLD, 18));
        style_center = style_center.deriveFRFont(font);
        workSheet.getCellElement(0, 0).setStyle(style_center);
        // 显示大标题--<<
    }

    // model getRowName 和 getColumnName 返回的题头以|分开，list_hengxiang、list_zongxiang包含左上角的标题
    public TableEditPanel(ReportTableModel model, List<String> list_hengxiang, List<String> list_zongxiang) {
        this();
        initTable(model, list_hengxiang, list_zongxiang);
    }

    public TableEditPanel(ReportTableModel model, List<String> list_hengxiang, List<String> list_zongxiang, String title) {
        this();
        begin_row = 2;    //第一行显示大标题，所以网格从第三行开始
        initTable(model, list_hengxiang, list_zongxiang);

        // 显示大标题-->>
        workSheet.merge(0, 0, 0, model.getColumnCount() + list_zongxiang.size());
        workSheet.setCellValue(0, 0, title);
        Style style_center = Style.DEFAULT_STYLE;
        try {
            style_center = (Style) Style.DEFAULT_STYLE.clone();
            style_center = style_center.deriveHorizontalAlignment(0);
        } catch (CloneNotSupportedException ex) {
        }
        FRFont font = FRFont.getInstance(new FontUIResource("宋体", Font.BOLD, 18));
        style_center = style_center.deriveFRFont(font);
        workSheet.getCellElement(0, 0).setStyle(style_center);
        // 显示大标题--<<
    }

    private void initTable(ReportTableModel model, List<String> list_hengxiang, List<String> list_zongxiang) {
        this.model = model;
        this.list_hengxiang = list_hengxiang;
        this.list_zongxiang = list_zongxiang;
        createReport();
        createToolBar();
        initStyle();

        ReportSettings rs = getReortSettings();
        if (rs != null) {
            workSheet.setReportSettings(rs);
        }

        if (gridid == null) {
            return;
        }
        SysParameter sp = (SysParameter) CommUtil.fetchEntityBy("from SysParameter sp where sp.sysParameter_key='" + gridid + ".row_heights" + "'");
        if (sp != null) {
            String row_heights = sp.getSysparameter_value();
            String[] rowWidths = row_heights.split(";");
            for (int row = 0; row < rowWidths.length; row++) {
                workSheet.setRowHeight(row, Integer.valueOf(rowWidths[row]));
            }
        }


        sp = (SysParameter) CommUtil.fetchEntityBy("from SysParameter sp where sp.sysParameter_key='" + gridid + ".col_widths" + "'");
        if (sp != null) {
            String col_widths = sp.getSysparameter_value();
            String[] colWidths = col_widths.split(";");
            for (int col = 0; col < colWidths.length; col++) {
                workSheet.setColumnWidth(col, Integer.valueOf(colWidths[col]));
            }
        }

//        sp = (SysParameter)CommUtil.fetchEntityBy("from SysParameter sp where sp.sysParameter_key='" + gridid + ".style_h" + "'");
//        if (sp != null){
//            String col_widths = sp.getSysparameter_value();
//            String[] colWidths = col_widths.split(";");
//            for (int col = 0; col < colWidths.length; col++)
//                workSheet.setColumnWidth(col, Integer.valueOf(colWidths[col]));    
//        }

        sp = (SysParameter) CommUtil.fetchEntityBy("from SysParameter sp where sp.sysParameter_key='" + gridid + ".style_v" + "'");
        if (sp != null) {
            String col_vs = sp.getSysparameter_value();
            String[] colVs = col_vs.split(";");
            Style style_center = Style.BORDER_STYLE;
            style_center = style_center.deriveHorizontalAlignment(0);
            Style style_left = style_center.deriveHorizontalAlignment(2);
            Style style_right = style_center.deriveHorizontalAlignment(4);
            for (int rownum = 0; rownum < model.getRowCount(); rownum++) {
                for (int colnum = 0; colnum < colVs.length; colnum++) {
                    CellElement ce = workSheet.getCellElement(colnum + 1, begin_row + rownum + list_hengxiang.size()/*行*/);
                    System.out.println("ce:" + ce);
                    if (ce == null) {
                        continue;
                    }
                    if (Integer.valueOf(colVs[colnum]) == 0) {
                        ce.setStyle(style_center);
                    } else if (Integer.valueOf(colVs[colnum]) == 2) {
                        ce.setStyle(style_left);
                    } else if (Integer.valueOf(colVs[colnum]) == 4) {
                        ce.setStyle(style_right);
                    }
                }
            }
        }
    }

    /** Creates new form TableEditPanel */
    public TableEditPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private void createReport() {
        this.removeAll();

        workSheet = new ParameterReport();
        WorkBook workbook = new WorkBook();
        workbook.addReport(workSheet);
        jWorkSheet = new DesignParameterJWorkSheet(workSheet) {
            
            @Override
            public JPopupMenu createPopupMenu(MouseEvent mouseevent) {
//                return null;
                                          JPopupMenu jpopupmenu2 = new JPopupMenu();
                                          JMenuItem mi = new JMenuItem("剪  切");
                                          mi.addActionListener(new ActionListener(){

                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    jWorkSheet.copy();
                                                    GridSelection gs = jWorkSheet.getGridSelection();
                                                    for (int i = 0; i < gs.getCellRectangleCount(); i++){
                                                        Rectangle rect = gs.getCellRectangle(i);
                                                        for (int row = 0; row < rect.height; row++){
                                                            for (int col = 0; col < rect.width; col++){
                                                                workSheet.setCellValue(row + rect.x, col + rect.y, null);
                                                            }
                                                        }
                                                    }
                                                    jWorkSheet.updateUI();
                                                }
                                              
                                          });
                                          jpopupmenu2.add(mi);
                                          
                                          mi = new JMenuItem("拷  贝");
                                          mi.addActionListener(new ActionListener(){

                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    jWorkSheet.copy();
                                                }
                                              
                                          });
                                          jpopupmenu2.add(mi);
                                          
                                          mi = new JMenuItem("粘  贴");
                                          mi.addActionListener(new ActionListener(){

                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    jWorkSheet.paste();
                                                    jWorkSheet.updateUI();
                                                }
                                              
                                          });
                                          jpopupmenu2.add(mi);
                                          return jpopupmenu2;
            }
        };
        
        Style style_center = Style.BORDER_STYLE;
        try {
            style_center = (Style) Style.BORDER_STYLE.clone();
            style_center = style_center.deriveHorizontalAlignment(0);
        } catch (CloneNotSupportedException ex) {
        }
        Style style_left = style_center.deriveHorizontalAlignment(2);
        Style style_right = style_center.deriveHorizontalAlignment(4);
        list_hengxiang.size();

        // 斜线类型->
        workSheet.merge(begin_row, begin_row + list_hengxiang.size() - 1, 1, list_zongxiang.size());
        String content = "";
        for (int i = 0; i < list_hengxiang.size(); i++) {
            if (i != 0) {
                content = content + "|";
            }
            content = content + list_hengxiang.get(i);
        }
        for (int i = list_zongxiang.size() - 1; i >= 0; i--) {
            content = content + "|";
            content = content + list_zongxiang.get(i);
        }
        if (workSheet.getCellElement(1, begin_row) != null) {
            if (bias_str == null || bias_str.trim().length() == 0) {
                workSheet.getCellElement(1, begin_row).setValue(new BiasTextPainter(content));
                workSheet.getCellElement(1, begin_row).setStyle(style_right);
            } else {
                workSheet.getCellElement(1, begin_row).setValue(bias_str);
                workSheet.getCellElement(1, begin_row).setStyle(style_center);
            }
        }
        // 斜线类型<-

        // 序号列->
        //workSheet.merge(row_num, row_num, col_num, col_num2);
        workSheet.merge(begin_row, begin_row + list_hengxiang.size() - 1, 0, 0);
        workSheet.setCellValue(0/*列*/, begin_row/*行*/, "序号");
        workSheet.getCellElement(0, begin_row).setStyle(style_center);
        for (int i = 1; i <= model.getRowCount(); i++) {
            workSheet.setCellValue(0/*列*/, begin_row + list_hengxiang.size() - 1 + i/*行*/, "" + i);
            workSheet.getCellElement(0, begin_row + list_hengxiang.size() - 1 + i).setStyle(style_center);
        }
        // 序号列<-

        // 行题头->
        for (int rownum = 0; rownum < model.getRowCount(); rownum++) {
            String rowtitle = model.getRowName(rownum);
            rowtitle = rowtitle == null ? "" : rowtitle;
            String[] titles = rowtitle.split("\\|");
            for (int i = 1; i <= list_zongxiang.size(); i++) {
                String title = (titles.length >= i) ? titles[i - 1] : "";
                workSheet.setCellValue(i/*列*/, begin_row + list_hengxiang.size() + rownum/*行*/, title);
                workSheet.getCellElement(i, begin_row + list_hengxiang.size() + rownum).setStyle(style_center);
            }
        }
        // 行题头<-

        // 合并行题头->
        for (int col_num = 1; col_num < (1 + list_zongxiang.size()); col_num++) {
            int row_num = begin_row + list_hengxiang.size();
            while (row_num < (model.getRowCount() + begin_row + list_hengxiang.size())) {
                int row_num2 = row_num;
                int count = 0;
                Object val1 = workSheet.getCellElement(col_num, row_num).getValue();
                if (val1 != null) {
                    while (row_num2 < (model.getRowCount() + begin_row + list_hengxiang.size())) {
                        Object val2 = workSheet.getCellElement(col_num, row_num2).getValue();
                        if (!val1.equals(val2)) {
                            break;
                        }
                        row_num2++;
                        count++;
                    }
                }
                System.out.println("row_num:" + row_num);
                System.out.println("count:" + count);
                System.out.println("row_num2:" + row_num2);
                if (row_num2 >= (row_num + 1)) {
                    // 检查一下这两个格子的父格子内容是否一致，如果不一致不合并
                    if (col_num == 1) {
                        workSheet.merge(row_num, row_num2 - 1, col_num, col_num);
                    } else {
                        CellElement ce1 = workSheet.getCellElement(col_num - 1, row_num);
                        CellElement ce2 = workSheet.getCellElement(col_num - 1, count > 1 ? (row_num2 - 1) : row_num2);
                        Object tmp_val1 = "";
                        if (ce1 != null && ce1.getValue() != null) {
                            tmp_val1 = ce1.getValue();
                        }
                        Object tmp_val2 = "";
                        if (ce2 != null && ce2.getValue() != null) {
                            tmp_val2 = ce2.getValue();
                        }
                        System.out.println("tmp_val1:" + tmp_val1 + " tmp_val2:" + tmp_val2);
                        if (tmp_val1.equals(tmp_val2)) {
                            workSheet.merge(row_num, row_num2 - 1, col_num, col_num);
                        }
                    }
                }
                row_num = row_num2;
            }
        }
        // 合并行题头<-

        // 列题头->
        for (int colnum = 0; colnum < model.getColumnCount(); colnum++) {
            String coltitle = model.getColumnName(colnum);
            coltitle = coltitle == null ? "" : coltitle;
            String[] titles = coltitle.split("\\|");
            for (int i = 1; i <= list_hengxiang.size(); i++) {
                String title = (titles.length >= i) ? titles[i - 1] : "";
                workSheet.setCellValue(colnum + 1 + list_zongxiang.size()/*列*/, begin_row + i - 1/*行*/, title);
                workSheet.getCellElement(colnum + 1 + list_zongxiang.size()/*列*/, begin_row + i - 1/*行*/).setStyle(style_center);
            }
        }
        // 列题头<-

        // 合并列题头->
        for (int row_num = begin_row; row_num < (begin_row + list_hengxiang.size()); row_num++) {
            int col_num = 1 + list_zongxiang.size();
            while (col_num < (model.getColumnCount() + 1 + list_zongxiang.size())) {
                int col_num2 = col_num;
                Object val1 = workSheet.getCellElement(col_num, row_num).getValue();
                Object val2 = null;
                if (val1 != null) {
                    while (col_num2 < (model.getColumnCount() + 1 + list_zongxiang.size())) {
                        val2 = workSheet.getCellElement(col_num2, row_num).getValue();
                        if (!val1.equals(val2)) {
                            break;
                        }
                        col_num2++;
                    }
                }
                if (col_num2 > (col_num + 1)) {
                    // 检查一下这两个格子的父格子内容是否一致，如果不一致不合并
                    CellElement ce1 = workSheet.getCellElement(col_num, row_num - 1);
                    CellElement ce2 = workSheet.getCellElement(col_num2, row_num - 1);
                    Object tmp_val1 = "";
                    if (ce1 != null && ce1.getValue() != null) {
                        tmp_val1 = ce1.getValue();
                    }
                    Object tmp_val2 = "";
                    if (ce2 != null && ce2.getValue() != null) {
                        tmp_val2 = ce2.getValue();
                    }

                    if (tmp_val1.equals(tmp_val2)) {
                        workSheet.merge(row_num, row_num, col_num, col_num2 - 1);
                    }

                }
                col_num = col_num2;
            }
        }
        // 合并列题头<-

        // 值->
        for (int rownum = 0; rownum < model.getRowCount(); rownum++) {
            for (int colnum = 0; colnum < model.getColumnCount(); colnum++) {
                Object val = model.getValueAt(rownum, colnum);
                workSheet.setCellValue(colnum + 1 + list_zongxiang.size()/*列*/, begin_row + rownum + list_hengxiang.size()/*行*/, val);
                workSheet.getCellElement(colnum + 1 + list_zongxiang.size()/*列*/, begin_row + rownum + list_hengxiang.size()/*行*/).setStyle(style_left);
            }
        }
        // 值<-
        //reFillData();        
        jWorkSheet.addGridSelectionChangeListener(new GridSelectionChangeListener() {

            @Override
            public void gridSelectionChanged(GridSelectionChangeEvent gsce) {
                GridSelection gs = jWorkSheet.getGridSelection();
                Rectangle rect = gs.getCellRectangle(0);
                System.out.println(workSheet.getCellValue(rect.x, rect.y));
            }
        });
        this.add(jWorkSheet, BorderLayout.CENTER);
    }

    public void addEndRow(String title, String val, int i) {
        int cur_r = begin_row + model.getRowCount() + list_hengxiang.size();
        int r = cur_r + i;
        Style c_style = Style.BORDER_STYLE;
        c_style = c_style.deriveHorizontalAlignment(0);
        Style l_style = c_style.deriveHorizontalAlignment(2);
        workSheet.setRowHeight(r, 90);
        //--------->序号
        workSheet.setCellValue(0, r, (model.getRowCount() + i + 1) + "");
        workSheet.getCellElement(0, r).setStyle(c_style);
        //----------<
        //--------->标题
        workSheet.setCellValue(1, r, title);
        workSheet.getCellElement(1, r).setStyle(c_style);
        //---------<
        //--------->正文
        workSheet.setCellValue(2, r, val);
        workSheet.getCellElement(2, r).setStyle(l_style);
        workSheet.merge(r, r, 2, model.getColumnCount() + list_zongxiang.size());
        //----------<
    }

    public Object getEndRowValue(int i) {
        Object o_val = workSheet.getCellValue(2, begin_row + model.getRowCount() + list_hengxiang.size() + i);
        return o_val;
    }

    private void createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setPreferredSize(new Dimension(toolBar.getWidth(), 25));
        toolBar.setRollover(true);
        JButton btnPrint = new JButton(ImageUtil.getIcon("printer2.png"));
        btnPrint.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                printView(ContextManager.getMainFrame());
            }
        });
        final JButton btnPageSetup = new JButton(BaseUtils.readIcon("/com/fr/design/images/m_file/pageSetup.png"));
        btnPageSetup.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PageSetupDialog localPageSetupDialog = PageSetupDialog.showWindow(SwingUtilities.getWindowAncestor(btnPageSetup));
                localPageSetupDialog.populate(workSheet);
                localPageSetupDialog.setVisible(true);
                int i = localPageSetupDialog.getReturnValue();
                if (i == 0) {
                    localPageSetupDialog.update(workSheet);
                }
                saveReportSetting();
            }
        });
        toolBar.add(btnPrint);
        toolBar.add(btnPageSetup);
        toolBar.addSeparator();
        JButton btnLeft = new JButton(BaseUtils.readIcon("/com/fr/design/images/m_format/cellstyle/leftAlignment.png"));
        btnLeft.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jWorkSheet.getGrid().isEditable()) {
                    return;
                }

                GridSelection gs = jWorkSheet.getGridSelection();
                for (int i = 0; i < gs.getCellRectangleCount(); i++) {
                    Rectangle rect = gs.getCellRectangle(i);
                    for (int row = 0; row < rect.width; row++) {
                        for (int col = 0; col < rect.height; col++) {
                            CellElement element = workSheet.getCellElement(row + rect.x, col + rect.y);
                            if (element == null) {
                                continue;
                            }
                            Style style = element.getStyle();
                            element.setStyle(style.deriveHorizontalAlignment(2));
                        }
                    }
                }
                jWorkSheet.getGrid().repaint();
            }
        });
        JButton btnRight = new JButton(BaseUtils.readIcon("/com/fr/design/images/m_format/cellstyle/rightAlignment.png"));
        btnRight.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jWorkSheet.getGrid().isEditable()) {
                    return;
                }

                GridSelection gs = jWorkSheet.getGridSelection();
                for (int i = 0; i < gs.getCellRectangleCount(); i++) {
                    Rectangle rect = gs.getCellRectangle(i);
                    for (int row = 0; row < rect.width; row++) {
                        for (int col = 0; col < rect.height; col++) {
                            CellElement element = workSheet.getCellElement(row + rect.x, col + rect.y);
                            if (element == null) {
                                continue;
                            }
                            Style style = element.getStyle();
                            element.setStyle(style.deriveHorizontalAlignment(4));
                        }
                    }
                }
                jWorkSheet.getGrid().repaint();
            }
        });
        JButton btnCenter = new JButton(BaseUtils.readIcon("/com/fr/design/images/m_format/cellstyle/centerAlignment.png"));
        btnCenter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jWorkSheet.getGrid().isEditable()) {
                    return;
                }

                GridSelection gs = jWorkSheet.getGridSelection();
                for (int i = 0; i < gs.getCellRectangleCount(); i++) {
                    Rectangle rect = gs.getCellRectangle(i);
                    for (int row = 0; row < rect.width; row++) {
                        for (int col = 0; col < rect.height; col++) {
                            CellElement element = workSheet.getCellElement(row + rect.x, col + rect.y);
                            if (element == null) {
                                continue;
                            }
                            Style style = element.getStyle();
                            element.setStyle(style.deriveHorizontalAlignment(0));
                        }
                    }
                }
                jWorkSheet.getGrid().repaint();
            }
        });
        toolBar.add(btnLeft);
        toolBar.add(btnCenter);
        toolBar.add(btnRight);
        toolBar.addSeparator();
        final JButton btnExport2 = new JButton(ImageUtil.getIcon("table_export.png"));
        btnExport2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                final Env old_env = FRContext.getCurrentEnv();
                LocalEnv localEnv = new LocalEnv(System.getProperty("user.home") + "/exec");
                FRContext.setCurrentEnv(localEnv);

                PreviewPane previewPane = new PreviewPane();
                WorkBook workBook = new WorkBook();
                workBook.addReport(workSheet);
                previewPane.print(workBook);

                ReportUtil.exportExcel(previewPane, btnExport2);

                FRContext.setCurrentEnv(old_env);
            }
        });
        toolBar.add(btnExport2);

        this.add(toolBar, BorderLayout.NORTH);
    }

    private void saveStyle() {
        if (gridid2 == null) {
            return;
        }
        LocalEnv localEnv = new LocalEnv(System.getProperty("user.home"));
        FRContext.setCurrentEnv(localEnv);
        try {
            TemplateExporter templateExporter = new TemplateExporter();
            templateExporter.export(new FileOutputStream(System.getProperty("user.home") + "/" + gridid2 + ".cpt"), jWorkSheet.getEditingReport().getWorkBook());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initStyle() {
        if (gridid2 == null) {
            return;
        }
        LocalEnv localEnv = new LocalEnv(System.getProperty("user.home"));
        FRContext.setCurrentEnv(localEnv);
        try {
            File file = new File(System.getProperty("user.home") + "/" + gridid2 + ".cpt");
            if (!file.exists()) {
                return;
            }
            byte imageBytes[] = new byte[(int) file.length()];
            BufferedInputStream input = new BufferedInputStream(
                    new FileInputStream(file));
            input.read(imageBytes, 0, imageBytes.length);
            input.close();
            InputStream is = new ByteArrayInputStream(imageBytes);
            if (imageBytes == null) {
                return;
            }
            TemplateImporter templateImporter = new TemplateImporter();
            workSheet = (ParameterReport) templateImporter.generate(is).getReport(0);
            jWorkSheet.setEditingReport(workSheet);
            jWorkSheet.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printView(final JFrame parentFrame) {
        final Env old_env = FRContext.getCurrentEnv();
        try {
            LocalEnv localEnv = new LocalEnv(System.getProperty("user.home") + "/exec");
            FRContext.setCurrentEnv(localEnv);

            final PreviewPane previewPane = new PreviewPane();
            WorkBook workBook = new WorkBook();
            workBook.addReport(workSheet);
            previewPane.print(workBook);
            final JButton btnExport = new JButton("导出");
            btnExport.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    ReportUtil.exportExcel(previewPane, btnExport);
                }
            });
            previewPane.getJpanel().add(btnExport);

            JFrame fm2 = ModelFrame.showModel(parentFrame, previewPane, true, "打印预览", ContextManager.getMainFrame().getToolkit().getScreenSize().width - 50, ContextManager.getMainFrame().getToolkit().getScreenSize().height - 50);
            fm2.setExtendedState(JFrame.MAXIMIZED_BOTH);
            fm2.setLocation(0, 0);
            fm2.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosed(WindowEvent e) {
                    FRContext.setCurrentEnv(old_env);
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            FRContext.setCurrentEnv(old_env);
        }
    }

    private void reFillData() {
        // 值->
        for (int rownum = 0; rownum < model.getRowCount(); rownum++) {
            for (int colnum = 0; colnum < model.getColumnCount(); colnum++) {
                Object val = model.getValueAt(rownum, colnum);
                workSheet.setCellValue(colnum + 1 + list_zongxiang.size()/*列*/, begin_row + rownum + list_hengxiang.size()/*行*/, val);
                //workSheet.getCellElement(colnum + 1 + list_zongxiang.size()/*列*/, begin_row + rownum + list_hengxiang.size()/*行*/).setStyle(style_right);
            }
        }
        // 值<-
    }

    public void saveRowColumn() {
        if (gridid == null) {
            return;
        }
        String row_heights = "";
        for (int rownum = 0; rownum < model.getRowCount() + begin_row + list_hengxiang.size(); rownum++) {
            if (rownum != 0) {
                row_heights = row_heights + ";";
            }
            row_heights = row_heights + workSheet.getRowHeight(rownum);
        }
        String col_widths = "";
        for (int colnum = 0; colnum < model.getColumnCount() + 1 + list_zongxiang.size(); colnum++) {
            if (colnum != 0) {
                col_widths = col_widths + ";";
            }
            col_widths = col_widths + workSheet.getColumnWidth(colnum);
        }
        String col_vs = "";
        for (int colnum = 1; colnum < model.getColumnCount() + 1 + list_zongxiang.size(); colnum++) {
            CellElement ce = workSheet.getCellElement(colnum, begin_row + list_hengxiang.size());
            if (ce == null) {
                continue;
            }
            col_vs = col_vs + ce.getStyle().getHorizontalAlignment() + ";";
        }
        SysParameter sp = new SysParameter();
        sp.setSysParameter_key(gridid + ".row_heights");
        sp.setSysparameter_value(row_heights);
        CommUtil.saveOrUpdate(sp);
        sp = new SysParameter();
        sp.setSysParameter_key(gridid + ".col_widths");
        sp.setSysparameter_value(col_widths);
        CommUtil.saveOrUpdate(sp);
        sp = new SysParameter();
        sp.setSysParameter_key(gridid + ".style_v");
        sp.setSysparameter_value(col_vs);
        CommUtil.saveOrUpdate(sp);

        //saveStyle();
    }

    public void cancel() {
        reFillData();
    }

    public void setEditable(boolean editable) {
        if (jWorkSheet != null) {
            jWorkSheet.getGrid().setEditable(editable);
        }
    }

    public void updateValue() {
        jWorkSheet.getGrid().stopEditing();
        // 值->
        for (int rownum = 0; rownum < model.getRowCount(); rownum++) {
            for (int colnum = 0; colnum < model.getColumnCount(); colnum++) {
                //Object val = model.getValueAt(rownum, colnum);
                Object val = workSheet.getCellValue(colnum + 1 + list_zongxiang.size()/*列*/, begin_row + rownum + list_hengxiang.size()/*行*/);
                model.setValueAt(val, rownum, colnum);
            }
        }
        // 值<-
    }

    private ReportSettings getReortSettings() {
        byte[] b = ReportImpl.getReportSetting(gridid);
        if (b == null) {
            return null;
        }
        ReportSettings rs = null;
        try {
            rs = (ReportSettings) ObjectToXMLUtil.objectXmlDecoder(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    private void saveReportSetting() {
        ReportSettings rs = workSheet.getReportSettings();
        if (rs == null) {
            return;
        }
        ValidateSQLResult result = ReportImpl.saveReportSetting(gridid, ObjectToXMLUtil.objectXmlEncoder(rs));
        if (result.getResult() != 0) {
            MsgUtil.showHRSaveErrorMsg(result);
        }
    }

    public static void main(String[] args) {
        ReportTableModel model = new ReportTableModel() {

            private String[] coltitles = new String[]{"3米以下|无筋", "3米以下|一层筋", "3米以下|二层筋",
                "3米以上|二层筋", "3米以上|一层筋", "3米以上|二层筋"};
            private String[] rowtitles = new String[]{"3米以下|A", "3米以下|B", "5米以下", "5米以上|A", "5米以上|B"};

            @Override
            public String getColumnName(int colnum) {
                return coltitles[colnum];
            }

            @Override
            public String getRowName(int rownum) {
                return rowtitles[rownum];
            }

            @Override
            public int getRowCount() {
                return 5;
            }

            @Override
            public int getColumnCount() {
                return 6;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {                
                if (columnIndex == 1) {
                    return "00";
                }
                return "" + rowIndex + columnIndex;
            }
        };

        List<String> list_hengxiang = new ArrayList<String>();
        List<String> list_zongxiang = new ArrayList<String>();
        list_hengxiang.add("拱厚");
        list_hengxiang.add("种类");
        list_zongxiang.add("拱高");
        list_zongxiang.add("分类");

        TableEditPanel tep = new TableEditPanel(model, list_hengxiang, list_zongxiang, "AAAAAAAAAAAAA");
        JFrame fm = new JFrame("测试");
        fm.add(tep, BorderLayout.CENTER);
        fm.setSize(500, 600);
        fm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fm.setVisible(true);
    }
}
