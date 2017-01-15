/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InportDialog.java
 *
 * Created on 2012-7-9, 17:21:29
 */
package org.jhrcore.client.report;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javassist.tools.rmi.RemoteException;
import org.jhrcore.client.CommUtil;
import org.jhrcore.entity.ExportDetail;
import org.jhrcore.entity.ExportScheme;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.msg.CommMsg;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.ui.action.CloseAction;
import org.jhrcore.ui.importxls.ReadXLS;
import org.jhrcore.ui.importxls.XlsImportInfo;
import org.jhrcore.util.DateUtil;
import org.jhrcore.util.ExportUtil;
import org.jhrcore.util.FileChooserUtil;
import org.jhrcore.util.MsgUtil;
import org.jhrcore.util.SysUtil;

/**
 *
 * @author Administrator
 */
public class ImportDialog extends javax.swing.JDialog {

    private boolean isCheck;
    private File select_file = null;//当前选择的文件
    private List<Hashtable<String, String>> repeat_list = new ArrayList<Hashtable<String, String>>();//用于记录XLS中的重复记录
    private List<Hashtable<String, String>> notFind_list = new ArrayList<Hashtable<String, String>>();//用于记录XLS中的找不到匹配记录
    private List<Hashtable<String, String>> error_list = new ArrayList<Hashtable<String, String>>();//用于记录错误数据
    private List<Hashtable<String, String>> correct_list = new ArrayList<Hashtable<String, String>>();//用于记录正确数据
    private int update_no = 0;//最后更新的对象个数
    private int notexist_no = 0;//查找不到的记录
    private ExportScheme exportScheme;//当前导入方案
    private StringBuffer upSql;
    private Hashtable<Integer, List<String>> error_keys = new Hashtable<Integer, List<String>>();//错误数据索引

    /** Creates new form InportDialog */
    public ImportDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initOthers();
        setupEvents();
    }

    public ImportDialog() {
        initComponents();
        initOthers();
        setupEvents();
    }

    private void initOthers() {
        setTitle("EXCEL文件导入");
    }

    private void setupEvents() {
        btn_cx.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                File file = FileChooserUtil.getXLSFile(CommMsg.SELECTXLSFILE_MESSAGE);
                if (file == null) {
                    return;
                }
                isCheck = false;
                select_file = file;
                mc.setText(select_file.getPath());
            }
        });

        btn_jy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (select_file == null) {
                    JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(btn_qd), "请选择导入文件", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Runnable run = new Runnable() {

                    @Override
                    public void run() {
                        final int i = check(select_file.getPath());
                        Runnable tmp_run = new Runnable() {

                            @Override
                            public void run() {
                                report_msg(select_file.getPath(), true, i);
                                lblInfo.setText("");
                                jProgressBar1.setValue(0);
                            }
                        };
                        SwingUtilities.invokeLater(tmp_run);
                    }
                };
                new Thread(run).start();
            }
        });

        btn_qd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (select_file == null) {
                    JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(btn_qd), "请选择导入文件", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Runnable run = new Runnable() {

                    @Override
                    public void run() {
                        final int i = importXls(select_file.getPath());
                        Runnable tmp_run = new Runnable() {

                            @Override
                            public void run() {
                                report_msg(select_file.getPath(), false, i);
                                lblInfo.setText("");
                                jProgressBar1.setValue(0);
                                if (ContextManager.getMainFrame() != null) {
                                    ContextManager.getMainFrame().setCursor(Cursor.getDefaultCursor());
                                }
                            }
                        };
                        SwingUtilities.invokeLater(tmp_run);
                    }
                };
                new Thread(run).start();
            }
        });
        CloseAction.doCloseAction(btn_qx);
    }

    /**
     *
     *导入数据
     * 
     */
    private int importXls(String fileName) {
        int i = 0;

        if (!isCheck) {
            i = check(fileName);
        }
        if (i != 0) {
            return i;
        }

        try {
            Runnable tmp_run2 = new Runnable() {

                @Override
                public void run() {
                    if (ContextManager.getMainFrame() != null) {
                        ContextManager.getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    }
                    lblInfo.setText("正在导入数据");
                }
            };
            SwingUtilities.invokeLater(tmp_run2);
            final ValidateSQLResult validateSQLResult = CommUtil.excuteSQLs(upSql.toString(), ";");
            Runnable tmp_run = new Runnable() {

                @Override
                public void run() {
                    if (validateSQLResult.getError_result() != 0) {
                        String msg = "";
                        msg += " 成功插入：" + validateSQLResult.getInsert_result() + " 条;\n";
                        msg += " 成功更新：" + validateSQLResult.getUpdate_result() + " 条;\n";
                        msg += " 执行错误：" + validateSQLResult.getError_result() + " 条;\n";
                        msg += validateSQLResult.getMsg();
                        MsgUtil.showHRValidateReportMsg(msg);
                    }
                }
            };
            SwingUtilities.invokeLater(tmp_run);
            if (validateSQLResult.getError_result() != 0) {
                return 4;
            }
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(btn_qd), "数据库导入错误", "错误", JOptionPane.ERROR_MESSAGE);
            return 4;
        }
        return 0;
    }

    /**
     *
     *校验数据
     * 
     */
    public int check(String fileName) {
        error_keys.clear();
        error_list.clear();
        correct_list.clear();
        update_no = 0;
        notexist_no = 0;
        XlsImportInfo xlsImportInfo = null;
        //检查读excel
        try {
            xlsImportInfo = ReadXLS.importXls(new File(fileName), "custom");
        } catch (Exception e) {
            return 6;
        }
        exportScheme = xlsImportInfo.getExportScheme();//当前显示方案

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                lblInfo.setText("读取数据");
                jProgressBar1.setMinimum(0);
                jProgressBar1.setMaximum(exportScheme.getExportDetails().size());
                jProgressBar1.setValue(0);
            }
        });
        String tableName = exportScheme.getEntity_name().split("@")[0];
        //表主键
        String[] keyStr = exportScheme.getEntity_name().split("@")[1].split("#");

        Hashtable<String, TempFieldInfo> classMap = xlsImportInfo.getExportScheme().getClassMap();
        Hashtable<String, String> use_fields = new Hashtable<String, String>();

        for (ExportDetail exportDetail : exportScheme.getExportDetails()) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    jProgressBar1.setValue(jProgressBar1.getValue() + 1);
                }
            });
            String field_name = exportDetail.getField_name();
