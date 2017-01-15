/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foundercy.pf.control.table;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 * @author Administrator
 */
public class FColumnStat {

    private String column_id;  // 列id，为字段名称，用'.'分割，如dept.dept_name
    private int stat_type = 0; // 0 : 求和; 1 : 平均; 2 : 计数
    private double stat_val = 0;
    private FTable ftable = null;
    private DecimalFormat df = null;

    public String getDisplay() {

        String tmp = "合";//合计";
        if (stat_type == 1) {
            tmp = "平";//平均";
        } else if (stat_type == 2) {
            return "计" + ftable.getObjects().size();//计数"
        }
//        if ((stat_val - (int) stat_val) < 0.000001) {
//            return tmp + String.valueOf((int) stat_val);
//        } else {
        if (df != null) {
            return tmp + df.format(stat_val);//NumberFormat.getInstance().format(stat_val);
        }
        return tmp + NumberFormat.getInstance().format(stat_val);
//        }
    }

    public String getColumn_id() {
        return column_id;
    }

    public void setColumn_id(String column_id) {
        this.column_id = column_id;
    }

    public int getStat_type() {
        return stat_type;
    }

    public void setStat_type(int stat_type) {
        this.stat_type = stat_type;
    }

    public double getStat_val() {
        return stat_val;
    }

    public void setStat_val(double stat_val) {
        this.stat_val = stat_val;
    }

    public FColumnStat(FTable ftable, String column_id, int stat_type, double stat_val, DecimalFormat df) {
        this.ftable = ftable;
        this.column_id = column_id;
        this.stat_type = stat_type;
        this.stat_val = stat_val;
        this.df = df;
    }

    public void changeStatBy(Object old_val, Object aValue) {
        if (stat_type == 2) {
            return;
        }
        old_val = (old_val == null || old_val.equals("")) ? 0 : old_val.toString();
        aValue = (aValue == null || aValue.equals("")) ? 0 : aValue.toString();
        try {
            double val = Double.valueOf(aValue.toString()) - Double.valueOf(old_val.toString());
            if (stat_type == 0) {
                stat_val = stat_val + val;
            } else {
                stat_val = stat_val + val / ftable.getObjects().size();
            }
            ftable.getPnlStat().updateUI();
        } catch (Exception e) {
        }
    }

    public FTable getFtable() {
        return ftable;
    }

    public void setFtable(FTable ftable) {
        this.ftable = ftable;
    }
}
