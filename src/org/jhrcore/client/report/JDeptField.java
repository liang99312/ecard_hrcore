/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.client.report;

import javax.swing.JTextField;
import org.jhrcore.entity.DeptCode;

/**
 *
 * @author Administrator
 */
public class JDeptField extends JTextField {

    private DeptCode deptcode;

    public DeptCode getDeptcode() {
        return deptcode;
    }

    public void setDeptcode(DeptCode deptcode) {
        this.deptcode = deptcode;
    }
}