//            表字段
            use_fields.put(field_name, field_name);
        }
        final int len = xlsImportInfo.getValues().size();
        int k = 0;
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                lblInfo.setText("数据读取");
                jProgressBar1.setMinimum(0);
                jProgressBar1.setMaximum(len);
                jProgressBar1.setValue(0);
            }
        });
        for (int i = 0; i < len; i++) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    jProgressBar1.setValue(jProgressBar1.getValue() + 1);
                }
            });
            boolean error_data_flag = false;
            Hashtable<String, String> row_data = xlsImportInfo.getValues().get(i);

            List<String> row_error_keys = error_keys.get(k);
            if (row_error_keys == null) {
                row_error_keys = new ArrayList<String>();
            }
            //判断是否存在
            String selectsql = "SELECT * FROM " + tableName + " WHERE 1=1";
            TempFieldInfo field = null;
            for (String key : keyStr) {
                field = classMap.get(key);
                selectsql += "  AND  " + sqlFragment(field.getField_type(), key, row_data.get(key));//更新条件
            }
            if (CommUtil.selectSQL(selectsql).isEmpty()) {
                notexist_no++;
                continue;
            }
            //验证数据格式
            try {
                for (String field_name : use_fields.keySet()) {
                    Object tmp_obj = row_data.get(field_name);
                    if (dataCheck(tmp_obj, classMap.get(field_name))) {
                        row_error_keys.add(field_name);
                        continue;
                    }
                }//字段for结尾

                error_data_flag = row_error_keys.size() > 0;
                if (error_data_flag) {
                    error_keys.put(k, row_error_keys);
                    error_list.add(row_data);
                    k++;
                    error_data_flag = false;
                    continue;
                } else {
                    correct_list.add(row_data);
                }
            } catch (Exception e) {
            }
        }
//数据拼串

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                lblInfo.setText("数据校验");
                jProgressBar1.setMinimum(0);
                jProgressBar1.setMaximum(len);
                jProgressBar1.setValue(0);
            }
        });


        String updateSql = "";
        String condSql = "";
        upSql = new StringBuffer();
        for (int i = 0; i < correct_list.size(); i++) {
            updateSql = "";
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    jProgressBar1.setValue(jProgressBar1.getValue() + 1);
                }
            });
            Hashtable<String, String> row_data = correct_list.get(i);
            updateSql = "UPDATE  " + tableName + "  SET ";
            condSql = "WHERE 1=1 ";

            flag:
            for (String field_name : use_fields.keySet()) {   //更新语句
                TempFieldInfo field = classMap.get(field_name);
                String field_type = field.getField_type();
                for (int kl = 0; kl < keyStr.length; kl++) {
                    if (field_name.equals(keyStr[kl])) {
                        condSql += "  AND  " + sqlFragment(field_type, field_name, row_data.get(field_name));//更新条件
                        continue flag;
                    }
                }
                updateSql += " " + sqlFragment(field_type, field_name, row_data.get(field_name)) + " ,"; //更新结果
            }
            updateSql = updateSql.substring(0, updateSql.length() - 1) + condSql;
            updateSql = updateSql + " ;";  //一段sql 结束
            update_no++;
            upSql.append(updateSql);
        }//for 结尾
        isCheck = true;
        return 0;
    }

