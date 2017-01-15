/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhrcore.util;

import com.foundercy.pf.control.table.FTable;
import com.foundercy.pf.control.table.FTableModel;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.jhrcore.ui.ContextManager;
import org.jhrcore.ui.ExportDBFDataDialog;
import org.jhrcore.ui.ExportDBFDialog;

/**
 *
 * @author Administrator
 */
public class DBFUtil {

    public static List readDBF(String path) {
        List list = new ArrayList();
        InputStream fis = null;
        try {
            fis = new FileInputStream(path);
            DBFReader reader = new DBFReader(fis);
            reader.setCharactersetName("GB2312");
            int fieldsCount = reader.getFieldCount();
            for (int i = 0; i < fieldsCount; i++) {
                DBFField field = reader.getField(i);
                System.out.println(field.getName());
            }
            Object[] rowValues;
            while ((rowValues = reader.nextRecord()) != null) {
                for (int i = 0; i < rowValues.length; i++) {
                    System.out.println(rowValues[i]);
                }
                list.add(rowValues);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }

        return list;
    }

    public static void exportData(FTable ft){
        if(ft == null) return;       
        FTableModel model = (FTableModel) ft.getModel();
        if (model.getEntityClass() != null) {
            ExportDBFDialog eDlg = new ExportDBFDialog(model.getEntityClass(), ft, ft.getUseModuleCode(), ft.getAll_fields());
            ContextManager.locateOnMainScreenCenter(eDlg);
            eDlg.setVisible(true);
        } else {
            ExportDBFDataDialog dlg = new ExportDBFDataDialog(JOptionPane.getFrameForComponent(ft), ft);
            ContextManager.locateOnMainScreenCenter(dlg);
            dlg.setVisible(true);
        }
    }
    
    public static void generateDbfFromArray(String dbfName, String[] strutName, byte[] strutType, int[] strutLength, Object[][] data) {
        OutputStream fos = null;
        try {
            int fieldCount = strutName.length;
            DBFField[] fields = new DBFField[fieldCount];
            for (int i = 0; i < fieldCount; i++) {
                fields[i] = new DBFField();
                fields[i].setName(strutName[i]);
                fields[i].setDataType(strutType[i]);
                fields[i].setFieldLength(strutLength[i]);
            }
            DBFWriter writer = new DBFWriter();
            writer.setFields(fields);
            for (int i = 0; i < fieldCount; i++) {
                writer.addRecord(data[i]);
            }
            fos = new FileOutputStream(dbfName);
            writer.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    public static void ResultsetToArray(ResultSet rs) {
        try {
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();
            String[] strutName = new String[columnCount];
            byte[] strutType = new byte[columnCount];
            rs.last();
            int itemCount = rs.getRow();
            rs.first();
            Object[][] data = new Object[columnCount][itemCount];
            for (int i = 0; i < columnCount; i++) {
                strutType[i] = (byte) meta.getColumnType(i);
                strutName[i] = meta.getColumnName(i);
            }
            for (int i = 0; rs.next(); i++) {
                for (int j = 0; j < columnCount; j++) {
                    data[i][j] = rs.getObject(j);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
