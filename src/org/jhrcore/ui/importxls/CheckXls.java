/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.importxls;

import com.foundercy.pf.control.table.FTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.log4j.Logger;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.DateUtil;
import org.jhrcore.util.DbUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.comm.CodeManager;
import org.jhrcore.entity.Code;
import org.jhrcore.entity.ExportDetail;
import org.jhrcore.entity.ExportScheme;
import org.jhrcore.entity.annotation.ObjectListHint;
import org.jhrcore.entity.base.TempFieldInfo;
import org.jhrcore.entity.salary.ValidateSQLResult;
import org.jhrcore.rebuild.EntityBuilder;
import org.jhrcore.ui.ModelFrame;
import org.jhrcore.util.ComponentUtil;
import org.jhrcore.util.ExportUtil;
import org.jhrcore.util.MsgUtil;

/**
 *
 * @author mxliteboss
 */
public class CheckXls {

    private static CheckXls checkXls = null;
    private List<Hashtable<String, String>> not_exist_list = new ArrayList<Hashtable<String, String>>();//用于记录XLS中的重复记录
    private List<Hashtable<String, String>> error_list = new ArrayList<Hashtable<String, String>>();//用于记录错误数据
    private List<Hashtable<String, String>> repeat_list = new ArrayList<Hashtable<String, String>>();//用于记录重复项
    private Hashtable<String, Hashtable<String, Object>> result_data = new Hashtable<String, Hashtable<String, Object>>();//用于记录读取出来转换成对象的数据
    private Hashtable<String, String> exist_keys = new Hashtable<String, String>();//用于记录数据库中已经存在的记录，索引为匹配字段值，值为a01_key(即主键值)
    private Class cur_class = null;
    private Hashtable<Integer, List<String>> error_all_keys = new Hashtable<Integer, List<String>>();
    private Hashtable<String, TempFieldInfo> infoKeys = new Hashtable<String, TempFieldInfo>();
    private List<String> show_fields = new ArrayList<String>();
    private List<TempFieldInfo> infos = null;
    private ExportScheme scheme = null;
    private String fetch_sql = null;
    private List<String> fetch_fields = null;
    private List<String> disable_fields = null;
    private boolean error_data_pass_flag = false;
    private boolean checked = false;
    private Logger log = Logger.getLogger(CheckXls.class.getName());
    private FTable ftableResult = null;
    private FTable ftableDataError = null;
    private FTable ftableNotExists = null;
    private FTable ftableRepeat = null;
    private ModelFrame mf = null;
    private HashSet<String> update_able_fields = new HashSet<String>();
    private double t_step = 0;
    private JProgressBar jProgressBar1 = new JProgressBar();
    private JLabel lblInfo = new JLabel();
    private JTabbedPane jtp = new JTabbedPane();

    public static CheckXls getInstance() {
        if (checkXls == null) {
            checkXls = new CheckXls();
        }
        checkXls.clearSession();
        return checkXls;
    }