//类型  变量名  变量值   数据单元组合
    private String sqlFragment(String type, String field_name, String value) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sqlfram = "";
        if (type.equals("CHAR") || type.startsWith("VARCHAR")) {
            sqlfram = field_name + "='" + value + "'";
        } else if (type.equals("DATE") || type.equals("TIME") || type.equals("TIMESTAMP")) {
            if (value.trim().length() == 0) {
                sqlfram = field_name + "='' ";
                return sqlfram;
            }
            Object obj = DateUtil.StrToDate(value);
            sqlfram = field_name + "='' ";
            if (type.equals("DATE")) {
                sqlfram = field_name + "= to_date('" + date.format(obj) + "','yyyy-MM-dd') ";
            } else if (type.equals("TIME")) {
                sqlfram = field_name + "= to_date('" + time.format(obj) + "','hh24:mi:ss') ";
            } else {
                sqlfram = field_name + "= to_date('" + timestamp.format(obj) + "','yyyy-MM-dd hh24:mi:ss') ";
            }
        } else if (type.equals("NUMBER")) {
            sqlfram = field_name + "=" + (SysUtil.objToStr(value).trim().equals("") ? "0" : value) + " ";
        }
        return sqlfram;
    }

    /**
     * 用于校验或者更新后产生的消息报告
     * @param fileName：错误数据放置路径
     * @param ischeck：是否校验
     */
    private void report_msg(String fileName, boolean ischeck, int check_flag) {
        if (check_flag == 0) {
            fileName = fileName.replace(".xls", "1.xls");
            repeat_list.addAll(notFind_list);
            ExportUtil.export(fileName, exportScheme, repeat_list, error_list, error_keys);
            String export_msg = "导入消息报告如下：\n";
            if (ischeck) {
                export_msg += " 可更新记录：" + update_no + "条;\n";
                export_msg += " 不存在记录：" + notexist_no + "条;\n";
                export_msg += " 数据格式有误的记录： " + error_list.size() + "条.\n";
            } else {
                export_msg += " 成功更新记录：" + update_no + "条;\n";
                export_msg += " 不存在记录：" + notexist_no + "条;\n";
                export_msg += " 数据格式有误的记录： " + error_list.size() + "条.\n";
            }
            MsgUtil.showHRValidateReportMsg(export_msg);
        } else if (check_flag == 4) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(btn_qd), "数据库更新错误", "错误", JOptionPane.ERROR_MESSAGE);
        } else if (check_flag == 6) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(btn_qd), "导入文件格式有误,请检查表批注、字段批注等", "错误", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     *
     *校验数据合法性 
     *如合法返回 false 
     *如不合法  true
     */
    private boolean dataCheck(Object obj, TempFieldInfo field) {
        if (null == obj || obj.toString().length() == 0) {
            return false;
        }
        if (field.getField_type().equals("TIMESTAMP")
                || field.getField_type().equals("DATE")
                || field.getField_type().equals("TIME")) {
            Object object = DateUtil.StrToDate(obj.toString());
            if (object == null) {
                return true;
            }
        }
        if (field.getField_type().equals("CHAR") || field.getField_type().startsWith("VARCHAR")) {
            if (field.getField_width() < obj.toString().length()) {
                return true;
            }
        }
        if (field.getField_type().equals("NUMBER")) {
            //表达式的功能：验证必须为数字（整数或小数）  
            String pattern ="^[-+]?\\d+(\\.\\d+)?$";// "(-)+[0-9]+(.[0-9]+)?";
            //对()的用法总结：将()中的表达式作为一个整体进行处理，必须满足他的整体结构才可以。  
            //(.[0-9]+)? ：表示()中的整体出现一次或一次也不出现  
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(obj.toString().trim());
            return !m.matches();
        }
        return false;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        mc = new javax.swing.JTextField();
        btn_cx = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        btn_qd = new javax.swing.JButton();
        btn_qx = new javax.swing.JButton();
        btn_jy = new javax.swing.JButton();
        lblInfo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        mc.setEditable(false);
        mc.setPreferredSize(new java.awt.Dimension(6, 22));

        btn_cx.setText("...");
        btn_cx.setPreferredSize(new java.awt.Dimension(6, 22));

        jProgressBar1.setPreferredSize(new java.awt.Dimension(6, 22));

        btn_qd.setText("导入");

        btn_qx.setText("取消");

        btn_jy.setText("校验");

        lblInfo.setPreferredSize(new java.awt.Dimension(6, 22));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(288, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                .addGap(35, 35, 35))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(mc, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cx, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_jy)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_qd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_qx)))
                .addGap(35, 35, 35))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_jy)
                    .addComponent(btn_qd)
                    .addComponent(btn_qx))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(lblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cx;
    private javax.swing.JButton btn_jy;
    private javax.swing.JButton btn_qd;
    private javax.swing.JButton btn_qx;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JTextField mc;
    // End of variables declaration//GEN-END:variables
}
