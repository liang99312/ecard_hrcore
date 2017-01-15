/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.ui.renderer;

import java.util.ArrayList;
import java.util.List;
import org.jhrcore.util.SysUtil;
import org.jhrcore.comm.CodeManager;
import org.jhrcore.entity.Code;
import org.jhrcore.entity.annotation.ObjectListHint;

/**
 *
 * @author mxliteboss
 */
public class TempCodeEditor {

    private List codes = null;
    private Object cell_value = null;//当前值
    private boolean single = false;//是否多级
    private boolean enable = false;
    private String code_type;
    private boolean selectName = false;//是否选择名称

    public static TempCodeEditor getCodeEditor(Object bean, ObjectListHint objectListHint, Object cell_value) {
        TempCodeEditor editor = new TempCodeEditor();
        editor.setCell_value(cell_value);
        String hql = objectListHint.hqlForObjectList();
        editor.setEnable(true);
        editor.setSelectName(!hql.startsWith("from"));
        if (editor.isSelectName()) {
            editor.setSingle(false);
            List codes = new ArrayList();
            Code tmp_code = new Code();
            tmp_code.setCode_id(null);
            tmp_code.setCode_name("      ");
            tmp_code.setParent_id("ROOT");
            codes.add(tmp_code);
            codes.addAll(CodeManager.getCodeManager().getCode_types());
            editor.setCodes(codes);
            return editor;
        }
        final String code_type_name = hql.substring(hql.indexOf("=") + 1);
        editor.setCode_type(code_type_name);
        List codes = CodeManager.getCodeManager().getLimitCodes(bean, objectListHint);
        boolean empty = objectListHint.nullable() ? (codes.size() == 1) : codes.isEmpty();
        editor.setSingle(codes.size() < 1 || ((Code) codes.get(0)).getGrades() <= 2);
        List<Code> editCodes = new ArrayList();
        for (Object obj : codes) {
            Code c = (Code) obj;
            if (c.isEdit_flag()) {
                editCodes.add(c);
            }
        }
        if (cell_value == null || empty) {
            editor.setCodes(editCodes);
        } else {
            int editInt = editCodes.indexOf(cell_value);
            boolean enable = editInt != -1;
            boolean visible = codes.indexOf(cell_value) != -1;
            if (visible) {
                if (enable) {
                    codes.clear();
                    for(Code c:editCodes){
                        if(c.isHide_flag()&&c!=cell_value){
                            continue;
                        }
                        codes.add(c);
                    }
//                    codes.addAll(editCodes);
                }
            } else {
                codes.clear();
                Code c = new Code();
                c.setCode_name("***");
                codes.add(c);
                enable = false;
                editor.setCell_value(c);
            }
            editor.setEnable(enable);
            editor.setCodes(codes);
        }
        return editor;
    }

    public Object getCell_value() {
        return cell_value;
    }

    public void setCell_value(Object cell_value) {
        if (this.selectName) {
            this.cell_value = SysUtil.objToStr(cell_value);
        } else {
            this.cell_value = cell_value;
        }
    }

    public List getShowCodes() {
        List removes = new ArrayList();
        for (Object c : codes) {
            Code code = (Code) c;
            if (code.isHide_flag()) {
//                removes.add(c);
                continue;
            }
        }
        codes.removeAll(removes);
        if (cell_value != null && !codes.contains(cell_value)) {
            codes.add(0,cell_value);
        }
        return codes;
    }

    public List getCodes() {
        return codes;
    }

    public void setCodes(List codes) {
        this.codes = codes;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getCode_type() {
        return code_type;
    }

    public void setCode_type(String code_type) {
        this.code_type = code_type;
    }

    public boolean isSelectName() {
        return selectName;
    }

    public void setSelectName(boolean selectName) {
        this.selectName = selectName;
    }
}