    public ValidateSQLResult checkXls(XlsImportInfo xinfo, String fetch_sql, Class cur_class, List<TempFieldInfo> infos, List<String> fetch_fields, boolean error_data_pass_flag, List<String> disable_fields) {
        this.disable_fields = disable_fields;
        if (!checked) {
            checkXls.clearSession();
        }
        show_fields.clear();
        checked = false;
        this.fetch_sql = fetch_sql;
        this.infos = infos;
        this.error_data_pass_flag = error_data_pass_flag;
        this.fetch_fields = fetch_fields;
        scheme = null;
        for (TempFieldInfo tfi : infos) {
            infoKeys.put(tfi.getField_name(), tfi);
        }
        this.cur_class = cur_class;
        ValidateSQLResult vs = new ValidateSQLResult();
        vs.setResult(1);
        int fetch_field_len = fetch_fields.size();
        if (fetch_field_len == 0) {
            vs.setMsg("未选择任何匹配项");
            return vs;
        }
        scheme = xinfo.getExportScheme();
        for (ExportDetail detail : scheme.getExportDetails()) {
            String fetched_field = "";
            for (String field : fetch_fields) {
                if (field.toLowerCase().equals(detail.getField_name().toLowerCase())) {
                    fetched_field = field;
                    show_fields.add(fetched_field);
                    break;
                }
            }
        }
        if (fetch_fields.size() != show_fields.size()) {
            fetch_fields.removeAll(show_fields);
            String msg = "";
            for (String field : fetch_fields) {
                msg += "," + infoKeys.get(field);
            }
            vs.setMsg("XLS不包含匹配列:" + msg.substring(1));
            return vs;
        }
        Hashtable<String, String> field_type_keys = new Hashtable<String, String>();
        for (TempFieldInfo tfi : infos) {
            field_type_keys.put(tfi.getField_name(), tfi.getField_type());
        }
        for (ExportDetail exportDetail : scheme.getExportDetails()) {
            String field_name = exportDetail.getField_name();

            int field_right = 0;
            if (!field_name.startsWith("#")) {
                field_right = UserContext.getFieldRight(cur_class.getSimpleName() + "." + field_name.replace("_code_", ""));
            }
            if (field_right == 1) {
                update_able_fields.add(field_name);
                if (!show_fields.contains(field_name)) {
                    show_fields.add(field_name);
                }
            }
            String type = field_type_keys.get(field_name);
            if (type != null) {
                exportDetail.setField_type(type);
            } else {
                type = field_type_keys.get(field_name + "_code_");
                if (type != null) {
                    exportDetail.setField_type(type);
                } else {
                    type = field_type_keys.get(field_name.substring(0, field_name.length() - 6));
                    if (type != null) {
                        exportDetail.setField_type(type);
                    }
                }
            }
        }
        int k = 0;
        int len = xinfo.getValues().size();
        String fetch_col = fetch_fields.get(0);
        List<String> keys = new ArrayList<String>();
        for (int i = 0; i < len; i++) {
            boolean error_data_flag = false;
            Hashtable<String, String> row_data = xinfo.getValues().get(i);
            String compare_val = row_data.get(fetch_col);
            if (compare_val == null || compare_val.replace(" ", "").equals("")) {
                not_exist_list.add(row_data);
            } else {
                compare_val = compare_val.replace(" ", "");
                keys.add(compare_val);
                List<String> row_error_keys = error_all_keys.get(k);
                if (row_error_keys == null) {
                    row_error_keys = new ArrayList<String>();
                }
                Hashtable<String, Object> result_row_data = result_data.get(compare_val);
                if (result_row_data == null) {
                    result_row_data = new Hashtable<String, Object>();
                    for (String field_name : row_data.keySet()) {
                        if (field_name.startsWith("#") && !field_name.equals(fetch_col)) {
                            continue;
                        }
                        Object tmp_obj = row_data.get(field_name);
                        if (!update_able_fields.contains(field_name)) {
                            continue;
                        }
                        if (field_name.endsWith("_code_")) {
                            if (tmp_obj == null || tmp_obj.toString().equals("")) {
                            } else {
                                Field field = infoKeys.get(field_name).getField();
                                ObjectListHint objHint = field.getAnnotation(ObjectListHint.class);
                                if (objHint != null && objHint.hqlForObjectList().startsWith("from Code ")) {
                                    String hql = objHint.hqlForObjectList();
                                    tmp_obj = CodeManager.getCodeManager().getCodeByName(hql.substring(hql.indexOf("=") + 1), tmp_obj.toString());
                                } else {
                                    if (error_data_pass_flag) {
                                        continue;
                                    }
                                    row_error_keys.add(field_name);
                                }
                            }
                        } else {
                            String type = field_type_keys.get(field_name);
                            if (type == null) {
                                continue;
                            }
                            type = type.toLowerCase();
                            if (type.equals("boolean")) {
                                if (tmp_obj.toString().equals("0") || tmp_obj.toString().equals("1")) {
                                    tmp_obj = tmp_obj.toString().equals("1");
                                } else if (tmp_obj.toString().toLowerCase().equals("false") || tmp_obj.toString().equals("否")) {
                                    tmp_obj = "0";
                                } else if (tmp_obj.toString().toLowerCase().equals("true") || tmp_obj.toString().equals("是")) {
                                    tmp_obj = "1";
                                } else {
                                    tmp_obj = null;
                                }
                            } else if (type.equals("date")) {
                                tmp_obj = DateUtil.StrToDate(tmp_obj.toString());
                            } else if (type.equals("int")) {
                                tmp_obj = SysUtil.objToInt(tmp_obj);
                            } else if (type.equals("integer")) {
                                tmp_obj = SysUtil.objToInteger(tmp_obj, null);
                            } else if (type.equals("float")) {
                                tmp_obj = SysUtil.objToFloat(tmp_obj, null);
                            } else if (type.equals("bigdecimal")) {
                                tmp_obj = SysUtil.objToBigDecimal(tmp_obj, null);
                            }
                        }
                        if (tmp_obj == null) {
                            if (!error_data_pass_flag) {
                                row_error_keys.add(field_name);
                            }
                            continue;
                        }
                        result_row_data.put(field_name, tmp_obj);
                    }
                    error_data_flag = row_error_keys.size() > 0;
                    if (error_data_flag) {
                        error_all_keys.put(k, row_error_keys);
                        error_list.add(row_data);
                        error_data_flag = false;
                        k++;
                        continue;
                    }
                    result_data.put(compare_val, result_row_data);
                } else {
                    repeat_list.add(row_data);
                    Hashtable<String, String> pre_data = new Hashtable<String, String>();
                    for (String key : result_row_data.keySet()) {
                        Object value = result_row_data.get(key);
                        pre_data.put(key, value == null ? "" : value.toString());
                    }
                    repeat_list.add(pre_data);
                    result_data.remove(compare_val);
                }
            }
        }
        String entityKey = EntityBuilder.getEntityKey(cur_class);
        String plusStr = DbUtil.getPlusStr(UserContext.sql_dialect);
        String fetch_field = fetch_fields.get(0);
        int cols = fetch_fields.size();
        String s_select = fetch_field;
        for (int i = 1; i < cols; i++) {
            s_select += plusStr + "'_'" + plusStr + fetch_fields.get(i);
        }
        String sql = "select " + s_select + "," + entityKey + " " + fetch_sql + " and " + fetch_field + " in ";
        List fetch_data = CommUtil.selectSQL(sql, keys);
        if (fetch_data.isEmpty()) {
            vs.setMsg("匹配数据读取错误");
            return vs;
        }
        for (Object obj : fetch_data) {
            Object[] objs = (Object[]) obj;
            exist_keys.put(objs[0].toString(), objs[1].toString());
        }
        if (cols == 1) {
            List<String> removeKeys = new ArrayList<String>();
            for (String key : result_data.keySet()) {
                if (exist_keys.keySet().contains(key)) {
                    continue;
                }
                Hashtable<String, Object> row_data = result_data.get(key);
                Hashtable<String, String> error_data = new Hashtable<String, String>();
                for (String r_key : row_data.keySet()) {
                    error_data.put(r_key, row_data.get(r_key).toString());
                }
                removeKeys.add(key);
                not_exist_list.add(error_data);
            }
            for (String key : removeKeys) {
                result_data.remove(key);
            }
        }
        vs.setResult(0);
        return vs;
    }
//    public void tranXlsInfo(){
//        
//    }

