/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.queryanalysis;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.jhrcore.util.FormatUtil;

/**
 *
 * @author mxliteboss
 */
public class AnalyseDataModel {

    private List<AnalyseField> analyse_fields = new ArrayList<AnalyseField>();
    private List<AnalyseField> x_fields = new ArrayList<AnalyseField>();
    private List<AnalyseField> comm_fields = new ArrayList<AnalyseField>();
    private List<Object[]> result_data = new ArrayList<Object[]>();
    private List<Object[]> data;
    private List<String> x_caption = new ArrayList<String>();
    private Hashtable<String, List<String>> x_header = new Hashtable<String, List<String>>();
    private int data_len = 0;//显示的表格列数
    private int comm_len = 0;//统计参考项个数
    private DecimalFormat df_int = new DecimalFormat("#");
    private Hashtable<Integer, DecimalFormat> format_keys = new Hashtable<Integer, DecimalFormat>();

    public AnalyseDataModel(List data1, List<AnalyseField> analyse_fields, List<AnalyseField> x_fields, List<AnalyseField> y_fields, boolean dept_analyse_flag, int analyse_type, String scheme_type) {
        this.x_caption.clear();
        this.x_header.clear();
        this.x_fields.clear();
        this.x_fields.addAll(x_fields);
        this.result_data.clear();
        this.analyse_fields = analyse_fields;
        this.data = new ArrayList<Object[]>();
        if (data1.size() > 0) {
            if (data1.get(0) instanceof Object[]) {
                this.data.addAll(data1);
            } else {
                data.clear();
                for (Object obj : data1) {
                    data.add(new Object[]{obj});
                }
            }

        }
        AnalyseField first_field = null;
        if (x_fields.size() > 0) {
            first_field = x_fields.get(0);
        }

        if (y_fields.size() > 0) {
            first_field = y_fields.get(0);
        }
        int y_len = y_fields.size() - 1;
        int x_len = x_fields.size();

        if (first_field != null) {
            x_caption.add(first_field.getField_caption());
        }
        int x_ind = 1;
        comm_len = analyse_fields.size();
        if (!dept_analyse_flag && analyse_type == 1) {
            for (AnalyseField af : analyse_fields) {
                String caption = af.getField_caption() + "(" + af.getStat_type() + ")";
                x_caption.add(caption);
                initFormat(af, x_ind);
                x_ind++;
            }
        } else {
            for (AnalyseField af : analyse_fields) {
                String caption = af.getField_caption() + "(" + af.getStat_type() + ")";
                x_caption.add(caption);
                List<String> field_header = new ArrayList<String>();
                if (x_len > 1) {
                    for (int i = 1; i < x_len; i++) {
                        field_header.add(x_fields.get(i).getField_caption());
                        initFormat(af, x_ind);
                        x_ind++;
                    }
                }
                x_header.put(caption, field_header);
            }
        }
        data_len = comm_len * (x_len - 1);
//        System.out.println("x_len:"+x_len);
//        System.out.println("y_len:"+y_len);
        if (data.size() > 0) {
            Object[] datas = data.get(0);
            int all_len = datas.length;
//            System.out.println("a:" + all_len + ";" + data_len + ";" + comm_len + ";" + x_len);
            if ("个人对比".equals(scheme_type)) {
                data_len++;
                y_len = data.size();
                for (int i = 0; i < y_len; i++) {
                    datas = data.get(i);
                    Object[] row_data = new Object[data_len];
                    AnalyseField af = new AnalyseField();
                    af.setField_caption(datas[0] == null ? "" : datas[0].toString());
                    af.setField_value(datas[1] == null ? "@@@" : datas[1].toString());
                    row_data[0] = af;
                    y_fields.add(af);
                    for (int j = 2; j < all_len; j++) {
                        row_data[j - 1] = datas[j];
                    }
                    result_data.add(row_data);
                }
            }
//            else if (all_len == data_len && !dept_analyse_flag) {
//                data_len = comm_len + 1;
//                for (int i = 0; i < y_len; i++) {
//                    Object[] row_data = new Object[data_len];
//                    row_data[0] = y_fields.get(i + 1);
//                    for (int j = 0; j < comm_len; j++) {
//                        if ((i * comm_len + j) >= datas.length) {
//                            continue;
//                        }
//
//                        row_data[j + 1] = datas[i * comm_len + j];
//                    }
//                    result_data.add(row_data);
//                }
//            } 
            else if (!dept_analyse_flag) {
                for (int i = 0; i < y_len; i++) {
                    Object[] row_data = new Object[data_len+1];
                    row_data[0] = y_fields.get(i + 1);
                    for (int j = 0; j < data_len; j++) {
                        if ((i * data_len + j) >= datas.length) {
                            continue;
                        }
//                        System.out.println("d:"+(j+1)+";"+datas[i * data_len + j]+";"+(i * data_len + j));
                        row_data[j + 1] = datas[i * data_len + j];
                    }
//                    for (int j = 0; j < comm_len; j++) {
//                        if ((i * comm_len + j) >= datas.length) {
//                            continue;
//                        }
//
//                        row_data[j + 1] = datas[i * comm_len + j];
//                    }
                    result_data.add(row_data);
                }
            } else {
                x_len--;
                for (int i = 0; i < y_len; i++) {
                    Object[] row_data = new Object[data_len + 1];
                    row_data[0] = y_fields.get(i + 1);
                    int ind = 0;
                    for (int j = 0; j < comm_len; j++) {
                        for (int k = 0; k < x_len; k++) {
                            ind++;
                            if ((i * data_len + j * x_len + k) >= datas.length) {
                                row_data[ind] = "0";
                            } else {
                                row_data[ind] = datas[i * data_len + j * x_len + k];
                            }

                        }
                    }
                    result_data.add(row_data);
                }
            }
        }
    }

