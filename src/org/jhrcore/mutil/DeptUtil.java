/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.mutil;

import java.util.ArrayList;
import java.util.List;
import org.jhrcore.client.CommUtil;
import org.jhrcore.util.SysUtil;
import org.jhrcore.client.UserContext;
import org.jhrcore.entity.DeptCode;
import org.jhrcore.entity.SysParameter;
import org.jhrcore.msg.dept.DeptMngMsg;

/**
 *
 * @author Administrator
 */
public class DeptUtil {

    private static SysParameter deptGrade = null;

    /**
     * ���ż�������
     */
    public static SysParameter getDeptGrade() {
        if (deptGrade == null) {
            deptGrade = (SysParameter) CommUtil.fetchEntityBy("from SysParameter sys where sys.sysParameter_key='Dept.deptgrade'");
            if (deptGrade == null) {
                deptGrade = new SysParameter();
                deptGrade.setSysParameter_key("Dept.deptgrade");
                deptGrade.setSysparameter_code("Dept.deptgrade");
                deptGrade.setSysparameter_name("���ż�������");
                deptGrade.setSysparameter_value("1;2;2;2;2;2;2;2;2;2;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;");
                CommUtil.saveOrUpdate(deptGrade);
            }
        }
        return deptGrade;
    }

    public static int getDeptMaxLevel() {
        SysParameter sp = getDeptGrade();
        int i = 0;
        String[] tmp = sp.getSysparameter_value().split(";");
        while (!tmp[i].equals("0")) {
            i++;
        }
        return i;
    }

    public static int getDeptCodeLength(int level) {
        SysParameter sp = getDeptGrade();
        int len = 0;
        String[] tmp = sp.getSysparameter_value().split(";");
        for (int i = 0; i < level; i++) {
            len = len + Integer.valueOf(tmp[i]);
        }
        return len;
    }

    public static int getSingleDeptCodeLength(int level) {
        SysParameter sp = getDeptGrade();
        String[] tmp = sp.getSysparameter_value().split(";");
        return Integer.valueOf(tmp[level - 1]);
    }

    /*
     * �����Ƿ����ñ���
     */
    public static boolean getDeptWeaveFlag() {
        boolean flag = false;
        SysParameter dept_flag = (SysParameter) CommUtil.fetchEntityBy("from SysParameter s where s.sysParameter_key = 'weave.dept_flag' ");
        if (dept_flag == null) {
            return flag;
        }
        flag = "1".equals(dept_flag.getSysparameter_value());
        return flag;
    }

    public static String getDeptRightSQL(DeptCode dc, String preChar) {
        String sql = UserContext.getDept_right_rea_str(preChar);
        if (dc == null || dc.getDept_code() == null) {
            sql += " and 1=0";
        } else {
            sql += " and " + preChar + ".dept_code like '" + dc.getDept_code() + "%'";
        }
        return sql;
    }

    public static String validateDeptPxCode(DeptCode curDept) {
        String newPxCode = curDept.getPx_code();
        Object msg = "";
        String prePx = "";
        if (newPxCode == null || newPxCode.trim().equals("")) {
//            msg = "����Ų���Ϊ��";
            msg = DeptMngMsg.msgOrderNull;
        } else if (newPxCode.length() != curDept.getDept_code().length()) {
//            msg = "����ų��ȴ��󣬵�ǰѡ���ŵ�����ų��ȱ���Ϊ��" + curDept.getDept_code().length();
            msg = DeptMngMsg.msgOrderWrong.toString() + curDept.getDept_code().length();
        } else {
            Object obj = CommUtil.fetchEntityBy("select px_code from DeptCode d where d.dept_code='" + curDept.getParent_code() + "'");
            prePx = SysUtil.objToStr(obj);
            if (!newPxCode.startsWith(prePx)) {
                msg = "�����Υ�����򣬵�ǰѡ���ŵ�����ű�����[" + prePx + "]��ͷ";
            } else if (SysUtil.objToInt(newPxCode.substring(prePx.length())) == 0) {
//                msg = "����ű���Ϊ����";
                msg = DeptMngMsg.msgOrderNum;
            }
        }
        return msg.toString();
    }
}
