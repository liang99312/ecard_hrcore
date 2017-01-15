/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhrcore.client.report;

import com.fr.cell.editor.AbstractCellEditor;
import com.fr.cell.editor.DateCellEditor;
import com.fr.cell.editor.NumberCellEditor;
import com.fr.cell.editor.TextCellEditor;
import com.fr.design.mainframe.JReportInternalFrame;
import com.fr.report.parameter.Parameter;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import org.jhrcore.client.report.componenteditor.CheckBoxCellEditor;
import org.jhrcore.client.report.componenteditor.CodeCellEditor;
import org.jhrcore.client.report.componenteditor.DeptCodeCellEditor;
import org.jhrcore.client.report.componenteditor.TextAreaCellEditor;
/**
 *
 * @author wangzhenhua
 */
public class FtReportUtil {
    private static Hashtable<String, String> htCodeFields = new Hashtable<String, String>();

    public static JReportInternalFrame getLocalJReportInternalFrame() {
        return new JReportInternalFrame(org.jhrcore.client.report.ReportPanel.getJWorkSheet());
    }

    public static Hashtable<String, String> getHtCodeFields() {
        return htCodeFields;
    }

    public static String getCodeType(String id){
        return htCodeFields.get(id);
    }

    public static AbstractCellEditor createParameterEditor(Parameter param){
        if ("��������".equals(param.getParam_type())){
            return new CodeCellEditor(param.getParam_type2().equals("ѡ�����")? 0 : 1, param.isMulti(), param.getParam_type3());//.getCellEditorComponent(null, null);
        }
        if ("��������".equals(param.getParam_type())){
            return new DeptCodeCellEditor(param.getParam_type2().equals("ѡ�����")? 0 : 1, param.isMulti());//.getCellEditorComponent(null, null);
        }
        if (param.isMulti()){
            return new TextAreaCellEditor();//.getCellEditorComponent(null, null);
        }
        //�ַ����������������͡������͡�����
        if ("�ַ���".equals(param.getParam_type())){
            return new TextCellEditor();//.getCellEditorComponent(null, null);
        }
        if ("����".equals(param.getParam_type())){
            return new NumberCellEditor();//.getCellEditorComponent(null, null);
        }
        if ("������".equals(param.getParam_type())){
            return new CheckBoxCellEditor();//.getCellEditorComponent(null, null);
        }
        if ("������".equals(param.getParam_type())){
            return new TextCellEditor();//.getCellEditorComponent(null, null);
        }
        if ("����".equals(param.getParam_type())){
            DateCellEditor dce = new DateCellEditor();
            if (param.getFormat() != null)
                dce.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
            return dce;//new DateCellEditor();//.getCellEditorComponent(null, null);
        }
        return new TextCellEditor();
    }
}