    /**
     * 该方法用于根据统计字段设置其格式化模式，并以目标列序号作为索引存储到hashtable
     * @param af：统计字段
     * @param x_ind：列序号
     */
    private void initFormat(AnalyseField af, int x_ind) {
        DecimalFormat df;
        if (af.getStat_operator().equals("count")) {
            df = df_int;
        } else {
            df = FormatUtil.getFormatByDecimalLen(af.getDecimal_len());
        }
        format_keys.put(x_ind, df);
    }

    public Hashtable<Integer, DecimalFormat> getFormat_keys() {
        return format_keys;
    }

    public List<AnalyseField> getComm_fields() {
        return comm_fields;
    }

    public void setComm_fields(List<AnalyseField> comm_fields) {
        this.comm_fields = comm_fields;
    }

    public List<AnalyseField> getAnalyse_fields() {
        return analyse_fields;
    }

    public List<Object[]> getData() {
        return data;
    }

    public void setData(List<Object[]> data) {
        this.data = data;
    }

    public void setAnalyse_fields(List<AnalyseField> analyse_fields) {
        this.analyse_fields = analyse_fields;
    }

    public List<Object[]> getResult_data() {
        return result_data;
    }

    public void setResult_data(List<Object[]> result_data) {
        this.result_data = result_data;
    }

    public List<String> getX_caption() {
        return x_caption;
    }

    public void setX_caption(List<String> x_caption) {
        this.x_caption = x_caption;
    }

    public Hashtable<String, List<String>> getX_header() {
        return x_header;
    }

    public void setX_header(Hashtable<String, List<String>> x_header) {
        this.x_header = x_header;
    }

    public int getData_len() {
        return data_len;
    }

    public void setData_len(int data_len) {
        this.data_len = data_len;
    }

    public int getComm_len() {
        return comm_len;
    }

    public void setComm_len(int comm_len) {
        this.comm_len = comm_len;
    }

    public List<AnalyseField> getX_fields() {
        return x_fields;
    }

    public void setX_fields(List<AnalyseField> x_fields) {
        this.x_fields = x_fields;
    }
}