    private void clearSession() {
        not_exist_list.clear();
        error_list.clear();
        repeat_list.clear();
        result_data.clear();
        exist_keys.clear();
        error_all_keys.clear();
        infoKeys.clear();
        show_fields.clear();
        scheme = null;
    }

    public void showCheckResult(JFrame jf, final String file_path) {
        jtp.removeAll();
        JPanel pnl = new JPanel(new BorderLayout());
        final JButton btnExport = new JButton("导出XLS");
        final JButton btnCheck = new JButton("重新校验");
        final JButton btnImport = new JButton("导入");
        final JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.add(btnCheck);
        toolbar.addSeparator();
        toolbar.add(btnExport);
        toolbar.addSeparator();
        toolbar.add(btnImport);
        toolbar.add(jProgressBar1);
        toolbar.add(lblInfo);
        ComponentUtil.setSize(jProgressBar1, 100, 14);
        ComponentUtil.setSize(lblInfo, 200, 20);
        jtp.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                btnExport.setEnabled(jtp.getSelectedIndex() > 0);
                btnCheck.setEnabled(jtp.getSelectedIndex() > 0);
                btnImport.setEnabled(jtp.getSelectedIndex() == 0);
            }
        });
        pnl.add(jtp);
        btnExport.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int ind = jtp.getSelectedIndex();
                List<Hashtable<String, String>> not_exists_list = new ArrayList<Hashtable<String, String>>();
                List<Hashtable<String, String>> error_list = new ArrayList<Hashtable<String, String>>();
                Hashtable<Integer, List<String>> error_keys = new Hashtable<Integer, List<String>>();
                if (ind == 1) {
                    error_list = getError_list();
                    error_keys = error_all_keys;
                } else if (ind == 2) {
                    not_exists_list = getNot_exist_list();
                } else {
                    not_exists_list = getRepeat_list();
                }
                ExportUtil.export(file_path, scheme, not_exists_list, error_list, error_keys);
            }
        });
        btnImport.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Runnable run = new Runnable() {

                    @Override
                    public void run() {
                        importData();
                    }
                };
                new Thread(run).start();
            }
        });
        pnl.add(toolbar, BorderLayout.NORTH);
        JPanel pnlResult = new JPanel(new BorderLayout());
        jtp.add("校验通过", pnlResult);
        List<String> t_fields = new ArrayList<String>();
        for (String field : show_fields) {
            TempFieldInfo tfi = infoKeys.get(field);
            t_fields.add(tfi.getCaption_name());
        }
        int len = show_fields.size();
        ftableResult = new FTable(t_fields);
        setResultData(len);
        pnlResult.add(ftableResult);


        JPanel pnlDataError = new JPanel(new BorderLayout());
        jtp.add("数据不合法", pnlDataError);
        final Hashtable<Object, Integer> error_data_keys = new Hashtable<Object, Integer>();
        ftableDataError = new FTable(t_fields) {

            @Override
            public Color getCellBackgroud(String fileName, Object cellValue, Object row_obj) {
                if (row_obj == null || error_data_keys.get(row_obj) == null) {
                    return null;
                }
                List<String> fields = error_all_keys.get(error_data_keys.get(row_obj));
                if (fields == null) {
                    return null;
                }
                if (fields.contains(fileName)) {
                    return Color.RED;
                }
                return null;
            }
        };
        setObjects(ftableDataError, error_list, len);
        int i = 0;
        for (Object obj : ftableDataError.getObjects()) {
            error_data_keys.put(obj, i);
            i++;
        }
        pnlDataError.add(ftableDataError);

        JPanel pnlNotExists = new JPanel(new BorderLayout());
        jtp.add("无法匹配", pnlNotExists);
        ftableNotExists = new FTable(t_fields);
        setObjects(ftableNotExists, not_exist_list, len);
        pnlNotExists.add(ftableNotExists);

        JPanel pnlRepeat = new JPanel(new BorderLayout());
        jtp.add("重复数据", pnlRepeat);
        ftableRepeat = new FTable(t_fields);
        setObjects(ftableRepeat, repeat_list, len);
        pnlRepeat.add(ftableRepeat);

        btnCheck.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                XlsImportInfo xinfo = new XlsImportInfo();
                xinfo.setExportScheme(scheme);
                List<Hashtable<String, String>> values = new ArrayList<Hashtable<String, String>>();
                FTable ftable = ftableRepeat;
                if (jtp.getSelectedIndex() == 1) {
                    ftable = ftableDataError;
                    error_list.clear();
                    error_all_keys.clear();
                } else if (jtp.getSelectedIndex() == 2) {
                    ftable = ftableNotExists;
                    not_exist_list.clear();
                } else {
                    repeat_list.clear();
                }
                List list = ftable.getObjects();
                if (list.isEmpty()) {
                    return;
                }
                checked = true;
                List<String> fields = ftable.getFields();
                Hashtable<String, String> fieldKeys = new Hashtable<String, String>();
                for (String key : infoKeys.keySet()) {
                    TempFieldInfo tfi = infoKeys.get(key);
                    fieldKeys.put(tfi.getCaption_name(), tfi.getField_name());
                }
                int len = fields.size();
                for (Object obj : list) {
                    Object[] objs = (Object[]) obj;
                    Hashtable<String, String> data = new Hashtable<String, String>();
                    for (int i = 0; i < len; i++) {
                        data.put(fieldKeys.get(fields.get(i)), objs[i] == null ? "" : objs[i].toString());
                    }
                    values.add(data);
                }
                xinfo.setValues(values);
                ValidateSQLResult result = checkXls(xinfo, fetch_sql, cur_class, infos, fetch_fields, error_data_pass_flag, disable_fields);
                if (result.getResult() == 0) {
                    setResultData(len);
                    setObjects(ftableDataError, error_list, len);
                    setObjects(ftableNotExists, not_exist_list, len);
                    setObjects(ftableRepeat, repeat_list, len);
                    refreshTabTitle(jtp);
                } else {
                    MsgUtil.showHRSaveErrorMsg(result);
                }
            }
        });
        refreshTabTitle(jtp);
        mf = ModelFrame.showModel(jf, pnl, true, "校验结果：");
    }

    private void refreshTabTitle(JTabbedPane jtp) {
        int count = jtp.getTabCount();
        for (int i = 0; i < count; i++) {
            String title = "";
            if (i == 0) {
                title = "校验通过(" + result_data.size() + ")";
            } else if (i == 1) {
                title = "数据不合法(" + error_list.size() + ")";
            } else if (i == 2) {
                title = "无法匹配(" + not_exist_list.size() + ")";
            } else if (i == 3) {
                title = "重复数据(" + repeat_list.size() + ")";
            }
            if (title.equals("")) {
                continue;
            }
            jtp.setTitleAt(i, title);
        }
    }

    private void setResultData(int len) {
        List<Object[]> data = new ArrayList<Object[]>();
        for (String key : result_data.keySet()) {
            Hashtable<String, Object> r_data = result_data.get(key);
            Object[] row = new Object[len];
            for (int i = 0; i < len; i++) {
                row[i] = r_data.get(show_fields.get(i));
            }
            data.add(row);
        }
        ftableResult.setObjects(data);
        ftableResult.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    }

    private void setObjects(FTable ftable, List<Hashtable<String, String>> repeat_list, int len) {
        List<Object[]> data = new ArrayList<Object[]>();
        for (Hashtable<String, String> r_data : repeat_list) {
            Object[] row = new Object[len];
            for (int i = 0; i < len; i++) {
                row[i] = r_data.get(show_fields.get(i));
            }
            data.add(row);
        }
        ftable.setObjects(data);
        ftable.setEditable(true);
        ftable.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    }

    private void importData() {
        t_step = 0;
        incProgress(0, "开始数据准备：");
        String export_msg = "导入消息报告如下：\n";
        export_msg += " 成功导入记录： " + getResult_data().size() + "条;\n";
        export_msg += " 无匹配项或匹配不到的记录：" + getNot_exist_list().size() + "条;\n";
        export_msg += " 数据格式有误的记录： " + getError_list().size() + "条.\n";
        export_msg += " 重复记录： " + getRepeat_list().size() + "条.\n";
        String sqls = getUpdateSQL(cur_class, getResult_data(), getExist_keys());
        incProgress(20, "数据准备完毕,开始导入：");
        ValidateSQLResult vs = CommUtil.excuteSQLs(sqls, ";");
        if (vs.getResult() == 0) {
            incProgress(80, "导入完毕：");
            MsgUtil.showHRValidateReportMsg(export_msg);
            result_data.clear();
            ftableResult.deleteAllRows();
            refreshTabTitle(jtp);
            t_step = 0;
            incProgress(0, "");
        } else {
            MsgUtil.showHRMsg(vs.getMsg(), "");
        }
    }

    public String getUpdateSQL(Class cur_class, Hashtable<String, Hashtable<String, Object>> result_data, Hashtable<String, String> exist_keys) {
        StringBuilder ex_sql = new StringBuilder();
        if (result_data.isEmpty()) {
            return ex_sql.toString();
        }
        Hashtable<String, String> field_type_keys = new Hashtable<String, String>();
        for (String key : infoKeys.keySet()) {
            field_type_keys.put(key, infoKeys.get(key).getField_type());
        }
        int ex_size = 0;
        String tabName = cur_class.getSimpleName();
        String tabKey = EntityBuilder.getEntityKey(cur_class);
        for (String key : result_data.keySet()) {
            Hashtable<String, Object> row_data = result_data.get(key);
            String pay_key = exist_keys.get(key);
            if (pay_key == null) {
                continue;
            } else {
                int i = 0;
                ex_sql.append("update ");
                ex_sql.append(tabName);
                ex_sql.append(" set ");
                for (String field_name : row_data.keySet()) {
                    if (disable_fields.contains(field_name) || !update_able_fields.contains(field_name)) {
                        continue;
                    }
                    i++;
                    if (i > 1) {
                        ex_sql.append(",");
                    }
                    Object value = row_data.get(field_name);
                    String type = field_type_keys.get(field_name);
                    if (type == null) {
                        type = field_type_keys.get(field_name + "_code_");
                        if (type == null) {
                            continue;
                        }
                    }
                    type = type.toLowerCase();
                    if (value == null && !(type.equals("string") || type.equals("code"))) {
                        continue;
                    }
                    ex_sql.append(field_name.replace("_code_", ""));
                    ex_sql.append("=");
                    if (type.equals("date")) {
                        ex_sql.append(DateUtil.toStringForQuery((Date) value));
                    } else if (type.equals("string") || type.equals("code")) {
                        if (value == null) {
                            ex_sql.append("null");
                        } else {
                            ex_sql.append("'");
                            if (value instanceof Code) {
                                ex_sql.append(((Code) value).getCode_id());
                            } else {
                                ex_sql.append(value.toString());
                            }
                            ex_sql.append("'");
                        }
                    } else {
                        ex_sql.append(value.toString());
                    }
                }
                if (ex_sql.toString().endsWith(" set ")) {
                    int len = ex_sql.length();
                    int leng = 12 + tabName.length();
                    ex_sql.delete(len - leng, len);
                    continue;
                }
                ex_sql.append(" where ").append(tabKey).append("='");
                ex_sql.append(pay_key);
                ex_sql.append("';");
                ex_size++;
            }
        }
        return ex_sql.toString();
    }

    private void incProgress(final double step, final String text) {
        Runnable run = new Runnable() {

            public void run() {
                t_step = t_step + step;
                jProgressBar1.setValue((int) t_step);
                lblInfo.setText(text + "(" + (int) t_step + "/100)");
            }
        };
        SwingUtilities.invokeLater(run);
    }

    public List<Hashtable<String, String>> getError_list() {
        return error_list;
    }

    public Hashtable<String, String> getExist_keys() {
        return exist_keys;
    }

    public List<Hashtable<String, String>> getNot_exist_list() {
        return not_exist_list;
    }

    public Hashtable<String, Hashtable<String, Object>> getResult_data() {
        return result_data;
    }

    public List<Hashtable<String, String>> getRepeat_list() {
        return repeat_list;
    }
}
